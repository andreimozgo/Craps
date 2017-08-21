package by.mozgo.craps.util;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

/**
 * Creates pool of database connections and manages it.
 *
 * @author Mozgo Andrei
 *
 */
public class ConnectionPool {

    private static final Logger LOG = LogManager.getLogger();
    private static final String UNEXPECTED_INTERRUPT = "Unexpected interrupt";
    private static final int VALID_TIMEOUT = 3; // seconds
    private static final int RETRIEVE_TIMEOUT = 300; // milliseconds
    private static AtomicReference<ConnectionPool> instance = new AtomicReference<>();
    private static Semaphore semaphore = new Semaphore(1);
    private static AtomicBoolean isEmpty = new AtomicBoolean(true);
    private final ThreadLocal<ConnectionWrapper> threadLocalConnection = new ThreadLocal<>();
    private AtomicInteger connectionsCount;
    private BlockingQueue<Connection> availableConnections;
    private AtomicBoolean isClosing;
    private Driver driver;
    private ConnectorDB connectorDB;
    private int minPoolSize;
    private int maxPoolSize;

    private ConnectionPool() {

        connectorDB = new ConnectorDB();
        minPoolSize = connectorDB.getMinPoolSize();
        maxPoolSize = connectorDB.getMaxPoolSize();
        isClosing = new AtomicBoolean(false);
        connectionsCount = new AtomicInteger();
        availableConnections = new ArrayBlockingQueue<>(maxPoolSize);

        try {
            driver = new com.mysql.jdbc.Driver();
            DriverManager.registerDriver(driver);
        } catch (SQLException e) {
            LOG.log(Level.FATAL, "Unable to register DB driver. {}", e, e);
            throw new RuntimeException("Unable to load db driver.\n" + e.getMessage(), e);
        }

        for (int i = 0; i < minPoolSize; i++) {
            availableConnections.add(createConnection());
        }
        if (getAvailableConnectionsCount() < minPoolSize) {
            for (int i = getAvailableConnectionsCount(); i < minPoolSize; i++) {
                availableConnections.add(createConnection());
            }
        }
    }

    /**
     * @return ConnectionPool instance
     */
    public static ConnectionPool getInstance() {
        if (isEmpty.get()) {
            try {
                semaphore.acquire();
                if (instance.get() == null) {
                    instance.set(new ConnectionPool());
                    isEmpty.set(false);
                }
            } catch (InterruptedException e) {
                LOG.log(Level.ERROR, "{}. {}", UNEXPECTED_INTERRUPT, e, e);
            } finally {
                semaphore.release();
            }
        }
        return instance.get();
    }

    /**
     * Establish and return new db connection
     *
     * @return
     */
    private Connection createConnection() {
        Connection conn = null;
        try {
            conn = connectorDB.getConnection();
            connectionsCount.incrementAndGet();
        } catch (SQLException e) {
            LOG.log(Level.ERROR, "Unable to establish database connection. {}", e, e);
        }
        return conn;
    }

    /**
     * Method closes pool and all connections.
     */
    public void closePool() {
        if (this.isConnectionOpen()) {
            this.getConnection().close();
        }
        isClosing.set(true);
        for(int i = 0; i < connectionsCount.get(); i++) {
            try {
                availableConnections.take().close();
            } catch (SQLException e) {
                LOG.log(Level.ERROR, "Unable to close connection", e, e);
            } catch (InterruptedException e) {
                LOG.log(Level.WARN, "{}. {}", UNEXPECTED_INTERRUPT, e, e);
            }
        }
        try {
            DriverManager.deregisterDriver(driver);
        } catch (SQLException e) {
            LOG.log(Level.ERROR, "Unable to deregister DB driver. {}", e, e);
        }
    }

    /**
     * Retrieve connection from pool. It creates wrapper and puts connection to
     * wrapper before retrieve.
     *
     * @return connection wrapper
     */
    public ConnectionWrapper getConnection() {
        ConnectionWrapper connectionWrapper = threadLocalConnection.get();
        if (connectionWrapper == null) {
            assertNotClosing();
            Connection conn = null;
            try {
                conn = availableConnections.poll(RETRIEVE_TIMEOUT, TimeUnit.MILLISECONDS);
                if (conn == null) {
                    if (connectionsCount.get() < maxPoolSize) {
                        conn = this.createConnection();
                    } else {
                        conn = availableConnections.take();
                    }
                } else {
                    try {
                        if (!conn.isValid(VALID_TIMEOUT)) {
                            conn.close();
                            connectionsCount.decrementAndGet();
                            conn = this.createConnection();
                        }
                    } catch (SQLException e) {
                        LOG.log(Level.ERROR, "Unable to establish database connection. {}", e, e);
                    }
                }
            } catch (InterruptedException e) {
                LOG.log(Level.WARN, "{}. {}", UNEXPECTED_INTERRUPT, e, e);
            }
            connectionWrapper = new ConnectionWrapper(conn);
            threadLocalConnection.set(connectionWrapper);
        }
        return connectionWrapper;
    }

    /**
     * Available only in package scope. Put connection back to pool. It's
     * usually called from wrapper
     *
     * @param conn
     */
    void releaseConnection(Connection conn) {
        if (availableConnections.size() < minPoolSize) {
            try {
                availableConnections.put(conn);
            } catch (InterruptedException e) {
                LOG.log(Level.WARN, "{}. {}", UNEXPECTED_INTERRUPT, e, e);
            }
        } else {
            try {
                conn.close();
                connectionsCount.decrementAndGet();
            } catch (SQLException e) {
                LOG.log(Level.ERROR, "Unable to close connection. {}", e, e);
            }
        }
        threadLocalConnection.remove();
    }

    /**
     * @return connections count available in pool at moment
     */
    public int getAvailableConnectionsCount() {
        return availableConnections.size();
    }

    /**
     * throws exception if perform action on closing pool
     */
    private void assertNotClosing() {
        if (isClosing.get()) {
            LOG.log(Level.FATAL, "Illegal state - connection pool is closing.");
            throw new RuntimeException("Cannot perform operation: pool is closing.");
        }
    }

    public boolean isConnectionOpen() {
        return (threadLocalConnection.get() != null);
    }
}

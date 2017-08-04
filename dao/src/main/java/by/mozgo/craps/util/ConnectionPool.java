package by.mozgo.craps.util;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

/**
 * Created by Andrei Mozgo. 2017.
 */
public class ConnectionPool {

    private static final String UNEXPECTED_INTERRUPT = "Unexpected interrupt";
    private static final Logger LOG = LogManager.getLogger();
    private static AtomicReference<ConnectionPool> instance = new AtomicReference<>();
    private static int VALID_TIMEOUT = 3; // seconds
    private static int RETRIEVE_TIMEOUT = 300; // milliseconds
    private static ResourceBundle resource = ResourceBundle.getBundle("database");
    private static int MAX_POOL_SIZE = Integer.parseInt(resource.getString("db.maxPoolSize"));
    private static int MIN_POOL_SIZE = Integer.parseInt(resource.getString("db.minPoolSize"));
    private static Semaphore semaphore = new Semaphore(1);
    private static AtomicBoolean isEmpty = new AtomicBoolean(true);
    private final ThreadLocal<ConnectionWrapper> threadLocalConnection = new ThreadLocal<>();
    private AtomicInteger connectionsCount;
    private BlockingQueue<Connection> availableConnections;
    private AtomicBoolean isClosing;
    private Driver driver;


    private ConnectionPool() {

        isClosing = new AtomicBoolean(false);
        connectionsCount = new AtomicInteger();
        availableConnections = new ArrayBlockingQueue<>(MAX_POOL_SIZE);

        try {
            driver = new com.mysql.jdbc.Driver();
            DriverManager.registerDriver(driver);
        } catch (SQLException e) {
            LOG.log(Level.FATAL, "Unable to register DB driver. {}", e, e);
            throw new RuntimeException("Unable to load db driver.\n" + e.getMessage(), e);
        }
        for (int i = 0; i < MIN_POOL_SIZE; i++) {
            availableConnections.add(createConnection());
        }
        if (getAvailableConnectionsCount() < MIN_POOL_SIZE) {
            for (int i = getAvailableConnectionsCount(); i < MIN_POOL_SIZE; i++) {
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
     * Estasblish and return new db connection
     *
     * @return
     */
    private Connection createConnection() {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(resource.getString("db.url"), resource.getString("db.user"),
                    resource.getString("db.password"));
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
        isClosing.set(true);
        while (connectionsCount.get() > 0) {
            try {
                availableConnections.take().close();
                connectionsCount.decrementAndGet();
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
                    if (connectionsCount.get() < MAX_POOL_SIZE) {
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
        if (availableConnections.size() < MIN_POOL_SIZE) {
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

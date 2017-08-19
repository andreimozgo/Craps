package by.mozgo.craps.util;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * Created by Andrei Mozgo. 2017.
 */
public class ConnectorDB {
    private static final Logger LOG = LogManager.getLogger();
    private static final String DATABASE_PROPERTIES = "database.properties";
    private static final String MIN_POOL_SIZE_DEFAULT = "5";
    private static final String MAX_POOL_SIZE_DEFAULT = "25";
    private final String DB_URL;
    private final String USER;
    private final String PASSWORD;
    private final int MIN_POOL_SIZE;
    private final int MAX_POOL_SIZE;

    ConnectorDB(){
        Properties props = new Properties();
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        try (InputStream in = getClass().getClassLoader().getResourceAsStream(DATABASE_PROPERTIES)) {
            if (in != null) {
                props.load(in);
            }
        } catch (IOException e) {
            LOG.log(Level.FATAL, "Error while reading DB properties file. {}", e, e);
            throw new RuntimeException("Unable to read " + DATABASE_PROPERTIES);
        }

        DB_URL = props.getProperty("url");
        USER = props.getProperty("user");
        PASSWORD = props.getProperty("password");
        MIN_POOL_SIZE = Integer.parseInt(props.getProperty("minPoolSize", MIN_POOL_SIZE_DEFAULT));
        MAX_POOL_SIZE = Integer.parseInt(props.getProperty("maxPoolSize", MAX_POOL_SIZE_DEFAULT));
    }

    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL, USER, PASSWORD);
    }

    public int getMinPoolSize() {
        return MIN_POOL_SIZE;
    }

    public int getMaxPoolSize() {
        return MAX_POOL_SIZE;
    }
}

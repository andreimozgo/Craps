package by.mozgo.craps.util;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.SQLException;

/**
 * Created by Andrei Mozgo. 2017.
 */
public class TransactionAssistant {
    private static final Logger LOG = LogManager.getLogger();

    public static void startTransaction(){
        ConnectionWrapper connection = ConnectionPool.getInstance().getConnection();
        try {
            connection.setAutoCommit(false);
        } catch (SQLException e) {
            LOG.log(Level.ERROR, "Transaction failed. {}", e);
        }
    }

    public static void endTransaction(){
        ConnectionWrapper connection = ConnectionPool.getInstance().getConnection();
        try {
            connection.commit();
        } catch (SQLException e1) {
            LOG.log(Level.ERROR, "Transaction failed. {}", e1);
            try {
                connection.rollback();
            } catch (SQLException e2) {
                LOG.log(Level.ERROR, "Transaction rollback failed. {}", e2);
            }
        } finally {
            try {
                connection.setAutoCommit(true);
            } catch (SQLException e) {
                LOG.log(Level.ERROR, "Transaction failed. {}", e);
            }
        }
    }
}

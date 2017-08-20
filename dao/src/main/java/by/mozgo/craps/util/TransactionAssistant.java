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

    public static void startTransaction() throws TransactionAssistantException {
        ConnectionWrapper connection = ConnectionPool.getInstance().getConnection();
        try {
            connection.setAutoCommit(false);
        } catch (SQLException e) {
            throw new TransactionAssistantException("Transaction failed. " + e.getMessage(), e);
        }
    }

    public static void endTransaction() throws TransactionAssistantException {
        ConnectionWrapper connection = ConnectionPool.getInstance().getConnection();
        try {
            connection.commit();
        } catch (SQLException e1) {
            LOG.log(Level.ERROR, "Transaction failed. {}", e1);
            try {
                connection.rollback();
                throw new TransactionAssistantException("Transaction failed. Transaction rolled back. " + e1.getMessage(), e1);
            } catch (SQLException e2) {
                throw new TransactionAssistantException("Transaction failed. Transaction rollback failed. " + e2.getMessage(), e2);
            }
        } finally {
            try {
                connection.setAutoCommit(true);
            } catch (SQLException e) {
                throw new TransactionAssistantException("Unable to change autocommit state. " + e.getMessage(), e);
            }
        }
    }

    public static void rollBackTransaction() throws TransactionAssistantException {
        ConnectionWrapper connection = ConnectionPool.getInstance().getConnection();
        try {
            connection.rollback();
        } catch (SQLException e) {
            throw new TransactionAssistantException("Transaction rollback failed. " + e.getMessage(), e);
        } finally {
            try {
                connection.setAutoCommit(true);
            } catch (SQLException e) {
                throw new TransactionAssistantException("Unable to change autocommit state. " + e.getMessage(), e);
            }
        }

    }
}
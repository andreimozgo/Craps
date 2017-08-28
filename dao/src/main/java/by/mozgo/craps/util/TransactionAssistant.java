package by.mozgo.craps.util;

import java.sql.SQLException;

/**
 * Manages transactions.
 *
 * @author Mozgo Andrei
 */
public class TransactionAssistant {
    private ConnectionWrapper connection;

    /**
     * Sets AutoCommit of the current connection to the false state.
     * Makes current connection available for all daos.
     *
     * @throws TransactionAssistantException if a database access error occurs
     * @see by.mozgo.craps.util.ConnectionWrapper#setAutoCommit(boolean)
     */
    public void startTransaction() throws TransactionAssistantException {
        connection = ConnectionPool.getInstance().getConnection();
        try {
            connection.setAutoCommit(false);
        } catch (SQLException e) {
            throw new TransactionAssistantException("Unable to start transaction. " + e.getMessage(), e);
        }
    }

    /**
     * @throws TransactionAssistantException if a database access error occurs
     * @see by.mozgo.craps.util.ConnectionWrapper#commit()
     */
    public void commitTransaction() throws TransactionAssistantException {
        try {
            connection.commit();
        } catch (SQLException e) {
            throw new TransactionAssistantException("Unable to commit transaction. " + e.getMessage(), e);
        }
    }

    /**
     * Attempts to set AutoCommit of the current connection to the false state.
     * Closes current connection.
     *
     * @throws TransactionAssistantException if a database access error occurs
     * @see by.mozgo.craps.util.ConnectionWrapper#setAutoCommit(boolean)
     * @see ConnectionWrapper#close()
     */
    public void endTransaction() throws TransactionAssistantException {
        try {
            connection.setAutoCommit(true);
        } catch (SQLException e) {
            throw new TransactionAssistantException("Unable to change autocommit state. " + e.getMessage(), e);
        } finally {
            connection.close();
        }
    }

    /**
     * Attempts to rollback current transaction
     *
     * @throws TransactionAssistantException if a database access error occurs
     * @see ConnectionWrapper#rollback()
     */
    public void rollBackTransaction() throws TransactionAssistantException {
        ConnectionWrapper connection = ConnectionPool.getInstance().getConnection();
        try {
            connection.rollback();
        } catch (SQLException e) {
            throw new TransactionAssistantException("Transaction rollback failed. " + e.getMessage(), e);
        }
    }
}
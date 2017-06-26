package by.mozgo.craps.services.transaction;

import by.mozgo.craps.dao.BaseDao;
import by.mozgo.craps.util.ConnectionPool;
import by.mozgo.craps.util.ConnectionWrapper;

import java.sql.SQLException;

/**
 * Created by Andrei Mozgo. 2017.
 */
public class TransactionHelper {
    private ConnectionWrapper connection = ConnectionPool.getInstance().getConnection();

    public void beginTransaction(BaseDao... daos) {
        try {
            connection.setAutoCommit(false);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        for (BaseDao dao : daos) {
            dao.setConnection(connection);
        }
    }

    public void endTransaction() {
        try {
            connection.setAutoCommit(true);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        connection.close();
    }
}
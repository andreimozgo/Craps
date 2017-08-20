package by.mozgo.craps.dao.impl;

import by.mozgo.craps.dao.BetTypeDao;
import by.mozgo.craps.dao.DaoException;
import by.mozgo.craps.entity.BetType;
import by.mozgo.craps.util.ConnectionPool;
import by.mozgo.craps.util.ConnectionWrapper;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by Andrei Mozgo. 2017.
 */
public class BetTypeDaoImpl extends BaseDaoImpl<BetType> implements BetTypeDao {
    private static final String TABLE_NAME = "bet_type";
    private static final String QUERY_FIND_NAME_BY_ID = "SELECT name FROM bet_type WHERE id = ?";

    private static BetTypeDaoImpl instance = null;

    public BetTypeDaoImpl() {
        tableName = TABLE_NAME;
    }

    public static BetTypeDaoImpl getInstance() {
        if (instance == null) {
            instance = new BetTypeDaoImpl();
        }
        return instance;
    }

    @Override
    public String findNameById(Integer id) throws DaoException {
        ConnectionWrapper connection = ConnectionPool.getInstance().getConnection();
        String name = null;
        try (PreparedStatement preparedStatement = connection.prepareStatement(QUERY_FIND_NAME_BY_ID)) {
            preparedStatement.setInt(1, id);
            ResultSet result = preparedStatement.executeQuery();
            if (result.isBeforeFirst()) {
                result.next();
                name = result.getString(1);
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        }
        return name;
    }
}

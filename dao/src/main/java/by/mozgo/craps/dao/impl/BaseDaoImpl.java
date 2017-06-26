package by.mozgo.craps.dao.impl;

import by.mozgo.craps.dao.BaseDao;
import by.mozgo.craps.dao.exception.DaoException;
import by.mozgo.craps.entity.AbstractEntity;
import by.mozgo.craps.util.ConnectionPool;
import by.mozgo.craps.util.ConnectionWrapper;

import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Created by Andrei Mozgo. 2017.
 */
public abstract class BaseDaoImpl<T extends AbstractEntity> implements BaseDao<T> {
    ConnectionWrapper connection;
    String tableName;

    @Override
    public void delete(Integer id) throws DaoException {
        String query = "DELETE FROM " + tableName + " WHERE id = ?";
        connection = ConnectionPool.getInstance().getConnection();
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }
}

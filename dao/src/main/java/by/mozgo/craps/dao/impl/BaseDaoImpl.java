package by.mozgo.craps.dao.impl;

import by.mozgo.craps.dao.BaseDao;
import by.mozgo.craps.dao.DaoException;
import by.mozgo.craps.entity.AbstractEntity;
import by.mozgo.craps.util.ConnectionPool;
import by.mozgo.craps.util.ConnectionWrapper;

import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * BaseDao implementation for MYSQL DB
 *
 * @author Mozgo Andrei
 *
 */
public abstract class BaseDaoImpl<T extends AbstractEntity> implements BaseDao<T> {
    protected String tableName;

    public int create(T t) throws DaoException {
        throw new DaoException("Method unavailable");
    }

    public void update(T t) throws DaoException{
        throw new DaoException("Method unavailable");
    }

    @Override
    public void delete(Integer id) throws DaoException {
        String query = "DELETE FROM " + tableName + " WHERE id = ?";
        ConnectionWrapper connection = ConnectionPool.getInstance().getConnection();
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }
}

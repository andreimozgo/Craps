package by.mozgo.craps.dao;

import by.mozgo.craps.dao.exception.DaoException;
import by.mozgo.craps.entity.AbstractEntity;
import by.mozgo.craps.util.ConnectionWrapper;

public interface BaseDao<T extends AbstractEntity> {

    void create(T entity) throws DaoException;

    T findEntityById(Integer id) throws DaoException;

    void delete(Integer id) throws DaoException;

    void setConnection(ConnectionWrapper connection);

}

package by.mozgo.craps.dao;

import by.mozgo.craps.dao.exception.DaoException;
import by.mozgo.craps.entity.AbstractEntity;

public interface BaseDao<T extends AbstractEntity> {

    Integer create(T entity) throws DaoException;

    T findEntityById(Integer id) throws DaoException;

    void update(T t) throws DaoException;

    void delete(Integer id) throws DaoException;
}

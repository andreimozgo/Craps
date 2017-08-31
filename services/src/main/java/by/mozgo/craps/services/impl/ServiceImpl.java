package by.mozgo.craps.services.impl;

import by.mozgo.craps.dao.BaseDao;
import by.mozgo.craps.dao.DaoException;
import by.mozgo.craps.entity.AbstractEntity;
import by.mozgo.craps.services.Service;
import by.mozgo.craps.services.ServiceException;

/**
 * Contains base service implementation
 *
 * @author Mozgo Andrei
 *
 */
public abstract class ServiceImpl<T extends AbstractEntity> implements Service<T> {
    protected BaseDao baseDao;

    @Override
    public long create(T t) throws ServiceException {
        long id;
        try {
            id = baseDao.create(t);
        } catch (DaoException e) {
            throw new ServiceException("Exception in DAO. " + e.getMessage(), e);
        }
        return id;
    }

    @Override
    public void update(T t) throws ServiceException {
        try {
            baseDao.update(t);
        } catch (DaoException e) {
            throw new ServiceException("Exception in DAO. " + e.getMessage(), e);
        }
    }

    @Override
    public void delete(long id) throws ServiceException {
        try {
            baseDao.delete(id);
        } catch (DaoException e) {
            throw new ServiceException("Exception in DAO. " + e.getMessage(), e);
        }
    }
}

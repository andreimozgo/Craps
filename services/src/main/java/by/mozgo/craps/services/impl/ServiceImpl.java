package by.mozgo.craps.services.impl;

import by.mozgo.craps.dao.BaseDao;
import by.mozgo.craps.dao.DaoException;
import by.mozgo.craps.entity.AbstractEntity;
import by.mozgo.craps.services.Service;
import by.mozgo.craps.services.ServiceException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Created by Andrei Mozgo. 2017.
 */
public abstract class ServiceImpl<T extends AbstractEntity> implements Service<T> {
    private static final Logger LOG = LogManager.getLogger();
    protected BaseDao baseDao;

    @Override
    public int create(T t) throws ServiceException {
        int id;
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
    public void delete(Integer id) throws ServiceException {
        try {
            baseDao.delete(id);
        } catch (DaoException e) {
            throw new ServiceException("Exception in DAO. " + e.getMessage(), e);
        }
    }
}

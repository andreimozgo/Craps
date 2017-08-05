package by.mozgo.craps.services.impl;

import by.mozgo.craps.dao.BaseDao;
import by.mozgo.craps.dao.exception.DaoException;
import by.mozgo.craps.entity.AbstractEntity;
import by.mozgo.craps.services.Service;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Created by Andrei Mozgo. 2017.
 */
public abstract class ServiceImpl<T extends AbstractEntity> implements Service<T> {
    private static final Logger LOG = LogManager.getLogger();
    protected BaseDao baseDao;

    @Override
    public Integer create(T t) {
        Integer id = null;
        try {
            id = baseDao.create(t);
        } catch (DaoException e) {
            LOG.log(Level.ERROR, "Exception in DAO {}", e);
        }
        return id;
    }

    @Override
    public T findEntityById(Integer id) {
        return null;
    }

    @Override
    public void delete(Integer id) {
    }
}

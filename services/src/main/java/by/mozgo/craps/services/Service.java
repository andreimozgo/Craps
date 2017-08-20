package by.mozgo.craps.services;

import by.mozgo.craps.entity.AbstractEntity;

public interface Service<T extends AbstractEntity> {
    int create(T t) throws ServiceException;

    void update(T t) throws ServiceException;

    void delete(Integer id) throws ServiceException;
}

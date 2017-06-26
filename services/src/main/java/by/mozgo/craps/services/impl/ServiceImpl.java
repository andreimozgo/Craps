package by.mozgo.craps.services.impl;

import by.mozgo.craps.entity.AbstractEntity;
import by.mozgo.craps.services.Service;

/**
 * Created by Andrei Mozgo. 2017.
 */
public class ServiceImpl<T extends AbstractEntity> implements Service<T> {

    @Override
    public void create(T t) {

    }

    @Override
    public T findEntityById(Integer id) {
        return null;
    }

    @Override
    public void delete(Integer id) {

    }
}

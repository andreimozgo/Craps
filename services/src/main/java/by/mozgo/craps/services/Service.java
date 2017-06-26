package by.mozgo.craps.services;

import by.mozgo.craps.entity.AbstractEntity;

public interface Service<T extends AbstractEntity> {

    void create(T t);

    T findEntityById(Integer id);

    void delete(Integer id);
}

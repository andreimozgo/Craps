package by.mozgo.craps.services;

import by.mozgo.craps.entity.AbstractEntity;

public interface Service<T extends AbstractEntity> {
    int create(T t);

    void update(T t);

    void delete(Integer id);
}

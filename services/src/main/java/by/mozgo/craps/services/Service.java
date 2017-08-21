package by.mozgo.craps.services;

import by.mozgo.craps.entity.AbstractEntity;

/**
 * Interface that contains base service operations
 *
 * @param <T> - AbstractEntity type
 * @author Mozgo Andrei
 */
public interface Service<T extends AbstractEntity> {
    /**
     * It's called to create new entity
     *
     * @param t entity to insert
     * @return Id of inserted entity
     * @throws ServiceException
     */
    int create(T t) throws ServiceException;

    /**
     * It's called to update entity
     *
     * @param t entity to update
     * @throws ServiceException
     */
    void update(T t) throws ServiceException;

    /**
     * It's called to delete entity
     *
     * @param id entity Id to delete
     * @throws ServiceException
     */
    void delete(Integer id) throws ServiceException;
}

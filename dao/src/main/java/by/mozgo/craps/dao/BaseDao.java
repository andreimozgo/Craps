package by.mozgo.craps.dao;

import by.mozgo.craps.entity.AbstractEntity;

/**
 * Interface that contains basic DAO operations
 *
 * @param <T> - AbstractEntity type
 * @author Mozgo Andrei
 */
public interface BaseDao<T extends AbstractEntity> {
    /**
     * Inserts entity to database
     *
     * @param t entity to insert
     * @return Id of inserted entity
     * @throws DaoException if a database access error occurs or method unavailable
     */
    int create(T t) throws DaoException;

    /**
     * Updates entity at database
     *
     * @param t entity to update
     * @throws DaoException if a database access error occurs or method unavailable
     */
    void update(T t) throws DaoException;

    /**
     * Deletes entity from database
     *
     * @param id entity Id to delete
     * @throws DaoException if a database access error occurs
     */
    void delete(Integer id) throws DaoException;
}

package by.mozgo.craps.dao;

import by.mozgo.craps.entity.BetType;

/**
 * Interface that contains bet type DAO operations
 *
 * @author Mozgo Andrei
 *
 */
public interface BetTypeDao extends BaseDao<BetType> {
    /**
     * Finds name of bet type by id
     *
     * @param id
     * @return name of bet type
     * @throws DaoException if a database access error occurs
     */
    String findNameById(Integer id) throws DaoException;
}

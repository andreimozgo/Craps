package by.mozgo.craps.dao;

import by.mozgo.craps.entity.Bet;

/**
 * Interface that contains bet DAO operations
 *
 * @author Mozgo Andrei
 *
 */
public interface BetDao extends BaseDao<Bet> {
    /**
     * Finds number of bets was made by user
     *
     * @param userId
     * @return number of bets was made by user
     * @throws DaoException if a database access error occurs
     */
    int findBetsNumber(int userId) throws DaoException;

    /**
     * Finds number of bets was won by user
     *
     * @param userId
     * @return number of bets was won by user
     * @throws DaoException if a database access error occurs
     */
    int findWonBetsNumber(int userId) throws DaoException;
}

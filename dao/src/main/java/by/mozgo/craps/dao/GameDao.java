package by.mozgo.craps.dao;

import by.mozgo.craps.entity.Game;

/**
 * Interface that contains game DAO operations
 *
 * @author Mozgo Andrei
 *
 */
public interface GameDao extends BaseDao<Game> {
    /**
     * Finds number of games played by user
     *
     * @param userId
     * @return number of games
     * @throws DaoException if a database access error occurs
     */
    int findGamesNumber(int userId) throws DaoException;
}

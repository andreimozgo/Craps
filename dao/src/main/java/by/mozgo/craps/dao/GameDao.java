package by.mozgo.craps.dao;

import by.mozgo.craps.entity.Game;

/**
 * Created by Andrei Mozgo. 2017.
 */
public interface GameDao extends BaseDao<Game> {
    int findGamesNumber(int userId) throws DaoException;
}

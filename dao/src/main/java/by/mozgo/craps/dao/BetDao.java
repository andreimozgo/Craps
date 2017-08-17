package by.mozgo.craps.dao;

import by.mozgo.craps.entity.Bet;

/**
 * Created by Andrei Mozgo. 2017.
 */
public interface BetDao extends BaseDao<Bet> {
    int findBetsNumber(int userId) throws DaoException;

    int findWonBetsNumber(int userId) throws DaoException;
}

package by.mozgo.craps.dao;

import by.mozgo.craps.dao.exception.DaoException;
import by.mozgo.craps.entity.Bet;

/**
 * Created by Andrei Mozgo. 2017.
 */
public interface BetDao extends BaseDao<Bet> {
    int getBetsNumber(int userId) throws DaoException;

    int getWonBetsNumber(int userId) throws DaoException;
}

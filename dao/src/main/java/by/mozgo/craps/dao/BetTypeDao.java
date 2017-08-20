package by.mozgo.craps.dao;

import by.mozgo.craps.entity.BetType;

/**
 * Created by Andrei Mozgo. 2017.
 */
public interface BetTypeDao extends BaseDao<BetType> {
    String findNameById(Integer id) throws DaoException;
}

package by.mozgo.craps.dao;

import by.mozgo.craps.dao.exception.DaoException;
import by.mozgo.craps.entity.User;

public interface UserDao extends Dao<User> {

    String getPassword(String email) throws DaoException;

    User findUserByEmail(String email) throws DaoException;

}

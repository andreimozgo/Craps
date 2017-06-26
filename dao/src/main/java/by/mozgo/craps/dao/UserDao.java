package by.mozgo.craps.dao;

import by.mozgo.craps.dao.exception.DaoException;
import by.mozgo.craps.entity.User;

import java.util.List;

public interface UserDao extends BaseDao<User> {

    String getPassword(String email) throws DaoException;

    User findUserByEmail(String email) throws DaoException;

    List getAll(int recordsPerPage, int currentPage) throws DaoException;

}

package by.mozgo.craps.dao;

import by.mozgo.craps.entity.User;

import java.util.List;

public interface UserDao extends BaseDao<User> {

    String findPassword(String email) throws DaoException;

    User findUserByEmail(String email) throws DaoException;

    List findAll(int recordsPerPage, int currentPage) throws DaoException;

    void updateRole(Integer userId, int role) throws DaoException;

    int findNumber() throws DaoException;

    void updateWithPass(User user)  throws DaoException;
}

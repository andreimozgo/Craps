package by.mozgo.craps.services;

import by.mozgo.craps.entity.User;

import java.util.List;

public interface UserService extends Service<User> {
    boolean checkUser(String enterLogin, String enterPass) throws ServiceException;

    User findUserByEmail(String login) throws ServiceException;

    int findPagesNumber(int recordsOnPage) throws ServiceException;

    List<User> findAll(int recordsOnPage, int currentPage) throws ServiceException;

    void updateRole(Integer userId, int roleId) throws ServiceException;

    User makePayment(User user, String amount) throws ServiceException;
}

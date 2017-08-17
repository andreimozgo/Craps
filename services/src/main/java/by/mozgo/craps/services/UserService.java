package by.mozgo.craps.services;

import by.mozgo.craps.entity.User;

import java.util.List;

public interface UserService extends Service<User> {

    boolean checkUser(String enterLogin, String enterPass);

    User findUserByEmail(String login);

    int findPagesNumber(int recordsOnPage);

    List<User> findAll(int recordsOnPage, int currentPage);

    void updateRole(Integer userId, int role);

    User makePayment(User user, String amount);
}

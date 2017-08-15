package by.mozgo.craps.services;

import by.mozgo.craps.entity.User;

import java.util.List;

public interface UserService extends Service<User> {

    boolean checkUser(String enterLogin, String enterPass);

    User findUserByEmail(String login);

    int getNumberOfPages(int recordsPerPage);

    List<User> getAll(int recordsPerPage, int currentPage);

    void updateRole(Integer userId, int role);

    User makePayment(User user, String amount);
}

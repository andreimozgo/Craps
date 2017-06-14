package by.mozgo.craps.services;

import by.mozgo.craps.entity.User;

public interface UserService extends Service<User> {
    boolean checkPassword(String enterLogin, String enterPass);

    User findUserByEmail(String login);

}

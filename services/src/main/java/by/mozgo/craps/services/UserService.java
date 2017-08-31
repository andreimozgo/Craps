package by.mozgo.craps.services;

import by.mozgo.craps.entity.User;

import java.util.List;

/**
 * Interface that contains user service operations
 *
 * @author Mozgo Andrei
 */
public interface UserService extends Service<User> {
    /**
     * Checks if user with such email and password exist in application
     *
     * @param email
     * @param password
     * @return true if user exists and false if doesn't
     * @throws ServiceException
     */
    boolean checkUser(String email, String password) throws ServiceException;

    /**
     * Finds user by given email
     *
     * @param email
     * @return User entity
     * @throws ServiceException
     */
    User findUserByEmail(String email) throws ServiceException;

    /**
     * Finds number of pages depending on records showed on page.
     * Used to realise the pagination
     *
     * @param recordsOnPage records showed on page
     * @return number of pages
     * @throws ServiceException
     */
    int findPagesNumber(int recordsOnPage) throws ServiceException;

    /**
     * Finds list of users depending on pagination parameters
     *
     * @param recordsOnPage number of records showed on page
     * @param currentPage   current page number to show
     * @return list of users
     * @throws ServiceException
     */
    List<User> findAll(int recordsOnPage, int currentPage) throws ServiceException;

    /**
     * Updates user role
     *
     * @param userId user Id
     * @param roleId Id of new user role
     * @throws ServiceException
     */
    void updateRole(long userId, byte roleId) throws ServiceException;

    /**
     * Makes a payment from user balance
     *
     * @param user
     * @param amount amount of made bet
     * @return User entity with new balance
     * @throws ServiceException
     */
    User makePayment(User user, String amount) throws ServiceException;
}

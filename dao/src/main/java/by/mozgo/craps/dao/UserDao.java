package by.mozgo.craps.dao;

import by.mozgo.craps.entity.User;

import java.util.List;

/**
 * Interface that contains user DAO operations
 *
 * @author Mozgo Andrei
 */
public interface UserDao extends BaseDao<User> {

    /**
     * Finds hashed password stored at database by user email
     *
     * @param email
     * @return hashed user password
     * @throws DaoException if a database access error occurs
     */
    String findPassword(String email) throws DaoException;

    /**
     * Finds User entity by their email
     *
     * @param email
     * @return User
     * @throws DaoException if a database access error occurs
     */
    User findUserByEmail(String email) throws DaoException;

    /**
     * Finds list of users depending on pagination parameters
     *
     * @param recordsOnPage number of records showed on page
     * @param currentPage   current page number to show
     * @return list of users
     * @throws DaoException if a database access error occurs
     */
    List findAll(int recordsOnPage, int currentPage) throws DaoException;

    /**
     * Updates user role at database
     *
     * @param userId
     * @param role   Id of new user role
     * @throws DaoException if a database access error occurs
     */
    void updateRole(long userId, byte role) throws DaoException;

    /**
     * Finds number of users stored at database
     *
     * @return number of users
     * @throws DaoException if a database access error occurs
     */
    long findNumber() throws DaoException;

    /**
     * Updates entity User with its password attribute
     *
     * @param user
     * @throws DaoException if a database access error occurs
     */
    void updateWithPass(User user)  throws DaoException;
}

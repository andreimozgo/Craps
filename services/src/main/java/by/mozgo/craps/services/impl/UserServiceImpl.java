package by.mozgo.craps.services.impl;

import by.mozgo.craps.dao.DaoException;
import by.mozgo.craps.dao.UserDao;
import by.mozgo.craps.dao.impl.UserDaoImpl;
import by.mozgo.craps.entity.User;
import by.mozgo.craps.services.ServiceException;
import by.mozgo.craps.services.UserService;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

/**
 * Contains user service implementation
 *
 * @author Mozgo Andrei
 */
public class UserServiceImpl extends ServiceImpl<User> implements UserService {
    private static final String DIGEST_ALGORITHM = "MD5";
    private static UserServiceImpl instance = null;
    private UserDao userDao = UserDaoImpl.getInstance();

    private UserServiceImpl() {
        baseDao = userDao;
    }

    public static UserServiceImpl getInstance() {
        if (instance == null) {
            instance = new UserServiceImpl();
        }
        return instance;
    }

    @Override
    public long create(User user) throws ServiceException {
        long id;
        user.setPassword(hash(user.getPassword()));
        try {
            id = userDao.create(user);
        } catch (DaoException e) {
            throw new ServiceException("Exception in DAO. " + e.getMessage(), e);
        }
        return id;
    }

    public User findUserByEmail(String email) throws ServiceException {
        User user;
        try {
            user = userDao.findUserByEmail(email);
        } catch (DaoException e) {
            throw new ServiceException("Exception in DAO. " + e.getMessage(), e);
        }
        return user;
    }

    @Override
    public void update(User user) throws ServiceException {
        if (user.getPassword() != null) {
            user.setPassword(hash(user.getPassword()));
            try {
                userDao.updateWithPass(user);
            } catch (DaoException e) {
                throw new ServiceException("Exception in DAO. " + e.getMessage(), e);
            } finally {
                user.setPassword(null);
            }
        } else {
            try {
                userDao.update(user);
            } catch (DaoException e) {
                throw new ServiceException("Exception in DAO. " + e.getMessage(), e);
            }
        }
    }

    public void delete(long id) throws ServiceException {
        try {
            userDao.delete(id);
        } catch (DaoException e) {
            throw new ServiceException("Exception in DAO. " + e.getMessage(), e);
        }
    }

    public boolean checkUser(String email, String password) throws ServiceException {
        boolean result;
        try {
            result = (hash(password)).equals(userDao.findPassword(email));
        } catch (DaoException e) {
            throw new ServiceException("Exception in DAO. " + e.getMessage(), e);
        }
        return result;
    }

    public List<User> findAll(int recordsOnPage, int currentPage) throws ServiceException {
        List<User> users;
        try {
            users = userDao.findAll(recordsOnPage, currentPage);
        } catch (DaoException e) {
            throw new ServiceException("Exception in DAO. " + e.getMessage(), e);
        }
        return users;
    }

    public void updateRole(long userId, byte roleId) throws ServiceException {
        try {
            userDao.updateRole(userId, roleId);
        } catch (DaoException e) {
            throw new ServiceException("Exception in DAO. " + e.getMessage(), e);
        }
    }

    public int findPagesNumber(int recordsOnPage) throws ServiceException {
        int numberOfPages;
        try {
            long numberOfRecords = userDao.findNumber();
            numberOfPages = Math.round(numberOfRecords / recordsOnPage);
            if ((numberOfRecords % recordsOnPage) > 0) numberOfPages++;
        } catch (DaoException e) {
            throw new ServiceException("Exception in DAO. " + e.getMessage(), e);
        }
        return numberOfPages;
    }

    public User makePayment(User user, String amount) throws ServiceException {
        BigDecimal balance = user.getBalance();
        user.setBalance(balance.add(new BigDecimal(amount)));
        update(user);
        return user;
    }

    private String hash(String input) throws ServiceException {
        String md5Hashed;
        try {
            MessageDigest digest = MessageDigest.getInstance(DIGEST_ALGORITHM);
            digest.update(input.getBytes(), 0, input.length());
            md5Hashed = new BigInteger(1, digest.digest()).toString(16);
        } catch (NoSuchAlgorithmException e) {
            throw new ServiceException("Exception during hash password.\n " + e.getMessage(), e);
        }
        return md5Hashed;
    }
}

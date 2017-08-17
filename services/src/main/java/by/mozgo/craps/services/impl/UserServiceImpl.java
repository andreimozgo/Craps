package by.mozgo.craps.services.impl;

import by.mozgo.craps.dao.DaoException;
import by.mozgo.craps.dao.UserDao;
import by.mozgo.craps.dao.impl.UserDaoImpl;
import by.mozgo.craps.entity.User;
import by.mozgo.craps.services.UserService;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

public class UserServiceImpl extends ServiceImpl<User> implements UserService {
    private static final Logger LOG = LogManager.getLogger();
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

    public boolean checkUser(String email, String password) {
        boolean result = false;
        try {
            result = (hash(password)).equals(userDao.findPassword(email));
        } catch (DaoException e) {
            LOG.log(Level.ERROR, "Exception {}", e);
        }
        return result;
    }

    @Override
    public int create(User user) {
        Integer id = null;
        user.setPassword(hash(user.getPassword()));
        try {
            id = userDao.create(user);
        } catch (DaoException e) {
            LOG.log(Level.ERROR, "Exception in DAO {}", e);
        }
        return id;
    }

    public User findEntityById(Integer id) {
        return null;
    }

    public void delete(Integer id) {
        try {
            userDao.delete(id);
        } catch (DaoException e) {
            LOG.log(Level.ERROR, "Exception in DAO {}", e);
        }
    }

    public User findUserByEmail(String email) {
        User user = null;
        try {
            user = userDao.findUserByEmail(email);
        } catch (DaoException e) {
            LOG.log(Level.ERROR, "Exception in DAO {}", e);
        }
        return user;
    }

    @Override
    public void update(User user) {
        if (user.getPassword() != null) {
            user.setPassword(hash(user.getPassword()));
            try {
                userDao.updateWithPass(user);
            } catch (DaoException e) {
                LOG.log(Level.ERROR, "Exception in DAO {}", e);
            } finally {
                user.setPassword(null);
            }
        } else {
            try {
                userDao.update(user);
            } catch (DaoException e) {
                LOG.log(Level.ERROR, "Exception in DAO {}", e);
            }
        }
    }

    public List<User> findAll(int recordsOnPage, int currentPage) {
        List<User> users = null;
        try {
            users = userDao.findAll(recordsOnPage, currentPage);
        } catch (DaoException e) {
            LOG.log(Level.ERROR, "Exception in DAO {}", e);
        }
        return users;
    }

    public void updateRole(Integer userId, int role) {
        try {
            userDao.updateRole(userId, role);
        } catch (DaoException e) {
            LOG.log(Level.ERROR, "Exception in DAO {}", e);
        }
    }

    public int findPagesNumber(int recordsOnPage) {
        int numberOfPages = 1;
        try {
            int numberOfRecords = userDao.findNumber();
            numberOfPages = Math.round(numberOfRecords / recordsOnPage);
            if ((numberOfRecords % recordsOnPage) > 0) numberOfPages++;
            LOG.info("Count of flight pages: " + numberOfPages);
        } catch (DaoException e) {
            LOG.log(Level.ERROR, "Exception in DAO {}", e);
        }
        return numberOfPages;
    }

    public User makePayment(User user, String amount) {
        BigDecimal balance = user.getBalance();
        user.setBalance(balance.add(new BigDecimal(amount)));
        update(user);
        return user;
    }

    private String hash(String input) {
        String md5Hashed = null;
        if (null == input) {
            return null;
        }
        try {
            MessageDigest digest = MessageDigest.getInstance("MD5");
            digest.update(input.getBytes(), 0, input.length());
            md5Hashed = new BigInteger(1, digest.digest()).toString(16);

        } catch (NoSuchAlgorithmException e) {
            LOG.log(Level.ERROR, "Error in hash UserService: {}", e);
        }
        return md5Hashed;
    }


}

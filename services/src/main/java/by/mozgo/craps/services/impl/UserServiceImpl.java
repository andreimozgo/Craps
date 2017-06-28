package by.mozgo.craps.services.impl;

import by.mozgo.craps.dao.UserDao;
import by.mozgo.craps.dao.exception.DaoException;
import by.mozgo.craps.dao.impl.UserDaoImpl;
import by.mozgo.craps.entity.User;
import by.mozgo.craps.services.UserService;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

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

    public static synchronized UserServiceImpl getInstance() {
        if (instance == null) instance = new UserServiceImpl();
        return instance;
    }

    public boolean checkPassword(String email, String password) {
        boolean passCheckResult = false;
        if (!email.equals("") & !password.equals("")) {
            try {
                passCheckResult = userDao.getPassword(email).equals((hash(password)));
            } catch (DaoException e) {
                LOG.log(Level.ERROR, "Exception {}", e);
            }
        }
        return passCheckResult;
    }

    @Override
    public Integer create(User user) {
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

    public List<User> getAll(int recordsPerPage, int currentPage) {
        List<User> users = null;
        try {
            users = userDao.getAll(recordsPerPage, currentPage);
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

    public int getNumberOfPages(int recordsPerPage) {
        int numberOfPages = 1;
        try {
            int numberOfRecords = userDao.getAmount();
            numberOfPages = Math.round(numberOfRecords / recordsPerPage);
            if ((numberOfRecords % recordsPerPage) > 0) numberOfPages++;
            LOG.info("Count of flight pages: " + numberOfPages);
        } catch (DaoException e) {
            LOG.log(Level.ERROR, "Exception in DAO {}", e);
        }
        return numberOfPages;
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

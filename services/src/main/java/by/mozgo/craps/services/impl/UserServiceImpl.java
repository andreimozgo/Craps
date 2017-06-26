package by.mozgo.craps.services.impl;

import by.mozgo.craps.dao.exception.DaoException;
import by.mozgo.craps.dao.impl.UserDaoImpl;
import by.mozgo.craps.entity.User;
import by.mozgo.craps.services.UserService;
import by.mozgo.craps.util.ConnectionPool;
import by.mozgo.craps.util.ConnectionWrapper;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

public class UserServiceImpl implements UserService, by.mozgo.craps.services.Service<User> {
    private static final Logger LOG = LogManager.getLogger();
    private static UserServiceImpl instance = null;

    public UserServiceImpl() {
    }

    public static synchronized UserServiceImpl getInstance() {
        if (instance == null) instance = new UserServiceImpl();
        return instance;
    }

    public boolean checkPassword(String email, String password) {
        boolean passCheckResult = false;
        if (!email.equals("") & !password.equals("")) {
            UserDaoImpl userDao = new UserDaoImpl();
            ConnectionWrapper connection = ConnectionPool.getInstance().getConnection();
            userDao.setConnection(connection);
            try {
                passCheckResult = userDao.getPassword(email).equals((hash(password)));
            } catch (DaoException e) {
                LOG.log(Level.ERROR, "Exception {}", e);
            } finally {
                connection.close();
            }
        }
        return passCheckResult;
    }

    public void create(User user) {
        user.setPassword(hash(user.getPassword()));
        UserDaoImpl userDao = new UserDaoImpl();
        ConnectionWrapper connection = ConnectionPool.getInstance().getConnection();
        userDao.setConnection(connection);
        try {
            userDao.create(user);
        } catch (DaoException e) {
            LOG.log(Level.ERROR, "Exception in DAO {}", e);
        } finally {
            connection.close();
        }
    }

    public User findEntityById(Integer id) {
        return null;
    }

    public void delete(Integer id) {
        UserDaoImpl userDao = new UserDaoImpl();
        ConnectionWrapper connection = ConnectionPool.getInstance().getConnection();
        userDao.setConnection(connection);
        try {
            userDao.delete(id);
        } catch (DaoException e) {
            LOG.log(Level.ERROR, "Exception in DAO {}", e);
        } finally {
            connection.close();
        }
    }

    public User findUserByEmail(String email) {
        User user = null;
        UserDaoImpl userDao = new UserDaoImpl();
        ConnectionWrapper connection = ConnectionPool.getInstance().getConnection();
        userDao.setConnection(connection);
        try {
            user = userDao.findUserByEmail(email);
        } catch (DaoException e) {
            LOG.log(Level.ERROR, "Exception in DAO {}", e);
        } finally {
            connection.close();
        }
        return user;
    }

    public List<User> getAll() {
        List<User> users = null;
        UserDaoImpl userDao = new UserDaoImpl();
        ConnectionWrapper connection = ConnectionPool.getInstance().getConnection();
        userDao.setConnection(connection);
        try {
            users = userDao.getAll();
        } catch (DaoException e) {
            LOG.log(Level.ERROR, "Exception in DAO {}", e);
        } finally {
            connection.close();
        }
        return users;
    }

    public List<User> getAll(int recordsPerPage, int currentPage) {
        List<User> users = null;
        UserDaoImpl userDao = new UserDaoImpl();
        ConnectionWrapper connection = ConnectionPool.getInstance().getConnection();
        userDao.setConnection(connection);
        try {
            users = userDao.getAll(recordsPerPage, currentPage);
        } catch (DaoException e) {
            LOG.log(Level.ERROR, "Exception in DAO {}", e);
        } finally {
            connection.close();
        }
        return users;
    }

    protected String hash(String input) {
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

    public void updateRole(Integer userId, int role) {
        UserDaoImpl userDao = new UserDaoImpl();
        ConnectionWrapper connection = ConnectionPool.getInstance().getConnection();
        userDao.setConnection(connection);
        try {
            userDao.updateRole(userId, role);
        } catch (DaoException e) {
            LOG.log(Level.ERROR, "Exception in DAO {}", e);
        } finally {
            connection.close();
        }
    }

    public int getNumberOfPages(int recordsPerPage) {
        UserDaoImpl userDao = new UserDaoImpl();
        ConnectionWrapper connection = ConnectionPool.getInstance().getConnection();
        userDao.setConnection(connection);
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
}

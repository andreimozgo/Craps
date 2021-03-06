package by.mozgo.craps.dao.impl;

import by.mozgo.craps.dao.DaoException;
import by.mozgo.craps.dao.UserDao;
import by.mozgo.craps.entity.User;
import by.mozgo.craps.util.ConnectionPool;
import by.mozgo.craps.util.ConnectionWrapper;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * UserDao implementation for MYSQL DB
 *
 * @author Mozgo Andrei
 */
public class UserDaoImpl extends BaseDaoImpl<User> implements UserDao {
    private static final String TABLE_NAME = "user";
    private static final String QUERY_INSERT = "INSERT INTO user (email, password, username) VALUES (?, ?, ?)";
    private static final String QUERY_UPDATE = "UPDATE user SET email=?, username=?, money=? WHERE id = ?";
    private static final String QUERY_UPDATE_WITH_PASS = "UPDATE user SET email=?, password=?, username=?, money=? WHERE id = ?";
    private static final String QUERY_UPDATE_ROLE = "UPDATE user SET role_id = ? WHERE id = ?";
    private static final String QUERY_GET_ALL = "SELECT user.id, email, username, create_time, money, role FROM user INNER JOIN role ON user.role_id=role.id ORDER BY user.id LIMIT ?,?";
    private static final String QUERY_GET_USERS_NUMBER = "SELECT COUNT(*) FROM user";
    private static final String QUERY_FIND_PASSWORD = "SELECT password FROM user WHERE email = ?";
    private static final String QUERY_FIND_USER = "SELECT user.id, email, username, create_time, money, role FROM user INNER JOIN role ON user.role_id=role.id WHERE email = ?";
    private static UserDaoImpl instance = null;

    public UserDaoImpl() {
        tableName = TABLE_NAME;
    }

    public static UserDaoImpl getInstance() {
        if (instance == null) {
            instance = new UserDaoImpl();
        }
        return instance;
    }

    @Override
    public long create(User entity) throws DaoException {
        long id;
        try (ConnectionWrapper connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement ps = connection.prepareStatement(QUERY_INSERT, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, entity.getEmail());
            ps.setString(2, entity.getPassword());
            ps.setString(3, entity.getUsername());
            ps.executeUpdate();
            ResultSet generatedKeys = ps.getGeneratedKeys();
            generatedKeys.next();
            id = generatedKeys.getLong(1);
        } catch (SQLException e) {
            throw new DaoException(e);
        }
        return id;
    }

    @Override
    public User findUserByEmail(String email) throws DaoException {
        User user = null;
        try (ConnectionWrapper connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(QUERY_FIND_USER)) {
            preparedStatement.setString(1, email);
            ResultSet result = preparedStatement.executeQuery();
            if (result.isBeforeFirst()) {
                result.next();
                user = new User();
                user.setId(result.getInt(1));
                user.setEmail(result.getString(2));
                user.setUsername(result.getString(3));
                user.setCreateTime(result.getTimestamp(4).toLocalDateTime());
                user.setBalance(result.getBigDecimal(5));
                user.setUserRole(User.UserRole.valueOf(result.getString(6).toUpperCase()));
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        }
        return user;
    }

    public void update(User entity) throws DaoException {
        try (ConnectionWrapper connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement ps = connection.prepareStatement(QUERY_UPDATE)) {
            ps.setString(1, entity.getEmail());
            ps.setString(2, entity.getUsername());
            ps.setBigDecimal(3, entity.getBalance());
            ps.setLong(4, entity.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    public void updateWithPass(User entity) throws DaoException {
        try (ConnectionWrapper connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement ps = connection.prepareStatement(QUERY_UPDATE_WITH_PASS)) {
            ps.setString(1, entity.getEmail());
            ps.setString(2, entity.getPassword());
            ps.setString(3, entity.getUsername());
            ps.setBigDecimal(4, entity.getBalance());
            ps.setLong(5, entity.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public String findPassword(String email) throws DaoException {
        String pass = null;
        try (ConnectionWrapper connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(QUERY_FIND_PASSWORD)) {
            preparedStatement.setString(1, email);
            ResultSet result = preparedStatement.executeQuery();
            if (result.next()) {
                pass = result.getString(1);
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        }
        return pass;
    }

    @Override
    public List findAll(int recordsOnPage, int currentPage) throws DaoException {
        List<User> users = new ArrayList<>();
        try (ConnectionWrapper connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(QUERY_GET_ALL)) {
            preparedStatement.setInt(1, (currentPage - 1) * recordsOnPage);
            preparedStatement.setInt(2, recordsOnPage);
            ResultSet result = preparedStatement.executeQuery();
            while (result.next()) {
                User user = new User();
                user.setId(result.getInt(1));
                user.setEmail(result.getString(2));
                user.setUsername(result.getString(3));
                user.setCreateTime(result.getTimestamp(4).toLocalDateTime());
                user.setBalance(result.getBigDecimal(5));
                user.setUserRole(User.UserRole.valueOf(result.getString(6).toUpperCase()));
                users.add(user);
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        }
        return users;
    }

    public void updateRole(long userId, byte role) throws DaoException {
        try (ConnectionWrapper connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement ps = connection.prepareStatement(QUERY_UPDATE_ROLE)) {
            ps.setByte(1, role);
            ps.setLong(2, userId);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    public long findNumber() throws DaoException {
        long amount;
        try (ConnectionWrapper connection = ConnectionPool.getInstance().getConnection();
             Statement statement = connection.createStatement()) {
            ResultSet result = statement.executeQuery(QUERY_GET_USERS_NUMBER);
            result.next();
            amount = result.getLong(1);
        } catch (SQLException e) {
            throw new DaoException(e);
        }
        return amount;
    }
}
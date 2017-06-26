package by.mozgo.craps.dao.impl;

import by.mozgo.craps.dao.UserDao;
import by.mozgo.craps.dao.exception.DaoException;
import by.mozgo.craps.entity.User;
import by.mozgo.craps.util.ConnectionPool;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class UserDaoImpl extends BaseDaoImpl<User> implements UserDao {
    private static final String TABLE_NAME = "user";
    private static UserDaoImpl instance = null;

    public UserDaoImpl() {
        tableName = TABLE_NAME;
    }

    public static synchronized UserDaoImpl getInstance() {
        if (instance == null) instance = new UserDaoImpl();
        return instance;
    }

    @Override
    public String getPassword(String email) throws DaoException {
        String query = "SELECT password FROM user WHERE email = ?";
        connection = ConnectionPool.getInstance().getConnection();
        String pass = null;

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, email);
            ResultSet result = preparedStatement.executeQuery();
            result.next();
            pass = result.getString(1);
        } catch (SQLException e) {
            throw new DaoException(e);
        }
        return pass;
    }

    @Override
    public User findUserByEmail(String email) throws DaoException {
        String query = "SELECT user.id, email, username, create_time, money, role FROM user INNER JOIN role ON user.role_id=role.id WHERE email = ?";
        connection = ConnectionPool.getInstance().getConnection();
        User user = null;
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, email);
            ResultSet result = preparedStatement.executeQuery();
            if (result.isBeforeFirst()) {
                result.next();
                user = new User();
                user.setId(result.getInt(1));
                user.setEmail(result.getString(2));
                user.setUsername(result.getString(3));
                user.setCreateTime(result.getTimestamp(4).toLocalDateTime());
                user.setMoney(result.getBigDecimal(5));
                user.setUserRole(User.UserRole.valueOf(result.getString(6).toUpperCase()));
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        }
        return user;
    }

    @Override
    public void create(User entity) throws DaoException {
        String query = "INSERT INTO user (email, password, username) VALUES (?, ?, ?)";
        connection = ConnectionPool.getInstance().getConnection();
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, entity.getEmail());
            ps.setString(2, entity.getPassword());
            ps.setString(3, entity.getUsername());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public User findEntityById(Integer id) {
        return null;
    }

    public void update(User entity) {
    }

    @Override
    public List getAll(int recordsPerPage, int currentPage) throws DaoException {
        String query = "SELECT user.id, email, username, create_time, money, role FROM user INNER JOIN role ON user.role_id=role.id ORDER BY user.id LIMIT ?,?";
        connection = ConnectionPool.getInstance().getConnection();

        List<User> users = new ArrayList<>();
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, (currentPage - 1) * recordsPerPage);
            preparedStatement.setInt(2, recordsPerPage);
            ResultSet result = preparedStatement.executeQuery();
            while (result.next()) {
                User user = new User();
                user.setId(result.getInt(1));
                user.setEmail(result.getString(2));
                user.setUsername(result.getString(3));
                user.setCreateTime(result.getTimestamp(4).toLocalDateTime());
                user.setMoney(result.getBigDecimal(5));
                user.setUserRole(User.UserRole.valueOf(result.getString(6).toUpperCase()));
                users.add(user);
            }
            result.close();
        } catch (SQLException e) {
            throw new DaoException(e);
        }
        return users;
    }

    public void updateRole(Integer userId, int role) throws DaoException {
        String query = "UPDATE user SET role_id = ? WHERE id = ?";
        connection = ConnectionPool.getInstance().getConnection();

        try (PreparedStatement prepStatement = connection.prepareStatement(query)) {
            prepStatement.setInt(1, role);
            prepStatement.setLong(2, userId);
            prepStatement.executeUpdate();

        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    public int getAmount() throws DaoException {
        String query = "SELECT COUNT(*) FROM user";
        connection = ConnectionPool.getInstance().getConnection();

        int amount;
        try (Statement statement = connection.createStatement()) {
            ResultSet result = statement.executeQuery(query);
            result.next();
            amount = result.getInt(1);
        } catch (SQLException e) {
            throw new DaoException(e);
        }
        return amount;
    }
}
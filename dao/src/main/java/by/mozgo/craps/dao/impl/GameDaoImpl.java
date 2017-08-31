package by.mozgo.craps.dao.impl;

import by.mozgo.craps.dao.DaoException;
import by.mozgo.craps.dao.GameDao;
import by.mozgo.craps.entity.Game;
import by.mozgo.craps.util.ConnectionPool;
import by.mozgo.craps.util.ConnectionWrapper;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * GameDao implementation for MYSQL DB
 *
 * @author Mozgo Andrei
 *
 */
public class GameDaoImpl extends BaseDaoImpl<Game> implements GameDao {
    private static final String TABLE_NAME = "game";
    private static final String QUERY_INSERT = "INSERT INTO game (user_id) VALUES (?)";
    private static final String QUERY_GET_NUMBER_BY_USER = "SELECT COUNT(*) FROM game WHERE user_id=?";
    private static GameDaoImpl instance = null;

    public GameDaoImpl() {
        tableName = TABLE_NAME;
    }

    public static GameDaoImpl getInstance() {
        if (instance == null) {
            instance = new GameDaoImpl();
        }
        return instance;
    }

    @Override
    public long create(Game entity) throws DaoException {
        long id;
        try (ConnectionWrapper connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement ps = connection.prepareStatement(QUERY_INSERT, Statement.RETURN_GENERATED_KEYS)) {
            ps.setLong(1, entity.getUserId());
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
    public int findGamesNumber(long userId) throws DaoException {
        int number;
        try (ConnectionWrapper connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(QUERY_GET_NUMBER_BY_USER)) {
            preparedStatement.setLong(1, userId);
            ResultSet result = preparedStatement.executeQuery();
            result.next();
            number = result.getInt(1);
        } catch (SQLException e) {
            throw new DaoException(e);
        }
        return number;
    }
}

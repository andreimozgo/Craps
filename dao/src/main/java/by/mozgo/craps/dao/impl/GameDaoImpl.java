package by.mozgo.craps.dao.impl;

import by.mozgo.craps.dao.DaoException;
import by.mozgo.craps.dao.GameDao;
import by.mozgo.craps.entity.Game;
import by.mozgo.craps.util.ConnectionPool;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by Andrei Mozgo. 2017.
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
    public int create(Game entity) throws DaoException {
        int id;
        connection = ConnectionPool.getInstance().getConnection();
        try (PreparedStatement ps = connection.prepareStatement(QUERY_INSERT, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, entity.getUserId());
            ps.executeUpdate();
            ResultSet generatedKeys = ps.getGeneratedKeys();
            generatedKeys.next();
            id = generatedKeys.getInt(1);
        } catch (SQLException e) {
            throw new DaoException(e);
        }
        return id;
    }

    @Override
    public int findGamesNumber(int userId) throws DaoException {
        connection = ConnectionPool.getInstance().getConnection();
        int number;
        try (PreparedStatement preparedStatement = connection.prepareStatement(QUERY_GET_NUMBER_BY_USER)) {
            preparedStatement.setInt(1, userId);
            ResultSet result = preparedStatement.executeQuery();
            result.next();
            number = result.getInt(1);
        } catch (SQLException e) {
            throw new DaoException(e);
        }
        return number;
    }
}

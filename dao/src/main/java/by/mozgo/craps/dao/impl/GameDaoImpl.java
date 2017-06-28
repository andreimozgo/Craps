package by.mozgo.craps.dao.impl;

import by.mozgo.craps.dao.GameDao;
import by.mozgo.craps.dao.exception.DaoException;
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
    private static GameDaoImpl instance = null;

    public GameDaoImpl() {
        tableName = TABLE_NAME;
    }

    public static synchronized GameDaoImpl getInstance() {
        if (instance == null) instance = new GameDaoImpl();
        return instance;
    }

    @Override
    public Integer create(Game entity) throws DaoException {
        Integer id = null;
        String query = "INSERT INTO game (user_id) VALUES (?)";
        connection = ConnectionPool.getInstance().getConnection();
        try (PreparedStatement ps = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, entity.getUserId());
            ps.executeUpdate();
            ResultSet generatedKeys = ps.getGeneratedKeys();
            if (generatedKeys.next()) {
                id = generatedKeys.getInt(1);
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        }
        return id;
    }

    @Override
    public Game findEntityById(Integer id) throws DaoException {
        return null;
    }
}

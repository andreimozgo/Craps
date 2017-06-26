package by.mozgo.craps.dao.impl;

import by.mozgo.craps.dao.GameDao;
import by.mozgo.craps.dao.exception.DaoException;
import by.mozgo.craps.entity.Game;
import by.mozgo.craps.util.ConnectionPool;

import java.sql.PreparedStatement;
import java.sql.SQLException;

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
    public void create(Game entity) throws DaoException {
        String query = "INSERT INTO game (user_id) VALUES (?)";
        connection = ConnectionPool.getInstance().getConnection();
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, entity.getUserId());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public Game findEntityById(Integer id) throws DaoException {
        return null;
    }
}

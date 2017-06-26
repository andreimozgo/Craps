package by.mozgo.craps.dao.impl;

import by.mozgo.craps.dao.GameDao;
import by.mozgo.craps.dao.exception.DaoException;
import by.mozgo.craps.entity.Game;

import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Created by Andrei Mozgo. 2017.
 */
public class GameDaoImpl extends BaseDaoImpl<Game> implements GameDao {
    private static final String TABLE_NAME = "game";

    public GameDaoImpl() {
        tableName = TABLE_NAME;
    }

    @Override
    public void create(Game entity) throws DaoException {
        String query = "INSERT INTO game (user_id) VALUES (?)";
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

package by.mozgo.craps.dao.impl;

import by.mozgo.craps.dao.BetDao;
import by.mozgo.craps.dao.exception.DaoException;
import by.mozgo.craps.entity.Bet;
import by.mozgo.craps.util.ConnectionPool;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by Andrei Mozgo. 2017.
 */
public class BetDaoImpl extends BaseDaoImpl<Bet> implements BetDao {
    private static final String TABLE_NAME = "bet";
    private static BetDaoImpl instance = null;

    private BetDaoImpl() {
        tableName = TABLE_NAME;
    }

    public static synchronized BetDaoImpl getInstance() {
        if (instance == null) instance = new BetDaoImpl();
        return instance;
    }

    @Override
    public Integer create(Bet entity) throws DaoException {
        Integer id = null;
        String query = "INSERT INTO bet (game_id, amount) VALUES (?, ?)";
        connection = ConnectionPool.getInstance().getConnection();
        try (PreparedStatement ps = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, entity.getGameId());
            ps.setBigDecimal(2, entity.getAmount());
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
    public Bet findEntityById(Integer id) throws DaoException {
        return null;
    }

    @Override
    public void update(Bet entity) throws DaoException {
        String query = "UPDATE bet SET game_id=?, amount=?, profit=? WHERE id = ?";
        connection = ConnectionPool.getInstance().getConnection();
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, entity.getGameId());
            ps.setBigDecimal(2, entity.getAmount());
            ps.setBigDecimal(3, entity.getProfit());
            ps.setInt(4, entity.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }
}

package by.mozgo.craps.dao.impl;

import by.mozgo.craps.dao.BetDao;
import by.mozgo.craps.dao.exception.DaoException;
import by.mozgo.craps.entity.Bet;
import by.mozgo.craps.util.ConnectionPool;

import java.sql.PreparedStatement;
import java.sql.SQLException;

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
    public void create(Bet entity) throws DaoException {
        String query = "INSERT INTO bet (game_id, amount, profit) VALUES (?, ?, ?)";
        connection = ConnectionPool.getInstance().getConnection();
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, entity.getGameId());
            ps.setBigDecimal(2, entity.getAmount());
            ps.setBigDecimal(3, entity.getProfit());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public Bet findEntityById(Integer id) throws DaoException {
        return null;
    }
}

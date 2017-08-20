package by.mozgo.craps.dao.impl;

import by.mozgo.craps.dao.BetDao;
import by.mozgo.craps.dao.DaoException;
import by.mozgo.craps.entity.Bet;
import by.mozgo.craps.util.ConnectionPool;
import by.mozgo.craps.util.ConnectionWrapper;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by Andrei Mozgo. 2017.
 */
public class BetDaoImpl extends BaseDaoImpl<Bet> implements BetDao {
    private static final String TABLE_NAME = "bet";
    private static final String QUERY_INSERT = "INSERT INTO bet (game_id, amount, bet_type_id) VALUES (?, ?, ?)";
    private static final String QUERY_UPDATE = "UPDATE bet SET game_id=?, amount=?, profit=?, bet_type_id=? WHERE id = ?";
    private static final String QUERY_FIND_NUMBER_BY_USER = "SELECT COUNT(*) FROM  (SELECT userId, gameId, bet.id AS 'betId' FROM (SELECT user.id AS 'userId', game.id AS 'gameId' FROM user INNER JOIN game ON user.id=game.user_id WHERE user.id=?) AS usergames INNER JOIN bet ON gameId=bet.game_id) AS userbets";
    private static final String QUERY_GET_NUMBER_WON_BY_USER = "SELECT COUNT(*) FROM  (SELECT userId, gameId, bet.id AS 'betId' FROM (SELECT user.id AS 'userId', game.id AS 'gameId' FROM user INNER JOIN game ON user.id=game.user_id WHERE user.id=?) AS usergames INNER JOIN bet ON gameId=bet.game_id WHERE bet.profit > 0) AS userbets";
    private static BetDaoImpl instance = null;

    private BetDaoImpl() {
        tableName = TABLE_NAME;
    }

    public static BetDaoImpl getInstance() {
        if (instance == null) {
            instance = new BetDaoImpl();
        }
        return instance;
    }

    @Override
    public int create(Bet entity) throws DaoException {
        Integer id;
        ConnectionWrapper connection = ConnectionPool.getInstance().getConnection();
        try (PreparedStatement ps = connection.prepareStatement(QUERY_INSERT, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, entity.getGameId());
            ps.setBigDecimal(2, entity.getAmount());
            ps.setInt(3, entity.getBetTypeId());
            ps.executeUpdate();
            ResultSet generatedKeys = ps.getGeneratedKeys();
            if (generatedKeys.next()) {
                id = generatedKeys.getInt(1);
            } else {
                throw new DaoException("Unable to create entity");
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        }
        return id;
    }

    @Override
    public void update(Bet entity) throws DaoException {
        ConnectionWrapper connection = ConnectionPool.getInstance().getConnection();
        try (PreparedStatement ps = connection.prepareStatement(QUERY_UPDATE)) {
            ps.setInt(1, entity.getGameId());
            ps.setBigDecimal(2, entity.getAmount());
            ps.setBigDecimal(3, entity.getProfit());
            ps.setInt(4, entity.getId());
            ps.setInt(5, entity.getBetTypeId());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public int findBetsNumber(int userId) throws DaoException {
        ConnectionWrapper connection = ConnectionPool.getInstance().getConnection();
        int number;
        try (PreparedStatement preparedStatement = connection.prepareStatement(QUERY_FIND_NUMBER_BY_USER)) {
            preparedStatement.setInt(1, userId);
            ResultSet result = preparedStatement.executeQuery();
            result.next();
            number = result.getInt(1);
        } catch (SQLException e) {
            throw new DaoException(e);
        }
        return number;
    }

    @Override
    public int findWonBetsNumber(int userId) throws DaoException {
        ConnectionWrapper connection = ConnectionPool.getInstance().getConnection();
        int number;
        try (PreparedStatement preparedStatement = connection.prepareStatement(QUERY_GET_NUMBER_WON_BY_USER)) {
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

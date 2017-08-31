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
 * BetDao implementation for MYSQL DB
 *
 * @author Mozgo Andrei
 */
public class BetDaoImpl extends BaseDaoImpl<Bet> implements BetDao {
    private static final String TABLE_NAME = "bet";
    private static final String QUERY_INSERT = "INSERT INTO bet (game_id, amount, bet_type_id) VALUES (?, ?, ?)";
    private static final String QUERY_UPDATE = "UPDATE bet SET game_id=?, amount=?, profit=?, bet_type_id=? WHERE id = ?";
    private static final String QUERY_FIND_NUMBER_BY_USER = "SELECT COUNT(*) FROM  (SELECT userId, gameId, bet.id AS 'betId' FROM (SELECT user.id AS 'userId', game.id AS 'gameId' FROM user INNER JOIN game ON user.id=game.user_id WHERE user.id=?) AS usergames INNER JOIN bet ON gameId=bet.game_id) AS userbets";
    private static final String QUERY_GET_NUMBER_WON_BY_USER = "SELECT COUNT(*) FROM  (SELECT userId, gameId, bet.id AS 'betId' FROM (SELECT user.id AS 'userId', game.id AS 'gameId' FROM user INNER JOIN game ON user.id=game.user_id WHERE user.id=?) AS usergames INNER JOIN bet ON gameId=bet.game_id WHERE bet.profit > 0) AS userbets";
    private static final String QUERY_FIND_NAME_BY_ID = "SELECT name FROM bet_type WHERE id = ?";
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
    public long create(Bet entity) throws DaoException {
        long id;
        try (ConnectionWrapper connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement ps = connection.prepareStatement(QUERY_INSERT, Statement.RETURN_GENERATED_KEYS)) {
            ps.setLong(1, entity.getGameId());
            ps.setBigDecimal(2, entity.getAmount());
            ps.setByte(3, entity.getBetTypeId());
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
    public void update(Bet entity) throws DaoException {
        try (ConnectionWrapper connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement ps = connection.prepareStatement(QUERY_UPDATE)) {
            ps.setLong(1, entity.getGameId());
            ps.setBigDecimal(2, entity.getAmount());
            ps.setBigDecimal(3, entity.getProfit());
            ps.setByte(4, entity.getBetTypeId());
            ps.setLong(5, entity.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public int findBetsNumber(long userId) throws DaoException {
        int number;
        try (ConnectionWrapper connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(QUERY_FIND_NUMBER_BY_USER)) {
            preparedStatement.setLong(1, userId);
            ResultSet result = preparedStatement.executeQuery();
            result.next();
            number = result.getInt(1);
        } catch (SQLException e) {
            throw new DaoException(e);
        }
        return number;
    }

    /**
     * @param userId
     * @return
     * @throws DaoException
     */
    @Override
    public int findWonBetsNumber(long userId) throws DaoException {
        int number;
        try (ConnectionWrapper connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(QUERY_GET_NUMBER_WON_BY_USER)) {
            preparedStatement.setLong(1, userId);
            ResultSet result = preparedStatement.executeQuery();
            result.next();
            number = result.getInt(1);
        } catch (SQLException e) {
            throw new DaoException(e);
        }
        return number;
    }

    @Override
    public String findTypeNameById(byte id) throws DaoException {
        String name = null;
        try (ConnectionWrapper connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(QUERY_FIND_NAME_BY_ID)) {
            preparedStatement.setByte(1, id);
            ResultSet result = preparedStatement.executeQuery();
            if (result.isBeforeFirst()) {
                result.next();
                name = result.getString(1);
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        }
        return name;
    }
}

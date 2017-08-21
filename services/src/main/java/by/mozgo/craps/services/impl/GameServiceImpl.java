package by.mozgo.craps.services.impl;

import by.mozgo.craps.dao.DaoException;
import by.mozgo.craps.dao.GameDao;
import by.mozgo.craps.dao.impl.GameDaoImpl;
import by.mozgo.craps.entity.Game;
import by.mozgo.craps.services.GameService;
import by.mozgo.craps.services.ServiceException;

/**
 * Contains game service implementation
 *
 * @author Mozgo Andrei
 *
 */
public class GameServiceImpl extends ServiceImpl<Game> implements GameService {
    private static GameServiceImpl instance = null;
    private GameDao gameDao = GameDaoImpl.getInstance();

    private GameServiceImpl() {
        baseDao = gameDao;
    }

    public static GameServiceImpl getInstance() {
        if (instance == null) {
            instance = new GameServiceImpl();
        }
        return instance;
    }

    @Override
    public int findGamesNumber(int userId) throws ServiceException {
        int number = 0;
        try {
            number = gameDao.findGamesNumber(userId);
        } catch (DaoException e) {
            throw new ServiceException("Exception in DAO. " + e.getMessage(), e);
        }
        return number;
    }
}

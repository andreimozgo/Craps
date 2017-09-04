package by.mozgo.craps.services.impl;

import by.mozgo.craps.dao.BetDao;
import by.mozgo.craps.dao.DaoException;
import by.mozgo.craps.dao.impl.BetDaoImpl;
import by.mozgo.craps.entity.Bet;
import by.mozgo.craps.services.BetService;
import by.mozgo.craps.services.ServiceException;
import by.mozgo.craps.services.dto.BetDto;

import java.util.ArrayList;
import java.util.List;

/**
 * Contains bet service implementation
 *
 * @author Mozgo Andrei
 */
public class BetServiceImpl extends ServiceImpl<Bet> implements BetService {
    private static BetServiceImpl instance = null;
    private BetDao betDao = BetDaoImpl.getInstance();

    private BetServiceImpl() {
        baseDao = betDao;
    }

    public static BetServiceImpl getInstance() {
        if (instance == null) {
            instance = new BetServiceImpl();
        }
        return instance;
    }

    @Override
    public int findBetsNumber(long userId) throws ServiceException {
        int number;
        try {
            number = betDao.findBetsNumber(userId);
        } catch (DaoException e) {
            throw new ServiceException("Exception in DAO. " + e.getMessage(), e);
        }
        return number;
    }

    @Override
    public int findWonBetsNumber(long userId) throws ServiceException {
        int number;
        try {
            number = betDao.findWonBetsNumber(userId);
        } catch (DaoException e) {
            throw new ServiceException("Exception in DAO. " + e.getMessage(), e);
        }
        return number;
    }

    @Override
    public List<BetDto> generateBetDto(List<Bet> bets) throws ServiceException {
        List<BetDto> betDtos = new ArrayList<>();
        try {
            for (Bet bet : bets) {
                String betTypeName = betDao.findTypeNameById(bet.getBetTypeId());
                betDtos.add(new BetDto(betTypeName, bet.getAmount(), bet.getProfit(), bet.getPoint()));
            }
        } catch (DaoException e) {
            throw new ServiceException("Exception in DAO. " + e.getMessage(), e);
        }
        return betDtos;
    }
}
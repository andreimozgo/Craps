package by.mozgo.craps.services.impl;

import by.mozgo.craps.dao.BetDao;
import by.mozgo.craps.dao.DaoException;
import by.mozgo.craps.dao.impl.BetDaoImpl;
import by.mozgo.craps.entity.Bet;
import by.mozgo.craps.services.BetService;
import by.mozgo.craps.services.BetTypeService;
import by.mozgo.craps.services.ServiceException;
import by.mozgo.craps.services.vo.BetVO;

import java.util.ArrayList;
import java.util.List;

/**
 * Contains bet service implementation
 *
 * @author Mozgo Andrei
 *
 */
public class BetServiceImpl extends ServiceImpl<Bet>  implements BetService {
    private static BetServiceImpl instance = null;
    private BetDao betDao = BetDaoImpl.getInstance();
    private BetTypeService betTypeService = BetTypeServiceImpl.getInstance();

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
    public int findBetsNumber(int userId) throws ServiceException {
        int number = 0;
        try {
            number = betDao.findBetsNumber(userId);
        } catch (DaoException e) {
            throw new ServiceException("Exception in DAO. " + e.getMessage(), e);
        }
        return number;
    }

    @Override
    public int findWonBetsNumber(int userId) throws ServiceException {
        int number = 0;
        try {
            number = betDao.findWonBetsNumber(userId);
        } catch (DaoException e) {
            throw new ServiceException("Exception in DAO. " + e.getMessage(), e);
        }
        return number;
    }

    @Override
    public List<BetVO> generateBetVO(List<Bet> bets) throws ServiceException {
        List<BetVO> betVOs = new ArrayList<>();
     for(Bet bet: bets){
         String betTypeName = betTypeService.getNameById(bet.getBetTypeId());
         betVOs.add(new BetVO(betTypeName, bet.getAmount(), bet.getProfit(), bet.getPoint()));
     }
        return betVOs;
    }
}
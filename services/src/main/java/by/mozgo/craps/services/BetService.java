package by.mozgo.craps.services;

import by.mozgo.craps.entity.Bet;
import by.mozgo.craps.services.vo.BetVO;

import java.util.List;

/**
 * Interface that contains bet service operations
 *
 * @author Mozgo Andrei
 *
 */
public interface BetService extends Service<Bet> {
    /**
     * Finds number of bets was made by user
     *
     * @param userId
     * @return number of bets was made by user
     * @throws ServiceException
     */
    int findBetsNumber(long userId) throws ServiceException;

    /**
     * Finds number of bets was won by user
     *
     * @param userId
     * @return number of bets was won by user
     * @throws ServiceException
     */
    int findWonBetsNumber(long userId) throws ServiceException;

    /**
     * Generates list of BetVO entities from list of Bet entities
     *
     * @param bets
     * @return list of BetVO entities
     * @throws ServiceException
     */
    List<BetVO> generateBetVO(List<Bet> bets) throws ServiceException;
}

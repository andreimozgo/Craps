package by.mozgo.craps.services;

import by.mozgo.craps.entity.Bet;
import by.mozgo.craps.services.vo.BetVO;

import java.util.List;

/**
 * Created by Andrei Mozgo. 2017.
 */
public interface BetService extends Service<Bet> {
    int findBetsNumber(int userId);

    int findWonBetsNumber(int userId);

    List<BetVO> generateBetVO(List<Bet> bets);
}

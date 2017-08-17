package by.mozgo.craps.services;

import by.mozgo.craps.entity.Bet;

/**
 * Created by Andrei Mozgo. 2017.
 */
public interface BetService extends Service<Bet> {
    int findBetsNumber(int userId);

    int findWonBetsNumber(int userId);
}

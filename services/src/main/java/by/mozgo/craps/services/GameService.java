package by.mozgo.craps.services;

import by.mozgo.craps.entity.Game;

/**
 * Interface that contains game service operations
 *
 * @author Mozgo Andrei
 *
 */
public interface GameService extends Service<Game> {
    /**
     * Finds number of games played by user
     *
     * @param userId
     * @return number of games
     * @throws ServiceException
     */
    int findGamesNumber(long userId) throws ServiceException;
}

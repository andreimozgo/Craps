package by.mozgo.craps.services;

import by.mozgo.craps.entity.Game;

/**
 * Created by Andrei Mozgo. 2017.
 */
public interface GameService extends Service<Game> {

    Integer create (Game game);
}

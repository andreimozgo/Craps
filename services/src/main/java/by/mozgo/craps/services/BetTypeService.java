package by.mozgo.craps.services;

import by.mozgo.craps.entity.BetType;

/**
 * Created by Andrei Mozgo. 2017.
 */
public interface BetTypeService extends Service<BetType> {
    String getNameById(Integer id);
}

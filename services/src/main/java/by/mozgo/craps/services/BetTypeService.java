package by.mozgo.craps.services;

import by.mozgo.craps.entity.BetType;

/**
 * Interface that contains bet type service operations
 *
 * @author Mozgo Andrei
 *
 */
public interface BetTypeService extends Service<BetType> {
    /**
     * Finds name of bet type by id
     *
     * @param id
     * @return name of bet type
     * @throws ServiceException
     */
    String getNameById(Integer id) throws ServiceException;
}

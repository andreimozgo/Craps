package by.mozgo.craps.services.impl;

import by.mozgo.craps.dao.BetTypeDao;
import by.mozgo.craps.dao.DaoException;
import by.mozgo.craps.dao.impl.BetTypeDaoImpl;
import by.mozgo.craps.entity.BetType;
import by.mozgo.craps.services.BetTypeService;
import by.mozgo.craps.services.ServiceException;

/**
 * Contains bet type service implementation
 *
 * @author Mozgo Andrei
 *
 */
public class BetTypeServiceImpl extends ServiceImpl<BetType>  implements BetTypeService {
    private static BetTypeServiceImpl instance = null;
    private BetTypeDao betTypeDao = BetTypeDaoImpl.getInstance();

    private BetTypeServiceImpl() {
        baseDao = betTypeDao;
    }

    public static BetTypeServiceImpl getInstance() {
        if (instance == null) {
            instance = new BetTypeServiceImpl();
        }
        return instance;
    }

    @Override
    public String getNameById(Integer id) throws ServiceException {
        String name = null;
        try {
            name = betTypeDao.findNameById(id);
        } catch (DaoException e) {
            throw new ServiceException("Exception in DAO. " + e.getMessage(), e);
        }
        return name;
    }
}

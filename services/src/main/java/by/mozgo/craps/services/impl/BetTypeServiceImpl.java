package by.mozgo.craps.services.impl;

import by.mozgo.craps.dao.BetTypeDao;
import by.mozgo.craps.dao.DaoException;
import by.mozgo.craps.dao.impl.BetTypeDaoImpl;
import by.mozgo.craps.entity.BetType;
import by.mozgo.craps.services.BetTypeService;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Created by Andrei Mozgo. 2017.
 */
public class BetTypeServiceImpl extends ServiceImpl<BetType>  implements BetTypeService {
    private static final Logger LOG = LogManager.getLogger();
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
    public String getNameById(Integer id) {
        String name = null;
        try {
            name = betTypeDao.findNameById(id);
        } catch (DaoException e) {
            LOG.log(Level.ERROR, "Exception in DAO {}", e);
        }
        return name;
    }
}

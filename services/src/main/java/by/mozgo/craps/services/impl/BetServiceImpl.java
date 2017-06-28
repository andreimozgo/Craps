package by.mozgo.craps.services.impl;

import by.mozgo.craps.dao.BetDao;
import by.mozgo.craps.dao.impl.BetDaoImpl;
import by.mozgo.craps.entity.Bet;
import by.mozgo.craps.services.BetService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Created by Andrei Mozgo. 2017.
 */
public class BetServiceImpl extends ServiceImpl<Bet>  implements BetService {
    private static final Logger LOG = LogManager.getLogger();
    private static BetServiceImpl instance = null;
    private BetDao betDao = BetDaoImpl.getInstance();

    private BetServiceImpl() {
        baseDao = betDao;
    }

    public static synchronized BetServiceImpl getInstance() {
        if (instance == null) instance = new BetServiceImpl();
        return instance;
    }

/*    @Override
    public Integer create(Bet bet) {
        Integer id = null;
        try {
            id = betDao.create(bet);
        } catch (DaoException e) {
            LOG.log(Level.ERROR, "Exception in DAO {}", e);
        }
        return id;
    }*/

    @Override
    public Bet findEntityById(Integer id) {
        return null;
    }

    @Override
    public void delete(Integer id) {

    }
}
package by.mozgo.craps.command.player;

import by.mozgo.craps.command.ActionCommand;
import by.mozgo.craps.command.ActionResult;
import by.mozgo.craps.command.ConfigurationManager;
import by.mozgo.craps.command.CrapsConstant;
import by.mozgo.craps.entity.User;
import by.mozgo.craps.services.BetService;
import by.mozgo.craps.services.GameService;
import by.mozgo.craps.services.ServiceException;
import by.mozgo.craps.services.impl.BetServiceImpl;
import by.mozgo.craps.services.impl.GameServiceImpl;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import static by.mozgo.craps.command.ActionResult.ActionType.FORWARD;

/**
 * Created by Andrei Mozgo. 2017.
 */
public class StatsCommand implements ActionCommand {
    private static final Logger LOG = LogManager.getLogger();
    private static final String NUMBER_OF_GAMES = "gamesNumber";
    private static final String NUMBER_OF_BETS = "betsNumber";
    private static final String NUMBER_OF_BETS_WON = "wonBetsNumber";


    @Override
    public ActionResult execute(HttpServletRequest request) {
        GameService gameService = GameServiceImpl.getInstance();
        BetService betService = BetServiceImpl.getInstance();
        HttpSession session = request.getSession();
        String page = ConfigurationManager.getProperty("path.page.stats");

        try {
            User user = (User) session.getAttribute(CrapsConstant.USER);
            int gamesNumber = gameService.findGamesNumber(user.getId());
            int betsNumber = betService.findBetsNumber(user.getId());
            int wonBetsNumber = betService.findWonBetsNumber(user.getId());
            request.setAttribute(NUMBER_OF_GAMES, gamesNumber);
            request.setAttribute(NUMBER_OF_BETS, betsNumber);
            request.setAttribute(NUMBER_OF_BETS_WON, wonBetsNumber);
        } catch (ServiceException e) {
            LOG.log(Level.ERROR, "Unable to show stats page.\n" + e.getMessage());
            page = ConfigurationManager.getProperty("path.page.error");
        }

        return new ActionResult(FORWARD, page);
    }
}
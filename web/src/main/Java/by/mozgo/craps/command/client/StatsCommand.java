package by.mozgo.craps.command.client;

import by.mozgo.craps.command.ActionCommand;
import by.mozgo.craps.command.ActionResult;
import by.mozgo.craps.command.ConfigurationManager;
import by.mozgo.craps.entity.User;
import by.mozgo.craps.services.BetService;
import by.mozgo.craps.services.GameService;
import by.mozgo.craps.services.impl.BetServiceImpl;
import by.mozgo.craps.services.impl.GameServiceImpl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import static by.mozgo.craps.command.ActionResult.ActionType.FORWARD;

/**
 * Created by Andrei Mozgo. 2017.
 */
public class StatsCommand implements ActionCommand {

    @Override
    public ActionResult execute(HttpServletRequest request) {
        GameService gameService = GameServiceImpl.getInstance();
        BetService betService = BetServiceImpl.getInstance();
        HttpSession session = request.getSession();

        User user = (User) session.getAttribute("user");
        int gamesNumber = gameService.getGamesNumber(user.getId());
        int betsNumber = betService.getBetsNumber(user.getId());
        int wonBetsNumber = betService.getWonBetsNumber(user.getId());

        request.setAttribute("gamesNumber", gamesNumber);
        request.setAttribute("betsNumber", betsNumber);
        request.setAttribute("wonBetsNumber", wonBetsNumber);

        String page = ConfigurationManager.getProperty("path.page.stats");
        return new ActionResult(FORWARD, page);
    }
}
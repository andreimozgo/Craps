package by.mozgo.craps.command.client;

import by.mozgo.craps.command.ActionCommand;
import by.mozgo.craps.command.ActionResult;
import by.mozgo.craps.command.ConfigurationManager;
import by.mozgo.craps.entity.Bet;
import by.mozgo.craps.entity.User;
import by.mozgo.craps.services.game.GameLogic;
import by.mozgo.craps.services.game.RollResult;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import static by.mozgo.craps.command.ActionResult.ActionType.FORWARD;

/**
 * Created by Andrei Mozgo. 2017.
 */
public class PlayCommand implements ActionCommand {
    @Override
    public ActionResult execute(HttpServletRequest request) {

        HttpSession session = request.getSession();

        User user = (User) session.getAttribute("user");
        GameLogic gameLogic = new GameLogic(user);
        String passBet = request.getParameter("passBet");
        gameLogic.addBet(Bet.BetType.PASS, passBet);
        String dontPassBet = request.getParameter("dontPassBet");
        gameLogic.addBet(Bet.BetType.DONTPASS, dontPassBet);
        RollResult rollResult = gameLogic.roll();
        request.setAttribute("dice", rollResult);

        String page = ConfigurationManager.getProperty("path.page.play");
        return new ActionResult(FORWARD, page);
    }
}

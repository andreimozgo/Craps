package by.mozgo.craps.command.client;

import by.mozgo.craps.command.ActionCommand;
import by.mozgo.craps.command.ActionResult;
import by.mozgo.craps.command.ConfigurationManager;
import by.mozgo.craps.entity.Bet;
import by.mozgo.craps.entity.User;
import by.mozgo.craps.services.Validator;
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
        String page = ConfigurationManager.getProperty("path.page.play");
        if (user != null) {
            GameLogic gameLogic = new GameLogic(user);
            String passBet = request.getParameter("passBet");
            if(Validator.validateMoney(passBet)) {
                gameLogic.addBet(Bet.BetType.PASS, passBet);
            }
            String dontPassBet = request.getParameter("dontPassBet");
            if(Validator.validateMoney(dontPassBet)) {
                gameLogic.addBet(Bet.BetType.DONTPASS, dontPassBet);
            }
            String comeBet = request.getParameter("comeBet");
            if(Validator.validateMoney(comeBet)) {
                gameLogic.addBet(Bet.BetType.COME, comeBet);
            }
            String dontComeBet = request.getParameter("dontComeBet");
            if(Validator.validateMoney(comeBet)) {
                gameLogic.addBet(Bet.BetType.DONTCOME, dontComeBet);
            }
            RollResult rollResult = gameLogic.roll();
            request.setAttribute("dice", rollResult);
            if (user.getGame() != null) {
                if (!user.getGame().isFirstRoll()) {
                    page = ConfigurationManager.getProperty("path.page.secondroll");
                }
            }
        }
        return new ActionResult(FORWARD, page);
    }
}

package by.mozgo.craps.command.player;

import by.mozgo.craps.command.*;
import by.mozgo.craps.entity.Bet;
import by.mozgo.craps.entity.User;
import by.mozgo.craps.services.Validator;
import by.mozgo.craps.services.game.GameLogic;
import by.mozgo.craps.services.game.RollResult;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.util.Locale;

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
        boolean isEnoughtMoney = true;
        if (user != null) {
            GameLogic gameLogic = new GameLogic(user);
            String passBet = request.getParameter("passBet");
            if (Validator.validateMoney(passBet)) {
                passBet = passBet.trim();
                if (checkBalance(passBet, user)) {
                    gameLogic.addBet(Bet.BetType.PASS, passBet);
                } else {
                    isEnoughtMoney = false;
                }
            }
            String dontPassBet = request.getParameter("dontPassBet");
            if (Validator.validateMoney(dontPassBet)) {
                dontPassBet = dontPassBet.trim();
                if (checkBalance(dontPassBet, user)) {
                    gameLogic.addBet(Bet.BetType.DONTPASS, dontPassBet);
                } else {
                    isEnoughtMoney = false;
                }
            }
            String comeBet = request.getParameter("comeBet");
            if (Validator.validateMoney(comeBet)) {
                comeBet = comeBet.trim();
                if (checkBalance(comeBet, user)) {
                    gameLogic.addBet(Bet.BetType.COME, comeBet);
                } else {
                    isEnoughtMoney = false;
                }
            }
            String dontComeBet = request.getParameter("dontComeBet");
            if (Validator.validateMoney(dontComeBet)) {
                dontComeBet = dontComeBet.trim();
                if (checkBalance(dontComeBet, user)) {
                    gameLogic.addBet(Bet.BetType.DONTCOME, dontComeBet);
                } else {
                    isEnoughtMoney = false;
                }
            }
            if (user.getGame() != null) {
                RollResult rollResult = gameLogic.roll();
                request.setAttribute("dice", rollResult);
                if (!user.getGame().isFirstRoll()) {
                    page = ConfigurationManager.getProperty("path.page.secondroll");
                }
            }
            if (!isEnoughtMoney){
                Locale locale = (Locale) session.getAttribute(CrapsConstant.ATTRIBUTE_LOCALE);
                request.setAttribute("playMessage", MessageManager.getProperty("play.badbalance", locale));
            }
        }
        return new ActionResult(FORWARD, page);
    }

    private boolean checkBalance(String bet, User user) {
        return user.getBalance().compareTo(new BigDecimal(bet)) > -1;
    }
}

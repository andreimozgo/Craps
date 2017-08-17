package by.mozgo.craps.command.player;

import by.mozgo.craps.command.*;
import by.mozgo.craps.entity.Bet;
import by.mozgo.craps.entity.User;
import by.mozgo.craps.services.game.GameLogic;
import by.mozgo.craps.services.game.RollResult;
import by.mozgo.craps.services.validator.Validator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.util.Locale;

import static by.mozgo.craps.command.ActionResult.ActionType.FORWARD;

/**
 * Created by Andrei Mozgo. 2017.
 */
public class PlayCommand implements ActionCommand {
    private static final String BET_ON_PASS = "passBet";
    private static final String BET_ON_DONT_PASS = "dontPassBet";
    private static final String BET_ON_COME = "comeBet";
    private static final String BET_ON_DONT_COME = "dontComeBet";
    private static final String DICE = "dice";
    private static final String MESSAGE = "playMessage";

    @Override
    public ActionResult execute(HttpServletRequest request) {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(CrapsConstant.USER);
        String page = ConfigurationManager.getProperty("path.page.play");
        boolean isEnoughMoney = true;
        if (user != null) {
            GameLogic gameLogic = new GameLogic(user);
            String passBet = request.getParameter(BET_ON_PASS);
            if (Validator.validateMoney(passBet)) {
                passBet = passBet.trim();
                if (checkBalance(passBet, user)) {
                    gameLogic.addBet(Bet.BetType.PASS, passBet);
                } else {
                    isEnoughMoney = false;
                }
            }
            String dontPassBet = request.getParameter(BET_ON_DONT_PASS);
            if (Validator.validateMoney(dontPassBet)) {
                dontPassBet = dontPassBet.trim();
                if (checkBalance(dontPassBet, user)) {
                    gameLogic.addBet(Bet.BetType.DONTPASS, dontPassBet);
                } else {
                    isEnoughMoney = false;
                }
            }
            String comeBet = request.getParameter(BET_ON_COME);
            if (Validator.validateMoney(comeBet)) {
                comeBet = comeBet.trim();
                if (checkBalance(comeBet, user)) {
                    gameLogic.addBet(Bet.BetType.COME, comeBet);
                } else {
                    isEnoughMoney = false;
                }
            }
            String dontComeBet = request.getParameter(BET_ON_DONT_COME);
            if (Validator.validateMoney(dontComeBet)) {
                dontComeBet = dontComeBet.trim();
                if (checkBalance(dontComeBet, user)) {
                    gameLogic.addBet(Bet.BetType.DONTCOME, dontComeBet);
                } else {
                    isEnoughMoney = false;
                }
            }
            if (user.getGame() != null) {
                RollResult rollResult = gameLogic.roll();
                request.setAttribute(DICE, rollResult);
                if (!user.getGame().isFirstRoll()) {
                    page = ConfigurationManager.getProperty("path.page.secondroll");
                }
            }
            if (!isEnoughMoney){
                Locale locale = (Locale) session.getAttribute(CrapsConstant.ATTRIBUTE_LOCALE);
                request.setAttribute(MESSAGE, MessageManager.getProperty("play.badbalance", locale));
            }
        }
        return new ActionResult(FORWARD, page);
    }

    private boolean checkBalance(String bet, User user) {
        return user.getBalance().compareTo(new BigDecimal(bet)) > -1;
    }
}

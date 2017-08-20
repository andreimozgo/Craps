package by.mozgo.craps.command.player;

import by.mozgo.craps.command.*;
import by.mozgo.craps.entity.User;
import by.mozgo.craps.services.ServiceException;
import by.mozgo.craps.services.game.GameLogic;
import by.mozgo.craps.services.game.RollResult;
import by.mozgo.craps.services.impl.BetServiceImpl;
import by.mozgo.craps.services.validator.Validator;
import by.mozgo.craps.services.vo.BetVO;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.util.List;
import java.util.Locale;

import static by.mozgo.craps.command.ActionResult.ActionType.FORWARD;

/**
 * Created by Andrei Mozgo. 2017.
 */
public class PlayCommand implements ActionCommand {
    private static final Logger LOG = LogManager.getLogger();
    private static final String BET_ON_PASS = "passBet";
    private static final String BET_ON_DONT_PASS = "dontPassBet";
    private static final String BET_ON_COME = "comeBet";
    private static final String BET_ON_DONT_COME = "dontComeBet";
    private static final String DICE = "dice";
    private static final String MESSAGE = "playMessage";
    private static final String BET_LIST = "betList";
    private static final int passBetId = 1;
    private static final int dontPassBetId = 2;
    private static final int comeBetId = 3;
    private static final int dontComeBetId = 4;

    @Override
    public ActionResult execute(HttpServletRequest request) {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(CrapsConstant.USER);
        String page = ConfigurationManager.getProperty("path.page.play");
        Locale locale;
        boolean isEnoughMoney = true;
        if (user != null) {
            GameLogic gameLogic = new GameLogic(user);
            String passBet = request.getParameter(BET_ON_PASS);
            try {
                if (Validator.validateMoney(passBet)) {
                    passBet = passBet.trim();
                    if (checkBalance(passBet, user)) {
                        gameLogic.addBet(passBetId, passBet);
                    } else {
                        isEnoughMoney = false;
                    }
                }
                String dontPassBet = request.getParameter(BET_ON_DONT_PASS);
                if (Validator.validateMoney(dontPassBet)) {
                    dontPassBet = dontPassBet.trim();
                    if (checkBalance(dontPassBet, user)) {
                        gameLogic.addBet(dontPassBetId, dontPassBet);
                    } else {
                        isEnoughMoney = false;
                    }
                }
                String comeBet = request.getParameter(BET_ON_COME);
                if (Validator.validateMoney(comeBet)) {
                    comeBet = comeBet.trim();
                    if (checkBalance(comeBet, user)) {
                        gameLogic.addBet(comeBetId, comeBet);
                    } else {
                        isEnoughMoney = false;
                    }
                }
                String dontComeBet = request.getParameter(BET_ON_DONT_COME);
                if (Validator.validateMoney(dontComeBet)) {
                    dontComeBet = dontComeBet.trim();
                    if (checkBalance(dontComeBet, user)) {
                        gameLogic.addBet(dontComeBetId, dontComeBet);
                    } else {
                        isEnoughMoney = false;
                    }
                }
                if (user.getGame() != null) {
                    RollResult rollResult = gameLogic.roll();
                    request.setAttribute(DICE, rollResult);
                    List<BetVO> betVOList = BetServiceImpl.getInstance().generateBetVO(user.getGame().getBets());
                    request.setAttribute(BET_LIST, betVOList);
                    if (!user.getGame().isFirstRoll()) {
                        page = ConfigurationManager.getProperty("path.page.secondroll");
                    }
                } else {
                    locale = (Locale) session.getAttribute(CrapsConstant.ATTRIBUTE_LOCALE);
                    request.setAttribute(MESSAGE, MessageManager.getProperty("play.make", locale));
                }
                if (!isEnoughMoney) {
                    locale = (Locale) session.getAttribute(CrapsConstant.ATTRIBUTE_LOCALE);
                    request.setAttribute(MESSAGE, MessageManager.getProperty("play.badbalance", locale));
                }
            } catch (ServiceException e) {
                LOG.log(Level.ERROR, e.getMessage());
                page = ConfigurationManager.getProperty("path.page.error");
            }
        }
        return new ActionResult(FORWARD, page);
    }

    private boolean checkBalance(String bet, User user) {
        return user.getBalance().compareTo(new BigDecimal(bet)) > -1;
    }
}

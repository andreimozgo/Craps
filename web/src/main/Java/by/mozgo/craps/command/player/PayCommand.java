package by.mozgo.craps.command.player;

import by.mozgo.craps.command.ActionCommand;
import by.mozgo.craps.command.ActionResult;
import by.mozgo.craps.command.ConfigurationManager;
import by.mozgo.craps.command.CrapsConstant;
import by.mozgo.craps.entity.User;
import by.mozgo.craps.services.UserService;
import by.mozgo.craps.services.impl.UserServiceImpl;
import by.mozgo.craps.services.validator.Validator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import static by.mozgo.craps.command.ActionResult.ActionType.FORWARD;

/**
 * Created by Andrei Mozgo. 2017.
 */
public class PayCommand implements ActionCommand {
    private static final String AMOUNT = "amount";

    @Override
    public ActionResult execute(HttpServletRequest request) {
        UserService userService = UserServiceImpl.getInstance();
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(CrapsConstant.USER);
        String amount = request.getParameter(AMOUNT);

        if (Validator.validateMoney(amount)) {
            amount = amount.trim();
            userService.makePayment(user, amount);
        }
        String page = ConfigurationManager.getProperty("path.page.payment");
        return new ActionResult(FORWARD, page);
    }
}

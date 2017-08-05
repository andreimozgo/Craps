package by.mozgo.craps.command.client;

import by.mozgo.craps.command.ActionCommand;
import by.mozgo.craps.command.ActionResult;
import by.mozgo.craps.command.ConfigurationManager;
import by.mozgo.craps.entity.User;
import by.mozgo.craps.services.Validator;
import by.mozgo.craps.services.impl.UserServiceImpl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import static by.mozgo.craps.command.ActionResult.ActionType.FORWARD;

/**
 * Created by Andrei Mozgo. 2017.
 */
public class PayCommand implements ActionCommand {
    @Override
    public ActionResult execute(HttpServletRequest request) {
        UserServiceImpl userService = UserServiceImpl.getInstance();
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");

        String amount = request.getParameter("amount");
        if (Validator.validateMoney(amount)) {
            userService.makePayment(user, amount);
        }
        String page = ConfigurationManager.getProperty("path.page.payment");
        return new ActionResult(FORWARD, page);
    }
}

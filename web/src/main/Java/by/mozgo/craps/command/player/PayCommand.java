package by.mozgo.craps.command.player;

import by.mozgo.craps.command.ActionCommand;
import by.mozgo.craps.command.ActionResult;
import by.mozgo.craps.command.ConfigurationManager;
import by.mozgo.craps.command.CrapsConstant;
import by.mozgo.craps.entity.User;
import by.mozgo.craps.services.ServiceException;
import by.mozgo.craps.services.UserService;
import by.mozgo.craps.services.impl.UserServiceImpl;
import by.mozgo.craps.services.validator.Validator;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import static by.mozgo.craps.command.ActionResult.ActionType.FORWARD;

/**
 * Created by Andrei Mozgo. 2017.
 */
public class PayCommand implements ActionCommand {
    private static final Logger LOG = LogManager.getLogger();
    private static final String AMOUNT = "amount";

    @Override
    public ActionResult execute(HttpServletRequest request) {
        UserService userService = UserServiceImpl.getInstance();
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(CrapsConstant.USER);
        String amount = request.getParameter(AMOUNT);
        String page = ConfigurationManager.getProperty("path.page.payment");

        if (Validator.validateMoney(amount)) {
            amount = amount.trim();
            try {
                userService.makePayment(user, amount);
            } catch (ServiceException e) {
                LOG.log(Level.ERROR, "Unable to make the payment.\n" + e.getMessage());
                page = ConfigurationManager.getProperty("path.page.error");
            }
        }
        return new ActionResult(FORWARD, page);
    }
}

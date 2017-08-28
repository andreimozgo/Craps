package by.mozgo.craps.command.user;

import by.mozgo.craps.StringConstant;
import by.mozgo.craps.command.ActionCommand;
import by.mozgo.craps.command.ActionResult;
import by.mozgo.craps.command.ConfigurationManager;
import by.mozgo.craps.command.MessageManager;
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
import java.util.Locale;

import static by.mozgo.craps.StringConstant.ATTRIBUTE_LOCALE;
import static by.mozgo.craps.StringConstant.USER;
import static by.mozgo.craps.command.ActionResult.ActionType.FORWARD;
import static by.mozgo.craps.command.ActionResult.ActionType.REDIRECT;

/**
 * ActionCommand implementation.
 * Performs actions to authenticate user
 *
 * @author Mozgo Andrei
 */
public class LoginCommand implements ActionCommand {
    private static final Logger LOG = LogManager.getLogger();
    private static final String PASSWORD = "password";

    @Override
    public ActionResult execute(HttpServletRequest request) {
        UserService userService = UserServiceImpl.getInstance();
        HttpSession session = request.getSession(true);
        Locale locale = (Locale) session.getAttribute(ATTRIBUTE_LOCALE);

        String page = ConfigurationManager.getProperty("path.page.login");
        ActionResult result = new ActionResult(FORWARD, page);
        request.setAttribute("errorLoginPassMessage", MessageManager.getProperty("error.loginerror", locale));

        // getting email from request
        String email = request.getParameter(StringConstant.EMAIL);
        String pass = request.getParameter(PASSWORD);
        if (Validator.validateEmail(email) && Validator.validatePassword(pass)) {
            email = email.trim();
            pass = pass.trim();
            try {
                if (userService.checkUser(email, pass)) {
                    User user = userService.findUserByEmail(email);
                    session.setAttribute(USER, user);
                    LOG.log(Level.INFO, "User {} logged in successfully", email);
                    page = ConfigurationManager.getProperty("command.empty");
                    result = new ActionResult(REDIRECT, page);
                }
            } catch (ServiceException e) {
                LOG.log(Level.ERROR, "Unable to make the payment.\n" + e.getMessage());
                page = ConfigurationManager.getProperty("path.page.error");
                new ActionResult(FORWARD, page);
            }
        }
        return result;
    }
}

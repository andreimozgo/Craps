package by.mozgo.craps.command.user;

import by.mozgo.craps.command.*;
import by.mozgo.craps.entity.User;
import by.mozgo.craps.services.UserService;
import by.mozgo.craps.services.impl.UserServiceImpl;
import by.mozgo.craps.services.validator.Validator;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Locale;

import static by.mozgo.craps.command.ActionResult.ActionType.FORWARD;
import static by.mozgo.craps.command.ActionResult.ActionType.REDIRECT;

public class LoginCommand implements ActionCommand {
    private static final String PASSWORD = "password";
    private static final Logger LOG = LogManager.getLogger();

    @Override
    public ActionResult execute(HttpServletRequest request) {
        UserService userService = UserServiceImpl.getInstance();
        HttpSession session = request.getSession(true);
        Locale locale = (Locale) session.getAttribute(CrapsConstant.ATTRIBUTE_LOCALE);

        String page = ConfigurationManager.getProperty("path.page.login");
        ActionResult result = new ActionResult(FORWARD, page);
        request.setAttribute("errorLoginPassMessage", MessageManager.getProperty("error.loginerror", locale));

        // getting email from request
        String email = request.getParameter(CrapsConstant.EMAIL);
        String pass = request.getParameter(PASSWORD);
        if (Validator.validateEmail(email) && Validator.validatePassword(pass)) {
            email = email.trim();
            pass = pass.trim();
            if (userService.checkUser(email, pass)) {
                User user = userService.findUserByEmail(email);
                session.setAttribute(CrapsConstant.USER, user);
                LOG.log(Level.INFO, "User {} logged in successfully", email);
                page = ConfigurationManager.getProperty("command.empty");
                result = new ActionResult(REDIRECT, page);
            }
        }
        return result;
    }
}

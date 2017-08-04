package by.mozgo.craps.command.user;

import by.mozgo.craps.command.*;
import by.mozgo.craps.entity.User;
import by.mozgo.craps.services.impl.UserServiceImpl;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Locale;

import static by.mozgo.craps.command.ActionResult.ActionType.FORWARD;
import static by.mozgo.craps.command.ActionResult.ActionType.REDIRECT;

public class LoginCommand implements ActionCommand {
    private static final Logger LOG = LogManager.getLogger();
    @Override
    public ActionResult execute(HttpServletRequest request) {
        UserServiceImpl userService = UserServiceImpl.getInstance();
        ActionResult result = null;
        String page;

        // getting email from request
        String email = request.getParameter("email");
        HttpSession session = request.getSession(true);
        String pass = request.getParameter("password");

        if (userService.checkPassword(email, pass)) {
            User user = userService.findUserByEmail(email);
            session.setAttribute("user", user);
            LOG.log(Level.INFO, "User {} logged in successfully", email);
            page = ConfigurationManager.getProperty("command.empty");
            result = new ActionResult(REDIRECT, page);
            throw new NullPointerException();
        } else {
            Locale locale = (Locale) session.getAttribute(StringConstant.ATTRIBUTE_LOCALE);
            request.setAttribute("errorLoginPassMessage", MessageManager.getProperty("error.loginerror", locale));
            page = ConfigurationManager.getProperty("path.page.login");
            result = new ActionResult(FORWARD, page);
        }
        return result;
    }
}

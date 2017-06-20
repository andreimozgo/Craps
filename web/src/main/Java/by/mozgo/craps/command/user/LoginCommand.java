package by.mozgo.craps.command.user;

import by.mozgo.craps.command.*;
import by.mozgo.craps.entity.User;
import by.mozgo.craps.services.impl.UserServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Locale;

import static by.mozgo.craps.command.ActionResult.ActionType.FORWARD;
import static by.mozgo.craps.command.ActionResult.ActionType.REDIRECT;

public class LoginCommand implements ActionCommand {
    private static final Logger LOG = LogManager.getLogger();

    public ActionResult execute(HttpServletRequest request) {
        UserServiceImpl userService = UserServiceImpl.getInstance();
        ActionResult result = null;
        String page = null;
        String userRole;

        // getting email from request
        String email = request.getParameter("email");
        HttpSession session = request.getSession(true);
        // email check
        User user = userService.findUserByEmail(email);
        if (user != null) {
            String pass = request.getParameter("password");
            if (userService.checkPassword(email, pass)) {
                // getting user role
                session.setAttribute("user", user);
                userRole = user.getUserRole().toString();
                // setting user role to session
                session.setAttribute("role", userRole);
                LOG.info("User " + email + " logged in successfully");

                // getting page depending on user role
                if (userRole.equals("admin")) {
                    page = ConfigurationManager.getProperty("command.adminpage");
                } else {
                    page = ConfigurationManager.getProperty("command.clientpage");
                }
                result = new ActionResult(REDIRECT, page);
            }
        } else {
            Locale locale = (Locale) session.getAttribute(StringConstant.ATTRIBUTE_LOCALE);
            request.setAttribute("errorLoginPassMessage", MessageManager.getProperty("error.loginerror", locale));
            page = ConfigurationManager.getProperty("path.page.login");
            result = new ActionResult(FORWARD, page);
        }
        return result;
    }
}

package by.mozgo.craps.command.user;

import by.mozgo.craps.command.ActionCommand;
import by.mozgo.craps.command.ConfigurationManager;
import by.mozgo.craps.command.MessageManager;
import by.mozgo.craps.command.StringConstant;
import by.mozgo.craps.command.admin.AdminPageCommand;
import by.mozgo.craps.command.client.ClientPageCommand;
import by.mozgo.craps.entity.User;
import by.mozgo.craps.services.impl.UserServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Locale;

public class LoginCommand implements ActionCommand {
    private static final Logger LOG = LogManager.getLogger();

    public String execute(HttpServletRequest request) {
        UserServiceImpl userService = UserServiceImpl.getInstance();
        String page = null;
        String userRole;
        boolean userExist = false;

        // getting email from request
        String email = request.getParameter("email");
        HttpSession session = request.getSession(true);
        // email check
        if (userService.findUserByEmail(email) != null) {
            String pass = request.getParameter("password");
            if (userService.checkPassword(email, pass)) {
                // getting user role
                User user = userService.findUserByEmail(email);
                session.setAttribute("username", user.getUsername());
                userRole = user.getUserRole().toString();
                // setting user role to session
                session.setAttribute("role", userRole);
                LOG.info("User " + email + " logged in successfully");

                // getting page depending on user role
                if (userRole.equals("admin")) {
                    page = new AdminPageCommand().execute(request);
                } else {
                    page = new ClientPageCommand().execute(request);
                }
                userExist = true;
            }
        }
        if (!userExist) {
            Locale locale = (Locale) session.getAttribute(StringConstant.ATTRIBUTE_LOCALE);
            request.setAttribute("errorLoginPassMessage", MessageManager.getProperty("error.loginerror", locale));
            page = ConfigurationManager.getProperty("path.page.login");
        }
        return page;
    }
}

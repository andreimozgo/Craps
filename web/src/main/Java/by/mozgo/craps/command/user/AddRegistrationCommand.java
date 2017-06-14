package by.mozgo.craps.command.user;

import by.mozgo.craps.command.ActionCommand;
import by.mozgo.craps.command.ConfigurationManager;
import by.mozgo.craps.command.MessageManager;
import by.mozgo.craps.command.StringConstant;
import by.mozgo.craps.entity.User;
import by.mozgo.craps.services.impl.UserServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.util.Locale;
import java.util.Map;

public class AddRegistrationCommand implements ActionCommand {
    private static final Logger LOG = LogManager.getLogger();

    public String execute(HttpServletRequest request) {
        UserServiceImpl userService = UserServiceImpl.getInstance();
        String page = null;
        Map<String, String[]> params = request.getParameterMap();

        String email = request.getParameter("email");
        Locale locale = (Locale) request.getSession().getAttribute(StringConstant.ATTRIBUTE_LOCALE);
        String username = request.getParameter("username");
        ;

        if (userService.findUserByEmail(email) == null) {
            String password = request.getParameter("pwd1");
            User user = new User();
            user.setEmail(email);
            user.setPassword(password);
            user.setUsername(username);
            userService.createOrUpdate(user);
            LOG.info("New registration added successfully");
            request.setAttribute("registrationResultMessage", MessageManager.getProperty("registration.success", locale));
            page = ConfigurationManager.getProperty("path.page.login");
        } else {
            request.setAttribute("registrationResultMessage", MessageManager.getProperty("registration.failemail", locale));
            page = ConfigurationManager.getProperty("path.page.registration");
            request.setAttribute("email", email);
            request.setAttribute("username", username);
        }
        return page;
    }
}

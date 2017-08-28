package by.mozgo.craps.command.user;

import by.mozgo.craps.command.ActionCommand;
import by.mozgo.craps.command.ActionResult;
import by.mozgo.craps.command.ConfigurationManager;
import by.mozgo.craps.entity.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import static by.mozgo.craps.StringConstant.USER;
import static by.mozgo.craps.command.ActionResult.ActionType.FORWARD;

/**
 * ActionCommand implementation.
 * If error occurred or direct call controller
 * forwarding user to page depending on role or to login page
 *
 * @author Mozgo Andrei
 */
public class EmptyCommand implements ActionCommand {
    @Override
    public ActionResult execute(HttpServletRequest request) {
        String page;
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(USER);
        if (user != null) {
            // getting page depending on user role
            switch (user.getUserRole()) {
                case ADMIN:
                    page = ConfigurationManager.getProperty("command.adminpage");
                    break;
                default:
                    page = ConfigurationManager.getProperty("command.playerpage");
                    break;
            }
        } else {
            page = ConfigurationManager.getProperty("path.page.login");
        }
        return new ActionResult(FORWARD, page);
    }
}


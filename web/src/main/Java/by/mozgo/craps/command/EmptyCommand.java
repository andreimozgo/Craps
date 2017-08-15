package by.mozgo.craps.command;

import by.mozgo.craps.entity.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import static by.mozgo.craps.command.ActionResult.ActionType.FORWARD;

public class EmptyCommand implements ActionCommand {
    private static final Logger LOG = LogManager.getLogger();
    @Override
    public ActionResult execute(HttpServletRequest request) {
        /*
         * if error occurred or direct call controller
		 *  forwarding user to page depending on role or to login page
		 */
        String page;
        LOG.info("Empty command");
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
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


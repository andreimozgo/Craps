package by.mozgo.craps.command;

import by.mozgo.craps.entity.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import static by.mozgo.craps.command.ActionResult.ActionType.FORWARD;

public class EmptyCommand implements ActionCommand {
    @Override
    public ActionResult execute(HttpServletRequest request) {
        /*
         * if error occurred or direct call controller
		 *  forwarding user to page depending on role or to login page
		 */
        String page;
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(CrapsConstant.USER);
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


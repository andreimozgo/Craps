package by.mozgo.craps.command.admin;

import by.mozgo.craps.command.ActionCommand;
import by.mozgo.craps.command.ActionResult;
import by.mozgo.craps.command.ConfigurationManager;
import by.mozgo.craps.services.UserService;
import by.mozgo.craps.services.impl.UserServiceImpl;

import javax.servlet.http.HttpServletRequest;

import static by.mozgo.craps.command.ActionResult.ActionType.FORWARD;

/**
 * Created by Andrei Mozgo. 2017.
 */
public class ChangeRoleCommand implements ActionCommand {
    private static final String USER_ID = "user_id";
    private static final String NEW_ROLE = "newRole";

    @Override
    public ActionResult execute(HttpServletRequest request) {
        UserService userService = UserServiceImpl.getInstance();
        String page;
        int id = Integer.parseInt(request.getParameter(USER_ID));
        int newRole = Integer.parseInt(request.getParameter(NEW_ROLE));
        userService.updateRole(id, newRole);
        page = ConfigurationManager.getProperty("command.adminpage");
        return new ActionResult(FORWARD, page);
    }
}

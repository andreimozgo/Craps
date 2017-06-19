package by.mozgo.craps.command.admin;

import by.mozgo.craps.command.ActionCommand;
import by.mozgo.craps.command.ActionResult;
import by.mozgo.craps.command.ConfigurationManager;
import by.mozgo.craps.services.impl.UserServiceImpl;

import javax.servlet.http.HttpServletRequest;

import static by.mozgo.craps.command.ActionResult.ActionType.REDIRECT;

/**
 * Created by Andrei Mozgo. 2017.
 */
public class ChangeRoleCommand implements ActionCommand {

    public ActionResult execute(HttpServletRequest request) {
        UserServiceImpl userService = UserServiceImpl.getInstance();
        ActionResult result;
        String page;
        int id = Integer.parseInt(request.getParameter("user_id"));
        int newRole = Integer.parseInt(request.getParameter("newRole"));
        userService.updateRole(id, newRole);
        page = ConfigurationManager.getProperty("command.adminpage");
        return new ActionResult(REDIRECT, page);
    }
}

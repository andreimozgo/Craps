package by.mozgo.craps.command.admin;

import by.mozgo.craps.command.ActionCommand;
import by.mozgo.craps.command.ActionResult;
import by.mozgo.craps.command.ConfigurationManager;
import by.mozgo.craps.services.impl.UserServiceImpl;

import javax.servlet.http.HttpServletRequest;

import static by.mozgo.craps.command.ActionResult.ActionType.FORWARD;

public class DeleteUserCommand implements ActionCommand {
    @Override
    public ActionResult execute(HttpServletRequest request) {
        UserServiceImpl userService = UserServiceImpl.getInstance();
        String page;

        int id = Integer.parseInt(request.getParameter("user_id"));
        userService.delete(id);

        page = ConfigurationManager.getProperty("command.adminpage");
        return new ActionResult(FORWARD, page);
    }
}

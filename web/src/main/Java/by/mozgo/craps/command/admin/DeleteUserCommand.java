package by.mozgo.craps.command.admin;

import by.mozgo.craps.command.ActionCommand;
import by.mozgo.craps.command.ActionResult;
import by.mozgo.craps.command.ConfigurationManager;
import by.mozgo.craps.services.UserService;
import by.mozgo.craps.services.impl.UserServiceImpl;

import javax.servlet.http.HttpServletRequest;

import static by.mozgo.craps.command.ActionResult.ActionType.FORWARD;

public class DeleteUserCommand implements ActionCommand {
    private static final String USER_ID = "user_id";

    @Override
    public ActionResult execute(HttpServletRequest request) {
        UserService userService = UserServiceImpl.getInstance();
        String page;

        int id = Integer.parseInt(request.getParameter(USER_ID));
        userService.delete(id);

        page = ConfigurationManager.getProperty("command.adminpage");
        return new ActionResult(FORWARD, page);
    }
}

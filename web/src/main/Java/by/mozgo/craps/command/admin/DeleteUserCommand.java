package by.mozgo.craps.command.admin;

import by.mozgo.craps.command.ActionCommand;
import by.mozgo.craps.command.ActionResult;
import by.mozgo.craps.command.ConfigurationManager;
import by.mozgo.craps.services.ServiceException;
import by.mozgo.craps.services.UserService;
import by.mozgo.craps.services.impl.UserServiceImpl;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

import static by.mozgo.craps.command.ActionResult.ActionType.FORWARD;

public class DeleteUserCommand implements ActionCommand {
    private static final Logger LOG = LogManager.getLogger();
    private static final String USER_ID = "user_id";

    @Override
    public ActionResult execute(HttpServletRequest request) {
        UserService userService = UserServiceImpl.getInstance();
        String page = ConfigurationManager.getProperty("command.adminpage");

        int id = Integer.parseInt(request.getParameter(USER_ID));
        try {
            userService.delete(id);
        } catch (ServiceException e) {
            LOG.log(Level.ERROR, "Unable to show users list.\n" + e.getMessage());
            page = ConfigurationManager.getProperty("path.page.error");
        }
        return new ActionResult(FORWARD, page);
    }
}

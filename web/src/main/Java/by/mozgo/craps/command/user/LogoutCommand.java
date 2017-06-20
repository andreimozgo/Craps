package by.mozgo.craps.command.user;

import by.mozgo.craps.command.ActionCommand;
import by.mozgo.craps.command.ActionResult;
import by.mozgo.craps.command.ConfigurationManager;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

import static by.mozgo.craps.command.ActionResult.ActionType.FORWARD;

public class LogoutCommand implements ActionCommand {
    private static final Logger LOG = LogManager.getLogger();

    public ActionResult execute(HttpServletRequest request) {
        request.getSession().invalidate();
        LOG.log(Level.INFO, "User logged out successfully");
        String page = ConfigurationManager.getProperty("path.page.index");
        return new ActionResult(FORWARD, page);
    }
}
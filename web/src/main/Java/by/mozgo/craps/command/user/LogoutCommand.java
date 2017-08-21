package by.mozgo.craps.command.user;

import by.mozgo.craps.command.ActionCommand;
import by.mozgo.craps.command.ActionResult;
import by.mozgo.craps.command.ConfigurationManager;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

import static by.mozgo.craps.command.ActionResult.ActionType.REDIRECT;

/**
 * ActionCommand implementation.
 * Makes logout action
 *
 * @author Mozgo Andrei
 */
public class LogoutCommand implements ActionCommand {
    private static final Logger LOG = LogManager.getLogger();

    @Override
    public ActionResult execute(HttpServletRequest request) {
        request.getSession().invalidate();
        LOG.log(Level.INFO, "User logged out successfully");
        String page = ConfigurationManager.getProperty("command.empty");
        return new ActionResult(REDIRECT, page);
    }
}
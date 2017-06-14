package by.mozgo.craps.command.user;

import by.mozgo.craps.command.ActionCommand;
import by.mozgo.craps.command.ConfigurationManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

public class LogoutCommand implements ActionCommand {
    private static final Logger LOG = LogManager.getLogger();

    public String execute(HttpServletRequest request) {
        request.getSession().invalidate();
        LOG.info("User logged out successfully");
        String page = ConfigurationManager.getProperty("path.page.index");
        return page;
    }
}
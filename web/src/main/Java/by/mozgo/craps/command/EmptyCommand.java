package by.mozgo.craps.command;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

public class EmptyCommand implements ActionCommand {
    private static final Logger LOG = LogManager.getLogger();

    public String execute(HttpServletRequest request) {
        /*
         * if error occurred or direct call controller
		 *  forwarding to login page
		 */
        LOG.info("Empty command");
        String page = ConfigurationManager.getProperty("path.page.login");
        return page;
    }
}

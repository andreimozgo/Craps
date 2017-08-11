package by.mozgo.craps.command;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

public class ActionFactory {
    private static final Logger LOG = LogManager.getLogger();

    public ActionCommand defineCommand(HttpServletRequest request) {
        ActionCommand current = new EmptyCommand();
        // get command from request
        String action = request.getParameter("command");
        if (!(action == null || action.isEmpty())) {
            // call of the corresponding object
            try {
                CommandEnum currentEnum = CommandEnum.valueOf(action.toUpperCase());
                current = currentEnum.getCurrentCommand();
            } catch (IllegalArgumentException e) {
                LOG.log(Level.ERROR, "{} - {}", action, "Command not found or wrong.");
            }
        }
        return current;
    }
}

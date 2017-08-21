package by.mozgo.craps.command;

import by.mozgo.craps.command.user.EmptyCommand;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

/**
 * ActionFactory.
 *
 * @author Mozgo Andrei
 */
public class ActionFactory {
    private static final String COMMAND = "command";
    private static final Logger LOG = LogManager.getLogger();

    /**
     * Defines concrete command by request data
     *
     * @param request
     * @return defined ActionCommand implementation
     */
    public ActionCommand defineCommand(HttpServletRequest request) {
        ActionCommand current = new EmptyCommand();
        // get command from request
        String action = request.getParameter(COMMAND);
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

package by.mozgo.craps.command;

import javax.servlet.http.HttpServletRequest;

/**
 * Interface used in Command pattern.
 *
 * @author Mozgo Andrei
 */
public interface ActionCommand {
    /**
     * Executes some action with request
     *
     * @param request
     * @return ActionResult
     */
    ActionResult execute(HttpServletRequest request);
}

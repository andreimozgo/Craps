package by.mozgo.craps.command.player;

import by.mozgo.craps.command.ActionCommand;
import by.mozgo.craps.command.ActionResult;
import by.mozgo.craps.command.ConfigurationManager;

import javax.servlet.http.HttpServletRequest;

import static by.mozgo.craps.command.ActionResult.ActionType.FORWARD;

/**
 * ActionCommand implementation.
 * Forwards to main player page.
 *
 * @author Mozgo Andrei
 */
public class PlayerPageCommand implements ActionCommand {
    @Override
    public ActionResult execute(HttpServletRequest request) {
        String page = ConfigurationManager.getProperty("path.page.player");
        return new ActionResult(FORWARD, page);
    }
}

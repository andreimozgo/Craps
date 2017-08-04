package by.mozgo.craps.command.user;

import by.mozgo.craps.command.ActionCommand;
import by.mozgo.craps.command.ActionResult;
import by.mozgo.craps.command.ConfigurationManager;

import javax.servlet.http.HttpServletRequest;

import static by.mozgo.craps.command.ActionResult.ActionType.FORWARD;

public class RegistrationCommand implements ActionCommand {
    @Override
    public ActionResult execute(HttpServletRequest request) {
        String page = ConfigurationManager.getProperty("path.page.registration");
        return new ActionResult(FORWARD, page);
    }
}

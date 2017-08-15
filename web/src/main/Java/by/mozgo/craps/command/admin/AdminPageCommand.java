package by.mozgo.craps.command.admin;

import by.mozgo.craps.command.ActionCommand;
import by.mozgo.craps.command.ActionResult;
import by.mozgo.craps.command.ConfigurationManager;

import javax.servlet.http.HttpServletRequest;

import static by.mozgo.craps.command.ActionResult.ActionType.FORWARD;

public class AdminPageCommand implements ActionCommand {
    @Override
    public ActionResult execute(HttpServletRequest request) {
        String page = ConfigurationManager.getProperty("path.page.admin");
        return new ActionResult(FORWARD, page);
    }
}

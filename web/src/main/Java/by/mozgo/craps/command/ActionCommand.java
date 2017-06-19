package by.mozgo.craps.command;

import javax.servlet.http.HttpServletRequest;

public interface ActionCommand {
    ActionResult execute(HttpServletRequest request);
}

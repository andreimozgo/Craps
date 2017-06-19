package by.mozgo.craps.command.admin;

import by.mozgo.craps.command.ActionCommand;
import by.mozgo.craps.command.ActionResult;
import by.mozgo.craps.command.ConfigurationManager;
import by.mozgo.craps.entity.User;
import by.mozgo.craps.services.impl.UserServiceImpl;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

import static by.mozgo.craps.command.ActionResult.ActionType.FORWARD;

public class AdminPageCommand implements ActionCommand {

    public ActionResult execute(HttpServletRequest request) {
        UserServiceImpl userService = UserServiceImpl.getInstance();
        String page;
        int currentPage;
        int recordsPerPage;

        if (request.getParameter("recordsPerPage") != null) {
            recordsPerPage = Integer.valueOf(request.getParameter("recordsPerPage"));
        } else {
            recordsPerPage = 3;
        }

        if (request.getParameter("currentPage") != null) {
            currentPage = Integer.valueOf(request.getParameter("currentPage"));
        } else {
            currentPage = 1;
        }
        List<User> users = userService.getAll(recordsPerPage, currentPage);
        int numberOfPages = userService.getNumberOfPages(recordsPerPage);
        request.setAttribute("users", users);
        request.setAttribute("numberOfPages", numberOfPages);
        request.setAttribute("currentPage", currentPage);
        request.setAttribute("recordsPerPage", recordsPerPage);

        page = ConfigurationManager.getProperty("path.page.main");

        return new ActionResult(FORWARD, page);
    }
}

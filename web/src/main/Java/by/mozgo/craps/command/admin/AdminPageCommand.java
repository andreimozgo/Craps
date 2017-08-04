package by.mozgo.craps.command.admin;

import by.mozgo.craps.command.ActionCommand;
import by.mozgo.craps.command.ActionResult;
import by.mozgo.craps.command.ConfigurationManager;
import by.mozgo.craps.entity.User;
import by.mozgo.craps.services.impl.UserServiceImpl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

import static by.mozgo.craps.command.ActionResult.ActionType.FORWARD;

public class AdminPageCommand implements ActionCommand {
    @Override
    public ActionResult execute(HttpServletRequest request) {
        UserServiceImpl userService = UserServiceImpl.getInstance();
        HttpSession session = request.getSession();
        String page;
        int currentPage;
        int recordsPerPage;

        if (request.getParameter("recordsPerPage") != null) {
            recordsPerPage = Integer.parseInt(request.getParameter("recordsPerPage"));
        } else if (session.getAttribute("recordsPerPage") != null) {
            recordsPerPage = (Integer) session.getAttribute("recordsPerPage");
        } else {
            recordsPerPage = 5;
        }

        if (request.getParameter("currentPage") != null) {
            currentPage = Integer.parseInt(request.getParameter("currentPage"));
        } else if (session.getAttribute("currentPage") != null) {
            currentPage = (Integer) session.getAttribute("currentPage");
        } else {
            currentPage = 1;
        }
        int numberOfPages = userService.getNumberOfPages(recordsPerPage);
        if (currentPage > numberOfPages) {
            currentPage = numberOfPages;
        }
        List<User> users = userService.getAll(recordsPerPage, currentPage);
        request.setAttribute("users", users);
        request.setAttribute("numberOfPages", numberOfPages);
        session.setAttribute("currentPage", currentPage);
        session.setAttribute("recordsPerPage", recordsPerPage);

        page = ConfigurationManager.getProperty("path.page.admin");
        return new ActionResult(FORWARD, page);
    }
}

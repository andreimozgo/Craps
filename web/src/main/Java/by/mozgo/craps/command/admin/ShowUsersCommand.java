package by.mozgo.craps.command.admin;

import by.mozgo.craps.command.ActionCommand;
import by.mozgo.craps.command.ActionResult;
import by.mozgo.craps.command.ConfigurationManager;
import by.mozgo.craps.entity.User;
import by.mozgo.craps.services.ServiceException;
import by.mozgo.craps.services.UserService;
import by.mozgo.craps.services.impl.UserServiceImpl;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

import static by.mozgo.craps.command.ActionResult.ActionType.FORWARD;

/**
 * Created by Andrei Mozgo. 2017.
 */
public class ShowUsersCommand implements ActionCommand {
    private static final Logger LOG = LogManager.getLogger();
    private static final String RECORDS_ON_PAGE = "recordsPerPage";
    private static final String CURRENT_PAGE = "currentPage";
    private static final String USERS = "users";
    private static final String NUMBER_OF_PAGES = "numberOfPages";
    private static final int RECORDS_ON_PAGE_DEFAULT = 5;
    private static final int DEFAULT_CURRENT_PAGE = 1;

    @Override
    public ActionResult execute(HttpServletRequest request) {
        UserService userService = UserServiceImpl.getInstance();
        HttpSession session = request.getSession();
        String page = ConfigurationManager.getProperty("path.page.adminusers");

        int recordsOnPage = RECORDS_ON_PAGE_DEFAULT;
        int currentPage = DEFAULT_CURRENT_PAGE;
        String recordsOnPageString = request.getParameter(RECORDS_ON_PAGE);
        String currentPageString = request.getParameter(CURRENT_PAGE);

        if (recordsOnPageString != null) {
            recordsOnPage = Integer.parseInt(recordsOnPageString);
        } else if (session.getAttribute(RECORDS_ON_PAGE) != null) {
            recordsOnPage = (Integer) session.getAttribute(RECORDS_ON_PAGE);
        }
        if (currentPageString != null) {
            currentPage = Integer.parseInt(currentPageString);
        } else if (session.getAttribute(CURRENT_PAGE) != null) {
            currentPage = (Integer) session.getAttribute(CURRENT_PAGE);
        }

        try {
            int numberOfPages = userService.findPagesNumber(recordsOnPage);
            if (currentPage > numberOfPages) {
                currentPage = numberOfPages;
            }
            List<User> users = userService.findAll(recordsOnPage, currentPage);
            request.setAttribute(USERS, users);
            request.setAttribute(NUMBER_OF_PAGES, numberOfPages);
            session.setAttribute(CURRENT_PAGE, currentPage);
            session.setAttribute(RECORDS_ON_PAGE, recordsOnPage);
        } catch (ServiceException e) {
            LOG.log(Level.ERROR, "Unable to show users list.\n" + e.getMessage());
            page = ConfigurationManager.getProperty("path.page.error");
        }

        return new ActionResult(FORWARD, page);
    }
}

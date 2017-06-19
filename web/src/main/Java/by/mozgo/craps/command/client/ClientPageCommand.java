package by.mozgo.craps.command.client;

import by.mozgo.craps.command.ActionCommand;
import by.mozgo.craps.command.ActionResult;
import by.mozgo.craps.command.ConfigurationManager;

import javax.servlet.http.HttpServletRequest;

import static by.mozgo.craps.command.ActionResult.ActionType.FORWARD;

public class ClientPageCommand implements ActionCommand {

    public ActionResult execute(HttpServletRequest request) {
        String page;
/*        final Logger LOG = Logger.getLogger(ClientPageCommand.class);
        TicketServiceImpl ticketService = TicketServiceImpl.getInstance();
        FlightServiceImpl flightService = FlightServiceImpl.getInstance();

        int currentPage;
        int recordsPerPage;
        String flightDate;
        List<Flight> flights;

        HttpSession session = request.getSession(true);
        int userId = (Integer) session.getAttribute("userid");
        List<Ticket> tickets = ticketService.getAllByUser(userId);
        request.setAttribute("tickets", tickets);

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
        int numberOfPages;
        if (request.getParameter("flightDate") != null) {
            flightDate = request.getParameter("flightDate");
            LOG.info("FlightDate= " + flightDate);
            flights = flightService.getAll(recordsPerPage, currentPage, flightDate);
            numberOfPages = flightService.getNumberOfPages(recordsPerPage, flightDate);
        } else {
            flights = flightService.getAll(recordsPerPage, currentPage);
            numberOfPages = flightService.getNumberOfPages(recordsPerPage);
        }

        request.setAttribute("flights", flights);
        request.setAttribute("numberOfPages", numberOfPages);
        request.setAttribute("currentPage", currentPage);
        request.setAttribute("recordsPerPage", recordsPerPage);*/

        page = ConfigurationManager.getProperty("path.page.user");
        return new ActionResult(FORWARD, page);
    }
}

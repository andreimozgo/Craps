package by.mozgo.craps.controller;

import by.mozgo.craps.command.*;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Locale;

@WebServlet(urlPatterns = {"/controller"})
public class Controller extends HttpServlet {

    private static final long serialVersionUID = -6668349208729370249L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    private void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String page;
        // get the command from JSP
        ActionFactory client = new ActionFactory();
        ActionCommand command = client.defineCommand(request);

        // call execute() and forward request
        page = command.execute(request); // gives answer page
        if (page != null) {
            RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(page);
            // calls response page
            dispatcher.forward(request, response);
        } else {
            // gets error page
            page = ConfigurationManager.getProperty("path.page.index");
            HttpSession session = request.getSession(true);
            if (session.getAttribute("role").equals(1)) {
                Locale locale = (Locale) session.getAttribute(StringConstant.ATTRIBUTE_LOCALE);
                session.setAttribute("nullPage", MessageManager.getProperty("message.nullpage", locale));
            }
            response.sendRedirect(request.getContextPath() + page);
        }
    }
}
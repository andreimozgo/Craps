package by.mozgo.craps.controller;

import by.mozgo.craps.command.ActionCommand;
import by.mozgo.craps.command.ActionFactory;
import by.mozgo.craps.command.ActionResult;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(urlPatterns = {"/craps"})
@MultipartConfig(fileSizeThreshold = 1024 * 512, // 512 Kb
        maxFileSize = 1024 * 512, // 512 Kb
        maxRequestSize = 1024 * 528 // 520 Kb (512 Kb for image + 16 Kb for other data)
)
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
        ActionResult result;
        String page;
        // get the command from JSP
        ActionFactory client = new ActionFactory();
        ActionCommand command = client.defineCommand(request);

        // call execute() and forward request
        result = command.execute(request); // gives answer page
        switch (result.getType()) {
            case FORWARD:
                RequestDispatcher dispatcher = request.getRequestDispatcher(result.getPage());
                dispatcher.forward(request, response);
                break;
            case REDIRECT:
                response.sendRedirect(result.getPage());
                break;
        }
    }
}
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

/**
 * Application Controller (HttpServlet)
 *
 * @author Mozgo Andrej
 */
@WebServlet(urlPatterns = {"/craps"})
@MultipartConfig(fileSizeThreshold = 1024 * 512, // 512 Kb
        maxFileSize = 1024 * 512, // 512 Kb
        maxRequestSize = 1024 * 528 // 520 Kb (512 Kb for image + 16 Kb for other data)
)
public class Controller extends HttpServlet {

    private static final long serialVersionUID = -6668349208729370249L;

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
     * response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
     *      response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Processes request
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    private void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        ActionResult result;
        ActionFactory client = new ActionFactory();
        ActionCommand command = client.defineCommand(request);
        result = command.execute(request);
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

    @Override
    public void destroy() {
        //ConnectionPool.getInstance().closePool();
        super.destroy();
    }
}
package by.mozgo.craps.filter;

import by.mozgo.craps.command.ConfigurationManager;
import by.mozgo.craps.entity.User;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * Created by Andrei Mozgo. 2017.
 */
@WebFilter(dispatcherTypes = {DispatcherType.FORWARD, DispatcherType.REQUEST}, urlPatterns = {"/jsp/admin/*"})
public class AdminFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        User user = (User) httpRequest.getSession().getAttribute("user");
        if (user == null || !user.getUserRole().equals(User.UserRole.ADMIN)) {
            String page = ConfigurationManager.getProperty("path.page.error404");
            RequestDispatcher dispatcher = request.getServletContext().getRequestDispatcher(page);
            dispatcher.forward(request, response);
        }
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
    }
}

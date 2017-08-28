package by.mozgo.craps.filter;

import by.mozgo.craps.command.ConfigurationManager;
import by.mozgo.craps.entity.User;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

import static by.mozgo.craps.StringConstant.USER;

/**
 * Servlet Filter implementation class AdminFilter checks user type,
 * compares user permission to get admin pages and prevents
 * unauthorized access.
 *
 * @author Mozgo Andrei
 */
@WebFilter(dispatcherTypes = {DispatcherType.FORWARD, DispatcherType.REQUEST}, urlPatterns = {"/jsp/admin/*"})
public class AdminFilter implements Filter {

    /**
     * @see Filter#init(FilterConfig)
     */
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    /**
     * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
     */
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        User user = (User) httpRequest.getSession().getAttribute(USER);
        if (user == null || !user.getUserRole().equals(User.UserRole.ADMIN)) {
            String page = ConfigurationManager.getProperty("path.page.error.404");
            RequestDispatcher dispatcher = request.getServletContext().getRequestDispatcher(page);
            dispatcher.forward(request, response);
        }
        chain.doFilter(request, response);
    }

    /**
     * @see Filter#destroy()
     */
    @Override
    public void destroy() {
    }
}
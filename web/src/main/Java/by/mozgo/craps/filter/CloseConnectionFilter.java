package by.mozgo.craps.filter;

import by.mozgo.craps.util.ConnectionPool;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;

/**
 * Servlet Filter implementation class CloseConnectionFilter closes the
 * connection to the database
 *
 * @author Mozgo Andrei
 */
@WebFilter(urlPatterns = {"/*"})
public class CloseConnectionFilter implements Filter {
    private static final Logger LOG = LogManager.getLogger();

    /**
     * @see Filter#init(FilterConfig)
     */
    public void init(FilterConfig fConfig) throws ServletException {
    }

    /**
     * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
     */
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        ConnectionPool pool = ConnectionPool.getInstance();
        if (pool.isConnectionOpen()) {
            pool.getConnection().close();
        }
        chain.doFilter(request, response);
        LOG.log(Level.INFO, "DB connection closed");
    }

    /**
     * @see Filter#destroy()
     */
    public void destroy() {
    }
}

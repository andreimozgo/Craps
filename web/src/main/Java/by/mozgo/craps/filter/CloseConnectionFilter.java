package by.mozgo.craps.filter;

import by.mozgo.craps.util.ConnectionPool;
import by.mozgo.craps.util.ConnectionWrapper;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;

@WebFilter(urlPatterns = {"/*"})
public class CloseConnectionFilter implements Filter {
    private static final Logger LOG = LogManager.getLogger();

    public void init(FilterConfig fConfig) throws ServletException {
    }

    public void doFilter(ServletRequest request, ServletResponse response,
                         FilterChain chain) throws IOException, ServletException {
        ConnectionWrapper connection = ConnectionPool.getInstance().getConnection();
        connection.close();

        chain.doFilter(request, response);
        LOG.log(Level.INFO, "DB connection closed");
    }

    public void destroy() {
    }
}

package by.mozgo.craps.filter;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;
import java.io.IOException;

/**
 * Servlet Filter implementation class EncodingFilter
 * set characters encoding to UTF-8
 *
 * @author Mozgo Andrei
 */
@WebFilter(urlPatterns = {"/*"}, initParams = {
        @WebInitParam(name = "encoding", value = "UTF-8", description = "Encoding Param")})

public class EncodingFilter implements Filter {
    private static final Logger LOG = LogManager.getLogger();
    private String code;

    /**
     * @see Filter#init(FilterConfig)
     */
    public void init(FilterConfig fConfig) throws ServletException {
        code = fConfig.getInitParameter("encoding");
    }

    /**
     * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
     */
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        String codeRequest = request.getCharacterEncoding();
        // set encoding if don't exist
        if (code != null && !code.equalsIgnoreCase(codeRequest)) {
            request.setCharacterEncoding(code);
            response.setCharacterEncoding(code);
            LOG.log(Level.INFO,"Encoding changed to UTF-8 successfully");
        }
        chain.doFilter(request, response);
    }

    /**
     * @see Filter#destroy()
     */
    public void destroy() {
        code = null;
    }
}
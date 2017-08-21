package by.mozgo.craps.filter;


import by.mozgo.craps.StringConstant;
import by.mozgo.craps.services.locale.LocaleLogic;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Locale;

/**
 * Servlet Filter implementation class SetLocaleFilter sets
 * session locale based on cookies or browser settings.
 * Changes session locale if new locale came from request.
 *
 * @author Mozgo Andrei
 */
@WebFilter(urlPatterns = {"/*"})
public class SetLocaleFilter implements Filter {
    private static final String COOKIE_LOCALE = "locale";

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
        HttpSession session = ((HttpServletRequest) request).getSession();
        //gets locale from request
        String localeStr = request.getParameter(StringConstant.ATTRIBUTE_LOCALE);
        Locale locale;

        if (localeStr != null) {
            locale = LocaleLogic.getLocaleByString(localeStr);
            session.setAttribute(StringConstant.ATTRIBUTE_LOCALE, locale);
            ((HttpServletResponse) response).addCookie(new Cookie(COOKIE_LOCALE, localeStr));
        } else {
            //if no locale at request checks locale from session
            locale = (Locale) session.getAttribute(StringConstant.ATTRIBUTE_LOCALE);
            if (locale == null) {

                //if no locale at request and session - searches locale cookies
                Cookie[] cookies = ((HttpServletRequest) request).getCookies();

                if (cookies != null) {
                    for (Cookie cookie : cookies) {
                        if (cookie.getName().equals(COOKIE_LOCALE)) {
                            localeStr = cookie.getValue();
                            locale = LocaleLogic.getLocaleByString(localeStr);
                            session.setAttribute(StringConstant.ATTRIBUTE_LOCALE, locale);
                            break;
                        }
                    }
                }

                if (localeStr == null) {
                    //if no cookie - gets locale from user browser
                    String browserLocale = request.getLocale().getLanguage();
                    if (browserLocale != null) {
                        localeStr = browserLocale;
                        locale = LocaleLogic.getLocaleByString(localeStr);
                        session.setAttribute(StringConstant.ATTRIBUTE_LOCALE, locale);
                        ((HttpServletResponse) response).addCookie(new Cookie(COOKIE_LOCALE, localeStr));
                    }
                }
            }
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
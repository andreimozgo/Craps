package by.mozgo.craps.filter;


import by.mozgo.craps.command.CrapsConstant;
import by.mozgo.craps.command.LocaleLogic;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Locale;

/**
 * Servlet Filter implementation class SetLocaleFilter
 * set session lovale based on cookies or browser settings if it is supported. Else set default.
 */
@WebFilter(urlPatterns = {"/*"})
public class SetLocaleFilter implements Filter {
    private static final String COOKIE_LOCALE = "locale";

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpSession session = ((HttpServletRequest) request).getSession();
        //get locale from request
        String localeStr = request.getParameter(CrapsConstant.ATTRIBUTE_LOCALE);
        Locale locale;

        if (localeStr != null) {
            locale = LocaleLogic.getLocaleByString(localeStr);
            session.setAttribute(CrapsConstant.ATTRIBUTE_LOCALE, locale);
            ((HttpServletResponse) response).addCookie(new Cookie(CrapsConstant.ATTRIBUTE_LOCALE, localeStr));
        } else {
            //if no locale at request get locale from session
            locale = (Locale) session.getAttribute(COOKIE_LOCALE);
            if (locale == null) {

                //if no locale at request and session search locale cookies
                Cookie[] cookies = ((HttpServletRequest) request).getCookies();

                if (cookies != null) {
                    for (Cookie cookie : cookies) {
                        if (cookie.getName().equals(CrapsConstant.COOKIE_LOCALE)) {
                            localeStr = cookie.getValue();
                            locale = LocaleLogic.getLocaleByString(localeStr);
                            session.setAttribute(CrapsConstant.ATTRIBUTE_LOCALE, locale);
                            break;
                        }
                    }
                }

                if (localeStr == null) {
                    //if no cookie - get preferred Locale for the player
                    String browserLocale = request.getLocale().getLanguage();
                    if (browserLocale != null) {
                        localeStr = browserLocale;
                        locale = LocaleLogic.getLocaleByString(localeStr);
                        session.setAttribute(CrapsConstant.ATTRIBUTE_LOCALE, locale);
                        ((HttpServletResponse) response).addCookie(new Cookie(CrapsConstant.ATTRIBUTE_LOCALE, localeStr));
                    }
                }
            }
        }
        chain.doFilter(request, response);
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void destroy() {
    }
}

package by.mozgo.craps.command.user;

import by.mozgo.craps.command.*;
import by.mozgo.craps.entity.User;
import by.mozgo.craps.manager.AvatarManager;
import by.mozgo.craps.services.impl.UserServiceImpl;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;
import java.io.IOException;
import java.util.Locale;
import java.util.regex.Pattern;

import static by.mozgo.craps.command.ActionResult.ActionType.FORWARD;
import static by.mozgo.craps.command.ActionResult.ActionType.REDIRECT;

public class AddRegistrationCommand implements ActionCommand {
    private static final Logger LOG = LogManager.getLogger();
    private static final String EMAIL_PATTERN = "\\p{Upper}[^.]+\\.";
    @Override
    public ActionResult execute(HttpServletRequest request) {
        UserServiceImpl userService = UserServiceImpl.getInstance();
        Locale locale = (Locale) request.getSession().getAttribute(StringConstant.ATTRIBUTE_LOCALE);
        boolean validation = true;
        ActionResult result = null;
        String page = null;

        String email = request.getParameter("email");
        email = email.toLowerCase();
        email = email.trim();
        if (!Pattern.compile(EMAIL_PATTERN).matcher(email).find()) {
            validation = false;
            request.setAttribute("registrationResultMessage", MessageManager.getProperty("registration.bademail", locale));
        }

        String username = request.getParameter("username");

        page = ConfigurationManager.getProperty("path.page.registration");
        request.setAttribute("email", email);
        request.setAttribute("username", username);
        result = new ActionResult(FORWARD, page);

        if (validation) {
            if (userService.findUserByEmail(email) == null) {
                Part part = null;
                try {
                    part = request.getPart("photo");
                } catch (IOException e) {
                    LOG.log(Level.ERROR, "Unable to save file: {}", e);
                } catch (ServletException e) {
                    LOG.log(Level.ERROR, "Unable to save file: {}", e);
                } catch (IllegalStateException e) {
                    LOG.log(Level.WARN, "File is too big: {}", e, e);
                    request.setAttribute("registrationResultMessage", MessageManager.getProperty("registration.error.bigfile", locale));
                    page = ConfigurationManager.getProperty("path.page.registration");
                    request.setAttribute("email", email);
                    request.setAttribute("username", username);
                    result = new ActionResult(FORWARD, page);
                }
                if (result == null) {
                    String password = request.getParameter("pwd1");
                    User user = new User();
                    user.setEmail(email);
                    user.setPassword(password);
                    user.setUsername(username);
                    userService.create(user);
                    LOG.log(Level.INFO, "New registration added successfully");
                    user = userService.findUserByEmail(user.getEmail());
                    AvatarManager uploader = new AvatarManager(request.getServletContext());
                    uploader.uploadPhoto(part, user.getId());
                    HttpSession session = request.getSession();
                    session.setAttribute("user", user);
                    page = ConfigurationManager.getProperty("path.page.registered");
                    result = new ActionResult(REDIRECT, page);
                }
            } else {
                request.setAttribute("registrationResultMessage", MessageManager.getProperty("registration.failemail", locale));
            }
        }

        return result;
    }
}
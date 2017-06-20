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
import javax.servlet.http.Part;
import java.io.IOException;
import java.util.Locale;

import static by.mozgo.craps.command.ActionResult.ActionType.FORWARD;
import static by.mozgo.craps.command.ActionResult.ActionType.REDIRECT;

public class AddRegistrationCommand implements ActionCommand {
    private static final Logger LOG = LogManager.getLogger();

    public ActionResult execute(HttpServletRequest request) {
        UserServiceImpl userService = UserServiceImpl.getInstance();
        ActionResult result = null;
        String page = null;
        String email = request.getParameter("email");
        Locale locale = (Locale) request.getSession().getAttribute(StringConstant.ATTRIBUTE_LOCALE);
        String username = request.getParameter("username");

        if (userService.findUserByEmail(email) == null) {
            Part part = null;
            try {
                part = request.getPart("photo");
            } catch (IOException e) {
                LOG.log(Level.ERROR, "Unable to save file: {}", e);
            } catch (ServletException e) {
                LOG.log(Level.ERROR, "Unable to save file: {}", e);
            } catch (IllegalStateException e) {
                LOG.log(Level.WARN, "File is too big: {}", e);
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
                userService.createOrUpdate(user);
                user = userService.findUserByEmail(user.getEmail());

                AvatarManager uploader = new AvatarManager(request.getServletContext());
                uploader.uploadPhoto(part, user.getId());
                LOG.log(Level.INFO, "New registration added successfully");
                page = ConfigurationManager.getProperty("path.page.registered");
                result = new ActionResult(REDIRECT, page);
            }
        } else {
            request.setAttribute("registrationResultMessage", MessageManager.getProperty("registration.failemail", locale));
            page = ConfigurationManager.getProperty("path.page.registration");
            request.setAttribute("email", email);
            request.setAttribute("username", username);
            result = new ActionResult(FORWARD, page);
        }
        return result;
    }
}

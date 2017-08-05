package by.mozgo.craps.command.user;

import by.mozgo.craps.command.*;
import by.mozgo.craps.entity.User;
import by.mozgo.craps.manager.AvatarManager;
import by.mozgo.craps.services.Validator;
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

import static by.mozgo.craps.command.ActionResult.ActionType.FORWARD;
import static by.mozgo.craps.command.ActionResult.ActionType.REDIRECT;

public class AddRegistrationCommand implements ActionCommand {
    private static final Logger LOG = LogManager.getLogger();

    @Override
    public ActionResult execute(HttpServletRequest request) {
        UserServiceImpl userService = UserServiceImpl.getInstance();
        Locale locale = (Locale) request.getSession().getAttribute(StringConstant.ATTRIBUTE_LOCALE);
        boolean validation = true;
        Part part = null;

        String email = request.getParameter("email");
        String pass1 = request.getParameter("pwd1");
        String pass2 = request.getParameter("pwd2");
        String name = request.getParameter("username");
        String age = request.getParameter("age");

        String page = ConfigurationManager.getProperty("path.page.registration");
        request.setAttribute("email", email);
        request.setAttribute("username", name);
        request.setAttribute("age", age);
        ActionResult result = new ActionResult(FORWARD, page);

        if (!Validator.validateEmail(email)) {
            validation = false;
            request.setAttribute("registrationResultMessage", MessageManager.getProperty("registration.bademail", locale));
        } else if (!Validator.validateRegistrationPassword(pass1)) {
            validation = false;
            request.setAttribute("registrationResultMessage", MessageManager.getProperty("registration.badpassword", locale));
        } else if (!pass1.equals(pass2)) {
            validation = false;
            request.setAttribute("registrationResultMessage", MessageManager.getProperty("registration.badpassword.repeat", locale));
        } else if (!Validator.validateName(name)) {
            validation = false;
            request.setAttribute("registrationResultMessage", MessageManager.getProperty("registration.error.badname", locale));
        } else if (!Validator.validateAge(age)) {
            validation = false;
            request.setAttribute("registrationResultMessage", MessageManager.getProperty("registration.error.age", locale));
        } else if (userService.findUserByEmail(email) != null) {
            request.setAttribute("registrationResultMessage", MessageManager.getProperty("registration.failemail", locale));
            validation = false;
        } else {
            try {
                part = request.getPart("photo");
            } catch (IOException e) {
                LOG.log(Level.ERROR, "Unable to save file: {}", e);
            } catch (ServletException e) {
                LOG.log(Level.ERROR, "Unable to save file: {}", e);
            } catch (IllegalStateException e) {
                LOG.log(Level.WARN, "File is too big: {}", e, e);
                request.setAttribute("registrationResultMessage", MessageManager.getProperty("registration.error.bigfile", locale));
                validation = false;
            }
        }

        if (validation) {
            User user = new User();
            user.setEmail(email);
            user.setPassword(pass1);
            user.setUsername(name);
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
        return result;
    }
}
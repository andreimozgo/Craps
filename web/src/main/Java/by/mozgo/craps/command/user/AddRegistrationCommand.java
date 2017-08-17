package by.mozgo.craps.command.user;

import by.mozgo.craps.command.*;
import by.mozgo.craps.entity.User;
import by.mozgo.craps.manager.AvatarManager;
import by.mozgo.craps.services.UserService;
import by.mozgo.craps.services.impl.UserServiceImpl;
import by.mozgo.craps.services.validator.Validator;
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
    private static final String PASSWORD = "pwd1";
    private static final String PASSWORD_REPEAT = "pwd2";
    private static final String USERNAME = "username";
    private static final String AGE = "age";
    private static final String PHOTO = "photo";
    private static final Logger LOG = LogManager.getLogger();

    @Override
    public ActionResult execute(HttpServletRequest request) {
        UserService userService = UserServiceImpl.getInstance();
        Locale locale = (Locale) request.getSession().getAttribute(CrapsConstant.ATTRIBUTE_LOCALE);
        boolean validation = true;
        Part part = null;

        String email = request.getParameter(CrapsConstant.EMAIL);
        String pass1 = request.getParameter(PASSWORD);
        String pass2 = request.getParameter(PASSWORD_REPEAT);
        String name = request.getParameter(USERNAME);
        String age = request.getParameter(AGE);

        String page = ConfigurationManager.getProperty("path.page.registration");
        request.setAttribute(CrapsConstant.EMAIL, email);
        request.setAttribute(USERNAME, name);
        request.setAttribute(AGE, age);
        ActionResult result = new ActionResult(FORWARD, page);

        if (!Validator.validateEmail(email)) {
            validation = false;
            request.setAttribute("registrationResultMessage", MessageManager.getProperty("registration.bademail", locale));
        } else if (!Validator.validatePassword(pass1)) {
            validation = false;
            request.setAttribute("registrationResultMessage", MessageManager.getProperty("registration.badpassword", locale));
        } else if (!pass1.equals(pass2)) {
            validation = false;
            request.setAttribute("registrationResultMessage", MessageManager.getProperty("registration.error.repeatpwd", locale));
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
                part = request.getPart(PHOTO);
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
            session.setAttribute(CrapsConstant.USER, user);
            page = ConfigurationManager.getProperty("path.page.registered");
            result = new ActionResult(REDIRECT, page);
        }
        return result;
    }
}
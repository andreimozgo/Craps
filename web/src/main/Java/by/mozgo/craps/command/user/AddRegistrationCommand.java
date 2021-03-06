package by.mozgo.craps.command.user;

import by.mozgo.craps.command.*;
import by.mozgo.craps.entity.User;
import by.mozgo.craps.services.ServiceException;
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

import static by.mozgo.craps.StringConstant.*;
import static by.mozgo.craps.command.ActionResult.ActionType.FORWARD;
import static by.mozgo.craps.command.ActionResult.ActionType.REDIRECT;

/**
 * ActionCommand implementation.
 * Adds new user registration.
 *
 * @author Mozgo Andrei
 */
public class AddRegistrationCommand implements ActionCommand {
    private static final Logger LOG = LogManager.getLogger();
    private static final String PASSWORD = "pwd1";
    private static final String PASSWORD_REPEAT = "pwd2";
    private static final String USERNAME = "username";
    private static final String AGE = "age";
    private static final String PHOTO = "photo";

    @Override
    public ActionResult execute(HttpServletRequest request) {
        UserService userService = UserServiceImpl.getInstance();
        Locale locale = (Locale) request.getSession().getAttribute(ATTRIBUTE_LOCALE);
        boolean validation = true;
        Part part = null;

        String email = request.getParameter(EMAIL);
        String pass1 = request.getParameter(PASSWORD);
        String pass2 = request.getParameter(PASSWORD_REPEAT);
        String name = request.getParameter(USERNAME);
        String age = request.getParameter(AGE);
        String page;
        ActionResult result;

        try {
            if (!Validator.validateEmail(email)) {
                validation = false;
                request.setAttribute("registrationResultMessage",
                        MessageManager.getProperty("registration.bademail", locale));
            } else if (!Validator.validatePassword(pass1)) {
                validation = false;
                request.setAttribute("registrationResultMessage",
                        MessageManager.getProperty("registration.badpassword", locale));
            } else if (!pass1.equals(pass2)) {
                validation = false;
                request.setAttribute("registrationResultMessage",
                        MessageManager.getProperty("registration.error.repeatpwd", locale));
            } else if (!Validator.validateName(name)) {
                validation = false;
                request.setAttribute("registrationResultMessage",
                        MessageManager.getProperty("registration.error.badname", locale));
            } else if (!Validator.validateAge(age)) {
                validation = false;
                request.setAttribute("registrationResultMessage",
                        MessageManager.getProperty("registration.error.age", locale));
            } else if (userService.findUserByEmail(email) != null) {
                request.setAttribute("registrationResultMessage",
                        MessageManager.getProperty("registration.failemail", locale));
                validation = false;
            } else {
                try {
                    part = request.getPart(PHOTO);
                } catch (IOException e) {
                    LOG.log(Level.ERROR, "Unable to save file: {}", e);
                } catch (ServletException e) {
                    // no file
                } catch (IllegalStateException e) {
                    LOG.log(Level.WARN, "File is too big: {}", e, e);
                    request.setAttribute("registrationResultMessage",
                            MessageManager.getProperty("registration.error.bigfile", locale));
                    validation = false;
                }
                if (validation && part.getSize() > 0) {
                    if (!AvatarManager.checkFileExtension(part)) {
                        LOG.log(Level.WARN, "Incorrect file extension.");
                        request.setAttribute("registrationResultMessage",
                                MessageManager.getProperty("registration.error.extension", locale));
                        validation = false;
                    }
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
                if (part.getSize() > 0) {
                    AvatarManager uploader = new AvatarManager(request.getServletContext());
                    uploader.uploadAvatar(part, user.getId());
                }
                HttpSession session = request.getSession();
                session.setAttribute(USER, user);
                page = ConfigurationManager.getProperty("path.page.registered");
                result = new ActionResult(REDIRECT, page);
            } else {
                request.setAttribute(EMAIL, email);
                request.setAttribute(USERNAME, name);
                request.setAttribute(AGE, age);
                page = ConfigurationManager.getProperty("path.page.registration");
                result = new ActionResult(FORWARD, page);
            }
        } catch (ServiceException e) {
            LOG.log(Level.ERROR, "Error during adding registration.\n" + e.getMessage());
            page = ConfigurationManager.getProperty("path.page.error");
            result = new ActionResult(REDIRECT, page);
        }
        return result;
    }
}
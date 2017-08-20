package by.mozgo.craps.command.player;

import by.mozgo.craps.command.*;
import by.mozgo.craps.entity.User;
import by.mozgo.craps.services.ServiceException;
import by.mozgo.craps.services.UserService;
import by.mozgo.craps.services.impl.UserServiceImpl;
import by.mozgo.craps.services.validator.Validator;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Locale;

import static by.mozgo.craps.command.ActionResult.ActionType.FORWARD;

/**
 * Created by Andrei Mozgo. 2017.
 */
public class ChangePwdCommand implements ActionCommand {
    private static final Logger LOG = LogManager.getLogger();
    private static final String OLD_PASSWORD = "oldPwd";
    private static final String NEW_PASSWORD = "newPwd1";
    private static final String NEW_PASSWORD_REPEAT = "newPwd2";

    @Override
    public ActionResult execute(HttpServletRequest request) {
        UserService userService = UserServiceImpl.getInstance();
        HttpSession session = request.getSession();
        Locale locale = (Locale) session.getAttribute(CrapsConstant.ATTRIBUTE_LOCALE);
        User user = (User) session.getAttribute(CrapsConstant.USER);
        String oldPwd = request.getParameter(OLD_PASSWORD);
        String page = ConfigurationManager.getProperty("path.page.password");

        if (oldPwd != null) {
            if (Validator.validatePassword(oldPwd)) {
                oldPwd = oldPwd.trim();
                String newPwd1 = request.getParameter(NEW_PASSWORD);
                //if old pass is valid check new password
                if (Validator.validatePassword(newPwd1)) {
                    newPwd1 = newPwd1.trim();
                    String newPwd2 = request.getParameter(NEW_PASSWORD_REPEAT);
                    // if old and new passwords are valid check new password repeat
                    if (Validator.validatePassword(newPwd2)) {
                        newPwd2 = newPwd2.trim();
                        //if all entered passwords are valid check new pass and its repeat for equality
                        if (newPwd1.equals(newPwd2)) {
                            //if all entered passwords are valid and entered right we make equality check for old and new passwords
                            //we don't need to change password at DB if user entered equal old and new passwords
                            if (!oldPwd.equals(newPwd1)) {
                                //finally we check old password at DB
                                try {
                                    if (userService.checkUser(user.getEmail(), oldPwd.trim())) {
                                        //and set new one
                                        user.setPassword(newPwd1);
                                        userService.update(user);
                                        request.setAttribute("changePwdMessage", MessageManager.getProperty("changepwd.success", locale));
                                    } else {
                                        //if old pass doesn't equal to stored at DB one
                                        request.setAttribute("changePwdMessage", MessageManager.getProperty("changepwd.wrong", locale));
                                    }
                                } catch (ServiceException e) {
                                    LOG.log(Level.ERROR, "Unable to change password.\n" + e.getMessage());
                                    page = ConfigurationManager.getProperty("path.page.error");
                                }
                            } else {
                                //if old and new passwords are equal
                                request.setAttribute("changePwdMessage", MessageManager.getProperty("changepwd.equals", locale));
                            }
                        } else {
                            //if user repeated new password wrong
                            request.setAttribute("changePwdMessage", MessageManager.getProperty("changepwd.newpass2", locale));
                        }

                    } else {
                        //if user repeated new password incorrectly
                        request.setAttribute("changePwdMessage", MessageManager.getProperty("changepwd.newpass2", locale));
                    }
                } else {
                    //if user entered incorrect new password
                    request.setAttribute("changePwdMessage", MessageManager.getProperty("changepwd.bad", locale));
                }
            } else {
                //if user entered incorrect old password
                request.setAttribute("changePwdMessage", MessageManager.getProperty("changepwd.wrong", locale));
            }
        }
        return new ActionResult(FORWARD, page);
    }
}

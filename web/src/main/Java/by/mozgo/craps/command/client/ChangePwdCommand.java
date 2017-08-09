package by.mozgo.craps.command.client;

import by.mozgo.craps.command.*;
import by.mozgo.craps.entity.User;
import by.mozgo.craps.services.UserService;
import by.mozgo.craps.services.Validator;
import by.mozgo.craps.services.impl.UserServiceImpl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Locale;

import static by.mozgo.craps.command.ActionResult.ActionType.FORWARD;

/**
 * Created by Andrei Mozgo. 2017.
 */
public class ChangePwdCommand implements ActionCommand {
    @Override
    public ActionResult execute(HttpServletRequest request) {
        UserService userService = UserServiceImpl.getInstance();
        HttpSession session = request.getSession();
        Locale locale = (Locale) session.getAttribute(StringConstant.ATTRIBUTE_LOCALE);
        User user = (User) session.getAttribute("user");

        String oldPwd = request.getParameter("oldPwd");
        String newPwd = request.getParameter("newPwd");

        if (oldPwd != null) {
            if (userService.checkUser(user.getEmail(), oldPwd)) {
                if (Validator.validateNewPassword(newPwd)) {
                    user.setPassword(newPwd);
                    userService.update(user);
                    request.setAttribute("changePwdMessage", MessageManager.getProperty("changepwd.success", locale));
                } else {
                    request.setAttribute("changePwdMessage", MessageManager.getProperty("changepwd.bad", locale));
                }
            } else {
                request.setAttribute("changePwdMessage", MessageManager.getProperty("changepwd.wrong", locale));
            }
        } else {
            request.setAttribute("changePwdMessage", MessageManager.getProperty("changepwd.wrong", locale));
        }

        String page = ConfigurationManager.getProperty("path.page.password");
        return new ActionResult(FORWARD, page);
    }
}

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

        if (oldPwd != null) {
            if (userService.checkUser(user.getEmail(), oldPwd.trim())) {
                String newPwd1 = request.getParameter("newPwd1");
                if (Validator.validateNewPassword(newPwd1)) {
                    newPwd1 = newPwd1.trim();
                    String newPwd2 = request.getParameter("newPwd2").trim();
                    if(newPwd1.equals(newPwd2)) {
                        user.setPassword(newPwd1);
                        userService.update(user);
                        request.setAttribute("changePwdMessage", MessageManager.getProperty("changepwd.success", locale));
                    } else {
                        request.setAttribute("changePwdMessage", MessageManager.getProperty("changepwd.newpass2", locale));
                    }
                } else {
                    request.setAttribute("changePwdMessage", MessageManager.getProperty("changepwd.bad", locale));
                }
            } else {
                request.setAttribute("changePwdMessage", MessageManager.getProperty("changepwd.wrong", locale));
            }
        }

        String page = ConfigurationManager.getProperty("path.page.password");
        return new ActionResult(FORWARD, page);
    }
}

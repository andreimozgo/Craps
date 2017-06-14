package by.mozgo.craps.command.admin;

import by.mozgo.craps.command.ActionCommand;
import by.mozgo.craps.services.impl.UserServiceImpl;

import javax.servlet.http.HttpServletRequest;

public class DeleteUserCommand implements ActionCommand {

    public String execute(HttpServletRequest request) {
        UserServiceImpl userService = UserServiceImpl.getInstance();
        String page;

        int id = Integer.parseInt(request.getParameter("user_id"));
        userService.delete(id);

        page = new AdminPageCommand().execute(request);
        return page;
    }
}

package ua.kiev.mvovnianko.hospital.controller.impl.admin;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import ua.kiev.mvovnianko.hospital.controller.Command;
import ua.kiev.mvovnianko.hospital.entity.User;
import ua.kiev.mvovnianko.hospital.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

import static ua.kiev.mvovnianko.hospital.utils.UtilConstants.*;

public class DeleteUserCommand implements Command {

    private static final Logger LOGGER = LogManager.getLogger(DeleteUserCommand.class);

    private final UserService SERVICE;



    public DeleteUserCommand(UserService userService) {
        SERVICE = userService;
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        final String LANG = getLang(request);

        String email = request.getParameter("userEmail");

        String message = localize(USER_DELETED, LANG);

        if (email == null || email.isEmpty()) {
            message = localize(EMPTY_EMAIL, LANG);
            request.setAttribute("message", message);
            return ERROR_PAGE;
        }

        User user = SERVICE.getUserByEmail(email);

        if (user == null) {
            message = localize(CANT_FIND_USER_BY_LOGIN, LANG);
            request.setAttribute("message", message);
            return ERROR_PAGE;
        }

        try {

            SERVICE.deleteUserByEmail(user.getEmail());

        } catch (SQLException throwable) {
            message = localize(CANT_DELETE_USER, LANG);
            request.setAttribute("message", message);
            return ERROR_PAGE;
        }

        request.setAttribute("message", message);
        return CONFIRM_PAGE;
    }
}

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

/**
 * The {@code DeleteUserCommand} class is an implementation of
 * {@code Command} interface, that is responsible for deleting user with specified email.
 *
 */
public class DeleteUserCommand implements Command {

    private static final Logger LOGGER = LogManager.getLogger(DeleteUserCommand.class);

    private final UserService SERVICE;

    public DeleteUserCommand(UserService userService) {
        SERVICE = userService;
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        final String lang = getLang(request);

        String email = request.getParameter(EMAIL);

        String message = localize(USER_DELETED, lang);

        if (email == null || email.isEmpty()) {
            message = localize(EMPTY_EMAIL, lang);
            request.setAttribute(MESSAGE, message);
            return ERROR_PAGE;
        }

        User user = SERVICE.getUserByEmail(email);

        if (user == null) {
            message = localize(CANT_FIND_USER_BY_LOGIN, lang);
            request.setAttribute(MESSAGE, message);
            return ERROR_PAGE;
        }

        try {

            SERVICE.deleteUserByEmail(user.getEmail());
            LOGGER.info(USER + DELETED);

        } catch (SQLException throwable) {
            LOGGER.error("User delete error");
            message = localize(CANT_DELETE_USER, lang);
            request.setAttribute(MESSAGE, message);
            return ERROR_PAGE;
        }

        request.setAttribute(MESSAGE, message);
        return CONFIRM_PAGE;
    }
}

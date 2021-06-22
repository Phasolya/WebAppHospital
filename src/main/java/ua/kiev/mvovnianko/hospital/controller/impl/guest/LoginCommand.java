package ua.kiev.mvovnianko.hospital.controller.impl.guest;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import ua.kiev.mvovnianko.hospital.controller.Command;
import ua.kiev.mvovnianko.hospital.entity.User;
import ua.kiev.mvovnianko.hospital.entity.UserRole;
import ua.kiev.mvovnianko.hospital.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static ua.kiev.mvovnianko.hospital.utils.UtilConstants.*;

public class LoginCommand implements Command {

    private static final Logger LOGGER = LogManager.getLogger(LoginCommand.class);

    private final UserService SERVICE;

    public LoginCommand(UserService userService) {
        SERVICE = userService;
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        final String LANG = getLang(request);

        HttpSession session = request.getSession();

        String email = request.getParameter(EMAIL);
        String password = request.getParameter(PASSWORD);

        String message = null;

        if (email == null || password == null || email.isEmpty() || password.isEmpty()) {
            message = localize(EMPTY_LOGIN_PASSWORD, LANG);
            request.setAttribute(MESSAGE, message);
            return ERROR_PAGE;
        }

        User user = SERVICE.validateUser(email, password);

        if (user == null) {
            message = localize(CANT_FIND_USER_BY_LOGIN_PASSWORD, LANG);
            request.setAttribute(MESSAGE, message);
            return ERROR_PAGE;
        }

        UserRole userRole = user.getUserRole();

        session.setAttribute(USER, user);
        session.setAttribute(USER_ROLE, userRole);

        message = localize(WELCOME, LANG) + " " + localize(userRole.getName(), LANG);
        request.setAttribute(MESSAGE, message);
        return CONFIRM_PAGE;

    }
}
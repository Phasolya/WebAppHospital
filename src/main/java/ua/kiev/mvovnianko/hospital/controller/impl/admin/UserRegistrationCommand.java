package ua.kiev.mvovnianko.hospital.controller.impl.admin;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import ua.kiev.mvovnianko.hospital.controller.Command;
import ua.kiev.mvovnianko.hospital.entity.User;
import ua.kiev.mvovnianko.hospital.entity.UserRole;
import ua.kiev.mvovnianko.hospital.entity.builder.UserBuilder;
import ua.kiev.mvovnianko.hospital.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;
import java.time.LocalDate;

import static ua.kiev.mvovnianko.hospital.utils.UtilConstants.*;

public class UserRegistrationCommand implements Command {

    private static final Logger LOGGER = LogManager.getLogger(GetDoctorsCommand.class);

    private final UserService SERVICE;

    public UserRegistrationCommand(UserService userService) {
        SERVICE = userService;
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        final String LANG = getLang(request);

        HttpSession session = request.getSession();

        String message = localize(USER_REGISTRATION_COMPLETE, LANG);
        User user;

        try {

            user = validationData(request);

            SERVICE.createNewUser(user);


            if (user.getUserRole() == UserRole.DOCTOR){

                String doctorType = request.getParameter("doctorType");
                SERVICE.setDoctorType(user.getId(), doctorType);

            }


        } catch (ValidationDateException ex) {

            message = localize(ex.getMessage(), LANG);
            request.setAttribute("message", message);
            return ERROR_PAGE;

        } catch (SQLException ex) {

            message = ex.getMessage();
            request.setAttribute("message", message);
            return ERROR_PAGE;

        }

        request.setAttribute("message", message);
        return CONFIRM_PAGE;
    }

    private User validationData(HttpServletRequest request) throws ValidationDateException {

        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String fullName = request.getParameter("fullName");
        String birthDate = request.getParameter("birthDate");
        String userRoleStr = request.getParameter("userRole");
        String doctorTypeStr = request.getParameter("doctorType");

        if (email == null || password == null || email.isEmpty() || password.isEmpty()) {
            throw new ValidationDateException(EMPTY_LOGIN_PASSWORD);
        }
        if (!(email.matches(REGEX_EMAIL) || password.matches(REGEX_PASSWORD))) {

            throw new ValidationDateException(REGISTRATION_LOGIN_PASSWORD_ERROR);
        }

        if (fullName == null || fullName.isEmpty()) {

            throw new ValidationDateException(EMPTY_FULL_NAME);

        }
        if (!(fullName.matches(REGEX_FULL_NAME_EN) || fullName.matches(REGEX_FULL_NAME_RU))) {

            throw new ValidationDateException(WRONG_FULL_NAME);

        }

        UserRole userRole;

        try {

            userRole = UserRole.valueOf(userRoleStr);

        } catch (IllegalArgumentException ex) {

            throw new ValidationDateException(WRONG_USER_ROLE);

        }

        if (userRole == UserRole.DOCTOR && (doctorTypeStr == null || doctorTypeStr.isEmpty())) {

            throw new ValidationDateException(EMPTY_TYPE_DESCRIPTION);

        }

        return new UserBuilder()
                .buildEmail(email)
                .buildPassword(password)
                .buildFullName(fullName)
                .buildBirthDate(LocalDate.parse(birthDate))
                .buildUserRole(userRole)
                .build();

    }

    private static class ValidationDateException extends Exception {
        public ValidationDateException(String message) {
            super(message);
        }
    }
}
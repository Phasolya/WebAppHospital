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

/**
 * The {@code UserRegistrationCommand} class is an implementation of
 * {@code Command} interface, that is responsible for register new user.
 *
 */
public class UserRegistrationCommand implements Command {

    private static final Logger LOGGER = LogManager.getLogger(GetDoctorsCommand.class);

    private final UserService SERVICE;

    public UserRegistrationCommand(UserService userService) {
        SERVICE = userService;
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        String lang = getLang(request);

        HttpSession session = request.getSession();

        String message = localize(USER_REGISTRATION_COMPLETE, lang);
        User user;

        try {

            user = validationData(request);
            LOGGER.info("User data validated");
            SERVICE.createNewUser(user);
            LOGGER.info("User added to db");

            if (user.getUserRole() == UserRole.DOCTOR){

                String doctorType = request.getParameter(JSP_DOCTOR_TYPE);
                SERVICE.setDoctorType(user.getId(), doctorType);
                LOGGER.info("DoctorType added to db");

            }
        } catch (ValidationDateException ex) {
            LOGGER.error("new user add to db error");
            message = localize(ex.getMessage(), lang);
            request.setAttribute(MESSAGE, message);
            return ERROR_PAGE;

        } catch (SQLException ex) {
            LOGGER.error("User data not validated");
            message = ex.getMessage();
            request.setAttribute(MESSAGE, message);
            return ERROR_PAGE;
        }

        request.setAttribute(MESSAGE, message);
        return CONFIRM_PAGE;
    }

    private User validationData(HttpServletRequest request) throws ValidationDateException {

        String email = request.getParameter(EMAIL);
        String password = request.getParameter(PASSWORD);
        String fullName = request.getParameter(JSP_FULL_NAME );
        String birthDate = request.getParameter(BIRTH_DATE);
        String userRoleStr = request.getParameter(USER_ROLE);
        String doctorTypeStr = request.getParameter(DOCTOR_TYPE);

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
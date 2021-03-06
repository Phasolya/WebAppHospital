package ua.kiev.mvovnianko.hospital.controller.impl.common;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import ua.kiev.mvovnianko.hospital.controller.Command;
import ua.kiev.mvovnianko.hospital.entity.UserRole;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;

import static ua.kiev.mvovnianko.hospital.utils.UtilConstants.*;

/**
 * The {@code MainPageCommand} class is an implementation of
 * {@code Command} interface, that is responsible for getting main page according to userRole.
 *
 */
public class MainPageCommand implements Command {

    private static final Logger LOGGER = LogManager.getLogger(MainPageCommand.class);

    private static final HashMap<UserRole, String> PAGES;

    static {

        PAGES = new HashMap<>();

        PAGES.put(UserRole.ADMIN, ADMIN_PAGE);
        PAGES.put(UserRole.DOCTOR, DOCTOR_PAGE);
        PAGES.put(UserRole.NURSE, NURSE_PAGE);
        PAGES.put(UserRole.PATIENT, PATIENT_PAGE);
        PAGES.put(UserRole.GUEST, GUEST_PAGE);

    }


    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        LOGGER.debug("Command mainPage starts");

        HttpSession session = request.getSession();

        UserRole userRole = (UserRole)request.getSession().getAttribute(USER_ROLE);

        String forward = PAGES.getOrDefault(userRole, GUEST_PAGE);

        LOGGER.debug("Command mainPage finish");

        return forward;
    }
}

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

        HttpSession session = request.getSession();

        UserRole userRole = (UserRole)request.getSession().getAttribute("userRole");

        String forward = PAGES.getOrDefault(userRole, GUEST_PAGE);

        return forward;
    }
}

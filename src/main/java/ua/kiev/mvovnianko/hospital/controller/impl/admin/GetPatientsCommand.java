package ua.kiev.mvovnianko.hospital.controller.impl.admin;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import ua.kiev.mvovnianko.hospital.controller.Command;
import ua.kiev.mvovnianko.hospital.entity.User;
import ua.kiev.mvovnianko.hospital.entity.UserRole;
import ua.kiev.mvovnianko.hospital.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import static ua.kiev.mvovnianko.hospital.utils.UtilConstants.*;

public class GetPatientsCommand implements Command {

    private static final Logger LOGGER = LogManager.getLogger(GetPatientsCommand.class);

    private final UserService SERVICE;

    public GetPatientsCommand(UserService userService) {
        SERVICE = userService;
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        final String LANG = getLang(request);

        List<User> patients;

        String sortBy = request.getParameter("patientsSortBy");

        String message = null;

        if (sortBy == null || sortBy.isEmpty()) {
            message = localize(EMPTY_SORT_PARAMETER, LANG);
            request.setAttribute("message", message);
            return ERROR_PAGE;
        }

        if (!(sortBy.equals("birth_date") || sortBy.equals("full_name") )) {
            message = localize(UNKNOWN_SORT_PARAMETER, LANG);
            request.setAttribute("message", message);
            return ERROR_PAGE;
        }

        try {

            patients = SERVICE.getSortedUsersByRoleId(UserRole.PATIENT.getId(), sortBy);

        } catch (SQLException throwable) {
            message = localize(SOMETHING_WENT_WRONG, LANG);
            request.setAttribute("message", message);
            return ERROR_PAGE;
        }

        String attributeSortBy = localize("by", LANG) + " " + localize(sortBy, LANG);
        request.setAttribute("patientsSortBy", attributeSortBy);
        request.setAttribute("patients", patients);
        return ADMIN_PAGE;
    }
}

package ua.kiev.mvovnianko.hospital.controller.impl.admin;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import ua.kiev.mvovnianko.hospital.controller.Command;
import ua.kiev.mvovnianko.hospital.entity.EntityDoctor;
import ua.kiev.mvovnianko.hospital.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import static ua.kiev.mvovnianko.hospital.utils.UtilConstants.*;

public class GetDoctorsCommand implements Command {

    private static final Logger LOGGER = LogManager.getLogger(GetDoctorsCommand.class);

    private final UserService SERVICE;

    public GetDoctorsCommand(UserService userService) {
        SERVICE = userService;
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        final String LANG = getLang(request);

        List<EntityDoctor> doctors;

        String docSortBy = request.getParameter("doctorsSortBy");

        String message = null;

        if (docSortBy == null || docSortBy.isEmpty()) {
            message = localize(EMPTY_SORT_PARAMETER, LANG);
            request.setAttribute("message", message);
            return ERROR_PAGE;
        }

        if (!(docSortBy.equals("full_name") || docSortBy.equals("doctor_type") || docSortBy.equals("patients_amount"))) {
            message = localize(UNKNOWN_SORT_PARAMETER, LANG);
            request.setAttribute("message", message);
            return ERROR_PAGE;
        }

        try {

            doctors = SERVICE.getSortedDoctors("all", docSortBy);

        } catch (SQLException throwable) {
            message = localize(SOMETHING_WENT_WRONG, LANG);
            request.setAttribute("message", message);
            return ERROR_PAGE;
        }

        String attributeSortBy = localize("by", LANG) + " " + localize(docSortBy, LANG);
        request.setAttribute("doctorsSortBy", attributeSortBy);
        request.setAttribute("doctors", doctors);
        return ADMIN_PAGE;
    }
}

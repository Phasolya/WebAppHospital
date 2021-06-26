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

/**
 * The {@code GetPatientsCommand} class is an implementation of
 * {@code Command} interface, that is responsible for getting patients list by dozes(pagination) in chosen order.
 */
public class GetPatientsCommand implements Command {

    private static final Logger LOGGER = LogManager.getLogger(GetPatientsCommand.class);

    private final UserService SERVICE;

    public GetPatientsCommand(UserService userService) {
        SERVICE = userService;
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        String lang = getLang(request);

        int page = FIRST_PAGE;
        int recordsPerPage = RECORDS_PER_PAGE;
        int noOfPages;
        if (request.getParameter(PATIENTS_CURRENT_PAGE) != null) {
            page = Integer.parseInt(request.getParameter(PATIENTS_CURRENT_PAGE));
        }

        List<User> patients;

        String sortBy = request.getParameter(PATIENTS_SORT_PARAMETER);

        String message;

        if (sortBy == null || sortBy.isEmpty()) {
            message = localize(EMPTY_SORT_PARAMETER, lang);
            request.setAttribute(MESSAGE, message);
            return ERROR_PAGE;
        }

        if (!(sortBy.equals(MYSQL_USER_BIRT_DATE) || sortBy.equals(MYSQL_USER_FULL_NAME) )) {
            message = localize(UNKNOWN_SORT_PARAMETER, lang);
            request.setAttribute(MESSAGE, message);
            return ERROR_PAGE;
        }

        try {

            patients = SERVICE.getUsersPageByRoleId(UserRole.PATIENT.getId(), sortBy, (page - 1) * recordsPerPage, recordsPerPage);
            LOGGER.info("patients page reads from db");
            int noOfRecords = SERVICE.countUsersByRoleId(UserRole.PATIENT.getId());
            LOGGER.info("get from db patients amount");
            noOfPages = (int) Math.ceil(noOfRecords * 1.0 / recordsPerPage);

        } catch (SQLException throwable) {
            LOGGER.error("patients page can't be read from db");
            message = localize(SOMETHING_WENT_WRONG, lang);
            request.setAttribute(MESSAGE, message);
            return ERROR_PAGE;
        }
        String attributeSortBy = localize(sortBy, lang);
        request.setAttribute(PATIENTS_SORT_PARAMETER, attributeSortBy);
        request.setAttribute("patientsPageSortBy", sortBy);
        request.setAttribute( PATIENTS, patients);
        request.setAttribute(PATIENTS_NUMBER_OF_PAGES, noOfPages);
        request.setAttribute(PATIENTS_CURRENT_PAGE, page);
        return ADMIN_PAGE;
    }
}

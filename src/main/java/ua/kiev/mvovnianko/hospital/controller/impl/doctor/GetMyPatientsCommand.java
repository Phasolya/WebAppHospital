package ua.kiev.mvovnianko.hospital.controller.impl.doctor;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import ua.kiev.mvovnianko.hospital.controller.Command;
import ua.kiev.mvovnianko.hospital.entity.User;
import ua.kiev.mvovnianko.hospital.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import static ua.kiev.mvovnianko.hospital.utils.UtilConstants.*;

/**
 * The {@code GetMyPatientsCommand} class is an implementation of
 * {@code Command} interface, that is responsible for getting patients list by dozes(pagination) in chosen order.
 *
 */
public class GetMyPatientsCommand implements Command {

    private static final Logger LOGGER = LogManager.getLogger(GetMyPatientsCommand.class);

    private final UserService SERVICE;

    public GetMyPatientsCommand(UserService userService) {
        SERVICE = userService;
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        LOGGER.debug("Command GetMyPatients starts");

        HttpSession session = request.getSession();

        User doctor = (User) session.getAttribute(USER);
        if (doctor == null) {
            request.setAttribute(MESSAGE, "please, login");
            return ERROR_PAGE;
        }

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

            patients = SERVICE.getPatientsPageByDoctorId(doctor.getId(), sortBy, (page - 1) * recordsPerPage, recordsPerPage);
            LOGGER.info("patients list get downloaded from db");
            int noOfRecords = SERVICE.countPatientsByDoctorId(doctor.getId());
            LOGGER.info("patients amount downloaded from db");
            noOfPages = (int) Math.ceil(noOfRecords * 1.0 / recordsPerPage);

        } catch (SQLException throwable) {
            LOGGER.error("patients downloaded error");
            message = localize(SOMETHING_WENT_WRONG, lang);
            request.setAttribute(MESSAGE, message);
            return ERROR_PAGE;
        }

        String attributeSortBy = localize(sortBy, lang);
        request.setAttribute(PATIENTS_SORT_PARAMETER, attributeSortBy);
        request.setAttribute("patientsPageSortBy", sortBy);
        request.setAttribute(PATIENTS, patients);
        request.setAttribute(PATIENTS_NUMBER_OF_PAGES, noOfPages);
        request.setAttribute(PATIENTS_CURRENT_PAGE, page);

        LOGGER.debug("Command GetMyPatients finished");
        return DOCTOR_PAGE;
    }
}


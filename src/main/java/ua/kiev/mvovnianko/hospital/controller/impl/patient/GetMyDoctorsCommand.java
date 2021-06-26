package ua.kiev.mvovnianko.hospital.controller.impl.patient;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import ua.kiev.mvovnianko.hospital.controller.Command;
import ua.kiev.mvovnianko.hospital.entity.EntityDoctor;
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
 * The {@code GetMyDoctorsCommand} class is an implementation of
 * {@code Command} interface, that is responsible for getting doctors list by dozes(pagination) in chosen order.
 */
public class GetMyDoctorsCommand implements Command {

    private static final Logger LOGGER = LogManager.getLogger(GetMyDoctorsCommand.class);

    private final UserService SERVICE;

    public GetMyDoctorsCommand(UserService userService) {
        SERVICE = userService;
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        HttpSession session = request.getSession();

        User patient = (User) session.getAttribute(USER);

        String lang = getLang(request);

        int page = FIRST_PAGE;
        int recordsPerPage = RECORDS_PER_PAGE;
        int noOfPages;
        if (request.getParameter(DOCTORS_CURRENT_PAGE) != null) {
            page = Integer.parseInt(request.getParameter(DOCTORS_CURRENT_PAGE));
        }

        List<EntityDoctor> doctors;

        String sortBy = request.getParameter(DOCTORS_SORT_PARAMETER);

        String message;

        if (sortBy == null || sortBy.isEmpty()) {
            message = localize(EMPTY_SORT_PARAMETER, lang);
            request.setAttribute(MESSAGE, message);
            return ERROR_PAGE;
        }

        if (!(sortBy.equals(MYSQL_USER_FULL_NAME) || sortBy.equals(MYSQL_DOCTOR_TYPE))) {
            message = localize(UNKNOWN_SORT_PARAMETER, lang);
            request.setAttribute(MESSAGE, message);
            return ERROR_PAGE;
        }

        try {

            doctors = SERVICE.getDoctorsPageByPatientId(patient.getId(), sortBy, (page - 1) * recordsPerPage, recordsPerPage);
            LOGGER.info("doctors page reads from db");
            int noOfRecords = SERVICE.countDoctorsByPatientId(patient.getId());
            LOGGER.info("get from db doctors amount");
            noOfPages = (int) Math.ceil(noOfRecords * 1.0 / recordsPerPage);

        } catch (SQLException throwable) {
            LOGGER.error("doctors page can't be read from db");
            message = localize(SOMETHING_WENT_WRONG, lang);
            request.setAttribute(MESSAGE, message);
            return ERROR_PAGE;
        }
        String attributeSortBy = localize(sortBy, lang);
        request.setAttribute(DOCTORS_SORT_PARAMETER, attributeSortBy);
        request.setAttribute("doctorsPageSortBy", sortBy);
        request.setAttribute(JSP_DOCTORS, doctors);
        request.setAttribute(JSP_DOCTORS_NO_OF_PAGES, noOfPages);
        request.setAttribute(DOCTORS_CURRENT_PAGE, page);
        return PATIENT_PAGE;
    }
}

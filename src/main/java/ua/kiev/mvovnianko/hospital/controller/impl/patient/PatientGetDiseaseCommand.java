package ua.kiev.mvovnianko.hospital.controller.impl.patient;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import ua.kiev.mvovnianko.hospital.controller.Command;
import ua.kiev.mvovnianko.hospital.entity.Disease;
import ua.kiev.mvovnianko.hospital.entity.User;
import ua.kiev.mvovnianko.hospital.service.DiseaseService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import static ua.kiev.mvovnianko.hospital.utils.UtilConstants.*;

/**
 * The {@code GetDiseaseCommand} class is an implementation of
 * {@code Command} interface, that is responsible for getting diseases list by dozes(pagination) in chosen order.
 *
 */
public class PatientGetDiseaseCommand implements Command {

    private static final Logger LOGGER = LogManager.getLogger(ua.kiev.mvovnianko.hospital.controller.impl.doctor.GetDiseaseCommand.class);

    private final DiseaseService SERVICE;

    public PatientGetDiseaseCommand (DiseaseService diseaseService) {
        SERVICE = diseaseService;
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        LOGGER.debug("Command PatientGetDisease starts");

        String lang = getLang(request);

        HttpSession session = request.getSession();

        User patient = (User) session.getAttribute(USER);

        int page = FIRST_PAGE;
        int recordsPerPage = RECORDS_PER_PAGE;
        int noOfPages;
        if (request.getParameter(DISEASES_CURRENT_PAGE) != null) {
            page = Integer.parseInt(request.getParameter(DISEASES_CURRENT_PAGE));
        }

        List<Disease> diseases;

        String sortBy = request.getParameter(DISEASES_SORT_PARAMETER);

        String message;

        if (sortBy == null || sortBy.isEmpty()) {
            message = localize(EMPTY_SORT_PARAMETER, lang);
            request.setAttribute(MESSAGE, message);
            return ERROR_PAGE;
        }

        if (!(sortBy.equals(MYSQL_NAME) || sortBy.equals(MYSQL_USER_FULL_NAME))) {
            message = localize(UNKNOWN_SORT_PARAMETER, lang);
            request.setAttribute(MESSAGE, message);
            return ERROR_PAGE;
        }

        try {

            diseases = SERVICE.getDiseasesPageByPatientId(sortBy, patient.getId(), (page - 1) * recordsPerPage, recordsPerPage);
            LOGGER.info("diseases page reads from db");
            int noOfRecords = SERVICE.countDiseasesByPatientId(patient.getId());
            LOGGER.info("get from db diseases amount");
            noOfPages = (int) Math.ceil(noOfRecords * 1.0 / recordsPerPage);

        } catch (SQLException throwable) {
            LOGGER.error("diseases page can't be read from db");
            message = localize(SOMETHING_WENT_WRONG, lang);
            request.setAttribute(MESSAGE, message);
            return ERROR_PAGE;
        }

        String attributeSortBy = localize(sortBy, lang);
        request.setAttribute(DISEASES_SORT_PARAMETER, attributeSortBy);
        request.setAttribute("diseasesPageSortBy", sortBy);
        request.setAttribute(JSP_DISEASES, diseases);
        request.setAttribute(DISEASES_NUMBER_OF_PAGES, noOfPages);
        request.setAttribute(DISEASES_CURRENT_PAGE, page);

        LOGGER.debug("Command PatientGetDisease finished");
        return PATIENT_PAGE;
    }
}

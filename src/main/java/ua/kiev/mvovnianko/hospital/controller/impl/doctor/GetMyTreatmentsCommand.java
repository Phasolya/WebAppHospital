package ua.kiev.mvovnianko.hospital.controller.impl.doctor;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import ua.kiev.mvovnianko.hospital.controller.Command;
import ua.kiev.mvovnianko.hospital.entity.Treatment;
import ua.kiev.mvovnianko.hospital.service.TreatmentService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import static ua.kiev.mvovnianko.hospital.utils.UtilConstants.*;

/**
 * The {@code GetMyTreatmentsCommand} class is an implementation of
 * {@code Command} interface, that is responsible for getting treatments list by dozes(pagination) in chosen order.
 *
 */
public class GetMyTreatmentsCommand implements Command {
    private static final Logger LOGGER = LogManager.getLogger(GetMyTreatmentsCommand.class);

    private final TreatmentService SERVICE;

    public GetMyTreatmentsCommand(TreatmentService treatmentService) {
        SERVICE = treatmentService;
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        LOGGER.debug("Command GetMyTreatments starts");

        String lang = getLang(request);

        int page = FIRST_PAGE;
        int recordsPerPage = RECORDS_PER_PAGE;
        int noOfPages;
        if (request.getParameter(TREATMENTS_CURRENT_PAGE) != null) {
            page = Integer.parseInt(request.getParameter(TREATMENTS_CURRENT_PAGE));
        }

        List<Treatment> treatments;

        String sortBy = request.getParameter(TREATMENTS_SORT_PARAMETER);

        String message;

        if (sortBy == null || sortBy.isEmpty()) {
            message = localize(EMPTY_SORT_PARAMETER, lang);
            request.setAttribute(MESSAGE, message);
            return ERROR_PAGE;
        }

        if (!(sortBy.equals(MYSQL_PATIENTS_LOGIN) || sortBy.equals(MYSQL_DISEASE_NAME) || sortBy.equals(MYSQL_TREATMENT_TYPE_NAME))) {
            message = localize(UNKNOWN_SORT_PARAMETER, lang);
            request.setAttribute(MESSAGE, message);
            return ERROR_PAGE;
        }

        try {

            treatments = SERVICE.getTreatmentsPage(sortBy, (page - 1) * recordsPerPage, recordsPerPage);
            LOGGER.info("treatments list get downloaded from db");
            int noOfRecords = SERVICE.countTreatments();
            LOGGER.info("treatments amount downloaded from db");
            noOfPages = (int) Math.ceil(noOfRecords * 1.0 / recordsPerPage);

        } catch (SQLException throwable) {
            LOGGER.error("treatments downloaded error");
            message = localize(SOMETHING_WENT_WRONG, lang);
            request.setAttribute(MESSAGE, message);
            return ERROR_PAGE;
        }

        String attributeSortBy = localize(sortBy, lang);
        request.setAttribute(TREATMENTS_SORT_PARAMETER, attributeSortBy);
        request.setAttribute("treatmentsPageSortBy", sortBy);
        request.setAttribute(JSP_TREATMENTS, treatments);
        request.setAttribute(TREATMENTS_NO_OF_PAGES, noOfPages);
        request.setAttribute(TREATMENTS_CURRENT_PAGE, page);

        LOGGER.debug("Command GetMyTreatments finished");
        return DOCTOR_PAGE;
    }
}

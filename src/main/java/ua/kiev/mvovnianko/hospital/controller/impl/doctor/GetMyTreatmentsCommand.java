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

public class GetMyTreatmentsCommand implements Command {
    private static final Logger LOGGER = LogManager.getLogger(GetMyTreatmentsCommand.class);

    private final TreatmentService SERVICE;

    public GetMyTreatmentsCommand(TreatmentService treatmentService) {
        SERVICE = treatmentService;
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        final String LANG = getLang(request);

        int page = 1;
        int recordsPerPage = 5;
        int noOfPages;
        if (request.getParameter(TREATMENTS_CURRENT_PAGE) != null) {
            page = Integer.parseInt(request.getParameter(TREATMENTS_CURRENT_PAGE));
        }

        List<Treatment> treatments;

        String sortBy = request.getParameter(TREATMENTS_SORT_PARAMETER);

        String message = null;

        if (sortBy == null || sortBy.isEmpty()) {
            message = localize(EMPTY_SORT_PARAMETER, LANG);
            request.setAttribute(MESSAGE, message);
            return ERROR_PAGE;
        }

        if (!(sortBy.equals("patients_login") || sortBy.equals("disease_name") || sortBy.equals("treatment_type_name"))) {
            message = localize(UNKNOWN_SORT_PARAMETER, LANG);
            request.setAttribute(MESSAGE, message);
            return ERROR_PAGE;
        }

        try {

            treatments = SERVICE.getTreatmentsPage(sortBy, (page - 1) * recordsPerPage, recordsPerPage);
            int noOfRecords = SERVICE.countTreatments();
            noOfPages = (int) Math.ceil(noOfRecords * 1.0 / recordsPerPage);

        } catch (SQLException throwable) {
            message = localize(SOMETHING_WENT_WRONG, LANG);
            request.setAttribute(MESSAGE, message);
            return ERROR_PAGE;
        }

        String attributeSortBy = localize(sortBy, LANG);
        request.setAttribute(TREATMENTS_SORT_PARAMETER, attributeSortBy);
        request.setAttribute("treatmentsPageSortBy", sortBy);
        request.setAttribute("treatments", treatments);
        request.setAttribute("treatmentsNoOfPages", noOfPages);
        request.setAttribute(TREATMENTS_CURRENT_PAGE, page);
        return DOCTOR_PAGE;
    }
}

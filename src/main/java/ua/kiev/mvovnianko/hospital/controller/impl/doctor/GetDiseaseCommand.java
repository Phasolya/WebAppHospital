package ua.kiev.mvovnianko.hospital.controller.impl.doctor;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import ua.kiev.mvovnianko.hospital.controller.Command;
import ua.kiev.mvovnianko.hospital.entity.Disease;
import ua.kiev.mvovnianko.hospital.entity.User;
import ua.kiev.mvovnianko.hospital.service.DiseaseService;
import ua.kiev.mvovnianko.hospital.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import static ua.kiev.mvovnianko.hospital.utils.UtilConstants.*;

public class GetDiseaseCommand implements Command {

    private static final Logger LOGGER = LogManager.getLogger(GetDiseaseCommand.class);

    private final DiseaseService SERVICE;

    public GetDiseaseCommand(DiseaseService diseaseService) {
        SERVICE = diseaseService;
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        final String LANG = getLang(request);

        List<Disease> diseases;

        String sortBy = request.getParameter("diseasesSortBy");

        String message = null;

        if (sortBy == null || sortBy.isEmpty()) {
            message = localize(EMPTY_SORT_PARAMETER, LANG);
            request.setAttribute("message", message);
            return ERROR_PAGE;
        }

        if (!(sortBy.equals("name") || sortBy.equals("full_name") )) {
            message = localize(UNKNOWN_SORT_PARAMETER, LANG);
            request.setAttribute("message", message);
            return ERROR_PAGE;
        }

        try {

            diseases = SERVICE.getAllDiseases(sortBy);

        } catch (SQLException throwable) {
            message = localize(SOMETHING_WENT_WRONG, LANG);
            request.setAttribute("message", message);
            return ERROR_PAGE;
        }

        String attributeSortBy = localize("by", LANG) + " " + localize(sortBy, LANG);
        request.setAttribute("diseasesSortBy", attributeSortBy);
        request.setAttribute("diseases", diseases);
        return DOCTOR_PAGE;
    }
}

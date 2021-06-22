package ua.kiev.mvovnianko.hospital.controller.impl.nurse;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import ua.kiev.mvovnianko.hospital.controller.Command;
import ua.kiev.mvovnianko.hospital.entity.Treatment;
import ua.kiev.mvovnianko.hospital.entity.TreatmentType;
import ua.kiev.mvovnianko.hospital.service.TreatmentService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

import static ua.kiev.mvovnianko.hospital.utils.UtilConstants.*;
import static ua.kiev.mvovnianko.hospital.utils.UtilConstants.CONFIRM_PAGE;

public class NurseDoTreatmentCommand implements Command {

    private static final Logger LOGGER = LogManager.getLogger(NurseDoTreatmentCommand.class);

    private final TreatmentService SERVICE;

    public NurseDoTreatmentCommand(TreatmentService treatmentService) {
        SERVICE = treatmentService;
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        final String LANG = getLang(request);

        String message = TREATMENT_DELETED;

        int treatmentId;

        try {

            treatmentId = Integer.parseInt(request.getParameter(TREATMENT_ID));

        } catch (NumberFormatException ex) {
            message = localize(INCORRECT_ID, LANG);
            request.setAttribute(MESSAGE, message);
            return ERROR_PAGE;
        }

        if (treatmentId <= 0) {
            message = localize(INCORRECT_ID, LANG);
            request.setAttribute(MESSAGE, message);
            return ERROR_PAGE;
        }

        try {

            Treatment treatment = SERVICE.getTreatmentById(treatmentId);

            if (treatment == null) {
                message = localize(TREATMENT_DONT_EXIST, LANG);
                request.setAttribute(MESSAGE, message);
                return ERROR_PAGE;
            }

            if (treatment.getTreatmentType() == TreatmentType.OPERATION) {
                message = localize(NURSE_CANT_DO_OPERATION, LANG);
                request.setAttribute(MESSAGE, message);
                return ERROR_PAGE;
            }

            SERVICE.deleteTreatmentById(treatmentId);

        } catch (SQLException throwable) {
            message = localize(CANT_DELETE_TREATMENT, LANG);
            request.setAttribute(MESSAGE, message);
            return ERROR_PAGE;
        }

        request.setAttribute(MESSAGE, message);
        return CONFIRM_PAGE;
    }
}

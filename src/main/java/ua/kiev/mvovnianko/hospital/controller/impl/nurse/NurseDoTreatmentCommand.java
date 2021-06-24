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

/**
 * The {@code NurseDoTreatmentCommand} class is an implementation of
 * {@code Command} interface, that is responsible for delete treatments.
 *
 */
public class NurseDoTreatmentCommand implements Command {

    private static final Logger LOGGER = LogManager.getLogger(NurseDoTreatmentCommand.class);

    private final TreatmentService SERVICE;

    public NurseDoTreatmentCommand(TreatmentService treatmentService) {
        SERVICE = treatmentService;
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        LOGGER.debug("Command NurseDoTreatment starts");

        final String lang = getLang(request);

        String message = TREATMENT_DELETED;

        int treatmentId;

        try {

            treatmentId = Integer.parseInt(request.getParameter(TREATMENT_ID));
            LOGGER.info("get treatmentId from HttpServletRequest");

        } catch (NumberFormatException ex) {
            LOGGER.error("treatmentId format error");
            message = localize(INCORRECT_ID, lang);
            request.setAttribute(MESSAGE, message);
            return ERROR_PAGE;
        }

        if (treatmentId <= 0) {
            message = localize(INCORRECT_ID, lang);
            request.setAttribute(MESSAGE, message);
            return ERROR_PAGE;
        }

        try {

            Treatment treatment = SERVICE.getTreatmentById(treatmentId);
            LOGGER.info("get treatment from db");

            if (treatment == null) {
                message = localize(TREATMENT_DONT_EXIST, lang);
                request.setAttribute(MESSAGE, message);
                return ERROR_PAGE;
            }

            if (treatment.getTreatmentType() == TreatmentType.OPERATION) {
                message = localize(NURSE_CANT_DO_OPERATION, lang);
                request.setAttribute(MESSAGE, message);
                return ERROR_PAGE;
            }

            SERVICE.deleteTreatmentById(treatmentId);
            LOGGER.info("delete treatment from db");

        } catch (SQLException throwable) {
            LOGGER.error("treatment get/delete from db error");
            message = localize(CANT_DELETE_TREATMENT, lang);
            request.setAttribute(MESSAGE, message);
            return ERROR_PAGE;
        }

        request.setAttribute(MESSAGE, message);
        LOGGER.debug("Command NurseDoTreatment finished");
        return CONFIRM_PAGE;
    }
}

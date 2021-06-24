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

import static ua.kiev.mvovnianko.hospital.utils.UtilConstants.*;

/**
 * The {@code DoDoctorsTreatmentCommand} class is an implementation of
 * {@code Command} interface, that is responsible for deleting treatments.
 *
 */
public class DoDoctorsTreatmentCommand implements Command {

    private static final Logger LOGGER = LogManager.getLogger(DoDoctorsTreatmentCommand.class);

    private final TreatmentService SERVICE;

    public DoDoctorsTreatmentCommand(TreatmentService treatmentService) {
        SERVICE = treatmentService;
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        LOGGER.debug("Command DoDoctorsTreatment starts");

        final String lang = getLang(request);

        String message = TREATMENT_DELETED;

        int treatmentId;

        try {

            treatmentId = Integer.parseInt(request.getParameter(TREATMENT_ID));
            LOGGER.info("treatmentId validated");

        } catch (NumberFormatException ex) {
            LOGGER.error("treatmentId validation error");
            message = localize(INCORRECT_ID, lang);
            request.setAttribute(MESSAGE, message);
            return ERROR_PAGE;
        }

        if (treatmentId <= 0) {
            LOGGER.error("treatmentId validation error");
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

            SERVICE.deleteTreatmentById(treatmentId);
            LOGGER.info("treatment deleted from db");

        } catch (SQLException throwable) {
            LOGGER.info("treatment delete from db error");
            message = localize(CANT_DELETE_TREATMENT, lang);
            request.setAttribute(MESSAGE, message);
            return ERROR_PAGE;
        }

        request.setAttribute(MESSAGE, message);
        LOGGER.debug("Command DoDoctorsTreatment finished");
        return CONFIRM_PAGE;
    }
}

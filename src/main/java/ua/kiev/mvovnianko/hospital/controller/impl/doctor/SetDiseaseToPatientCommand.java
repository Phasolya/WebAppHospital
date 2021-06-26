package ua.kiev.mvovnianko.hospital.controller.impl.doctor;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import ua.kiev.mvovnianko.hospital.controller.Command;
import ua.kiev.mvovnianko.hospital.controller.impl.admin.DocForPatientCommand;
import ua.kiev.mvovnianko.hospital.entity.Disease;
import ua.kiev.mvovnianko.hospital.entity.User;
import ua.kiev.mvovnianko.hospital.entity.UserRole;
import ua.kiev.mvovnianko.hospital.entity.builder.DiseaseBuilder;
import ua.kiev.mvovnianko.hospital.service.DiseaseService;
import ua.kiev.mvovnianko.hospital.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

import static ua.kiev.mvovnianko.hospital.utils.UtilConstants.*;
import static ua.kiev.mvovnianko.hospital.utils.UtilConstants.CONFIRM_PAGE;

/**
 * The {@code SetDiseaseToPatientCommand} class is an implementation of
 * {@code Command} interface, that is responsible for setting disease for patient.
 *
 */
public class SetDiseaseToPatientCommand implements Command {

    private static final Logger LOGGER = LogManager.getLogger(SetDiseaseToPatientCommand.class);

    private final DiseaseService DISEASE_SERVICE;

    private final UserService USER_SERVICE;

    public SetDiseaseToPatientCommand(DiseaseService diseaseService, UserService userService) {
        DISEASE_SERVICE = diseaseService;
        USER_SERVICE = userService;
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        LOGGER.debug("Command SetDiseaseToPatient starts");

        String lang = getLang(request);

        String message = localize(OPERATION_SUCCESS, lang);
        String patientLogin = request.getParameter( JSP_PATIENT_EMAIL);
        String diseaseName = request.getParameter(JSP_DISEASE_NAME);


        if (diseaseName == null || patientLogin == null || diseaseName.isEmpty() || patientLogin.isEmpty()) {
            message = localize(EMPTY_LOGIN_ERROR, lang);
            request.setAttribute(MESSAGE, message);
            return ERROR_PAGE;
        }

        User patient = USER_SERVICE.getUserByEmail(patientLogin);
        LOGGER.info("user downloaded from db");

        if (patient == null) {
            message = localize(CANT_FIND_USER_BY_LOGIN, lang);
            request.setAttribute(MESSAGE, message);
            return ERROR_PAGE;
        }

        Disease disease = new DiseaseBuilder()
                .buildName(diseaseName)
                .buildUser(patient)
                .build();

        try {

            DISEASE_SERVICE.createDisease(disease);
            LOGGER.info("disease added to db");

        } catch (SQLException throwable) {
            LOGGER.error("disease add to db error");
            message = localize(SOMETHING_WENT_WRONG, lang);
            request.setAttribute(MESSAGE, message);
            return ERROR_PAGE;
        }

        request.setAttribute(MESSAGE, message);
        LOGGER.debug("Command SetDiseaseToPatient finished");
        return CONFIRM_PAGE;
    }
}

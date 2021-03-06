package ua.kiev.mvovnianko.hospital.controller.impl.doctor;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import ua.kiev.mvovnianko.hospital.controller.Command;
import ua.kiev.mvovnianko.hospital.entity.Disease;
import ua.kiev.mvovnianko.hospital.entity.Treatment;
import ua.kiev.mvovnianko.hospital.entity.TreatmentType;
import ua.kiev.mvovnianko.hospital.entity.User;
import ua.kiev.mvovnianko.hospital.entity.builder.TreatmentBuilder;
import ua.kiev.mvovnianko.hospital.service.DiseaseService;
import ua.kiev.mvovnianko.hospital.service.TreatmentService;
import ua.kiev.mvovnianko.hospital.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import static ua.kiev.mvovnianko.hospital.utils.UtilConstants.*;

/**
 * The {@code SetTreatmentForPatientCommand} class is an implementation of
 * {@code Command} interface, that is responsible for setting treatment for patient.
 *
 */
public class SetTreatmentForPatientCommand implements Command {

    private static final Logger LOGGER = LogManager.getLogger(SetTreatmentForPatientCommand.class);

    private final DiseaseService DISEASE_SERVICE;

    private final UserService USER_SERVICE;

    private final TreatmentService TREATMENT_SERVICE;

    public SetTreatmentForPatientCommand(DiseaseService diseaseService, UserService userService, TreatmentService treatmentService) {
        DISEASE_SERVICE = diseaseService;
        USER_SERVICE = userService;
        TREATMENT_SERVICE = treatmentService;
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        LOGGER.debug("Command SetTreatmentForPatient starts");

        String lang = getLang(request);

        String message = localize(OPERATION_SUCCESS, lang);

        String patientEmail = request.getParameter(JSP_PATIENT_EMAIL);
        String diseaseName = request.getParameter(JSP_DISEASE_NAME);
        String treatmentName = request.getParameter(JSP_TREATMENT_NAME);
        String treatmentTypeName = request.getParameter(JSP_TREATMENT_TYPE_NAME);

        if (patientEmail == null || diseaseName == null || patientEmail.isEmpty() || diseaseName.isEmpty()) {
            message = localize(EMPTY_LOGIN_ERROR, lang);
            request.setAttribute(MESSAGE, message);
            return ERROR_PAGE;
        }

        if (treatmentName == null || treatmentTypeName == null || treatmentName.isEmpty() || treatmentTypeName.isEmpty()) {
            message = localize(EMPTY_LOGIN_ERROR, lang);
            request.setAttribute(MESSAGE, message);
            return ERROR_PAGE;
        }

        User patient = USER_SERVICE.getUserByEmail(patientEmail);
        LOGGER.info("get user from db");

        if (patient == null) {
            message = localize(CANT_FIND_USER_BY_LOGIN, lang);
            request.setAttribute(MESSAGE, message);
            return ERROR_PAGE;
        }

        Disease disease ;

        try {

            List<Disease> diseases = DISEASE_SERVICE.getDiseasesByUserId(patient.getId());
            LOGGER.info("get diseases from db");
            disease = diseases.stream().
                    filter(d -> d.getName().equals(diseaseName)).
                    findAny().orElse(null);

        } catch (SQLException throwable) {
            LOGGER.error("get diseases from db error");
            message = localize(SOMETHING_WENT_WRONG, lang);
            request.setAttribute(MESSAGE, message);
            return ERROR_PAGE;
        }

        if(disease == null){
            message = localize(CANT_FIND_DISEASE, lang);
            request.setAttribute(MESSAGE, message);
            return ERROR_PAGE;
        }

        Treatment treatment = new TreatmentBuilder()
                .buildName(treatmentName)
                .buildDTreatmentType(TreatmentType.valueOf(treatmentTypeName.toUpperCase()))
                .buildDisease(disease)
                .build();
        LOGGER.info("build treatment");

        try {

            TREATMENT_SERVICE.createTreatment(treatment);
            LOGGER.info("add treatment to db");

        } catch (SQLException throwable) {
            LOGGER.error("add treatment to db error");
            message = localize(SOMETHING_WENT_WRONG, lang);
            request.setAttribute(MESSAGE, message);
            return ERROR_PAGE;
        }

        request.setAttribute(MESSAGE, message);
        LOGGER.debug("Command SetTreatmentForPatient finished");
        return CONFIRM_PAGE;
    }
}

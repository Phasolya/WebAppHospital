package ua.kiev.mvovnianko.hospital.controller.impl.doctor;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import ua.kiev.mvovnianko.hospital.controller.Command;
import ua.kiev.mvovnianko.hospital.entity.Disease;
import ua.kiev.mvovnianko.hospital.entity.Treatment;
import ua.kiev.mvovnianko.hospital.entity.TreatmentType;
import ua.kiev.mvovnianko.hospital.entity.User;
import ua.kiev.mvovnianko.hospital.entity.builder.DiseaseBuilder;
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
import java.util.stream.Collectors;

import static ua.kiev.mvovnianko.hospital.utils.UtilConstants.*;

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

        final String LANG = getLang(request);

        String message = localize(OPERATION_SUCCESS, LANG);

        String patientLogin = request.getParameter("patientLogin");
        String diseaseName = request.getParameter("diseaseName");
        String treatmentName = request.getParameter("treatmentName");
        String treatmentTypeName = request.getParameter("treatmentTypeName");

        if (patientLogin == null || diseaseName == null || patientLogin.isEmpty() || diseaseName.isEmpty()) {
            message = localize(EMPTY_LOGIN_ERROR, LANG);
            request.setAttribute("message", message);
            return ERROR_PAGE;
        }

        if (treatmentName == null || treatmentTypeName == null || treatmentName.isEmpty() || treatmentTypeName.isEmpty()) {
            message = localize(EMPTY_LOGIN_ERROR, LANG);
            request.setAttribute("message", message);
            return ERROR_PAGE;
        }

        User patient = USER_SERVICE.getUserByEmail(patientLogin);

        if (patient == null) {
            message = localize(CANT_FIND_USER_BY_LOGIN, LANG);
            request.setAttribute("message", message);
            return ERROR_PAGE;
        }

        Disease disease ;

        try {

            List<Disease> diseases = DISEASE_SERVICE.getDiseasesByUserId(patient.getId());

            disease = diseases.stream().
                    filter(d -> d.getName().equals(diseaseName)).
                    findAny().orElse(null);

        } catch (SQLException throwable) {
            message = localize(SOMETHING_WENT_WRONG, LANG);
            request.setAttribute("message", message);
            return ERROR_PAGE;
        }


        if(disease == null){
            message = localize(CANT_FIND_DISEASE, LANG);
            request.setAttribute("message", message);
            return ERROR_PAGE;
        }


        Treatment treatment = new TreatmentBuilder()
                .buildName(treatmentName)
                .buildDTreatmentType(TreatmentType.valueOf(treatmentTypeName.toUpperCase()))
                .buildDisease(disease)
                .build();

        try {

            TREATMENT_SERVICE.createTreatment(treatment);

        } catch (SQLException throwable) {
            message = localize(SOMETHING_WENT_WRONG, LANG);
            request.setAttribute("message", message);
            return ERROR_PAGE;
        }

        request.setAttribute("message", message);
        return CONFIRM_PAGE;
    }
}

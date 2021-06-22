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

        final String LANG = getLang(request);

        String message = localize(OPERATION_SUCCESS, LANG);
        String patientLogin = request.getParameter("patientLogin");
        String diseaseName = request.getParameter("diseaseName");


        if (diseaseName == null || patientLogin == null || diseaseName.isEmpty() || patientLogin.isEmpty()) {
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

        Disease disease = new DiseaseBuilder()
                .buildName(diseaseName)
                .buildUser(patient)
                .build();

        try {

            DISEASE_SERVICE.createDisease(disease);

        } catch (SQLException throwable) {
            message = localize(SOMETHING_WENT_WRONG, LANG);
            request.setAttribute("message", message);
            return ERROR_PAGE;
        }

        request.setAttribute("message", message);
        return CONFIRM_PAGE;
    }
}

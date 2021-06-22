package ua.kiev.mvovnianko.hospital.controller.impl.admin;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import ua.kiev.mvovnianko.hospital.controller.Command;
import ua.kiev.mvovnianko.hospital.entity.User;
import ua.kiev.mvovnianko.hospital.entity.UserRole;
import ua.kiev.mvovnianko.hospital.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

import static ua.kiev.mvovnianko.hospital.utils.UtilConstants.*;

public class DocForPatientCommand implements Command {

    private static final Logger LOGGER = LogManager.getLogger(DocForPatientCommand.class);

    private final UserService SERVICE;

    public DocForPatientCommand(UserService userService) {
        SERVICE = userService;
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        final String LANG = getLang(request);

        String doctorLogin = request.getParameter("doctorLogin");
        String patientLogin = request.getParameter("patientLogin");

        String message = localize(PATIENT_ADD_FOR_DOC, LANG);

        if (doctorLogin == null || patientLogin == null || doctorLogin.isEmpty() || patientLogin.isEmpty()) {
            message = localize(EMPTY_LOGIN_ERROR, LANG);
            request.setAttribute("message", message);
            return ERROR_PAGE;
        }

        User doctor = SERVICE.getUserByEmail(doctorLogin);

        User patient = SERVICE.getUserByEmail(patientLogin);

        if (doctor == null || patient == null) {
            message = localize(CANT_FIND_USER_BY_LOGIN, LANG);
            request.setAttribute("message", message);
            return ERROR_PAGE;
        }

        UserRole doctorRole = doctor.getUserRole();
        UserRole patientRole = patient.getUserRole();

        if (doctorRole != UserRole.DOCTOR || patientRole != UserRole.PATIENT) {
            message = localize(WRONG_ROLE, LANG);
            request.setAttribute("message", message);
            return ERROR_PAGE;
        }

        try {

            SERVICE.setDoctorForPatient(doctor.getId(), patient.getId());

        } catch (SQLException throwable) {
            message = localize(DOCTOR_ALREADY_HAVE_PATIENT, LANG);
            request.setAttribute("message", message);
            return ERROR_PAGE;
        }

        request.setAttribute("message", message);
        return CONFIRM_PAGE;
    }
}

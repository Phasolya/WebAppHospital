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

/**
 * The {@code DocForPatientCommand} class is an implementation of
 * {@code Command} interface, that is responsible for setting doctor for patient by specified emails.
 *
 */
public class DocForPatientCommand implements Command {

    private static final Logger LOGGER = LogManager.getLogger(DocForPatientCommand.class);

    private final UserService SERVICE;

    public DocForPatientCommand(UserService userService) {
        SERVICE = userService;
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        String lang = getLang(request);

        String doctorEmail = request.getParameter(JSP_DOCTOR_EMAIL);
        String patientEmail = request.getParameter(JSP_PATIENT_EMAIL);

        String message = localize(PATIENT_ADD_FOR_DOC, lang);

        if (doctorEmail == null || patientEmail == null || doctorEmail.isEmpty() || patientEmail.isEmpty()) {
            message = localize(EMPTY_LOGIN_ERROR, lang);
            request.setAttribute(MESSAGE, message);
            return ERROR_PAGE;
        }

        User doctor = SERVICE.getUserByEmail(doctorEmail);
        User patient = SERVICE.getUserByEmail(patientEmail);

        if (doctor == null || patient == null) {
            message = localize(CANT_FIND_USER_BY_LOGIN, lang);
            request.setAttribute(MESSAGE, message);
            return ERROR_PAGE;
        }

        UserRole doctorRole = doctor.getUserRole();
        UserRole patientRole = patient.getUserRole();

        if (doctorRole != UserRole.DOCTOR || patientRole != UserRole.PATIENT) {
            message = localize(WRONG_ROLE, lang);
            request.setAttribute(MESSAGE, message);
            return ERROR_PAGE;
        }

        try {

            SERVICE.setDoctorForPatient(doctor.getId(), patient.getId());
            LOGGER.info(PATIENT_ADD_FOR_DOC);

        } catch (SQLException throwable) {
            LOGGER.error("set doctor for patient error");
            message = localize(DOCTOR_ALREADY_HAVE_PATIENT, lang);
            request.setAttribute(MESSAGE, message);
            return ERROR_PAGE;
        }

        request.setAttribute(MESSAGE, message);
        return CONFIRM_PAGE;
    }
}

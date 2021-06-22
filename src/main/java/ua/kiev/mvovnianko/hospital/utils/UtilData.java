package ua.kiev.mvovnianko.hospital.utils;

import ua.kiev.mvovnianko.hospital.controller.Command;
import ua.kiev.mvovnianko.hospital.controller.impl.admin.*;
import ua.kiev.mvovnianko.hospital.controller.impl.common.*;

import ua.kiev.mvovnianko.hospital.controller.impl.doctor.*;
import ua.kiev.mvovnianko.hospital.controller.impl.guest.LoginCommand;
import ua.kiev.mvovnianko.hospital.controller.impl.nurse.NurseDoTreatmentCommand;
import ua.kiev.mvovnianko.hospital.controller.impl.nurse.NurseGetTreatmentsCommand;
import ua.kiev.mvovnianko.hospital.dao.DiseaseDAO;
import ua.kiev.mvovnianko.hospital.dao.TreatmentDAO;
import ua.kiev.mvovnianko.hospital.dao.UserDAO;
import ua.kiev.mvovnianko.hospital.dao.impl.JDBCDiseaseDAO;
import ua.kiev.mvovnianko.hospital.dao.impl.JDBCTreatmentDAO;
import ua.kiev.mvovnianko.hospital.dao.impl.JDBCUserDAO;
import ua.kiev.mvovnianko.hospital.service.DiseaseService;
import ua.kiev.mvovnianko.hospital.service.TreatmentService;
import ua.kiev.mvovnianko.hospital.service.UserService;
import ua.kiev.mvovnianko.hospital.service.mysqlimpl.MySQLDiseaseService;
import ua.kiev.mvovnianko.hospital.service.mysqlimpl.MySQLTreatmentService;
import ua.kiev.mvovnianko.hospital.service.mysqlimpl.MySQLUserService;

import java.util.HashMap;
import java.util.Map;

/**
 * The {@code UtilData} class is responsible for holding all service collections including application data
 */
public class UtilData {

    public static final Map<String, Command> COMMANDS_MAP = new HashMap<>();


    static {

        DiseaseDAO diseaseDAO = new JDBCDiseaseDAO();
        TreatmentDAO treatmentDAO = new JDBCTreatmentDAO();
        UserDAO userDAO = new JDBCUserDAO();

        UserService userService = new MySQLUserService(userDAO);
        DiseaseService diseaseService = new MySQLDiseaseService(diseaseDAO, userService);
        TreatmentService treatmentService = new MySQLTreatmentService(treatmentDAO, diseaseService);

        COMMANDS_MAP.put("login", new LoginCommand(userService));
        COMMANDS_MAP.put("logout", new LogoutCommand());
        COMMANDS_MAP.put("mainPage", new MainPageCommand());
        COMMANDS_MAP.put("language", new LanguageCommand());
        COMMANDS_MAP.put("docForPatient", new DocForPatientCommand(userService));
        COMMANDS_MAP.put("deleteUser", new DeleteUserCommand(userService));
        COMMANDS_MAP.put("getRegistrationForm", new GetRegistrationFormCommand());
        COMMANDS_MAP.put("getDoctors", new GetDoctorsCommand(userService));

        COMMANDS_MAP.put("getPatients", new GetPatientsCommand(userService));
        COMMANDS_MAP.put("userRegistration", new UserRegistrationCommand(userService));
        COMMANDS_MAP.put("getMyPatients", new GetMyPatientsCommand(userService));
        COMMANDS_MAP.put("getDoctorsTreatments", new GetMyTreatmentsCommand(treatmentService));
        COMMANDS_MAP.put("doDoctorsTreatment", new DoDoctorsTreatmentCommand(treatmentService));
        COMMANDS_MAP.put("setDisease", new SetDiseaseToPatientCommand(diseaseService, userService));
        COMMANDS_MAP.put("setTreatment", new SetTreatmentForPatientCommand(diseaseService, userService, treatmentService));
        COMMANDS_MAP.put("getMyPatientsDiseases", new GetDiseaseCommand(diseaseService));

        COMMANDS_MAP.put("getNurseTreatments", new NurseGetTreatmentsCommand(treatmentService));
        COMMANDS_MAP.put("doNurseTreatment", new NurseDoTreatmentCommand(treatmentService));
    }

}

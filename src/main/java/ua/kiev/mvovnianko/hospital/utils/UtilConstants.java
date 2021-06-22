package ua.kiev.mvovnianko.hospital.utils;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 * The {@code UtilConstants} class is responsible for holding all the text constants of application
 */
public class UtilConstants {

    public static final String LANGUAGE = "language";
    public static final String TREATMENT_ID = "treatmentId";
    public static final String MESSAGE = "message";
    public static final String PAGE = "page";
    public static final String TREATMENTS_CURRENT_PAGE = "treatmentsCurrentPage";
    public static final String EMAIL = "email";
    public static final String PASSWORD = "password";
    public static final String TREATMENTS_SORT_PARAMETER = "treatmentsSortParameter";
    public static final String USER = "user";
    public static final String USER_ROLE = "userRole";

//    public static final String PAGE = "page";


    //regEx
    public static final String REGEX_EMAIL = "^^[\\w\\.]+@(\\w+\\.)+\\w{2,4}$";
    public static final String REGEX_PASSWORD = "^[A-Z]([A-Za-z0-9]{5,9})$";
    public static final String REGEX_FULL_NAME_EN = "^[A-Z][a-z]{0,10}[ ][A-Z][a-z]{0,10}[ ][A-Z][a-z]{0,10}$";
    public static final String REGEX_FULL_NAME_RU = "^[А-ЯЁ][а-яё]{0,10}[ ][А-ЯЁ][а-яё]{0,10}[ ][А-ЯЁ][а-яё]{0,10}$";

    //admin messages for LOCALIZE method
    public static final String USER_DELETED = "user.deleted";
    public static final String EMPTY_EMAIL = "user.email.cannot.be.empty";
    public static final String CANT_FIND_USER_BY_LOGIN = "cannot.find.user.with.such.login";
    public static final String CANT_DELETE_USER = "you.can't.delete.this.user";
    public static final String PATIENT_ADD_FOR_DOC = "patient.was.added.to.the.doctor";

    public static final String EMPTY_LOGIN_ERROR = "doctor.or.patient.login.cannot.be.empty";
    public static final String WRONG_ROLE = "doctor.or.patient.have.wrong.role";
    public static final String FAILED_OPERATION = "operation.was.failed";
    public static final String DOCTOR_ALREADY_HAVE_PATIENT = "doctor.already.have.this.patient";
    public static final String EMPTY_SORT_PARAMETER = "sort.parameter.can't.be.empty";
    public static final String INCORRECT_ID = "incorrect.id";
    public static final String OPERATION_SUCCESS = "operation.success";
    public static final String CANT_DELETE_TREATMENT = "cant.delete.treatment";
    public static final String TREATMENT_DONT_EXIST = "treatment.not.exist";
    public static final String NURSE_CANT_DO_OPERATION = "nurse.cant.do.operation";
    public static final String TREATMENT_DELETED = "treatment.deleted";
    public static final String UNKNOWN_SORT_PARAMETER = "unknown.sort.parameter";
    public static final String SOMETHING_WENT_WRONG = "something.went.wrong";
    public static final String CANT_FIND_DISEASE = "cant.find.disease";


    public static final String WELCOME = "we.glad.to.see.you.dear";
    public static final String ADMIN = "admin";
    public static final String DOCTOR = "doctor";
    public static final String NURSE = "nurse";
    public static final String PATIENT = "patient";


    public static final String USER_REGISTRATION_COMPLETE = "user.registration.complete";
    public static final String CANT_FIND_USER_BY_LOGIN_PASSWORD = "cannot.find.user.with.such.login.password";
    public static final String EMPTY_LOGIN_PASSWORD = "Login.or.password.cannot.be.empty";
    public static final String EMPTY_FULL_NAME = "fullName.cannot.be.empty";
    public static final String WRONG_FULL_NAME = "fullName.incorrect.input";
    public static final String WRONG_USER_ROLE = "user.role.incorrect.input";
    public static final String EMPTY_TYPE_DESCRIPTION = "type.description.for.doctors.cannot.be.empty";
    public static final String REGISTRATION_LOGIN_PASSWORD_ERROR = "email.password.condition";

    //common messages for LOCALIZE method
    public static final String WRONG_COMMAND = "no.such.command";
    public static final String HACKING_ATTEMPT = "hacking.attempt.prevented";


    //DAO messages for LOCALIZE method
    public static final String DISEASE_EXISTS = "disease.already.exists";
    public static final String TREATMENT_EXISTS = "treatment.already.exists";
    public static final String USER_EXISTS = "user.already.exists";
    public static final String DOC_FOR_PATIENT_EXISTS = "doctor.have.this.user.already";
    public static final String TYPE_FOR_DOCTOR_EXISTS = "doctor.already.have.type";

    //Service messages for LOCALIZE method
    public static final String COULD_NOT_LOAD_TREATMENTS = "could.not.load.treatments.from.db";
    public static final String COULD_NOT_LOAD_DISEASE = "could.not.load.disease.from.db";
    public static final String TREATMENT_CREATED = "treatment.created";
    public static final String USER_CREATED = "user.created";
    public static final String USER_NOT_VALIDATED = "user.not.validated";
    public static final String DOCTOR_TYPE_NOT_VALIDATED = "doctor.type.not.validated";
    public static final String COULD_NOT_LOAD_DOCTORS = "could.not.load.doctors.from.db";

    public static final String COULD_NOT_LOAD_USERS = "could.not.load.users.from.db";

    //==================================================================================================================
    //================================================command url patterns==============================================
    //==================================================================================================================
    public static final String ERROR_PAGE = "/jsp/common/error_page.jsp";
    public static final String CONFIRM_PAGE = "/jsp/common/confirm_page.jsp";
    public static final String REGISTRAION_FORM_PAGE = "/jsp/admin/registration_page.jsp";

    public static final String ADMIN_PAGE = "/jsp/admin/admin_page.jsp";
    public static final String DOCTOR_PAGE = "/jsp/doctor/doctor_page.jsp";
    public static final String NURSE_PAGE = "/jsp/nurse/nurse_page.jsp";
    public static final String PATIENT_PAGE = "/jsp/patient/patient_page.jsp";
    public static final String GUEST_PAGE = "/index.jsp";


    //db constants:
    public static final String MYSQL_DATA_BASE = "MySQL";
    public static final String MYSQL_COUNT = "count";

    public static final String CONTENT_TYPE = "application/json";
    public static final String ENCODING = "UTF-8";

    //==================================================================================================================
    //===================================================== SQL User ===================================================
    //==================================================================================================================
    public static final String SQL_ADD_NEW_USER =
            "INSERT INTO user (`email`, `password`, `full_name`, `birth_date`, `role_id`) VALUES (?, ?, ?, ?, ?)";
    public static final String SQL_SET_DOC_FOR_PATIENT = "INSERT INTO `mydb`.`doctor_has_patient` (`doctor_id`, `patient_id`) VALUES (?, ?);";
    public static final String SQL_VALIDATE_PASSWORD_USER = "SELECT * FROM user WHERE email=(?) AND password=(?);";
    public static final String SQL_GET_USER_BY_ID = "SELECT * FROM user WHERE id=(?);";
    public static final String SQL_GET_USER_BY_EMAIL = "SELECT * FROM user WHERE email=(?);";
    public static final String SQL_DELETE_USER_BY_EMAIL = "DELETE FROM user WHERE email=(?);";
    public static final String SQL_GET_TYPE_ID_BY_NAME = "SELECT id FROM `mydb`.`doctor_type` WHERE name=(?);";
    public static final String SQL_ADD_TYPE_ID_BY_NAME = "INSERT INTO `mydb`.`doctor_type` (`name`) VALUES (?);";
    public static final String SQL_SET_TYPE_FOR_DOCTOR = "INSERT INTO `mydb`.`doctor_has_type` (`doctor_id`, `doctor_type_id`) VALUES (?, ?);";


    public static final String SQL_TABLE_TREATMENT = "`mydb`.`treatment`";
    // for String#format() use!
    public static final String SQL__COUNT_ROWS = "SELECT COUNT(*) AS count FROM %s;";
    public static final String SQL__FIND_AND_SORT_USERS = "SELECT * FROM `mydb`.`user` WHERE role_id=? ORDER BY %s";
    public static final String SQL__FIND_AND_SORT_PATIENTS_BY_DOCTOR_ID = "SELECT u.* FROM user AS u, doctor_has_patient AS dhp " +
            "where u.id = patient_id and doctor_id = ? " +
            "ORDER BY %s;";
    public static final String SQL__FIND_AND_SORT_DOCTORS_BY_TYPE =
            "SELECT u.*, dt.name as doctor_type, count(dhp.patient_id) AS patients_amount " +
                    "FROM user AS u" +
                    "INNER JOIN doctor_has_type AS dht ON u.id = dht.doctor_id " +
                    "INNER JOIN doctor_type AS dt ON dht.doctor_type_id = dt.id " +
                    "LEFT JOIN doctor_has_patient AS dhp ON dhp.doctor_id = u.id " +
                    "WHERE dt.name = '%s' " +
                    "GROUP BY u.id " +
                    "ORDER BY %s;";
    public static final String SQL__FIND_AND_SORT_ALL_DOCTORS =
            "SELECT u.*, dt.name as doctor_type, count(dhp.patient_id) AS patients_amount " +
                    "FROM user AS u " +
                    "INNER JOIN doctor_has_type AS dht ON u.id = dht.doctor_id " +
                    "INNER JOIN doctor_type AS dt ON dht.doctor_type_id = dt.id " +
                    "LEFT JOIN doctor_has_patient AS dhp ON dhp.doctor_id = u.id " +
                    "GROUP BY u.id " +
                    "ORDER BY %s";


    //==================================================================================================================
    //===================================================== SQL Treatment ==============================================
    //==================================================================================================================
    public static final String SQL_GET_ALL_TREATMENTS = "SELECT * FROM `mydb`.`treatment`;";
    public static final String SQL_GET_TREATMENTS =
            "SELECT t.id, t.name, tt.name AS treatment_type_name, d.name AS disease_name, u.email AS patients_login, u.full_name AS patients_name " +
                    "FROM treatment AS t " +
                    "INNER JOIN treatment_type AS tt ON t.treatment_type_id = tt.id " +
                    "INNER JOIN disease AS d ON d.id = t.disease_id " +
                    "INNER JOIN user AS u ON u.id = d.user_id " +
                    "ORDER BY %s " +
                    "LIMIT ?, ?;";

    public static final String SQL_ADD_NEW_TREATMENT =
            "INSERT INTO `mydb`.`treatment` (`name`, `disease_id`, `treatment_type_id`) VALUES (?, ?, ?);";
    public static final String SQL_DELETE_TREATMENT_BY_ID =
            "DELETE FROM `mydb`.`treatment` WHERE id=(?);";
    public static final String SQL_GET_TREATMENTS_BY_DISEASE_ID =
            "SELECT * FROM `mydb`.`treatment` WHERE disease_id = (?);";
    public static final String SQL_GET_TREATMENT_BY_ID =
            "SELECT * FROM `mydb`.`treatment` WHERE id = (?);";
    public static final String SQL_GET_TREATMENTS_BY_TREATMENT_TYPE_ID =
            "SELECT * FROM `mydb`.`treatment` WHERE treatment_type_id = (?);";
    public static final String SQL_GET_SORTED_TREATMENTS =
            "SELECT t.id, t.name, tt.name AS treatment_type_name, d.name AS disease_name, u.email AS patients_login, u.full_name AS patients_name " +
                    "FROM treatment AS t " +
                    "INNER JOIN treatment_type AS tt ON t.treatment_type_id = tt.id " +
                    "INNER JOIN disease AS d ON d.id = t.disease_id " +
                    "INNER JOIN user AS u ON u.id = d.user_id " +
                    "ORDER BY %s;";
    public static final String SQL_GET_SORTED_DISEASES = "SELECT d.id, d.name, u.email AS email, u.full_name AS full_name " +
            "FROM mydb.disease AS d " +
            "INNER JOIN mydb.user AS u ON d.user_id =u.id " +
            "ORDER BY %s;";

    //==================================================================================================================
    //===================================================== SQL Disease ==============================================
    //==================================================================================================================
    public static final String SQL_ADD_NEW_DISEASE = "INSERT INTO `mydb`.`disease` (`name`, `user_id`) VALUES (?, ?);";
    public static final String SQL_GET_DISEASE_BY_USER_ID = "SELECT * FROM `mydb`.`disease` WHERE user_id = (?);";
    public static final String SQL_GET_DISEASE_BY_ID = "SELECT * FROM `mydb`.`disease` WHERE id = (?);";
    public static final String SQL_DELETE_DISEASE_BY_ID = "DELETE FROM `mydb`.`disease` WHERE id=(?);";


    public static String localize(String message, String language) {

        Locale locale;

        switch (language) {
            case "ru": {
                locale = new Locale("ru");
                break;
            }
            case "en": {
                locale = new Locale("en");
                break;
            }
            default: {
                locale = new Locale("en");
            }
        }

        ResourceBundle bundle = ResourceBundle.getBundle("messages", locale);
        String localizeMessage = bundle.getString(message);

        return localizeMessage;
    }
}






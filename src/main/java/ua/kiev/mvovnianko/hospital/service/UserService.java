package ua.kiev.mvovnianko.hospital.service;

import ua.kiev.mvovnianko.hospital.entity.EntityDoctor;
import ua.kiev.mvovnianko.hospital.entity.User;

import java.sql.SQLException;
import java.util.List;

/**
 * The {@code UserService} interface is responsible for processing business logic
 * with {@code User} entity class
 */
public interface UserService {

    /**
     * Responsible for creating new User instance and save it to DB.
     *
     * @param user is {@code User} instance to save.
     */
    void createNewUser(User user) throws SQLException;

    /**
     * Responsible for validating user by checking matching email to password.
     *
     * @param email the {@code String} parameter specifies email.
     * @param password the {@code String} parameter specifies password.
     * */
    User validateUser(String email, String password);

    /**
     * Responsible for getting the list of all Users by Role
     * which sorted by specified parameter.
     *
     * @param roleId the {@code int} parameter specifies User's role.
     * @param sortParameter   the {@code String} parameter specifies order.
     * @return {@code List<User>} list of Users in specified order.
     */
    List<User> getSortedUsersByRoleId(int roleId, String sortParameter) throws SQLException;

    /**
     * Responsible for getting the list of all Doctors by Doctor's type
     * which sorted by specified parameter.
     *
     * @param doctorType the {@code String} parameter specifies Doctor's type.
     * @param sortParameter   the {@code String} parameter specifies order.
     * @return {@code List<Doctor>} list of Doctors in specified order.
     */
    List<EntityDoctor> getSortedDoctors(String doctorType, String sortParameter) throws SQLException;

    User getUserById(int id);

    User getUserByEmail(String email);

    void deleteUserByEmail(String email) throws SQLException;

    void setDoctorForPatient(int doctorId, int patientId) throws SQLException;

    void setDoctorType(int doctorId, String doctorType) throws SQLException;

    List<User> getSortedPatientsByDoctorId(int DoctorId, String sortBy) throws SQLException;

    int countPatientsByDoctorId(int DoctorId) throws SQLException;

    List<User> getPatientsPageByDoctorId(int id, String sortBy, int i, int recordsPerPage);

    List<EntityDoctor> getDoctorsPage(String doctorType, String sortParameter, int startRow, int amount) throws SQLException;

    int countDoctors() throws SQLException;

    List<User> getSortedUsersPageByRoleId(int roleId, String sortParameter, int startRow, int amount) throws SQLException;

    int countUsersByRoleId(int id) throws SQLException;
}

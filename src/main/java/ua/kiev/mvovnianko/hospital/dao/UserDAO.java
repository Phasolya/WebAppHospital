package ua.kiev.mvovnianko.hospital.dao;

import ua.kiev.mvovnianko.hospital.entity.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * The {@code UserDao} interface is responsible for
 * connecting {@code User} entity class to DB
 *
 *  Created by M.Vovnianko on 13.06.2021.
 */
public interface UserDAO {

    /**
     * Responsible for saving new Station to DB
     *
     * @param user the instance of {@code User} entity class
     */
    void createNewUser(User user) throws SQLException;

    /**
     * Responsible for getting the ResultSet with all the data of specified User from DB.
     *
     * @param statement the {@code PreparedStatement} from {@code MySQLStationService}.
     * @param email the{@code String} parameter specifies the eMail of corresponding User.
     * @param password the{@code String} parameter specifies the password of corresponding User.
     * */
    ResultSet validateUser(PreparedStatement statement, String email, String password) throws SQLException;

    /**
     * Responsible for deleting User from DB
     *
     * @param userEmail the {@code String} parameter, specifies User.
     */
    void deleteUserByEmail(String userEmail) throws SQLException;

    /**
     * Responsible for getting ResultSet from DB containing all the data of all Users
     * due to the specified Role.
     *
     * @param roleId the {@code int} parameter, specifies User's role.
     * @param statement the {@code PreparedStatement} from {@code MySQLUserService}.
     */
    ResultSet getUsersByRoleId(PreparedStatement statement, int roleId) throws SQLException;

    ResultSet getEntityDoctors(PreparedStatement statement) throws SQLException;

    ResultSet getUserById(PreparedStatement statement, int userId) throws SQLException;

    ResultSet getUserByEmail(PreparedStatement statement, String email) throws SQLException;

    void setDoctorForPatient(int doctorId, int patientId) throws SQLException;

    void setTypeForDoctor(int doctorId, int typeId) throws SQLException;

    ResultSet getDoctorTypeByTypeName(PreparedStatement statement, String doctorType) throws SQLException;

    ResultSet addDoctorType(PreparedStatement statement, String doctorType) throws SQLException;

    ResultSet countPatientsByDoctorId(PreparedStatement statement, int doctorId) throws SQLException;

    ResultSet getPatients(PreparedStatement statement, int doctorId, int startRow, int amount) throws SQLException;

    ResultSet getEntityDoctorsPage(PreparedStatement statement, int startRow, int amount) throws SQLException;
    ResultSet getEntityDoctorsPageByPatientId(PreparedStatement statement, int patientId, int startRow, int amount) throws SQLException;

    int countUsersByRoleId(int id) throws SQLException;

}

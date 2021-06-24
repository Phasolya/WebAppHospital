package ua.kiev.mvovnianko.hospital.service.mysqlimpl;

import ua.kiev.mvovnianko.hospital.dao.UserDAO;
import ua.kiev.mvovnianko.hospital.dbConnector.MySQLConnectorManager;
import ua.kiev.mvovnianko.hospital.entity.EntityDoctor;
import ua.kiev.mvovnianko.hospital.entity.User;
import ua.kiev.mvovnianko.hospital.entity.UserRole;
import ua.kiev.mvovnianko.hospital.entity.builder.EntityDoctorBuilder;
import ua.kiev.mvovnianko.hospital.entity.builder.UserBuilder;
import ua.kiev.mvovnianko.hospital.service.UserService;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static ua.kiev.mvovnianko.hospital.utils.UtilConstants.*;

/**
 * This is the MySQL implementation of {@code UserService interface}
 */
public class MySQLUserService implements UserService {

    private static final Logger LOGGER = LogManager.getLogger(MySQLUserService.class);

    private final UserDAO USER_DAO;

    public MySQLUserService(UserDAO userDAO) {
        this.USER_DAO = userDAO;
    }

    @Override
    public void createNewUser(User user) throws SQLException {

        USER_DAO.createNewUser(user);

        LOGGER.info(USER_CREATED);
    }


    /**
     * Gets from {@code ValidateUserPasswordCommand} email and password.
     * checks the matching email to password.
     *
     * @param email    {@code String} from {@code ValidateUserPasswordCommand} command.
     * @param password {@code String} from {@code ValidateUserPasswordCommand} command.
     */
    @Override
    public User validateUser(String email, String password) {

        User user = null;

        try (Connection connection = MySQLConnectorManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_VALIDATE_PASSWORD_USER)) {

            MySQLConnectorManager.startTransaction(connection);

            ResultSet resultSet = USER_DAO.validateUser(statement, email, password);

            if (resultSet.next()) {

                user = getUserFromResultSet(resultSet);

            }

            MySQLConnectorManager.commitTransaction(connection);

            resultSet.close();

        } catch (SQLException e) {

            LOGGER.error(USER_NOT_VALIDATED);

        }

        return user;
    }

    /**
     * Responsible for creating {@code User} instance from ResultSet.
     *
     * @param resultSet the {@code ResultSet} from {@code validateUser()} method.
     * @return {@code User} instance.
     */
    private User getUserFromResultSet(ResultSet resultSet) throws SQLException {

        return new UserBuilder()
                .buildId(resultSet.getInt("id"))
                .buildEmail(resultSet.getString("email"))
                .buildPassword(resultSet.getString("password"))
                .buildFullName(resultSet.getString("full_name"))
                .buildBirthDate(LocalDate.parse(resultSet.getString("birth_date")))
                .buildUserRole(UserRole.getRole(resultSet.getInt("role_id")))
                .build();
    }

    /**
     * Responsible for getting the list of all Users by Role
     * which sorted by specified parameter.
     *
     * @param roleId        the {@code int} parameter specifies User's role.
     * @param sortParameter the {@code String} parameter specifies order.
     * @return {@code List<User>} list of Users in specified order.
     */
    @Override
    public List<User> getSortedUsersByRoleId(int roleId, String sortParameter) throws SQLException {
        List<User> users = new ArrayList<>();
        // sortParameter === birth_date OR full_name
        String sqlRequest = String.format(SQL__FIND_AND_SORT_USERS, sortParameter);

        try (Connection connection = MySQLConnectorManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(sqlRequest)) {

            MySQLConnectorManager.startTransaction(connection);

            ResultSet resultSet = USER_DAO.getUsersByRoleId(statement, roleId);

            while (resultSet.next()) {

                User user = getUserFromResultSet(resultSet);

                users.add(user);

            }

            MySQLConnectorManager.commitTransaction(connection);

            resultSet.close();

        } catch (SQLException e) {

            LOGGER.error(COULD_NOT_LOAD_USERS);
        }
        return users;
    }


    /**
     * Responsible for getting the list of all Doctors by Doctor's type
     * which sorted by specified parameter.
     *
     * @param doctorType    the {@code String} parameter specifies Doctor's type.
     * @param sortParameter the {@code String} parameter specifies order.
     * @return {@code List<Doctor>} list of Doctors in specified order.
     */
    @Override
    public List<EntityDoctor> getSortedDoctors(String doctorType, String sortParameter) throws SQLException {
        List<EntityDoctor> doctors = new ArrayList<>();
        // sortParameter === full_name OR doctor_type OR patients_amount
        String sqlRequest = String.format(SQL__FIND_AND_SORT_ALL_DOCTORS, sortParameter);

        if (!doctorType.equals("all")) {
            sqlRequest = String.format(SQL__FIND_AND_SORT_DOCTORS_BY_TYPE, doctorType, sortParameter);
        }

        try (Connection connection = MySQLConnectorManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(sqlRequest)) {

            MySQLConnectorManager.startTransaction(connection);

            ResultSet resultSet = USER_DAO.getEntityDoctors(statement);

            while (resultSet.next()) {

                EntityDoctor doctor = getDoctorFromResultSet(resultSet);

                doctors.add(doctor);

            }

            MySQLConnectorManager.commitTransaction(connection);

            resultSet.close();

        } catch (SQLException e) {

            LOGGER.error(COULD_NOT_LOAD_DOCTORS);
        }
        return doctors;
    }

    @Override
    public User getUserById(int id) {

        User user = null;

        try (Connection connection = MySQLConnectorManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_GET_USER_BY_ID)) {

            MySQLConnectorManager.startTransaction(connection);

            ResultSet resultSet = USER_DAO.getUserById(statement, id);

            user = getUserFromResultSet(resultSet);

            MySQLConnectorManager.commitTransaction(connection);

            resultSet.close();

        } catch (SQLException e) {

            LOGGER.error(USER_NOT_VALIDATED);

        }

        return user;
    }

    @Override
    public User getUserByEmail(String email) {

        User user = null;

        try (Connection connection = MySQLConnectorManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_GET_USER_BY_EMAIL)) {

            MySQLConnectorManager.startTransaction(connection);

            ResultSet resultSet = USER_DAO.getUserByEmail(statement, email);

            if (resultSet.next()) {

                user = getUserFromResultSet(resultSet);

            }

            MySQLConnectorManager.commitTransaction(connection);

            resultSet.close();

        } catch (SQLException e) {

            LOGGER.error(USER_NOT_VALIDATED);

        }

        return user;
    }

    @Override
    public void deleteUserByEmail(String email) throws SQLException {

        USER_DAO.deleteUserByEmail(email);

    }

    @Override
    public void setDoctorForPatient(int doctorId, int patientId) throws SQLException {

        USER_DAO.setDoctorForPatient(doctorId, patientId);

    }

    @Override
    public void setDoctorType(int doctorId, String doctorType) throws SQLException {

        int typeId = getDoctorTypeIdByTypeName(doctorType);

        USER_DAO.setTypeForDoctor(doctorId, typeId);

    }

    private Integer getDoctorTypeIdByTypeName(String doctorType) {

        Integer typeId = null;

        try (Connection connection = MySQLConnectorManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_GET_TYPE_ID_BY_NAME)) {

            MySQLConnectorManager.startTransaction(connection);

            ResultSet resultSet = USER_DAO.getDoctorTypeByTypeName(statement, doctorType);

            if (resultSet.next()) {

                typeId = resultSet.getInt("id");

            } else {

                typeId = addDoctorTypeByTypeName(doctorType);

            }

            MySQLConnectorManager.commitTransaction(connection);

            resultSet.close();

        } catch (SQLException e) {

            LOGGER.error(DOCTOR_TYPE_NOT_VALIDATED);

        }

        return typeId;
    }

    private EntityDoctor getDoctorFromResultSet(ResultSet resultSet) throws SQLException {

        User user = getUserFromResultSet(resultSet);

        return new EntityDoctorBuilder()
                .buildUser(user)
                .buildDoctorType(resultSet.getString("doctor_type"))
                .buildPatientsAmount(resultSet.getInt("patients_amount"))
                .build();
    }

    private Integer addDoctorTypeByTypeName(String doctorType) throws SQLException {

        Integer typeId = null;

        try (Connection connection = MySQLConnectorManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_ADD_TYPE_ID_BY_NAME, Statement.RETURN_GENERATED_KEYS)) {

            MySQLConnectorManager.startTransaction(connection);

            statement.setString(1, doctorType);

            statement.executeUpdate();

            MySQLConnectorManager.commitTransaction(connection);

            ResultSet rs = statement.getGeneratedKeys();

            if (rs.next()) {
                typeId = rs.getInt(1);
            }

        } catch (SQLException e) {

            throw new SQLException(TYPE_FOR_DOCTOR_EXISTS);
        }

        return typeId;
    }

    @Override
    public int countPatientsByDoctorId(int doctorId) throws SQLException {

        int count = 0;

        try (Connection connection = MySQLConnectorManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_COUNT_PATIENTS_BY_DOCTOR_ID)) {

            MySQLConnectorManager.startTransaction(connection);

            ResultSet resultSet = USER_DAO.countPatientsByDoctorId(statement, doctorId);

            if (resultSet.next()) {

                count = resultSet.getInt(MYSQL_COUNT);

            }

            MySQLConnectorManager.commitTransaction(connection);

            resultSet.close();

        } catch (SQLException e) {

            LOGGER.error(COULD_NOT_LOAD_USERS);
        }
        return count;
    }

    @Override
    public List<User> getSortedPatientsByDoctorId(int doctorId, String sortBy) throws SQLException {

        List<User> users = new ArrayList<>();
        // sortParameter === birth_date OR full_name
        String sqlRequest = String.format(SQL__FIND_AND_SORT_PATIENTS_BY_DOCTOR_ID, sortBy);

        try (Connection connection = MySQLConnectorManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(sqlRequest)) {

            MySQLConnectorManager.startTransaction(connection);

            ResultSet resultSet = USER_DAO.getUsersByRoleId(statement, doctorId);

            while (resultSet.next()) {

                User user = getUserFromResultSet(resultSet);

                users.add(user);

            }

            MySQLConnectorManager.commitTransaction(connection);

            resultSet.close();

        } catch (SQLException e) {

            LOGGER.error(COULD_NOT_LOAD_USERS);
        }
        return users;
    }

    @Override
    public List<User> getPatientsPageByDoctorId(int doctorId, String sortBy, int startRow, int amount) {

        List<User> users = new ArrayList<>();

        String sqlRequest = String.format(SQL__PAGE_OF_SORTED_PATIENTS_BY_DOCTOR_ID, sortBy);

        try (Connection connection = MySQLConnectorManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(sqlRequest)) {

            MySQLConnectorManager.startTransaction(connection);

            ResultSet resultSet = USER_DAO.getPatients(statement, doctorId, startRow, amount);

            while (resultSet.next()) {

                User user = getUserFromResultSet(resultSet);

                users.add(user);

            }

            MySQLConnectorManager.commitTransaction(connection);

            resultSet.close();

        } catch (SQLException e) {

            LOGGER.error(COULD_NOT_LOAD_USERS);
        }
        return users;
    }

    @Override
    public List<EntityDoctor> getDoctorsPage(String doctorType, String sortParameter, int startRow, int amount) throws SQLException {

        List<EntityDoctor> doctors = new ArrayList<>();
        // sortParameter === full_name OR doctor_type OR patients_amount
        String sqlRequest = String.format(SQL__FIND_AND_SORT_ALL_DOCTORS_PAGE, sortParameter);

        if (!doctorType.equals("all")) {
            sqlRequest = String.format(SQL__FIND_AND_SORT_DOCTORS_BY_TYPE_PAGE, doctorType, sortParameter);
        }

        try (Connection connection = MySQLConnectorManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(sqlRequest)) {

            MySQLConnectorManager.startTransaction(connection);

            ResultSet resultSet = USER_DAO.getEntityDoctorsPage(statement, startRow, amount);

            while (resultSet.next()) {

                EntityDoctor doctor = getDoctorFromResultSet(resultSet);

                doctors.add(doctor);

            }

            MySQLConnectorManager.commitTransaction(connection);

            resultSet.close();

        } catch (SQLException e) {

            LOGGER.error(COULD_NOT_LOAD_DOCTORS);
        }
        return doctors;
    }

    @Override
    public int countDoctors() throws SQLException {

        return USER_DAO.countUsersByRoleId(UserRole.DOCTOR.getId());

    }

    @Override
    public List<User> getSortedUsersPageByRoleId(int roleId, String sortParameter, int startRow, int amount) throws SQLException {

        List<User> users = new ArrayList<>();

        String sqlRequest = String.format(SQL__PAGE_OF_SORTED_USERS_BY_ROLE_ID, sortParameter);

        try (Connection connection = MySQLConnectorManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(sqlRequest)) {

            MySQLConnectorManager.startTransaction(connection);

            ResultSet resultSet = USER_DAO.getPatients(statement, roleId, startRow, amount);

            while (resultSet.next()) {

                User user = getUserFromResultSet(resultSet);

                users.add(user);

            }

            MySQLConnectorManager.commitTransaction(connection);

            resultSet.close();

        } catch (SQLException e) {

            LOGGER.error(COULD_NOT_LOAD_USERS);
        }
        return users;
    }

    @Override
    public int countUsersByRoleId(int id) throws SQLException {

        return USER_DAO.countUsersByRoleId(UserRole.PATIENT.getId());

    }

}

package ua.kiev.mvovnianko.hospital.dao.impl;

import ua.kiev.mvovnianko.hospital.dao.UserDAO;
import ua.kiev.mvovnianko.hospital.dbConnector.MySQLConnectorManager;
import ua.kiev.mvovnianko.hospital.entity.User;

import java.sql.*;

import static ua.kiev.mvovnianko.hospital.utils.UtilConstants.*;

/**
 * The {@code JDBCUserDao} class is a JDBC implementation
 * of {@code UserDAO} interface
 */
public class JDBCUserDAO implements UserDAO {

    /**
     * Responsible for getting the ResultSet with all the data of specified User from DB.
     *
     * @param statement the {@code PreparedStatement} from {@code MySQLStationService}.
     * @param email     the{@code String} parameter specifies the eMail of corresponding User.
     * @param password  the{@code String} parameter specifies the password of corresponding User.
     */
    @Override
    public ResultSet validateUser(PreparedStatement statement, String email, String password) throws SQLException {

        statement.setString(1, email);
        statement.setString(2, password);

        return statement.executeQuery();
    }

    /**
     * Responsible for deleting User from DB
     *
     * @param userEmail the {@code String} parameter, specifies User.
     */
    @Override
    public void deleteUserByEmail(String userEmail) throws SQLException {

        try (Connection connection = MySQLConnectorManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_DELETE_USER_BY_EMAIL)) {

            MySQLConnectorManager.startTransaction(connection);

            statement.setString(1, userEmail);

            statement.executeUpdate();

            MySQLConnectorManager.commitTransaction(connection);

        }
    }

    /**
     * Responsible for saving new Station to DB
     *
     * @param user the instance of {@code User} entity class
     */
    @Override
    public void createNewUser(User user) throws SQLException {

        try (Connection connection = MySQLConnectorManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_ADD_NEW_USER, Statement.RETURN_GENERATED_KEYS)) {
            MySQLConnectorManager.startTransaction(connection);

            statement.setString(1, user.getEmail());
            statement.setString(2, user.getPassword());
            statement.setString(3, user.getFullName());
            statement.setString(4, user.getBirthDate().toString());
            statement.setInt(5, user.getUserRole().getId());
            statement.executeUpdate();

            MySQLConnectorManager.commitTransaction(connection);

            ResultSet rs = statement.getGeneratedKeys();

            if (rs.next()) {
                user.setId(rs.getInt(1));
            }

        } catch (SQLException e) {

            throw new SQLException(USER_EXISTS);
        }
    }

    /**
     * Responsible for getting ResultSet from DB containing all the data of all Users
     * due to the specified Role.
     *
     * @param roleId    the {@code int} parameter, specifies User's role.
     * @param statement the {@code PreparedStatement} from {@code MySQLUserService}.
     */
    @Override
    public ResultSet getUsersByRoleId(PreparedStatement statement, int roleId) throws SQLException {

        statement.setInt(1, roleId);

        return statement.executeQuery();
    }

    @Override
    public ResultSet getEntityDoctors(PreparedStatement statement) throws SQLException {

        return statement.executeQuery();
    }

    @Override
    public ResultSet getUserById(PreparedStatement statement, int userId) throws SQLException {

        statement.setInt(1, userId);

        return statement.executeQuery();
    }

    @Override
    public ResultSet getUserByEmail(PreparedStatement statement, String email) throws SQLException {

        statement.setString(1, email);

        return statement.executeQuery();
    }

    @Override
    public void setDoctorForPatient(int doctorId, int patientId) throws SQLException {

        try (Connection connection = MySQLConnectorManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_SET_DOC_FOR_PATIENT)) {
            MySQLConnectorManager.startTransaction(connection);

            statement.setInt(1, doctorId);
            statement.setInt(2, patientId);

            statement.executeUpdate();

            MySQLConnectorManager.commitTransaction(connection);

        } catch (SQLException e) {

            throw new SQLException(DOC_FOR_PATIENT_EXISTS);
        }
    }

    @Override
    public void setTypeForDoctor(int doctorId, int typeId) throws SQLException {

        try (Connection connection = MySQLConnectorManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_SET_TYPE_FOR_DOCTOR)) {
            MySQLConnectorManager.startTransaction(connection);

            statement.setInt(1, doctorId);
            statement.setInt(2, typeId);

            statement.executeUpdate();

            MySQLConnectorManager.commitTransaction(connection);

        } catch (SQLException e) {

            throw new SQLException(TYPE_FOR_DOCTOR_EXISTS);
        }
    }

    @Override
    public ResultSet getDoctorTypeByTypeName(PreparedStatement statement, String doctorType) throws SQLException {

        statement.setString(1, doctorType);

        return statement.executeQuery();
    }

    @Override
    public ResultSet addDoctorType(PreparedStatement statement, String doctorType) throws SQLException {

        statement.setString(1, doctorType);

        return statement.executeQuery();
    }
}
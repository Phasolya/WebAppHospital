package ua.kiev.mvovnianko.hospital.service.mysqlimpl;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import ua.kiev.mvovnianko.hospital.dao.DiseaseDAO;
import ua.kiev.mvovnianko.hospital.dbConnector.MySQLConnectorManager;
import ua.kiev.mvovnianko.hospital.entity.Disease;
import ua.kiev.mvovnianko.hospital.entity.User;
import ua.kiev.mvovnianko.hospital.entity.builder.DiseaseBuilder;
import ua.kiev.mvovnianko.hospital.entity.builder.UserBuilder;
import ua.kiev.mvovnianko.hospital.service.DiseaseService;
import ua.kiev.mvovnianko.hospital.service.UserService;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static ua.kiev.mvovnianko.hospital.utils.UtilConstants.*;

public class MySQLDiseaseService implements DiseaseService {

    private static final Logger LOGGER = LogManager.getLogger(MySQLDiseaseService.class);

    private final DiseaseDAO DISEASE_DAO;
    private final UserService USER_SERVICE;

    public MySQLDiseaseService(DiseaseDAO diseaseDAO, UserService userService) {
        DISEASE_DAO = diseaseDAO;
        USER_SERVICE = userService;
    }

    @Override
    public void createDisease(Disease disease) throws SQLException {

        DISEASE_DAO.createNewDisease(disease);

        LOGGER.info(TREATMENT_CREATED);
    }

    @Override
    public void deleteDiseaseById(int diseaseId) throws SQLException {

        DISEASE_DAO.deleteDiseaseById(diseaseId);

    }

    @Override
    public List<Disease> getDiseasesByUserId(int userId) throws SQLException {
        List<Disease> diseases = new ArrayList<>();

        try (Connection connection = MySQLConnectorManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_GET_DISEASE_BY_USER_ID_PARTLY)) {

            MySQLConnectorManager.startTransaction(connection);

            ResultSet resultSet = DISEASE_DAO.getDiseasesByUserId(statement, userId);

            while (resultSet.next()) {

                Disease disease = getDiseaseFromResultSet(resultSet);

                diseases.add(disease);

            }

            MySQLConnectorManager.commitTransaction(connection);

            resultSet.close();

        } catch (SQLException e) {

            LOGGER.error(COULD_NOT_LOAD_TREATMENTS);
        }
        return diseases;
    }

    @Override
    public Disease getDiseaseById(int diseaseId) throws SQLException {

        Disease disease = null;

        try (Connection connection = MySQLConnectorManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_GET_DISEASE_BY_ID)) {

            MySQLConnectorManager.startTransaction(connection);

            ResultSet resultSet = DISEASE_DAO.getDiseaseById(statement, diseaseId);

            disease = getDiseaseFromResultSet(resultSet);

            MySQLConnectorManager.commitTransaction(connection);

            resultSet.close();

        } catch (SQLException e) {

            LOGGER.error(COULD_NOT_LOAD_DISEASE);
        }
        return disease;
    }

    @Override
    public List<Disease> getAllDiseases(String sortBy) throws SQLException {

        List<Disease> diseases = new ArrayList<>();

        String sqlRequest = String.format(SQL_GET_SORTED_DISEASES, sortBy);

        try (Connection connection = MySQLConnectorManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(sqlRequest)) {

            MySQLConnectorManager.startTransaction(connection);

            ResultSet resultSet = DISEASE_DAO.getDiseases(statement);

            while (resultSet.next()) {

                Disease disease = getDiseaseFromResultSet(resultSet);

                diseases.add(disease);

            }

            MySQLConnectorManager.commitTransaction(connection);

            resultSet.close();

        } catch (SQLException e) {

            LOGGER.error(COULD_NOT_LOAD_DISEASE);
        }
        return diseases;
    }

    @Override
    public List<Disease> getDiseasesPage(String sortBy, int doctorId, int startRow, int amount) throws SQLException {

        List<Disease> diseases = new ArrayList<>();

        String sqlRequest = String.format(SQL_GET_SORTED_DISEASES_BY_DOCTOR_ID, sortBy);

        try (Connection connection = MySQLConnectorManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(sqlRequest)) {

            MySQLConnectorManager.startTransaction(connection);

            ResultSet resultSet = DISEASE_DAO.getDiseasesPage(statement, doctorId, startRow, amount);

            while (resultSet.next()) {

                Disease disease = getDiseaseFromResultSet(resultSet);

                diseases.add(disease);

            }

            MySQLConnectorManager.commitTransaction(connection);

            resultSet.close();

        } catch (SQLException e) {

            LOGGER.error(COULD_NOT_LOAD_DISEASE);
        }
        return diseases;
    }

    @Override
    public List<Disease> getDiseasesPageByPatientId(String sortBy, int patientId, int startRow, int amount) throws SQLException {

        List<Disease> diseases = new ArrayList<>();

        String sqlRequest = String.format(SQL_GET_SORTED_DISEASES_BY_PATIENT_ID, sortBy);

        try (Connection connection = MySQLConnectorManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(sqlRequest)) {

            MySQLConnectorManager.startTransaction(connection);

            ResultSet resultSet = DISEASE_DAO.getDiseasesPage(statement, patientId, startRow, amount);

            while (resultSet.next()) {

                Disease disease = getDiseaseFromResultSet(resultSet);

                diseases.add(disease);

            }

            MySQLConnectorManager.commitTransaction(connection);

            resultSet.close();

        } catch (SQLException e) {

            LOGGER.error(COULD_NOT_LOAD_DISEASE);
        }
        return diseases;
    }

    @Override
    public int countDiseasesByDoctorId(int doctorId) throws SQLException {

        int count = 0;

        try (Connection connection = MySQLConnectorManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_COUNT_DISEASES_BY_DOCTOR_ID)) {

            MySQLConnectorManager.startTransaction(connection);

            ResultSet resultSet = DISEASE_DAO.countDiseasesByDoctorId(statement, doctorId);

            if (resultSet.next()) {

                count = resultSet.getInt(MYSQL_COUNT);

            }

            MySQLConnectorManager.commitTransaction(connection);

            resultSet.close();

        } catch (SQLException e) {

            LOGGER.error(COULD_NOT_LOAD_DISEASE);
        }
        return count;
    }

    @Override
    public int countDiseasesByPatientId(int patientId) throws SQLException {

        int count = 0;

        try (Connection connection = MySQLConnectorManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_COUNT_DISEASES_BY_PATIENT_ID)) {

            MySQLConnectorManager.startTransaction(connection);

            ResultSet resultSet = DISEASE_DAO.countDiseasesByDoctorId(statement, patientId);

            if (resultSet.next()) {

                count = resultSet.getInt(MYSQL_COUNT);

            }

            MySQLConnectorManager.commitTransaction(connection);

            resultSet.close();

        } catch (SQLException e) {

            LOGGER.error(COULD_NOT_LOAD_DISEASE);
        }
        return count;
    }

    private Disease getDiseaseFromResultSet(ResultSet resultSet) throws SQLException {

        //User user = USER_SERVICE.getUserById(resultSet.getInt("user_id"));

        User user = new UserBuilder()
                .buildFullName(resultSet.getString("full_name"))
                .buildEmail(resultSet.getString("email"))
                .build();

        return new DiseaseBuilder()
                .buildId(resultSet.getInt("id"))
                .buildName(resultSet.getString("name"))
                .buildUser(user)
                .build();
    }

    private Disease getFullDiseaseFromResultSet(ResultSet resultSet) throws SQLException {

        User user = USER_SERVICE.getUserById(resultSet.getInt("user_id"));

        return new DiseaseBuilder()
                .buildId(resultSet.getInt("id"))
                .buildName(resultSet.getString("name"))
                .buildUser(user)
                .build();
    }
}

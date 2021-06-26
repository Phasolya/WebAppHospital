package ua.kiev.mvovnianko.hospital.service.mysqlimpl;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import ua.kiev.mvovnianko.hospital.dao.DiseaseDAO;
import ua.kiev.mvovnianko.hospital.dao.TreatmentDAO;
import ua.kiev.mvovnianko.hospital.dao.UserDAO;
import ua.kiev.mvovnianko.hospital.dao.impl.JDBCDiseaseDAO;
import ua.kiev.mvovnianko.hospital.dao.impl.JDBCUserDAO;
import ua.kiev.mvovnianko.hospital.dbConnector.MySQLConnectorManager;
import ua.kiev.mvovnianko.hospital.entity.Disease;
import ua.kiev.mvovnianko.hospital.entity.Treatment;
import ua.kiev.mvovnianko.hospital.entity.TreatmentType;
import ua.kiev.mvovnianko.hospital.entity.User;
import ua.kiev.mvovnianko.hospital.entity.builder.TreatmentBuilder;
import ua.kiev.mvovnianko.hospital.service.DiseaseService;
import ua.kiev.mvovnianko.hospital.service.TreatmentService;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import static ua.kiev.mvovnianko.hospital.utils.UtilConstants.*;

public class MySQLTreatmentService implements TreatmentService {

    private static final Logger LOGGER = LogManager.getLogger(MySQLTreatmentService.class);

    private final TreatmentDAO TREATMENT_DAO;
    private final DiseaseService DISEASE_DAO;

    public MySQLTreatmentService(TreatmentDAO treatmentDAO, DiseaseService diseaseService) {
        TREATMENT_DAO = treatmentDAO;
        DISEASE_DAO = diseaseService;
    }

    @Override
    public void createTreatment(Treatment treatment) throws SQLException {

        TREATMENT_DAO.createNewTreatment(treatment);

        LOGGER.info(TREATMENT_CREATED);
    }

    @Override
    public void deleteTreatmentById(int treatmentId) throws SQLException {

        TREATMENT_DAO.deleteTreatmentById(treatmentId);

    }

    @Override
    public List<Treatment> getTreatmentsByDiseaseId(int diseaseId) throws SQLException {

        List<Treatment> treatments = new ArrayList<>();

        try (Connection connection = MySQLConnectorManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_GET_TREATMENTS_BY_DISEASE_ID)) {

            MySQLConnectorManager.startTransaction(connection);

            ResultSet resultSet = TREATMENT_DAO.getTreatmentsByDiseaseId(statement, diseaseId);

            while (resultSet.next()) {

                Treatment treatment = getTreatmentFromResultSet(resultSet);

                treatments.add(treatment);

            }

            MySQLConnectorManager.commitTransaction(connection);

            resultSet.close();

        } catch (SQLException e) {

            LOGGER.error(COULD_NOT_LOAD_TREATMENTS);
        }
        return treatments;
    }

    @Override
    public List<Treatment> getTreatmentsByTreatmentTypeId(int treatmentTypeId) throws SQLException {

        List<Treatment> treatments = new ArrayList<>();

        try (Connection connection = MySQLConnectorManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_GET_TREATMENTS_BY_TREATMENT_TYPE_ID)) {

            MySQLConnectorManager.startTransaction(connection);

            ResultSet resultSet = TREATMENT_DAO.getTreatmentsByTreatmentTypeId(statement, treatmentTypeId);

            while (resultSet.next()) {

                Treatment treatment = getTreatmentFromResultSet(resultSet);

                treatments.add(treatment);

            }

            MySQLConnectorManager.commitTransaction(connection);

            resultSet.close();

        } catch (SQLException e) {

            LOGGER.error(COULD_NOT_LOAD_TREATMENTS);
        }
        return treatments;
    }

    @Override
    public List<Treatment> getSortedTreatments(String sortBy) throws SQLException {

        List<Treatment> treatments = new ArrayList<>();

        String sqlRequest = String.format(SQL_GET_SORTED_TREATMENTS, sortBy);

        try (Connection connection = MySQLConnectorManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(sqlRequest)) {

            MySQLConnectorManager.startTransaction(connection);

            ResultSet resultSet = TREATMENT_DAO.getSortedTreatments(statement);

            while (resultSet.next()) {

                Treatment treatment = getTreatmentFromResultSet(resultSet);

                treatments.add(treatment);

            }

            MySQLConnectorManager.commitTransaction(connection);

            resultSet.close();

        } catch (SQLException e) {

            LOGGER.error(COULD_NOT_LOAD_TREATMENTS);
        }
        return treatments;
    }

    @Override
    public Treatment getTreatmentById(int treatmentId) throws SQLException {

        Treatment treatment = null;

        try (Connection connection = MySQLConnectorManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_GET_TREATMENT_BY_ID_FULL)) {

            MySQLConnectorManager.startTransaction(connection);

            ResultSet resultSet = TREATMENT_DAO.getTreatmentById(statement, treatmentId);

            if (resultSet.next()) {

                treatment = getTreatmentFromResultSet(resultSet);

            }

            MySQLConnectorManager.commitTransaction(connection);

            resultSet.close();

        } catch (SQLException e) {

            LOGGER.error(COULD_NOT_LOAD_TREATMENTS);
        }
        return treatment;
    }


    private Treatment getTreatmentFromResultSet(ResultSet resultSet) throws SQLException {

        User user = new User();
        user.setFullName(resultSet.getString("patients_name"));
        user.setEmail(resultSet.getString("patients_login"));

        Disease disease = new Disease();
        disease.setName(resultSet.getString("disease_name"));
        disease.setUser(user);

        String treatmentTypeName = resultSet.getString("treatment_type_name").toUpperCase();
        TreatmentType treatmentType = TreatmentType.valueOf(treatmentTypeName);

        return new TreatmentBuilder()
                .buildId(resultSet.getInt("id"))
                .buildName(resultSet.getString("name"))
                .buildDisease(disease)
                .buildDTreatmentType(treatmentType)
                .build();
    }

    @Override
    public List<Treatment> getTreatmentsPage(String sortBy, int startRow, int amount) throws SQLException {

        String sqlRequest = String.format(SQL_GET_TREATMENTS, sortBy);

        List<Treatment> treatments = new ArrayList<>();

        try (Connection connection = MySQLConnectorManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(sqlRequest)) {

            MySQLConnectorManager.startTransaction(connection);

            ResultSet resultSet = TREATMENT_DAO.getTreatmentsPage(statement, startRow, amount);

            while (resultSet.next()) {

                Treatment treatment = getTreatmentFromResultSet(resultSet);

                treatments.add(treatment);

            }

            MySQLConnectorManager.commitTransaction(connection);

            resultSet.close();

        } catch (SQLException e) {

            LOGGER.error(COULD_NOT_LOAD_TREATMENTS);
        }
        return treatments;
    }

    @Override
    public List<Treatment> getTreatmentsPageByPatientId(String sortBy,int patientId, int startRow, int amount) throws SQLException{

        String sqlRequest = String.format(SQL_GET_TREATMENTS_BY_PATIENT_ID, sortBy);

        List<Treatment> treatments = new ArrayList<>();

        try (Connection connection = MySQLConnectorManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(sqlRequest)) {

            MySQLConnectorManager.startTransaction(connection);

            ResultSet resultSet = TREATMENT_DAO.getTreatmentsPageByPatientId(statement, patientId, startRow, amount);

            while (resultSet.next()) {

                Treatment treatment = getTreatmentFromResultSet(resultSet);

                treatments.add(treatment);

            }

            MySQLConnectorManager.commitTransaction(connection);

            resultSet.close();

        } catch (SQLException e) {

            LOGGER.error(COULD_NOT_LOAD_TREATMENTS);
        }
        return treatments;
    }


    @Override
    public int countTreatments() throws SQLException {

        return TREATMENT_DAO.countTreatments();

    }

    @Override
    public int countTreatmentsByPatientId(int patientId) throws SQLException{

        int count = 0;

        try (Connection connection = MySQLConnectorManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_COUNT_TREATMENTS_BY_PATIENT_ID)) {

            MySQLConnectorManager.startTransaction(connection);

            ResultSet resultSet = TREATMENT_DAO.countTreatmentsByPatient(statement, patientId);

            if (resultSet.next()) {

                count = resultSet.getInt(MYSQL_COUNT);

            }

            MySQLConnectorManager.commitTransaction(connection);

            resultSet.close();

        } catch (SQLException e) {

            LOGGER.error(COULD_NOT_LOAD_TREATMENTS);
        }
        return count;
    }

    private List<Treatment> getTreatmentsFromResultSet(ResultSet resultSet) throws SQLException {

        List<Treatment> treatments = new ArrayList<>();

        while (resultSet.next()) {

            treatments.add(getTreatmentFromResultSet(resultSet));
        }

        return treatments;
    }

//    private Treatment getTreatmentFromResultSet(ResultSet resultSet) throws SQLException {
//
//        DiseaseService DISEASE_SERVICE = new MySQLDiseaseService(new JDBCDiseaseDAO(), new MySQLUserService(new JDBCUserDAO()));
//
//        Disease disease = DISEASE_SERVICE.getDiseaseById(resultSet.getInt("disease_id"));
//
//        TreatmentType treatmentType = TreatmentType.getTreatmentType(resultSet.getInt("treatment_type_id"));
//
//        return new TreatmentBuilder()
//                .buildId(resultSet.getInt("id"))
//                .buildName(resultSet.getString("name"))
//                .buildDisease(disease)
//                .buildDTreatmentType(treatmentType)
//                .build();
//
//    }

}

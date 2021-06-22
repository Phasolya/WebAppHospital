package ua.kiev.mvovnianko.hospital.dao.impl;

import ua.kiev.mvovnianko.hospital.dao.CommonsOperable;
import ua.kiev.mvovnianko.hospital.dao.TreatmentDAO;
import ua.kiev.mvovnianko.hospital.dbConnector.MySQLConnectorManager;
import ua.kiev.mvovnianko.hospital.entity.Treatment;

import java.sql.*;

import static ua.kiev.mvovnianko.hospital.utils.UtilConstants.*;

public class JDBCTreatmentDAO implements TreatmentDAO, CommonsOperable {

    @Override
    public ResultSet getAllTreatments(Connection connection) throws SQLException {

        return getAllItems(connection, SQL_GET_ALL_TREATMENTS);
    }

    @Override
    public int countTreatments() throws SQLException {

        String sqlRequest = String.format(SQL__COUNT_ROWS, SQL_TABLE_TREATMENT);

        return countRows(sqlRequest);
    }

    @Override
    public ResultSet getTreatmentsPage(PreparedStatement statement, int startRow, int amount) throws SQLException {

        statement.setInt(1, startRow);
        statement.setInt(2, amount);

        return statement.executeQuery();

    }

    @Override
    public void createNewTreatment(Treatment treatment) throws SQLException {

        try (Connection connection = MySQLConnectorManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_ADD_NEW_TREATMENT, Statement.RETURN_GENERATED_KEYS)) {
            MySQLConnectorManager.startTransaction(connection);

            statement.setString(1, treatment.getName());
            statement.setInt(2, treatment.getDisease().getId());
            statement.setInt(3, treatment.getTreatmentType().getId());
            statement.executeUpdate();

            MySQLConnectorManager.commitTransaction(connection);

            ResultSet rs = statement.getGeneratedKeys();

            if (rs.next()) {
                treatment.setId(rs.getInt(1));
            }

        } catch (SQLException e) {

            throw new SQLException(TREATMENT_EXISTS);
        }
    }

    @Override
    public void deleteTreatmentById(int treatmentId) throws SQLException {

        try (Connection connection = MySQLConnectorManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_DELETE_TREATMENT_BY_ID)) {

            MySQLConnectorManager.startTransaction(connection);

            statement.setInt(1, treatmentId);

            statement.executeUpdate();

            MySQLConnectorManager.commitTransaction(connection);

        }
    }

    @Override
    public ResultSet getTreatmentsByDiseaseId(PreparedStatement statement, int diseaseId) throws SQLException {

        statement.setInt(1, diseaseId);

        return statement.executeQuery();

    }

    @Override
    public ResultSet getTreatmentsByTreatmentTypeId(PreparedStatement statement, int treatmentTypeId) throws SQLException {

        statement.setInt(1, treatmentTypeId);

        return statement.executeQuery();

    }

    @Override
    public ResultSet getSortedTreatments(PreparedStatement statement) throws SQLException {
        return statement.executeQuery();
    }

    @Override
    public ResultSet getTreatmentById(PreparedStatement statement, int treatmentId) throws SQLException {

        statement.setInt(1, treatmentId);

        return statement.executeQuery();

    }
}

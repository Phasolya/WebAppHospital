package ua.kiev.mvovnianko.hospital.dao.impl;

import ua.kiev.mvovnianko.hospital.dao.DiseaseDAO;
import ua.kiev.mvovnianko.hospital.dbConnector.MySQLConnectorManager;
import ua.kiev.mvovnianko.hospital.entity.Disease;

import java.sql.*;

import static ua.kiev.mvovnianko.hospital.utils.UtilConstants.*;

public class JDBCDiseaseDAO implements DiseaseDAO {
    @Override
    public void createNewDisease(Disease disease) throws SQLException {

        try (Connection connection = MySQLConnectorManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_ADD_NEW_DISEASE, Statement.RETURN_GENERATED_KEYS)) {
            MySQLConnectorManager.startTransaction(connection);

            statement.setString(1, disease.getName());
            statement.setInt(2, disease.getUser().getId());
            statement.executeUpdate();

            MySQLConnectorManager.commitTransaction(connection);

            ResultSet rs = statement.getGeneratedKeys();

            if (rs.next()) {
                disease.setId(rs.getInt(1));
            }

        } catch (SQLException e) {

            throw new SQLException(DISEASE_EXISTS);
        }
    }

    @Override
    public void deleteDiseaseById(int diseaseId) throws SQLException {

        try (Connection connection = MySQLConnectorManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_DELETE_DISEASE_BY_ID)) {

            MySQLConnectorManager.startTransaction(connection);

            statement.setInt(1, diseaseId);

            statement.executeUpdate();

            MySQLConnectorManager.commitTransaction(connection);

        }
    }

    @Override
    public ResultSet getDiseasesByUserId(PreparedStatement statement, int userId) throws SQLException {

        statement.setInt(1, userId);

        return statement.executeQuery();

    }

    @Override
    public ResultSet getDiseaseById(PreparedStatement statement, int diseaseId) throws SQLException {

        statement.setInt(1, diseaseId);

        return statement.executeQuery();

    }

    @Override
    public ResultSet getDiseases(PreparedStatement statement) throws SQLException {

        return statement.executeQuery();

    }

    @Override
    public ResultSet getDiseasesPage(PreparedStatement statement, int doctorId, int startRow, int amount) throws SQLException {

        statement.setInt(1, doctorId);
        statement.setInt(2, startRow);
        statement.setInt(3, amount);

        return statement.executeQuery();

    }

    @Override
    public ResultSet countDiseasesByDoctorId(PreparedStatement statement, int doctorId) throws SQLException {

        statement.setInt(1, doctorId);

        return statement.executeQuery();
    }
}

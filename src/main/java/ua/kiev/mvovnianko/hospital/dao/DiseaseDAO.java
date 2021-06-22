package ua.kiev.mvovnianko.hospital.dao;

import ua.kiev.mvovnianko.hospital.entity.Disease;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by M.Vovnianko on 15.06.2021.
 */
public interface DiseaseDAO {

    /**
     * Responsible for saving new Disease to DB
     *
     * @param disease the instance of {@code Disease} entity class
     */
    void createNewDisease(Disease disease) throws SQLException;

    /**
     * Responsible for deleting Disease from DB
     *
     * @param diseaseId the {@code int} parameter, specifies Disease.
     */
    void deleteDiseaseById(int diseaseId) throws SQLException;

    /**
     * Responsible for retrieving all Disease by User from DB
     *
     * @param userId the{@code int} parameter specifies the corresponding Disease.
     */
    ResultSet getDiseasesByUserId(PreparedStatement statement, int userId) throws SQLException;

    ResultSet getDiseaseById(PreparedStatement statement, int diseaseId) throws SQLException;

    ResultSet getDiseases(PreparedStatement statement) throws SQLException;
}

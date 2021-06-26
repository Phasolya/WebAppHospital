package ua.kiev.mvovnianko.hospital.dao;

import ua.kiev.mvovnianko.hospital.entity.Treatment;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by M.Vovnianko on 13.06.2021.
 */
public interface TreatmentDAO{

    /**
     * Responsible for saving new Treatment to DB
     *
     * @param treatment the instance of {@code Treatment} entity class
     */
    void createNewTreatment(Treatment treatment) throws SQLException;

    /**
     * Responsible for deleting Treatment from DB
     *
     * @param treatmentId the {@code int} parameter, specifies Treatment.
     */
    void deleteTreatmentById(int treatmentId) throws SQLException;

    /**
     * Responsible for retrieving all Treatments by Disease from DB
     *
     * @param diseaseId the{@code int} parameter specifies the corresponding Disease.
     */
    ResultSet getTreatmentsByDiseaseId(PreparedStatement statement, int diseaseId) throws SQLException;

    /**
     * Responsible for retrieving all Treatments by Treatment Type from DB
     *
     * @param treatmentTypeId the{@code int} parameter specifies the corresponding Treatment Type.
     */
    ResultSet getTreatmentsByTreatmentTypeId(PreparedStatement statement, int treatmentTypeId) throws SQLException;

    ResultSet getSortedTreatments(PreparedStatement statement) throws SQLException;

    ResultSet getTreatmentById(PreparedStatement statement, int treatmentId) throws SQLException;

    ResultSet getAllTreatments(Connection connection) throws SQLException;

    int countTreatments() throws SQLException;

    ResultSet getTreatmentsPage(PreparedStatement statement, int startRow, int amount) throws SQLException;
    ResultSet getTreatmentsPageByPatientId(PreparedStatement statement, int patientId, int startRow, int amount) throws SQLException;

    ResultSet countTreatmentsByPatient(PreparedStatement statement, int patientId) throws SQLException;

}

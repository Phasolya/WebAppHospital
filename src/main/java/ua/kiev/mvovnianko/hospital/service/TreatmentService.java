package ua.kiev.mvovnianko.hospital.service;

import ua.kiev.mvovnianko.hospital.entity.Treatment;

import java.sql.SQLException;
import java.util.List;

public interface TreatmentService {

    void createTreatment(Treatment treatment) throws SQLException;

    void deleteTreatmentById(int treatmentId) throws SQLException;

    List<Treatment> getTreatmentsByDiseaseId( int diseaseId) throws SQLException;

    List<Treatment> getTreatmentsByTreatmentTypeId( int treatmentTypeId) throws SQLException;

    List<Treatment> getSortedTreatments(String sortBy) throws SQLException;

    Treatment getTreatmentById(int treatmentId)throws SQLException;

    /**
     * Responsible for getting the list of all Treatments by using pagination,
     * which sorted by specified parameter.
     *
     * @param sortBy   the {@code String} parameter specifies order.
     * @param startRow   the {@code int} parameter specifies the offset of the first Treatment to return.
     * @param amount the {@code String} parameter Treatments amount.
     * @return {@code List<Treatment>} list of Treatments in specified order.
     */
    List<Treatment> getTreatmentsPage(String sortBy, int startRow, int amount) throws SQLException;

    int countTreatments() throws SQLException;
}

package ua.kiev.mvovnianko.hospital.service;

import ua.kiev.mvovnianko.hospital.entity.Disease;

import java.sql.SQLException;
import java.util.List;

public interface DiseaseService {

    void createDisease(Disease disease) throws SQLException;

    List<Disease> getDiseasesByUserId(int diseaseId) throws SQLException;

    List<Disease> getDiseasesPage(String sortBy, int doctorId, int startRow, int amount) throws SQLException;

    List<Disease> getDiseasesPageByPatientId(String sortBy, int patientId, int startRow, int amount) throws SQLException;

    int countDiseasesByDoctorId(int doctorId) throws SQLException;

    int countDiseasesByPatientId(int patientId) throws SQLException;

    void deleteDiseaseById(int diseaseId) throws SQLException;

    Disease getDiseaseById(int diseaseId) throws SQLException;

    List<Disease> getAllDiseases(String sortBy) throws SQLException;


}

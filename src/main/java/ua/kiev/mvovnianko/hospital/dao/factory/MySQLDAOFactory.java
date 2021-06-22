package ua.kiev.mvovnianko.hospital.dao.factory;


import ua.kiev.mvovnianko.hospital.dao.*;
import ua.kiev.mvovnianko.hospital.service.*;
import ua.kiev.mvovnianko.hospital.service.mysqlimpl.*;

/**
 * Created by M.Vovnianko on 13.06.2021.
 */
public class MySQLDAOFactory extends DAOFactory {

    /**
     * Method responsible for getting the instance of service class MySQLUserService implementation.
     *
     * @param userDAO {@code UserDAO} instance
     */
    @Override
    public UserService getUserService(UserDAO userDAO) {
        return new MySQLUserService(userDAO);
    }

    /**
     * Method responsible for getting the instance of service class MySQLUserService implementation.
     *
     * @param diseaseDAO {@code DiseaseDAO} instance
     * @param userService    {@code UserDAO} instance
     */
    @Override
    public DiseaseService getDiseaseService(DiseaseDAO diseaseDAO, UserService userService) {
        return new MySQLDiseaseService(diseaseDAO, userService);
    }

    /**
     * Method responsible for getting the instance of service class MySQLUserService implementation.
     *
     * @param treatmentDAO {@code TreatmentDAO} instance
     * @param diseaseService {@code DiseaseService} instance
     */
    @Override
    public TreatmentService getTreatmentService(TreatmentDAO treatmentDAO, DiseaseService diseaseService) {
        return new MySQLTreatmentService(treatmentDAO, diseaseService);
    }
}

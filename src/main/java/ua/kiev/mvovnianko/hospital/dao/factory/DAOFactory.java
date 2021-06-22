package ua.kiev.mvovnianko.hospital.dao.factory;

import ua.kiev.mvovnianko.hospital.dao.*;
import ua.kiev.mvovnianko.hospital.service.*;

import static ua.kiev.mvovnianko.hospital.utils.UtilConstants.MYSQL_DATA_BASE;

/**
 * Created by M.Vovnianko on 13.06.2021.
 */
public abstract class DAOFactory {

    private static final String DATA_BASE = MYSQL_DATA_BASE;

    public static DAOFactory getDAOFactory() {
        switch (DATA_BASE) {
            case MYSQL_DATA_BASE: {

                return new MySQLDAOFactory();
            }
            default:
                return null;
        }
    }

    public abstract UserService getUserService(UserDAO userDAO);

    public abstract DiseaseService getDiseaseService(DiseaseDAO diseaseDAO, UserService userService);

    public abstract TreatmentService getTreatmentService(TreatmentDAO treatmentDAO, DiseaseService diseaseService);

}

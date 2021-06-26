package ua.kiev.mvovnianko.hospital.service.mysqlimpl;

import org.junit.Assert;
import org.junit.Test;
import ua.kiev.mvovnianko.hospital.dao.impl.JDBCUserDAO;
import ua.kiev.mvovnianko.hospital.dbConnector.MySQLConnectorManager;
import ua.kiev.mvovnianko.hospital.entity.User;
import ua.kiev.mvovnianko.hospital.entity.UserRole;
import ua.kiev.mvovnianko.hospital.entity.builder.UserBuilder;
import ua.kiev.mvovnianko.hospital.service.UserService;

import java.sql.*;
import java.time.LocalDate;
import java.util.Iterator;
import java.util.List;

import static ua.kiev.mvovnianko.hospital.utils.UtilConstants.*;

public class MySQLUserServiceTest  {
    private static final UserService SERVICE = new MySQLUserService(new JDBCUserDAO());

    @Test
    public void shouldCreateNewUser() {
        try (Connection connection = MySQLConnectorManager.getConnection()) {

            int countOfRowsBefore = getCountOfRows(connection, SQL_COUNT_ROWS_USERS);

            SERVICE.createNewUser(getTestUser());

            int countOfRowsAfter = getCountOfRows(connection, SQL_COUNT_ROWS_USERS);

            Assert.assertEquals(countOfRowsBefore, countOfRowsAfter - 1);

            deleteTestUser(connection);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void shouldGetAllUsersByRoleId(){
        try (Connection connection = MySQLConnectorManager.getConnection()) {

            int countOfRows = getCountOfRows(connection, SQL_COUNT_ROWS_PATIENTS);

            List<User> patients = SERVICE.getUsersByRoleId(UserRole.PATIENT.getId(), FULL_NAME);

            Assert.assertTrue(countOfRows == patients.size());

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void shouldGetSortedUsersByRoleId(){
        try (Connection connection = MySQLConnectorManager.getConnection()) {

            List<User> patients = SERVICE.getUsersByRoleId(UserRole.PATIENT.getId(), FULL_NAME);

            boolean isSorted = checkSortingUsersByFullName(patients);

            Assert.assertTrue(isSorted);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //==================================================================================================================
    //==============================================PRIVATE METHODS=====================================================
    //==================================================================================================================

    private boolean checkSortingUsersByFullName(List<User> users){
        Iterator<User> iter = users.iterator();
        User current, previous = iter.next();
        while (iter.hasNext()) {
            current = iter.next();
            if (previous.getFullName().compareTo(current.getFullName()) > 0) {
                return false;
            }
            previous = current;
        }
        return true;
    }


    private int getCountOfRows(Connection connection, String query) throws SQLException {

        Statement statement = connection.createStatement();

        int count = 0;

        ResultSet resultSet = statement.executeQuery(query);

        if (resultSet.next()) {

            count = resultSet.getInt(1);

        }
        return count;
    }

    private User getTestUser() {

        LocalDate birthDate = LocalDate.parse("1991-01-01");
        UserRole userRole = UserRole.PATIENT;

        return new UserBuilder()
                .buildEmail("test@gmail.ru")
                .buildPassword("testPassword")
                .buildFullName("Testing Testing Testing")
                .buildBirthDate(birthDate)
                .buildUserRole(userRole)
                .build();
    }

    private void deleteTestUser(Connection connection) throws SQLException {
        Statement statement = connection.createStatement();
        String query = "DELETE FROM user WHERE email = 'test@gmail.ru'";

        statement.executeUpdate(query);

    }

}
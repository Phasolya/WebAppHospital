package ua.kiev.mvovnianko.hospital.dao;

import ua.kiev.mvovnianko.hospital.dbConnector.MySQLConnectorManager;

import java.sql.*;

import static ua.kiev.mvovnianko.hospital.utils.UtilConstants.MYSQL_COUNT;

/**
 * Created by M.Vovnianko on 13.06.2021.
 */
public interface CommonsOperable {

    default void deleteItemById(int itemId, String query) throws SQLException {

        try (Connection connection = MySQLConnectorManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            MySQLConnectorManager.startTransaction(connection);

            statement.setInt(1, itemId);

            statement.executeUpdate();

            MySQLConnectorManager.commitTransaction(connection);

        }
    }

    default ResultSet getAllItems(Connection connection, String query) throws SQLException {

        Statement statement = connection.createStatement();

        return statement.executeQuery(query);
    }

    default ResultSet getItemById(Connection connection, String query, int itemId) throws SQLException {

        PreparedStatement statement = connection.prepareStatement(query);

        statement.setInt(1, itemId);

        return statement.executeQuery();
    }

    default int countRows(String query) throws SQLException {

        int rowsCount;

        try (Connection connection = MySQLConnectorManager.getConnection();
             Statement statement = connection.createStatement()) {

            MySQLConnectorManager.startTransaction(connection);
            ResultSet resultSet = statement.executeQuery(query);

            if (resultSet.next())
                rowsCount = resultSet.getInt(MYSQL_COUNT);
            else throw new SQLException();

            MySQLConnectorManager.commitTransaction(connection);

            resultSet.close();
        }

        return rowsCount;
    }



}

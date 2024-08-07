package deliverydb.data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Utility class providing helper methods for database access.
 */
public final class DAOUtils {

    /**
     * Establishes a connection to a MySQL database running locally.
     *
     * @param database the name of the database to connect to
     * @param username the username to use for the connection
     * @param password the password for the provided username
     * @return a {@link Connection} object for the specified database
     * @throws DAOException if a database access error occurs
     */
    public static Connection localMySQLConnection(String database, String username, String password) {
        try {
            var host = "localhost";
            var port = "3306";
            var connectionString = "jdbc:mysql://" + host + ":" + port + "/" + database;
            return DriverManager.getConnection(connectionString, username, password);
        } catch (Exception e) {
            throw new DAOException(e);
        }
    }

    /**
     * Prepares a SQL {@link PreparedStatement} with the given query and parameters.
     *
     * @param connection the {@link Connection} object used to prepare the statement
     * @param query the SQL query to prepare
     * @param values the values to set in the query parameters
     * @return a {@link PreparedStatement} object with the provided query and parameters
     * @throws SQLException if a database access error occurs or the SQL statement is invalid
     */
    public static PreparedStatement prepare(Connection connection, String query, Object... values) throws SQLException {
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(query);
            for (int i = 0; i < values.length; i++) {
                statement.setObject(i + 1, values[i]);
            }
            return statement;
        } catch (Exception e) {
            if (statement != null) {
                statement.close();
            }
            throw e;
        }
    }
}

package deliverydb;

import java.sql.SQLException;
import deliverydb.controller.FirstController;
import deliverydb.data.DAOUtils;
import deliverydb.model.Model;

/**
 * The App class is the entry point for the DeliveryDB application.
 * It establishes a connection to the local MySQL database, initializes
 * the data model, and starts the first controller.
 */
public final class App {

    private App() { }
    /**
     * The main method is the entry point of the application.
     * It establishes a connection to the database, initializes the data model,
     * and starts the first controller.
     *
     * @param args the command line arguments (not used)
     * @throws SQLException if a database access error occurs
     */
    public static void main(String[] args) throws SQLException {
        // Establish a connection to the local MySQL database named "DeliveryDB" using the "root" user without a password.
        var connection = DAOUtils.localMySQLConnection("DeliveryDB", "root", "");

        // Initialize the data model from the established database connection.
        var model = Model.fromConnection(connection);

        // Start the first controller with the initialized model and the database connection.
        new FirstController(model, connection);
    }
}

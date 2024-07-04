package deliveryDB;

import java.sql.SQLException;

import deliveryDB.controller.FirstController;
import deliveryDB.data.DAOUtils;
import deliveryDB.model.Model;

public final class App {

    public static void main(String[] args) throws SQLException {
        // If you want to get a feel of the application before having implemented
        // all methods, you can pass the controller a mocked model instead:
        //
      
        var connection = DAOUtils.localMySQLConnection("DeliveryDatabase", "root", "");
        var model = Model.fromConnection(connection);
        new FirstController(model, connection);
 
    }
}

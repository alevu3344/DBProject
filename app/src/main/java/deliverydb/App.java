package deliverydb;

import java.sql.SQLException;

import deliverydb.controller.FirstController;
import deliverydb.data.DAOUtils;
import deliverydb.model.Model;

public final class App {

    public static void main(String[] args) throws SQLException {
        var connection = DAOUtils.localMySQLConnection("DeliveryDB", "root", "");
        var model = Model.fromConnection(connection);
        new FirstController(model, connection);
    }
}



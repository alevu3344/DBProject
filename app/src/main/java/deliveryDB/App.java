package deliveryDB;

import java.sql.SQLException;

import deliveryDB.controller.FirstController;
import deliveryDB.data.DAOUtils;
import deliveryDB.model.DelModel;

public final class App {

    public static void main(String[] args) throws SQLException {
        var connection = DAOUtils.localMySQLConnection("DeliveryDatabase", "root", "");
        var model = DelModel.fromConnection(connection);
        new FirstController(model, connection);
    }
}

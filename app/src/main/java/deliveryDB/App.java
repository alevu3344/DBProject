package deliveryDB;

import java.sql.SQLException;

import deliveryDB.controller.FirstController;
import deliveryDB.data.DAOUtils;
import deliveryDB.model.Model;

public final class App {

    public static void main(String[] args) throws SQLException {
        var connection = DAOUtils.localMySQLConnection("DEL_DB4_SenzaStatoOrdine", "root", "");
        var model = Model.fromConnection(connection);
        new FirstController(model, connection);
    }
}



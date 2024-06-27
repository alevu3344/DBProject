package db_lab;

import db_lab.controller.FirstController;
import db_lab.controller.LoginController;
import db_lab.data.DAOException;
import db_lab.data.DAOUtils;
import db_lab.model.Model;
import db_lab.view.FirstPage;
import db_lab.view.LoginPage;

import java.sql.SQLException;

public final class App {

    public static void main(String[] args) throws SQLException {
        // If you want to get a feel of the application before having implemented
        // all methods, you can pass the controller a mocked model instead:
        //
      
        var connection = DAOUtils.localMySQLConnection("Tessiland", "root", "");
        var model = Model.fromConnection(connection);
        var controller = new FirstController(model, connection);
 
    }
}

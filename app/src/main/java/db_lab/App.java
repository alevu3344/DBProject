package db_lab;

import db_lab.data.DAOUtils;
import db_lab.model.Model;
import db_lab.view.MainMenu;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.sql.SQLException;

public class App extends Application {

    

    @Override
    public void start(Stage primaryStage) {
 
        var connection = DAOUtils.localMySQLConnection("Tessiland", "root", "");
        var model = Model.fromConnection(connection);
        var view = new MainMenu(primaryStage);
        var controller = new Controller(model, view);
        view.setController(controller);
        controller.userRequestedInitialPage();
    }
}


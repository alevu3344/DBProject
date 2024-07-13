package deliveryDB.controller;


import java.sql.Connection;


import deliveryDB.model.DelModel;
import deliveryDB.view.FirstPage;
import deliveryDB.view.LoginPage;
import deliveryDB.view.RegisterPage;

import java.util.Objects;

public class FirstController {
    private final DelModel model;
    private FirstPage firstPage;
    private Runnable onClose;

    public FirstController(DelModel model, Connection connection) {
        Objects.requireNonNull(model, "MainController created with null model");
        Objects.requireNonNull(connection, "MainController created with null connection");

        this.onClose = () -> {
            try{
                connection.close();
            }
            catch(Exception e){

            }
        };

        this.firstPage = new FirstPage(this.onClose);
        this.model = model;

        // Set this controller as the loginView's controller
        this.firstPage.setController(this);
    }

    public void handleLoginButtonClick(){
        var loginView = new LoginPage(this.firstPage.getMainFrame());
        new LoginController(loginView, this.model, this);

    }

    public void handleRegisterButtonClick(){
        var registerView = new RegisterPage(this.firstPage.getMainFrame());
        new RegisterController(registerView,this.model, this);
    }    

    public void backToFirstPage(){
        this.firstPage.redraw();
    }

    
}

package db_lab.controller;

import db_lab.data.DAOException;
import db_lab.model.Model;
import db_lab.view.FirstPage;
import db_lab.view.LoginPage;
import db_lab.view.RegisterPage;
import db_lab.view.RestaurantsPage;

import java.util.Optional;

import java.util.Objects;

public class FirstController {
    private final Model model;
    private final FirstPage firstPage;
    private boolean loggingIn;
    private Optional<LoginController> loginCtrl;
    private Optional<RegisterController> registerCtrl;

    public FirstController(Model model, FirstPage firstPage) {
        Objects.requireNonNull(model, "MainController created with null model");
        Objects.requireNonNull(firstPage, "MainController created with null loginView");
        this.loginCtrl = Optional.empty();
        this.firstPage = firstPage;
        this.model = model;
        this.loggingIn = false;

        // Set this controller as the loginView's controller
        this.firstPage.setController(this);
    }

    public void handleLoginButtonClick(){
        var loginView = new LoginPage(this.firstPage.getMainFrame());
        this.loginCtrl = Optional.of(new LoginController(loginView, this.model));

    }

    public void handleRegisterButtonClick(){
        var registerView = new RegisterPage(this.firstPage.getMainFrame());
        this.registerCtrl = Optional.of(new RegisterController(registerView,this.model));
    }    

    
}

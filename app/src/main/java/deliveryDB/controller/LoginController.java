package deliveryDB.controller;

import javax.swing.BoxLayout;

import deliveryDB.data.User;
import deliveryDB.model.DelModel;
import deliveryDB.view.LoginPage;
import deliveryDB.view.PreviewPage;

import java.util.Optional;

import java.util.Objects;

public class LoginController {

    private final DelModel model;
    private final LoginPage loginView;
    private Optional<PreviewsController> resCtrl;
    private FirstController firstCtrl;

    public LoginController(LoginPage loginView, DelModel model, FirstController firstCtrl) {
        Objects.requireNonNull(model, "MainController created with null model");
        Objects.requireNonNull(loginView, "MainController created with null loginView");
        this.resCtrl = Optional.empty();
        this.loginView = loginView;
        this.model = model;
        this.firstCtrl = firstCtrl;

        // Set this controller as the loginView's controller
        loginView.setController(this);
    }

    public void handleLogin(String username, String password) {

        //If username and password are correct in the UTENTI table in the database, then login is successful    
        if (this.model.login(username, password)) {
            loginView.displayMessage("Login successful");
            //var boxFrame = loginView.getMainFrame();
            //boxFrame.setLayout(new BoxLayout(boxFrame.getContentPane(), BoxLayout.PAGE_AXIS));
            //var previewPage = new PreviewPage(boxFrame);
            //this.resCtrl = Optional.of(new PreviewsController(previewPage, this.model));
            //previewPage.setController(this.resCtrl.get());
            
        } else {
            loginView.displayMessage("Invalid username or password");
        }
    }

    public void handleBack(){
        this.firstCtrl.backToFirstPage();
    }
}

package deliveryDB.controller;

import javax.swing.BoxLayout;

import deliveryDB.model.Model;
import deliveryDB.view.LoginPage;
import deliveryDB.view.RestaurantsPage;

import java.util.Optional;

import java.util.Objects;

public class LoginController {

    private final Model model;
    private final LoginPage loginView;
    private Optional<RestaurantsController> resCtrl;
    private FirstController firstCtrl;

    public LoginController(LoginPage loginView, Model model, FirstController firstCtrl) {
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
        // Replace this with actual authentication logic
        System.out.println("Username: " + username);
        System.out.println("Password: " + password);
        if (username.equals("admin") && password.equals("admin123")) {
            loginView.displayMessage("Login successful");
            var boxFrame = loginView.getMainFrame();
            boxFrame.setLayout(new BoxLayout(boxFrame.getContentPane(), BoxLayout.PAGE_AXIS));
            var resPage = new RestaurantsPage(boxFrame);
            this.resCtrl = Optional.of(new RestaurantsController(resPage, this.model));
            resPage.setController(this.resCtrl.get());
            
        } else {
            loginView.displayMessage("Invalid username or password");
        }
    }

    public void handleBack(){
        this.firstCtrl.backToFirstPage();
    }
}

package deliveryDB.controller;

import deliveryDB.data.User;
import deliveryDB.model.DelModel;
import deliveryDB.view.LoginPage;
import deliveryDB.view.RestaurantsPage;

import java.util.Optional;

import java.util.Objects;

public class LoginController implements Controller {

    private final DelModel model;
    private final LoginPage loginView;
    private Optional<ResController> resCtrl;
    private Optional<AdminController> adminCtrl;
    private Optional<DeliveryCtrl> delCtrl;
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

        // If username and password are correct in the UTENTI table in the database,
        // then login is successful
        var userType = this.model.login(username, password);
        if (userType.equals(Optional.of(User.USER_TYPE.CUSTOMER))) {

            loginView.displayMessage("Login successful");
            var boxFrame = loginView.getMainFrame();
            var view = new RestaurantsPage(boxFrame);
            this.resCtrl = Optional.of(new ResController(this, view, this.model));
            view.setController(this.resCtrl.get());

        } else if (userType.equals(Optional.of(User.USER_TYPE.DELIVERY_PERSON))) {
            loginView.displayMessage("Login successful");
            this.delCtrl = Optional.of(new DeliveryCtrl(this, loginView.getMainFrame(), this.model));
        }

        else if (userType.equals(Optional.of(User.USER_TYPE.ADMIN))) {

            loginView.displayMessage("Login successful");
            var boxFrame = loginView.getMainFrame();
            this.adminCtrl = Optional.of(new AdminController(this, boxFrame, this.model));
        }

        else {
            loginView.displayMessage("Invalid username or password");
        }
    }

    public void handleBack() {
        this.firstCtrl.backToFirstPage();
    }

    @Override
    public void show() {
        this.loginView.setupComponents();
    }
}

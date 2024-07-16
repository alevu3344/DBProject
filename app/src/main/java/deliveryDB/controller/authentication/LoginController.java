package deliveryDB.controller.authentication;

import deliveryDB.controller.Controller;
import deliveryDB.controller.admin.AdminController;
import deliveryDB.controller.customer.ResController;
import deliveryDB.controller.delivery.DeliveryCtrl;
import deliveryDB.data.User;
import deliveryDB.model.Model;
import deliveryDB.view.authentication.LoginPage;

import java.util.Optional;

import javax.swing.JFrame;

public class LoginController implements Controller {

    private final Model model;
    private final LoginPage loginView;
    private Controller firstCtrl;
    private JFrame mainFrame;

    public LoginController(JFrame mainFrame, Model model, Controller firstCtrl) {
        this.mainFrame = mainFrame;
        this.loginView = new LoginPage(mainFrame, this);
        this.model = model;
        this.firstCtrl = firstCtrl;
    }

    public void handleLogin(String username, String password) {

        var userType = this.model.login(username, password);
        if (userType.equals(Optional.of(User.USER_TYPE.CUSTOMER))) {

            loginView.displayMessage("Login successful");

            new ResController(this, this.mainFrame, this.model);

        } else if (userType.equals(Optional.of(User.USER_TYPE.DELIVERY_PERSON))) {
            loginView.displayMessage("Login successful");
            new DeliveryCtrl(this, this.mainFrame, this.model);
        }

        else if (userType.equals(Optional.of(User.USER_TYPE.ADMIN))) {

            loginView.displayMessage("Login successful");
            new AdminController(this, this.mainFrame, this.model);
        }

        else {
            loginView.displayMessage("Invalid username or password");
        }
    }

    public void handleBack() {
        this.firstCtrl.show();
    }

    @Override
    public void show() {
        this.loginView.setupComponents();
    }
}

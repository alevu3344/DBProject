package deliverydb.controller.authentication;

import java.util.Optional;

import javax.swing.JFrame;

import deliverydb.controller.Controller;
import deliverydb.controller.admin.AdminController;
import deliverydb.controller.customer.ResController;
import deliverydb.controller.delivery.DeliveryCtrl;
import deliverydb.data.User;
import deliverydb.model.Model;
import deliverydb.view.authentication.LoginPage;

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

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

/**
 * The LoginController class manages the login process for the DeliveryDB application.
 * It handles user login, directs users to the appropriate controller based on their role,
 * and provides feedback on login success or failure.
 */
public class LoginController implements Controller {

    private final Model model;
    private final LoginPage loginView;
    private Controller firstCtrl;
    private JFrame mainFrame;

    /**
     * Constructs a LoginController object.
     *
     * @param mainFrame the main application frame
     * @param model the data model for the application
     * @param firstCtrl the controller to return to when the login view is exited
     */
    public LoginController(final JFrame mainFrame, final Model model, final Controller firstCtrl) {
        this.mainFrame = mainFrame;
        this.loginView = new LoginPage(mainFrame, this);
        this.model = model;
        this.firstCtrl = firstCtrl;
    }

    /**
     * Handles the login process for a user.
     *
     * @param username the username entered by the user
     * @param password the password entered by the user
     */
    public void handleLogin(final String username, final String password) {
        var userType = this.model.login(username, password);
        if (userType.equals(Optional.of(User.Usertype.CUSTOMER))) {
            loginView.displayMessage("Login successful");
            new ResController(this, this.mainFrame, this.model);
        } else if (userType.equals(Optional.of(User.Usertype.DELIVERY_PERSON))) {
            loginView.displayMessage("Login successful");
            new DeliveryCtrl(this, this.mainFrame, this.model);
        } else if (userType.equals(Optional.of(User.Usertype.ADMIN))) {
            loginView.displayMessage("Login successful");
            new AdminController(this, this.mainFrame, this.model);
        } else {
            loginView.displayMessage("Invalid username or password");
        }
    }

    /**
     * Handles the action to go back to the previous controller's view.
     */
    public void handleBack() {
        this.firstCtrl.show();
    }

    /**
     * Displays the login page view associated with this controller.
     */
    @Override
    public void show() {
        this.loginView.setupComponents();
    }
}

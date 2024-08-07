package deliverydb.controller.authentication;

import java.util.List;

import javax.swing.JFrame;

import deliverydb.controller.Controller;
import deliverydb.data.User;
import deliverydb.model.Model;
import deliverydb.view.authentication.RegisterPage;

/**
 * The RegisterController class handles the user registration process
 * within the DeliveryDB application. It manages the registration view,
 * processes user input, and communicates with the data model to register new users.
 */
public class RegisterController implements Controller {

    private final Model model;
    private RegisterPage registerView;
    private Controller firstCtrl;
    private JFrame mainFrame;

    /**
     * Constructs a RegisterController object.
     *
     * @param mainFrame the main application frame
     * @param model the data model for the application
     * @param firstCtrl the controller to return to after successful registration
     */
    public RegisterController(JFrame mainFrame, Model model, Controller firstCtrl) {
        this.model = model;
        this.mainFrame = mainFrame;
        this.registerView = new RegisterPage(mainFrame, this);
        this.firstCtrl = firstCtrl;
    }

    /**
     * Handles the user registration process.
     *
     * @param type the type of user to register
     * @param list the list of user details required for registration
     */
    public void handleRegistration(User.USER_TYPE type, List<String> list) {
        var success = this.model.userRegister(type, list.get(0),
                list.get(1),
                list.get(2),
                list.get(3),
                list.get(4),
                list.get(5),
                list.get(6));

        if (success) {
            this.firstCtrl.show();
        } else {
            this.registerView.displayMessage("Username already exists!");
        }
    }

    /**
     * Handles the action to go back to the previous controller's view.
     */
    public void handleBack() {
        this.firstCtrl.show();
    }

    /**
     * Displays the registration page view associated with this controller.
     */
    @Override
    public void show() {
        this.registerView = new RegisterPage(mainFrame, this);
    }
}

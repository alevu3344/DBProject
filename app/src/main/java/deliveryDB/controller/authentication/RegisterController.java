package deliveryDB.controller.authentication;

import java.util.List;

import javax.swing.JFrame;

import deliveryDB.controller.Controller;
import deliveryDB.data.User;
import deliveryDB.model.Model;
import deliveryDB.view.authentication.RegisterPage;

public class RegisterController implements Controller {

    private final Model model;
    private RegisterPage registerView;
    private Controller firstCtrl;
    private JFrame mainFrame;

    public RegisterController(JFrame mainFrame, Model model, Controller firstCtrl) {
        this.model = model;
        this.mainFrame = mainFrame;
        this.registerView = new RegisterPage(mainFrame, this);
        this.firstCtrl = firstCtrl;
    }

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

    public void handleBack() {
        this.firstCtrl.show();
    }

    @Override
    public void show() {
        this.registerView = new RegisterPage(mainFrame, this);
    }
}

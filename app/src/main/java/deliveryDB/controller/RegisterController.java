package deliveryDB.controller;

import java.util.List;

import java.util.Objects;

import deliveryDB.data.User;
import deliveryDB.model.DelModel;
import deliveryDB.view.RegisterPage;

public class RegisterController {

    private final DelModel model;
    private final RegisterPage registerView;
    private FirstController firstCtrl;

    public RegisterController(RegisterPage registerView, DelModel model, FirstController firstCtrl) {
        Objects.requireNonNull(model, "RegisterController created with null model");
        Objects.requireNonNull(registerView, "RegisterController created with null registerView");
        this.model = model;
        this.registerView = registerView;
        this.firstCtrl = firstCtrl;

        // Set this controller as the registerView's controller
        registerView.setController(this);
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
            this.firstCtrl.backToFirstPage();
        } else {
            this.registerView.displayMessage("Username already exists!");
        }
                
    }

    public void handleBack(){
        this.firstCtrl.backToFirstPage();
    }
}

package deliveryDB.controller;

import java.util.List;

import java.util.Objects;

import deliveryDB.data.User;
import deliveryDB.data.User.USER_TYPE;
import deliveryDB.model.DelModel;
import deliveryDB.view.FirstPage;
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

        //check if username is already in use
        if (model.checkUsername(list.get(5))) {
            registerView.displayMessage("Username already in use");
            return;
        }
        System.out.println("Registration details:");
        System.out.println("Name: " + list.get(0));
        System.out.println("Surname: " + list.get(1));
        System.out.println("Street: " + list.get(2));
        System.out.println("Number: " + list.get(3));
        System.out.println("City: " + list.get(4));
        System.out.println("Username: " + list.get(5));
        System.out.println("Password: " + list.get(6));
        System.out.println("Type: " + type);
        registerView.displayMessage("Registration successful");
        // Optionally, you can proceed to another page or update the UI as needed
    }

    public void handleBack(){
        this.firstCtrl.backToFirstPage();
    }
}

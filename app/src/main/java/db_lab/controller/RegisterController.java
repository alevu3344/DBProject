package db_lab.controller;

import db_lab.model.Model;
import db_lab.view.RegisterPage;

import java.util.Objects;

public class RegisterController {

    private final Model model;
    private final RegisterPage registerView;

    public RegisterController(RegisterPage registerView, Model model) {
        Objects.requireNonNull(model, "RegisterController created with null model");
        Objects.requireNonNull(registerView, "RegisterController created with null registerView");
        this.model = model;
        this.registerView = registerView;

        // Set this controller as the registerView's controller
        registerView.setController(this);
    }

    public void handleRegistration(String name, String surname, String address, String username, String password) {
        // Replace this with actual registration logic
        // For now, just print the registration details
        System.out.println("Registration details:");
        System.out.println("Name: " + name);
        System.out.println("Surname: " + surname);
        System.out.println("Address: " + address);
        System.out.println("Username: " + username);
        System.out.println("Password: " + password);
        registerView.displayMessage("Registration successful");
        // Optionally, you can proceed to another page or update the UI as needed
    }
}

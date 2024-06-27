package db_lab.view;

import javax.swing.*;

import db_lab.controller.RegisterController;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Optional;

public class RegisterPage implements ActionListener {

    // Components of the Form
    private Container container;
    private JLabel nameLabel;
    private JLabel surnameLabel;
    private JLabel addressLabel;
    private JLabel usernameLabel;
    private JLabel passwordLabel;
    private JTextField nameTextField;
    private JTextField surnameTextField;
    private JTextField addressTextField;
    private JTextField usernameTextField;
    private JPasswordField passwordField;
    private JButton registerButton;
    private JButton resetButton;
    private JButton backButton;
    private JLabel messageLabel;

    private Optional<RegisterController> controller;
    private final JFrame mainFrame;

    // Constructor to set up the GUI components
    public RegisterPage(JFrame mainFrame) {
        this.controller = Optional.empty();
        this.mainFrame = mainFrame;
        this.setupComponents();
        this.addComponentsToFrame();
        System.out.println("Constructor of register page");
    }

    private void setupComponents() {
        container = mainFrame.getContentPane();
        container.removeAll();
        container.validate();
        container.repaint();

        nameLabel = new JLabel("Name");
        surnameLabel = new JLabel("Surname");
        addressLabel = new JLabel("Address");
        usernameLabel = new JLabel("Username");
        passwordLabel = new JLabel("Password");

        nameTextField = new JTextField();
        surnameTextField = new JTextField();
        addressTextField = new JTextField();
        usernameTextField = new JTextField();
        passwordField = new JPasswordField();

        registerButton = new JButton("Register");
        resetButton = new JButton("Reset");
        backButton = new JButton("Back");

        messageLabel = new JLabel("");

        registerButton.addActionListener(this);
        resetButton.addActionListener(this);
        backButton.addActionListener(this);
    }

    public JFrame getMainFrame() {
        return this.mainFrame;
    }

    private void addComponentsToFrame() {
        container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));

        container.add(nameLabel);
        container.add(nameTextField);
        container.add(surnameLabel);
        container.add(surnameTextField);
        container.add(addressLabel);
        container.add(addressTextField);
        container.add(usernameLabel);
        container.add(usernameTextField);
        container.add(passwordLabel);
        container.add(passwordField);
        container.add(registerButton);
        container.add(resetButton);
        container.add(backButton);
        container.add(messageLabel);

        nameLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        surnameLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        addressLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        usernameLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        passwordLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        nameTextField.setAlignmentX(Component.CENTER_ALIGNMENT);
        surnameTextField.setAlignmentX(Component.CENTER_ALIGNMENT);
        addressTextField.setAlignmentX(Component.CENTER_ALIGNMENT);
        usernameTextField.setAlignmentX(Component.CENTER_ALIGNMENT);
        passwordField.setAlignmentX(Component.CENTER_ALIGNMENT);
        registerButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        resetButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        backButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        messageLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        Dimension fieldDimension = new Dimension(200, 30);
        nameLabel.setPreferredSize(fieldDimension);
        surnameLabel.setPreferredSize(fieldDimension);
        addressLabel.setPreferredSize(fieldDimension);
        usernameLabel.setPreferredSize(fieldDimension);
        passwordLabel.setPreferredSize(fieldDimension);
        nameTextField.setPreferredSize(fieldDimension);
        surnameTextField.setPreferredSize(fieldDimension);
        addressTextField.setPreferredSize(fieldDimension);
        usernameTextField.setPreferredSize(fieldDimension);
        passwordField.setPreferredSize(fieldDimension);
        registerButton.setPreferredSize(new Dimension(100, 30));
        resetButton.setPreferredSize(new Dimension(100, 30));
        backButton.setPreferredSize(new Dimension(100, 30));
        messageLabel.setPreferredSize(new Dimension(250, 30));

        mainFrame.pack(); // Adjust frame size
    }

    // Action event handler
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == registerButton) {
            String name = nameTextField.getText();
            String surname = surnameTextField.getText();
            String address = addressTextField.getText();
            String username = usernameTextField.getText();
            String password = new String(passwordField.getPassword());
            
            // Inform the controller about the registration attempt
            controller.ifPresent(ctrl -> ctrl.handleRegistration(name, surname, address, username, password));
        } else if (e.getSource() == resetButton) {
            nameTextField.setText("");
            surnameTextField.setText("");
            addressTextField.setText("");
            usernameTextField.setText("");
            passwordField.setText("");
            messageLabel.setText("");
        } else if (e.getSource() == backButton) {
            this.controller.get().handleBack();
        }
    }

    public void setController(RegisterController controller) {
        this.controller = Optional.of(controller);
    }

    public void displayMessage(String message) {
        messageLabel.setText(message);
    }
}

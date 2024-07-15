package deliveryDB.view.authentication;

import javax.swing.*;

import deliveryDB.controller.authentication.LoginController;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Optional;

public class LoginPage implements ActionListener {

    // Components of the Form
    private JLabel userLabel;
    private JLabel passwordLabel;
    private JTextField userTextField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JButton resetButton;
    private JButton backButton;
    private JLabel messageLabel;

    private Optional<LoginController> controller;
    private final JFrame mainFrame;

    // Constructor to set up the GUI components
    public LoginPage(JFrame mainFrame) {
        this.controller = Optional.empty();
        this.mainFrame = mainFrame;
        this.setupComponents();
        System.out.println("Constructor of login page");
    }

    public void setupComponents() {
        this.mainFrame.getContentPane().removeAll();
        this.mainFrame.getContentPane().validate();
        this.mainFrame.getContentPane().repaint();
        Container container = mainFrame.getContentPane();
        container.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        userLabel = new JLabel("Username:");
        passwordLabel = new JLabel("Password:");

        userTextField = new JTextField();
        passwordField = new JPasswordField();

        loginButton = new JButton("Login");
        resetButton = new JButton("Reset");
        backButton = new JButton("Back");

        messageLabel = new JLabel("");

        // Set the preferred sizes for the components
        userTextField.setPreferredSize(new Dimension(150, 25));
        passwordField.setPreferredSize(new Dimension(150, 25));
        loginButton.setPreferredSize(new Dimension(80, 30));
        resetButton.setPreferredSize(new Dimension(80, 30));
        backButton.setPreferredSize(new Dimension(80, 30));

        loginButton.addActionListener(this);
        resetButton.addActionListener(this);
        backButton.addActionListener(this);

        // Add components to container with GridBagConstraints
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.gridx = 0;
        gbc.gridy = 0;
        container.add(userLabel, gbc);

        gbc.gridx = 1;
        container.add(userTextField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        container.add(passwordLabel, gbc);

        gbc.gridx = 1;
        container.add(passwordField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        container.add(loginButton, gbc);

        gbc.gridx = 1;
        container.add(resetButton, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        container.add(backButton, gbc);

        gbc.gridy = 4;
        container.add(messageLabel, gbc);

        this.mainFrame.pack();
    }

    public JFrame getMainFrame() {
        return this.mainFrame; 
    }

    // Action event handler
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == loginButton) {
            String userText = userTextField.getText();
            String passwordText = new String(passwordField.getPassword());
            // Inform the controller about the login attempt
            controller.ifPresent(ctrl -> ctrl.handleLogin(userText, passwordText));
        } else if (e.getSource() == resetButton) {
            userTextField.setText("");
            passwordField.setText("");
            messageLabel.setText("");
        } else if (e.getSource() == backButton) {
            this.controller.get().handleBack();
        }
    }

    public void setController(LoginController controller) {
        this.controller = Optional.of(controller);
    }

    public void displayMessage(String message) {
        messageLabel.setText(message);
    }
}

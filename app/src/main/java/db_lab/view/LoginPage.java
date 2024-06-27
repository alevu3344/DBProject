package db_lab.view;

import javax.swing.*;

import db_lab.controller.LoginController;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Optional;
import java.awt.event.WindowEvent;

public class LoginPage implements ActionListener {

    // Components of the Form
    private Container container;
    private JLabel userLabel;
    private JLabel passwordLabel;
    private JTextField userTextField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JButton resetButton;
    private JLabel messageLabel;

    private Optional<LoginController> controller;
    private final JFrame mainFrame;

    // Constructor to set up the GUI components
    public LoginPage(JFrame mainFrame) {
        this.controller = Optional.empty();
        this.mainFrame = mainFrame;
        this.setupComponents();
        this.addComponentsToFrame();
        System.out.println("Constructor of login page");
    }

    

    private void setupComponents() {
        container = mainFrame.getContentPane();
        container.removeAll();
        container.validate();
        container.repaint();

        

        userLabel = new JLabel("Username");
        passwordLabel = new JLabel("Password");

        userTextField = new JTextField();
        passwordField = new JPasswordField();

        loginButton = new JButton("Login");
        resetButton = new JButton("Reset");

        messageLabel = new JLabel("");

        loginButton.addActionListener(this);
        resetButton.addActionListener(this);
    }

    public JFrame getMainFrame(){
        return this.mainFrame;
    }



    private void addComponentsToFrame() {
        
        container.add(userLabel);
        container.add(userTextField);
        container.add(passwordLabel);
        container.add(passwordField);
        container.add(loginButton);
        container.add(resetButton);
        container.add(messageLabel);

        userLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        passwordLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        userTextField.setAlignmentX(Component.CENTER_ALIGNMENT);
        passwordField.setAlignmentX(Component.CENTER_ALIGNMENT);
        loginButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        resetButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        messageLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        userLabel.setPreferredSize(new Dimension(100, 30));
        passwordLabel.setPreferredSize(new Dimension(100, 30));
        userTextField.setPreferredSize(new Dimension(150, 30));
        passwordField.setPreferredSize(new Dimension(150, 30));
        loginButton.setPreferredSize(new Dimension(100, 30));
        resetButton.setPreferredSize(new Dimension(100, 30));
        messageLabel.setPreferredSize(new Dimension(250, 30));

        //container.validate();
        //container.repaint();
//------------------------------------------
        this.mainFrame.pack();            //
//----------------------------------------
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
        }
    }

    public void setController(LoginController controller) {
        this.controller = Optional.of(controller);
    }

    public void displayMessage(String message) {
        messageLabel.setText(message);
    }
}

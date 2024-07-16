package deliveryDB.view.authentication;

import javax.swing.*;

import deliveryDB.controller.authentication.LoginController;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.function.Consumer;

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

    private LoginController controller;
    private final JFrame mainFrame;

    // Constructor to set up the GUI components
    public LoginPage(JFrame mainFrame, LoginController controller) {
        this.controller = controller;
        this.mainFrame = mainFrame;
        this.setupComponents();
    }

    private void freshPane(Consumer<Container> consumer) {
        Container cp = this.mainFrame.getContentPane();
        cp.removeAll();
        cp.validate();
        cp.repaint();
        consumer.accept(cp);
        this.mainFrame.pack();
    }

    public void setupComponents() {
        freshPane(container -> {
            container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));

            userLabel = new JLabel("Username:");
            passwordLabel = new JLabel("Password:");

            userTextField = new JTextField();
            passwordField = new JPasswordField();

            loginButton = new JButton("Login");
            resetButton = new JButton("Reset");
            backButton = new JButton("Back");

            messageLabel = new JLabel("");

            // Set the preferred sizes and maximum sizes for the components
            Dimension textFieldSize = new Dimension(150, 25);
            userTextField.setPreferredSize(textFieldSize);
            userTextField.setMaximumSize(textFieldSize);
            passwordField.setPreferredSize(textFieldSize);
            passwordField.setMaximumSize(textFieldSize);

            Dimension buttonSize = new Dimension(80, 30);
            loginButton.setPreferredSize(buttonSize);
            resetButton.setPreferredSize(buttonSize);
            backButton.setPreferredSize(buttonSize);

            loginButton.addActionListener(this);
            resetButton.addActionListener(this);
            backButton.addActionListener(this);

            // Create horizontal boxes for each row and center them
            Box userBox = Box.createHorizontalBox();
            userBox.add(Box.createHorizontalGlue()); // Center horizontally
            userBox.add(userLabel);
            userBox.add(Box.createHorizontalStrut(10));
            userBox.add(userTextField);
            userBox.add(Box.createHorizontalGlue()); // Center horizontally

            Box passwordBox = Box.createHorizontalBox();
            passwordBox.add(Box.createHorizontalGlue()); // Center horizontally
            passwordBox.add(passwordLabel);
            passwordBox.add(Box.createHorizontalStrut(10));
            passwordBox.add(passwordField);
            passwordBox.add(Box.createHorizontalGlue()); // Center horizontally

            // Center the userBox and passwordBox horizontally in a vertical box
            Box centerBox = Box.createVerticalBox();
            centerBox.add(Box.createVerticalStrut(20)); // Add some vertical space at the top
            centerBox.add(userBox);
            centerBox.add(Box.createVerticalStrut(10));
            centerBox.add(passwordBox);
            centerBox.add(Box.createVerticalStrut(10));

            // Create a horizontal box for buttons and center it
            Box buttonBox = Box.createHorizontalBox();
            buttonBox.add(Box.createHorizontalGlue()); // Center horizontally
            buttonBox.add(loginButton);
            buttonBox.add(Box.createHorizontalStrut(10));
            buttonBox.add(resetButton);
            buttonBox.add(Box.createHorizontalGlue()); // Center horizontally

            // Add components to the container with vertical spacing
            container.add(Box.createVerticalGlue()); // Center the content vertically
            container.add(centerBox);
            container.add(Box.createVerticalStrut(10));
            container.add(buttonBox);
            container.add(Box.createVerticalStrut(10));
            container.add(backButton);
            container.add(Box.createVerticalStrut(10));
            container.add(messageLabel);
            container.add(Box.createVerticalGlue()); // Fill remaining space

        });
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == loginButton) {
            String userText = userTextField.getText();
            String passwordText = new String(passwordField.getPassword());
            // Inform the controller about the login attempt
            controller.handleLogin(userText, passwordText);
        } else if (e.getSource() == resetButton) {
            userTextField.setText("");
            passwordField.setText("");
            messageLabel.setText("");
        } else if (e.getSource() == backButton) {
            this.controller.handleBack();
        }
    }


    public void displayMessage(String message) {
        messageLabel.setText(message);
    }
}

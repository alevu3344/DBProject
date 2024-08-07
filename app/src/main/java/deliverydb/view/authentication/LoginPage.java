package deliverydb.view.authentication;

import deliverydb.controller.authentication.LoginController;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.function.Consumer;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

/**
 * Represents the login page for the application where users can enter their credentials to log in.
 */
public final class LoginPage implements ActionListener {

    // Components of the Form
    private final JLabel userLabel;
    private final JLabel passwordLabel;
    private final JTextField userTextField;
    private final JPasswordField passwordField;
    private final JButton loginButton;
    private final JButton resetButton;
    private final JButton backButton;
    private final JLabel messageLabel;

    private final LoginController controller;
    private final JFrame mainFrame;

    /**
     * Constructs a LoginPage with the given JFrame and LoginController.
     *
     * @param mainFrame the main frame of the application
     * @param controller the controller that handles login actions
     */
    public LoginPage(final JFrame mainFrame, final LoginController controller) {
        this.controller = controller;
        this.mainFrame = mainFrame;

        this.userLabel = new JLabel("Username:");
        this.passwordLabel = new JLabel("Password:");
        this.userTextField = new JTextField();
        this.passwordField = new JPasswordField();
        this.loginButton = new JButton("Login");
        this.resetButton = new JButton("Reset");
        this.backButton = new JButton("Back");
        this.messageLabel = new JLabel("");

        setupComponents();
    }

    private void freshPane(final Consumer<Container> consumer) {
        final Container cp = this.mainFrame.getContentPane();
        cp.removeAll();
        cp.validate();
        cp.repaint();
        consumer.accept(cp);
        this.mainFrame.pack();
    }

    /**
     * Sets up the components and layout of the login page.
     */
    public void setupComponents() {
        freshPane(container -> {
            container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));

            // Set the preferred sizes and maximum sizes for the components
            final Dimension textFieldSize = new Dimension(150, 25);
            userTextField.setPreferredSize(textFieldSize);
            userTextField.setMaximumSize(textFieldSize);
            passwordField.setPreferredSize(textFieldSize);
            passwordField.setMaximumSize(textFieldSize);

            final Dimension buttonSize = new Dimension(80, 30);
            loginButton.setPreferredSize(buttonSize);
            resetButton.setPreferredSize(buttonSize);
            backButton.setPreferredSize(buttonSize);

            loginButton.addActionListener(this);
            resetButton.addActionListener(this);
            backButton.addActionListener(this);

            // Create horizontal boxes for each row and center them
            final Box userBox = Box.createHorizontalBox();
            userBox.add(Box.createHorizontalGlue()); // Center horizontally
            userBox.add(userLabel);
            userBox.add(Box.createHorizontalStrut(10));
            userBox.add(userTextField);
            userBox.add(Box.createHorizontalGlue()); // Center horizontally

            final Box passwordBox = Box.createHorizontalBox();
            passwordBox.add(Box.createHorizontalGlue()); // Center horizontally
            passwordBox.add(passwordLabel);
            passwordBox.add(Box.createHorizontalStrut(10));
            passwordBox.add(passwordField);
            passwordBox.add(Box.createHorizontalGlue()); // Center horizontally

            // Center the userBox and passwordBox horizontally in a vertical box
            final Box centerBox = Box.createVerticalBox();
            centerBox.add(Box.createVerticalStrut(20)); // Add some vertical space at the top
            centerBox.add(userBox);
            centerBox.add(Box.createVerticalStrut(10));
            centerBox.add(passwordBox);
            centerBox.add(Box.createVerticalStrut(10));

            // Create a horizontal box for buttons and center it
            final Box buttonBox = Box.createHorizontalBox();
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
    public void actionPerformed(final ActionEvent e) {
        if (e.getSource().equals(loginButton)) {
            final String userText = userTextField.getText();
            final String passwordText = new String(passwordField.getPassword());
            // Inform the controller about the login attempt
            controller.handleLogin(userText, passwordText);
        } else if (e.getSource().equals(resetButton)) {
            userTextField.setText("");
            passwordField.setText("");
            messageLabel.setText("");
        } else if (e.getSource().equals(backButton)) {
            this.controller.handleBack();
        }
    }

    /**
     * Displays a message on the login page.
     *
     * @param message the message to be displayed
     */
    public void displayMessage(final String message) {
        messageLabel.setText(message);
    }
}

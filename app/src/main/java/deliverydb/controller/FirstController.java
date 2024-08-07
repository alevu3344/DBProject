package deliverydb.controller;

import java.awt.Dimension;
import java.sql.Connection;

import javax.swing.JFrame;

import deliverydb.controller.authentication.LoginController;
import deliverydb.controller.authentication.RegisterController;
import deliverydb.model.Model;
import deliverydb.view.FirstPage;

/**
 * The FirstController class is responsible for handling the initial interaction
 * with the user in the DeliveryDB application. It manages the main frame of the application,
 * handles login and registration button clicks, and ensures proper closure of the database connection.
 */
public class FirstController implements Controller {
    private final Model model;
    private JFrame mainFrame;
    private Runnable onClose;

    /**
     * Constructs a FirstController object.
     *
     * @param model the data model for the application
     * @param connection the database connection to be closed on application exit
     */
    public FirstController(Model model, Connection connection) {
        this.onClose = () -> {
            try {
                connection.close();
            } catch (Exception e) {
                // Handle the exception if needed
            }
        };

        this.mainFrame = new JFrame("DeliveryDB");
        this.mainFrame.setMinimumSize(new Dimension(600, 600));
        this.mainFrame.setResizable(false);
        this.mainFrame.setLocationRelativeTo(null); // Center the frame on the screen
        this.mainFrame.setVisible(true);
        this.mainFrame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.mainFrame.addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent e) {
                onClose.run();
                System.exit(0);
            }
        });
        new FirstPage(this, mainFrame);
        this.model = model;
    }

    /**
     * Handles the login button click event by initializing the LoginController.
     */
    public void handleLoginButtonClick() {
        new LoginController(this.mainFrame, this.model, this);
    }

    /**
     * Handles the register button click event by initializing the RegisterController.
     */
    public void handleRegisterButtonClick() {
        new RegisterController(this.mainFrame, this.model, this);
    }

    /**
     * Shows the initial page of the application.
     */
    @Override
    public void show() {
        new FirstPage(this, mainFrame);
    }
}

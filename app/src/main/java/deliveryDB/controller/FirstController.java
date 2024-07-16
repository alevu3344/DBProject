package deliveryDB.controller;

import java.awt.Dimension;
import java.sql.Connection;

import deliveryDB.controller.authentication.LoginController;
import deliveryDB.controller.authentication.RegisterController;
import deliveryDB.model.Model;
import deliveryDB.view.FirstPage;

import javax.swing.JFrame;

public class FirstController implements Controller {
    private final Model model;

    private JFrame mainFrame;
    private Runnable onClose;

    public FirstController(Model model, Connection connection) {
        

        this.onClose = () -> {
            try {
                connection.close();
            } catch (Exception e) {

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

        // Set this controller as the loginView's controller

    }

    public void handleLoginButtonClick() {
        new LoginController(this.mainFrame, this.model, this);
    }

    public void handleRegisterButtonClick() {
        new RegisterController(this.mainFrame, this.model, this);
    }

    @Override
    public void show() {
        new FirstPage(this, mainFrame);
    }
}

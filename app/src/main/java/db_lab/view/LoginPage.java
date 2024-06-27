package db_lab.view;

import javax.swing.*;

import db_lab.controller.MainController;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Optional;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
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

    private Optional<MainController> controller;
    private final JFrame mainFrame;

    // Constructor to set up the GUI components
    public LoginPage(Runnable onClose) {
        this.controller = Optional.empty();
        this.mainFrame = this.setupMainFrame(onClose);
        this.setupComponents();
        this.addComponentsToFrame();
        System.out.println("Constructor of login page");
    }

    private JFrame setupMainFrame(Runnable onClose) {
        var frame = new JFrame("Consegne_Db");
        var padding = BorderFactory.createEmptyBorder(10, 10, 10, 10);
        ((JComponent) frame.getContentPane()).setBorder(padding);
        frame.setMinimumSize(new Dimension(300, 300));
        frame.setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.PAGE_AXIS));
        frame.pack();
        frame.setResizable(false);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        frame.addWindowListener(
            new java.awt.event.WindowAdapter() {
                public void windowClosing(WindowEvent e) {
                    onClose.run();
                    System.exit(0);
                }
            }
        );
        return frame;
    }

    private void setupComponents() {
        container = mainFrame.getContentPane();

        

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

    public void setController(MainController controller) {
        this.controller = Optional.of(controller);
    }

    public void displayMessage(String message) {
        messageLabel.setText(message);
    }
}

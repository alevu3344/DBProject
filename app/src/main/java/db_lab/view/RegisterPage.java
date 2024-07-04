package db_lab.view;

import javax.swing.*;

import db_lab.controller.RegisterController;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Optional;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;

public class RegisterPage implements ActionListener {

    // Components of the Form

    private JButton registerButton;
    private JButton resetButton;
    private JButton backButton;
    private JLabel messageLabel;

    private Optional<RegisterController> controller;
    private final JFrame mainFrame;
    // a hashmap that maps each text field to the corresponding label
    private HashMap<JLabel, JTextField> textFieldsMap;

    // Constructor to set up the GUI components
    public RegisterPage(JFrame mainFrame) {
        this.controller = Optional.empty();
        this.mainFrame = mainFrame;
        this.setupComponents();
        System.out.println("Constructor of register page");
    }

    private void setupComponents() {
        this.mainFrame.getContentPane().removeAll();
        this.mainFrame.getContentPane().validate();
        this.mainFrame.getContentPane().repaint();
        Container container = mainFrame.getContentPane();
        container.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        // add the labels and text fields to the hashmap
        this.textFieldsMap = new LinkedHashMap<>();

        // Adding entries to the map
        textFieldsMap.put(new JLabel("Name:"), new JTextField());
        textFieldsMap.put(new JLabel("Surname:"), new JTextField());
        textFieldsMap.put(new JLabel("Street:"), new JTextField());
        textFieldsMap.put(new JLabel("Number:"), new JTextField());
        textFieldsMap.put(new JLabel("City:"), new JTextField());
        textFieldsMap.put(new JLabel("Username:"), new JTextField());
        textFieldsMap.put(new JLabel("Password:"), new JPasswordField());

        registerButton = new JButton("Register");
        resetButton = new JButton("Reset");
        backButton = new JButton("Back");

        messageLabel = new JLabel("");

        // Set the preferred sizes for the components
        Dimension fieldDimension = new Dimension(200, 25);
        this.textFieldsMap.values().forEach(textField -> textField.setPreferredSize(fieldDimension));
        registerButton.setPreferredSize(new Dimension(100, 30));
        resetButton.setPreferredSize(new Dimension(100, 30));
        backButton.setPreferredSize(new Dimension(100, 30));

        registerButton.addActionListener(this);
        resetButton.addActionListener(this);
        backButton.addActionListener(this);

        // Add components to container with GridBagConstraints
        gbc.insets = new Insets(5, 5, 5, 5);

        int row = 0; // Initial row index

        for (var entry : textFieldsMap.entrySet()) {
            JLabel lab = entry.getKey();
            JTextField txt = entry.getValue();

            gbc.gridx = 0;
            gbc.gridy = row;
            gbc.anchor = GridBagConstraints.LINE_END;
            container.add(lab, gbc);

            gbc.gridx = 1;
            gbc.anchor = GridBagConstraints.LINE_START;
            container.add(txt, gbc);

            row++;
        }

        // Add buttons and message label
        gbc.gridx = 0;
        gbc.gridy = row;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(15, 0, 0, 0); // Larger top inset for spacing

        container.add(registerButton, gbc);

        row++;

        gbc.gridy = row;
        container.add(resetButton, gbc);

        row++;

        gbc.gridy = row;
        container.add(backButton, gbc);

        row++;

        gbc.gridy = row;
        container.add(messageLabel, gbc);

        this.mainFrame.pack();
    }

    public JFrame getMainFrame() {
        return this.mainFrame;
    }

    // Action event handler
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == registerButton) {
            LinkedList<String> fields = new LinkedList<>();

            // get the text from the text fields and put in the fields list using a stream
            this.textFieldsMap.forEach((label, textField) -> fields.add(textField.getText()));
            // get the last value as the password
            String password = fields.getLast();

            // Inform the controller about the registration attempt
            controller.ifPresent(ctrl -> ctrl.handleRegistration(fields));
        } else if (e.getSource() == resetButton) {
            this.textFieldsMap.forEach((label, textField) -> textField.setText(""));
            messageLabel.setText("");
        } else if (e.getSource() == backButton) {
            controller.ifPresent(RegisterController::handleBack);
        }
    }

    public void setController(RegisterController controller) {
        this.controller = Optional.of(controller);
    }

    public void displayMessage(String message) {
        messageLabel.setText(message);
    }
}

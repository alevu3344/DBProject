package deliverydb.view.authentication;

import javax.swing.*;

import deliverydb.controller.authentication.RegisterController;
import deliverydb.data.User;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.function.Consumer;

/**
 * Represents the registration page where users can create a new account.
 */
public class RegisterPage implements ActionListener {

    // Components of the Form
    private JButton registerButton;
    private JButton resetButton;
    private JButton backButton;
    private JLabel messageLabel;
    private JCheckBox deliveryManCheckBox; // Add the checkbox

    private RegisterController controller;
    private final JFrame mainFrame;
    // a hashmap that maps each text field to the corresponding label
    private HashMap<JLabel, JTextField> textFieldsMap;

    /**
     * Constructs a RegisterPage with the given JFrame and RegisterController.
     *
     * @param mainFrame the main frame of the application
     * @param controller the controller that handles registration actions
     */
    public RegisterPage(JFrame mainFrame, RegisterController controller) {
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

    private void setupComponents() {
        freshPane(container -> {
            container.setLayout(new GridBagLayout());
            GridBagConstraints gbc = new GridBagConstraints();

            // add the labels and text fields to the hashmap
            this.textFieldsMap = new LinkedHashMap<>();

            // Adding entries to the map
            textFieldsMap.put(new JLabel("Username:"), new JTextField());
            textFieldsMap.put(new JLabel("Nome:"), new JTextField());
            textFieldsMap.put(new JLabel("Cognome:"), new JTextField());
            textFieldsMap.put(new JLabel("Password:"), new JPasswordField());
            textFieldsMap.put(new JLabel("Via:"), new JTextField());
            textFieldsMap.put(new JLabel("Civico:"), new JTextField());
            textFieldsMap.put(new JLabel("Città:"), new JTextField());

            registerButton = new JButton("Register");
            resetButton = new JButton("Reset");
            backButton = new JButton("Back");

            deliveryManCheckBox = new JCheckBox("Register as a delivery man"); // Initialize the checkbox

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

            // Add the checkbox
            gbc.gridx = 0;
            gbc.gridy = row;
            gbc.anchor = GridBagConstraints.CENTER;
            gbc.gridwidth = 2;
            container.add(deliveryManCheckBox, gbc);

            row++;

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

        });
    }

    /**
     * Retrieves the main frame associated with this page.
     *
     * @return the main frame of the application
     */
    public JFrame getMainFrame() {
        return this.mainFrame;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == registerButton) {
            LinkedList<String> fields = new LinkedList<>();

            // get the text from the text fields and put in the fields list using a stream
            this.textFieldsMap.forEach((label, textField) -> fields.add(textField.getText()));

            controller.handleRegistration(
                    deliveryManCheckBox.isSelected() ? User.USER_TYPE.DELIVERY_PERSON : User.USER_TYPE.CUSTOMER,
                    fields);
        } else if (e.getSource() == resetButton) {
            this.textFieldsMap.forEach((label, textField) -> textField.setText(""));
            deliveryManCheckBox.setSelected(false); // Reset the checkbox
            messageLabel.setText("");
        } else if (e.getSource() == backButton) {
            controller.handleBack();
        }
    }

    /**
     * Displays a message on the registration page.
     *
     * @param message the message to be displayed
     */
    public void displayMessage(String message) {
        messageLabel.setText(message);
    }
}

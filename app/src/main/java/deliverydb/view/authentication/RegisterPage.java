package deliverydb.view.authentication;


import deliverydb.controller.authentication.RegisterController;
import deliverydb.data.User;


import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.function.Consumer;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Map;

/**
 * Represents the registration page where users can create a new account.
 */
public class RegisterPage implements ActionListener {

    // Components of the Form
    private final JButton registerButton;
    private final JButton resetButton;
    private final JButton backButton;
    private final JLabel messageLabel;
    private final JCheckBox deliveryManCheckBox;

    private final RegisterController controller;
    private final JFrame mainFrame;
    private final Map<JLabel, JTextField> textFieldsMap;

    /**
     * Constructs a RegisterPage with the given JFrame and RegisterController.
     *
     * @param mainFrame the main frame of the application
     * @param controller the controller that handles registration actions
     */
    public RegisterPage(final JFrame mainFrame, final RegisterController controller) {
        this.controller = controller;
        this.mainFrame = mainFrame;
        this.textFieldsMap = new LinkedHashMap<>();
        this.registerButton = new JButton("Register");
        this.resetButton = new JButton("Reset");
        this.backButton = new JButton("Back");
        this.messageLabel = new JLabel("");
        this.deliveryManCheckBox = new JCheckBox("Register as a delivery man");

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

    private void setupComponents() {
        freshPane(container -> {
            container.setLayout(new GridBagLayout());
            final GridBagConstraints gbc = new GridBagConstraints();

            // Add the labels and text fields to the map
            textFieldsMap.put(new JLabel("Username:"), new JTextField());
            textFieldsMap.put(new JLabel("Nome:"), new JTextField());
            textFieldsMap.put(new JLabel("Cognome:"), new JTextField());
            textFieldsMap.put(new JLabel("Password:"), new JPasswordField());
            textFieldsMap.put(new JLabel("Via:"), new JTextField());
            textFieldsMap.put(new JLabel("Civico:"), new JTextField());
            textFieldsMap.put(new JLabel("Città:"), new JTextField());

            // Set the preferred sizes for the components
            final Dimension fieldDimension = new Dimension(200, 25);
            textFieldsMap.values().forEach(textField -> textField.setPreferredSize(fieldDimension));
            registerButton.setPreferredSize(new Dimension(100, 30));
            resetButton.setPreferredSize(new Dimension(100, 30));
            backButton.setPreferredSize(new Dimension(100, 30));

            registerButton.addActionListener(this);
            resetButton.addActionListener(this);
            backButton.addActionListener(this);

            // Add components to container with GridBagConstraints
            gbc.insets = new Insets(5, 5, 5, 5);

            int row = 0; // Initial row index

            for (final Map.Entry<JLabel, JTextField> entry : textFieldsMap.entrySet()) {
                final JLabel lab = entry.getKey();
                final JTextField txt = entry.getValue();

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

    @Override
    public void actionPerformed(final ActionEvent e) {
        if (e.getSource().equals(registerButton)) {
            final LinkedList<String> fields = new LinkedList<>();

            // Get the text from the text fields and put in the fields list
            textFieldsMap.forEach((label, textField) -> fields.add(textField.getText()));

            controller.handleRegistration(
                    deliveryManCheckBox.isSelected() ? User.Usertype.DELIVERY_PERSON : User.Usertype.CUSTOMER,
                    fields
            );
        } else if (e.getSource().equals(resetButton)) {
            textFieldsMap.forEach((label, textField) -> textField.setText(""));
            deliveryManCheckBox.setSelected(false); // Reset the checkbox
            messageLabel.setText("");
        } else if (e.getSource().equals(backButton)) {
            controller.handleBack();
        }
    }

    /**
     * Displays a message on the registration page.
     *
     * @param message the message to be displayed
     */
    public void displayMessage(final String message) {
        messageLabel.setText(message);
    }
}

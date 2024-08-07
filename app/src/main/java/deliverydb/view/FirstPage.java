package deliverydb.view;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.function.Consumer;

import deliverydb.controller.FirstController;

/**
 * Represents the initial page of the application that provides options to log in or register.
 */
public class FirstPage {

    private final JButton loginButton;
    private final JButton registerButton;
    private final JFrame mainFrame;
    private final FirstController controller;

    /**
     * Constructs a FirstPage with the specified controller and JFrame.
     *
     * @param controller the controller that handles button actions
     * @param mainFrame the main frame of the application
     */
    public FirstPage(final FirstController controller, final JFrame mainFrame) {
        this.mainFrame = mainFrame;
        this.controller = controller;
        this.loginButton = createButton("Login");
        this.registerButton = createButton("Register");
        initializeUI();
    }

    /**
     * Refreshes the main content pane of the JFrame and applies the given consumer to it.
     *
     * @param consumer a Consumer function that modifies the content pane
     */
    private void freshPane(final Consumer<Container> consumer) {
        final Container cp = this.mainFrame.getContentPane();
        cp.removeAll();
        cp.validate();
        cp.repaint();
        consumer.accept(cp);
        this.mainFrame.pack();
    }

    /**
     * Initializes the user interface for the first page, including setting up the layout and buttons.
     */
    private void initializeUI() {
        freshPane(container -> {
            final var padding = BorderFactory.createEmptyBorder(10, 10, 10, 10);
            ((JComponent) container).setBorder(padding);
            container.setLayout(new BorderLayout());
            container.setBackground(Color.WHITE); // Optional: Set background color

            // Create a panel for the buttons
            final JPanel buttonPanel = new JPanel();
            buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
            buttonPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

            buttonPanel.add(Box.createVerticalGlue()); // Add vertical glue to push buttons to the center
            buttonPanel.add(createAlignedButtonPanel(loginButton));
            buttonPanel.add(Box.createRigidArea(new Dimension(0, 10))); // Add some space between buttons
            buttonPanel.add(createAlignedButtonPanel(registerButton));
            buttonPanel.add(Box.createVerticalGlue()); // Add vertical glue to push buttons to the center

            // Add the button panel to the center of the main frame
            container.add(buttonPanel, BorderLayout.CENTER);
        });
    }

    /**
     * Creates a button with the specified text, custom styling, and action listeners.
     *
     * @param text the text to be displayed on the button
     * @return the created JButton
     */
    private JButton createButton(final String text) {
        final JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.PLAIN, 16)); // Set font
        button.setBackground(new Color(60, 179, 113)); // Set background color (PaleGreen)
        button.setForeground(Color.WHITE); // Set text color to white
        button.setFocusPainted(false); // Remove focus border
        button.setBorderPainted(false); // Remove border
        button.setOpaque(true); // Make the button opaque for background color to show
        button.setPreferredSize(new Dimension(200, 50)); // Set a larger preferred size for the buttons

        // Add hover effect - change background color when mouse enters
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(final MouseEvent evt) {
                button.setBackground(new Color(34, 139, 34)); // Darker green on hover (ForestGreen)
            }

            @Override
            public void mouseExited(final MouseEvent evt) {
                button.setBackground(new Color(60, 179, 113)); // Restore original color on exit
            }
        });

        // Add action listener
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                if ("Login".equals(text)) {
                    controller.handleLoginButtonClick();
                } else if ("Register".equals(text)) {
                    controller.handleRegisterButtonClick();
                }
            }
        });

        return button;
    }

    /**
     * Creates a panel with the specified button centered horizontally.
     *
     * @param button the button to be centered
     * @return the JPanel containing the centered button
     */
    private JPanel createAlignedButtonPanel(final JButton button) {
        final JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
        panel.add(Box.createHorizontalGlue()); // Add horizontal glue to center the button
        panel.add(button);
        panel.add(Box.createHorizontalGlue()); // Add horizontal glue to center the button
        return panel;
    }
}

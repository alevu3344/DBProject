package deliverydb.view;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.function.Consumer;

import deliverydb.controller.FirstController;

/**
 * Represents the initial page of the application that provides options to log
 * in or register.
 */
public class FirstPage {

    private static final Color BUTTON_BACKGROUND_COLOR = new Color(60, 179, 113); // PaleGreen
    private static final Color BUTTON_BACKGROUND_HOVER_COLOR = new Color(34, 139, 34); // ForestGreen
    private static final Color BUTTON_TEXT_COLOR = Color.WHITE;
    private static final int BUTTON_FONT_SIZE = 16;
    private static final Dimension BUTTON_DIMENSION = new Dimension(200, 50);
    private static final int BUTTON_SPACING = 10;
    private static final int PADDING = 10;

    private final JButton loginButton;
    private final JButton registerButton;
    private final JFrame mainFrame;
    private final FirstController controller;

    /**
     * Constructs a FirstPage with the specified controller and JFrame.
     *
     * @param controller the controller that handles button actions
     * @param mainFrame  the main frame of the application
     */
    public FirstPage(final FirstController controller, final JFrame mainFrame) {
        this.mainFrame = mainFrame;
        this.controller = controller;
        this.loginButton = createButton("Login");
        this.registerButton = createButton("Register");
        initializeUI();
    }

    /**
     * Refreshes the main content pane of the JFrame and applies the given consumer
     * to it.
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
     * Initializes the user interface for the first page, including setting up the
     * layout and buttons.
     */
    private void initializeUI() {
        freshPane(container -> {
            final var padding = BorderFactory.createEmptyBorder(PADDING, PADDING, PADDING, PADDING);
            ((JComponent) container).setBorder(padding);
            container.setLayout(new BorderLayout());
            container.setBackground(Color.WHITE); // Optional: Set background color

            // Create a panel for the buttons
            final JPanel buttonPanel = new JPanel();
            buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
            buttonPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

            buttonPanel.add(Box.createVerticalGlue()); // Add vertical glue to push buttons to the center
            buttonPanel.add(createAlignedButtonPanel(loginButton));
            buttonPanel.add(Box.createRigidArea(new Dimension(0, BUTTON_SPACING))); // Add some space between buttons
            buttonPanel.add(createAlignedButtonPanel(registerButton));
            buttonPanel.add(Box.createVerticalGlue()); // Add vertical glue to push buttons to the center

            // Add the button panel to the center of the main frame
            container.add(buttonPanel, BorderLayout.CENTER);
        });
    }

    /**
     * Creates a button with the specified text, custom styling, and action
     * listeners.
     *
     * @param text the text to be displayed on the button
     * @return the created JButton
     */
    private JButton createButton(final String text) {
        final JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.PLAIN, BUTTON_FONT_SIZE)); // Set font
        button.setBackground(BUTTON_BACKGROUND_COLOR); // Set background color
        button.setForeground(BUTTON_TEXT_COLOR); // Set text color to white
        button.setFocusPainted(false); // Remove focus border
        button.setBorderPainted(false); // Remove border
        button.setOpaque(true); // Make the button opaque for background color to show
        button.setPreferredSize(BUTTON_DIMENSION); // Set a larger preferred size for the buttons

        // Add hover effect - change background color when mouse enters
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(final MouseEvent evt) {
                button.setBackground(BUTTON_BACKGROUND_HOVER_COLOR); // Darker green on hover
            }

            @Override
            public void mouseExited(final MouseEvent evt) {
                button.setBackground(BUTTON_BACKGROUND_COLOR); // Restore original color on exit
            }
        });

        button.addActionListener(e -> {
            if ("Login".equals(text)) {
                controller.handleLoginButtonClick();
            } else if ("Register".equals(text)) {
                controller.handleRegisterButtonClick();
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

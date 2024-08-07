package deliverydb.view;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;

import deliverydb.controller.FirstController;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.function.Consumer;

public class FirstPage {

    private JButton loginButton;
    private JButton registerButton;
    private final JFrame mainFrame;
    private FirstController controller;

    // Constructor to set up the GUI components
    public FirstPage(FirstController controller, JFrame mainFrame) {
        this.mainFrame = mainFrame;
        this.controller = controller;
        this.initializeUI();
    }

    private void initializeUI() {
        freshPane(container -> {
            var padding = BorderFactory.createEmptyBorder(10, 10, 10, 10);
            ((JComponent) container).setBorder(padding);
            container.setLayout(new BorderLayout());
            container.setBackground(Color.WHITE); // Optional: Set background color

            // Create a panel for the buttons
            JPanel buttonPanel = new JPanel();
            buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
            buttonPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

            loginButton = createButton("Login");
            registerButton = createButton("Register");

            buttonPanel.add(Box.createVerticalGlue()); // Add vertical glue to push buttons to the center
            buttonPanel.add(createAlignedButtonPanel(loginButton));
            buttonPanel.add(Box.createRigidArea(new Dimension(0, 10))); // Add some space between buttons
            buttonPanel.add(createAlignedButtonPanel(registerButton));
            buttonPanel.add(Box.createVerticalGlue()); // Add vertical glue to push buttons to the center

            // Add the button panel to the center of the main frame
            container.add(buttonPanel, BorderLayout.CENTER);
        });
    }

    private void freshPane(Consumer<Container> consumer) {
        Container cp = this.mainFrame.getContentPane();
        cp.removeAll();
        cp.validate();
        cp.repaint();
        consumer.accept(cp);
        this.mainFrame.pack();
    }

    private JButton createButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.PLAIN, 16)); // Set font
        button.setBackground(new Color(60, 179, 113)); // Set background color (PaleGreen)
        button.setForeground(Color.WHITE); // Set text color to white
        button.setFocusPainted(false); // Remove focus border
        button.setBorderPainted(false); // Remove border
        button.setOpaque(true); // Make the button opaque for background color to show
        button.setPreferredSize(new Dimension(200, 50)); // Set a larger preferred size for the buttons

        // Add hover effect - change background color when mouse enters
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(34, 139, 34)); // Darker green on hover (ForestGreen)
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(60, 179, 113)); // Restore original color on exit
            }
        });

        // Add action listener
        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (text.equals("Login")) {
                    controller.handleLoginButtonClick();
                } else if (text.equals("Register")) {
                    controller.handleRegisterButtonClick();
                }
            }
        });

        return button;
    }

    private JPanel createAlignedButtonPanel(JButton button) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
        panel.add(Box.createHorizontalGlue()); // Add horizontal glue to center the button
        panel.add(button);
        panel.add(Box.createHorizontalGlue()); // Add horizontal glue to center the button
        return panel;
    }
}

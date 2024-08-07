package deliverydb.view.delivery;

import javax.swing.*;
import javax.swing.border.TitledBorder;

import deliverydb.controller.delivery.DeliveryCtrl;

import java.awt.*;
import java.util.function.Consumer;

/**
 * Represents the panel for delivery-related actions in the application. It allows delivery personnel to view available
 * orders, accepted orders, and manage their account.
 */
public class DeliveryPanel {

    /**
     * Enum representing the state of orders that can be shown: either available or accepted.
     */
    public enum Flag {
        AVAILABLE, ACCEPTED
    }

    private final JFrame mainFrame;
    private final DeliveryCtrl ctrl;

    /**
     * Constructs a DeliveryPanel with the given JFrame and DeliveryCtrl.
     *
     * @param mainFrame the main frame of the application
     * @param ctrl the controller that handles delivery actions
     */
    public DeliveryPanel(JFrame mainFrame, DeliveryCtrl ctrl) {
        this.mainFrame = mainFrame;
        this.ctrl = ctrl;
        this.initializeUI();
    }

    /**
     * Initializes the user interface for the delivery panel.
     * It sets up the layout, buttons, and balance display.
     */
    private void initializeUI() {
        freshPane(container -> {
            container.setLayout(new BorderLayout());

            // Create a panel to hold the buttons with BoxLayout
            JPanel buttonPanel = new JPanel();
            buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));

            // Create and add the "Show available orders" button
            JButton showAvailableOrdersButton = new JButton("Show available orders");
            showAvailableOrdersButton.setAlignmentX(Component.CENTER_ALIGNMENT); // Center button
            showAvailableOrdersButton.addActionListener(e -> this.ctrl.showOrders(Flag.AVAILABLE));
            buttonPanel.add(showAvailableOrdersButton);

            // Add space between buttons
            buttonPanel.add(Box.createRigidArea(new Dimension(0, 10))); // 10px vertical space

            // Create and add the "View Accepted" button
            JButton viewAcceptedButton = new JButton("View Accepted");
            viewAcceptedButton.setAlignmentX(Component.CENTER_ALIGNMENT); // Center button
            viewAcceptedButton.addActionListener(e -> this.ctrl.showOrders(Flag.ACCEPTED));
            buttonPanel.add(viewAcceptedButton);

            // Add space between buttons
            buttonPanel.add(Box.createRigidArea(new Dimension(0, 10))); // 10px vertical space

            // Create and add the "Logout" button
            JButton logoutButton = new JButton("Logout");
            logoutButton.setAlignmentX(Component.CENTER_ALIGNMENT); // Center button
            logoutButton.addActionListener(e -> this.ctrl.handleLogOut());
            buttonPanel.add(logoutButton);

            // Add space at the top and bottom to center the buttons in the available space
            buttonPanel.add(Box.createVerticalGlue());

            // Add the button panel to the container
            container.add(buttonPanel, BorderLayout.CENTER);

            // Create a panel to hold the balance label at the bottom
            JPanel balancePanel = new JPanel();
            balancePanel.setBorder(BorderFactory.createTitledBorder(
                    BorderFactory.createLineBorder(Color.BLACK), "Balance", TitledBorder.CENTER, TitledBorder.TOP));

            // Create the balance label
            JLabel balanceLabel = new JLabel(String.format("%.2f", this.ctrl.getBalance()));
            balancePanel.add(balanceLabel);

            // Add the balance panel to the container at the bottom
            container.add(balancePanel, BorderLayout.SOUTH);
        });
    }

    /**
     * Refreshes the main content pane of the JFrame and applies the given consumer to it.
     *
     * @param consumer a Consumer function that modifies the content pane
     */
    private void freshPane(Consumer<Container> consumer) {
        Container cp = this.mainFrame.getContentPane();
        cp.removeAll();
        cp.validate();
        cp.repaint();
        consumer.accept(cp);
        this.mainFrame.pack();
    }
}

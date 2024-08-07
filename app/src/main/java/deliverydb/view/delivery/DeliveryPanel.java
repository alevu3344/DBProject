package deliverydb.view.delivery;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

import deliverydb.controller.delivery.DeliveryCtrl;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
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
        /**
         * Flag tot determine wether the panel requested is about available orders.
         */
        AVAILABLE, 
        /**
         * Flag tot determine wether the panel requested is about accepted orders.
         */
        ACCEPTED
    }

    private final JFrame mainFrame;
    private final DeliveryCtrl ctrl;

    /**
     * Constructs a DeliveryPanel with the given JFrame and DeliveryCtrl.
     *
     * @param mainFrame the main frame of the application
     * @param ctrl the controller that handles delivery actions
     */
    public DeliveryPanel(final JFrame mainFrame, final DeliveryCtrl ctrl) {
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
            final JPanel buttonPanel = new JPanel();
            buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));

            // Create and add the "Show available orders" button
            final JButton showAvailableOrdersButton = new JButton("Show available orders");
            showAvailableOrdersButton.setAlignmentX(Component.CENTER_ALIGNMENT); // Center button
            showAvailableOrdersButton.addActionListener(e -> ctrl.showOrders(Flag.AVAILABLE));
            buttonPanel.add(showAvailableOrdersButton);

            // Add space between buttons
            buttonPanel.add(Box.createRigidArea(new Dimension(0, 10))); // 10px vertical space

            // Create and add the "View Accepted" button
            final JButton viewAcceptedButton = new JButton("View Accepted");
            viewAcceptedButton.setAlignmentX(Component.CENTER_ALIGNMENT); // Center button
            viewAcceptedButton.addActionListener(e -> ctrl.showOrders(Flag.ACCEPTED));
            buttonPanel.add(viewAcceptedButton);

            // Add space between buttons
            buttonPanel.add(Box.createRigidArea(new Dimension(0, 10))); // 10px vertical space

            // Create and add the "Logout" button
            final JButton logoutButton = new JButton("Logout");
            logoutButton.setAlignmentX(Component.CENTER_ALIGNMENT); // Center button
            logoutButton.addActionListener(e -> ctrl.handleLogOut());
            buttonPanel.add(logoutButton);

            // Add space at the top and bottom to center the buttons in the available space
            buttonPanel.add(Box.createVerticalGlue());

            // Add the button panel to the container
            container.add(buttonPanel, BorderLayout.CENTER);

            // Create a panel to hold the balance label at the bottom
            final JPanel balancePanel = new JPanel();
            balancePanel.setBorder(BorderFactory.createTitledBorder(
                    BorderFactory.createLineBorder(Color.BLACK), "Balance", TitledBorder.CENTER, TitledBorder.TOP));

            // Create the balance label
            final JLabel balanceLabel = new JLabel(String.format("%.2f", ctrl.getBalance()));
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
    private void freshPane(final Consumer<Container> consumer) {
        final Container cp = mainFrame.getContentPane();
        cp.removeAll();
        cp.validate();
        cp.repaint();
        consumer.accept(cp);
        mainFrame.pack();
    }
}

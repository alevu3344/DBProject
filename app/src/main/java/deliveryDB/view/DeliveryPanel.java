package deliveryDB.view;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.function.Consumer;
import deliveryDB.controller.DeliveryCtrl;
import javax.swing.BoxLayout;
import javax.swing.JOptionPane;

public class DeliveryPanel {

    private final JFrame mainFrame;
    private final DeliveryCtrl ctrl;

    public DeliveryPanel(JFrame mainFrame, DeliveryCtrl ctrl) {
        this.mainFrame = mainFrame;
        this.ctrl = ctrl;
        initializeUI();
    }

    private void initializeUI() {
        freshPane(container -> {
            container.setLayout(new BorderLayout());

            // Create a panel to hold the buttons
            JPanel buttonPanel = new JPanel();
            buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));

            // Create and add the "Show available orders" button
            JButton showAvailableOrdersButton = new JButton("Show available orders");
            showAvailableOrdersButton.addActionListener(e -> {
                this.showAvailableOrders();
            });
            buttonPanel.add(showAvailableOrdersButton);

            // Create and add the "View Accepted" button
            JButton viewAcceptedButton = new JButton("View Accepted");
            viewAcceptedButton.addActionListener(e -> {
                this.viewAcceptedOrders();
            });
            buttonPanel.add(viewAcceptedButton);

            // Create and add the "Logout" button
            JButton logoutButton = new JButton("Logout");
            logoutButton.addActionListener(e -> {
                this.ctrl.handleLogOut();
            });
            buttonPanel.add(logoutButton);

            // Add the button panel to the container
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

    private void showAvailableOrders() {
        // Logic to show available orders
        JOptionPane.showMessageDialog(mainFrame, "Showing available orders.");
    }

    private void viewAcceptedOrders() {
        // Logic to view accepted orders
        JOptionPane.showMessageDialog(mainFrame, "Viewing accepted orders.");
    }

}

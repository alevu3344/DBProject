package deliveryDB.view.delivery;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.util.List;
import java.util.function.Consumer;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import deliveryDB.controller.delivery.OrdersCtrl;
import deliveryDB.data.Order;
import deliveryDB.data.Restaurant;
import deliveryDB.view.delivery.DeliveryPanel.Flag;

public class OrdersView {

    private final JFrame mainFrame;
    private final OrdersCtrl ctrl;
    private Flag flag;

    public OrdersView(Flag flag, JFrame mainFrame, OrdersCtrl ctrl) {
        this.mainFrame = mainFrame;
        this.flag = flag;
        this.ctrl = ctrl;
        initializeUI();
    }

    private void freshPane(Consumer<Container> consumer) {
        Container cp = this.mainFrame.getContentPane();
        cp.removeAll();
        cp.validate();
        cp.repaint();
        consumer.accept(cp);
        this.mainFrame.pack();
    }

    private void initializeUI() {
        freshPane(cp -> {
            cp.setLayout(new BorderLayout());

            // Title Panel
            JPanel titlePanel = new JPanel();
            titlePanel.setLayout(new BoxLayout(titlePanel, BoxLayout.X_AXIS));
            titlePanel.add(Box.createHorizontalGlue());
            titlePanel.add(new JButton("Back") {{
                addActionListener(e -> ctrl.handleBack());
            }});
            cp.add(titlePanel, BorderLayout.NORTH);

            // Orders Panel
            JPanel ordersPanel = new JPanel();
            ordersPanel.setLayout(new BoxLayout(ordersPanel, BoxLayout.Y_AXIS));
            ordersPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

            List<Order> orders = ctrl.getOrdersOnFlag(flag);
            for (Order order : orders) {
                JPanel orderPanel = createOrderPanel(order);
                ordersPanel.add(orderPanel);
                ordersPanel.add(Box.createRigidArea(new Dimension(0, 10)));
            }

            JScrollPane scrollPane = new JScrollPane(ordersPanel);
            scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
            scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
            scrollPane.setPreferredSize(new Dimension(600, 400)); // Adjust dimensions as needed
            cp.add(scrollPane, BorderLayout.CENTER);
        });
    }

    private JPanel createOrderPanel(Order order) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(new CompoundBorder(new LineBorder(Color.BLACK, 1), new EmptyBorder(10, 10, 10, 10)));
        panel.add(new JLabel("Order ID: " + order.getOrderID()));
        panel.add(new JLabel("Username: " + order.getUsername()));
        panel.add(new JLabel("Restaurant ID: " + order.getRestaurantID()));
        panel.add(new JLabel("Number of Items: " + order.getItems().size()));

        // Show Details Button
        JButton showDetailsButton = new JButton("Show details");
        showDetailsButton.addActionListener(e -> {
            displayOrderDetails(order);
        });
        panel.add(showDetailsButton);

        // Accept Button (conditionally added if flag is AVAILABLE)
        if (flag == Flag.AVAILABLE) {
            JButton acceptButton = new JButton("Accept");
            acceptButton.addActionListener(e -> {
                int response = JOptionPane.showConfirmDialog(
                    mainFrame,
                    "Are you sure you want to accept this order?",
                    "Confirm Accept",
                    JOptionPane.YES_NO_OPTION
                );
                if (response == JOptionPane.YES_OPTION) {
                    if (ctrl.acceptOrder(order.getOrderID())) {
                        JOptionPane.showMessageDialog(mainFrame, "Order accepted successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
                        initializeUI();
                    } else {
                        JOptionPane.showMessageDialog(mainFrame, "Failed to accept order.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            });
            panel.add(acceptButton);
        }

        // Deliver Button (conditionally added if flag is ACCEPTED)
        if (flag == Flag.ACCEPTED) {
            JButton deliverButton = new JButton("Consegna");
            deliverButton.addActionListener(e -> {
                int response = JOptionPane.showConfirmDialog(
                    mainFrame,
                    "Are you sure you want to deliver this order?",
                    "Confirm Deliver",
                    JOptionPane.YES_NO_OPTION
                );
                if (response == JOptionPane.YES_OPTION) {
                    if (ctrl.deliverOrder(order.getOrderID())) {
                        JOptionPane.showMessageDialog(mainFrame, "Order delivered successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
                        initializeUI();
                    } else {
                        JOptionPane.showMessageDialog(mainFrame, "Failed to deliver order.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            });
            panel.add(deliverButton);
        }

        return panel;
    }

    private void displayOrderDetails(Order order) {
        var details = ctrl.restaurantDetails(order);
        Restaurant restaurant = details.get1();
        String deliveryAddress = details.get2();

        if (restaurant == null) {
            JOptionPane.showMessageDialog(mainFrame, "Restaurant details not found.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        float compensation = ctrl.getCompensation(order); // Retrieve the compensation value
        String formattedCompensation = String.format("%.2f", compensation); // Format the compensation

        JPanel detailsPanel = new JPanel();
        detailsPanel.setLayout(new BoxLayout(detailsPanel, BoxLayout.Y_AXIS));

        JPanel namePanel = new JPanel();
        namePanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        namePanel.setLayout(new BoxLayout(namePanel, BoxLayout.X_AXIS));
        namePanel.add(new JLabel("Restaurant Name: "));
        namePanel.add(new JLabel(restaurant.getName()));

        JPanel addressPanel = new JPanel();
        addressPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        addressPanel.setLayout(new BoxLayout(addressPanel, BoxLayout.X_AXIS));
        addressPanel.add(new JLabel("Restaurant Address: "));
        addressPanel.add(new JLabel(restaurant.getAddress()));

        JPanel deliveryAddressPanel = new JPanel();
        deliveryAddressPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        deliveryAddressPanel.setLayout(new BoxLayout(deliveryAddressPanel, BoxLayout.X_AXIS));
        deliveryAddressPanel.add(new JLabel("Delivery Address: "));
        deliveryAddressPanel.add(new JLabel(deliveryAddress));

        JPanel itemsPanel = new JPanel();
        itemsPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        itemsPanel.setLayout(new BoxLayout(itemsPanel, BoxLayout.Y_AXIS));
        itemsPanel.add(new JLabel("Ordered Items:"));
        order.getItems().forEach((item, quantity) -> itemsPanel.add(new JLabel("- " + item.getName() + " x " + quantity)));

        JPanel compensationPanel = new JPanel();
        compensationPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        compensationPanel.setLayout(new BoxLayout(compensationPanel, BoxLayout.X_AXIS));
        compensationPanel.add(new JLabel("Compensation: $"));
        compensationPanel.add(new JLabel(formattedCompensation));

        detailsPanel.add(namePanel);
        detailsPanel.add(addressPanel);
        detailsPanel.add(deliveryAddressPanel);
        detailsPanel.add(itemsPanel);
        detailsPanel.add(compensationPanel); // Add the compensation panel

        JOptionPane.showMessageDialog(mainFrame, detailsPanel, "Order Details", JOptionPane.INFORMATION_MESSAGE);
    }
}

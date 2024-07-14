package deliveryDB.view;

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
import deliveryDB.controller.OrdersCtrl;
import deliveryDB.data.Order;
import deliveryDB.data.Restaurant;
import deliveryDB.view.DeliveryPanel.Flag;

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
            acceptButton.addActionListener(e -> ctrl.acceptOrder(order));
            panel.add(acceptButton);
        }

        return panel;
    }

    private void displayOrderDetails(Order order) {
        Restaurant restaurant = this.ctrl.restaurantDetails(order);

        if (restaurant == null) {
            JOptionPane.showMessageDialog(mainFrame, "Restaurant details not found.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Create panels for the restaurant name, address, and ordered items
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

        JPanel itemsPanel = new JPanel();
        itemsPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        itemsPanel.setLayout(new BoxLayout(itemsPanel, BoxLayout.Y_AXIS));
        itemsPanel.add(new JLabel("Ordered Items:"));
        order.getItems().forEach((item, quantity) -> itemsPanel.add(new JLabel("- " + item.getName())));

        // Add all panels to the detailsPanel
        detailsPanel.add(namePanel);
        detailsPanel.add(addressPanel);
        detailsPanel.add(itemsPanel);

        // Show details in a JOptionPane
        JOptionPane.showMessageDialog(mainFrame, detailsPanel, "Order Details", JOptionPane.INFORMATION_MESSAGE);
    }
}

package deliverydb.view.delivery;

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

import deliverydb.controller.delivery.OrdersCtrl;
import deliverydb.data.Order;
import deliverydb.data.Restaurant;
import deliverydb.view.delivery.DeliveryPanel.Flag;

/**
 * Represents the view for displaying orders in the application. It allows the user to see available or accepted orders
 * and perform actions such as accepting or delivering orders.
 */
public class OrdersView {

    private final JFrame mainFrame;
    private final OrdersCtrl ctrl;
    private final Flag flag;
    private static final Dimension SCROLL_DIMENSION = new Dimension(600, 400);

    /**
     * Constructs an OrdersView with the specified flag, JFrame, and OrdersCtrl.
     *
     * @param flag the flag indicating the type of orders to display (AVAILABLE or ACCEPTED)
     * @param mainFrame the main frame of the application
     * @param ctrl the controller that handles order actions
     */
    public OrdersView(final Flag flag, final JFrame mainFrame, final OrdersCtrl ctrl) {
        this.mainFrame = mainFrame;
        this.flag = flag;
        this.ctrl = ctrl;
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
     * Initializes the user interface for the orders view, including setting up the layout, title panel, orders list, and
     * scroll pane.
     */
    private void initializeUI() {
        freshPane(cp -> {
            cp.setLayout(new BorderLayout());

            // Title Panel
            final JPanel titlePanel = new JPanel();
            titlePanel.setLayout(new BoxLayout(titlePanel, BoxLayout.X_AXIS));
            titlePanel.add(Box.createHorizontalGlue());
            final JButton backButton = new JButton("Back");
            backButton.addActionListener(e -> ctrl.handleBack());
            titlePanel.add(backButton);
            cp.add(titlePanel, BorderLayout.NORTH);

            // Orders Panel
            final JPanel ordersPanel = new JPanel();
            ordersPanel.setLayout(new BoxLayout(ordersPanel, BoxLayout.Y_AXIS));
            ordersPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

            final List<Order> orders = ctrl.getOrdersOnFlag(flag);
            for (final Order order : orders) {
                final JPanel orderPanel = createOrderPanel(order);
                ordersPanel.add(orderPanel);
                ordersPanel.add(Box.createRigidArea(new Dimension(0, 10)));
            }

            final JScrollPane scrollPane = new JScrollPane(ordersPanel);
            scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
            scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
            scrollPane.setPreferredSize(SCROLL_DIMENSION); // Adjust dimensions as needed
            cp.add(scrollPane, BorderLayout.CENTER);
        });
    }

    /**
     * Displays the details of a specific order in a dialog.
     *
     * @param order the order whose details are to be displayed
     */
    private void displayOrderDetails(final Order order) {
        final var details = ctrl.restaurantDetails(order);
        final Restaurant restaurant = details.get1();

        if (restaurant == null) {
            JOptionPane.showMessageDialog(mainFrame, "Restaurant details not found.", "Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        final float compensation = ctrl.getCompensation(order); // Retrieve the compensation value
        final String formattedCompensation = String.format("%.2f", compensation); // Format the compensation

        final JPanel detailsPanel = new JPanel();
        detailsPanel.setLayout(new BoxLayout(detailsPanel, BoxLayout.Y_AXIS));

        final JPanel namePanel = new JPanel();
        namePanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        namePanel.setLayout(new BoxLayout(namePanel, BoxLayout.X_AXIS));
        namePanel.add(new JLabel("Restaurant Name: "));
        namePanel.add(new JLabel(restaurant.getName()));

        final JPanel addressPanel = new JPanel();
        addressPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        addressPanel.setLayout(new BoxLayout(addressPanel, BoxLayout.X_AXIS));
        addressPanel.add(new JLabel("Restaurant Address: "));
        addressPanel.add(new JLabel(restaurant.getAddress()));

        final JPanel deliveryAddressPanel = new JPanel();
        final String deliveryAddress = details.get2();
        deliveryAddressPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        deliveryAddressPanel.setLayout(new BoxLayout(deliveryAddressPanel, BoxLayout.X_AXIS));
        deliveryAddressPanel.add(new JLabel("Delivery Address: "));
        deliveryAddressPanel.add(new JLabel(deliveryAddress));

        final JPanel itemsPanel = new JPanel();
        itemsPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        itemsPanel.setLayout(new BoxLayout(itemsPanel, BoxLayout.Y_AXIS));
        itemsPanel.add(new JLabel("Ordered Items:"));
        order.getItems()
                .forEach((item, quantity) -> itemsPanel.add(new JLabel("- " + item.getName() + " x " + quantity)));

        final JPanel compensationPanel = new JPanel();
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

    /**
     * Creates a panel for displaying information about a single order, including buttons to show details, accept, or deliver
     * the order, depending on its status.
     *
     * @param order the order to display
     * @return the JPanel representing the order
     */
    private JPanel createOrderPanel(final Order order) {
        final JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(new CompoundBorder(new LineBorder(Color.BLACK, 1), new EmptyBorder(10, 10, 10, 10)));
        panel.add(new JLabel("Order ID: " + order.getOrderID()));
        panel.add(new JLabel("Username: " + order.getUsername()));
        panel.add(new JLabel("Restaurant ID: " + order.getRestaurantID()));
        panel.add(new JLabel("Number of Items: " + order.getItems().size()));

        // Show Details Button
        final JButton showDetailsButton = new JButton("Show details");
        showDetailsButton.addActionListener(e -> displayOrderDetails(order));
        panel.add(showDetailsButton);

        // Accept Button (conditionally added if flag is AVAILABLE)
        if (flag == Flag.AVAILABLE) {
            final JButton acceptButton = new JButton("Accept");
            acceptButton.addActionListener(e -> {
                final int response = JOptionPane.showConfirmDialog(
                        mainFrame,
                        "Are you sure you want to accept this order?",
                        "Confirm Accept",
                        JOptionPane.YES_NO_OPTION);
                if (response == JOptionPane.YES_OPTION) {
                    if (ctrl.acceptOrder(order.getOrderID())) {
                        JOptionPane.showMessageDialog(mainFrame, "Order accepted successfully.", "Success",
                                JOptionPane.INFORMATION_MESSAGE);
                        initializeUI();
                    } else {
                        JOptionPane.showMessageDialog(mainFrame, "Failed to accept order.", "Error",
                                JOptionPane.ERROR_MESSAGE);
                    }
                }
            });
            panel.add(acceptButton);
        }

        // Deliver Button (conditionally added if flag is ACCEPTED)
        if (flag == Flag.ACCEPTED) {
            final JButton deliverButton = new JButton("Deliver");
            deliverButton.addActionListener(e -> {
                final int response = JOptionPane.showConfirmDialog(
                        mainFrame,
                        "Are you sure you want to deliver this order?",
                        "Confirm Deliver",
                        JOptionPane.YES_NO_OPTION);
                if (response == JOptionPane.YES_OPTION) {
                    if (ctrl.deliverOrder(order)) {
                        JOptionPane.showMessageDialog(mainFrame, "Order delivered successfully.", "Success",
                                JOptionPane.INFORMATION_MESSAGE);
                        initializeUI();
                    } else {
                        JOptionPane.showMessageDialog(mainFrame, "Failed to deliver order.", "Error",
                                JOptionPane.ERROR_MESSAGE);
                    }
                }
            });
            panel.add(deliverButton);
        }

        return panel;
    }
}

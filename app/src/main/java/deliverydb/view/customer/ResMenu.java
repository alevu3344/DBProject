package deliverydb.view.customer;

import java.util.Optional;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Locale;
import java.util.function.Consumer;
import java.text.SimpleDateFormat;
import java.util.Date;

import deliverydb.controller.customer.ResMenuCtrl;
import deliverydb.data.Item;
import deliverydb.utilities.Pair;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTextArea;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;

/**
 * Represents the restaurant menu where customers can view items, select
 * quantities, and place orders. It also displays the restaurant information and
 * user balance.
 */
public class ResMenu {

    private static final Locale LOCALE = Locale.getDefault();
    private static final String CURRENCY_FORMAT = "%.2f";
    private static final int INITIAL_SPINNER_VALUE = 0;
    private static final int MAX_SPINNER_VALUE = 20;
    private static final int SPINNER_STEP = 1;
    private static final int SPINNER_WIDTH = 50;
    private static final int SPINNER_HEIGHT = 30;
    private static final Dimension SPINNER_DIMENSION = new Dimension(SPINNER_WIDTH, SPINNER_HEIGHT);
    private static final Dimension LABEL_DIMENSION = new Dimension(200, 30);
    private static final Dimension INFO_PANEL_DIMENSION = new Dimension(200, 100);

    private static final Dimension SCROLLPANE_DIMENSION = new Dimension(600, 600);

    private final JFrame mainFrame;
    private final Optional<ResMenuCtrl> controller;
    private final Map<Item, Integer> itemQuantityMap;
    private final JTextArea orderSummaryArea;
    private final JLabel totalCostLabel; // Label for total cost
    private final JLabel balanceLabel; // Label for user balance
    private final JLabel restaurantInfoLabel; // Label to show restaurant info
    private final JButton sendOrderButton; // Button to send the order
    private final Map<Item, JSpinner> itemSpinners; // Map to track spinners for each item
    private final JButton reviewsButton; // Button to show reviews

    /**
     * Constructs a ResMenu with the given JFrame and ResMenuCtrl.
     *
     * @param mainFrame  the main frame of the application
     * @param controller the controller that handles restaurant menu actions
     */
    public ResMenu(final JFrame mainFrame, final ResMenuCtrl controller) {
        this.controller = Optional.of(controller);
        this.mainFrame = mainFrame;
        this.orderSummaryArea = new JTextArea();
        this.orderSummaryArea.setLineWrap(true);
        this.totalCostLabel = new JLabel("Total: $0.0"); // Initialize total cost label
        this.balanceLabel = new JLabel(); // Initialize balance label
        this.restaurantInfoLabel = new JLabel(); // Initialize restaurant info label
        this.sendOrderButton = new JButton("Send Order");
        this.sendOrderButton.setBackground(Color.RED);
        this.sendOrderButton.setForeground(Color.WHITE);
        this.sendOrderButton.setEnabled(false); // Initially disabled
        this.itemSpinners = new LinkedHashMap<>(); // Initialize itemSpinners
        this.reviewsButton = new JButton("Reviews"); // Initialize reviews button

        orderSummaryArea.setEditable(false);

        this.itemQuantityMap = this.controller.get().getItemMap();

        this.displayMenu();
        this.updateBalance(this.controller.get().getBalance());
        this.updateRestaurantInfo();
    }

    private void freshPane(final Consumer<Container> consumer) {
        final var cp = this.mainFrame.getContentPane();
        this.mainFrame.setLayout(new BoxLayout(this.mainFrame.getContentPane(), BoxLayout.PAGE_AXIS));
        cp.removeAll();
        cp.validate();
        cp.repaint();
        consumer.accept(cp);
        this.mainFrame.pack();
    }

    private void displayMenu() {
        freshPane(cp -> {
            final var mainPanel = Box.createHorizontalBox(); // Create a horizontal box to hold menu and summary

            final var mainBox = Box.createVerticalBox();
            for (final var item : this.itemQuantityMap.keySet()) {
                final var rowBox = Box.createHorizontalBox();

                final var dishLabel = new JLabel(item.getName());
                dishLabel.setPreferredSize(LABEL_DIMENSION);
                dishLabel.setMaximumSize(LABEL_DIMENSION);
                dishLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
                rowBox.add(dishLabel);

                final var typeLabel = new JLabel(item.getType());
                typeLabel.setPreferredSize(LABEL_DIMENSION);
                typeLabel.setMaximumSize(LABEL_DIMENSION);
                typeLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
                rowBox.add(Box.createHorizontalStrut(10));
                rowBox.add(typeLabel);

                final var spinner = new JSpinner(new SpinnerNumberModel(INITIAL_SPINNER_VALUE, INITIAL_SPINNER_VALUE,
                        MAX_SPINNER_VALUE, SPINNER_STEP));
                spinner.setMaximumSize(SPINNER_DIMENSION);
                spinner.addChangeListener(e -> {
                    this.itemQuantityMap.put(item, (Integer) spinner.getValue());
                    updateOrderSummary();
                    updateSendOrderButtonState(); // Update button state on quantity change
                });
                spinner.setAlignmentX(Component.LEFT_ALIGNMENT);
                rowBox.add(Box.createHorizontalStrut(10));
                rowBox.add(spinner);

                final var priceLabel = new JLabel(item.getPrice() + "$");
                priceLabel.setPreferredSize(LABEL_DIMENSION);
                priceLabel.setMaximumSize(LABEL_DIMENSION);
                priceLabel.setHorizontalAlignment(SwingConstants.LEFT);
                priceLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
                rowBox.add(Box.createHorizontalStrut(10));
                rowBox.add(priceLabel);

                rowBox.setAlignmentX(Component.LEFT_ALIGNMENT);
                mainBox.add(rowBox);

                // Track the spinner for each item
                itemSpinners.put(item, spinner);
            }
            mainBox.setAlignmentX(Component.LEFT_ALIGNMENT);

            // Wrap mainBox in a JScrollPane
            final var scrollPane = new JScrollPane(mainBox);
            scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
            scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
            scrollPane.setPreferredSize(SCROLLPANE_DIMENSION); // Adjust the size as needed

            mainPanel.add(scrollPane);

            // Create the order summary panel
            final var orderSummaryPanel = createOrderSummaryPanel();
            mainPanel.add(orderSummaryPanel);

            // Create and add the restaurant panel
            final var restaurantPanel = createRestaurantPanel();
            mainPanel.add(restaurantPanel);

            cp.add(mainPanel);
            // Add back button
            this.addBackButton(cp);
            // Add send order button
            this.addSendOrderButton(cp);
        });
    }

    private JPanel createOrderSummaryPanel() {
        final var orderSummaryPanel = new JPanel();
        orderSummaryPanel.setLayout(new BoxLayout(orderSummaryPanel, BoxLayout.Y_AXIS));
        orderSummaryPanel.setPreferredSize(INFO_PANEL_DIMENSION);
        orderSummaryPanel.setMaximumSize(INFO_PANEL_DIMENSION);

        // Add balance label to the top of the summary panel
        orderSummaryPanel.add(balanceLabel);

        orderSummaryPanel.add(new JLabel("Order Summary:"));

        // Wrap JTextArea in a JScrollPane and set size constraints
        orderSummaryArea.setPreferredSize(new Dimension(INFO_PANEL_DIMENSION.width, 100)); // Adjust the height for the
                                                                                           // scrollable area
        final var summaryScrollPane = new JScrollPane(orderSummaryArea);
        summaryScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        summaryScrollPane.setPreferredSize(new Dimension(INFO_PANEL_DIMENSION.width, 100)); // Ensure the scroll pane is
                                                                                            // shorter
        orderSummaryPanel.add(summaryScrollPane);

        orderSummaryPanel.add(totalCostLabel); // Add total cost label to the panel

        // Align the panel to the top right corner
        orderSummaryPanel.setAlignmentX(Component.RIGHT_ALIGNMENT);
        orderSummaryPanel.setAlignmentY(Component.TOP_ALIGNMENT);
        return orderSummaryPanel;
    }

    private JPanel createRestaurantPanel() {
        final var restaurantPanel = new JPanel();
        restaurantPanel.setLayout(new BoxLayout(restaurantPanel, BoxLayout.Y_AXIS));
        restaurantPanel.setPreferredSize(INFO_PANEL_DIMENSION); // Adjust size as needed
        restaurantPanel.setMaximumSize(INFO_PANEL_DIMENSION); // Adjust size as needed

        restaurantPanel.add(new JLabel("Restaurant Info:"));

        // Add restaurantInfoLabel with HTML content
        restaurantPanel.add(restaurantInfoLabel);

        // Add reviews button under the restaurant info label
        reviewsButton.setBackground(Color.BLUE);
        reviewsButton.setForeground(Color.WHITE);
        reviewsButton.setAlignmentX(Component.LEFT_ALIGNMENT);
        reviewsButton.addActionListener(e -> {
            controller.ifPresent(ctrl -> ctrl.handleReviews());
        });
        restaurantPanel.add(reviewsButton);

        // Align the panel to the top left corner
        restaurantPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        restaurantPanel.setAlignmentY(Component.TOP_ALIGNMENT);
        return restaurantPanel;
    }

    private void updateRestaurantInfo() {
        final String restaurantName = this.controller.get().getRestaurantName();
        final Pair<Date, Date> openingHours = this.controller.get().getRestaurantTime();

        final SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm", LOCALE);
        final String openingTime = timeFormat.format(openingHours.get1());
        final String closingTime = timeFormat.format(openingHours.get2());

        // Use HTML to format the text with line breaks
        final String info = String.format("<html>Restaurant: %s<br>Opening Hours: %s - %s</html>", restaurantName,
                openingTime, closingTime);
        restaurantInfoLabel.setText(info);
    }

    private void updateOrderSummary() {
        final StringBuilder summary = new StringBuilder(100); // Initialize with a reasonable capacity
        double totalCost = 0.0;
        for (final var entry : itemQuantityMap.entrySet()) {
            if (entry.getValue() > 0) {
                summary.append(entry.getKey().getName())
                        .append(" (")
                        .append(entry.getKey().getType())
                        .append("): ")
                        .append(entry.getValue())
                        .append(" x ")
                        .append(entry.getKey().getPrice())
                        .append("$\n");
                totalCost += entry.getValue() * entry.getKey().getPrice();
            }
        }
        orderSummaryArea.setText(summary.toString());
        totalCostLabel.setText("Total: $" + String.format(CURRENCY_FORMAT, totalCost)); // Update total cost label

        // Update the send order button state
        updateSendOrderButtonState();
    }

    private void updateSendOrderButtonState() {
        final boolean anySelected = itemQuantityMap.values().stream().anyMatch(quantity -> quantity > 0);
        sendOrderButton.setEnabled(anySelected);
    }

    private void addBackButton(final Container cp) {
        final var logoutButton = new JButton("Back");
        logoutButton.addActionListener(e -> {
            controller.ifPresent(ctrl -> ctrl.handleBack());
        });
        logoutButton.setAlignmentX(Component.RIGHT_ALIGNMENT); // Center alignment
        cp.add(logoutButton);
    }

    /**
     * Updates the balance label with the current user balance.
     *
     * @param balance the current user balance
     */
    public void updateBalance(final double balance) {
        balanceLabel.setText("Balance: $" + String.format(CURRENCY_FORMAT, balance));
    }

    private void addSendOrderButton(final Container cp) {
        sendOrderButton.addActionListener(e -> {
            controller.ifPresent(ctrl -> {
                // Gather order details
                final StringBuilder orderDetails = new StringBuilder(200); // Initialize with a reasonable capacity
                double totalCost = 0.0;
                for (final var entry : itemQuantityMap.entrySet()) {
                    if (entry.getValue() > 0) {
                        orderDetails.append(entry.getKey().getName())
                                .append(" (")
                                .append(entry.getKey().getType())
                                .append("): ")
                                .append(entry.getValue())
                                .append(" x ")
                                .append(entry.getKey().getPrice())
                                .append("$\n");
                        totalCost += entry.getValue() * entry.getKey().getPrice();
                    }
                }
                // Calculate commission

                final double commission = controller.get().getCommission(totalCost);
                final var formattedCommission = String.format(CURRENCY_FORMAT, commission);
                final var formattedTotalCost = String.format(CURRENCY_FORMAT, totalCost);

                // Add total cost and commission details
                orderDetails.append("\nTotal: $").append(formattedTotalCost)
                        .append("\nCommission: $").append(formattedCommission)
                        .append("\nTotal + Commission: $")
                        .append(String.format(CURRENCY_FORMAT, totalCost + commission));

                // Show order confirmation dialog
                final int confirmation = JOptionPane.showConfirmDialog(mainFrame, orderDetails.toString(),
                        "Confirm Order", JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE);

                // If user confirms the order, send it
                if (confirmation == JOptionPane.OK_OPTION) {
                    ctrl.handleSendOrder(itemQuantityMap);
                    this.resetAllSpinners();
                    this.showOrderConfirmation();
                }
            });

        });
        sendOrderButton.setAlignmentX(Component.RIGHT_ALIGNMENT);
        cp.add(sendOrderButton);
    }

    private void resetAllSpinners() {
        itemSpinners.values().forEach(sp -> sp.setValue(INITIAL_SPINNER_VALUE));
        itemQuantityMap.replaceAll((item, oldValue) -> INITIAL_SPINNER_VALUE);
        this.updateOrderSummary(); // Ensure the order summary is updated
    }

    private void showOrderConfirmation() {
        JOptionPane.showMessageDialog(mainFrame, "Order sent successfully!", "Order Confirmation",
                JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * Shows an error dialog indicating that the order could not be sent.
     */
    public void showOrderError() {
        JOptionPane.showMessageDialog(mainFrame, "Order could not be sent. Please try again.", "Order Error",
                JOptionPane.ERROR_MESSAGE);
    }
}

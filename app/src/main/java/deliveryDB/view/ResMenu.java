package deliveryDB.view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Optional;
import java.util.LinkedHashMap;
import java.util.Map;
import javax.swing.*;
import java.awt.*;
import java.util.function.Consumer;
import java.text.SimpleDateFormat;
import deliveryDB.controller.ResMenuCtrl;
import deliveryDB.data.Item;
import deliveryDB.utilities.Pair; 
import java.util.Date;

public class ResMenu {

    private final JFrame mainFrame;
    private Optional<ResMenuCtrl> controller;
    private Map<Item, Integer> itemQuantityMap;
    private JTextArea orderSummaryArea;
    private JLabel totalCostLabel; // Label for total cost
    private JLabel balanceLabel;   // Label for user balance
    private JLabel restaurantInfoLabel; // Label to show restaurant info
    private JButton sendOrderButton; // Button to send the order
    private Map<Item, JSpinner> itemSpinners; // Map to track spinners for each item
    private JButton reviewsButton; // Button to show reviews

    public ResMenu(JFrame mainFrame, ResMenuCtrl controller) {
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

    private void freshPane(Consumer<Container> consumer) {
        var cp = this.mainFrame.getContentPane();
        this.mainFrame.setLayout(new BoxLayout(this.mainFrame.getContentPane(), BoxLayout.PAGE_AXIS));
        cp.removeAll();
        cp.validate();
        cp.repaint();
        consumer.accept(cp);
        this.mainFrame.pack();
    }

    public void displayMenu() {
        freshPane(cp -> {
            var mainPanel = Box.createHorizontalBox(); // Create a horizontal box to hold menu and summary

            var mainBox = Box.createVerticalBox();
            for (var item : this.itemQuantityMap.keySet()) {
                var rowBox = Box.createHorizontalBox();

                var dishLabel = new JLabel(item.getName());
                dishLabel.setPreferredSize(new Dimension(200, 30));
                dishLabel.setMaximumSize(new Dimension(200, 30));
                dishLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
                rowBox.add(dishLabel);

                var typeLabel = new JLabel(item.getType());
                typeLabel.setPreferredSize(new Dimension(100, 30));
                typeLabel.setMaximumSize(new Dimension(100, 30));
                typeLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
                rowBox.add(Box.createHorizontalStrut(10));
                rowBox.add(typeLabel);

                var spinner = new JSpinner(new SpinnerNumberModel(0, 0, 20, 1));
                spinner.setMaximumSize(new Dimension(50, 30));
                spinner.addChangeListener(e -> {
                    this.itemQuantityMap.put(item, (Integer) spinner.getValue());
                    updateOrderSummary();
                    updateSendOrderButtonState(); // Update button state on quantity change
                });
                spinner.setAlignmentX(Component.LEFT_ALIGNMENT);
                rowBox.add(Box.createHorizontalStrut(10));
                rowBox.add(spinner);

                var priceLabel = new JLabel(String.valueOf(item.getPrice()) + "$");
                priceLabel.setPreferredSize(new Dimension(50, 30));
                priceLabel.setMaximumSize(new Dimension(50, 30));
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
            var scrollPane = new JScrollPane(mainBox);
            scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
            scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
            scrollPane.setPreferredSize(new Dimension(600, 600)); // Adjust the size as needed

            mainPanel.add(scrollPane);

            // Create the order summary panel
            var orderSummaryPanel = createOrderSummaryPanel();
            mainPanel.add(orderSummaryPanel);

            // Create and add the restaurant panel
            var restaurantPanel = createRestaurantPanel();
            mainPanel.add(restaurantPanel);

            cp.add(mainPanel);
            // Add back button
            this.addBackButton(cp);
            // Add send order button
            this.addSendOrderButton(cp);
        });
    }

    private JPanel createOrderSummaryPanel() {
        var orderSummaryPanel = new JPanel();
        orderSummaryPanel.setLayout(new BoxLayout(orderSummaryPanel, BoxLayout.Y_AXIS));
        orderSummaryPanel.setPreferredSize(new Dimension(200, 200));
        orderSummaryPanel.setMaximumSize(new Dimension(200, 200));

        // Add balance label to the top of the summary panel
        orderSummaryPanel.add(balanceLabel);
        
        orderSummaryPanel.add(new JLabel("Order Summary:"));

        // Wrap JTextArea in a JScrollPane and set size constraints
        orderSummaryArea.setPreferredSize(new Dimension(200, 100)); // Adjust the height for the scrollable area
        var summaryScrollPane = new JScrollPane(orderSummaryArea);
        summaryScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        summaryScrollPane.setPreferredSize(new Dimension(200, 100)); // Ensure the scroll pane is shorter
        orderSummaryPanel.add(summaryScrollPane);

        orderSummaryPanel.add(totalCostLabel); // Add total cost label to the panel

        // Align the panel to the top right corner
        orderSummaryPanel.setAlignmentX(Component.RIGHT_ALIGNMENT);
        orderSummaryPanel.setAlignmentY(Component.TOP_ALIGNMENT);
        return orderSummaryPanel;
    }

    private JPanel createRestaurantPanel() {
        var restaurantPanel = new JPanel();
        restaurantPanel.setLayout(new BoxLayout(restaurantPanel, BoxLayout.Y_AXIS));
        restaurantPanel.setPreferredSize(new Dimension(200, 100)); // Adjust size as needed
        restaurantPanel.setMaximumSize(new Dimension(200, 100)); // Adjust size as needed
    
        restaurantPanel.add(new JLabel("Restaurant Info:"));
        
        // Add restaurantInfoLabel with HTML content
        restaurantPanel.add(restaurantInfoLabel);

        // Add reviews button under the restaurant info label
        reviewsButton.setBackground(Color.BLUE);
        reviewsButton.setForeground(Color.WHITE);
        reviewsButton.setAlignmentX(Component.LEFT_ALIGNMENT);
        reviewsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.ifPresent(ctrl -> ctrl.handleReviews());
            }
        });
        restaurantPanel.add(reviewsButton);
    
        // Align the panel to the top left corner
        restaurantPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        restaurantPanel.setAlignmentY(Component.TOP_ALIGNMENT);
        return restaurantPanel;
    }
    
    private void updateRestaurantInfo() {
        if (controller.isPresent()) {
            var ctrl = controller.get();
            String restaurantName = ctrl.getRestaurantName();
            Pair<Date, Date> openingHours = ctrl.getRestaurantTime();
    
            SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
            String openingTime = timeFormat.format(openingHours.get1());
            String closingTime = timeFormat.format(openingHours.get2());
    
            // Use HTML to format the text with line breaks
            String info = String.format("<html>Restaurant: %s<br>Opening Hours: %s - %s</html>", restaurantName, openingTime, closingTime);
            restaurantInfoLabel.setText(info);
        } else {
            restaurantInfoLabel.setText("<html>No restaurant information available.</html>");
        }
    }

    private void updateOrderSummary() {
        StringBuilder summary = new StringBuilder();
        double totalCost = 0.0;
        boolean anySelected = false;
        for (var entry : itemQuantityMap.entrySet()) {
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
                anySelected = true;
            }
        }
        orderSummaryArea.setText(summary.toString());
        totalCostLabel.setText("Total: $" + totalCost); // Update total cost label

        // Update the send order button state
        updateSendOrderButtonState();
    }

    private void updateSendOrderButtonState() {
        boolean anySelected = itemQuantityMap.values().stream().anyMatch(quantity -> quantity > 0);
        sendOrderButton.setEnabled(anySelected);
    }

    public void addBackButton(Container cp) {
        var logoutButton = new JButton("Back");
        logoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.ifPresent(ctrl -> ctrl.handleBack());
            }
        });
        logoutButton.setAlignmentX(Component.RIGHT_ALIGNMENT); // Center alignment
        cp.add(logoutButton);
    }

    public void updateBalance(double balance) {
        balanceLabel.setText("Balance: $" + balance);
    }

    public void addSendOrderButton(Container cp) {
        sendOrderButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.ifPresent(ctrl -> {
                    ctrl.handleSendOrder(itemQuantityMap);
                    resetAllSpinners();
                });
            }
        });
        sendOrderButton.setAlignmentX(Component.RIGHT_ALIGNMENT);
        cp.add(sendOrderButton);
    }

    private void resetAllSpinners() {
        for (JSpinner spinner : itemSpinners.values()) {
            spinner.setValue(0);
        }
        // Clear itemQuantityMap after resetting spinners
        itemQuantityMap.replaceAll((item, oldValue) -> 0);
        updateOrderSummary(); // Ensure the order summary is updated
    }

    public void showOrderConfirmation() {
        JOptionPane.showMessageDialog(mainFrame, "Order sent successfully!", "Order Confirmation", JOptionPane.INFORMATION_MESSAGE);
    }

    public void showOrderError() {
        JOptionPane.showMessageDialog(mainFrame, "Order could not be sent. Please try again.", "Order Error", JOptionPane.ERROR_MESSAGE);
    }
}

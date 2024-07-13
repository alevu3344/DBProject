package deliveryDB.view;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Optional;
import java.util.LinkedHashMap;
import java.util.Map;
import javax.swing.*;
import java.awt.*;
import java.util.function.Consumer;

import deliveryDB.controller.ResMenuCtrl;
import deliveryDB.data.Item;

public class ResMenu {

    private final JFrame mainFrame;
    private Optional<ResMenuCtrl> controller;
    private Map<Item, Integer> itemQuantityMap;
    private JTextArea orderSummaryArea;
    private JLabel totalCostLabel; // Label for total cost
    private JLabel balanceLabel;   // Label for user balance

    public ResMenu(JFrame mainFrame) {
        this.controller = Optional.empty();
        this.mainFrame = mainFrame;
        this.orderSummaryArea = new JTextArea();
        this.orderSummaryArea.setLineWrap(true);
        this.totalCostLabel = new JLabel("Total: $0.0"); // Initialize total cost label
        this.balanceLabel = new JLabel(); // Initialize balance label
        orderSummaryArea.setEditable(false);
    }

    public void setItemMap(List<Item> list) {
        this.itemQuantityMap = new LinkedHashMap<>();
        list.forEach(e -> itemQuantityMap.put(e, 0));
        itemQuantityMap.entrySet().forEach(e -> System.out.println(e.getKey().getName() + " " + e.getKey().getType()));
    }

    public void setController(ResMenuCtrl ctrl) {
        this.controller = Optional.of(ctrl);
        this.updateBalance(this.controller.get().getBalance());
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

    private void updateOrderSummary() {
        StringBuilder summary = new StringBuilder();
        double totalCost = 0.0;
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
            }
        }
        orderSummaryArea.setText(summary.toString());
        totalCostLabel.setText("Total: $" + totalCost); // Update total cost label
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

    // Method to update balance label
    public void updateBalance(double balance) {
        balanceLabel.setText("Balance: $" + balance);
    }

    // Method to add send order button
    public void addSendOrderButton(Container cp) {
        var sendOrderButton = new JButton("Send Order");
        sendOrderButton.setBackground(Color.RED);
        sendOrderButton.setForeground(Color.WHITE);
        sendOrderButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.ifPresent(ctrl -> ctrl.handleSendOrder(itemQuantityMap));
            }
        });
        sendOrderButton.setAlignmentX(Component.RIGHT_ALIGNMENT);
        cp.add(sendOrderButton);
    }

    //show a pop up to either confirm the order or cancel it
    public void showOrderConfirmation() {
        JOptionPane.showMessageDialog(mainFrame, "Order sent successfully!", "Order Confirmation", JOptionPane.INFORMATION_MESSAGE);
    }

    //show a pop up to inform the user that the order could not be sent
    public void showOrderError() {
        JOptionPane.showMessageDialog(mainFrame, "Order could not be sent. Please try again.", "Order Error", JOptionPane.ERROR_MESSAGE);
    }
}

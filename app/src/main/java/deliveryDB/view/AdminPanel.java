package deliveryDB.view;

import deliveryDB.controller.AdminController;
import deliveryDB.utilities.Pair;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.function.Consumer;

public class AdminPanel {

    private final JFrame mainFrame;
    private final AdminController adminController;
    private JComboBox<String> reviewComboBox;
    private List<Pair<String, Integer>> restaurants;

    public AdminPanel(JFrame mainFrame, AdminController adminController) {
        this.mainFrame = mainFrame;
        this.adminController = adminController;
        this.restaurants = adminController.getRestaurants();
        this.initializeUI();
    }

    private void initializeUI() {
        freshPane(container -> {
            // Set the layout for the main panel
            container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));
            container.setBackground(Color.WHITE); // Optional: Set background color

            // Create and add the Statistics button
            JButton statisticsButton = new JButton("Statistics");
            statisticsButton.setAlignmentX(Component.CENTER_ALIGNMENT);
            statisticsButton.addActionListener(e -> this.showStatistics());
            container.add(Box.createRigidArea(new Dimension(0, 10))); // Add some space before the button
            container.add(statisticsButton);

            // Create a panel to hold the View Reviews button and ComboBox
            JPanel reviewPanel = new JPanel();
            reviewPanel.setLayout(new BoxLayout(reviewPanel, BoxLayout.Y_AXIS));
            reviewPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

            // Create and add the View Reviews button
            JButton viewReviewsButton = new JButton("View Reviews");
            viewReviewsButton.setAlignmentX(Component.CENTER_ALIGNMENT);
            reviewPanel.add(Box.createRigidArea(new Dimension(0, 10))); // Add some space before the button
            reviewPanel.add(viewReviewsButton);

            // Create and add the ComboBox
            reviewComboBox = new JComboBox<>();
            for (Pair<String, Integer> restaurant : restaurants) {
                reviewComboBox.addItem(restaurant.get1());
            }
            reviewComboBox.setAlignmentX(Component.CENTER_ALIGNMENT);
            reviewComboBox.setMaximumSize(new Dimension(150, 30));
            reviewPanel.add(Box.createRigidArea(new Dimension(0, 10))); // Add some space before the ComboBox
            reviewPanel.add(reviewComboBox);

            // Add action listener to the View Reviews button
            viewReviewsButton.addActionListener(e -> {
                String selectedItem = (String) reviewComboBox.getSelectedItem();
                this.viewReviews(selectedItem);
            });

            // Add the review panel to the container
            container.add(reviewPanel);
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

    private void showStatistics() {
        // Implement the logic to display statistics
        JOptionPane.showMessageDialog(mainFrame, "Statistics functionality not yet implemented.");
    }

    private void viewReviews(String selectedItem) {
        // Find the ristoranteID corresponding to the selected restaurant name
        int ristoranteID = restaurants.stream()
                .filter(pair -> pair.get1().equals(selectedItem))
                .map(Pair::get2)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Selected restaurant not found"));

        // Call the controller method to show reviews
        this.adminController.showReviews(ristoranteID);
    }
}

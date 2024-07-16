package deliveryDB.view.admin;

import deliveryDB.controller.admin.AdminController;
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
            container.setLayout(new BorderLayout());
            container.setBackground(Color.WHITE); // Optional: Set background color

            // Create a panel for the top section
            JPanel topPanel = new JPanel();
            topPanel.setLayout(new BorderLayout());

            // Create and add the Logout button to the top left
            JButton logoutButton = new JButton("Logout");
            logoutButton.addActionListener(e -> this.adminController.handleBack());
            topPanel.add(logoutButton, BorderLayout.WEST);

            // Add the top panel to the container
            container.add(topPanel, BorderLayout.NORTH);

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
            container.add(reviewPanel, BorderLayout.CENTER);

            // Create a panel to hold the bottom buttons
            JPanel bottomButtonPanel = new JPanel();
            bottomButtonPanel.setLayout(new BoxLayout(bottomButtonPanel, BoxLayout.X_AXIS));
            bottomButtonPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

            // Create and add bottom buttons
            JButton topDishButton = new JButton("Top 5 dish");
            topDishButton.addActionListener(e -> showTopDishes());

            JButton popularCuisineButton = new JButton("Most popular cuisine type");
            popularCuisineButton.addActionListener(e -> showPopularCuisine());

            JButton topRestaurantsButton = new JButton("5 most chosen restaurants");
            topRestaurantsButton.addActionListener(e -> showTopRestaurants());

            JButton worstRestaurantButton = new JButton("Worst restaurant");
            worstRestaurantButton.addActionListener(e -> showWorstRestaurant());

            JButton topFattoriniButton = new JButton("Top 5 Fattorini");
            topFattoriniButton.addActionListener(e -> showTopFattorini());

            // Add buttons to the bottom panel
            bottomButtonPanel.add(topDishButton);
            bottomButtonPanel.add(Box.createRigidArea(new Dimension(10, 0))); // Add some space between buttons
            bottomButtonPanel.add(popularCuisineButton);
            bottomButtonPanel.add(Box.createRigidArea(new Dimension(10, 0))); // Add some space between buttons
            bottomButtonPanel.add(topRestaurantsButton);
            bottomButtonPanel.add(Box.createRigidArea(new Dimension(10, 0))); // Add some space between buttons
            bottomButtonPanel.add(worstRestaurantButton);
            bottomButtonPanel.add(Box.createRigidArea(new Dimension(10, 0))); // Add some space between buttons
            bottomButtonPanel.add(topFattoriniButton);

            // Add the bottom button panel to the container
            container.add(bottomButtonPanel, BorderLayout.SOUTH);
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

    private void showPopup(String title, String message) {
        JOptionPane.showMessageDialog(mainFrame, message, title, JOptionPane.INFORMATION_MESSAGE);
    }

    private void showTopDishes() {
        Pair<String, Integer> topDish = this.adminController.topDish();
        showPopup("Top dish", "Top Dish: " + topDish.get1() + " with " + topDish.get2() + " orders.");
    }

    private void showPopularCuisine() {
        String popularCuisine = this.adminController.mostPopularCuisine();
        showPopup("Most Popular Cuisine", "Most Popular Cuisine: " + popularCuisine);
    }

    private void showTopRestaurants() {
        List<Pair<String, Integer>> topRestaurants = this.adminController.mostChosen5Rest();
        StringBuilder message = new StringBuilder("Top 5 Most Chosen Restaurants:\n");
        for (Pair<String, Integer> restaurant : topRestaurants) {
            message.append(restaurant.get1()).append(": ").append(restaurant.get2()).append(" orders\n");
        }
        showPopup("Top 5 Restaurants", message.toString());
    }

    private void showWorstRestaurant() {
        Pair<String, Integer> worstRestaurant = this.adminController.worstRestaurant();
        showPopup("Worst Restaurant", "Worst Restaurant: " + worstRestaurant.get1() + " with an adjusted average of "
                + worstRestaurant.get2());
    }

    private void showTopFattorini() {
        List<Pair<String, Integer>> topFattorini = this.adminController.top5Deliverers();
        StringBuilder message = new StringBuilder("Top 5 Fattorini:\n");
        for (Pair<String, Integer> fattorino : topFattorini) {
            message.append(fattorino.get1()).append(": ").append(fattorino.get2()).append(" deliveries\n");
        }
        showPopup("Miglior fattorino", message.toString());
    }
}

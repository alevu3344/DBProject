package deliverydb.view.admin;

import deliverydb.controller.admin.AdminController;
import deliverydb.utilities.Pair;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.util.List;
import java.util.function.Consumer;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 * Represents the administrative panel of the application where admin users can
 * manage and view various data.
 */
public class AdminPanel {

    private final JFrame mainFrame;
    private final AdminController adminController;
    private final JComboBox<String> reviewComboBox;
    private final List<Pair<String, Integer>> restaurants;

    /**
     * Constructs an AdminPanel with the given JFrame and AdminController.
     *
     * @param mainFrame       the main frame of the application
     * @param adminController the controller that handles administrative actions
     */
    public AdminPanel(final JFrame mainFrame, final AdminController adminController) {
        this.mainFrame = mainFrame;
        this.adminController = adminController;
        this.restaurants = adminController.getRestaurants();
        this.reviewComboBox = new JComboBox<>();
        initializeUI();
    }

    private void initializeUI() {
        freshPane(container -> {
            container.setLayout(new BorderLayout());
            container.setBackground(Color.WHITE);

            // Top panel for the Logout button
            final JPanel topPanel = new JPanel(new BorderLayout());
            final JButton logoutButton = new JButton("Logout");
            logoutButton.addActionListener(e -> adminController.handleBack());
            topPanel.add(logoutButton, BorderLayout.WEST);
            container.add(topPanel, BorderLayout.NORTH);

            // Review panel with View Reviews button and ComboBox
            final JPanel reviewPanel = new JPanel();
            reviewPanel.setLayout(new BoxLayout(reviewPanel, BoxLayout.Y_AXIS));
            reviewPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

            final JButton viewReviewsButton = new JButton("View Reviews");
            viewReviewsButton.setAlignmentX(Component.CENTER_ALIGNMENT);
            reviewPanel.add(Box.createRigidArea(new Dimension(0, 10)));
            reviewPanel.add(viewReviewsButton);

            populateReviewComboBox();
            reviewComboBox.setAlignmentX(Component.CENTER_ALIGNMENT);
            reviewComboBox.setMaximumSize(new Dimension(150, 30));
            reviewPanel.add(Box.createRigidArea(new Dimension(0, 10)));
            reviewPanel.add(reviewComboBox);

            viewReviewsButton.addActionListener(e -> {
                String selectedItem = (String) reviewComboBox.getSelectedItem();
                viewReviews(selectedItem);
            });

            container.add(reviewPanel, BorderLayout.CENTER);

            // Bottom panel with various buttons
            final JPanel bottomButtonPanel = new JPanel();
            bottomButtonPanel.setLayout(new BoxLayout(bottomButtonPanel, BoxLayout.X_AXIS));
            bottomButtonPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

            final JButton topDishButton = new JButton("Top dish");
            topDishButton.addActionListener(e -> showTopDishes());

            final JButton popularCuisineButton = new JButton("Most popular cuisine type");
            popularCuisineButton.addActionListener(e -> showPopularCuisine());

            final JButton topRestaurantsButton = new JButton("5 most chosen restaurants");
            topRestaurantsButton.addActionListener(e -> showTopRestaurants());

            final JButton worstRestaurantButton = new JButton("Worst restaurant");
            worstRestaurantButton.addActionListener(e -> showWorstRestaurant());

            final JButton topFattoriniButton = new JButton("Best deliverer");
            topFattoriniButton.addActionListener(e -> showTopFattorini());

            bottomButtonPanel.add(topDishButton);
            bottomButtonPanel.add(Box.createRigidArea(new Dimension(10, 0)));
            bottomButtonPanel.add(popularCuisineButton);
            bottomButtonPanel.add(Box.createRigidArea(new Dimension(10, 0)));
            bottomButtonPanel.add(topRestaurantsButton);
            bottomButtonPanel.add(Box.createRigidArea(new Dimension(10, 0)));
            bottomButtonPanel.add(worstRestaurantButton);
            bottomButtonPanel.add(Box.createRigidArea(new Dimension(10, 0)));
            bottomButtonPanel.add(topFattoriniButton);

            container.add(bottomButtonPanel, BorderLayout.SOUTH);
        });
    }

    private void freshPane(final Consumer<Container> consumer) {
        final Container cp = mainFrame.getContentPane();
        cp.removeAll();
        cp.validate();
        cp.repaint();
        consumer.accept(cp);
        mainFrame.pack();
    }

    private void populateReviewComboBox() {
        for (Pair<String, Integer> restaurant : restaurants) {
            reviewComboBox.addItem(restaurant.get1());
        }
    }

    private void viewReviews(final String selectedItem) {
        final int ristoranteID = restaurants.stream()
                .filter(pair -> pair.get1().equals(selectedItem))
                .map(Pair::get2)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Selected restaurant not found"));

        adminController.showReviews(ristoranteID);
    }

    private void showPopup(final String title, final String message) {
        JOptionPane.showMessageDialog(mainFrame, message, title, JOptionPane.INFORMATION_MESSAGE);
    }

    private void showTopDishes() {
        final Pair<Pair<String, Integer>, String> topDish = adminController.topDish();
        showPopup("Top dish", "Top Dish: " + topDish.get1().get1() + " from " + topDish.get2() + ", with "
                + topDish.get1().get2() + " orders.");
    }

    private void showPopularCuisine() {
        final String popularCuisine = adminController.mostPopularCuisine();
        showPopup("Most Popular Cuisine", "Most Popular Cuisine: " + popularCuisine);
    }

    private void showTopRestaurants() {
        final List<Pair<String, Integer>> topRestaurants = adminController.mostChosen5Rest();
        final StringBuilder message = new StringBuilder("Most Chosen Restaurants:\n");
        for (final Pair<String, Integer> restaurant : topRestaurants) {
            message.append(restaurant.get1()).append(": ").append(restaurant.get2()).append(" orders\n");
        }
        showPopup("Most chosen restaurant", message.toString());
    }

    private void showWorstRestaurant() {
        final Pair<String, Integer> worstRestaurant = adminController.worstRestaurant();
        showPopup("Worst Restaurant", "Worst Restaurant: " + worstRestaurant.get1() + " with an adjusted average of "
                + worstRestaurant.get2());
    }

    private void showTopFattorini() {
        final List<Pair<String, Integer>> topFattorini = adminController.top5Deliverers();
        final StringBuilder message = new StringBuilder("Top deliverer:\n");
        for (final Pair<String, Integer> fattorino : topFattorini) {
            message.append(fattorino.get1()).append(": ").append(fattorino.get2()).append(" deliveries\n");
        }
        showPopup("Miglior fattorino", message.toString());
    }
}

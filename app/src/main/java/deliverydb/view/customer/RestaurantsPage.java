package deliverydb.view.customer;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.*;

import deliverydb.controller.customer.ResController;
import deliverydb.utilities.Pair;

import java.util.function.Consumer;
import java.util.Map;

/**
 * Represents the page where users can view a list of restaurants. It displays each restaurant with clickable labels
 * and allows users to log out.
 */
public class RestaurantsPage {

    private ResController controller;
    private final JFrame mainFrame;

    /**
     * Constructs a RestaurantsPage with the given JFrame and ResController.
     *
     * @param mainFrame the main frame of the application
     * @param controller the controller that handles restaurant interactions
     */
    public RestaurantsPage(JFrame mainFrame, ResController controller) {
        this.controller = controller;
        this.mainFrame = mainFrame;
    }

    /**
     * Displays the list of restaurants on the page.
     *
     * @param restaurants a map where the key is a Pair containing restaurant name and ID, and the value is the cuisine type
     */
    public void displayRestaurants(Map<Pair<String, Integer>, String> restaurants) {
        freshPane(cp -> {
            var box = Box.createVerticalBox();
            for (var restaurant : restaurants.entrySet()) {
                var restaurantBox = Box.createHorizontalBox();

                var label = clickableLabel(restaurant.getKey().get1(), () -> {
                    this.controller.handleRestaurant(restaurant.getKey().get2());
                });
                label.setAlignmentX(Component.LEFT_ALIGNMENT);

                var cuisineLabel = new JLabel(restaurant.getValue());
                cuisineLabel.setFont(new Font("Arial", Font.PLAIN, 20));
                cuisineLabel.setAlignmentX(Component.RIGHT_ALIGNMENT);

                restaurantBox.add(label);
                restaurantBox.add(Box.createHorizontalGlue()); // To push cuisine label to the right
                restaurantBox.add(cuisineLabel);

                box.add(restaurantBox);
            }
            cp.add(box);
            this.addLogoutButton(cp); // Pass the container to addLogoutButton
        });
    }

    /**
     * Adds a logout button to the specified container.
     *
     * @param cp the container to add the logout button to
     */
    public void addLogoutButton(Container cp) {
        var logoutButton = new JButton("Logout");
        logoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.handleLogOut();
            }
        });
        logoutButton.setAlignmentX(Component.CENTER_ALIGNMENT); // Center alignment
        cp.add(logoutButton);
    }

    /**
     * Refreshes the main content pane of the JFrame and applies the given consumer to it.
     *
     * @param consumer a Consumer function that modifies the content pane
     */
    private void freshPane(Consumer<Container> consumer) {
        var cp = this.mainFrame.getContentPane();
        cp.removeAll();
        cp.revalidate();
        cp.repaint();
        this.mainFrame.setLayout(new BoxLayout(cp, BoxLayout.PAGE_AXIS));
        consumer.accept(cp);
        this.mainFrame.pack();
    }

    /**
     * Creates a JLabel that acts as a clickable link.
     *
     * @param labelText the text to display on the label
     * @param action the action to perform when the label is clicked
     * @return a JLabel configured to be clickable
     */
    private JLabel clickableLabel(String labelText, Runnable action) {
        var label = new JLabel(labelText);
        label.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        label.setFont(new Font("Arial", Font.PLAIN, 20));
        label.setHorizontalAlignment(SwingConstants.LEFT);
        label.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                SwingUtilities.invokeLater(() -> {
                    action.run();
                });
            }
        });
        return label;
    }
}

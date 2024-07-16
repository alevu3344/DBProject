package deliveryDB.view.customer;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.*;
import java.util.function.Consumer;

import deliveryDB.controller.customer.ResController;
import deliveryDB.utilities.Pair;
import java.util.Map;

public class RestaurantsPage {

    private ResController controller;
    private final JFrame mainFrame;


    public RestaurantsPage(JFrame mainFrame, ResController controller) {
        this.controller = controller;
        this.mainFrame = mainFrame;
    }


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

    public JFrame getMainFrame() {
        return this.mainFrame;
    }

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

    private void freshPane(Consumer<Container> consumer) {
        var cp = this.mainFrame.getContentPane();
        cp.removeAll();
        cp.revalidate();
        cp.repaint();
        this.mainFrame.setLayout(new BoxLayout(cp, BoxLayout.PAGE_AXIS));
        consumer.accept(cp);
        this.mainFrame.pack();
    }

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

package deliveryDB.view;


import deliveryDB.controller.ResController;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Optional;
import javax.swing.*;
import java.util.function.Consumer;
import deliveryDB.utilities.Pair;

import java.util.List;

public class RestaurantsPage {
    

    private Optional<ResController> controller;
    private final JFrame mainFrame;


    public RestaurantsPage(JFrame mainFrame) {
        this.controller = Optional.empty();
        this.mainFrame = mainFrame;
        System.out.println("Constructor of Restaurants page");

    }
    public void setController(ResController resController) {
        this.controller = Optional.of(resController);
    }

    public void displayRestaurants(List<Pair<String, Integer>> restaurants) {
        freshPane(cp -> {
            var box = Box.createVerticalBox();
            for (var restaurant : restaurants) {
                var label = clickableLabel(restaurant.get1(), () -> {
                    this.controller.ifPresent(ctrl -> {
                        ctrl.handleRestaurant(restaurant.get2());
                    });
                });
                label.setAlignmentX(Component.CENTER_ALIGNMENT); // Center alignment
                box.add(label);
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
                controller.ifPresent(ctrl -> ctrl.handleLogOut());
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
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.addMouseListener(
                new MouseAdapter() {
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

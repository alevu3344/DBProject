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
                box.add(clickableLabel(restaurant.get1(), () -> {
                    this.controller.ifPresent(ctrl -> {
                        ctrl.handleRestaurant(restaurant.get2());
                    });
                }));
            }
            cp.add(box);
        });
    }

    private void freshPane(Consumer<Container> fill) {
        this.mainFrame.getContentPane().removeAll();
        fill.accept(this.mainFrame.getContentPane());
        this.mainFrame.revalidate();
        this.mainFrame.repaint();
    }

    private JLabel clickableLabel(String labelText, Runnable action) {
        var label = new JLabel(labelText);
        label.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        //make the label bigger and centered
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

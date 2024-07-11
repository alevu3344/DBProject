package deliveryDB.view;


import deliveryDB.controller.ResController;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
            cp.setLayout(new BoxLayout(cp, BoxLayout.PAGE_AXIS));
            for (Pair<String, Integer> restaurant : restaurants) {
                JButton button = new JButton(restaurant.get1());
                button.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        if (controller.isPresent()) {
                            controller.get().handleRestaurant(restaurant.get2());
                        } else {
                            throw new IllegalStateException("RestaurantsPage's Controller is undefined");
                        }
                    }
                });
                cp.add(button);
            }
        });
    }

    private void freshPane(Consumer<Container> fill) {
        this.mainFrame.getContentPane().removeAll();
        fill.accept(this.mainFrame.getContentPane());
        this.mainFrame.revalidate();
        this.mainFrame.repaint();
    }
}

package deliveryDB.view;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import deliveryDB.controller.ResPreviewCtrl;

import javax.swing.*;
import java.util.function.Consumer;
import deliveryDB.utilities.Pair;
import java.util.LinkedHashMap;
import java.awt.Component;
import deliveryDB.data.Item;
import java.util.Map;

public class ResPreview {

    private final JFrame mainFrame;
    private Optional<ResPreviewCtrl> controller;
    private Map<Item, Integer> itemQuantityMap;

    public ResPreview(JFrame mainFrame) {
        this.controller = Optional.empty();
        this.mainFrame = mainFrame;
    }

    public void setItemMap(List<Item> list) {
        this.itemQuantityMap = new LinkedHashMap<>();
        list.forEach(e -> itemQuantityMap.put(e, 0));
        itemQuantityMap.entrySet().forEach(e -> System.out.println(e.getKey().getName() + " " + e.getKey().getType()));
    }

    public void setController(ResPreviewCtrl ctrl) {
        this.controller = Optional.of(ctrl);
    }

    // List the menu for the restaurant

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

            // Set preferred size for the scroll pane
            scrollPane.setPreferredSize(new Dimension(600, 600)); // Adjust the size as needed

            // Add the scroll pane to the content pane
            cp.add(scrollPane);
            // Add back button
            this.addBackButton(cp);
        });
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
    
    
    


}

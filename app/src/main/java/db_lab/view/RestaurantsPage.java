package db_lab.view;

import db_lab.controller.Controller;
import db_lab.controller.RestaurantsController;
import db_lab.data.Product;
import db_lab.data.ProductPreview;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

public class RestaurantsPage {

    private final JFrame mainFrame;
    private JLabel titleLabel;
    private Optional<RestaurantsController> controller;

    public RestaurantsPage(JFrame mainFrame) {
        this.controller = Optional.empty();
        this.mainFrame = mainFrame;

    }



    public void setController(RestaurantsController ctrl){
        this.controller = Optional.of(ctrl);
    }

    private RestaurantsController getController() {
        if (this.controller.isPresent()) {
            return this.controller.get();
        } else {
            throw new IllegalStateException(
                """
                The RestaurantsPage's Controller is undefined, did you remember to call
                `setController` before starting the application?
                Remeber that `MainMenu` needs a reference to the controller in order
                to notify it of button clicks and other changes.
                """
            );
        }
    }





    public void loadingProduct() {
        freshPane(cp -> cp.add(new JLabel("Loading product...", SwingConstants.CENTER)));
    }

    public void loadingPreviews() {
        freshPane(cp -> cp.add(new JLabel("Loading previews...", SwingConstants.CENTER)));
    }

    public void productPage(Product product) {
        freshPane(cp -> {
            cp.add(new JLabel(product.name));
            cp.add(new JLabel(" "));
            cp.add(new JLabel(product.description));
            cp.add(new JLabel(" "));
            product.composition
                .entrySet()
                .stream()
                .sorted((entry1, entry2) -> entry2.getValue().compareTo(entry1.getValue()))
                .map(entry -> {
                    var percent = Math.round(entry.getValue() * 100) + "%";
                    return "- " + entry.getKey().description + " " + percent;
                })
                .forEach(entry -> cp.add(new JLabel(entry)));

            cp.add(new JLabel(" "));
            cp.add(button("Go back", () -> this.getController().userClickedBack()));
        });
    }

    public void failedToLoadProduct(ProductPreview productPreview) {
        freshPane(cp -> {
            cp.add(new JLabel("I couldn't load the page for product", SwingConstants.CENTER));
            cp.add(new JLabel(productPreview.name, SwingConstants.CENTER));
            cp.add(new JLabel(" "));
            cp.add(button("Retry", () -> this.getController().userClickedPreview(productPreview)));
            cp.add(button("Go back", () -> this.getController().userClickedBack()));
        });
    }

    public void previewPage(List<ProductPreview> productPreviews) {
        freshPane(cp -> {
            cp.add(new JLabel("All our products", SwingConstants.CENTER));
            cp.add(new JLabel(" "));
            this.addPreviews(cp, productPreviews);
            cp.add(new JLabel(" "));
            cp.add(button("Product Page", () -> this.getController().userClickedReloadPreviews()));
        });
    }

    private void addPreviews(Container cp, List<ProductPreview> productPreviews) {
        productPreviews.forEach(preview -> {
            var tags = preview.tags
                .stream()
                .map(tag -> tag.name)
                .sorted((tag1, tag2) -> tag1.compareTo(tag2))
                .collect(Collectors.joining(","));
            var label = "- " + preview.name + " [" + tags + "]";
            cp.add(clickableLabel(label, () -> this.getController().userClickedPreview(preview)));
        });
    }

    public void failedToLoadPreviews() {
        freshPane(cp -> {
            cp.add(new JLabel("I couldn't load the previews", SwingConstants.CENTER));
            cp.add(button("Retry", () -> this.getController().userClickedReloadPreviews()));
        });
    }

    private JButton button(String label, Runnable action) {
        var button = new JButton(label);
        button.addActionListener(event -> {
            button.setEnabled(false);
            SwingUtilities.invokeLater(() -> {
                action.run();
                button.setEnabled(true);
            });
        });
        return button;
    }

    private JLabel clickableLabel(String labelText, Runnable action) {
        var label = new JLabel(labelText);
        label.addMouseListener(
            new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    SwingUtilities.invokeLater(() -> {
                        action.run();
                    });
                }
            }
        );
        return label;
    }

    private void freshPane(Consumer<Container> consumer) {
        var cp = this.mainFrame.getContentPane();
        cp.removeAll();
        consumer.accept(cp);
        cp.validate();
        cp.repaint();
        this.mainFrame.pack();
    }


}

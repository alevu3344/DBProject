package db_lab.view;

import db_lab.Controller;
import db_lab.data.Product;
import db_lab.data.ProductPreview;
import db_lab.model.Model;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.stream.Collectors;

public final class MainMenu {


    private static Runnable onClose;
    private Optional<Controller> controller = Optional.empty();
    private Stage mainStage;

    public MainMenu(Stage primaryStage){
        this.mainStage = primaryStage;
        primaryStage.setTitle("Tessiland");
        primaryStage.setOnCloseRequest(event -> {
            if (onClose != null) {
                onClose.run();
            }
            Platform.exit();
        });

        primaryStage.setScene(new Scene(new VBox(), 5000, 1000));
        primaryStage.show();

    }

    public static void setOnClose(Runnable onCloseAction) {
        onClose = onCloseAction;
    }

    public void setController(Controller ctrl) {
        this.controller = Optional.of(ctrl);
    }

    private Controller getController() {
        return controller.orElseThrow(() -> new IllegalStateException(
            "The MainMenu's Controller is undefined. Did you remember to call " +
            "setController before starting the application? Remember that " +
            "MainMenu needs a reference to the controller in order to notify it " +
            "of button clicks and other changes."
        ));
    }

    public void loadingProduct() {
        freshPane(cp -> cp.getChildren().add(new Label("Loading product...")));
    }

    public void loadingPreviews() {
        freshPane(cp -> cp.getChildren().add(new Label("Loading previews...")));
    }

    public void productPage(Product product) {
        freshPane(cp -> {
            cp.getChildren().add(new Label(product.name));
            cp.getChildren().add(new Label(" "));
            cp.getChildren().add(new Label(product.description));
            cp.getChildren().add(new Label(" "));
            product.composition.entrySet().stream()
                .sorted((entry1, entry2) -> entry2.getValue().compareTo(entry1.getValue()))
                .map(entry -> {
                    var percent = Math.round(entry.getValue() * 100) + "%";
                    return "- " + entry.getKey().description + " " + percent;
                })
                .forEach(entry -> cp.getChildren().add(new Label(entry)));

            cp.getChildren().add(new Label(" "));
            cp.getChildren().add(button("Go back", () -> this.getController().userClickedBack()));
        });
    }

    public void failedToLoadProduct(ProductPreview productPreview) {
        freshPane(cp -> {
            cp.getChildren().add(new Label("I couldn't load the page for product"));
            cp.getChildren().add(new Label(productPreview.name));
            cp.getChildren().add(new Label(" "));
            cp.getChildren().add(button("Retry", () -> this.getController().userClickedPreview(productPreview)));
            cp.getChildren().add(button("Go back", () -> this.getController().userClickedBack()));
        });
    }

    public void previewPage(List<ProductPreview> productPreviews) {
        freshPane(cp -> {
            cp.getChildren().add(new Label("All our products"));
            cp.getChildren().add(new Label(" "));
            this.addPreviews(cp, productPreviews);
            cp.getChildren().add(new Label(" "));
            cp.getChildren().add(button("Reload", () -> this.getController().userClickedReloadPreviews()));
            //cp.getChildren().add(button("Go to products", ()-> this.getController().userClickedPreview(null););
        });
    }

    private void addPreviews(VBox cp, List<ProductPreview> productPreviews) {
        productPreviews.forEach(preview -> {
            var tags = preview.tags.stream()
                .map(tag -> tag.name)
                .sorted(String::compareTo)
                .collect(Collectors.joining(","));
            var label = "- " + preview.name + " [" + tags + "]";
            cp.getChildren().add(clickableLabel(label, () -> this.getController().userClickedPreview(preview)));
        });
    }

    public void failedToLoadPreviews() {
        freshPane(cp -> {
            cp.getChildren().add(new Label("I couldn't load the previews"));
            cp.getChildren().add(button("Retry", () -> this.getController().userClickedReloadPreviews()));
        });
    }

    private Button button(String label, Runnable action) {
        var button = new Button(label);
        button.setOnAction(event -> {
            button.setDisable(true);
            Platform.runLater(() -> {
                action.run();
                button.setDisable(false);
            });
        });
        return button;
    }

    private Label clickableLabel(String labelText, Runnable action) {
        var label = new Label(labelText);
        label.setOnMouseClicked(event -> Platform.runLater(action));
        return label;
    }

    private void freshPane(Consumer<VBox> consumer) {
        var cp = new VBox();
        consumer.accept(cp);
        var scene = new Scene(cp);
        mainStage.setScene(scene);
        mainStage.sizeToScene();
    }
}

package deliveryDB.view;

import java.awt.Container;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.stream.Collectors;


import deliveryDB.controller.PreviewsController;
import deliveryDB.controller.ResPreviewCtrl;

import javax.swing.*;
import java.util.function.Consumer;
import deliveryDB.utilities.Pair;

import java.util.List;

public class ResPreview {

    private final JFrame mainFrame;
    private Optional<ResPreviewCtrl> controller;

    public ResPreview(JFrame mainFrame) {
        this.controller = Optional.empty();
        this.mainFrame = mainFrame;

    }

    //List the menu for the restaurant

    private void freshPane(Consumer<Container> consumer) {
        var cp = this.mainFrame.getContentPane();
        this.mainFrame.setLayout(new BoxLayout(this.mainFrame.getContentPane(), BoxLayout.PAGE_AXIS));
        cp.removeAll();
        cp.validate();
        cp.repaint();
        consumer.accept(cp);
        this.mainFrame.pack();
    }

    public void setController(ResPreviewCtrl ctrl) {
        this.controller = Optional.of(ctrl);
    }

}

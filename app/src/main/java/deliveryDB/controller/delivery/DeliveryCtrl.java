package deliveryDB.controller.delivery;

import javax.swing.JFrame;

import deliveryDB.controller.Controller;
import deliveryDB.model.Model;
import deliveryDB.view.delivery.DeliveryPanel;
import deliveryDB.view.delivery.DeliveryPanel.Flag;

public class DeliveryCtrl implements Controller {

    private final Model model;
    private final JFrame mainFrame;
    private final Controller prevCtrl;

    public DeliveryCtrl(Controller prevCtrl, JFrame mainFrame, Model model) {
        this.prevCtrl = prevCtrl;
        this.model = model;
        this.mainFrame = mainFrame;
        new DeliveryPanel(this.mainFrame, this);
    }

    @Override
    public void show() {
        new DeliveryPanel(this.mainFrame, this);
    }

    public void handleLogOut() {
        this.model.logout();
        this.prevCtrl.show();
    }

    public void showOrders(Flag flag) {
        new OrdersCtrl(flag, this, this.mainFrame, this.model);
    }

    public float getBalance() {
        return this.model.getBalance();
    }

}

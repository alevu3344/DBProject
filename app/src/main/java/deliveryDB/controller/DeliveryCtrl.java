package deliveryDB.controller;

import javax.swing.JFrame;
import deliveryDB.model.DelModel;
import deliveryDB.view.DeliveryPanel;
import deliveryDB.view.DeliveryPanel.Flag;

public class DeliveryCtrl implements Controller {
    
    

    private final DelModel model;
    private DeliveryPanel view;
    private final JFrame mainFrame;
    private final Controller prevCtrl;

    public DeliveryCtrl(Controller prevCtrl, JFrame mainFrame, DelModel model) {
        this.prevCtrl = prevCtrl;
        this.model = model;
        this.mainFrame = mainFrame;
        this.view = new DeliveryPanel(this.mainFrame, this);
    }

    @Override
    public void show() {
        this.view = new DeliveryPanel(this.mainFrame, this);
    }

    public void handleLogOut(){
        this.model.logout();
        this.prevCtrl.show();
    }

    public void showOrders(Flag flag){
        new OrdersCtrl(flag, this, this.mainFrame, this.model);
    }


}

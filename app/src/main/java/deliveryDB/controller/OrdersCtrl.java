package deliveryDB.controller;

import javax.swing.JFrame;

import deliveryDB.data.Order;
import deliveryDB.data.Restaurant;
import deliveryDB.model.DelModel;
import deliveryDB.utilities.Pair;
import deliveryDB.view.DeliveryPanel.Flag;
import deliveryDB.view.OrdersView;

import java.util.List;



public class OrdersCtrl implements Controller {
    
    private OrdersView view;
    private final DelModel model;
    private final JFrame mainFrame;
    private final Controller prevCtrl;
    private Flag flag;

    public OrdersCtrl( Flag flag, Controller prevCtrl, JFrame mainFrame, DelModel model) {
        this.prevCtrl = prevCtrl;
        this.model = model;
        this.mainFrame = mainFrame;
        this.flag = flag;
        this.view = new OrdersView(flag, this.mainFrame, this);
        
    }
    @Override
    public void show() {
        this.view = new OrdersView(this.flag ,this.mainFrame, this);
    }

    public void handleBack(){
        this.prevCtrl.show();
    }

    public List<Order> getOrdersOnFlag(Flag flag){
        switch(flag){
            case AVAILABLE:
                return this.model.getAvailableOrders();
            case ACCEPTED:
                return this.model.getAcceptedOrders();
            default:
                return null;
        }
    }

    public Pair<Restaurant, String> restaurantDetails(Order order){
       return new Pair<>(this.model.onRestaurantID(order.getRestaurantID()), this.model.getAddress(order.getUsername()));
    }

    public boolean acceptOrder(int orderID){
        return this.model.acceptOrder(orderID);
    }

    public boolean deliverOrder(int orderID){
        return this.model.deliverOrder(orderID);
    }


}

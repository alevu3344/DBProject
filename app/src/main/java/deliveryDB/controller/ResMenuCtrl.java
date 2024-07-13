package deliveryDB.controller;

import deliveryDB.model.DelModel;
import deliveryDB.view.ResMenu;

import java.util.Map;

import deliveryDB.data.Item;
import deliveryDB.data.Restaurant;


public class ResMenuCtrl {
    
    private final DelModel model;
    private final ResMenu view;
    private final ResController prevctrl;
    private Restaurant restaurant;
    public ResMenuCtrl(ResController prevctrl, ResMenu view, DelModel model, Restaurant restaurant) {
        this.view = view;
        this.model = model;
        this.prevctrl = prevctrl;
        this.restaurant = restaurant;
    
        
        view.setItemMap(this.model.getMenuFor(restaurant.getRestaurantID()));
        view.displayMenu();
    }   


    public void handleBack(){
        this.prevctrl.showRestaurants();
    }

    public float getBalance(){
        return this.model.getBalance();
    }

    public void handleSendOrder(Map<Item, Integer> order){
        if(this.model.sendOrder(order, this.restaurant.getRestaurantID())){
            this.view.updateBalance(this.model.getBalance());
            this.view.showOrderConfirmation();
            return;
        }
        this.view.showOrderError();
        return;


    }

}

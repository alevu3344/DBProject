package deliveryDB.controller;

import deliveryDB.model.DelModel;
import deliveryDB.view.ResMenu;
import deliveryDB.data.Restaurant;
import java.util.Optional;


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

}

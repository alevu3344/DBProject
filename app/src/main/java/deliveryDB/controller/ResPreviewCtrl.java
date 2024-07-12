package deliveryDB.controller;

import deliveryDB.model.DelModel;
import deliveryDB.view.ResPreview;
import deliveryDB.data.Restaurant;
import java.util.Optional;


public class ResPreviewCtrl {
    
    private final DelModel model;
    private final ResPreview view;
    private final ResController prevctrl;
    private Restaurant restaurant;
    public ResPreviewCtrl(ResController prevctrl, ResPreview view, DelModel model, Restaurant restaurant) {
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



}

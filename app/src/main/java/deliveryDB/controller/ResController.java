package deliveryDB.controller;

import deliveryDB.model.DelModel;
import deliveryDB.view.RestaurantsPage;

public class ResController {
    

    private final DelModel model;
    private final RestaurantsPage resView;


    public ResController(RestaurantsPage resView, DelModel model) {
        this.resView = resView;
        this.model = model;
        resView.setController(this);
        this.showRestaurants();
    }
    /* 
    public void handleBack(){
        this.model.logout();
    }
    */
    public void showRestaurants(){
        this.resView.displayRestaurants(this.model.getRestaurants());
    }

    public void handleRestaurant(int restaurantID){
        
    }




}

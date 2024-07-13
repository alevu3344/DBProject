package deliveryDB.controller;

import deliveryDB.model.DelModel;
import deliveryDB.view.RestaurantsPage;
import deliveryDB.view.ResMenu;

public class ResController {
    

    private final DelModel model;
    private final RestaurantsPage resView;
    private final LoginController prevCtrl;


    public ResController(LoginController prevCtrl,RestaurantsPage resView, DelModel model) {
        this.resView = resView;
        this.model = model;
        this.prevCtrl = prevCtrl;
        resView.setController(this);
        this.showRestaurants();
    }

    public void showRestaurants(){
        this.resView.displayRestaurants(this.model.getRestaurants());
    }

    public void handleRestaurant(int restaurantID){
        new ResMenuCtrl(this, this.resView.getMainFrame() , this.model, this.model.onRestaurantID(restaurantID));
    }

    public void handleLogOut(){
        this.model.logout();
        this.prevCtrl.show();
    }




}

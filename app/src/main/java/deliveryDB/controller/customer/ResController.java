package deliveryDB.controller.customer;

import deliveryDB.controller.authentication.LoginController;
import deliveryDB.model.Model;
import deliveryDB.view.customer.RestaurantsPage;

public class ResController {
    

    private final Model model;
    private final RestaurantsPage resView;
    private final LoginController prevCtrl;


    public ResController(LoginController prevCtrl,RestaurantsPage resView, Model model) {
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

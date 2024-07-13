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
        var x = this.model.getRestaurants();
        this.resView.displayRestaurants(x);
    }

    public void handleRestaurant(int restaurantID){
        var res = this.model.onRestaurantID(restaurantID);
        var view = new ResMenu(this.resView.getMainFrame());
        var resPrvCtrl = new ResMenuCtrl(this, view , this.model, res);
        view.setController(resPrvCtrl);
    }

    public void handleLogOut(){
        this.model.logout();
        this.prevCtrl.show();
    }




}

package deliveryDB.controller;

import deliveryDB.data.Restaurant;
import deliveryDB.model.DelModel;
import deliveryDB.view.RestaurantsPage;
import deliveryDB.view.ResPreview;

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
    /* 
    public void handleBack(){
        this.model.logout();
    }
    */
    public void showRestaurants(){
        this.resView.displayRestaurants(this.model.getRestaurants());
    }

    public void handleRestaurant(int restaurantID){
        var res = this.model.onRestaurantID(restaurantID);
        var view = new ResPreview(this.resView.getMainFrame());
        var resPrvCtrl = new ResPreviewCtrl(this, view , this.model, res);
        view.setController(resPrvCtrl);

    }

    public void handleLogOut(){
        this.model.logout();
        this.prevCtrl.show();
    }




}

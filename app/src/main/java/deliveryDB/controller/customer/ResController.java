package deliveryDB.controller.customer;

import javax.swing.JFrame;

import deliveryDB.controller.Controller;
import deliveryDB.model.Model;
import deliveryDB.view.customer.RestaurantsPage;

public class ResController implements Controller {
    

    private final Model model;
    private RestaurantsPage resView;
    private final Controller prevCtrl;
    private JFrame mainFrame;


    public ResController(Controller prevCtrl,JFrame mainFrame, Model model) {
        this.mainFrame = mainFrame;
        this.model = model;
        this.prevCtrl = prevCtrl;
        this.resView = new RestaurantsPage(this.mainFrame, this);
        this.showRestaurants();
    }

    public void showRestaurants(){
        this.resView.displayRestaurants(this.model.getRestaurants());
    }

    public void handleRestaurant(int restaurantID){
        new ResMenuCtrl(this, this.mainFrame, this.model, this.model.onRestaurantID(restaurantID));
    }

    public void handleLogOut(){
        this.model.logout();
        this.prevCtrl.show();
    }

    @Override
    public void show() {
        this.resView = new RestaurantsPage(this.mainFrame, this);
    }




}

package deliverydb.controller.customer;

import javax.swing.JFrame;

import deliverydb.controller.Controller;
import deliverydb.model.Model;
import deliverydb.view.customer.RestaurantsPage;

/**
 * The ResController class handles interactions related to the restaurant listings
 * within the DeliveryDB application. It supports viewing a list of restaurants,
 * navigating to a restaurant's menu, and handling user logout.
 */
public class ResController implements Controller {

    private final Model model;
    private RestaurantsPage resView;
    private final Controller prevCtrl;
    private JFrame mainFrame;

    /**
     * Constructs a ResController object.
     *
     * @param prevCtrl the previous controller to return to when necessary
     * @param mainFrame the main application frame
     * @param model the data model for the application
     */
    public ResController(final Controller prevCtrl, final JFrame mainFrame, final Model model) {
        this.mainFrame = mainFrame;
        this.model = model;
        this.prevCtrl = prevCtrl;
        this.resView = new RestaurantsPage(this.mainFrame, this);
        this.showRestaurants();
    }

    /**
     * Displays the list of restaurants.
     */
    public void showRestaurants() {
        this.resView.displayRestaurants(this.model.getRestaurants());
    }

    /**
     * Handles the action to navigate to a selected restaurant's menu.
     *
     * @param restaurantID the ID of the selected restaurant
     */
    public void handleRestaurant(final int restaurantID) {
        new ResMenuCtrl(this, this.mainFrame, this.model, this.model.onRestaurantID(restaurantID));
    }

    /**
     * Handles the action to log out the current user and returns to the previous controller's view.
     */
    public void handleLogOut() {
        this.model.logout();
        this.prevCtrl.show();
    }

    /**
     * Displays the restaurants page view associated with this controller.
     */
    @Override
    public void show() {
        this.resView = new RestaurantsPage(this.mainFrame, this);
    }

}

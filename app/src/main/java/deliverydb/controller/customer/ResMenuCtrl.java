package deliverydb.controller.customer;

import java.util.Map;
import javax.swing.JFrame;

import deliverydb.controller.Controller;
import deliverydb.controller.ReviewController;
import deliverydb.data.Item;
import deliverydb.data.Restaurant;
import deliverydb.model.Model;
import deliverydb.utilities.Pair;
import deliverydb.view.customer.ResMenu;

import java.util.LinkedHashMap;
import java.util.Date;

/**
 * The ResMenuCtrl class handles the interactions related to the restaurant menu
 * within the DeliveryDB application. It supports viewing the restaurant menu,
 * sending orders, viewing reviews, and retrieving restaurant information.
 */
public class ResMenuCtrl implements Controller {

    private final Model model;
    private ResMenu view;
    private final ResController prevctrl;
    private final Restaurant restaurant;
    private final JFrame mainFrame;
    private final float commission;

    /**
     * Constructs a ResMenuCtrl object.
     *
     * @param prevctrl the previous controller to return to when necessary
     * @param mainFrame the main application frame
     * @param model the data model for the application
     * @param restaurant the restaurant for which the menu is managed
     */
    public ResMenuCtrl(final ResController prevctrl, final JFrame mainFrame, final Model model, final Restaurant restaurant) {
        this.model = model;
        this.mainFrame = mainFrame;
        this.prevctrl = prevctrl;
        this.restaurant = restaurant;
        this.view = new ResMenu(this.mainFrame, this);
        this.commission = this.model.getCommission();
    }

    /**
     * Handles the action to go back to the previous controller's view.
     */
    public void handleBack() {
        this.prevctrl.showRestaurants();
    }

    /**
     * Retrieves the balance for the customer.
     *
     * @return the balance of the customer
     */
    public float getBalance() {
        return this.model.getBalance();
    }

    /**
     * Handles sending an order to the restaurant.
     *
     * @param order the map of items and their quantities to be ordered
     */
    public void handleSendOrder(final Map<Item, Integer> order) {
        if (this.model.sendOrder(order, this.restaurant.getRestaurantID())) {
            this.view.updateBalance(this.model.getBalance());
            return;
        }
        this.view.showOrderError();
    }

    /**
     * Retrieves the name of the restaurant.
     *
     * @return the name of the restaurant
     */
    public String getRestaurantName() {
        return this.restaurant.getName();
    }

    /**
     * Retrieves the opening and closing times of the restaurant.
     *
     * @return a pair containing the opening and closing times of the restaurant
     */
    public Pair<Date, Date> getRestaurantTime() {
        return this.restaurant.getOpeningHours();
    }

    /**
     * Retrieves the menu items for the restaurant as a map with initial quantities set to zero.
     *
     * @return a map of menu items and their initial quantities
     */
    public Map<Item, Integer> getItemMap() {
        final var menu = this.model.getMenuFor(this.restaurant.getRestaurantID());
        final Map<Item, Integer> itemQuantityMap = new LinkedHashMap<>();
        menu.forEach(e -> itemQuantityMap.put(e, 0));
        return itemQuantityMap;
    }

    /**
     * Handles the action to view reviews for the restaurant.
     */
    public void handleReviews() {
        new ReviewController(this, this.mainFrame, this.model, this.restaurant.getRestaurantID());
    }

    /**
     * Displays the restaurant menu view associated with this controller.
     */
    @Override
    public void show() {
        this.view = new ResMenu(this.mainFrame, this);
    }

    /**
     * Calculates the commission for a given total order amount.
     *
     * @param total the total order amount
     * @return the calculated commission
     */
    public float getCommission(final double total) {
        return this.commission * (float) total;
    }

}

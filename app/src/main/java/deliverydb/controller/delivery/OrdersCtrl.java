package deliverydb.controller.delivery;

import javax.swing.JFrame;

import deliverydb.controller.Controller;
import deliverydb.data.Order;
import deliverydb.data.Restaurant;
import deliverydb.model.Model;
import deliverydb.utilities.Pair;
import deliverydb.view.delivery.OrdersView;
import deliverydb.view.delivery.DeliveryPanel.Flag;

import java.util.List;

/**
 * The OrdersCtrl class handles the logic and interaction for managing delivery orders 
 * within the DeliveryDB application. It supports viewing available and accepted orders,
 * accepting orders, delivering orders, and retrieving restaurant details and compensation information.
 */
public class OrdersCtrl implements Controller {

    private final Model model;
    private final JFrame mainFrame;
    private final Controller prevCtrl;
    private Flag flag;

    /**
     * Constructs an OrdersCtrl object.
     *
     * @param flag the current flag indicating the type of orders to display (available or accepted)
     * @param prevCtrl the previous controller to return to when necessary
     * @param mainFrame the main application frame
     * @param model the data model for the application
     */
    public OrdersCtrl(final Flag flag, final Controller prevCtrl, final JFrame mainFrame, final Model model) {
        this.prevCtrl = prevCtrl;
        this.model = model;
        this.mainFrame = mainFrame;
        this.flag = flag;
        new OrdersView(flag, this.mainFrame, this);
    }

    /**
     * Displays the orders view associated with this controller.
     */
    @Override
    public void show() {
        new OrdersView(this.flag, this.mainFrame, this);
    }

    /**
     * Handles the action to go back to the previous controller's view.
     */
    public void handleBack() {
        this.prevCtrl.show();
    }

    /**
     * Retrieves the list of orders based on the specified flag.
     *
     * @param flag the flag indicating the type of orders to retrieve (available or accepted)
     * @return a list of orders based on the specified flag
     */
    public List<Order> getOrdersOnFlag(final Flag flag) {
        switch (flag) {
            case AVAILABLE:
                return this.model.getAvailableOrders();
            case ACCEPTED:
                return this.model.getAcceptedOrders();
            default:
                return null;
        }
    }

    /**
     * Retrieves the restaurant details and address for a given order.
     *
     * @param order the order for which to retrieve the restaurant details
     * @return a pair containing the restaurant and address associated with the order
     */
    public Pair<Restaurant, String> restaurantDetails(final Order order) {
        return new Pair<>(this.model.onRestaurantID(order.getRestaurantID()),
                this.model.getAddress(order.getUsername()));
    }

    /**
     * Accepts an order based on the specified order ID.
     *
     * @param orderID the ID of the order to accept
     * @return true if the order was successfully accepted, false otherwise
     */
    public boolean acceptOrder(final int orderID) {
        return this.model.acceptOrder(orderID);
    }

    /**
     * Marks an order as delivered.
     *
     * @param order the order to mark as delivered
     * @return true if the order was successfully marked as delivered, false otherwise
     */
    public boolean deliverOrder(final Order order) {
        return this.model.deliverOrder(order);
    }

    /**
     * Retrieves the compensation for a given order.
     *
     * @param order the order for which to retrieve the compensation
     * @return the compensation amount for the order
     */
    public float getCompensation(final Order order) {
        return this.model.getCompensation(order);
    }

}

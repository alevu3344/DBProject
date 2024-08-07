package deliverydb.controller.delivery;

import javax.swing.JFrame;

import deliverydb.controller.Controller;
import deliverydb.model.Model;
import deliverydb.view.delivery.DeliveryPanel;
import deliverydb.view.delivery.DeliveryPanel.Flag;

/**
 * The DeliveryCtrl class handles the delivery-related operations
 * within the DeliveryDB application. It manages the delivery panel view,
 * handles user logout, displays orders, and retrieves the delivery driver's balance.
 */
public class DeliveryCtrl implements Controller {

    private final Model model;
    private final JFrame mainFrame;
    private final Controller prevCtrl;

    /**
     * Constructs a DeliveryCtrl object.
     *
     * @param prevCtrl the previous controller to return to when necessary
     * @param mainFrame the main application frame
     * @param model the data model for the application
     */
    public DeliveryCtrl(Controller prevCtrl, JFrame mainFrame, Model model) {
        this.prevCtrl = prevCtrl;
        this.model = model;
        this.mainFrame = mainFrame;
        new DeliveryPanel(this.mainFrame, this);
    }

    /**
     * Displays the delivery panel view associated with this controller.
     */
    @Override
    public void show() {
        new DeliveryPanel(this.mainFrame, this);
    }

    /**
     * Handles the action to log out the current user and returns to the previous controller's view.
     */
    public void handleLogOut() {
        this.model.logout();
        this.prevCtrl.show();
    }

    /**
     * Shows the orders view for the specified flag (either available or accepted orders).
     *
     * @param flag the flag indicating the type of orders to display
     */
    public void showOrders(Flag flag) {
        new OrdersCtrl(flag, this, this.mainFrame, this.model);
    }

    /**
     * Retrieves the balance for the delivery driver.
     *
     * @return the balance of the delivery driver
     */
    public float getBalance() {
        return this.model.getBalance();
    }

}

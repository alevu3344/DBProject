package deliverydb.controller.admin;

import javax.swing.JFrame;

import deliverydb.controller.Controller;
import deliverydb.controller.ReviewController;
import deliverydb.controller.authentication.LoginController;
import deliverydb.model.Model;
import deliverydb.utilities.Pair;
import deliverydb.view.admin.AdminPanel;

import java.util.List;

/**
 * The AdminController class manages the administrative functions within the DeliveryDB application.
 * It provides access to various administrative features such as viewing restaurant details,
 * handling user reviews, and analyzing performance metrics.
 */
public class AdminController implements Controller {

    private final Model model;
    private final LoginController prevctrl;
    private final JFrame mainFrame;

    /**
     * Constructs an AdminController object.
     *
     * @param prevctrl the previous controller to return to when necessary
     * @param mainFrame the main application frame
     * @param model the data model for the application
     */
    public AdminController(final LoginController prevctrl, final JFrame mainFrame, final Model model) {
        this.mainFrame = mainFrame;
        this.model = model;
        this.prevctrl = prevctrl;
        new AdminPanel(mainFrame, this);
    }

    /**
     * Handles the action to go back to the previous controller's view and logs out the current user.
     */
    public void handleBack() {
        this.model.logout();
        this.prevctrl.show();
    }

    /**
     * Retrieves a list of restaurants along with some details.
     *
     * @return a list of pairs where each pair contains restaurant name and quantity
     */
    public List<Pair<String, Integer>> getRestaurants() {
        return this.model.getRestaurants().keySet().stream().toList();
    }

    /**
     * Displays the reviews for a specific restaurant.
     *
     * @param restaurantID the ID of the restaurant for which reviews are to be shown
     */
    public void showReviews(final int restaurantID) {
        new ReviewController(this, this.mainFrame, this.model, restaurantID);
    }

    /**
     * Displays the administrative panel view associated with this controller.
     */
    @Override
    public void show() {
        new AdminPanel(this.mainFrame, this);
    }

    /**
     * Retrieves the restaurant with the worst performance.
     *
     * @return a pair containing the restaurant's name and performance metric
     */
    public Pair<String, Integer> worstRestaurant() {
        return this.model.worstRestaurant();
    }

    /**
     * Retrieves the top dish based on performance metrics.
     *
     * @return a pair where the first element is another pair containing the dish's name and quantity,
     *         and the second element is the name of the restaurant serving the dish
     */
    public Pair<Pair<String, Integer>, String> topDish() {
        return this.model.topDish();
    }

    /**
     * Retrieves the most popular cuisine based on performance metrics.
     *
     * @return the name of the most popular cuisine
     */
    public String mostPopularCuisine() {
        return this.model.mostPopularCuisine();
    }

    /**
     * Retrieves the top 5 most chosen restaurants.
     *
     * @return a list of pairs where each pair contains the restaurant's name and quantity
     */
    public List<Pair<String, Integer>> mostChosen5Rest() {
        return this.model.mostChosen5Restaurants();
    }

    /**
     * Retrieves the top 5 deliverers based on performance metrics.
     *
     * @return a list of pairs where each pair contains the deliverer's name and performance metric
     */
    public List<Pair<String, Integer>> top5Deliverers() {
        return this.model.top5Deliverers();
    }
}

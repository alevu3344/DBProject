package deliveryDB.controller.admin;

import deliveryDB.controller.Controller;
import deliveryDB.controller.ReviewController;
import deliveryDB.controller.authentication.LoginController;
import deliveryDB.model.Model;

import javax.swing.JFrame;
import java.util.List;
import deliveryDB.utilities.Pair;
import deliveryDB.view.admin.AdminPanel;

public class AdminController implements Controller {

    private final Model model;
    private final LoginController prevctrl;
    private final JFrame mainFrame;

    public AdminController(LoginController prevctrl, JFrame mainFrame, Model model) {
        this.mainFrame = mainFrame;
        this.model = model;
        this.prevctrl = prevctrl;
        new AdminPanel(mainFrame, this);
    }

    public void handleBack() {
        this.model.logout();
        this.prevctrl.show();
    }

    public List<Pair<String, Integer>> getRestaurants() {
        return this.model.getRestaurants().keySet().stream().toList();
    }

    public void showReviews(int restaurantID) {
        new ReviewController(this, this.mainFrame, this.model, restaurantID);
    }

    @Override
    public void show() {
        new AdminPanel(this.mainFrame, this);
    }

    public Pair<String, Integer> worstRestaurant() {
        return this.model.worstRestaurant();
    }

    public Pair<String, Integer> topDish() {
        return this.model.topDish();
    }

    public String mostPopularCuisine() {
        return this.model.mostPopularCuisine();
    }

    public List<Pair<String, Integer>> mostChosen5Rest() {
        return this.model.mostChosen5Restaurants();
    }

    public List<Pair<String, Integer>> top5Deliverers() {
        return this.model.top5Deliverers();
    }
}

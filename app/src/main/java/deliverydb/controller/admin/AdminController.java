package deliverydb.controller.admin;

import javax.swing.JFrame;

import deliverydb.controller.Controller;
import deliverydb.controller.ReviewController;
import deliverydb.controller.authentication.LoginController;
import deliverydb.model.Model;
import deliverydb.utilities.Pair;
import deliverydb.view.admin.AdminPanel;

import java.util.List;

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
    //               Nome     Qtà      Ristorante
    public Pair<Pair<String, Integer>, String> topDish() {
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

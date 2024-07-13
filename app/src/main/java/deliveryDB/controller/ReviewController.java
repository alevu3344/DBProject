package deliveryDB.controller;

import deliveryDB.model.DelModel;
import javax.swing.JFrame;
import deliveryDB.data.Review;
import java.util.List;
import deliveryDB.view.ReviewPage;

public class ReviewController {

    private final DelModel model;
    private final JFrame mainFrame;
    private final ResMenuCtrl prevCtrl;
    private final int restaurantID;
    private ReviewPage view;
    
    public ReviewController(ResMenuCtrl resMenuCtrl, JFrame mainFrame, DelModel model, int restaurantID) {
        this.model = model;
        this.mainFrame = mainFrame;
        this.prevCtrl = resMenuCtrl;
        this.restaurantID = restaurantID;
        this.view = new ReviewPage(this.mainFrame, this);
    }

    public void handleBack(){
        this.prevCtrl.showMenu();
    }

    public List<Review> getReviews(){
        return this.model.getReviewsFor(this.restaurantID);
    }



    
}

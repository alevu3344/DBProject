package deliverydb.controller;

import javax.swing.JFrame;

import deliverydb.data.Review;
import deliverydb.data.User;
import deliverydb.model.Model;
import deliverydb.view.ReviewPage;

import java.util.List;

public class ReviewController {

    private final Model model;
    private final JFrame mainFrame;
    private final Controller prevCtrl;
    private final int restaurantID;
    
    public ReviewController(Controller resMenuCtrl, JFrame mainFrame, Model model, int restaurantID) {
        this.model = model;
        this.mainFrame = mainFrame;
        this.prevCtrl = resMenuCtrl;
        this.restaurantID = restaurantID;
        new ReviewPage(this.mainFrame, this);
    }

    public void handleBack(){
        this.prevCtrl.show();
    }

    public List<Review> getReviews(){
        return this.model.getReviewsFor(this.restaurantID);
    }

    public void addReview(int stars, String review){
        this.model.addReview(stars, review, this.restaurantID);   
    }

    public User.USER_TYPE getUserType(){
        return this.model.getUserType();
    }

    public void deleteReview(Review review){
        this.model.deleteReview(review);
    }
}

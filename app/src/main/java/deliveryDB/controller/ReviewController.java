package deliveryDB.controller;

import deliveryDB.model.DelModel;
import javax.swing.JFrame;
import deliveryDB.data.Review;
import deliveryDB.data.User;

import java.util.List;
import deliveryDB.view.ReviewPage;

public class ReviewController {

    private final DelModel model;
    private final JFrame mainFrame;
    private final Controller prevCtrl;
    private final int restaurantID;
    
    public ReviewController(Controller resMenuCtrl, JFrame mainFrame, DelModel model, int restaurantID) {
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
        System.out.println(review.getDate());
        this.model.deleteReview(review);
    }



    
}

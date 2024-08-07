package deliverydb.controller;

import javax.swing.JFrame;

import deliverydb.data.Review;
import deliverydb.data.User;
import deliverydb.model.Model;
import deliverydb.view.ReviewPage;

import java.util.List;

/**
 * The ReviewController class handles interactions related to reviews
 * within the DeliveryDB application. It manages the review page, 
 * facilitates the addition and deletion of reviews, and provides
 * access to review data.
 */
public class ReviewController {

    private final Model model;
    private final JFrame mainFrame;
    private final Controller prevCtrl;
    private final int restaurantID;

    /**
     * Constructs a ReviewController object.
     *
     * @param resMenuCtrl the previous controller, typically the restaurant menu controller
     * @param mainFrame the main application frame
     * @param model the data model for the application
     * @param restaurantID the ID of the restaurant for which reviews are managed
     */
    public ReviewController(final Controller resMenuCtrl, final JFrame mainFrame, final Model model, final int restaurantID) {
        this.model = model;
        this.mainFrame = mainFrame;
        this.prevCtrl = resMenuCtrl;
        this.restaurantID = restaurantID;
        new ReviewPage(this.mainFrame, this);
    }

    /**
     * Handles the action to go back to the previous controller's view.
     */
    public void handleBack() {
        this.prevCtrl.show();
    }

    /**
     * Retrieves the list of reviews for the specified restaurant.
     *
     * @return a list of reviews for the restaurant
     */
    public List<Review> getReviews() {
        return this.model.getReviewsFor(this.restaurantID);
    }

    /**
     * Adds a review for the specified restaurant.
     *
     * @param stars the rating given in the review
     * @param review the review text
     */
    public void addReview(final int stars, final String review) {
        this.model.addReview(stars, review, this.restaurantID);   
    }

    /**
     * Retrieves the type of the current user.
     *
     * @return the user type
     */
    public User.USER_TYPE getUserType() {
        return this.model.getUserType();
    }

    /**
     * Deletes a specified review.
     *
     * @param review the review to be deleted
     */
    public void deleteReview(final Review review) {
        this.model.deleteReview(review);
    }
}

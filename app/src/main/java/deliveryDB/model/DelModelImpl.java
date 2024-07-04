package deliveryDB.model;

import java.util.List;

import deliveryDB.data.Item;
import deliveryDB.data.Order;
import deliveryDB.data.Restaurant;
import deliveryDB.data.Review;

public class DelModelImpl implements DelModel{

    @Override
    public boolean sendOrder(Order order) {
        return false;
    }

    @Override
    public boolean acceptOrder(Order order) {
        return false;
    }

    @Override
    public List<Restaurant> getRestaurants() {
        return null;
    }

    @Override
    public List<Review> getReviewsFor(int RestaurantID) {
        return null;
    }

    @Override
    public boolean addReview(Review review) {
        return false;
    }

    @Override
    public boolean deliverOrder(Order order) {
        return false;   
    }

    @Override
    public boolean deleteReview(int reviewID) {
        return false;
    }

    @Override
    public List<Item> getMenuFor(int RestaurantID) {
        return null;
    }
    
}

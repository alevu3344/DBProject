package deliveryDB.model;
import java.sql.Connection;
import java.util.List;

import deliveryDB.data.Item;
import deliveryDB.data.Order;
import deliveryDB.data.Restaurant;
import deliveryDB.data.Review;

public interface DelModel {
    // Create a model that connects to a database using the given connection.
    //
    public static DelModel fromConnection(Connection connection) {
        return new DelModelImpl(connection);
    }

    public boolean sendOrder(Order order);

    public boolean acceptOrder(Order order);

    public List<Restaurant> getRestaurants();

    public List<Item> getMenuFor(int RestaurantID);

    public List<Review> getReviewsFor(int RestaurantID);

    public boolean addReview(Review review);

    public boolean deliverOrder(Order order);

    public boolean deleteReview(int reviewID);

    public boolean login(String username, String password);

    public boolean checkUsername(String username);
}
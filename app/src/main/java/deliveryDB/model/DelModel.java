package deliveryDB.model;
import java.sql.Connection;
import java.util.List;
import java.util.Map;

import deliveryDB.data.Item;
import deliveryDB.data.Order;
import deliveryDB.data.Restaurant;
import deliveryDB.data.Review;
import deliveryDB.data.User;
import deliveryDB.utilities.Pair;

public interface DelModel {
    // Create a model that connects to a database using the given connection.
    //
    public static DelModel fromConnection(Connection connection) {
        return new DelModelImpl(connection);
    }

    public boolean sendOrder(Map<Item, Integer> order, int restaurantID);

    public Map<Pair<String, Integer>, String> getRestaurants();

    public Restaurant onRestaurantID(int restaurantID);

    public List<Item> getMenuFor(int restaurantID);

    public List<Review> getReviewsFor(int restaurantID);

    public boolean addReview(int stars, String review, int restaurantID);

    public boolean deliverOrder(Order order);

    public boolean deleteReview(int reviewID);

    public boolean login(String username, String password);

    public float getBalance();

    public void logout();

    public boolean userRegister(User.USER_TYPE type, String username, String name, String surname,String password, String street, String number, String city);
}

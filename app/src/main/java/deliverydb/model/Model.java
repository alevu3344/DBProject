package deliverydb.model;

import java.sql.Connection;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import deliverydb.data.Item;
import deliverydb.data.Order;
import deliverydb.data.Restaurant;
import deliverydb.data.Review;
import deliverydb.data.User;
import deliverydb.utilities.Pair;

import java.util.LinkedHashMap;

public interface Model {
    // Create a model that connects to a database using the given connection.
    //
    public static Model fromConnection(Connection connection) {
        return new ModelImpl(connection);
    }

    public float getCommission();

    public float getCompensation(Order order);

    public Pair<String, Integer> worstRestaurant();

    public Pair<Pair<String, Integer>, String> topDish();

    public String mostPopularCuisine();

    public List<Pair<String, Integer>> mostChosen5Restaurants();

    public List<Pair<String, Integer>> top5Deliverers();

    public boolean sendOrder(Map<Item, Integer> order, int restaurantID);

    public LinkedHashMap<Pair<String, Integer>, String> getRestaurants();

    public Restaurant onRestaurantID(int restaurantID);

    public List<Item> getMenuFor(int restaurantID);

    public List<Review> getReviewsFor(int restaurantID);

    public boolean addReview(int stars, String review, int restaurantID);

    public boolean acceptOrder(int orderID);

    public boolean deliverOrder(Order order);

    public void deleteReview(Review review);

    public List<Order> getAvailableOrders();

    public List<Order> getAcceptedOrders();

    public Optional<User.USER_TYPE> login(String username, String password);

    public float getBalance();

    public void logout();

    public boolean userRegister(User.USER_TYPE type, String username, String name, String surname, String password,
            String street, String number, String city);

    public User.USER_TYPE getUserType();

    public String getAddress(String username);
}

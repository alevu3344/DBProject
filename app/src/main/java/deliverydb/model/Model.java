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

/**
 * Defines the operations available for interacting with the delivery system model.
 */
public interface Model {

    /**
     * Creates a model instance connected to the specified database connection.
     *
     * @param connection the {@link Connection} object used to connect to the database
     * @return a {@link Model} implementation instance
     */
    public static Model fromConnection(Connection connection) {
        return new ModelImpl(connection);
    }

    /**
     * Gets the commission rate.
     *
     * @return the commission rate as a float
     */
    public float getCommission();

    /**
     * Calculates the compensation for a given order.
     *
     * @param order the {@link Order} object for which the compensation is calculated
     * @return the compensation amount as a float
     */
    public float getCompensation(Order order);

    /**
     * Retrieves the worst restaurant based on certain criteria.
     *
     * @return a {@link Pair} where the first element is the restaurant name and the second is the total quantity sold
     */
    public Pair<String, Integer> worstRestaurant();

    /**
     * Retrieves the top dish based on total quantity sold and its associated restaurant.
     *
     * @return a {@link Pair} containing the top dish details and the restaurant name
     */
    public Pair<Pair<String, Integer>, String> topDish();

    /**
     * Determines the most popular cuisine based on the data available.
     *
     * @return the name of the most popular cuisine
     */
    public String mostPopularCuisine();

    /**
     * Retrieves the top 5 most chosen restaurants.
     *
     * @return a list of {@link Pair} objects where each pair contains a restaurant name and the number of times it was chosen
     */
    public List<Pair<String, Integer>> mostChosen5Restaurants();

    /**
     * Retrieves the top 5 deliverers based on the number of deliveries.
     *
     * @return a list of {@link Pair} objects where each pair contains a deliverer ID and the number of deliveries
     */
    public List<Pair<String, Integer>> top5Deliverers();

    /**
     * Sends an order to a specified restaurant.
     *
     * @param order a map of {@link Item} objects to their quantities
     * @param restaurantID the ID of the restaurant where the order is to be sent
     * @return {@code true} if the order was successfully sent, {@code false} otherwise
     */
    public boolean sendOrder(Map<Item, Integer> order, int restaurantID);

    /**
     * Retrieves a map of restaurants with their IDs and names.
     *
     * @return a {@link LinkedHashMap} where each entry contains a {@link Pair} of restaurant ID and name, and the value is the restaurant's address
     */
    public LinkedHashMap<Pair<String, Integer>, String> getRestaurants();

    /**
     * Retrieves a restaurant based on its ID.
     *
     * @param restaurantID the ID of the restaurant to retrieve
     * @return the {@link Restaurant} object associated with the given ID
     */
    public Restaurant onRestaurantID(int restaurantID);

    /**
     * Retrieves the menu items for a specified restaurant.
     *
     * @param restaurantID the ID of the restaurant for which the menu is retrieved
     * @return a list of {@link Item} objects available in the restaurant's menu
     */
    public List<Item> getMenuFor(int restaurantID);

    /**
     * Retrieves the reviews for a specified restaurant.
     *
     * @param restaurantID the ID of the restaurant for which the reviews are retrieved
     * @return a list of {@link Review} objects associated with the restaurant
     */
    public List<Review> getReviewsFor(int restaurantID);

    /**
     * Adds a review for a specified restaurant.
     *
     * @param stars the number of stars given in the review
     * @param review the text of the review
     * @param restaurantID the ID of the restaurant being reviewed
     * @return {@code true} if the review was successfully added, {@code false} otherwise
     */
    public boolean addReview(int stars, String review, int restaurantID);

    /**
     * Accepts an order based on its ID.
     *
     * @param orderID the ID of the order to be accepted
     * @return {@code true} if the order was successfully accepted, {@code false} otherwise
     */
    public boolean acceptOrder(int orderID);

    /**
     * Delivers a specified order.
     *
     * @param order the {@link Order} object to be delivered
     * @return {@code true} if the order was successfully delivered, {@code false} otherwise
     */
    public boolean deliverOrder(Order order);

    /**
     * Deletes a specified review.
     *
     * @param review the {@link Review} object to be deleted
     */
    public void deleteReview(Review review);

    /**
     * Retrieves the list of available orders.
     *
     * @return a list of {@link Order} objects that are currently available
     */
    public List<Order> getAvailableOrders();

    /**
     * Retrieves the list of accepted orders.
     *
     * @return a list of {@link Order} objects that have been accepted
     */
    public List<Order> getAcceptedOrders();

    /**
     * Logs in a user and returns their type if the credentials are correct.
     *
     * @param username the username of the user attempting to log in
     * @param password the password of the user attempting to log in
     * @return an {@link Optional} containing the {@link User.USER_TYPE} if login is successful, or {@link Optional#empty()} if not
     */
    public Optional<User.USER_TYPE> login(String username, String password);

    /**
     * Retrieves the balance for the currently logged-in user.
     *
     * @return the balance of the user as a float
     */
    public float getBalance();

    /**
     * Logs out the currently logged-in user.
     */
    public void logout();

    /**
     * Registers a new user in the system.
     *
     * @param type the type of the user
     * @param username the username for the new user
     * @param name the first name of the new user
     * @param surname the last name of the new user
     * @param password the password for the new user
     * @param street the street address of the new user
     * @param number the street number of the new user
     * @param city the city where the new user resides
     * @return {@code true} if the registration was successful, {@code false} otherwise
     */
    public boolean userRegister(User.USER_TYPE type, String username, String name, String surname, String password,
            String street, String number, String city);

    /**
     * Retrieves the user type of the currently logged-in user.
     *
     * @return the {@link User.USER_TYPE} of the currently logged-in user
     */
    public User.USER_TYPE getUserType();

    /**
     * Retrieves the address of a specified user.
     *
     * @param username the username of the user whose address is to be retrieved
     * @return the address of the user as a string
     */
    public String getAddress(String username);
}

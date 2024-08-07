package deliverydb.model;

import java.util.List;
import java.util.Map;
import java.util.LinkedHashMap;
import java.sql.Connection;
import java.util.Objects;
import java.util.Optional;

import deliverydb.data.Item;
import deliverydb.data.Order;
import deliverydb.data.Restaurant;
import deliverydb.data.Review;
import deliverydb.data.User;
import deliverydb.data.User.USER_TYPE;
import deliverydb.utilities.Pair;

/**
 * Implementation of the {@link Model} interface.
 */
public final class ModelImpl implements Model {

    private final Connection connection;
    private Optional<User> user = Optional.empty();
    private float commission = 0.2f;

    /**
     * Constructs a {@link ModelImpl} instance with the specified database connection.
     *
     * @param connection the {@link Connection} object used to connect to the database
     * @throws NullPointerException if {@code connection} is {@code null}
     */
    public ModelImpl(final Connection connection) {
        Objects.requireNonNull(connection, "Model created with null connection");
        this.connection = connection;
    }

    @Override
    public boolean sendOrder(final Map<Item, Integer> order, final int restaurantID) {
        return Order.DAO.sendOrder(order, this.user.get().getUsername(), restaurantID, connection, this.commission);
    }

    @Override
    public LinkedHashMap<Pair<String, Integer>, String> getRestaurants() {
        return Restaurant.DAO.listRestaurants(connection).get();
    }

    @Override
    public List<Review> getReviewsFor(final int restaurantID) {
        return Review.DAO.listReviews(connection, restaurantID).orElse(List.of());
    }

    @Override
    public boolean deliverOrder(final Order order) {
        return Order.DAO.deliverOrder(connection, order.getOrderID(), this.user.get().getUsername(),
                this.getCompensation(order));
    }

    @Override
    public void deleteReview(final Review review) {
        Review.DAO.deleteReview(connection, review);
    }

    @Override
    public List<Item> getMenuFor(final int restaurantID) {
        return Item.DAO.getMenuFor(connection, restaurantID).orElse(List.of());
    }

    @Override
    public Optional<User.USER_TYPE> login(final String username, final String password) {
        if (User.DAO.userLogin(connection, username, password)) {
            this.user = Optional.of(User.DAO.getUser(connection, username));
            return Optional.of(this.user.get().getType());
        }
        return Optional.empty();
    }

    @Override
    public boolean userRegister(final User.USER_TYPE type, final String username, final String name,
                                final String surname, final String password, final String street,
                                final String number, final String city) {
        return User.DAO.userRegister(connection, type, username, password, name, surname, street, number, city);
    }

    @Override
    public void logout() {
        this.user = Optional.empty();
    }

    @Override
    public Restaurant onRestaurantID(final int restaurantID) {
        return Restaurant.DAO.findRestaurant(connection, restaurantID);
    }

    @Override
    public float getBalance() {
        return User.DAO.getBalanceFor(connection, this.user.get().getUsername());
    }

    @Override
    public boolean addReview(final int stars, final String review, final int restaurantID) {
        return Restaurant.DAO.addReview(connection, restaurantID, stars, review, this.user.get().getUsername());
    }

    @Override
    public USER_TYPE getUserType() {
        return this.user.get().getType();
    }

    @Override
    public List<Order> getAvailableOrders() {
        return Order.DAO.getAvailableOrders(connection);
    }

    @Override
    public List<Order> getAcceptedOrders() {
        return Order.DAO.getAcceptedOrders(connection, this.user.get().getUsername());
    }

    @Override
    public boolean acceptOrder(final int orderID) {
        return Order.DAO.acceptOrder(connection, orderID, this.user.get().getUsername());
    }

    @Override
    public String getAddress(final String username) {
        return User.DAO.getAddress(connection, username);
    }

    @Override
    public Pair<String, Integer> worstRestaurant() {
        return Review.DAO.worstRestaurant(connection).get();
    }

    @Override
    public Pair<Pair<String, Integer>, String> topDish() {
        return Item.DAO.topDish(connection);
    }

    @Override
    public String mostPopularCuisine() {
        return Restaurant.DAO.mostPopularCuisine(connection);
    }

    @Override
    public List<Pair<String, Integer>> mostChosen5Restaurants() {
        return Restaurant.DAO.mostChosen5Restaurants(connection);
    }

    @Override
    public List<Pair<String, Integer>> top5Deliverers() {
        return User.DAO.top5Deliverers(connection);
    }

    @Override
    public float getCommission() {
        return this.commission;
    }

    @Override
    public float getCompensation(final Order order) {
        return Order.DAO.getCompensation(connection, order.getOrderID());
    }
}

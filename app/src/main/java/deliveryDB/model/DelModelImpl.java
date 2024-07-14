package deliveryDB.model;

import java.util.List;
import java.util.Map;
import java.util.LinkedHashMap;
import java.sql.Connection;
import java.util.Objects;
import java.util.Optional;


import deliveryDB.data.Item;
import deliveryDB.data.Order;
import deliveryDB.data.Restaurant;
import deliveryDB.data.Review;
import deliveryDB.data.User;
import deliveryDB.data.User.USER_TYPE;
import deliveryDB.utilities.Pair;


public class DelModelImpl implements DelModel {

    private final Connection connection;
    private Optional<User> user = Optional.empty();

    public DelModelImpl(Connection connection) {
        Objects.requireNonNull(connection, "Model created with null connection");
        this.connection = connection;
    }

    @Override
    public boolean sendOrder(Map <Item, Integer> order, int restaurantID) {
        return Order.DAO.sendOrder(order, this.user.get().getUsername(), restaurantID, connection);
    }


    @Override
    public LinkedHashMap<Pair<String, Integer>, String> getRestaurants() {
        return Restaurant.DAO.listRestaurants(connection).get();
    }

    @Override
    public List<Review> getReviewsFor(int restaurantID) {
        return Review.DAO.listReviews(connection, restaurantID).orElse(List.of());
    }

    

    @Override
    public boolean deliverOrder(int orderID) {
        return Order.DAO.deliverOrder(connection, orderID);
    }

    @Override
    public void deleteReview(Review review) {
        Review.DAO.deleteReview(connection, review);
        return;
    }

    @Override
    public List<Item> getMenuFor(int restaurantID) {
        return Item.DAO.getMenuFor(connection, restaurantID).orElse(List.of());
    }

    @Override
    public Optional<User.USER_TYPE> login(String username, String password) {
        if(User.DAO.userLogin(connection, username, password)){
            this.user = Optional.of(User.DAO.getUser(connection, username));
            return Optional.of(this.user.get().getType());
        }
        return Optional.empty();
    }

    @Override
    public boolean userRegister(User.USER_TYPE type, String username, String name, String surname, String password, String street,
            String number, String city) {
        return User.DAO.userRegister(connection,type, username, password, name, surname, street, number, city);
    }

    @Override
    public void logout() {
        this.user = Optional.empty();
    }

    @Override
    public Restaurant onRestaurantID(int restaurantID) {
        return Restaurant.DAO.findRestaurant(connection, restaurantID);
    }

    @Override
    public float getBalance() {
        return User.DAO.getBalanceFor(connection, this.user.get().getUsername());
    }

    @Override
    public boolean addReview(int stars, String review, int restaurantID) {
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
    public boolean acceptOrder(int orderID) {
        return Order.DAO.acceptOrder(connection, orderID, this.user.get().getUsername());
    }

    @Override
    public String getAddress(String username) {
        return User.DAO.getAddress(connection, username);
    }

}

package deliveryDB.model;

import java.util.List;
import java.util.Map;
import java.sql.Connection;
import java.util.Objects;
import java.util.Optional;

import deliveryDB.data.Item;
import deliveryDB.data.Order;
import deliveryDB.data.Restaurant;
import deliveryDB.data.Review;
import deliveryDB.data.User;
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
    public List<Pair<String, Integer>> getRestaurants() {
        return Restaurant.DAO.listRestaurants(connection).orElse(List.of());
    }

    @Override
    public List<Review> getReviewsFor(int restaurantID) {
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
    public List<Item> getMenuFor(int restaurantID) {
        return Item.DAO.getMenuFor(connection, restaurantID).orElse(List.of());
    }

    @Override
    public boolean login(String username, String password) {
        if(User.DAO.userLogin(connection, username, password)){
            this.user = Optional.of(User.DAO.getUser(connection, username));
            return true;
        }
        return false;
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

}

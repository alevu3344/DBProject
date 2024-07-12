package deliveryDB.model;

import java.util.List;
import java.sql.Connection;
import java.util.List;
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
    public boolean sendOrder(Order order) {
        return false;
    }

    @Override
    public boolean acceptOrder(Order order) {
        return false;
    }

    @Override
    public List<Pair<String, Integer>> getRestaurants() {
        return Restaurant.DAO.listRestaurants(connection).orElse(List.of());
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
        return Item.DAO.getMenuFor(connection, RestaurantID).orElse(List.of());
    }

    @Override
    public boolean login(String username, String password) {
        var tempUserName = username;
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
    public Restaurant onRestaurantID(int RestaurantID) {
        return Restaurant.DAO.findRestaurant(connection, RestaurantID);
    }

}

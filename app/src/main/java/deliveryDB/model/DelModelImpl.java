package deliveryDB.model;

import java.util.List;
import java.sql.Connection;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import deliveryDB.data.Item;
import deliveryDB.data.Order;
import deliveryDB.data.ProductPreview;
import deliveryDB.data.Restaurant;
import deliveryDB.data.Review;
import deliveryDB.data.User;

public class DelModelImpl implements DelModel{


    private final Connection connection;


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

    @Override
    public boolean login(String username, String password) {
        return User.DAO.userLogin(connection, username, password);
    }
    
}

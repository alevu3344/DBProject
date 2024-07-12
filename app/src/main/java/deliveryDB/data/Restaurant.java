package deliveryDB.data;

import java.util.Date;

import java.util.Optional;
import java.sql.Connection;
import java.util.List;
import java.util.LinkedList;

import deliveryDB.utilities.Pair;

public class Restaurant {
    private final int restaurantID;
    private final String name;
    private final String address;
    private final Pair<Date, Date> openingHours;
    private final String cuisineType;

    public Restaurant(int restaurantID, String name, String address, Pair<Date, Date> openingHours,
            String cuisineType) {
        this.restaurantID = restaurantID;
        this.name = name;
        this.address = address;
        this.openingHours = openingHours;
        this.cuisineType = cuisineType;
    }

    public final class DAO {

        public static Optional<Restaurant> find(Connection connection, int restaurantID) {
            return Optional.empty();
        }

        // add a review to the restaurant
        public static boolean addReview(Connection connection, int restaurantID, int stars, String review) {
            try {
                var statement = DAOUtils.prepare(connection, Queries.ADD_REVIEW, restaurantID, stars, review);
                statement.executeUpdate();
                return true;
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }

        // List all the reviews of a restaurant
        public static Optional<String> listReviews(Connection connection, int restaurantID) {
            return Optional.empty();
        }

        public static Restaurant findRestaurant(Connection connection, int restaurantID) {
            try {
                var statement = DAOUtils.prepare(connection, Queries.RESTAURANT_DETAILS, restaurantID);
                var result = statement.executeQuery();
                if (result.next()) {
                    return new Restaurant(result.getInt("RistoranteID"),
                            result.getString("Nome"),
                            result.getString("IndirizzoCittà") + " " + result.getString("IndirizzoVia") + " "
                                    + result.getString("IndirizzoCivico"),
                            new Pair<>(result.getTime("OraApertura"), result.getTime("OraChiusura")),
                            result.getString("TipologiaCucina"));
                }
                return null;
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        public static Optional<List<Pair<String, Integer>>> listRestaurants(Connection connection) {
            var restaurants = new LinkedList<Pair<String, Integer>>();
            try {
                var statement = DAOUtils.prepare(connection, Queries.RESTAURANT_LIST);
                var result = statement.executeQuery();
                while (result.next()) {
                    restaurants.add(new Pair<>(result.getString("Nome"), result.getInt("RistoranteID")));
                }
                return Optional.of(restaurants);
            } catch (Exception e) {
                e.printStackTrace();
                return Optional.empty();
            }

        }
    }

}

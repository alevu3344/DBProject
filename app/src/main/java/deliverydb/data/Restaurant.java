package deliverydb.data;

import java.sql.Connection;
import java.util.Date;
import java.util.Optional;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.LinkedList;

import deliverydb.utilities.Pair;

/**
 * Represents a restaurant with its details and provides methods to interact with restaurant data.
 */
public class Restaurant {

    private final int restaurantID;
    private final String name;
    private final String address;
    private final Pair<Date, Date> openingHours;
    private final String cuisineType;

    /**
     * Constructs a new Restaurant object with the specified details.
     *
     * @param restaurantID  the unique identifier for the restaurant
     * @param name          the name of the restaurant
     * @param address       the address of the restaurant
     * @param openingHours  the opening and closing hours of the restaurant
     * @param cuisineType   the type of cuisine offered by the restaurant
     */
    public Restaurant(int restaurantID, String name, String address, Pair<Date, Date> openingHours,
                      String cuisineType) {
        this.restaurantID = restaurantID;
        this.name = name;
        this.address = address;
        this.openingHours = openingHours;
        this.cuisineType = cuisineType;
    }

    /**
     * Gets the unique identifier for the restaurant.
     *
     * @return the restaurant ID
     */
    public int getRestaurantID() {
        return restaurantID;
    }

    /**
     * Gets the name of the restaurant.
     *
     * @return the restaurant name
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the address of the restaurant.
     *
     * @return the restaurant address
     */
    public String getAddress() {
        return address;
    }

    /**
     * Gets the opening and closing hours of the restaurant.
     *
     * @return a Pair containing the opening and closing times
     */
    public Pair<Date, Date> getOpeningHours() {
        return openingHours;
    }

    /**
     * Gets the type of cuisine offered by the restaurant.
     *
     * @return the cuisine type
     */
    public String getCuisineType() {
        return cuisineType;
    }

    /**
     * Data access object (DAO) for interacting with the restaurant-related database operations.
     */
    public final class DAO {

        /**
         * Retrieves the most popular cuisine type from the database.
         *
         * @param connection the database connection
         * @return the most popular cuisine type, or null if not found
         */
        public static String mostPopularCuisine(Connection connection) {
            try {
                var statement = DAOUtils.prepare(connection, Queries.BEST_CUISINE);
                var result = statement.executeQuery();
                if (result.next()) {
                    return result.getString("TipologiaCucina");
                }
                return null;
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        /**
         * Retrieves the top 5 most chosen restaurants from the database.
         *
         * @param connection the database connection
         * @return a list of pairs containing restaurant names and their order counts, or null if an error occurs
         */
        public static List<Pair<String, Integer>> mostChosen5Restaurants(Connection connection) {
            var restaurants = new LinkedList<Pair<String, Integer>>();
            try {
                var statement = DAOUtils.prepare(connection, Queries.TOP5_RESTAURANT);
                var result = statement.executeQuery();
                while (result.next()) {
                    restaurants.add(new Pair<>(result.getString("Nome"), result.getInt("NumeroOrdini")));
                }
                return restaurants;
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        /**
         * Finds a restaurant by its ID. Currently not implemented.
         *
         * @param connection    the database connection
         * @param restaurantID the ID of the restaurant to find
         * @return an Optional containing the Restaurant if found, or an empty Optional if not
         */
        public static Optional<Restaurant> find(Connection connection, int restaurantID) {
            return Optional.empty();
        }

        /**
         * Adds a review for a restaurant.
         *
         * @param connection    the database connection
         * @param restaurantID the ID of the restaurant to review
         * @param stars         the number of stars for the review
         * @param review        the text of the review
         * @param username      the username of the reviewer
         * @return true if the review was added successfully, false otherwise
         */
        public static boolean addReview(Connection connection, int restaurantID, int stars, String review,
                                         String username) {
            try {
                var statement = DAOUtils.prepare(connection, Queries.ADD_REVIEW, restaurantID, stars, review, username);
                statement.executeUpdate();
                return true;
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }

        /**
         * Lists all reviews for a restaurant. Currently not implemented.
         *
         * @param connection    the database connection
         * @param restaurantID the ID of the restaurant for which to list reviews
         * @return an Optional containing the reviews if found, or an empty Optional if not
         */
        public static Optional<String> listReviews(Connection connection, int restaurantID) {
            return Optional.empty();
        }

        /**
         * Finds and returns a Restaurant object by its ID.
         *
         * @param connection    the database connection
         * @param restaurantID the ID of the restaurant to find
         * @return the Restaurant object if found, or null if not
         */
        public static Restaurant findRestaurant(Connection connection, int restaurantID) {
            try {
                var statement = DAOUtils.prepare(connection, Queries.RESTAURANT_DETAILS, restaurantID);
                var result = statement.executeQuery();
                if (result.next()) {
                    return new Restaurant(restaurantID,
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

        /**
         * Lists all restaurants with their IDs and cuisine types.
         *
         * @param connection the database connection
         * @return an Optional containing a map of restaurant names and IDs with their cuisine types, or an empty Optional if an error occurs
         */
        public static Optional<LinkedHashMap<Pair<String, Integer>, String>> listRestaurants(Connection connection) {
            var restaurants = new LinkedHashMap<Pair<String, Integer>, String>();
            try {
                var statement = DAOUtils.prepare(connection, Queries.RESTAURANT_LIST);
                var result = statement.executeQuery();
                while (result.next()) {
                    restaurants.put(new Pair<>(result.getString("Nome"), result.getInt("RistoranteID")),
                            result.getString("TipologiaCucina"));
                }
                return Optional.of(restaurants);
            } catch (Exception e) {
                e.printStackTrace();
                return Optional.empty();
            }
        }
    }
}

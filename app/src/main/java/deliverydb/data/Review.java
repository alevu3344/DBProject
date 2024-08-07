package deliverydb.data;

import java.sql.Timestamp;
import java.util.Optional;
import java.util.List;
import java.util.LinkedList;
import java.sql.Connection;

import deliverydb.utilities.Pair;

/**
 * Represents a review for a restaurant.
 */
public class Review {

    private final int restaurantID;
    private final int stars;
    private final String review;
    private final Timestamp date;
    private final String author;

    /**
     * Constructs a new Review object with the specified details.
     *
     * @param restaurantID the ID of the restaurant being reviewed
     * @param stars        the number of stars given in the review
     * @param review       the text of the review
     * @param author       the username of the person who wrote the review
     * @param date         the timestamp when the review was created
     */
    public Review(int restaurantID, int stars, String review, String author, Timestamp date) {
        this.restaurantID = restaurantID;
        this.stars = stars;
        this.review = review;
        this.date = date;
        this.author = author;
    }

    /**
     * Gets the ID of the restaurant being reviewed.
     *
     * @return the restaurant ID
     */
    public int getRestaurantID() {
        return restaurantID;
    }

    /**
     * Gets the number of stars given in the review.
     *
     * @return the number of stars
     */
    public int getStars() {
        return stars;
    }

    /**
     * Gets the text of the review.
     *
     * @return the review text
     */
    public String getReview() {
        return review;
    }

    /**
     * Gets the timestamp when the review was created.
     *
     * @return the timestamp of the review
     */
    public Timestamp getDate() {
        return date;
    }

    /**
     * Gets the username of the person who wrote the review.
     *
     * @return the author's username
     */
    public String getAuthor() {
        return author;
    }

    /**
     * Data access object (DAO) for interacting with the review-related database operations.
     */
    public final class DAO {

        /**
         * Retrieves the restaurant with the worst rating from the database.
         *
         * @param connection the database connection
         * @return a Pair containing the restaurant name and its adjusted average rating, or null if not found
         */
        public static Pair<String, Integer> worstRestaurant(Connection connection) {
            try {
                var statement = DAOUtils.prepare(connection, Queries.WORST_RATING);
                var result = statement.executeQuery();
                if (result.next()) {
                    return new Pair<>(result.getString("Nome"), result.getInt("adjusted_average"));
                }
                return null;
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        /**
         * Lists all reviews for a specific restaurant.
         *
         * @param connection    the database connection
         * @param restaurantID the ID of the restaurant for which to list reviews
         * @return an Optional containing a list of reviews if found, or an empty Optional if not
         */
        public static Optional<List<Review>> listReviews(Connection connection, int restaurantID) {
            try {
                var statement = DAOUtils.prepare(connection, Queries.RESTAURANT_REVIEWS, restaurantID);
                var result = statement.executeQuery();
                LinkedList<Review> reviews = new LinkedList<>();
                while (result.next()) {
                    reviews.add(new Review(result.getInt("RistoranteID"),
                            result.getInt("Voto"),
                            result.getString("Commento"),
                            result.getString("Username"),
                            result.getTimestamp("DataOra")));
                }
                return Optional.of(reviews);
            } catch (Exception e) {
                e.printStackTrace();
                return Optional.empty();
            }
        }

        /**
         * Deletes a review from the database.
         *
         * @param connection the database connection
         * @param review     the review to delete
         */
        public static void deleteReview(Connection connection, Review review) {
            try {
                var statement = DAOUtils.prepare(connection, Queries.DELETE_REVIEW, review.getRestaurantID(),
                        review.getAuthor(), review.getDate());
                statement.executeUpdate();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}

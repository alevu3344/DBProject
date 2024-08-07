package deliverydb.data;

import java.sql.Timestamp;
import java.util.Optional;
import java.util.List;
import java.util.LinkedList;
import java.sql.Connection;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.sql.SQLException;

import deliverydb.utilities.Pair;

/**
 * Represents a review for a restaurant.
 */
public class Review {

    private final int restaurantID;
    private final int stars;
    private final String reviewText;
    private final Timestamp date;
    private final String author;

    /**
     * Constructs a new Review object with the specified details.
     *
     * @param restaurantID the ID of the restaurant being reviewed
     * @param stars        the number of stars given in the review
     * @param reviewText   the text of the review
     * @param author       the username of the person who wrote the review
     * @param date         the timestamp when the review was created
     */
    public Review(final int restaurantID, final int stars, final String reviewText, 
                  final String author, final Timestamp date) {
        this.restaurantID = restaurantID;
        this.stars = stars;
        this.reviewText = reviewText;
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
    public String getReviewText() {
        return reviewText;
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
    public static final class DAO {

        private static final Logger LOGGER = Logger.getLogger(DAO.class.getName());

        // Private constructor to prevent instantiation
        private DAO() {
            throw new UnsupportedOperationException("Utility class");
        }

        /**
         * Retrieves the restaurant with the worst rating from the database.
         *
         * @param connection the database connection
         * @return an Optional containing a Pair with the restaurant name and its adjusted average rating, or an empty Optional if not found
         */
        public static Optional<Pair<String, Integer>> worstRestaurant(final Connection connection) {
            final String query = Queries.WORST_RATING;
            try (var statement = DAOUtils.prepare(connection, query);
                 var result = statement.executeQuery()) {
                if (result.next()) {
                    return Optional.of(new Pair<>(result.getString("Nome"), result.getInt("adjusted_average")));
                }
                return Optional.empty();
            } catch (SQLException e) {
                LOGGER.log(Level.SEVERE, "Error retrieving worst restaurant", e);
                return Optional.empty();
            }
        }

        /**
         * Lists all reviews for a specific restaurant.
         *
         * @param connection    the database connection
         * @param restaurantID the ID of the restaurant for which to list reviews
         * @return an Optional containing a list of reviews if found, or an empty Optional if not
         */
        public static Optional<List<Review>> listReviews(final Connection connection, final int restaurantID) {
            final String query = Queries.RESTAURANT_REVIEWS;
            try (var statement = DAOUtils.prepare(connection, query, restaurantID);
                 var result = statement.executeQuery()) {
                final List<Review> reviews = new LinkedList<>();
                while (result.next()) {
                    reviews.add(new Review(
                            result.getInt("RistoranteID"),
                            result.getInt("Voto"),
                            result.getString("Commento"),
                            result.getString("Username"),
                            result.getTimestamp("DataOra")));
                }
                return Optional.of(reviews);
            } catch (SQLException e) {
                LOGGER.log(Level.SEVERE, "Error listing reviews", e);
                return Optional.empty();
            }
        }

        /**
         * Deletes a review from the database.
         *
         * @param connection the database connection
         * @param review     the review to delete
         */
        public static void deleteReview(final Connection connection, final Review review) {
            final String query = Queries.DELETE_REVIEW;
            try (var statement = DAOUtils.prepare(connection, query, review.getRestaurantID(),
                    review.getAuthor(), review.getDate())) {
                statement.executeUpdate();
            } catch (SQLException e) {
                LOGGER.log(Level.SEVERE, "Error deleting review", e);
            }
        }
    }
}

package deliveryDB.data;

import java.util.Date;

import java.util.Optional;



import java.sql.Connection;
import java.util.List;
import java.util.LinkedList;

public class Review {
    private final int restaurantID;
    private final int stars;
    private final String review;
    private final Date date;
    private final String author;

    public Review(int restaurantID, int stars, String review, String author, Date date) {
        this.restaurantID = restaurantID;
        this.stars = stars;
        this.review = review;
        this.date = date;
        this.author = author;
    }

    private final class DAO {

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
                            result.getDate("Data")));
                }
                return Optional.of(reviews);
            } catch (Exception e) {
                e.printStackTrace();
                return Optional.empty();
            }
        }

        public static boolean deleteReview(Connection connection, int restaurantID, String author, Date date) {
            try {
                var statement = DAOUtils.prepare(connection, Queries.DELETE_REVIEW, restaurantID, author, date);
                statement.executeUpdate();
                return true;
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }
    }
}

package deliveryDB.data;

import java.sql.Timestamp;

import java.util.Optional;



import java.sql.Connection;
import java.util.List;
import java.util.LinkedList;

public class Review {
    private final int restaurantID;
    private final int stars;
    private final String review;
    private final Timestamp date;
    private final String author;

    public Review(int restaurantID, int stars, String review, String author, Timestamp date) {
        this.restaurantID = restaurantID;
        this.stars = stars;
        this.review = review;
        this.date = date;
        this.author = author;
    }


    public int getRestaurantID() {
        return restaurantID;
    }

    public int getStars() {
        return stars;
    }

    public String getReview() {
        return review;
    }

    public Timestamp getDate() {
        return date;
    }

    public String getAuthor() {
        return author;
    }


    public final class DAO {

        

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

        public static void deleteReview(Connection connection, Review review) {
            try {
                var statement = DAOUtils.prepare(connection, Queries.DELETE_REVIEW, review.getRestaurantID(), review.getAuthor(), review.getDate());
                statement.executeUpdate();
                return;
            } catch (Exception e) {
                e.printStackTrace();
                return;
            }
        }
    }
}

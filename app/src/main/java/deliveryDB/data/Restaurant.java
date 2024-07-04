package deliveryDB.data;
import java.util.Date;

import com.mysql.cj.x.protobuf.MysqlxDatatypes.Scalar.String;
import java.util.Optional;
import java.sql.Connection;

import deliveryDB.utilities.Pair;

public class Restaurant {
    private final int restaurantID;
    private final String name;
    private final String address;
    private final Pair<Date, Date> openingHours;
    private final String cuisineType;

    public Restaurant(int restaurantID, String name, String address, Pair<Date, Date> openingHours, String cuisineType) {
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
    }


}

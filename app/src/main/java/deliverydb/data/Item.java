package deliverydb.data;

import java.util.List;
import java.util.Optional;
import java.util.LinkedList;
import java.sql.Connection;
import java.sql.SQLException;

import java.util.logging.Level;
import java.util.logging.Logger;

import deliverydb.utilities.Pair;

/**
 * Represents an item on a restaurant menu.
 */
public class Item {

    private final int itemID;
    private final int resID;
    private final String name;
    private final float price;
    private final String type;

    /**
     * Constructs an Item object.
     *
     * @param itemID the unique identifier for the item
     * @param resID  the ID of the restaurant where the item is available
     * @param price  the price of the item
     * @param name   the name of the item
     * @param type   the type of the item (e.g., appetizer, main course)
     */
    public Item(final int itemID, final int resID, final float price, final String name, final String type) {
        this.itemID = itemID;
        this.resID = resID;
        this.price = price;
        this.name = name;
        this.type = type;
    }

    /**
     * Gets the unique identifier for the item.
     *
     * @return the item ID
     */
    public int getItemID() {
        return itemID;
    }

    /**
     * Gets the ID of the restaurant where the item is available.
     *
     * @return the restaurant ID
     */
    public int getResID() {
        return resID;
    }

    /**
     * Gets the price of the item.
     *
     * @return the price of the item
     */
    public float getPrice() {
        return price;
    }

    /**
     * Gets the name of the item.
     *
     * @return the name of the item
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the type of the item (e.g., appetizer, main course).
     *
     * @return the type of the item
     */
    public String getType() {
        return type;
    }

    /**
     * Provides data access methods for {@link Item}.
     */
    public final class DAO {

        private static final Logger LOGGER = Logger.getLogger(DAO.class.getName());

        private DAO() { }

        /**
         * Retrieves the top dish based on total quantity sold and its associated
         * restaurant.
         *
         * @param connection the {@link Connection} object used to execute the query
         * @return a {@link Pair} containing the top dish details and the restaurant
         *         name, or {@code null} if no results are found
         */
        public static Pair<Pair<String, Integer>, String> topDish(final Connection connection) {
            try {
                final var statement = DAOUtils.prepare(connection, Queries.TOP_DISH);
                final var result = statement.executeQuery();
                if (result.next()) {
                    return new Pair<>(new Pair<>(result.getString("Nome"), result.getInt("QuantitàTotale")),
                            result.getString("Ristorante"));
                }
                return null;
            } catch (SQLException e) {
                LOGGER.log(Level.SEVERE, "Error retrieving top dish", e);
                return new Pair<>(new Pair<>("None", 0),
                "None");
            }
        }

        /**
         * Retrieves the menu items for a specified restaurant.
         *
         * @param connection   the {@link Connection} object used to execute the query
         * @param restaurantID the ID of the restaurant for which the menu is retrieved
         * @return an {@link Optional} containing a list of {@link Item} objects, or
         *         {@link Optional#empty()} if no items are found
         */
        public static Optional<List<Item>> getMenuFor(final Connection connection, final int restaurantID) {
            final var items = new LinkedList<Item>();
            try {
                final var statement = DAOUtils.prepare(connection, Queries.RESTAURANT_MENU, restaurantID);
                final var result = statement.executeQuery();
                while (result.next()) {
                    items.addFirst(
                            new Item(result.getInt("ElementoMenuID"), restaurantID, result.getFloat("Prezzo"),
                                    result.getString("Nome"), result.getString("Tipo")));
                }
                return Optional.of(items);
            } catch (SQLException e) {
                LOGGER.log(Level.SEVERE, "Error retrieving menu for restaurant ID " + restaurantID, e);
                return Optional.empty();
            }
        }
    }
}

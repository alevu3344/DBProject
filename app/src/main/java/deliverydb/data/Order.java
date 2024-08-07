package deliverydb.data;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Map;
import java.util.Collections;
import java.util.List;
import java.util.LinkedList;
import java.util.HashMap;
import java.util.stream.Collectors;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Represents an order placed by a user.
 */
public class Order {

    private final int orderID;
    private final String username;
    private final int restaurantID;
    private final Map<Item, Integer> items;

    /**
     * Constructs an Order object.
     *
     * @param orderID      the unique identifier for the order
     * @param username     the username of the person who placed the order
     * @param restaurantID the ID of the restaurant where the order was placed
     * @param items        a map of {@link Item} objects and their quantities in the
     *                     order
     */
    public Order(final int orderID, final String username, final int restaurantID, final Map<Item, Integer> items) {
        this.orderID = orderID;
        this.username = username;
        this.restaurantID = restaurantID;
        this.items = items;
    }

    /**
     * Gets the unique identifier for the order.
     *
     * @return the order ID
     */
    public int getOrderID() {
        return orderID;
    }

    /**
     * Gets the username of the person who placed the order.
     *
     * @return the username
     */
    public String getUsername() {
        return username;
    }

    /**
     * Gets the ID of the restaurant where the order was placed.
     *
     * @return the restaurant ID
     */
    public int getRestaurantID() {
        return restaurantID;
    }

    /**
     * Gets the map of {@link Item} objects and their quantities in the order.
     *
     * @return a map of items and their quantities
     */
    public Map<Item, Integer> getItems() {
        return items;
    }

    /**
     * Provides data access methods for {@link Order}.
     */
    public static final class DAO {

        private static final Logger LOGGER = Logger.getLogger(DAO.class.getName());
        private static final String ORDINE_ID = "OrdineID";

        /**
         * Private constructor to prevent instantiation of this utility class.
         */
        private DAO() {
            throw new UnsupportedOperationException("Utility class");
        }

        /**
         * Retrieves the compensation amount for a given order ID.
         *
         * @param connection the database connection
         * @param orderID    the unique identifier for the order
         * @return the compensation amount, or 0.0 if an error occurs or no result is
         *         found
         */
        public static float getCompensation(final Connection connection, final int orderID) {
            try {
                final var statement = DAOUtils.prepare(connection, Queries.GET_COMPENSATION, orderID);
                final var result = statement.executeQuery();
                if (result.next()) {
                    return result.getFloat("CompensoOrdine");
                }
                return 0.0f;
            } catch (SQLException e) {
                LOGGER.log(Level.SEVERE, "Error retrieving compensation", e);
                return 0.0f;
            }
        }

        /**
         * Marks an order as delivered and updates the user's balance.
         *
         * @param connection   the database connection
         * @param orderID      the unique identifier for the order
         * @param username     the username of the person who placed the order
         * @param compensation the compensation amount to be added to the user's balance
         * @return true if the order is successfully delivered and the balance is
         *         updated, false otherwise
         */
        public static boolean deliverOrder(final Connection connection, final int orderID, final String username,
                final float compensation) {
            try {
                final var statement = DAOUtils.prepare(connection, Queries.DELIVER_ORDER, orderID);
                if (statement.executeUpdate() > 0) {
                    final var actualBalanceStatement = DAOUtils.prepare(connection, Queries.USER_BALANCE, username);
                    final var actualBalanceResult = actualBalanceStatement.executeQuery();
                    float balance;
                    if (actualBalanceResult.next()) {
                        balance = actualBalanceResult.getFloat("Balance");
                    } else {
                        return false;
                    }
                    final var updateBalanceStatement = DAOUtils.prepare(connection, Queries.UPDATE_USER_BALANCE,
                            Float.valueOf(String.format("%.2f", compensation + balance)), username);
                    return updateBalanceStatement.executeUpdate() > 0;
                }
                return false;
            } catch (SQLException e) {
                LOGGER.log(Level.SEVERE, "Error delivering order", e);
                return false;
            }
        }

        /**
         * Marks an order as accepted by updating the order status.
         *
         * @param connection the database connection
         * @param orderID    the unique identifier for the order
         * @param username   the username of the person accepting the order
         * @return true if the order is successfully accepted, false otherwise
         */
        public static boolean acceptOrder(final Connection connection, final int orderID, final String username) {
            try {
                final var statement = DAOUtils.prepare(connection, Queries.ACCEPT_ORDER, orderID, username);
                return statement.executeUpdate() > 0;
            } catch (SQLException e) {
                LOGGER.log(Level.SEVERE, "Error accepting order", e);
                return false;
            }
        }

        /**
         * Builds a map of {@link Item} objects and their quantities from the order
         * details.
         *
         * @param connection the database connection
         * @param orderID    the unique identifier for the order
         * @return a map of items and their quantities, or an empty map if an error
         *         occurs
         */
        private static Map<Item, Integer> buildMap(final Connection connection, final int orderID) {
            try {
                final var statement = DAOUtils.prepare(connection, Queries.ORDER_DETAILS, orderID);
                final var result = statement.executeQuery();
                final var map = new HashMap<Item, Integer>();
                while (result.next()) {
                    final var item = new Item(result.getInt("ElementoMenuID"), result.getInt("RistoranteID"),
                            result.getFloat("Prezzo"), result.getString("Nome"), result.getString("Tipo"));
                    map.put(item, result.getInt("Quantità"));
                }
                return map;
            } catch (SQLException e) {
                LOGGER.log(Level.SEVERE, "Error building order map", e);
                return Collections.emptyMap();
            }
        }

        /**
         * Retrieves a list of available orders.
         *
         * @param connection the database connection
         * @return a list of available orders, or an empty list if an error occurs
         */
        public static List<Order> getAvailableOrders(final Connection connection) {
            try {
                final var orders = new LinkedList<Order>();
                final var statement = DAOUtils.prepare(connection, Queries.AVAILABLE_ORDERS);
                final var result = statement.executeQuery();
                while (result.next()) {
                    final var order = new Order(result.getInt(ORDINE_ID), result.getString("Username"),
                            result.getInt("RistoranteID"), buildMap(connection, result.getInt(ORDINE_ID)));
                    orders.add(order);
                }
                return orders;
            } catch (SQLException e) {
                LOGGER.log(Level.SEVERE, "Error retrieving available orders", e);
                return Collections.emptyList();
            }
        }

        /**
         * Retrieves a list of orders accepted by a specific user.
         *
         * @param connection the database connection
         * @param username   the username of the person who accepted the orders
         * @return a list of accepted orders, or an empty list if an error occurs
         */
        public static List<Order> getAcceptedOrders(final Connection connection, final String username) {
            try {
                final var orders = new LinkedList<Order>();
                final var statement = DAOUtils.prepare(connection, Queries.ACCEPTED_ORDERS, username);
                final var result = statement.executeQuery();
                while (result.next()) {
                    final var order = new Order(result.getInt(ORDINE_ID), result.getString("Username"),
                            result.getInt("RistoranteID"), buildMap(connection, result.getInt(ORDINE_ID)));
                    orders.add(order);
                }
                return orders;
            } catch (SQLException e) {
                LOGGER.log(Level.SEVERE, "Error retrieving accepted orders", e);
                return Collections.emptyList();
            }
        }

        /**
         * Sends an order and updates the user's balance accordingly.
         *
         * @param order        the map of {@link Item} objects and their quantities to
         *                     be ordered
         * @param username     the username of the person placing the order
         * @param restaurantId the ID of the restaurant where the order is placed
         * @param connection   the database connection
         * @param commission   the commission rate applied to the total order amount
         * @return true if the order is successfully sent and the balance is updated,
         *         false otherwise
         */
        public static boolean sendOrder(final Map<Item, Integer> order, final String username, final int restaurantId,
                final Connection connection, final float commission) {
            try {
                final var balanceStatement = DAOUtils.prepare(connection, Queries.USER_BALANCE, username);
                final var balanceResult = balanceStatement.executeQuery();
                if (balanceResult.next()) {
                    final var balance = balanceResult.getInt("Balance");
                    final var filteredOrder = order.entrySet().stream()
                            .filter(e -> e.getValue() > 0)
                            .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
                    final var total = filteredOrder.entrySet().stream()
                            .mapToDouble(e -> e.getKey().getPrice() * e.getValue()).sum();
                    if (balance < total) {
                        return false; // Insufficient balance
                    }

                    connection.setAutoCommit(false);

                    final var orderStatement = connection.prepareStatement(Queries.SEND_ORDER,
                            Statement.RETURN_GENERATED_KEYS);
                    orderStatement.setString(1, username);
                    orderStatement.setInt(2, restaurantId);
                    orderStatement.setFloat(3, commission);
                    orderStatement.executeUpdate();

                    final var orderIDResult = orderStatement.getGeneratedKeys();
                    if (!orderIDResult.next()) {
                        connection.rollback(); // Rollback if no order ID generated
                        return false;
                    }
                    final int orderID = orderIDResult.getInt(1);

                    final var detailStatement = connection.prepareStatement(Queries.SEND_ORDER_DETAILS);
                    for (Map.Entry<Item, Integer> entry : filteredOrder.entrySet()) {
                        detailStatement.setInt(1, orderID);
                        detailStatement.setInt(2, entry.getKey().getItemID());
                        detailStatement.setInt(3, entry.getValue());
                        detailStatement.addBatch();
                    }
                    detailStatement.executeBatch();

                    // Update user balance
                    final var updateBalanceStatement = DAOUtils.prepare(connection, Queries.UPDATE_USER_BALANCE,
                            balance - (total + (total * commission)), username);
                    updateBalanceStatement.executeUpdate();

                    // Commit transaction
                    connection.commit();
                    connection.setAutoCommit(true);
                    return true;
                }
                return false;
            } catch (SQLException e) {
                try {
                    connection.rollback(); // Rollback on error
                } catch (SQLException rollbackEx) {
                    LOGGER.log(Level.SEVERE, "Error during rollback", rollbackEx);
                }
                LOGGER.log(Level.SEVERE, "Error sending order", e);
                return false;
            }
        }
    }
}

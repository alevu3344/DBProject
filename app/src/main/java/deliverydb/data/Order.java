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
     * @param orderID the unique identifier for the order
     * @param username the username of the person who placed the order
     * @param restaurantID the ID of the restaurant where the order was placed
     * @param items a map of {@link Item} objects and their quantities in the order
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

        // Private constructor to prevent instantiation
        private DAO() {
            throw new UnsupportedOperationException("Utility class");
        }

        /**
         * Retrieves the compensation for a given order.
         *
         * @param connection the {@link Connection} object used to execute the query
         * @param orderID the ID of the order for which to retrieve the compensation
         * @return the compensation amount for the order
         */
        public static float getCompensation(final Connection connection, final int orderID) {
            try {
                var statement = DAOUtils.prepare(connection, Queries.GET_COMPENSATION, orderID);
                var result = statement.executeQuery();
                if (result.next()) {
                    return result.getFloat("CompensoOrdine");
                }
                return 0.0f;
            } catch (SQLException e) {
                e.printStackTrace();
                return 0.0f;
            }
        }

        /**
         * Marks an order as delivered and updates the deliverer's balance.
         *
         * @param connection the {@link Connection} object used to execute the queries
         * @param orderID the ID of the order to deliver
         * @param username the username of the deliverer
         * @param compensation the compensation amount for delivering the order
         * @return {@code true} if the order was successfully delivered and the balance updated, {@code false} otherwise
         */
        public static boolean deliverOrder(final Connection connection, final int orderID, final String username, final float compensation) {
            try {
                var statement = DAOUtils.prepare(connection, Queries.DELIVER_ORDER, orderID);
                if (statement.executeUpdate() > 0) {
                    var actualBalanceStatement = DAOUtils.prepare(connection, Queries.USER_BALANCE, username);
                    var actualBalanceResult = actualBalanceStatement.executeQuery();
                    float balance;
                    if (actualBalanceResult.next()) {
                        balance = actualBalanceResult.getFloat("Balance");
                    } else {
                        return false;
                    }
                    var updateBalanceStatement = DAOUtils.prepare(connection, Queries.UPDATE_USER_BALANCE,
                            Float.valueOf(String.format("%.2f", compensation + balance)), username);
                    return updateBalanceStatement.executeUpdate() > 0;
                }
                return false;
            } catch (SQLException e) {
                e.printStackTrace();
                return false;
            }
        }

        /**
         * Marks an order as accepted by a delivery person.
         *
         * @param connection the {@link Connection} object used to execute the query
         * @param orderID the ID of the order to accept
         * @param username the username of the delivery person accepting the order
         * @return {@code true} if the order was successfully accepted, {@code false} otherwise
         */
        public static boolean acceptOrder(final Connection connection, final int orderID, final String username) {
            try {
                var statement = DAOUtils.prepare(connection, Queries.ACCEPT_ORDER, orderID, username);
                return statement.executeUpdate() > 0;
            } catch (SQLException e) {
                e.printStackTrace();
                return false;
            }
        }

        /**
         * Builds a map of items and their quantities for a given order.
         *
         * @param connection the {@link Connection} object used to execute the query
         * @param orderID the ID of the order for which to retrieve the details
         * @return a map of {@link Item} objects and their quantities, or an empty map if no items are found
         */
        private static Map<Item, Integer> buildMap(final Connection connection, final int orderID) {
            try {
                var statement = DAOUtils.prepare(connection, Queries.ORDER_DETAILS, orderID);
                var result = statement.executeQuery();
                var map = new HashMap<Item, Integer>();
                while (result.next()) {
                    var item = new Item(result.getInt("ElementoMenuID"), result.getInt("RistoranteID"),
                            result.getFloat("Prezzo"), result.getString("Nome"), result.getString("Tipo"));
                    map.put(item, result.getInt("Quantità"));
                }
                return map;
            } catch (SQLException e) {
                e.printStackTrace();
                return Collections.emptyMap();
            }
        }

        /**
         * Retrieves all available orders.
         *
         * @param connection the {@link Connection} object used to execute the query
         * @return a list of available {@link Order} objects, or an empty list if no orders are found
         */
        public static List<Order> getAvailableOrders(final Connection connection) {
            try {
                LinkedList<Order> orders = new LinkedList<>();
                var statement = DAOUtils.prepare(connection, Queries.AVAILABLE_ORDERS);
                var result = statement.executeQuery();
                while (result.next()) {
                    var order = new Order(result.getInt("OrdineID"), result.getString("Username"),
                            result.getInt("RistoranteID"), buildMap(connection, result.getInt("OrdineID")));
                    orders.add(order);
                }
                return orders;
            } catch (SQLException e) {
                e.printStackTrace();
                return Collections.emptyList();
            }
        }

        /**
         * Retrieves all accepted orders for a specific delivery person.
         *
         * @param connection the {@link Connection} object used to execute the query
         * @param username the username of the delivery person
         * @return a list of accepted {@link Order} objects, or an empty list if no orders are found
         */
        public static List<Order> getAcceptedOrders(final Connection connection, final String username) {
            try {
                LinkedList<Order> orders = new LinkedList<>();
                var statement = DAOUtils.prepare(connection, Queries.ACCEPTED_ORDERS, username);
                var result = statement.executeQuery();
                while (result.next()) {
                    var order = new Order(result.getInt("OrdineID"), result.getString("Username"),
                            result.getInt("RistoranteID"), buildMap(connection, result.getInt("OrdineID")));
                    orders.add(order);
                }
                return orders;
            } catch (SQLException e) {
                e.printStackTrace();
                return Collections.emptyList();
            }
        }

        /**
         * Sends a new order and updates the user's balance accordingly.
         *
         * @param order a map of {@link Item} objects and their quantities for the order
         * @param username the username of the person placing the order
         * @param restaurantId the ID of the restaurant where the order is placed
         * @param connection the {@link Connection} object used to execute the queries
         * @param commission the commission rate to apply
         * @return {@code true} if the order was successfully placed and the balance updated, {@code false} otherwise
         */
        public static boolean sendOrder(final Map<Item, Integer> order, final String username, final int restaurantId,
                final Connection connection, final float commission) {
            try {
                var balanceStatement = DAOUtils.prepare(connection, Queries.USER_BALANCE, username);
                var balanceResult = balanceStatement.executeQuery();
                if (balanceResult.next()) {
                    var balance = balanceResult.getInt("Balance");
                    var filteredOrder = order.entrySet().stream()
                            .filter(e -> e.getValue() > 0)
                            .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
                    var total = filteredOrder.entrySet().stream()
                            .mapToDouble(e -> e.getKey().getPrice() * e.getValue()).sum();
                    if (balance < total) {
                        return false; // Insufficient balance
                    }

                    connection.setAutoCommit(false);

                    var orderStatement = connection.prepareStatement(Queries.SEND_ORDER,
                            Statement.RETURN_GENERATED_KEYS);
                    orderStatement.setString(1, username);
                    orderStatement.setInt(2, restaurantId);
                    orderStatement.setFloat(3, commission);
                    orderStatement.executeUpdate();

                    var orderIDResult = orderStatement.getGeneratedKeys();
                    if (!orderIDResult.next()) {
                        connection.rollback(); // Rollback if no order ID generated
                        return false;
                    }
                    int orderID = orderIDResult.getInt(1);

                    var detailStatement = connection.prepareStatement(Queries.SEND_ORDER_DETAILS);
                    for (Map.Entry<Item, Integer> entry : filteredOrder.entrySet()) {
                        detailStatement.setInt(1, orderID);
                        detailStatement.setInt(2, entry.getKey().getItemID());
                        detailStatement.setInt(3, entry.getValue());
                        detailStatement.addBatch();
                    }
                    detailStatement.executeBatch();

                    // Update user balance
                    var updateBalanceStatement = DAOUtils.prepare(connection, Queries.UPDATE_USER_BALANCE,
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
                    rollbackEx.printStackTrace();
                }
                e.printStackTrace();
                return false;
            }
        }
    }
}

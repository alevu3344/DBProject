package deliverydb.data;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.Collections;
import java.util.List;
import java.util.LinkedList;
import java.util.HashMap;

public class Order {

    private int orderID;
    private String username;
    private int restaurantID;
    private Map<Item, Integer> items;

    public Order(int orderID, String username, int restaurantID, Map<Item, Integer> items) {
        this.orderID = orderID;
        this.username = username;
        this.restaurantID = restaurantID;
        this.items = items;
    }

    public int getOrderID() {
        return orderID;
    }

    public String getUsername() {
        return username;
    }

    public int getRestaurantID() {
        return restaurantID;
    }

    public Map<Item, Integer> getItems() {
        return items;
    }

    public final class DAO {

        public static float getCompensation(Connection connection, int orderID){
            try{
                var statement = DAOUtils.prepare(connection, Queries.GET_COMPENSATION, orderID);
                var result = statement.executeQuery();
                if(result.next()){
                    return result.getFloat("CompensoOrdine");
                }
                return 0.0f;

            }
            catch(SQLException e){
                e.printStackTrace();
                return 0.0f;
            }
        }

        public static boolean deliverOrder(Connection connection, int orderID, String username, float compensation) {
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

        public static boolean acceptOrder(Connection connection, int orderID, String username) {
            try {
                var statement = DAOUtils.prepare(connection, Queries.ACCEPT_ORDER, orderID, username);
                return statement.executeUpdate() > 0;
            } catch (SQLException e) {
                e.printStackTrace();
                return false;
            }
        }

        private static Map<Item, Integer> buildMap(Connection connection, int orderID) {
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

        public static List<Order> getAvailableOrders(Connection connection) {
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

        public static List<Order> getAcceptedOrders(Connection connection, String username) {
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

        public static boolean sendOrder(Map<Item, Integer> order, String username, int restaurant_id,
                Connection connection, float commission) {
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
                        return false; // Saldo insufficiente
                    }

                    connection.setAutoCommit(false);

                    var orderStatement = connection.prepareStatement(Queries.SEND_ORDER,
                            Statement.RETURN_GENERATED_KEYS);
                    orderStatement.setString(1, username);
                    orderStatement.setInt(2, restaurant_id);
                    orderStatement.setFloat(3, commission);
                    orderStatement.executeUpdate();

                    var orderIDResult = orderStatement.getGeneratedKeys();
                    if (!orderIDResult.next()) {
                        connection.rollback(); // Annulla tutte le operazioni
                        return false;
                    }
                    int ordineID = orderIDResult.getInt(1);

                    var detailStatement = connection.prepareStatement(Queries.SEND_ORDER_DETAILS);
                    for (Map.Entry<Item, Integer> entry : filteredOrder.entrySet()) {

                        detailStatement.setInt(1, ordineID);
                        detailStatement.setInt(2, entry.getKey().getItemID());
                        detailStatement.setInt(3, entry.getValue());
                        detailStatement.addBatch();
                    }
                    detailStatement.executeBatch();

                    // 5. Aggiorna il saldo dell'utente

                    var updateBalanceStatement = DAOUtils.prepare(connection, Queries.UPDATE_USER_BALANCE,
                            balance - (total + ((total) * commission)), username);
                    updateBalanceStatement.executeUpdate();

                    // Commit della transazione
                    connection.commit();
                    connection.setAutoCommit(true);
                    return true;
                }
                return false;
            } catch (SQLException e) {
                try {
                    connection.rollback(); // Annulla tutte le operazioni in caso di errore
                } catch (SQLException rollbackEx) {
                    rollbackEx.printStackTrace();
                }
                e.printStackTrace();
                return false;
            }
        }
    }
}

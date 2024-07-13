package deliveryDB.data;



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

        private static Map<Item, Integer> buildMap(Connection connection, int orderID) {
            try {
                var statement = DAOUtils.prepare(connection, Queries.ORDER_DETAILS, orderID);
                var result = statement.executeQuery();
                var map = new HashMap<Item, Integer>();
                while (result.next()) {
                    var item = new Item(result.getInt("ElementoMenuID"), result.getInt("RistoranteID"),
                            result.getFloat("Prezzo"), result.getString("Nome"), result.getString("Tipo"));
                    map.put(item, result.getInt("Quantity"));
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
                    var order = new Order(result.getInt("OrderID"), result.getString("Username"),
                            result.getInt("RestaurantID"), buildMap(connection, result.getInt("OrderID")));

                    orders.add(order);
                }
                return orders;
            } catch (SQLException e) {
                e.printStackTrace();
                return Collections.emptyList();
            }
        }

        public static boolean sendOrder(Map<Item, Integer> order, String username, int restaurant_id,
                Connection connection) {
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
                        detailStatement.setInt(3, restaurant_id);
                        detailStatement.setInt(4, entry.getValue());
                        detailStatement.addBatch();
                    }
                    detailStatement.executeBatch();

                    // 5. Aggiorna il saldo dell'utente
                    var updateBalanceStatement = DAOUtils.prepare(connection, Queries.UPDATE_USER_BALANCE,
                            balance - total, username);
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

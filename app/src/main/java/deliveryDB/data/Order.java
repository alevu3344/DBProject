package deliveryDB.data;



import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Map;
import java.util.stream.Collectors;

public class Order {

    public final class DAO {

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

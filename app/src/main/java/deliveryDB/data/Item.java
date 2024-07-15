package deliveryDB.data;

import java.util.List;
import java.util.Optional;

import deliveryDB.utilities.Pair;

import java.util.LinkedList;
import java.sql.Connection;

public class Item {

    public final int itemID;
    public final int resID;
    public final float price;
    public final String type;
    public final String name;

    public Item(int itemID, int resID, float price, String name, String type) {
        this.itemID = itemID;
        this.resID = resID;
        this.price = price;
        this.name = name;
        this.type = type;
    }

    public int getItemID() {
        return itemID;
    }

    public int getResID() {
        return resID;
    }

    public float getPrice() {
        return price;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public final class DAO {

        public static Pair<String, Integer> topDish(Connection connection) {
            try {
                var statement = DAOUtils.prepare(connection, Queries.TOP_DISH);
                var result = statement.executeQuery();
                if (result.next()) {
                    return new Pair<>(result.getString("Nome"), result.getInt("QuantitàTotale"));
                }
                return null;
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        public static Optional<List<Item>> getMenuFor(Connection connection, int restaurantID) {
            var items = new LinkedList<Item>();
            try {
                var statement = DAOUtils.prepare(connection, Queries.RESTAURANT_MENU, restaurantID);
                var result = statement.executeQuery();
                while (result.next()) {
                    items.addFirst(
                            new Item(result.getInt("ElementoMenuID"), restaurantID, result.getFloat("Prezzo"),
                                    result.getString("Nome"), result.getString("Tipo")));
                }
                //items.stream().forEach(i -> System.out.println(i.getName()+i.getType()));
                return Optional.of(items);
            } catch (Exception e) {
                e.printStackTrace();
                return Optional.empty();
                
            }
        }
    }
}

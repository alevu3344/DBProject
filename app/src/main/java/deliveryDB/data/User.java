package deliveryDB.data;

import java.sql.Connection;
import java.util.LinkedList;
import java.util.List;

import deliveryDB.utilities.Pair;

public class User {

    private final USER_TYPE type;
    private final String username;
    private final String name;
    private final String surname;
    private final String street;
    private final String number;
    private final String city;

    public enum USER_TYPE {
        CUSTOMER,
        DELIVERY_PERSON,
        ADMIN;

        public static USER_TYPE fromString(String type) {
            return switch (type) {
                case "Consumatore" -> CUSTOMER;
                case "Fattorino" -> DELIVERY_PERSON;
                case "Amministratore" -> ADMIN;
                default -> null;
            };
        }

        public String toStringIta() {
            return switch (this) {
                case CUSTOMER -> "Consumatore";
                case DELIVERY_PERSON -> "Fattorino";
                case ADMIN -> "Amministratore";
            };
        }
    }

    public User(USER_TYPE type, String username, String name, String surname, String street, String number,
            String city) {
        this.type = type;
        this.username = username;
        this.name = name;
        this.surname = surname;
        this.street = street;
        this.number = number;
        this.city = city;
    }

    // Custom toString that ovverids the default one
    @Override
    public String toString() {
        return "User{" +
                "type=" + type +
                ", username='" + username + '\'' +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", street='" + street + '\'' +
                ", number='" + number + '\'' +
                ", city='" + city + '\'' +
                '}';
    }

    public USER_TYPE getType() {
        return type;
    }

    public String getUsername() {
        return username;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getStreet() {
        return street + " " + number + "," + city;
    }

    public final class DAO {

        public static List<Pair<String, Integer>> top5Deliverers(Connection connection) {
            var deliverers = new LinkedList<Pair<String, Integer>>();
            try {
                var statement = DAOUtils.prepare(connection, Queries.TOP5_DELIVERER);
                var result = statement.executeQuery();
                while (result.next()) {
                    deliverers.add(new Pair<>(result.getString("FattorinoID"), result.getInt("NumeroConsegne")));
                }
                return deliverers;
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        public static String getAddress(Connection connection, String username) {
            try {
                var statement = DAOUtils.prepare(connection, Queries.USER_ADDRESS, username);
                var result = statement.executeQuery();
                if (result.next()) {
                    return result.getString("IndirizzoVia") + " " + result.getString("IndirizzoCivico") + ", "
                            + result.getString("IndirizzoCittà");
                }
                return null;
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        public static float getBalanceFor(Connection connection, String username) {
            try {
                var statement = DAOUtils.prepare(connection, Queries.USER_BALANCE, username);
                var result = statement.executeQuery();
                if (result.next()) {
                    return result.getFloat("Balance");
                }
                return -1;
            } catch (Exception e) {
                e.printStackTrace();
                return -1;
            }
        }

        public static boolean isUserNameAvailable(Connection connection, String username) {
            try {
                var statement = DAOUtils.prepare(connection, Queries.USERNAME_AVAILABILITY_CHECK, username);
                var result = statement.executeQuery();
                return !result.next();
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }

        public static boolean userLogin(Connection connection, String username, String password) {
            try {
                var statement = DAOUtils.prepare(connection, Queries.USER_LOGIN, username, password);
                var result = statement.executeQuery();
                return result.next();
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }

        public static boolean userRegister(Connection connection, User.USER_TYPE type, String username, String password,
                String name, String surname, String street, String number, String city) {
            try {
                if (!isUserNameAvailable(connection, username)) {
                    return false;
                }
                var statement = DAOUtils.prepare(connection, Queries.USER_REGISTER, username, password, name, surname,
                        street, number, city, type.toStringIta());
                statement.executeUpdate();
                return true;
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }

        public static User getUser(Connection connection, String username) {
            try {
                var statement = DAOUtils.prepare(connection, Queries.USER_DETAILS, username);
                var result = statement.executeQuery();
                if (result.next()) {
                    return new User(User.USER_TYPE.fromString(result.getString("Ruolo")), result.getString("Username"),
                            result.getString("Nome"), result.getString("Cognome"), result.getString("IndirizzoVia"),
                            result.getString("IndirizzoCivico"), result.getString("IndirizzoCittà"));
                }
                return null;
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }
    }
}

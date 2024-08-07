package deliverydb.data;

import java.sql.Connection;
import java.util.LinkedList;
import java.util.List;

import deliverydb.utilities.Pair;

/**
 * Represents a user of the system.
 */
public class User {

    private final USER_TYPE type;
    private final String username;
    private final String name;
    private final String surname;
    private final String street;
    private final String number;
    private final String city;

    /**
     * Enum representing the types of users in the system.
     */
    public enum USER_TYPE {
        CUSTOMER,
        DELIVERY_PERSON,
        ADMIN;

        /**
         * Converts a string to the corresponding {@link USER_TYPE}.
         *
         * @param type the string representing the user type
         * @return the corresponding {@link USER_TYPE}, or {@code null} if the string does not match any type
         */
        public static USER_TYPE fromString(final String type) {
            return switch (type) {
                case "Consumatore" -> CUSTOMER;
                case "Fattorino" -> DELIVERY_PERSON;
                case "Amministratore" -> ADMIN;
                default -> null;
            };
        }

        /**
         * Converts the {@link USER_TYPE} to its Italian representation.
         *
         * @return the Italian representation of the user type
         */
        public String toStringIta() {
            return switch (this) {
                case CUSTOMER -> "Consumatore";
                case DELIVERY_PERSON -> "Fattorino";
                case ADMIN -> "Amministratore";
            };
        }
    }

    /**
     * Constructs a User object.
     *
     * @param type the type of the user
     * @param username the username of the user
     * @param name the first name of the user
     * @param surname the last name of the user
     * @param street the street address of the user
     * @param number the street number of the user
     * @param city the city where the user resides
     */
    public User(final USER_TYPE type, final String username, final String name, final String surname,
                final String street, final String number, final String city) {
        this.type = type;
        this.username = username;
        this.name = name;
        this.surname = surname;
        this.street = street;
        this.number = number;
        this.city = city;
    }

    /**
     * Returns a string representation of the User object.
     *
     * @return a string containing the user's details
     */
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

    /**
     * Gets the type of the user.
     *
     * @return the type of the user
     */
    public USER_TYPE getType() {
        return type;
    }

    /**
     * Gets the username of the user.
     *
     * @return the username of the user
     */
    public String getUsername() {
        return username;
    }

    /**
     * Gets the first name of the user.
     *
     * @return the first name of the user
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the last name of the user.
     *
     * @return the last name of the user
     */
    public String getSurname() {
        return surname;
    }

    /**
     * Gets the full address of the user.
     *
     * @return the full address, combining street, number, and city
     */
    public String getAddress() {
        return street + " " + number + ", " + city;
    }

    /**
     * Provides data access methods for {@link User}.
     */
    public static final class DAO {

        // Private constructor to prevent instantiation
        private DAO() {
            throw new UnsupportedOperationException("Utility class");
        }

        /**
         * Retrieves the top 5 delivery persons based on the number of deliveries.
         *
         * @param connection the {@link Connection} object used to execute the query
         * @return a list of {@link Pair} objects, where each pair contains a delivery person's ID and the number of deliveries
         */
        public static List<Pair<String, Integer>> top5Deliverers(final Connection connection) {
            List<Pair<String, Integer>> deliverers = new LinkedList<>();
            try {
                var statement = DAOUtils.prepare(connection, Queries.TOP5_DELIVERER);
                var result = statement.executeQuery();
                while (result.next()) {
                    deliverers.add(new Pair<>(result.getString("FattorinoID"), result.getInt("NumeroConsegne")));
                }
                return deliverers;
            } catch (Exception e) {
                e.printStackTrace();
                return List.of(); // Returning an empty list instead of null
            }
        }

        /**
         * Retrieves the address of a user.
         *
         * @param connection the {@link Connection} object used to execute the query
         * @param username the username of the user whose address is to be retrieved
         * @return the full address of the user, or {@code null} if the user is not found
         */
        public static String getAddress(final Connection connection, final String username) {
            try {
                var statement = DAOUtils.prepare(connection, Queries.USER_ADDRESS, username);
                var result = statement.executeQuery();
                if (result.next()) {
                    return result.getString("IndirizzoVia") + " " +
                           result.getString("IndirizzoCivico") + ", " +
                           result.getString("IndirizzoCittà");
                }
                return null;
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        /**
         * Retrieves the balance for a user.
         *
         * @param connection the {@link Connection} object used to execute the query
         * @param username the username of the user whose balance is to be retrieved
         * @return the balance of the user, or {@code -1} if the user is not found or an error occurs
         */
        public static float getBalanceFor(final Connection connection, final String username) {
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

        /**
         * Checks if a username is available for registration.
         *
         * @param connection the {@link Connection} object used to execute the query
         * @param username the username to check for availability
         * @return {@code true} if the username is available, {@code false} otherwise
         */
        public static boolean isUserNameAvailable(final Connection connection, final String username) {
            try {
                var statement = DAOUtils.prepare(connection, Queries.USERNAME_AVAILABILITY_CHECK, username);
                var result = statement.executeQuery();
                return !result.next();
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }

        /**
         * Authenticates a user based on username and password.
         *
         * @param connection the {@link Connection} object used to execute the query
         * @param username the username of the user
         * @param password the password of the user
         * @return {@code true} if the login is successful, {@code false} otherwise
         */
        public static boolean userLogin(final Connection connection, final String username, final String password) {
            try {
                var statement = DAOUtils.prepare(connection, Queries.USER_LOGIN, username, password);
                var result = statement.executeQuery();
                return result.next();
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }

        /**
         * Registers a new user in the system.
         *
         * @param connection the {@link Connection} object used to execute the query
         * @param type the type of the user
         * @param username the username of the user
         * @param password the password of the user
         * @param name the first name of the user
         * @param surname the last name of the user
         * @param street the street address of the user
         * @param number the street number of the user
         * @param city the city where the user resides
         * @return {@code true} if the registration is successful, {@code false} otherwise
         */
        public static boolean userRegister(final Connection connection, final User.USER_TYPE type, final String username,
                                            final String password, final String name, final String surname,
                                            final String street, final String number, final String city) {
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

        /**
         * Retrieves a user by username.
         *
         * @param connection the {@link Connection} object used to execute the query
         * @param username the username of the user to retrieve
         * @return the {@link User} object, or {@code null} if the user is not found
         */
        public static User getUser(final Connection connection, final String username) {
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

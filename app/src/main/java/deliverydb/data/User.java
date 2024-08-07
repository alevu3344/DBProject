package deliverydb.data;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import deliverydb.utilities.Pair;

/**
 * Represents a user of the system.
 */
public class User {

    private final Usertype type;
    private final String username;
    private final String name;
    private final String surname;
    private final String street;
    private final String number;
    private final String city;

    /**
     * Enum representing the types of users in the system.
     */
    public enum Usertype {
        /**
         * Consumer.
         */
        CUSTOMER("Consumatore"),
        /**
         * Delivery person.
         */
        DELIVERY_PERSON("Fattorino"),
        /**
         * Administrator.
         */
        ADMIN("Amministratore");

        private final String italianRepresentation;

        Usertype(final String italianRepresentation) {
            this.italianRepresentation = italianRepresentation;
        }

        /**
         * Converts a string to the corresponding {@link Usertype}.
         *
         * @param type the string representing the user type
         * @return the corresponding {@link Usertype}, or {@code null} if the string
         *         does not match any type
         */
        public static Usertype fromString(final String type) {
            for (final Usertype userType : values()) {
                if (userType.italianRepresentation.equalsIgnoreCase(type)) {
                    return userType;
                }
            }
            return null;
        }

        /**
         * Converts the {@link Usertype} to its Italian representation.
         *
         * @return the Italian representation of the user type
         */
        public String toStringIta() {
            return italianRepresentation;
        }
    }

    /**
     * Constructs a User object.
     *
     * @param type     the type of the user
     * @param username the username of the user
     * @param name     the first name of the user
     * @param surname  the last name of the user
     * @param street   the street address of the user
     * @param number   the street number of the user
     * @param city     the city where the user resides
     */
    public User(final Usertype type, final String username, final String name, final String surname,
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
     * Gets the type of the user.
     *
     * @return the type of the user
     */
    public Usertype getType() {
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

        private static final Logger LOGGER = Logger.getLogger(DAO.class.getName());

        // Private constructor to prevent instantiation
        private DAO() {
            throw new UnsupportedOperationException("Utility class");
        }

        /**
         * Retrieves the top 5 delivery persons based on the number of deliveries.
         *
         * @param connection the {@link Connection} object used to execute the query
         * @return a list of {@link Pair} objects, where each pair contains a delivery
         *         person's ID and the number of deliveries
         */
        public static List<Pair<String, Integer>> top5Deliverers(final Connection connection) {
            final List<Pair<String, Integer>> deliverers = new LinkedList<>();
            try (var statement = DAOUtils.prepare(connection, Queries.TOP5_DELIVERER);
                    var result = statement.executeQuery()) {
                while (result.next()) {
                    deliverers.add(new Pair<>(result.getString("FattorinoID"), result.getInt("NumeroConsegne")));
                }
            } catch (SQLException e) {
                LOGGER.log(Level.SEVERE, "Error retrieving top 5 deliverers", e);
            }
            return deliverers;
        }

        /**
         * Retrieves the address of a user.
         *
         * @param connection the {@link Connection} object used to execute the query
         * @param username   the username of the user whose address is to be retrieved
         * @return the full address of the user, or {@code null} if the user is not
         *         found
         */
        public static String getAddress(final Connection connection, final String username) {
            try (var statement = DAOUtils.prepare(connection, Queries.USER_ADDRESS, username);
                    var result = statement.executeQuery()) {
                if (result.next()) {
                    return result.getString("IndirizzoVia") 
                            + 
                            " " 
                            +
                            result.getString("IndirizzoCivico") 
                            + 
                            ", " 
                            +
                            result.getString("IndirizzoCittà");
                }
            } catch (SQLException e) {
                LOGGER.log(Level.SEVERE, "Error retrieving address for user: " + username, e);
            }
            return null;
        }

        /**
         * Retrieves the balance for a user.
         *
         * @param connection the {@link Connection} object used to execute the query
         * @param username   the username of the user whose balance is to be retrieved
         * @return the balance of the user, or {@code -1} if the user is not found or an
         *         error occurs
         */
        public static float getBalanceFor(final Connection connection, final String username) {
            try (var statement = DAOUtils.prepare(connection, Queries.USER_BALANCE, username);
                    var result = statement.executeQuery()) {
                if (result.next()) {
                    return result.getFloat("Balance");
                }
            } catch (SQLException e) {
                LOGGER.log(Level.SEVERE, "Error retrieving balance for user: " + username, e);
            }
            return -1;
        }

        /**
         * Checks if a username is available for registration.
         *
         * @param connection the {@link Connection} object used to execute the query
         * @param username   the username to check for availability
         * @return {@code true} if the username is available, {@code false} otherwise
         */
        public static boolean isUserNameAvailable(final Connection connection, final String username) {
            try (var statement = DAOUtils.prepare(connection, Queries.USERNAME_AVAILABILITY_CHECK, username);
                    var result = statement.executeQuery()) {
                return !result.next();
            } catch (SQLException e) {
                LOGGER.log(Level.SEVERE, "Error checking availability of username: " + username, e);
                return false;
            }
        }

        /**
         * Authenticates a user based on username and password.
         *
         * @param connection the {@link Connection} object used to execute the query
         * @param username   the username of the user
         * @param password   the password of the user
         * @return {@code true} if the login is successful, {@code false} otherwise
         */
        public static boolean userLogin(final Connection connection, final String username, final String password) {
            try (var statement = DAOUtils.prepare(connection, Queries.USER_LOGIN, username, password);
                    var result = statement.executeQuery()) {
                return result.next();
            } catch (SQLException e) {
                LOGGER.log(Level.SEVERE, "Error during login for user: " + username, e);
                return false;
            }
        }

        /**
         * Registers a new user in the system.
         *
         * @param connection the {@link Connection} object used to execute the query
         * @param type       the type of the user
         * @param username   the username of the user
         * @param password   the password of the user
         * @param name       the first name of the user
         * @param surname    the last name of the user
         * @param street     the street address of the user
         * @param number     the street number of the user
         * @param city       the city where the user resides
         * @return {@code true} if the registration is successful, {@code false}
         *         otherwise
         */
        public static boolean userRegister(final Connection connection, final Usertype type, final String username,
                final String password, final String name, final String surname,
                final String street, final String number, final String city) {
            try {
                if (!isUserNameAvailable(connection, username)) {
                    return false;
                }
                try (var statement = DAOUtils.prepare(connection, Queries.USER_REGISTER, username, password, name,
                        surname,
                        street, number, city, type.toStringIta())) {
                    statement.executeUpdate();
                }
                return true;
            } catch (SQLException e) {
                LOGGER.log(Level.SEVERE, "Error registering user: " + username, e);
                return false;
            }
        }

        /**
         * Retrieves user details based on username.
         *
         * @param connection the {@link Connection} object used to execute the query
         * @param username   the username of the user whose details are to be retrieved
         * @return a {@link User} object representing the user, or {@code null} if the
         *         user is not found
         */
        public static User getUser(final Connection connection, final String username) {
            try (var statement = DAOUtils.prepare(connection, Queries.USER_DETAILS, username);
                    var result = statement.executeQuery()) {
                if (result.next()) {
                    return new User(User.Usertype.fromString(result.getString("Ruolo")), result.getString("Username"),
                            result.getString("Nome"), result.getString("Cognome"), result.getString("IndirizzoVia"),
                            result.getString("IndirizzoCivico"), result.getString("IndirizzoCittà"));
                }
            } catch (SQLException e) {
                LOGGER.log(Level.SEVERE, "Error retrieving user details for username: " + username, e);
            }
            return null;
        }
    }
}

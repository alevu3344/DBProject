package deliveryDB.data;

import java.sql.Connection;

public class User {

    public enum USER_TYPE {
        CUSTOMER,
        DELIVERY_PERSON,
        ADMIN;

        public USER_TYPE fromString(String type) {
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
    


    public final class DAO {

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

        public static boolean userLogin(Connection connection, String username,String password) {
            try {
                var statement = DAOUtils.prepare(connection, Queries.USER_LOGIN, username, password);
                var result = statement.executeQuery();
                return result.next();
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }
        public static boolean userRegister(Connection connection,User.USER_TYPE type, String username,String password, String name, String surname, String street, String number, String city) {
            try {
                if (!isUserNameAvailable(connection, username)) {
                    return false;
                }
                var statement = DAOUtils.prepare(connection, Queries.USER_REGISTER, username, password, name, surname, street, number, city, type.toStringIta());
                statement.executeUpdate();
                return true;
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }
    }
}

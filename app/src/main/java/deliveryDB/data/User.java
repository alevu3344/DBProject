package deliveryDB.data;

import java.sql.Connection;

public class User {

    public enum USER_TYPE {
        CUSTOMER,
        DELIVERY_PERSON
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
                if (!isUserNameAvailable(connection, username)) {
                    return false;
                }
                var statement = DAOUtils.prepare(connection, Queries.USER_LOGIN, username, password);
                var result = statement.executeQuery();
                return result.next();
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }
        public static boolean userRegister(Connection connection, String username,String password) {
            try {
                if (!isUserNameAvailable(connection, username)) {
                    return false;
                }
                var statement = DAOUtils.prepare(connection, Queries.USER_REGISTER, username, password);
                statement.executeUpdate();
                return true;
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }
    }
}

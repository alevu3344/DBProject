package deliveryDB.data;

import java.sql.Connection;

public class User {
    


    public final class DAO {

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
        public static boolean userRegister(Connection connection, String username,String password) {
            try {
                //if username already in use return false
                var check = DAOUtils.prepare(connection, Queries.USERNAME_AVAILABILITY_CHECK, username);
                if(check.executeQuery().next()){
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

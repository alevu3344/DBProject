package deliveryDB.data;

public final class Queries {

    public static final String TAGS_FOR_PRODUCT =
        """
        SELECT t.tag_name
        FROM TAGGED t
        WHERE t.product_code = ?
        

        """;

    public static final String LIST_PRODUCTS =
        """
        SELECT p.code, p.name
        FROM PRODUCT p
        """;

    public static final String PRODUCT_COMPOSITION =
        """
        SELECT m.code, m.description, pc.percent
        FROM MATERIAL m
        JOIN COMPOSITION pc ON m.code = pc.material_code
        WHERE pc.product_code = ?

        """;

    public static final String FIND_PRODUCT =
        """
        SELECT p.code, p.name, p.description
        FROM PRODUCT p
        WHERE p.code = ?
        """;

    public static final String RESTAURANT_REVIEWS =
        """
        SELECT r.review
        FROM REVIEW r
        WHERE r.restaurant_id = ?
        """;

    public static final String RESTAURANT_MENU =
        """
        SELECT p.code, p.name
        FROM PRODUCT p
        JOIN SERVES s ON p.code = s.product_code
        WHERE s.restaurant_id = ?
        """;

    public static final String USERNAME_AVAILABILITY_CHECK =
        """
        SELECT u.username
        FROM UTENTI u
        WHERE u.username = ?
        """;

    public static final String USER_LOGIN =
        """
        SELECT u.username
        FROM UTENTI u
        WHERE u.username = ? AND u.password = ?
        """;


    public static final String USER_REGISTER =
        """
        INSERT INTO UTENTI (Username, Password, Nome, Cognome, IndirizzoVia, IndirizzoCivico, IndirizzoCittà)
        VALUES (?, ?, ?, ?, ?, ?, ?)
        """;
        

}

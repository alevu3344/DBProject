package deliveryDB.data;

public final class Queries {

    public static final String TAGS_FOR_PRODUCT = """
            SELECT t.tag_name
            FROM TAGGED t
            WHERE t.product_code = ?


            """;

    public static final String LIST_PRODUCTS = """
            SELECT p.code, p.name
            FROM PRODUCT p
            """;

    public static final String PRODUCT_COMPOSITION = """
            SELECT m.code, m.description, pc.percent
            FROM MATERIAL m
            JOIN COMPOSITION pc ON m.code = pc.material_code
            WHERE pc.product_code = ?

            """;

    public static final String FIND_PRODUCT = """
            SELECT p.code, p.name, p.description
            FROM PRODUCT p
            WHERE p.code = ?
            """;

    public static final String RESTAURANT_REVIEWS = """
            SELECT r.review
            FROM REVIEW r
            WHERE r.restaurant_id = ?
            """;

    public static final String RESTAURANT_MENU = """
            SELECT p.code, p.name
            FROM PRODUCT p
            JOIN SERVES s ON p.code = s.product_code
            WHERE s.restaurant_id = ?
            """;

    public static final String USERNAME_AVAILABILITY_CHECK = """
            SELECT u.username
            FROM UTENTI u
            WHERE u.username = ?
            """;

    public static final String USER_LOGIN = """
            SELECT u.username
            FROM UTENTI u
            WHERE u.username = ? AND u.password = ?
            """;

    public static final String USER_REGISTER = """
            INSERT INTO UTENTI (Username, Password, Nome, Cognome, IndirizzoVia, IndirizzoCivico, IndirizzoCittà, Ruolo)
            VALUES (?, ?, ?, ?, ?, ?, ?, ?)
            """;
    public static final String USER_DETAILS = """
            SELECT u.username, u.nome, u.cognome, u.indirizzoVia, u.indirizzoCivico, u.indirizzoCittà, u.ruolo
            FROM UTENTI u
            WHERE u.username = ?
            """;

    public static final String ADD_REVIEW = """
            INSERT INTO RECENSIONI (RistoranteID, Voto, Commento)
            VALUES (?, ?, ?)
            """;
    public static final String DELETE_REVIEW = """
            DELETE FROM RECENSIONI
            WHERE RistoranteID = ? AND Username = ? AND Data = ?
            """;
    public static final String RESTAURANT_DETAILS = """
            SELECT r.Nome, r.IndirizzoVia, r.IndirizzoCittà, r.IndirizzoCivico, r.TipologiaCucina, OraApertura, OraChiusura
            FROM RISTORANTI r
            WHERE r.id = ?
            """;

    // Seleziona tutti i ristoranti e i relativi id
    public static final String RESTAURANT_LIST = """
            SELECT r.RistoranteID, r.Nome
            FROM RISTORANTI r
            """;

}

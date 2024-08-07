package deliverydb.data;

/**
 * Contains SQL queries used throughout the application.
 * These queries cover various operations related to restaurants, reviews, users, and orders.
 */
public final class Queries {

    /**
     * Query to retrieve all reviews for a specific restaurant.
     */
    public static final String RESTAURANT_REVIEWS = """
                    SELECT *
                    FROM RECENSIONI r
                    WHERE r.RistoranteID = ?
                    """;

    /**
     * Query to retrieve menu items for a specific restaurant, ordered by type.
     */
    public static final String RESTAURANT_MENU = """
                    SELECT m.ElementoMenuID, m.Prezzo, m.Nome, m.Tipo
                    FROM ELEMENTI m
                    WHERE m.RistoranteID = ?
                    ORDER BY m.Tipo
                    """;

    /**
     * Query to check if a username is available.
     */
    public static final String USERNAME_AVAILABILITY_CHECK = """
                    SELECT u.username
                    FROM UTENTI u
                    WHERE u.username = ?
                    """;

    /**
     * Query to validate user login credentials.
     */
    public static final String USER_LOGIN = """
                    SELECT u.username
                    FROM UTENTI u
                    WHERE u.username = ? AND u.password = ?
                    """;

    /**
     * Query to register a new user in the system.
     */
    public static final String USER_REGISTER = """
                    INSERT INTO UTENTI (Username, Password, Nome, Cognome, IndirizzoVia, IndirizzoCivico, IndirizzoCittà, Ruolo)
                    VALUES (?, ?, ?, ?, ?, ?, ?, ?)
                    """;

    /**
     * Query to retrieve details of a specific user.
     */
    public static final String USER_DETAILS = """
                    SELECT u.*
                    FROM UTENTI u
                    WHERE u.username = ?
                    """;

    /**
     * Query to add a new review to the database.
     */
    public static final String ADD_REVIEW = """
                    INSERT INTO RECENSIONI (RistoranteID, Voto, Commento, Username)
                    VALUES (?, ?, ?, ?)
                    """;

    /**
     * Query to delete a specific review from the database.
     */
    public static final String DELETE_REVIEW = """
                    DELETE FROM RECENSIONI
                    WHERE RistoranteID = ? AND Username = ? AND DataOra = ?
                    """;

    /**
     * Query to retrieve details of a specific restaurant.
     */
    public static final String RESTAURANT_DETAILS = """
                    SELECT r.*
                    FROM RISTORANTI r
                    WHERE r.RistoranteID = ?
                    """;

    /**
     * Query to retrieve a list of all restaurants, ordered by cuisine type.
     */
    public static final String RESTAURANT_LIST = """
                    SELECT r.RistoranteID, r.Nome, r.TipologiaCucina
                    FROM RISTORANTI r
                    ORDER BY r.TipologiaCucina
                    """;

    /**
     * Query to retrieve the balance of a specific user.
     */
    public static final String USER_BALANCE = """
                    SELECT u.Balance
                    FROM UTENTI u
                    WHERE u.Username = ?
                    """;

    /**
     * Query to create a new order in the database.
     */
    public static final String SEND_ORDER = """
                    INSERT INTO ORDINI (Username, RistoranteID, Commissione)
                    VALUES (?, ?, ?)
                    """;

    /**
     * Query to add details of a specific order.
     */
    public static final String SEND_ORDER_DETAILS = """
                    INSERT INTO DETTAGLI_ORDINI (OrdineID, ElementoMenuID, Quantità)
                    VALUES (?, ? , ?)
                    """;

    /**
     * Query to update the balance of a specific user.
     */
    public static final String UPDATE_USER_BALANCE = """
                    UPDATE UTENTI
                    SET Balance = ?
                    WHERE Username = ?
                    """;

    /**
     * Query to retrieve available orders within the last hour.
     */
    public static final String AVAILABLE_ORDERS = """
                    SELECT o.OrdineID, o.Username, o.RistoranteID
                    FROM ORDINI o
                    WHERE o.OrdineID NOT IN (SELECT ao.OrdineID FROM ASSEGNAZIONI_CONSEGNE ao)
                    AND o.DataOra BETWEEN DATE_SUB(NOW(), INTERVAL 1 HOUR) AND NOW()
                    ORDER BY o.DataOra DESC
                    """;

    /**
     * Query to retrieve orders assigned to a specific deliverer and not yet delivered.
     */
    public static final String ACCEPTED_ORDERS = """
                    SELECT o.OrdineID, o.Username, o.RistoranteID
                    FROM ORDINI o
                    WHERE o.OrdineID IN (
                        SELECT ao.OrdineID
                        FROM ASSEGNAZIONI_CONSEGNE ao
                        WHERE ao.FattorinoID = ?
                        AND ao.DataOraConsegna IS NULL
                        AND ao.DataOraAssegnazione BETWEEN DATE_SUB(NOW(), INTERVAL 1 HOUR) AND NOW()
                    )
                    ORDER BY o.DataOra DESC
                    """;

    /**
     * Query to retrieve details of a specific order.
     */
    public static final String ORDER_DETAILS = """
                    SELECT d.ElementoMenuID, d.Quantità, m.Nome, m.Prezzo, m.Tipo, o.RistoranteID
                    FROM DETTAGLI_ORDINI d
                    JOIN ORDINI o ON d.OrdineID = o.OrdineID
                    JOIN ELEMENTI m ON d.ElementoMenuID = m.ElementoMenuID AND o.RistoranteID = m.RistoranteID
                    WHERE d.OrdineID = ?
                    """;

    /**
     * Query to calculate the total price of a specific order.
     */
    public static final String ORDER_TOTAL_PRICE = """
                    SELECT SUM(m.Prezzo * d.Quantità) AS PrezzoTotale
                    FROM DETTAGLI_ORDINI d
                    JOIN ORDINI o ON d.OrdineID = o.OrdineID
                    JOIN ELEMENTI m ON d.ElementoMenuID = m.ElementoMenuID AND o.RistoranteID = m.RistoranteID
                    WHERE d.OrdineID = ?
                    """;

    /**
     * Query to assign a specific order to a deliverer.
     */
    public static final String ACCEPT_ORDER = """
                    INSERT INTO ASSEGNAZIONI_CONSEGNE (OrdineID, FattorinoID)
                    VALUES (?, ?)
                    """;

    /**
     * Query to update the delivery time of an order.
     */
    public static final String DELIVER_ORDER = """
                    UPDATE ASSEGNAZIONI_CONSEGNE
                    SET DataOraConsegna = CURRENT_TIMESTAMP
                    WHERE OrdineID = ?
                    """;

    /**
     * Query to retrieve the address of a specific user.
     */
    public static final String USER_ADDRESS = """
                    SELECT u.IndirizzoVia, u.IndirizzoCivico, u.IndirizzoCittà
                    FROM UTENTI u
                    WHERE u.Username = ?
                    """;

    /**
     * Query to retrieve the most popular dish based on total quantity ordered.
     */
    public static final String TOP_DISH = """
                    SELECT e.Nome AS Nome, r.Nome AS Ristorante, SUM(d.Quantità) AS QuantitàTotale
                    FROM DETTAGLI_ORDINI d
                    JOIN ORDINI o ON d.OrdineID = o.OrdineID
                    JOIN ELEMENTI e ON d.ElementoMenuID = e.ElementoMenuID AND o.RistoranteID = e.RistoranteID
                    JOIN RISTORANTI r ON o.RistoranteID = r.RistoranteID
                    WHERE e.Tipo = 'Cibo'
                    GROUP BY e.Nome, r.Nome
                    ORDER BY QuantitàTotale DESC
                    LIMIT 1
                    """;

    /**
     * Query to retrieve the top 5 restaurants based on the number of orders.
     */
    public static final String TOP5_RESTAURANT = """
                    SELECT r.RistoranteID, r.Nome, COUNT(r.RistoranteID) AS NumeroOrdini
                    FROM ORDINI o
                    JOIN RISTORANTI r ON o.RistoranteID = r.RistoranteID
                    GROUP BY r.RistoranteID
                    ORDER BY NumeroOrdini DESC
                    LIMIT 5
                    """;

    /**
     * Query to retrieve the top 5 deliverers based on the number of deliveries.
     */
    public static final String TOP5_DELIVERER = """
                    SELECT ac.FattorinoID, COUNT(ac.FattorinoID) AS NumeroConsegne
                    FROM ASSEGNAZIONI_CONSEGNE ac
                    GROUP BY ac.FattorinoID
                    ORDER BY NumeroConsegne DESC
                    LIMIT 5
                    """;

    /**
     * Query to retrieve restaurants with the worst average rating.
     */
    public static final String WORST_RATING = """
                    SELECT r.Nome, subquery.adjusted_average
                    FROM RISTORANTI r
                    JOIN
                    (
                    SELECT r.RistoranteID, (SUM(r.Voto) + 1) / (COUNT(*) + 1) AS adjusted_average
                    FROM RECENSIONI r
                    GROUP BY r.RistoranteID
                    ) subquery ON r.RistoranteID = subquery.RistoranteID
                     ORDER BY subquery.adjusted_average ASC;
                     """;

    /**
     * Query to retrieve the most popular cuisine based on the number of orders.
     */
    public static final String BEST_CUISINE = """
                    SELECT r.TipologiaCucina, COUNT(r.TipologiaCucina) AS NumeroOrdini
                    FROM ORDINI o
                    JOIN RISTORANTI r ON o.RistoranteID = r.RistoranteID
                    GROUP BY r.TipologiaCucina
                    ORDER BY NumeroOrdini DESC
                    LIMIT 1
                    """;

    /**
     * Query to calculate the compensation for a specific order based on its commission rate.
     */
    public static final String GET_COMPENSATION = """
                    SELECT SUM(m.Prezzo * d.Quantità) * o.Commissione AS CompensoOrdine
                    FROM DETTAGLI_ORDINI d
                    JOIN ORDINI o ON d.OrdineID = o.OrdineID
                    JOIN ELEMENTI m ON d.ElementoMenuID = m.ElementoMenuID AND o.RistoranteID = m.RistoranteID
                    WHERE d.OrdineID = ?
                    """;
    private Queries() { }
}

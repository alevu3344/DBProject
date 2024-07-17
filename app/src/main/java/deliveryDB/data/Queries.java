package deliveryDB.data;

public final class Queries {

        public static final String RESTAURANT_REVIEWS = """
                        SELECT *
                        FROM RECENSIONI r
                        WHERE r.RistoranteID = ?
                        """;

        public static final String RESTAURANT_MENU = """
                        SELECT m.ElementoMenuID, m.Prezzo, m.Nome, m.Tipo
                        FROM ELEMENTI m
                        WHERE m.RistoranteID = ?
                        ORDER BY m.Tipo
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
                        INSERT INTO RECENSIONI (RistoranteID, Voto, Commento, Username)
                        VALUES (?, ?, ?, ?)
                        """;
        public static final String DELETE_REVIEW = """
                        DELETE FROM RECENSIONI
                        WHERE RistoranteID = ? AND Username = ? AND DataOra = ?
                        """;
        public static final String RESTAURANT_DETAILS = """
                        SELECT r.Nome, r.IndirizzoVia, r.IndirizzoCittà, r.IndirizzoCivico, r.TipologiaCucina, r.OraApertura, r.OraChiusura
                        FROM RISTORANTI r
                        WHERE r.RistoranteID = ?
                        """;

        public static final String RESTAURANT_LIST = """
                        SELECT r.RistoranteID, r.Nome, r.TipologiaCucina
                        FROM RISTORANTI r
                        ORDER BY r.TipologiaCucina
                        """;

        public static final String USER_BALANCE = """
                        SELECT u.Balance
                        FROM UTENTI u
                        WHERE u.Username = ?
                        """;

        public static final String SEND_ORDER = """
                        INSERT INTO ORDINI (Username, RistoranteID, Commissione)
                        VALUES (?, ?, ?)
                        """;

        public static final String SEND_ORDER_DETAILS = """
                        INSERT INTO DETTAGLI_ORDINI (OrdineID, ElementoMenuID, RistoranteID , Quantità)
                        VALUES (?, ?, ? , ?)""";
        public static final String UPDATE_USER_BALANCE = """
                        UPDATE UTENTI
                        SET Balance = ?
                        WHERE Username = ?
                        """;

        public static final String AVAILABLE_ORDERS = """
                        SELECT o.OrdineID, o.Username, o.RistoranteID
                        FROM ORDINI o
                        WHERE o.OrdineID NOT IN (SELECT ao.OrdineID FROM ASSEGNAZIONI_CONSEGNE ao)
                        AND o.DataOra BETWEEN DATE_SUB(NOW(), INTERVAL 1 HOUR) AND NOW()
                        ORDER BY o.DataOra DESC
                        """;

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

        public static final String ORDER_DETAILS = """
                        SELECT d.ElementoMenuID, d.Quantità, m.Nome, m.Prezzo, m.Tipo, d.RistoranteID
                        FROM DETTAGLI_ORDINI d
                        JOIN ELEMENTI m ON d.ElementoMenuID = m.ElementoMenuID AND d.RistoranteID = m.RistoranteID
                        WHERE d.OrdineID = ?
                        """;

        public static final String ORDER_TOTAL_PRICE = """
                        SELECT SUM(m.Prezzo * d.Quantità) AS PrezzoTotale
                        FROM DETTAGLI_ORDINI d
                        JOIN ELEMENTI m ON d.ElementoMenuID = m.ElementoMenuID AND d.RistoranteID = m.RistoranteID
                        WHERE d.OrdineID = ?
                        """;

        public static final String ACCEPT_ORDER = """
                        INSERT INTO ASSEGNAZIONI_CONSEGNE (OrdineID, FattorinoID)
                        VALUES (?, ?)
                        """;

        public static final String DELIVER_ORDER = """
                        UPDATE ASSEGNAZIONI_CONSEGNE
                        SET DataOraConsegna = CURRENT_TIMESTAMP
                        WHERE OrdineID = ?
                        """;

        public static final String USER_ADDRESS = """
                        SELECT u.IndirizzoVia, u.IndirizzoCivico, u.IndirizzoCittà
                        FROM UTENTI u
                        WHERE u.Username = ?
                        """;
        public static final String TOP_DISH = """
                        SELECT e.Nome AS Nome, r.Nome AS Ristorante, SUM(d.Quantità) AS QuantitàTotale
                        FROM DETTAGLI_ORDINI d
                        JOIN ELEMENTI e ON d.ElementoMenuID = e.ElementoMenuID AND d.RistoranteID = e.RistoranteID
                        JOIN RISTORANTI r ON e.RistoranteID = r.RistoranteID
                        WHERE e.Tipo = 'Cibo'
                        GROUP BY e.Nome, r.Nome
                        ORDER BY QuantitàTotale DESC
                        LIMIT 1
                        """;

        public static final String TOP5_RESTAURANT = """
                        SELECT r.RistoranteID, r.Nome, COUNT(r.RistoranteID) AS NumeroOrdini
                        FROM ORDINI o
                        JOIN RISTORANTI r ON o.RistoranteID = r.RistoranteID
                        GROUP BY r.RistoranteID
                        ORDER BY NumeroOrdini DESC
                        LIMIT 5
                        """;
        public static final String TOP5_DELIVERER = """
                        SELECT ac.FattorinoID, COUNT(ac.FattorinoID) AS NumeroConsegne
                        FROM ASSEGNAZIONI_CONSEGNE ac
                        GROUP BY ac.FattorinoID
                        ORDER BY NumeroConsegne DESC
                        LIMIT 5
                        """;

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

        public static final String BEST_CUISINE = """
                        SELECT r.TipologiaCucina, COUNT(r.TipologiaCucina) AS NumeroOrdini
                        FROM ORDINI o
                        JOIN RISTORANTI r ON o.RistoranteID = r.RistoranteID
                        GROUP BY r.TipologiaCucina
                        ORDER BY NumeroOrdini DESC
                        LIMIT 1
                        """;
        public static final String GET_COMMISSION = """
                        SELECT o.Commissione
                        FROM ORDINI o
                        WHERE o.OrdineID = ?
                        """;

}

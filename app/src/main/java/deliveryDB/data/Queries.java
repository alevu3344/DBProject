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

        // Seleziona tutti i ristoranti e i relativi id
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
                        INSERT INTO ORDINI (Username, RistoranteID)
                        VALUES (?, ?)
                        """;

        public static final String SEND_ORDER_DETAILS = """
                        INSERT INTO DETTAGLI_ORDINI (OrdineID, ElementoMenuID, RistoranteID , Quantità)
                        VALUES (?, ?, ? , ?)""";
        public static final String UPDATE_USER_BALANCE = """
                        UPDATE UTENTI
                        SET Balance = ?
                        WHERE Username = ?
                        """;
        // Seleziona tutti gli ordini disponibili (che non si trovano in ASSEGNAZIONI_CONSEGNE)
        public static final String AVAILABLE_ORDERS = """
                        SELECT o.OrdineID, o.Username, o.RistoranteID
                        FROM ORDINI o
                        WHERE o.OrdineID NOT IN (SELECT ao.OrdineID FROM ASSEGNAZIONI_CONSEGNE ao)
                        """;
        // Seleziona tutti gli ordini in ORDINI accettati da un certo username, e che abbianol'attributo DataOraConsegna = null in ASSEGNAZIONI_CONSEGNE
        public static final String ACCEPTED_ORDERS = """
                        SELECT o.OrdineID, o.Username, o.RistoranteID
                        FROM ORDINI o
                        WHERE o.OrdineID IN (SELECT ao.OrdineID FROM ASSEGNAZIONI_CONSEGNE ao WHERE ao.FattorinoID = ? AND ao.DataOraConsegna IS NULL)
                        """;

        public static final String ORDER_DETAILS = """
                        SELECT d.ElementoMenuID, d.Quantità, m.Nome, m.Prezzo, m.Tipo, d.RistoranteID
                        FROM DETTAGLI_ORDINI d
                        JOIN ELEMENTI m ON d.ElementoMenuID = m.ElementoMenuID
                        WHERE d.OrdineID = ?
                        """;

        //query per visualizzare il prezzo totale di un ordine
        public static final String ORDER_TOTAL_PRICE = """
                        SELECT SUM(m.Prezzo * d.Quantità) AS PrezzoTotale
                        FROM DETTAGLI_ORDINI d
                        JOIN ELEMENTI m ON d.ElementoMenuID = m.ElementoMenuID
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

}

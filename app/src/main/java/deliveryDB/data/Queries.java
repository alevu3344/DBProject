package deliveryDB.data;

public final class Queries {

        public static final String RESTAURANT_REVIEWS = """
                        SELECT r.review
                        FROM REVIEW r
                        WHERE r.restaurant_id = ?
                        """;
        // ottiene il menu di un ristorante, ordinando per categoria (Cibo, Bevanda),
        // quindi la query ottera una lista con prima tutti gli eelementi che sono
        // bevanda, poi tutti quelli che sono cibo
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
                        INSERT INTO RECENSIONI (RistoranteID, Voto, Commento)
                        VALUES (?, ?, ?)
                        """;
        public static final String DELETE_REVIEW = """
                        DELETE FROM RECENSIONI
                        WHERE RistoranteID = ? AND Username = ? AND Data = ?
                        """;
        public static final String RESTAURANT_DETAILS = """
                        SELECT r.Nome, r.IndirizzoVia, r.IndirizzoCittà, r.IndirizzoCivico, r.TipologiaCucina, r.OraApertura, r.OraChiusura
                        FROM RISTORANTI r
                        WHERE r.RistoranteID = ?
                        """;

        // Seleziona tutti i ristoranti e i relativi id
        public static final String RESTAURANT_LIST = """
                        SELECT r.RistoranteID, r.Nome
                        FROM RISTORANTI r
                        """;

        public static final String USER_BALANCE = """
                        SELECT u.Balance
                        FROM UTENTI u
                        WHERE u.Username = ?
                        """;

}

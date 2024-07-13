-- Inserisci recensioni solo se l'utente è presente nella tabella UTENTI
-- e se non esistono già recensioni con la stessa data, username e ristorante
INSERT INTO `RECENSIONI` (`Username`, `DataOra`, `RistoranteID`, `Voto`, `Commento`)
SELECT
    r.Username, 
    r.DataOra, 
    r.RistoranteID, 
    r.Voto, 
    r.Commento
FROM
    (SELECT 'johndoe1' AS Username, '2024-07-10 14:30:00' AS DataOra, 1 AS RistoranteID, 5 AS Voto, 'Un’ottima esperienza! Il cibo era delizioso e il servizio impeccabile.' AS Commento
     UNION ALL
     SELECT 'janedoe2', '2024-07-08 18:20:00', 2, 4, 'Ottimi frutti di mare, ma il servizio potrebbe essere migliorato.'
     UNION ALL
     SELECT 'mike.smith', '2024-07-11 20:15:00', 3, 3, 'Pizza buona ma un po\' fredda.'
     UNION ALL
     SELECT 'lucas.johnson', '2024-07-09 19:00:00', 4, 5, 'Eccellente cucina toscana! Piatti gustosi e ambiente accogliente.'
     UNION ALL
     SELECT 'emily.williams', '2024-07-07 21:30:00', 5, 4, 'Buona cucina emiliana, ma un po\' di attesa per essere serviti.'
     UNION ALL
     SELECT 'oliver.brown', '2024-07-12 13:45:00', 6, 5, 'Autentica trattoria siciliana. Cibo fantastico e personale cordiale.'
     UNION ALL
     SELECT 'sophie.jones', '2024-07-11 15:00:00', 7, 3, 'Ristorante nella media. Il cibo è ok, ma niente di speciale.'
     UNION ALL
     SELECT 'benjamin.taylor', '2024-07-10 22:30:00', 8, 5, 'Sushi eccellente e atmosfera moderna. Da consigliare!'
     UNION ALL
     SELECT 'ella.martin', '2024-07-09 12:00:00', 9, 4, 'Piatti piemontesi ben preparati e servizio rapido.'
     UNION ALL
     SELECT 'liam.wilson', '2024-07-12 17:00:00', 10, 3, 'Pizza buona ma l’atmosfera del locale potrebbe essere migliorata.'
     UNION ALL
     SELECT 'ava.moore', '2024-07-11 13:30:00', 11, 4, 'Ottimo cibo italiano e buon servizio, ma il locale era molto affollato.'
     UNION ALL
     SELECT 'mason.miller', '2024-07-08 19:15:00', 12, 4, 'Bella esperienza mediterranea, piatti freschi e ben preparati.'
     UNION ALL
     SELECT 'mia.martinez', '2024-07-10 21:00:00', 13, 5, 'Una vera trattoria veneta con piatti deliziosi e autentici.'
     UNION ALL
     SELECT 'james.anderson', '2024-07-09 14:00:00', 14, 4, 'Cucina siciliana di qualità, anche se un po’ costosa.'
     UNION ALL
     SELECT 'olivia.thomas', '2024-07-11 18:00:00', 15, 5, 'Atmosfera toscana perfetta con cibo eccezionale. Molto soddisfatto.'
     UNION ALL
     SELECT 'william.jackson', '2024-07-10 20:00:00', 16, 4, 'Ristorante italiano con piatti saporiti e servizio amichevole.'
     UNION ALL
     SELECT 'isabella.white', '2024-07-12 19:30:00', 17, 5, 'Sushi fresco e di alta qualità. Ottima scelta per una cena elegante.'
     UNION ALL
     SELECT 'jacob.harris', '2024-07-09 13:00:00', 18, 3, 'Kebab buono ma il locale non era molto pulito.'
     UNION ALL
     SELECT 'ava.davis', '2024-07-11 21:45:00', 19, 4, 'Ramen gustoso e ben servito, anche se un po’ piccante.'
     UNION ALL
     SELECT 'noah.garcia', '2024-07-07 20:00:00', 1, 4, 'Trattoria accogliente con ottimo cibo italiano.'
     UNION ALL
     SELECT 'sophia.martinez', '2024-07-09 16:00:00', 2, 5, 'Frutti di mare freschissimi e servizio veloce.'
     UNION ALL
     SELECT 'ethan.rodriguez', '2024-07-12 12:00:00', 3, 3, 'Pizza decente ma il locale era troppo rumoroso.'
     UNION ALL
     SELECT 'mia.jones', '2024-07-10 15:30:00', 4, 5, 'Ristorante toscano eccellente con cibo delizioso.'
     UNION ALL
     SELECT 'jack.wilson', '2024-07-08 20:00:00', 5, 4, 'Cucina emiliana saporita, ma c’è stata un po’ di attesa.'
     UNION ALL
     SELECT 'amelia.hall', '2024-07-12 14:45:00', 6, 5, 'Cibo siciliano eccellente con un servizio molto cordiale.'
     UNION ALL
     SELECT 'michael.young', '2024-07-09 19:00:00', 7, 3, 'Cibo buono ma senza particolari pregi.'
     UNION ALL
     SELECT 'madison.smith', '2024-07-10 22:30:00', 8, 5, 'Sushi di alta qualità e ambiente raffinato.'
     UNION ALL
     SELECT 'ryan.lewis', '2024-07-11 13:00:00', 9, 4, 'Ottimo cibo piemontese, ma l’ambiente potrebbe essere più accogliente.'
     UNION ALL
     SELECT 'hannah.kim', '2024-07-08 17:30:00', 10, 3, 'Pizza decente ma il servizio è stato un po’ lento.'
     UNION ALL
     SELECT 'chris.carter', '2024-07-11 18:30:00', 11, 4, 'Buona cucina italiana e servizio veloce, anche se il locale era rumoroso.'
     UNION ALL
     SELECT 'ella.baker', '2024-07-09 20:00:00', 12, 4, 'Piatti mediterranei ben preparati e ambiente gradevole.'
     UNION ALL
     SELECT 'nathan.ross', '2024-07-12 14:00:00', 13, 5, 'Autentica trattoria veneta con cibo delizioso.'
     UNION ALL
     SELECT 'emily.james', '2024-07-10 15:45:00', 14, 4, 'Buona cucina siciliana anche se il servizio era un po’ lento.'
     UNION ALL
     SELECT 'daniel.morris', '2024-07-11 22:00:00', 15, 5, 'Cucina toscana deliziosa in un ambiente raffinato.'
     UNION ALL
     SELECT 'ava.perez', '2024-07-08 12:00:00', 16, 4, 'Ristorante italiano con piatti gustosi e ambiente accogliente.'
     UNION ALL
     SELECT 'lucas.cox', '2024-07-10 19:30:00', 17, 5, 'Sushi di alta qualità e ambiente elegante.'
     UNION ALL
     SELECT 'olivia.martinez', '2024-07-11 14:15:00', 18, 3, 'Kebab buono ma il servizio era lento.'
     UNION ALL
     SELECT 'michael.jackson', '2024-07-12 13:30:00', 19, 4, 'Ramen gustoso con un servizio buono.'
    ) r
JOIN `UTENTI` u ON u.Username = r.Username
LEFT JOIN `RECENSIONI` ex ON r.Username = ex.Username AND r.DataOra = ex.DataOra AND r.RistoranteID = ex.RistoranteID
WHERE ex.Username IS NULL;







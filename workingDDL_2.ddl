
-- Creazione della tabella UTENTI
CREATE TABLE UTENTI (
    Username VARCHAR(50) PRIMARY KEY,
    Nome VARCHAR(50) NOT NULL,
    Cognome VARCHAR(50) NOT NULL,
    Password VARCHAR(255) NOT NULL,
    IndirizzoVia VARCHAR(100) NOT NULL,
    IndirizzoCivico VARCHAR(20) NOT NULL,
    IndirizzoCittà VARCHAR(50) NOT NULL,
    Ruolo ENUM('Amministratore', 'Consumatore', 'Fattorino') NOT NULL DEFAULT 'Consumatore',
    Balance DECIMAL(10, 2) DEFAULT 0.00
);


-- Creazione della tabella RISTORANTI
CREATE TABLE RISTORANTI (
    RistoranteID INT PRIMARY KEY AUTO_INCREMENT,
    Nome VARCHAR(100) NOT NULL,
    OraApertura TIME NOT NULL,
    OraChiusura TIME NOT NULL,
    TipologiaCucina VARCHAR(100) NOT NULL,
    IndirizzoVia VARCHAR(100) NOT NULL,
    IndirizzoCivico VARCHAR(20) NOT NULL,
    IndirizzoCittà VARCHAR(50) NOT NULL
);

-- Creazione della tabella ELEMENTI 
CREATE TABLE ELEMENTI (
    ElementoMenuID INT,
    Nome VARCHAR(100) NOT NULL,
    Prezzo DECIMAL(10, 2) NOT NULL,
    Tipo VARCHAR(20) NOT NULL CHECK (Tipo IN ('Cibo', 'Bevanda')),
    RistoranteID INT,
    PRIMARY KEY (ElementoMenuID, RistoranteID), -- Chiave primaria composta
    FOREIGN KEY (RistoranteID) REFERENCES RISTORANTI(RistoranteID)
);

-- Creazione della tabella ORDINI
CREATE TABLE ORDINI (
    OrdineID INT PRIMARY KEY AUTO_INCREMENT,
    DataOra DATETIME NOT NULL,
    Username VARCHAR(50) NOT NULL,
    RistoranteID INT NOT NULL,
    Stato ENUM('Stallo', 'Consegna', 'Consegnato') NOT NULL DEFAULT 'Stallo',
    FOREIGN KEY (Username) REFERENCES UTENTI(Username),
    FOREIGN KEY (RistoranteID) REFERENCES RISTORANTI(RistoranteID)
);



-- Creazione della tabella DETTAGLI_ORDINI 
CREATE TABLE DETTAGLI_ORDINI (
    OrdineID INT,
    ElementoMenuID INT,
    RistoranteID INT,
    Quantità INT NOT NULL,
    PRIMARY KEY (OrdineID, ElementoMenuID, RistoranteID), -- Chiave primaria composta
    FOREIGN KEY (OrdineID) REFERENCES ORDINI(OrdineID),
    FOREIGN KEY (ElementoMenuID, RistoranteID) REFERENCES ELEMENTI(ElementoMenuID, RistoranteID)
);


--Giusta
-- Creazione della tabella ORDINI
CREATE TABLE ORDINI (
    DataOra DATETIME NOT NULL,
    Username VARCHAR(50) NOT NULL,
    RistoranteID INT NOT NULL,
    Stato ENUM('Stallo', 'Consegna', 'Consegnato') NOT NULL DEFAULT 'Stallo',
    PRIMARY KEY (RistoranteID, Username, DataOra),
    FOREIGN KEY (Username) REFERENCES UTENTI(Username),
    FOREIGN KEY (RistoranteID) REFERENCES RISTORANTI(RistoranteID)
);
-- Giusta 
-- Creazione della tabella DETTAGLI_ORDINI 
CREATE TABLE DETTAGLI_ORDINI (
    RistoranteID INT NOT NULL,
    Username VARCHAR(50) NOT NULL,
    DataOra DATETIME NOT NULL,
    ElementoMenuID INT NOT NULL,
    Quantità INT NOT NULL,
    PRIMARY KEY (RistoranteID, Username, DataOra, ElementoMenuID), -- Chiave primaria composta
    FOREIGN KEY (RistoranteID, Username, DataOra) REFERENCES ORDINI(RistoranteID, Username, DataOra),
    FOREIGN KEY (ElementoMenuID, RistoranteID) REFERENCES ELEMENTI(ElementoMenuID, RistoranteID)
);




CREATE TABLE `ASSEGNAZIONI_CONSEGNE` (
  `DataOra` datetime NOT NULL,
  `ConsumatoreID` varchar(50) NOT NULL,
  `RistoranteID` int(11) NOT NULL,
  `FattorinoID` varchar(50) NOT NULL,
  `DataOraAssegnazione` datetime DEFAULT NULL,
  `DataOraConsegna` datetime DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;


-- Creazione della tabella RECENSIONI con chiave primaria composta
CREATE TABLE RECENSIONI (
    Username VARCHAR(50),
    DataOra DATETIME NOT NULL,
    RistoranteID INT NOT NULL,
    Voto INT NOT NULL CHECK (Voto BETWEEN 1 AND 5),
    Commento TEXT,
    PRIMARY KEY (Username, DataOra, RistoranteID),
    FOREIGN KEY (Username) REFERENCES UTENTI(Username),
    FOREIGN KEY (RistoranteID) REFERENCES RISTORANTI(RistoranteID)
);








-- Indici per le tabelle `ASSEGNAZIONI_CONSEGNE`
--
ALTER TABLE `ASSEGNAZIONI_CONSEGNE`
  ADD PRIMARY KEY (`OrdineID`,`DeliveryManUsername`),
  ADD KEY `DeliveryManUsername` (`DeliveryManUsername`);

--
-- Indici per le tabelle `DETTAGLI_ORDINI`
--
ALTER TABLE `DETTAGLI_ORDINI`
  ADD PRIMARY KEY (`OrdineID`,`ElementoMenuID`,`RistoranteID`),
  ADD KEY `ElementoMenuID` (`ElementoMenuID`,`RistoranteID`);

--
--
-- Indici per le tabelle `ORDINI`
--
ALTER TABLE `ORDINI`
  ADD PRIMARY KEY (`OrdineID`),
  ADD KEY `Username` (`Username`),
  ADD KEY `RistoranteID` (`RistoranteID`);


ALTER TABLE `ORDINI`
  MODIFY `OrdineID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=21;


--
-- Limiti per la tabella `ASSEGNAZIONI_CONSEGNE`
--
ALTER TABLE `ASSEGNAZIONI_CONSEGNE`
  ADD CONSTRAINT `ASSEGNAZIONI_CONSEGNE_ibfk_1` FOREIGN KEY (`OrdineID`) REFERENCES `ORDINI` (`OrdineID`),
  ADD CONSTRAINT `ASSEGNAZIONI_CONSEGNE_ibfk_2` FOREIGN KEY (`DeliveryManUsername`) REFERENCES `UTENTI` (`Username`);

--
-- Limiti per la tabella `DETTAGLI_ORDINI`
--
ALTER TABLE `DETTAGLI_ORDINI`
  ADD CONSTRAINT `DETTAGLI_ORDINI_ibfk_1` FOREIGN KEY (`OrdineID`) REFERENCES `ORDINI` (`OrdineID`),
  ADD CONSTRAINT `DETTAGLI_ORDINI_ibfk_2` FOREIGN KEY (`ElementoMenuID`,`RistoranteID`) REFERENCES `ELEMENTI` (`ElementoMenuID`, `RistoranteID`);

--Limiti per la tabella `ORDINI`
--
ALTER TABLE `ORDINI`
  ADD CONSTRAINT `ORDINI_ibfk_1` FOREIGN KEY (`Username`) REFERENCES `UTENTI` (`Username`),
  ADD CONSTRAINT `ORDINI_ibfk_2` FOREIGN KEY (`RistoranteID`) REFERENCES `RISTORANTI` (`RistoranteID`);


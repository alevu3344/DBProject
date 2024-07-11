
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

CREATE TABLE ASSEGNAZIONI_CONSEGNE (
    OrdineID INT NOT NULL,
    DeliveryManUsername VARCHAR(50) NOT NULL,
    DataOraAssegnazione DATETIME,
    DataOraConsegna DATETIME,
    PRIMARY KEY (OrdineID, DeliveryManUsername),
    FOREIGN KEY (OrdineID) REFERENCES ORDINI(OrdineID),
    FOREIGN KEY (DeliveryManUsername) REFERENCES UTENTI(Username)
);

-- Creazione della tabella RECENSIONI
CREATE TABLE RECENSIONI (
    RecensioneID INT PRIMARY KEY AUTO_INCREMENT,
    Voto INT NOT NULL CHECK (Voto BETWEEN 1 AND 5),
    Commento TEXT,
    DataOra DATETIME NOT NULL,
    Username VARCHAR(50) NOT NULL,
    RistoranteID INT NOT NULL,
    FOREIGN KEY (Username) REFERENCES UTENTI(Username),
    FOREIGN KEY (RistoranteID) REFERENCES RISTORANTI(RistoranteID)
);




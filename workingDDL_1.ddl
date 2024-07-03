-- Creazione della tabella Ruolo
CREATE TABLE Ruolo (
    RuoloID INT PRIMARY KEY AUTO_INCREMENT,
    Nome VARCHAR(50) NOT NULL UNIQUE
);

-- Inserimento dei ruoli possibili
INSERT INTO Ruolo (Nome) VALUES
    ('Amministratore'),
    ('Utente'),
    ('Delivery Man');

-- Creazione della tabella Utente
CREATE TABLE Utente (
    UserID INT PRIMARY KEY AUTO_INCREMENT,
    Nome VARCHAR(50) NOT NULL,
    Cognome VARCHAR(50) NOT NULL,
    Username VARCHAR(50) NOT NULL UNIQUE,
    Mail VARCHAR(100) NOT NULL UNIQUE,
    Password VARCHAR(255) NOT NULL,
    IndirizzoVia VARCHAR(100) NOT NULL,
    IndirizzoCivico VARCHAR(20) NOT NULL,
    IndirizzoCittà VARCHAR(50) NOT NULL,
    RuoloID INT NOT NULL,
    FOREIGN KEY (RuoloID) REFERENCES Ruolo(RuoloID)
);

-- Creazione della tabella Ristorante
CREATE TABLE Ristorante (
    RistoranteID INT PRIMARY KEY AUTO_INCREMENT,
    Nome VARCHAR(100) NOT NULL,
    OraApertura TIME NOT NULL,
    OraChiusura TIME NOT NULL,
    TipologiaCucina VARCHAR(100) NOT NULL,
    IndirizzoVia VARCHAR(100) NOT NULL,
    IndirizzoCivico VARCHAR(20) NOT NULL,
    IndirizzoCittà VARCHAR(50) NOT NULL
);

-- Creazione della tabella Ordine
CREATE TABLE Ordine (
    OrdineID INT PRIMARY KEY AUTO_INCREMENT,
    DataOra DATETIME NOT NULL,
    UserID INT NOT NULL,
    RistoranteID INT NOT NULL,
    Stato VARCHAR(20) NOT NULL CHECK (Stato IN ('Stallo', 'Consegna', 'Consegnato')),
    FOREIGN KEY (UserID) REFERENCES Utente(UserID),
    FOREIGN KEY (RistoranteID) REFERENCES Ristorante(RistoranteID)
);

-- Creazione della tabella ElementoMenu
CREATE TABLE ElementoMenu (
    ElementoMenuID INT PRIMARY KEY AUTO_INCREMENT,
    Nome VARCHAR(100) NOT NULL,
    Prezzo DECIMAL(10, 2) NOT NULL,
    Tipo VARCHAR(20) NOT NULL CHECK (Tipo IN ('Cibo', 'Bevanda')),
    RistoranteID INT NOT NULL,
    FOREIGN KEY (RistoranteID) REFERENCES Ristorante(RistoranteID)
);

-- Creazione della tabella DettaglioOrdine
CREATE TABLE DettaglioOrdine (
    OrdineID INT,
    ElementoMenuID INT,
    Quantità INT NOT NULL,
    PRIMARY KEY (OrdineID, ElementoMenuID),
    FOREIGN KEY (OrdineID) REFERENCES Ordine(OrdineID),
    FOREIGN KEY (ElementoMenuID) REFERENCES ElementoMenu(ElementoMenuID)
);

-- Creazione della tabella Recensione
CREATE TABLE Recensione (
    RecensioneID INT PRIMARY KEY AUTO_INCREMENT,
    Voto INT NOT NULL CHECK (Voto BETWEEN 1 AND 5),
    Commento TEXT,
    DataOra DATETIME NOT NULL,
    UserID INT NOT NULL,
    RistoranteID INT NOT NULL,
    FOREIGN KEY (UserID) REFERENCES Utente(UserID),
    FOREIGN KEY (RistoranteID) REFERENCES Ristorante(RistoranteID)
);

-- Creazione della tabella AssegnazioneConsegna
CREATE TABLE AssegnazioneConsegna (
    AssegnazioneID INT PRIMARY KEY AUTO_INCREMENT,
    OrdineID INT NOT NULL,
    DeliveryManID INT NOT NULL,
    DataOraAssegnazione DATETIME,
    DataOraConsegna DATETIME,
    FOREIGN KEY (OrdineID) REFERENCES Ordine(OrdineID),
    FOREIGN KEY (DeliveryManID) REFERENCES Utente(UserID)
);


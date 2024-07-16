CREATE TABLE `ASSEGNAZIONI_CONSEGNE` (
  `OrdineID` int(11) NOT NULL,
  `FattorinoID` varchar(50) NOT NULL,
  `DataOraAssegnazione` datetime DEFAULT current_timestamp(),
  `DataOraConsegna` datetime DEFAULT NULL
);

CREATE TABLE `ASSEGNAZIONI_CONSEGNE` (
  `OrdineID` int(11) NOT NULL,
  `FattorinoID` varchar(50) NOT NULL,
  `DataOraAssegnazione` datetime DEFAULT current_timestamp(),
  `DataOraConsegna` datetime DEFAULT NULL
);


CREATE TABLE `ELEMENTI` (
  `ElementoMenuID` int(11) NOT NULL,
  `Nome` varchar(100) NOT NULL,
  `Prezzo` decimal(10,2) NOT NULL,
  `Tipo` varchar(20) NOT NULL CHECK (`Tipo` in ('Cibo','Bevanda')),
  `RistoranteID` int(11) NOT NULL
);

CREATE TABLE `ORDINI` (
  `OrdineID` int(11) NOT NULL,
  `DataOra` datetime NOT NULL DEFAULT current_timestamp(),
  `Username` varchar(50) NOT NULL,
  `RistoranteID` int(11) NOT NULL,
  `Commissione` decimal(5,2) NOT NULL DEFAULT 0.20
);

CREATE TABLE `RECENSIONI` (
  `Username` varchar(50) NOT NULL,
  `DataOra` datetime NOT NULL DEFAULT current_timestamp(),
  `RistoranteID` int(11) NOT NULL,
  `Voto` int(11) NOT NULL CHECK (`Voto` between 1 and 5),
  `Commento` text DEFAULT NULL
);


CREATE TABLE `RISTORANTI` (
  `RistoranteID` int(11) NOT NULL,
  `Nome` varchar(100) NOT NULL,
  `OraApertura` time NOT NULL,
  `OraChiusura` time NOT NULL,
  `TipologiaCucina` varchar(100) NOT NULL,
  `IndirizzoVia` varchar(100) NOT NULL,
  `IndirizzoCivico` varchar(20) NOT NULL,
  `IndirizzoCittà` varchar(50) NOT NULL
);


CREATE TABLE `UTENTI` (
  `Username` varchar(50) NOT NULL,
  `Nome` varchar(50) NOT NULL,
  `Cognome` varchar(50) NOT NULL,
  `Password` varchar(255) NOT NULL,
  `IndirizzoVia` varchar(100) NOT NULL,
  `IndirizzoCivico` varchar(20) NOT NULL,
  `IndirizzoCittà` varchar(50) NOT NULL,
  `Ruolo` enum('Amministratore','Consumatore','Fattorino') NOT NULL DEFAULT 'Consumatore',
  `Balance` decimal(10,2) DEFAULT 0.00
);


ALTER TABLE `ASSEGNAZIONI_CONSEGNE`
  ADD PRIMARY KEY (`OrdineID`),
  ADD UNIQUE KEY `UniqueAccept` (`DataOraAssegnazione`,`FattorinoID`);


ALTER TABLE `DETTAGLI_ORDINI`
  ADD PRIMARY KEY (`OrdineID`,`ElementoMenuID`,`RistoranteID`),
  ADD KEY `ElementoMenuID` (`ElementoMenuID`,`RistoranteID`);


ALTER TABLE `ELEMENTI`
  ADD PRIMARY KEY (`ElementoMenuID`,`RistoranteID`),
  ADD KEY `RistoranteID` (`RistoranteID`);


ALTER TABLE `ORDINI`
  ADD PRIMARY KEY (`OrdineID`),
  ADD UNIQUE KEY `UniqueOrder` (`DataOra`,`Username`,`RistoranteID`),
  ADD KEY `ORDINI_ibfk_1` (`Username`),
  ADD KEY `ORDINI_ibfk_2` (`RistoranteID`);


ALTER TABLE `RECENSIONI`
  ADD PRIMARY KEY (`Username`,`DataOra`,`RistoranteID`),
  ADD KEY `RistoranteID` (`RistoranteID`);

ALTER TABLE `RISTORANTI`
  ADD PRIMARY KEY (`RistoranteID`);


ALTER TABLE `UTENTI`
  ADD PRIMARY KEY (`Username`);

ALTER TABLE `ORDINI`
  MODIFY `OrdineID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=51;

ALTER TABLE `RISTORANTI`
  MODIFY `RistoranteID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=20;


ALTER TABLE `ASSEGNAZIONI_CONSEGNE`
  ADD CONSTRAINT `ASSEGNAZIONI_CONSEGNE_ibfk_1` FOREIGN KEY (`OrdineID`) REFERENCES `ORDINI` (`OrdineID`) ON DELETE CASCADE,
  ADD CONSTRAINT `ASSEGNAZIONI_CONSEGNE_ibfk_2` FOREIGN KEY (`FattorinoID`) REFERENCES `UTENTI` (`Username`);

ALTER TABLE `DETTAGLI_ORDINI`
  ADD CONSTRAINT `DETTAGLI_ORDINI_ibfk_1` FOREIGN KEY (`OrdineID`) REFERENCES `ORDINI` (`OrdineID`) ON DELETE CASCADE,
  ADD CONSTRAINT `DETTAGLI_ORDINI_ibfk_2` FOREIGN KEY (`ElementoMenuID`,`RistoranteID`) REFERENCES `ELEMENTI` (`ElementoMenuID`, `RistoranteID`);

ALTER TABLE `ELEMENTI`
  ADD CONSTRAINT `ELEMENTI_ibfk_1` FOREIGN KEY (`RistoranteID`) REFERENCES `RISTORANTI` (`RistoranteID`);

ALTER TABLE `ORDINI`
  ADD CONSTRAINT `ORDINI_ibfk_1` FOREIGN KEY (`Username`) REFERENCES `UTENTI` (`Username`),
  ADD CONSTRAINT `ORDINI_ibfk_2` FOREIGN KEY (`RistoranteID`) REFERENCES `RISTORANTI` (`RistoranteID`);

ALTER TABLE `RECENSIONI`
  ADD CONSTRAINT `RECENSIONI_ibfk_1` FOREIGN KEY (`Username`) REFERENCES `UTENTI` (`Username`),
  ADD CONSTRAINT `RECENSIONI_ibfk_2` FOREIGN KEY (`RistoranteID`) REFERENCES `RISTORANTI` (`RistoranteID`);

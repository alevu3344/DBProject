/*DELIVERY DATABASE4 (Ha i dati del 2)  HA L'ATTRIBUTO STATO DI ORDINI*/

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `DeliveryDatabase2`
--

-- --------------------------------------------------------

--
-- Struttura della tabella `ASSEGNAZIONI_CONSEGNE`
--

CREATE TABLE ASSEGNAZIONI_CONSEGNE (
  OrdineID int(11) NOT NULL,
  FattorinoID varchar(50) NOT NULL,
  DataOraAssegnazione datetime DEFAULT NOW(),
  DataOraConsegna datetime DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;


-- --------------------------------------------------------

--
-- Struttura della tabella `DETTAGLI_ORDINI`
--

CREATE TABLE `DETTAGLI_ORDINI` (
  `OrdineID` int(11) NOT NULL,
  `ElementoMenuID` int(11) NOT NULL,
  `RistoranteID` int(11) NOT NULL,
  `Quantità` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Struttura della tabella `ELEMENTI`
--

CREATE TABLE `ELEMENTI` (
  `ElementoMenuID` int(11) NOT NULL,
  `Nome` varchar(100) NOT NULL,
  `Prezzo` decimal(10,2) NOT NULL,
  `Tipo` varchar(20) NOT NULL CHECK (`Tipo` in ('Cibo','Bevanda')),
  `RistoranteID` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dump dei dati per la tabella `ELEMENTI`
--

INSERT INTO `ELEMENTI` (`ElementoMenuID`, `Nome`, `Prezzo`, `Tipo`, `RistoranteID`) VALUES
(1, 'Pizza Margherita', 8.50, 'Cibo', 1),
(1, 'Spaghetti alle Vongole', 12.90, 'Cibo', 2),
(1, 'Risotto ai Frutti di Mare', 14.50, 'Cibo', 3),
(1, 'Tagliata di Manzo', 15.50, 'Cibo', 4),
(1, 'Gnocchi al Pesto', 9.00, 'Cibo', 5),
(1, 'Arancini Siciliani', 5.50, 'Cibo', 6),
(1, 'Tiramisù', 7.00, 'Cibo', 7),
(1, 'Sushi Misto', 18.00, 'Cibo', 8),
(1, 'Vitel tonné', 18.00, 'Cibo', 9),
(1, 'Margherita', 6.50, 'Cibo', 10),
(1, 'Bruschetta al Pomodoro', 5.00, 'Cibo', 11),
(1, 'Antipasto di Mare', 14.00, 'Cibo', 12),
(1, 'Risotto al Nero di Seppia', 14.00, 'Cibo', 13),
(1, 'Arancini Siciliani', 8.00, 'Cibo', 14),
(1, 'Ribollita', 10.00, 'Cibo', 15),
(1, 'Cacio e Pepe', 12.00, 'Cibo', 16),
(1, 'Sashimi Salmone', 16.00, 'Cibo', 17),
(1, 'Kebab Classico', 6.50, 'Cibo', 18),
(1, 'Ramen Tonkotsu', 12.00, 'Cibo', 19),
(2, 'Insalata Caprese', 6.50, 'Cibo', 1),
(2, 'Calamari Fritti', 11.00, 'Cibo', 2),
(2, 'Spaghetti Carbonara', 9.00, 'Cibo', 3),
(2, 'Lasagna al Forno', 10.00, 'Cibo', 4),
(2, 'Insalata Greca', 9.00, 'Cibo', 5),
(2, 'Cannoli alla Siciliana', 6.00, 'Cibo', 6),
(2, 'Pizza Quattro Stagioni', 10.50, 'Cibo', 7),
(2, 'Sake Giapponese', 12.00, 'Bevanda', 8),
(2, 'Acciughe al verde', 12.00, 'Cibo', 9),
(2, 'Capricciosa', 8.50, 'Cibo', 10),
(2, 'Risotto ai Funghi Porcini', 12.00, 'Cibo', 11),
(2, 'Spaghetti alle Cozze', 12.50, 'Cibo', 12),
(2, 'Baccalà Mantecato', 12.50, 'Cibo', 13),
(2, 'Pasta alla Norma', 12.50, 'Cibo', 14),
(2, 'Pappa al Pomodoro', 9.50, 'Cibo', 15),
(2, 'Saltimbocca alla Romana', 16.50, 'Cibo', 16),
(2, 'Sushi Maki', 12.50, 'Cibo', 17),
(2, 'Kebab di Pollo', 7.00, 'Cibo', 18),
(2, 'Ramen Miso', 11.50, 'Cibo', 19),
(3, 'Cotoletta alla Milanese', 12.00, 'Cibo', 1),
(3, 'Coca Cola', 3.00, 'Bevanda', 2),
(3, 'Carciofi alla Romana', 8.50, 'Cibo', 3),
(3, 'Risotto al Funghi Porcini', 14.00, 'Cibo', 4),
(3, 'Risotto al Tartufo', 20.00, 'Cibo', 5),
(3, 'Polpo alla Griglia', 18.00, 'Cibo', 6),
(3, 'Caffè Lungo', 2.00, 'Bevanda', 7),
(3, 'Uramaki California', 15.00, 'Cibo', 8),
(3, 'Agnolotti', 15.00, 'Cibo', 9),
(3, 'Quattro Stagioni', 9.00, 'Cibo', 10),
(3, 'Cotoletta alla Milanese', 14.00, 'Cibo', 11),
(3, 'Frittura di Paranza', 18.00, 'Cibo', 12),
(3, 'Polenta e Schie', 16.00, 'Cibo', 13),
(3, 'Caponata', 9.00, 'Cibo', 14),
(3, 'Bistecca alla Fiorentina', 25.00, 'Cibo', 15),
(3, 'Carne alla Brace', 20.00, 'Cibo', 16),
(3, 'Tempura di Gamberi', 18.00, 'Cibo', 17),
(3, 'Falafel', 5.00, 'Cibo', 18),
(3, 'Ramen Shoyu', 11.50, 'Cibo', 19),
(4, 'Gelato alla Fragola', 4.50, 'Cibo', 1),
(4, 'Tortellini in Brodo', 10.50, 'Cibo', 2),
(4, 'Bistecca alla Fiorentina', 22.00, 'Cibo', 3),
(4, 'Frittura di Pesce', 13.00, 'Cibo', 4),
(4, 'Panna Cotta', 6.50, 'Cibo', 5),
(4, 'Tiramisù al Limone', 8.00, 'Cibo', 6),
(4, 'Torta della Nonna', 5.50, 'Cibo', 7),
(4, 'Tempura di Gamberi', 17.00, 'Cibo', 8),
(4, 'Brasato al Barolo', 17.00, 'Cibo', 9),
(4, 'Diavola', 7.50, 'Cibo', 10),
(4, 'Tagliatelle al Ragu', 10.00, 'Cibo', 11),
(4, 'Tagliata di Manzo', 16.50, 'Cibo', 12),
(4, 'Frittura di Pesce', 18.50, 'Cibo', 13),
(4, 'Spaghetti alle Sarde', 14.50, 'Cibo', 14),
(4, 'Pappardelle al Cinghiale', 18.00, 'Cibo', 15),
(4, 'Tartufo Nero', 25.00, 'Cibo', 16),
(4, 'Uramaki California', 15.00, 'Cibo', 17),
(4, 'Shawarma di Agnello', 8.00, 'Cibo', 18),
(4, 'Gyoza', 6.50, 'Cibo', 19),
(5, 'Focaccia al Rosmarino', 3.50, 'Cibo', 1),
(5, 'Granita al Limone', 3.00, 'Cibo', 2),
(5, 'Vino Rosso Chianti', 18.00, 'Bevanda', 3),
(5, 'Pappardelle al Cinghiale', 13.50, 'Cibo', 4),
(5, 'Gelato al Cioccolato', 4.00, 'Cibo', 5),
(5, 'Gelato alla Fragola', 3.50, 'Cibo', 6),
(5, 'Spritz', 7.00, 'Bevanda', 7),
(5, 'Mochi al Matcha', 5.00, 'Cibo', 8),
(5, 'Fritto misto alla Piemontese', 5.00, 'Cibo', 9),
(5, 'Napoletana', 8.00, 'Cibo', 10),
(5, 'Polpette al Sugo', 8.50, 'Cibo', 11),
(5, 'Pesce Spada alla Griglia', 20.00, 'Cibo', 12),
(5, 'Bigoli in Salsa', 11.00, 'Cibo', 13),
(5, 'Parmigiana di Melanzane', 16.00, 'Cibo', 14),
(5, 'Cantucci con Vin Santo', 8.50, 'Cibo', 15),
(5, 'Carciofi alla Giudia', 11.50, 'Cibo', 16),
(5, 'Edamame', 5.00, 'Cibo', 17),
(5, 'Patatine', 3.00, 'Cibo', 18),
(5, 'Tempura', 7.00, 'Cibo', 19),
(6, 'Birra Moretti', 4.00, 'Bevanda', 1),
(6, 'Negroni', 8.00, 'Bevanda', 2),
(6, 'Pasta alla Norma', 11.50, 'Cibo', 3),
(6, 'Sashimi Salmone', 16.00, 'Cibo', 4),
(6, 'Birra Artigianale IPA', 6.50, 'Bevanda', 5),
(6, 'Birra Peroni', 3.50, 'Bevanda', 6),
(6, 'Frittura di Pesce', 13.00, 'Cibo', 7),
(6, 'Birra Asahi', 5.00, 'Bevanda', 8),
(6, 'Bicerin', 5.00, 'Bevanda', 9),
(6, 'Calzone', 9.50, 'Cibo', 10),
(6, 'Panna Cotta', 6.00, 'Cibo', 11),
(6, 'Risotto ai Frutti di Mare', 15.00, 'Cibo', 12),
(6, 'Sarde in Saor', 9.50, 'Cibo', 13),
(6, 'Pesce Spada alla Siciliana', 22.00, 'Cibo', 14),
(6, 'Panforte', 7.00, 'Cibo', 15),
(6, 'Baccalà alla Romana', 18.00, 'Cibo', 16),
(6, 'Gyoza', 8.00, 'Cibo', 17),
(6, 'Insalata Greca', 4.50, 'Cibo', 18),
(6, 'Edamame', 4.00, 'Cibo', 19),
(7, 'Torta Tatin', 7.00, 'Cibo', 1),
(7, 'Pizza Diavola', 9.00, 'Cibo', 2),
(7, 'Gelato alla Vaniglia', 4.00, 'Cibo', 3),
(7, 'Vino Rosso Brunello di Montalcino', 30.00, 'Bevanda', 4),
(7, 'Gelato alla Pistacchio', 4.50, 'Cibo', 5),
(7, 'Caffè Espresso', 1.50, 'Bevanda', 6),
(7, 'Ravioli al Burro e Salvia', 12.00, 'Cibo', 7),
(7, 'Sashimi Tonno', 19.00, 'Cibo', 8),
(7, 'Gianduiotto', 19.00, 'Cibo', 9),
(7, 'Pizza Bianca', 7.00, 'Cibo', 10),
(7, 'Tiramisù', 7.00, 'Cibo', 11),
(7, 'Insalata Caprese', 8.50, 'Cibo', 12),
(7, 'Tiramisù', 7.00, 'Cibo', 13),
(7, 'Cannoli Siciliani', 6.50, 'Cibo', 14),
(7, 'Vino Chianti Classico', 15.00, 'Bevanda', 15),
(7, 'Gnocchi alla Romana', 14.00, 'Cibo', 16),
(7, 'Miso Soup', 4.50, 'Cibo', 17),
(7, 'Hummus', 4.00, 'Cibo', 18),
(7, 'Sushi Nigiri', 8.00, 'Cibo', 19),
(8, 'Vino Rosato Chardonnay', 12.00, 'Bevanda', 1),
(8, 'Cannolo Siciliano', 3.00, 'Cibo', 2),
(8, 'Carciofi alla Giudia', 10.50, 'Cibo', 3),
(8, 'Caffè Lungo', 2.00, 'Bevanda', 4),
(8, 'Birra Heineken', 4.00, 'Bevanda', 5),
(8, 'Mojito', 8.00, 'Bevanda', 6),
(8, 'Amarone della Valpolicella', 35.00, 'Bevanda', 7),
(8, 'Sake Giapponese Premium', 25.00, 'Bevanda', 8),
(8, 'Bonet', 25.00, 'Cibo', 9),
(8, 'Prosciutto e Funghi', 8.50, 'Cibo', 10),
(8, 'Gelato alla Crema', 4.50, 'Cibo', 11),
(8, 'Tiramisù', 7.50, 'Cibo', 12),
(8, 'Panna Cotta', 6.50, 'Cibo', 13),
(8, 'Granita al Limone', 3.50, 'Bevanda', 14),
(8, 'Vino Brunello di Montalcino', 30.00, 'Bevanda', 15),
(8, 'Pizza Margherita', 10.00, 'Cibo', 16),
(8, 'Matcha Latte', 5.50, 'Bevanda', 17),
(8, 'Baklava', 3.50, 'Cibo', 18),
(8, 'Sushi Sashimi', 10.00, 'Cibo', 19),
(9, 'Tagliolini al Tartufo', 18.00, 'Cibo', 1),
(9, 'Aperol Spritz', 7.50, 'Bevanda', 2),
(9, 'Vino Bianco Vermentino', 14.00, 'Bevanda', 3),
(9, 'Uramaki Filadelfia', 16.00, 'Cibo', 4),
(9, 'Acqua Minerale Naturale', 2.00, 'Bevanda', 5),
(9, 'Fettuccine Alfredo', 13.50, 'Cibo', 6),
(9, 'Tiramisù al Cioccolato', 7.00, 'Cibo', 7),
(9, 'Matcha Latte', 5.50, 'Bevanda', 8),
(9, 'Bagna Caoda', 5.50, 'Cibo', 9),
(9, 'Tonno e Cipolla', 9.00, 'Cibo', 10),
(9, 'Acqua Naturale', 1.00, 'Bevanda', 11),
(9, 'Acqua Naturale', 1.50, 'Bevanda', 12),
(9, 'Acqua Minerale', 1.00, 'Bevanda', 13),
(9, 'Birra Moretti', 4.00, 'Bevanda', 14),
(9, 'Acqua Naturale', 1.50, 'Bevanda', 15),
(9, 'Prosciutto e Melone', 9.00, 'Cibo', 16),
(9, 'Sake Giapponese', 12.00, 'Bevanda', 17),
(9, 'Ayran', 2.50, 'Bevanda', 18),
(9, 'Matcha Latte', 4.50, 'Bevanda', 19),
(10, 'Amarone della Valpolicella', 35.00, 'Bevanda', 1),
(10, 'Pasta all\'Arrabbiata', 9.50, 'Cibo', 3),
(10, 'Sashimi Tonno', 19.00, 'Cibo', 4),
(10, 'Lambrusco', 15.00, 'Bevanda', 5),
(10, 'Tempura di Sushi', 22.00, 'Cibo', 6),
(10, 'Fettuccine Alfredo', 13.50, 'Cibo', 7),
(10, 'Tempura di Verdure', 9.50, 'Cibo', 8),
(10, 'Insalata russa', 9.50, 'Cibo', 9),
(10, 'Salsiccia e Friarielli', 10.00, 'Cibo', 10),
(10, 'Vino Rosso della Casa', 10.00, 'Bevanda', 11),
(10, 'Vino Bianco Vermentino', 14.00, 'Bevanda', 12),
(10, 'Spritz', 6.00, 'Bevanda', 13),
(10, 'Limoncello', 5.00, 'Bevanda', 14),
(10, 'Birra Artigianale', 5.00, 'Bevanda', 15),
(10, 'Bruschette miste', 8.00, 'Cibo', 16),
(10, 'Birra Asahi', 5.00, 'Bevanda', 17),
(10, 'Tè Turco', 2.00, 'Bevanda', 18),
(10, 'Sake', 6.00, 'Bevanda', 19),
(11, 'Risotto al Barolo', 9.50, 'Cibo', 9),
(11, 'Pasta al Pomodoro', 6.00, 'Cibo', 10),
(11, 'Birra Peroni', 3.50, 'Bevanda', 11),
(11, 'Birra Moretti', 4.00, 'Bevanda', 12),
(11, 'Vino Bianco Soave', 12.00, 'Bevanda', 13),
(11, 'Insalata di Arance e Finocchi', 8.50, 'Cibo', 14),
(11, 'Insalata di Ceci', 8.00, 'Cibo', 15),
(11, 'Caprese', 7.50, 'Cibo', 16),
(11, 'Sushi Sashimi Misto', 20.00, 'Cibo', 17),
(11, 'Baklava al Pistacchio', 4.00, 'Cibo', 18),
(11, 'Mochi', 5.00, 'Cibo', 19),
(12, 'Baci di dama', 9.50, 'Cibo', 9),
(12, 'Rucola e Parmigiano', 8.50, 'Cibo', 10),
(12, 'Limoncello', 4.00, 'Bevanda', 11),
(12, 'Limoncello', 5.00, 'Bevanda', 12),
(12, 'Limoncello', 4.00, 'Bevanda', 13),
(12, 'Risotto alla Pescatora', 18.00, 'Cibo', 14),
(12, 'Cinghiale in Umido', 20.00, 'Cibo', 15),
(12, 'Panna Cotta', 6.50, 'Cibo', 16),
(12, 'Tempura di Sushi', 22.00, 'Cibo', 17),
(12, 'Kebab Vegetariano', 6.00, 'Cibo', 18),
(12, 'Udon', 10.00, 'Cibo', 19),
(13, 'D.O.C.G.', 30.00, 'Bevanda', 9),
(13, 'Tiramisù', 7.00, 'Cibo', 10),
(13, 'Pizza Margherita', 8.00, 'Cibo', 11),
(13, 'Pasta alla Puttanesca', 11.00, 'Cibo', 12),
(13, 'Risi e Bisi', 10.00, 'Cibo', 13),
(13, 'Gelato alla Pistacchio', 4.00, 'Cibo', 14),
(13, 'Caffè Lungo', 2.00, 'Bevanda', 15),
(13, 'Tiramisù', 7.00, 'Cibo', 16),
(13, 'Mochi al Matcha', 5.00, 'Cibo', 17),
(13, 'Köfte', 7.50, 'Cibo', 18),
(13, 'Yakitori', 7.50, 'Cibo', 19),
(14, 'Alta Langa', 35.00, 'Bevanda', 9),
(14, 'Acqua Minerale', 1.50, 'Bevanda', 10),
(14, 'Insalata Caprese', 6.50, 'Cibo', 11),
(14, 'Carciofi alla Giudia', 9.00, 'Cibo', 12),
(14, 'Fegato alla Veneziana', 15.00, 'Cibo', 13),
(14, 'Caffè Espresso', 2.00, 'Bevanda', 14),
(14, 'Vino Vernaccia di San Gimignano', 18.00, 'Bevanda', 15),
(14, 'Caffè Espresso', 2.50, 'Bevanda', 16),
(14, 'Sake Giapponese Premium', 25.00, 'Bevanda', 17),
(14, 'Limonata Turca', 3.50, 'Bevanda', 18),
(14, 'Takoyaki', 8.50, 'Cibo', 19),
(15, 'Asti', 25.00, 'Bevanda', 9),
(15, 'Birra Moretti', 3.50, 'Bevanda', 10),
(15, 'Frittura di Paranza', 15.00, 'Cibo', 11),
(15, 'Gelato alla Crema', 4.00, 'Cibo', 12),
(15, 'Radicchio alla Griglia', 8.00, 'Cibo', 13),
(15, 'Amaro Averna', 5.00, 'Bevanda', 14),
(15, 'Castagnaccio', 6.00, 'Cibo', 15),
(15, 'Amarone della Valpolicella', 35.00, 'Bevanda', 16),
(15, 'Matcha Ice Cream', 6.00, 'Cibo', 17),
(15, 'Kebab di Manzo', 8.00, 'Cibo', 18),
(15, 'Chirashi Sushi', 14.00, 'Cibo', 19),
(16, 'Barbaresco', 40.00, 'Bevanda', 9),
(16, 'Coca Cola', 2.50, 'Bevanda', 10),
(16, 'Caffè Espresso', 2.00, 'Bevanda', 11),
(16, 'Caffè Espresso', 2.00, 'Bevanda', 12),
(16, 'Caffè Espresso', 2.00, 'Bevanda', 13),
(16, 'Vino Rosso Nero d\'Avola', 14.00, 'Bevanda', 14),
(16, 'Crostini Toscani', 9.00, 'Cibo', 15),
(16, 'Negroni', 8.00, 'Bevanda', 16),
(16, 'Japanese Whisky', 10.00, 'Bevanda', 17),
(16, 'Tabbouleh', 5.00, 'Cibo', 18),
(16, 'Unagi Don', 15.00, 'Cibo', 19),
(17, 'Barbera d\'Asti', 22.00, 'Bevanda', 9),
(17, 'Spritz', 5.00, 'Bevanda', 10),
(17, 'Aperol Spritz', 7.00, 'Bevanda', 11),
(17, 'Aperol Spritz', 7.00, 'Bevanda', 12),
(17, 'Bellini', 8.50, 'Bevanda', 13),
(17, 'Sfincione Siciliano', 10.00, 'Cibo', 14),
(17, 'Caffè Espresso', 2.00, 'Bevanda', 15),
(17, 'Limoncello', 4.00, 'Bevanda', 16),
(17, 'Sashimi Tonno', 19.00, 'Cibo', 17),
(17, 'Baba Ganoush', 4.50, 'Cibo', 18),
(17, 'Soba', 9.00, 'Cibo', 19),
(18, 'Barbera del Monferrato Superiore', 18.00, 'Bevanda', 9),
(18, 'Negroni', 7.00, 'Bevanda', 10),
(18, 'Vino Bianco Pinot Grigio', 12.00, 'Bevanda', 11),
(18, 'Vino Rosso Chianti', 16.00, 'Bevanda', 12),
(18, 'Vino Rosso Valpolicella', 15.00, 'Bevanda', 13),
(18, 'Casarecce alla Trapanese', 11.50, 'Cibo', 14),
(18, 'Vino Chianti Riserva', 20.00, 'Bevanda', 15),
(18, 'Birra Moretti', 4.00, 'Bevanda', 16),
(18, 'Green Tea', 3.50, 'Bevanda', 17),
(18, 'Dolma', 5.50, 'Cibo', 18),
(18, 'Green Tea', 2.50, 'Bevanda', 19),
(19, 'Barolo', 50.00, 'Bevanda', 9),
(19, 'Chianti Classico', 25.00, 'Bevanda', 10),
(19, 'Ravioli Burro e Salvia', 11.00, 'Cibo', 11),
(19, 'Calamari Fritti', 10.50, 'Cibo', 12),
(19, 'Carpaccio di Manzo', 13.00, 'Cibo', 13),
(19, 'Cannoli alla Ricotta', 7.00, 'Cibo', 14),
(19, 'Ravioli di Ricotta e Spinaci', 14.50, 'Cibo', 15),
(19, 'Vino Rosso Montepulciano d\'Abruzzo', 18.00, 'Bevanda', 16),
(19, 'Sushi Nigiri', 14.00, 'Cibo', 17),
(19, 'Tzatziki', 4.00, 'Cibo', 18),
(19, 'Okonomiyaki', 10.50, 'Cibo', 19),
(20, 'Brachetto d\'Acqui o Acqui', 20.00, 'Bevanda', 9),
(20, 'Prosecco', 20.00, 'Bevanda', 10),
(20, 'Carciofi alla Romana', 9.50, 'Cibo', 11),
(20, 'Insalata di Mare', 9.00, 'Cibo', 12),
(20, 'Risi e Bisi', 11.00, 'Cibo', 13),
(20, 'Birra Peroni', 3.50, 'Bevanda', 14),
(20, 'Schiacciata Fiorentina', 7.50, 'Cibo', 15),
(20, 'Vino Bianco Vermentino', 15.00, 'Bevanda', 16),
(20, 'Sake Caldo', 8.50, 'Bevanda', 17),
(20, 'Büyük Efes', 5.00, 'Bevanda', 18),
(20, 'Katsu Curry', 12.50, 'Cibo', 19),
(21, 'Canelli', 15.00, 'Bevanda', 9),
(21, 'Limoncello', 4.00, 'Bevanda', 10),
(22, 'Dolcetto di Diano d\'Alba o Diano d\'Alba', 18.00, 'Bevanda', 9),
(22, 'Amaro del Capo', 6.00, 'Bevanda', 10),
(23, 'Dolcetto di Ovada Superiore o Ovada', 16.00, 'Bevanda', 9),
(23, 'Frittura di Calamari', 12.00, 'Cibo', 10),
(24, 'Dogliani', 17.00, 'Bevanda', 9),
(24, 'Gelato alla Vaniglia', 3.50, 'Cibo', 10),
(25, 'Erbaluce di Caluso o Caluso', 19.00, 'Bevanda', 9),
(25, 'Patatine Fritte', 4.00, 'Cibo', 10),
(26, 'Gattinara', 30.00, 'Bevanda', 9),
(27, 'Gavi o Cortese di Gavi', 22.00, 'Bevanda', 9),
(28, 'Ghemme', 35.00, 'Bevanda', 9),
(29, 'Nizza', 40.00, 'Bevanda', 9),
(30, 'Roero', 28.00, 'Bevanda', 9),
(31, 'Ruchè di Castagnole Monferrato', 25.00, 'Bevanda', 9),
(32, 'Terre Alfieri', 23.00, 'Bevanda', 9);
-- --------------------------------------------------------

--
-- Struttura della tabella `ORDINI`
--

CREATE TABLE `ORDINI` (
  `OrdineID` int(11) NOT NULL,
  `DataOra` datetime NOT NULL DEFAULT NOW(),
  `Username` varchar(50) NOT NULL,
  `RistoranteID` int(11) NOT NULL,
  PRIMARY KEY (`OrdineID`),
  UNIQUE KEY `UniqueOrder` (`DataOra`, `Username`, `RistoranteID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;


--
-- Dump dei dati per la tabella `ORDINI`
--

INSERT INTO `ORDINI` (`OrdineID`, `DataOra`, `Username`, `RistoranteID`) VALUES
(1, '2024-07-10 12:30:00', 'luca.verdi', 2),
(2, '2024-07-09 19:00:00', 'francesca.bianchi', 1),
(3, '2024-07-11 13:45:00', 'andrea.gallo', 3),
(4, '2024-07-08 20:15:00', 'paolo.bianchi', 4),
(5, '2024-07-07 21:30:00', 'giuseppe.ferrari', 2),
(6, '2024-07-10 18:00:00', 'simone.rizzo', 5),
(7, '2024-07-09 14:30:00', 'maria.ferraro', 6),
(8, '2024-07-11 19:30:00', 'laura.galli', 8),
(9, '2024-07-10 21:00:00', 'giovanna.romano', 10),
(10, '2024-07-09 12:00:00', 'andrea.vitali', 6),
(11, '2024-07-08 20:30:00', 'sara.longo', 9),
(12, '2024-07-07 19:15:00', 'lorenzo.leone', 11),
(13, '2024-07-10 13:45:00', 'rita.pellegrini', 12),
(14, '2024-07-11 14:00:00', 'fabio.gallo', 13),
(15, '2024-07-09 22:15:00', 'elisa.gatti', 14),
(16, '2024-07-10 20:30:00', 'roberto.marini', 8),
(17, '2024-07-11 18:45:00', 'serena.conti', 15),
(18, '2024-07-09 16:30:00', 'giulia.marini', 10),
(19, '2024-07-08 21:15:00', 'marco.conti', 12),
(20, '2024-07-07 19:30:00', 'giulia.marini', 2),
(25, '2024-07-13 13:04:49', 'elisa.gatti', 10),
(26, '2024-07-13 14:13:01', 'elisa.gatti', 14),
(27, '2024-07-13 14:32:53', 'elisa.gatti', 10),
(28, '2024-07-13 14:32:58', 'elisa.gatti', 10),
(29, '2024-07-13 14:33:04', 'elisa.gatti', 10),
(30, '2024-07-13 14:33:08', 'elisa.gatti', 10),
(31, '2024-07-13 14:33:13', 'elisa.gatti', 10),
(32, '2024-07-13 14:35:34', 'elisa.gatti', 10),
(33, '2024-07-13 14:35:39', 'elisa.gatti', 10),
(34, '2024-07-13 14:35:44', 'elisa.gatti', 10),
(35, '2024-07-13 17:00:44', 'elisa.gatti', 12),
(36, '2024-07-13 17:14:20', 'elisa.gatti', 16),
(37, '2024-07-13 19:35:45', 'elisa.gatti', 5);

-- --------------------------------------------------------

--
-- Struttura della tabella `RECENSIONI`
--

CREATE TABLE `RECENSIONI` (
  `Username` varchar(50) NOT NULL,
  `DataOra` datetime NOT NULL DEFAULT NOW(),
  `RistoranteID` int(11) NOT NULL,
  `Voto` int(11) NOT NULL CHECK (`Voto` between 1 and 5),
  `Commento` text DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dump dei dati per la tabella `RECENSIONI`
--

INSERT INTO `RECENSIONI` (`Username`, `DataOra`, `RistoranteID`, `Voto`, `Commento`) VALUES
('aiden.sanders', '2024-07-13 19:45:00', 1, 5, 'Ottimo cibo e servizio eccellente!'),
('amanda.jones', '2024-07-13 20:00:00', 4, 4, 'Bella atmosfera e piatti gustosi.'),
('amelia.hall', '2024-07-12 14:45:00', 6, 5, 'Cibo siciliano eccellente con un servizio molto cordiale.'),
('amelia.hall', '2024-07-14 12:00:00', 7, 3, 'Cibo buono ma l\'attesa è stata lunga.'),
('andrea.gallo', '2024-07-11 13:00:00', 3, 3, 'Pizza buona ma un po\' fredda.'),
('andrea.gallo', '2024-07-14 18:30:00', 10, 4, 'Pizza ottima, servizio veloce.'),
('andrea.vitali', '2024-07-09 12:45:00', 6, 4, 'Piatti siciliani autentici, prezzi giusti.'),
('andrea.vitali', '2024-07-14 19:15:00', 6, 5, 'Eccellente cucina siciliana!'),
('anna.rossi', '2024-07-15 13:00:00', 8, 4, 'Sushi fresco e ben preparato.'),
('arabella.ward', '2024-07-15 20:30:00', 12, 3, 'Il ristorante è carino ma il cibo era mediocre.'),
('ava.davis', '2024-07-11 21:45:00', 19, 4, 'Ramen gustoso e ben servito, anche se un po’ piccante.'),
('ava.davis', '2024-07-16 14:45:00', 13, 5, 'Piatti veneti eccezionali!'),
('ava.moore', '2024-07-11 13:30:00', 11, 4, 'Ottimo cibo italiano e buon servizio, ma il locale era molto affollato.'),
('ava.moore', '2024-07-16 19:00:00', 2, 4, 'Frutti di mare freschi e ben cucinati.'),
('ava.perez', '2024-07-08 12:00:00', 16, 4, 'Ristorante italiano con piatti gustosi e ambiente accogliente.'),
('ava.perez', '2024-07-17 12:30:00', 14, 3, 'Buona cucina siciliana ma il servizio può migliorare.'),
('benjamin.smith', '2024-07-17 20:00:00', 15, 5, 'Ambiente accogliente e piatti toscani deliziosi.'),
('benjamin.taylor', '2024-07-10 22:30:00', 8, 5, 'Sushi eccellente e atmosfera moderna. Da consigliare!'),
('benjamin.taylor', '2024-07-18 13:00:00', 16, 4, 'Ottima cucina italiana, porzioni abbondanti.'),
('carla.martin', '2024-07-18 19:15:00', 17, 3, 'Il sushi è buono ma il servizio lento.'),
('chloe.morris', '2024-07-19 14:00:00', 18, 4, 'Kebab saporito e prezzo giusto.'),
('chris.carter', '2024-07-11 18:30:00', 11, 4, 'Buona cucina italiana e servizio veloce, anche se il locale era rumoroso.'),
('chris.carter', '2024-07-19 20:30:00', 19, 5, 'Ramen eccellente, uno dei migliori che abbia provato.'),
('daniel.garcia', '2024-07-20 13:15:00', 3, 4, 'Pizza buona e ambiente accogliente.'),
('daniel.morris', '2024-07-11 22:00:00', 15, 5, 'Cucina toscana deliziosa in un ambiente raffinato.'),
('daniel.morris', '2024-07-20 19:00:00', 5, 3, 'Cucina emiliana decente ma un po\' costosa.'),
('eleonora.romano', '2024-07-21 14:30:00', 7, 5, 'Sushi fresco e ben presentato.'),
('elijah.martinez', '2024-07-21 19:15:00', 8, 4, 'Ottimo sushi, ma il servizio può migliorare.'),
('elijah.martinez', '2024-08-22 12:00:00', 18, 4, 'Kebab saporito e ben preparato.'),
('elijah.martinez', '2024-08-23 19:00:00', 19, 5, 'Ramen di alta qualità, molto soddisfatto.'),
('elisa.gatti', '2024-07-09 22:00:00', 14, 4, 'Atmosfera accogliente e buona selezione di vini.'),
('elisa.gatti', '2024-07-13 19:21:53', 1, 5, 'aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa'),
('elisa.gatti', '2024-07-22 12:00:00', 10, 4, 'Pizza gustosa, ma l’attesa è stata lunga.'),
('elizabeth.evans', '2024-07-22 19:00:00', 12, 5, 'Cucina mediterranea fantastica e servizio eccellente.'),
('ella.baker', '2024-07-09 20:00:00', 12, 4, 'Piatti mediterranei ben preparati e ambiente gradevole.'),
('ella.baker', '2024-07-23 14:00:00', 13, 3, 'Piatti veneti buoni ma non eccezionali.'),
('ella.brown', '2024-07-23 20:15:00', 14, 4, 'Sicilia al top, ma il servizio era lento.'),
('ella.lee', '2024-07-24 13:30:00', 15, 5, 'Cucina toscana deliziosa, esperienza eccellente.'),
('ella.martin', '2024-07-09 12:00:00', 9, 4, 'Piatti piemontesi ben preparati e servizio rapido.'),
('ella.martin', '2024-07-24 19:00:00', 16, 4, 'Cucina italiana buona, atmosfera accogliente.'),
('emily.james', '2024-07-10 15:45:00', 14, 4, 'Buona cucina siciliana anche se il servizio era un po’ lento.'),
('emily.williams', '2024-07-07 21:30:00', 5, 4, 'Buona cucina emiliana, ma un po\' di attesa per essere serviti.'),
('ethan.rodriguez', '2024-07-12 12:00:00', 3, 3, 'Pizza decente ma il locale era troppo rumoroso.'),
('fabio.gallo', '2024-07-11 14:30:00', 13, 5, 'Cibo siciliano fresco e gustoso.'),
('felix.lee', '2024-07-15 12:00:00', 1, 5, 'Ottimo servizio e cibo eccellente.'),
('felix.lee', '2024-07-27 18:00:00', 13, 4, 'Cucina veneta autentica, ottima esperienza.'),
('francesca.bianchi', '2024-07-09 19:45:00', 1, 5, 'Ambiente accogliente e piatti deliziosi.'),
('giovanna.romano', '2024-07-10 21:30:00', 10, 3, 'Buona pizza ma un po\' rumoroso il locale.'),
('giulia.marini', '2024-07-07 19:00:00', 2, 5, 'Mangiato benissimo, consigliatissimo!'),
('giulia.marini', '2024-07-09 16:45:00', 10, 4, 'Buona pizza e staff gentile.'),
('giuseppe.ferrari', '2024-07-07 21:15:00', 2, 5, 'Molto soddisfatto del pesce fresco.'),
('grace.williams', '2024-07-15 13:00:00', 1, 4, 'Buona cucina, ma il servizio era un po\' lento.'),
('grace.williams', '2024-07-27 18:30:00', 13, 5, 'Ristorante eccellente con piatti tipici veneti.'),
('grace.williams', '2024-08-08 13:00:00', 4, 5, 'Cucina toscana eccellente, ambiente raffinato.'),
('grace.williams', '2024-08-12 19:00:00', 8, 5, 'Sushi di alta qualità e freschezza.'),
('hannah.chen', '2024-07-15 14:00:00', 1, 5, 'Un’esperienza fantastica, ritornerò sicuramente.'),
('hannah.chen', '2024-07-27 19:00:00', 13, 5, 'Un gioiello nascosto a Venezia.'),
('hannah.chen', '2024-08-12 19:30:00', 8, 4, 'Buon sushi, ma il locale è un po\' affollato.'),
('hannah.chen', '2024-08-17 19:00:00', 13, 5, 'Un’ottima esperienza culinaria.'),
('hannah.chen', '2024-08-22 14:00:00', 18, 4, 'Kebab gustoso e servizio veloce.'),
('hannah.kim', '2024-07-08 17:30:00', 10, 3, 'Pizza decente ma il servizio è stato un po’ lento.'),
('harper.white', '2024-07-16 11:00:00', 2, 4, 'Pesce fresco e ben cucinato.'),
('harper.white', '2024-07-28 13:00:00', 14, 4, 'Cibo siciliano delizioso e atmosfera accogliente.'),
('harper.white', '2024-08-07 18:00:00', 3, 5, 'Pizza napoletana autentica e deliziosa.'),
('harper.white', '2024-08-09 12:30:00', 5, 4, 'Piatti ben preparati e saporiti.'),
('harper.white', '2024-08-15 13:00:00', 11, 4, 'Ottima cucina tradizionale, ma il servizio potrebbe migliorare.'),
('ian.roberts', '2024-07-16 12:00:00', 2, 5, 'Il miglior ristorante di frutti di mare che abbia mai visitato.'),
('ian.roberts', '2024-07-28 14:00:00', 14, 5, 'Ottimo ristorante, cibo eccellente.'),
('ian.roberts', '2024-08-07 19:00:00', 3, 4, 'Buona pizza, ma il servizio è stato lento.'),
('ian.roberts', '2024-08-18 12:00:00', 14, 5, 'Cibo siciliano eccezionale e servizio impeccabile.'),
('isabella.white', '2024-07-12 19:30:00', 17, 5, 'Sushi fresco e di alta qualità. Ottima scelta per una cena elegante.'),
('jack.wilson', '2024-07-08 20:00:00', 5, 4, 'Cucina emiliana saporita, ma c’è stata un po’ di attesa.'),
('jackson.harris', '2024-07-16 13:00:00', 2, 3, 'Buona qualità, ma un po\' caro.'),
('jackson.harris', '2024-07-28 15:00:00', 14, 4, 'Buona cucina e servizio cortese.'),
('jackson.harris', '2024-08-05 12:00:00', 1, 4, 'Ambiente accogliente e ottimo cibo.'),
('jackson.harris', '2024-08-11 11:00:00', 7, 5, 'Ottimo ristorante ligure, piatti eccellenti.'),
('jacob.harris', '2024-07-09 13:00:00', 18, 3, 'Kebab buono ma il locale non era molto pulito.'),
('james.anderson', '2024-07-09 14:00:00', 14, 4, 'Cucina siciliana di qualità, anche se un po’ costosa.'),
('james.johnson', '2024-07-17 18:30:00', 3, 5, 'Pizza autentica napoletana, eccezionale.'),
('james.johnson', '2024-07-29 12:00:00', 15, 4, 'Cucina toscana tradizionale, buon servizio.'),
('james.johnson', '2024-08-08 14:00:00', 4, 4, 'Ottimo cibo, ma il servizio potrebbe migliorare.'),
('james.johnson', '2024-08-13 14:00:00', 9, 4, 'Buon cibo e atmosfera accogliente.'),
('janedoe2', '2024-07-08 18:20:00', 2, 4, 'Ottimi frutti di mare, ma il servizio potrebbe essere migliorato.'),
('johndoe1', '2024-07-10 14:30:00', 1, 5, 'Un’ottima esperienza! Il cibo era delizioso e il servizio impeccabile.'),
('joseph.johnson', '2024-07-17 19:00:00', 3, 4, 'Buona pizza, ma un po\' troppo affollato.'),
('joseph.johnson', '2024-07-29 13:00:00', 15, 5, 'Ristorante eccellente, piatti ben preparati.'),
('joseph.johnson', '2024-08-18 13:00:00', 14, 4, 'Ottima cucina, ma il servizio era lento.'),
('joseph.martin', '2024-07-17 19:30:00', 3, 4, 'Servizio veloce e pizza molto buona.'),
('joseph.martin', '2024-07-29 14:00:00', 15, 4, 'Un’ottima esperienza gastronomica.'),
('joseph.martin', '2024-08-08 15:00:00', 4, 5, 'Un’esperienza culinaria indimenticabile.'),
('joseph.martin', '2024-08-10 12:00:00', 6, 5, 'Cucina siciliana autentica e deliziosa.'),
('karen.jones', '2024-07-18 13:00:00', 4, 5, 'Cibo toscano autentico e atmosfera accogliente.'),
('karen.jones', '2024-07-30 12:00:00', 16, 5, 'Cibo eccezionale e atmosfera elegante.'),
('karen.jones', '2024-08-12 20:00:00', 8, 5, 'Una delle migliori esperienze di sushi.'),
('laura.galli', '2024-07-11 19:00:00', 8, 5, 'Fantastico sushi fresco e varietà di piatti.'),
('leo.hughes', '2024-07-18 14:00:00', 4, 4, 'Ottima cucina, ma i tempi di attesa erano lunghi.'),
('leo.hughes', '2024-07-30 13:00:00', 16, 4, 'Ottima cucina, ma un po\' costoso.'),
('liam.wilson', '2024-07-12 17:00:00', 10, 3, 'Pizza buona ma l’atmosfera del locale potrebbe essere migliorata.'),
('logan.flores', '2024-07-18 15:00:00', 4, 5, 'Un’esperienza culinaria eccellente.'),
('logan.flores', '2024-07-30 14:00:00', 16, 5, 'Esperienza gastronomica fantastica.'),
('logan.flores', '2024-08-19 12:00:00', 15, 5, 'Cucina toscana di alta qualità.'),
('lorenzo.leone', '2024-07-07 19:30:00', 11, 4, 'Ambiente romantico e cucina deliziosa.'),
('luca.verdi', '2024-07-10 15:30:00', 2, 4, 'Ottimo cibo e servizio rapido!'),
('lucas.cox', '2024-07-10 19:30:00', 17, 5, 'Sushi di alta qualità e ambiente elegante.'),
('lucas.johnson', '2024-07-09 19:00:00', 4, 5, 'Eccellente cucina toscana! Piatti gustosi e ambiente accogliente.'),
('lucas.jones', '2024-07-19 12:00:00', 5, 4, 'Piatti ben preparati, ma un po\' costoso.'),
('lucas.jones', '2024-07-31 19:00:00', 17, 5, 'Sushi fresco e ben preparato.'),
('lucas.jones', '2024-08-11 12:00:00', 7, 4, 'Cibo buono, ma il servizio è stato lento.'),
('lucas.jones', '2024-08-21 20:00:00', 17, 5, 'Sushi di alta qualità, un vero piacere.'),
('luigi.rossi', '2024-07-19 13:00:00', 5, 5, 'Cucina emiliana autentica e servizio ottimo.'),
('luigi.rossi', '2024-07-31 19:30:00', 17, 4, 'Buon sushi e ambiente accogliente.'),
('luke.morris', '2024-07-19 14:00:00', 5, 4, 'Atmosfera accogliente e cibo gustoso.'),
('luke.morris', '2024-07-31 20:00:00', 17, 5, 'Sushi di alta qualità, un vero piacere.'),
('luke.morris', '2024-08-17 18:00:00', 13, 5, 'Piatti veneti deliziosi e ben presentati.'),
('luna.price', '2024-07-20 12:00:00', 6, 5, 'Cibo siciliano delizioso e autentico.'),
('luna.price', '2024-08-01 12:00:00', 18, 4, 'Kebab gustoso e ben preparato.'),
('luna.price', '2024-08-20 12:00:00', 16, 5, 'Cibo eccezionale, un ristorante da consigliare.'),
('madison.smith', '2024-07-10 22:30:00', 8, 5, 'Sushi di alta qualità e ambiente raffinato.'),
('marco.conti', '2024-07-08 21:00:00', 12, 3, 'Molto caro per la qualità offerta.'),
('maria.ferraro', '2024-07-09 14:00:00', 6, 4, 'Locale pulito e personale cortese.'),
('mason.miller', '2024-07-08 19:15:00', 12, 4, 'Bella esperienza mediterranea, piatti freschi e ben preparati.'),
('maxwell.reed', '2024-07-20 14:00:00', 6, 4, 'Ottima esperienza gastronomica.'),
('maxwell.reed', '2024-08-01 14:00:00', 18, 4, 'Kebab saporito e servizio veloce.'),
('maxwell.reed', '2024-08-22 13:00:00', 18, 5, 'Ottimo kebab, sicuramente tornerò.'),
('mia.cook', '2024-07-21 11:00:00', 7, 4, 'Buona cucina ligure, servizio eccellente.'),
('mia.cook', '2024-08-02 18:00:00', 19, 5, 'Ramen eccellente e servizio veloce.'),
('mia.cook', '2024-08-05 13:00:00', 1, 5, 'Una vera delizia, ci tornerò sicuramente.'),
('mia.jones', '2024-07-10 15:30:00', 4, 5, 'Ristorante toscano eccellente con cibo delizioso.'),
('mia.martinez', '2024-07-10 21:00:00', 13, 5, 'Una vera trattoria veneta con piatti deliziosi e autentici.'),
('michael.thompson', '2024-07-21 12:00:00', 7, 5, 'Il miglior ristorante ligure che abbia mai visitato.'),
('michael.thompson', '2024-08-02 18:30:00', 19, 4, 'Buon ramen e atmosfera accogliente.'),
('michael.thompson', '2024-08-10 13:00:00', 6, 4, 'Buona cucina, ma il servizio era lento.'),
('michael.thompson', '2024-08-19 13:00:00', 15, 4, 'Cibo buono, ma il servizio era un po\' lento.'),
('michael.young', '2024-07-09 19:00:00', 7, 3, 'Cibo buono ma senza particolari pregi.'),
('mike.smith', '2024-07-11 20:15:00', 3, 3, 'Pizza buona ma un po\' fredda.'),
('nathan.brown', '2024-07-21 13:00:00', 7, 3, 'Buon cibo, ma il posto è un po\' rumoroso.'),
('nathan.brown', '2024-08-02 19:00:00', 19, 5, 'Ramen di alta qualità, molto soddisfatto.'),
('nathan.brown', '2024-08-21 19:30:00', 17, 4, 'Buon sushi, ma il locale è affollato.'),
('nathan.ross', '2024-07-12 14:00:00', 13, 5, 'Autentica trattoria veneta con cibo delizioso.'),
('nina.smith', '2024-07-22 19:00:00', 8, 5, 'Sushi freschissimo e ben preparato.'),
('nina.smith', '2024-08-14 18:30:00', 10, 4, 'Pizza buona, ma il locale era troppo affollato.'),
('nina.smith', '2024-08-23 18:00:00', 19, 5, 'Ramen eccellente, ottima esperienza.'),
('noah.garcia', '2024-07-07 20:00:00', 1, 4, 'Trattoria accogliente con ottimo cibo italiano.'),
('oliver.brown', '2024-07-12 13:45:00', 6, 5, 'Autentica trattoria siciliana. Cibo fantastico e personale cordiale.'),
('olivia.bennett', '2024-07-22 19:30:00', 8, 4, 'Buon sushi, ma il servizio potrebbe migliorare.'),
('olivia.perez', '2024-07-22 20:00:00', 8, 5, 'Un’esperienza fantastica, sushi di alta qualità.'),
('olivia.perez', '2024-08-17 18:30:00', 13, 4, 'Cibo eccellente, ma un po\' costoso.'),
('olivia.thomas', '2024-07-11 18:00:00', 15, 5, 'Atmosfera toscana perfetta con cibo eccezionale. Molto soddisfatto.'),
('paolo.bianchi', '2024-07-08 20:00:00', 4, 4, 'Cucina tradizionale toscana, consigliato!'),
('paul.miller', '2024-07-23 12:00:00', 9, 4, 'Ottima cucina piemontese e atmosfera accogliente.'),
('paul.miller', '2024-08-18 14:00:00', 14, 4, 'Cibo buono e atmosfera accogliente.'),
('quinn.taylor', '2024-07-23 13:00:00', 9, 5, 'Cibo eccezionale, un must per gli amanti della cucina piemontese.'),
('rachel.adams', '2024-07-23 14:00:00', 9, 4, 'Buon cibo e servizio cortese.'),
('rachel.adams', '2024-08-05 14:00:00', 1, 3, 'Cibo buono, ma il servizio era lento.'),
('rachel.adams', '2024-08-14 19:00:00', 10, 5, 'Un’esperienza culinaria eccellente.'),
('rachel.adams', '2024-08-20 13:00:00', 16, 4, 'Ottima cucina, ma il prezzo è elevato.'),
('rita.pellegrini', '2024-07-10 13:15:00', 12, 3, 'Pizze non eccezionali ma buon rapporto qualità/prezzo.'),
('roberto.marini', '2024-07-10 20:00:00', 8, 2, 'Sushi scadente, non tornerei.'),
('ryan.lewis', '2024-07-11 13:00:00', 9, 4, 'Ottimo cibo piemontese, ma l’ambiente potrebbe essere più accogliente.'),
('samuel.foster', '2024-07-24 18:00:00', 10, 4, 'Pizza buona e ben preparata.'),
('sara.longo', '2024-07-08 20:45:00', 9, 5, 'Piatti piemontesi eccellenti, da provare!'),
('serena.conti', '2024-07-11 18:30:00', 15, 5, 'Servizio impeccabile e piatti raffinati.'),
('simone.rizzo', '2024-07-10 18:30:00', 5, 2, 'Servizio lento e piatti poco gustosi.'),
('sophia.martinez', '2024-07-09 16:00:00', 2, 5, 'Frutti di mare freschissimi e servizio veloce.'),
('sophie.jones', '2024-07-11 15:00:00', 7, 3, 'Ristorante nella media. Il cibo è ok, ma niente di speciale.'),
('sophie.murphy', '2024-07-24 18:30:00', 10, 5, 'Pizza eccezionale, ritornerò sicuramente.'),
('sophie.murphy', '2024-08-06 11:30:00', 2, 5, 'Pesce freschissimo e ben preparato.'),
('sophie.murphy', '2024-08-09 13:00:00', 5, 5, 'Ottima cucina e atmosfera accogliente.'),
('sophie.murphy', '2024-08-16 12:00:00', 12, 5, 'Piatti deliziosi e vista panoramica.'),
('sophie.murphy', '2024-08-20 14:00:00', 16, 5, 'Un’esperienza culinaria fantastica.'),
('tina.martinez', '2024-07-24 19:00:00', 10, 4, 'Pizza deliziosa, ma il locale era affollato.'),
('tina.martinez', '2024-08-06 12:00:00', 2, 4, 'Ottimo cibo, ma il posto è affollato.'),
('tina.martinez', '2024-08-14 18:00:00', 10, 5, 'Pizza eccellente, uno dei migliori di Milano.'),
('tina.martinez', '2024-08-23 18:30:00', 19, 4, 'Buon ramen e servizio accogliente.'),
('ursula.white', '2024-07-25 12:00:00', 11, 5, 'Cibo casalingo eccellente e servizio accogliente.'),
('victor.green', '2024-07-25 13:00:00', 11, 4, 'Cucina tradizionale molto buona.'),
('victor.green', '2024-08-06 13:00:00', 2, 4, 'Servizio rapido e cibo di qualità.'),
('victor.green', '2024-08-11 13:00:00', 7, 5, 'Una vera esperienza culinaria ligure.'),
('victor.green', '2024-08-15 14:00:00', 11, 5, 'Una vera delizia, ci tornerò sicuramente.'),
('wendy.davis', '2024-07-25 14:00:00', 11, 5, 'Una vera delizia, consigliato a tutti.'),
('wendy.davis', '2024-08-19 14:00:00', 15, 4, 'Ottima esperienza gastronomica.'),
('william.jackson', '2024-07-10 20:00:00', 16, 4, 'Ristorante italiano con piatti saporiti e servizio amichevole.'),
('xander.harris', '2024-07-26 12:00:00', 12, 4, 'Ottima combinazione di piatti di mare e montagna.'),
('xander.harris', '2024-08-15 12:00:00', 11, 5, 'Cibo casalingo eccellente e servizio caloroso.'),
('yara.james', '2024-07-26 13:00:00', 12, 5, 'Cibo eccellente e vista spettacolare.'),
('yara.james', '2024-08-10 14:00:00', 6, 4, 'Ottimo cibo siciliano e ambiente accogliente.'),
('yara.james', '2024-08-16 13:00:00', 12, 4, 'Buona combinazione di piatti, ma il servizio era lento.'),
('zachary.garcia', '2024-07-26 14:00:00', 12, 4, 'Piatti gustosi e servizio cortese.'),
('zachary.garcia', '2024-08-16 14:00:00', 12, 4, 'Cibo ottimo e atmosfera piacevole.'),
('zoe.green', '2024-08-07 19:30:00', 3, 4, 'Pizza gustosa, ma l’ambiente era troppo rumoroso.'),
('zoe.green', '2024-08-09 14:00:00', 5, 4, 'Cibo gustoso, ma leggermente caro.'),
('zoe.green', '2024-08-13 13:00:00', 9, 4, 'Ottima cucina, ma il servizio potrebbe essere più veloce.'),
('zoe.green', '2024-08-21 19:00:00', 17, 5, 'Sushi fresco e ben preparato.');


-- --------------------------------------------------------

--
-- Struttura della tabella `RISTORANTI`
--

CREATE TABLE `RISTORANTI` (
  `RistoranteID` int(11) NOT NULL,
  `Nome` varchar(100) NOT NULL,
  `OraApertura` time NOT NULL,
  `OraChiusura` time NOT NULL,
  `TipologiaCucina` varchar(100) NOT NULL,
  `IndirizzoVia` varchar(100) NOT NULL,
  `IndirizzoCivico` varchar(20) NOT NULL,
  `IndirizzoCittà` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dump dei dati per la tabella `RISTORANTI`
--

INSERT INTO `RISTORANTI` (`RistoranteID`, `Nome`, `OraApertura`, `OraChiusura`, `TipologiaCucina`, `IndirizzoVia`, `IndirizzoCivico`, `IndirizzoCittà`) VALUES
(1, 'Trattoria da Mario', '12:00:00', '22:00:00', 'Italiana', 'Via Roma', '10', 'Roma'),
(2, 'Ristorante del Porto', '11:30:00', '23:00:00', 'Frutti di mare', 'Lungomare Vesuvio', '1', 'Napoli'),
(3, 'La Pizzeria di Napoli', '18:00:00', '23:30:00', 'Pizza', 'Via dei Mille', '5', 'Napoli'),
(4, 'Ristorante La Florentina', '13:00:00', '21:30:00', 'Toscana', 'Via dei Calzaiuoli', '25', 'Firenze'),
(5, 'Osteria dei Sapori', '12:30:00', '22:30:00', 'Emiliana', 'Via delle Belle Arti', '8', 'Bologna'),
(6, 'Trattoria Siciliana', '12:00:00', '23:00:00', 'Siciliana', 'Via Vittorio Emanuele', '15', 'Palermo'),
(7, 'Ristorante da Genova', '11:00:00', '22:00:00', 'Ligure', 'Piazza De Ferrari', '3', 'Genova'),
(8, 'Sushi Master', '19:00:00', '00:00:00', 'Giapponese', 'Corso Italia', '15', 'Milano'),
(9, 'La Taverna del Piemonte', '12:30:00', '22:30:00', 'Piemontese', 'Via Roma', '12', 'Torino'),
(10, 'Pizzeria di Franco', '18:00:00', '23:30:00', 'Pizza', 'Via Cavour', '30', 'Milano'),
(11, 'Ristorante della Nonna', '12:00:00', '22:00:00', 'Italiana', 'Via Garibaldi', '18', 'Roma'),
(12, 'Ristorante Mare e Monti', '11:30:00', '23:00:00', 'Mediterranea', 'Lungomare Adriatico', '2', 'Bari'),
(13, 'La Trattoria di Venezia', '18:00:00', '23:30:00', 'Veneta', 'Fondamenta della Misericordia', '7', 'Venezia'),
(14, 'Ristorante del Sud', '13:00:00', '21:30:00', 'Siciliana', 'Via Etna', '10', 'Catania'),
(15, 'Osteria Toscanella', '12:30:00', '22:30:00', 'Toscana', 'Via dei Neri', '8', 'Firenze'),
(16, 'Ristorante dei Sapori', '12:00:00', '23:00:00', 'Italiana', 'Via Veneto', '5', 'Roma'),
(17, 'Sushi Time', '19:00:00', '00:00:00', 'Giapponese', 'Corso Umberto I', '20', 'Milano'),
(18, 'Eccomi Kebab', '12:30:00', '22:30:00', 'Turca', 'Via Garibaldi', '10', 'Torino'),
(19, 'Ramen Bar', '18:00:00', '23:30:00', 'Giapponese', 'Via Roma', '15', 'Napoli');
-- --------------------------------------------------------

--
-- Struttura della tabella `UTENTI`
--

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
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dump dei dati per la tabella `UTENTI`
--

INSERT INTO `UTENTI` (`Username`, `Nome`, `Cognome`, `Password`, `IndirizzoVia`, `IndirizzoCivico`, `IndirizzoCittà`, `Ruolo`, `Balance`) VALUES
('admin', 'Alessandro', 'Valmori', '123', 'Via Roma', '10', 'Roma', 'Amministratore', 1000.00),
('aiden.sanders', 'Aiden', 'Sanders', 'sanders456', 'Palm Street', '4040', 'Oakland', 'Consumatore', 280.00),
('amanda.jones', 'Amanda', 'Jones', 'jones432', 'Elm Street', '21', 'Florence', 'Consumatore', 320.00),
('amelia.hall', 'Amelia', 'Hall', 'hall567', 'Pecan Drive', '2525', 'Milwaukee', 'Consumatore', 250.00),
('andrea.gallo', 'Andrea', 'Gallo', 'gallo567', 'Via Mazzini', '12', 'Bologna', 'Consumatore', 320.00),
('andrea.vitali', 'Andrea', 'Vitali', 'vitali789', 'Via Garibaldi', '6', 'Catania', 'Consumatore', 410.00),
('anna.rossi', 'Anna', 'Rossi', 'rossi890', 'Corso Vittorio Emanuele', '45', 'Roma', 'Consumatore', 180.00),
('arabella.ward', 'Arabella', 'Ward', 'ward789', 'Cypress Street', '4141', 'Minneapolis', 'Consumatore', 270.00),
('ava.davis', 'Ava', 'Davis', 'davis789', 'Moss Road', '1919', 'Washington', 'Consumatore', 340.00),
('ava.moore', 'Ava', 'Moore', 'moore890', 'Grove Place', '1111', 'Austin', 'Consumatore', 250.00),
('ava.perez', 'Ava', 'Perez', 'perez789', 'Orchard Avenue', '3535', 'Omaha', 'Consumatore', 310.00),
('benjamin.smith', 'Benjamin', 'Smith', 'smith123', 'Maple Avenue', '5', 'New York', 'Consumatore', 410.00),
('benjamin.taylor', 'Benjamin', 'Taylor', 'taylor678', 'Cherry Lane', '808', 'San Diego', 'Consumatore', 470.00),
('carla.martin', 'Carla', 'Martin', 'martin234', 'Oak Road', '11', 'Paris', 'Consumatore', 180.00),
('chloe.morris', 'Chloe', 'Morris', 'morris123', 'Cypress Avenue', '5151', 'Reno', 'Consumatore', 310.00),
('chris.carter', 'Chris', 'Carter', 'carter123', 'Laurel Avenue', '3030', 'Kansas City', 'Consumatore', 310.00),
('daniel.garcia', 'Daniel', 'Garcia', 'garcia345', 'Pine Lane', '8', 'Madrid', 'Consumatore', 250.00),
('daniel.morris', 'Daniel', 'Morris', 'morris456', 'Alder Drive', '3434', 'Colorado Springs', 'Consumatore', 350.00),
('eleonora.romano', 'Eleonora', 'Romano', 'romano321', 'Via Garibaldi', '25', 'Napoli', 'Consumatore', 250.00),
('elijah.martinez', 'Elijah', 'Martinez', 'martinez123', 'Holly Road', '4242', 'Wichita', 'Consumatore', 320.00),
('elisa.gatti', 'Elisa', 'Gatti', 'gatti567', 'Via Garibaldi', '9', 'Roma', 'Consumatore', 19809.50),
('elizabeth.evans', 'Elizabeth', 'Evans', 'evans456', 'Laurel Street', '5555', 'Newport News', 'Consumatore', 350.00),
('ella.baker', 'Ella', 'Baker', 'baker456', 'Maple Street', '3131', 'Mesa', 'Consumatore', 320.00),
('ella.brown', 'Ella', 'Brown', 'brown678', 'Sunset Boulevard', '15', 'Los Angeles', 'Consumatore', 380.00),
('ella.lee', 'Ella', 'Lee', 'lee456', 'Birch Lane', '4949', 'Anaheim', 'Consumatore', 300.00),
('ella.martin', 'Ella', 'Martin', 'martin234', 'Willow Street', '909', 'Dallas', 'Consumatore', 360.00),
('emily.james', 'Emily', 'James', 'james123', 'Cedar Street', '3333', 'Atlanta', 'Consumatore', 330.00),
('emily.williams', 'Emily', 'Williams', 'williams654', 'Maple Drive', '505', 'Phoenix', 'Consumatore', 390.00),
('ethan.rodriguez', 'Ethan', 'Rodriguez', 'rodriguez678', 'Fern Drive', '2222', 'Detroit', 'Consumatore', 350.00),
('fabio.gallo', 'Fabio', 'Gallo', 'gallo123', 'Corso Vittorio Emanuele', '25', 'Palermo', 'Consumatore', 350.00),
('felix.lee', 'Felix', 'Lee', 'lee901', 'River Road', '19', 'Sydney', 'Consumatore', 350.00),
('francesca.bianchi', 'Francesca', 'Bianchi', 'bianchi456', 'Corso Italia', '15', 'Torino', 'Consumatore', 750.00),
('giovanna.romano', 'Giovanna', 'Romano', 'romano789', 'Via Dante Alighieri', '14', 'Firenze', 'Consumatore', 270.00),
('giulia.marini', 'Giulia', 'Marini', 'marini890', 'Via Veneto', '8', 'Palermo', 'Consumatore', 150.00),
('giuseppe.ferrari', 'Giuseppe', 'Ferrari', 'ferrari789', 'Via Dante', '30', 'Firenze', 'Fattorino', 0.00),
('grace.williams', 'Grace', 'Williams', 'williams456', 'Greenwich Street', '10', 'London', 'Consumatore', 750.00),
('hannah.chen', 'Hannah', 'Chen', 'chen567', 'Baker Street', '7', 'Beijing', 'Consumatore', 270.00),
('hannah.kim', 'Hannah', 'Kim', 'kim789', 'Cottonwood Road', '2929', 'Sacramento', 'Consumatore', 330.00),
('harper.white', 'Harper', 'White', 'white789', 'Maple Lane', '4747', 'Honolulu', 'Consumatore', 310.00),
('ian.roberts', 'Ian', 'Roberts', 'roberts678', 'Victoria Street', '25', 'Toronto', 'Consumatore', 150.00),
('isabella.white', 'Isabella', 'White', 'white123', 'Redwood Avenue', '1717', 'Seattle', 'Consumatore', 310.00),
('jack.wilson', 'Jack', 'Wilson', 'wilson234', 'Sycamore Road', '2424', 'Baltimore', 'Consumatore', 330.00),
('jackson.harris', 'Jackson', 'Harris', 'harris789', 'Hickory Lane', '4444', 'Arlington', 'Consumatore', 310.00),
('jacob.harris', 'Jacob', 'Harris', 'harris456', 'Pinecrest Drive', '1818', 'Denver', 'Consumatore', 330.00),
('james.anderson', 'James', 'Anderson', 'anderson789', 'Spruce Road', '1414', 'Fort Worth', 'Consumatore', 310.00),
('james.johnson', 'James', 'Johnson', 'johnson789', 'King Street', '4', 'Berlin', 'Fattorino', 0.00),
('janedoe2', 'Jane', 'Doe', 'doe5678', 'Oak Avenue', '202', 'Los Angeles', 'Consumatore', 420.00),
('johndoe1', 'John', 'Doe', 'doe1234', 'Main Street', '101', 'New York', 'Consumatore', 350.00),
('joseph.johnson', 'Joseph', 'Johnson', 'johnson456', 'Pine Street', '4646', 'Tampa', 'Consumatore', 330.00),
('joseph.martin', 'Joseph', 'Martin', 'martin456', 'Palm Avenue', '5252', 'Modesto', 'Consumatore', 340.00),
('karen.jones', 'Karen', 'Jones', 'jones345', 'High Street', '30', 'Stockholm', 'Consumatore', 200.00),
('laura.galli', 'Laura', 'Galli', 'galli567', 'Corso Vittorio Emanuele', '20', 'Catania', 'Consumatore', 200.00),
('leo.hughes', 'Leo', 'Hughes', 'hughes789', 'Myrtle Avenue', '3838', 'Cleveland', 'Consumatore', 320.00),
('liam.wilson', 'Liam', 'Wilson', 'wilson567', 'Hickory Avenue', '1010', 'San Jose', 'Consumatore', 400.00),
('logan.flores', 'Logan', 'Flores', 'flores123', 'Cedar Street', '4848', 'Aurora', 'Consumatore', 350.00),
('lorenzo.leone', 'Lorenzo', 'Leone', 'leone567', 'Via Garibaldi', '13', 'Roma', 'Consumatore', 320.00),
('luca.verdi', 'Luca', 'Verdi', 'verdi123', 'Via Garibaldi', '5', 'Milano', 'Consumatore', 500.00),
('lucas.cox', 'Lucas', 'Cox', 'cox123', 'Birch Street', '3636', 'Raleigh', 'Consumatore', 290.00),
('lucas.johnson', 'Lucas', 'Johnson', 'johnson321', 'Elm Street', '404', 'Houston', 'Consumatore', 280.00),
('lucas.jones', 'Lucas', 'Jones', 'jones789', 'Redwood Avenue', '5353', 'Fayetteville', 'Consumatore', 280.00),
('luigi.rossi', 'Luigi', 'Rossi', 'rossi456', 'Via Garibaldi', '22', 'Torino', 'Consumatore', 400.00),
('luke.morris', 'Luke', 'Morris', 'morris123', 'Rose Avenue', '22', 'Vienna', 'Consumatore', 320.00),
('luna.price', 'Luna', 'Price', 'price123', 'Oak Street', '4545', 'Bakersfield', 'Consumatore', 320.00),
('madison.smith', 'Madison', 'Smith', 'smith123', 'Willowbrook Street', '2727', 'Tucson', 'Consumatore', 300.00),
('marco.conti', 'Marco', 'Conti', 'conti234', 'Via Roma', '3', 'Genova', 'Consumatore', 430.00),
('maria.ferraro', 'Maria', 'Ferraro', 'ferraro123', 'Via Verdi', '7', 'Napoli', 'Fattorino', 0.00),
('mason.miller', 'Mason', 'Miller', 'miller123', 'Poplar Court', '1212', 'San Francisco', 'Consumatore', 330.00),
('maxwell.reed', 'Maxwell', 'Reed', 'reed789', 'Oakwood Street', '5050', 'Corpus Christi', 'Consumatore', 330.00),
('mia.cook', 'Mia', 'Cook', 'cook456', 'Walnut Lane', '4343', 'New Orleans', 'Consumatore', 340.00),
('mia.jones', 'Mia', 'Jones', 'jones901', 'Aspen Avenue', '2323', 'Nashville', 'Consumatore', 370.00),
('mia.martinez', 'Mia', 'Martinez', 'martinez456', 'Laurel Crescent', '1313', 'Columbus', 'Consumatore', 290.00),
('michael.thompson', 'Michael', 'Thompson', 'thompson456', 'Willow Drive', '3', 'Rome', 'Consumatore', 500.00),
('michael.young', 'Michael', 'Young', 'young890', 'Jasmine Lane', '2626', 'Albuquerque', 'Consumatore', 340.00),
('mike.smith', 'Mike', 'Smith', 'smith910', 'Pine Road', '303', 'Chicago', 'Consumatore', 250.00),
('nathan.brown', 'Nathan', 'Brown', 'brown789', 'Maple Road', '5656', 'Little Rock', 'Consumatore', 310.00),
('nathan.ross', 'Nathan', 'Ross', 'ross789', 'Juniper Lane', '3232', 'Virginia Beach', 'Consumatore', 340.00),
('nina.smith', 'Nina', 'Smith', 'smith789', 'Cherry Lane', '18', 'Budapest', 'Consumatore', 400.00),
('noah.garcia', 'Noah', 'Garcia', 'garcia012', 'Bamboo Blvd', '2020', 'Boston', 'Consumatore', 300.00),
('oliver.brown', 'Oliver', 'Brown', 'brown987', 'Cedar Lane', '606', 'Philadelphia', 'Consumatore', 420.00),
('olivia.bennett', 'Olivia', 'Bennett', 'bennett123', 'Eucalyptus Drive', '3939', 'Tulsa', 'Consumatore', 350.00),
('olivia.perez', 'Olivia', 'Perez', 'perez123', 'Birch Street', '15', 'San Francisco', 'Consumatore', 430.00),
('olivia.thomas', 'Olivia', 'Thomas', 'thomas234', 'Cypress Lane', '1515', 'Indianapolis', 'Consumatore', 270.00),
('paolo.bianchi', 'Paolo', 'Bianchi', 'bianchi234', 'Corso Italia', '30', 'Milano', 'Consumatore', 520.00),
('paul.miller', 'Paul', 'Miller', 'miller234', 'Cedar Lane', '5', 'Prague', 'Fattorino', 0.00),
('quinn.taylor', 'Quinn', 'Taylor', 'taylor567', 'Harrison Avenue', '22', 'Athens', 'Consumatore', 200.00),
('rachel.adams', 'Rachel', 'Adams', 'adams678', 'Sunrise Drive', '12', 'Lisbon', 'Consumatore', 320.00),
('rita.pellegrini', 'Rita', 'Pellegrini', 'pellegrini456', 'Via Veneto', '11', 'Genova', 'Consumatore', 240.00),
('roberto.marini', 'Roberto', 'Marini', 'marini345', 'Via Mazzini', '5', 'Bologna', 'Consumatore', 290.00),
('ryan.lewis', 'Ryan', 'Lewis', 'lewis456', 'Elmwood Avenue', '2828', 'Fresno', 'Consumatore', 320.00),
('samuel.foster', 'Samuel', 'Foster', 'foster345', 'Elm Street', '25', 'Brussels', 'Consumatore', 290.00),
('sara.longo', 'Sara', 'Longo', 'longo234', 'Corso Italia', '40', 'Milano', 'Consumatore', 480.00),
('serena.conti', 'Serena', 'Conti', 'conti678', 'Via Roma', '17', 'Napoli', 'Consumatore', 210.00),
('simone.rizzo', 'Simone', 'Rizzo', 'rizzo567', 'Via Garibaldi', '18', 'Milano', 'Consumatore', 300.00),
('sir', 'ale', 'valmo', '123', 'Zio', '2', 'Pera', 'Fattorino', 0.00),
('sophia.martinez', 'Sophia', 'Martinez', 'martinez345', 'Fawn Lane', '2121', 'El Paso', 'Consumatore', 280.00),
('sophie.jones', 'Sophie', 'Jones', 'jones345', 'Birch Blvd', '707', 'San Antonio', 'Consumatore', 310.00),
('sophie.murphy', 'Sophie', 'Murphy', 'murphy456', 'Dogwood Lane', '3737', 'Miami', 'Consumatore', 300.00),
('tina.martinez', 'Tina', 'Martinez', 'martinez456', 'Miller Road', '8', 'Oslo', 'Consumatore', 310.00),
('ursula.white', 'Ursula', 'White', 'white567', 'Maple Street', '18', 'Zurich', 'Consumatore', 350.00),
('victor.green', 'Victor', 'Green', 'green678', 'Spring Street', '6', 'Copenhagen', 'Consumatore', 260.00),
('wendy.davis', 'Wendy', 'Davis', 'davis789', 'Peach Drive', '9', 'Rome', 'Consumatore', 270.00),
('william.jackson', 'William', 'Jackson', 'jackson678', 'Oakwood Street', '1616', 'Charlotte', 'Consumatore', 320.00),
('xander.harris', 'Xander', 'Harris', 'harris123', 'Mountain View', '13', 'Hong Kong', 'Consumatore', 340.00),
('yara.james', 'Yara', 'James', 'james456', 'Forest Road', '17', 'Dublin', 'Consumatore', 290.00),
('zachary.garcia', 'Zachary', 'Garcia', 'garcia789', 'Sunset Drive', '22', 'Edinburgh', 'Consumatore', 310.00),
('zoe.green', 'Zoe', 'Green', 'green123', 'Spring Lane', '5454', 'Winston-Salem', 'Consumatore', 270.00);

--
-- Indici per le tabelle scaricate
--

--
-- Indici per le tabelle `ASSEGNAZIONI_CONSEGNE`
--
ALTER TABLE `ASSEGNAZIONI_CONSEGNE`
  ADD PRIMARY KEY (`OrdineID`,`FattorinoID`),
  ADD KEY `FattorinoID` (`FattorinoID`);

--
-- Indici per le tabelle `DETTAGLI_ORDINI`
--
ALTER TABLE `DETTAGLI_ORDINI`
  ADD PRIMARY KEY (`OrdineID`,`ElementoMenuID`,`RistoranteID`),
  ADD KEY `ElementoMenuID` (`ElementoMenuID`,`RistoranteID`);

--
-- Indici per le tabelle `ELEMENTI`
--
ALTER TABLE `ELEMENTI`
  ADD PRIMARY KEY (`ElementoMenuID`,`RistoranteID`),
  ADD KEY `RistoranteID` (`RistoranteID`);

--

--
-- Indici per le tabelle `RECENSIONI`
--
ALTER TABLE `RECENSIONI`
  ADD PRIMARY KEY (`Username`,`DataOra`,`RistoranteID`),
  ADD KEY `RistoranteID` (`RistoranteID`);

--
-- Indici per le tabelle `RISTORANTI`
--
ALTER TABLE `RISTORANTI`
  ADD PRIMARY KEY (`RistoranteID`);

--
-- Indici per le tabelle `UTENTI`
--
ALTER TABLE `UTENTI`
  ADD PRIMARY KEY (`Username`);

--
-- AUTO_INCREMENT per le tabelle scaricate
--

--
-- AUTO_INCREMENT per la tabella `ORDINI`
--
ALTER TABLE `ORDINI`
  MODIFY `OrdineID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=21;

--
-- AUTO_INCREMENT per la tabella `RISTORANTI`
--
ALTER TABLE `RISTORANTI`
  MODIFY `RistoranteID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=20;

--
-- Limiti per le tabelle scaricate
--

--
-- Limiti per la tabella `ASSEGNAZIONI_CONSEGNE`
--
ALTER TABLE `ASSEGNAZIONI_CONSEGNE`
  ADD CONSTRAINT `ASSEGNAZIONI_CONSEGNE_ibfk_1` FOREIGN KEY (`OrdineID`) REFERENCES `ORDINI` (`OrdineID`),
  ADD CONSTRAINT `ASSEGNAZIONI_CONSEGNE_ibfk_2` FOREIGN KEY (`FattorinoID`) REFERENCES `UTENTI` (`Username`);

--
-- Limiti per la tabella `DETTAGLI_ORDINI`
--
ALTER TABLE `DETTAGLI_ORDINI`
  ADD CONSTRAINT `DETTAGLI_ORDINI_ibfk_1` FOREIGN KEY (`OrdineID`) REFERENCES `ORDINI` (`OrdineID`),
  ADD CONSTRAINT `DETTAGLI_ORDINI_ibfk_2` FOREIGN KEY (`ElementoMenuID`,`RistoranteID`) REFERENCES `ELEMENTI` (`ElementoMenuID`, `RistoranteID`);

--
-- Limiti per la tabella `ELEMENTI`
--
ALTER TABLE `ELEMENTI`
  ADD CONSTRAINT `ELEMENTI_ibfk_1` FOREIGN KEY (`RistoranteID`) REFERENCES `RISTORANTI` (`RistoranteID`);

--
-- Limiti per la tabella `ORDINI`
--
ALTER TABLE `ORDINI`
  ADD CONSTRAINT `ORDINI_ibfk_1` FOREIGN KEY (`Username`) REFERENCES `UTENTI` (`Username`),
  ADD CONSTRAINT `ORDINI_ibfk_2` FOREIGN KEY (`RistoranteID`) REFERENCES `RISTORANTI` (`RistoranteID`);

--
-- Limiti per la tabella `RECENSIONI`
--
ALTER TABLE `RECENSIONI`
  ADD CONSTRAINT `RECENSIONI_ibfk_1` FOREIGN KEY (`Username`) REFERENCES `UTENTI` (`Username`),
  ADD CONSTRAINT `RECENSIONI_ibfk_2` FOREIGN KEY (`RistoranteID`) REFERENCES `RISTORANTI` (`RistoranteID`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*DELIVERY DATABASE4*/

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

CREATE TABLE `ASSEGNAZIONI_CONSEGNE` (
  `OrdineID` int(11) NOT NULL,
  `FattorinoID` varchar(50) NOT NULL,
  `DataOraAssegnazione` datetime DEFAULT NULL,
  `DataOraConsegna` datetime DEFAULT NULL
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
  `DataOra` datetime NOT NULL,
  `Username` varchar(50) NOT NULL,
  `RistoranteID` int(11) NOT NULL,
  `Stato` enum('Stallo', 'Consegna', 'Consegnato') NOT NULL DEFAULT 'Stallo',
  PRIMARY KEY (`OrdineID`),
  UNIQUE KEY `UniqueOrder` (`DataOra`, `Username`, `RistoranteID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;


--
-- Dump dei dati per la tabella `ORDINI`
--

INSERT INTO `ORDINI` (`OrdineID`, `DataOra`, `Username`, `RistoranteID`, `Stato`) VALUES
(1, '2024-07-10 12:30:00', 'luca.verdi', 2, 'Stallo'),
(2, '2024-07-09 19:00:00', 'francesca.bianchi', 1, 'Consegna'),
(3, '2024-07-11 13:45:00', 'andrea.gallo', 3, 'Stallo'),
(4, '2024-07-08 20:15:00', 'paolo.bianchi', 4, 'Consegna'),
(5, '2024-07-07 21:30:00', 'giuseppe.ferrari', 2, 'Consegnato'),
(6, '2024-07-10 18:00:00', 'simone.rizzo', 5, 'Stallo'),
(7, '2024-07-09 14:30:00', 'maria.ferraro', 6, 'Consegna'),
(8, '2024-07-11 19:30:00', 'laura.galli', 8, 'Stallo'),
(9, '2024-07-10 21:00:00', 'giovanna.romano', 10, 'Stallo'),
(10, '2024-07-09 12:00:00', 'andrea.vitali', 6, 'Consegna'),
(11, '2024-07-08 20:30:00', 'sara.longo', 9, 'Stallo'),
(12, '2024-07-07 19:15:00', 'lorenzo.leone', 11, 'Consegna'),
(13, '2024-07-10 13:45:00', 'rita.pellegrini', 12, 'Stallo'),
(14, '2024-07-11 14:00:00', 'fabio.gallo', 13, 'Consegna'),
(15, '2024-07-09 22:15:00', 'elisa.gatti', 14, 'Stallo'),
(16, '2024-07-10 20:30:00', 'roberto.marini', 8, 'Consegna'),
(17, '2024-07-11 18:45:00', 'serena.conti', 15, 'Stallo'),
(18, '2024-07-09 16:30:00', 'giulia.marini', 10, 'Stallo'),
(19, '2024-07-08 21:15:00', 'marco.conti', 12, 'Consegna'),
(20, '2024-07-07 19:30:00', 'giulia.marini', 2, 'Stallo');

-- --------------------------------------------------------

--
-- Struttura della tabella `RECENSIONI`
--

CREATE TABLE `RECENSIONI` (
  `Username` varchar(50) NOT NULL,
  `DataOra` datetime NOT NULL,
  `RistoranteID` int(11) NOT NULL,
  `Voto` int(11) NOT NULL CHECK (`Voto` between 1 and 5),
  `Commento` text DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dump dei dati per la tabella `RECENSIONI`
--

INSERT INTO `RECENSIONI` (`Username`, `DataOra`, `RistoranteID`, `Voto`, `Commento`) VALUES
('andrea.gallo', '2024-07-11 13:00:00', 3, 3, 'Pizza buona ma un po\' fredda.'),
('andrea.vitali', '2024-07-09 12:45:00', 6, 4, 'Piatti siciliani autentici, prezzi giusti.'),
('elisa.gatti', '2024-07-09 22:00:00', 14, 4, 'Atmosfera accogliente e buona selezione di vini.'),
('fabio.gallo', '2024-07-11 14:30:00', 13, 5, 'Cibo siciliano fresco e gustoso.'),
('francesca.bianchi', '2024-07-09 19:45:00', 1, 5, 'Ambiente accogliente e piatti deliziosi.'),
('giovanna.romano', '2024-07-10 21:30:00', 10, 3, 'Buona pizza ma un po\' rumoroso il locale.'),
('giulia.marini', '2024-07-07 19:00:00', 2, 5, 'Mangiato benissimo, consigliatissimo!'),
('giulia.marini', '2024-07-09 16:45:00', 10, 4, 'Buona pizza e staff gentile.'),
('giuseppe.ferrari', '2024-07-07 21:15:00', 2, 5, 'Molto soddisfatto del pesce fresco.'),
('laura.galli', '2024-07-11 19:00:00', 8, 5, 'Fantastico sushi fresco e varietà di piatti.'),
('lorenzo.leone', '2024-07-07 19:30:00', 11, 4, 'Ambiente romantico e cucina deliziosa.'),
('luca.verdi', '2024-07-10 15:30:00', 2, 4, 'Ottimo cibo e servizio rapido!'),
('marco.conti', '2024-07-08 21:00:00', 12, 3, 'Molto caro per la qualità offerta.'),
('maria.ferraro', '2024-07-09 14:00:00', 6, 4, 'Locale pulito e personale cortese.'),
('paolo.bianchi', '2024-07-08 20:00:00', 4, 4, 'Cucina tradizionale toscana, consigliato!'),
('rita.pellegrini', '2024-07-10 13:15:00', 12, 3, 'Pizze non eccezionali ma buon rapporto qualità/prezzo.'),
('roberto.marini', '2024-07-10 20:00:00', 8, 2, 'Sushi scadente, non tornerei.'),
('sara.longo', '2024-07-08 20:45:00', 9, 5, 'Piatti piemontesi eccellenti, da provare!'),
('serena.conti', '2024-07-11 18:30:00', 15, 5, 'Servizio impeccabile e piatti raffinati.'),
('simone.rizzo', '2024-07-10 18:30:00', 5, 2, 'Servizio lento e piatti poco gustosi.');

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
('andrea.gallo', 'Andrea', 'Gallo', 'gallo567', 'Via Mazzini', '12', 'Bologna', 'Consumatore', 320.00),
('andrea.vitali', 'Andrea', 'Vitali', 'vitali789', 'Via Garibaldi', '6', 'Catania', 'Consumatore', 410.00),
('anna.rossi', 'Anna', 'Rossi', 'rossi890', 'Corso Vittorio Emanuele', '45', 'Roma', 'Consumatore', 180.00),
('eleonora.romano', 'Eleonora', 'Romano', 'romano321', 'Via Garibaldi', '25', 'Napoli', 'Consumatore', 250.00),
('elisa.gatti', 'Elisa', 'Gatti', 'gatti567', 'Via Garibaldi', '9', 'Roma', 'Consumatore', 380.00),
('fabio.gallo', 'Fabio', 'Gallo', 'gallo123', 'Corso Vittorio Emanuele', '25', 'Palermo', 'Consumatore', 350.00),
('francesca.bianchi', 'Francesca', 'Bianchi', 'bianchi456', 'Corso Italia', '15', 'Torino', 'Consumatore', 750.00),
('giovanna.romano', 'Giovanna', 'Romano', 'romano789', 'Via Dante Alighieri', '14', 'Firenze', 'Consumatore', 270.00),
('giulia.marini', 'Giulia', 'Marini', 'marini890', 'Via Veneto', '8', 'Palermo', 'Consumatore', 150.00),
('giuseppe.ferrari', 'Giuseppe', 'Ferrari', 'ferrari789', 'Via Dante', '30', 'Firenze', 'Fattorino', 0.00),
('laura.galli', 'Laura', 'Galli', 'galli567', 'Corso Vittorio Emanuele', '20', 'Catania', 'Consumatore', 200.00),
('lorenzo.leone', 'Lorenzo', 'Leone', 'leone567', 'Via Garibaldi', '13', 'Roma', 'Consumatore', 320.00),
('luca.verdi', 'Luca', 'Verdi', 'verdi123', 'Via Garibaldi', '5', 'Milano', 'Consumatore', 500.00),
('luigi.rossi', 'Luigi', 'Rossi', 'rossi456', 'Via Garibaldi', '22', 'Torino', 'Consumatore', 400.00),
('marco.conti', 'Marco', 'Conti', 'conti234', 'Via Roma', '3', 'Genova', 'Consumatore', 430.00),
('maria.ferraro', 'Maria', 'Ferraro', 'ferraro123', 'Via Verdi', '7', 'Napoli', 'Fattorino', 0.00),
('mario.rossi', 'Mario', 'Rossi', 'password123', 'Via Roma', '10', 'Roma', 'Amministratore', 1000.00),
('paolo.bianchi', 'Paolo', 'Bianchi', 'bianchi234', 'Corso Italia', '30', 'Milano', 'Consumatore', 520.00),
('rita.pellegrini', 'Rita', 'Pellegrini', 'pellegrini456', 'Via Veneto', '11', 'Genova', 'Consumatore', 240.00),
('roberto.marini', 'Roberto', 'Marini', 'marini345', 'Via Mazzini', '5', 'Bologna', 'Consumatore', 290.00),
('sara.longo', 'Sara', 'Longo', 'longo234', 'Corso Italia', '40', 'Milano', 'Consumatore', 480.00),
('serena.conti', 'Serena', 'Conti', 'conti678', 'Via Roma', '17', 'Napoli', 'Consumatore', 210.00),
('simone.rizzo', 'Simone', 'Rizzo', 'rizzo567', 'Via Garibaldi', '18', 'Milano', 'Consumatore', 300.00);
('alessandro.martini', 'Alessandro', 'Martini', 'martini567', 'Via Bellini', '22', 'Napoli', 'Consumatore', 300.00),
('antonella.palma', 'Antonella', 'Palma', 'palma789', 'Via Libertà', '13', 'Torino', 'Consumatore', 420.00),
('beatrice.russo', 'Beatrice', 'Russo', 'russo123', 'Via Torino', '28', 'Firenze', 'Consumatore', 190.00),
('benedetta.ferraro', 'Benedetta', 'Ferraro', 'ferraro456', 'Via Italia', '12', 'Milano', 'Consumatore', 350.00),
('bruno.bartoli', 'Bruno', 'Bartoli', 'bartoli789', 'Corso Umberto', '15', 'Genova', 'Consumatore', 270.00),
('caterina.rossi', 'Caterina', 'Rossi', 'rossi234', 'Via Giuseppe', '6', 'Roma', 'Consumatore', 400.00),
('claudio.galli', 'Claudio', 'Galli', 'galli678', 'Via Alberti', '10', 'Catania', 'Consumatore', 330.00),
('cosimo.palma', 'Cosimo', 'Palma', 'palma890', 'Via Roma', '20', 'Bologna', 'Consumatore', 210.00),
('daniela.rossi', 'Daniela', 'Rossi', 'rossi345', 'Via Magenta', '5', 'Palermo', 'Consumatore', 280.00),
('davide.romano', 'Davide', 'Romano', 'romano678', 'Via Montevideo', '3', 'Torino', 'Consumatore', 490.00),
('elena.conti', 'Elena', 'Conti', 'conti123', 'Corso Francia', '4', 'Napoli', 'Consumatore', 370.00),
('enzo.ferraro', 'Enzo', 'Ferraro', 'ferraro789', 'Via Emilia', '7', 'Milano', 'Consumatore', 300.00),
('fabrizio.rossi', 'Fabrizio', 'Rossi', 'rossi456', 'Via Liguria', '8', 'Genova', 'Consumatore', 220.00),
('filomena.palma', 'Filomena', 'Palma', 'palma567', 'Via Dante', '29', 'Catania', 'Consumatore', 310.00),
('francesco.rossi', 'Francesco', 'Rossi', 'rossi678', 'Via Alessandro', '11', 'Palermo', 'Consumatore', 450.00),
('giovanni.martini', 'Giovanni', 'Martini', 'martini890', 'Via Sant\'Antonio', '5', 'Bologna', 'Consumatore', 290.00),
('giulia.palma', 'Giulia', 'Palma', 'palma678', 'Corso Vittorio', '13', 'Firenze', 'Consumatore', 250.00),
('giuseppe.rossi', 'Giuseppe', 'Rossi', 'rossi789', 'Via Dante', '22', 'Catania', 'Consumatore', 400.00),
('laura.martini', 'Laura', 'Martini', 'martini123', 'Via Roma', '17', 'Genova', 'Consumatore', 310.00),
('leonardo.ferraro', 'Leonardo', 'Ferraro', 'ferraro234', 'Via Giuseppe', '6', 'Milano', 'Consumatore', 350.00),
('lorenzo.palma', 'Lorenzo', 'Palma', 'palma123', 'Via Torino', '14', 'Napoli', 'Consumatore', 290.00),
('luana.rossi', 'Luana', 'Rossi', 'rossi345', 'Via Garibaldi', '25', 'Firenze', 'Consumatore', 200.00),
('marta.romano', 'Marta', 'Romano', 'romano456', 'Via Verdi', '30', 'Torino', 'Consumatore', 230.00),
('matteo.conti', 'Matteo', 'Conti', 'conti567', 'Via Libertà', '8', 'Catania', 'Consumatore', 310.00),
('michele.galli', 'Michele', 'Galli', 'galli678', 'Via Milano', '2', 'Roma', 'Consumatore', 320.00),
('monica.palma', 'Monica', 'Palma', 'palma890', 'Via Roma', '16', 'Genova', 'Consumatore', 350.00),
('natalia.rossi', 'Natalia', 'Rossi', 'rossi567', 'Corso Italia', '7', 'Milano', 'Consumatore', 250.00),
('nicola.martini', 'Nicola', 'Martini', 'martini234', 'Via Magenta', '9', 'Napoli', 'Consumatore', 420.00),
('orazio.rossi', 'Orazio', 'Rossi', 'rossi890', 'Corso Umberto', '11', 'Catania', 'Consumatore', 370.00),
('paola.ferraro', 'Paola', 'Ferraro', 'ferraro678', 'Via Dante', '22', 'Bologna', 'Consumatore', 330.00),
('pietro.romano', 'Pietro', 'Romano', 'romano234', 'Via dei Fiori', '6', 'Firenze', 'Consumatore', 280.00),
('raffaella.conti', 'Raffaella', 'Conti', 'conti678', 'Corso Italia', '18', 'Genova', 'Consumatore', 270.00),
('roberto.martini', 'Roberto', 'Martini', 'martini456', 'Via Verdi', '27', 'Milano', 'Consumatore', 320.00),
('romina.palma', 'Romina', 'Palma', 'palma456', 'Via Garibaldi', '19', 'Torino', 'Consumatore', 290.00),
('samuele.rossi', 'Samuele', 'Rossi', 'rossi678', 'Via Garibaldi', '20', 'Napoli', 'Consumatore', 350.00),
('sara.conti', 'Sara', 'Conti', 'conti789', 'Corso Italia', '12', 'Roma', 'Consumatore', 280.00),
('silvia.galli', 'Silvia', 'Galli', 'galli345', 'Via Roma', '23', 'Bologna', 'Consumatore', 310.00),
('sonia.ferraro', 'Sonia', 'Ferraro', 'ferraro789', 'Via Dante', '15', 'Genova', 'Consumatore', 300.00),
('stefano.rossi', 'Stefano', 'Rossi', 'rossi456', 'Via Milano', '11', 'Firenze', 'Consumatore', 250.00),
('tatiana.palma', 'Tatiana', 'Palma', 'palma567', 'Via Torino', '30', 'Milano', 'Consumatore', 330.00),
('tommaso.conti', 'Tommaso', 'Conti', 'conti890', 'Via Roma', '19', 'Catania', 'Consumatore', 420.00),
('valentina.rossi', 'Valentina', 'Rossi', 'rossi123', 'Via Verdi', '17', 'Torino', 'Consumatore', 250.00),
('vincenzo.rossi', 'Vincenzo', 'Rossi', 'rossi234', 'Via Roma', '8', 'Palermo', 'Consumatore', 290.00),
('vittoria.ferraro', 'Vittoria', 'Ferraro', 'ferraro345', 'Corso Umberto', '14', 'Milano', 'Consumatore', 310.00),
('alessio.romano', 'Alessio', 'Romano', 'romano456', 'Via Dante', '12', 'Catania', 'Consumatore', 300.00),
('amanda.martini', 'Amanda', 'Martini', 'martini567', 'Via Roma', '7', 'Bologna', 'Consumatore', 350.00),
('angelo.palma', 'Angelo', 'Palma', 'palma678', 'Corso Umberto', '23', 'Torino', 'Consumatore', 270.00),
('anita.rossi', 'Anita', 'Rossi', 'rossi789', 'Via Garibaldi', '28', 'Palermo', 'Consumatore', 420.00),
('antonio.rossi', 'Antonio', 'Rossi', 'rossi890', 'Via Roma', '10', 'Milano', 'Consumatore', 300.00),
('barbara.galli', 'Barbara', 'Galli', 'galli123', 'Via Garibaldi', '7', 'Firenze', 'Consumatore', 330.00),
('beatrice.rossi', 'Beatrice', 'Rossi', 'rossi234', 'Via Italia', '21', 'Catania', 'Consumatore', 280.00),
('carlo.palma', 'Carlo', 'Palma', 'palma345', 'Via Napoli', '9', 'Bologna', 'Consumatore', 290.00),
('carmen.romano', 'Carmen', 'Romano', 'romano456', 'Corso Italia', '11', 'Genova', 'Consumatore', 350.00),
('claudio.conti', 'Claudio', 'Conti', 'conti567', 'Via Garibaldi', '16', 'Napoli', 'Consumatore', 310.00),
('daniela.palma', 'Daniela', 'Palma', 'palma678', 'Via Roma', '21', 'Milano', 'Consumatore', 360.00),
('dario.rossi', 'Dario', 'Rossi', 'rossi789', 'Via Verona', '8', 'Firenze', 'Consumatore', 240.00),
('diana.ferraro', 'Diana', 'Ferraro', 'ferraro890', 'Via Garibaldi', '5', 'Torino', 'Consumatore', 300.00),
('elena.martini', 'Elena', 'Martini', 'martini123', 'Via Roma', '18', 'Catania', 'Consumatore', 290.00),
('giacomo.rossi', 'Giacomo', 'Rossi', 'rossi234', 'Via Roma', '16', 'Milano', 'Consumatore', 340.00),
('ilaria.palma', 'Ilaria', 'Palma', 'palma345', 'Via Napoli', '10', 'Palermo', 'Consumatore', 280.00),
('ivano.rossi', 'Ivano', 'Rossi', 'rossi456', 'Corso Italia', '22', 'Napoli', 'Consumatore', 250.00),
('jacopo.ferraro', 'Jacopo', 'Ferraro', 'ferraro567', 'Via Garibaldi', '19', 'Bologna', 'Consumatore', 310.00),
('laura.rossi', 'Laura', 'Rossi', 'rossi678', 'Via Torino', '12', 'Firenze', 'Consumatore', 350.00),
('luigi.ferraro', 'Luigi', 'Ferraro', 'ferraro789', 'Via Roma', '6', 'Torino', 'Consumatore', 300.00),
('marco.palma', 'Marco', 'Palma', 'palma890', 'Via Garibaldi', '8', 'Milano', 'Consumatore', 330.00),
('martina.rossi', 'Martina', 'Rossi', 'rossi123', 'Via Napoli', '14', 'Catania', 'Consumatore', 250.00),
('michele.conti', 'Michele', 'Conti', 'conti456', 'Corso Italia', '10', 'Genova', 'Consumatore', 320.00),
('monica.rossi', 'Monica', 'Rossi', 'rossi789', 'Via Roma', '25', 'Napoli', 'Consumatore', 270.00),
('nino.palma', 'Nino', 'Palma', 'palma567', 'Via Verdi', '12', 'Firenze', 'Consumatore', 360.00),
('ornella.rossi', 'Ornella', 'Rossi', 'rossi678', 'Via Roma', '14', 'Milano', 'Consumatore', 280.00),
('patrizia.ferraro', 'Patrizia', 'Ferraro', 'ferraro789', 'Corso Italia', '6', 'Palermo', 'Consumatore', 300.00),
('raffaele.rossi', 'Raffaele', 'Rossi', 'rossi890', 'Via Garibaldi', '11', 'Catania', 'Consumatore', 350.00),
('rossella.palma', 'Rossella', 'Palma', 'palma123', 'Via Roma', '18', 'Genova', 'Consumatore', 290.00),
('samuele.rossi', 'Samuele', 'Rossi', 'rossi234', 'Via Napoli', '16', 'Bologna', 'Consumatore', 310.00),
('silvia.martini', 'Silvia', 'Martini', 'martini567', 'Via Roma', '12', 'Napoli', 'Consumatore', 260.00),
('stefania.rossi', 'Stefania', 'Rossi', 'rossi678', 'Via Italia', '9', 'Milano', 'Consumatore', 270.00),
('tiziana.palma', 'Tiziana', 'Palma', 'palma789', 'Via Roma', '30', 'Catania', 'Consumatore', 350.00),
('valeria.ferraro', 'Valeria', 'Ferraro', 'ferraro890', 'Corso Umberto', '22', 'Torino', 'Consumatore', 320.00),
('vincenzo.martini', 'Vincenzo', 'Martini', 'martini123', 'Via Napoli', '11', 'Bologna', 'Consumatore', 300.00),
('vittoria.rossi', 'Vittoria', 'Rossi', 'rossi456', 'Via Garibaldi', '8', 'Genova', 'Consumatore', 310.00),
('adriana.palma', 'Adriana', 'Palma', 'palma567', 'Via Roma', '24', 'Milano', 'Consumatore', 290.00),
('alessia.conti', 'Alessia', 'Conti', 'conti678', 'Via Dante', '7', 'Napoli', 'Consumatore', 260.00),
('antonio.galli', 'Antonio', 'Galli', 'galli789', 'Via Roma', '15', 'Firenze', 'Consumatore', 350.00),
('beatrice.palma', 'Beatrice', 'Palma', 'palma890', 'Via Napoli', '14', 'Catania', 'Consumatore', 310.00),
('claudia.rossi', 'Claudia', 'Rossi', 'rossi123', 'Via Garibaldi', '16', 'Torino', 'Consumatore', 280.00),
('enrico.ferraro', 'Enrico', 'Ferraro', 'ferraro234', 'Corso Italia', '25', 'Milano', 'Consumatore', 320.00),
('federica.palma', 'Federica', 'Palma', 'palma345', 'Via Roma', '12', 'Genova', 'Consumatore', 300.00),
('gianna.rossi', 'Gianna', 'Rossi', 'rossi456', 'Via Dante', '19', 'Catania', 'Consumatore', 280.00),
('giovanni.ferraro', 'Giovanni', 'Ferraro', 'ferraro567', 'Via Napoli', '23', 'Napoli', 'Consumatore', 350.00),
('ilaria.rossi', 'Ilaria', 'Rossi', 'rossi678', 'Via Roma', '27', 'Palermo', 'Consumatore', 270.00),
('luigi.palma', 'Luigi', 'Palma', 'palma789', 'Via Garibaldi', '8', 'Bologna', 'Consumatore', 310.00),
('marina.ferraro', 'Marina', 'Ferraro', 'ferraro890', 'Via Roma', '20', 'Firenze', 'Consumatore', 290.00),
('marco.rossi', 'Marco', 'Rossi', 'rossi123', 'Via Napoli', '6', 'Milano', 'Consumatore', 320.00),
('natalia.rossi', 'Natalia', 'Rossi', 'rossi456', 'Corso Italia', '14', 'Genova', 'Consumatore', 350.00),
('nicoletta.palma', 'Nicoletta', 'Palma', 'palma567', 'Via Dante', '22', 'Catania', 'Consumatore', 290.00),
('paolo.rossi', 'Paolo', 'Rossi', 'rossi678', 'Via Roma', '18', 'Torino', 'Consumatore', 310.00),
('raffaele.palma', 'Raffaele', 'Palma', 'palma789', 'Via Garibaldi', '10', 'Milano', 'Consumatore', 330.00),
('rachele.rossi', 'Rachele', 'Rossi', 'rossi890', 'Via Verdi', '21', 'Bologna', 'Consumatore', 300.00),
('stefano.ferraro', 'Stefano', 'Ferraro', 'ferraro123', 'Via Napoli', '25', 'Palermo', 'Consumatore', 320.00),
('tiziana.rossi', 'Tiziana', 'Rossi', 'rossi456', 'Corso Umberto', '13', 'Milano', 'Consumatore', 270.00),
('vittoria.ferraro', 'Vittoria', 'Ferraro', 'ferraro678', 'Via Garibaldi', '24', 'Catania', 'Consumatore', 350.00),
('alessandro.rossi', 'Alessandro', 'Rossi', 'rossi789', 'Via Roma', '17', 'Genova', 'Consumatore', 290.00),
('antonella.rossi', 'Antonella', 'Rossi', 'rossi890', 'Via Verdi', '19', 'Napoli', 'Consumatore', 310.00),
('bruno.rossi', 'Bruno', 'Rossi', 'rossi123', 'Via Roma', '15', 'Firenze', 'Consumatore', 280.00),
('claudia.ferraro', 'Claudia', 'Ferraro', 'ferraro456', 'Corso Italia', '18', 'Torino', 'Consumatore', 300.00),
('donatella.palma', 'Donatella', 'Palma', 'palma789', 'Via Garibaldi', '27', 'Milano', 'Consumatore', 320.00),
('giovanni.martini', 'Giovanni', 'Martini', 'martini123', 'Via Napoli', '14', 'Catania', 'Consumatore', 350.00),
('ilaria.martini', 'Ilaria', 'Martini', 'martini456', 'Via Verdi', '25', 'Genova', 'Consumatore', 270.00),
('luca.ferraro', 'Luca', 'Ferraro', 'ferraro789', 'Via Roma', '18', 'Palermo', 'Consumatore', 290.00),
('marina.rossi', 'Marina', 'Rossi', 'rossi890', 'Via Napoli', '23', 'Bologna', 'Consumatore', 310.00),
('massimo.ferraro', 'Massimo', 'Ferraro', 'ferraro123', 'Via Dante', '8', 'Firenze', 'Consumatore', 320.00),
('nicoletta.rossi', 'Nicoletta', 'Rossi', 'rossi234', 'Via Roma', '19', 'Milano', 'Consumatore', 290.00),
('patrizia.rossi', 'Patrizia', 'Rossi', 'rossi567', 'Corso Umberto', '12', 'Catania', 'Consumatore', 270.00),
('silvio.palma', 'Silvio', 'Palma', 'palma678', 'Via Garibaldi', '15', 'Genova', 'Consumatore', 310.00),
('teresa.rossi', 'Teresa', 'Rossi', 'rossi789', 'Via Roma', '22', 'Napoli', 'Consumatore', 350.00);

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

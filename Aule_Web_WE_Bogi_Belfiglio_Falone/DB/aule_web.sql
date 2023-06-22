-- MySQL dump 10.13  Distrib 8.0.33, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: aule_web
-- ------------------------------------------------------
-- Server version	8.0.33

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `amministratore`
--

DROP TABLE IF EXISTS `amministratore`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `amministratore` (
  `ID` int NOT NULL AUTO_INCREMENT,
  `username` varchar(30) NOT NULL,
  `psw` varchar(200) NOT NULL,
  `versione` int DEFAULT '0',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `amministratore`
--

LOCK TABLES `amministratore` WRITE;
/*!40000 ALTER TABLE `amministratore` DISABLE KEYS */;
/*!40000 ALTER TABLE `amministratore` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `associazione_aula_gruppo`
--

DROP TABLE IF EXISTS `associazione_aula_gruppo`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `associazione_aula_gruppo` (
  `ID` int NOT NULL AUTO_INCREMENT,
  `ID_aula` int NOT NULL,
  `ID_gruppo` int NOT NULL,
  PRIMARY KEY (`ID`),
  KEY `ID_aula` (`ID_aula`),
  KEY `ID_gruppo` (`ID_gruppo`),
  CONSTRAINT `associazione_aula_gruppo_ibfk_1` FOREIGN KEY (`ID_aula`) REFERENCES `aula` (`ID`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `associazione_aula_gruppo_ibfk_2` FOREIGN KEY (`ID_gruppo`) REFERENCES `gruppo` (`ID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `associazione_aula_gruppo`
--

LOCK TABLES `associazione_aula_gruppo` WRITE;
/*!40000 ALTER TABLE `associazione_aula_gruppo` DISABLE KEYS */;
INSERT INTO `associazione_aula_gruppo` VALUES (1,1,2),(2,2,1),(3,3,2);
/*!40000 ALTER TABLE `associazione_aula_gruppo` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `attrezzatura`
--

DROP TABLE IF EXISTS `attrezzatura`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `attrezzatura` (
  `ID` int NOT NULL AUTO_INCREMENT,
  `nome` varchar(30) NOT NULL,
  `numero_di_serie` varchar(40) NOT NULL,
  `ID_aula` int DEFAULT NULL,
  `versione` int DEFAULT '0',
  PRIMARY KEY (`ID`),
  KEY `ID_aula` (`ID_aula`),
  CONSTRAINT `attrezzatura_ibfk_1` FOREIGN KEY (`ID_aula`) REFERENCES `aula` (`ID`) ON DELETE SET NULL ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `attrezzatura`
--

LOCK TABLES `attrezzatura` WRITE;
/*!40000 ALTER TABLE `attrezzatura` DISABLE KEYS */;
INSERT INTO `attrezzatura` VALUES (1,'PROIETTORE','ABC111',1,0),(2,'MONITOR','ABC222',1,0),(3,'LAVAGNA','ABC333',1,0),(4,'PROIETTORE','ABC123',2,0),(5,'MICROFONO','ABC001',2,0);
/*!40000 ALTER TABLE `attrezzatura` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `aula`
--

DROP TABLE IF EXISTS `aula`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `aula` (
  `ID` int NOT NULL AUTO_INCREMENT,
  `nome` varchar(20) NOT NULL,
  `luogo` varchar(20) NOT NULL,
  `edificio` varchar(20) NOT NULL,
  `piano` int NOT NULL,
  `capienza` int NOT NULL,
  `numero_prese_elettriche` int NOT NULL,
  `numero_prese_di_rete` int NOT NULL,
  `note_generiche` text,
  `ID_responsabile` int DEFAULT NULL,
  `versione` int DEFAULT '0',
  PRIMARY KEY (`ID`),
  KEY `ID_responsabile` (`ID_responsabile`),
  CONSTRAINT `aula_ibfk_1` FOREIGN KEY (`ID_responsabile`) REFERENCES `responsabile` (`ID`) ON DELETE SET NULL ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `aula`
--

LOCK TABLES `aula` WRITE;
/*!40000 ALTER TABLE `aula` DISABLE KEYS */;
INSERT INTO `aula` VALUES (1,'Rossa','coppito','coppito1',1,60,10,5,'Lorem ipsum dolor sit amet, consectetur adipisci elit, sed eiusmod tempor incidunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrum exercitationem ullam corporis suscipit laboriosam, nisi ut aliquid ex ea commodi consequatur. Quis aute iure reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint obcaecat cupiditat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.',1,0),(2,'A1.2','coppito','Alan Turing',1,40,10,5,'aula piatta',2,0),(3,'A1.3','coppito','Alan Turing',1,20,10,5,'aula ristretta',3,0);
/*!40000 ALTER TABLE `aula` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `corso`
--

DROP TABLE IF EXISTS `corso`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `corso` (
  `ID` int NOT NULL AUTO_INCREMENT,
  `nome` varchar(30) NOT NULL,
  `corso_di_laurea` varchar(30) DEFAULT NULL,
  `tipo_laurea` varchar(20) NOT NULL,
  `anno_di_frequentazione` int DEFAULT NULL,
  `versione` int DEFAULT '0',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `corso`
--

LOCK TABLES `corso` WRITE;
/*!40000 ALTER TABLE `corso` DISABLE KEYS */;
INSERT INTO `corso` VALUES (1,'Matematica','Informatica','Triennale',2023,0),(2,'Fisica','Informatica','Triennale',2023,0),(3,'Informatica','Informatica','Triennale',2023,0);
/*!40000 ALTER TABLE `corso` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `evento`
--

DROP TABLE IF EXISTS `evento`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `evento` (
  `ID` int NOT NULL AUTO_INCREMENT,
  `nome` varchar(30) NOT NULL,
  `descrizione` text,
  `tipologia` varchar(15) NOT NULL,
  `data_evento` date NOT NULL,
  `ora_inizio` time NOT NULL,
  `ora_fine` time NOT NULL,
  `ricorrenza` varchar(20) DEFAULT NULL,
  `data_fine_ricorrenza` date DEFAULT NULL,
  `ID_corso` int DEFAULT NULL,
  `ID_responsabile` int DEFAULT NULL,
  `ID_aula` int NOT NULL,
  `versione` int DEFAULT '0',
  PRIMARY KEY (`ID`),
  KEY `ID_corso` (`ID_corso`),
  KEY `ID_responsabile` (`ID_responsabile`),
  KEY `ID_aula` (`ID_aula`),
  CONSTRAINT `evento_ibfk_1` FOREIGN KEY (`ID_corso`) REFERENCES `corso` (`ID`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `evento_ibfk_2` FOREIGN KEY (`ID_responsabile`) REFERENCES `responsabile` (`ID`) ON DELETE SET NULL ON UPDATE CASCADE,
  CONSTRAINT `evento_ibfk_3` FOREIGN KEY (`ID_aula`) REFERENCES `aula` (`ID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `evento`
--

LOCK TABLES `evento` WRITE;
/*!40000 ALTER TABLE `evento` DISABLE KEYS */;
INSERT INTO `evento` VALUES (1,'LezMatematica','Conti bbbbbbbbbbbbbbbbbbbbbbbb rrrrrrrrrrrrrrrrrrrrrrrrrrrr eeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee wwwwwwwwwwwwwwwwwwwwwwwww aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa bbbbbbbbbbbbbbbbbbbb tttttttttttttttttttt','LEZIONE','2023-06-07','18:10:28','20:10:28','settimanale','2023-06-21',1,1,1,0),(2,'LezFisica',' moto ddddddddddddddddddddddddddddddddddd ffffffffffffffffffffffffffffffffffffffffffff ggggggggggggggggggggggggggggggggggggggg hhhhhhhhhhhhhhhhhhhhhhhhhhhh moto ddddddddddddddddddddddddddddddddddd ffffffffffffffffffffffffffffffffffffffffffff ggggggggggggggggggggggggggggggggggggggg hhhhhhhhhhhhhhhhhhhhhhhhhhhh moto ddddddddddddddddddddddddddddddddddd ffffffffffffffffffffffffffffffffffffffffffff ggggggggggggggggggggggggggggggggggggggg hhhhhhhhhhhhhhhhhhhhhhhhhhhh','LEZIONE','2023-06-07','19:10:28','20:10:28','giornaliera','2023-06-11',2,2,1,0),(3,'EsInformatica','algoritmi','ESAME','2023-06-07','21:10:28','23:10:28','giornaliera','2023-06-11',3,3,2,0);
/*!40000 ALTER TABLE `evento` ENABLE KEYS */;
UNLOCK TABLES;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`localhost`*/ /*!50003 TRIGGER `Ricorrenza` BEFORE INSERT ON `evento` FOR EACH ROW BEGIN
	IF NEW.ricorrenza <> "NESSUNA" and NEW.data_fine_ricorrenza IS NULL THEN
			SIGNAL SQLSTATE '45000' SET message_text = 'Non è stata definita una data di fine per
				la ricorrenza dell\'evento';
	END IF;
END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`localhost`*/ /*!50003 TRIGGER `Definizione_corso` BEFORE INSERT ON `evento` FOR EACH ROW BEGIN
		if new.tipologia IN ("LEZIONE", "ESAME", "PARZIALE") and new.ID_corso IS NULL
        THEN
			SIGNAL SQLSTATE '45000' SET message_text = 'Non è stato specificato il corso dell\'evento';
		END IF;
END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`localhost`*/ /*!50003 TRIGGER `inserisci_eventi_ricorrenti` AFTER INSERT ON `evento` FOR EACH ROW BEGIN
	DECLARE data_evento_ricorrente DATE;
	SET data_evento_ricorrente = new.data_evento;
    
	if(new.ricorrenza = "MENSILE") then
    SET data_evento_ricorrente = data_evento_ricorrente + interval 1 month;
		while(data_evento_ricorrente <= new.data_fine_ricorrenza) DO
			call insert_evento_ricorrente(new.ID, data_evento_ricorrente);
            SET data_evento_ricorrente = data_evento_ricorrente + interval 1 month;
            
			end while;
	end if;
    
    if(new.ricorrenza = "GIORNALIERA") then
		SET data_evento_ricorrente = data_evento_ricorrente + interval 1 day;
		while(data_evento_ricorrente <= new.data_fine_ricorrenza) DO
			call insert_evento_ricorrente(new.ID, data_evento_ricorrente);
            SET data_evento_ricorrente = data_evento_ricorrente + interval 1 day;
            
		end while;
	end if;
    
    if(new.ricorrenza = "SETTIMANALE") then
		set data_evento_ricorrente = data_evento_ricorrente + interval 1 week;
		while(data_evento_ricorrente <= new.data_fine_ricorrenza) DO
			call insert_evento_ricorrente(new.ID, data_evento_ricorrente);
            set data_evento_ricorrente = data_evento_ricorrente + interval 1 week;
            
		end while;
	end if;

END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;

--
-- Table structure for table `evento_ricorrente`
--

DROP TABLE IF EXISTS `evento_ricorrente`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `evento_ricorrente` (
  `ID` int NOT NULL AUTO_INCREMENT,
  `data_evento` date NOT NULL,
  `ID_evento` int NOT NULL,
  `versione` int DEFAULT '0',
  PRIMARY KEY (`ID`),
  KEY `ID_evento` (`ID_evento`),
  CONSTRAINT `evento_ricorrente_ibfk_1` FOREIGN KEY (`ID_evento`) REFERENCES `evento` (`ID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `evento_ricorrente`
--

LOCK TABLES `evento_ricorrente` WRITE;
/*!40000 ALTER TABLE `evento_ricorrente` DISABLE KEYS */;
INSERT INTO `evento_ricorrente` VALUES (1,'2023-06-14',1,0),(2,'2023-06-21',1,0),(3,'2023-06-08',2,0),(4,'2023-06-09',2,0),(5,'2023-06-10',2,0),(6,'2023-06-11',2,0),(7,'2023-06-08',3,0),(8,'2023-06-09',3,0),(9,'2023-06-10',3,0),(10,'2023-06-11',3,0);
/*!40000 ALTER TABLE `evento_ricorrente` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `gruppo`
--

DROP TABLE IF EXISTS `gruppo`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `gruppo` (
  `ID` int NOT NULL AUTO_INCREMENT,
  `nome` varchar(30) NOT NULL,
  `tipo` varchar(20) NOT NULL,
  `descrizione` varchar(60) NOT NULL,
  `versione` int DEFAULT '0',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `gruppo`
--

LOCK TABLES `gruppo` WRITE;
/*!40000 ALTER TABLE `gruppo` DISABLE KEYS */;
INSERT INTO `gruppo` VALUES (1,'polo coppito','POLO','aaaa',0),(2,'DISIM','DIPARTIMENTO','GGaa',0),(3,'polo 1','POLO','aaFFFF',0),(4,'aaa','aaa','aaa',0),(5,'bbb','bbb','aaa',0),(6,'aaa','ccc','aaa',0),(7,'aaa','ddd','aaa',0),(8,'aaa','eee','aaa',0),(9,'aaa','fff','aaa',0),(10,'prova polo','POLO','www',0);
/*!40000 ALTER TABLE `gruppo` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `responsabile`
--

DROP TABLE IF EXISTS `responsabile`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `responsabile` (
  `ID` int NOT NULL AUTO_INCREMENT,
  `nome` varchar(20) NOT NULL,
  `cognome` varchar(20) NOT NULL,
  `codice_fiscale` char(16) NOT NULL,
  `email` varchar(50) NOT NULL,
  `versione` int DEFAULT '0',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `responsabile`
--

LOCK TABLES `responsabile` WRITE;
/*!40000 ALTER TABLE `responsabile` DISABLE KEYS */;
INSERT INTO `responsabile` VALUES (1,'Pino','Daniele','PNDNL56CH76B','pino.daniele@gmail.com',0),(2,'Mario','Rossi','MRSSI56BG876N','mario.rossi@gmail.com',0),(3,'Giuseppe','Francoantonio','GSPFRT567L8M','giuseppe.francantonio@gmail.com',0);
/*!40000 ALTER TABLE `responsabile` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping events for database 'aule_web'
--

--
-- Dumping routines for database 'aule_web'
--
/*!50003 DROP PROCEDURE IF EXISTS `insert_evento_ricorrente` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `insert_evento_ricorrente`(ID INTEGER, data_evento_ricorrente DATE)
BEGIN
	insert into evento_ricorrente(data_evento, ID_evento) values (data_evento_ricorrente, ID);
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2023-06-07 19:06:20

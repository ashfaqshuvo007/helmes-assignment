-- MySQL dump 10.13  Distrib 8.0.33, for Linux (aarch64)
--
-- Host: localhost    Database: helmes
-- ------------------------------------------------------
-- Server version	8.0.33

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `sectors`
--

DROP TABLE IF EXISTS `sectors`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sectors` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `sector_name` varchar(255) NOT NULL,
  `parent_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKq1348ewyn6hb4vr2yypo2ofcy` (`parent_id`),
  CONSTRAINT `FKq1348ewyn6hb4vr2yypo2ofcy` FOREIGN KEY (`parent_id`) REFERENCES `sectors` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=83 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sectors`
--

LOCK TABLES `sectors` WRITE;
/*!40000 ALTER TABLE `sectors` DISABLE KEYS */;
INSERT INTO `sectors` VALUES (1,'Manufacturing',NULL),(2,'Other',NULL),(3,'Service',NULL),(6,'Construction materials',1),(7,'Electronics and Optics',1),(8,'Food and Beverage',1),(9,'Bakery & confectionery products',8),(10,'Beverages',8),(11,'Fish & fish products',8),(12,'Meat & meat products',8),(13,'Milk & dairy products',8),(14,'Sweets & snack food',8),(15,'Other (Food and Beverage)',8),(16,'Furniture',1),(17,'Bathroom/sauna',16),(18,'Bedroom',16),(19,'Children’s room ',16),(20,'Kitchen',16),(21,'Living room',16),(22,'Office',16),(23,'Other (Furniture)',16),(24,'Project furniture',16),(25,'Machinery',1),(26,'Machinery components',25),(27,'Machinery equipment/tools',25),(28,'Manufacture of machinery',25),(29,'Maritime',25),(30,'Metal structures',25),(31,'Other (Machinery)',25),(32,'Repair and maintenance service',25),(33,'Aluminium and steel workboats',29),(34,'Boat/Yacht building',29),(35,'Ship repair and conversion',29),(36,'Metalworking',1),(37,'Construction of metal structures',36),(38,'Houses and buildings',36),(39,'Metal products',36),(40,'Metal works',36),(41,'CNC-machining',40),(42,'Forgings, Fasteners',40),(43,'Gas, Plasma, Laser cutting',40),(44,'MIG, TIG, Aluminum welding',40),(45,'Plastic and Rubber',1),(46,'Packaging',45),(47,'Plastic goods',45),(48,'Plastic processing technology',45),(49,'Plastic profiles',45),(50,'Blowing',48),(51,'Moulding',48),(52,'Plastics welding and processing',48),(53,'Printing',1),(54,'Advertising',53),(55,'Book/Periodicals printing',53),(56,'Labelling and packaging printing',53),(59,'Textile and Clothing',1),(60,'Clothing',59),(61,'Textile',59),(62,'Wood',1),(63,'Other (Wood)',62),(64,'Wooden building materials',62),(65,'Wooden houses',62),(66,'Creative industries',2),(67,'Energy technology',2),(68,'Environment',2),(69,'Business services',3),(70,'Engineering',3),(71,'Information Technology and Telecommunications',3),(72,'Tourism',3),(73,'Translation services',3),(74,'Data processing, Web portals, E-marketing',71),(75,'Programming, Consultancy',71),(76,'Software, Hardware',71),(77,'Telecommunications',71),(78,'Transport and Logistics',3),(79,'Air',78),(80,'Rail',78),(81,'Road',78),(82,'Water',78);
/*!40000 ALTER TABLE `sectors` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_sectors`
--

DROP TABLE IF EXISTS `user_sectors`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user_sectors` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `user_id` bigint NOT NULL,
  `sector_id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  KEY `user_id` (`user_id`),
  KEY `sector_id` (`sector_id`),
  CONSTRAINT `user_sectors_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`),
  CONSTRAINT `user_sectors_ibfk_2` FOREIGN KEY (`sector_id`) REFERENCES `sectors` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_sectors`
--

LOCK TABLES `user_sectors` WRITE;
/*!40000 ALTER TABLE `user_sectors` DISABLE KEYS */;
INSERT INTO `user_sectors` VALUES (1,2,2),(2,3,1);
/*!40000 ALTER TABLE `user_sectors` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `users` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `username` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  `role` varchar(8) NOT NULL DEFAULT 'USER',
  `user_terms_and_conditions` varchar(16) DEFAULT 'DISAGREE',
  PRIMARY KEY (`id`),
  UNIQUE KEY `username` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES (1,'John','$2a$12$t5zUFCfw/duPJVaaCxbV8.m1nPRX/kA.2QEvJZm.2uu5n8kKFeHeW','ADMIN','DISAGREE'),(2,'Jane','$2a$12$t5zUFCfw/duPJVaaCxbV8.m1nPRX/kA.2QEvJZm.2uu5n8kKFeHeW','USER','DISAGREE'),(3,'Mika','$2a$12$t5zUFCfw/duPJVaaCxbV8.m1nPRX/kA.2QEvJZm.2uu5n8kKFeHeW','USER','DISAGREE');
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-12-17  7:20:14

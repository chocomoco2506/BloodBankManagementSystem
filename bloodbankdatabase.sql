-- MySQL dump 10.13  Distrib 8.0.38, for Win64 (x86_64)
--
-- Host: localhost    Database: bloodbank
-- ------------------------------------------------------
-- Server version	8.0.38

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
-- Table structure for table `blood`
--

DROP TABLE IF EXISTS `blood`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `blood` (
  `bloodid` int NOT NULL AUTO_INCREMENT,
  `bloodtype` varchar(50) DEFAULT NULL,
  `donorid` int DEFAULT NULL,
  `donation_date` varchar(255) DEFAULT NULL,
  `receiverid` int DEFAULT NULL,
  `receive_date` varchar(255) DEFAULT NULL,
  `status` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`bloodid`)
) ENGINE=InnoDB AUTO_INCREMENT=26 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `blood`
--

LOCK TABLES `blood` WRITE;
/*!40000 ALTER TABLE `blood` DISABLE KEYS */;
INSERT INTO `blood` VALUES (1,'AB+',1,'2024-01-01',3,'2024-01-29','new'),(2,'A+',2,'2024-01-01',2,'2024-01-25','new'),(3,'A-',3,'2024-01-04',NULL,NULL,'expired'),(4,'B+',4,'2024-01-04',NULL,NULL,'expired'),(5,'O+',5,'2024-01-07',1,'2024-01-24','new'),(6,'AB+',6,'2024-01-15',4,'2024-02-01','new'),(7,'AB+',1,'2024-06-25',6,'2024-07-23','new'),(8,'O+',7,'2024-07-03',5,'2024-07-18','new'),(9,'O-',8,'2024-07-24',NULL,NULL,'expired'),(10,'O+',9,'2024-07-26',NULL,NULL,'new'),(11,'A+',10,'2024-07-31',7,'2024-09-05','new'),(12,'AB+',11,'2024-08-01',NULL,NULL,'new'),(13,'B+',12,'2024-08-03',NULL,NULL,'new'),(14,'AB+',13,'2024-08-05',NULL,NULL,'new'),(15,'AB+',14,'2024-08-05',NULL,NULL,'new'),(16,'A+',15,'2024-08-07',NULL,NULL,'new'),(17,'A+',16,'2024-08-08',NULL,NULL,'new'),(18,'A+',2,'2024-08-25',NULL,NULL,'new'),(19,'B+',4,'2024-08-25',NULL,NULL,'new'),(20,'O+',5,'2024-08-26',NULL,NULL,'new'),(21,'A-',3,'2024-08-29',NULL,NULL,'new'),(22,'O+',17,'2024-08-30',NULL,NULL,'new'),(23,'B-',18,'2024-08-30',NULL,NULL,'new'),(24,'A+',19,'2024-09-05',NULL,NULL,'new'),(25,'B+',4,'2024-09-05',NULL,NULL,'new');
/*!40000 ALTER TABLE `blood` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `donor`
--

DROP TABLE IF EXISTS `donor`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `donor` (
  `donorid` int NOT NULL AUTO_INCREMENT,
  `donorname` varchar(255) DEFAULT NULL,
  `phone` varchar(100) DEFAULT NULL,
  `age` varchar(50) DEFAULT NULL,
  `gender` varchar(50) DEFAULT NULL,
  `address` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `nrc` varchar(255) DEFAULT NULL,
  `bloodtype` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`donorid`)
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `donor`
--

LOCK TABLES `donor` WRITE;
/*!40000 ALTER TABLE `donor` DISABLE KEYS */;
INSERT INTO `donor` VALUES (1,'May Thandar Htun','09784996110','23','Female','Labutta','may@gmail.com','14/LAPATA(N)123456','AB+'),(2,'Sithu','09789675654','25','Male','MyaungMya','sithu@gmail.com','14/MAMANA(N)250623','A+'),(3,'Htet Soe Wai','09786786754','22','Male','Pathein','htet@gmail.com','14/PATHANA(N)121212','A-'),(4,'Chue Eain Si','09678675645','22','Female','Pathien','chue@gmail.com','14/PATHANA(N)232323','B+'),(5,'Htet Myat Moe','09786787654','23','Male','Pathein','htetmyat@gmail.com','14/PATHANA(N)343434','O+'),(6,'Su Myat Lwin','09789678654','22','Female','Pathien','su@gmail.com','14/PATHANA(N)454545','AB+'),(7,'Su Shwe Zin','09789765654','23','Female','Pathein','su123@gmail.com','14/AHGAPA(N)123123','O+'),(8,'Htet Oo','09786756453','45','Male','Pathein','hteto123@gmail.com','14/PATHANA(N)676767','O-'),(9,'Chit Su Maung','09681144567','24','Female','Pathein','chit12@gmail.com','14/PATHANA(N)898989','O+'),(10,'Nyan Lin Set','09786786456','25','Male','Pathein','nyan@gmail.com','14/PATHANA(N)242424','A+'),(11,'Aye Chang Maung','09786786754','30','Male','Yangon','aye123@gmail.com','14/PATHANA(N)789786','AB+'),(12,'Htet Htet Aung','09422345654','30','Female','Yangon','bsox@gmail.com','14/PATHANA(N)456456','B+'),(13,'Aung Yell Lin','09789786765','30','Male','Pathein','aungy@gmail.com','14/PATHANA(N)678678','AB+'),(14,'Yu Thandar Tin','09789786786','32','Female','Labutta','yuyu@gmail.com','4/PATHANA(N)789789','AB+'),(15,'Win Kyi','09789787675','32','Female','WaKama','win@gmail.com','14/WAKHAMA(N)234567','A+'),(16,'Kyi Zyay','09789878654','27','Male','Wakhama','kyi@gmail.com','14/WAKHAMA(N)123890','A+'),(17,'Kyaw Hein','09789786544','35','Male','Pathein','kyaw3@gmail.com','14/PATHANA(N)234789','O+'),(18,'Nandar','09789786754','36','Female','Labutta','nan@gmail.com','14/LAPATA(N)237895','B-'),(19,'Hnin Hnin','09987876564','34','Female','Pathein','hnin12@gmail.com','14/PATHANA(N)234565','A+');
/*!40000 ALTER TABLE `donor` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `inventory`
--

DROP TABLE IF EXISTS `inventory`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `inventory` (
  `bloodtype` varchar(50) NOT NULL,
  `total` int DEFAULT NULL,
  PRIMARY KEY (`bloodtype`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `inventory`
--

LOCK TABLES `inventory` WRITE;
/*!40000 ALTER TABLE `inventory` DISABLE KEYS */;
INSERT INTO `inventory` VALUES ('A-',1),('A+',4),('AB-',0),('AB+',3),('B-',1),('B+',3),('O-',0),('O+',3);
/*!40000 ALTER TABLE `inventory` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `receiver`
--

DROP TABLE IF EXISTS `receiver`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `receiver` (
  `receiverid` int NOT NULL AUTO_INCREMENT,
  `receivername` varchar(255) DEFAULT NULL,
  `phone` varchar(100) DEFAULT NULL,
  `age` varchar(50) DEFAULT NULL,
  `gender` varchar(50) DEFAULT NULL,
  `address` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`receiverid`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `receiver`
--

LOCK TABLES `receiver` WRITE;
/*!40000 ALTER TABLE `receiver` DISABLE KEYS */;
INSERT INTO `receiver` VALUES (1,'Min Lwin','09789786765','27','Male','Pathein','minlwin123@gmail.com'),(2,'Lwin Mar','09789675643','40','Female','Labutta','lwin123@gmail.com'),(3,'May Hnin','09678767654','35','Female','Pathein','mayh@gmail.com'),(4,'Hnin Wai','09789786543','25','Female','Pathien','thnin@gmail.com'),(5,'Kyaw Htet','09789767564','25','Male','Pathein','kyaw@gmail.com'),(6,'Naing Lin','09789786754','27','Male','Pathein','naing@gmail.com'),(7,'Thandar','0978676564','34','Female','Pathein','thandar@gmail.com');
/*!40000 ALTER TABLE `receiver` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-09-05 12:58:18

CREATE DATABASE  IF NOT EXISTS `springtest` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `springtest`;
-- MySQL dump 10.13  Distrib 5.6.24, for Win64 (x86_64)
--
-- Host: localhost    Database: springtest
-- ------------------------------------------------------
-- Server version	5.6.26-log

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `reading_material`
--

DROP TABLE IF EXISTS `reading_material`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `reading_material` (
  `reading_material_id` int(11) NOT NULL AUTO_INCREMENT,
  `title` varchar(255) NOT NULL,
  `author` varchar(255) NOT NULL,
  `year` int(11) NOT NULL,
  `publisher` varchar(255) NOT NULL,
  `description` varchar(255) NOT NULL,
  `tags` varchar(255) NOT NULL,
  `status` varchar(255) NOT NULL,
  PRIMARY KEY (`reading_material_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `reading_material`
--

LOCK TABLES `reading_material` WRITE;
/*!40000 ALTER TABLE `reading_material` DISABLE KEYS */;
/*!40000 ALTER TABLE `reading_material` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `reading_material_reservation`
--

DROP TABLE IF EXISTS `reading_material_reservation`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `reading_material_reservation` (
  `reservation_id` int(11) NOT NULL AUTO_INCREMENT,
  `reading_material_id` int(11) NOT NULL,
  `user_id` varchar(255) NOT NULL,
  `reservation_date` date NOT NULL,
  `return_date` date NOT NULL,
  PRIMARY KEY (`reservation_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `reading_material_reservation`
--

LOCK TABLES `reading_material_reservation` WRITE;
/*!40000 ALTER TABLE `reading_material_reservation` DISABLE KEYS */;
/*!40000 ALTER TABLE `reading_material_reservation` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `role`
--

DROP TABLE IF EXISTS `role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `role` (
  `role_id` int(11) NOT NULL AUTO_INCREMENT,
  `role` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`role_id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `role`
--

LOCK TABLES `role` WRITE;
/*!40000 ALTER TABLE `role` DISABLE KEYS */;
INSERT INTO `role` VALUES (1,'ADMIN'),(2,'STUDENT'),(3,'FACULTY'),(4,'LIBRARY_STAFF'),(5,'LIBRARY_MANAGER');
/*!40000 ALTER TABLE `role` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `room`
--

DROP TABLE IF EXISTS `room`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `room` (
  `room_id` int(11) NOT NULL AUTO_INCREMENT,
  `is_available` varchar(255) NOT NULL,
  PRIMARY KEY (`room_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `room`
--

LOCK TABLES `room` WRITE;
/*!40000 ALTER TABLE `room` DISABLE KEYS */;
/*!40000 ALTER TABLE `room` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `room_reservation`
--

DROP TABLE IF EXISTS `room_reservation`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `room_reservation` (
  `room_reservation_id` int(11) NOT NULL AUTO_INCREMENT,
  `room_id` int(11) NOT NULL,
  `user_id` varchar(255) NOT NULL,
  `start_date_time` datetime NOT NULL,
  `end_date_time` datetime NOT NULL,
  PRIMARY KEY (`room_reservation_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `room_reservation`
--

LOCK TABLES `room_reservation` WRITE;
/*!40000 ALTER TABLE `room_reservation` DISABLE KEYS */;
/*!40000 ALTER TABLE `room_reservation` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user` (
  `user_id` int(11) NOT NULL,
  `active` int(11) DEFAULT NULL,
  `email` varchar(255) NOT NULL,
  `first_name` varchar(255) NOT NULL,
  `last_name` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  `middle_name` varchar(255) NOT NULL,
  `birthday` varchar(255) NOT NULL,
  `secret_question` varchar(255) NOT NULL,
  `secret_question_answer` varchar(255) NOT NULL,
  `type` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (981234,1,'student@yahoo.com','student','student','$2a$10$BDkLW4aTNqJ5d65GT10gxeaegr03Vt.aJQECwfVpMWSMcyuU1QA4q','student','asdasdasd','asd','asd',NULL),(11111111,1,'admin@yahoo.com','a','a','$2a$10$dCN.dGq3cdMYtG6ZAmiX5OX/7sbMsAxuMLzrWl6hnVDfBBqKpagPa','a','a','asd','asd',NULL),(12312311,1,'staff@yahoo.com','asd','asd','$2a$10$o3Nt.7axQ9qIdx.Q/.H6k.za2MFO2aCi/r8ZAEauNWF1lkxRCSXZ6','asd','asd','asd','asd',NULL),(12312344,1,'manager2@yahoo.com','a','a','$2a$10$KOfkXcroxxvAbY/R6xNyfeDc53ORxxLNQmMD0uGJdXkJsrcepXRTm','a','a','asd','ad',NULL),(12345222,1,'student2@yahoo.com','sad','asd','$2a$10$pDGB08zjUZ9F28NLO5yFw.D9rez1MrCv9IjRPBcPBZ4lsn24NUf.u','asd','asd','sad','asd',NULL),(12345678,1,'student3@yahoo.com','asd','asd','$2a$10$19oG4etiXPU7GEfVB/bkfOM83JT7HZN6CfutGWq2EeTm6Tyjc0Z2G','ad','asd','sd','aas',NULL),(20142222,1,'faculty2@yahoo.com','asd','asd','$2a$10$p93Z1WsmHxhVUGoOhUAu9e57PV5dEBPbZIDm/z0v3HuiNj/SQ9jNq','asd','asd','asd','asd',NULL),(22222222,1,'librarystaff@yahoo.com','a','a','$2a$10$ChjKWSm/8SW1f063dY8lGeJ4sCIFNEdhX9xkia72aQmPnHdeuSxKW','a','a','asd','asd',NULL),(33333333,1,'faculty@yahoo.com','asd','asd','$2a$10$jZHtu93PexBwAHFjj0v69eazpSCA1MjN8/cDjtfBao7BA9I7Fv.sa','asd','asd','asd','asd',NULL),(55112233,1,'staff17@yahoo.com','asd','asd','$2a$10$tXpGMro1CM.2HdxOmT4CDe0wdD8xK6IwMR3875GY/aotLxLL.A9mW','asd','asd','sd','asd',NULL),(55522212,1,'staff3@yahoo.com','as','z','$2a$10$cuWCYIHEQMSsGHHnvlvege/8YRGq44fmKtPJhpgnH83s5r61E2TWi','a','a','zz','as',NULL),(55555555,1,'librarymanager@yahoo.com','q','q','$2a$10$m7ZhdIBUrzwnU4Y5bMWJW.090r2p/PRfHYsYRClvCqyUbaCyz6c2u','q','q','asd','asd',NULL),(61352222,1,'staffpogi@yahoo.com','asd','asd','$2a$10$OapNmINl9NDMuBQJG9R00uqttFC2QQRwGQxX8RFDfrsd1EruV0TRG','asd','asd','asd','asd',NULL),(98575555,1,'staffzz@yahoo.com','asd','q','$2a$10$E07xhdEbFzsoZXfKrvTmDuZHSzoXX0iGQfAu/BaQIaGMBgnYvtC0O','asd','q','sd','asd',NULL),(99997433,1,'managerwoo@yahoo.com','asd','1','$2a$10$41tEZY4B9dulUn0nLijcsefYZNgwN5lmEYg0x32GdR56IZLdan98O','ad','asd','asd','asd',NULL);
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_role`
--

DROP TABLE IF EXISTS `user_role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user_role` (
  `user_id` int(11) NOT NULL,
  `role_id` int(11) NOT NULL,
  PRIMARY KEY (`user_id`,`role_id`),
  KEY `FKa68196081fvovjhkek5m97n3y` (`role_id`),
  CONSTRAINT `FK859n2jvi8ivhui0rl0esws6o` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `FKa68196081fvovjhkek5m97n3y` FOREIGN KEY (`role_id`) REFERENCES `role` (`role_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_role`
--

LOCK TABLES `user_role` WRITE;
/*!40000 ALTER TABLE `user_role` DISABLE KEYS */;
INSERT INTO `user_role` VALUES (11111111,1),(981234,2),(12345222,2),(12345678,2),(20142222,3),(33333333,3),(12312311,4),(22222222,4),(55112233,4),(55522212,4),(61352222,4),(98575555,4),(12312344,5),(55555555,5),(99997433,5);
/*!40000 ALTER TABLE `user_role` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2017-07-05  2:05:25

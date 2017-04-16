-- MySQL dump 10.13  Distrib 5.7.17, for Win64 (x86_64)
--
-- Host: localhost    Database: bdfotogramas
-- ------------------------------------------------------
-- Server version	5.7.17-log

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
-- Table structure for table `acertadas`
--

DROP TABLE IF EXISTS `acertadas`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `acertadas` (
  `idFotograma` int(11) NOT NULL,
  `login` varchar(12) NOT NULL,
  PRIMARY KEY (`idFotograma`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `acertadas`
--

LOCK TABLES `acertadas` WRITE;
/*!40000 ALTER TABLE `acertadas` DISABLE KEYS */;
INSERT INTO `acertadas` VALUES (2,'lean'),(4,'lean'),(6,'lean'),(7,'lean'),(8,'lean');
/*!40000 ALTER TABLE `acertadas` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `fotogramas`
--

DROP TABLE IF EXISTS `fotogramas`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `fotogramas` (
  `idFotograma` int(11) NOT NULL,
  `archivo` varchar(45) NOT NULL,
  `titPelicula` varchar(255) NOT NULL,
  `anyoEstreno` int(11) NOT NULL,
  `director` varchar(128) NOT NULL,
  `genero` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`idFotograma`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `fotogramas`
--

LOCK TABLES `fotogramas` WRITE;
/*!40000 ALTER TABLE `fotogramas` DISABLE KEYS */;
INSERT INTO `fotogramas` VALUES (1,'chacal.jpg','Chacal',1936,'Fred Zinnemann','Policiaco'),(2,'ciudadano.jpg','Un ciudadano ejemplar',2009,'F. Gary Gray','Intriga'),(3,'diablo.jpg','Diablo',2003,'F. Gary Gray','Accion'),(4,'romper.jpg','Romper stomper',1992,'Geoffrey Wright','Accion'),(5,'invicto3.jpg','Invicto 3',2010,'Isaac Florentine','Accion'),(6,'scout.jpg','El ultimo boy scout',1991,'Tony Scott','Accion'),(7,'tirador.jpg','El tirador',2007,'Antoine Fuqua','Thriller'),(8,'vecinos.jpg','Vecinos invasores',2006,'Tim Johnson','Comedia'),(9,'england.jpg','This is England',2006,'Shane Meadows','Drama'),(10,'gladiator.jpg','Gladiator',2000,'Ridley Scott','Accion');
/*!40000 ALTER TABLE `fotogramas` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `generos`
--

DROP TABLE IF EXISTS `generos`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `generos` (
  `genero` varchar(30) NOT NULL,
  PRIMARY KEY (`genero`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `generos`
--

LOCK TABLES `generos` WRITE;
/*!40000 ALTER TABLE `generos` DISABLE KEYS */;
INSERT INTO `generos` VALUES ('Accion'),('Comedia'),('Drama'),('Intriga'),('Policiaca'),('Thriller');
/*!40000 ALTER TABLE `generos` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ranking`
--

DROP TABLE IF EXISTS `ranking`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ranking` (
  `login` varchar(12) NOT NULL,
  `puntos` int(11) DEFAULT NULL,
  PRIMARY KEY (`login`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ranking`
--

LOCK TABLES `ranking` WRITE;
/*!40000 ALTER TABLE `ranking` DISABLE KEYS */;
INSERT INTO `ranking` VALUES ('admin',48),('eduardo',10),('lean',4);
/*!40000 ALTER TABLE `ranking` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `usuarios`
--

DROP TABLE IF EXISTS `usuarios`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `usuarios` (
  `login` varchar(12) NOT NULL,
  `clave` varchar(12) NOT NULL,
  PRIMARY KEY (`login`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `usuarios`
--

LOCK TABLES `usuarios` WRITE;
/*!40000 ALTER TABLE `usuarios` DISABLE KEYS */;
INSERT INTO `usuarios` VALUES ('bubu','bubu'),('frolik','frolik'),('lean','lean'),('rox','rox'),('usuario','usuario');
/*!40000 ALTER TABLE `usuarios` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2017-04-16 17:53:21

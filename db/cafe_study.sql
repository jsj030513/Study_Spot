-- MySQL dump 10.13  Distrib 8.0.46, for Win64 (x86_64)
--
-- Host: localhost    Database: cafe_study_db
-- ------------------------------------------------------
-- Server version	8.0.46

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
-- Table structure for table `cafe_facility`
--

DROP TABLE IF EXISTS `cafe_facility`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `cafe_facility` (
  `CAFE_ID` char(12) DEFAULT NULL,
  `OTL_FLG` char(1) DEFAULT NULL,
  `NOI_LVL` varchar(10) DEFAULT NULL,
  `WIFI_ST` varchar(10) DEFAULT NULL,
  `SEAT_TY` varchar(20) DEFAULT NULL,
  KEY `CAFE_ID` (`CAFE_ID`),
  CONSTRAINT `cafe_facility_ibfk_1` FOREIGN KEY (`CAFE_ID`) REFERENCES `cafe_info` (`CAFE_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cafe_facility`
--

LOCK TABLES `cafe_facility` WRITE;
/*!40000 ALTER TABLE `cafe_facility` DISABLE KEYS */;
/*!40000 ALTER TABLE `cafe_facility` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `cafe_info`
--

DROP TABLE IF EXISTS `cafe_info`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `cafe_info` (
  `CAFE_ID` char(12) NOT NULL,
  `CAFE_NM` varchar(50) NOT NULL,
  `LAT` decimal(15,10) NOT NULL,
  `LNT` decimal(15,10) NOT NULL,
  `ADDR` varchar(100) DEFAULT NULL,
  `TEL_NO` varchar(15) DEFAULT NULL,
  PRIMARY KEY (`CAFE_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cafe_info`
--

LOCK TABLES `cafe_info` WRITE;
/*!40000 ALTER TABLE `cafe_info` DISABLE KEYS */;
INSERT INTO `cafe_info` VALUES 
('CAFE00000001','요거프레소 천안백석대점',36.8422318000,127.1863968000,'충남 천안시 동남구 문암5길 23 1층',NULL),
('CAFE00000002','이디야커피 백석대북카페점',36.8425480000,127.1851386000,'충남 천안시 동남구 백석대학로 1-19 백석생활관 2층',NULL),
('CAFE00000003','빽다방 천안백석대점',36.8415442000,127.1817741000,'충남 천안시 동남구 문암로 80 1층',NULL),
('CAFE00000004','쉬는시간에',36.8411632000,127.1813398000,'충남 천안시 동남구 문암로 74 2층',NULL),
('CAFE00000005','일루',36.8416291000,127.1813844000,'충남 천안시 동남구 문암로 77',NULL),
('CAFE00000006','버터라운지',36.8410761000,127.1811639000,'충남 천안시 동남구 문암로 72 3층',NULL),
('CAFE00000007','카페블랙컨테이너',36.8410300000,127.1807455000,'충남 천안시 동남구 문암로 69 도진빌딩 2층',NULL),
('CAFE00000008','메가MGC커피 백석대점',36.8409956000,127.1810821000,'충남 천안시 동남구 문암로 70 1층',NULL),
('CAFE00000009','공차 천안백석대점',36.8408320000,127.1808966000,'충남 천안시 동남구 문암로 70 1층',NULL),
('CAFE00000010','이디야커피 천안백석대점',36.8404282000,127.1805363000,'충남 천안시 동남구 문암로 62 1-2층',NULL),
('CAFE00000011','컴포즈커피 천안백석대점',36.8405694000,127.1803125000,'충남 천안시 동남구 문암로 63 도연빌딩 1층',NULL),
('CAFE00000012','해피카페24 제이',36.8375047000,127.1826070000,'충남 천안시 동남구 백석대학로 1-15 백석문화대학교 창조관 2층',NULL),
('CAFE00000013','카페뷰리 백석대학교조형관점',36.8411384000,127.1880927000,'충남 천안시 동남구 백석대학로 1-18 15동 1층',NULL);
/*!40000 ALTER TABLE `cafe_info` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_master`
--

DROP TABLE IF EXISTS `user_master`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user_master` (
  `USER_ID` varchar(20) NOT NULL,
  `USER_PW` varchar(100) NOT NULL,
  `USER_NM` varchar(30) NOT NULL,
  `ROLE_TY` char(1) DEFAULT 'U',
  `REG_DT` date DEFAULT (curdate()),
  PRIMARY KEY (`USER_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_master`
--

LOCK TABLES `user_master` WRITE;
/*!40000 ALTER TABLE `user_master` DISABLE KEYS */;
/*!40000 ALTER TABLE `user_master` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2026-05-17 19:59:29

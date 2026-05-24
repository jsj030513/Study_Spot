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

DROP TABLE IF EXISTS `cafe_photo`;
DROP TABLE IF EXISTS `cafe_profile`;
DROP TABLE IF EXISTS `cafe_occupancy_status`;
DROP TABLE IF EXISTS `cafe_open_status`;
DROP TABLE IF EXISTS `owner_verification`;
DROP TABLE IF EXISTS `place_owner`;
DROP TABLE IF EXISTS `cafe_review`;
DROP TABLE IF EXISTS `place_master`;

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
-- Table structure for table `place_master`
--

DROP TABLE IF EXISTS `place_master`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `place_master` (
  `PLACE_ID` varchar(20) NOT NULL,
  `PLACE_NM` varchar(80) NOT NULL,
  `PLACE_TY` varchar(20) NOT NULL,
  `LAT` decimal(15,10) NOT NULL,
  `LNT` decimal(15,10) NOT NULL,
  `ADDR` varchar(255) DEFAULT NULL,
  `TEL_NO` varchar(30) DEFAULT NULL,
  `WIFI_ST` varchar(30) DEFAULT NULL,
  `OTL_ST` varchar(30) DEFAULT NULL,
  `NOI_LVL` varchar(30) DEFAULT NULL,
  `SEAT_TY` varchar(50) DEFAULT NULL,
  `DESC_TXT` varchar(1000) DEFAULT NULL,
  PRIMARY KEY (`PLACE_ID`),
  KEY `idx_place_master_type` (`PLACE_TY`),
  KEY `idx_place_master_name` (`PLACE_NM`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `place_master`
-- The current backend reads places from this unified table.
-- Existing cafe_info rows are copied here so /api/places?type=cafe can return cafe addresses.
--

LOCK TABLES `place_master` WRITE, `cafe_info` READ, `cafe_facility` READ;
/*!40000 ALTER TABLE `place_master` DISABLE KEYS */;
INSERT INTO `place_master`
(`PLACE_ID`, `PLACE_NM`, `PLACE_TY`, `LAT`, `LNT`, `ADDR`, `TEL_NO`, `WIFI_ST`, `OTL_ST`, `NOI_LVL`, `SEAT_TY`, `DESC_TXT`)
SELECT
  ci.`CAFE_ID`,
  ci.`CAFE_NM`,
  'cafe',
  ci.`LAT`,
  ci.`LNT`,
  ci.`ADDR`,
  ci.`TEL_NO`,
  COALESCE(cf.`WIFI_ST`, '보통'),
  CASE
    WHEN cf.`OTL_FLG` = 'Y' THEN '있음'
    WHEN cf.`OTL_FLG` = 'N' THEN '없음'
    ELSE '보통'
  END,
  COALESCE(cf.`NOI_LVL`, '보통'),
  COALESCE(cf.`SEAT_TY`, '일반 좌석'),
  CONCAT(ci.`CAFE_NM`, '의 공부하기 좋은 카페 정보입니다.')
FROM `cafe_info` ci
LEFT JOIN `cafe_facility` cf ON ci.`CAFE_ID` = cf.`CAFE_ID`;
INSERT INTO `place_master` 
(`PLACE_ID`, `PLACE_NM`, `PLACE_TY`, `LAT`, `LNT`, `ADDR`)
VALUES 
('LIB000000001', '백석대학교 학술정보관', 'library', 36.8377770, 127.1839946, '충남 천안시 동남구 백석대학로 1-12'),
('LIB000000002', '백석대학교 본부동 도서관', 'library', 36.8391882, 127.1858767, '충남 천안시 동남구 백석대학로 1-11'),
('LIB000000003', '백석문화대학 도서관', 'library', 36.8382586, 127.1829669, '충남 천안시 동남구 백석대학로 1-2'),

('CON000000001', 'CU 천안백석대정문점', 'convenience', 36.8414198, 127.1817066, '충남 천안시 동남구 문암로 78 1층'),
('CON000000002', '세븐일레븐 천안백석학생복지동점', 'convenience', 36.8406689, 127.1825567, '충남 천안시 동남구 백석대학로 1-9 2층 211호'),
('CON000000003', 'CU 천안백석대진리관점', 'convenience', 36.8401131, 127.1845193, '충남 천안시 동남구 백석대학로 1-1 지하1층 101호'),
('CON000000004', '이마트24 은혜관점', 'convenience', 36.8386476, 127.1819442, '충남 천안시 동남구 백석대학로 1-7 은혜관 101호'),
('CON000000005', 'GS25 자유관점', 'convenience', 36.8384998, 127.1832242, '충남 천안시 동남구 백석대학로 1-2'),
('CON000000006', 'CU 천안백석대지혜관점', 'convenience', 36.8385655, 127.1843555, '충남 천안시 동남구 백석대학로 1-1 지혜관동 3층 309호'),
('CON000000007', 'CU 천안백석대본부동점', 'convenience', 36.8391803, 127.1858902, '충남 천안시 동남구 백석대학로 1-11 백석대학교 본부동'),
('CON000000008', 'CU 천안백석대조형관점', 'convenience', 36.8409088, 127.1884576, '충남 천안시 동남구 백석대학로 1-18 1층 104,105,107호'),
('CON000000009', '세븐일레븐 천안백석대학로점', 'convenience', 36.8420231, 127.1868946, '충남 천안시 동남구 문암5길 25 1층'),
('CON000000010', 'GS25 백석생활관점', 'convenience', 36.8425553, 127.1851498, '충남 천안시 동남구 백석대학로 1-19 백석생활관 2층 202호'),
('CON000000011', '세븐일레븐 백석문암점', 'convenience', 36.8421389, 127.1825057, '충남 천안시 동남구 문암로 90 1층'),
('CON000000012', 'GS25 백석대점', 'convenience', 36.8413307, 127.1808794, '충남 천안시 동남구 문암4길 10-18 창이빌딩 1층'),
('CON000000013', 'GS25 백석대타운점', 'convenience', 36.8411190, 127.1800456, '충남 천안시 동남구 문암4길 7 1층');


/*!40000 ALTER TABLE `place_master` ENABLE KEYS */;
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
  PRIMARY KEY (`USER_ID`),
  CONSTRAINT chk_user_role CHECK (ROLE_TY IN ('U', 'O', 'A'))  -- 사용자 권한 제약조건
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_master`
--

LOCK TABLES `user_master` WRITE;
/*!40000 ALTER TABLE `user_master` DISABLE KEYS */;
/*!40000 ALTER TABLE `user_master` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `cafe_review`
--

DROP TABLE IF EXISTS `cafe_review`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `cafe_review` (
  `REVIEW_ID` varchar(20) NOT NULL,
  `PLACE_ID` varchar(20) NOT NULL,
  `USER_ID` varchar(20) NOT NULL,
  `REVIEW_TXT` varchar(100) NOT NULL,
  `CLEAN_TXT` varchar(100) NOT NULL,
  `SENTIMENT_TY` varchar(20) NOT NULL,
  `CLEAN_FLG` char(1) NOT NULL DEFAULT 'Y',
  `REG_DT` date DEFAULT (curdate()),
  PRIMARY KEY (`REVIEW_ID`),
  KEY `idx_cafe_review_place` (`PLACE_ID`),
  KEY `idx_cafe_review_user` (`USER_ID`),
  CONSTRAINT `cafe_review_place_fk` FOREIGN KEY (`PLACE_ID`) REFERENCES `place_master` (`PLACE_ID`) ON DELETE CASCADE,
  CONSTRAINT `cafe_review_user_fk` FOREIGN KEY (`USER_ID`) REFERENCES `user_master` (`USER_ID`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `place_owner`
--

DROP TABLE IF EXISTS `place_owner`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `place_owner` (
  `USER_ID` varchar(20) NOT NULL,
  `PLACE_ID` varchar(20) NOT NULL,
  PRIMARY KEY (`USER_ID`, `PLACE_ID`),
  KEY `idx_place_owner_place` (`PLACE_ID`),
  CONSTRAINT `place_owner_user_fk` FOREIGN KEY (`USER_ID`) REFERENCES `user_master` (`USER_ID`) ON DELETE CASCADE,
  CONSTRAINT `place_owner_place_fk` FOREIGN KEY (`PLACE_ID`) REFERENCES `place_master` (`PLACE_ID`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `owner_verification`
--

DROP TABLE IF EXISTS `owner_verification`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `owner_verification` (
  `VERIFICATION_ID` varchar(20) NOT NULL,
  `USER_ID` varchar(20) NOT NULL,
  `PLACE_ID` varchar(20) NOT NULL,
  `BUSINESS_NO` varchar(20) NOT NULL,
  `DOCUMENT_URL` varchar(500) NOT NULL,
  `STATUS_TY` varchar(20) NOT NULL DEFAULT 'PENDING',
  `REJECT_REASON` varchar(255) DEFAULT NULL,
  `REQ_DT` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `REVIEW_DT` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`VERIFICATION_ID`),
  KEY `idx_owner_verification_user` (`USER_ID`),
  KEY `idx_owner_verification_place` (`PLACE_ID`),
  KEY `idx_owner_verification_status` (`STATUS_TY`),
  CONSTRAINT `owner_verification_user_fk` FOREIGN KEY (`USER_ID`) REFERENCES `user_master` (`USER_ID`) ON DELETE CASCADE,
  CONSTRAINT `owner_verification_place_fk` FOREIGN KEY (`PLACE_ID`) REFERENCES `place_master` (`PLACE_ID`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `cafe_open_status`
--

DROP TABLE IF EXISTS `cafe_open_status`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `cafe_open_status` (
  `PLACE_ID` varchar(20) NOT NULL,
  `OPEN_FLG` char(1) NOT NULL DEFAULT 'N',
  `STATUS_MSG` varchar(100) DEFAULT NULL,
  `UPDATED_AT` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`PLACE_ID`),
  CONSTRAINT `cafe_open_status_place_fk` FOREIGN KEY (`PLACE_ID`) REFERENCES `place_master` (`PLACE_ID`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `cafe_occupancy_status`
--

DROP TABLE IF EXISTS `cafe_occupancy_status`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `cafe_occupancy_status` (
  `PLACE_ID` varchar(20) NOT NULL,
  `CURRENT_CNT` int NOT NULL DEFAULT 0,
  `CAPACITY_CNT` int NOT NULL DEFAULT 1,
  `CONGESTION_TY` varchar(20) NOT NULL DEFAULT 'LOW',
  `UPDATED_AT` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`PLACE_ID`),
  CONSTRAINT `cafe_occupancy_status_place_fk` FOREIGN KEY (`PLACE_ID`) REFERENCES `place_master` (`PLACE_ID`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `cafe_profile`
--

DROP TABLE IF EXISTS `cafe_profile`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `cafe_profile` (
  `PLACE_ID` varchar(20) NOT NULL,
  `INTRO_TXT` varchar(1000) DEFAULT NULL,
  `NOTICE_TXT` varchar(1000) DEFAULT NULL,
  `OPENING_HOURS` varchar(255) DEFAULT NULL,
  `MENU_TXT` varchar(1000) DEFAULT NULL,
  `SNS_URL` varchar(255) DEFAULT NULL,
  `UPDATED_AT` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`PLACE_ID`),
  CONSTRAINT `cafe_profile_place_fk` FOREIGN KEY (`PLACE_ID`) REFERENCES `place_master` (`PLACE_ID`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `cafe_photo`
--

DROP TABLE IF EXISTS `cafe_photo`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `cafe_photo` (
  `PHOTO_ID` varchar(20) NOT NULL,
  `PLACE_ID` varchar(20) NOT NULL,
  `PHOTO_URL` varchar(500) NOT NULL,
  `DISPLAY_ORD` int NOT NULL DEFAULT 1,
  `REG_DT` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`PHOTO_ID`),
  KEY `idx_cafe_photo_place` (`PLACE_ID`),
  CONSTRAINT `cafe_photo_place_fk` FOREIGN KEY (`PLACE_ID`) REFERENCES `place_master` (`PLACE_ID`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2026-05-17 19:59:29


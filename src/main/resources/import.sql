-- --------------------------------------------------------
-- Host:                         127.0.0.1
-- Server version:               5.7.24 - MySQL Community Server (GPL)
-- Server OS:                    Win64
-- HeidiSQL Version:             10.2.0.5599
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;


-- Dumping database structure for eshopdb
CREATE DATABASE IF NOT EXISTS `eshopdb` /*!40100 DEFAULT CHARACTER SET utf16 COLLATE utf16_bin */;
USE `eshopdb`;

-- Dumping structure for table eshopdb.address
CREATE TABLE IF NOT EXISTS `address` (
  `id` int(11) NOT NULL,
  `apartment` varchar(255) COLLATE utf16_bin DEFAULT NULL,
  `building` int(11) DEFAULT NULL,
  `city` varchar(255) COLLATE utf16_bin DEFAULT NULL,
  `country` varchar(255) COLLATE utf16_bin DEFAULT NULL,
  `street` varchar(255) COLLATE utf16_bin DEFAULT NULL,
  `zip` varchar(255) COLLATE utf16_bin DEFAULT NULL,
  `user_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKda8tuywtf0gb6sedwk7la1pgi` (`user_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf16 COLLATE=utf16_bin;

-- Dumping data for table eshopdb.address: 0 rows
/*!40000 ALTER TABLE `address` DISABLE KEYS */;
/*!40000 ALTER TABLE `address` ENABLE KEYS */;

-- Dumping structure for table eshopdb.book
CREATE TABLE IF NOT EXISTS `book` (
  `id` int(11) NOT NULL,
  `amount` int(11) DEFAULT NULL,
  `name` varchar(255) COLLATE utf16_bin DEFAULT NULL,
  `pages` int(11) DEFAULT NULL,
  `price` float DEFAULT NULL,
  `sold` int(11) DEFAULT NULL,
  `book_category_id` int(11) DEFAULT NULL,
  `author` varchar(255) COLLATE utf16_bin DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKs8rqq96mvfrfsj9euw5mn973t` (`book_category_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf16 COLLATE=utf16_bin;

-- Dumping data for table eshopdb.book: 1 rows
/*!40000 ALTER TABLE `book` DISABLE KEYS */;
INSERT IGNORE INTO `book` (`id`, `amount`, `name`, `pages`, `price`, `sold`, `book_category_id`, `author`) VALUES
	(1, 20, 'Sherlock Holmes', 300, 250, 10, 1, 'Arthur Ignatius Conan Doyle'),
	(2, 10, 'Moby Dick', 600, 200, 20, 2, 'Herman Melville');
/*!40000 ALTER TABLE `book` ENABLE KEYS */;

-- Dumping structure for table eshopdb.book_category
CREATE TABLE IF NOT EXISTS `book_category` (
  `id` int(11) NOT NULL,
  `name` varchar(255) COLLATE utf16_bin NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf16 COLLATE=utf16_bin;

-- Dumping data for table eshopdb.book_category: 1 rows
/*!40000 ALTER TABLE `book_category` DISABLE KEYS */;
INSERT IGNORE INTO `book_category` (`id`, `name`) VALUES
	(1, 'Detective'),
	(2, 'Novel');
/*!40000 ALTER TABLE `book_category` ENABLE KEYS */;

-- Dumping structure for table eshopdb.hibernate_sequence
CREATE TABLE IF NOT EXISTS `hibernate_sequence` (
  `next_val` bigint(20) DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf16 COLLATE=utf16_bin;

-- Dumping data for table eshopdb.hibernate_sequence: 5 rows
/*!40000 ALTER TABLE `hibernate_sequence` DISABLE KEYS */;
INSERT IGNORE INTO `hibernate_sequence` (`next_val`) VALUES
	(1),
	(1),
	(1),
	(1),
	(1);
/*!40000 ALTER TABLE `hibernate_sequence` ENABLE KEYS */;

-- Dumping structure for table eshopdb.order_entity
CREATE TABLE IF NOT EXISTS `order_entity` (
  `id` int(11) NOT NULL,
  `books` tinyblob,
  `customer_address` varchar(255) COLLATE utf16_bin DEFAULT NULL,
  `delivery_method` int(11) DEFAULT NULL,
  `order_status` int(11) DEFAULT NULL,
  `payment_method` int(11) DEFAULT NULL,
  `payment_status` int(11) DEFAULT NULL,
  `user_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKdikderaycp8pwoigw489wohfa` (`user_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf16 COLLATE=utf16_bin;

-- Dumping data for table eshopdb.order_entity: 0 rows
/*!40000 ALTER TABLE `order_entity` DISABLE KEYS */;
/*!40000 ALTER TABLE `order_entity` ENABLE KEYS */;

-- Dumping structure for table eshopdb.user
CREATE TABLE IF NOT EXISTS `user` (
  `id` int(11) NOT NULL,
  `birthdate` varchar(255) COLLATE utf16_bin DEFAULT NULL,
  `email` varchar(255) COLLATE utf16_bin NOT NULL,
  `first_name` varchar(255) COLLATE utf16_bin NOT NULL,
  `last_name` varchar(255) COLLATE utf16_bin NOT NULL,
  `password` varchar(255) COLLATE utf16_bin NOT NULL,
  `role` varchar(255) COLLATE utf16_bin NOT NULL,
  `username` varchar(255) COLLATE utf16_bin NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf16 COLLATE=utf16_bin;

-- Dumping data for table eshopdb.user: 0 rows
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
/*!40000 ALTER TABLE `user` ENABLE KEYS */;

/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;

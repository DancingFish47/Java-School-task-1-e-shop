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
  `version` int(11) DEFAULT NULL,
  `apartment` varchar(255) COLLATE utf16_bin DEFAULT NULL,
  `building` varchar(255) COLLATE utf16_bin DEFAULT NULL,
  `city` varchar(255) COLLATE utf16_bin DEFAULT NULL,
  `country` varchar(255) COLLATE utf16_bin DEFAULT NULL,
  `street` varchar(255) COLLATE utf16_bin DEFAULT NULL,
  `zip` varchar(255) COLLATE utf16_bin DEFAULT NULL,
  `user_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKtrot3fhihthq0l9koef094xwr` (`user_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf16 COLLATE=utf16_bin;

-- Dumping data for table eshopdb.address: 0 rows
/*!40000 ALTER TABLE `address` DISABLE KEYS */;
/*!40000 ALTER TABLE `address` ENABLE KEYS */;

-- Dumping structure for table eshopdb.book
CREATE TABLE IF NOT EXISTS `book` (
  `id` int(11) NOT NULL,
  `version` int(11) DEFAULT NULL,
  `amount` int(11) DEFAULT NULL,
  `author` varchar(255) COLLATE utf16_bin DEFAULT NULL,
  `name` varchar(255) COLLATE utf16_bin DEFAULT NULL,
  `pages` int(11) DEFAULT NULL,
  `price` float DEFAULT NULL,
  `sold` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf16 COLLATE=utf16_bin;

-- Dumping data for table eshopdb.book: 1 rows
/*!40000 ALTER TABLE `book` DISABLE KEYS */;
INSERT IGNORE INTO `book` (`id`, `version`, `amount`, `author`, `name`, `pages`, `price`, `sold`) VALUES
	(1, 1, 20, 'Herman Melvill', 'Moby Dick', 300, 250, 20);
/*!40000 ALTER TABLE `book` ENABLE KEYS */;

-- Dumping structure for table eshopdb.book_book_category
CREATE TABLE IF NOT EXISTS `book_book_category` (
  `Book_id` int(11) NOT NULL,
  `bookCategories_id` int(11) NOT NULL,
  PRIMARY KEY (`Book_id`,`bookCategories_id`),
  UNIQUE KEY `UK_kk48gyv8n1xt292iqtptgd0jb` (`bookCategories_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf16 COLLATE=utf16_bin;

-- Dumping data for table eshopdb.book_book_category: 1 rows
/*!40000 ALTER TABLE `book_book_category` DISABLE KEYS */;
INSERT IGNORE INTO `book_book_category` (`Book_id`, `bookCategories_id`) VALUES
	(1, 1);
/*!40000 ALTER TABLE `book_book_category` ENABLE KEYS */;

-- Dumping structure for table eshopdb.book_category
CREATE TABLE IF NOT EXISTS `book_category` (
  `id` int(11) NOT NULL,
  `version` int(11) DEFAULT NULL,
  `name` varchar(255) COLLATE utf16_bin DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf16 COLLATE=utf16_bin;

-- Dumping data for table eshopdb.book_category: 1 rows
/*!40000 ALTER TABLE `book_category` DISABLE KEYS */;
INSERT IGNORE INTO `book_category` (`id`, `version`, `name`) VALUES
	(1, 1, 'Novel');
/*!40000 ALTER TABLE `book_category` ENABLE KEYS */;

-- Dumping structure for table eshopdb.delivery_method
CREATE TABLE IF NOT EXISTS `delivery_method` (
  `id` int(11) NOT NULL,
  `version` int(11) DEFAULT NULL,
  `method` varchar(255) COLLATE utf16_bin DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf16 COLLATE=utf16_bin;

-- Dumping data for table eshopdb.delivery_method: 0 rows
/*!40000 ALTER TABLE `delivery_method` DISABLE KEYS */;
/*!40000 ALTER TABLE `delivery_method` ENABLE KEYS */;

-- Dumping structure for table eshopdb.hibernate_sequence
CREATE TABLE IF NOT EXISTS `hibernate_sequence` (
  `next_val` bigint(20) DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf16 COLLATE=utf16_bin;

-- Dumping data for table eshopdb.hibernate_sequence: 10 rows
/*!40000 ALTER TABLE `hibernate_sequence` DISABLE KEYS */;
INSERT IGNORE INTO `hibernate_sequence` (`next_val`) VALUES
	(1),
	(1),
	(1),
	(1),
	(1),
	(1),
	(1),
	(1),
	(1),
	(1);
/*!40000 ALTER TABLE `hibernate_sequence` ENABLE KEYS */;

-- Dumping structure for table eshopdb.order_book
CREATE TABLE IF NOT EXISTS `order_book` (
  `OrderEntity_id` int(11) NOT NULL,
  `books_id` int(11) NOT NULL,
  PRIMARY KEY (`OrderEntity_id`,`books_id`),
  UNIQUE KEY `UK_fw5fl3lrux4t4b4o3mtgu1cpo` (`books_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf16 COLLATE=utf16_bin;

-- Dumping data for table eshopdb.order_book: 0 rows
/*!40000 ALTER TABLE `order_book` DISABLE KEYS */;
/*!40000 ALTER TABLE `order_book` ENABLE KEYS */;

-- Dumping structure for table eshopdb.order_books
CREATE TABLE IF NOT EXISTS `order_books` (
  `order_entity_id` int(11) NOT NULL,
  `books_id` int(11) NOT NULL,
  PRIMARY KEY (`order_entity_id`,`books_id`),
  UNIQUE KEY `UK_afi39p96y0q1s36dffi0tx4bm` (`books_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf16 COLLATE=utf16_bin;

-- Dumping data for table eshopdb.order_books: 0 rows
/*!40000 ALTER TABLE `order_books` DISABLE KEYS */;
/*!40000 ALTER TABLE `order_books` ENABLE KEYS */;

-- Dumping structure for table eshopdb.order_status
CREATE TABLE IF NOT EXISTS `order_status` (
  `id` int(11) NOT NULL,
  `version` int(11) DEFAULT NULL,
  `status` varchar(255) COLLATE utf16_bin DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf16 COLLATE=utf16_bin;

-- Dumping data for table eshopdb.order_status: 0 rows
/*!40000 ALTER TABLE `order_status` DISABLE KEYS */;
/*!40000 ALTER TABLE `order_status` ENABLE KEYS */;

-- Dumping structure for table eshopdb.payment_method
CREATE TABLE IF NOT EXISTS `payment_method` (
  `id` int(11) NOT NULL,
  `version` int(11) DEFAULT NULL,
  `method` varchar(255) COLLATE utf16_bin DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf16 COLLATE=utf16_bin;

-- Dumping data for table eshopdb.payment_method: 0 rows
/*!40000 ALTER TABLE `payment_method` DISABLE KEYS */;
/*!40000 ALTER TABLE `payment_method` ENABLE KEYS */;

-- Dumping structure for table eshopdb.payment_status
CREATE TABLE IF NOT EXISTS `payment_status` (
  `id` int(11) NOT NULL,
  `version` int(11) DEFAULT NULL,
  `status` varchar(255) COLLATE utf16_bin DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf16 COLLATE=utf16_bin;

-- Dumping data for table eshopdb.payment_status: 0 rows
/*!40000 ALTER TABLE `payment_status` DISABLE KEYS */;
/*!40000 ALTER TABLE `payment_status` ENABLE KEYS */;

-- Dumping structure for table eshopdb.user
CREATE TABLE IF NOT EXISTS `user` (
  `id` int(11) NOT NULL,
  `version` int(11) DEFAULT NULL,
  `birthdate` varchar(255) COLLATE utf16_bin DEFAULT NULL,
  `email` varchar(255) COLLATE utf16_bin DEFAULT NULL,
  `first_name` varchar(255) COLLATE utf16_bin DEFAULT NULL,
  `last_name` varchar(255) COLLATE utf16_bin DEFAULT NULL,
  `password` varchar(255) COLLATE utf16_bin DEFAULT NULL,
  `username` varchar(255) COLLATE utf16_bin DEFAULT NULL,
  `role_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK7p4y6cmlfd1fd1istmuqjwkrt` (`role_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf16 COLLATE=utf16_bin;

-- Dumping data for table eshopdb.user: 0 rows
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
/*!40000 ALTER TABLE `user` ENABLE KEYS */;

-- Dumping structure for table eshopdb.user_role
CREATE TABLE IF NOT EXISTS `user_role` (
  `id` int(11) NOT NULL,
  `version` int(11) DEFAULT NULL,
  `role` varchar(255) COLLATE utf16_bin DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf16 COLLATE=utf16_bin;

-- Dumping data for table eshopdb.user_role: 0 rows
/*!40000 ALTER TABLE `user_role` DISABLE KEYS */;
/*!40000 ALTER TABLE `user_role` ENABLE KEYS */;

/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;

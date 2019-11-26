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
CREATE DATABASE IF NOT EXISTS `eshopdb` /*!40100 DEFAULT CHARACTER SET latin1 */;
USE `eshopdb`;

-- Dumping structure for table eshopdb.address
CREATE TABLE IF NOT EXISTS `address` (
  `id` int(11) NOT NULL,
  `created_date` date DEFAULT NULL,
  `version` int(11) DEFAULT NULL,
  `apartment` varchar(255) DEFAULT NULL,
  `building` varchar(255) DEFAULT NULL,
  `city` varchar(255) DEFAULT NULL,
  `country` varchar(255) DEFAULT NULL,
  `street` varchar(255) DEFAULT NULL,
  `zip` varchar(255) DEFAULT NULL,
  `user_id` int(11) DEFAULT NULL,
  `address_status` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKr845mby648p5m0gx8cyyr1d55` (`address_status`),
  KEY `FKtrot3fhihthq0l9koef094xwr` (`user_id`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- Dumping data for table eshopdb.address: 0 rows
/*!40000 ALTER TABLE `address` DISABLE KEYS */;
/*!40000 ALTER TABLE `address` ENABLE KEYS */;

-- Dumping structure for table eshopdb.address_status
CREATE TABLE IF NOT EXISTS `address_status` (
  `id` int(11) NOT NULL,
  `created_date` date DEFAULT NULL,
  `version` int(11) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- Dumping data for table eshopdb.address_status: 2 rows
/*!40000 ALTER TABLE `address_status` DISABLE KEYS */;
INSERT IGNORE INTO `address_status` (`id`, `created_date`, `version`, `name`) VALUES
	(1, '2019-11-26', 0, 'DELETED'),
	(2, '2019-11-26', 0, 'EXISTS');
/*!40000 ALTER TABLE `address_status` ENABLE KEYS */;

-- Dumping structure for table eshopdb.book
CREATE TABLE IF NOT EXISTS `book` (
  `id` int(11) NOT NULL,
  `created_date` date DEFAULT NULL,
  `version` int(11) DEFAULT NULL,
  `amount` int(11) DEFAULT NULL,
  `author` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `pages` int(11) DEFAULT NULL,
  `price` float DEFAULT NULL,
  `sold` int(11) DEFAULT NULL,
  `category_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKj7aaobjge68fn1owlrob2twyg` (`category_id`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- Dumping data for table eshopdb.book: 7 rows
/*!40000 ALTER TABLE `book` DISABLE KEYS */;
INSERT IGNORE INTO `book` (`id`, `created_date`, `version`, `amount`, `author`, `name`, `pages`, `price`, `sold`, `category_id`) VALUES
	(1, '2019-10-17', 65, 10, 'Herman Melvill', 'Moby Dick', 600, 20, 25, 1),
	(2, '2019-10-17', 62, 10, 'A.S.Pushkin', 'Captain\'s daughter', 400, 10, 11, 1),
	(3, '2019-10-17', 66, 10, 'V.Nabokov', 'Pale Fire', 200, 5, 10, 1),
	(4, '2019-10-20', 16, 10, 'A.C.Doyle', 'Sherlock Holmes', 500, 40, 15, 2),
	(5, '2019-10-20', 17, 10, 'Stieg Larsson', 'The Girl with the Dragon Tattoo', 250, 100, 10, 2),
	(6, '2019-10-20', 24, 10, 'Gillian Flynn', 'Gone Girl', 700, 30, 10, 2),
	(7, '2019-10-23', 26, 0, 'Sun Tzu', 'The Art of War', 400, 120, 10, 1),
	(8, '2019-11-26', 0, 10, 'Bradley Hope, Tom Wright', 'Billion Dollar Whale', 900, 35, 5, 4),
	(9, '2019-11-26', 0, 10, 'Emily Bronte', 'Wuthering Heights', 600, 20, 2, 3),
	(10, '2019-11-26', 0, 10, 'Andy Weir', 'The Martian', 500, 25, 1, 6),
	(11, '2019-11-26', 0, 10, 'A.G. Riddle', 'Winter World (The Long Winter)', 400, 40, 3, 6),
	(12, '2019-11-26', 0, 10, 'BLIZZARD ENTERTAINMENT', 'World of Warcraft: Chronicle Volume 1', 700, 60, 1, 5),
	(13, '2019-11-26', 0, 10, 'Ayn Rand', 'Atlas Shrugged', 500, 40, 0, 7),
	(14, '2019-11-26', 0, 10, 'Matt Burns', 'World of Warcraft: Legion #1', 100, 20, 0, 5),
	(15, '2019-11-26', 0, 10, 'David Lagercrantz', 'The Girl Who Lived Twice', 400, 20, 0, 2),
	(16, '2019-11-26', 0, 10, 'William Golding', 'Lord of the Flies', 600, 30, 10, 7),
	(17, '2019-11-26', 0, 10, 'J. D. Salinger', 'The Catcher in the Rye', 500, 100, 0, 7),
	(18, '2019-11-26', 0, 10, 'Robert Harris', 'The Second Sleep', 300, 50, 2, 2),
	(19, '2019-11-26', 0, 10, 'Matt Burns', 'World of Warcraft: Legion #2', 100, 20, 1, 5);
/*!40000 ALTER TABLE `book` ENABLE KEYS */;

-- Dumping structure for table eshopdb.book_category
CREATE TABLE IF NOT EXISTS `book_category` (
  `id` int(11) NOT NULL,
  `created_date` date DEFAULT NULL,
  `version` int(11) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- Dumping data for table eshopdb.book_category: 3 rows
/*!40000 ALTER TABLE `book_category` DISABLE KEYS */;
INSERT IGNORE INTO `book_category` (`id`, `created_date`, `version`, `name`) VALUES
	(3, '2019-11-25', 0, 'Romance'),
	(1, '2019-11-25', 0, 'Novel'),
	(4, '2019-11-26', 0, 'Finance'),
	(2, '2019-11-25', 0, 'Detective'),
	(5, '2019-11-26', 0, 'Comics'),
	(6, '2019-11-26', 0, 'Science Fiction'),
	(7, '2019-11-26', 0, 'Classics');
/*!40000 ALTER TABLE `book_category` ENABLE KEYS */;

-- Dumping structure for table eshopdb.delivery_method
CREATE TABLE IF NOT EXISTS `delivery_method` (
  `id` int(11) NOT NULL,
  `created_date` date DEFAULT NULL,
  `version` int(11) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- Dumping data for table eshopdb.delivery_method: 2 rows
/*!40000 ALTER TABLE `delivery_method` DISABLE KEYS */;
INSERT IGNORE INTO `delivery_method` (`id`, `created_date`, `version`, `name`) VALUES
	(1, '2019-10-23', 0, 'FAST'),
	(2, '2019-10-23', 0, 'SLOW');
/*!40000 ALTER TABLE `delivery_method` ENABLE KEYS */;

-- Dumping structure for table eshopdb.hibernate_sequence
CREATE TABLE IF NOT EXISTS `hibernate_sequence` (
  `next_val` bigint(20) DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- Dumping data for table eshopdb.hibernate_sequence: 9 rows
/*!40000 ALTER TABLE `hibernate_sequence` DISABLE KEYS */;
INSERT IGNORE INTO `hibernate_sequence` (`next_val`) VALUES
	(258),
	(258),
	(258),
	(258),
	(258),
	(258),
	(258),
	(258),
	(258);
/*!40000 ALTER TABLE `hibernate_sequence` ENABLE KEYS */;

-- Dumping structure for table eshopdb.orders
CREATE TABLE IF NOT EXISTS `orders` (
  `id` int(11) NOT NULL,
  `created_date` date DEFAULT NULL,
  `version` int(11) DEFAULT NULL,
  `totalPrice` float DEFAULT NULL,
  `address_id` int(11) DEFAULT NULL,
  `deliveryMethod_id` int(11) DEFAULT NULL,
  `orderStatus_id` int(11) DEFAULT NULL,
  `paymentMethod_id` int(11) DEFAULT NULL,
  `paymentStatus_id` int(11) DEFAULT NULL,
  `user_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKky1pbb8qo6fa1hg5m5tmcb962` (`address_id`),
  KEY `FKc3773rir9odyf3fsytc7mr4mn` (`deliveryMethod_id`),
  KEY `FK1k8meh5vonqpdb7576wcjnt7b` (`orderStatus_id`),
  KEY `FK9c6dagllwd96ltkb96uo1uf8w` (`paymentMethod_id`),
  KEY `FK6dwwtxyfk3whxr5gp9ulgp5ss` (`paymentStatus_id`),
  KEY `FKsgwj42spshwc3eestq5vk520j` (`user_id`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- Dumping data for table eshopdb.orders: 1 rows
/*!40000 ALTER TABLE `orders` DISABLE KEYS */;
/*!40000 ALTER TABLE `orders` ENABLE KEYS */;

-- Dumping structure for table eshopdb.order_books
CREATE TABLE IF NOT EXISTS `order_books` (
  `order_id` int(11) NOT NULL,
  `book_id` int(11) NOT NULL,
  KEY `FKdrsa96ij2kw3sxsy7wsnua8ac` (`book_id`),
  KEY `FKfo05ca9bxto7jnhw9nsdmlhjq` (`order_id`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- Dumping data for table eshopdb.order_books: 6 rows
/*!40000 ALTER TABLE `order_books` DISABLE KEYS */;
/*!40000 ALTER TABLE `order_books` ENABLE KEYS */;

-- Dumping structure for table eshopdb.order_status
CREATE TABLE IF NOT EXISTS `order_status` (
  `id` int(11) NOT NULL,
  `created_date` date DEFAULT NULL,
  `version` int(11) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- Dumping data for table eshopdb.order_status: 5 rows
/*!40000 ALTER TABLE `order_status` DISABLE KEYS */;
INSERT IGNORE INTO `order_status` (`id`, `created_date`, `version`, `name`) VALUES
	(1, '2019-10-23', 0, 'PACKING'),
	(2, '2019-10-23', 0, 'SENT'),
	(3, '2019-10-23', 0, 'RECEIVED'),
	(4, '2019-10-24', 0, 'REGISTERED'),
	(5, '2019-10-28', 0, 'TEMPORDER');
/*!40000 ALTER TABLE `order_status` ENABLE KEYS */;

-- Dumping structure for table eshopdb.payment_method
CREATE TABLE IF NOT EXISTS `payment_method` (
  `id` int(11) NOT NULL,
  `created_date` date DEFAULT NULL,
  `version` int(11) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- Dumping data for table eshopdb.payment_method: 2 rows
/*!40000 ALTER TABLE `payment_method` DISABLE KEYS */;
INSERT IGNORE INTO `payment_method` (`id`, `created_date`, `version`, `name`) VALUES
	(1, '2019-10-23', 0, 'CASH'),
	(2, '2019-10-23', 0, 'CREDIT');
/*!40000 ALTER TABLE `payment_method` ENABLE KEYS */;

-- Dumping structure for table eshopdb.payment_status
CREATE TABLE IF NOT EXISTS `payment_status` (
  `id` int(11) NOT NULL,
  `created_date` date DEFAULT NULL,
  `version` int(11) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- Dumping data for table eshopdb.payment_status: 2 rows
/*!40000 ALTER TABLE `payment_status` DISABLE KEYS */;
INSERT IGNORE INTO `payment_status` (`id`, `created_date`, `version`, `name`) VALUES
	(1, '2019-10-23', 0, 'PAYED'),
	(2, '2019-10-23', 0, 'NOT PAYED');
/*!40000 ALTER TABLE `payment_status` ENABLE KEYS */;

-- Dumping structure for table eshopdb.user
CREATE TABLE IF NOT EXISTS `user` (
  `id` int(11) NOT NULL,
  `created_date` date DEFAULT NULL,
  `version` int(11) DEFAULT NULL,
  `birthdate` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `first_name` varchar(255) DEFAULT NULL,
  `last_name` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `role` varchar(255) DEFAULT NULL,
  `username` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_e6gkqunxajvyxl5uctpl2vl2p` (`email`),
  UNIQUE KEY `UK_jreodf78a7pl5qidfh43axdfb` (`username`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- Dumping data for table eshopdb.user: 2 rows
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT IGNORE INTO `user` (`id`, `created_date`, `version`, `birthdate`, `email`, `first_name`, `last_name`, `password`, `role`, `username`) VALUES
	(1, '2019-10-22', 0, '1996-06-11', 'admin@mail.ru', 'Admin', 'Adminovich', '$2a$10$mxKxcqHc8LRGMz29UaYFeesDDuYSz6hGVWgLPD6rpaydF4mfy5XLe', 'Admin', 'Admin');
/*!40000 ALTER TABLE `user` ENABLE KEYS */;

/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;

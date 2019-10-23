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
  PRIMARY KEY (`id`),
  KEY `FKtrot3fhihthq0l9koef094xwr` (`user_id`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- Dumping data for table eshopdb.address: 6 rows
/*!40000 ALTER TABLE `address` DISABLE KEYS */;
INSERT IGNORE INTO `address` (`id`, `created_date`, `version`, `apartment`, `building`, `city`, `country`, `street`, `zip`, `user_id`) VALUES
	(15, '2019-10-20', 5, '20', '99', 'City', 'Usa', '1st Avenue', '400004', 3),
	(19, '2019-10-21', 0, '12331233333333333', '3833', 'Magnitogorsk', 'Russia', 'Marksa', '455555', 3),
	(22, '2019-10-22', 0, '324', '123', 'Moscow', 'Russia', 'River', '455044', 3),
	(23, '2019-10-22', 0, '345', '234', 'NewYork', 'United', 'Buckinghem', '4563', 3),
	(24, '2019-10-22', 0, '23', '123', 'Warsawa', 'Poland', 'PolskyStreet', '455055', 3),
	(25, '2019-10-22', 0, '123', '123', 'Los-Santos', 'Usa', 'GrooveStreet', '1232', 3);
/*!40000 ALTER TABLE `address` ENABLE KEYS */;

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

-- Dumping data for table eshopdb.book: 6 rows
/*!40000 ALTER TABLE `book` DISABLE KEYS */;
INSERT IGNORE INTO `book` (`id`, `created_date`, `version`, `amount`, `author`, `name`, `pages`, `price`, `sold`, `category_id`) VALUES
	(1, '2019-10-17', 0, 20, 'Herman Melvill', 'Moby Dick', 600, 20, 10, 1),
	(2, '2019-10-17', 0, 20, 'A.S.Pushkin', 'Captain\'s daughter', 400, 10, 5, 1),
	(3, '2019-10-17', 0, 30, 'V.Nabokov', 'Pale Fire', 200, 5, 2, 1),
	(4, '2019-10-20', 0, 40, 'A.C.Doyle', 'Sherlock Holmes', 500, 40, 20, 2),
	(5, '2019-10-20', 0, 50, 'Stieg Larsson', 'The Girl with the Dragon Tattoo', 250, 25, 60, 2),
	(6, '2019-10-20', 0, 60, 'Gillian Flynn', 'Gone Girl', 700, 30, 30, 2);
/*!40000 ALTER TABLE `book` ENABLE KEYS */;

-- Dumping structure for table eshopdb.book_category
CREATE TABLE IF NOT EXISTS `book_category` (
  `id` int(11) NOT NULL,
  `created_date` date DEFAULT NULL,
  `version` int(11) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- Dumping data for table eshopdb.book_category: 2 rows
/*!40000 ALTER TABLE `book_category` DISABLE KEYS */;
INSERT IGNORE INTO `book_category` (`id`, `created_date`, `version`, `name`) VALUES
	(1, '2019-10-17', 0, 'Novel'),
	(2, '2019-10-20', 0, 'Detective');
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
	(26),
	(26),
	(26),
	(26),
	(26),
	(26),
	(26),
	(26),
	(26);
/*!40000 ALTER TABLE `hibernate_sequence` ENABLE KEYS */;

-- Dumping structure for table eshopdb.orders
CREATE TABLE IF NOT EXISTS `orders` (
  `id` int(11) NOT NULL,
  `created_date` date DEFAULT NULL,
  `version` int(11) DEFAULT NULL,
  `address_id` int(11) DEFAULT NULL,
  `deliveryMethod_id` int(11) DEFAULT NULL,
  `orderStatus_id` int(11) DEFAULT NULL,
  `paymentMethod_id` int(11) DEFAULT NULL,
  `paymentStatus_id` int(11) DEFAULT NULL,
  `user_id` int(11) DEFAULT NULL,
  `totalPrice` float DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKky1pbb8qo6fa1hg5m5tmcb962` (`address_id`),
  KEY `FKc3773rir9odyf3fsytc7mr4mn` (`deliveryMethod_id`),
  KEY `FK1k8meh5vonqpdb7576wcjnt7b` (`orderStatus_id`),
  KEY `FK9c6dagllwd96ltkb96uo1uf8w` (`paymentMethod_id`),
  KEY `FK6dwwtxyfk3whxr5gp9ulgp5ss` (`paymentStatus_id`),
  KEY `FKsgwj42spshwc3eestq5vk520j` (`user_id`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- Dumping data for table eshopdb.orders: 0 rows
/*!40000 ALTER TABLE `orders` DISABLE KEYS */;
/*!40000 ALTER TABLE `orders` ENABLE KEYS */;

-- Dumping structure for table eshopdb.order_books
CREATE TABLE IF NOT EXISTS `order_books` (
  `order_id` int(11) NOT NULL,
  `book_id` int(11) NOT NULL,
  PRIMARY KEY (`order_id`,`book_id`),
  KEY `FKdrsa96ij2kw3sxsy7wsnua8ac` (`book_id`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- Dumping data for table eshopdb.order_books: 0 rows
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

-- Dumping data for table eshopdb.order_status: 0 rows
/*!40000 ALTER TABLE `order_status` DISABLE KEYS */;
INSERT IGNORE INTO `order_status` (`id`, `created_date`, `version`, `name`) VALUES
	(1, '2019-10-23', 0, 'PACKING'),
	(2, '2019-10-23', 0, 'SENT'),
	(3, '2019-10-23', 0, 'RECEIVED');
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

-- Dumping data for table eshopdb.user: 4 rows
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT IGNORE INTO `user` (`id`, `created_date`, `version`, `birthdate`, `email`, `first_name`, `last_name`, `password`, `role`, `username`) VALUES
	(3, '2019-10-17', 31, '1996-06-19', 'roman@mail.ru', 'Roma', 'Rychkov', '$2a$10$Xb4yh4FCuwJvkZgkDm9pqebMg1eBjgkLrNUHBI2uKeJ67U8Hv2y7G', 'User', 'Roman'),
	(4, '2019-10-17', 1, '1996-06-19', 'snake@mail.ru', 'Snake', 'Snake', '$2a$10$5XB3.98JOePhoStYeXbxCuLmHNZF3/0lTp4ERo9K.GIPPn11qsGte', 'User', 'Snake'),
	(21, '2019-10-22', 0, '1996-06-11', 'admin@mail.ru', 'Admin', 'Adminovich', '$2a$10$mxKxcqHc8LRGMz29UaYFeesDDuYSz6hGVWgLPD6rpaydF4mfy5XLe', 'Admin', 'Admin'),
	(5, '2019-10-18', 0, '1998-03-19', 'john@mail.ru', 'Snow', 'Jack', '$2a$10$UDUxdZqWesFTgyLe2aI3bOsLpor9E1s7VIF0GRXn3hO7nf4kgppvq', 'User', 'John');
/*!40000 ALTER TABLE `user` ENABLE KEYS */;

/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;

/*
SQLyog Community v11.51 (32 bit)
MySQL - 10.4.21-MariaDB : Database - icin
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`icin` /*!40100 DEFAULT CHARACTER SET utf8mb4 */;

USE `icin`;

/*Table structure for table `appointment` */

DROP TABLE IF EXISTS `appointment`;

CREATE TABLE `appointment` (
  `id` bigint(20) NOT NULL,
  `confirmed` bit(1) NOT NULL,
  `date` datetime DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `location` varchar(255) DEFAULT NULL,
  `user_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKa8m1smlfsc8kkjn2t6wpdmysk` (`user_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4;

/*Data for the table `appointment` */

insert  into `appointment`(`id`,`confirmed`,`date`,`description`,`location`,`user_id`) values (17,'\0','2022-02-21 09:56:00','aeaawef','Enugu',3);

/*Table structure for table `hibernate_sequence` */

DROP TABLE IF EXISTS `hibernate_sequence`;

CREATE TABLE `hibernate_sequence` (
  `next_val` bigint(20) DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4;

/*Data for the table `hibernate_sequence` */

insert  into `hibernate_sequence`(`next_val`) values (18);

/*Table structure for table `primary_account` */

DROP TABLE IF EXISTS `primary_account`;

CREATE TABLE `primary_account` (
  `id` bigint(20) NOT NULL,
  `account_balance` decimal(19,2) DEFAULT NULL,
  `account_number` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4;

/*Data for the table `primary_account` */

insert  into `primary_account`(`id`,`account_balance`,`account_number`) values (1,'550.00',1122003490);

/*Table structure for table `primary_transaction` */

DROP TABLE IF EXISTS `primary_transaction`;

CREATE TABLE `primary_transaction` (
  `id` bigint(20) NOT NULL,
  `amount` double NOT NULL,
  `available_balance` decimal(19,2) DEFAULT NULL,
  `date` datetime DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `status` varchar(255) DEFAULT NULL,
  `type` varchar(255) DEFAULT NULL,
  `primary_account_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK643wtfdx6y0e093wlc09csehn` (`primary_account_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4;

/*Data for the table `primary_transaction` */

insert  into `primary_transaction`(`id`,`amount`,`available_balance`,`date`,`description`,`status`,`type`,`primary_account_id`) values (5,500,'1000.00','2022-02-17 15:14:01','Deposit to Primary Account','Finished','Account',1),(8,50,'950.00','2022-02-17 15:37:44','Deposit to Primary Account','Finished','Account',1),(9,150,'800.00','2022-02-17 15:40:55','Debit Alert From Primary Account','Finished','Account',1),(11,150,'650.00','2022-02-17 17:38:36','Transfer from Primary to Savings accounts','Finished','Account',1),(12,50,'600.00','2022-02-17 17:42:04','Transfer from Primary to Savings accounts','Finished','Account',1),(16,50,'550.00','2022-02-18 16:13:35','Transfer From Primary Account To Recipient recipient1','Finished','Account',1);

/*Table structure for table `recipient` */

DROP TABLE IF EXISTS `recipient`;

CREATE TABLE `recipient` (
  `id` bigint(20) NOT NULL,
  `account_number` varchar(255) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `phone` varchar(255) DEFAULT NULL,
  `user_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK3041ks22uyyuuw441k5671ah9` (`user_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4;

/*Data for the table `recipient` */

insert  into `recipient`(`id`,`account_number`,`description`,`email`,`name`,`phone`,`user_id`) values (13,'233345556','Test Demo','abc@gmail.com','recipient1','123444444',3),(15,'009988773','Another one','another@gmail.com','Another Recipient','66353528282',3);

/*Table structure for table `role` */

DROP TABLE IF EXISTS `role`;

CREATE TABLE `role` (
  `role_id` int(11) NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`role_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4;

/*Data for the table `role` */

insert  into `role`(`role_id`,`name`) values (0,'ROLE_USER'),(1,'ROLE_ADMIN');

/*Table structure for table `savings_account` */

DROP TABLE IF EXISTS `savings_account`;

CREATE TABLE `savings_account` (
  `id` bigint(20) NOT NULL,
  `account_balance` decimal(19,2) DEFAULT NULL,
  `account_number` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4;

/*Data for the table `savings_account` */

insert  into `savings_account`(`id`,`account_balance`,`account_number`) values (2,'1100.00',1122003491);

/*Table structure for table `savings_transaction` */

DROP TABLE IF EXISTS `savings_transaction`;

CREATE TABLE `savings_transaction` (
  `id` bigint(20) NOT NULL,
  `amount` double NOT NULL,
  `available_balance` decimal(19,2) DEFAULT NULL,
  `date` datetime DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `status` varchar(255) DEFAULT NULL,
  `type` varchar(255) DEFAULT NULL,
  `savings_account_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK4bt1l2090882974glyn79q2s9` (`savings_account_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4;

/*Data for the table `savings_transaction` */

insert  into `savings_transaction`(`id`,`amount`,`available_balance`,`date`,`description`,`status`,`type`,`savings_account_id`) values (6,500,'850.00','2022-02-17 15:15:37','Deposit to Savings Account','Finished','Account',2),(7,200,'1050.00','2022-02-17 15:20:30','Deposit to Savings Account','Finished','Account',2),(10,150,'900.00','2022-02-17 15:41:00','Debit Alert to Savings Account','Finished','Account',2);

/*Table structure for table `user` */

DROP TABLE IF EXISTS `user`;

CREATE TABLE `user` (
  `user_id` bigint(20) NOT NULL,
  `email` varchar(255) NOT NULL,
  `enabled` bit(1) NOT NULL,
  `first_name` varchar(255) DEFAULT NULL,
  `last_name` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `phone` varchar(255) DEFAULT NULL,
  `username` varchar(255) DEFAULT NULL,
  `primary_account_id` bigint(20) DEFAULT NULL,
  `savings_account_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`user_id`),
  UNIQUE KEY `UK_ob8kqyqqgmefl0aco34akdtpe` (`email`) USING HASH,
  KEY `FKbj0uoj9i40dory8w4t5ojyb9n` (`primary_account_id`),
  KEY `FKihums7d3g5cv9ehminfs1539e` (`savings_account_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4;

/*Data for the table `user` */

insert  into `user`(`user_id`,`email`,`enabled`,`first_name`,`last_name`,`password`,`phone`,`username`,`primary_account_id`,`savings_account_id`) values (3,'april7baby@gmail.com','','David','Tobrise','$2a$12$L0h/RC/3cjxioGCQ5uA0be3WmJUZNee63Ij2ZxjFWumtjKI7OpkQq','+08099887746','demo',1,2);

/*Table structure for table `user_role` */

DROP TABLE IF EXISTS `user_role`;

CREATE TABLE `user_role` (
  `user_role_id` bigint(20) NOT NULL,
  `role_id` int(11) DEFAULT NULL,
  `user_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`user_role_id`),
  KEY `FKa68196081fvovjhkek5m97n3y` (`role_id`),
  KEY `FK859n2jvi8ivhui0rl0esws6o` (`user_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4;

/*Data for the table `user_role` */

insert  into `user_role`(`user_role_id`,`role_id`,`user_id`) values (4,0,3);

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

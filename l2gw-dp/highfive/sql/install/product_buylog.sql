DROP TABLE IF EXISTS `product_buylog`;
CREATE TABLE `product_buylog` (
  `job_id` int(11) NOT NULL,
  `account_id` int(11) NOT NULL,
  `object_id` int(11) NOT NULL,
  `char_name` varchar(36) NOT NULL,
  `product_id` int(11) NOT NULL,
  `amount` int(11) NOT NULL,
  `price` int(11) NOT NULL,
  `buy_id` decimal(20,0) NOT NULL,
  `buy_date` datetime NOT NULL,
PRIMARY KEY(`job_Id`),
INDEX(`object_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
CREATE TABLE `col_market` (
  `pos_id` int(11) NOT NULL AUTO_INCREMENT,
  `object_id` int(11) NOT NULL,
  `price` BIGINT UNSIGNED NOT NULL,
  `package` int NOT NULL DEFAULT '0',
  `pos_date` int(11) DEFAULT NULL,
  PRIMARY KEY (`pos_id`),
  UNIQUE INDEX (`object_id`),
  INDEX (`pos_date`)
) ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS `character_tpbookmark` (
  `char_obj_id` int NOT NULL,
  `slot` TINYINT UNSIGNED NOT NULL,
  `name` varchar(32) NOT NULL,
  `acronym` varchar(4) NOT NULL,
  `icon` TINYINT UNSIGNED NOT NULL,
  `x` int NOT NULL,
  `y` int NOT NULL,
  `z` int NOT NULL,
  PRIMARY KEY (`char_obj_id`,`slot`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
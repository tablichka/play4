DROP TABLE IF EXISTS `pvp_buffsets`;
CREATE TABLE `pvp_buffsets` (
  `obj_id` int(11) NOT NULL,
  `set_name` VARCHAR(16) NOT NULL,
  `skills` VARCHAR(255) NOT NULL,
  PRIMARY KEY (`obj_id`, `set_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

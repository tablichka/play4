CREATE TABLE IF NOT EXISTS `character_mail` (
  `message_id` INT UNSIGNED NOT NULL auto_increment,
  `src_obj_id` INT UNSIGNED NOT NULL,
  `dst_obj_id` INT UNSIGNED NOT NULL,
  `expire` INT UNSIGNED NOT NULL,
  `subject` VARCHAR(30) NOT NULL,
  `message` TEXT NOT NULL,
  `price` BIGINT UNSIGNED NOT NULL DEFAULT 0,
  `system` TINYINT UNSIGNED NOT NULL DEFAULT 0,
  `unread` TINYINT UNSIGNED NOT NULL DEFAULT 1,
  `attach_id` INT UNSIGNED NOT NULL DEFAULT 0,
  `returned` TINYINT UNSIGNED NOT NULL DEFAULT 0,
  PRIMARY KEY (`message_id`),
  KEY `src_obj_id` (`src_obj_id`),
  KEY `dst_obj_id` (`dst_obj_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

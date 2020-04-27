CREATE TABLE IF NOT EXISTS `character_mail_attach` (
  `attach_id` INT UNSIGNED NOT NULL auto_increment,
  `item_obj_id` INT UNSIGNED NOT NULL,
  PRIMARY KEY (`attach_id`,`item_obj_id`),
  KEY `attach_id` (`attach_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
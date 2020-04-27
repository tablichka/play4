DROP TABLE IF EXISTS `character_shortcuts`;
CREATE TABLE `character_shortcuts` (
`char_obj_id` INT(10) NOT NULL DEFAULT '0',
`slot` DECIMAL(3) NOT NULL DEFAULT '0',
`page` DECIMAL(3) NOT NULL DEFAULT '0',
`type` DECIMAL(3),
`shortcut_id` DECIMAL(16),
`level` VARCHAR(4),
`class_index` SMALLINT(3) NOT NULL DEFAULT '0',
PRIMARY KEY (`char_obj_id`,`slot`,`page`,`class_index`),
KEY `shortcut_id` (`shortcut_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
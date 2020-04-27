DROP TABLE IF EXISTS `character_effects_save`;
CREATE TABLE `character_effects_save` (
`char_obj_id` INT NOT NULL DEFAULT '0',
`skill_id` INT NOT NULL DEFAULT '0',
`skill_level` INT NOT NULL DEFAULT '0',
`duration` INT NOT NULL DEFAULT '0',
`order` tinyint(2) NOT NULL DEFAULT '0',
`class_index` SMALLINT(3) NOT NULL DEFAULT '0',
PRIMARY KEY (`char_obj_id`,`skill_id`,`class_index`),
KEY `order` (`order`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
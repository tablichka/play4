DROP TABLE IF EXISTS `character_skills`;
CREATE TABLE `character_skills` (
`char_obj_id` INT NOT NULL DEFAULT '0',
`skill_id` INT NOT NULL DEFAULT '0',
`skill_level` VARCHAR(5),
`skill_name` VARCHAR(40),
`class_index` SMALLINT(3) NOT NULL DEFAULT '0',
PRIMARY KEY(`char_obj_id`,`skill_id`,`class_index`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
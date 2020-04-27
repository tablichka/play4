DROP TABLE IF EXISTS `character_skills_save`;
CREATE TABLE `character_skills_save` (
`char_obj_id` int(11) NOT NULL default '0',
`skill_id` int(11) NOT NULL default '0',
`class_index` SMALLINT(3) NOT NULL default '0',
`end_time` bigint(110) NOT NULL default '0',
`reuse_delay_org` int(11) NOT NULL default '0',
PRIMARY KEY(`char_obj_id`,`skill_id`,`class_index`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
DROP TABLE IF EXISTS `character_summons_effects`;
CREATE TABLE `character_summons_effects` (
`char_obj_id` INT NOT NULL,
`npc_id` INT NOT NULL,
`skill_id` INT NOT NULL DEFAULT '0',
`skill_level` INT NOT NULL DEFAULT '0',
`duration` INT NOT NULL DEFAULT '0',
`order` INT NOT NULL DEFAULT '0',
PRIMARY KEY(`char_obj_id`, `npc_id`, `skill_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
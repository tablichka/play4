DROP TABLE IF EXISTS `character_summons`;
CREATE TABLE `character_summons` (
`char_obj_id` INT NOT NULL,
`npc_id` INT NOT NULL,
`curr_hp` INT NOT NULL,
`curr_mp` INT NOT NULL,
`life_time` INT NOT NULL,
`max_life_time` INT NOT NULL,
`item_id` INT NOT NULL,
`item_count` INT NOT NULL,
`item_delay` INT NOT NULL,
`penalty` FLOAT NOT NULL,
PRIMARY KEY(`char_obj_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
DROP TABLE IF EXISTS `kill_status`;
CREATE TABLE `kill_status` (
`spawn_id` int(11) NOT NULL,
`npc_templateid` int(11) NOT NULL,
`current_hp` decimal(8,0) default 0,
`current_mp` decimal(8,0) default 0,
`respawn_time` int(11) default 0,
PRIMARY KEY(`spawn_id`),
INDEX(`npc_templateid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
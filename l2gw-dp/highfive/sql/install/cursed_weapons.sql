DROP TABLE IF EXISTS `cursed_weapons`;
CREATE TABLE `cursed_weapons` (
`item_id` smallint(5) unsigned NOT NULL,
`player_id` INT(10) UNSIGNED NOT NULL DEFAULT 0,
`player_karma` int(11) unsigned NOT NULL default '0',
`player_pkkills` int(10) unsigned NOT NULL default '0',
`nb_kills` int(10) unsigned NOT NULL default '0',
`x` int(11) NOT NULL default '0',
`y` int(11) NOT NULL default '0',
`z` int(11) NOT NULL default '0',
`end_time` INT UNSIGNED NOT NULL DEFAULT 0,
PRIMARY KEY (`item_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


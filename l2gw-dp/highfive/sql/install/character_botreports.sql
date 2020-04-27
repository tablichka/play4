DROP TABLE IF EXISTS `character_botreports`;
CREATE TABLE `character_botreports` (
`char_id` int(11) NOT NULL default '0',
`bot_id` int(11) NOT NULL default '0',
`exp_time` int UNSIGNED NOT NULL default '0',
PRIMARY KEY(`char_id`,`bot_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
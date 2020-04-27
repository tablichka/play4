DROP TABLE IF EXISTS `clan_subpledges`;
CREATE TABLE `clan_subpledges` (
`clan_id` INT UNSIGNED NOT NULL default '0',
`type` SMALLINT NOT NULL DEFAULT '0',
`name` VARCHAR(45) NOT NULL DEFAULT '',
`leader_id` INT UNSIGNED NOT NULL DEFAULT '0',
PRIMARY KEY(`clan_id`,`type`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

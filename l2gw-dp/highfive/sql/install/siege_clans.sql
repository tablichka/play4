DROP TABLE IF EXISTS `siege_clans`;
CREATE TABLE `siege_clans` (
unit_id int(1) NOT NULL default 0,
clan_id int(11) NOT NULL default 0,
type int(1) default NULL,
castle_owner int(1) default NULL,
PRIMARY KEY(clan_id,unit_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
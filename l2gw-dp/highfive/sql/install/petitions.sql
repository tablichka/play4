DROP TABLE IF EXISTS `petitions`;
CREATE TABLE `petitions` (
`serv_id` tinyint(3) unsigned NOT NULL default '0',
`act_time` int(10) unsigned NOT NULL default '0',
`petition_type` tinyint(3) unsigned NOT NULL default '0',
`actor` int(10) unsigned NOT NULL default '0',
`location_x` mediumint(9) default NULL,
`location_y` mediumint(9) default NULL,
`location_z` smallint(6) default NULL,
`petition_text` text NOT NULL,
`STR_actor` varchar(50) default NULL,
`STR_actor_account` varchar(50) default NULL,
`petition_status` tinyint(3) unsigned NOT NULL default '0',
KEY `actor` (`actor`),
KEY `petition_status` (`petition_status`),
KEY `petition_type` (`petition_type`),
KEY `serv_id` (`serv_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
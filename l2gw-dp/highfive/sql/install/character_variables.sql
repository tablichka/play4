DROP TABLE IF EXISTS `character_variables`;
CREATE TABLE `character_variables` (
`obj_id` int(11) NOT NULL default '0',
`type` varchar(86) NOT NULL default '0',
`name` varchar(86) NOT NULL default '0',
`index` int(11) NOT NULL default '0',
`value` varchar(255) NOT NULL default '0',
`expire_time` int(11) NOT NULL default '0',
UNIQUE KEY `prim` (`obj_id`,`type`,`name`,`index`),
KEY `obj_id` (`obj_id`),
KEY `type` (`type`),
KEY `name` (`name`),
KEY `index` (`index`),
KEY `value` (`value`),
KEY `expire_time` (`expire_time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

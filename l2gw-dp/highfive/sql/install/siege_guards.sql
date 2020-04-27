DROP TABLE IF EXISTS `siege_guards`;
CREATE TABLE `siege_guards` (
`unitId` int(11) NOT NULL default '0',
`id` int(11) NOT NULL auto_increment,
`npcId` int(11) NOT NULL default '0',
`x` int(11) NOT NULL default '0',
`y` int(11) NOT NULL default '0',
`z` int(11) NOT NULL default '0',
`heading` int(11) NOT NULL default '0',
`respawnDelay` int(11) NOT NULL default '0',
`isHired` int(11) NOT NULL default '1',
PRIMARY KEY(`id`),
KEY `id` (`unitId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

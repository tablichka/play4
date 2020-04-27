DROP TABLE IF EXISTS `ch_sieges`;
CREATE TABLE `ch_sieges` (
`ch_id` VARCHAR(45) DEFAULT NULL,
`starttime` decimal(24,0) NOT NULL DEFAULT '0',
`lastSiegeDate` int(11) NOT NULL default '0',
`siegeDate` int(11) NOT NULL default '0',
PRIMARY KEY(`ch_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

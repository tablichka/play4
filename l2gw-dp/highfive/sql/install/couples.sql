DROP TABLE IF EXISTS `couples`;
CREATE TABLE `couples` (
`id` int(11) NOT NULL auto_increment,
`player1Id` int(11) NOT NULL default '0',
`player2Id` int(11) NOT NULL default '0',
`maried` varchar(5) default NULL,
`affiancedDate` decimal(20,0) default '0',
`weddingDate` decimal(20,0) default '0',
PRIMARY KEY(`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
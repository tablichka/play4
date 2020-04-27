DROP TABLE IF EXISTS `tournament_variables`;
CREATE TABLE `tournament_variables` (
`name` varchar(255) NOT NULL default '',
`value` varchar(255) default NULL,
PRIMARY KEY(`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO `tournament_variables` VALUES ('start','0');
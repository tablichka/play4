CREATE TABLE IF NOT EXISTS `territories` (
  `territory_id` int(11) NOT NULL default '0',
  `castle_id` int(11) NOT NULL default '0',
  `fort_id` int(11) NOT NULL default '0',
  `ward_ids` varchar(30) NOT NULL default '',
  PRIMARY KEY  (`territory_id`)
) ENGINE=InnoDB DEFAULT CHARSET=UTF8;

INSERT IGNORE INTO `territories` VALUES
(81,1,101,'81;'),
(82,2,103,'82;'),
(83,3,104,'83;'),
(84,4,105,'84;'),
(85,5,106,'85;'),
(86,6,108,'86;'),
(87,7,109,'87;'),
(88,8,110,'88;'),
(89,9,111,'89;');

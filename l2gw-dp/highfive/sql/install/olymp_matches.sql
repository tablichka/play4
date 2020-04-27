DROP TABLE IF EXISTS `olymp_matches`;
CREATE TABLE `olymp_matches` (
  `id` int(11) NOT NULL auto_increment,
  `char_id` decimal(11,0) NOT NULL default '0',
  `vsName` varchar(45) NOT NULL default '',
  `vsClassId` decimal(3,0) NOT NULL default '0',
  `result` decimal(1,0) NOT NULL default '0', -- 1 win, 2 loos, 3 draw
  `type` tinyint(1) NOT NULL default '0', -- 1 class, 2 general
  `mons` decimal(11,0) NOT NULL default '0',
  `time` decimal(11,0) NOT NULL default '0',
  `stdt` decimal(20,0) NOT NULL default '0',
  primary key(`id`)
) ENGINE=InnoDB;



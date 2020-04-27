DROP TABLE IF EXISTS `hero_life_history`;
CREATE TABLE `hero_life_history` (
  `id` int(11) NOT NULL auto_increment,
  `hero_id` decimal(11,0) NOT NULL default '0',
  `descr` varchar(100) NOT NULL default '',
  `stdt` mediumtext NOT NULL,
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

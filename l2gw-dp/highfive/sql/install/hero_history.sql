DROP TABLE IF EXISTS `hero_history`;
CREATE TABLE `hero_history` (
  `char_id` decimal(11,0) NOT NULL default '0',
  `char_name` varchar(45) NOT NULL default '',
  `class_id` decimal(3,0) NOT NULL default '0',
  `cycle` decimal(11,0) NOT NULL default '0',
  `mons` decimal(11,0) NOT NULL default '0',
  `active` decimal(1,0) NOT NULL default '0',
  `message` varchar(100) default NULL,
  UNIQUE KEY `char_id` (`char_id`,`mons`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

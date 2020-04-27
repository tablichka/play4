CREATE TABLE IF NOT EXISTS `event_mog_skilltable` (
  `id` int(11) NOT NULL auto_increment,
  `skill_id` int(11) default NULL,
  `skill_lvl` tinyint(4) default NULL,
  `event_group` tinyint(4) default NULL,
  `comment` varchar(128) default NULL,
  `probability` smallint(6) default '100',
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=79 DEFAULT CHARSET=utf8;

CREATE TABLE IF NOT EXISTS  `event_mog_skills` (
  `id` int(11) default NULL,
  `owner_id` int(11) default NULL,
  `class_id` int(11) default NULL,
  UNIQUE KEY `myind` (`id`,`owner_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8

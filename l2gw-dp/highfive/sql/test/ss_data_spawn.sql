/*
MySQL Backup
Source Host:           localhost
Source Server Version: 5.0.24a-community-nt
Source Database:       l2jdb
Date:                  2007.04.16 15:09:13
*/

SET FOREIGN_KEY_CHECKS=0;
use l2jdb;
#----------------------------
# Table structure for ss_data_spawn
#----------------------------
CREATE TABLE `ss_data_spawn` (
  `npc_id` int(11) NOT NULL,
  `side` tinyint(1) NOT NULL,
  `x` int(11) NOT NULL,
  `y` int(11) NOT NULL,
  `z` int(11) NOT NULL,
  `heading` int(11) NOT NULL,
  `despawn_delay` int(11) NOT NULL,
  `isRandomSpawn` tinyint(1) NOT NULL,
  PRIMARY KEY  (`x`,`y`,`z`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='InnoDB free: 9216 kB; InnoDB free: 9216 kB; InnoDB free: 921';
#----------------------------
# No records for table ss_data_spawn
#----------------------------



/*
MySQL Backup
Source Host:           localhost
Source Server Version: 5.0.24a-community-nt
Source Database:       l2jdb
Date:                  2007.04.16 15:09:07
*/

SET FOREIGN_KEY_CHECKS=0;
use l2jdb;
#----------------------------
# Table structure for ss_data_players
#----------------------------
CREATE TABLE `ss_data_players` (
  `player_id` int(11) NOT NULL,
  `side` tinyint(1) NOT NULL,
  `seal` tinyint(1) NOT NULL,
  `stones_red_dawn` int(11) NOT NULL,
  `stones_red_dusk` int(11) NOT NULL,
  `stones_green_dawn` int(11) NOT NULL,
  `stones_green_dusk` int(11) NOT NULL,
  `stones_blue_dawn` int(11) NOT NULL,
  `stones_blue_dusk` int(11) NOT NULL,
  PRIMARY KEY  (`player_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='InnoDB free: 9216 kB; InnoDB free: 9216 kB';
#----------------------------
# No records for table ss_data_players
#----------------------------



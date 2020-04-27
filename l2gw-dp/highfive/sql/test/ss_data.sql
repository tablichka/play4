/*
MySQL Backup
Source Host:           localhost
Source Server Version: 5.0.24a-community-nt
Source Database:       l2jdb
Date:                  2007.04.16 15:09:30
*/

SET FOREIGN_KEY_CHECKS=0;
use l2jdb;
#----------------------------
# Table structure for ss_data
#----------------------------
CREATE TABLE `ss_data` (
  `cycle` int(11) NOT NULL,
  `period` tinyint(11) NOT NULL,
  `winner` tinyint(1) NOT NULL,
  `owner_avarice` tinyint(1) NOT NULL,
  `owner_gnosis` tinyint(1) NOT NULL,
  `owner_strife` tinyint(1) NOT NULL,
  `last_cycle_change_time` int(11) NOT NULL,
  PRIMARY KEY  (`cycle`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='InnoDB free: 9216 kB; InnoDB free: 9216 kB; InnoDB free: 921';
#----------------------------
# No records for table ss_data
#----------------------------



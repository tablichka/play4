DROP TABLE IF EXISTS `hwid_windows_ext`;
CREATE TABLE `hwid_windows_ext` (
  `hwid` varchar(32) default '00000000000000000000000000000000',
  `expire_date` datetime default '0000-00-00 00:00:00'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

 CREATE TABLE `capture_bans` (
  `hwid` varchar(32) NOT NULL,
  `expire_time` int(11) NOT NULL DEFAULT '-1',
  PRIMARY KEY (`hwid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
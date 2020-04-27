CREATE TABLE `mail_announce` (
  `id` int unsigned not null auto_increment,
  `char_name` varchar(35) not null default "",
  `hwid` varchar(32) not null default "",
  `field1` varchar(128) not null default "",
  `field2` varchar(128) not null default "",
  `field3` varchar(128) not null default "",
  `field4` varchar(128) not null default "",
  `field5` varchar(128) not null default "",
  `item_attach` varchar(128) not null default "",
  `status` int not null default 0,
  PRIMARY KEY(`id`),
  INDEX(`char_name`),
  INDEX(`hwid`),
  INDEX(`status`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
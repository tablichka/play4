DROP TABLE IF EXISTS `global_tasks`;
CREATE TABLE `global_tasks` (
id int(11) NOT NULL auto_increment,
task varchar(50) NOT NULL default '',
type varchar(50) NOT NULL default '',
last_activation int(20) NOT NULL default 0,
param1 varchar(100) NOT NULL default '',
param2 varchar(100) NOT NULL default '',
param3 varchar(255) NOT NULL default '',
PRIMARY KEY(`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
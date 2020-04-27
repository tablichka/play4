DROP TABLE IF EXISTS `agit_auction`;
CREATE TABLE `agit_auction` (
agit_id int(11) NOT NULL default '0',
clan_id int(11) NOT NULL default '0',
start_bid bigint(20) NOT NULL default '0',
deposit bigint(20) NOT NULL default '0',
description varchar(150) NOT NULL default '',
end_date decimal(20,0) NOT NULL default '0',
PRIMARY KEY  (`agit_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

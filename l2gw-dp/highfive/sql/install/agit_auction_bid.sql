DROP TABLE IF EXISTS `agit_auction_bid`;
CREATE TABLE `agit_auction_bid` (
agit_id INT NOT NULL default 0,
clan_id INT NOT NULL default 0,
bid bigint(20) NOT NULL default 0,
bid_time decimal(20,0) NOT NULL default '0',
PRIMARY KEY  (`agit_id`, `clan_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

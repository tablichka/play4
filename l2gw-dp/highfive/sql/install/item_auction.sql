DROP TABLE IF EXISTS `item_auction`;
CREATE TABLE `item_auction` (
`auction_id` int(11) NOT NULL AUTO_INCREMENT,
`broker_id` int(11) NOT NULL,
`item_id` int(11) NOT NULL,
`current_bid` bigint(20) NOT NULL,
`bidder_id` int(11) NOT NULL,
`start_date` decimal(20,0) NOT NULL,
`end_date` decimal(20,0) NOT NULL,
`finished` tinyint NOT NULL DEFAULT 0,
`prev_item_id` int(11) NOT NULL,
`prev_bid` bigint(20) NOT NULL,
PRIMARY KEY  (`auction_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

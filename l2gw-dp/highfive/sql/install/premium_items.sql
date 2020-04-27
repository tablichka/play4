DROP TABLE IF EXISTS `premium_items`;
CREATE TABLE `premium_items` (
`id` int(11) NOT NULL PRIMARY KEY AUTO_INCREMENT,
`item_id` int(11) NOT NULL,
`owner_id` int(11) NOT NULL,
`amount` decimal(20,0) NOT NULL,
`sender` varchar(35) NOT NULL,
`status` boolean NOT NULL default '0',
KEY owner_id (owner_id),
KEY status (owner_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

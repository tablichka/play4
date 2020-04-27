DROP TABLE IF EXISTS `items_delayed`;
CREATE TABLE `items_delayed` (
`payment_id` INT(10) UNSIGNED NOT NULL auto_increment,
`owner_id` INT(10) NOT NULL,
`item_id` SMALLINT(5) UNSIGNED NOT NULL,
`count` INT(11) UNSIGNED NOT NULL DEFAULT '1',
`enchant_level` SMALLINT(5) UNSIGNED NOT NULL DEFAULT '0',
`flags` int(11) NOT NULL default '0',
`payment_status` TINYINT UNSIGNED DEFAULT "0" NOT NULL,
`description` varchar(255) default NULL,
PRIMARY KEY (payment_id),
KEY `key_owner_id` (`owner_id`),
KEY `key_item_id` (`item_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
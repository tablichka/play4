DROP TABLE IF EXISTS `ally_data`;
CREATE TABLE `ally_data` (
ally_id INT NOT NULL default 0,
ally_name VARCHAR(45),
leader_id INT UNSIGNED NOT NULL DEFAULT 0,
expelled_member INT UNSIGNED NOT NULL DEFAULT 0,
crest VARBINARY(192) NULL DEFAULT NULL,
PRIMARY KEY  (ally_id),
KEY `leader_id` (`leader_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

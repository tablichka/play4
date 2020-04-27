DROP TABLE IF EXISTS `character_quests`;
CREATE TABLE `character_quests` (
`char_id` INT NOT NULL DEFAULT '0',
`name` VARCHAR(100) NOT NULL DEFAULT '',
`var`VARCHAR(20) NOT NULL DEFAULT '',
`value` VARCHAR(255),
PRIMARY KEY(`char_id`,`name`,`var`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
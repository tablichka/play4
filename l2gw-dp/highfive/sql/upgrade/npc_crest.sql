DROP TABLE IF EXISTS `npc_crest`;
CREATE TABLE `npc_crest` (
pledge_id INT UNSIGNED NOT NULL DEFAULT 0,
ally_id INT UNSIGNED NOT NULL DEFAULT 0,
ally_crest VARBINARY(8192) NULL DEFAULT NULL,
crest_id INT UNSIGNED NOT NULL DEFAULT 0,
crest VARBINARY(8192) NULL DEFAULT NULL,
PRIMARY KEY (pledge_id),
KEY `crest_id` (`crest_id`),
KEY `ally_id` (`ally_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

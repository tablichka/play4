DROP TABLE IF EXISTS `character_contactlist`;
CREATE TABLE `character_contactlist` (
`char_id` int(11) NOT NULL,
`contact_id` int(11) NOT NULL,
`contact_name` varchar(35) NOT NULL,
PRIMARY KEY(`char_id`,`contact_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
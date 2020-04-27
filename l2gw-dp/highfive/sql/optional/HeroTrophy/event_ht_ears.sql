CREATE TABLE `event_ht_ears` (
  `object_id` int(11) NOT NULL PRIMARY KEY,
  `owner_id` int(11) NOT NULL,
  `char_name` varchar(35) NOT NULL,
  `kill_time` int(11) NOT NULL,
  `left` boolean NOT NULL,
  KEY `owner_id` (`owner_id`),
  KEY `kill_time` (`kill_time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

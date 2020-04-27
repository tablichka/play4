 CREATE TABLE `capture_stat` (
  `object_id` int(11) NOT NULL,
  `points` int(11) NOT NULL DEFAULT '0',
  `current_points` int(11) NOT NULL DEFAULT '0',
  `wins_count` int(11) NOT NULL DEFAULT '0',
  `loos_count` int(11) NOT NULL DEFAULT '0',
  `kill_count` int(11) NOT NULL DEFAULT '0',
  `killed_count` int(11) NOT NULL DEFAULT '0',
  `resurrect_count` int(11) NOT NULL DEFAULT '0',
  `resurrected_count` int(11) NOT NULL DEFAULT '0',
  `flag_capture` int(11) NOT NULL DEFAULT '0',
  `flag_attack` int(11) NOT NULL DEFAULT '0',
  `heal_amount` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`object_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
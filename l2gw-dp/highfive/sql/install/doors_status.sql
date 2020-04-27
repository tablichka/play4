DROP TABLE IF EXISTS `doors_status`;
CREATE TABLE `doors_status` (
  `id` int(11) NOT NULL,
  `nexttimer` bigint(20) NOT NULL,
  `isopen` tinyint(1) NOT NULL,
  UNIQUE KEY `id` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

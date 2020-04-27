DROP TABLE IF EXISTS `field_cycle`;
CREATE TABLE `field_cycle` (
`field_id` int NOT NULL,
`point` bigint NOT NULL,
`step` int NOT NULL,
`step_changed_time` int NOT NULL,
`point_changed_time` int NOT NULL,
PRIMARY KEY(`field_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
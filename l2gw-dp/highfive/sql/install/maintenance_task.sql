DROP TABLE IF EXISTS `maintenance_task`;
CREATE TABLE `maintenance_task` (
`id` INT(10) UNSIGNED NOT NULL auto_increment,
`name` VARCHAR(50) NOT NULL,
`param` VARCHAR(100) NOT NULL,
`status` SMALLINT NOT NULL DEFAULT 0,
`result` BOOLEAN NOT NULL DEFAULT false,
`datetime` INT(11) NOT NULL,
`lastResult` VARCHAR(100) DEFAULT NULL,
PRIMARY KEY (id),
KEY `key_task_time` (`datetime`),
UNIQUE `key_name_param` (`name`, `param`)
) ENGINE=InnoDB DEFAULT CHARSET=UTF8;

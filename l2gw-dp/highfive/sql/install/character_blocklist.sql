DROP TABLE IF EXISTS `character_blocklist`;
CREATE TABLE `character_blocklist` (
`obj_Id` int(10) NOT NULL,
`target_Id` int(10) NOT NULL,
`target_Name` varchar(35) NOT NULL,
PRIMARY KEY(`obj_Id`,`target_Id`,`target_Name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
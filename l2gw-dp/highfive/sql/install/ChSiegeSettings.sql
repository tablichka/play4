DROP TABLE IF EXISTS `ChSiegeSettings`;
CREATE TABLE `ChSiegeSettings` (
`clanId` INT UNSIGNED NOT NULL DEFAULT 0,
`unitId` VARCHAR(45) DEFAULT NULL,
`memberId` INT UNSIGNED NOT NULL DEFAULT 0,
`npcindex` INT (2) NOT NULL DEFAULT 0,
PRIMARY KEY(`clanId`,`unitId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
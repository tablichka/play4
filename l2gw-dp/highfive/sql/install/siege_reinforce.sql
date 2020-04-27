DROP TABLE IF EXISTS `siege_reinforce`;
CREATE TABLE `siege_reinforce` (
  `siegeUnitId` int(11) NOT NULL default '0',
  `reinforceId` int(11) NOT NULL,
  `level` int(11) NOT NULL default '0',
  PRIMARY KEY  (`siegeUnitId`, `reinforceId`),
  KEY `siegeUnitId` (`siegeUnitId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
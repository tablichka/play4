CREATE TABLE IF NOT EXISTS `territory_reg` (
  `territory_id` int(11) NOT NULL default '0',
  `object_id` int(11) NOT NULL default '0',
  `reg_type` int(1) NOT NULL default '0',
  PRIMARY KEY  (`territory_id`,`object_id`)
) ENGINE=InnoDB DEFAULT CHARSET=UTF8;
DROP TABLE IF EXISTS `manor_crop`;
CREATE TABLE `manor_crop` (
`castle_id` int(1) NOT NULL,
`crop_id` int(11) NOT NULL,
`qty` int(11) NOT NULL,
`qty_next` int(11) NOT NULL,
`price` int(11) NOT NULL,
`price_next` int(11) NOT NULL,
`left` int(11) NOT NULL default '0',
`reward_type` int(1) NOT NULL default '0',
PRIMARY KEY(`castle_id`,`crop_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT IGNORE `manor_crop` VALUES
(1,5650,0,0,0,0,0,0),
(1,5016,0,0,0,0,0,0),
(1,5651,0,0,0,0,0,0),
(1,5017,0,0,0,0,0,0),
(1,5652,0,0,0,0,0,0),
(1,5018,0,0,0,0,0,0),
(1,5653,0,0,0,0,0,0),
(1,5654,0,0,0,0,0,0),
(1,5655,0,0,0,0,0,0),
(1,5656,0,0,0,0,0,0),
(1,5019,0,0,0,0,0,0),
(1,5020,0,0,0,0,0,0),
(1,5021,0,0,0,0,0,0),
(1,5022,0,0,0,0,0,0),
(1,7044,0,0,0,0,0,0),
(1,7051,0,0,0,0,0,0),
(1,7033,0,0,0,0,0,0),
(1,7019,0,0,0,0,0,0),
(1,5657,0,0,0,0,0,0),
(1,5023,0,0,0,0,0,0),
(1,7035,0,0,0,0,0,0);
DROP TABLE IF EXISTS `clan_skills`;
CREATE TABLE `clan_skills` (
clan_id int(11) NOT NULL default 0,
pledge_id int(11) NOT NULL default -1,
skill_id int(11) NOT NULL default 0,
skill_level int(5) NOT NULL default 0,
skill_name varchar(26) default NULL,
PRIMARY KEY(`clan_id`,`pledge_id`,`skill_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

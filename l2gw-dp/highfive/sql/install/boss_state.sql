DROP TABLE IF EXISTS `boss_state`;
CREATE TABLE `boss_state` (
  `bossId` int(11) NOT NULL,
  `respawnDate` decimal(20,0) NOT NULL,
  `state` enum('NOTSPAWN', 'ALIVE', 'DEAD') NOT NULL DEFAULT 'NOTSPAWN',
  PRIMARY KEY  (`bossId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO boss_state VALUES(29019, 0, 'NOTSPAWN'); -- Antharas
INSERT INTO boss_state VALUES(29020, 0, 'NOTSPAWN'); -- Baium
INSERT INTO boss_state VALUES(29028, 0, 'NOTSPAWN'); -- Valakas
INSERT INTO boss_state VALUES(29045, 0, 'NOTSPAWN'); -- Frintezza
INSERT INTO boss_state VALUES(29065, 0, 'NOTSPAWN'); -- Sailren

ALTER TABLE characters ADD `hunt_points` INT UNSIGNED NOT NULL DEFAULT '0' AFTER `rec_bonus_time`;
ALTER TABLE characters ADD `hunt_time` INT UNSIGNED NOT NULL DEFAULT '14400' AFTER `hunt_points`;
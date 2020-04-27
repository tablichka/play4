ALTER TABLE accounts ADD `second_use` TINYINT(1) NOT NULL DEFAULT '1';
ALTER TABLE accounts ADD `second_password` VARCHAR(32) NOT NULL DEFAULT '';
ALTER TABLE accounts ADD `second_fail` INT(10) NOT NULL DEFAULT '0';
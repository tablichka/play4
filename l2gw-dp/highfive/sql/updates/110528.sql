ALTER TABLE items CHANGE `price_sell` `price_sell` BIGINT unsigned NOT NULL default '0';
ALTER TABLE items CHANGE `price_buy` `price_buy` BIGINT unsigned NOT NULL default '0';
ALTER TABLE items CHANGE `time_of_use` `expire_time` BIGINT NOT NULL default '0';
ALTER TABLE items CHANGE `shadow_life_time` `mana_left` INT(11) NOT NULL DEFAULT '0';
UPDATE items SET expire_time=mana_left * 1000 WHERE mana_left > 100000;
UPDATE items SET mana_left=0 WHERE mana_left > 100000;
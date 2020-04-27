ALTER TABLE `items` ADD `fire_val` SMALLINT NOT NULL DEFAULT 0;
ALTER TABLE `items` ADD `water_val` SMALLINT NOT NULL DEFAULT 0;
ALTER TABLE `items` ADD `wind_val` SMALLINT NOT NULL DEFAULT 0;
ALTER TABLE `items` ADD `earth_val` SMALLINT NOT NULL DEFAULT 0;
ALTER TABLE `items` ADD `dark_val` SMALLINT NOT NULL DEFAULT 0;
ALTER TABLE `items` ADD `holy_val` SMALLINT NOT NULL DEFAULT 0;

update items  set fire_val = enchant_attr_value where (SELECT enchant_attr = 0);
update items  set water_val = enchant_attr_value where (SELECT enchant_attr = 1);
update items  set wind_val = enchant_attr_value where (SELECT enchant_attr = 2);
update items  set earth_val = enchant_attr_value where (SELECT enchant_attr = 3);
update items  set dark_val = enchant_attr_value where (SELECT enchant_attr = 5);
update items  set holy_val = enchant_attr_value where (SELECT enchant_attr = 4);

ALTER TABLE `items`  DROP COLUMN `enchant_attr`;
ALTER TABLE `items`  DROP COLUMN `enchant_attr_value`;
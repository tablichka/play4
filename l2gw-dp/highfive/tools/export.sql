-- custom script for merge two l2j database

-- main db is l2jair, import db is l2jmaster
-- make table with current objectId's situation from main database
drop table if exists l2jair.objectIdx;
create table l2jair.objectIdx (
object_id int NOT NULL,
PRIMARY KEY (object_id)
);
insert into l2jair.objectIdx (object_id)
select obj_id from l2jair.characters;
insert into l2jair.objectIdx (object_id)
select object_id from l2jair.items;
insert into l2jair.objectIdx (object_id)
select clan_id from l2jair.clan_data;
insert into l2jair.objectIdx (object_id)
select ally_id from l2jair.ally_data;
insert into l2jair.objectIdx (object_id)
select objId from l2jair.pets;

-- make table with current objectId's situation from imported database
drop table if exists l2jmaster.objectIdx;
create table l2jmaster.objectIdx (
shortIdx int NOT NULL auto_increment,
object_id int NOT NULL,
PRIMARY KEY  (shortIdx),
KEY `object_id` (`object_id`)
);
insert into l2jmaster.objectIdx (object_id)
select obj_id from l2jmaster.characters;
insert into l2jmaster.objectIdx (object_id)
select object_id from l2jmaster.items;
insert into l2jmaster.objectIdx (object_id)
select clan_id from l2jmaster.clan_data;
insert into l2jmaster.objectIdx (object_id)
select ally_id from l2jmaster.ally_data;
insert into l2jmaster.objectIdx (object_id)
select objId from l2jmaster.pets;

-- make table with oldId/newId for imported database
drop table if exists l2jmaster.exportIdx;
create table l2jmaster.exportIdx (
old_id int NOT NULL,
new_id int NOT NULL,
PRIMARY KEY  (old_id),
KEY `new_id` (`new_id`)
);
insert into l2jmaster.exportIdx
select object_id,shortIdx+(select max(object_id) from l2jair.objectIdx)
from l2jmaster.objectIdx;

-- okeys, lets do change old_id on new_id
-- main ids change
update l2jmaster.characters a,l2jmaster.exportIdx b 
set a.obj_id = b.new_id where a.obj_id = b.old_id; 

update l2jmaster.items a,l2jmaster.exportIdx b
set a.object_id = b.new_id where a.object_id = b.old_id;

update l2jmaster.clan_data a,l2jmaster.exportIdx b
set a.clan_id = b.new_id where a.clan_id = b.old_id;

update l2jmaster.ally_data a,l2jmaster.exportIdx b
set a.ally_id = b.new_id where a.ally_id = b.old_id;

update l2jmaster.pets a,l2jmaster.exportIdx b
set a.objId = b.new_id where a.objId = b.old_id;
-- secondary ids change
UPDATE l2jmaster.items a,l2jmaster.exportIdx b
SET a.owner_id = b.new_id
WHERE a.owner_id= b.old_id;

UPDATE l2jmaster.character_quests a,l2jmaster.exportIdx b
SET a.char_id = b.new_id
WHERE a.char_id = b.old_id;

UPDATE l2jmaster.character_friends a,l2jmaster.exportIdx b
SET a.char_id = b.new_id
WHERE a.char_id = b.old_id;

UPDATE l2jmaster.character_hennas a,l2jmaster.exportIdx b
SET a.char_obj_id = b.new_id
WHERE a.char_obj_id = b.old_id;

UPDATE l2jmaster.character_recipebook a,l2jmaster.exportIdx b
SET a.char_id = b.new_id
WHERE a.char_id = b.old_id;

UPDATE l2jmaster.character_shortcuts a,l2jmaster.exportIdx b
SET a.char_obj_id = b.new_id
WHERE a.char_obj_id = b.old_id;

UPDATE l2jmaster.character_shortcuts a,l2jmaster.exportIdx b
SET a.shortcut_id = b.new_id
WHERE a.shortcut_id = b.old_id AND type = 1;

UPDATE l2jmaster.character_macroses a,l2jmaster.exportIdx b
SET a.char_obj_id = b.new_id
WHERE a.char_obj_id = b.old_id;

UPDATE l2jmaster.character_skills a,l2jmaster.exportIdx b
SET a.char_obj_id = b.new_id
WHERE a.char_obj_id = b.old_id;

UPDATE l2jmaster.character_skills_save a,l2jmaster.exportIdx b
SET a.char_obj_id = b.new_id
WHERE a.char_obj_id = b.old_id;

UPDATE l2jmaster.character_subclasses a,l2jmaster.exportIdx b
SET a.char_obj_id = b.new_id
WHERE a.char_obj_id = b.old_id;

UPDATE l2jmaster.character_variables a,l2jmaster.exportIdx b
SET a.obj_id = b.new_id
WHERE a.obj_id = b.old_id;

UPDATE l2jmaster.characters a,l2jmaster.exportIdx b
SET a.clanid = b.new_id
WHERE a.clanid = b.old_id;

UPDATE l2jmaster.siege_clans a,l2jmaster.exportIdx b
SET a.clan_id = b.new_id
WHERE a.clan_id = b.old_id;

UPDATE l2jmaster.clan_data a,l2jmaster.exportIdx b
SET a.ally_id = b.new_id
WHERE a.ally_id = b.old_id;

UPDATE l2jmaster.clan_data a,l2jmaster.exportIdx b
SET a.leader_id = b.new_id
WHERE a.leader_id = b.old_id;

UPDATE l2jmaster.pets a,l2jmaster.exportIdx b
SET a.item_obj_id = b.new_id
WHERE a.item_obj_id = b.old_id;

-- fix duplicate names
select '-- check login (in accounts) ---';
update l2jmaster.accounts
set login = CONCAT(login,'XL')
where login in (select login from l2jair.accounts);

select '-- check login (in characters) ---';
update l2jmaster.characters
set account_name = CONCAT(account_name,'XL')
where account_name in (select account_name from l2jair.characters);

select '-- check char_name ---';
update l2jmaster.characters
set char_name = CONCAT(char_name,'XL')
where char_name in (select char_name from l2jair.characters);

select '-- check clan_name ---';
update l2jmaster.clan_data
set clan_name = CONCAT(clan_name,'XL')
where clan_name in (select clan_name from l2jair.clan_data);

select '-- check ally_name ---';
update l2jmaster.ally_data
set ally_name = CONCAT(ally_name,'XL')
where ally_name in (select ally_name from l2jair.ally_data);

-- now may start merge
-- main tables
select 'Start merge process.';
insert into l2jair.accounts select * from l2jmaster.accounts;
select 'accounts ok.';
insert into l2jair.characters select * from l2jmaster.characters; 
select 'characters ok.';
insert into l2jair.items select * from l2jmaster.items; 
select 'items ok.';
insert into l2jair.clan_data select * from l2jmaster.clan_data; 
select 'clan_data ok.';
insert into l2jair.ally_data select * from l2jmaster.ally_data; 
select 'ally_data ok.';
insert into l2jair.pets select * from l2jmaster.pets; 
select 'pets ok.';
-- secondary tables
insert into l2jair.character_quests select * from l2jmaster.character_quests;
select 'character_quests ok.';
insert into l2jair.character_friends select * from l2jmaster.character_friends;
select 'character_friends ok.';
insert into l2jair.character_hennas select * from l2jmaster.character_hennas;
select 'character_hennas ok.';
insert into l2jair.character_recipebook select * from l2jmaster.character_recipebook;
select 'character_recipebook ok.';
insert into l2jair.character_shortcuts select * from l2jmaster.character_shortcuts;
select 'character_shortcuts ok.';
insert into l2jair.character_macroses select * from l2jmaster.character_macroses;
select 'character_macroses ok.';
insert into l2jair.character_skills select * from l2jmaster.character_skills;
select 'character_skills ok.';
insert into l2jair.character_skills_save select * from l2jmaster.character_skills_save;
select 'character_skills_save ok.';
insert into l2jair.character_subclasses select * from l2jmaster.character_subclasses;
select 'character_subclasses ok.';
insert into l2jair.character_variables select * from l2jmaster.character_variables;
select 'character_variables ok.';
insert into l2jair.siege_clans select * from l2jmaster.siege_clans;
select 'siege_clans ok.';


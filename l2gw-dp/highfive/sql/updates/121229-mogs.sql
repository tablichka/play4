ALTER TABLE event_mog_skills ADD `skill_id` int(11) AFTER `owner_id`;
ALTER TABLE event_mog_skills ADD `skill_lvl` int(11) AFTER `skill_id`;
UPDATE event_mog_skills ems, event_mog_skilltable emt SET ems.skill_id = emt.skill_id, ems.skill_lvl = emt.skill_lvl WHERE ems.id = emt.id;
UPDATE event_mog_skills ems, character_skills cs SET ems.skill_lvl = cs.skill_level WHERE cs.char_obj_id = ems.owner_id AND cs.class_index = ems.class_id AND ems.skill_id = cs.skill_id;
DELETE FROM event_mog_skilltable WHERE id in(231,232,235,236,237,238,239,240,241,255,242,243,244,233,234,203,245,247,248,249,250,251,252,253,254);
ALTER TABLE event_mog_skills DROP KEY myind;
ALTER TABLE event_mog_skills DROP `id`;
ALTER TABLE event_mog_skills ADD KEY uniqeue (owner_id,skill_id,class_id);
DELETE FROM event_mog_skills WHERE owner_id NOT IN (SELECT obj_id FROM characters);

#Убирает дроп spellbooks
DELETE FROM droplist WHERE itemId<>57 AND itemId IN (select `item_id` from skill_spellbooks);
package ai.base;

import ai.CombatMonster;
import ru.l2gw.extensions.scripts.Functions;
import ru.l2gw.gameserver.clientpackets.Say2C;
import ru.l2gw.gameserver.model.L2Character;
import ru.l2gw.gameserver.model.L2ObjectsStorage;
import ru.l2gw.gameserver.model.L2Skill;
import ru.l2gw.gameserver.model.entity.category.CategoryManager;
import ru.l2gw.gameserver.tables.SkillTable;
import ru.l2gw.commons.math.Rnd;

/**
 * @author: rage
 * @date: 13.12.11 19:55
 */
public class IsBasic extends CombatMonster
{
	public int type = 0;
	public int FieldCycle = 3;
	public int FieldCycle_Quantity = 50;
	public int attack_x = 0;
	public int attack_y = 0;
	public int attack_z = 0;

	public IsBasic(L2Character actor)
	{
		super(actor);
		_globalAggro = 0;
	}

	@Override
	protected void onEvtFinishCasting(L2Skill skill)
	{
		L2Character target = _thisActor.getCastingTarget();
		if(target == null)
			return;

		int skillIndex = skill != null ? skill.getId() * 65536 + skill.getLevel() : 0;

		if(target != _thisActor && (skillIndex != 387252225 && skillIndex != 387317761 && skillIndex != 387055617))
		{
			if(skillIndex == 385220609 || skillIndex == 385286145 || skillIndex == 385351681 || skillIndex == 385351682 || skillIndex == 385417217 || skillIndex == 385417218 || skillIndex == 385482753 || skillIndex == 385482754 || skillIndex == 385548289 || skillIndex == 385941505 || skillIndex == 385941506 || skillIndex == 385941507 || skillIndex == 386072577 || skillIndex == 386138113 || skillIndex == 386138114)
			{
				if(SkillTable.getAbnormalLevel(target, 384696321) <= 8)
				{
					if(target.getMaxHp() * 0.300000 >= target.getCurrentHp())
					{
						if(SkillTable.isMagic(384696323) == 0 && (SkillTable.getAbnormalLevel(_thisActor, 23134209) > 0 || SkillTable.getAbnormalLevel(_thisActor, 87556097) > 0))
						{
							if(debug)
							{
								Functions.npcSay(_thisActor, Say2C.ALL, "Physical defence!");
							}
						}
						else if(SkillTable.isMagic(384696323) == 1 && (SkillTable.getAbnormalLevel(_thisActor, 69730305) > 0 || SkillTable.getAbnormalLevel(_thisActor, 87556097) > 0))
						{
							if(debug)
							{
								Functions.npcSay(_thisActor, Say2C.ALL, "Magic block!");
							}
						}
						else if(SkillTable.mpConsume(384696323) < _thisActor.getCurrentMp() && SkillTable.hpConsume(384696323) < _thisActor.getCurrentHp() && !_thisActor.isSkillDisabledEx(384696323))
						{
							if(debug)
							{
								Functions.npcSay(_thisActor, Say2C.ALL, "Use " + 384696323 / 65536 + " skill");
							}
							addUseSkillDesire(target, 384696323, 0, 1, 1000000);
						}
						else if(debug)
						{
							Functions.npcSay(_thisActor, Say2C.ALL, "no skill");
						}
					}
					else if(target.getMaxHp() * 0.600000 >= target.getCurrentHp())
					{
						if(SkillTable.isMagic(384696322) == 0 && (SkillTable.getAbnormalLevel(_thisActor, 23134209) > 0 || SkillTable.getAbnormalLevel(_thisActor, 87556097) > 0))
						{
							if(debug)
							{
								Functions.npcSay(_thisActor, Say2C.ALL, "Physical defence!");
							}
						}
						else if(SkillTable.isMagic(384696322) == 1 && (SkillTable.getAbnormalLevel(_thisActor, 69730305) > 0 || SkillTable.getAbnormalLevel(_thisActor, 87556097) > 0))
						{
							if(debug)
							{
								Functions.npcSay(_thisActor, Say2C.ALL, "Magic block!");
							}
						}
						else if(SkillTable.mpConsume(384696322) < _thisActor.getCurrentMp() && SkillTable.hpConsume(384696322) < _thisActor.getCurrentHp() && !_thisActor.isSkillDisabledEx(384696322))
						{
							if(debug)
							{
								Functions.npcSay(_thisActor, Say2C.ALL, "Use " + 384696322 / 65536 + " skill");
							}
							addUseSkillDesire(target, 384696322, 0, 1, 1000000);
						}
						else if(debug)
						{
							Functions.npcSay(_thisActor, Say2C.ALL, "no skill");
						}
					}
					else if(SkillTable.isMagic(384696321) == 0 && (SkillTable.getAbnormalLevel(_thisActor, 23134209) > 0 || SkillTable.getAbnormalLevel(_thisActor, 87556097) > 0))
					{
						if(debug)
						{
							Functions.npcSay(_thisActor, Say2C.ALL, "Physical defence!");
						}
					}
					else if(SkillTable.isMagic(384696321) == 1 && (SkillTable.getAbnormalLevel(_thisActor, 69730305) > 0 || SkillTable.getAbnormalLevel(_thisActor, 87556097) > 0))
					{
						if(debug)
						{
							Functions.npcSay(_thisActor, Say2C.ALL, "Magic block!");
						}
					}
					else if(SkillTable.mpConsume(384696321) < _thisActor.getCurrentMp() && SkillTable.hpConsume(384696321) < _thisActor.getCurrentHp() && !_thisActor.isSkillDisabledEx(384696321))
					{
						if(debug)
						{
							Functions.npcSay(_thisActor, Say2C.ALL, "Use " + 384696321 / 65536 + " skill");
						}
						addUseSkillDesire(target, 384696321, 0, 1, 1000000);
					}
					else if(debug)
					{
						Functions.npcSay(_thisActor, Say2C.ALL, "no skill");
					}
				}
				else if(SkillTable.getAbnormalLevel(target, 384696321) <= 9)
				{
					if(target.getMaxHp() * 0.300000 >= target.getCurrentHp())
					{
						if(SkillTable.isMagic(384696327) == 0 && (SkillTable.getAbnormalLevel(_thisActor, 23134209) > 0 || SkillTable.getAbnormalLevel(_thisActor, 87556097) > 0))
						{
							if(debug)
							{
								Functions.npcSay(_thisActor, Say2C.ALL, "Physical defence!");
							}
						}
						else if(SkillTable.isMagic(384696327) == 1 && (SkillTable.getAbnormalLevel(_thisActor, 69730305) > 0 || SkillTable.getAbnormalLevel(_thisActor, 87556097) > 0))
						{
							if(debug)
							{
								Functions.npcSay(_thisActor, Say2C.ALL, "Magic block!");
							}
						}
						else if(SkillTable.mpConsume(384696327) < _thisActor.getCurrentMp() && SkillTable.hpConsume(384696327) < _thisActor.getCurrentHp() && !_thisActor.isSkillDisabledEx(384696327))
						{
							if(debug)
							{
								Functions.npcSay(_thisActor, Say2C.ALL, "Use " + 384696327 / 65536 + " skill");
							}
							addUseSkillDesire(target, 384696327, 0, 1, 1000000);
						}
						else if(debug)
						{
							Functions.npcSay(_thisActor, Say2C.ALL, "no skill");
						}
					}
					else if(target.getMaxHp() * 0.600000 >= target.getCurrentHp())
					{
						if(SkillTable.isMagic(384696326) == 0 && (SkillTable.getAbnormalLevel(_thisActor, 23134209) > 0 || SkillTable.getAbnormalLevel(_thisActor, 87556097) > 0))
						{
							if(debug)
							{
								Functions.npcSay(_thisActor, Say2C.ALL, "Physical defence!");
							}
						}
						else if(SkillTable.isMagic(384696326) == 1 && (SkillTable.getAbnormalLevel(_thisActor, 69730305) > 0 || SkillTable.getAbnormalLevel(_thisActor, 87556097) > 0))
						{
							if(debug)
							{
								Functions.npcSay(_thisActor, Say2C.ALL, "Magic block!");
							}
						}
						else if(SkillTable.mpConsume(384696326) < _thisActor.getCurrentMp() && SkillTable.hpConsume(384696326) < _thisActor.getCurrentHp() && !_thisActor.isSkillDisabledEx(384696326))
						{
							if(debug)
							{
								Functions.npcSay(_thisActor, Say2C.ALL, "Use " + 384696326 / 65536 + " skill");
							}
							addUseSkillDesire(target, 384696326, 0, 1, 1000000);
						}
						else if(debug)
						{
							Functions.npcSay(_thisActor, Say2C.ALL, "no skill");
						}
					}
					else if(target.getMaxHp() * 0.900000 >= target.getCurrentHp())
					{
						if(SkillTable.isMagic(384696325) == 0 && (SkillTable.getAbnormalLevel(_thisActor, 23134209) > 0 || SkillTable.getAbnormalLevel(_thisActor, 87556097) > 0))
						{
							if(debug)
							{
								Functions.npcSay(_thisActor, Say2C.ALL, "Physical defence!");
							}
						}
						else if(SkillTable.isMagic(384696325) == 1 && (SkillTable.getAbnormalLevel(_thisActor, 69730305) > 0 || SkillTable.getAbnormalLevel(_thisActor, 87556097) > 0))
						{
							if(debug)
							{
								Functions.npcSay(_thisActor, Say2C.ALL, "Magic block!");
							}
						}
						else if(SkillTable.mpConsume(384696325) < _thisActor.getCurrentMp() && SkillTable.hpConsume(384696325) < _thisActor.getCurrentHp() && !_thisActor.isSkillDisabledEx(384696325))
						{
							if(debug)
							{
								Functions.npcSay(_thisActor, Say2C.ALL, "Use " + 384696325 / 65536 + " skill");
							}
							addUseSkillDesire(target, 384696325, 0, 1, 1000000);
						}
						else if(debug)
						{
							Functions.npcSay(_thisActor, Say2C.ALL, "no skill");
						}
					}
					else if(SkillTable.isMagic(384696324) == 0 && (SkillTable.getAbnormalLevel(_thisActor, 23134209) > 0 || SkillTable.getAbnormalLevel(_thisActor, 87556097) > 0))
					{
						if(debug)
						{
							Functions.npcSay(_thisActor, Say2C.ALL, "Physical defence!");
						}
					}
					else if(SkillTable.isMagic(384696324) == 1 && (SkillTable.getAbnormalLevel(_thisActor, 69730305) > 0 || SkillTable.getAbnormalLevel(_thisActor, 87556097) > 0))
					{
						if(debug)
						{
							Functions.npcSay(_thisActor, Say2C.ALL, "Magic block!");
						}
					}
					else if(SkillTable.mpConsume(384696324) < _thisActor.getCurrentMp() && SkillTable.hpConsume(384696324) < _thisActor.getCurrentHp() && !_thisActor.isSkillDisabledEx(384696324))
					{
						if(debug)
						{
							Functions.npcSay(_thisActor, Say2C.ALL, "Use " + 384696324 / 65536 + " skill");
						}
						addUseSkillDesire(target, 384696324, 0, 1, 1000000);
					}
					else if(debug)
					{
						Functions.npcSay(_thisActor, Say2C.ALL, "no skill");
					}
				}
				else if(SkillTable.getAbnormalLevel(target, 384696321) <= 10)
				{
					if(target.getMaxHp() * 0.300000 >= target.getCurrentHp())
					{
						if(SkillTable.isMagic(384696330) == 0 && (SkillTable.getAbnormalLevel(_thisActor, 23134209) > 0 || SkillTable.getAbnormalLevel(_thisActor, 87556097) > 0))
						{
							if(debug)
							{
								Functions.npcSay(_thisActor, Say2C.ALL, "Physical defence!");
							}
						}
						else if(SkillTable.isMagic(384696330) == 1 && (SkillTable.getAbnormalLevel(_thisActor, 69730305) > 0 || SkillTable.getAbnormalLevel(_thisActor, 87556097) > 0))
						{
							if(debug)
							{
								Functions.npcSay(_thisActor, Say2C.ALL, "Magic block!");
							}
						}
						else if(SkillTable.mpConsume(384696330) < _thisActor.getCurrentMp() && SkillTable.hpConsume(384696330) < _thisActor.getCurrentHp() && !_thisActor.isSkillDisabledEx(384696330))
						{
							if(debug)
							{
								Functions.npcSay(_thisActor, Say2C.ALL, "Use " + 384696330 / 65536 + " skill");
							}
							addUseSkillDesire(target, 384696330, 0, 1, 1000000);
						}
						else if(debug)
						{
							Functions.npcSay(_thisActor, Say2C.ALL, "no skill");
						}
					}
					else if(target.getMaxHp() * 0.600000 >= target.getCurrentHp())
					{
						if(SkillTable.isMagic(384696329) == 0 && (SkillTable.getAbnormalLevel(_thisActor, 23134209) > 0 || SkillTable.getAbnormalLevel(_thisActor, 87556097) > 0))
						{
							if(debug)
							{
								Functions.npcSay(_thisActor, Say2C.ALL, "Physical defence!");
							}
						}
						else if(SkillTable.isMagic(384696329) == 1 && (SkillTable.getAbnormalLevel(_thisActor, 69730305) > 0 || SkillTable.getAbnormalLevel(_thisActor, 87556097) > 0))
						{
							if(debug)
							{
								Functions.npcSay(_thisActor, Say2C.ALL, "Magic block!");
							}
						}
						else if(SkillTable.mpConsume(384696329) < _thisActor.getCurrentMp() && SkillTable.hpConsume(384696329) < _thisActor.getCurrentHp() && !_thisActor.isSkillDisabledEx(384696329))
						{
							if(debug)
							{
								Functions.npcSay(_thisActor, Say2C.ALL, "Use " + 384696329 / 65536 + " skill");
							}
							addUseSkillDesire(target, 384696329, 0, 1, 1000000);
						}
						else if(debug)
						{
							Functions.npcSay(_thisActor, Say2C.ALL, "no skill");
						}
					}
					else if(SkillTable.isMagic(384696328) == 0 && (SkillTable.getAbnormalLevel(_thisActor, 23134209) > 0 || SkillTable.getAbnormalLevel(_thisActor, 87556097) > 0))
					{
						if(debug)
						{
							Functions.npcSay(_thisActor, Say2C.ALL, "Physical defence!");
						}
					}
					else if(SkillTable.isMagic(384696328) == 1 && (SkillTable.getAbnormalLevel(_thisActor, 69730305) > 0 || SkillTable.getAbnormalLevel(_thisActor, 87556097) > 0))
					{
						if(debug)
						{
							Functions.npcSay(_thisActor, Say2C.ALL, "Magic block!");
						}
					}
					else if(SkillTable.mpConsume(384696328) < _thisActor.getCurrentMp() && SkillTable.hpConsume(384696328) < _thisActor.getCurrentHp() && !_thisActor.isSkillDisabledEx(384696328))
					{
						if(debug)
						{
							Functions.npcSay(_thisActor, Say2C.ALL, "Use " + 384696328 / 65536 + " skill");
						}
						addUseSkillDesire(target, 384696328, 0, 1, 1000000);
					}
					else if(debug)
					{
						Functions.npcSay(_thisActor, Say2C.ALL, "no skill");
					}
				}
			}
			if(skillIndex == 385613825 || skillIndex == 385613826 || skillIndex == 385679361 || skillIndex == 383713282 || skillIndex == 385875969 || skillIndex == 385875970 || skillIndex == 388759553 || skillIndex == 388825089)
			{
				if(SkillTable.getAbnormalLevel(target, 384761859) < 8)
				{
					if(SkillTable.isMagic(384761859) == 0 && (SkillTable.getAbnormalLevel(_thisActor, 23134209) > 0 || SkillTable.getAbnormalLevel(_thisActor, 87556097) > 0))
					{
						if(debug)
						{
							Functions.npcSay(_thisActor, Say2C.ALL, "Physical defence!");
						}
					}
					else if(SkillTable.isMagic(384761859) == 1 && (SkillTable.getAbnormalLevel(_thisActor, 69730305) > 0 || SkillTable.getAbnormalLevel(_thisActor, 87556097) > 0))
					{
						if(debug)
						{
							Functions.npcSay(_thisActor, Say2C.ALL, "Magic block!");
						}
					}
					else if(SkillTable.mpConsume(384761859) < _thisActor.getCurrentMp() && SkillTable.hpConsume(384761859) < _thisActor.getCurrentHp() && !_thisActor.isSkillDisabledEx(384761859))
					{
						if(debug)
						{
							Functions.npcSay(_thisActor, Say2C.ALL, "Use " + 384761859 / 65536 + " skill");
						}
						addUseSkillDesire(target, 384761859, 0, 1, 1000000);
					}
					else if(debug)
					{
						Functions.npcSay(_thisActor, Say2C.ALL, "no skill");
					}
				}
				else if(SkillTable.getAbnormalLevel(target, 384761859) < 9)
				{
					if(SkillTable.isMagic(384761863) == 0 && (SkillTable.getAbnormalLevel(_thisActor, 23134209) > 0 || SkillTable.getAbnormalLevel(_thisActor, 87556097) > 0))
					{
						if(debug)
						{
							Functions.npcSay(_thisActor, Say2C.ALL, "Physical defence!");
						}
					}
					else if(SkillTable.isMagic(384761863) == 1 && (SkillTable.getAbnormalLevel(_thisActor, 69730305) > 0 || SkillTable.getAbnormalLevel(_thisActor, 87556097) > 0))
					{
						if(debug)
						{
							Functions.npcSay(_thisActor, Say2C.ALL, "Magic block!");
						}
					}
					else if(SkillTable.mpConsume(384761863) < _thisActor.getCurrentMp() && SkillTable.hpConsume(384761863) < _thisActor.getCurrentHp() && !_thisActor.isSkillDisabledEx(384761863))
					{
						if(debug)
						{
							Functions.npcSay(_thisActor, Say2C.ALL, "Use " + 384761863 / 65536 + " skill");
						}
						addUseSkillDesire(target, 384761863, 0, 1, 1000000);
					}
					else if(debug)
					{
						Functions.npcSay(_thisActor, Say2C.ALL, "no skill");
					}
				}
				else if(SkillTable.getAbnormalLevel(target, 384761859) < 10)
				{
					if(SkillTable.isMagic(384761866) == 0 && (SkillTable.getAbnormalLevel(_thisActor, 23134209) > 0 || SkillTable.getAbnormalLevel(_thisActor, 87556097) > 0))
					{
						if(debug)
						{
							Functions.npcSay(_thisActor, Say2C.ALL, "Physical defence!");
						}
					}
					else if(SkillTable.isMagic(384761866) == 1 && (SkillTable.getAbnormalLevel(_thisActor, 69730305) > 0 || SkillTable.getAbnormalLevel(_thisActor, 87556097) > 0))
					{
						if(debug)
						{
							Functions.npcSay(_thisActor, Say2C.ALL, "Magic block!");
						}
					}
					else if(SkillTable.mpConsume(384761866) < _thisActor.getCurrentMp() && SkillTable.hpConsume(384761866) < _thisActor.getCurrentHp() && !_thisActor.isSkillDisabledEx(384761866))
					{
						if(debug)
						{
							Functions.npcSay(_thisActor, Say2C.ALL, "Use " + 384761866 / 65536 + " skill");
						}
						addUseSkillDesire(target, 384761866, 0, 1, 1000000);
					}
					else if(debug)
					{
						Functions.npcSay(_thisActor, Say2C.ALL, "no skill");
					}
				}
			}
			if(skillIndex == 385744897 || skillIndex == 385810433 || skillIndex == 386007041 || skillIndex == 386007042 || skillIndex == 386007043)
			{
				if(SkillTable.getAbnormalLevel(target, 384827395) < 8)
				{
					if(SkillTable.isMagic(384827395) == 0 && (SkillTable.getAbnormalLevel(_thisActor, 23134209) > 0 || SkillTable.getAbnormalLevel(_thisActor, 87556097) > 0))
					{
						if(debug)
						{
							Functions.npcSay(_thisActor, Say2C.ALL, "Physical defence!");
						}
					}
					else if(SkillTable.isMagic(384827395) == 1 && (SkillTable.getAbnormalLevel(_thisActor, 69730305) > 0 || SkillTable.getAbnormalLevel(_thisActor, 87556097) > 0))
					{
						if(debug)
						{
							Functions.npcSay(_thisActor, Say2C.ALL, "Magic block!");
						}
					}
					else if(SkillTable.mpConsume(384827395) < _thisActor.getCurrentMp() && SkillTable.hpConsume(384827395) < _thisActor.getCurrentHp() && !_thisActor.isSkillDisabledEx(384827395))
					{
						if(debug)
						{
							Functions.npcSay(_thisActor, Say2C.ALL, "Use " + 384827395 / 65536 + " skill");
						}
						addUseSkillDesire(target, 384827395, 0, 1, 1000000);
					}
					else if(debug)
					{
						Functions.npcSay(_thisActor, Say2C.ALL, "no skill");
					}
				}
				else if(SkillTable.getAbnormalLevel(target, 384827395) < 9)
				{
					if(SkillTable.isMagic(384827399) == 0 && (SkillTable.getAbnormalLevel(_thisActor, 23134209) > 0 || SkillTable.getAbnormalLevel(_thisActor, 87556097) > 0))
					{
						if(debug)
						{
							Functions.npcSay(_thisActor, Say2C.ALL, "Physical defence!");
						}
					}
					else if(SkillTable.isMagic(384827399) == 1 && (SkillTable.getAbnormalLevel(_thisActor, 69730305) > 0 || SkillTable.getAbnormalLevel(_thisActor, 87556097) > 0))
					{
						if(debug)
						{
							Functions.npcSay(_thisActor, Say2C.ALL, "Magic block!");
						}
					}
					else if(SkillTable.mpConsume(384827399) < _thisActor.getCurrentMp() && SkillTable.hpConsume(384827399) < _thisActor.getCurrentHp() && !_thisActor.isSkillDisabledEx(384827399))
					{
						if(debug)
						{
							Functions.npcSay(_thisActor, Say2C.ALL, "Use " + 384827399 / 65536 + " skill");
						}
						addUseSkillDesire(target, 384827399, 0, 1, 1000000);
					}
					else if(debug)
					{
						Functions.npcSay(_thisActor, Say2C.ALL, "no skill");
					}
				}
				else if(SkillTable.getAbnormalLevel(target, 384827395) < 10)
				{
					if(SkillTable.isMagic(384827402) == 0 && (SkillTable.getAbnormalLevel(_thisActor, 23134209) > 0 || SkillTable.getAbnormalLevel(_thisActor, 87556097) > 0))
					{
						if(debug)
						{
							Functions.npcSay(_thisActor, Say2C.ALL, "Physical defence!");
						}
					}
					else if(SkillTable.isMagic(384827402) == 1 && (SkillTable.getAbnormalLevel(_thisActor, 69730305) > 0 || SkillTable.getAbnormalLevel(_thisActor, 87556097) > 0))
					{
						if(debug)
						{
							Functions.npcSay(_thisActor, Say2C.ALL, "Magic block!");
						}
					}
					else if(SkillTable.mpConsume(384827402) < _thisActor.getCurrentMp() && SkillTable.hpConsume(384827402) < _thisActor.getCurrentHp() && !_thisActor.isSkillDisabledEx(384827402))
					{
						if(debug)
						{
							Functions.npcSay(_thisActor, Say2C.ALL, "Use " + 384827402 / 65536 + " skill");
						}
						addUseSkillDesire(target, 384827402, 0, 1, 1000000);
					}
					else if(debug)
					{
						Functions.npcSay(_thisActor, Say2C.ALL, "no skill");
					}
				}
			}
			if(skillIndex == 386269185 || skillIndex == 386334721 || skillIndex == 386400257 || skillIndex == 386400258)
			{
				if(SkillTable.getAbnormalLevel(target, 384434177) <= 8)
				{
					if(target.getMaxHp() * 0.300000 >= target.getCurrentHp())
					{
						if(SkillTable.isMagic(384434179) == 0 && (SkillTable.getAbnormalLevel(_thisActor, 23134209) > 0 || SkillTable.getAbnormalLevel(_thisActor, 87556097) > 0))
						{
							if(debug)
							{
								Functions.npcSay(_thisActor, Say2C.ALL, "Physical defence!");
							}
						}
						else if(SkillTable.isMagic(384434179) == 1 && (SkillTable.getAbnormalLevel(_thisActor, 69730305) > 0 || SkillTable.getAbnormalLevel(_thisActor, 87556097) > 0))
						{
							if(debug)
							{
								Functions.npcSay(_thisActor, Say2C.ALL, "Magic block!");
							}
						}
						else if(SkillTable.mpConsume(384434179) < _thisActor.getCurrentMp() && SkillTable.hpConsume(384434179) < _thisActor.getCurrentHp() && !_thisActor.isSkillDisabledEx(384434179))
						{
							if(debug)
							{
								Functions.npcSay(_thisActor, Say2C.ALL, "Use " + 384434179 / 65536 + " skill");
							}
							addUseSkillDesire(target, 384434179, 0, 1, 1000000);
						}
						else if(debug)
						{
							Functions.npcSay(_thisActor, Say2C.ALL, "no skill");
						}
					}
					else if(target.getMaxHp() * 0.600000 >= target.getCurrentHp())
					{
						if(SkillTable.isMagic(384434178) == 0 && (SkillTable.getAbnormalLevel(_thisActor, 23134209) > 0 || SkillTable.getAbnormalLevel(_thisActor, 87556097) > 0))
						{
							if(debug)
							{
								Functions.npcSay(_thisActor, Say2C.ALL, "Physical defence!");
							}
						}
						else if(SkillTable.isMagic(384434178) == 1 && (SkillTable.getAbnormalLevel(_thisActor, 69730305) > 0 || SkillTable.getAbnormalLevel(_thisActor, 87556097) > 0))
						{
							if(debug)
							{
								Functions.npcSay(_thisActor, Say2C.ALL, "Magic block!");
							}
						}
						else if(SkillTable.mpConsume(384434178) < _thisActor.getCurrentMp() && SkillTable.hpConsume(384434178) < _thisActor.getCurrentHp() && !_thisActor.isSkillDisabledEx(384434178))
						{
							if(debug)
							{
								Functions.npcSay(_thisActor, Say2C.ALL, "Use " + 384434178 / 65536 + " skill");
							}
							addUseSkillDesire(target, 384434178, 0, 1, 1000000);
						}
						else if(debug)
						{
							Functions.npcSay(_thisActor, Say2C.ALL, "no skill");
						}
					}
					else if(SkillTable.isMagic(384434177) == 0 && (SkillTable.getAbnormalLevel(_thisActor, 23134209) > 0 || SkillTable.getAbnormalLevel(_thisActor, 87556097) > 0))
					{
						if(debug)
						{
							Functions.npcSay(_thisActor, Say2C.ALL, "Physical defence!");
						}
					}
					else if(SkillTable.isMagic(384434177) == 1 && (SkillTable.getAbnormalLevel(_thisActor, 69730305) > 0 || SkillTable.getAbnormalLevel(_thisActor, 87556097) > 0))
					{
						if(debug)
						{
							Functions.npcSay(_thisActor, Say2C.ALL, "Magic block!");
						}
					}
					else if(SkillTable.mpConsume(384434177) < _thisActor.getCurrentMp() && SkillTable.hpConsume(384434177) < _thisActor.getCurrentHp() && !_thisActor.isSkillDisabledEx(384434177))
					{
						if(debug)
						{
							Functions.npcSay(_thisActor, Say2C.ALL, "Use " + 384434177 / 65536 + " skill");
						}
						addUseSkillDesire(target, 384434177, 0, 1, 1000000);
					}
					else if(debug)
					{
						Functions.npcSay(_thisActor, Say2C.ALL, "no skill");
					}
				}
				else if(SkillTable.getAbnormalLevel(target, 384434177) <= 9)
				{
					if(target.getMaxHp() * 0.300000 >= target.getCurrentHp())
					{
						if(SkillTable.isMagic(384434183) == 0 && (SkillTable.getAbnormalLevel(_thisActor, 23134209) > 0 || SkillTable.getAbnormalLevel(_thisActor, 87556097) > 0))
						{
							if(debug)
							{
								Functions.npcSay(_thisActor, Say2C.ALL, "Physical defence!");
							}
						}
						else if(SkillTable.isMagic(384434183) == 1 && (SkillTable.getAbnormalLevel(_thisActor, 69730305) > 0 || SkillTable.getAbnormalLevel(_thisActor, 87556097) > 0))
						{
							if(debug)
							{
								Functions.npcSay(_thisActor, Say2C.ALL, "Magic block!");
							}
						}
						else if(SkillTable.mpConsume(384434183) < _thisActor.getCurrentMp() && SkillTable.hpConsume(384434183) < _thisActor.getCurrentHp() && !_thisActor.isSkillDisabledEx(384434183))
						{
							if(debug)
							{
								Functions.npcSay(_thisActor, Say2C.ALL, "Use " + 384434183 / 65536 + " skill");
							}
							addUseSkillDesire(target, 384434183, 0, 1, 1000000);
						}
						else if(debug)
						{
							Functions.npcSay(_thisActor, Say2C.ALL, "no skill");
						}
					}
					else if(target.getMaxHp() * 0.600000 >= target.getCurrentHp())
					{
						if(SkillTable.isMagic(384434182) == 0 && (SkillTable.getAbnormalLevel(_thisActor, 23134209) > 0 || SkillTable.getAbnormalLevel(_thisActor, 87556097) > 0))
						{
							if(debug)
							{
								Functions.npcSay(_thisActor, Say2C.ALL, "Physical defence!");
							}
						}
						else if(SkillTable.isMagic(384434182) == 1 && (SkillTable.getAbnormalLevel(_thisActor, 69730305) > 0 || SkillTable.getAbnormalLevel(_thisActor, 87556097) > 0))
						{
							if(debug)
							{
								Functions.npcSay(_thisActor, Say2C.ALL, "Magic block!");
							}
						}
						else if(SkillTable.mpConsume(384434182) < _thisActor.getCurrentMp() && SkillTable.hpConsume(384434182) < _thisActor.getCurrentHp() && !_thisActor.isSkillDisabledEx(384434182))
						{
							if(debug)
							{
								Functions.npcSay(_thisActor, Say2C.ALL, "Use " + 384434182 / 65536 + " skill");
							}
							addUseSkillDesire(target, 384434182, 0, 1, 1000000);
						}
						else if(debug)
						{
							Functions.npcSay(_thisActor, Say2C.ALL, "no skill");
						}
					}
					else if(target.getMaxHp() * 0.900000 >= target.getCurrentHp())
					{
						if(SkillTable.isMagic(384434181) == 0 && (SkillTable.getAbnormalLevel(_thisActor, 23134209) > 0 || SkillTable.getAbnormalLevel(_thisActor, 87556097) > 0))
						{
							if(debug)
							{
								Functions.npcSay(_thisActor, Say2C.ALL, "Physical defence!");
							}
						}
						else if(SkillTable.isMagic(384434181) == 1 && (SkillTable.getAbnormalLevel(_thisActor, 69730305) > 0 || SkillTable.getAbnormalLevel(_thisActor, 87556097) > 0))
						{
							if(debug)
							{
								Functions.npcSay(_thisActor, Say2C.ALL, "Magic block!");
							}
						}
						else if(SkillTable.mpConsume(384434181) < _thisActor.getCurrentMp() && SkillTable.hpConsume(384434181) < _thisActor.getCurrentHp() && !_thisActor.isSkillDisabledEx(384434181))
						{
							if(debug)
							{
								Functions.npcSay(_thisActor, Say2C.ALL, "Use " + 384434181 / 65536 + " skill");
							}
							addUseSkillDesire(target, 384434181, 0, 1, 1000000);
						}
						else if(debug)
						{
							Functions.npcSay(_thisActor, Say2C.ALL, "no skill");
						}
					}
					else if(SkillTable.isMagic(384434180) == 0 && (SkillTable.getAbnormalLevel(_thisActor, 23134209) > 0 || SkillTable.getAbnormalLevel(_thisActor, 87556097) > 0))
					{
						if(debug)
						{
							Functions.npcSay(_thisActor, Say2C.ALL, "Physical defence!");
						}
					}
					else if(SkillTable.isMagic(384434180) == 1 && (SkillTable.getAbnormalLevel(_thisActor, 69730305) > 0 || SkillTable.getAbnormalLevel(_thisActor, 87556097) > 0))
					{
						if(debug)
						{
							Functions.npcSay(_thisActor, Say2C.ALL, "Magic block!");
						}
					}
					else if(SkillTable.mpConsume(384434180) < _thisActor.getCurrentMp() && SkillTable.hpConsume(384434180) < _thisActor.getCurrentHp() && !_thisActor.isSkillDisabledEx(384434180))
					{
						if(debug)
						{
							Functions.npcSay(_thisActor, Say2C.ALL, "Use " + 384434180 / 65536 + " skill");
						}
						addUseSkillDesire(target, 384434180, 0, 1, 1000000);
					}
					else if(debug)
					{
						Functions.npcSay(_thisActor, Say2C.ALL, "no skill");
					}
				}
				else if(SkillTable.getAbnormalLevel(target, 384434177) <= 10)
				{
					if(target.getMaxHp() * 0.300000 >= target.getCurrentHp())
					{
						if(SkillTable.isMagic(384434186) == 0 && (SkillTable.getAbnormalLevel(_thisActor, 23134209) > 0 || SkillTable.getAbnormalLevel(_thisActor, 87556097) > 0))
						{
							if(debug)
							{
								Functions.npcSay(_thisActor, Say2C.ALL, "Physical defence!");
							}
						}
						else if(SkillTable.isMagic(384434186) == 1 && (SkillTable.getAbnormalLevel(_thisActor, 69730305) > 0 || SkillTable.getAbnormalLevel(_thisActor, 87556097) > 0))
						{
							if(debug)
							{
								Functions.npcSay(_thisActor, Say2C.ALL, "Magic block!");
							}
						}
						else if(SkillTable.mpConsume(384434186) < _thisActor.getCurrentMp() && SkillTable.hpConsume(384434186) < _thisActor.getCurrentHp() && !_thisActor.isSkillDisabledEx(384434186))
						{
							if(debug)
							{
								Functions.npcSay(_thisActor, Say2C.ALL, "Use " + 384434186 / 65536 + " skill");
							}
							addUseSkillDesire(target, 384434186, 0, 1, 1000000);
						}
						else if(debug)
						{
							Functions.npcSay(_thisActor, Say2C.ALL, "no skill");
						}
					}
					else if(target.getMaxHp() * 0.600000 >= target.getCurrentHp())
					{
						if(SkillTable.isMagic(384434185) == 0 && (SkillTable.getAbnormalLevel(_thisActor, 23134209) > 0 || SkillTable.getAbnormalLevel(_thisActor, 87556097) > 0))
						{
							if(debug)
							{
								Functions.npcSay(_thisActor, Say2C.ALL, "Physical defence!");
							}
						}
						else if(SkillTable.isMagic(384434185) == 1 && (SkillTable.getAbnormalLevel(_thisActor, 69730305) > 0 || SkillTable.getAbnormalLevel(_thisActor, 87556097) > 0))
						{
							if(debug)
							{
								Functions.npcSay(_thisActor, Say2C.ALL, "Magic block!");
							}
						}
						else if(SkillTable.mpConsume(384434185) < _thisActor.getCurrentMp() && SkillTable.hpConsume(384434185) < _thisActor.getCurrentHp() && !_thisActor.isSkillDisabledEx(384434185))
						{
							if(debug)
							{
								Functions.npcSay(_thisActor, Say2C.ALL, "Use " + 384434185 / 65536 + " skill");
							}
							addUseSkillDesire(target, 384434185, 0, 1, 1000000);
						}
						else if(debug)
						{
							Functions.npcSay(_thisActor, Say2C.ALL, "no skill");
						}
					}
					else if(SkillTable.isMagic(384434184) == 0 && (SkillTable.getAbnormalLevel(_thisActor, 23134209) > 0 || SkillTable.getAbnormalLevel(_thisActor, 87556097) > 0))
					{
						if(debug)
						{
							Functions.npcSay(_thisActor, Say2C.ALL, "Physical defence!");
						}
					}
					else if(SkillTable.isMagic(384434184) == 1 && (SkillTable.getAbnormalLevel(_thisActor, 69730305) > 0 || SkillTable.getAbnormalLevel(_thisActor, 87556097) > 0))
					{
						if(debug)
						{
							Functions.npcSay(_thisActor, Say2C.ALL, "Magic block!");
						}
					}
					else if(SkillTable.mpConsume(384434184) < _thisActor.getCurrentMp() && SkillTable.hpConsume(384434184) < _thisActor.getCurrentHp() && !_thisActor.isSkillDisabledEx(384434184))
					{
						if(debug)
						{
							Functions.npcSay(_thisActor, Say2C.ALL, "Use " + 384434184 / 65536 + " skill");
						}
						addUseSkillDesire(target, 384434184, 0, 1, 1000000);
					}
					else if(debug)
					{
						Functions.npcSay(_thisActor, Say2C.ALL, "no skill");
					}
				}
			}
			if(skillIndex == 386531329 || skillIndex == 386531330 || skillIndex == 387121153 || skillIndex == 387121154 || skillIndex == 387186689 || skillIndex == 387186690)
			{
				if(SkillTable.getAbnormalLevel(target, 384565251) < 8)
				{
					if(SkillTable.isMagic(384565251) == 0 && (SkillTable.getAbnormalLevel(_thisActor, 23134209) > 0 || SkillTable.getAbnormalLevel(_thisActor, 87556097) > 0))
					{
						if(debug)
						{
							Functions.npcSay(_thisActor, Say2C.ALL, "Physical defence!");
						}
					}
					else if(SkillTable.isMagic(384565251) == 1 && (SkillTable.getAbnormalLevel(_thisActor, 69730305) > 0 || SkillTable.getAbnormalLevel(_thisActor, 87556097) > 0))
					{
						if(debug)
						{
							Functions.npcSay(_thisActor, Say2C.ALL, "Magic block!");
						}
					}
					else if(SkillTable.mpConsume(384565251) < _thisActor.getCurrentMp() && SkillTable.hpConsume(384565251) < _thisActor.getCurrentHp() && !_thisActor.isSkillDisabledEx(384565251))
					{
						if(debug)
						{
							Functions.npcSay(_thisActor, Say2C.ALL, "Use " + 384565251 / 65536 + " skill");
						}
						addUseSkillDesire(target, 384565251, 0, 1, 1000000);
					}
					else if(debug)
					{
						Functions.npcSay(_thisActor, Say2C.ALL, "no skill");
					}
				}
				else if(SkillTable.getAbnormalLevel(target, 384565251) < 9)
				{
					if(SkillTable.isMagic(384565255) == 0 && (SkillTable.getAbnormalLevel(_thisActor, 23134209) > 0 || SkillTable.getAbnormalLevel(_thisActor, 87556097) > 0))
					{
						if(debug)
						{
							Functions.npcSay(_thisActor, Say2C.ALL, "Physical defence!");
						}
					}
					else if(SkillTable.isMagic(384565255) == 1 && (SkillTable.getAbnormalLevel(_thisActor, 69730305) > 0 || SkillTable.getAbnormalLevel(_thisActor, 87556097) > 0))
					{
						if(debug)
						{
							Functions.npcSay(_thisActor, Say2C.ALL, "Magic block!");
						}
					}
					else if(SkillTable.mpConsume(384565255) < _thisActor.getCurrentMp() && SkillTable.hpConsume(384565255) < _thisActor.getCurrentHp() && !_thisActor.isSkillDisabledEx(384565255))
					{
						if(debug)
						{
							Functions.npcSay(_thisActor, Say2C.ALL, "Use " + 384565255 / 65536 + " skill");
						}
						addUseSkillDesire(target, 384565255, 0, 1, 1000000);
					}
					else if(debug)
					{
						Functions.npcSay(_thisActor, Say2C.ALL, "no skill");
					}
				}
				else if(SkillTable.getAbnormalLevel(target, 384565251) < 10)
				{
					if(SkillTable.isMagic(384565258) == 0 && (SkillTable.getAbnormalLevel(_thisActor, 23134209) > 0 || SkillTable.getAbnormalLevel(_thisActor, 87556097) > 0))
					{
						if(debug)
						{
							Functions.npcSay(_thisActor, Say2C.ALL, "Physical defence!");
						}
					}
					else if(SkillTable.isMagic(384565258) == 1 && (SkillTable.getAbnormalLevel(_thisActor, 69730305) > 0 || SkillTable.getAbnormalLevel(_thisActor, 87556097) > 0))
					{
						if(debug)
						{
							Functions.npcSay(_thisActor, Say2C.ALL, "Magic block!");
						}
					}
					else if(SkillTable.mpConsume(384565258) < _thisActor.getCurrentMp() && SkillTable.hpConsume(384565258) < _thisActor.getCurrentHp() && !_thisActor.isSkillDisabledEx(384565258))
					{
						if(debug)
						{
							Functions.npcSay(_thisActor, Say2C.ALL, "Use " + 384565258 / 65536 + " skill");
						}
						addUseSkillDesire(target, 384565258, 0, 1, 1000000);
					}
					else if(debug)
					{
						Functions.npcSay(_thisActor, Say2C.ALL, "no skill");
					}
				}
			}
			if(skillIndex == 386727937 || skillIndex == 386727938 || skillIndex == 386924545 || skillIndex == 386924546 || skillIndex == 386924547 || skillIndex == 386990081)
			{
				if(SkillTable.getAbnormalLevel(target, 384630787) < 8)
				{
					if(SkillTable.isMagic(384630787) == 0 && (SkillTable.getAbnormalLevel(_thisActor, 23134209) > 0 || SkillTable.getAbnormalLevel(_thisActor, 87556097) > 0))
					{
						if(debug)
						{
							Functions.npcSay(_thisActor, Say2C.ALL, "Physical defence!");
						}
					}
					else if(SkillTable.isMagic(384630787) == 1 && (SkillTable.getAbnormalLevel(_thisActor, 69730305) > 0 || SkillTable.getAbnormalLevel(_thisActor, 87556097) > 0))
					{
						if(debug)
						{
							Functions.npcSay(_thisActor, Say2C.ALL, "Magic block!");
						}
					}
					else if(SkillTable.mpConsume(384630787) < _thisActor.getCurrentMp() && SkillTable.hpConsume(384630787) < _thisActor.getCurrentHp() && !_thisActor.isSkillDisabledEx(384630787))
					{
						if(debug)
						{
							Functions.npcSay(_thisActor, Say2C.ALL, "Use " + 384630787 / 65536 + " skill");
						}
						addUseSkillDesire(target, 384630787, 0, 1, 1000000);
					}
					else if(debug)
					{
						Functions.npcSay(_thisActor, Say2C.ALL, "no skill");
					}
				}
				else if(SkillTable.getAbnormalLevel(target, 384630787) < 9)
				{
					if(SkillTable.isMagic(384630791) == 0 && (SkillTable.getAbnormalLevel(_thisActor, 23134209) > 0 || SkillTable.getAbnormalLevel(_thisActor, 87556097) > 0))
					{
						if(debug)
						{
							Functions.npcSay(_thisActor, Say2C.ALL, "Physical defence!");
						}
					}
					else if(SkillTable.isMagic(384630791) == 1 && (SkillTable.getAbnormalLevel(_thisActor, 69730305) > 0 || SkillTable.getAbnormalLevel(_thisActor, 87556097) > 0))
					{
						if(debug)
						{
							Functions.npcSay(_thisActor, Say2C.ALL, "Magic block!");
						}
					}
					else if(SkillTable.mpConsume(384630791) < _thisActor.getCurrentMp() && SkillTable.hpConsume(384630791) < _thisActor.getCurrentHp() && !_thisActor.isSkillDisabledEx(384630791))
					{
						if(debug)
						{
							Functions.npcSay(_thisActor, Say2C.ALL, "Use " + 384630791 / 65536 + " skill");
						}
						addUseSkillDesire(target, 384630791, 0, 1, 1000000);
					}
					else if(debug)
					{
						Functions.npcSay(_thisActor, Say2C.ALL, "no skill");
					}
				}
				else if(SkillTable.getAbnormalLevel(target, 384630787) < 10)
				{
					if(SkillTable.isMagic(384630794) == 0 && (SkillTable.getAbnormalLevel(_thisActor, 23134209) > 0 || SkillTable.getAbnormalLevel(_thisActor, 87556097) > 0))
					{
						if(debug)
						{
							Functions.npcSay(_thisActor, Say2C.ALL, "Physical defence!");
						}
					}
					else if(SkillTable.isMagic(384630794) == 1 && (SkillTable.getAbnormalLevel(_thisActor, 69730305) > 0 || SkillTable.getAbnormalLevel(_thisActor, 87556097) > 0))
					{
						if(debug)
						{
							Functions.npcSay(_thisActor, Say2C.ALL, "Magic block!");
						}
					}
					else if(SkillTable.mpConsume(384630794) < _thisActor.getCurrentMp() && SkillTable.hpConsume(384630794) < _thisActor.getCurrentHp() && !_thisActor.isSkillDisabledEx(384630794))
					{
						if(debug)
						{
							Functions.npcSay(_thisActor, Say2C.ALL, "Use " + 384630794 / 65536 + " skill");
						}
						addUseSkillDesire(target, 384630794, 0, 1, 1000000);
					}
					else if(debug)
					{
						Functions.npcSay(_thisActor, Say2C.ALL, "no skill");
					}
				}
			}
			if(skillIndex == 386596865 || skillIndex == 386596866 || skillIndex == 386662401 || skillIndex == 386859009)
			{
				if(SkillTable.getAbnormalLevel(target, 384499715) < 8)
				{
					if(SkillTable.isMagic(384499715) == 0 && (SkillTable.getAbnormalLevel(_thisActor, 23134209) > 0 || SkillTable.getAbnormalLevel(_thisActor, 87556097) > 0))
					{
						if(debug)
						{
							Functions.npcSay(_thisActor, Say2C.ALL, "Physical defence!");
						}
					}
					else if(SkillTable.isMagic(384499715) == 1 && (SkillTable.getAbnormalLevel(_thisActor, 69730305) > 0 || SkillTable.getAbnormalLevel(_thisActor, 87556097) > 0))
					{
						if(debug)
						{
							Functions.npcSay(_thisActor, Say2C.ALL, "Magic block!");
						}
					}
					else if(SkillTable.mpConsume(384499715) < _thisActor.getCurrentMp() && SkillTable.hpConsume(384499715) < _thisActor.getCurrentHp() && !_thisActor.isSkillDisabledEx(384499715))
					{
						if(debug)
						{
							Functions.npcSay(_thisActor, Say2C.ALL, "Use " + 384499715 / 65536 + " skill");
						}
						addUseSkillDesire(target, 384499715, 0, 1, 1000000);
					}
					else if(debug)
					{
						Functions.npcSay(_thisActor, Say2C.ALL, "no skill");
					}
				}
				else if(SkillTable.getAbnormalLevel(target, 384499715) < 9)
				{
					if(SkillTable.isMagic(384499715) == 0 && (SkillTable.getAbnormalLevel(_thisActor, 23134209) > 0 || SkillTable.getAbnormalLevel(_thisActor, 87556097) > 0))
					{
						if(debug)
						{
							Functions.npcSay(_thisActor, Say2C.ALL, "Physical defence!");
						}
					}
					else if(SkillTable.isMagic(384499715) == 1 && (SkillTable.getAbnormalLevel(_thisActor, 69730305) > 0 || SkillTable.getAbnormalLevel(_thisActor, 87556097) > 0))
					{
						if(debug)
						{
							Functions.npcSay(_thisActor, Say2C.ALL, "Magic block!");
						}
					}
					else if(SkillTable.mpConsume(384499715) < _thisActor.getCurrentMp() && SkillTable.hpConsume(384499715) < _thisActor.getCurrentHp() && !_thisActor.isSkillDisabledEx(384499715))
					{
						if(debug)
						{
							Functions.npcSay(_thisActor, Say2C.ALL, "Use " + 384499715 / 65536 + " skill");
						}
						addUseSkillDesire(target, 384499715, 0, 1, 1000000);
					}
					else if(debug)
					{
						Functions.npcSay(_thisActor, Say2C.ALL, "no skill");
					}
				}
				else if(SkillTable.getAbnormalLevel(target, 384499715) < 10)
				{
					if(SkillTable.isMagic(384499722) == 0 && (SkillTable.getAbnormalLevel(_thisActor, 23134209) > 0 || SkillTable.getAbnormalLevel(_thisActor, 87556097) > 0))
					{
						if(debug)
						{
							Functions.npcSay(_thisActor, Say2C.ALL, "Physical defence!");
						}
					}
					else if(SkillTable.isMagic(384499722) == 1 && (SkillTable.getAbnormalLevel(_thisActor, 69730305) > 0 || SkillTable.getAbnormalLevel(_thisActor, 87556097) > 0))
					{
						if(debug)
						{
							Functions.npcSay(_thisActor, Say2C.ALL, "Magic block!");
						}
					}
					else if(SkillTable.mpConsume(384499722) < _thisActor.getCurrentMp() && SkillTable.hpConsume(384499722) < _thisActor.getCurrentHp() && !_thisActor.isSkillDisabledEx(384499722))
					{
						if(debug)
						{
							Functions.npcSay(_thisActor, Say2C.ALL, "Use " + 384499722 / 65536 + " skill");
						}
						addUseSkillDesire(target, 384499722, 0, 1, 1000000);
					}
					else if(debug)
					{
						Functions.npcSay(_thisActor, Say2C.ALL, "no skill");
					}
				}
			}
			if(skillIndex == 388235265 || skillIndex == 388300801)
			{
				if(SkillTable.getAbnormalLevel(target, 384892929) < 1)
				{
					if(SkillTable.isMagic(384892929) == 0 && (SkillTable.getAbnormalLevel(_thisActor, 23134209) > 0 || SkillTable.getAbnormalLevel(_thisActor, 87556097) > 0))
					{
						if(debug)
						{
							Functions.npcSay(_thisActor, Say2C.ALL, "Physical defence!");
						}
					}
					else if(SkillTable.isMagic(384892929) == 1 && (SkillTable.getAbnormalLevel(_thisActor, 69730305) > 0 || SkillTable.getAbnormalLevel(_thisActor, 87556097) > 0))
					{
						if(debug)
						{
							Functions.npcSay(_thisActor, Say2C.ALL, "Magic block!");
						}
					}
					else if(SkillTable.mpConsume(384892929) < _thisActor.getCurrentMp() && SkillTable.hpConsume(384892929) < _thisActor.getCurrentHp() && !_thisActor.isSkillDisabledEx(384892929))
					{
						if(debug)
						{
							Functions.npcSay(_thisActor, Say2C.ALL, "Use " + 384892929 / 65536 + " skill");
						}
						addUseSkillDesire(target, 384892929, 0, 1, 1000000);
					}
					else if(debug)
					{
						Functions.npcSay(_thisActor, Say2C.ALL, "no skill");
					}
				}
				else if(SkillTable.getAbnormalLevel(target, 384892929) < 2)
				{
					if(SkillTable.isMagic(384892930) == 0 && (SkillTable.getAbnormalLevel(_thisActor, 23134209) > 0 || SkillTable.getAbnormalLevel(_thisActor, 87556097) > 0))
					{
						if(debug)
						{
							Functions.npcSay(_thisActor, Say2C.ALL, "Physical defence!");
						}
					}
					else if(SkillTable.isMagic(384892930) == 1 && (SkillTable.getAbnormalLevel(_thisActor, 69730305) > 0 || SkillTable.getAbnormalLevel(_thisActor, 87556097) > 0))
					{
						if(debug)
						{
							Functions.npcSay(_thisActor, Say2C.ALL, "Magic block!");
						}
					}
					else if(SkillTable.mpConsume(384892930) < _thisActor.getCurrentMp() && SkillTable.hpConsume(384892930) < _thisActor.getCurrentHp() && !_thisActor.isSkillDisabledEx(384892930))
					{
						if(debug)
						{
							Functions.npcSay(_thisActor, Say2C.ALL, "Use " + 384892930 / 65536 + " skill");
						}
						addUseSkillDesire(target, 384892930, 0, 1, 1000000);
					}
					else if(debug)
					{
						Functions.npcSay(_thisActor, Say2C.ALL, "no skill");
					}
				}
				else if(SkillTable.getAbnormalLevel(target, 384892929) < 3)
				{
					if(SkillTable.isMagic(384892931) == 0 && (SkillTable.getAbnormalLevel(_thisActor, 23134209) > 0 || SkillTable.getAbnormalLevel(_thisActor, 87556097) > 0))
					{
						if(debug)
						{
							Functions.npcSay(_thisActor, Say2C.ALL, "Physical defence!");
						}
					}
					else if(SkillTable.isMagic(384892931) == 1 && (SkillTable.getAbnormalLevel(_thisActor, 69730305) > 0 || SkillTable.getAbnormalLevel(_thisActor, 87556097) > 0))
					{
						if(debug)
						{
							Functions.npcSay(_thisActor, Say2C.ALL, "Magic block!");
						}
					}
					else if(SkillTable.mpConsume(384892931) < _thisActor.getCurrentMp() && SkillTable.hpConsume(384892931) < _thisActor.getCurrentHp() && !_thisActor.isSkillDisabledEx(384892931))
					{
						if(debug)
						{
							Functions.npcSay(_thisActor, Say2C.ALL, "Use " + 384892931 / 65536 + " skill");
						}
						addUseSkillDesire(target, 384892931, 0, 1, 1000000);
					}
					else if(debug)
					{
						Functions.npcSay(_thisActor, Say2C.ALL, "no skill");
					}
				}
				else if(SkillTable.getAbnormalLevel(target, 384892929) < 4)
				{
					if(SkillTable.isMagic(384892932) == 0 && (SkillTable.getAbnormalLevel(_thisActor, 23134209) > 0 || SkillTable.getAbnormalLevel(_thisActor, 87556097) > 0))
					{
						if(debug)
						{
							Functions.npcSay(_thisActor, Say2C.ALL, "Physical defence!");
						}
					}
					else if(SkillTable.isMagic(384892932) == 1 && (SkillTable.getAbnormalLevel(_thisActor, 69730305) > 0 || SkillTable.getAbnormalLevel(_thisActor, 87556097) > 0))
					{
						if(debug)
						{
							Functions.npcSay(_thisActor, Say2C.ALL, "Magic block!");
						}
					}
					else if(SkillTable.mpConsume(384892932) < _thisActor.getCurrentMp() && SkillTable.hpConsume(384892932) < _thisActor.getCurrentHp() && !_thisActor.isSkillDisabledEx(384892932))
					{
						if(debug)
						{
							Functions.npcSay(_thisActor, Say2C.ALL, "Use " + 384892932 / 65536 + " skill");
						}
						addUseSkillDesire(target, 384892932, 0, 1, 1000000);
					}
					else if(debug)
					{
						Functions.npcSay(_thisActor, Say2C.ALL, "no skill");
					}
				}
				else if(SkillTable.getAbnormalLevel(target, 384892929) < 5)
				{
					if(SkillTable.isMagic(384892933) == 0 && (SkillTable.getAbnormalLevel(_thisActor, 23134209) > 0 || SkillTable.getAbnormalLevel(_thisActor, 87556097) > 0))
					{
						if(debug)
						{
							Functions.npcSay(_thisActor, Say2C.ALL, "Physical defence!");
						}
					}
					else if(SkillTable.isMagic(384892933) == 1 && (SkillTable.getAbnormalLevel(_thisActor, 69730305) > 0 || SkillTable.getAbnormalLevel(_thisActor, 87556097) > 0))
					{
						if(debug)
						{
							Functions.npcSay(_thisActor, Say2C.ALL, "Magic block!");
						}
					}
					else if(SkillTable.mpConsume(384892933) < _thisActor.getCurrentMp() && SkillTable.hpConsume(384892933) < _thisActor.getCurrentHp() && !_thisActor.isSkillDisabledEx(384892933))
					{
						if(debug)
						{
							Functions.npcSay(_thisActor, Say2C.ALL, "Use " + 384892933 / 65536 + " skill");
						}
						addUseSkillDesire(target, 384892933, 0, 1, 1000000);
					}
					else if(debug)
					{
						Functions.npcSay(_thisActor, Say2C.ALL, "no skill");
					}
				}
			}
			if(skillIndex == 388366337)
			{
				if(SkillTable.getAbnormalLevel(target, 384958465) < 1)
				{
					if(SkillTable.isMagic(384958465) == 0 && (SkillTable.getAbnormalLevel(_thisActor, 23134209) > 0 || SkillTable.getAbnormalLevel(_thisActor, 87556097) > 0))
					{
						if(debug)
						{
							Functions.npcSay(_thisActor, Say2C.ALL, "Physical defence!");
						}
					}
					else if(SkillTable.isMagic(384958465) == 1 && (SkillTable.getAbnormalLevel(_thisActor, 69730305) > 0 || SkillTable.getAbnormalLevel(_thisActor, 87556097) > 0))
					{
						if(debug)
						{
							Functions.npcSay(_thisActor, Say2C.ALL, "Magic block!");
						}
					}
					else if(SkillTable.mpConsume(384958465) < _thisActor.getCurrentMp() && SkillTable.hpConsume(384958465) < _thisActor.getCurrentHp() && !_thisActor.isSkillDisabledEx(384958465))
					{
						if(debug)
						{
							Functions.npcSay(_thisActor, Say2C.ALL, "Use " + 384958465 / 65536 + " skill");
						}
						addUseSkillDesire(target, 384958465, 0, 1, 1000000);
					}
					else if(debug)
					{
						Functions.npcSay(_thisActor, Say2C.ALL, "no skill");
					}
				}
				else if(SkillTable.getAbnormalLevel(target, 384958465) < 2)
				{
					if(SkillTable.isMagic(384958466) == 0 && (SkillTable.getAbnormalLevel(_thisActor, 23134209) > 0 || SkillTable.getAbnormalLevel(_thisActor, 87556097) > 0))
					{
						if(debug)
						{
							Functions.npcSay(_thisActor, Say2C.ALL, "Physical defence!");
						}
					}
					else if(SkillTable.isMagic(384958466) == 1 && (SkillTable.getAbnormalLevel(_thisActor, 69730305) > 0 || SkillTable.getAbnormalLevel(_thisActor, 87556097) > 0))
					{
						if(debug)
						{
							Functions.npcSay(_thisActor, Say2C.ALL, "Magic block!");
						}
					}
					else if(SkillTable.mpConsume(384958466) < _thisActor.getCurrentMp() && SkillTable.hpConsume(384958466) < _thisActor.getCurrentHp() && !_thisActor.isSkillDisabledEx(384958466))
					{
						if(debug)
						{
							Functions.npcSay(_thisActor, Say2C.ALL, "Use " + 384958466 / 65536 + " skill");
						}
						addUseSkillDesire(target, 384958466, 0, 1, 1000000);
					}
					else if(debug)
					{
						Functions.npcSay(_thisActor, Say2C.ALL, "no skill");
					}
				}
				else if(SkillTable.getAbnormalLevel(target, 384958465) < 3)
				{
					if(SkillTable.isMagic(384958467) == 0 && (SkillTable.getAbnormalLevel(_thisActor, 23134209) > 0 || SkillTable.getAbnormalLevel(_thisActor, 87556097) > 0))
					{
						if(debug)
						{
							Functions.npcSay(_thisActor, Say2C.ALL, "Physical defence!");
						}
					}
					else if(SkillTable.isMagic(384958467) == 1 && (SkillTable.getAbnormalLevel(_thisActor, 69730305) > 0 || SkillTable.getAbnormalLevel(_thisActor, 87556097) > 0))
					{
						if(debug)
						{
							Functions.npcSay(_thisActor, Say2C.ALL, "Magic block!");
						}
					}
					else if(SkillTable.mpConsume(384958467) < _thisActor.getCurrentMp() && SkillTable.hpConsume(384958467) < _thisActor.getCurrentHp() && !_thisActor.isSkillDisabledEx(384958467))
					{
						if(debug)
						{
							Functions.npcSay(_thisActor, Say2C.ALL, "Use " + 384958467 / 65536 + " skill");
						}
						addUseSkillDesire(target, 384958467, 0, 1, 1000000);
					}
					else if(debug)
					{
						Functions.npcSay(_thisActor, Say2C.ALL, "no skill");
					}
				}
				else if(SkillTable.getAbnormalLevel(target, 384958465) < 4)
				{
					if(SkillTable.isMagic(384958468) == 0 && (SkillTable.getAbnormalLevel(_thisActor, 23134209) > 0 || SkillTable.getAbnormalLevel(_thisActor, 87556097) > 0))
					{
						if(debug)
						{
							Functions.npcSay(_thisActor, Say2C.ALL, "Physical defence!");
						}
					}
					else if(SkillTable.isMagic(384958468) == 1 && (SkillTable.getAbnormalLevel(_thisActor, 69730305) > 0 || SkillTable.getAbnormalLevel(_thisActor, 87556097) > 0))
					{
						if(debug)
						{
							Functions.npcSay(_thisActor, Say2C.ALL, "Magic block!");
						}
					}
					else if(SkillTable.mpConsume(384958468) < _thisActor.getCurrentMp() && SkillTable.hpConsume(384958468) < _thisActor.getCurrentHp() && !_thisActor.isSkillDisabledEx(384958468))
					{
						if(debug)
						{
							Functions.npcSay(_thisActor, Say2C.ALL, "Use " + 384958468 / 65536 + " skill");
						}
						addUseSkillDesire(target, 384958468, 0, 1, 1000000);
					}
					else if(debug)
					{
						Functions.npcSay(_thisActor, Say2C.ALL, "no skill");
					}
				}
				else if(SkillTable.getAbnormalLevel(target, 384958465) < 5)
				{
					if(SkillTable.isMagic(384958469) == 0 && (SkillTable.getAbnormalLevel(_thisActor, 23134209) > 0 || SkillTable.getAbnormalLevel(_thisActor, 87556097) > 0))
					{
						if(debug)
						{
							Functions.npcSay(_thisActor, Say2C.ALL, "Physical defence!");
						}
					}
					else if(SkillTable.isMagic(384958469) == 1 && (SkillTable.getAbnormalLevel(_thisActor, 69730305) > 0 || SkillTable.getAbnormalLevel(_thisActor, 87556097) > 0))
					{
						if(debug)
						{
							Functions.npcSay(_thisActor, Say2C.ALL, "Magic block!");
						}
					}
					else if(SkillTable.mpConsume(384958469) < _thisActor.getCurrentMp() && SkillTable.hpConsume(384958469) < _thisActor.getCurrentHp() && !_thisActor.isSkillDisabledEx(384958469))
					{
						if(debug)
						{
							Functions.npcSay(_thisActor, Say2C.ALL, "Use " + 384958469 / 65536 + " skill");
						}
						addUseSkillDesire(target, 384958469, 0, 1, 1000000);
					}
					else if(debug)
					{
						Functions.npcSay(_thisActor, Say2C.ALL, "no skill");
					}
				}
			}
		}
		super.onEvtFinishCasting(skill);
	}

	@Override
	protected void onEvtSpawn()
	{
		if(type == 1)
		{
			_thisActor.setRunning();
			addMoveToDesire(attack_x + Rnd.get(100), attack_y + Rnd.get(100), attack_z, 1);
		}

		L2Character c0 = L2ObjectsStorage.getAsCharacter(_thisActor.param1);
		if(c0 != null)
		{
			_thisActor.setRunning();
			addMoveToDesire(c0.getX(), c0.getY(), c0.getZ(), 1);
		}
		addTimer(7777, 5000);
		addTimer(7787, 900000);
		super.onEvtSpawn();
	}

	@Override
	protected void onEvtTimer(int timerId, Object arg1, Object arg2)
	{
		if(timerId == 7777 && type == 1)
		{
			_thisActor.setSpawnedLoc(_thisActor.getLoc());
		}
		else if(timerId == 7787)
		{
			_thisActor.onDecay();
		}
		else
			super.onEvtTimer(timerId, arg1, arg2);
	}

	@Override
	protected void onEvtClanAttacked(L2Character attacked_member, L2Character attacker, int damage)
	{
		if(_thisActor.i_ai0 == 0)
		{
			_thisActor.i_ai0 = 1;
		}
		if(attacker.isPlayer())
		{
			_thisActor.addDamageHate(attacker, 0, 1);
		}
		else if(!attacker.isPlayer() && CategoryManager.isInCategory(12, attacker.getNpcId()))
		{
			if(!attacker.getPlayer().isDead())
			{
				_thisActor.addDamageHate(attacker, 0, 2);
				_thisActor.addDamageHate(attacker.getPlayer(), 0, 1);
				addAttackDesire(attacker, 1, 100);
			}
			else
			{
				addAttackDesire(attacker, 1, 100);
			}
		}
		super.onEvtClanAttacked(attacked_member, attacker, damage);
	}
}
package ai;

import ai.base.AiImmoSbossBasic;
import ru.l2gw.extensions.scripts.Functions;
import ru.l2gw.gameserver.ai.CtrlIntention;
import ru.l2gw.gameserver.clientpackets.Say2C;
import ru.l2gw.gameserver.model.L2Character;
import ru.l2gw.gameserver.model.entity.category.CategoryManager;
import ru.l2gw.gameserver.tables.SkillTable;
import ru.l2gw.commons.math.Rnd;

/**
 * @author: rage
 * @date: 17.12.11 14:02
 */
public class ImmoSbossSlayerA extends AiImmoSbossBasic
{
	public ImmoSbossSlayerA(L2Character actor)
	{
		super(actor);
		type = "solo_boss_melee";
	}

	@Override
	protected void onEvtSeeCreature(L2Character creature)
	{
		if(creature != null && creature.isPlayer())
		{
			creature.setDieEvent(_thisActor);
		}
		super.onEvtSeeCreature(creature);
	}

	@Override
	protected void onEvtTimer(int timerId, Object arg1, Object arg2)
	{
		if(timerId == TM_ATTACK_COOLDOWN)
		{
			_thisActor.removeAllHateInfoIF(1, 0);
			_thisActor.removeAllHateInfoIF(3, 2000);
			L2Character c0 = _thisActor.getMostHated();
			if(c0 != null && _thisActor.getHate(c0) > 0)
			{
				int i0 = Rnd.get(100);
				if(i0 <= Skillchance_Dim && c0.isPlayer() && !CategoryManager.isInCategory(84, c0.getActiveClass()) && !CategoryManager.isInCategory(86, c0.getActiveClass()) && !CategoryManager.isInCategory(87, c0.getActiveClass()))
				{
					if(SkillTable.isMagic(Skill02_ID) == 0 && (SkillTable.getAbnormalLevel(_thisActor, 23134209) > 0 || SkillTable.getAbnormalLevel(_thisActor, 87556097) > 0))
					{
						if(debug)
						{
							Functions.npcSay(_thisActor, Say2C.ALL, "Phys skill!");
						}
					}
					else if(SkillTable.isMagic(Skill02_ID) == 1 && (SkillTable.getAbnormalLevel(_thisActor, 69730305) > 0 || SkillTable.getAbnormalLevel(_thisActor, 87556097) > 0))
					{
						if(debug)
						{
							Functions.npcSay(_thisActor, Say2C.ALL, "Magic block!");
						}
					}
					else if(Skill02_ID.getMpConsume() < _thisActor.getCurrentMp() && Skill02_ID.getHpConsume() < _thisActor.getCurrentHp() && !_thisActor.isSkillDisabled(Skill02_ID))
					{
						if(debug)
						{
							Functions.npcSay(_thisActor, Say2C.ALL, "Cast " + (Skill02_ID.getId()) + " skill");
						}
						addUseSkillDesire(c0, Skill02_ID, 0, 1, (long) (_thisActor.getHate(c0) * UseSkill_BoostValue));
					}
					else if(debug)
					{
						Functions.npcSay(_thisActor, Say2C.ALL, "no skill condition!");
					}
				}
				else if(i0 <= Skillchance_Low)
				{
					if(SkillTable.isMagic(Skill03_ID) == 0 && (SkillTable.getAbnormalLevel(_thisActor, 23134209) > 0 || SkillTable.getAbnormalLevel(_thisActor, 87556097) > 0))
					{
						if(debug)
						{
							Functions.npcSay(_thisActor, Say2C.ALL, "Phys skill!");
						}
					}
					else if(SkillTable.isMagic(Skill03_ID) == 1 && (SkillTable.getAbnormalLevel(_thisActor, 69730305) > 0 || SkillTable.getAbnormalLevel(_thisActor, 87556097) > 0))
					{
						if(debug)
						{
							Functions.npcSay(_thisActor, Say2C.ALL, "Magic block!");
						}
					}
					else if(Skill03_ID.getMpConsume() < _thisActor.getCurrentMp() && Skill03_ID.getHpConsume() < _thisActor.getCurrentHp() && !_thisActor.isSkillDisabled(Skill03_ID))
					{
						if(debug)
						{
							Functions.npcSay(_thisActor, Say2C.ALL, "Cast " + (Skill03_ID.getId()) + " skill");
						}
						addUseSkillDesire(c0, Skill03_ID, 0, 1, (long) (_thisActor.getHate(c0) * UseSkill_BoostValue));
					}
					else if(debug)
					{
						Functions.npcSay(_thisActor, Say2C.ALL, "no skill condition!");
					}
				}
				else if(i0 <= Skillchance_High)
				{
					if(SkillTable.isMagic(Skill01_ID) == 0 && (SkillTable.getAbnormalLevel(_thisActor, 23134209) > 0 || SkillTable.getAbnormalLevel(_thisActor, 87556097) > 0))
					{
						if(debug)
						{
							Functions.npcSay(_thisActor, Say2C.ALL, "Phys skill!");
						}
					}
					else if(SkillTable.isMagic(Skill01_ID) == 1 && (SkillTable.getAbnormalLevel(_thisActor, 69730305) > 0 || SkillTable.getAbnormalLevel(_thisActor, 87556097) > 0))
					{
						if(debug)
						{
							Functions.npcSay(_thisActor, Say2C.ALL, "Magic block!");
						}
					}
					else if(Skill01_ID.getMpConsume() < _thisActor.getCurrentMp() && Skill01_ID.getHpConsume() < _thisActor.getCurrentHp() && !_thisActor.isSkillDisabled(Skill01_ID))
					{
						if(debug)
						{
							Functions.npcSay(_thisActor, Say2C.ALL, "Cast " + (Skill01_ID.getId()) + " skill");
						}
						addUseSkillDesire(c0, Skill01_ID, 0, 1, (long) (_thisActor.getHate(c0) * UseSkill_BoostValue));
					}
					else if(debug)
					{
						Functions.npcSay(_thisActor, Say2C.ALL, "no skill condition!");
					}
				}
				else
				{
					addAttackDesire(c0, 1, DEFAULT_DESIRE);
				}
			}
			_thisActor.removeAllHateInfoIF(1, 0);
			_thisActor.removeAllHateInfoIF(3, 2000);
			if(type.equals("dot") || type.equals("cc") || type.equals("con") || type.equals("ambush_dc_kamikaze") || type.equals("solo_boss_caster") || type.equals("duo_boss_caster") || type.equals("echmus"))
			{
				if(_thisActor.getAggroListSize() != 0 && _intention != CtrlIntention.AI_INTENTION_ACTIVE)
				{
					c0 = _thisActor.getMostHated();
					if(c0 != null && _thisActor.getHate(c0) > 0)
					{
						if(Skill01_ID.getMpConsume() < _thisActor.getCurrentMp() && Skill01_ID.getHpConsume() < _thisActor.getCurrentHp() && ((SkillTable.isMagic(Skill01_ID) == 0 && (SkillTable.getAbnormalLevel(_thisActor, 23134209) <= 0 || SkillTable.getAbnormalLevel(_thisActor, 87556097) <= 0)) || (SkillTable.isMagic(Skill01_ID) == 1 && (SkillTable.getAbnormalLevel(_thisActor, 69730305) <= 0 || SkillTable.getAbnormalLevel(_thisActor, 87556097) <= 0))))
						{
							if(SkillTable.isMagic(Skill01_ID) == 0 && (SkillTable.getAbnormalLevel(_thisActor, 23134209) > 0 || SkillTable.getAbnormalLevel(_thisActor, 87556097) > 0))
							{
								if(debug)
								{
									Functions.npcSay(_thisActor, Say2C.ALL, "Phys skill!");
								}
							}
							else if(SkillTable.isMagic(Skill01_ID) == 1 && (SkillTable.getAbnormalLevel(_thisActor, 69730305) > 0 || SkillTable.getAbnormalLevel(_thisActor, 87556097) > 0))
							{
								if(debug)
								{
									Functions.npcSay(_thisActor, Say2C.ALL, "Magic block!");
								}
							}
							else if(Skill01_ID.getMpConsume() < _thisActor.getCurrentMp() && Skill01_ID.getHpConsume() < _thisActor.getCurrentHp() && !_thisActor.isSkillDisabled(Skill01_ID))
							{
								if(debug)
								{
									Functions.npcSay(_thisActor, Say2C.ALL, "Cast " + (Skill01_ID.getId()) + " skill");
								}
								addUseSkillDesire(c0, Skill01_ID, 0, 1, (long) (100 * UseSkill_BoostValue));
							}
							else if(debug)
							{
								Functions.npcSay(_thisActor, Say2C.ALL, "no skill condition!");
							}
						}
						else
						{
							addAttackDesire(c0, 1, DEFAULT_DESIRE);
						}
					}
				}
				addTimer(TM_ATTACK_COOLDOWN, (TIME_ATTACK_COOLDOWN_CASTER + Rnd.get(TIME_ATTACK_COOLDOWN_CASTER)) * 1000);
			}
			else if(_thisActor.getAggroListSize() != 0 && _intention != CtrlIntention.AI_INTENTION_ACTIVE)
			{
				c0 = _thisActor.getMostHated();
				if(c0 != null && _thisActor.getHate(c0) > 0)
				{
					addAttackDesire(c0, 1, 100);
				}
			}
			addTimer(TM_ATTACK_COOLDOWN, (TIME_ATTACK_COOLDOWN_MELEE + Rnd.get(TIME_ATTACK_COOLDOWN_MELEE)) * 1000);
		}
	}

	@Override
	protected void onEvtDieSet(L2Character talker)
	{
		if(talker != null && talker.isDead() && talker.isPlayer())
		{
			if(debug)
			{
				Functions.npcSay(_thisActor, Say2C.ALL, "Player killed: " + talker.getName());
			}
			if(SkillTable.isMagic(386203649) == 0 && (SkillTable.getAbnormalLevel(_thisActor, 23134209) > 0 || SkillTable.getAbnormalLevel(_thisActor, 87556097) > 0))
			{
				if(debug)
				{
					Functions.npcSay(_thisActor, Say2C.ALL, "Phys skill!");
				}
			}
			else if(SkillTable.isMagic(386203649) == 1 && (SkillTable.getAbnormalLevel(_thisActor, 69730305) > 0 || SkillTable.getAbnormalLevel(_thisActor, 87556097) > 0))
			{
				if(debug)
				{
					Functions.npcSay(_thisActor, Say2C.ALL, "Magic block!");
				}
			}
			else if(SkillTable.mpConsume(386203649) < _thisActor.getCurrentMp() && SkillTable.hpConsume(386203649) < _thisActor.getCurrentHp() && !_thisActor.isSkillDisabledEx(386203649))
			{
				if(debug)
				{
					Functions.npcSay(_thisActor, Say2C.ALL, "Cast " + (386203649 / 65536) + " skill");
				}
				addUseSkillDesire(talker, 386203649, 1, 1, (long) (Maximum_Hate * UseSkill_BoostValue));
			}
			else if(debug)
			{
				Functions.npcSay(_thisActor, Say2C.ALL, "no skill condition!");
			}
		}
	}
}
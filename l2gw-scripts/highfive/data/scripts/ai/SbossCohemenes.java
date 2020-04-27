package ai;

import ai.base.AiImmoSbossBasic;
import ru.l2gw.commons.math.Rnd;
import ru.l2gw.extensions.scripts.Functions;
import ru.l2gw.gameserver.ai.CtrlIntention;
import ru.l2gw.gameserver.clientpackets.Say2C;
import ru.l2gw.gameserver.instancemanager.InstanceManager;
import ru.l2gw.gameserver.model.L2Character;
import ru.l2gw.gameserver.model.L2Skill;
import ru.l2gw.gameserver.model.instances.L2NpcInstance;
import ru.l2gw.gameserver.model.npcmaker.DefaultMaker;
import ru.l2gw.gameserver.tables.SkillTable;

import static ru.l2gw.gameserver.ai.CtrlIntention.AI_INTENTION_ATTACK;

/**
 * @author: rage
 * @date: 15.12.11 16:10
 */
public class SbossCohemenes extends AiImmoSbossBasic
{
	public L2Skill Skill_sbossdef2 = SkillTable.getInstance().getInfo(388562945);
	public int my_victim = 25635;
	public String my_victim_class = "ImmoSbossArgekhunte";
	public int TM_DEFENSE_CHANGE = 780009;
	public int TIME_DEFENSE_CHANGE = 60;
	public int z2_sb1_x = -178418;
	public int z2_sb1_y = 211653;
	public int z2_sb1_z = -12029;
	public int z2_sb2_x = -178417;
	public int z2_sb2_y = 206558;
	public int z2_sb2_z = -12032;
	public int z2_sb3_x = -180911;
	public int z2_sb3_y = 206551;
	public int z2_sb3_z = -12028;
	public int z2_sb4_x = -180911;
	public int z2_sb4_y = 211652;
	public int z2_sb4_z = -12028;

	public SbossCohemenes(L2Character actor)
	{
		super(actor);
		type = "duo_boss_caster";
		Party_Type = 2;
		Skill_sbossdef = SkillTable.getInstance().getInfo(388628481);
	}

	@Override
	protected void onEvtSpawn()
	{
		_thisActor.i_quest2 = 0;
		_thisActor.i_quest3 = 0;
		_thisActor.i_quest4 = 0;
		addTimer(TM_DEFENSE_CHANGE, TIME_DEFENSE_CHANGE * 1000);
		Functions.npcSay(_thisActor, Say2C.SHOUT, 1800233);
		_thisActor.createOnePrivate(my_victim, my_victim_class, 0, 0, _thisActor.getX() + Rnd.get(50) - Rnd.get(50), _thisActor.getY() + Rnd.get(50) - Rnd.get(50), _thisActor.getZ(), Rnd.get(61440), room, 0, 0);
		super.onEvtSpawn();
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
				if(i0 <= Skillchance_High && _thisActor.getLoc().distance3D(c0.getLoc()) <= 200)
				{
					if(SkillTable.isMagic(Skill02_ID) == 0 && (SkillTable.getAbnormalLevel(_thisActor, 23134209) > 0 || SkillTable.getAbnormalLevel(_thisActor, 87556097) > 0))
					{
						if(debug)
						{
							Functions.npcSay(_thisActor, Say2C.ALL, "Phys defence");
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
							Functions.npcSay(_thisActor, Say2C.ALL, "Cast " + Skill02_ID.getId() + " skill");
						}
						addUseSkillDesire(c0, Skill02_ID, 0, 1, (long) (_thisActor.getHate(c0) * UseSkill_BoostValue));
					}
					else if(debug)
					{
						Functions.npcSay(_thisActor, Say2C.ALL, " no skill condition!");
					}
				}
				else if(SkillTable.isMagic(Skill01_ID) == 0 && (SkillTable.getAbnormalLevel(_thisActor, 23134209) > 0 || SkillTable.getAbnormalLevel(_thisActor, 87556097) > 0))
				{
					if(debug)
					{
						Functions.npcSay(_thisActor, Say2C.ALL, "Phys defence");
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
						Functions.npcSay(_thisActor, Say2C.ALL, "Cast " + Skill01_ID.getId() + " skill");
					}
					addUseSkillDesire(c0, Skill02_ID, 0, 1, (long) (_thisActor.getHate(c0) * UseSkill_BoostValue));
				}
				else if(debug)
				{
					Functions.npcSay(_thisActor, Say2C.ALL, " no skill condition!");
				}
			}
			_thisActor.removeAllHateInfoIF(1, 0);
			_thisActor.removeAllHateInfoIF(3, 2000);
			if(type.equals("dot") || type.equals("cc") || type.equals("con") || type.equals("ambush_dc_kamikaze") || type.equals("solo_boss_caster") || type.equals("duo_boss_caster") || type.equals("echmus"))
			{
				if(_thisActor.getAggroListSize() != 0 && (_intention == CtrlIntention.AI_INTENTION_ACTIVE))
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
									Functions.npcSay(_thisActor, Say2C.ALL, "Phys defence");
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
									Functions.npcSay(_thisActor, Say2C.ALL, "Cast " + Skill01_ID.getId() + " skill");
								}
								addUseSkillDesire(c0, Skill01_ID, 0, 1, (long) (100 * UseSkill_BoostValue));
							}
							else if(debug)
							{
								Functions.npcSay(_thisActor, Say2C.ALL, " no skill condition!");
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
			else if(_thisActor.getAggroListSize() != 0 && (_intention != AI_INTENTION_ATTACK))
			{
				c0 = _thisActor.getMostHated();
				if(c0 != null && _thisActor.getHate(c0) > 0)
				{
					addAttackDesire(c0, 1, DEFAULT_DESIRE);
				}
			}
			addTimer(TM_ATTACK_COOLDOWN, (TIME_ATTACK_COOLDOWN_MELEE + Rnd.get(TIME_ATTACK_COOLDOWN_MELEE)) * 1000);
		}
		else if(timerId == TM_DEFENSE_CHANGE)
		{
			if(_thisActor.i_quest2 == 1)
			{
				Functions.npcSay(_thisActor, Say2C.SHOUT, 1800237);
				_thisActor.dispelByAbnormal(Skill_sbossdef);
				_thisActor.dispelByAbnormal(Skill_sbossdef2);
				_thisActor.createOnePrivate(my_victim, my_victim_class, 0, 0, _thisActor.getX() + Rnd.get(50) - Rnd.get(50), _thisActor.getY() + Rnd.get(50) - Rnd.get(50), _thisActor.getZ(), Rnd.get(61440), room, 0, 0);
				_thisActor.i_quest2 = 0;
			}
			else if(_thisActor.i_quest3 > 100 && _thisActor.i_quest3 > _thisActor.i_quest4 * 4)
			{
				Functions.npcSay(_thisActor, Say2C.SHOUT, 1800254);
				_thisActor.dispelByAbnormal(Skill_sbossdef);
				addUseSkillDesire(_thisActor, Skill_sbossdef2, 1, 1, Maximum_Hate);
			}
			else if(_thisActor.i_quest4 > 50 && _thisActor.i_quest4 > _thisActor.i_quest3 * 2)
			{
				Functions.npcSay(_thisActor, Say2C.SHOUT, 1800255);
				_thisActor.dispelByAbnormal(Skill_sbossdef2);
				addUseSkillDesire(_thisActor, Skill_sbossdef, 1, 1, Maximum_Hate);
			}
			_thisActor.i_quest3 = 0;
			_thisActor.i_quest4 = 0;
			addTimer(TM_DEFENSE_CHANGE, TIME_DEFENSE_CHANGE * 1000);
		}
		super.onEvtTimer(timerId, arg1, arg2);
	}

	@Override
	protected void onEvtDieSet(L2Character talker)
	{
		if(talker != null && talker.isDead() && talker.isPlayer())
		{
			if(debug)
			{
				Functions.npcSay(_thisActor, Say2C.ALL, "Player died: " + talker.getName());
			}
			if(SkillTable.isMagic(Skill03_ID) == 0 && (SkillTable.getAbnormalLevel(_thisActor, 23134209) > 0 || SkillTable.getAbnormalLevel(_thisActor, 87556097) > 0))
			{
				if(debug)
				{
					Functions.npcSay(_thisActor, Say2C.ALL, "Phys defence");
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
					Functions.npcSay(_thisActor, Say2C.ALL, "Cast " + Skill03_ID.getId() + " skill");
				}
				addUseSkillDesire(talker, Skill03_ID, 1, 1, (long) (Maximum_Hate * UseSkill_BoostValue));
			}
			else if(debug)
			{
				Functions.npcSay(_thisActor, Say2C.ALL, " no skill condition!");
			}
		}
	}

	@Override
	protected void onEvtClanDead(L2NpcInstance victim)
	{
		if(victim != null && victim.getNpcId() != my_victim)
		{
			if(debug)
			{
				Functions.npcSay(_thisActor, Say2C.ALL, "NPC Dead: " + victim.getName());
			}
			if(SkillTable.isMagic(Skill04_ID) == 0 && (SkillTable.getAbnormalLevel(_thisActor, 23134209) > 0 || SkillTable.getAbnormalLevel(_thisActor, 87556097) > 0))
			{
				if(debug)
				{
					Functions.npcSay(_thisActor, Say2C.ALL, "Phys defence");
				}
			}
			else if(SkillTable.isMagic(Skill04_ID) == 1 && (SkillTable.getAbnormalLevel(_thisActor, 69730305) > 0 || SkillTable.getAbnormalLevel(_thisActor, 87556097) > 0))
			{
				if(debug)
				{
					Functions.npcSay(_thisActor, Say2C.ALL, "Magic block!");
				}
			}
			else if(Skill04_ID.getMpConsume() < _thisActor.getCurrentMp() && Skill04_ID.getHpConsume() < _thisActor.getCurrentHp() && !_thisActor.isSkillDisabled(Skill04_ID))
			{
				if(debug)
				{
					Functions.npcSay(_thisActor, Say2C.ALL, "Cast " + Skill04_ID.getId() + " skill");
				}
				addUseSkillDesire(victim, Skill04_ID, 1, 1, (long) (Maximum_Hate * UseSkill_BoostValue));
			}
			else if(debug)
			{
				Functions.npcSay(_thisActor, Say2C.ALL, " no skill condition!");
			}
		}
		super.onEvtClanDead(victim);
	}

	@Override
	protected void onEvtPartyDead(L2NpcInstance partyPrivate)
	{
		if(partyPrivate != _thisActor)
		{
			_thisActor.i_quest2 = 1;
		}
	}

	@Override
	protected void onEvtScriptEvent(int eventId, Object arg1, Object arg2)
	{
		if(eventId == 78010064)
		{
			Functions.npcSay(_thisActor, Say2C.SHOUT, 1800234);
			_thisActor.dispelByAbnormal(Skill_sbossdef);
			_thisActor.dispelByAbnormal(Skill_sbossdef2);
		}
		else if(eventId == 78010070)
		{
			Functions.npcSay(_thisActor, Say2C.SHOUT, 1800235);
			_thisActor.onDecay();
		}
		super.onEvtScriptEvent(eventId, arg1, arg2);
	}

	@Override
	protected void onEvtAttacked(L2Character attacker, int damage, L2Skill skill)
	{
		if(attacker == null)
			return;

		if(!_thisActor.isInRange(attacker, 200) || skill != null && SkillTable.isMagic(skill.getId()) == 1)
		{
			_thisActor.i_quest4++;
			_thisActor.i_quest3--;
		}
		else
		{
			_thisActor.i_quest3++;
			_thisActor.i_quest4--;
		}

		super.onEvtAttacked(attacker, damage, skill);
	}

	@Override
	protected void onEvtDead(L2Character killer)
	{
		super.onEvtDead(killer);
		Functions.npcSay(_thisActor, Say2C.SHOUT, 1800236);
		DefaultMaker maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z2_a_dispatcher_maker);
		if(maker0 != null)
		{
			maker0.onScriptEvent(78010071, 0, 0);
		}
	}
}
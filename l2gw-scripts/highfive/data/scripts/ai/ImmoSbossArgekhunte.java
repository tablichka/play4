package ai;

import ai.base.AiImmoSbossBasic;
import ru.l2gw.commons.math.Rnd;
import ru.l2gw.extensions.scripts.Functions;
import ru.l2gw.gameserver.ai.CtrlEvent;
import ru.l2gw.gameserver.ai.CtrlIntention;
import ru.l2gw.gameserver.clientpackets.Say2C;
import ru.l2gw.gameserver.instancemanager.InstanceManager;
import ru.l2gw.gameserver.model.L2Character;
import ru.l2gw.gameserver.model.L2Skill;
import ru.l2gw.gameserver.model.entity.category.CategoryManager;
import ru.l2gw.gameserver.model.instances.L2NpcInstance;
import ru.l2gw.gameserver.model.npcmaker.DefaultMaker;
import ru.l2gw.gameserver.tables.SkillTable;

/**
 * @author: rage
 * @date: 16.12.11 12:29
 */
public class ImmoSbossArgekhunte extends AiImmoSbossBasic
{
	public L2Skill Skill_Invin = SkillTable.getInstance().getInfo(355008513);
	public int TM_Invincibility = 78008;
	public int TIME_Invincibility = 60;
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
	public String mark_maker_01 = "rumwarsha14_1424_a_sb1m1";
	public String mark_maker_02 = "rumwarsha14_1424_a_sb2m1";
	public String mark_maker_03 = "rumwarsha14_1424_a_sb3m1";
	public String mark_maker_04 = "rumwarsha14_1424_a_sb4m1";

	public ImmoSbossArgekhunte(L2Character actor)
	{
		super(actor);
		type = "duo_boss_melee";
		Aggressive_Time = 3.000000f;
		ATTACKED_Weight_Point = 0.000000f;
		Party_Type = 1;
		Skill_sbossdef = SkillTable.getInstance().getInfo(388562945);
	}

	@Override
	protected void onEvtSpawn()
	{
		_thisActor.i_quest1 = 0;
		_thisActor.i_quest2 = (int) _thisActor.param1;
		Functions.npcSay(_thisActor, Say2C.SHOUT, 1800238);
		super.onEvtSpawn();
	}

	@Override
	protected void onEvtTimer(int timerId, Object arg1, Object arg2)
	{
		if(timerId == TM_ATTACK_COOLDOWN)
		{
			if(!_thisActor.isMyBossAlive())
			{
				_thisActor.doDie(null);
				return;
			}
			_thisActor.removeAllHateInfoIF(1, 0);
			_thisActor.removeAllHateInfoIF(3, 2000);
			L2Character c0 = _thisActor.getMostHated();
			if(c0 != null && _thisActor.getHate(c0) > 0)
			{
				int i0 = Rnd.get(100);
				if(i0 <= Skillchance_Dim && c0.isPlayer() && (CategoryManager.isInCategory(88, c0.getActiveClass()) || CategoryManager.isInCategory(91, c0.getActiveClass()) || CategoryManager.isInCategory(93, c0.getActiveClass())))
				{
					if(SkillTable.isMagic(Skill03_ID) == 0 && (SkillTable.getAbnormalLevel(_thisActor, 23134209) > 0 || SkillTable.getAbnormalLevel(_thisActor, 87556097) > 0))
					{
						if(debug)
						{
							Functions.npcSay(_thisActor, Say2C.ALL, "Phys defence!");
						}
					}
					else if(SkillTable.isMagic(Skill03_ID) == 1 && (SkillTable.getAbnormalLevel(_thisActor, 69730305) > 0 || SkillTable.getAbnormalLevel(_thisActor, 87556097) > 0))
					{
						if(debug)
						{
							Functions.npcSay(_thisActor, Say2C.ALL, "Magic block!");
						}
					}
					else if(Skill03_ID != null && Skill03_ID.getMpConsume() < _thisActor.getCurrentMp() && Skill03_ID.getHpConsume() < _thisActor.getCurrentHp() && !_thisActor.isSkillDisabled(Skill03_ID))
					{
						if(debug)
						{
							Functions.npcSay(_thisActor, Say2C.ALL, "Cast " + Skill03_ID.getId() + " skill");
						}
						addUseSkillDesire(c0, Skill03_ID, 0, 1, (long) (_thisActor.getHate(c0) * UseSkill_BoostValue));
					}
					else if(debug)
					{
						Functions.npcSay(_thisActor, Say2C.ALL, "no skill condition!");
					}
				}
				else if(i0 <= Skillchance_Low)
				{
					if(c0.isPlayer() && (CategoryManager.isInCategory(89, c0.getActiveClass()) || CategoryManager.isInCategory(86, c0.getActiveClass()) || CategoryManager.isInCategory(87, c0.getActiveClass())))
					{
						if(SkillTable.isMagic(Skill04_ID) == 0 && (SkillTable.getAbnormalLevel(_thisActor, 23134209) > 0 || SkillTable.getAbnormalLevel(_thisActor, 87556097) > 0))
						{
							if(debug)
							{
								Functions.npcSay(_thisActor, Say2C.ALL, "Phys defence!");
							}
						}
						else if(SkillTable.isMagic(Skill04_ID) == 1 && (SkillTable.getAbnormalLevel(_thisActor, 69730305) > 0 || SkillTable.getAbnormalLevel(_thisActor, 87556097) > 0))
						{
							if(debug)
							{
								Functions.npcSay(_thisActor, Say2C.ALL, "Magic block!");
							}
						}
						else if(Skill04_ID != null && Skill04_ID.getMpConsume() < _thisActor.getCurrentMp() && Skill04_ID.getHpConsume() < _thisActor.getCurrentHp() && !_thisActor.isSkillDisabled(Skill04_ID))
						{
							if(debug)
							{
								Functions.npcSay(_thisActor, Say2C.ALL, "Cast " + Skill04_ID.getId() + " skill");
							}
							addUseSkillDesire(c0, Skill04_ID, 0, 1, (long) (_thisActor.getHate(c0) * UseSkill_BoostValue));
						}
						else if(debug)
						{
							Functions.npcSay(_thisActor, Say2C.ALL, "no skill condition!");
						}
					}
					else if(SkillTable.isMagic(Skill02_ID) == 0 && (SkillTable.getAbnormalLevel(_thisActor, 23134209) > 0 || SkillTable.getAbnormalLevel(_thisActor, 87556097) > 0))
					{
						if(debug)
						{
							Functions.npcSay(_thisActor, Say2C.ALL, "Phys defence!");
						}
					}
					else if(SkillTable.isMagic(Skill02_ID) == 1 && (SkillTable.getAbnormalLevel(_thisActor, 69730305) > 0 || SkillTable.getAbnormalLevel(_thisActor, 87556097) > 0))
					{
						if(debug)
						{
							Functions.npcSay(_thisActor, Say2C.ALL, "Magic block!");
						}
					}
					else if(Skill02_ID != null && Skill02_ID.getMpConsume() < _thisActor.getCurrentMp() && Skill02_ID.getHpConsume() < _thisActor.getCurrentHp() && !_thisActor.isSkillDisabled(Skill02_ID))
					{
						if(debug)
						{
							Functions.npcSay(_thisActor, Say2C.ALL, "Cast " + Skill02_ID.getId() + " skill");
						}
						addUseSkillDesire(c0, Skill02_ID, 0, 1, (long) (_thisActor.getHate(c0) * UseSkill_BoostValue));
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
							Functions.npcSay(_thisActor, Say2C.ALL, "Phys defence!");
						}
					}
					else if(SkillTable.isMagic(Skill01_ID) == 1 && (SkillTable.getAbnormalLevel(_thisActor, 69730305) > 0 || SkillTable.getAbnormalLevel(_thisActor, 87556097) > 0))
					{
						if(debug)
						{
							Functions.npcSay(_thisActor, Say2C.ALL, "Magic block!");
						}
					}
					else if(Skill01_ID != null && Skill01_ID.getMpConsume() < _thisActor.getCurrentMp() && Skill01_ID.getHpConsume() < _thisActor.getCurrentHp() && !_thisActor.isSkillDisabled(Skill01_ID))
					{
						if(debug)
						{
							Functions.npcSay(_thisActor, Say2C.ALL, "Cast " + Skill01_ID.getId() + " skill");
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
				if(_thisActor.getAggroListSize() != 0 && (_intention != CtrlIntention.AI_INTENTION_ATTACK))
				{
					c0 = _thisActor.getMostHated();
					if(c0 != null && _thisActor.getHate(c0) > 0)
					{
						if(Skill01_ID != null && Skill01_ID.getMpConsume() < _thisActor.getCurrentMp() && Skill01_ID.getHpConsume() < _thisActor.getCurrentHp() && ((SkillTable.isMagic(Skill01_ID) == 0 && (SkillTable.getAbnormalLevel(_thisActor, 23134209) <= 0 || SkillTable.getAbnormalLevel(_thisActor, 87556097) <= 0)) || (SkillTable.isMagic(Skill01_ID) == 1 && (SkillTable.getAbnormalLevel(_thisActor, 69730305) <= 0 || SkillTable.getAbnormalLevel(_thisActor, 87556097) <= 0))))
						{
							if(SkillTable.isMagic(Skill01_ID) == 0 && (SkillTable.getAbnormalLevel(_thisActor, 23134209) > 0 || SkillTable.getAbnormalLevel(_thisActor, 87556097) > 0))
							{
								if(debug)
								{
									Functions.npcSay(_thisActor, Say2C.ALL, "Phys defence!");
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
			else if(_thisActor.getAggroListSize() != 0 && (_intention != CtrlIntention.AI_INTENTION_ATTACK))
			{
				c0 = _thisActor.getMostHated();
				if(c0 != null && _thisActor.getHate(c0) > 0)
				{
					addAttackDesire(c0, 1, DEFAULT_DESIRE);
				}
			}
			addTimer(TM_ATTACK_COOLDOWN, (TIME_ATTACK_COOLDOWN_MELEE + Rnd.get(TIME_ATTACK_COOLDOWN_MELEE)) * 1000);
		}
		else if(timerId == TM_Invincibility)
		{
			Functions.npcSay(_thisActor, Say2C.SHOUT, 7865);
			_thisActor.doDie(null);
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
			if(_thisActor.getCurrentHp() <= _thisActor.getMaxHp() * 0.200000)
			{
				if(SkillTable.isMagic(386203649) == 0 && (SkillTable.getAbnormalLevel(_thisActor, 23134209) > 0 || SkillTable.getAbnormalLevel(_thisActor, 87556097) > 0))
				{
					if(debug)
					{
						Functions.npcSay(_thisActor, Say2C.ALL, "Phys defence!");
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

	@Override
	protected void onEvtScriptEvent(int eventId, Object arg1, Object arg2)
	{
		if(eventId == 78010062)
		{
			if((Integer) arg1 == 212)
			{
				Functions.npcSay(_thisActor, Say2C.SHOUT, 1800889);
				_thisActor.i_quest2 = 212;
				_thisActor.getLeader().teleToLocation(z2_sb1_x, z2_sb1_y, z2_sb1_z);
				_thisActor.teleToLocation(z2_sb1_x, z2_sb1_y, z2_sb1_z);
			}
			else if((Integer) arg1 == 222)
			{
				Functions.npcSay(_thisActor, Say2C.SHOUT, 1800889);
				_thisActor.i_quest2 = 222;
				_thisActor.getLeader().teleToLocation(z2_sb2_x, z2_sb2_y, z2_sb2_z);
				_thisActor.teleToLocation(z2_sb2_x, z2_sb2_y, z2_sb2_z);
			}
			else if((Integer) arg1 == 232)
			{
				Functions.npcSay(_thisActor, Say2C.SHOUT, 1800889);
				_thisActor.i_quest2 = 232;
				_thisActor.getLeader().teleToLocation(z2_sb3_x, z2_sb3_y, z2_sb3_z);
				_thisActor.teleToLocation(z2_sb3_x, z2_sb3_y, z2_sb3_z);
			}
			else if((Integer) arg1 == 242)
			{
				Functions.npcSay(_thisActor, Say2C.SHOUT, 1800889);
				_thisActor.i_quest2 = 242;
				_thisActor.getLeader().teleToLocation(z2_sb4_x, z2_sb4_y, z2_sb4_z);
				_thisActor.teleToLocation(z2_sb4_x, z2_sb4_y, z2_sb4_z);
			}
		}
		super.onEvtScriptEvent(eventId, arg1, arg2);
	}

	@Override
	protected void onEvtPartyAttacked(L2Character attacker, L2Character victim, int damage)
	{
		if(victim == _thisActor.getLeader() && victim.getCurrentHp() <= victim.getMaxHp() * 0.250000 && _thisActor.i_quest1 == 0)
		{
			Functions.npcSay(_thisActor, Say2C.SHOUT, 1800239);
			if(SkillTable.getAbnormalLevel(_thisActor, Skill_AntiParty_atk01) < 1 && _thisActor.getCurrentHp() <= _thisActor.getMaxHp() * 0.600000)
			{
				if(Skill_AntiParty_atk01.getMpConsume() < _thisActor.getCurrentMp())
				{
					addUseSkillDesire(_thisActor, Skill_AntiParty_atk01, 1, 1, Maximum_Hate);
				}
			}
			else if(SkillTable.getAbnormalLevel(_thisActor, Skill_AntiParty_atk01) < 2 && _thisActor.getCurrentHp() <= _thisActor.getMaxHp() * 0.300000)
			{
				if(Skill_AntiParty_atk02.getMpConsume() < _thisActor.getCurrentMp())
				{
					addUseSkillDesire(_thisActor, Skill_AntiParty_atk02, 1, 1, Maximum_Hate);
				}
			}
			_thisActor.i_quest1 = 1;
		}
		else if(victim == _thisActor.getLeader() && Rnd.get(150) == 0 && _thisActor.i_quest2 != 0)
		{
			int i0 = Rnd.get(4);
			if(victim != null)
			{
				_thisActor.notifyAiEvent(victim, CtrlEvent.EVT_SCRIPT_EVENT, 78010064, i0, null);
			}
			DefaultMaker maker0 = null;
			switch(i0)
			{
				case 0:
					if(_thisActor.i_quest2 == 212)
					{
						maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), mark_maker_02);
						if(maker0 != null)
						{
							maker0.onScriptEvent(78010061, _thisActor.getStoredId(), 0);
						}
					}
					else if(_thisActor.i_quest2 == 222)
					{
						maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), mark_maker_03);
						if(maker0 != null)
						{
							maker0.onScriptEvent(78010061, _thisActor.getStoredId(), 0);
						}
					}
					else if(_thisActor.i_quest2 == 232)
					{
						maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), mark_maker_04);
						if(maker0 != null)
						{
							maker0.onScriptEvent(78010061, _thisActor.getStoredId(), 0);
						}
					}
					else if(_thisActor.i_quest2 == 242)
					{
						maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), mark_maker_01);
						if(maker0 != null)
						{
							maker0.onScriptEvent(78010061, _thisActor.getStoredId(), 0);
						}
					}
					break;
				case 1:
					if(_thisActor.i_quest2 == 212)
					{
						maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), mark_maker_03);
						if(maker0 != null)
						{
							maker0.onScriptEvent(78010061, _thisActor.getStoredId(), 0);
						}
					}
					else if(_thisActor.i_quest2 == 222)
					{
						maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), mark_maker_04);
						if(maker0 != null)
						{
							maker0.onScriptEvent(78010061, _thisActor.getStoredId(), 0);
						}
					}
					else if(_thisActor.i_quest2 == 232)
					{
						maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), mark_maker_01);
						if(maker0 != null)
						{
							maker0.onScriptEvent(78010061, _thisActor.getStoredId(), 0);
						}
					}
					else if(_thisActor.i_quest2 == 242)
					{
						maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), mark_maker_02);
						if(maker0 != null)
						{
							maker0.onScriptEvent(78010061, _thisActor.getStoredId(), 0);
						}
					}
					break;
				case 2:
					if(_thisActor.i_quest2 == 212)
					{
						maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), mark_maker_04);
						if(maker0 != null)
						{
							maker0.onScriptEvent(78010061, _thisActor.getStoredId(), 0);
						}
					}
					else if(_thisActor.i_quest2 == 222)
					{
						maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), mark_maker_03);
						if(maker0 != null)
						{
							maker0.onScriptEvent(78010061, _thisActor.getStoredId(), 0);
						}
					}
					else if(_thisActor.i_quest2 == 232)
					{
						maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), mark_maker_02);
						if(maker0 != null)
						{
							maker0.onScriptEvent(78010061, _thisActor.getStoredId(), 0);
						}
					}
					else if(_thisActor.i_quest2 == 242)
					{
						maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), mark_maker_01);
						if(maker0 != null)
						{
							maker0.onScriptEvent(78010061, _thisActor.getStoredId(), 0);
						}
					}
					break;
				case 3:
					if(_thisActor.i_quest2 == 212)
					{
						maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), mark_maker_03);
						if(maker0 != null)
						{
							maker0.onScriptEvent(78010061, _thisActor.getStoredId(), 0);
						}
					}
					else if(_thisActor.i_quest2 == 222)
					{
						maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), mark_maker_04);
						if(maker0 != null)
						{
							maker0.onScriptEvent(78010061, _thisActor.getStoredId(), 0);
						}
					}
					else if(_thisActor.i_quest2 == 232)
					{
						maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), mark_maker_01);
						if(maker0 != null)
						{
							maker0.onScriptEvent(78010061, _thisActor.getStoredId(), 0);
						}
					}
					else if(_thisActor.i_quest2 == 242)
					{
						maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), mark_maker_02);
						if(maker0 != null)
						{
							maker0.onScriptEvent(78010061, _thisActor.getStoredId(), 0);
						}
					}
					break;
			}
		}
		super.onEvtPartyAttacked(attacker, victim, damage);
	}

	@Override
	protected void onEvtPartyDead(L2NpcInstance partyPrivate)
	{
		if(partyPrivate == _thisActor.getLeader())
		{
			Functions.npcSay(_thisActor, Say2C.SHOUT, 7164);
			addUseSkillDesire(_thisActor, Skill_Invin, 1, 1, Maximum_Hate);
			addTimer(TM_Invincibility, TIME_Invincibility * 1000);
			if(SkillTable.getAbnormalLevel(_thisActor, Skill_AntiParty_atk01) < 1 && _thisActor.getCurrentHp() <= _thisActor.getMaxHp() * 0.600000)
			{
				if(Skill_AntiParty_atk01.getMpConsume() < _thisActor.getCurrentMp())
				{
					addUseSkillDesire(_thisActor, Skill_AntiParty_atk01, 1, 1, Maximum_Hate);
				}
			}
			else if(SkillTable.getAbnormalLevel(_thisActor, Skill_AntiParty_atk01) < 2 && _thisActor.getCurrentHp() <= _thisActor.getMaxHp() * 0.300000)
			{
				if(Skill_AntiParty_atk02.getMpConsume() < _thisActor.getCurrentMp())
				{
					addUseSkillDesire(_thisActor, Skill_AntiParty_atk02, 1, 1, Maximum_Hate);
				}
			}
		}
	}
}
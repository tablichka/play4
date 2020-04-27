package ai;

import ai.base.MonsterBehavior;
import ru.l2gw.commons.math.Rnd;
import ru.l2gw.extensions.scripts.Functions;
import ru.l2gw.gameserver.ai.CtrlIntention;
import ru.l2gw.gameserver.clientpackets.Say2C;
import ru.l2gw.gameserver.model.*;
import ru.l2gw.gameserver.model.entity.category.CategoryManager;
import ru.l2gw.gameserver.model.instances.L2NpcInstance;
import ru.l2gw.gameserver.tables.SkillTable;
import ru.l2gw.util.Util;

/**
 * @author: rage
 * @date: 17.12.11 15:47
 */
public class ImmoBossVoidhound extends MonsterBehavior
{
	public float DefaultHate = 100.000000f;
	public float Maximum_Hate = 999999984306749440.000000f;
	public int HateClassGroup1 = 5;
	public float HateClassGroup1Boost = 20.000000f;
	public int HateClassGroup2 = 4;
	public float HateClassGroup2Boost = 10.000000f;
	public int FavorClassGroup1 = 3;
	public float FavorClassGroup1Boost = 10.000000f;
	public float SEE_CREATURE_Weight_Point = 1.000000f;
	public float ATTACKED_Weight_Point = 0.000000f;
	public float CLAN_ATTACKED_Weight_Point = 10.000000f;
	public float PARTY_ATTACKED_Weight_Point = 10.000000f;
	public float SEE_SPELL_Weight_Point = 20.000000f;
	public float HATE_SKILL_Weight_Point = 50.000000f;
	public float TUMOR_ATTACKED_Weight_Point = 0.000000f;
	public float VEIN_SIGNAL_Weight_Point = 0.000000f;
	public float TUMOR_HELP_Weight_Point = 0.000000f;
	public float LIFESEED_TAUNT_Weight_Point = 0.000000f;
	public L2Skill Skill01_ID = SkillTable.getInstance().getInfo(388366337);
	public L2Skill Skill02_ID = SkillTable.getInstance().getInfo(388300801);
	public L2Skill Skill03_ID = SkillTable.getInstance().getInfo(388235265);
	public int Skillchance_High = 15;
	public int Skillchance_Low = 7;
	public int Skillchance_Dim = 2;
	public int tide = 0;
	public String type = "voidhound_a";
	public int TM_ATTACK_COOLDOWN = 78001;
	public int TIME_ATTACK_COOLDOWN_MELEE = 5;
	public int TIME_ATTACK_COOLDOWN_CASTER = 1;

	public ImmoBossVoidhound(L2Character actor)
	{
		super(actor);
		IsAggressive = 1;
		Aggressive_Time = 1.000000f;
		Attack_DecayRatio = 6.600000f;
		UseSkill_DecayRatio = 66000.000000f;
		Attack_BoostValue = 300.000000f;
		UseSkill_BoostValue = 1000000.000000f;
		Party_Type = 1;
	}

	@Override
	protected void onEvtSpawn()
	{
		_thisActor.i_ai0 = 0;
		_thisActor.i_ai2 = 0;
		_thisActor.i_ai3 = 0;
		_thisActor.i_ai4 = 0;
		_thisActor.i_quest0 = 0;
		if(type.equals("dot") || type.equals("cc") || type.equals("con") || type.equals("ambush_dc_kamikaze") || type.equals("solo_boss_caster") || type.equals("duo_boss_caster") || type.equals("echmus"))
		{
			addTimer(TM_ATTACK_COOLDOWN, 1000);
		}
		else
		{
			addTimer(TM_ATTACK_COOLDOWN, 1000);
		}
		super.onEvtSpawn();
	}

	@Override
	protected void onEvtSeeCreature(L2Character creature)
	{
		if(_thisActor.getLifeTime() < Aggressive_Time || creature == null)
		{
			return;
		}
		if(creature.isPlayer())
		{
			creature.setDieEvent(_thisActor);
		}

		if(creature.isPlayer() || CategoryManager.isInCategory(12, creature.getNpcId()))
		{
			if(_thisActor.getAggroListSize() == 0)
			{
				float f0 = DefaultHate;
				if(HateClassGroup1 > -1)
				{
					if(CategoryManager.isInCategory(HateClassGroup1, creature))
					{
						f0 += HateClassGroup1Boost;
					}
				}
				if(HateClassGroup2 > -1)
				{
					if(CategoryManager.isInCategory(HateClassGroup2, creature))
					{
						f0 += HateClassGroup2Boost;
					}
				}
				if(FavorClassGroup1 > -1)
				{
					if(CategoryManager.isInCategory(FavorClassGroup1, creature))
					{
						f0 -= FavorClassGroup1Boost;
					}
				}
				if((f0 + 1) < 0)
				{
					f0 = 0;
				}
				else
				{
					f0 = DefaultHate * (f0 + 1);
				}
				_thisActor.addDamageHate(creature, 0, (long) (f0 * SEE_CREATURE_Weight_Point + Attack_BoostValue));
			}
			else
			{
				float f0 = DefaultHate;
				if(HateClassGroup1 > -1)
				{
					if(CategoryManager.isInCategory(HateClassGroup1, creature))
					{
						f0 += HateClassGroup1Boost;
					}
				}
				if(HateClassGroup2 > -1)
				{
					if(CategoryManager.isInCategory(HateClassGroup2, creature))
					{
						f0 += HateClassGroup2Boost;
					}
				}
				if(FavorClassGroup1 > -1)
				{
					if(CategoryManager.isInCategory(FavorClassGroup1, creature))
					{
						f0 -= FavorClassGroup1Boost;
					}
				}
				if((f0 + 1) < 0)
				{
					f0 = 0;
				}
				else
				{
					f0 = DefaultHate * (f0 + 1);
				}
				_thisActor.addDamageHate(creature, 0, (long) (f0 * SEE_CREATURE_Weight_Point));
			}
		}
		if(CategoryManager.isInCategory(12, creature.getNpcId()))
		{
			float f0 = DefaultHate;
			if(HateClassGroup1 > -1)
			{
				if(CategoryManager.isInCategory(HateClassGroup1, creature.getPlayer()))
				{
					f0 += HateClassGroup1Boost;
				}
			}
			if(HateClassGroup2 > -1)
			{
				if(CategoryManager.isInCategory(HateClassGroup2, creature.getPlayer()))
				{
					f0 += HateClassGroup2Boost;
				}
			}
			if(FavorClassGroup1 > -1)
			{
				if(CategoryManager.isInCategory(FavorClassGroup1, creature.getPlayer()))
				{
					f0 -= FavorClassGroup1Boost;
				}
			}
			if((f0 + 1) < 0)
			{
				f0 = 0;
			}
			else
			{
				f0 = DefaultHate * (f0 + 1);
			}
			_thisActor.addDamageHate(creature.getPlayer(), 0, (long) (f0 * SEE_CREATURE_Weight_Point));
		}
	}

	@Override
	protected void onEvtAttacked(L2Character attacker, int damage, L2Skill skill)
	{
		if(attacker == null)
			return;

		if(_thisActor.i_quest0 != 0)
		{
			if(attacker.isPlayer() || CategoryManager.isInCategory(12, attacker.getNpcId()))
			{
				if(_thisActor.getAggroListSize() == 0)
				{
					float f0 = DefaultHate;
					if(HateClassGroup1 > -1)
					{
						if(CategoryManager.isInCategory(HateClassGroup1, attacker))
						{
							f0 += HateClassGroup1Boost;
						}
					}
					if(HateClassGroup2 > -1)
					{
						if(CategoryManager.isInCategory(HateClassGroup2, attacker))
						{
							f0 += HateClassGroup2Boost;
						}
					}
					if(FavorClassGroup1 > -1)
					{
						if(CategoryManager.isInCategory(FavorClassGroup1, attacker))
						{
							f0 -= FavorClassGroup1Boost;
						}
					}
					if((f0 + 1) < 0)
					{
						f0 = 0;
					}
					else
					{
						f0 = damage * (f0 + 1);
					}
					_thisActor.addDamageHate(attacker, damage, (long) ((f0 * ATTACKED_Weight_Point + Attack_BoostValue)));
				}
				else
				{
					float f0 = DefaultHate;
					if(HateClassGroup1 > -1)
					{
						if(CategoryManager.isInCategory(HateClassGroup1, attacker))
						{
							f0 += HateClassGroup1Boost;
						}
					}
					if(HateClassGroup2 > -1)
					{
						if(CategoryManager.isInCategory(HateClassGroup2, attacker))
						{
							f0 += HateClassGroup2Boost;
						}
					}
					if(FavorClassGroup1 > -1)
					{
						if(CategoryManager.isInCategory(FavorClassGroup1, attacker))
						{
							f0 -= FavorClassGroup1Boost;
						}
					}
					if((f0 + 1) < 0)
					{
						f0 = 0;
					}
					else
					{
						f0 = damage * (f0 + 1);
					}
					_thisActor.addDamageHate(attacker, damage, (long) (f0 * ATTACKED_Weight_Point));
				}
			}
			if(CategoryManager.isInCategory(12, attacker.getNpcId()))
			{
				float f0 = DefaultHate;
				if(HateClassGroup1 > -1)
				{
					if(CategoryManager.isInCategory(HateClassGroup1, attacker.getPlayer()))
					{
						f0 += HateClassGroup1Boost;
					}
				}
				if(HateClassGroup2 > -1)
				{
					if(CategoryManager.isInCategory(HateClassGroup2, attacker.getPlayer()))
					{
						f0 += HateClassGroup2Boost;
					}
				}
				if(FavorClassGroup1 > -1)
				{
					if(CategoryManager.isInCategory(FavorClassGroup1, attacker.getPlayer()))
					{
						f0 -= FavorClassGroup1Boost;
					}
				}
				if((f0 + 1) < 0)
				{
					f0 = 0;
				}
				else
				{
					f0 = damage * (f0 + 1);
				}
				_thisActor.addDamageHate(attacker.getPlayer(), damage, (long) (f0 * ATTACKED_Weight_Point));
			}
		}
	}

	@Override
	protected void onEvtClanAttacked(L2Character attacked_member, L2Character attacker, int damage)
	{
		if(_thisActor.i_quest0 == 0)
		{
			if(_thisActor.getLifeTime() > Aggressive_Time && ((Party_Type == 0 || (Party_Type == 1 && Party_Loyalty == 0)) || Party_Type == 2))
			{
				if(_thisActor.getLeader() == null && type.equals("voidhound_d"))
				{
					if(attacker.isPlayer() || CategoryManager.isInCategory(12, attacker.getNpcId()))
					{
						if(_thisActor.getAggroListSize() == 0)
						{
							float f0 = DefaultHate;
							if(HateClassGroup1 > -1)
							{
								if(CategoryManager.isInCategory(HateClassGroup1, attacker))
								{
									f0 += HateClassGroup1Boost;
								}
							}
							if(HateClassGroup2 > -1)
							{
								if(CategoryManager.isInCategory(HateClassGroup2, attacker))
								{
									f0 += HateClassGroup2Boost;
								}
							}
							if(FavorClassGroup1 > -1)
							{
								if(CategoryManager.isInCategory(FavorClassGroup1, attacker))
								{
									f0 -= FavorClassGroup1Boost;
								}
							}
							if((f0 + 1) < 0)
							{
								f0 = 0;
							}
							else
							{
								f0 = damage * (f0 + 1);
							}
							_thisActor.addDamageHate(attacker, 0, (long) ((f0 * CLAN_ATTACKED_Weight_Point + Attack_BoostValue)));
						}
						else
						{
							float f0 = DefaultHate;
							if(HateClassGroup1 > -1)
							{
								if(CategoryManager.isInCategory(HateClassGroup1, attacker))
								{
									f0 += HateClassGroup1Boost;
								}
							}
							if(HateClassGroup2 > -1)
							{
								if(CategoryManager.isInCategory(HateClassGroup2, attacker))
								{
									f0 += HateClassGroup2Boost;
								}
							}
							if(FavorClassGroup1 > -1)
							{
								if(CategoryManager.isInCategory(FavorClassGroup1, attacker))
								{
									f0 -= FavorClassGroup1Boost;
								}
							}
							if((f0 + 1) < 0)
							{
								f0 = 0;
							}
							else
							{
								f0 = damage * (f0 + 1);
							}
							_thisActor.addDamageHate(attacker, 0, (long) (f0 * CLAN_ATTACKED_Weight_Point));
						}
					}
					if(CategoryManager.isInCategory(12, attacker.getNpcId()))
					{
						float f0 = DefaultHate;
						if(HateClassGroup1 > -1)
						{
							if(CategoryManager.isInCategory(HateClassGroup1, attacker.getPlayer()))
							{
								f0 += HateClassGroup1Boost;
							}
						}
						if(HateClassGroup2 > -1)
						{
							if(CategoryManager.isInCategory(HateClassGroup2, attacker.getPlayer()))
							{
								f0 += HateClassGroup2Boost;
							}
						}
						if(FavorClassGroup1 > -1)
						{
							if(CategoryManager.isInCategory(FavorClassGroup1, attacker.getPlayer()))
							{
								f0 -= FavorClassGroup1Boost;
							}
						}
						if((f0 + 1) < 0)
						{
							f0 = 0;
						}
						else
						{
							f0 = damage * (f0 + 1);
						}
						_thisActor.addDamageHate(attacker.getPlayer(), 0, (long) (f0 * CLAN_ATTACKED_Weight_Point));
					}
				}
			}
		}
	}

	@Override
	protected void onEvtPartyAttacked(L2Character attacker, L2Character victim, int damage)
	{
		if(_thisActor.i_quest0 == 0)
		{
			if(((Party_Type == 1 && (Party_Loyalty == 0 || Party_Loyalty == 1)) || (Party_Type == 1 && Party_Loyalty == 2 && victim == _thisActor.getLeader())) || (Party_Type == 2 && victim != _thisActor.getLeader()))
			{
				if(_thisActor.getLeader() != null && victim == _thisActor.getLeader() && type.equals("voidhound_a"))
				{
					if(attacker.isPlayer() || CategoryManager.isInCategory(12, attacker.getNpcId()))
					{
						if(_thisActor.getAggroListSize() == 0)
						{
							float f0 = DefaultHate;
							if(HateClassGroup1 > -1)
							{
								if(CategoryManager.isInCategory(HateClassGroup1, attacker))
								{
									f0 += HateClassGroup1Boost;
								}
							}
							if(HateClassGroup2 > -1)
							{
								if(CategoryManager.isInCategory(HateClassGroup2, attacker))
								{
									f0 += HateClassGroup2Boost;
								}
							}
							if(FavorClassGroup1 > -1)
							{
								if(CategoryManager.isInCategory(FavorClassGroup1, attacker))
								{
									f0 -= FavorClassGroup1Boost;
								}
							}
							if((f0 + 1) < 0)
							{
								f0 = 0;
							}
							else
							{
								f0 = damage * (f0 + 1);
							}
							_thisActor.addDamageHate(attacker, 0, (long) ((f0 * PARTY_ATTACKED_Weight_Point + Attack_BoostValue)));
						}
						else
						{
							float f0 = DefaultHate;
							if(HateClassGroup1 > -1)
							{
								if(CategoryManager.isInCategory(HateClassGroup1, attacker))
								{
									f0 += HateClassGroup1Boost;
								}
							}
							if(HateClassGroup2 > -1)
							{
								if(CategoryManager.isInCategory(HateClassGroup2, attacker))
								{
									f0 += HateClassGroup2Boost;
								}
							}
							if(FavorClassGroup1 > -1)
							{
								if(CategoryManager.isInCategory(FavorClassGroup1, attacker))
								{
									f0 -= FavorClassGroup1Boost;
								}
							}
							if((f0 + 1) < 0)
							{
								f0 = 0;
							}
							else
							{
								f0 = damage * (f0 + 1);
							}
							_thisActor.addDamageHate(attacker, 0, (long) (f0 * PARTY_ATTACKED_Weight_Point));
						}
					}
					if(CategoryManager.isInCategory(12, attacker.getNpcId()))
					{
						float f0 = DefaultHate;
						if(HateClassGroup1 > -1)
						{
							if(CategoryManager.isInCategory(HateClassGroup1, attacker.getPlayer()))
							{
								f0 += HateClassGroup1Boost;
							}
						}
						if(HateClassGroup2 > -1)
						{
							if(CategoryManager.isInCategory(HateClassGroup2, attacker.getPlayer()))
							{
								f0 += HateClassGroup2Boost;
							}
						}
						if(FavorClassGroup1 > -1)
						{
							if(CategoryManager.isInCategory(FavorClassGroup1, attacker.getPlayer()))
							{
								f0 -= FavorClassGroup1Boost;
							}
						}
						if((f0 + 1) < 0)
						{
							f0 = 0;
						}
						else
						{
							f0 = damage * (f0 + 1);
						}
						_thisActor.addDamageHate(attacker.getPlayer(), 0, (long) (f0 * PARTY_ATTACKED_Weight_Point));
					}
				}
			}
		}
	}

	@Override
	protected void onEvtPartyDead(L2NpcInstance victim)
	{
		if(victim == _thisActor.getLeader() && type.equals("voidhound_a"))
		{
			_thisActor.doDie(null);
		}
	}

	@Override
	protected void onEvtManipulation(L2Character target, int aggro, L2Skill skill)
	{
		_thisActor.addDamageHate(target, 0, (long) (aggro * HATE_SKILL_Weight_Point));
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
				if(_thisActor.getLoc().distance3D(c0.getLoc()) >= 300)
				{
					if(i0 <= Skillchance_Dim)
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
					else if(SkillTable.isMagic(Skill02_ID) == 0 && (SkillTable.getAbnormalLevel(_thisActor, 23134209) > 0 || SkillTable.getAbnormalLevel(_thisActor, 87556097) > 0))
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
			if(_thisActor.getLeader() != null && !_thisActor.isMyBossAlive() && type.equals("voidhound_a"))
			{
				_thisActor.doDie(null);
			}
			_thisActor.removeAllHateInfoIF(1, 0);
			_thisActor.removeAllHateInfoIF(3, 2000);
			if(type.equals("dot") || type.equals("cc") || type.equals("con") || type.equals("ambush_dc_kamikaze") || type.equals("solo_boss_caster") || type.equals("duo_boss_caster") || type.equals("echmus"))
			{
				if(_thisActor.getAggroListSize() != 0 && _intention != CtrlIntention.AI_INTENTION_ATTACK)
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
							addAttackDesire(c0, 1, 100);
						}
					}
				}
				addTimer(TM_ATTACK_COOLDOWN, (TIME_ATTACK_COOLDOWN_CASTER + Rnd.get(TIME_ATTACK_COOLDOWN_CASTER)) * 1000);
			}
			else if(_thisActor.getAggroListSize() != 0 && _intention != CtrlIntention.AI_INTENTION_ATTACK)
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
	protected void onEvtFinishCasting(L2Skill skill)
	{
		L2Character target = _thisActor.getCastingTarget();

		if(skill == null || target == null)
			return;

		if(target != _thisActor && (skill.getIndex() != 387252225 || skill.getIndex() != 387317761 || skill.getIndex() != 387055617))
		{
			if(skill.getIndex() == 385220609 || skill.getIndex() == 385286145 || skill.getIndex() == 385351681 || skill.getIndex() == 385351682 || skill.getIndex() == 385417217 || skill.getIndex() == 385417218 || skill.getIndex() == 385482753 || skill.getIndex() == 385482754 || skill.getIndex() == 385548289 || skill.getIndex() == 385941505 || skill.getIndex() == 385941506 || skill.getIndex() == 385941507 || skill.getIndex() == 386072577 || skill.getIndex() == 386138113 || skill.getIndex() == 386138114)
			{
				if(SkillTable.getAbnormalLevel(target, 384696321) <= 8)
				{
					if(target.getMaxHp() * 0.300000 >= target.getCurrentHp())
					{
						if(SkillTable.isMagic(384696323) == 0 && (SkillTable.getAbnormalLevel(_thisActor, 23134209) > 0 || SkillTable.getAbnormalLevel(_thisActor, 87556097) > 0))
						{
							if(debug)
							{
								Functions.npcSay(_thisActor, Say2C.ALL, "Phys skill!");
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
								Functions.npcSay(_thisActor, Say2C.ALL, "Cast " + (384696323 / 65536) + " skill");
							}
							addUseSkillDesire(target, 384696323, 0, 1, (long) (1000000 * UseSkill_BoostValue));
						}
						else if(debug)
						{
							Functions.npcSay(_thisActor, Say2C.ALL, "no skill condition!");
						}
					}
					else if(target.getMaxHp() * 0.600000 >= target.getCurrentHp())
					{
						if(SkillTable.isMagic(384696322) == 0 && (SkillTable.getAbnormalLevel(_thisActor, 23134209) > 0 || SkillTable.getAbnormalLevel(_thisActor, 87556097) > 0))
						{
							if(debug)
							{
								Functions.npcSay(_thisActor, Say2C.ALL, "Phys skill!");
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
								Functions.npcSay(_thisActor, Say2C.ALL, "Cast " + (384696322 / 65536) + " skill");
							}
							addUseSkillDesire(target, 384696322, 0, 1, (long) (1000000 * UseSkill_BoostValue));
						}
						else if(debug)
						{
							Functions.npcSay(_thisActor, Say2C.ALL, "no skill condition!");
						}
					}
					else if(SkillTable.isMagic(384696321) == 0 && (SkillTable.getAbnormalLevel(_thisActor, 23134209) > 0 || SkillTable.getAbnormalLevel(_thisActor, 87556097) > 0))
					{
						if(debug)
						{
							Functions.npcSay(_thisActor, Say2C.ALL, "Phys skill!");
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
							Functions.npcSay(_thisActor, Say2C.ALL, "Cast " + (384696321 / 65536) + " skill");
						}
						addUseSkillDesire(target, 384696321, 0, 1, (long) (1000000 * UseSkill_BoostValue));
					}
					else if(debug)
					{
						Functions.npcSay(_thisActor, Say2C.ALL, "no skill condition!");
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
								Functions.npcSay(_thisActor, Say2C.ALL, "Phys skill!");
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
								Functions.npcSay(_thisActor, Say2C.ALL, "Cast " + (384696327 / 65536) + " skill");
							}
							addUseSkillDesire(target, 384696327, 0, 1, (long) (1000000 * UseSkill_BoostValue));
						}
						else if(debug)
						{
							Functions.npcSay(_thisActor, Say2C.ALL, "no skill condition!");
						}
					}
					else if(target.getMaxHp() * 0.600000 >= target.getCurrentHp())
					{
						if(SkillTable.isMagic(384696326) == 0 && (SkillTable.getAbnormalLevel(_thisActor, 23134209) > 0 || SkillTable.getAbnormalLevel(_thisActor, 87556097) > 0))
						{
							if(debug)
							{
								Functions.npcSay(_thisActor, Say2C.ALL, "Phys skill!");
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
								Functions.npcSay(_thisActor, Say2C.ALL, "Cast " + (384696326 / 65536) + " skill");
							}
							addUseSkillDesire(target, 384696326, 0, 1, (long) (1000000 * UseSkill_BoostValue));
						}
						else if(debug)
						{
							Functions.npcSay(_thisActor, Say2C.ALL, "no skill condition!");
						}
					}
					else if(target.getMaxHp() * 0.900000 >= target.getCurrentHp())
					{
						if(SkillTable.isMagic(384696325) == 0 && (SkillTable.getAbnormalLevel(_thisActor, 23134209) > 0 || SkillTable.getAbnormalLevel(_thisActor, 87556097) > 0))
						{
							if(debug)
							{
								Functions.npcSay(_thisActor, Say2C.ALL, "Phys skill!");
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
								Functions.npcSay(_thisActor, Say2C.ALL, "Cast " + (384696325 / 65536) + " skill");
							}
							addUseSkillDesire(target, 384696325, 0, 1, (long) (1000000 * UseSkill_BoostValue));
						}
						else if(debug)
						{
							Functions.npcSay(_thisActor, Say2C.ALL, "no skill condition!");
						}
					}
					else if(SkillTable.isMagic(384696324) == 0 && (SkillTable.getAbnormalLevel(_thisActor, 23134209) > 0 || SkillTable.getAbnormalLevel(_thisActor, 87556097) > 0))
					{
						if(debug)
						{
							Functions.npcSay(_thisActor, Say2C.ALL, "Phys skill!");
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
							Functions.npcSay(_thisActor, Say2C.ALL, "Cast " + (384696324 / 65536) + " skill");
						}
						addUseSkillDesire(target, 384696324, 0, 1, (long) (1000000 * UseSkill_BoostValue));
					}
					else if(debug)
					{
						Functions.npcSay(_thisActor, Say2C.ALL, "no skill condition!");
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
								Functions.npcSay(_thisActor, Say2C.ALL, "Phys skill!");
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
								Functions.npcSay(_thisActor, Say2C.ALL, "Cast " + (384696330 / 65536) + " skill");
							}
							addUseSkillDesire(target, 384696330, 0, 1, (long) (1000000 * UseSkill_BoostValue));
						}
						else if(debug)
						{
							Functions.npcSay(_thisActor, Say2C.ALL, "no skill condition!");
						}
					}
					else if(target.getMaxHp() * 0.600000 >= target.getCurrentHp())
					{
						if(SkillTable.isMagic(384696329) == 0 && (SkillTable.getAbnormalLevel(_thisActor, 23134209) > 0 || SkillTable.getAbnormalLevel(_thisActor, 87556097) > 0))
						{
							if(debug)
							{
								Functions.npcSay(_thisActor, Say2C.ALL, "Phys skill!");
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
								Functions.npcSay(_thisActor, Say2C.ALL, "Cast " + (384696329 / 65536) + " skill");
							}
							addUseSkillDesire(target, 384696329, 0, 1, (long) (1000000 * UseSkill_BoostValue));
						}
						else if(debug)
						{
							Functions.npcSay(_thisActor, Say2C.ALL, "no skill condition!");
						}
					}
					else if(SkillTable.isMagic(384696328) == 0 && (SkillTable.getAbnormalLevel(_thisActor, 23134209) > 0 || SkillTable.getAbnormalLevel(_thisActor, 87556097) > 0))
					{
						if(debug)
						{
							Functions.npcSay(_thisActor, Say2C.ALL, "Phys skill!");
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
							Functions.npcSay(_thisActor, Say2C.ALL, "Cast " + (384696328 / 65536) + " skill");
						}
						addUseSkillDesire(target, 384696328, 0, 1, (long) (1000000 * UseSkill_BoostValue));
					}
					else if(debug)
					{
						Functions.npcSay(_thisActor, Say2C.ALL, "no skill condition!");
					}
				}
			}
			if(skill.getIndex() == 385613825 || skill.getIndex() == 385613826 || skill.getIndex() == 385679361 || skill.getIndex() == 383713282 || skill.getIndex() == 385875969 || skill.getIndex() == 385875970 || skill.getIndex() == 388759553 || skill.getIndex() == 388825089)
			{
				if(SkillTable.getAbnormalLevel(target, 384761859) < 8)
				{
					if(SkillTable.isMagic(384761859) == 0 && (SkillTable.getAbnormalLevel(_thisActor, 23134209) > 0 || SkillTable.getAbnormalLevel(_thisActor, 87556097) > 0))
					{
						if(debug)
						{
							Functions.npcSay(_thisActor, Say2C.ALL, "Phys skill!");
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
							Functions.npcSay(_thisActor, Say2C.ALL, "Cast " + (384761859 / 65536) + " skill");
						}
						addUseSkillDesire(target, 384761859, 0, 1, (long) (1000000 * UseSkill_BoostValue));
					}
					else if(debug)
					{
						Functions.npcSay(_thisActor, Say2C.ALL, "no skill condition!");
					}
				}
				else if(SkillTable.getAbnormalLevel(target, 384761859) < 9)
				{
					if(SkillTable.isMagic(384761863) == 0 && (SkillTable.getAbnormalLevel(_thisActor, 23134209) > 0 || SkillTable.getAbnormalLevel(_thisActor, 87556097) > 0))
					{
						if(debug)
						{
							Functions.npcSay(_thisActor, Say2C.ALL, "Phys skill!");
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
							Functions.npcSay(_thisActor, Say2C.ALL, "Cast " + (384761863 / 65536) + " skill");
						}
						addUseSkillDesire(target, 384761863, 0, 1, (long) (1000000 * UseSkill_BoostValue));
					}
					else if(debug)
					{
						Functions.npcSay(_thisActor, Say2C.ALL, "no skill condition!");
					}
				}
				else if(SkillTable.getAbnormalLevel(target, 384761859) < 10)
				{
					if(SkillTable.isMagic(384761866) == 0 && (SkillTable.getAbnormalLevel(_thisActor, 23134209) > 0 || SkillTable.getAbnormalLevel(_thisActor, 87556097) > 0))
					{
						if(debug)
						{
							Functions.npcSay(_thisActor, Say2C.ALL, "Phys skill!");
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
							Functions.npcSay(_thisActor, Say2C.ALL, "Cast " + (384761866 / 65536) + " skill");
						}
						addUseSkillDesire(target, 384761866, 0, 1, (long) (1000000 * UseSkill_BoostValue));
					}
					else if(debug)
					{
						Functions.npcSay(_thisActor, Say2C.ALL, "no skill condition!");
					}
				}
			}
			if(skill.getIndex() == 385744897 || skill.getIndex() == 385810433 || skill.getIndex() == 386007041 || skill.getIndex() == 386007042 || skill.getIndex() == 386007043)
			{
				if(SkillTable.getAbnormalLevel(target, 384827395) < 8)
				{
					if(SkillTable.isMagic(384827395) == 0 && (SkillTable.getAbnormalLevel(_thisActor, 23134209) > 0 || SkillTable.getAbnormalLevel(_thisActor, 87556097) > 0))
					{
						if(debug)
						{
							Functions.npcSay(_thisActor, Say2C.ALL, "Phys skill!");
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
							Functions.npcSay(_thisActor, Say2C.ALL, "Cast " + (384827395 / 65536) + " skill");
						}
						addUseSkillDesire(target, 384827395, 0, 1, (long) (1000000 * UseSkill_BoostValue));
					}
					else if(debug)
					{
						Functions.npcSay(_thisActor, Say2C.ALL, "no skill condition!");
					}
				}
				else if(SkillTable.getAbnormalLevel(target, 384827395) < 9)
				{
					if(SkillTable.isMagic(384827399) == 0 && (SkillTable.getAbnormalLevel(_thisActor, 23134209) > 0 || SkillTable.getAbnormalLevel(_thisActor, 87556097) > 0))
					{
						if(debug)
						{
							Functions.npcSay(_thisActor, Say2C.ALL, "Phys skill!");
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
							Functions.npcSay(_thisActor, Say2C.ALL, "Cast " + (384827399 / 65536) + " skill");
						}
						addUseSkillDesire(target, 384827399, 0, 1, (long) (1000000 * UseSkill_BoostValue));
					}
					else if(debug)
					{
						Functions.npcSay(_thisActor, Say2C.ALL, "no skill condition!");
					}
				}
				else if(SkillTable.getAbnormalLevel(target, 384827395) < 10)
				{
					if(SkillTable.isMagic(384827402) == 0 && (SkillTable.getAbnormalLevel(_thisActor, 23134209) > 0 || SkillTable.getAbnormalLevel(_thisActor, 87556097) > 0))
					{
						if(debug)
						{
							Functions.npcSay(_thisActor, Say2C.ALL, "Phys skill!");
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
							Functions.npcSay(_thisActor, Say2C.ALL, "Cast " + (384827402 / 65536) + " skill");
						}
						addUseSkillDesire(target, 384827402, 0, 1, (long) (1000000 * UseSkill_BoostValue));
					}
					else if(debug)
					{
						Functions.npcSay(_thisActor, Say2C.ALL, "no skill condition!");
					}
				}
			}
			if(skill.getIndex() == 386269185 || skill.getIndex() == 386334721 || skill.getIndex() == 386400257 || skill.getIndex() == 386400258)
			{
				if(SkillTable.getAbnormalLevel(target, 384434177) <= 8)
				{
					if(target.getMaxHp() * 0.300000 >= target.getCurrentHp())
					{
						if(SkillTable.isMagic(384434179) == 0 && (SkillTable.getAbnormalLevel(_thisActor, 23134209) > 0 || SkillTable.getAbnormalLevel(_thisActor, 87556097) > 0))
						{
							if(debug)
							{
								Functions.npcSay(_thisActor, Say2C.ALL, "Phys skill!");
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
								Functions.npcSay(_thisActor, Say2C.ALL, "Cast " + (384434179 / 65536) + " skill");
							}
							addUseSkillDesire(target, 384434179, 0, 1, (long) (1000000 * UseSkill_BoostValue));
						}
						else if(debug)
						{
							Functions.npcSay(_thisActor, Say2C.ALL, "no skill condition!");
						}
					}
					else if(target.getMaxHp() * 0.600000 >= target.getCurrentHp())
					{
						if(SkillTable.isMagic(384434178) == 0 && (SkillTable.getAbnormalLevel(_thisActor, 23134209) > 0 || SkillTable.getAbnormalLevel(_thisActor, 87556097) > 0))
						{
							if(debug)
							{
								Functions.npcSay(_thisActor, Say2C.ALL, "Phys skill!");
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
								Functions.npcSay(_thisActor, Say2C.ALL, "Cast " + (384434178 / 65536) + " skill");
							}
							addUseSkillDesire(target, 384434178, 0, 1, (long) (1000000 * UseSkill_BoostValue));
						}
						else if(debug)
						{
							Functions.npcSay(_thisActor, Say2C.ALL, "no skill condition!");
						}
					}
					else if(SkillTable.isMagic(384434177) == 0 && (SkillTable.getAbnormalLevel(_thisActor, 23134209) > 0 || SkillTable.getAbnormalLevel(_thisActor, 87556097) > 0))
					{
						if(debug)
						{
							Functions.npcSay(_thisActor, Say2C.ALL, "Phys skill!");
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
							Functions.npcSay(_thisActor, Say2C.ALL, "Cast " + (384434177 / 65536) + " skill");
						}
						addUseSkillDesire(target, 384434177, 0, 1, (long) (1000000 * UseSkill_BoostValue));
					}
					else if(debug)
					{
						Functions.npcSay(_thisActor, Say2C.ALL, "no skill condition!");
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
								Functions.npcSay(_thisActor, Say2C.ALL, "Phys skill!");
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
								Functions.npcSay(_thisActor, Say2C.ALL, "Cast " + (384434183 / 65536) + " skill");
							}
							addUseSkillDesire(target, 384434183, 0, 1, (long) (1000000 * UseSkill_BoostValue));
						}
						else if(debug)
						{
							Functions.npcSay(_thisActor, Say2C.ALL, "no skill condition!");
						}
					}
					else if(target.getMaxHp() * 0.600000 >= target.getCurrentHp())
					{
						if(SkillTable.isMagic(384434182) == 0 && (SkillTable.getAbnormalLevel(_thisActor, 23134209) > 0 || SkillTable.getAbnormalLevel(_thisActor, 87556097) > 0))
						{
							if(debug)
							{
								Functions.npcSay(_thisActor, Say2C.ALL, "Phys skill!");
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
								Functions.npcSay(_thisActor, Say2C.ALL, "Cast " + (384434182 / 65536) + " skill");
							}
							addUseSkillDesire(target, 384434182, 0, 1, (long) (1000000 * UseSkill_BoostValue));
						}
						else if(debug)
						{
							Functions.npcSay(_thisActor, Say2C.ALL, "no skill condition!");
						}
					}
					else if(target.getMaxHp() * 0.900000 >= target.getCurrentHp())
					{
						if(SkillTable.isMagic(384434181) == 0 && (SkillTable.getAbnormalLevel(_thisActor, 23134209) > 0 || SkillTable.getAbnormalLevel(_thisActor, 87556097) > 0))
						{
							if(debug)
							{
								Functions.npcSay(_thisActor, Say2C.ALL, "Phys skill!");
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
								Functions.npcSay(_thisActor, Say2C.ALL, "Cast " + (384434181 / 65536) + " skill");
							}
							addUseSkillDesire(target, 384434181, 0, 1, (long) (1000000 * UseSkill_BoostValue));
						}
						else if(debug)
						{
							Functions.npcSay(_thisActor, Say2C.ALL, "no skill condition!");
						}
					}
					else if(SkillTable.isMagic(384434180) == 0 && (SkillTable.getAbnormalLevel(_thisActor, 23134209) > 0 || SkillTable.getAbnormalLevel(_thisActor, 87556097) > 0))
					{
						if(debug)
						{
							Functions.npcSay(_thisActor, Say2C.ALL, "Phys skill!");
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
							Functions.npcSay(_thisActor, Say2C.ALL, "Cast " + (384434180 / 65536) + " skill");
						}
						addUseSkillDesire(target, 384434180, 0, 1, (long) (1000000 * UseSkill_BoostValue));
					}
					else if(debug)
					{
						Functions.npcSay(_thisActor, Say2C.ALL, "no skill condition!");
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
								Functions.npcSay(_thisActor, Say2C.ALL, "Phys skill!");
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
								Functions.npcSay(_thisActor, Say2C.ALL, "Cast " + (384434186 / 65536) + " skill");
							}
							addUseSkillDesire(target, 384434186, 0, 1, (long) (1000000 * UseSkill_BoostValue));
						}
						else if(debug)
						{
							Functions.npcSay(_thisActor, Say2C.ALL, "no skill condition!");
						}
					}
					else if(target.getMaxHp() * 0.600000 >= target.getCurrentHp())
					{
						if(SkillTable.isMagic(384434185) == 0 && (SkillTable.getAbnormalLevel(_thisActor, 23134209) > 0 || SkillTable.getAbnormalLevel(_thisActor, 87556097) > 0))
						{
							if(debug)
							{
								Functions.npcSay(_thisActor, Say2C.ALL, "Phys skill!");
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
								Functions.npcSay(_thisActor, Say2C.ALL, "Cast " + (384434185 / 65536) + " skill");
							}
							addUseSkillDesire(target, 384434185, 0, 1, (long) (1000000 * UseSkill_BoostValue));
						}
						else if(debug)
						{
							Functions.npcSay(_thisActor, Say2C.ALL, "no skill condition!");
						}
					}
					else if(SkillTable.isMagic(384434184) == 0 && (SkillTable.getAbnormalLevel(_thisActor, 23134209) > 0 || SkillTable.getAbnormalLevel(_thisActor, 87556097) > 0))
					{
						if(debug)
						{
							Functions.npcSay(_thisActor, Say2C.ALL, "Phys skill!");
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
							Functions.npcSay(_thisActor, Say2C.ALL, "Cast " + (384434184 / 65536) + " skill");
						}
						addUseSkillDesire(target, 384434184, 0, 1, (long) (1000000 * UseSkill_BoostValue));
					}
					else if(debug)
					{
						Functions.npcSay(_thisActor, Say2C.ALL, "no skill condition!");
					}
				}
			}
			if(skill.getIndex() == 386531329 || skill.getIndex() == 386531330 || skill.getIndex() == 387121153 || skill.getIndex() == 387121154 || skill.getIndex() == 387186689 || skill.getIndex() == 387186690)
			{
				if(SkillTable.getAbnormalLevel(target, 384565251) < 8)
				{
					if(SkillTable.isMagic(384565251) == 0 && (SkillTable.getAbnormalLevel(_thisActor, 23134209) > 0 || SkillTable.getAbnormalLevel(_thisActor, 87556097) > 0))
					{
						if(debug)
						{
							Functions.npcSay(_thisActor, Say2C.ALL, "Phys skill!");
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
							Functions.npcSay(_thisActor, Say2C.ALL, "Cast " + (384565251 / 65536) + " skill");
						}
						addUseSkillDesire(target, 384565251, 0, 1, (long) (1000000 * UseSkill_BoostValue));
					}
					else if(debug)
					{
						Functions.npcSay(_thisActor, Say2C.ALL, "no skill condition!");
					}
				}
				else if(SkillTable.getAbnormalLevel(target, 384565251) < 9)
				{
					if(SkillTable.isMagic(384565255) == 0 && (SkillTable.getAbnormalLevel(_thisActor, 23134209) > 0 || SkillTable.getAbnormalLevel(_thisActor, 87556097) > 0))
					{
						if(debug)
						{
							Functions.npcSay(_thisActor, Say2C.ALL, "Phys skill!");
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
							Functions.npcSay(_thisActor, Say2C.ALL, "Cast " + (384565255 / 65536) + " skill");
						}
						addUseSkillDesire(target, 384565255, 0, 1, (long) (1000000 * UseSkill_BoostValue));
					}
					else if(debug)
					{
						Functions.npcSay(_thisActor, Say2C.ALL, "no skill condition!");
					}
				}
				else if(SkillTable.getAbnormalLevel(target, 384565251) < 10)
				{
					if(SkillTable.isMagic(384565258) == 0 && (SkillTable.getAbnormalLevel(_thisActor, 23134209) > 0 || SkillTable.getAbnormalLevel(_thisActor, 87556097) > 0))
					{
						if(debug)
						{
							Functions.npcSay(_thisActor, Say2C.ALL, "Phys skill!");
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
							Functions.npcSay(_thisActor, Say2C.ALL, "Cast " + (384565258 / 65536) + " skill");
						}
						addUseSkillDesire(target, 384565258, 0, 1, (long) (1000000 * UseSkill_BoostValue));
					}
					else if(debug)
					{
						Functions.npcSay(_thisActor, Say2C.ALL, "no skill condition!");
					}
				}
			}
			if(skill.getIndex() == 386727937 || skill.getIndex() == 386727938 || skill.getIndex() == 386924545 || skill.getIndex() == 386924546 || skill.getIndex() == 386924547 || skill.getIndex() == 386990081)
			{
				if(SkillTable.getAbnormalLevel(target, 384630787) < 8)
				{
					if(SkillTable.isMagic(384630787) == 0 && (SkillTable.getAbnormalLevel(_thisActor, 23134209) > 0 || SkillTable.getAbnormalLevel(_thisActor, 87556097) > 0))
					{
						if(debug)
						{
							Functions.npcSay(_thisActor, Say2C.ALL, "Phys skill!");
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
							Functions.npcSay(_thisActor, Say2C.ALL, "Cast " + (384630787 / 65536) + " skill");
						}
						addUseSkillDesire(target, 384630787, 0, 1, (long) (1000000 * UseSkill_BoostValue));
					}
					else if(debug)
					{
						Functions.npcSay(_thisActor, Say2C.ALL, "no skill condition!");
					}
				}
				else if(SkillTable.getAbnormalLevel(target, 384630787) < 9)
				{
					if(SkillTable.isMagic(384630791) == 0 && (SkillTable.getAbnormalLevel(_thisActor, 23134209) > 0 || SkillTable.getAbnormalLevel(_thisActor, 87556097) > 0))
					{
						if(debug)
						{
							Functions.npcSay(_thisActor, Say2C.ALL, "Phys skill!");
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
							Functions.npcSay(_thisActor, Say2C.ALL, "Cast " + (384630791 / 65536) + " skill");
						}
						addUseSkillDesire(target, 384630791, 0, 1, (long) (1000000 * UseSkill_BoostValue));
					}
					else if(debug)
					{
						Functions.npcSay(_thisActor, Say2C.ALL, "no skill condition!");
					}
				}
				else if(SkillTable.getAbnormalLevel(target, 384630787) < 10)
				{
					if(SkillTable.isMagic(384630794) == 0 && (SkillTable.getAbnormalLevel(_thisActor, 23134209) > 0 || SkillTable.getAbnormalLevel(_thisActor, 87556097) > 0))
					{
						if(debug)
						{
							Functions.npcSay(_thisActor, Say2C.ALL, "Phys skill!");
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
							Functions.npcSay(_thisActor, Say2C.ALL, "Cast " + (384630794 / 65536) + " skill");
						}
						addUseSkillDesire(target, 384630794, 0, 1, (long) (1000000 * UseSkill_BoostValue));
					}
					else if(debug)
					{
						Functions.npcSay(_thisActor, Say2C.ALL, "no skill condition!");
					}
				}
			}
			if(skill.getIndex() == 386596865 || skill.getIndex() == 386596866 || skill.getIndex() == 386662401 || skill.getIndex() == 386859009)
			{
				if(SkillTable.getAbnormalLevel(target, 384499715) < 8)
				{
					if(SkillTable.isMagic(384499715) == 0 && (SkillTable.getAbnormalLevel(_thisActor, 23134209) > 0 || SkillTable.getAbnormalLevel(_thisActor, 87556097) > 0))
					{
						if(debug)
						{
							Functions.npcSay(_thisActor, Say2C.ALL, "Phys skill!");
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
							Functions.npcSay(_thisActor, Say2C.ALL, "Cast " + (384499715 / 65536) + " skill");
						}
						addUseSkillDesire(target, 384499715, 0, 1, (long) (1000000 * UseSkill_BoostValue));
					}
					else if(debug)
					{
						Functions.npcSay(_thisActor, Say2C.ALL, "no skill condition!");
					}
				}
				else if(SkillTable.getAbnormalLevel(target, 384499715) < 9)
				{
					if(SkillTable.isMagic(384499715) == 0 && (SkillTable.getAbnormalLevel(_thisActor, 23134209) > 0 || SkillTable.getAbnormalLevel(_thisActor, 87556097) > 0))
					{
						if(debug)
						{
							Functions.npcSay(_thisActor, Say2C.ALL, "Phys skill!");
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
							Functions.npcSay(_thisActor, Say2C.ALL, "Cast " + (384499715 / 65536) + " skill");
						}
						addUseSkillDesire(target, 384499715, 0, 1, (long) (1000000 * UseSkill_BoostValue));
					}
					else if(debug)
					{
						Functions.npcSay(_thisActor, Say2C.ALL, "no skill condition!");
					}
				}
				else if(SkillTable.getAbnormalLevel(target, 384499715) < 10)
				{
					if(SkillTable.isMagic(384499722) == 0 && (SkillTable.getAbnormalLevel(_thisActor, 23134209) > 0 || SkillTable.getAbnormalLevel(_thisActor, 87556097) > 0))
					{
						if(debug)
						{
							Functions.npcSay(_thisActor, Say2C.ALL, "Phys skill!");
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
							Functions.npcSay(_thisActor, Say2C.ALL, "Cast " + (384499722 / 65536) + " skill");
						}
						addUseSkillDesire(target, 384499722, 0, 1, (long) (1000000 * UseSkill_BoostValue));
					}
					else if(debug)
					{
						Functions.npcSay(_thisActor, Say2C.ALL, "no skill condition!");
					}
				}
			}
			if(skill.getIndex() == 388235265 || skill.getIndex() == 388300801)
			{
				if(SkillTable.getAbnormalLevel(target, 384892929) < 1)
				{
					if(SkillTable.isMagic(384892929) == 0 && (SkillTable.getAbnormalLevel(_thisActor, 23134209) > 0 || SkillTable.getAbnormalLevel(_thisActor, 87556097) > 0))
					{
						if(debug)
						{
							Functions.npcSay(_thisActor, Say2C.ALL, "Phys skill!");
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
							Functions.npcSay(_thisActor, Say2C.ALL, "Cast " + (384892929 / 65536) + " skill");
						}
						addUseSkillDesire(target, 384892929, 0, 1, (long) (1000000 * UseSkill_BoostValue));
					}
					else if(debug)
					{
						Functions.npcSay(_thisActor, Say2C.ALL, "no skill condition!");
					}
				}
				else if(SkillTable.getAbnormalLevel(target, 384892929) < 2)
				{
					if(SkillTable.isMagic(384892930) == 0 && (SkillTable.getAbnormalLevel(_thisActor, 23134209) > 0 || SkillTable.getAbnormalLevel(_thisActor, 87556097) > 0))
					{
						if(debug)
						{
							Functions.npcSay(_thisActor, Say2C.ALL, "Phys skill!");
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
							Functions.npcSay(_thisActor, Say2C.ALL, "Cast " + (384892930 / 65536) + " skill");
						}
						addUseSkillDesire(target, 384892930, 0, 1, (long) (1000000 * UseSkill_BoostValue));
					}
					else if(debug)
					{
						Functions.npcSay(_thisActor, Say2C.ALL, "no skill condition!");
					}
				}
				else if(SkillTable.getAbnormalLevel(target, 384892929) < 3)
				{
					if(SkillTable.isMagic(384892931) == 0 && (SkillTable.getAbnormalLevel(_thisActor, 23134209) > 0 || SkillTable.getAbnormalLevel(_thisActor, 87556097) > 0))
					{
						if(debug)
						{
							Functions.npcSay(_thisActor, Say2C.ALL, "Phys skill!");
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
							Functions.npcSay(_thisActor, Say2C.ALL, "Cast " + (384892931 / 65536) + " skill");
						}
						addUseSkillDesire(target, 384892931, 0, 1, (long) (1000000 * UseSkill_BoostValue));
					}
					else if(debug)
					{
						Functions.npcSay(_thisActor, Say2C.ALL, "no skill condition!");
					}
				}
				else if(SkillTable.getAbnormalLevel(target, 384892929) < 4)
				{
					if(SkillTable.isMagic(384892932) == 0 && (SkillTable.getAbnormalLevel(_thisActor, 23134209) > 0 || SkillTable.getAbnormalLevel(_thisActor, 87556097) > 0))
					{
						if(debug)
						{
							Functions.npcSay(_thisActor, Say2C.ALL, "Phys skill!");
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
							Functions.npcSay(_thisActor, Say2C.ALL, "Cast " + (384892932 / 65536) + " skill");
						}
						addUseSkillDesire(target, 384892932, 0, 1, (long) (1000000 * UseSkill_BoostValue));
					}
					else if(debug)
					{
						Functions.npcSay(_thisActor, Say2C.ALL, "no skill condition!");
					}
				}
				else if(SkillTable.getAbnormalLevel(target, 384892929) < 5)
				{
					if(SkillTable.isMagic(384892933) == 0 && (SkillTable.getAbnormalLevel(_thisActor, 23134209) > 0 || SkillTable.getAbnormalLevel(_thisActor, 87556097) > 0))
					{
						if(debug)
						{
							Functions.npcSay(_thisActor, Say2C.ALL, "Phys skill!");
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
							Functions.npcSay(_thisActor, Say2C.ALL, "Cast " + (384892933 / 65536) + " skill");
						}
						addUseSkillDesire(target, 384892933, 0, 1, (long) (1000000 * UseSkill_BoostValue));
					}
					else if(debug)
					{
						Functions.npcSay(_thisActor, Say2C.ALL, "no skill condition!");
					}
				}
			}
			if(skill.getIndex() == 388366337)
			{
				if(SkillTable.getAbnormalLevel(target, 384958465) < 1)
				{
					if(SkillTable.isMagic(384958465) == 0 && (SkillTable.getAbnormalLevel(_thisActor, 23134209) > 0 || SkillTable.getAbnormalLevel(_thisActor, 87556097) > 0))
					{
						if(debug)
						{
							Functions.npcSay(_thisActor, Say2C.ALL, "Phys skill!");
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
							Functions.npcSay(_thisActor, Say2C.ALL, "Cast " + (384958465 / 65536) + " skill");
						}
						addUseSkillDesire(target, 384958465, 0, 1, (long) (1000000 * UseSkill_BoostValue));
					}
					else if(debug)
					{
						Functions.npcSay(_thisActor, Say2C.ALL, "no skill condition!");
					}
				}
				else if(SkillTable.getAbnormalLevel(target, 384958465) < 2)
				{
					if(SkillTable.isMagic(384958466) == 0 && (SkillTable.getAbnormalLevel(_thisActor, 23134209) > 0 || SkillTable.getAbnormalLevel(_thisActor, 87556097) > 0))
					{
						if(debug)
						{
							Functions.npcSay(_thisActor, Say2C.ALL, "Phys skill!");
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
							Functions.npcSay(_thisActor, Say2C.ALL, "Cast " + (384958466 / 65536) + " skill");
						}
						addUseSkillDesire(target, 384958466, 0, 1, (long) (1000000 * UseSkill_BoostValue));
					}
					else if(debug)
					{
						Functions.npcSay(_thisActor, Say2C.ALL, "no skill condition!");
					}
				}
				else if(SkillTable.getAbnormalLevel(target, 384958465) < 3)
				{
					if(SkillTable.isMagic(384958467) == 0 && (SkillTable.getAbnormalLevel(_thisActor, 23134209) > 0 || SkillTable.getAbnormalLevel(_thisActor, 87556097) > 0))
					{
						if(debug)
						{
							Functions.npcSay(_thisActor, Say2C.ALL, "Phys skill!");
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
							Functions.npcSay(_thisActor, Say2C.ALL, "Cast " + (384958467 / 65536) + " skill");
						}
						addUseSkillDesire(target, 384958467, 0, 1, (long) (1000000 * UseSkill_BoostValue));
					}
					else if(debug)
					{
						Functions.npcSay(_thisActor, Say2C.ALL, "no skill condition!");
					}
				}
				else if(SkillTable.getAbnormalLevel(target, 384958465) < 4)
				{
					if(SkillTable.isMagic(384958468) == 0 && (SkillTable.getAbnormalLevel(_thisActor, 23134209) > 0 || SkillTable.getAbnormalLevel(_thisActor, 87556097) > 0))
					{
						if(debug)
						{
							Functions.npcSay(_thisActor, Say2C.ALL, "Phys skill!");
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
							Functions.npcSay(_thisActor, Say2C.ALL, "Cast " + (384958468 / 65536) + " skill");
						}
						addUseSkillDesire(target, 384958468, 0, 1, (long) (1000000 * UseSkill_BoostValue));
					}
					else if(debug)
					{
						Functions.npcSay(_thisActor, Say2C.ALL, "no skill condition!");
					}
				}
				else if(SkillTable.getAbnormalLevel(target, 384958465) < 5)
				{
					if(SkillTable.isMagic(384958469) == 0 && (SkillTable.getAbnormalLevel(_thisActor, 23134209) > 0 || SkillTable.getAbnormalLevel(_thisActor, 87556097) > 0))
					{
						if(debug)
						{
							Functions.npcSay(_thisActor, Say2C.ALL, "Phys skill!");
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
							Functions.npcSay(_thisActor, Say2C.ALL, "Cast " + (384958469 / 65536) + " skill");
						}
						addUseSkillDesire(target, 384958469, 0, 1, (long) (1000000 * UseSkill_BoostValue));
					}
					else if(debug)
					{
						Functions.npcSay(_thisActor, Say2C.ALL, "no skill condition!");
					}
				}
			}
		}
	}

	@Override
	protected void onEvtSpelled(L2Skill skill, L2Character caster)
	{
		if(skill == Skill03_ID)
		{
			addFleeDesire(caster, 100000);
		}
	}

	@Override
	protected void onEvtScriptEvent(int eventId, Object arg1, Object arg2)
	{
		if(eventId == 78010074 && (Integer) arg1 == 0)
		{
			_thisActor.i_quest0 = 1;
			_thisActor.removeAllHateInfoIF(0, 0);
			clearTasks();
			addMoveAroundDesire(5, 5);
		}
		else if(eventId == 78010074 && (Integer) arg1 == 1)
		{
			_thisActor.i_quest0 = 0;
			_thisActor.removeAllHateInfoIF(0, 0);
			clearTasks();
			if(_thisActor.isMyBossAlive())
			{
				addFollowDesire(_thisActor.getLeader(), 5);
			}
			else
			{
				addMoveAroundDesire(5, 5);
			}
		}
		else if(eventId == 78010074 && (Integer) arg1 == 98)
		{
			L2Party party0 = Util.getPartyFromID(_thisActor, (Integer) arg2);
			if(party0 != null)
			{
				float f1 = party0.getMemberCount() * SEE_CREATURE_Weight_Point;
				for(L2Player c0 : party0.getPartyMembers())
				{
					if(c0 != null && _thisActor.getLoc().distance3D(c0.getLoc()) <= 3000)
					{
						if(c0.isPlayer() || CategoryManager.isInCategory(12, c0.getNpcId()))
						{
							if(_thisActor.getAggroListSize() == 0)
							{
								float f0 = DefaultHate;
								if(HateClassGroup1 > -1)
								{
									if(CategoryManager.isInCategory(HateClassGroup1, c0))
									{
										f0 += HateClassGroup1Boost;
									}
								}
								if(HateClassGroup2 > -1)
								{
									if(CategoryManager.isInCategory(HateClassGroup2, c0))
									{
										f0 += HateClassGroup2Boost;
									}
								}
								if(FavorClassGroup1 > -1)
								{
									if(CategoryManager.isInCategory(FavorClassGroup1, c0))
									{
										f0 -= FavorClassGroup1Boost;
									}
								}
								if((f0 + 1) < 0)
								{
									f0 = 0;
								}
								else
								{
									f0 = DefaultHate * (f0 + 1);
								}
								_thisActor.addDamageHate(c0, 0, (long) ((f0 * f1 + Attack_BoostValue)));
							}
							else
							{
								float f0 = DefaultHate;
								if(HateClassGroup1 > -1)
								{
									if(CategoryManager.isInCategory(HateClassGroup1, c0))
									{
										f0 += HateClassGroup1Boost;
									}
								}
								if(HateClassGroup2 > -1)
								{
									if(CategoryManager.isInCategory(HateClassGroup2, c0))
									{
										f0 += HateClassGroup2Boost;
									}
								}
								if(FavorClassGroup1 > -1)
								{
									if(CategoryManager.isInCategory(FavorClassGroup1, c0))
									{
										f0 -= FavorClassGroup1Boost;
									}
								}
								if((f0 + 1) < 0)
								{
									f0 = 0;
								}
								else
								{
									f0 = DefaultHate * (f0 + 1);
								}
								_thisActor.addDamageHate(c0, 0, (long) (f0 * f1));
							}
						}
					}
				}
			}
			else if(eventId == 78010074 && (Integer) arg1 == 99)
			{
				L2Character c0 = L2ObjectsStorage.getAsCharacter((Long) arg2);
				if(c0 != null && _thisActor.getLoc().distance3D(c0.getLoc()) <= 3000)
				{
					if(_thisActor.getAggroListSize() == 0)
					{
						float f0 = DefaultHate;
						if(HateClassGroup1 > -1)
						{
							if(CategoryManager.isInCategory(HateClassGroup1, c0))
							{
								f0 += HateClassGroup1Boost;
							}
						}
						if(HateClassGroup2 > -1)
						{
							if(CategoryManager.isInCategory(HateClassGroup2, c0))
							{
								f0 += HateClassGroup2Boost;
							}
						}
						if(FavorClassGroup1 > -1)
						{
							if(CategoryManager.isInCategory(FavorClassGroup1, c0))
							{
								f0 -= FavorClassGroup1Boost;
							}
						}
						if((f0 + 1) < 0)
						{
							f0 = 0;
						}
						else
						{
							f0 = DefaultHate * (f0 + 1);
						}
						_thisActor.addDamageHate(c0, 0, (long) ((f0 * SEE_CREATURE_Weight_Point + Attack_BoostValue)));
					}
					else
					{
						float f0 = DefaultHate;
						if(HateClassGroup1 > -1)
						{
							if(CategoryManager.isInCategory(HateClassGroup1, c0))
							{
								f0 += HateClassGroup1Boost;
							}
						}
						if(HateClassGroup2 > -1)
						{
							if(CategoryManager.isInCategory(HateClassGroup2, c0))
							{
								f0 += HateClassGroup2Boost;
							}
						}
						if(FavorClassGroup1 > -1)
						{
							if(CategoryManager.isInCategory(FavorClassGroup1, c0))
							{
								f0 -= FavorClassGroup1Boost;
							}
						}
						if((f0 + 1) < 0)
						{
							f0 = 0;
						}
						else
						{
							f0 = DefaultHate * (f0 + 1);
						}
						_thisActor.addDamageHate(c0, 0, (long) (f0 * SEE_CREATURE_Weight_Point));
					}
				}
				if(CategoryManager.isInCategory(12, c0.getNpcId()))
				{
					float f0 = DefaultHate;
					if(HateClassGroup1 > -1)
					{
						if(CategoryManager.isInCategory(HateClassGroup1, c0.getPlayer()))
						{
							f0 += HateClassGroup1Boost;
						}
					}
					if(HateClassGroup2 > -1)
					{
						if(CategoryManager.isInCategory(HateClassGroup2, c0.getPlayer()))
						{
							f0 += HateClassGroup2Boost;
						}
					}
					if(FavorClassGroup1 > -1)
					{
						if(CategoryManager.isInCategory(FavorClassGroup1, c0.getPlayer()))
						{
							f0 -= FavorClassGroup1Boost;
						}
					}
					if((f0 + 1) < 0)
					{
						f0 = 0;
					}
					else
					{
						f0 = DefaultHate * (f0 + 1);
					}
					_thisActor.addDamageHate(c0.getPlayer(), 0, (long) (f0 * SEE_CREATURE_Weight_Point));
				}
			}
		}
	}
}
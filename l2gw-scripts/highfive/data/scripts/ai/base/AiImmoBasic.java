package ai.base;

import ru.l2gw.extensions.scripts.Functions;
import ru.l2gw.gameserver.ai.CtrlIntention;
import ru.l2gw.gameserver.clientpackets.Say2C;
import ru.l2gw.gameserver.instancemanager.InstanceManager;
import ru.l2gw.gameserver.instancemanager.superpoint.SuperpointNode;
import ru.l2gw.gameserver.model.*;
import ru.l2gw.gameserver.model.entity.category.CategoryManager;
import ru.l2gw.gameserver.model.instances.L2NpcInstance;
import ru.l2gw.gameserver.model.npcmaker.DefaultMaker;
import ru.l2gw.gameserver.tables.SkillTable;
import ru.l2gw.commons.arrays.GCSArray;
import ru.l2gw.commons.math.Rnd;
import ru.l2gw.util.Util;

/**
 * @author: rage
 * @date: 15.12.11 13:06
 */
public class AiImmoBasic extends MonsterBehavior
{
	public float DefaultHate = 100.000000f;
	public long Maximum_Hate = 999999984306749440L;
	public int HateClassGroup1 = 5;
	public float HateClassGroup1Boost = 80.000000f;
	public int HateClassGroup2 = 3;
	public float HateClassGroup2Boost = 40.000000f;
	public int FavorClassGroup1 = 4;
	public float FavorClassGroup1Boost = 20.000000f;
	public float SEE_CREATURE_Weight_Point = 1.000000f;
	public float ATTACKED_Weight_Point = 10.000000f;
	public float CLAN_ATTACKED_Weight_Point = 5.000000f;
	public float PARTY_ATTACKED_Weight_Point = 5.000000f;
	public float SEE_SPELL_Weight_Point = 20.000000f;
	public float HATE_SKILL_Weight_Point = 30.000000f;
	public float TUMOR_ATTACKED_Weight_Point = 10.000000f;
	public float VEIN_SIGNAL_Weight_Point = 10.000000f;
	public float TUMOR_HELP_Weight_Point = 10.000000f;
	public float LIFESEED_TAUNT_Weight_Point = 100.000000f;
	public L2Skill Skill01_ID = null;
	public L2Skill Skill02_ID = null;
	public L2Skill Skill03_ID = null;
	public L2Skill Skill04_ID = null;
	public int Skillchance_High = 15;
	public int Skillchance_Low = 7;
	public int Skillchance_Dim = 2;
	public L2Skill Skill_Siege = null;
	public L2Skill Skill_HPBuff = null;
	public L2Skill Skill_AntiParty_atk01 = SkillTable.getInstance().getInfo(388431873);
	public L2Skill Skill_AntiParty_atk02 = SkillTable.getInstance().getInfo(388431874);
	public L2Skill Skill_AntiParty_atk03 = SkillTable.getInstance().getInfo(388431875);
	public L2Skill Skill_AntiParty_atk04 = SkillTable.getInstance().getInfo(388431876);
	public L2Skill Skill_AntiParty_def01 = SkillTable.getInstance().getInfo(388497409);
	public L2Skill Skill_AntiParty_def02 = SkillTable.getInstance().getInfo(388497410);
	public L2Skill Skill_AntiParty_def03 = SkillTable.getInstance().getInfo(388497411);
	public L2Skill Skill_AntiParty_def04 = SkillTable.getInstance().getInfo(388497412);
	public int raise_modifier = 10;
	public String SuperPointName1 = "";
	public String SuperPointName2 = "";
	public int zone = 0;
	public int room = 0;
	public int tide = 0;
	public int raise = 0;
	public String dispatcher_maker = "";
	public int tact = 0;
	public String type = "";
	public String ech_atk_expel_maker = "rumwarsha15_1424_expelm1";
	public String z2_a_dispatcher_maker = "rumwarsha14_1424_a_dispm1";
	public String z2_d_dispatcher_maker = "rumwarsha14_1424_d_dispm1";
	public String z3_a_dispatcher_maker = "rumwarsha15_1424_a_dispm1";
	public String z3_d_dispatcher_maker = "rumwarsha15_1424_d_dispm1";
	public int TM_ATTACK_COOLDOWN = 78001;
	public int TIME_ATTACK_COOLDOWN_MELEE = 5;
	public int TIME_ATTACK_COOLDOWN_CASTER = 1;
	public int TM_REDEPLOY = 78003;
	public int TIME_REDEPLOY = 90;
	public int TACT_AGGRESIVE = 0;
	public int TACT_INTERCEPT = 1;
	public int TACT_DEFENSIVE = 2;
	protected GCSArray<Integer> int_list = new GCSArray<>();

	public AiImmoBasic(L2Character actor)
	{
		super(actor);
		IsAggressive = 1;
		Aggressive_Time = 5.000000f;
		Attack_DecayRatio = 6.600000f;
		UseSkill_DecayRatio = 66000.000000f;
		Attack_BoostValue = 300.000000f;
		UseSkill_BoostValue = 1000000.000000f;
		SuperPointMethod = 0;
		SuperPointDesire = 2000;
	}

	@Override
	protected void onEvtSpawn()
	{
		_globalAggro = 0;
		_thisActor.i_ai0 = 0;
		_thisActor.i_ai1 = 0;
		_thisActor.i_ai3 = 0;
		_thisActor.i_ai4 = 0;
		_thisActor.i_ai2 = 0;
		_thisActor.i_quest0 = 0;
		_thisActor.i_quest4 = 0;
		if(type.equals("boss_marching"))
		{
			_thisActor.setWalking();
			if(Rnd.get(2) == 0)
			{
				_thisActor.i_ai1 = 1;
			}
			else
			{
				_thisActor.i_ai1 = 2;
			}
			if(_thisActor.i_ai1 == 1)
			{
				addMoveSuperPointDesire(SuperPointName1, SuperPointMethod, SuperPointDesire);
			}
			else if(_thisActor.i_ai1 == 2)
			{
				addMoveSuperPointDesire(SuperPointName2, SuperPointMethod, SuperPointDesire);
			}
		}
		else
		{
			addTimer(TM_REDEPLOY, TIME_REDEPLOY * 1000);
			if(type.equals("dot") || type.equals("cc") || type.equals("con") || type.equals("ambush_dc_kamikaze") || type.equals("solo_boss_caster") || type.equals("duo_boss_caster") || type.equals("echmus"))
			{
				addTimer(TM_ATTACK_COOLDOWN, 1000);
			}
			else
			{
				addTimer(TM_ATTACK_COOLDOWN, 1000);
			}
		}
	}

	@Override
	protected void onEvtSeeCreature(L2Character creature)
	{
		if(creature == null || type.equals("boss_marching") || _thisActor.i_quest0 == 1 || _thisActor.getLifeTime() < Aggressive_Time)
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
					if(CategoryManager.isInCategory(HateClassGroup1, creature.getActiveClass()))
					{
						f0 += HateClassGroup1Boost;
					}
				}
				if(HateClassGroup2 > -1)
				{
					if(CategoryManager.isInCategory(HateClassGroup2, creature.getActiveClass()))
					{
						f0 += HateClassGroup2Boost;
					}
				}
				if(FavorClassGroup1 > -1)
				{
					if(CategoryManager.isInCategory(FavorClassGroup1, creature.getActiveClass()))
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
					if(CategoryManager.isInCategory(HateClassGroup1, creature.getActiveClass()))
					{
						f0 += HateClassGroup1Boost;
					}
				}
				if(HateClassGroup2 > -1)
				{
					if(CategoryManager.isInCategory(HateClassGroup2, creature.getActiveClass()))
					{
						f0 += HateClassGroup2Boost;
					}
				}
				if(FavorClassGroup1 > -1)
				{
					if(CategoryManager.isInCategory(FavorClassGroup1, creature.getActiveClass()))
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
				if(CategoryManager.isInCategory(HateClassGroup1, creature.getPlayer().getActiveClass()))
				{
					f0 += HateClassGroup1Boost;
				}
			}
			if(HateClassGroup2 > -1)
			{
				if(CategoryManager.isInCategory(HateClassGroup2, creature.getPlayer().getActiveClass()))
				{
					f0 += HateClassGroup2Boost;
				}
			}
			if(FavorClassGroup1 > -1)
			{
				if(CategoryManager.isInCategory(FavorClassGroup1, creature.getPlayer().getActiveClass()))
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
		if(attacker == null || type.equals("boss_marching") || _thisActor.i_quest0 == 1)
		{
			return;
		}

		if(attacker.isPlayer() || CategoryManager.isInCategory(12, attacker.getNpcId()))
		{
			if(_thisActor.getAggroListSize() == 0)
			{
				float f0 = DefaultHate;
				if(HateClassGroup1 > -1)
				{
					if(CategoryManager.isInCategory(HateClassGroup1, attacker.getActiveClass()))
					{
						f0 += HateClassGroup1Boost;
					}
				}
				if(HateClassGroup2 > -1)
				{
					if(CategoryManager.isInCategory(HateClassGroup2, attacker.getActiveClass()))
					{
						f0 += HateClassGroup2Boost;
					}
				}
				if(FavorClassGroup1 > -1)
				{
					if(CategoryManager.isInCategory(FavorClassGroup1, attacker.getActiveClass()))
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
				_thisActor.addDamageHate(attacker, damage, (long) (f0 * ATTACKED_Weight_Point + Attack_BoostValue));
			}
			else
			{
				float f0 = DefaultHate;
				if(HateClassGroup1 > -1)
				{
					if(CategoryManager.isInCategory(HateClassGroup1, attacker.getActiveClass()))
					{
						f0 += HateClassGroup1Boost;
					}
				}
				if(HateClassGroup2 > -1)
				{
					if(CategoryManager.isInCategory(HateClassGroup2, attacker.getActiveClass()))
					{
						f0 += HateClassGroup2Boost;
					}
				}
				if(FavorClassGroup1 > -1)
				{
					if(CategoryManager.isInCategory(FavorClassGroup1, attacker.getActiveClass()))
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
				if(CategoryManager.isInCategory(HateClassGroup1, attacker.getPlayer().getActiveClass()))
				{
					f0 += HateClassGroup1Boost;
				}
			}
			if(HateClassGroup2 > -1)
			{
				if(CategoryManager.isInCategory(HateClassGroup2, attacker.getPlayer().getActiveClass()))
				{
					f0 += HateClassGroup2Boost;
				}
			}
			if(FavorClassGroup1 > -1)
			{
				if(CategoryManager.isInCategory(FavorClassGroup1, attacker.getPlayer().getActiveClass()))
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
		if(attacker != null && (attacker.isPlayer() || CategoryManager.isInCategory(12, attacker.getNpcId())))
		{
			L2Party party0 = null;
			if(attacker.isPlayer())
			{
				party0 = Util.getParty(attacker);
			}
			else if(CategoryManager.isInCategory(12, attacker.getNpcId()) && attacker.getPlayer() != null)
			{
				party0 = Util.getParty(attacker.getPlayer());
			}

			if(party0 != null && int_list.size() == 0)
			{
				int_list.add(party0.getPartyId());
			}
			else if(party0 == null && int_list.size() == 0)
			{
				int_list.add(attacker.getObjectId());
			}
			else if(party0 != null && int_list.size() > 0)
			{
				for(int i0 = 0; i0 < int_list.size(); i0++)
				{
					if(int_list.get(i0) == party0.getPartyId())
					{
						_thisActor.i_ai2++;
					}
				}
				if(_thisActor.i_ai2 == 0)
				{
					if(_thisActor.getNpcId() == 29150)
					{
						_thisActor.removeAllHateInfoIF(1, 0);
						_thisActor.removeAllHateInfoIF(3, 2000);
						clearTasks();
						_thisActor.i_ai2 = 0;
						DefaultMaker maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), ech_atk_expel_maker);
						if(maker0 != null)
						{
							maker0.onScriptEvent(78010066, 0, 0);
						}
						int i0 = 1800249;
						Functions.broadcastOnScreenMsgFStr(_thisActor, 8000, 5, 0, 1, 4, 1, 1, 5000, 0, i0);
						Functions.broadcastSystemMessageFStr(_thisActor, 8000, i0);
					}
					else
					{
						Functions.npcSay(_thisActor, Say2C.SHOUT, 1800256);
						removeAllAttackDesire();
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
						if(SkillTable.getAbnormalLevel(_thisActor, Skill_AntiParty_def01) < 1 && _thisActor.getCurrentHp() <= _thisActor.getMaxHp() * 0.600000)
						{
							if(Skill_AntiParty_def01.getMpConsume() < _thisActor.getCurrentMp())
							{
								addUseSkillDesire(_thisActor, Skill_AntiParty_def01, 1, 1, Maximum_Hate);
							}
						}
						else if(SkillTable.getAbnormalLevel(_thisActor, Skill_AntiParty_def01) < 2 && _thisActor.getCurrentHp() <= _thisActor.getMaxHp() * 0.300000)
						{
							if(Skill_AntiParty_def02.getMpConsume() < _thisActor.getCurrentMp())
							{
								addUseSkillDesire(_thisActor, Skill_AntiParty_def02, 1, 1, Maximum_Hate);
							}
						}
					}

					int_list.add(party0.getPartyId());
					_thisActor.i_ai2 = 0;
				}
			}
			else if(party0 == null && int_list.size() > 0)
			{
				for(int i0 = 0; i0 < int_list.size(); i0++)
				{
					if(int_list.get(i0) == attacker.getObjectId())
					{
						_thisActor.i_ai2++;
					}
				}
				if(_thisActor.i_ai2 == 0)
				{
					if(_thisActor.getNpcId() == 29150)
					{
						int i0 = 1800249;
						Functions.broadcastOnScreenMsgFStr(_thisActor, 8000, 5, 0, 1, 4, 1, 1, 5000, 0, i0);
						Functions.broadcastSystemMessageFStr(_thisActor, 8000, i0);
						DefaultMaker maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), ech_atk_expel_maker);
						if(maker0 != null)
						{
							maker0.onScriptEvent(78010066, 0, 0);
						}
					}
					else
					{
						Functions.npcSay(_thisActor, Say2C.SHOUT, 1800256);
						removeAllAttackDesire();
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
						if(SkillTable.getAbnormalLevel(_thisActor, Skill_AntiParty_def01) < 1 && _thisActor.getCurrentHp() <= _thisActor.getMaxHp() * 0.600000)
						{
							if(Skill_AntiParty_def01.getMpConsume() < _thisActor.getCurrentMp())
							{
								addUseSkillDesire(_thisActor, Skill_AntiParty_def01, 1, 1, Maximum_Hate);
							}
						}
						else if(SkillTable.getAbnormalLevel(_thisActor, Skill_AntiParty_def01) < 2 && _thisActor.getCurrentHp() <= _thisActor.getMaxHp() * 0.300000)
						{
							if(Skill_AntiParty_def02.getMpConsume() < _thisActor.getCurrentMp())
							{
								addUseSkillDesire(_thisActor, Skill_AntiParty_def02, 1, 1, Maximum_Hate);
							}
						}
					}
					int_list.add(attacker.getObjectId());
					_thisActor.i_ai2 = 0;
				}
			}
			if(party0 != null)
			{
				float f1 = party0.getMemberCount() * SEE_CREATURE_Weight_Point;
				for(L2Player c0 : party0.getPartyMembers())
				{
					if(c0 != null)
					{
						if(c0.isPlayer())
						{
							if(_thisActor.getAggroListSize() == 0)
							{
								float f0 = DefaultHate;
								if(HateClassGroup1 > -1)
								{
									if(CategoryManager.isInCategory(HateClassGroup1, c0.getActiveClass()))
									{
										f0 += HateClassGroup1Boost;
									}
								}
								if(HateClassGroup2 > -1)
								{
									if(CategoryManager.isInCategory(HateClassGroup2, c0.getActiveClass()))
									{
										f0 += HateClassGroup2Boost;
									}
								}
								if(FavorClassGroup1 > -1)
								{
									if(CategoryManager.isInCategory(FavorClassGroup1, c0.getActiveClass()))
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
								_thisActor.addDamageHate(c0, 0, (long) (f0 * f1 + Attack_BoostValue));
							}
							else
							{
								float f0 = DefaultHate;
								if(HateClassGroup1 > -1)
								{
									if(CategoryManager.isInCategory(HateClassGroup1, c0.getActiveClass()))
									{
										f0 += HateClassGroup1Boost;
									}
								}
								if(HateClassGroup2 > -1)
								{
									if(CategoryManager.isInCategory(HateClassGroup2, c0.getActiveClass()))
									{
										f0 += HateClassGroup2Boost;
									}
								}
								if(FavorClassGroup1 > -1)
								{
									if(CategoryManager.isInCategory(FavorClassGroup1, c0.getActiveClass()))
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
		}

		if(_thisActor.getCurrentHp() / _thisActor.getMaxHp() <= 0.100000 && raise <= Rnd.get(100) && _thisActor.i_quest4 == 0)
		{
			Functions.npcSay(_thisActor, Say2C.ALL, 1800748);
			_thisActor.setHpRegen(_thisActor.getTemplate().baseHpReg * raise_modifier);
			_thisActor.i_quest4 = 1;
		}

		if(Skill_HPBuff != null)
		{
			if(_thisActor.i_ai0 <= 0 && _thisActor.getCurrentHp() <= _thisActor.getMaxHp() * 0.900000 && (type.equals("solo_boss") || type.equals("duo_boss") || type.equals("boss_marching")))
			{
				_thisActor.i_ai0 = 1;
				if(Skill_HPBuff.getIndex() == 387645441)
				{
					if(Skill_HPBuff.getMpConsume() < _thisActor.getCurrentMp())
					{
						addUseSkillDesire(_thisActor, Skill_HPBuff, 1, 1, Maximum_Hate);
					}
				}
				else if(Skill_HPBuff.getIndex() == 387710977)
				{
					if(Skill_HPBuff.getMpConsume() < _thisActor.getCurrentMp())
					{
						addUseSkillDesire(_thisActor, Skill_HPBuff, 1, 1, Maximum_Hate);
					}
				}
				else if(Skill_HPBuff.getIndex() == 387776513)
				{
					if(Skill_HPBuff.getMpConsume() < _thisActor.getCurrentMp())
					{
						addUseSkillDesire(_thisActor, Skill_HPBuff, 1, 1, Maximum_Hate);
					}
				}
				else if(Skill_HPBuff.getIndex() == 392232961)
				{
					if(Skill_HPBuff.getMpConsume() < _thisActor.getCurrentMp())
					{
						addUseSkillDesire(_thisActor, Skill_HPBuff, 1, 1, Maximum_Hate);
					}
				}
				else if(Skill_HPBuff.getIndex() == 392298497)
				{
					if(Skill_HPBuff.getMpConsume() < _thisActor.getCurrentMp())
					{
						addUseSkillDesire(_thisActor, Skill_HPBuff, 1, 1, Maximum_Hate);
					}
				}
			}
			else if(_thisActor.i_ai0 <= 1 && _thisActor.getCurrentHp() <= _thisActor.getMaxHp() * 0.800000 && (type.equals("solo_boss") || type.equals("duo_boss") || type.equals("boss_marching")))
			{
				_thisActor.i_ai0 = 2;
				if(Skill_HPBuff.getIndex() == 387645441)
				{
					if(SkillTable.mpConsume(387645442) < _thisActor.getCurrentMp())
					{
						addUseSkillDesire(_thisActor, SkillTable.getInstance().getInfo(387645442), 1, 1, Maximum_Hate);
					}
				}
				else if(Skill_HPBuff.getIndex() == 387710977)
				{
					if(SkillTable.mpConsume(387710978) < _thisActor.getCurrentMp())
					{
						addUseSkillDesire(_thisActor, SkillTable.getInstance().getInfo(387710978), 1, 1, Maximum_Hate);
					}
				}
				else if(Skill_HPBuff.getIndex() == 387776513)
				{
					if(SkillTable.mpConsume(387776514) < _thisActor.getCurrentMp())
					{
						addUseSkillDesire(_thisActor, SkillTable.getInstance().getInfo(387776514), 1, 1, Maximum_Hate);
					}
				}
				else if(Skill_HPBuff.getIndex() == 392232961)
				{
					if(SkillTable.mpConsume(392232962) < _thisActor.getCurrentMp())
					{
						addUseSkillDesire(_thisActor, SkillTable.getInstance().getInfo(392232962), 1, 1, Maximum_Hate);
					}
				}
				else if(Skill_HPBuff.getIndex() == 392298497)
				{
					if(SkillTable.mpConsume(392298498) < _thisActor.getCurrentMp())
					{
						addUseSkillDesire(_thisActor, SkillTable.getInstance().getInfo(392298498), 1, 1, Maximum_Hate);
					}
				}
			}
			else if(_thisActor.i_ai0 <= 2 && _thisActor.getCurrentHp() <= _thisActor.getMaxHp() * 0.700000 && (type.equals("solo_boss") || type.equals("duo_boss") || type.equals("boss_marching")))
			{
				_thisActor.i_ai0 = 3;
				if(Skill_HPBuff.getIndex() == 387645441)
				{
					if(SkillTable.mpConsume(387645443) < _thisActor.getCurrentMp())
					{
						addUseSkillDesire(_thisActor, SkillTable.getInstance().getInfo(387645443), 1, 1, Maximum_Hate);
					}
				}
				else if(Skill_HPBuff.getIndex() == 387710977)
				{
					if(SkillTable.mpConsume(387710979) < _thisActor.getCurrentMp())
					{
						addUseSkillDesire(_thisActor, SkillTable.getInstance().getInfo(387710979), 1, 1, Maximum_Hate);
					}
				}
				else if(Skill_HPBuff.getIndex() == 387776513)
				{
					if(SkillTable.mpConsume(387776515) < _thisActor.getCurrentMp())
					{
						addUseSkillDesire(_thisActor, SkillTable.getInstance().getInfo(387776515), 1, 1, Maximum_Hate);
					}
				}
				else if(Skill_HPBuff.getIndex() == 392232961)
				{
					if(SkillTable.mpConsume(392232963) < _thisActor.getCurrentMp())
					{
						addUseSkillDesire(_thisActor, SkillTable.getInstance().getInfo(392232963), 1, 1, Maximum_Hate);
					}
				}
				else if(Skill_HPBuff.getIndex() == 392298497)
				{
					if(SkillTable.mpConsume(392298499) < _thisActor.getCurrentMp())
					{
						addUseSkillDesire(_thisActor, SkillTable.getInstance().getInfo(392298499), 1, 1, Maximum_Hate);
					}
				}
			}
			else if(_thisActor.i_ai0 <= 3 && _thisActor.getCurrentHp() <= _thisActor.getMaxHp() * 0.600000 && (type.equals("solo_boss") || type.equals("duo_boss") || type.equals("boss_marching")))
			{
				_thisActor.i_ai0 = 4;
				if(Skill_HPBuff.getIndex() == 387645441)
				{
					if(SkillTable.mpConsume(387645444) < _thisActor.getCurrentMp())
					{
						addUseSkillDesire(_thisActor, SkillTable.getInstance().getInfo(387645444), 1, 1, Maximum_Hate);
					}
				}
				else if(Skill_HPBuff.getIndex() == 387710977)
				{
					if(SkillTable.mpConsume(387710980) < _thisActor.getCurrentMp())
					{
						addUseSkillDesire(_thisActor, SkillTable.getInstance().getInfo(387710980), 1, 1, Maximum_Hate);
					}
				}
				else if(Skill_HPBuff.getIndex() == 387776513)
				{
					if(SkillTable.mpConsume(387776516) < _thisActor.getCurrentMp())
					{
						addUseSkillDesire(_thisActor, SkillTable.getInstance().getInfo(387776516), 1, 1, Maximum_Hate);
					}
				}
				else if(Skill_HPBuff.getIndex() == 392232961)
				{
					if(SkillTable.mpConsume(392232964) < _thisActor.getCurrentMp())
					{
						addUseSkillDesire(_thisActor, SkillTable.getInstance().getInfo(392232964), 1, 1, Maximum_Hate);
					}
				}
				else if(Skill_HPBuff.getIndex() == 392298497)
				{
					if(SkillTable.mpConsume(392298500) < _thisActor.getCurrentMp())
					{
						addUseSkillDesire(_thisActor, SkillTable.getInstance().getInfo(392298500), 1, 1, Maximum_Hate);
					}
				}
			}
			else if(_thisActor.i_ai0 <= 4 && _thisActor.getCurrentHp() <= _thisActor.getMaxHp() * 0.500000 && (type.equals("solo_boss") || type.equals("duo_boss") || type.equals("boss_marching")))
			{
				_thisActor.i_ai0 = 5;
				if(Skill_HPBuff.getIndex() == 387645441)
				{
					if(SkillTable.mpConsume(387645445) < _thisActor.getCurrentMp())
					{
						addUseSkillDesire(_thisActor, SkillTable.getInstance().getInfo(387645445), 1, 1, Maximum_Hate);
					}
				}
				else if(Skill_HPBuff.getIndex() == 387710977)
				{
					if(SkillTable.mpConsume(387710981) < _thisActor.getCurrentMp())
					{
						addUseSkillDesire(_thisActor, SkillTable.getInstance().getInfo(387710981), 1, 1, Maximum_Hate);
					}
				}
				else if(Skill_HPBuff.getIndex() == 387776513)
				{
					if(SkillTable.mpConsume(387776517) < _thisActor.getCurrentMp())
					{
						addUseSkillDesire(_thisActor, SkillTable.getInstance().getInfo(387776517), 1, 1, Maximum_Hate);
					}
				}
				else if(Skill_HPBuff.getIndex() == 392232961)
				{
					if(SkillTable.mpConsume(392232965) < _thisActor.getCurrentMp())
					{
						addUseSkillDesire(_thisActor, SkillTable.getInstance().getInfo(392232965), 1, 1, Maximum_Hate);
					}
				}
				else if(Skill_HPBuff.getIndex() == 392298497)
				{
					if(SkillTable.mpConsume(392298501) < _thisActor.getCurrentMp())
					{
						addUseSkillDesire(_thisActor, SkillTable.getInstance().getInfo(392298501), 1, 1, Maximum_Hate);
					}
				}
			}
			else if(_thisActor.i_ai0 <= 5 && _thisActor.getCurrentHp() <= _thisActor.getMaxHp() * 0.400000 && (type.equals("solo_boss") || type.equals("duo_boss") || type.equals("boss_marching")))
			{
				_thisActor.i_ai0 = 6;
				if(Skill_HPBuff.getIndex() == 387645441)
				{
					if(SkillTable.mpConsume(387645446) < _thisActor.getCurrentMp())
					{
						addUseSkillDesire(_thisActor, SkillTable.getInstance().getInfo(387645446), 1, 1, Maximum_Hate);
					}
				}
				else if(Skill_HPBuff.getIndex() == 387710977)
				{
					if(SkillTable.mpConsume(387710982) < _thisActor.getCurrentMp())
					{
						addUseSkillDesire(_thisActor, SkillTable.getInstance().getInfo(387710982), 1, 1, Maximum_Hate);
					}
				}
				else if(Skill_HPBuff.getIndex() == 387776513)
				{
					if(SkillTable.mpConsume(387776518) < _thisActor.getCurrentMp())
					{
						addUseSkillDesire(_thisActor, SkillTable.getInstance().getInfo(387776518), 1, 1, Maximum_Hate);
					}
				}
				else if(Skill_HPBuff.getIndex() == 392232961)
				{
					if(SkillTable.mpConsume(392232966) < _thisActor.getCurrentMp())
					{
						addUseSkillDesire(_thisActor, SkillTable.getInstance().getInfo(392232966), 1, 1, Maximum_Hate);
					}
				}
				else if(Skill_HPBuff.getIndex() == 392298497)
				{
					if(SkillTable.mpConsume(392298502) < _thisActor.getCurrentMp())
					{
						addUseSkillDesire(_thisActor, SkillTable.getInstance().getInfo(392298502), 1, 1, Maximum_Hate);
					}
				}
			}
			else if(_thisActor.i_ai0 <= 6 && _thisActor.getCurrentHp() <= _thisActor.getMaxHp() * 0.300000 && (type.equals("solo_boss") || type.equals("duo_boss") || type.equals("boss_marching")))
			{
				_thisActor.i_ai0 = 7;
				if(Skill_HPBuff.getIndex() == 387645441)
				{
					if(SkillTable.mpConsume(387645447) < _thisActor.getCurrentMp())
					{
						addUseSkillDesire(_thisActor, SkillTable.getInstance().getInfo(387645447), 1, 1, Maximum_Hate);
					}
				}
				else if(Skill_HPBuff.getIndex() == 387710977)
				{
					if(SkillTable.mpConsume(387710983) < _thisActor.getCurrentMp())
					{
						addUseSkillDesire(_thisActor, SkillTable.getInstance().getInfo(387710983), 1, 1, Maximum_Hate);
					}
				}
				else if(Skill_HPBuff.getIndex() == 387776513)
				{
					if(SkillTable.mpConsume(387776519) < _thisActor.getCurrentMp())
					{
						addUseSkillDesire(_thisActor, SkillTable.getInstance().getInfo(387776519), 1, 1, Maximum_Hate);
					}
				}
				else if(Skill_HPBuff.getIndex() == 392232961)
				{
					if(SkillTable.mpConsume(392232967) < _thisActor.getCurrentMp())
					{
						addUseSkillDesire(_thisActor, SkillTable.getInstance().getInfo(392232967), 1, 1, Maximum_Hate);
					}
				}
				else if(Skill_HPBuff.getIndex() == 392298497)
				{
					if(SkillTable.mpConsume(392298503) < _thisActor.getCurrentMp())
					{
						addUseSkillDesire(_thisActor, SkillTable.getInstance().getInfo(392298503), 1, 1, Maximum_Hate);
					}
				}
			}
			else if(_thisActor.i_ai0 <= 7 && _thisActor.getCurrentHp() <= _thisActor.getMaxHp() * 0.200000 && (type.equals("solo_boss") || type.equals("duo_boss") || type.equals("boss_marching")))
			{
				_thisActor.i_ai0 = 8;
				if(Skill_HPBuff.getIndex() == 387645441)
				{
					if(SkillTable.mpConsume(387645448) < _thisActor.getCurrentMp())
					{
						addUseSkillDesire(_thisActor, SkillTable.getInstance().getInfo(387645448), 1, 1, Maximum_Hate);
					}
				}
				else if(Skill_HPBuff.getIndex() == 387710977)
				{
					if(SkillTable.mpConsume(387710984) < _thisActor.getCurrentMp())
					{
						addUseSkillDesire(_thisActor, SkillTable.getInstance().getInfo(387710984), 1, 1, Maximum_Hate);
					}
				}
				else if(Skill_HPBuff.getIndex() == 387776513)
				{
					if(SkillTable.mpConsume(387776520) < _thisActor.getCurrentMp())
					{
						addUseSkillDesire(_thisActor, SkillTable.getInstance().getInfo(387776520), 1, 1, Maximum_Hate);
					}
				}
				else if(Skill_HPBuff.getIndex() == 392232961)
				{
					if(SkillTable.mpConsume(392232968) < _thisActor.getCurrentMp())
					{
						addUseSkillDesire(_thisActor, SkillTable.getInstance().getInfo(392232968), 1, 1, Maximum_Hate);
					}
				}
				else if(Skill_HPBuff.getIndex() == 392298497)
				{
					if(SkillTable.mpConsume(392298504) < _thisActor.getCurrentMp())
					{
						addUseSkillDesire(_thisActor, SkillTable.getInstance().getInfo(392298504), 1, 1, Maximum_Hate);
					}
				}
			}
			else if(_thisActor.i_ai0 <= 8 && _thisActor.getCurrentHp() <= _thisActor.getMaxHp() * 0.100000 && (type.equals("solo_boss") || type.equals("duo_boss") || type.equals("boss_marching")))
			{
				_thisActor.i_ai0 = 9;
				if(Skill_HPBuff.getIndex() == 387645441)
				{
					if(SkillTable.mpConsume(387645449) < _thisActor.getCurrentMp())
					{
						addUseSkillDesire(_thisActor, SkillTable.getInstance().getInfo(387645449), 1, 1, Maximum_Hate);
					}
				}
				else if(Skill_HPBuff.getIndex() == 387710977)
				{
					if(SkillTable.mpConsume(387710985) < _thisActor.getCurrentMp())
					{
						addUseSkillDesire(_thisActor, SkillTable.getInstance().getInfo(387710985), 1, 1, Maximum_Hate);
					}
				}
				else if(Skill_HPBuff.getIndex() == 387776513)
				{
					if(SkillTable.mpConsume(387776521) < _thisActor.getCurrentMp())
					{
						addUseSkillDesire(_thisActor, SkillTable.getInstance().getInfo(387776521), 1, 1, Maximum_Hate);
					}
				}
				else if(Skill_HPBuff.getIndex() == 392232961)
				{
					if(SkillTable.mpConsume(392232969) < _thisActor.getCurrentMp())
					{
						addUseSkillDesire(_thisActor, SkillTable.getInstance().getInfo(392232969), 1, 1, Maximum_Hate);
					}
				}
				else if(Skill_HPBuff.getIndex() == 392298497)
				{
					if(SkillTable.mpConsume(392298505) < _thisActor.getCurrentMp())
					{
						addUseSkillDesire(_thisActor, SkillTable.getInstance().getInfo(392298505), 1, 1, Maximum_Hate);
					}
				}
			}
		}
		if(attacker != null && _thisActor.getCurrentHp() < _thisActor.getMaxHp() * 0.050000 && Rnd.get(25) == 0)
		{
			addUseSkillDesire(attacker, SkillTable.getInstance().getInfo(385089537), 0, 1, Maximum_Hate);
		}
	}

	@Override
	protected void onEvtClanAttacked(L2Character attacked_member, L2Character attacker, int damage)
	{
		if(type.equals("boss_marching") || _thisActor.i_quest0 == 1)
		{
			return;
		}
		if((Party_Type == 0 || (Party_Type == 1 && Party_Loyalty == 0)) || Party_Type == 2)
		{
			if(attacker.isPlayer() || CategoryManager.isInCategory(12, attacker.getNpcId()))
			{
				if(_thisActor.getAggroListSize() == 0)
				{
					float f0 = DefaultHate;
					if(HateClassGroup1 > -1)
					{
						if(CategoryManager.isInCategory(HateClassGroup1, attacker.getActiveClass()))
						{
							f0 += HateClassGroup1Boost;
						}
					}
					if(HateClassGroup2 > -1)
					{
						if(CategoryManager.isInCategory(HateClassGroup2, attacker.getActiveClass()))
						{
							f0 += HateClassGroup2Boost;
						}
					}
					if(FavorClassGroup1 > -1)
					{
						if(CategoryManager.isInCategory(FavorClassGroup1, attacker.getActiveClass()))
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
					_thisActor.addDamageHate(attacker, 0, (long) (f0 * CLAN_ATTACKED_Weight_Point + Attack_BoostValue));
				}
				else
				{
					float f0 = DefaultHate;
					if(HateClassGroup1 > -1)
					{
						if(CategoryManager.isInCategory(HateClassGroup1, attacker.getActiveClass()))
						{
							f0 += HateClassGroup1Boost;
						}
					}
					if(HateClassGroup2 > -1)
					{
						if(CategoryManager.isInCategory(HateClassGroup2, attacker.getActiveClass()))
						{
							f0 += HateClassGroup2Boost;
						}
					}
					if(FavorClassGroup1 > -1)
					{
						if(CategoryManager.isInCategory(FavorClassGroup1, attacker.getActiveClass()))
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
					if(CategoryManager.isInCategory(HateClassGroup1, attacker.getPlayer().getActiveClass()))
					{
						f0 += HateClassGroup1Boost;
					}
				}
				if(HateClassGroup2 > -1)
				{
					if(CategoryManager.isInCategory(HateClassGroup2, attacker.getPlayer().getActiveClass()))
					{
						f0 += HateClassGroup2Boost;
					}
				}
				if(FavorClassGroup1 > -1)
				{
					if(CategoryManager.isInCategory(FavorClassGroup1, attacker.getPlayer().getActiveClass()))
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

	@Override
	protected void onEvtPartyAttacked(L2Character attacker, L2Character victim, int damage)
	{
		if(type.equals("boss_marching") || _thisActor.i_quest0 == 1)
		{
			return;
		}
		if((Party_Type == 1 && victim == _thisActor.getLeader()) || (Party_Type == 2 && victim == _thisActor))
		{
			if(attacker.isPlayer() || CategoryManager.isInCategory(12, attacker.getNpcId()))
			{
				if(_thisActor.getAggroListSize() == 0)
				{
					float f0 = DefaultHate;
					if(HateClassGroup1 > -1)
					{
						if(CategoryManager.isInCategory(HateClassGroup1, attacker.getActiveClass()))
						{
							f0 += HateClassGroup1Boost;
						}
					}
					if(HateClassGroup2 > -1)
					{
						if(CategoryManager.isInCategory(HateClassGroup2, attacker.getActiveClass()))
						{
							f0 += HateClassGroup2Boost;
						}
					}
					if(FavorClassGroup1 > -1)
					{
						if(CategoryManager.isInCategory(FavorClassGroup1, attacker.getActiveClass()))
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
					_thisActor.addDamageHate(attacker, 0, (long) (f0 * PARTY_ATTACKED_Weight_Point + Attack_BoostValue));
				}
				else
				{
					float f0 = DefaultHate;
					if(HateClassGroup1 > -1)
					{
						if(CategoryManager.isInCategory(HateClassGroup1, attacker.getActiveClass()))
						{
							f0 += HateClassGroup1Boost;
						}
					}
					if(HateClassGroup2 > -1)
					{
						if(CategoryManager.isInCategory(HateClassGroup2, attacker.getActiveClass()))
						{
							f0 += HateClassGroup2Boost;
						}
					}
					if(FavorClassGroup1 > -1)
					{
						if(CategoryManager.isInCategory(FavorClassGroup1, attacker.getActiveClass()))
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
					if(CategoryManager.isInCategory(HateClassGroup1, attacker.getPlayer().getActiveClass()))
					{
						f0 += HateClassGroup1Boost;
					}
				}
				if(HateClassGroup2 > -1)
				{
					if(CategoryManager.isInCategory(HateClassGroup2, attacker.getPlayer().getActiveClass()))
					{
						f0 += HateClassGroup2Boost;
					}
				}
				if(FavorClassGroup1 > -1)
				{
					if(CategoryManager.isInCategory(FavorClassGroup1, attacker.getPlayer().getActiveClass()))
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

	@Override
	protected void onEvtManipulation(L2Character target, int aggro, L2Skill skill)
	{
		if(type.equals("boss_marching"))
		{
			return;
		}

		if(aggro > 0)
		{
			onEvtAggression(target, (int) (aggro * HATE_SKILL_Weight_Point), skill);
		}

		super.onEvtManipulation(target, aggro, skill);
	}

	@Override
	protected void onEvtSeeSpell(L2Skill skill, L2Character caster)
	{
		if(skill == null || caster == null || type.equals("boss_marching") || _thisActor.i_quest0 == 1 || _thisActor.getLifeTime() < Aggressive_Time)
		{
			return;
		}
		int i0 = skill.getEffectPoint();
		if(i0 > 0 && (caster.isPlayer() || CategoryManager.isInCategory(12, caster.getNpcId())))
		{
			L2Character c0 = _thisActor.getMostHated();

			if(c0 != null)
			{
				if(!_thisActor.isMoving && c0 == caster)
				{
					if(caster.isPlayer() || CategoryManager.isInCategory(12, caster.getNpcId()))
					{
						if(_thisActor.getAggroListSize() == 0)
						{
							float f0 = DefaultHate;
							if(HateClassGroup1 > -1)
							{
								if(CategoryManager.isInCategory(HateClassGroup1, caster.getActiveClass()))
								{
									f0 += HateClassGroup1Boost;
								}
							}
							if(HateClassGroup2 > -1)
							{
								if(CategoryManager.isInCategory(HateClassGroup2, caster.getActiveClass()))
								{
									f0 += HateClassGroup2Boost;
								}
							}
							if(FavorClassGroup1 > -1)
							{
								if(CategoryManager.isInCategory(FavorClassGroup1, caster.getActiveClass()))
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
								f0 = i0 * (f0 + 1);
							}
							_thisActor.addDamageHate(caster, 0, (long) (f0 * ATTACKED_Weight_Point + Attack_BoostValue));
						}
						else
						{
							float f0 = DefaultHate;
							if(HateClassGroup1 > -1)
							{
								if(CategoryManager.isInCategory(HateClassGroup1, caster.getActiveClass()))
								{
									f0 += HateClassGroup1Boost;
								}
							}
							if(HateClassGroup2 > -1)
							{
								if(CategoryManager.isInCategory(HateClassGroup2, caster.getActiveClass()))
								{
									f0 += HateClassGroup2Boost;
								}
							}
							if(FavorClassGroup1 > -1)
							{
								if(CategoryManager.isInCategory(FavorClassGroup1, caster.getActiveClass()))
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
								f0 = i0 * (f0 + 1);
							}
							_thisActor.addDamageHate(caster, 0, (long) (f0 * ATTACKED_Weight_Point));
						}
					}
					if(CategoryManager.isInCategory(12, caster.getNpcId()))
					{
						float f0 = DefaultHate;
						if(HateClassGroup1 > -1)
						{
							if(CategoryManager.isInCategory(HateClassGroup1, caster.getPlayer().getActiveClass()))
							{
								f0 += HateClassGroup1Boost;
							}
						}
						if(HateClassGroup2 > -1)
						{
							if(CategoryManager.isInCategory(HateClassGroup2, caster.getPlayer().getActiveClass()))
							{
								f0 += HateClassGroup2Boost;
							}
						}
						if(FavorClassGroup1 > -1)
						{
							if(CategoryManager.isInCategory(FavorClassGroup1, caster.getPlayer().getActiveClass()))
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
							f0 = i0 * (f0 + 1);
						}
						_thisActor.addDamageHate(caster.getPlayer(), 0, (long) (f0 * ATTACKED_Weight_Point));
					}
				}
				else if(caster.isPlayer() || CategoryManager.isInCategory(12, caster.getNpcId()))
				{
					if(_thisActor.getAggroListSize() == 0)
					{
						float f0 = DefaultHate;
						if(HateClassGroup1 > -1)
						{
							if(CategoryManager.isInCategory(HateClassGroup1, caster.getActiveClass()))
							{
								f0 += HateClassGroup1Boost;
							}
						}
						if(HateClassGroup2 > -1)
						{
							if(CategoryManager.isInCategory(HateClassGroup2, caster.getActiveClass()))
							{
								f0 += HateClassGroup2Boost;
							}
						}
						if(FavorClassGroup1 > -1)
						{
							if(CategoryManager.isInCategory(FavorClassGroup1, caster.getActiveClass()))
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
							f0 = i0 * (f0 + 1);
						}
						_thisActor.addDamageHate(caster, 0, (long) (f0 * SEE_SPELL_Weight_Point + Attack_BoostValue));
					}
					else
					{
						float f0 = DefaultHate;
						if(HateClassGroup1 > -1)
						{
							if(CategoryManager.isInCategory(HateClassGroup1, caster.getActiveClass()))
							{
								f0 += HateClassGroup1Boost;
							}
						}
						if(HateClassGroup2 > -1)
						{
							if(CategoryManager.isInCategory(HateClassGroup2, caster.getActiveClass()))
							{
								f0 += HateClassGroup2Boost;
							}
						}
						if(FavorClassGroup1 > -1)
						{
							if(CategoryManager.isInCategory(FavorClassGroup1, caster.getActiveClass()))
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
							f0 = i0 * (f0 + 1);
						}
						_thisActor.addDamageHate(caster, 0, (long) (f0 * SEE_SPELL_Weight_Point));
					}
				}
				if(CategoryManager.isInCategory(12, caster.getNpcId()))
				{
					float f0 = DefaultHate;
					if(HateClassGroup1 > -1)
					{
						if(CategoryManager.isInCategory(HateClassGroup1, caster.getPlayer().getActiveClass()))
						{
							f0 += HateClassGroup1Boost;
						}
					}
					if(HateClassGroup2 > -1)
					{
						if(CategoryManager.isInCategory(HateClassGroup2, caster.getPlayer().getActiveClass()))
						{
							f0 += HateClassGroup2Boost;
						}
					}
					if(FavorClassGroup1 > -1)
					{
						if(CategoryManager.isInCategory(FavorClassGroup1, caster.getPlayer().getActiveClass()))
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
						f0 = i0 * (f0 + 1);
					}
					_thisActor.addDamageHate(caster.getPlayer(), 0, (long) (f0 * SEE_SPELL_Weight_Point));
				}
			}
		}
	}

	@Override
	protected void onEvtScriptEvent(int eventId, Object arg1, Object arg2)
	{
		if(eventId == 78010049 && (Long) arg1 != 0 && _thisActor.i_quest0 == 0)
		{
			int i1 = (Integer) arg2 * 100;
			L2Character c0 = L2ObjectsStorage.getAsCharacter((Long) arg1);
			if(c0 != null)
			{
				if(c0.isPlayer() || CategoryManager.isInCategory(12, c0.getNpcId()))
				{
					if(_thisActor.getAggroListSize() == 0)
					{
						float f0 = DefaultHate;
						if(HateClassGroup1 > -1)
						{
							if(CategoryManager.isInCategory(HateClassGroup1, c0.getActiveClass()))
							{
								f0 += HateClassGroup1Boost;
							}
						}
						if(HateClassGroup2 > -1)
						{
							if(CategoryManager.isInCategory(HateClassGroup2, c0.getActiveClass()))
							{
								f0 += HateClassGroup2Boost;
							}
						}
						if(FavorClassGroup1 > -1)
						{
							if(CategoryManager.isInCategory(FavorClassGroup1, c0.getActiveClass()))
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
							f0 = i1 * (f0 + 1);
						}
						_thisActor.addDamageHate(c0, 0, (long) (f0 * TUMOR_ATTACKED_Weight_Point + Attack_BoostValue));
					}
					else
					{
						float f0 = DefaultHate;
						if(HateClassGroup1 > -1)
						{
							if(CategoryManager.isInCategory(HateClassGroup1, c0.getActiveClass()))
							{
								f0 += HateClassGroup1Boost;
							}
						}
						if(HateClassGroup2 > -1)
						{
							if(CategoryManager.isInCategory(HateClassGroup2, c0.getActiveClass()))
							{
								f0 += HateClassGroup2Boost;
							}
						}
						if(FavorClassGroup1 > -1)
						{
							if(CategoryManager.isInCategory(FavorClassGroup1, c0.getActiveClass()))
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
							f0 = i1 * (f0 + 1);
						}
						_thisActor.addDamageHate(c0, 0, (long) (f0 * TUMOR_ATTACKED_Weight_Point));
					}
				}
				if(CategoryManager.isInCategory(12, c0.getNpcId()))
				{
					float f0 = DefaultHate;
					if(HateClassGroup1 > -1)
					{
						if(CategoryManager.isInCategory(HateClassGroup1, c0.getPlayer().getActiveClass()))
						{
							f0 += HateClassGroup1Boost;
						}
					}
					if(HateClassGroup2 > -1)
					{
						if(CategoryManager.isInCategory(HateClassGroup2, c0.getPlayer().getActiveClass()))
						{
							f0 += HateClassGroup2Boost;
						}
					}
					if(FavorClassGroup1 > -1)
					{
						if(CategoryManager.isInCategory(FavorClassGroup1, c0.getPlayer().getActiveClass()))
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
						f0 = i1 * (f0 + 1);
					}
					_thisActor.addDamageHate(c0.getPlayer(), 0, (long) (f0 * TUMOR_ATTACKED_Weight_Point));
				}
			}
		}
		else if(eventId == 78010058 && (Long) arg1 != 0 && (Long) arg1 != 1 && _thisActor.i_quest0 == 0)
		{
			long i1 = (Long) arg2 * 100;
			L2Character c0 = L2ObjectsStorage.getAsCharacter((Long) arg1);
			if(c0 != null)
			{
				if(c0.isPlayer() || CategoryManager.isInCategory(12, c0.getNpcId()))
				{
					if(_thisActor.getAggroListSize() == 0)
					{
						float f0 = DefaultHate;
						if(HateClassGroup1 > -1)
						{
							if(CategoryManager.isInCategory(HateClassGroup1, c0.getActiveClass()))
							{
								f0 += HateClassGroup1Boost;
							}
						}
						if(HateClassGroup2 > -1)
						{
							if(CategoryManager.isInCategory(HateClassGroup2, c0.getActiveClass()))
							{
								f0 += HateClassGroup2Boost;
							}
						}
						if(FavorClassGroup1 > -1)
						{
							if(CategoryManager.isInCategory(FavorClassGroup1, c0.getActiveClass()))
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
							f0 = i1 * (f0 + 1);
						}
						_thisActor.addDamageHate(c0, 0, (long) (f0 * VEIN_SIGNAL_Weight_Point + Attack_BoostValue));
					}
					else
					{
						float f0 = DefaultHate;
						if(HateClassGroup1 > -1)
						{
							if(CategoryManager.isInCategory(HateClassGroup1, c0.getActiveClass()))
							{
								f0 += HateClassGroup1Boost;
							}
						}
						if(HateClassGroup2 > -1)
						{
							if(CategoryManager.isInCategory(HateClassGroup2, c0.getActiveClass()))
							{
								f0 += HateClassGroup2Boost;
							}
						}
						if(FavorClassGroup1 > -1)
						{
							if(CategoryManager.isInCategory(FavorClassGroup1, c0.getActiveClass()))
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
							f0 = i1 * (f0 + 1);
						}
						_thisActor.addDamageHate(c0, 0, (long) (f0 * VEIN_SIGNAL_Weight_Point));
					}
				}
				if(CategoryManager.isInCategory(12, c0.getNpcId()))
				{
					float f0 = DefaultHate;
					if(HateClassGroup1 > -1)
					{
						if(CategoryManager.isInCategory(HateClassGroup1, c0.getPlayer().getActiveClass()))
						{
							f0 += HateClassGroup1Boost;
						}
					}
					if(HateClassGroup2 > -1)
					{
						if(CategoryManager.isInCategory(HateClassGroup2, c0.getPlayer().getActiveClass()))
						{
							f0 += HateClassGroup2Boost;
						}
					}
					if(FavorClassGroup1 > -1)
					{
						if(CategoryManager.isInCategory(FavorClassGroup1, c0.getPlayer().getActiveClass()))
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
						f0 = i1 * (f0 + 1);
					}
					_thisActor.addDamageHate(c0.getPlayer(), 0, (long) (f0 * VEIN_SIGNAL_Weight_Point));
				}
			}
		}
		else if(eventId == 78010052 && (Long) arg1 != 0 && ((Integer) arg2 == room || (Integer) arg2 == 911) &&
				!type.equals("solo_boss_melee") && !type.equals("solo_boss_caster") && !type.equals("duo_boss_melee") && !type.equals("duo_boss_caster") &&
				!type.equals("boss_marching") && !type.equals("echmus") && !type.equals("voidhound_a") && !type.equals("voidhound_d") && !type.equals("knight") &&
				!type.equals("melee") && !type.equals("caster"))
		{
			L2Character c0 = L2ObjectsStorage.getAsCharacter((Long) arg1);
			if(c0 != null)
			{
				if(Rnd.get(25) == 0)
				{
					_thisActor.removeAllHateInfoIF(0, 0);
					_thisActor.i_quest0 = 1;
				}
				else
				{
					_thisActor.removeAllHateInfoIF(1, 0);
					_thisActor.removeAllHateInfoIF(3, 600);
				}
				if(_thisActor.getAggroListSize() == 0)
				{
					if(SkillTable.isMagic(Skill_Siege) == 0 && (SkillTable.getAbnormalLevel(_thisActor, 23134209) > 0 || SkillTable.getAbnormalLevel(_thisActor, 87556097) > 0))
					{
					}
					else if(SkillTable.isMagic(Skill_Siege) == 1 && (SkillTable.getAbnormalLevel(_thisActor, 69730305) > 0 || SkillTable.getAbnormalLevel(_thisActor, 87556097) > 0))
					{
					}
					else if(Skill_Siege.getMpConsume() < _thisActor.getCurrentMp() && Skill_Siege.getHpConsume() < _thisActor.getCurrentHp() && !_thisActor.isSkillDisabled(Skill_Siege.getId()))
					{
						if(debug)
						{
							Functions.npcSay(_thisActor, Say2C.ALL, "Cast " + Skill_Siege.getId() + " skill");
						}
						addUseSkillDesire(c0, Skill_Siege, 0, 1, (long) (LIFESEED_TAUNT_Weight_Point * UseSkill_BoostValue));
					}
				}
			}
		}
		else if(eventId == 78010073 && type.equals("boss_marching"))
		{
			DefaultMaker maker0 = _thisActor.getMyMaker();
			if(maker0 != null)
			{
				maker0.onScriptEvent(78010073, 0, 0);
			}

			_thisActor.onDecay();
		}
	}

	@Override
	protected void onEvtTimer(int timerId, Object arg1, Object arg2)
	{
		if(timerId == TM_ATTACK_COOLDOWN && !type.equals("boss_marching") && !type.equals("solo_boss_melee") && !type.equals("solo_boss_caster") && !type.equals("duo_boss_melee") && !type.equals("duo_boss_caster") && !type.equals("boss_marching") && !type.equals("echmus") && !type.equals("voidhound_a") && !type.equals("voidhound_d") && !type.equals("knight") && !type.equals("melee") && !type.equals("caster"))
		{
			_thisActor.removeAllHateInfoIF(1, 0);
			_thisActor.removeAllHateInfoIF(3, 2000);
			L2Character c0 = _thisActor.getMostHated();

			if(c0 != null)
			{
				int i0 = Rnd.get(100);
				if(type.equals("tank"))
				{
					if(i0 <= Skillchance_Dim && c0.isPlayer() && (CategoryManager.isInCategory(88, c0.getActiveClass()) || CategoryManager.isInCategory(91, c0.getActiveClass()) || CategoryManager.isInCategory(93, c0.getActiveClass())))
					{
						if(SkillTable.isMagic(Skill04_ID) == 0 && (SkillTable.getAbnormalLevel(_thisActor, 23134209) > 0 || SkillTable.getAbnormalLevel(_thisActor, 87556097) > 0))
						{
						}
						else if(SkillTable.isMagic(Skill04_ID) == 1 && (SkillTable.getAbnormalLevel(_thisActor, 69730305) > 0 || SkillTable.getAbnormalLevel(_thisActor, 87556097) > 0))
						{
						}
						else if(Skill04_ID.getMpConsume() < _thisActor.getCurrentMp() && Skill04_ID.getHpConsume() < _thisActor.getCurrentHp() && !_thisActor.isSkillDisabled(Skill04_ID.getId()))
						{
							if(debug)
							{
								Functions.npcSay(_thisActor, Say2C.ALL, "Cast " + Skill04_ID.getId() + " skill");
							}
							addUseSkillDesire(c0, Skill04_ID, 0, 1, (long) (_thisActor.getHate(c0) * UseSkill_BoostValue));
						}
					}
					else if(i0 <= Skillchance_Low)
					{
						if(SkillTable.isMagic(Skill01_ID) == 0 && (SkillTable.getAbnormalLevel(_thisActor, 23134209) > 0 || SkillTable.getAbnormalLevel(_thisActor, 87556097) > 0))
						{
						}
						else if(SkillTable.isMagic(Skill01_ID) == 1 && (SkillTable.getAbnormalLevel(_thisActor, 69730305) > 0 || SkillTable.getAbnormalLevel(_thisActor, 87556097) > 0))
						{
						}
						else if(Skill01_ID.getMpConsume() < _thisActor.getCurrentMp() && Skill01_ID.getHpConsume() < _thisActor.getCurrentHp() && !_thisActor.isSkillDisabled(Skill01_ID.getId()))
						{
							if(debug)
							{
								Functions.npcSay(_thisActor, Say2C.ALL, "Cast " + Skill01_ID.getId() + " skill");
							}
							addUseSkillDesire(c0, Skill01_ID, 0, 1, (long) (_thisActor.getHate(c0) * UseSkill_BoostValue));
						}
					}
					else if(i0 <= Skillchance_High)
					{
						if(!_thisActor.isInRange(c0, 300))
						{
							if(SkillTable.isMagic(Skill02_ID) == 0 && (SkillTable.getAbnormalLevel(_thisActor, 23134209) > 0 || SkillTable.getAbnormalLevel(_thisActor, 87556097) > 0))
							{
							}
							else if(SkillTable.isMagic(Skill02_ID) == 1 && (SkillTable.getAbnormalLevel(_thisActor, 69730305) > 0 || SkillTable.getAbnormalLevel(_thisActor, 87556097) > 0))
							{
							}
							else if(Skill02_ID.getMpConsume() < _thisActor.getCurrentMp() && Skill02_ID.getHpConsume() < _thisActor.getCurrentHp() && !_thisActor.isSkillDisabled(Skill02_ID.getId()))
							{
								if(debug)
								{
									Functions.npcSay(_thisActor, Say2C.ALL, "Cast " + Skill02_ID.getId() + " skill");
								}
								addUseSkillDesire(c0, Skill02_ID, 0, 1, (long) (_thisActor.getHate(c0) * UseSkill_BoostValue));
							}
						}
						if(SkillTable.isMagic(Skill03_ID) == 0 && (SkillTable.getAbnormalLevel(_thisActor, 23134209) > 0 || SkillTable.getAbnormalLevel(_thisActor, 87556097) > 0))
						{
						}
						else if(SkillTable.isMagic(Skill03_ID) == 1 && (SkillTable.getAbnormalLevel(_thisActor, 69730305) > 0 || SkillTable.getAbnormalLevel(_thisActor, 87556097) > 0))
						{
						}
						else if(Skill03_ID.getMpConsume() < _thisActor.getCurrentMp() && Skill03_ID.getHpConsume() < _thisActor.getCurrentHp() && !_thisActor.isSkillDisabled(Skill03_ID.getId()))
						{
							if(debug)
							{
								Functions.npcSay(_thisActor, Say2C.ALL, "Cast " + Skill03_ID.getId() + " skill");
							}
							addUseSkillDesire(c0, Skill03_ID, 0, 1, (long) (_thisActor.getHate(c0) * UseSkill_BoostValue));
						}
					}
					else
					{
						addAttackDesire(c0, 1, DEFAULT_DESIRE);
					}
				}
				else if(type.equals("charger"))
				{
					if(i0 <= Skillchance_Low)
					{
						if(!_thisActor.isInRange(c0, 300))
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
									Functions.npcSay(_thisActor, Say2C.ALL, "Magic block");
								}
							}
							else if(Skill02_ID.getMpConsume() < _thisActor.getCurrentMp() && Skill02_ID.getHpConsume() < _thisActor.getCurrentHp() && !_thisActor.isSkillDisabled(Skill02_ID.getId()))
							{
								if(debug)
								{
									Functions.npcSay(_thisActor, Say2C.ALL, "Cast " + Skill02_ID.getId() + " skill");
								}
								addUseSkillDesire(c0, Skill02_ID, 0, 1, (long) (_thisActor.getHate(c0) * UseSkill_BoostValue));
							}
							else if(debug)
							{
								Functions.npcSay(_thisActor, Say2C.ALL, "No skill condition");
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
								Functions.npcSay(_thisActor, Say2C.ALL, "Magic block");
							}
						}
						else if(Skill01_ID.getMpConsume() < _thisActor.getCurrentMp() && Skill01_ID.getHpConsume() < _thisActor.getCurrentHp() && !_thisActor.isSkillDisabled(Skill01_ID.getId()))
						{
							if(debug)
							{
								Functions.npcSay(_thisActor, Say2C.ALL, "Cast " + Skill01_ID.getId() + " skill");
							}
							addUseSkillDesire(c0, Skill01_ID, 0, 1, (long) (_thisActor.getHate(c0) * UseSkill_BoostValue));
						}
						else if(debug)
						{
							Functions.npcSay(_thisActor, Say2C.ALL, "No skill condition");
						}
					}
					else if(i0 <= Skillchance_High)
					{
						if(c0.isPlayer() && (CategoryManager.isInCategory(88, c0.getActiveClass()) || CategoryManager.isInCategory(91, c0.getActiveClass()) || CategoryManager.isInCategory(93, c0.getActiveClass())))
						{
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
									Functions.npcSay(_thisActor, Say2C.ALL, "Magic block");
								}
							}
							else if(Skill04_ID.getMpConsume() < _thisActor.getCurrentMp() && Skill04_ID.getHpConsume() < _thisActor.getCurrentHp() && !_thisActor.isSkillDisabled(Skill04_ID.getId()))
							{
								if(debug)
								{
									Functions.npcSay(_thisActor, Say2C.ALL, "Cast " + Skill04_ID.getId() + " skill");
								}
								addUseSkillDesire(c0, Skill04_ID, 0, 1, (long) (_thisActor.getHate(c0) * UseSkill_BoostValue));
							}
							else if(debug)
							{
								Functions.npcSay(_thisActor, Say2C.ALL, "No skill condition");
							}
						}
						else if(SkillTable.isMagic(Skill03_ID) == 0 && (SkillTable.getAbnormalLevel(_thisActor, 23134209) > 0 || SkillTable.getAbnormalLevel(_thisActor, 87556097) > 0))
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
								Functions.npcSay(_thisActor, Say2C.ALL, "Magic block");
							}
						}
						else if(Skill03_ID.getMpConsume() < _thisActor.getCurrentMp() && Skill03_ID.getHpConsume() < _thisActor.getCurrentHp() && !_thisActor.isSkillDisabled(Skill03_ID.getId()))
						{
							if(debug)
							{
								Functions.npcSay(_thisActor, Say2C.ALL, "Cast " + Skill03_ID.getId() + " skill");
							}
							addUseSkillDesire(c0, Skill03_ID, 0, 1, (long) (_thisActor.getHate(c0) * UseSkill_BoostValue));
						}
						else if(debug)
						{
							Functions.npcSay(_thisActor, Say2C.ALL, "No skill condition");
						}
					}
					else
					{
						addAttackDesire(c0, 1, DEFAULT_DESIRE);
					}
				}
				else if(type.equals("dealer"))
				{
					if(i0 <= Skillchance_Dim && c0.getMaxHp() * 0.300000 <= c0.getCurrentHp())
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
								Functions.npcSay(_thisActor, Say2C.ALL, "Magic block");
							}
						}
						else if(Skill02_ID.getMpConsume() < _thisActor.getCurrentMp() && Skill02_ID.getHpConsume() < _thisActor.getCurrentHp() && !_thisActor.isSkillDisabled(Skill02_ID.getId()))
						{
							if(debug)
							{
								Functions.npcSay(_thisActor, Say2C.ALL, "Cast " + Skill02_ID.getId() + " skill");
							}
							addUseSkillDesire(c0, Skill02_ID, 0, 1, (long) (_thisActor.getHate(c0) * UseSkill_BoostValue));
						}
						else if(debug)
						{
							Functions.npcSay(_thisActor, Say2C.ALL, "No skill condition");
						}
					}
					else if(i0 <= Skillchance_Low)
					{
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
								Functions.npcSay(_thisActor, Say2C.ALL, "Magic block");
							}
						}
						else if(Skill03_ID.getMpConsume() < _thisActor.getCurrentMp() && Skill03_ID.getHpConsume() < _thisActor.getCurrentHp() && !_thisActor.isSkillDisabled(Skill03_ID.getId()))
						{
							if(debug)
							{
								Functions.npcSay(_thisActor, Say2C.ALL, "Cast " + Skill03_ID.getId() + " skill");
							}
							addUseSkillDesire(c0, Skill03_ID, 0, 1, (long) (_thisActor.getHate(c0) * UseSkill_BoostValue));
						}
						else if(debug)
						{
							Functions.npcSay(_thisActor, Say2C.ALL, "No skill condition");
						}
					}
					else if(i0 <= Skillchance_High)
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
								Functions.npcSay(_thisActor, Say2C.ALL, "Magic block");
							}
						}
						else if(Skill01_ID.getMpConsume() < _thisActor.getCurrentMp() && Skill01_ID.getHpConsume() < _thisActor.getCurrentHp() && !_thisActor.isSkillDisabled(Skill01_ID.getId()))
						{
							if(debug)
							{
								Functions.npcSay(_thisActor, Say2C.ALL, "Cast " + Skill01_ID.getId() + " skill");
							}
							addUseSkillDesire(c0, Skill01_ID, 0, 1, (long) (_thisActor.getHate(c0) * UseSkill_BoostValue));
						}
						else if(debug)
						{
							Functions.npcSay(_thisActor, Say2C.ALL, "No skill condition");
						}
					}
					else
					{
						addAttackDesire(c0, 1, DEFAULT_DESIRE);
					}
				}
				else if(type.equals("debuffer"))
				{
					if(i0 <= Skillchance_Dim && c0.isPlayer() && !CategoryManager.isInCategory(84, c0.getActiveClass()) && !CategoryManager.isInCategory(86, c0.getActiveClass()) && !CategoryManager.isInCategory(87, c0.getActiveClass()))
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
								Functions.npcSay(_thisActor, Say2C.ALL, "Magic block");
							}
						}
						else if(Skill02_ID.getMpConsume() < _thisActor.getCurrentMp() && Skill02_ID.getHpConsume() < _thisActor.getCurrentHp() && !_thisActor.isSkillDisabled(Skill02_ID.getId()))
						{
							if(debug)
							{
								Functions.npcSay(_thisActor, Say2C.ALL, "Cast " + Skill02_ID.getId() + " skill");
							}
							addUseSkillDesire(c0, Skill02_ID, 0, 1, (long) (_thisActor.getHate(c0) * UseSkill_BoostValue));
						}
						else if(debug)
						{
							Functions.npcSay(_thisActor, Say2C.ALL, "No skill condition");
						}
					}
					else if(i0 <= Skillchance_Low)
					{
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
								Functions.npcSay(_thisActor, Say2C.ALL, "Magic block");
							}
						}
						else if(Skill03_ID.getMpConsume() < _thisActor.getCurrentMp() && Skill03_ID.getHpConsume() < _thisActor.getCurrentHp() && !_thisActor.isSkillDisabled(Skill03_ID.getId()))
						{
							if(debug)
							{
								Functions.npcSay(_thisActor, Say2C.ALL, "Cast " + Skill03_ID.getId() + " skill");
							}
							addUseSkillDesire(c0, Skill03_ID, 0, 1, (long) (_thisActor.getHate(c0) * UseSkill_BoostValue));
						}
						else if(debug)
						{
							Functions.npcSay(_thisActor, Say2C.ALL, "No skill condition");
						}
					}
					else if(i0 <= Skillchance_High)
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
								Functions.npcSay(_thisActor, Say2C.ALL, "Magic block");
							}
						}
						else if(Skill01_ID.getMpConsume() < _thisActor.getCurrentMp() && Skill01_ID.getHpConsume() < _thisActor.getCurrentHp() && !_thisActor.isSkillDisabled(Skill01_ID.getId()))
						{
							if(debug)
							{
								Functions.npcSay(_thisActor, Say2C.ALL, "Cast " + Skill01_ID.getId() + " skill");
							}
							addUseSkillDesire(c0, Skill01_ID, 0, 1, (long) (_thisActor.getHate(c0) * UseSkill_BoostValue));
						}
						else if(debug)
						{
							Functions.npcSay(_thisActor, Say2C.ALL, "No skill condition");
						}
					}
					else
					{
						addAttackDesire(c0, 1, DEFAULT_DESIRE);
					}
				}
				else if(type.equals("dot"))
				{
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
								Functions.npcSay(_thisActor, Say2C.ALL, "Magic block");
							}
						}
						else if(Skill02_ID.getMpConsume() < _thisActor.getCurrentMp() && Skill02_ID.getHpConsume() < _thisActor.getCurrentHp() && !_thisActor.isSkillDisabled(Skill02_ID.getId()))
						{
							if(debug)
							{
								Functions.npcSay(_thisActor, Say2C.ALL, "Cast " + Skill02_ID.getId() + " skill");
							}
							addUseSkillDesire(c0, Skill02_ID, 0, 1, (long) (_thisActor.getHate(c0) * UseSkill_BoostValue));
						}
						else if(debug)
						{
							Functions.npcSay(_thisActor, Say2C.ALL, "No skill condition");
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
							Functions.npcSay(_thisActor, Say2C.ALL, "Magic block");
						}
					}
					else if(Skill01_ID.getMpConsume() < _thisActor.getCurrentMp() && Skill01_ID.getHpConsume() < _thisActor.getCurrentHp() && !_thisActor.isSkillDisabled(Skill01_ID.getId()))
					{
						if(debug)
						{
							Functions.npcSay(_thisActor, Say2C.ALL, "Cast " + Skill01_ID.getId() + " skill");
						}
						addUseSkillDesire(c0, Skill01_ID, 0, 1, (long) (_thisActor.getHate(c0) * UseSkill_BoostValue));
					}
					else if(debug)
					{
						Functions.npcSay(_thisActor, Say2C.ALL, "No skill condition");
					}
				}
				else if(type.equals("cc"))
				{
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
								Functions.npcSay(_thisActor, Say2C.ALL, "Magic block");
							}
						}
						else if(Skill02_ID.getMpConsume() < _thisActor.getCurrentMp() && Skill02_ID.getHpConsume() < _thisActor.getCurrentHp() && !_thisActor.isSkillDisabled(Skill02_ID.getId()))
						{
							if(debug)
							{
								Functions.npcSay(_thisActor, Say2C.ALL, "Cast " + Skill02_ID.getId() + " skill");
							}
							addUseSkillDesire(c0, Skill02_ID, 0, 1, (long) (_thisActor.getHate(c0) * UseSkill_BoostValue));
						}
						else if(debug)
						{
							Functions.npcSay(_thisActor, Say2C.ALL, "No skill condition");
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
							Functions.npcSay(_thisActor, Say2C.ALL, "Magic block");
						}
					}
					else if(Skill01_ID.getMpConsume() < _thisActor.getCurrentMp() && Skill01_ID.getHpConsume() < _thisActor.getCurrentHp() && !_thisActor.isSkillDisabled(Skill01_ID.getId()))
					{
						if(debug)
						{
							Functions.npcSay(_thisActor, Say2C.ALL, "Cast " + Skill01_ID.getId() + " skill");
						}
						addUseSkillDesire(c0, Skill01_ID, 0, 1, (long) (_thisActor.getHate(c0) * UseSkill_BoostValue));
					}
					else if(debug)
					{
						Functions.npcSay(_thisActor, Say2C.ALL, "No skill condition");
					}
				}
				else if(type.equals("con"))
				{
					if(c0.isPlayer() && (!CategoryManager.isInCategory(85, c0.getActiveClass()) || !CategoryManager.isInCategory(92, c0.getActiveClass()) || !CategoryManager.isInCategory(90, c0.getActiveClass())))
					{
						if(i0 <= Skillchance_Dim)
						{
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
									Functions.npcSay(_thisActor, Say2C.ALL, "Magic block");
								}
							}
							else if(Skill03_ID.getMpConsume() < _thisActor.getCurrentMp() && Skill03_ID.getHpConsume() < _thisActor.getCurrentHp() && !_thisActor.isSkillDisabled(Skill03_ID.getId()))
							{
								if(debug)
								{
									Functions.npcSay(_thisActor, Say2C.ALL, "Cast " + Skill03_ID.getId() + " skill");
								}
								addUseSkillDesire(c0, Skill03_ID, 0, 1, (long) (_thisActor.getHate(c0) * UseSkill_BoostValue));
							}
							else if(debug)
							{
								Functions.npcSay(_thisActor, Say2C.ALL, "No skill condition");
							}
						}
						else if(i0 <= Skillchance_Low)
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
									Functions.npcSay(_thisActor, Say2C.ALL, "Magic block");
								}
							}
							else if(Skill02_ID.getMpConsume() < _thisActor.getCurrentMp() && Skill02_ID.getHpConsume() < _thisActor.getCurrentHp() && !_thisActor.isSkillDisabled(Skill02_ID.getId()))
							{
								if(debug)
								{
									Functions.npcSay(_thisActor, Say2C.ALL, "Cast " + Skill02_ID.getId() + " skill");
								}
								addUseSkillDesire(c0, Skill02_ID, 0, 1, (long) (_thisActor.getHate(c0) * UseSkill_BoostValue));
							}
							else if(debug)
							{
								Functions.npcSay(_thisActor, Say2C.ALL, "No skill condition");
							}
						}
						else if(i0 <= 30)
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
									Functions.npcSay(_thisActor, Say2C.ALL, "Magic block");
								}
							}
							else if(Skill01_ID.getMpConsume() < _thisActor.getCurrentMp() && Skill01_ID.getHpConsume() < _thisActor.getCurrentHp() && !_thisActor.isSkillDisabled(Skill01_ID.getId()))
							{
								if(debug)
								{
									Functions.npcSay(_thisActor, Say2C.ALL, "Cast " + Skill01_ID.getId() + " skill");
								}
								addUseSkillDesire(c0, Skill01_ID, 0, 1, (long) (_thisActor.getHate(c0) * UseSkill_BoostValue));
							}
							else if(debug)
							{
								Functions.npcSay(_thisActor, Say2C.ALL, "No skill condition");
							}
						}
						else
						{
							addAttackDesire(c0, 1, DEFAULT_DESIRE);
						}
					}
				}
				else if(type.equals("td"))
				{
					if(i0 <= Skillchance_Dim && c0.isPlayer() && (CategoryManager.isInCategory(88, c0.getActiveClass()) || CategoryManager.isInCategory(91, c0.getActiveClass()) || CategoryManager.isInCategory(93, c0.getActiveClass())))
					{
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
								Functions.npcSay(_thisActor, Say2C.ALL, "Magic block");
							}
						}
						else if(Skill03_ID.getMpConsume() < _thisActor.getCurrentMp() && Skill03_ID.getHpConsume() < _thisActor.getCurrentHp() && !_thisActor.isSkillDisabled(Skill03_ID.getId()))
						{
							if(debug)
							{
								Functions.npcSay(_thisActor, Say2C.ALL, "Cast " + Skill03_ID.getId() + " skill");
							}
							addUseSkillDesire(c0, Skill03_ID, 0, 1, (long) (_thisActor.getHate(c0) * UseSkill_BoostValue));
						}
						else if(debug)
						{
							Functions.npcSay(_thisActor, Say2C.ALL, "No skill condition");
						}
					}
					else if(i0 <= Skillchance_Low)
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
								Functions.npcSay(_thisActor, Say2C.ALL, "Magic block");
							}
						}
						else if(Skill01_ID.getMpConsume() < _thisActor.getCurrentMp() && Skill01_ID.getHpConsume() < _thisActor.getCurrentHp() && !_thisActor.isSkillDisabled(Skill01_ID.getId()))
						{
							if(debug)
							{
								Functions.npcSay(_thisActor, Say2C.ALL, "Cast " + Skill01_ID.getId() + " skill");
							}
							addUseSkillDesire(c0, Skill01_ID, 0, 1, (long) (_thisActor.getHate(c0) * UseSkill_BoostValue));
						}
						else if(debug)
						{
							Functions.npcSay(_thisActor, Say2C.ALL, "No skill condition");
						}
					}
					else if(i0 <= Skillchance_High)
					{
						if(Skill02_ID.getIndex() == 386531329 && _thisActor.isInRange(c0, 300))
						{
							addAttackDesire(c0, 1, DEFAULT_DESIRE);
						}
						else if(SkillTable.isMagic(Skill02_ID) == 0 && (SkillTable.getAbnormalLevel(_thisActor, 23134209) > 0 || SkillTable.getAbnormalLevel(_thisActor, 87556097) > 0))
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
								Functions.npcSay(_thisActor, Say2C.ALL, "Magic block");
							}
						}
						else if(Skill02_ID.getMpConsume() < _thisActor.getCurrentMp() && Skill02_ID.getHpConsume() < _thisActor.getCurrentHp() && !_thisActor.isSkillDisabled(Skill02_ID.getId()))
						{
							if(debug)
							{
								Functions.npcSay(_thisActor, Say2C.ALL, "Cast " + Skill02_ID.getId() + " skill");
							}
							addUseSkillDesire(c0, Skill02_ID, 0, 1, (long) (_thisActor.getHate(c0) * UseSkill_BoostValue));
						}
						else if(debug)
						{
							Functions.npcSay(_thisActor, Say2C.ALL, "No skill condition");
						}
					}
					else
					{
						addAttackDesire(c0, 1, DEFAULT_DESIRE);
					}
				}
				else if(type.equals("ambush_td_kamikaze"))
				{
					if(i0 <= Skillchance_Low)
					{
						if(c0.isPlayer() && (CategoryManager.isInCategory(88, c0.getActiveClass()) || CategoryManager.isInCategory(91, c0.getActiveClass()) || CategoryManager.isInCategory(93, c0.getActiveClass())))
						{
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
									Functions.npcSay(_thisActor, Say2C.ALL, "Magic block");
								}
							}
							else if(Skill03_ID.getMpConsume() < _thisActor.getCurrentMp() && Skill03_ID.getHpConsume() < _thisActor.getCurrentHp() && !_thisActor.isSkillDisabled(Skill03_ID.getId()))
							{
								if(debug)
								{
									Functions.npcSay(_thisActor, Say2C.ALL, "Cast " + Skill03_ID.getId() + " skill");
								}
								addUseSkillDesire(c0, Skill03_ID, 0, 1, (long) (_thisActor.getHate(c0) * UseSkill_BoostValue));
							}
							else if(debug)
							{
								Functions.npcSay(_thisActor, Say2C.ALL, "No skill condition");
							}
						}
						else if(SkillTable.isMagic(Skill02_ID) == 0 && (SkillTable.getAbnormalLevel(_thisActor, 23134209) > 0 || SkillTable.getAbnormalLevel(_thisActor, 87556097) > 0))
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
								Functions.npcSay(_thisActor, Say2C.ALL, "Magic block");
							}
						}
						else if(Skill02_ID.getMpConsume() < _thisActor.getCurrentMp() && Skill02_ID.getHpConsume() < _thisActor.getCurrentHp() && !_thisActor.isSkillDisabled(Skill02_ID.getId()))
						{
							if(debug)
							{
								Functions.npcSay(_thisActor, Say2C.ALL, "Cast " + Skill02_ID.getId() + " skill");
							}
							addUseSkillDesire(c0, Skill02_ID, 0, 1, (long) (_thisActor.getHate(c0) * UseSkill_BoostValue));
						}
						else if(debug)
						{
							Functions.npcSay(_thisActor, Say2C.ALL, "No skill condition");
						}
					}
					else if(i0 <= Skillchance_High)
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
								Functions.npcSay(_thisActor, Say2C.ALL, "Magic block");
							}
						}
						else if(Skill01_ID.getMpConsume() < _thisActor.getCurrentMp() && Skill01_ID.getHpConsume() < _thisActor.getCurrentHp() && !_thisActor.isSkillDisabled(Skill01_ID.getId()))
						{
							if(debug)
							{
								Functions.npcSay(_thisActor, Say2C.ALL, "Cast " + Skill01_ID.getId() + " skill");
							}
							addUseSkillDesire(c0, Skill01_ID, 0, 1, (long) (_thisActor.getHate(c0) * UseSkill_BoostValue));
						}
						else if(debug)
						{
							Functions.npcSay(_thisActor, Say2C.ALL, "No skill condition");
						}
					}
					else
					{
						addAttackDesire(c0, 1, DEFAULT_DESIRE);
					}
				}
				else if(type.equals("ambush_dc_kamikaze"))
				{
					if(i0 <= Skillchance_Dim && Skill03_ID != null)
					{
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
								Functions.npcSay(_thisActor, Say2C.ALL, "Magic block");
							}
						}
						else if(Skill03_ID.getMpConsume() < _thisActor.getCurrentMp() && Skill03_ID.getHpConsume() < _thisActor.getCurrentHp() && !_thisActor.isSkillDisabled(Skill03_ID.getId()))
						{
							if(debug)
							{
								Functions.npcSay(_thisActor, Say2C.ALL, "Cast " + Skill03_ID.getId() + " skill");
							}
							addUseSkillDesire(c0, Skill03_ID, 0, 1, (long) (_thisActor.getHate(c0) * UseSkill_BoostValue));
						}
						else if(debug)
						{
							Functions.npcSay(_thisActor, Say2C.ALL, "No skill condition");
						}
					}
					else if(i0 <= Skillchance_Low && Skill02_ID.getIndex() == 386924545 && _thisActor.getLoc().distance3D(c0.getLoc()) >= 150)
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
								Functions.npcSay(_thisActor, Say2C.ALL, "Magic block");
							}
						}
						else if(Skill02_ID.getMpConsume() < _thisActor.getCurrentMp() && Skill02_ID.getHpConsume() < _thisActor.getCurrentHp() && !_thisActor.isSkillDisabled(Skill02_ID.getId()))
						{
							if(debug)
							{
								Functions.npcSay(_thisActor, Say2C.ALL, "Cast " + Skill02_ID.getId() + " skill");
							}
							addUseSkillDesire(c0, Skill02_ID, 0, 1, (long) (_thisActor.getHate(c0) * UseSkill_BoostValue));
						}
						else if(debug)
						{
							Functions.npcSay(_thisActor, Say2C.ALL, "No skill condition");
						}
					}
					else if(i0 <= Skillchance_Low && Skill02_ID.getIndex() == 385286145)
					{
						if(SkillTable.getAbnormalLevel(_thisActor, Skill01_ID) > 0)
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
									Functions.npcSay(_thisActor, Say2C.ALL, "Magic block");
								}
							}
							else if(Skill02_ID.getMpConsume() < _thisActor.getCurrentMp() && Skill02_ID.getHpConsume() < _thisActor.getCurrentHp() && !_thisActor.isSkillDisabled(Skill02_ID.getId()))
							{
								if(debug)
								{
									Functions.npcSay(_thisActor, Say2C.ALL, "Cast " + Skill02_ID.getId() + " skill");
								}
								addUseSkillDesire(c0, Skill02_ID, 0, 1, (long) (_thisActor.getHate(c0) * UseSkill_BoostValue));
							}
							else if(debug)
							{
								Functions.npcSay(_thisActor, Say2C.ALL, "No skill condition");
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
								Functions.npcSay(_thisActor, Say2C.ALL, "Magic block");
							}
						}
						else if(Skill01_ID.getMpConsume() < _thisActor.getCurrentMp() && Skill01_ID.getHpConsume() < _thisActor.getCurrentHp() && !_thisActor.isSkillDisabled(Skill01_ID.getId()))
						{
							if(debug)
							{
								Functions.npcSay(_thisActor, Say2C.ALL, "Cast " + Skill01_ID.getId() + " skill");
							}
							addUseSkillDesire(c0, Skill01_ID, 0, 1, (long) (_thisActor.getHate(c0) * UseSkill_BoostValue));
						}
						else if(debug)
						{
							Functions.npcSay(_thisActor, Say2C.ALL, "No skill condition");
						}
					}
					else if(Skill01_ID.getIndex() == 386465793)
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
								Functions.npcSay(_thisActor, Say2C.ALL, "Magic block");
							}
						}
						else if(Skill01_ID.getMpConsume() < _thisActor.getCurrentMp() && Skill01_ID.getHpConsume() < _thisActor.getCurrentHp() && !_thisActor.isSkillDisabled(Skill01_ID.getId()))
						{
							if(debug)
							{
								Functions.npcSay(_thisActor, Say2C.ALL, "Cast " + Skill01_ID.getId() + " skill");
							}
							addUseSkillDesire(c0, Skill01_ID, 0, 1, (long) (_thisActor.getHate(c0) * UseSkill_BoostValue));
						}
						else if(debug)
						{
							Functions.npcSay(_thisActor, Say2C.ALL, "No skill condition");
						}
					}
					else
					{
						addAttackDesire(c0, 1, DEFAULT_DESIRE);
					}
				}
				else if(type.equals("spc_wagon"))
				{
					if(i0 <= Skillchance_High)
					{
						if(!_thisActor.isInRange(c0, 300))
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
									Functions.npcSay(_thisActor, Say2C.ALL, "Magic block");
								}
							}
							else if(Skill02_ID.getMpConsume() < _thisActor.getCurrentMp() && Skill02_ID.getHpConsume() < _thisActor.getCurrentHp() && !_thisActor.isSkillDisabled(Skill02_ID.getId()))
							{
								if(debug)
								{
									Functions.npcSay(_thisActor, Say2C.ALL, "Cast " + Skill02_ID.getId() + " skill");
								}
								addUseSkillDesire(c0, Skill02_ID, 0, 1, (long) (_thisActor.getHate(c0) * UseSkill_BoostValue));
							}
							else if(debug)
							{
								Functions.npcSay(_thisActor, Say2C.ALL, "No skill condition");
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
								Functions.npcSay(_thisActor, Say2C.ALL, "Magic block");
							}
						}
						else if(Skill01_ID.getMpConsume() < _thisActor.getCurrentMp() && Skill01_ID.getHpConsume() < _thisActor.getCurrentHp() && !_thisActor.isSkillDisabled(Skill01_ID.getId()))
						{
							if(debug)
							{
								Functions.npcSay(_thisActor, Say2C.ALL, "Cast " + Skill01_ID.getId() + " skill");
							}
							addUseSkillDesire(c0, Skill01_ID, 0, 1, (long) (_thisActor.getHate(c0) * UseSkill_BoostValue));
						}
						else if(debug)
						{
							Functions.npcSay(_thisActor, Say2C.ALL, "No skill condition");
						}
					}
					else
					{
						addAttackDesire(c0, 1, DEFAULT_DESIRE);
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
									Functions.npcSay(_thisActor, Say2C.ALL, "Magic block");
								}
							}
							else if(Skill01_ID.getMpConsume() < _thisActor.getCurrentMp() && Skill01_ID.getHpConsume() < _thisActor.getCurrentHp() && !_thisActor.isSkillDisabled(Skill01_ID.getId()))
							{
								if(debug)
								{
									Functions.npcSay(_thisActor, Say2C.ALL, "Cast " + Skill01_ID.getId() + " skill");
								}
								addUseSkillDesire(c0, Skill01_ID, 0, 1, (long) (100 * UseSkill_BoostValue));
							}
							else if(debug)
							{
								Functions.npcSay(_thisActor, Say2C.ALL, "No skill condition");
							}
						}
						else
						{
							addAttackDesire(c0, 1, DEFAULT_DESIRE);
						}
					}
				}
			}
			else if(_thisActor.getAggroListSize() != 0 && (_intention == CtrlIntention.AI_INTENTION_ACTIVE))
			{
				c0 = _thisActor.getMostHated();
				if(c0 != null && _thisActor.getHate(c0) > 0)
				{
					addAttackDesire(c0, 1, DEFAULT_DESIRE);
				}
			}
			addTimer(TM_ATTACK_COOLDOWN, (TIME_ATTACK_COOLDOWN_MELEE + Rnd.get(TIME_ATTACK_COOLDOWN_MELEE)) * 1000);
		}
		if(timerId == TM_REDEPLOY && !type.equals("solo_boss_melee") && !type.equals("solo_boss_caster") && !type.equals("duo_boss_melee") && !type.equals("duo_boss_caster") && !type.equals("boss_marching") && !type.equals("echmus") && !type.equals("voidhound_a") && !type.equals("voidhound_d") && !type.equals("knight") && !type.equals("melee") && !type.equals("caster"))
		{
			if(_intention == CtrlIntention.AI_INTENTION_ACTIVE)
			{
				if(debug)
				{
					Functions.npcSay(_thisActor, Say2C.ALL, "Teleport");
				}
				_thisActor.onDecay();
			}
			else
			{
				addTimer(TM_REDEPLOY, TIME_REDEPLOY * 1000);
			}
		}
	}

	@Override
	protected void onEvtFinishCasting(L2Skill skill)
	{
		L2Character target = _thisActor.getCastingTarget();
		if(target == null || skill == null)
			return;

		if(debug)
		{
			Functions.npcSay(_thisActor, Say2C.ALL, target.getName() + "Cast " + skill.getId() + " skill");
		}
		if(skill == Skill_Siege && _thisActor.getAggroListSize() == 0 && _thisActor.i_quest0 == 1)
		{
			if(SkillTable.isMagic(Skill_Siege) == 0 && (SkillTable.getAbnormalLevel(_thisActor, 23134209) > 0 || SkillTable.getAbnormalLevel(_thisActor, 87556097) > 0))
			{
				if(debug)
				{
					Functions.npcSay(_thisActor, Say2C.ALL, "Phys defence");
				}
			}
			else if(SkillTable.isMagic(Skill_Siege) == 1 && (SkillTable.getAbnormalLevel(_thisActor, 69730305) > 0 || SkillTable.getAbnormalLevel(_thisActor, 87556097) > 0))
			{
				if(debug)
				{
					Functions.npcSay(_thisActor, Say2C.ALL, "Magic block");
				}
			}
			else if(Skill_Siege.getMpConsume() < _thisActor.getCurrentMp() && Skill_Siege.getHpConsume() < _thisActor.getCurrentHp() && !_thisActor.isSkillDisabled(Skill_Siege.getId()))
			{
				if(debug)
				{
					Functions.npcSay(_thisActor, Say2C.ALL, "Cast " + Skill_Siege.getId() + " skill");
				}
				addUseSkillDesire(target, Skill_Siege, 0, 1, (long) (LIFESEED_TAUNT_Weight_Point * UseSkill_BoostValue));
			}
			else if(debug)
			{
				Functions.npcSay(_thisActor, Say2C.ALL, "No skill condition");
			}
			if(Rnd.get(25) == 0)
			{
				_thisActor.i_quest0 = 0;
			}
		}

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
								Functions.npcSay(_thisActor, Say2C.ALL, "Phys defence");
							}
						}
						else if(SkillTable.isMagic(384696323) == 1 && (SkillTable.getAbnormalLevel(_thisActor, 69730305) > 0 || SkillTable.getAbnormalLevel(_thisActor, 87556097) > 0))
						{
							if(debug)
							{
								Functions.npcSay(_thisActor, Say2C.ALL, "Magic block");
							}
						}
						else if(SkillTable.mpConsume(384696323) < _thisActor.getCurrentMp() && SkillTable.hpConsume(384696323) < _thisActor.getCurrentHp() && !_thisActor.isSkillDisabledEx(384696323))
						{
							if(debug)
							{
								Functions.npcSay(_thisActor, Say2C.ALL, "Cast " + SkillTable.getInstance().getInfo(384696323).getId() + " skill");
							}
							addUseSkillDesire(target, SkillTable.getInstance().getInfo(384696323), 0, 1, (long) (1000000 * UseSkill_BoostValue));
						}
						else if(debug)
						{
							Functions.npcSay(_thisActor, Say2C.ALL, "No skill condition");
						}
					}
					else if(target.getMaxHp() * 0.600000 >= target.getCurrentHp())
					{
						if(SkillTable.isMagic(384696322) == 0 && (SkillTable.getAbnormalLevel(_thisActor, 23134209) > 0 || SkillTable.getAbnormalLevel(_thisActor, 87556097) > 0))
						{
							if(debug)
							{
								Functions.npcSay(_thisActor, Say2C.ALL, "Phys defence");
							}
						}
						else if(SkillTable.isMagic(384696322) == 1 && (SkillTable.getAbnormalLevel(_thisActor, 69730305) > 0 || SkillTable.getAbnormalLevel(_thisActor, 87556097) > 0))
						{
							if(debug)
							{
								Functions.npcSay(_thisActor, Say2C.ALL, "Magic block");
							}
						}
						else if(SkillTable.mpConsume(384696322) < _thisActor.getCurrentMp() && SkillTable.hpConsume(384696322) < _thisActor.getCurrentHp() && !_thisActor.isSkillDisabledEx(384696322))
						{
							if(debug)
							{
								Functions.npcSay(_thisActor, Say2C.ALL, "Cast " + SkillTable.getInstance().getInfo(384696322).getId() + " skill");
							}
							addUseSkillDesire(target, SkillTable.getInstance().getInfo(384696322), 0, 1, (long) (1000000 * UseSkill_BoostValue));
						}
						else if(debug)
						{
							Functions.npcSay(_thisActor, Say2C.ALL, "No skill condition");
						}
					}
					else if(SkillTable.isMagic(384696321) == 0 && (SkillTable.getAbnormalLevel(_thisActor, 23134209) > 0 || SkillTable.getAbnormalLevel(_thisActor, 87556097) > 0))
					{
						if(debug)
						{
							Functions.npcSay(_thisActor, Say2C.ALL, "Phys defence");
						}
					}
					else if(SkillTable.isMagic(384696321) == 1 && (SkillTable.getAbnormalLevel(_thisActor, 69730305) > 0 || SkillTable.getAbnormalLevel(_thisActor, 87556097) > 0))
					{
						if(debug)
						{
							Functions.npcSay(_thisActor, Say2C.ALL, "Magic block");
						}
					}
					else if(SkillTable.mpConsume(384696321) < _thisActor.getCurrentMp() && SkillTable.hpConsume(384696321) < _thisActor.getCurrentHp() && !_thisActor.isSkillDisabledEx(384696321))
					{
						if(debug)
						{
							Functions.npcSay(_thisActor, Say2C.ALL, "Cast " + SkillTable.getInstance().getInfo(384696321).getId() + " skill");
						}
						addUseSkillDesire(target, SkillTable.getInstance().getInfo(384696321), 0, 1, (long) (1000000 * UseSkill_BoostValue));
					}
					else if(debug)
					{
						Functions.npcSay(_thisActor, Say2C.ALL, "No skill condition");
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
								Functions.npcSay(_thisActor, Say2C.ALL, "Phys defence");
							}
						}
						else if(SkillTable.isMagic(384696327) == 1 && (SkillTable.getAbnormalLevel(_thisActor, 69730305) > 0 || SkillTable.getAbnormalLevel(_thisActor, 87556097) > 0))
						{
							if(debug)
							{
								Functions.npcSay(_thisActor, Say2C.ALL, "Magic block");
							}
						}
						else if(SkillTable.mpConsume(384696327) < _thisActor.getCurrentMp() && SkillTable.hpConsume(384696327) < _thisActor.getCurrentHp() && !_thisActor.isSkillDisabledEx(384696327))
						{
							if(debug)
							{
								Functions.npcSay(_thisActor, Say2C.ALL, "Cast " + SkillTable.getInstance().getInfo(384696327).getId() + " skill");
							}
							addUseSkillDesire(target, SkillTable.getInstance().getInfo(384696327), 0, 1, (long) (1000000 * UseSkill_BoostValue));
						}
						else if(debug)
						{
							Functions.npcSay(_thisActor, Say2C.ALL, "No skill condition");
						}
					}
					else if(target.getMaxHp() * 0.600000 >= target.getCurrentHp())
					{
						if(SkillTable.isMagic(384696326) == 0 && (SkillTable.getAbnormalLevel(_thisActor, 23134209) > 0 || SkillTable.getAbnormalLevel(_thisActor, 87556097) > 0))
						{
							if(debug)
							{
								Functions.npcSay(_thisActor, Say2C.ALL, "Phys defence");
							}
						}
						else if(SkillTable.isMagic(384696326) == 1 && (SkillTable.getAbnormalLevel(_thisActor, 69730305) > 0 || SkillTable.getAbnormalLevel(_thisActor, 87556097) > 0))
						{
							if(debug)
							{
								Functions.npcSay(_thisActor, Say2C.ALL, "Magic block");
							}
						}
						else if(SkillTable.mpConsume(384696326) < _thisActor.getCurrentMp() && SkillTable.hpConsume(384696326) < _thisActor.getCurrentHp() && !_thisActor.isSkillDisabledEx(384696326))
						{
							if(debug)
							{
								Functions.npcSay(_thisActor, Say2C.ALL, "Cast " + SkillTable.getInstance().getInfo(384696326).getId() + " skill");
							}
							addUseSkillDesire(target, SkillTable.getInstance().getInfo(384696326), 0, 1, (long) (1000000 * UseSkill_BoostValue));
						}
						else if(debug)
						{
							Functions.npcSay(_thisActor, Say2C.ALL, "No skill condition");
						}
					}
					else if(target.getMaxHp() * 0.900000 >= target.getCurrentHp())
					{
						if(SkillTable.isMagic(384696325) == 0 && (SkillTable.getAbnormalLevel(_thisActor, 23134209) > 0 || SkillTable.getAbnormalLevel(_thisActor, 87556097) > 0))
						{
							if(debug)
							{
								Functions.npcSay(_thisActor, Say2C.ALL, "Phys defence");
							}
						}
						else if(SkillTable.isMagic(384696325) == 1 && (SkillTable.getAbnormalLevel(_thisActor, 69730305) > 0 || SkillTable.getAbnormalLevel(_thisActor, 87556097) > 0))
						{
							if(debug)
							{
								Functions.npcSay(_thisActor, Say2C.ALL, "Magic block");
							}
						}
						else if(SkillTable.mpConsume(384696325) < _thisActor.getCurrentMp() && SkillTable.hpConsume(384696325) < _thisActor.getCurrentHp() && !_thisActor.isSkillDisabledEx(384696325))
						{
							if(debug)
							{
								Functions.npcSay(_thisActor, Say2C.ALL, "Cast " + SkillTable.getInstance().getInfo(384696325).getId() + " skill");
							}
							addUseSkillDesire(target, SkillTable.getInstance().getInfo(384696325), 0, 1, (long) (1000000 * UseSkill_BoostValue));
						}
						else if(debug)
						{
							Functions.npcSay(_thisActor, Say2C.ALL, "No skill condition");
						}
					}
					else if(SkillTable.isMagic(384696324) == 0 && (SkillTable.getAbnormalLevel(_thisActor, 23134209) > 0 || SkillTable.getAbnormalLevel(_thisActor, 87556097) > 0))
					{
						if(debug)
						{
							Functions.npcSay(_thisActor, Say2C.ALL, "Phys defence");
						}
					}
					else if(SkillTable.isMagic(384696324) == 1 && (SkillTable.getAbnormalLevel(_thisActor, 69730305) > 0 || SkillTable.getAbnormalLevel(_thisActor, 87556097) > 0))
					{
						if(debug)
						{
							Functions.npcSay(_thisActor, Say2C.ALL, "Magic block");
						}
					}
					else if(SkillTable.mpConsume(384696324) < _thisActor.getCurrentMp() && SkillTable.hpConsume(384696324) < _thisActor.getCurrentHp() && !_thisActor.isSkillDisabledEx(384696324))
					{
						if(debug)
						{
							Functions.npcSay(_thisActor, Say2C.ALL, "Cast " + SkillTable.getInstance().getInfo(384696324).getId() + " skill");
						}
						addUseSkillDesire(target, SkillTable.getInstance().getInfo(384696324), 0, 1, (long) (1000000 * UseSkill_BoostValue));
					}
					else if(debug)
					{
						Functions.npcSay(_thisActor, Say2C.ALL, "No skill condition");
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
								Functions.npcSay(_thisActor, Say2C.ALL, "Phys defence");
							}
						}
						else if(SkillTable.isMagic(384696330) == 1 && (SkillTable.getAbnormalLevel(_thisActor, 69730305) > 0 || SkillTable.getAbnormalLevel(_thisActor, 87556097) > 0))
						{
							if(debug)
							{
								Functions.npcSay(_thisActor, Say2C.ALL, "Magic block");
							}
						}
						else if(SkillTable.mpConsume(384696330) < _thisActor.getCurrentMp() && SkillTable.hpConsume(384696330) < _thisActor.getCurrentHp() && !_thisActor.isSkillDisabledEx(384696330))
						{
							if(debug)
							{
								Functions.npcSay(_thisActor, Say2C.ALL, "Cast " + SkillTable.getInstance().getInfo(384696330).getId() + " skill");
							}
							addUseSkillDesire(target, SkillTable.getInstance().getInfo(384696330), 0, 1, (long) (1000000 * UseSkill_BoostValue));
						}
						else if(debug)
						{
							Functions.npcSay(_thisActor, Say2C.ALL, "No skill condition");
						}
					}
					else if(target.getMaxHp() * 0.600000 >= target.getCurrentHp())
					{
						if(SkillTable.isMagic(384696329) == 0 && (SkillTable.getAbnormalLevel(_thisActor, 23134209) > 0 || SkillTable.getAbnormalLevel(_thisActor, 87556097) > 0))
						{
							if(debug)
							{
								Functions.npcSay(_thisActor, Say2C.ALL, "Phys defence");
							}
						}
						else if(SkillTable.isMagic(384696329) == 1 && (SkillTable.getAbnormalLevel(_thisActor, 69730305) > 0 || SkillTable.getAbnormalLevel(_thisActor, 87556097) > 0))
						{
							if(debug)
							{
								Functions.npcSay(_thisActor, Say2C.ALL, "Magic block");
							}
						}
						else if(SkillTable.mpConsume(384696329) < _thisActor.getCurrentMp() && SkillTable.hpConsume(384696329) < _thisActor.getCurrentHp() && !_thisActor.isSkillDisabledEx(384696329))
						{
							if(debug)
							{
								Functions.npcSay(_thisActor, Say2C.ALL, "Cast " + SkillTable.getInstance().getInfo(384696329).getId() + " skill");
							}
							addUseSkillDesire(target, SkillTable.getInstance().getInfo(384696329), 0, 1, (long) (1000000 * UseSkill_BoostValue));
						}
						else if(debug)
						{
							Functions.npcSay(_thisActor, Say2C.ALL, "No skill condition");
						}
					}
					else if(SkillTable.isMagic(384696328) == 0 && (SkillTable.getAbnormalLevel(_thisActor, 23134209) > 0 || SkillTable.getAbnormalLevel(_thisActor, 87556097) > 0))
					{
						if(debug)
						{
							Functions.npcSay(_thisActor, Say2C.ALL, "Phys defence");
						}
					}
					else if(SkillTable.isMagic(384696328) == 1 && (SkillTable.getAbnormalLevel(_thisActor, 69730305) > 0 || SkillTable.getAbnormalLevel(_thisActor, 87556097) > 0))
					{
						if(debug)
						{
							Functions.npcSay(_thisActor, Say2C.ALL, "Magic block");
						}
					}
					else if(SkillTable.mpConsume(384696328) < _thisActor.getCurrentMp() && SkillTable.hpConsume(384696328) < _thisActor.getCurrentHp() && !_thisActor.isSkillDisabledEx(384696328))
					{
						if(debug)
						{
							Functions.npcSay(_thisActor, Say2C.ALL, "Cast " + SkillTable.getInstance().getInfo(384696328).getId() + " skill");
						}
						addUseSkillDesire(target, SkillTable.getInstance().getInfo(384696328), 0, 1, (long) (1000000 * UseSkill_BoostValue));
					}
					else if(debug)
					{
						Functions.npcSay(_thisActor, Say2C.ALL, "No skill condition");
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
							Functions.npcSay(_thisActor, Say2C.ALL, "Phys defence");
						}
					}
					else if(SkillTable.isMagic(384761859) == 1 && (SkillTable.getAbnormalLevel(_thisActor, 69730305) > 0 || SkillTable.getAbnormalLevel(_thisActor, 87556097) > 0))
					{
						if(debug)
						{
							Functions.npcSay(_thisActor, Say2C.ALL, "Magic block");
						}
					}
					else if(SkillTable.mpConsume(384761859) < _thisActor.getCurrentMp() && SkillTable.hpConsume(384761859) < _thisActor.getCurrentHp() && !_thisActor.isSkillDisabledEx(384761859))
					{
						if(debug)
						{
							Functions.npcSay(_thisActor, Say2C.ALL, "Cast " + SkillTable.getInstance().getInfo(384761859).getId() + " skill");
						}
						addUseSkillDesire(target, SkillTable.getInstance().getInfo(384761859), 0, 1, (long) (1000000 * UseSkill_BoostValue));
					}
					else if(debug)
					{
						Functions.npcSay(_thisActor, Say2C.ALL, "No skill condition");
					}
				}
				else if(SkillTable.getAbnormalLevel(target, 384761859) < 9)
				{
					if(SkillTable.isMagic(384761863) == 0 && (SkillTable.getAbnormalLevel(_thisActor, 23134209) > 0 || SkillTable.getAbnormalLevel(_thisActor, 87556097) > 0))
					{
						if(debug)
						{
							Functions.npcSay(_thisActor, Say2C.ALL, "Phys defence");
						}
					}
					else if(SkillTable.isMagic(384761863) == 1 && (SkillTable.getAbnormalLevel(_thisActor, 69730305) > 0 || SkillTable.getAbnormalLevel(_thisActor, 87556097) > 0))
					{
						if(debug)
						{
							Functions.npcSay(_thisActor, Say2C.ALL, "Magic block");
						}
					}
					else if(SkillTable.mpConsume(384761863) < _thisActor.getCurrentMp() && SkillTable.hpConsume(384761863) < _thisActor.getCurrentHp() && !_thisActor.isSkillDisabledEx(384761863))
					{
						if(debug)
						{
							Functions.npcSay(_thisActor, Say2C.ALL, "Cast " + SkillTable.getInstance().getInfo(384761863).getId() + " skill");
						}
						addUseSkillDesire(target, SkillTable.getInstance().getInfo(384761863), 0, 1, (long) (1000000 * UseSkill_BoostValue));
					}
					else if(debug)
					{
						Functions.npcSay(_thisActor, Say2C.ALL, "No skill condition");
					}
				}
				else if(SkillTable.getAbnormalLevel(target, 384761859) < 10)
				{
					if(SkillTable.isMagic(384761866) == 0 && (SkillTable.getAbnormalLevel(_thisActor, 23134209) > 0 || SkillTable.getAbnormalLevel(_thisActor, 87556097) > 0))
					{
						if(debug)
						{
							Functions.npcSay(_thisActor, Say2C.ALL, "Phys defence");
						}
					}
					else if(SkillTable.isMagic(384761866) == 1 && (SkillTable.getAbnormalLevel(_thisActor, 69730305) > 0 || SkillTable.getAbnormalLevel(_thisActor, 87556097) > 0))
					{
						if(debug)
						{
							Functions.npcSay(_thisActor, Say2C.ALL, "Magic block");
						}
					}
					else if(SkillTable.mpConsume(384761866) < _thisActor.getCurrentMp() && SkillTable.hpConsume(384761866) < _thisActor.getCurrentHp() && !_thisActor.isSkillDisabledEx(384761866))
					{
						if(debug)
						{
							Functions.npcSay(_thisActor, Say2C.ALL, "Cast " + SkillTable.getInstance().getInfo(384761866).getId() + " skill");
						}
						addUseSkillDesire(target, SkillTable.getInstance().getInfo(384761866), 0, 1, (long) (1000000 * UseSkill_BoostValue));
					}
					else if(debug)
					{
						Functions.npcSay(_thisActor, Say2C.ALL, "No skill condition");
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
							Functions.npcSay(_thisActor, Say2C.ALL, "Phys defence");
						}
					}
					else if(SkillTable.isMagic(384827395) == 1 && (SkillTable.getAbnormalLevel(_thisActor, 69730305) > 0 || SkillTable.getAbnormalLevel(_thisActor, 87556097) > 0))
					{
						if(debug)
						{
							Functions.npcSay(_thisActor, Say2C.ALL, "Magic block");
						}
					}
					else if(SkillTable.mpConsume(384827395) < _thisActor.getCurrentMp() && SkillTable.hpConsume(384827395) < _thisActor.getCurrentHp() && !_thisActor.isSkillDisabledEx(384827395))
					{
						if(debug)
						{
							Functions.npcSay(_thisActor, Say2C.ALL, "Cast " + SkillTable.getInstance().getInfo(384827395).getId() + " skill");
						}
						addUseSkillDesire(target, SkillTable.getInstance().getInfo(384827395), 0, 1, (long) (1000000 * UseSkill_BoostValue));
					}
					else if(debug)
					{
						Functions.npcSay(_thisActor, Say2C.ALL, "No skill condition");
					}
				}
				else if(SkillTable.getAbnormalLevel(target, 384827395) < 9)
				{
					if(SkillTable.isMagic(384827399) == 0 && (SkillTable.getAbnormalLevel(_thisActor, 23134209) > 0 || SkillTable.getAbnormalLevel(_thisActor, 87556097) > 0))
					{
						if(debug)
						{
							Functions.npcSay(_thisActor, Say2C.ALL, "Phys defence");
						}
					}
					else if(SkillTable.isMagic(384827399) == 1 && (SkillTable.getAbnormalLevel(_thisActor, 69730305) > 0 || SkillTable.getAbnormalLevel(_thisActor, 87556097) > 0))
					{
						if(debug)
						{
							Functions.npcSay(_thisActor, Say2C.ALL, "Magic block");
						}
					}
					else if(SkillTable.mpConsume(384827399) < _thisActor.getCurrentMp() && SkillTable.hpConsume(384827399) < _thisActor.getCurrentHp() && !_thisActor.isSkillDisabledEx(384827399))
					{
						if(debug)
						{
							Functions.npcSay(_thisActor, Say2C.ALL, "Cast " + SkillTable.getInstance().getInfo(384827399).getId() + " skill");
						}
						addUseSkillDesire(target, SkillTable.getInstance().getInfo(384827399), 0, 1, (long) (1000000 * UseSkill_BoostValue));
					}
					else if(debug)
					{
						Functions.npcSay(_thisActor, Say2C.ALL, "No skill condition");
					}
				}
				else if(SkillTable.getAbnormalLevel(target, 384827395) < 10)
				{
					if(SkillTable.isMagic(384827402) == 0 && (SkillTable.getAbnormalLevel(_thisActor, 23134209) > 0 || SkillTable.getAbnormalLevel(_thisActor, 87556097) > 0))
					{
						if(debug)
						{
							Functions.npcSay(_thisActor, Say2C.ALL, "Phys defence");
						}
					}
					else if(SkillTable.isMagic(384827402) == 1 && (SkillTable.getAbnormalLevel(_thisActor, 69730305) > 0 || SkillTable.getAbnormalLevel(_thisActor, 87556097) > 0))
					{
						if(debug)
						{
							Functions.npcSay(_thisActor, Say2C.ALL, "Magic block");
						}
					}
					else if(SkillTable.mpConsume(384827402) < _thisActor.getCurrentMp() && SkillTable.hpConsume(384827402) < _thisActor.getCurrentHp() && !_thisActor.isSkillDisabledEx(384827402))
					{
						if(debug)
						{
							Functions.npcSay(_thisActor, Say2C.ALL, "Cast " + SkillTable.getInstance().getInfo(384827402).getId() + " skill");
						}
						addUseSkillDesire(target, SkillTable.getInstance().getInfo(384827402), 0, 1, (long) (1000000 * UseSkill_BoostValue));
					}
					else if(debug)
					{
						Functions.npcSay(_thisActor, Say2C.ALL, "No skill condition");
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
								Functions.npcSay(_thisActor, Say2C.ALL, "Phys defence");
							}
						}
						else if(SkillTable.isMagic(384434179) == 1 && (SkillTable.getAbnormalLevel(_thisActor, 69730305) > 0 || SkillTable.getAbnormalLevel(_thisActor, 87556097) > 0))
						{
							if(debug)
							{
								Functions.npcSay(_thisActor, Say2C.ALL, "Magic block");
							}
						}
						else if(SkillTable.mpConsume(384434179) < _thisActor.getCurrentMp() && SkillTable.hpConsume(384434179) < _thisActor.getCurrentHp() && !_thisActor.isSkillDisabledEx(384434179))
						{
							if(debug)
							{
								Functions.npcSay(_thisActor, Say2C.ALL, "Cast " + SkillTable.getInstance().getInfo(384434179).getId() + " skill");
							}
							addUseSkillDesire(target, SkillTable.getInstance().getInfo(384434179), 0, 1, (long) (1000000 * UseSkill_BoostValue));
						}
						else if(debug)
						{
							Functions.npcSay(_thisActor, Say2C.ALL, "No skill condition");
						}
					}
					else if(target.getMaxHp() * 0.600000 >= target.getCurrentHp())
					{
						if(SkillTable.isMagic(384434178) == 0 && (SkillTable.getAbnormalLevel(_thisActor, 23134209) > 0 || SkillTable.getAbnormalLevel(_thisActor, 87556097) > 0))
						{
							if(debug)
							{
								Functions.npcSay(_thisActor, Say2C.ALL, "Phys defence");
							}
						}
						else if(SkillTable.isMagic(384434178) == 1 && (SkillTable.getAbnormalLevel(_thisActor, 69730305) > 0 || SkillTable.getAbnormalLevel(_thisActor, 87556097) > 0))
						{
							if(debug)
							{
								Functions.npcSay(_thisActor, Say2C.ALL, "Magic block");
							}
						}
						else if(SkillTable.mpConsume(384434178) < _thisActor.getCurrentMp() && SkillTable.hpConsume(384434178) < _thisActor.getCurrentHp() && !_thisActor.isSkillDisabledEx(384434178))
						{
							if(debug)
							{
								Functions.npcSay(_thisActor, Say2C.ALL, "Cast " + SkillTable.getInstance().getInfo(384434178).getId() + " skill");
							}
							addUseSkillDesire(target, SkillTable.getInstance().getInfo(384434178), 0, 1, (long) (1000000 * UseSkill_BoostValue));
						}
						else if(debug)
						{
							Functions.npcSay(_thisActor, Say2C.ALL, "No skill condition");
						}
					}
					else if(SkillTable.isMagic(384434177) == 0 && (SkillTable.getAbnormalLevel(_thisActor, 23134209) > 0 || SkillTable.getAbnormalLevel(_thisActor, 87556097) > 0))
					{
						if(debug)
						{
							Functions.npcSay(_thisActor, Say2C.ALL, "Phys defence");
						}
					}
					else if(SkillTable.isMagic(384434177) == 1 && (SkillTable.getAbnormalLevel(_thisActor, 69730305) > 0 || SkillTable.getAbnormalLevel(_thisActor, 87556097) > 0))
					{
						if(debug)
						{
							Functions.npcSay(_thisActor, Say2C.ALL, "Magic block");
						}
					}
					else if(SkillTable.mpConsume(384434177) < _thisActor.getCurrentMp() && SkillTable.hpConsume(384434177) < _thisActor.getCurrentHp() && !_thisActor.isSkillDisabledEx(384434177))
					{
						if(debug)
						{
							Functions.npcSay(_thisActor, Say2C.ALL, "Cast " + SkillTable.getInstance().getInfo(384434177).getId() + " skill");
						}
						addUseSkillDesire(target, SkillTable.getInstance().getInfo(384434177), 0, 1, (long) (1000000 * UseSkill_BoostValue));
					}
					else if(debug)
					{
						Functions.npcSay(_thisActor, Say2C.ALL, "No skill condition");
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
								Functions.npcSay(_thisActor, Say2C.ALL, "Phys defence");
							}
						}
						else if(SkillTable.isMagic(384434183) == 1 && (SkillTable.getAbnormalLevel(_thisActor, 69730305) > 0 || SkillTable.getAbnormalLevel(_thisActor, 87556097) > 0))
						{
							if(debug)
							{
								Functions.npcSay(_thisActor, Say2C.ALL, "Magic block");
							}
						}
						else if(SkillTable.mpConsume(384434183) < _thisActor.getCurrentMp() && SkillTable.hpConsume(384434183) < _thisActor.getCurrentHp() && !_thisActor.isSkillDisabledEx(384434183))
						{
							if(debug)
							{
								Functions.npcSay(_thisActor, Say2C.ALL, "Cast " + SkillTable.getInstance().getInfo(384434183).getId() + " skill");
							}
							addUseSkillDesire(target, SkillTable.getInstance().getInfo(384434183), 0, 1, (long) (1000000 * UseSkill_BoostValue));
						}
						else if(debug)
						{
							Functions.npcSay(_thisActor, Say2C.ALL, "No skill condition");
						}
					}
					else if(target.getMaxHp() * 0.600000 >= target.getCurrentHp())
					{
						if(SkillTable.isMagic(384434182) == 0 && (SkillTable.getAbnormalLevel(_thisActor, 23134209) > 0 || SkillTable.getAbnormalLevel(_thisActor, 87556097) > 0))
						{
							if(debug)
							{
								Functions.npcSay(_thisActor, Say2C.ALL, "Phys defence");
							}
						}
						else if(SkillTable.isMagic(384434182) == 1 && (SkillTable.getAbnormalLevel(_thisActor, 69730305) > 0 || SkillTable.getAbnormalLevel(_thisActor, 87556097) > 0))
						{
							if(debug)
							{
								Functions.npcSay(_thisActor, Say2C.ALL, "Magic block");
							}
						}
						else if(SkillTable.mpConsume(384434182) < _thisActor.getCurrentMp() && SkillTable.hpConsume(384434182) < _thisActor.getCurrentHp() && !_thisActor.isSkillDisabledEx(384434182))
						{
							if(debug)
							{
								Functions.npcSay(_thisActor, Say2C.ALL, "Cast " + SkillTable.getInstance().getInfo(384434182).getId() + " skill");
							}
							addUseSkillDesire(target, SkillTable.getInstance().getInfo(384434182), 0, 1, (long) (1000000 * UseSkill_BoostValue));
						}
						else if(debug)
						{
							Functions.npcSay(_thisActor, Say2C.ALL, "No skill condition");
						}
					}
					else if(target.getMaxHp() * 0.900000 >= target.getCurrentHp())
					{
						if(SkillTable.isMagic(384434181) == 0 && (SkillTable.getAbnormalLevel(_thisActor, 23134209) > 0 || SkillTable.getAbnormalLevel(_thisActor, 87556097) > 0))
						{
							if(debug)
							{
								Functions.npcSay(_thisActor, Say2C.ALL, "Phys defence");
							}
						}
						else if(SkillTable.isMagic(384434181) == 1 && (SkillTable.getAbnormalLevel(_thisActor, 69730305) > 0 || SkillTable.getAbnormalLevel(_thisActor, 87556097) > 0))
						{
							if(debug)
							{
								Functions.npcSay(_thisActor, Say2C.ALL, "Magic block");
							}
						}
						else if(SkillTable.mpConsume(384434181) < _thisActor.getCurrentMp() && SkillTable.hpConsume(384434181) < _thisActor.getCurrentHp() && !_thisActor.isSkillDisabledEx(384434181))
						{
							if(debug)
							{
								Functions.npcSay(_thisActor, Say2C.ALL, "Cast " + SkillTable.getInstance().getInfo(384434181).getId() + " skill");
							}
							addUseSkillDesire(target, SkillTable.getInstance().getInfo(384434181), 0, 1, (long) (1000000 * UseSkill_BoostValue));
						}
						else if(debug)
						{
							Functions.npcSay(_thisActor, Say2C.ALL, "No skill condition");
						}
					}
					else if(SkillTable.isMagic(384434180) == 0 && (SkillTable.getAbnormalLevel(_thisActor, 23134209) > 0 || SkillTable.getAbnormalLevel(_thisActor, 87556097) > 0))
					{
						if(debug)
						{
							Functions.npcSay(_thisActor, Say2C.ALL, "Phys defence");
						}
					}
					else if(SkillTable.isMagic(384434180) == 1 && (SkillTable.getAbnormalLevel(_thisActor, 69730305) > 0 || SkillTable.getAbnormalLevel(_thisActor, 87556097) > 0))
					{
						if(debug)
						{
							Functions.npcSay(_thisActor, Say2C.ALL, "Magic block");
						}
					}
					else if(SkillTable.mpConsume(384434180) < _thisActor.getCurrentMp() && SkillTable.hpConsume(384434180) < _thisActor.getCurrentHp() && !_thisActor.isSkillDisabledEx(384434180))
					{
						if(debug)
						{
							Functions.npcSay(_thisActor, Say2C.ALL, "Cast " + SkillTable.getInstance().getInfo(384434180).getId() + " skill");
						}
						addUseSkillDesire(target, SkillTable.getInstance().getInfo(384434180), 0, 1, (long) (1000000 * UseSkill_BoostValue));
					}
					else if(debug)
					{
						Functions.npcSay(_thisActor, Say2C.ALL, "No skill condition");
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
								Functions.npcSay(_thisActor, Say2C.ALL, "Phys defence");
							}
						}
						else if(SkillTable.isMagic(384434186) == 1 && (SkillTable.getAbnormalLevel(_thisActor, 69730305) > 0 || SkillTable.getAbnormalLevel(_thisActor, 87556097) > 0))
						{
							if(debug)
							{
								Functions.npcSay(_thisActor, Say2C.ALL, "Magic block");
							}
						}
						else if(SkillTable.mpConsume(384434186) < _thisActor.getCurrentMp() && SkillTable.hpConsume(384434186) < _thisActor.getCurrentHp() && !_thisActor.isSkillDisabledEx(384434186))
						{
							if(debug)
							{
								Functions.npcSay(_thisActor, Say2C.ALL, "Cast " + SkillTable.getInstance().getInfo(384434186).getId() + " skill");
							}
							addUseSkillDesire(target, SkillTable.getInstance().getInfo(384434186), 0, 1, (long) (1000000 * UseSkill_BoostValue));
						}
						else if(debug)
						{
							Functions.npcSay(_thisActor, Say2C.ALL, "No skill condition");
						}
					}
					else if(target.getMaxHp() * 0.600000 >= target.getCurrentHp())
					{
						if(SkillTable.isMagic(384434185) == 0 && (SkillTable.getAbnormalLevel(_thisActor, 23134209) > 0 || SkillTable.getAbnormalLevel(_thisActor, 87556097) > 0))
						{
							if(debug)
							{
								Functions.npcSay(_thisActor, Say2C.ALL, "Phys defence");
							}
						}
						else if(SkillTable.isMagic(384434185) == 1 && (SkillTable.getAbnormalLevel(_thisActor, 69730305) > 0 || SkillTable.getAbnormalLevel(_thisActor, 87556097) > 0))
						{
							if(debug)
							{
								Functions.npcSay(_thisActor, Say2C.ALL, "Magic block");
							}
						}
						else if(SkillTable.mpConsume(384434185) < _thisActor.getCurrentMp() && SkillTable.hpConsume(384434185) < _thisActor.getCurrentHp() && !_thisActor.isSkillDisabledEx(384434185))
						{
							if(debug)
							{
								Functions.npcSay(_thisActor, Say2C.ALL, "Cast " + SkillTable.getInstance().getInfo(384434185).getId() + " skill");
							}
							addUseSkillDesire(target, SkillTable.getInstance().getInfo(384434185), 0, 1, (long) (1000000 * UseSkill_BoostValue));
						}
						else if(debug)
						{
							Functions.npcSay(_thisActor, Say2C.ALL, "No skill condition");
						}
					}
					else if(SkillTable.isMagic(384434184) == 0 && (SkillTable.getAbnormalLevel(_thisActor, 23134209) > 0 || SkillTable.getAbnormalLevel(_thisActor, 87556097) > 0))
					{
						if(debug)
						{
							Functions.npcSay(_thisActor, Say2C.ALL, "Phys defence");
						}
					}
					else if(SkillTable.isMagic(384434184) == 1 && (SkillTable.getAbnormalLevel(_thisActor, 69730305) > 0 || SkillTable.getAbnormalLevel(_thisActor, 87556097) > 0))
					{
						if(debug)
						{
							Functions.npcSay(_thisActor, Say2C.ALL, "Magic block");
						}
					}
					else if(SkillTable.mpConsume(384434184) < _thisActor.getCurrentMp() && SkillTable.hpConsume(384434184) < _thisActor.getCurrentHp() && !_thisActor.isSkillDisabledEx(384434184))
					{
						if(debug)
						{
							Functions.npcSay(_thisActor, Say2C.ALL, "Cast " + SkillTable.getInstance().getInfo(384434184).getId() + " skill");
						}
						addUseSkillDesire(target, SkillTable.getInstance().getInfo(384434184), 0, 1, (long) (1000000 * UseSkill_BoostValue));
					}
					else if(debug)
					{
						Functions.npcSay(_thisActor, Say2C.ALL, "No skill condition");
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
							Functions.npcSay(_thisActor, Say2C.ALL, "Phys defence");
						}
					}
					else if(SkillTable.isMagic(384565251) == 1 && (SkillTable.getAbnormalLevel(_thisActor, 69730305) > 0 || SkillTable.getAbnormalLevel(_thisActor, 87556097) > 0))
					{
						if(debug)
						{
							Functions.npcSay(_thisActor, Say2C.ALL, "Magic block");
						}
					}
					else if(SkillTable.mpConsume(384565251) < _thisActor.getCurrentMp() && SkillTable.hpConsume(384565251) < _thisActor.getCurrentHp() && !_thisActor.isSkillDisabledEx(384565251))
					{
						if(debug)
						{
							Functions.npcSay(_thisActor, Say2C.ALL, "Cast " + SkillTable.getInstance().getInfo(384565251).getId() + " skill");
						}
						addUseSkillDesire(target, SkillTable.getInstance().getInfo(384565251), 0, 1, (long) (1000000 * UseSkill_BoostValue));
					}
					else if(debug)
					{
						Functions.npcSay(_thisActor, Say2C.ALL, "No skill condition");
					}
				}
				else if(SkillTable.getAbnormalLevel(target, 384565251) < 9)
				{
					if(SkillTable.isMagic(384565255) == 0 && (SkillTable.getAbnormalLevel(_thisActor, 23134209) > 0 || SkillTable.getAbnormalLevel(_thisActor, 87556097) > 0))
					{
						if(debug)
						{
							Functions.npcSay(_thisActor, Say2C.ALL, "Phys defence");
						}
					}
					else if(SkillTable.isMagic(384565255) == 1 && (SkillTable.getAbnormalLevel(_thisActor, 69730305) > 0 || SkillTable.getAbnormalLevel(_thisActor, 87556097) > 0))
					{
						if(debug)
						{
							Functions.npcSay(_thisActor, Say2C.ALL, "Magic block");
						}
					}
					else if(SkillTable.mpConsume(384565255) < _thisActor.getCurrentMp() && SkillTable.hpConsume(384565255) < _thisActor.getCurrentHp() && !_thisActor.isSkillDisabledEx(384565255))
					{
						if(debug)
						{
							Functions.npcSay(_thisActor, Say2C.ALL, "Cast " + SkillTable.getInstance().getInfo(384565255).getId() + " skill");
						}
						addUseSkillDesire(target, SkillTable.getInstance().getInfo(384565255), 0, 1, (long) (1000000 * UseSkill_BoostValue));
					}
					else if(debug)
					{
						Functions.npcSay(_thisActor, Say2C.ALL, "No skill condition");
					}
				}
				else if(SkillTable.getAbnormalLevel(target, 384565251) < 10)
				{
					if(SkillTable.isMagic(384565258) == 0 && (SkillTable.getAbnormalLevel(_thisActor, 23134209) > 0 || SkillTable.getAbnormalLevel(_thisActor, 87556097) > 0))
					{
						if(debug)
						{
							Functions.npcSay(_thisActor, Say2C.ALL, "Phys defence");
						}
					}
					else if(SkillTable.isMagic(384565258) == 1 && (SkillTable.getAbnormalLevel(_thisActor, 69730305) > 0 || SkillTable.getAbnormalLevel(_thisActor, 87556097) > 0))
					{
						if(debug)
						{
							Functions.npcSay(_thisActor, Say2C.ALL, "Magic block");
						}
					}
					else if(SkillTable.mpConsume(384565258) < _thisActor.getCurrentMp() && SkillTable.hpConsume(384565258) < _thisActor.getCurrentHp() && !_thisActor.isSkillDisabledEx(384565258))
					{
						if(debug)
						{
							Functions.npcSay(_thisActor, Say2C.ALL, "Cast " + SkillTable.getInstance().getInfo(384565258).getId() + " skill");
						}
						addUseSkillDesire(target, SkillTable.getInstance().getInfo(384565258), 0, 1, (long) (1000000 * UseSkill_BoostValue));
					}
					else if(debug)
					{
						Functions.npcSay(_thisActor, Say2C.ALL, "No skill condition");
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
							Functions.npcSay(_thisActor, Say2C.ALL, "Phys defence");
						}
					}
					else if(SkillTable.isMagic(384630787) == 1 && (SkillTable.getAbnormalLevel(_thisActor, 69730305) > 0 || SkillTable.getAbnormalLevel(_thisActor, 87556097) > 0))
					{
						if(debug)
						{
							Functions.npcSay(_thisActor, Say2C.ALL, "Magic block");
						}
					}
					else if(SkillTable.mpConsume(384630787) < _thisActor.getCurrentMp() && SkillTable.hpConsume(384630787) < _thisActor.getCurrentHp() && !_thisActor.isSkillDisabledEx(384630787))
					{
						if(debug)
						{
							Functions.npcSay(_thisActor, Say2C.ALL, "Cast " + SkillTable.getInstance().getInfo(384630787).getId() + " skill");
						}
						addUseSkillDesire(target, SkillTable.getInstance().getInfo(384630787), 0, 1, (long) (1000000 * UseSkill_BoostValue));
					}
					else if(debug)
					{
						Functions.npcSay(_thisActor, Say2C.ALL, "No skill condition");
					}
				}
				else if(SkillTable.getAbnormalLevel(target, 384630787) < 9)
				{
					if(SkillTable.isMagic(384630791) == 0 && (SkillTable.getAbnormalLevel(_thisActor, 23134209) > 0 || SkillTable.getAbnormalLevel(_thisActor, 87556097) > 0))
					{
						if(debug)
						{
							Functions.npcSay(_thisActor, Say2C.ALL, "Phys defence");
						}
					}
					else if(SkillTable.isMagic(384630791) == 1 && (SkillTable.getAbnormalLevel(_thisActor, 69730305) > 0 || SkillTable.getAbnormalLevel(_thisActor, 87556097) > 0))
					{
						if(debug)
						{
							Functions.npcSay(_thisActor, Say2C.ALL, "Magic block");
						}
					}
					else if(SkillTable.mpConsume(384630791) < _thisActor.getCurrentMp() && SkillTable.hpConsume(384630791) < _thisActor.getCurrentHp() && !_thisActor.isSkillDisabledEx(384630791))
					{
						if(debug)
						{
							Functions.npcSay(_thisActor, Say2C.ALL, "Cast " + SkillTable.getInstance().getInfo(384630791).getId() + " skill");
						}
						addUseSkillDesire(target, SkillTable.getInstance().getInfo(384630791), 0, 1, (long) (1000000 * UseSkill_BoostValue));
					}
					else if(debug)
					{
						Functions.npcSay(_thisActor, Say2C.ALL, "No skill condition");
					}
				}
				else if(SkillTable.getAbnormalLevel(target, 384630787) < 10)
				{
					if(SkillTable.isMagic(384630794) == 0 && (SkillTable.getAbnormalLevel(_thisActor, 23134209) > 0 || SkillTable.getAbnormalLevel(_thisActor, 87556097) > 0))
					{
						if(debug)
						{
							Functions.npcSay(_thisActor, Say2C.ALL, "Phys defence");
						}
					}
					else if(SkillTable.isMagic(384630794) == 1 && (SkillTable.getAbnormalLevel(_thisActor, 69730305) > 0 || SkillTable.getAbnormalLevel(_thisActor, 87556097) > 0))
					{
						if(debug)
						{
							Functions.npcSay(_thisActor, Say2C.ALL, "Magic block");
						}
					}
					else if(SkillTable.mpConsume(384630794) < _thisActor.getCurrentMp() && SkillTable.hpConsume(384630794) < _thisActor.getCurrentHp() && !_thisActor.isSkillDisabledEx(384630794))
					{
						if(debug)
						{
							Functions.npcSay(_thisActor, Say2C.ALL, "Cast " + SkillTable.getInstance().getInfo(384630794).getId() + " skill");
						}
						addUseSkillDesire(target, SkillTable.getInstance().getInfo(384630794), 0, 1, (long) (1000000 * UseSkill_BoostValue));
					}
					else if(debug)
					{
						Functions.npcSay(_thisActor, Say2C.ALL, "No skill condition");
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
							Functions.npcSay(_thisActor, Say2C.ALL, "Phys defence");
						}
					}
					else if(SkillTable.isMagic(384499715) == 1 && (SkillTable.getAbnormalLevel(_thisActor, 69730305) > 0 || SkillTable.getAbnormalLevel(_thisActor, 87556097) > 0))
					{
						if(debug)
						{
							Functions.npcSay(_thisActor, Say2C.ALL, "Magic block");
						}
					}
					else if(SkillTable.mpConsume(384499715) < _thisActor.getCurrentMp() && SkillTable.hpConsume(384499715) < _thisActor.getCurrentHp() && !_thisActor.isSkillDisabledEx(384499715))
					{
						if(debug)
						{
							Functions.npcSay(_thisActor, Say2C.ALL, "Cast " + SkillTable.getInstance().getInfo(384499715).getId() + " skill");
						}
						addUseSkillDesire(target, SkillTable.getInstance().getInfo(384499715), 0, 1, (long) (1000000 * UseSkill_BoostValue));
					}
					else if(debug)
					{
						Functions.npcSay(_thisActor, Say2C.ALL, "No skill condition");
					}
				}
				else if(SkillTable.getAbnormalLevel(target, 384499715) < 9)
				{
					if(SkillTable.isMagic(384499715) == 0 && (SkillTable.getAbnormalLevel(_thisActor, 23134209) > 0 || SkillTable.getAbnormalLevel(_thisActor, 87556097) > 0))
					{
						if(debug)
						{
							Functions.npcSay(_thisActor, Say2C.ALL, "Phys defence");
						}
					}
					else if(SkillTable.isMagic(384499715) == 1 && (SkillTable.getAbnormalLevel(_thisActor, 69730305) > 0 || SkillTable.getAbnormalLevel(_thisActor, 87556097) > 0))
					{
						if(debug)
						{
							Functions.npcSay(_thisActor, Say2C.ALL, "Magic block");
						}
					}
					else if(SkillTable.mpConsume(384499715) < _thisActor.getCurrentMp() && SkillTable.hpConsume(384499715) < _thisActor.getCurrentHp() && !_thisActor.isSkillDisabledEx(384499715))
					{
						if(debug)
						{
							Functions.npcSay(_thisActor, Say2C.ALL, "Cast " + SkillTable.getInstance().getInfo(384499715).getId() + " skill");
						}
						addUseSkillDesire(target, SkillTable.getInstance().getInfo(384499715), 0, 1, (long) (1000000 * UseSkill_BoostValue));
					}
					else if(debug)
					{
						Functions.npcSay(_thisActor, Say2C.ALL, "No skill condition");
					}
				}
				else if(SkillTable.getAbnormalLevel(target, 384499715) < 10)
				{
					if(SkillTable.isMagic(384499722) == 0 && (SkillTable.getAbnormalLevel(_thisActor, 23134209) > 0 || SkillTable.getAbnormalLevel(_thisActor, 87556097) > 0))
					{
						if(debug)
						{
							Functions.npcSay(_thisActor, Say2C.ALL, "Phys defence");
						}
					}
					else if(SkillTable.isMagic(384499722) == 1 && (SkillTable.getAbnormalLevel(_thisActor, 69730305) > 0 || SkillTable.getAbnormalLevel(_thisActor, 87556097) > 0))
					{
						if(debug)
						{
							Functions.npcSay(_thisActor, Say2C.ALL, "Magic block");
						}
					}
					else if(SkillTable.mpConsume(384499722) < _thisActor.getCurrentMp() && SkillTable.hpConsume(384499722) < _thisActor.getCurrentHp() && !_thisActor.isSkillDisabledEx(384499722))
					{
						if(debug)
						{
							Functions.npcSay(_thisActor, Say2C.ALL, "Cast " + SkillTable.getInstance().getInfo(384499722).getId() + " skill");
						}
						addUseSkillDesire(target, SkillTable.getInstance().getInfo(384499722), 0, 1, (long) (1000000 * UseSkill_BoostValue));
					}
					else if(debug)
					{
						Functions.npcSay(_thisActor, Say2C.ALL, "No skill condition");
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
							Functions.npcSay(_thisActor, Say2C.ALL, "Phys defence");
						}
					}
					else if(SkillTable.isMagic(384892929) == 1 && (SkillTable.getAbnormalLevel(_thisActor, 69730305) > 0 || SkillTable.getAbnormalLevel(_thisActor, 87556097) > 0))
					{
						if(debug)
						{
							Functions.npcSay(_thisActor, Say2C.ALL, "Magic block");
						}
					}
					else if(SkillTable.mpConsume(384892929) < _thisActor.getCurrentMp() && SkillTable.hpConsume(384892929) < _thisActor.getCurrentHp() && !_thisActor.isSkillDisabledEx(384892929))
					{
						if(debug)
						{
							Functions.npcSay(_thisActor, Say2C.ALL, "Cast " + SkillTable.getInstance().getInfo(384892929).getId() + " skill");
						}
						addUseSkillDesire(target, SkillTable.getInstance().getInfo(384892929), 0, 1, (long) (1000000 * UseSkill_BoostValue));
					}
					else if(debug)
					{
						Functions.npcSay(_thisActor, Say2C.ALL, "No skill condition");
					}
				}
				else if(SkillTable.getAbnormalLevel(target, 384892929) < 2)
				{
					if(SkillTable.isMagic(384892930) == 0 && (SkillTable.getAbnormalLevel(_thisActor, 23134209) > 0 || SkillTable.getAbnormalLevel(_thisActor, 87556097) > 0))
					{
						if(debug)
						{
							Functions.npcSay(_thisActor, Say2C.ALL, "Phys defence");
						}
					}
					else if(SkillTable.isMagic(384892930) == 1 && (SkillTable.getAbnormalLevel(_thisActor, 69730305) > 0 || SkillTable.getAbnormalLevel(_thisActor, 87556097) > 0))
					{
						if(debug)
						{
							Functions.npcSay(_thisActor, Say2C.ALL, "Magic block");
						}
					}
					else if(SkillTable.mpConsume(384892930) < _thisActor.getCurrentMp() && SkillTable.hpConsume(384892930) < _thisActor.getCurrentHp() && !_thisActor.isSkillDisabledEx(384892930))
					{
						if(debug)
						{
							Functions.npcSay(_thisActor, Say2C.ALL, "Cast " + SkillTable.getInstance().getInfo(384892930).getId() + " skill");
						}
						addUseSkillDesire(target, SkillTable.getInstance().getInfo(384892930), 0, 1, (long) (1000000 * UseSkill_BoostValue));
					}
					else if(debug)
					{
						Functions.npcSay(_thisActor, Say2C.ALL, "No skill condition");
					}
				}
				else if(SkillTable.getAbnormalLevel(target, 384892929) < 3)
				{
					if(SkillTable.isMagic(384892931) == 0 && (SkillTable.getAbnormalLevel(_thisActor, 23134209) > 0 || SkillTable.getAbnormalLevel(_thisActor, 87556097) > 0))
					{
						if(debug)
						{
							Functions.npcSay(_thisActor, Say2C.ALL, "Phys defence");
						}
					}
					else if(SkillTable.isMagic(384892931) == 1 && (SkillTable.getAbnormalLevel(_thisActor, 69730305) > 0 || SkillTable.getAbnormalLevel(_thisActor, 87556097) > 0))
					{
						if(debug)
						{
							Functions.npcSay(_thisActor, Say2C.ALL, "Magic block");
						}
					}
					else if(SkillTable.mpConsume(384892931) < _thisActor.getCurrentMp() && SkillTable.hpConsume(384892931) < _thisActor.getCurrentHp() && !_thisActor.isSkillDisabledEx(384892931))
					{
						if(debug)
						{
							Functions.npcSay(_thisActor, Say2C.ALL, "Cast " + SkillTable.getInstance().getInfo(384892931).getId() + " skill");
						}
						addUseSkillDesire(target, SkillTable.getInstance().getInfo(384892931), 0, 1, (long) (1000000 * UseSkill_BoostValue));
					}
					else if(debug)
					{
						Functions.npcSay(_thisActor, Say2C.ALL, "No skill condition");
					}
				}
				else if(SkillTable.getAbnormalLevel(target, 384892929) < 4)
				{
					if(SkillTable.isMagic(384892932) == 0 && (SkillTable.getAbnormalLevel(_thisActor, 23134209) > 0 || SkillTable.getAbnormalLevel(_thisActor, 87556097) > 0))
					{
						if(debug)
						{
							Functions.npcSay(_thisActor, Say2C.ALL, "Phys defence");
						}
					}
					else if(SkillTable.isMagic(384892932) == 1 && (SkillTable.getAbnormalLevel(_thisActor, 69730305) > 0 || SkillTable.getAbnormalLevel(_thisActor, 87556097) > 0))
					{
						if(debug)
						{
							Functions.npcSay(_thisActor, Say2C.ALL, "Magic block");
						}
					}
					else if(SkillTable.mpConsume(384892932) < _thisActor.getCurrentMp() && SkillTable.hpConsume(384892932) < _thisActor.getCurrentHp() && !_thisActor.isSkillDisabledEx(384892932))
					{
						if(debug)
						{
							Functions.npcSay(_thisActor, Say2C.ALL, "Cast " + SkillTable.getInstance().getInfo(384892932).getId() + " skill");
						}
						addUseSkillDesire(target, SkillTable.getInstance().getInfo(384892932), 0, 1, (long) (1000000 * UseSkill_BoostValue));
					}
					else if(debug)
					{
						Functions.npcSay(_thisActor, Say2C.ALL, "No skill condition");
					}
				}
				else if(SkillTable.getAbnormalLevel(target, 384892929) < 5)
				{
					if(SkillTable.isMagic(384892933) == 0 && (SkillTable.getAbnormalLevel(_thisActor, 23134209) > 0 || SkillTable.getAbnormalLevel(_thisActor, 87556097) > 0))
					{
						if(debug)
						{
							Functions.npcSay(_thisActor, Say2C.ALL, "Phys defence");
						}
					}
					else if(SkillTable.isMagic(384892933) == 1 && (SkillTable.getAbnormalLevel(_thisActor, 69730305) > 0 || SkillTable.getAbnormalLevel(_thisActor, 87556097) > 0))
					{
						if(debug)
						{
							Functions.npcSay(_thisActor, Say2C.ALL, "Magic block");
						}
					}
					else if(SkillTable.mpConsume(384892933) < _thisActor.getCurrentMp() && SkillTable.hpConsume(384892933) < _thisActor.getCurrentHp() && !_thisActor.isSkillDisabledEx(384892933))
					{
						if(debug)
						{
							Functions.npcSay(_thisActor, Say2C.ALL, "Cast " + SkillTable.getInstance().getInfo(384892933).getId() + " skill");
						}
						addUseSkillDesire(target, SkillTable.getInstance().getInfo(384892933), 0, 1, (long) (1000000 * UseSkill_BoostValue));
					}
					else if(debug)
					{
						Functions.npcSay(_thisActor, Say2C.ALL, "No skill condition");
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
							Functions.npcSay(_thisActor, Say2C.ALL, "Phys defence");
						}
					}
					else if(SkillTable.isMagic(384958465) == 1 && (SkillTable.getAbnormalLevel(_thisActor, 69730305) > 0 || SkillTable.getAbnormalLevel(_thisActor, 87556097) > 0))
					{
						if(debug)
						{
							Functions.npcSay(_thisActor, Say2C.ALL, "Magic block");
						}
					}
					else if(SkillTable.mpConsume(384958465) < _thisActor.getCurrentMp() && SkillTable.hpConsume(384958465) < _thisActor.getCurrentHp() && !_thisActor.isSkillDisabledEx(384958465))
					{
						if(debug)
						{
							Functions.npcSay(_thisActor, Say2C.ALL, "Cast " + SkillTable.getInstance().getInfo(384958465).getId() + " skill");
						}
						addUseSkillDesire(target, SkillTable.getInstance().getInfo(384958465), 0, 1, (long) (1000000 * UseSkill_BoostValue));
					}
					else if(debug)
					{
						Functions.npcSay(_thisActor, Say2C.ALL, "No skill condition");
					}
				}
				else if(SkillTable.getAbnormalLevel(target, 384958465) < 2)
				{
					if(SkillTable.isMagic(384958466) == 0 && (SkillTable.getAbnormalLevel(_thisActor, 23134209) > 0 || SkillTable.getAbnormalLevel(_thisActor, 87556097) > 0))
					{
						if(debug)
						{
							Functions.npcSay(_thisActor, Say2C.ALL, "Phys defence");
						}
					}
					else if(SkillTable.isMagic(384958466) == 1 && (SkillTable.getAbnormalLevel(_thisActor, 69730305) > 0 || SkillTable.getAbnormalLevel(_thisActor, 87556097) > 0))
					{
						if(debug)
						{
							Functions.npcSay(_thisActor, Say2C.ALL, "Magic block");
						}
					}
					else if(SkillTable.mpConsume(384958466) < _thisActor.getCurrentMp() && SkillTable.hpConsume(384958466) < _thisActor.getCurrentHp() && !_thisActor.isSkillDisabledEx(384958466))
					{
						if(debug)
						{
							Functions.npcSay(_thisActor, Say2C.ALL, "Cast " + SkillTable.getInstance().getInfo(384958466).getId() + " skill");
						}
						addUseSkillDesire(target, SkillTable.getInstance().getInfo(384958466), 0, 1, (long) (1000000 * UseSkill_BoostValue));
					}
					else if(debug)
					{
						Functions.npcSay(_thisActor, Say2C.ALL, "No skill condition");
					}
				}
				else if(SkillTable.getAbnormalLevel(target, 384958465) < 3)
				{
					if(SkillTable.isMagic(384958467) == 0 && (SkillTable.getAbnormalLevel(_thisActor, 23134209) > 0 || SkillTable.getAbnormalLevel(_thisActor, 87556097) > 0))
					{
						if(debug)
						{
							Functions.npcSay(_thisActor, Say2C.ALL, "Phys defence");
						}
					}
					else if(SkillTable.isMagic(384958467) == 1 && (SkillTable.getAbnormalLevel(_thisActor, 69730305) > 0 || SkillTable.getAbnormalLevel(_thisActor, 87556097) > 0))
					{
						if(debug)
						{
							Functions.npcSay(_thisActor, Say2C.ALL, "Magic block");
						}
					}
					else if(SkillTable.mpConsume(384958467) < _thisActor.getCurrentMp() && SkillTable.hpConsume(384958467) < _thisActor.getCurrentHp() && !_thisActor.isSkillDisabledEx(384958467))
					{
						if(debug)
						{
							Functions.npcSay(_thisActor, Say2C.ALL, "Cast " + SkillTable.getInstance().getInfo(384958467).getId() + " skill");
						}
						addUseSkillDesire(target, SkillTable.getInstance().getInfo(384958467), 0, 1, (long) (1000000 * UseSkill_BoostValue));
					}
					else if(debug)
					{
						Functions.npcSay(_thisActor, Say2C.ALL, "No skill condition");
					}
				}
				else if(SkillTable.getAbnormalLevel(target, 384958465) < 4)
				{
					if(SkillTable.isMagic(384958468) == 0 && (SkillTable.getAbnormalLevel(_thisActor, 23134209) > 0 || SkillTable.getAbnormalLevel(_thisActor, 87556097) > 0))
					{
						if(debug)
						{
							Functions.npcSay(_thisActor, Say2C.ALL, "Phys defence");
						}
					}
					else if(SkillTable.isMagic(384958468) == 1 && (SkillTable.getAbnormalLevel(_thisActor, 69730305) > 0 || SkillTable.getAbnormalLevel(_thisActor, 87556097) > 0))
					{
						if(debug)
						{
							Functions.npcSay(_thisActor, Say2C.ALL, "Magic block");
						}
					}
					else if(SkillTable.mpConsume(384958468) < _thisActor.getCurrentMp() && SkillTable.hpConsume(384958468) < _thisActor.getCurrentHp() && !_thisActor.isSkillDisabledEx(384958468))
					{
						if(debug)
						{
							Functions.npcSay(_thisActor, Say2C.ALL, "Cast " + SkillTable.getInstance().getInfo(384958468).getId() + " skill");
						}
						addUseSkillDesire(target, SkillTable.getInstance().getInfo(384958468), 0, 1, (long) (1000000 * UseSkill_BoostValue));
					}
					else if(debug)
					{
						Functions.npcSay(_thisActor, Say2C.ALL, "No skill condition");
					}
				}
				else if(SkillTable.getAbnormalLevel(target, 384958465) < 5)
				{
					if(SkillTable.isMagic(384958469) == 0 && (SkillTable.getAbnormalLevel(_thisActor, 23134209) > 0 || SkillTable.getAbnormalLevel(_thisActor, 87556097) > 0))
					{
						if(debug)
						{
							Functions.npcSay(_thisActor, Say2C.ALL, "Phys defence");
						}
					}
					else if(SkillTable.isMagic(384958469) == 1 && (SkillTable.getAbnormalLevel(_thisActor, 69730305) > 0 || SkillTable.getAbnormalLevel(_thisActor, 87556097) > 0))
					{
						if(debug)
						{
							Functions.npcSay(_thisActor, Say2C.ALL, "Magic block");
						}
					}
					else if(SkillTable.mpConsume(384958469) < _thisActor.getCurrentMp() && SkillTable.hpConsume(384958469) < _thisActor.getCurrentHp() && !_thisActor.isSkillDisabledEx(384958469))
					{
						if(debug)
						{
							Functions.npcSay(_thisActor, Say2C.ALL, "Cast " + SkillTable.getInstance().getInfo(384958469).getId() + " skill");
						}
						addUseSkillDesire(target, SkillTable.getInstance().getInfo(384958469), 0, 1, (long) (1000000 * UseSkill_BoostValue));
					}
					else if(debug)
					{
						Functions.npcSay(_thisActor, Say2C.ALL, "No skill condition");
					}
				}
			}
		}
	}

	@Override
	protected void onEvtNodeArrived(SuperpointNode node)
	{
		if(node.getNodeId() == 14)
		{
			DefaultMaker maker0 = _thisActor.getMyMaker();
			if(maker0 != null)
			{
				maker0.onScriptEvent(78010073, 0, 0);
			}
			_thisActor.onDecay();
		}
		else if(_thisActor.i_ai1 == 1)
		{
			addMoveSuperPointDesire(SuperPointName1, SuperPointMethod, SuperPointDesire);
		}
		else if(_thisActor.i_ai1 == 2)
		{
			addMoveSuperPointDesire(SuperPointName2, SuperPointMethod, SuperPointDesire);
		}
	}

	@Override
	protected void onEvtDieSet(L2Character talker)
	{
		if(type.equals("boss_marching"))
		{
			return;
		}
		if(talker != null && talker.isDead() && talker.isPlayer())
		{
			if(debug)
			{
				Functions.npcSay(_thisActor, Say2C.ALL, "Player died: " + talker.getName());
			}
			if((type.equals("dealer") || type.equals("debuffer")) && _thisActor.getCurrentHp() <= _thisActor.getMaxHp() * 0.300000)
			{
				if(SkillTable.isMagic(386203649) == 0 && (SkillTable.getAbnormalLevel(_thisActor, 23134209) > 0 || SkillTable.getAbnormalLevel(_thisActor, 87556097) > 0))
				{
					if(debug)
					{
						Functions.npcSay(_thisActor, Say2C.ALL, "Phys defence");
					}
				}
				else if(SkillTable.isMagic(386203649) == 1 && (SkillTable.getAbnormalLevel(_thisActor, 69730305) > 0 || SkillTable.getAbnormalLevel(_thisActor, 87556097) > 0))
				{
					if(debug)
					{
						Functions.npcSay(_thisActor, Say2C.ALL, "Magic block");
					}
				}
				else if(SkillTable.mpConsume(386203649) < _thisActor.getCurrentMp() && SkillTable.hpConsume(386203649) < _thisActor.getCurrentHp() && !_thisActor.isSkillDisabledEx(386203649))
				{
					if(debug)
					{
						Functions.npcSay(_thisActor, Say2C.ALL, "Cast " + SkillTable.getInstance().getInfo(386203649).getId() + " skill");
					}
					addUseSkillDesire(talker, SkillTable.getInstance().getInfo(386203649), 1, 1, (long) (Maximum_Hate * UseSkill_BoostValue));
				}
				else if(debug)
				{
					Functions.npcSay(_thisActor, Say2C.ALL, "No skill condition");
				}
			}
			else if(type.equals("cc") && talker.isPlayer())
			{
				if(SkillTable.isMagic(387186689) == 0 && (SkillTable.getAbnormalLevel(_thisActor, 23134209) > 0 || SkillTable.getAbnormalLevel(_thisActor, 87556097) > 0))
				{
					if(debug)
					{
						Functions.npcSay(_thisActor, Say2C.ALL, "Phys defence");
					}
				}
				else if(SkillTable.isMagic(387186689) == 1 && (SkillTable.getAbnormalLevel(_thisActor, 69730305) > 0 || SkillTable.getAbnormalLevel(_thisActor, 87556097) > 0))
				{
					if(debug)
					{
						Functions.npcSay(_thisActor, Say2C.ALL, "Magic block");
					}
				}
				else if(SkillTable.mpConsume(387186689) < _thisActor.getCurrentMp() && SkillTable.hpConsume(387186689) < _thisActor.getCurrentHp() && !_thisActor.isSkillDisabledEx(387186689))
				{
					if(debug)
					{
						Functions.npcSay(_thisActor, Say2C.ALL, "Cast " + SkillTable.getInstance().getInfo(387186689).getId() + " skill");
					}
					addUseSkillDesire(talker, SkillTable.getInstance().getInfo(387186689), 1, 1, (long) (Maximum_Hate * UseSkill_BoostValue));
				}
				else if(debug)
				{
					Functions.npcSay(_thisActor, Say2C.ALL, "No skill condition");
				}
			}
		}
	}

	@Override
	protected void onEvtClanDead(L2NpcInstance victim)
	{
		if(type.equals("dot") && victim != null)
		{
			if(debug)
			{
				Functions.npcSay(_thisActor, Say2C.ALL, "NPC dead: " + victim.getName());
			}
			if(SkillTable.isMagic(387121153) == 0 && (SkillTable.getAbnormalLevel(_thisActor, 23134209) > 0 || SkillTable.getAbnormalLevel(_thisActor, 87556097) > 0))
			{
				if(debug)
				{
					Functions.npcSay(_thisActor, Say2C.ALL, "Phys defence");
				}
			}
			else if(SkillTable.isMagic(387121153) == 1 && (SkillTable.getAbnormalLevel(_thisActor, 69730305) > 0 || SkillTable.getAbnormalLevel(_thisActor, 87556097) > 0))
			{
				if(debug)
				{
					Functions.npcSay(_thisActor, Say2C.ALL, "Magic block");
				}
			}
			else if(SkillTable.mpConsume(387121153) < _thisActor.getCurrentMp() && SkillTable.hpConsume(387121153) < _thisActor.getCurrentHp() && !_thisActor.isSkillDisabledEx(387121153))
			{
				if(debug)
				{
					Functions.npcSay(_thisActor, Say2C.ALL, "Cast " + SkillTable.getInstance().getInfo(387121153).getId() + " skill");
				}
				addUseSkillDesire(victim, SkillTable.getInstance().getInfo(387121153), 1, 1, (long) (Maximum_Hate * UseSkill_BoostValue));
			}
			else if(debug)
			{
				Functions.npcSay(_thisActor, Say2C.ALL, "No skill condition");
			}
		}
	}

	@Override
	protected void onEvtOutOfMyTerritory()
	{
	}

	@Override
	protected boolean createNewTask()
	{
		return true;
	}
}
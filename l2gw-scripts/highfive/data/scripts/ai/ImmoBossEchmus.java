package ai;

import ai.base.MonsterBehavior;
import ru.l2gw.extensions.scripts.Functions;
import ru.l2gw.gameserver.ai.CtrlIntention;
import ru.l2gw.gameserver.clientpackets.Say2C;
import ru.l2gw.gameserver.instancemanager.InstanceManager;
import ru.l2gw.gameserver.model.*;
import ru.l2gw.gameserver.model.entity.category.CategoryManager;
import ru.l2gw.gameserver.model.npcmaker.DefaultMaker;
import ru.l2gw.gameserver.tables.SkillTable;
import ru.l2gw.commons.arrays.GCSArray;
import ru.l2gw.commons.math.Rnd;
import ru.l2gw.util.Util;

/**
 * @author: rage
 * @date: 17.12.11 14:55
 */
public class ImmoBossEchmus extends MonsterBehavior
{
	public float DefaultHate = 100.000000f;
	public float Maximum_Hate = 999999984306749440.000000f;
	public int HateClassGroup1 = 3;
	public float HateClassGroup1Boost = 20.000000f;
	public int HateClassGroup2 = 4;
	public float HateClassGroup2Boost = 10.000000f;
	public int FavorClassGroup1 = 5;
	public float FavorClassGroup1Boost = 10.000000f;
	public float SEE_CREATURE_Weight_Point = 1.000000f;
	public float ATTACKED_Weight_Point = 10.000000f;
	public float CLAN_ATTACKED_Weight_Point = 0.000000f;
	public float PARTY_ATTACKED_Weight_Point = 0.000000f;
	public float SEE_SPELL_Weight_Point = 13.000000f;
	public float HATE_SKILL_Weight_Point = 30.000000f;
	public float TUMOR_ATTACKED_Weight_Point = 0.000000f;
	public float VEIN_SIGNAL_Weight_Point = 0.000000f;
	public float TUMOR_HELP_Weight_Point = 0.000000f;
	public float LIFESEED_TAUNT_Weight_Point = 0.000000f;
	public L2Skill Skill01_ID = SkillTable.getInstance().getInfo(386596866);
	public L2Skill Skill02_1_ID = SkillTable.getInstance().getInfo(386531330);
	public L2Skill Skill02_2_ID = SkillTable.getInstance().getInfo(388038657);
	public L2Skill Skill02_3_ID = SkillTable.getInstance().getInfo(388104193);
	public L2Skill Skill03_1_ID = SkillTable.getInstance().getInfo(386334721);
	public L2Skill Skill03_2_ID = SkillTable.getInstance().getInfo(386269185);
	public L2Skill Skill04_1_ID = SkillTable.getInstance().getInfo(386924547);
	public L2Skill Skill04_2_ID = SkillTable.getInstance().getInfo(387973121);
	public int Skillchance_High = 30;
	public int Skillchance_Low = 15;
	public int Skillchance_Dim = 4;
	public L2Skill Skill_Retain_1 = SkillTable.getInstance().getInfo(388169729);
	public L2Skill Skill_Retain_2 = SkillTable.getInstance().getInfo(394526721);
	public L2Skill Skill_Retain_3 = SkillTable.getInstance().getInfo(394592257);
	public L2Skill Skill_Retain_4 = SkillTable.getInstance().getInfo(394657793);
	public L2Skill Skill_Retain_5 = SkillTable.getInstance().getInfo(394723329);
	public L2Skill Skill_Obey = SkillTable.getInstance().getInfo(387907585);
	public L2Skill Skill_Husk = SkillTable.getInstance().getInfo(387055617);
	public L2Skill Skill_HPBuff = SkillTable.getInstance().getInfo(458752001);
	public int victim_voidhound = 29151;
	public String victim_voidhound_class = "ImmoBossVoidhound";
	public L2Skill Skill_AntiParty_atk01 = SkillTable.getInstance().getInfo(388431873);
	public L2Skill Skill_AntiParty_atk02 = SkillTable.getInstance().getInfo(388431874);
	public L2Skill Skill_AntiParty_atk03 = SkillTable.getInstance().getInfo(388431875);
	public L2Skill Skill_AntiParty_atk04 = SkillTable.getInstance().getInfo(388431876);
	public L2Skill Skill_AntiParty_def01 = SkillTable.getInstance().getInfo(388497409);
	public L2Skill Skill_AntiParty_def02 = SkillTable.getInstance().getInfo(388497410);
	public L2Skill Skill_AntiParty_def03 = SkillTable.getInstance().getInfo(388497411);
	public L2Skill Skill_AntiParty_def04 = SkillTable.getInstance().getInfo(388497412);
	public int victim_husk1 = 18715;
	public String victim_husk1_class = "ImmoPetObedience";
	public int victim_husk2 = 18717;
	public String victim_husk2_class = "ImmoPetObedience";
	public int victim_husk3 = 18716;
	public String victim_husk3_class = "ImmoPetObedience";
	public String ech_atk_expel_maker = "rumwarsha15_1424_expelm1";
	public String z3_a_dispatcher_maker = "rumwarsha15_1424_a_dispm1";
	public int tide = 0;
	public String type = "echmus";
	public int TM_ATTACK_COOLDOWN = 78001;
	public int TIME_ATTACK_COOLDOWN_MELEE = 5;
	public int TIME_ATTACK_COOLDOWN_CASTER = 1;
	public int TM_IDLE_LONG_1 = 78011;
	public int TIME_IDLE_LONG_1 = 30;
	public int TM_IDLE_LONG_2 = 78012;
	public int TIME_IDLE_LONG_2 = 180;
	private GCSArray<Integer> int_list = new GCSArray<>();

	public ImmoBossEchmus(L2Character actor)
	{
		super(actor);
		IsAggressive = 1;
		Aggressive_Time = 1.000000f;
		Attack_DecayRatio = 6.600000f;
		UseSkill_DecayRatio = 66000.000000f;
		Attack_BoostValue = 300.000000f;
		UseSkill_BoostValue = 1000000.000000f;
		Party_Type = 2;
	}

	@Override
	protected void onEvtSpawn()
	{
		_thisActor.i_ai0 = 0;
		_thisActor.i_ai1 = 0;
		_thisActor.i_ai3 = 0;
		_thisActor.i_ai4 = 0;
		_thisActor.i_ai2 = 0;
		_thisActor.i_quest0 = 0;
		_thisActor.c_ai0 = 0;
		_thisActor.createOnePrivate(victim_voidhound, victim_voidhound_class, 0, 0, -179686, 208854, -15496, 16384, 0, 0, 0);
		_thisActor.createOnePrivate(victim_voidhound, victim_voidhound_class, 0, 0, -179387, 208854, -15496, 16384, 0, 0, 0);
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
	protected void onEvtNoDesire()
	{
		_thisActor.removeAllHateInfoIF(1, 0);
		_thisActor.removeAllHateInfoIF(3, 3000);
		if(_thisActor.getAggroListSize() == 0 && _intention != CtrlIntention.AI_INTENTION_ATTACK)
		{
			addMoveAroundDesire(5, 5);
			_thisActor.lookNeighbor(1000);
			if(_thisActor.i_ai1 == 0)
			{
				addTimer(TM_IDLE_LONG_1, TIME_IDLE_LONG_1 * 1000);
				_thisActor.i_ai1 = 1;
			}
		}
		else if(_thisActor.getAggroListSize() != 0)
		{
			L2Character c0 = _thisActor.getMostHated();
			if(c0 != null && _thisActor.getHate(c0) > 0)
			{
				if(Skill01_ID.getMpConsume() < _thisActor.getCurrentMp() && Skill01_ID.getHpConsume() < _thisActor.getCurrentHp())
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
	}

	@Override
	protected void onEvtSeeCreature(L2Character creature)
	{
		if(_thisActor.getLifeTime() < Aggressive_Time)
		{
			return;
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
		super.onEvtSeeCreature(creature);
	}

	@Override
	protected void onEvtAttacked(L2Character attacker, int damage, L2Skill skill)
	{
		if(attacker == null)
			return;

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
		if(attacker != null && (attacker.isPlayer() || CategoryManager.isInCategory(12, attacker.getNpcId())))
		{
			L2Party party0 = Util.getParty(attacker);

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
				if(!int_list.contains(party0.getPartyId()))
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
						Functions.broadcastOnScreenMsgFStr(_thisActor, 8000, 5, 0, 1, 4, 1, 1, 5000, 0, 1800249);
						Functions.broadcastSystemMessageFStr(_thisActor, 8000, 1800249);
					}
					else
					{
						Functions.npcSay(_thisActor, Say2C.SHOUT, 1800256);
						removeAllAttackDesire();
						if(SkillTable.getAbnormalLevel(_thisActor, Skill_AntiParty_atk01) < 1 && _thisActor.getCurrentHp() <= _thisActor.getMaxHp() * 0.600000)
						{
							if(Skill_AntiParty_atk01.getMpConsume() < _thisActor.getCurrentMp())
							{
								addUseSkillDesire(_thisActor, Skill_AntiParty_atk01, 1, 1, (long) Maximum_Hate);
							}
						}
						else if(SkillTable.getAbnormalLevel(_thisActor, Skill_AntiParty_atk01) < 2 && _thisActor.getCurrentHp() <= _thisActor.getMaxHp() * 0.300000)
						{
							if(Skill_AntiParty_atk02.getMpConsume() < _thisActor.getCurrentMp())
							{
								addUseSkillDesire(_thisActor, Skill_AntiParty_atk02, 1, 1, (long) Maximum_Hate);
							}
						}
						if(SkillTable.getAbnormalLevel(_thisActor, Skill_AntiParty_def01) < 1 && _thisActor.getCurrentHp() <= _thisActor.getMaxHp() * 0.600000)
						{
							if(Skill_AntiParty_def01.getMpConsume() < _thisActor.getCurrentMp())
							{
								addUseSkillDesire(_thisActor, Skill_AntiParty_def01, 1, 1, (long) Maximum_Hate);
							}
						}
						else if(SkillTable.getAbnormalLevel(_thisActor, Skill_AntiParty_def01) < 2 && _thisActor.getCurrentHp() <= _thisActor.getMaxHp() * 0.300000)
						{
							if(Skill_AntiParty_def02.getMpConsume() < _thisActor.getCurrentMp())
							{
								addUseSkillDesire(_thisActor, Skill_AntiParty_def02, 1, 1, (long) Maximum_Hate);
							}
						}
					}
					int_list.add(party0.getPartyId());
				}
			}
			else if(party0 == null && int_list.size() > 0)
			{
				if(!int_list.contains(attacker.getObjectId()))
				{
					if(_thisActor.getNpcId() == 29150)
					{
						Functions.broadcastOnScreenMsgFStr(_thisActor, 8000, 5, 0, 1, 4, 1, 1, 5000, 0, 1800249);
						Functions.broadcastSystemMessageFStr(_thisActor, 8000, 1800249);
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
								addUseSkillDesire(_thisActor, Skill_AntiParty_atk01, 1, 1, (long) Maximum_Hate);
							}
						}
						else if(SkillTable.getAbnormalLevel(_thisActor, Skill_AntiParty_atk01) < 2 && _thisActor.getCurrentHp() <= _thisActor.getMaxHp() * 0.300000)
						{
							if(Skill_AntiParty_atk02.getMpConsume() < _thisActor.getCurrentMp())
							{
								addUseSkillDesire(_thisActor, Skill_AntiParty_atk02, 1, 1, (long) Maximum_Hate);
							}
						}
						if(SkillTable.getAbnormalLevel(_thisActor, Skill_AntiParty_def01) < 1 && _thisActor.getCurrentHp() <= _thisActor.getMaxHp() * 0.600000)
						{
							if(Skill_AntiParty_def01.getMpConsume() < _thisActor.getCurrentMp())
							{
								addUseSkillDesire(_thisActor, Skill_AntiParty_def01, 1, 1, (long) Maximum_Hate);
							}
						}
						else if(SkillTable.getAbnormalLevel(_thisActor, Skill_AntiParty_def01) < 2 && _thisActor.getCurrentHp() <= _thisActor.getMaxHp() * 0.300000)
						{
							if(Skill_AntiParty_def02.getMpConsume() < _thisActor.getCurrentMp())
							{
								addUseSkillDesire(_thisActor, Skill_AntiParty_def02, 1, 1, (long) Maximum_Hate);
							}
						}
					}
					int_list.add(attacker.getObjectId());
				}
			}
			if(party0 != null)
			{
				float f1 = party0.getMemberCount() * SEE_CREATURE_Weight_Point;
				for(L2Player c0 : party0.getPartyMembers())
				{
					if(c0 != null)
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
							_thisActor.addDamageHate(c0, 0, (long) (f0 * f1 + Attack_BoostValue));
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
		if(Skill_HPBuff != null)
		{
			if(_thisActor.i_ai0 <= 0 && _thisActor.getCurrentHp() <= _thisActor.getMaxHp() * 0.900000 && (type.equals("solo_boss") || type.equals("duo_boss") || type.equals("boss_marching")))
			{
				_thisActor.i_ai0 = 1;
				if(Skill_HPBuff.getIndex() == 387645441)
				{
					if(SkillTable.mpConsume(387645441) < _thisActor.getCurrentMp())
					{
						addUseSkillDesire(_thisActor, 387645441, 1, 1, (long) Maximum_Hate);
					}
				}
				else if(Skill_HPBuff.getIndex() == 387710977)
				{
					if(SkillTable.mpConsume(387710977) < _thisActor.getCurrentMp())
					{
						addUseSkillDesire(_thisActor, 387710977, 1, 1, (long) Maximum_Hate);
					}
				}
				else if(Skill_HPBuff.getIndex() == 387776513)
				{
					if(SkillTable.mpConsume(387776513) < _thisActor.getCurrentMp())
					{
						addUseSkillDesire(_thisActor, 387776513, 1, 1, (long) Maximum_Hate);
					}
				}
				else if(Skill_HPBuff.getIndex() == 392232961)
				{
					if(SkillTable.mpConsume(392232961) < _thisActor.getCurrentMp())
					{
						addUseSkillDesire(_thisActor, 392232961, 1, 1, (long) Maximum_Hate);
					}
				}
				else if(Skill_HPBuff.getIndex() == 392298497)
				{
					if(SkillTable.mpConsume(392298497) < _thisActor.getCurrentMp())
					{
						addUseSkillDesire(_thisActor, 392298497, 1, 1, (long) Maximum_Hate);
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
						addUseSkillDesire(_thisActor, 387645442, 1, 1, (long) Maximum_Hate);
					}
				}
				else if(Skill_HPBuff.getIndex() == 387710977)
				{
					if(SkillTable.mpConsume(387710978) < _thisActor.getCurrentMp())
					{
						addUseSkillDesire(_thisActor, 387710978, 1, 1, (long) Maximum_Hate);
					}
				}
				else if(Skill_HPBuff.getIndex() == 387776513)
				{
					if(SkillTable.mpConsume(387776514) < _thisActor.getCurrentMp())
					{
						addUseSkillDesire(_thisActor, 387776514, 1, 1, (long) Maximum_Hate);
					}
				}
				else if(Skill_HPBuff.getIndex() == 392232961)
				{
					if(SkillTable.mpConsume(392232962) < _thisActor.getCurrentMp())
					{
						addUseSkillDesire(_thisActor, 392232962, 1, 1, (long) Maximum_Hate);
					}
				}
				else if(Skill_HPBuff.getIndex() == 392298497)
				{
					if(SkillTable.mpConsume(392298498) < _thisActor.getCurrentMp())
					{
						addUseSkillDesire(_thisActor, 392298498, 1, 1, (long) Maximum_Hate);
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
						addUseSkillDesire(_thisActor, 387645443, 1, 1, (long) Maximum_Hate);
					}
				}
				else if(Skill_HPBuff.getIndex() == 387710977)
				{
					if(SkillTable.mpConsume(387710979) < _thisActor.getCurrentMp())
					{
						addUseSkillDesire(_thisActor, 387710979, 1, 1, (long) Maximum_Hate);
					}
				}
				else if(Skill_HPBuff.getIndex() == 387776513)
				{
					if(SkillTable.mpConsume(387776515) < _thisActor.getCurrentMp())
					{
						addUseSkillDesire(_thisActor, 387776515, 1, 1, (long) Maximum_Hate);
					}
				}
				else if(Skill_HPBuff.getIndex() == 392232961)
				{
					if(SkillTable.mpConsume(392232963) < _thisActor.getCurrentMp())
					{
						addUseSkillDesire(_thisActor, 392232963, 1, 1, (long) Maximum_Hate);
					}
				}
				else if(Skill_HPBuff.getIndex() == 392298497)
				{
					if(SkillTable.mpConsume(392298499) < _thisActor.getCurrentMp())
					{
						addUseSkillDesire(_thisActor, 392298499, 1, 1, (long) Maximum_Hate);
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
						addUseSkillDesire(_thisActor, 387645444, 1, 1, (long) Maximum_Hate);
					}
				}
				else if(Skill_HPBuff.getIndex() == 387710977)
				{
					if(SkillTable.mpConsume(387710980) < _thisActor.getCurrentMp())
					{
						addUseSkillDesire(_thisActor, 387710980, 1, 1, (long) Maximum_Hate);
					}
				}
				else if(Skill_HPBuff.getIndex() == 387776513)
				{
					if(SkillTable.mpConsume(387776516) < _thisActor.getCurrentMp())
					{
						addUseSkillDesire(_thisActor, 387776516, 1, 1, (long) Maximum_Hate);
					}
				}
				else if(Skill_HPBuff.getIndex() == 392232961)
				{
					if(SkillTable.mpConsume(392232964) < _thisActor.getCurrentMp())
					{
						addUseSkillDesire(_thisActor, 392232964, 1, 1, (long) Maximum_Hate);
					}
				}
				else if(Skill_HPBuff.getIndex() == 392298497)
				{
					if(SkillTable.mpConsume(392298500) < _thisActor.getCurrentMp())
					{
						addUseSkillDesire(_thisActor, 392298500, 1, 1, (long) Maximum_Hate);
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
						addUseSkillDesire(_thisActor, 387645445, 1, 1, (long) Maximum_Hate);
					}
				}
				else if(Skill_HPBuff.getIndex() == 387710977)
				{
					if(SkillTable.mpConsume(387710981) < _thisActor.getCurrentMp())
					{
						addUseSkillDesire(_thisActor, 387710981, 1, 1, (long) Maximum_Hate);
					}
				}
				else if(Skill_HPBuff.getIndex() == 387776513)
				{
					if(SkillTable.mpConsume(387776517) < _thisActor.getCurrentMp())
					{
						addUseSkillDesire(_thisActor, 387776517, 1, 1, (long) Maximum_Hate);
					}
				}
				else if(Skill_HPBuff.getIndex() == 392232961)
				{
					if(SkillTable.mpConsume(392232965) < _thisActor.getCurrentMp())
					{
						addUseSkillDesire(_thisActor, 392232965, 1, 1, (long) Maximum_Hate);
					}
				}
				else if(Skill_HPBuff.getIndex() == 392298497)
				{
					if(SkillTable.mpConsume(392298501) < _thisActor.getCurrentMp())
					{
						addUseSkillDesire(_thisActor, 392298501, 1, 1, (long) Maximum_Hate);
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
						addUseSkillDesire(_thisActor, 387645446, 1, 1, (long) Maximum_Hate);
					}
				}
				else if(Skill_HPBuff.getIndex() == 387710977)
				{
					if(SkillTable.mpConsume(387710982) < _thisActor.getCurrentMp())
					{
						addUseSkillDesire(_thisActor, 387710982, 1, 1, (long) Maximum_Hate);
					}
				}
				else if(Skill_HPBuff.getIndex() == 387776513)
				{
					if(SkillTable.mpConsume(387776518) < _thisActor.getCurrentMp())
					{
						addUseSkillDesire(_thisActor, 387776518, 1, 1, (long) Maximum_Hate);
					}
				}
				else if(Skill_HPBuff.getIndex() == 392232961)
				{
					if(SkillTable.mpConsume(392232966) < _thisActor.getCurrentMp())
					{
						addUseSkillDesire(_thisActor, 392232966, 1, 1, (long) Maximum_Hate);
					}
				}
				else if(Skill_HPBuff.getIndex() == 392298497)
				{
					if(SkillTable.mpConsume(392298502) < _thisActor.getCurrentMp())
					{
						addUseSkillDesire(_thisActor, 392298502, 1, 1, (long) Maximum_Hate);
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
						addUseSkillDesire(_thisActor, 387645447, 1, 1, (long) Maximum_Hate);
					}
				}
				else if(Skill_HPBuff.getIndex() == 387710977)
				{
					if(SkillTable.mpConsume(387710983) < _thisActor.getCurrentMp())
					{
						addUseSkillDesire(_thisActor, 387710983, 1, 1, (long) Maximum_Hate);
					}
				}
				else if(Skill_HPBuff.getIndex() == 387776513)
				{
					if(SkillTable.mpConsume(387776519) < _thisActor.getCurrentMp())
					{
						addUseSkillDesire(_thisActor, 387776519, 1, 1, (long) Maximum_Hate);
					}
				}
				else if(Skill_HPBuff.getIndex() == 392232961)
				{
					if(SkillTable.mpConsume(392232967) < _thisActor.getCurrentMp())
					{
						addUseSkillDesire(_thisActor, 392232967, 1, 1, (long) Maximum_Hate);
					}
				}
				else if(Skill_HPBuff.getIndex() == 392298497)
				{
					if(SkillTable.mpConsume(392298503) < _thisActor.getCurrentMp())
					{
						addUseSkillDesire(_thisActor, 392298503, 1, 1, (long) Maximum_Hate);
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
						addUseSkillDesire(_thisActor, 387645448, 1, 1, (long) Maximum_Hate);
					}
				}
				else if(Skill_HPBuff.getIndex() == 387710977)
				{
					if(SkillTable.mpConsume(387710984) < _thisActor.getCurrentMp())
					{
						addUseSkillDesire(_thisActor, 387710984, 1, 1, (long) Maximum_Hate);
					}
				}
				else if(Skill_HPBuff.getIndex() == 387776513)
				{
					if(SkillTable.mpConsume(387776520) < _thisActor.getCurrentMp())
					{
						addUseSkillDesire(_thisActor, 387776520, 1, 1, (long) Maximum_Hate);
					}
				}
				else if(Skill_HPBuff.getIndex() == 392232961)
				{
					if(SkillTable.mpConsume(392232968) < _thisActor.getCurrentMp())
					{
						addUseSkillDesire(_thisActor, 392232968, 1, 1, (long) Maximum_Hate);
					}
				}
				else if(Skill_HPBuff.getIndex() == 392298497)
				{
					if(SkillTable.mpConsume(392298504) < _thisActor.getCurrentMp())
					{
						addUseSkillDesire(_thisActor, 392298504, 1, 1, (long) Maximum_Hate);
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
						addUseSkillDesire(_thisActor, 387645449, 1, 1, (long) Maximum_Hate);
					}
				}
				else if(Skill_HPBuff.getIndex() == 387710977)
				{
					if(SkillTable.mpConsume(387710985) < _thisActor.getCurrentMp())
					{
						addUseSkillDesire(_thisActor, 387710985, 1, 1, (long) Maximum_Hate);
					}
				}
				else if(Skill_HPBuff.getIndex() == 387776513)
				{
					if(SkillTable.mpConsume(387776521) < _thisActor.getCurrentMp())
					{
						addUseSkillDesire(_thisActor, 387776521, 1, 1, (long) Maximum_Hate);
					}
				}
				else if(Skill_HPBuff.getIndex() == 392232961)
				{
					if(SkillTable.mpConsume(392232969) < _thisActor.getCurrentMp())
					{
						addUseSkillDesire(_thisActor, 392232969, 1, 1, (long) Maximum_Hate);
					}
				}
				else if(Skill_HPBuff.getIndex() == 392298497)
				{
					if(SkillTable.mpConsume(392298505) < _thisActor.getCurrentMp())
					{
						addUseSkillDesire(_thisActor, 392298505, 1, 1, (long) Maximum_Hate);
					}
				}
			}
		}
		super.onEvtAttacked(attacker, damage, skill);
	}

	@Override
	protected void onEvtClanAttacked(L2Character attacked_member, L2Character attacker, int damage)
	{
		if(_thisActor.getLifeTime() > Aggressive_Time && ((Party_Type == 0 || (Party_Type == 1 && Party_Loyalty == 0)) || Party_Type == 2))
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
		super.onEvtClanAttacked(attacked_member, attacker, damage);
	}

	@Override
	protected void onEvtManipulation(L2Character target, int aggro, L2Skill skill)
	{
		_thisActor.addDamageHate(target, 0, (long) (aggro * HATE_SKILL_Weight_Point));
		super.onEvtManipulation(target, aggro, skill);
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
				if(i0 <= Skillchance_Low)
				{
					if(_thisActor.i_quest0 >= 1 && _thisActor.i_quest0 <= 3)
					{
						if(SkillTable.isMagic(Skill04_1_ID) == 0 && (SkillTable.getAbnormalLevel(_thisActor, 23134209) > 0 || SkillTable.getAbnormalLevel(_thisActor, 87556097) > 0))
						{
							if(debug)
							{
								Functions.npcSay(_thisActor, Say2C.ALL, "Phys skill!");
							}
						}
						else if(SkillTable.isMagic(Skill04_1_ID) == 1 && (SkillTable.getAbnormalLevel(_thisActor, 69730305) > 0 || SkillTable.getAbnormalLevel(_thisActor, 87556097) > 0))
						{
							if(debug)
							{
								Functions.npcSay(_thisActor, Say2C.ALL, "Magic block!");
							}
						}
						else if(Skill04_1_ID.getMpConsume() < _thisActor.getCurrentMp() && Skill04_1_ID.getHpConsume() < _thisActor.getCurrentHp() && !_thisActor.isSkillDisabled(Skill04_1_ID))
						{
							if(debug)
							{
								Functions.npcSay(_thisActor, Say2C.ALL, "Cast " + (Skill04_1_ID.getId()) + " skill");
							}
							addUseSkillDesire(c0, Skill04_1_ID, 0, 1, (long) (_thisActor.getHate(c0) * UseSkill_BoostValue));
						}
						else if(debug)
						{
							Functions.npcSay(_thisActor, Say2C.ALL, "no skill condition!");
						}
					}
					else if(SkillTable.isMagic(Skill04_2_ID) == 0 && (SkillTable.getAbnormalLevel(_thisActor, 23134209) > 0 || SkillTable.getAbnormalLevel(_thisActor, 87556097) > 0))
					{
						if(debug)
						{
							Functions.npcSay(_thisActor, Say2C.ALL, "Phys skill!");
						}
					}
					else if(SkillTable.isMagic(Skill04_2_ID) == 1 && (SkillTable.getAbnormalLevel(_thisActor, 69730305) > 0 || SkillTable.getAbnormalLevel(_thisActor, 87556097) > 0))
					{
						if(debug)
						{
							Functions.npcSay(_thisActor, Say2C.ALL, "Magic block!");
						}
					}
					else if(Skill04_2_ID.getMpConsume() < _thisActor.getCurrentMp() && Skill04_2_ID.getHpConsume() < _thisActor.getCurrentHp() && !_thisActor.isSkillDisabled(Skill04_2_ID))
					{
						if(debug)
						{
							Functions.npcSay(_thisActor, Say2C.ALL, "Cast " + (Skill04_2_ID.getId()) + " skill");
						}
						addUseSkillDesire(c0, Skill04_2_ID, 0, 1, (long) (_thisActor.getHate(c0) * UseSkill_BoostValue));
					}
					else if(debug)
					{
						Functions.npcSay(_thisActor, Say2C.ALL, "no skill condition!");
					}
				}
				else if(i0 <= Skillchance_High)
				{
					if(_thisActor.i_quest0 >= 1 && _thisActor.i_quest0 <= 3)
					{
						if(SkillTable.isMagic(Skill02_1_ID) == 0 && (SkillTable.getAbnormalLevel(_thisActor, 23134209) > 0 || SkillTable.getAbnormalLevel(_thisActor, 87556097) > 0))
						{
							if(debug)
							{
								Functions.npcSay(_thisActor, Say2C.ALL, "Phys skill!");
							}
						}
						else if(SkillTable.isMagic(Skill02_1_ID) == 1 && (SkillTable.getAbnormalLevel(_thisActor, 69730305) > 0 || SkillTable.getAbnormalLevel(_thisActor, 87556097) > 0))
						{
							if(debug)
							{
								Functions.npcSay(_thisActor, Say2C.ALL, "Magic block!");
							}
						}
						else if(Skill02_1_ID.getMpConsume() < _thisActor.getCurrentMp() && Skill02_1_ID.getHpConsume() < _thisActor.getCurrentHp() && !_thisActor.isSkillDisabled(Skill02_1_ID))
						{
							if(debug)
							{
								Functions.npcSay(_thisActor, Say2C.ALL, "Cast " + (Skill02_1_ID.getId()) + " skill");
							}
							addUseSkillDesire(c0, Skill02_1_ID, 0, 1, (long) (_thisActor.getHate(c0) * UseSkill_BoostValue));
						}
						else if(debug)
						{
							Functions.npcSay(_thisActor, Say2C.ALL, "no skill condition!");
						}
					}
					else if(SkillTable.isMagic(Skill02_2_ID) == 0 && (SkillTable.getAbnormalLevel(_thisActor, 23134209) > 0 || SkillTable.getAbnormalLevel(_thisActor, 87556097) > 0))
					{
						if(debug)
						{
							Functions.npcSay(_thisActor, Say2C.ALL, "Phys skill!");
						}
					}
					else if(SkillTable.isMagic(Skill02_2_ID) == 1 && (SkillTable.getAbnormalLevel(_thisActor, 69730305) > 0 || SkillTable.getAbnormalLevel(_thisActor, 87556097) > 0))
					{
						if(debug)
						{
							Functions.npcSay(_thisActor, Say2C.ALL, "Magic block!");
						}
					}
					else if(Skill02_2_ID.getMpConsume() < _thisActor.getCurrentMp() && Skill02_2_ID.getHpConsume() < _thisActor.getCurrentHp() && !_thisActor.isSkillDisabled(Skill02_2_ID))
					{
						if(debug)
						{
							Functions.npcSay(_thisActor, Say2C.ALL, "Cast " + (Skill02_2_ID.getId()) + " skill");
						}
						addUseSkillDesire(c0, Skill02_2_ID, 0, 1, (long) (_thisActor.getHate(c0) * UseSkill_BoostValue));
					}
					else if(debug)
					{
						Functions.npcSay(_thisActor, Say2C.ALL, "no skill condition!");
					}
				}
				else if(SkillTable.isMagic(Skill01_ID) == 0 && (SkillTable.getAbnormalLevel(_thisActor, 23134209) > 0 || SkillTable.getAbnormalLevel(_thisActor, 87556097) > 0))
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
							addAttackDesire(c0, 1, DEFAULT_DESIRE);
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
					addAttackDesire(c0, 1, DEFAULT_DESIRE);
				}
			}
			addTimer(TM_ATTACK_COOLDOWN, (TIME_ATTACK_COOLDOWN_MELEE + Rnd.get(TIME_ATTACK_COOLDOWN_MELEE)) * 1000);
		}
		else if(timerId == TM_IDLE_LONG_1)
		{
			_thisActor.lookNeighbor(1000);
			if(_thisActor.getAggroListSize() == 0 && _intention != CtrlIntention.AI_INTENTION_ATTACK)
			{
				Functions.broadcastOnScreenMsgFStr(_thisActor, 8000, 5, 0, 1, 4, 1, 1, 5000, 0, 1800229, String.valueOf(TIME_IDLE_LONG_2));
				Functions.broadcastSystemMessageFStr(_thisActor, 8000, 1800229, String.valueOf(TIME_IDLE_LONG_2));
				addTimer(TM_IDLE_LONG_2, TIME_IDLE_LONG_2 * 1000);
			}
			else
			{
				_thisActor.i_ai1 = 0;
			}
		}
		else if(timerId == TM_IDLE_LONG_2)
		{
			_thisActor.lookNeighbor(1000);
			if(_thisActor.getAggroListSize() == 0 && _intention != CtrlIntention.AI_INTENTION_ATTACK)
			{
				DefaultMaker maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), ech_atk_expel_maker);
				if(maker0 != null)
				{
					maker0.onScriptEvent(78010066, 0, 0);
				}
				maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z3_a_dispatcher_maker);
				if(maker0 != null)
				{
					maker0.onScriptEvent(78010071, 0, 0);
				}
			}
			else
			{
				_thisActor.i_ai1 = 0;
			}
		}
	}

	@Override
	protected void onEvtFinishCasting(L2Skill skill)
	{
		L2Character target = _thisActor.getCastingTarget();
		if(skill == null || target == null)
			return;

		if(skill == Skill02_2_ID)
		{
			if(SkillTable.isMagic(Skill02_3_ID) == 0 && (SkillTable.getAbnormalLevel(_thisActor, 23134209) > 0 || SkillTable.getAbnormalLevel(_thisActor, 87556097) > 0))
			{
				if(debug)
				{
					Functions.npcSay(_thisActor, Say2C.ALL, "Phys skill!");
				}
			}
			else if(SkillTable.isMagic(Skill02_3_ID) == 1 && (SkillTable.getAbnormalLevel(_thisActor, 69730305) > 0 || SkillTable.getAbnormalLevel(_thisActor, 87556097) > 0))
			{
				if(debug)
				{
					Functions.npcSay(_thisActor, Say2C.ALL, "Magic block!");
				}
			}
			else if(Skill02_3_ID.getMpConsume() < _thisActor.getCurrentMp() && Skill02_3_ID.getHpConsume() < _thisActor.getCurrentHp() && !_thisActor.isSkillDisabled(Skill02_3_ID))
			{
				if(debug)
				{
					Functions.npcSay(_thisActor, Say2C.ALL, "Cast " + (Skill02_3_ID.getId()) + " skill");
				}
				addUseSkillDesire(target, Skill02_3_ID, 0, 1, (long) (Maximum_Hate * UseSkill_BoostValue));
			}
			else if(debug)
			{
				Functions.npcSay(_thisActor, Say2C.ALL, "no skill condition!");
			}
		}
		L2Character c0 = L2ObjectsStorage.getAsCharacter(_thisActor.c_ai0);
		if(skill == Skill_Husk && c0 != null)
		{
			DefaultMaker maker0 = _thisActor.getMyMaker();
			if(maker0.npc_count < maker0.maximum_npc)
			{
				if(CategoryManager.isInCategory(5, c0))
				{
					_thisActor.createOnePrivate(victim_husk1, victim_husk1_class, 0, 0, c0.getX(), c0.getY(), c0.getZ(), 0, 0, 0, 0);
				}
				else if(CategoryManager.isInCategory(1, c0))
				{
					_thisActor.createOnePrivate(victim_husk2, victim_husk2_class, 0, 0, c0.getX(), c0.getY(), c0.getZ(), 0, 0, 0, 0);
				}
				else
				{
					_thisActor.createOnePrivate(victim_husk3, victim_husk3_class, 0, 0, c0.getX(), c0.getY(), c0.getZ(), 0, 0, 0, 0);
				}
			}

			_thisActor.c_ai0 = 0;
		}
		if(skill != Skill_Obey && target.isPlayer() && target.getCurrentHp() <= target.getMaxHp() * 0.100000)
		{
			if(SkillTable.isMagic(Skill_Obey) == 0 && (SkillTable.getAbnormalLevel(_thisActor, 23134209) > 0 || SkillTable.getAbnormalLevel(_thisActor, 87556097) > 0))
			{
				if(debug)
				{
					Functions.npcSay(_thisActor, Say2C.ALL, "Phys skill!");
				}
			}
			else if(SkillTable.isMagic(Skill_Obey) == 1 && (SkillTable.getAbnormalLevel(_thisActor, 69730305) > 0 || SkillTable.getAbnormalLevel(_thisActor, 87556097) > 0))
			{
				if(debug)
				{
					Functions.npcSay(_thisActor, Say2C.ALL, "Magic block!");
				}
			}
			else if(Skill_Obey.getMpConsume() < _thisActor.getCurrentMp() && Skill_Obey.getHpConsume() < _thisActor.getCurrentHp() && !_thisActor.isSkillDisabled(Skill_Obey))
			{
				if(debug)
				{
					Functions.npcSay(_thisActor, Say2C.ALL, "Cast " + (Skill_Obey.getId()) + " skill");
				}
				addUseSkillDesire(target, Skill_Obey, 0, 1, (long) (Maximum_Hate * UseSkill_BoostValue));
			}
			else if(debug)
			{
				Functions.npcSay(_thisActor, Say2C.ALL, "no skill condition!");
			}

			target.setDieEvent(_thisActor);
		}
		super.onEvtFinishCasting(skill);
	}

	@Override
	protected void onEvtScriptEvent(int eventId, Object arg1, Object arg2)
	{
		if(eventId == 78010068 || eventId == 78010070)
		{
			removeAllAttackDesire();
			if(SkillTable.getAbnormalLevel(_thisActor, Skill_Retain_1) < 1 && _thisActor.i_quest0 == 0)
			{
				if(eventId == 78010068)
				{
					Functions.broadcastOnScreenMsgFStr(_thisActor, 8000, 5, 0, 1, 3, 1, 1, 5000, 0, 1800231);
					Functions.broadcastSystemMessageFStr(_thisActor, 8000, 1800231);
					_thisActor.i_quest0 = 1;
					addUseSkillDesire(_thisActor, Skill_Retain_1, 1, 1, (long) Maximum_Hate);
				}
			}
			else if(SkillTable.getAbnormalLevel(_thisActor, Skill_Retain_1) == 1 && _thisActor.i_quest0 == 1)
			{
				if(eventId == 78010068)
				{
					Functions.broadcastOnScreenMsgFStr(_thisActor, 8000, 5, 0, 1, 3, 1, 1, 5000, 0, 1800231);
					Functions.broadcastSystemMessageFStr(_thisActor, 8000, 1800231);
					_thisActor.i_quest0 = 2;
					addUseSkillDesire(_thisActor, Skill_Retain_2, 1, 1, (long) Maximum_Hate);
				}
				else
				{
					Functions.broadcastOnScreenMsgFStr(_thisActor, 8000, 5, 0, 1, 4, 1, 1, 5000, 0, 1800230);
					Functions.broadcastSystemMessageFStr(_thisActor, 8000, 1800230);
					_thisActor.i_quest0 = 0;
					_thisActor.dispelByAbnormal(Skill_Retain_1);
				}
			}
			else if(SkillTable.getAbnormalLevel(_thisActor, Skill_Retain_1) == 2 && _thisActor.i_quest0 == 2)
			{
				if(eventId == 78010068)
				{
					Functions.broadcastOnScreenMsgFStr(_thisActor, 8000, 5, 0, 1, 3, 1, 1, 5000, 0, 1800231);
					Functions.broadcastSystemMessageFStr(_thisActor, 8000, 1800231);
					_thisActor.i_quest0 = 3;
					addUseSkillDesire(_thisActor, Skill_Retain_3, 1, 1, (long) Maximum_Hate);
				}
				else
				{
					Functions.broadcastOnScreenMsgFStr(_thisActor, 8000, 5, 0, 1, 4, 1, 1, 5000, 0, 1800230);
					Functions.broadcastSystemMessageFStr(_thisActor, 8000, 1800230);
					_thisActor.i_quest0 = 1;
					_thisActor.dispelByAbnormal(Skill_Retain_1);
					addUseSkillDesire(_thisActor, Skill_Retain_1, 1, 1, (long) Maximum_Hate);
				}
			}
			else if(SkillTable.getAbnormalLevel(_thisActor, Skill_Retain_1) == 3 && _thisActor.i_quest0 == 3)
			{
				if(eventId == 78010068)
				{
					Functions.broadcastOnScreenMsgFStr(_thisActor, 8000, 5, 0, 1, 3, 1, 1, 5000, 0, 1800231);
					Functions.broadcastSystemMessageFStr(_thisActor, 8000, 1800231);
					_thisActor.i_quest0 = 4;
					addUseSkillDesire(_thisActor, Skill_Retain_4, 1, 1, (long) Maximum_Hate);
				}
				else
				{
					Functions.broadcastOnScreenMsgFStr(_thisActor, 8000, 5, 0, 1, 4, 1, 1, 5000, 0, 1800230);
					Functions.broadcastSystemMessageFStr(_thisActor, 8000, 1800230);
					_thisActor.i_quest0 = 2;
					_thisActor.dispelByAbnormal(Skill_Retain_1);
					addUseSkillDesire(_thisActor, Skill_Retain_2, 1, 1, (long) Maximum_Hate);
				}
			}
			else if(SkillTable.getAbnormalLevel(_thisActor, Skill_Retain_1) == 4 && _thisActor.i_quest0 == 4)
			{
				if(eventId == 78010068)
				{
					Functions.broadcastOnScreenMsgFStr(_thisActor, 8000, 5, 0, 1, 3, 1, 1, 5000, 0, 1800231);
					Functions.broadcastSystemMessageFStr(_thisActor, 8000, 1800231);
					_thisActor.i_quest0 = 5;
					addUseSkillDesire(_thisActor, Skill_Retain_5, 1, 1, (long) Maximum_Hate);
				}
				else
				{
					Functions.broadcastOnScreenMsgFStr(_thisActor, 8000, 5, 0, 1, 4, 1, 1, 5000, 0, 1800230);
					Functions.broadcastSystemMessageFStr(_thisActor, 8000, 1800230);
					_thisActor.i_quest0 = 3;
					_thisActor.dispelByAbnormal(Skill_Retain_1);
					addUseSkillDesire(_thisActor, Skill_Retain_3, 1, 1, (long) Maximum_Hate);
				}
			}
			else if(SkillTable.getAbnormalLevel(_thisActor, Skill_Retain_1) == 5 && _thisActor.i_quest0 == 5)
			{
				if(eventId == 78010068)
				{
					Functions.broadcastOnScreenMsgFStr(_thisActor, 8000, 5, 0, 1, 3, 1, 1, 5000, 0, 1800269);
					Functions.broadcastSystemMessageFStr(_thisActor, 8000, 1800269);
					_thisActor.i_quest0 = 6;
					broadcastScriptEvent(78010074, 0, _thisActor.getStoredId(), 6000);
				}
				else
				{
					Functions.broadcastOnScreenMsgFStr(_thisActor, 8000, 5, 0, 1, 4, 1, 1, 5000, 0, 1800230);
					Functions.broadcastSystemMessageFStr(_thisActor, 8000, 1800230);
					_thisActor.i_quest0 = 4;
					_thisActor.dispelByAbnormal(Skill_Retain_1);
					addUseSkillDesire(_thisActor, Skill_Retain_4, 1, 1, (long) Maximum_Hate);
				}
			}
			else if(_thisActor.i_quest0 == 6)
			{
				if(eventId == 78010070)
				{
					Functions.broadcastOnScreenMsgFStr(_thisActor, 8000, 5, 0, 1, 4, 1, 1, 5000, 0, 1800270);
					Functions.broadcastSystemMessageFStr(_thisActor, 8000, 1800270);
					_thisActor.i_quest0 = 5;
					broadcastScriptEvent(78010074, 1, _thisActor.getStoredId(), 6000);
				}
			}
		}
		else if(eventId == 78010067 && (Integer) arg1 != 0 && (Integer) arg1 != 1)
		{
			_thisActor.removeAllHateInfoIF(1, 0);
			_thisActor.removeAllHateInfoIF(3, 2000);
			_thisActor.i_ai2 = 0;
			if((Integer) arg2 == 0)
			{
				L2Party party0 = Util.getPartyFromID(_thisActor, (Integer) arg1);
				if(party0 != null)
				{
					float f1 = party0.getMemberCount() * SEE_CREATURE_Weight_Point;
					for(L2Player c0 : party0.getPartyMembers())
					{
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

					L2Player c0 = party0.getPartyLeader();
					Functions.npcSay(_thisActor, Say2C.SHOUT, 1800261, c0.getName());
					broadcastScriptEvent(78010074, 98, arg1, 6000);
				}
			}
			else if((Integer) arg2 == 1)
			{
				L2Character c0 = L2ObjectsStorage.getAsCharacter((Long) arg1);
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
					Functions.npcSay(_thisActor, Say2C.SHOUT, 1800261, c0.getName());
					broadcastScriptEvent(78010074, 99, arg1, 6000);

					int_list.add(c0.getObjectId());
				}
			}
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
			if(SkillTable.getAbnormalLevel(talker, 387907585) > 0)
			{
				DefaultMaker maker0 = _thisActor.getMyMaker();
				if(maker0.npc_count < maker0.maximum_npc)
				{
					if(SkillTable.isMagic(Skill_Husk) == 0 && (SkillTable.getAbnormalLevel(_thisActor, 23134209) > 0 || SkillTable.getAbnormalLevel(_thisActor, 87556097) > 0))
					{
						if(debug)
						{
							Functions.npcSay(_thisActor, Say2C.ALL, "Phys skill!");
						}
					}
					else if(SkillTable.isMagic(Skill_Husk) == 1 && (SkillTable.getAbnormalLevel(_thisActor, 69730305) > 0 || SkillTable.getAbnormalLevel(_thisActor, 87556097) > 0))
					{
						if(debug)
						{
							Functions.npcSay(_thisActor, Say2C.ALL, "Magic block!");
						}
					}
					else if(Skill_Husk.getMpConsume() < _thisActor.getCurrentMp() && Skill_Husk.getHpConsume() < _thisActor.getCurrentHp() && !_thisActor.isSkillDisabled(Skill_Husk))
					{
						if(debug)
						{
							Functions.npcSay(_thisActor, Say2C.ALL, "Cast " + (Skill_Husk.getId()) + " skill");
						}
						addUseSkillDesire(talker, Skill_Husk, 1, 1, (long) (Maximum_Hate * UseSkill_BoostValue));
					}
					else if(debug)
					{
						Functions.npcSay(_thisActor, Say2C.ALL, "no skill condition!");
					}
					_thisActor.c_ai0 = talker.getStoredId();
				}
				else if(debug)
				{
					Functions.npcSay(_thisActor, Say2C.ALL, "maxnpc м__н_┐ м<¤н_Ё");
				}
			}
		}
	}
}
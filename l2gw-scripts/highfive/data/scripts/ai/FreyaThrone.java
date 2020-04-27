package ai;

import ru.l2gw.commons.math.Rnd;
import ru.l2gw.extensions.scripts.Functions;
import ru.l2gw.gameserver.ai.CtrlEvent;
import ru.l2gw.gameserver.ai.DefaultAI;
import ru.l2gw.gameserver.clientpackets.Say2C;
import ru.l2gw.gameserver.model.L2Character;
import ru.l2gw.gameserver.model.L2ObjectsStorage;
import ru.l2gw.gameserver.model.L2Skill;
import ru.l2gw.gameserver.model.entity.category.CategoryManager;
import ru.l2gw.gameserver.model.instances.L2NpcInstance;
import ru.l2gw.gameserver.model.npcmaker.DefaultMaker;
import ru.l2gw.gameserver.tables.SkillTable;

/**
 * @author: rage
 * @date: 27.09.11 21:22
 */
public class FreyaThrone extends DefaultAI
{
	public L2Skill Eternal_Blizzard = SkillTable.getInstance().getInfo(411172865);
	public L2Skill Eternal_Blizzard_Hard = SkillTable.getInstance().getInfo(411238401);
	public L2Skill Ice_Ball = SkillTable.getInstance().getInfo(411435009);
	public int Ice_Ball_Prob = 3333;
	public L2Skill Summon_Elemental = SkillTable.getInstance().getInfo(411369473);
	public int Summon_Elemental_Prob = 800;
	public L2Skill Self_Nova = SkillTable.getInstance().getInfo(411500545);
	public int Self_Nova_Prob = 1500;
	public L2Skill Death_Clack = SkillTable.getInstance().getInfo(411566081);
	public int Death_Clack_Prob = 500;
	public int Death_Clack_Count = 2;
	public int Death_Clack_Count_Hard = 3;
	public L2Skill Freya_Anger = SkillTable.getInstance().getInfo(411893761);
	public int ChangeWeapon = 15471;
	public int TIMER_randomize_desire = 2314101;
	public int TIMER_battle_check = 2314102;
	public int TIMER_eternal_blizzard = 2314103;
	public int TIMER_phase1_failed = 2314104;
	public int TIMER_start_moving = 2314105;
	public int TIMER_use_freya_buff = 2314106;
	public int TIMER_enable_death_clack = 2314107;
	public int TIMER_check_evade_speech = 2314108;
	public int TIMER_freya_voice_normal = 2314109;
	public int is_hard_mode = 0;
	public int debug_mode = 0;
	public String MAKER_summoner = "schuttgart29_2314_05m1";
	public String MAKER_controller = "schuttgart29_2314_01m1";
	public String MAKER_ice_knight = "schuttgart29_2314_03m1";
	public float Attack_DecayRatio = 6.600000f;
	public float UseSkill_DecayRatio = 66000.000000f;
	public float Attack_BoostValue = 300.000000f;
	public float UseSkill_BoostValue = 100000.000000f;
	public int Dispel_Debuff = 1;

	public FreyaThrone(L2Character actor)
	{
		super(actor);
	}

	@Override
	public boolean isGlobalAI()
	{
		return true;
	}

	@Override
	protected void onEvtSpawn()
	{
		super.onEvtSpawn();
		if(is_hard_mode == 1)
		{
			if(Freya_Anger.getMpConsume() < _thisActor.getCurrentMp() && Freya_Anger.getHpConsume() < _thisActor.getCurrentHp() && !_thisActor.isSkillDisabled(Freya_Anger.getId()))
			{
				addUseSkillDesire(_thisActor, Freya_Anger, 1, 1, 1000000);
			}
			addTimer(TIMER_start_moving, 3000);
		}
		else
		{
			_thisActor.setWalking();
		}

		_thisActor.i_ai0 = 0;
		_thisActor.i_ai1 = 0;
		_thisActor.i_ai2 = (int) (System.currentTimeMillis() / 1000);
		_thisActor.i_ai3 = 0;
		_thisActor.l_ai4 = 0;
		_thisActor.i_ai5 = 0;
		_thisActor.i_ai6 = 0;
		_thisActor.i_ai7 = 0;
		_thisActor.i_ai8 = 0;

		DefaultMaker maker0 = _thisActor.getInstanceZone().getMaker(MAKER_ice_knight);
		if(maker0 != null)
		{
			maker0.onScriptEvent(23140013, getStoredIdFromCreature(_thisActor), 0);
		}
	}

	@Override
	protected void onEvtNoDesire()
	{
		if(_thisActor.i_ai1 == 1)
		{
			if((System.currentTimeMillis() / 1000) - _thisActor.i_ai2 > 300)
			{
				_thisActor.i_ai1 = 2;
				addTimer(TIMER_phase1_failed, 1);
			}
		}
	}

	@Override
	protected void onEvtManipulation(L2Character target, int aggro, L2Skill skill)
	{
		addAttackDesire(target, aggro, 0);
	}

	@Override
	protected void onEvtAttacked(L2Character attacker, int damage, L2Skill skill)
	{
		if(attacker == null)
			return;
		
		if(_thisActor.getCurrentHp() < (_thisActor.getMaxHp() * 0.020000))
		{
			if(_thisActor.i_ai6 == 0)
			{
				_thisActor.i_ai6 = 1;
				if(_thisActor.i_ai1 != 2)
				{
					DefaultMaker maker0 = _thisActor.getInstanceZone().getMaker(MAKER_controller);
					if(maker0 != null)
					{
						maker0.onScriptEvent(23140016, 1, 0);
					}
					_thisActor.i_ai1 = 3;
				}
				_thisActor.decayMe();
				_thisActor.stopMove();
				clearTasks();
				_thisActor.onDecay();
			}
		}
		else if(attacker.isPlayer() || CategoryManager.isInCategory(12, attacker.getNpcId()) || CategoryManager.isInCategory(123, attacker.getNpcId()))
		{
			_thisActor.addDamage(attacker, damage);
			if(_thisActor.i_ai1 == 0 && _thisActor.i_ai3 == 0)
			{
				if(debug_mode > 1)
				{
					Functions.npcSay(_thisActor, Say2C.SHOUT, "first_attack");
				}
				addTimer(TIMER_start_moving, 100);
				_thisActor.i_ai1 = 1;
				_thisActor.i_ai3 = 1;
				addTimer(TIMER_randomize_desire, 30000);
				if(is_hard_mode == 1)
				{
					addTimer(TIMER_use_freya_buff, 15000);
				}

				DefaultMaker maker0 = _thisActor.getInstanceZone().getMaker(MAKER_controller);
				if(maker0 != null)
				{
					maker0.onScriptEvent(23140014, 0, 0);
				}
				broadcastScriptEvent(23140010, getStoredIdFromCreature(attacker), null, 4000);
			}
			if(_thisActor.i_ai5 == 0)
			{
				_thisActor.i_ai5 = (int) (System.currentTimeMillis() / 1000);
			}

			_thisActor.i_ai2 = (int) (System.currentTimeMillis() / 1000);
			if(Rnd.get(100) < 50)
			{
				broadcastScriptEvent(23140010, getStoredIdFromCreature(attacker), null, 4000);
			}
			/*
			if(attacker.yongma_type == 1 && gg.GetAbnormalLevel(attacker, myself.Skill_GetAbnormalType(279052289)) <= 0)
			{
				if(279052289.
				getMpConsume() < _thisActor.getCurrentMp() && 279052289.
				getHpConsume() < _thisActor.getCurrentHp() && !_thisActor.isSkillDisabled(279052289.getId()))
				{
					addUseSkillDesire(attacker, 279052289, 0, 1, 1000000);
				}
			}
			*/
			if(_thisActor.i_ai0 == 0)
			{
				if(Ice_Ball != null)
				{
					if(Rnd.get(10000) < Ice_Ball_Prob)
					{
						if(_thisActor.getLoc().distance3D(attacker.getLoc()) <= 800)
						{
							if(Ice_Ball.getMpConsume() < _thisActor.getCurrentMp() && Ice_Ball.getHpConsume() < _thisActor.getCurrentHp() && !_thisActor.isSkillDisabled(Ice_Ball.getId()))
							{
								addUseSkillDesire(attacker, Ice_Ball, 0, 0, 1000000);
							}
						}
					}
				}
				if(Summon_Elemental != null)
				{
					if(Rnd.get(10000) < Summon_Elemental_Prob)
					{
						if(_thisActor.getLoc().distance3D(attacker.getLoc()) <= 800)
						{
							if(Summon_Elemental.getMpConsume() < _thisActor.getCurrentMp() && Summon_Elemental.getHpConsume() < _thisActor.getCurrentHp() && !_thisActor.isSkillDisabled(Summon_Elemental.getId()))
							{
								addUseSkillDesire(attacker, Summon_Elemental, 0, 0, 1000000);
							}
							_thisActor.l_ai4 = getStoredIdFromCreature(attacker);
						}
					}
				}
				if(_thisActor.getLoc().distance3D(attacker.getLoc()) < 350 || ((System.currentTimeMillis() / 1000) - _thisActor.i_ai5) > (60 * 3))
				{
					if(_thisActor.i_ai0 == 0)
					{
						_thisActor.c_ai0 = attacker.getStoredId();
						addTimer(TIMER_start_moving, 1);
					}
				}
			}
			else if(_thisActor.i_ai0 == 1)
			{
				addAttackDesire(attacker, 1, damage);
				if(Ice_Ball != null)
				{
					if(Rnd.get(10000) < Ice_Ball_Prob)
					{
						if(_thisActor.getMostHated() != null)
						{
							if(Rnd.get(10000) < 5000)
							{
								if(Ice_Ball.getMpConsume() < _thisActor.getCurrentMp() && Ice_Ball.getHpConsume() < _thisActor.getCurrentHp() && !_thisActor.isSkillDisabled(Ice_Ball.getId()))
								{
									addUseSkillDesire(attacker, Ice_Ball, 0, 1, 1000000);
								}
							}
							else if(Ice_Ball.getMpConsume() < _thisActor.getCurrentMp() && Ice_Ball.getHpConsume() < _thisActor.getCurrentHp() && !_thisActor.isSkillDisabled(Ice_Ball.getId()))
							{
								addUseSkillDesire(_thisActor.getMostHated(), Ice_Ball, 0, 1, 1000000);
							}
						}
					}
				}
				if(Summon_Elemental != null)
				{
					if(Rnd.get(10000) < Summon_Elemental_Prob)
					{
						if(_thisActor.getMostHated() != null)
						{
							if(Rnd.get(10000) < 5000)
							{
								if(Summon_Elemental.getMpConsume() < _thisActor.getCurrentMp() && Summon_Elemental.getHpConsume() < _thisActor.getCurrentHp() && !_thisActor.isSkillDisabled(Summon_Elemental.getId()))
								{
									addUseSkillDesire(attacker, Summon_Elemental, 0, 1, 1000000);
								}
								_thisActor.l_ai4 = getStoredIdFromCreature(attacker);
							}
							else if(Summon_Elemental.getMpConsume() < _thisActor.getCurrentMp() && Summon_Elemental.getHpConsume() < _thisActor.getCurrentHp() && !_thisActor.isSkillDisabled(Summon_Elemental.getId()))
							{
								addUseSkillDesire(_thisActor.getMostHated(), Summon_Elemental, 0, 1, 1000000);
							}
							_thisActor.l_ai4 = getStoredIdFromCreature(_thisActor.getMostHated());
						}
					}
				}
				if(Self_Nova != null)
				{
					if(Rnd.get(10000) < Self_Nova_Prob)
					{
						if(Self_Nova.getMpConsume() < _thisActor.getCurrentMp() && Self_Nova.getHpConsume() < _thisActor.getCurrentHp() && !_thisActor.isSkillDisabled(Self_Nova.getId()))
						{
							addUseSkillDesire(_thisActor, Self_Nova, 0, 1, 1000000);
						}
					}
				}
				if(Death_Clack != null)
				{
					if(Rnd.get(10000) < Death_Clack_Prob)
					{
						if(_thisActor.i_ai7 == 0)
						{
							int i0 = 0;
							_thisActor.i_ai7 = 1;
							addTimer(TIMER_enable_death_clack, 15000);
							L2Character c0 = _thisActor.getMostHated();
							if(c0 != null)
							{
								addUseSkillDesire(c0, Death_Clack, 0, 1, 10000000);
							}
							if(is_hard_mode != 1)
							{
								i0 = Death_Clack_Count;
							}
							else
							{
								i0 = Death_Clack_Count_Hard;
							}
							switch(i0)
							{
								case 2:
									if(_thisActor.getAggroListSize() >= 2)
									{
										L2NpcInstance.AggroInfo h0 = _thisActor.getRandomHateInfo();
										if(h0 != null)
										{
											L2Character c1 = h0.getAttacker();
											if(c1 != null)
											{
												addUseSkillDesire(c1, Death_Clack, 0, 1, 10000000);
											}
										}
									}
									break;
								case 3:
									if(_thisActor.getAggroListSize() >= 3)
									{
										L2NpcInstance.AggroInfo h0 = _thisActor.getRandomHateInfo();
										if(h0 != null)
										{
											L2Character c1 = h0.getAttacker();
											if(c1 != null)
											{
												addUseSkillDesire(c1, Death_Clack, 0, 1, 10000000);
											}
										}

										h0 = _thisActor.getRandomHateInfo();
										if(h0 != null)
										{
											L2Character c1 = h0.getAttacker();
											if(c1 != null)
											{
												addUseSkillDesire(c1, Death_Clack, 0, 1, 10000000);
											}
										}
									}
									break;
							}
						}
					}
				}
			}
		}
	}

	@Override
	protected void onEvtFinishCasting(L2Skill skill)
	{
		L2Character target = _thisActor.getCastingTarget();
		if(skill == Summon_Elemental)
		{
			DefaultMaker maker0 = _thisActor.getInstanceZone().getMaker(MAKER_summoner);
			if(maker0 != null)
			{
				if(target != null)
				{
					maker0.onScriptEvent(23140012, getStoredIdFromCreature(target), getStoredIdFromCreature(target));
				}
			}
		}
		else if(skill == Eternal_Blizzard || skill == Eternal_Blizzard_Hard)
		{
			if(debug_mode > 1)
			{
				Functions.npcSay(_thisActor, Say2C.SHOUT, "Use Skill Finished - Eternal Blizzard");
			}

			//RemoveAbsoluteDesire(); ??
			if(_thisActor.i_ai8 == 0)
			{
				addTimer(TIMER_check_evade_speech, 10000);
			}
			else if(Rnd.get(2) == 1)
			{
				addTimer(TIMER_freya_voice_normal, (Rnd.get(15) + 10) * 1000);
			}
		}
	}

	@Override
	protected void onEvtClanAttacked(L2Character attacked_member, L2Character attacker, int damage)
	{
		if(attacker.isPlayer() || CategoryManager.isInCategory(12, attacker.getNpcId()) || CategoryManager.isInCategory(123, attacker.getNpcId()))
		{
			_thisActor.addDamageHate(attacker, 0, damage);

			if(_thisActor.i_ai0 == 0)
			{
				if(Rnd.get(10000) < 500)
				{
					if(_thisActor.getLoc().distance3D(attacker.getLoc()) <= 800)
					{
						if(Ice_Ball != null)
						{
							if(Ice_Ball.getMpConsume() < _thisActor.getCurrentMp() && Ice_Ball.getHpConsume() < _thisActor.getCurrentHp() && !_thisActor.isSkillDisabled(Ice_Ball.getId()))
							{
								addUseSkillDesire(attacker, Ice_Ball, 0, 0, 1000000);
							}
						}
					}
				}
				if(Rnd.get(10000) < 500)
				{
					if(_thisActor.getLoc().distance3D(attacker.getLoc()) <= 800)
					{
						if(Summon_Elemental != null)
						{
							if(Summon_Elemental.getMpConsume() < _thisActor.getCurrentMp() && Summon_Elemental.getHpConsume() < _thisActor.getCurrentHp() && !_thisActor.isSkillDisabled(Summon_Elemental.getId()))
							{
								addUseSkillDesire(attacker, Summon_Elemental, 0, 1, 1000000);
							}
							_thisActor.l_ai4 = getStoredIdFromCreature(attacker);
						}
					}
				}
			}
		}
	}

	@Override
	protected void onEvtSeeCreature(L2Character creature)
	{
		if(_thisActor.i_ai0 == 1)
		{
			if(!creature.isPlayer() && !CategoryManager.isInCategory(12, creature.getNpcId()))
			{
				return;
			}
		}
		if(creature.isPlayer() || CategoryManager.isInCategory(12, creature.getNpcId()) || CategoryManager.isInCategory(123, creature.getNpcId()))
		{
			_thisActor.addDamageHate(creature, 0, 100);
			addAttackDesire(creature, 200, 0);
		}
	}

	@Override
	protected void onEvtSeeSpell(L2Skill skill, L2Character caster)
	{
		if(caster.isPlayer() || CategoryManager.isInCategory(12, caster.getNpcId()) || CategoryManager.isInCategory(123, caster.getNpcId()))
		{
			addAttackDesire(caster, 50, 0);
		}
	}

	@Override
	protected void onEvtScriptEvent(int eventId, Object arg1, Object arg2)
	{
		if(eventId == 23140044)
		{
			addTimer(TIMER_start_moving, 1);
		}
		else if(eventId == 23140046)
		{
			L2Character c0 = L2ObjectsStorage.getAsCharacter((Long) arg1);
			if(c0 != null)
			{
				L2Character c1 = _thisActor.getMostHated();
				if(c1 != null)
				{
					_thisActor.notifyAiEvent(c0, CtrlEvent.EVT_SCRIPT_EVENT, 23140047, getStoredIdFromCreature(c1), null);
				}
			}
		}
		else if(eventId == 23140020)
		{
			_thisActor.onDecay();
		}
	}

	@Override
	protected void onEvtTimer(int timerId, Object arg1, Object arg2)
	{
		super.onEvtTimer(timerId, arg1, arg2);
		if(timerId == TIMER_randomize_desire)
		{
			if(Rnd.get(2) == 1)
			{
				randomizeTargets();
			}
			addTimer(TIMER_randomize_desire, (30 * 1000));
		}
		else if(timerId == TIMER_eternal_blizzard)
		{
			DefaultMaker maker0 = _thisActor.getInstanceZone().getMaker(MAKER_controller);
			if(maker0 != null)
			{
				maker0.onScriptEvent(23140070, 0, 0);
				maker0.onScriptEvent(23140067, 0, 0);
			}
			if(is_hard_mode == 1)
			{
				addUseSkillDesire(_thisActor, Eternal_Blizzard_Hard, 0, 1, -1);
				addTimer(TIMER_eternal_blizzard, (Rnd.get(5) + 40) * 1000);
			}
			else
			{
				addUseSkillDesire(_thisActor, Eternal_Blizzard, 0, 1, -1);
				addTimer(TIMER_eternal_blizzard, (Rnd.get(5) + 55) * 1000);
			}
		}
		else if(timerId == TIMER_phase1_failed)
		{
			DefaultMaker maker0 = _thisActor.getInstanceZone().getMaker(MAKER_controller);
			if(maker0 != null)
			{
				maker0.onScriptEvent(23140016, 2, 0);
			}
			_thisActor.setIsInvul(true);
			//_thisActor.absolute_defence = 1;
			//_thisActor.no_attack_damage = 1;
		}
		else if(timerId == TIMER_start_moving)
		{
			if(_thisActor.i_ai0 == 0)
			{
				clearTasks();
				L2Character c0 = L2ObjectsStorage.getAsCharacter(_thisActor.c_ai0);
				if(c0 != null)
				{
					addAttackDesire(c0, 1, 1000);
				}
				addMoveToDesire(114730, -114805, -11200, 50);
				_thisActor.i_ai0 = 1;
				addTimer(TIMER_eternal_blizzard, 60000);
				Functions.broadcastOnScreenMsgFStr(_thisActor, 4000, 5, 1, 0, 0, 0, 1, 5000, 0, 1801097);
			}
		}
		else if(timerId == TIMER_use_freya_buff)
		{
			broadcastScriptEvent(23140062, 0, null, 1000);
			addTimer(TIMER_use_freya_buff, (15 * 1000));
		}
		else if(timerId == TIMER_enable_death_clack)
		{
			_thisActor.i_ai7 = 0;
		}
		else if(timerId == TIMER_check_evade_speech)
		{
			if(_thisActor.i_ai8 == 0)
			{
				L2Character c0 = _thisActor.getMostHated();
				if(c0 != null)
				{
					if(c0.getAbnormalLevelByType(6662) != 2)
					{
						_thisActor.i_ai8 = 1;
						DefaultMaker maker0 = _thisActor.getInstanceZone().getMaker(MAKER_controller);
						if(maker0 != null)
						{
							maker0.onScriptEvent(23140066, 0, 0);
						}
					}
				}
			}
		}
		else if(timerId == TIMER_freya_voice_normal)
		{
			DefaultMaker maker0 = _thisActor.getInstanceZone().getMaker(MAKER_controller);
			if(maker0 != null)
			{
				maker0.onScriptEvent(23140068, 0, 0);
			}
		}
	}

	@Override
	protected void onEvtDead(L2Character killer)
	{
		if(_thisActor.i_ai1 != 2)
		{
			DefaultMaker maker0 = _thisActor.getInstanceZone().getMaker(MAKER_controller);
			if(maker0 != null)
			{
				maker0.onScriptEvent(23140016, 1, 0);
			}
			_thisActor.i_ai1 = 3;
		}
		super.onEvtDead(killer);
	}

	@Override
	protected boolean randomWalk()
	{
		return false;
	}

	@Override
	public void returnHome()
	{
	}

	@Override
	public boolean checkBossPosition()
	{
		return false;
	}
}

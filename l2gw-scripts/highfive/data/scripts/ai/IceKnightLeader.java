package ai;

import ru.l2gw.commons.math.Rnd;
import ru.l2gw.extensions.scripts.Functions;
import ru.l2gw.gameserver.ai.DefaultAI;
import ru.l2gw.gameserver.clientpackets.Say2C;
import ru.l2gw.gameserver.model.L2Character;
import ru.l2gw.gameserver.model.L2Skill;
import ru.l2gw.gameserver.model.entity.category.CategoryManager;
import ru.l2gw.gameserver.model.npcmaker.DefaultMaker;
import ru.l2gw.gameserver.tables.SkillTable;
import ru.l2gw.util.Location;

/**
 * @author: rage
 * @date: 27.09.11 21:58
 */
public class IceKnightLeader extends DefaultAI
{
	public float HATE_SKILL_Weight_Point = 40.000000f;
	public L2Skill Skill01_ID = SkillTable.getInstance().getInfo(412418049);
	public int Skill01_Prob = 2500;
	public L2Skill Skill02_ID = SkillTable.getInstance().getInfo(412549121);
	public int Skill02_Prob = 1500;
	public L2Skill Skill03_ID = SkillTable.getInstance().getInfo(412680193);
	public int Skill03_Prob = 1500;
	public L2Skill SelfRangeBuff = SkillTable.getInstance().getInfo(412483585);
	public int SelfRangeBuff_Timer = 30;
	public L2Skill Skill_Summon = SkillTable.getInstance().getInfo(412745729);
	public int Skill_Summon_Prob = 500;
	public L2Skill Dash = SkillTable.getInstance().getInfo(412614657);
	public int Dash_Prob = 2500;
	public String TRR_FREYA_1F = "schuttgart29_2314_06";
	public String MAKER_controller = "schuttgart29_2314_01m1";
	public String MAKER_ice_knight = "schuttgart29_2314_03m1";
	public String MAKER_event_elemental = "schuttgart29_2314_102m4";
	public String MAKER_event_knight = "schuttgart29_2314_102m5";
	public String MAKER_ice_castle = "schuttgart29_2314_06m1";
	public int TIMER_leader_rangebuff = 2314009;
	public int TIMER_leader_randomize = 2314010;
	public int TIMER_dash = 2314015;
	public int TIMER_broadcast = 2314021;
	public int TIMER_despawn = 2314018;
	public int TIMER_despawn2 = 2314029;
	public int TIMER_destroy = 2314031;
	public int TIMER_phase2_failed = 2314024;
	public int TIMER_delay = 2314033;
	public int is_hard_mode = 0;
	public int debug_mode = 0;
	public int Dispel_Debuff = 1;

	public IceKnightLeader(L2Character actor)
	{
		super(actor);
	}

	@Override
	protected void onEvtSpawn()
	{
		_thisActor.i_ai0 = 0;
		_thisActor.i_ai2 = 0;
		_thisActor.i_ai5 = (int) (System.currentTimeMillis() / 1000);
		_thisActor.i_ai6 = 0;
		_thisActor.i_ai7 = 0;
		_thisActor.i_ai8 = 0;

		DefaultMaker maker0 = _thisActor.getInstanceZone().getMaker(MAKER_ice_knight);
		if(maker0 != null)
		{
			maker0.onScriptEvent(23140013, getStoredIdFromCreature(_thisActor), 0);
		}
		addTimer(TIMER_delay, 5000);
	}

	@Override
	protected void onEvtNoDesire()
	{
		if((System.currentTimeMillis() / 1000) - _thisActor.i_ai5 > 300)
		{
			if(_thisActor.i_ai6 == 0)
			{
				_thisActor.i_ai6 = 1;
				addTimer(TIMER_phase2_failed, 1);
			}
		}
	}

	@Override
	protected void onEvtAttacked(L2Character attacker, int damage, L2Skill skill)
	{
		if(attacker == null)
			return;

		if(_thisActor.getCurrentHp() < (_thisActor.getMaxHp() * 0.020000))
		{
			if(_thisActor.i_ai7 == 0)
			{
				_thisActor.i_ai7 = 1;
				_thisActor.i_ai8 = 2;
				_thisActor.altUseSkill(SkillTable.getInstance().getInfo(302645249), attacker);
				_thisActor.stopMove();
				clearTasks();
				_thisActor.setAbilityItemDrop(false);
				_thisActor.setIsInvul(true);
				//_thisActor.absolute_defence = 1;
				//_thisActor.no_attack_damage = 1;
				addUseSkillDesire(_thisActor, 422510593, 1, 0, 10000000000L);
				_thisActor.changeNpcState(2);
				addTimer(TIMER_despawn, 3000);
				DefaultMaker maker0 = _thisActor.getInstanceZone().getMaker(MAKER_controller);
				if(maker0 != null)
				{
					maker0.onScriptEvent(23140017, 1, 0);
				}
				_thisActor.i_ai1 = 3;
			}
			else if(_thisActor.i_ai7 == 1)
			{
				_thisActor.altUseSkill(SkillTable.getInstance().getInfo(302645249), attacker);
			}
		}
		else if(_thisActor.i_ai7 == 0 && _thisActor.i_ai8 == 1)
		{
			_thisActor.addDamage(attacker, damage);
			if(skill != null)
			{
				if(skill.getAbnormalTypes().contains("aura_of_hate"))
				{
					if(debug_mode > 0)
					{
						Functions.npcSay(_thisActor, Say2C.ALL, "HATE");
					}
					addAttackDesire(attacker, 1, damage * 10);
				}
			}
			_thisActor.i_ai5 = (int) (System.currentTimeMillis() / 1000);
			if(_thisActor.i_ai0 == 0)
			{
				_thisActor.i_ai0 = 1;
				int i0 = Rnd.get(30) + 1;
				addTimer(TIMER_leader_rangebuff, i0 * 1000);
				addTimer(TIMER_leader_randomize, 25000);
				addTimer(TIMER_dash, 5000);
				addTimer(TIMER_broadcast, 5000);
				addTimer(TIMER_destroy, 60 * 1000);
			}
			if(attacker.isPlayer() || CategoryManager.isInCategory(12, attacker.getNpcId()) || CategoryManager.isInCategory(123, attacker.getNpcId()))
			{
				addAttackDesire(attacker, 1, damage);
				if(_thisActor.i_ai2 == 0)
				{
					broadcastScriptEvent(23140043, getStoredIdFromCreature(attacker), null, 3500);
					_thisActor.i_ai2 = 1;
				}
				if(Skill01_ID != null)
				{
					if(Rnd.get(10000) < Skill01_Prob)
					{
						if(Rnd.get(10000) < 2500)
						{
							if(Skill01_ID.getMpConsume() < _thisActor.getCurrentMp() && Skill01_ID.getHpConsume() < _thisActor.getCurrentHp() && !_thisActor.isSkillDisabled(Skill01_ID.getId()))
							{
								addUseSkillDesire(attacker, Skill01_ID, 0, 1, 1000000);
							}
						}
						else if(Skill01_ID.getMpConsume() < _thisActor.getCurrentMp() && Skill01_ID.getHpConsume() < _thisActor.getCurrentHp() && !_thisActor.isSkillDisabled(Skill01_ID.getId()))
						{
							addUseSkillDesire(_thisActor.getMostHated(), Skill01_ID, 0, 1, 1000000);
						}
					}
				}
				if(Skill02_ID != null)
				{
					if(Rnd.get(10000) < Skill02_Prob)
					{
						if(Rnd.get(10000) < 5000)
						{
							if(Skill02_ID.getMpConsume() < _thisActor.getCurrentMp() && Skill02_ID.getHpConsume() < _thisActor.getCurrentHp() && !_thisActor.isSkillDisabled(Skill02_ID.getId()))
							{
								addUseSkillDesire(attacker, Skill02_ID, 0, 1, 1000000);
							}
						}
						else if(Skill02_ID.getMpConsume() < _thisActor.getCurrentMp() && Skill02_ID.getHpConsume() < _thisActor.getCurrentHp() && !_thisActor.isSkillDisabled(Skill02_ID.getId()))
						{
							addUseSkillDesire(_thisActor.getMostHated(), Skill02_ID, 0, 1, 1000000);
						}
					}
				}
				if(Skill03_ID != null)
				{
					if(Rnd.get(10000) < Skill03_Prob)
					{
						if(Rnd.get(10000) < 5000)
						{
							if(Skill03_ID.getMpConsume() < _thisActor.getCurrentMp() && Skill03_ID.getHpConsume() < _thisActor.getCurrentHp() && !_thisActor.isSkillDisabled(Skill03_ID.getId()))
							{
								addUseSkillDesire(attacker, Skill03_ID, 0, 1, 1000000);
							}
						}
						else if(Skill03_ID.getMpConsume() < _thisActor.getCurrentMp() && Skill03_ID.getHpConsume() < _thisActor.getCurrentHp() && !_thisActor.isSkillDisabled(Skill03_ID.getId()))
						{
							addUseSkillDesire(_thisActor.getMostHated(), Skill03_ID, 0, 1, 1000000);
						}
					}
				}
			}
		}
	}

	@Override
	protected void onEvtClanAttacked(L2Character attacked_member, L2Character attacker, int damage)
	{
		if(_thisActor.i_ai7 == 0 && _thisActor.i_ai8 == 1)
		{
			if(attacker.isPlayer() || CategoryManager.isInCategory(12, attacker.getNpcId()) || CategoryManager.isInCategory(123, attacker.getNpcId()))
			{
				addAttackDesire(attacker, 1, (damage / 5));
				if(Skill02_ID != null)
				{
					if(Rnd.get(10000) < (Skill02_Prob / 2))
					{
						if(Skill02_ID.getMpConsume() < _thisActor.getCurrentMp() && Skill02_ID.getHpConsume() < _thisActor.getCurrentHp() && !_thisActor.isSkillDisabled(Skill02_ID.getId()))
						{
							addUseSkillDesire(attacker, Skill02_ID, 0, 1, 1000000);
						}
					}
				}
				if(Skill03_ID != null)
				{
					if(Rnd.get(10000) < Skill03_Prob)
					{
						if(Skill03_ID.getMpConsume() < _thisActor.getCurrentMp() && Skill03_ID.getHpConsume() < _thisActor.getCurrentHp() && !_thisActor.isSkillDisabled(Skill03_ID.getId()))
						{
							addUseSkillDesire(attacker, Skill03_ID, 0, 1, 1000000);
						}
					}
				}
			}
		}
	}

	@Override
	protected void onEvtTimer(int timerId, Object arg1, Object arg2)
	{
		if(timerId == TIMER_leader_rangebuff)
		{
			if(SelfRangeBuff != null)
			{
				if(SelfRangeBuff.getMpConsume() < _thisActor.getCurrentMp() && SelfRangeBuff.getHpConsume() < _thisActor.getCurrentHp() && !_thisActor.isSkillDisabled(SelfRangeBuff.getId()))
				{
					addUseSkillDesire(_thisActor, SelfRangeBuff, 1, 1, 1000000);
				}
			}
			addTimer(TIMER_leader_rangebuff, SelfRangeBuff_Timer * 1000);
		}
		else if(timerId == TIMER_leader_randomize)
		{
			randomizeTargets();
			addTimer(TIMER_leader_randomize, 25000);
		}
		else if(timerId == TIMER_dash)
		{
			if(_thisActor.getMostHated() != null)
			{
				if(!_thisActor.getMostHated().isDead())
				{
					if(Rnd.get(10000) < Dash_Prob * 2)
					{
						addUseSkillDesire(_thisActor.getMostHated(), Dash, 0, 1, 1000000000L);
					}
				}
			}
			addTimer(TIMER_dash, 10000);
		}
		else if(timerId == TIMER_broadcast)
		{
			_thisActor.i_ai2 = 0;
			addTimer(TIMER_broadcast, 5000);
		}
		else if(timerId == TIMER_phase2_failed)
		{
			DefaultMaker maker0 = _thisActor.getInstanceZone().getMaker(MAKER_controller);
			if(maker0 != null)
			{
				maker0.onScriptEvent(23140017, 2, 0);
			}
			_thisActor.setIsInvul(true);
			//_thisActor.absolute_defence = 1;
			//_thisActor.no_attack_damage = 1;
		}
		else if(timerId == TIMER_despawn)
		{
			if(debug_mode > 0)
			{
				Functions.npcSay(_thisActor, Say2C.ALL, "timer_despawn");
			}
			_thisActor.equipItem(15280);
			addTimer(TIMER_despawn2, 4000);
		}
		else if(timerId == TIMER_despawn2)
		{
			if(debug_mode > 0)
			{
				Functions.npcSay(_thisActor, Say2C.ALL, "timer_despawn2");
			}
			_thisActor.onDecay();
		}
		else if(timerId == TIMER_destroy)
		{
			if(_thisActor.i_ai7 == 0)
			{
				if(debug_mode > 0)
				{
					Functions.npcSay(_thisActor, Say2C.ALL, "timer_destroy!!!!!");
				}
				int i0 = Rnd.get(3);
				Functions.broadcastOnScreenMsgFStr(_thisActor, 4000, 5, 1, 0, 0, 0, 1, 5000, 0, 1801124);
				switch(i0)
				{
					case 0:
						Functions.npcSay(_thisActor, Say2C.SHOUT, 1801120);
						DefaultMaker maker0 = _thisActor.getInstanceZone().getMaker(MAKER_event_elemental);
						if(maker0 != null)
						{
							maker0.onScriptEvent(1001, 0, 0);
						}
						break;
					case 1:
						Functions.npcSay(_thisActor, Say2C.SHOUT, 1801121);
						maker0 = _thisActor.getInstanceZone().getMaker(MAKER_event_knight);
						if(maker0 != null)
						{
							maker0.onScriptEvent(1001, 0, 0);
						}
						break;
					case 2:
						Functions.npcSay(_thisActor, Say2C.SHOUT, 1801122);
						maker0 = _thisActor.getInstanceZone().getMaker(MAKER_ice_castle);
						if(maker0 != null)
						{
							if(is_hard_mode == 1)
							{
								for(int i2 = 0; i2 < 7; i2++)
								{
									maker0.onScriptEvent(23140015, 0, 0);
								}
							}
							else
							{
								for(int i2 = 0; i2 < 5; i2++)
								{
									maker0.onScriptEvent(23140015, 0, 0);
								}
							}
						}
						break;
					case 3:
						Functions.npcSay(_thisActor, Say2C.SHOUT, 1801123);
						maker0 = _thisActor.getInstanceZone().getMaker(MAKER_ice_castle);
						if(maker0 != null)
						{
							for(int i2 = 0; i2 < 3; i2 = (i2 + 1))
							{
								maker0.onScriptEvent(23140015, 0, 0);
							}
						}
						for(int i2 = 0; i2 < 3; i2 = (i2 + 1))
						{
							Location pos0 = Location.coordsRandomize(_thisActor, 50, 500);
							_thisActor.createOnePrivate(18854, "IceCastleBreathing", 0, 0, pos0.getX(), pos0.getY(), pos0.getY(), 0, getStoredIdFromCreature(_thisActor.getMostHated()), 0, 0);
						}
				}
			}
		}
		else if(timerId == TIMER_delay)
		{
			if(_thisActor.i_ai8 == 0)
			{
				_thisActor.i_ai8 = 1;
			}
		}
	}

	@Override
	protected void onEvtFinishCasting(L2Skill skill)
	{
		L2Character target = _thisActor.getCastingTarget();
		if(skill == Skill02_ID)
		{
			if(target != null)
			{
				if(_thisActor.getLoc().distance3D(target.getLoc()) >= 300)
				{
					addUseSkillDesire(target, Dash, 0, 1, 100000000000L);
				}
			}
		}
	}

	@Override
	protected void onEvtSeeCreature(L2Character creature)
	{
		if(_thisActor.i_ai7 == 0 && _thisActor.i_ai8 == 1)
		{
			if(creature.isPlayer() || CategoryManager.isInCategory(12, creature.getNpcId()) || CategoryManager.isInCategory(123, creature.getNpcId()))
			{
				addAttackDesire(creature, 1, 1000);
			}
		}
	}

	@Override
	protected void onEvtScriptEvent(int eventId, Object arg1, Object arg2)
	{
		if(eventId == 23140020)
		{
			if(_thisActor.i_ai7 != 1)
			{
				_thisActor.onDecay();
			}
		}
		else if(eventId == 23140048)
		{
			_thisActor.setIsInvul(true);
			//_thisActor.absolute_defence = 1;
			//_thisActor.no_attack_damage = 1;
		}
	}

	@Override
	protected boolean randomWalk()
	{
		return false;
	}
}

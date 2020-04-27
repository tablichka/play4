package ai;

import ai.base.WarriorUseSkill;
import ru.l2gw.commons.math.Rnd;
import ru.l2gw.extensions.scripts.Functions;
import ru.l2gw.gameserver.ai.CtrlEvent;
import ru.l2gw.gameserver.clientpackets.Say2C;
import ru.l2gw.gameserver.instancemanager.superpoint.SuperpointNode;
import ru.l2gw.gameserver.model.L2Character;
import ru.l2gw.gameserver.model.L2Effect;
import ru.l2gw.gameserver.model.L2ObjectsStorage;
import ru.l2gw.gameserver.model.L2Skill;
import ru.l2gw.gameserver.model.entity.category.CategoryManager;
import ru.l2gw.gameserver.model.instances.L2NpcInstance;
import ru.l2gw.gameserver.model.npcmaker.DefaultMaker;
import ru.l2gw.gameserver.tables.SkillTable;

/**
 * @author: rage
 * @date: 27.09.11 20:22
 */
public class IceKnight extends WarriorUseSkill
{
	public int Skill01_Prob = 2000;
	public L2Skill SelfRangeDD = null;
	public int SelfRangeDD_Prob = 1500;
	public L2Skill Dash = null;
	public int Dash_Prob = 2000;
	public L2Skill Skill_Freya_Buff = SkillTable.getInstance().getInfo(411828225);
	public int first_spawner = -1;
	public int second_spawner = -1;
	public int third_spawner = -1;
	public int my_position = -1;
	public int PosX = -1;
	public int PosY = -1;
	public int PosZ = -1;
	public int TIMER_knight_state = 2314011;
	public int TIMER_knight_state2 = 2314012;
	public int TIMER_dash = 2314014;
	public int TIMER_superpoint_delay = 2314028;
	public String MAKER_controller = "schuttgart29_2314_01m1";
	public String MAKER_ice_knight = "schuttgart29_2314_03m1";
	public int Dispel_Debuff_Prob = 7000;
	public int debug_mode = 0;

	public IceKnight(L2Character actor)
	{
		super(actor);
		Skill01_ID = null;
		SuperPointName = "-1";
		SuperPointMethod = 0;
		IsAggressive = 1;
	}

	@Override
	protected void onEvtSpawn()
	{
		_thisActor.changeNpcState(1);
		_thisActor.i_ai0 = 0;
		_thisActor.l_ai1 = 0;
		_thisActor.i_ai2 = 0;
		_thisActor.i_ai3 = 0;
		_thisActor.setRunning();
		DefaultMaker maker0 = _thisActor.getInstanceZone().getMaker(MAKER_controller);
		if(maker0 != null)
		{
			maker0.onScriptEvent(23140064, getStoredIdFromCreature(_thisActor), 0);
		}
		addTimer(TIMER_dash, 5000);
	}

	@Override
	protected void onEvtSeeCreature(L2Character creature)
	{
		if(_thisActor.i_ai2 == 1)
		{
			if(creature.isPlayer())
			{
				L2NpcInstance.AggroInfo h0 = _thisActor.getAggroList().get(creature.getObjectId());
				if(h0 == null)
				{
					_thisActor.addDamageHate(creature, 0, 1);
				}
				if(_thisActor.getNpcState() != 3)
					_thisActor.changeNpcState(3);
			}
		}
		if(_thisActor.i_ai0 == 0)
		{
			if(third_spawner == 1)
			{
				addTimer(TIMER_knight_state, 10);
				_thisActor.i_ai0 = 1;
				DefaultMaker maker0 = _thisActor.getMyMaker();
				if(maker0 != null)
				{
					maker0.onScriptEvent(23140042, getStoredIdFromCreature(creature), 0);
				}
			}
			else
			{
				return;
			}
		}
		if(_thisActor.getLifeTime() > Aggressive_Time && creature.isPlayer())
			addAttackDesire(creature, 1, 100);
		super.onEvtSeeCreature(creature);
	}

	@Override
	protected void onEvtClanAttacked(L2Character attacked_member, L2Character attacker, int damage)
	{
		if(_thisActor.i_ai0 != 0)
			super.onEvtClanAttacked(attacked_member, attacker, damage);
	}

	@Override
	protected void onEvtScriptEvent(int eventId, Object arg1, Object arg2)
	{
		if(eventId == 23140010)
		{
			if(_thisActor.i_ai0 != 0)
			{
				L2Character c0 = L2ObjectsStorage.getAsCharacter((Long) arg1);
				if(c0 != null)
				{
					addAttackDesire(c0, 1, 1000);
				}
			}
			if(first_spawner == 1)
			{
				addTimer(TIMER_knight_state, 10);
				if((Long) arg1 == 1)
				{
					_thisActor.i_ai0 = 1;
				}
				else
				{
					L2Character c0 = L2ObjectsStorage.getAsCharacter((Long) arg1);
					if(c0 != null)
					{
						addAttackDesire(c0, 1, 1000);
					}
				}
			}
		}
		else if(eventId == 23140042)
		{
			if(debug_mode > 0)
			{
				Functions.npcSay(_thisActor, Say2C.ALL, "SCE_KNIGHT_FIRST_ATTACK");
			}
			addTimer(TIMER_knight_state, 10);
			_thisActor.i_ai0 = 1;
			L2Character c0 = L2ObjectsStorage.getAsCharacter((Long) arg1);
			if(c0 != null)
			{
				addAttackDesire(c0, 1, 1000);
			}
		}
		else if(eventId == 23140045)
		{
			if(_thisActor.i_ai0 == 0)
			{
				addTimer(TIMER_knight_state, 10);
				_thisActor.i_ai0 = 1;
				if(debug_mode > 0)
				{
					Functions.npcSay(_thisActor, Say2C.SHOUT, "ice_knight moving start. my_pos : " + my_position);
				}
				DefaultMaker maker0 = _thisActor.getMyMaker();
				if(maker0 != null)
				{
					maker0.onScriptEvent(23140056, my_position, 5);
				}
			}

			L2Character c0 = L2ObjectsStorage.getAsCharacter((Long) arg1);
			if(c0 != null)
			{
				_thisActor.l_ai1 = (Long) arg1;
			}
			if(my_position <= 18)
			{
				addMoveToDesire(114730, -114805, -11200, 50);
				if(_thisActor.l_ai1 != 0)
				{
					c0 = L2ObjectsStorage.getAsCharacter(_thisActor.l_ai1);
					if(c0 != null)
					{
						_thisActor.notifyAiEvent(c0, CtrlEvent.EVT_SCRIPT_EVENT, 23140046, getStoredIdFromCreature(_thisActor), null);
					}
				}
			}
			else if(my_position >= 21)
			{
				addMoveToDesire(PosX, PosY, PosZ, 10000);
				addTimer(TIMER_superpoint_delay, 2000);
			}
		}
		else if(eventId == 23140047)
		{
			L2Character c0 = L2ObjectsStorage.getAsCharacter((Long) arg1);
			if(c0 != null)
			{
				_thisActor.stopMove();
				addAttackDesire(c0, 1, 3000);
			}
		}
		else if(eventId == 23140043)
		{
			if(_thisActor.i_ai0 == 1)
			{
				L2Character c0 = L2ObjectsStorage.getAsCharacter((Long) arg1);
				if(c0 != null)
				{
					addAttackDesire(c0, 1, 100);
				}
			}
			else if(second_spawner == 1 && _thisActor.i_ai0 == 0)
			{
				L2Character c0 = L2ObjectsStorage.getAsCharacter((Long) arg1);
				if(c0 != null)
				{
					addAttackDesire(c0, 1000, 0);
				}
			}
		}
		else if(eventId == 23140020)
		{
			addTimer(TIMER_knight_state, 10);
			_thisActor.doDie(null);
		}
		else if(eventId == 23140048)
		{
			if(debug_mode > 0)
			{
				Functions.npcSay(_thisActor, Say2C.ALL, "SCE_HOLD_DESIRE_ON");
			}
			clearTasks();
			_thisActor.stopMove();
			_thisActor.onDecay();
			//_thisActor.absolute_defence = 1;
			//_thisActor.no_attack_damage = 1;
		}
		else if(eventId == 23140049)
		{
			if(debug_mode > 0)
			{
				Functions.npcSay(_thisActor, Say2C.ALL, "SCE_HOLD_DESIRE_OFF");
			}
			_thisActor.lookNeighbor(2000);

			//_thisActor.absolute_defence = 0;
			//_thisActor.no_attack_damage = 0;
		}
		else if(eventId == 23140051)
		{
			if(_thisActor.i_ai0 != 0)
			{
				L2Character c0 = L2ObjectsStorage.getAsCharacter((Long) arg1);
				if(c0 != null)
				{
					if(_thisActor.getZ() <= c0.getZ())
					{
						_thisActor.notifyAiEvent(c0, CtrlEvent.EVT_SCRIPT_EVENT, 23140052, getStoredIdFromCreature(_thisActor), 0);
					}
				}
			}
		}
		else if(eventId == 23140062)
		{
			if(_thisActor.i_ai3 != 3)
			{
				addUseSkillDesire(_thisActor, Skill_Freya_Buff, 1, 0, 10000000);
				_thisActor.i_ai3 = 3;
			}
		}
		else if(eventId == 23140065)
		{
			if(debug_mode > 0)
			{
				Functions.npcSay(_thisActor, Say2C.SHOUT, "is not able spawn");
			}
			_thisActor.onDecay();
		}
	}

	@Override
	protected void onEvtTimer(int timerId, Object arg1, Object arg2)
	{
		if(timerId == TIMER_knight_state)
		{
			if(_thisActor.i_ai2 == 0)
			{
				_thisActor.i_ai2 = 1;
				_thisActor.changeNpcState(2);
				addTimer(TIMER_knight_state2, 1500);
			}
		}
		else if(timerId == TIMER_knight_state2)
		{
			_thisActor.changeNpcState(3);
		}
		else if(timerId == TIMER_dash)
		{
			if(_thisActor.getMostHated() != null)
			{
				if(!_thisActor.getMostHated().isDead())
				{
					if(Rnd.get(10000) < Dash_Prob * 2)
					{
						addUseSkillDesire(_thisActor.getMostHated(), Dash, 0, 1, 100000000);
					}
				}
			}
			if(_thisActor.i_ai3 == 1)
			{
				if(_thisActor.l_ai1 != 0)
				{
					L2Character c0 = L2ObjectsStorage.getAsCharacter(_thisActor.l_ai1);
					if(c0 != null)
					{
						if(_thisActor.getLoc().distance3D(c0.getLoc()) < 150)
						{
							_thisActor.i_ai3 = 2;
							clearTasks();
							broadcastScriptEvent(23140062, 0, null, 1000);
						}
					}
				}
			}
			addTimer(TIMER_dash, 5000);
		}
		else if(timerId == TIMER_superpoint_delay)
		{
			if(!"-1".equals(SuperPointName))
			{
				clearTasks();
				if(debug_mode > 0)
				{
					Functions.npcSay(_thisActor, Say2C.SHOUT, "Move Superpoint. " + my_position);
				}
				addMoveSuperPointDesire(SuperPointName, SuperPointMethod, 5000000);
				_thisActor.i_ai3 = 1;
			}
		}
	}

	@Override
	protected void onEvtAttacked(L2Character attacker, int damage, L2Skill skill)
	{
		_thisActor.addDamage(attacker, damage);
		if(_thisActor.i_ai0 == 0)
		{
			_thisActor.i_ai0 = 1;
			if(debug_mode > 0)
			{
				Functions.npcSay(_thisActor, Say2C.SHOUT, "first attack");
			}
			if(_thisActor.i_ai2 == 0)
			{
				addTimer(TIMER_knight_state, 10);
			}
			if(first_spawner == 1)
			{
				DefaultMaker maker0 = _thisActor.getMyMaker();
				if(maker0 != null)
				{
					maker0.onScriptEvent(23140042, getStoredIdFromCreature(attacker), 0);
				}
				maker0 = _thisActor.getInstanceZone().getMaker(MAKER_controller);
				if(maker0 != null)
				{
					maker0.onScriptEvent(23140014, 0, 0);
				}
			}
			else if(second_spawner == 1)
			{
				DefaultMaker maker0 = _thisActor.getMyMaker();
				if(maker0 != null)
				{
					maker0.onScriptEvent(23140042, getStoredIdFromCreature(attacker), 0);
				}
			}

			DefaultMaker maker0 = _thisActor.getMyMaker();
			if(maker0 != null)
			{
				maker0.onScriptEvent(23140056, my_position, 10);
			}
		}
		if(attacker.isPlayer() || CategoryManager.isInCategory(12, attacker.getNpcId()) || CategoryManager.isInCategory(123, attacker.getNpcId()))
		{
			addAttackDesire(attacker, 1, damage);
			broadcastScriptEvent(23140043, getStoredIdFromCreature(attacker), null, 2000);
			if(Skill01_ID != null)
			{
				if(Rnd.get(10000) < Skill01_Prob)
				{
					if(_thisActor.getMostHated() != null)
					{
						if(Rnd.get(10000) < 8000)
						{
							if(Skill01_ID.getMpConsume() < _thisActor.getCurrentMp() && Skill01_ID.getHpConsume() < _thisActor.getCurrentHp() && !_thisActor.isSkillDisabled(Skill01_ID.getId()))
							{
								addUseSkillDesire(_thisActor.getMostHated(), Skill01_ID, 0, 1, 1000000);
							}
						}
						else if(Skill01_ID.getMpConsume() < _thisActor.getCurrentMp() && Skill01_ID.getHpConsume() < _thisActor.getCurrentHp() && !_thisActor.isSkillDisabled(Skill01_ID.getId()))
						{
							addUseSkillDesire(attacker, Skill01_ID, 0, 1, 1000000);
						}
					}
					else if(Skill01_ID.getMpConsume() < _thisActor.getCurrentMp() && Skill01_ID.getHpConsume() < _thisActor.getCurrentHp() && !_thisActor.isSkillDisabled(Skill01_ID.getId()))
					{
						addUseSkillDesire(attacker, Skill01_ID, 0, 1, 1000000);
					}
				}
			}
			if(SelfRangeDD != null)
			{
				if(Rnd.get(10000) < SelfRangeDD_Prob)
				{
					if(SelfRangeDD.getMpConsume() < _thisActor.getCurrentMp() && SelfRangeDD.getHpConsume() < _thisActor.getCurrentHp() && !_thisActor.isSkillDisabled(SelfRangeDD.getId()))
					{
						addUseSkillDesire(_thisActor, SelfRangeDD, 0, 1, 1000000);
					}
				}
			}
			if(Dash != null)
			{
				if(Rnd.get(10000) < Dash_Prob)
				{
					if(_thisActor.getMostHated() != null)
					{
						if(Rnd.get(10000) < 8000)
						{
							if(Dash.getMpConsume() < _thisActor.getCurrentMp() && Dash.getHpConsume() < _thisActor.getCurrentHp() && !_thisActor.isSkillDisabled(Dash.getId()))
							{
								addUseSkillDesire(_thisActor.getMostHated(), Dash, 0, 1, 1000000);
							}
						}
						else if(Dash.getMpConsume() < _thisActor.getCurrentMp() && Dash.getHpConsume() < _thisActor.getCurrentHp() && !_thisActor.isSkillDisabled(Dash.getId()))
						{
							addUseSkillDesire(attacker, Dash, 0, 1, 1000000);
						}
					}
					else if(Dash.getMpConsume() < _thisActor.getCurrentMp() && Dash.getHpConsume() < _thisActor.getCurrentHp() && !_thisActor.isSkillDisabled(Dash.getId()))
					{
						addUseSkillDesire(attacker, Dash, 0, 1, 1000000);
					}
				}
			}
		}
	}

	@Override
	protected void onEvtNodeArrived(SuperpointNode node)
	{
		if(debug_mode > 0)
		{
			Functions.npcSay(_thisActor, Say2C.ALL, "node_arr : " + node.getNodeId());
		}
		if(node.getNodeId() == 8)
		{
			clearTasks();
			_thisActor.i_ai3 = 1;
			addMoveToDesire(114707, -114797, -11199, 2000);
			L2Character c0 = L2ObjectsStorage.getAsCharacter(_thisActor.l_ai1);
			if(c0 != null)
			{
				addFollowDesire(c0, 1000);
			}
		}
		else if(node.getNodeId() == 9)
		{
			clearTasks();
		}
		else
		{
			L2Character c0 = L2ObjectsStorage.getAsCharacter(_thisActor.l_ai1);
			if(c0 != null)
			{
				if(_thisActor.getLoc().distance3D(c0.getLoc()) < 300 && ((_thisActor.getZ()) - (c0.getZ())) < 50)
				{
					clearTasks();
					_thisActor.i_ai3 = 1;
				}
			}
		}
	}

	@Override
	protected void onEvtDead(L2Character killer)
	{
		super.onEvtDead(killer);
		if(first_spawner == 1)
		{
			DefaultMaker maker0 = _thisActor.getMyMaker();
			if(maker0 != null)
			{
				maker0.onScriptEvent(23140044, 0, 0);
			}
		}
		if(second_spawner == 1)
		{
			DefaultMaker maker0 = _thisActor.getInstanceZone().getMaker(MAKER_ice_knight);
			if(maker0 != null)
			{
				maker0.onScriptEvent(23140069, 0, 0);
			}
		}
	}

	@Override
	protected void onEvtAbnormalStatusChanged(L2Character speller, L2Effect effect, boolean added)
	{
		if(added)
		{
			if(_thisActor.getInstanceZoneId() == 139)
			{
				if(effect.getSkill().getAbnormalTypes().contains(SkillTable.getInstance().getInfo(6029313).getAbnormalTypes().get(0)))
				{
					if(Rnd.get(10000) < Dispel_Debuff_Prob)
						effect.exit();
				}
				else if(effect.getSkill().getAbnormalTypes().contains(SkillTable.getInstance().getInfo(91357185).getAbnormalTypes().get(0)))
				{
					if(Rnd.get(10000) < Dispel_Debuff_Prob)
						effect.exit();
				}
				else if(effect.getSkill().getAbnormalTypes().contains(SkillTable.getInstance().getInfo(18284545).getAbnormalTypes().get(0)))
				{
					if(Rnd.get(10000) < Dispel_Debuff_Prob)
						effect.exit();
				}
				else if(effect.getSkill().getAbnormalTypes().contains(SkillTable.getInstance().getInfo(24051713).getAbnormalTypes().get(0)))
				{
					if(Rnd.get(10000) < Dispel_Debuff_Prob)
						effect.exit();
				}
				else if(effect.getSkill().getAbnormalTypes().contains(SkillTable.getInstance().getInfo(76611585).getAbnormalTypes().get(0)))
				{
					if(Rnd.get(10000) < Dispel_Debuff_Prob)
						effect.exit();
				}
				else if(effect.getSkill().getAbnormalTypes().contains(SkillTable.getInstance().getInfo(78708737).getAbnormalTypes().get(0)))
				{
					if(Rnd.get(10000) < Dispel_Debuff_Prob)
						effect.exit();
				}
				else if(effect.getSkill().getAbnormalTypes().contains(SkillTable.getInstance().getInfo(26411009).getAbnormalTypes().get(0)))
				{
					if(Rnd.get(10000) < Dispel_Debuff_Prob)
						effect.exit();
				}
			}
			else if(_thisActor.getInstanceZoneId() == 144)
			{
				if(effect.getSkill().getAbnormalTypes().contains(SkillTable.getInstance().getInfo(6029313).getAbnormalTypes().get(0)))
				{
					effect.exit();
				}
				else if(effect.getSkill().getAbnormalTypes().contains(SkillTable.getInstance().getInfo(91357185).getAbnormalTypes().get(0)))
				{
					effect.exit();
				}
				else if(effect.getSkill().getAbnormalTypes().contains(SkillTable.getInstance().getInfo(18284545).getAbnormalTypes().get(0)))
				{
					effect.exit();
				}
				else if(effect.getSkill().getAbnormalTypes().contains(SkillTable.getInstance().getInfo(24051713).getAbnormalTypes().get(0)))
				{
					effect.exit();
				}
				else if(effect.getSkill().getAbnormalTypes().contains(SkillTable.getInstance().getInfo(76611585).getAbnormalTypes().get(0)))
				{
					effect.exit();
				}
				else if(effect.getSkill().getAbnormalTypes().contains(SkillTable.getInstance().getInfo(78708737).getAbnormalTypes().get(0)))
				{
					effect.exit();
				}
				else if(effect.getSkill().getAbnormalTypes().contains(SkillTable.getInstance().getInfo(26411009).getAbnormalTypes().get(0)))
				{
					effect.exit();
				}
			}
		}
	}

	@Override
	protected void onEvtNoDesire()
	{
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

	@Override
	protected boolean createNewTask()
	{
		return true;
	}
}
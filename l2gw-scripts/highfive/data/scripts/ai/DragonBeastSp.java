package ai;

import ai.base.AntarasCaveRaidBasic;
import ru.l2gw.gameserver.model.*;
import ru.l2gw.gameserver.model.instances.L2NpcInstance;
import ru.l2gw.gameserver.serverpackets.Die;
import ru.l2gw.gameserver.tables.SkillTable;
import ru.l2gw.commons.math.Rnd;
import ru.l2gw.util.Util;

import static ru.l2gw.gameserver.ai.CtrlIntention.AI_INTENTION_IDLE;

/**
 * @author: rage
 * @date: 24.09.11 20:57
 */
public class DragonBeastSp extends AntarasCaveRaidBasic
{
	public int USE_SKILL03_TIME = 4000;
	public int USE_SKILL04_TIME = 4001;
	public int DESPAWN_TIME = 4002;
	public L2Skill Skill03_ID = SkillTable.getInstance().getInfo(441450497);
	public L2Skill Skill04_ID = SkillTable.getInstance().getInfo(441516033);

	public DragonBeastSp(L2Character actor)
	{
		super(actor);
		SPAWN_HOLD_MON_TIME = 110;
		CORPSE_TIME = 4003;
		Skill01_ID = SkillTable.getInstance().getInfo(451870721);
		Skill02_ID = SkillTable.getInstance().getInfo(451936257);
		corpse = 32886;
		ai_corpse = "CorpseDragonBeast";
	}

	@Override
	protected void onEvtSpawn()
	{
		_thisActor.lookNeighbor(1500);
		_thisActor.setCurrentHp(_thisActor.getMaxHp() * 0.200000);
		_thisActor.i_ai1 = (int) _thisActor.param1;
		_thisActor.i_ai2 = 0;
		_thisActor.i_ai3 = 0;
		_thisActor.i_ai4 = 0;
		addTimer(DESPAWN_TIME, 3 * 60 * 60 * 1000);
		addTimer(USE_SKILL04_TIME, (1 + Rnd.get(30)) * 1000);
		super.onEvtSpawn();
	}

	@Override
	protected void onEvtSeeCreature(L2Character creature)
	{
		if(creature.isPlayer() && _thisActor.i_ai5 == 0)
		{
			_thisActor.i_ai5 = 1;
			addTimer(USE_SKILL03_TIME, (30 + Rnd.get(30)) * 1000);
		}
		super.onEvtSeeCreature(creature);
	}

	@Override
	protected void onEvtTimer(int timerId, Object arg1, Object arg2)
	{
		if(timerId == USE_SKILL03_TIME)
		{
			if(_thisActor.i_ai0 == 1)
			{
				addTimer(USE_SKILL03_TIME, (30 + Rnd.get(30)) * 1000);
				for(int i2 = 0; i2 < 3; i2++)
				{
					L2NpcInstance.AggroInfo h0 = _thisActor.getRandomHateInfo();
					if(h0 != null)
					{
						L2Character c0 = h0.getAttacker();
						if(c0 != null)
						{
							if(_thisActor.getLoc().distance3D(c0.getLoc()) > 150)
							{
								addUseSkillDesire(c0, Skill03_ID, 0, 1, 10000000000L);
							}
						}
					}
				}
			}
		}
		else if(timerId == USE_SKILL04_TIME)
		{
			if(_thisActor.i_ai0 == 1)
			{
				addTimer(USE_SKILL04_TIME, 5000);
				if(_thisActor.i_ai1 == 1 && _thisActor.i_ai7 == 0)
				{
					_thisActor.i_ai7 = 1;
					addUseSkillDesire(_thisActor, Skill04_ID, 1, 1, 10000000000L);
				}
				if(_thisActor.i_ai1 == 2 && _thisActor.i_ai9 == 1)
				{
					_thisActor.i_ai9 = 0;
					addUseSkillDesire(_thisActor, Skill04_ID, 1, 1, 10000000000L);
				}
				if(_thisActor.i_ai1 == 3 && _thisActor.i_ai3 == 1)
				{
					_thisActor.i_ai3 = 0;
				}
			}
		}
		else if(timerId == DESPAWN_TIME)
		{
			_thisActor.onDecay();
		}
		else if(timerId == CORPSE_TIME)
		{
			_thisActor.decayMe();
			_thisActor.changeNpcState(2);
			L2Player c0 = L2ObjectsStorage.getAsPlayer(_thisActor.c_ai1);
			if(c0 != null)
			{
				_thisActor.createOnePrivate(corpse, ai_corpse, 0, 0, _thisActor.getX(), _thisActor.getY(), _thisActor.getZ(), _thisActor.getHeading(), getStoredIdFromCreature(_thisActor), getStoredIdFromCreature(c0), Util.getMPCCId(c0));
			}
		}
		else
			super.onEvtTimer(timerId, arg1, arg2);
	}

	@Override
	protected void onEvtScriptEvent(int eventId, Object arg1, Object arg2)
	{
		if(_thisActor.i_ai1 == 1)
		{
			if(eventId == 15003 || eventId == 15004)
			{
				_thisActor.i_ai4++;
			}
		}
		if(_thisActor.i_ai1 == 2)
		{
			if(eventId == 15002 || eventId == 15004)
			{
				_thisActor.i_ai4++;
				if(eventId == 15002)
				{
					_thisActor.i_ai9 = 1;
				}
			}
		}
		if(_thisActor.i_ai1 == 3)
		{
			if(eventId == 15003 || eventId == 15002)
			{
				_thisActor.i_ai4++;
				if(_thisActor.i_ai4 == 2)
				{
					_thisActor.i_ai3 = 1;
				}
			}
		}
		super.onEvtScriptEvent(eventId, arg1, arg2);
	}

	@Override
	protected void onEvtDead(L2Character killer)
	{
		if(_thisActor.i_ai1 == 1 && _thisActor.i_ai4 < 2)
		{
			_thisActor.i_ai0 = 0;
			addTimer(DESPAWN_TIME, 2000);
			broadcastScriptEvent(15002, 0, null, 5000);
		}
		if(_thisActor.i_ai1 == 2 && _thisActor.i_ai4 < 2)
		{
			_thisActor.i_ai0 = 0;
			addTimer(DESPAWN_TIME, 2000);
			broadcastScriptEvent(15003, 0, null, 5000);
		}
		if(_thisActor.i_ai1 == 3 && _thisActor.i_ai4 < 2)
		{
			_thisActor.i_ai0 = 0;
			addTimer(DESPAWN_TIME, 2000);
			broadcastScriptEvent(15004, 0, null, 5000);
		}
		if(_thisActor.i_ai4 == 2)
		{
			_thisActor.lookNeighbor(1500);
			_thisActor.i_ai0 = 2;
			broadcastScriptEvent(15008, 0, null, 5000);
			if(killer != null)
			{
				L2Player player = killer.getPlayer();
				if(player != null)
				{
					for(L2NpcInstance.AggroInfo ai : _thisActor.getAggroList().values())
					{
						if(ai != null)
						{
							L2Character cha = ai.getAttacker();
							if(cha != null && !attackers.contains(cha.getStoredId()))
								attackers.add(cha.getStoredId());
						}
					}

					_thisActor.c_ai1 = player.getStoredId();
					addTimer(CORPSE_TIME, 2000);
				}
			}
		}
		stopAITask();

		_thisActor.stopHate();

		// 10 seconds timeout of ATTACK after respawn
		setGlobalAggro(getInt("global_aggro", -10));

		_thisActor.setAttackTimeout(Integer.MAX_VALUE);

		// Удаляем все задания
		clearTasks();

		_actor.breakAttack();
		_actor.breakCast(true, false);
		_actor.stopMove();
		_actor.broadcastPacket(new Die(_actor));
		_intention = AI_INTENTION_IDLE;
		setAttackTarget(null);
		debug = false;
		_useUD = false;
	}

	protected void onEvtAbnormalStatusChanged(L2Character speller, L2Effect effect, boolean added)
	{
		if(added)
		{
			if(effect.getSkill().getAbnormalTypes().contains(SkillTable.getInstance().getInfo(6029313).getAbnormalTypes().get(0)))
			{
				if(Rnd.get(10000) < 600)
					effect.exit();
			}
			else if(effect.getSkill().getAbnormalTypes().contains(SkillTable.getInstance().getInfo(91357185).getAbnormalTypes().get(0)))
			{
				if(Rnd.get(10000) < 600)
					effect.exit();
			}
			else if(effect.getSkill().getAbnormalTypes().contains(SkillTable.getInstance().getInfo(18284545).getAbnormalTypes().get(0)))
			{
				if(Rnd.get(10000) < 600)
					effect.exit();
			}
			else if(effect.getSkill().getAbnormalTypes().contains(SkillTable.getInstance().getInfo(24051713).getAbnormalTypes().get(0)))
			{
				if(Rnd.get(10000) < 600)
					effect.exit();
			}
			else if(effect.getSkill().getAbnormalTypes().contains(SkillTable.getInstance().getInfo(76611585).getAbnormalTypes().get(0)))
			{
				if(Rnd.get(10000) < 600)
					effect.exit();
			}
			else if(effect.getSkill().getAbnormalTypes().contains(SkillTable.getInstance().getInfo(78708737).getAbnormalTypes().get(0)))
			{
				if(Rnd.get(10000) < 600)
					effect.exit();
			}
			else if(effect.getSkill().getAbnormalTypes().contains(SkillTable.getInstance().getInfo(26411009).getAbnormalTypes().get(0)))
			{
				if(Rnd.get(10000) < 600)
					effect.exit();
			}
		}
	}
}

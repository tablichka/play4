package ai;

import ai.base.AntarasCaveRaidBasic;
import ru.l2gw.commons.math.Rnd;
import ru.l2gw.gameserver.model.L2Character;
import ru.l2gw.gameserver.model.L2Effect;
import ru.l2gw.gameserver.model.L2Skill;
import ru.l2gw.gameserver.model.instances.L2NpcInstance;
import ru.l2gw.gameserver.serverpackets.Die;
import ru.l2gw.gameserver.tables.SkillTable;

import static ru.l2gw.gameserver.ai.CtrlIntention.AI_INTENTION_IDLE;

/**
 * @author: rage
 * @date: 24.09.11 20:50
 */
public class DragonBeast extends AntarasCaveRaidBasic
{
	public int USE_SKILL03_TIME = 4000;
	public int SPLIT_TIME = 4001;
	public L2Skill Skill03_ID = SkillTable.getInstance().getInfo(441450497);
	public L2Skill Skill04_ID = SkillTable.getInstance().getInfo(441516033);

	public DragonBeast(L2Character actor)
	{
		super(actor);
		Skill01_ID = SkillTable.getInstance().getInfo(451870721);
		Skill02_ID = SkillTable.getInstance().getInfo(451936257);
	}

	@Override
	protected void onEvtSeeCreature(L2Character creature)
	{
		if( creature.isPlayer() && _thisActor.i_ai5 == 0 )
		{
			_thisActor.i_ai5 = 1;
			addTimer(USE_SKILL03_TIME, 30 + Rnd.get(30) * 1000 );
			addTimer(SPLIT_TIME, 10000);
		}
		super.onEvtSeeCreature(creature);
	}

	@Override
	protected void onEvtTimer(int timerId, Object arg1, Object arg2)
	{
		if( timerId == USE_SKILL03_TIME )
		{
			if( _thisActor.i_ai0 == 1 )
			{
				addTimer(USE_SKILL03_TIME, 30 + Rnd.get(30) * 1000 );
				for(int i2 = 0; i2 < 3; i2++)
				{
					L2NpcInstance.AggroInfo h0 = _thisActor.getRandomHateInfo();
					if( h0 != null )
					{
						L2Character c0 = h0.getAttacker();
						if( c0 != null )
						{
							if( _thisActor.getLoc().distance3D(c0.getLoc()) > 150 )
							{
								addUseSkillDesire(_thisActor, Skill03_ID, 0, 1, 10000000000L);
							}
						}
					}
				}
			}
		}
		else if( timerId == SPLIT_TIME )
		{
			addTimer(SPLIT_TIME, 10000);
			if( _thisActor.i_ai0 == 1 && _thisActor.getCurrentHp() < ( _thisActor.getMaxHp() * 0.600000 ) && _thisActor.i_ai6 == 0 )
			{
				_thisActor.i_ai6 = 1;
				_thisActor.createOnePrivate(25732, "DragonBeastSp", 0, 0, _thisActor.getX(), _thisActor.getY(), _thisActor.getZ(), 0, 1, 0, 0);
				_thisActor.createOnePrivate(25732, "DragonBeastSp", 0, 0, _thisActor.getX(), _thisActor.getY(), _thisActor.getZ(), 0, 2, 0, 0);
				_thisActor.createOnePrivate(25732, "DragonBeastSp", 0, 0, _thisActor.getX(), _thisActor.getY(), _thisActor.getZ(), 0, 3, 0, 0);
				_thisActor.onDecay();
			}
		}
		super.onEvtTimer(timerId, arg1, arg2);
	}

	@Override
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

	@Override
	protected void onEvtDead(L2Character killer)
	{
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
}

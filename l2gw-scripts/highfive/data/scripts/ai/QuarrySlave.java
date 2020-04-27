package ai;

import quests.global.Hellbound;
import ru.l2gw.gameserver.Config;
import ru.l2gw.commons.math.Rnd;
import ru.l2gw.extensions.scripts.Functions;
import ru.l2gw.gameserver.ai.CtrlEvent;
import ru.l2gw.gameserver.ai.CtrlIntention;
import ru.l2gw.gameserver.ai.DefaultAI;
import ru.l2gw.gameserver.clientpackets.Say2C;
import ru.l2gw.gameserver.geodata.GeoEngine;
import ru.l2gw.gameserver.model.L2Character;
import ru.l2gw.gameserver.model.L2Player;
import ru.l2gw.gameserver.model.instances.L2NpcInstance;
import ru.l2gw.util.Location;

import java.lang.ref.WeakReference;

/**
 * @author: rage
 * @date: 23.01.2010 14:52:46
 */
public class QuarrySlave extends DefaultAI
{
	private WeakReference<L2Player> _followTarget = null;
	private boolean _follow = false;
	private boolean _returnToHome = false;
	private int _followFails = 0;
	private long _lastAggro = 0;
	private static Location _destination = new Location(-5952, 249344, -3112);


	public QuarrySlave(L2Character actor)
	{
		super(actor);
	}

	public void setFollowTarget(L2Player player)
	{
		if(player != null)
		{
			_followTarget = new WeakReference<L2Player>(player);
			_follow = true;
			_thisActor.hasChatWindow = false;
		}
		else
		{
			_followTarget = null;
			_follow = false;
		}
	}

	private L2Player getFollowTarget()
	{
		return _followTarget != null ? _followTarget.get() : null;
	}

	protected boolean thinkActive()
	{
		if(_thisActor.isDead())
			return true;

		if(_follow && _lastAggro < System.currentTimeMillis())
		{
			_lastAggro = System.currentTimeMillis() + 10000;
			for(L2NpcInstance npc : _thisActor.getKnownNpc(2500))
				if(npc.getNpcId() >= 22344 && npc.getNpcId() <= 22347 && Rnd.chance(50))
				{
					npc.addDamageHate(_thisActor, Rnd.get(1000), Rnd.get(10000));
					npc.getAI().notifyEvent(CtrlEvent.EVT_AGGRESSION, _thisActor, 1, null);
					if(npc.getAI().getIntention() != CtrlIntention.AI_INTENTION_ATTACK)
						npc.getAI().setIntention(CtrlIntention.AI_INTENTION_ATTACK, _thisActor);
				}
		}

		if(_def_think)
		{
			doTask();
			return true;
		}
		else
			createNewTask();

		return false;
	}

	@Override
	protected void onEvtArrived()
	{
		if(_thisActor.isInRange(_destination, 400))
		{
			Functions.npcSay(_thisActor, Say2C.ALL, "Thanks for saving us! We greatly appreciate your help.");//TODO: Найти fString и заменить.
			if(getFollowTarget() != null)
				_thisActor.dropItem(getFollowTarget(), Rnd.get(9628, 9630), Rnd.get(1, (int) Config.RATE_DROP_ITEMS));
			Hellbound.addPoints(50);
			super.onEvtDead(null);
			_thisActor.decayMe();
			_thisActor.doDie(null);
		}
	}

	@Override
	protected boolean createNewTask()
	{
		clearTasks();

		if(_returnToHome)
		{
			_follow = false;
			_followTarget = null;
			_thisActor.hasChatWindow = true;
			_followFails = 0;

			if(!GeoEngine.canSeeCoord(_thisActor.getX(), _thisActor.getY(), _thisActor.getZ(), _thisActor.getSpawn().getLoc().getX(), _thisActor.getSpawn().getLoc().getY(), _thisActor.getSpawn().getLoc().getZ(), false, _thisActor.getReflection()))
			{
				_thisActor.teleToLocation(_thisActor.getSpawnedLoc());
				return true;
			}

			Task task = new Task();
			task.type = TaskType.MOVE;
			task.loc = _thisActor.getSpawn().getLoc();
			_task_list.add(task);
			_def_think = true;
			_returnToHome = false;
			return true;
		}

		L2Player target = getFollowTarget();

		if(_follow && target != null && _thisActor.isInRange(target, 900) && GeoEngine.canSeeTarget(_thisActor, target))
		{
			if(_thisActor.isInRange(_destination, 400))
			{
				Functions.npcSay(_thisActor, Say2C.ALL, "Thanks for saving us! We greatly appreciate your help.");//TODO: Найти fString и заменить.
				_thisActor.dropItem(getFollowTarget(), Rnd.get(9628, 9630), Rnd.get(1, (int) Config.RATE_DROP_ITEMS));
				Hellbound.addPoints(50);
				_thisActor.decayMe();
				_thisActor.doDie(null);
				super.onEvtDead(null);
				return true;
			}

			if(!_thisActor.isInRange(target, 100))
			{
				Task task = new Task();
				task.type = TaskType.MOVE;
				task.usePF = false;
				task.loc = target.getLoc();
				_task_list.add(task);
				_followFails = 0;
				_def_think = true;
				return true;
			}
		}
		else if(_follow)
		{
			_followFails++;
			if(_followFails == 10)
			{
				_returnToHome = true;
				Functions.npcSay(_thisActor, Say2C.ALL, "You should not do like this anymore...");//TODO: Найти fString и заменить.
			}
		}

		return true;
	}

	@Override
	protected void onEvtDead(L2Character killer)
	{
		Functions.npcSay(_thisActor, Say2C.ALL, "Ah, you cannot protect us from dying!..");//TODO: Найти fString и заменить.
		super.onEvtDead(killer);
		Hellbound.addPoints(-10);
	}

	@Override
	protected void onEvtSpawn()
	{
		_followTarget = null;
		_follow = false;
		_returnToHome = false;
		_thisActor.hasChatWindow = true;
		_followFails = 0;
		_lastAggro = 0;
		super.onEvtSpawn();
	}
}

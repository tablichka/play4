package ai;

import ru.l2gw.extensions.scripts.Functions;
import ru.l2gw.gameserver.ai.CtrlIntention;
import ru.l2gw.gameserver.ai.DefaultAI;
import ru.l2gw.gameserver.clientpackets.Say2C;
import ru.l2gw.gameserver.instancemanager.superpoint.Superpoint;
import ru.l2gw.gameserver.instancemanager.superpoint.SuperpointManager;
import ru.l2gw.gameserver.instancemanager.superpoint.SuperpointNode;
import ru.l2gw.gameserver.model.L2Character;
import ru.l2gw.gameserver.model.L2ObjectsStorage;
import ru.l2gw.gameserver.model.L2Player;
import ru.l2gw.gameserver.model.L2Skill;
import ru.l2gw.gameserver.serverpackets.SocialAction;
import ru.l2gw.gameserver.tables.SkillTable;
import ru.l2gw.util.Location;

/**
 * @author admin
 * @date 25.11.2010 15:11:44
 */
public class SSQRitualGuard extends DefaultAI
{
	protected Superpoint _superpoint;
	protected SuperpointNode _prevNode;
	protected long _delay = 0;
	protected int _message = -1;
	protected int _my_agro_range;
	protected int _my_position;
	public String SuperPointName;

	protected static final L2Skill _ssqTeleport = SkillTable.getInstance().getInfo(5978, 1);
	protected static final Location[] _telePos =
			{
					new Location(-75775, 213415, -7120),
					new Location(-74959, 209240, -7472),
					new Location(-77699, 208905, -7640),
					new Location(-79939, 205857, -7888)
			};

	public SSQRitualGuard(L2Character actor)
	{
		super(actor);
	}

	@Override
	public void onEvtSpawn()
	{
		super.onEvtSpawn();

		_thisActor.i_ai0 = 0;
		_thisActor.c_ai0 = 0;
		_message = getInt("Fstring_Num");
		_my_agro_range = getInt("my_agro_range", 150);
		_my_position = getInt("my_position", -1);

		if(SuperPointName != null)
			_superpoint = SuperpointManager.getInstance().getSuperpointByName(SuperPointName);
	}

	@Override
	public boolean isGlobalAI()
	{
		return true;
	}

	@Override
	protected boolean thinkActive()
	{
		if(_thisActor.isDead())
			return true;

		if(_thisActor.i_ai0 == 0)
			for(L2Character cha : _thisActor.getKnownCharacters(_my_agro_range))
			{
				L2Player player = cha.getPlayer();
				if(player != null && !player.isInvisible() && player.getEffectBySkillId(963) == null)
				{
					_thisActor.i_ai0 = 1;
					Functions.npcSay(_thisActor, Say2C.ALL, _message);
					_thisActor.c_ai0 = player.getStoredId();
					_thisActor.stopMove();
					_thisActor.doCast(_ssqTeleport, player, false);
					addTimer(1001, 10000);
					return true;
				}
			}

		if(_def_think && _thisActor.i_ai0 == 0)
		{
			doTask();
			return true;
		}

		if(_superpoint != null && _delay < System.currentTimeMillis() && _thisActor.i_ai0 == 0)
		{
			// Добавить новое задание
			clearTasks();
			Task task = new Task();
			task.type = TaskType.MOVE;
			task.usePF = false;
			task.loc = _prevNode = _superpoint.getNextNode(_thisActor, 0);
			_task_list.add(task);
			_def_think = true;
			doTask();
			return true;
		}

		return true;
	}

	@Override
	protected void onEvtAttacked(L2Character attacker, int damage, L2Skill skill)
	{
		if(attacker == null || attacker.getPlayer() == null)
			return;

		L2Player player = attacker.getPlayer();
		if(!player.isInvisible() && player.getEffectBySkillId(963) == null)
		{
			_thisActor.i_ai0 = 1;
			Functions.npcSay(_thisActor, Say2C.ALL, _message);
			_thisActor.c_ai0 = player.getStoredId();
			_thisActor.stopMove();
			_thisActor.doCast(_ssqTeleport, player, false);
			addTimer(1001, 10000);
		}
	}

	@Override
	protected void onEvtArrived()
	{
		super.onEvtArrived();

		if(_superpoint != null && _intention == CtrlIntention.AI_INTENTION_ACTIVE && _thisActor.i_ai0 == 0)
		{
			clearTasks();

			if(_prevNode != null)
			{
				int message = _prevNode.getFStringId();
				if(message > 0)
					Functions.npcSay(_thisActor, Say2C.ALL, message);

				if(_prevNode.getSocial() >= 0)
					_thisActor.broadcastPacket(new SocialAction(_thisActor.getObjectId(), _prevNode.getSocial()));

				if(_prevNode.getDelay() > 0)
				{
					_delay = System.currentTimeMillis() + _prevNode.getDelay();
					return;
				}
			}

			Task task = new Task();
			task.type = TaskType.MOVE;
			task.usePF = false;
			task.loc = _prevNode = _superpoint.getNextNode(_thisActor, 0);
			_task_list.add(task);
			_def_think = true;
			doTask();
		}
	}

	@Override
	protected void onEvtFinishCasting(L2Skill skill)
	{
		if(skill != null)
			if(skill == _ssqTeleport)
				addTimer(27333, 1000);
	}

	@Override
	protected void onEvtTimer(int timerId, Object arg1, Object arg2)
	{
		if(timerId == 27333)
		{
			L2Character c0 = L2ObjectsStorage.getAsCharacter(_thisActor.c_ai0);
			if(c0 instanceof L2Player)
			{
				L2Player player = (L2Player) c0;
				_thisActor.c_ai0 = 0;
				if(_my_position > 0)
					player.teleToLocation(_telePos[_my_position - 1]);
			}
			_thisActor.onDecay();
		}
		else if(timerId == 1001)
		{
			if(_thisActor.i_ai0 == 1 && !_thisActor.isDecayed())
				_thisActor.onDecay();
		}
		else
			super.onEvtTimer(timerId, arg1, arg2);
	}
}

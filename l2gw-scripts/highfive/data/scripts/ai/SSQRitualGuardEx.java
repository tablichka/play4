package ai;

import ru.l2gw.extensions.scripts.Functions;
import ru.l2gw.gameserver.clientpackets.Say2C;
import ru.l2gw.gameserver.model.L2Character;
import ru.l2gw.gameserver.model.L2Player;
import ru.l2gw.gameserver.model.L2Skill;

/**
 * @author rage
 * @date 26.11.2010 13:28:42
 */
public class SSQRitualGuardEx extends SSQRitualGuard
{
	public SSQRitualGuardEx(L2Character actor)
	{
		super(actor);
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
				if(player != null && !player.isInvisible())
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
		if(!player.isInvisible())
		{
			_thisActor.i_ai0 = 1;
			Functions.npcSay(_thisActor, Say2C.ALL, _message);
			_thisActor.c_ai0 = player.getStoredId();
			_thisActor.stopMove();
			_thisActor.doCast(_ssqTeleport, player, false);
			addTimer(1001, 10000);
		}
	}
}

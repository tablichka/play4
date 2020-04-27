package ai;

import ru.l2gw.extensions.scripts.Functions;
import ru.l2gw.gameserver.controllers.ThreadPoolManager;
import ru.l2gw.gameserver.ai.CtrlEvent;
import ru.l2gw.gameserver.ai.Fighter;
import ru.l2gw.gameserver.clientpackets.Say2C;
import ru.l2gw.gameserver.geodata.GeoEngine;
import ru.l2gw.gameserver.model.L2Character;
import ru.l2gw.gameserver.model.L2Skill;
import ru.l2gw.util.Location;
import ru.l2gw.commons.math.Rnd;

import java.util.concurrent.ScheduledFuture;

/**
 * AI для Ol Mahum Guard ID: 20058
 *
 * @author Diamond
 */
public class OlMahumGuard extends Fighter
{
	private L2Character _attacker;

	public OlMahumGuard(L2Character actor)
	{
		super(actor);
	}

	@Override
	protected void onEvtAttacked(L2Character attacker, int damage, L2Skill skill)
	{
		if(attacker != null && !_thisActor.isAfraid() && _thisActor.getCurrentHp() < _thisActor.getMaxHp() / 2)
		{
			_attacker = attacker;

			if(Rnd.chance(50))
				Functions.npcSay(_thisActor, Say2C.ALL, "Я еще вернусь!");//TODO: Найти fString и заменить.
			else
				Functions.npcSay(_thisActor, Say2C.ALL, "Ты сильнее меня, хотя по виду не скажешь.");//TODO: Найти fString и заменить.

			// Удаляем все задания
			clearTasks();

			_thisActor.breakAttack();
			_thisActor.breakCast(true, false);
			_thisActor.stopMove();
			_thisActor.startFear();

			int posX = _thisActor.getX();
			int posY = _thisActor.getY();
			int posZ = _thisActor.getZ();

			int signx = -1;
			int signy = -1;

			if(posX > attacker.getX())
				signx = 1;
			if(posX > attacker.getY())
				signy = 1;

			int range = 1000;

			posX += Math.round(signx * range);
			posY += Math.round(signy * range);
			posZ = GeoEngine.getHeight(posX, posY, posZ, _thisActor.getReflection());

			_thisActor.setRunning();
			_thisActor.moveToLocation(new Location(posX, posY, posZ), 0, true);

			startEndFearTask();
		}
		else
			super.onEvtAttacked(attacker, damage, skill);
	}

	@Override
	protected void onEvtDead(L2Character killer)
	{
		_attacker = null;
		_thisActor.stopFear();
		if(_endFearTask != null)
			_endFearTask.cancel(true);
		_endFearTask = null;
		super.onEvtDead(killer);
	}

	private ScheduledFuture _endFearTask;

	public void startEndFearTask()
	{
		if(_endFearTask != null)
			_endFearTask.cancel(true);
		_endFearTask = ThreadPoolManager.getInstance().scheduleAi(new EndFearTask(), 10000, false);
	}

	public class EndFearTask implements Runnable
	{
		public void run()
		{
			_thisActor.stopFear();
			_endFearTask = null;
			if(_attacker != null)
				notifyEvent(CtrlEvent.EVT_AGGRESSION, _attacker, 100);
		}
	}

	@Override
	public boolean checkAggression(L2Character target)
	{
		return false;
	}

	@Override
	protected void onEvtAggression(L2Character target, int aggro, L2Skill skill)
	{}
}
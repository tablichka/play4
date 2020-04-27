package npc.maker;

import ru.l2gw.commons.math.Rnd;
import ru.l2gw.gameserver.controllers.GameTimeController;
import ru.l2gw.gameserver.model.npcmaker.DefaultMaker;
import ru.l2gw.gameserver.model.npcmaker.SpawnDefine;

/**
 * @author: rage
 * @date: 01.09.11 8:00
 */
public class JackpotEventMaker extends DefaultMaker
{
	public int RandRate = -1;

	public JackpotEventMaker(int maximum_npc, String name)
	{
		super(maximum_npc, name);
	}

	@Override
	public void onStart()
	{
		super.onStart();
		if(debug > 0)
			_log.info(this + " delay: " + getNextDelay());
		addTimer(2222, getNextDelay());
	}

	@Override
	public void onEvtTimer(int timerId, Object arg1, Object arg2)
	{
		if(timerId == 2222)
		{
			i_ai0 = Rnd.get(RandRate);
			for(SpawnDefine sd : spawn_defines)
				sd.sendScriptEvent(7777777, i_ai0, 0);

			if(debug > 0)
				_log.info(this + " delay: " + getNextDelay());
			addTimer(2222, getNextDelay());
		}
	}

	private long getNextDelay()
	{
		int t = GameTimeController.getInstance().getGameTime();
		int hh = t / 60 % 24;
		int mm = t % 60;
		int m = hh * 60 + mm; // Игровых минут с начала игровых суток
		if(debug > 0)
		{
			//int hh = t / 60 % 24;
			//int mm = t % 60;
			_log.info(this + " GameTime: " + hh + ":" + mm + " min: " + m);
		}
		if(m < 70)
			return (70 - m) * 10000L + 5000;
		//else if(h < 7 || h == 7 && m < 10)
		else if(m < 430)
			return (430 - m) * 10000L + 5000;
		//else if(h < 13 || h == 13 && m < 10)
		else if(m < 790)
			return (790 - m) * 10000L + 5000;
		//else if(h < 19 || h == 19 && m < 10)
		else if(m < 1150)
			return (1150 - m) * 10000L + 5000;

		return (1510 - m) * 10000L + 5000;
	}
}

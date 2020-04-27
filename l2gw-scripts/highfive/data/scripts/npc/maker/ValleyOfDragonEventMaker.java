package npc.maker;

import ru.l2gw.commons.math.Rnd;
import ru.l2gw.gameserver.model.npcmaker.DefaultMaker;
import ru.l2gw.gameserver.model.npcmaker.SpawnDefine;

/**
 * @author: rage
 * @date: 01.09.11 10:46
 */
public class ValleyOfDragonEventMaker extends DefaultMaker
{
	public int RandRate = 14;
	public int LoopTime = 60;
	public int RandomTime = 15;

	public ValleyOfDragonEventMaker(int maximum_npc, String name)
	{
		super(maximum_npc, name);
	}

	@Override
	public void onStart()
	{
		super.onStart();
		addTimer(20100504, LoopTime * 60000 + Rnd.get(RandomTime) * 60000);
	}

	@Override
	public void onEvtTimer(int timerId, Object arg1, Object arg2)
	{
		if(timerId == 20100504)
		{
			i_ai0 = Rnd.get(RandRate);
			addTimer(2222, 5000);
			addTimer(20100504, LoopTime * 60000 + Rnd.get(RandomTime) * 60000);
		}
		else if(timerId == 2222)
		{
			for(SpawnDefine sd : spawn_defines)
				sd.sendScriptEvent(20100504, i_ai0, 0);
		}
	}
}

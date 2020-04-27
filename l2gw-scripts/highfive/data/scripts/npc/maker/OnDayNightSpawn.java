package npc.maker;

import ru.l2gw.extensions.listeners.DayNightChangeListener;
import ru.l2gw.gameserver.controllers.GameTimeController;
import ru.l2gw.gameserver.model.instances.L2NpcInstance;
import ru.l2gw.gameserver.model.npcmaker.DefaultMaker;

/**
 * @author: rage
 * @date: 24.08.11 12:40
 */
public class OnDayNightSpawn extends DefaultMaker
{
	public int IsNight;

	public OnDayNightSpawn(int maximum_npc, String name)
	{
		super(maximum_npc, name);
		GameTimeController.getInstance().getListenerEngine().addPropertyChangeListener(new DayNightListener());
	}

	@Override
	public void onStart()
	{
		if(GameTimeController.getInstance().isNowNight() && IsNight == 1)
			super.onStart();
		else if(!GameTimeController.getInstance().isNowNight() && IsNight == 0)
			super.onStart();
	}

	@Override
	public void onNpcCreated(L2NpcInstance npc)
	{
		if(!GameTimeController.getInstance().isNowNight() && IsNight == 1)
			despawn();
		else if(GameTimeController.getInstance().isNowNight() && IsNight == 0)
			despawn();
	}

	@Override
	public void onNpcDeleted(L2NpcInstance npc)
	{
		if(GameTimeController.getInstance().isNowNight() && IsNight == 1)
			super.onNpcDeleted(npc);
		else if(!GameTimeController.getInstance().isNowNight() && IsNight == 0)
			super.onNpcDeleted(npc);
	}

	private class DayNightListener extends DayNightChangeListener
	{
		@Override
		public void switchToNight()
		{
			if(IsNight == 1)
				notifyScriptEvent(1001, 0, 0);
			else
				notifyScriptEvent(1000, 0, 0);
		}

		@Override
		public void switchToDay()
		{
			if(IsNight == 1)
				notifyScriptEvent(1000, 0, 0);
			else
				notifyScriptEvent(1001, 0, 0);
		}
	}
}

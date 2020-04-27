package ai;

import ru.l2gw.extensions.listeners.DayNightChangeListener;
import ru.l2gw.gameserver.controllers.GameTimeController;
import ru.l2gw.gameserver.ai.Fighter;
import ru.l2gw.gameserver.model.L2Character;

public class NightAgressionFighter extends Fighter
{
	public NightAgressionFighter(L2Character actor)
	{
		super(actor);
		GameTimeController.getInstance().getListenerEngine().addPropertyChangeListener(new NightAgressionDayNightListener());
	}

	private class NightAgressionDayNightListener extends DayNightChangeListener
	{
		private NightAgressionDayNightListener()
		{
			if(GameTimeController.getInstance().isNowNight())
				switchToNight();
			else
				switchToDay();
		}


		@Override
		public void switchToNight()
		{
			_thisActor.setAggroRange(-1);
		}


		@Override
		public void switchToDay()
		{
			_thisActor.setAggroRange(0);
		}
	}
}
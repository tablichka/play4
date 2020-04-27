package ai;

import ru.l2gw.extensions.listeners.DayNightChangeListener;
import ru.l2gw.gameserver.controllers.GameTimeController;
import ru.l2gw.gameserver.ai.Mystic;
import ru.l2gw.gameserver.model.L2Character;

/**
 * АИ для мобов, меняющих агресивность в ночное время.<BR>
 * Наследуется на прямую от Mystic.
 *
 * @author Death
 * date 23/11/2007
 * time 8:40:10
 */
public class NightAgressionMystic extends Mystic
{
	public NightAgressionMystic(L2Character actor)
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

		/**
		 * Вызывается, когда на сервере наступает ночь
		 */
		@Override
		public void switchToNight()
		{
			_thisActor.setAggroRange(-1);
		}

		/**
		 * Вызывается, когда на сервере наступает день
		 */
		@Override
		public void switchToDay()
		{
			_thisActor.setAggroRange(0);
		}
	}
}
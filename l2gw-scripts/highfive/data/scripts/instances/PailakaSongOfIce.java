package instances;

import ru.l2gw.extensions.listeners.L2ZoneEnterLeaveListener;
import ru.l2gw.gameserver.controllers.ThreadPoolManager;
import ru.l2gw.gameserver.model.L2Character;
import ru.l2gw.gameserver.model.L2Player;
import ru.l2gw.gameserver.model.entity.instance.Instance;
import ru.l2gw.gameserver.model.entity.instance.InstanceTemplate;
import ru.l2gw.gameserver.model.zone.L2Zone;

/**
 * @author rage
 * @date 06.10.2010 14:14:36
 */
public class PailakaSongOfIce extends Instance
{
	private ZoneListener zoneListener = new ZoneListener();

	public PailakaSongOfIce(InstanceTemplate template, int rId)
	{
		super(template, rId);
	}

	@Override
	public void startInstance()
	{
		super.startInstance();
		getTemplate().getZone().getListenerEngine().addMethodInvokedListener(zoneListener);
	}

	@Override
	public void stopInstance()
	{
		super.stopInstance();
		getTemplate().getZone().getListenerEngine().removeMethodInvokedListener(zoneListener);
	}

	@Override
	public void successEnd()
	{
		_terminate = true;

		if(_endTask != null)
			_endTask.cancel(true);

		_endTime = System.currentTimeMillis() + _template.getCoolTime();
		int[] time = calcTimeForEndTask((int) (_template.getCoolTime() / 1000));
		_endTask = ThreadPoolManager.getInstance().scheduleGeneral(new EndTask(time[1]), time[0] * 1000L);
	}

	@Override
	public void onPlayerExit(L2Player player)
	{
		super.onPlayerExit(player);
		if(player.getReflection() == getReflection())
			player.teleToLocation(getStartLoc(), getReflection());
		else
			player.unEquipInappropriateItems();
	}

	private class ZoneListener extends L2ZoneEnterLeaveListener
	{
		@Override
		public void objectEntered(L2Zone zone, L2Character object)
		{
		}

		@Override
		public void objectLeaved(L2Zone zone, L2Character object)
		{
			if(object instanceof L2Player && object.getReflection() == getReflection())
			{
				object.teleToLocation(getStartLoc(), getReflection());
			}
		}

		@Override
		public void sendZoneStatus(L2Zone zone, L2Player object)
		{
		}
	}
}

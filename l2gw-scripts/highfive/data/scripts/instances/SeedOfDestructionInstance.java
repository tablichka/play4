package instances;

import ru.l2gw.extensions.listeners.DoDieListener;
import ru.l2gw.gameserver.model.L2Character;
import ru.l2gw.gameserver.model.L2Player;
import ru.l2gw.gameserver.model.entity.instance.Instance;
import ru.l2gw.gameserver.model.entity.instance.InstanceTemplate;
import ru.l2gw.gameserver.model.instances.L2DoorInstance;
import ru.l2gw.gameserver.model.instances.L2NpcInstance;

/**
 * @author rage
 * @date 19.10.2010 14:58:46
 */
public class SeedOfDestructionInstance extends Instance
{
	private static int GRATE_DEVICE = 18777;
	private static int DESTRUCTION_DEVICE = 18778;

	private int _grateDeviceKills = 0;
	private int _destructionDeviceKills = 0;

	public SeedOfDestructionInstance(InstanceTemplate template, int rId)
	{
		super(template, rId);
	}

	@Override
	public void startInstance()
	{
		super.startInstance();
		for(L2DoorInstance door : _doors)
			if(door.getDoorId() == 12240030)
			{
				door.getListenerEngine().addMethodInvokedListener(new OnDoorDie());
				break;
			}
	}

	@Override
	public void notifyKill(L2Character cha, L2Player killer)
	{
		L2NpcInstance npc = (L2NpcInstance) cha;
		if(npc.getNpcId() == GRATE_DEVICE)
		{
			_grateDeviceKills++;
			if(_grateDeviceKills >= 3)
				for(L2DoorInstance door : _doors)
					if(door.getDoorId() == 12240027)
					{
						door.openMe();
						break;
					}
		}
		else if(npc.getNpcId() == DESTRUCTION_DEVICE)
		{
			_destructionDeviceKills++;
			if(_destructionDeviceKills >= 2)
				for(L2DoorInstance door : _doors)
					if(door.getDoorId() == 12240031)
					{
						door.openMe();
						break;
					}
		}
	}

	private class OnDoorDie extends DoDieListener
	{
		public void onDie(L2Character cha)
		{
			spawnEvent("sod_md");
		}
	}
}

package npc.model;

import ru.l2gw.commons.math.Rnd;
import ru.l2gw.gameserver.controllers.ThreadPoolManager;
import ru.l2gw.gameserver.model.L2Player;
import ru.l2gw.gameserver.model.instances.L2NpcInstance;
import ru.l2gw.gameserver.templates.L2NpcTemplate;

/**
 * @author rage
 * @date 23.03.11 14:02
 */
public class BaiumCubeInstance extends L2NpcInstance
{
	public BaiumCubeInstance(int objectId, L2NpcTemplate template, long bossIndex, long p1, long p2, long p3)
	{
		super(objectId, template, bossIndex, p1, p2, p3);
	}

	@Override
	public void onSpawn()
	{
		super.onSpawn();
		ThreadPoolManager.getInstance().scheduleGeneral(new Runnable()
		{
			@Override
			public void run()
			{
				deleteMe();
			}
		}, 900000);
	}

	@Override
	public void onBypassFeedback(L2Player player, String command)
	{
		if(command.equalsIgnoreCase("teleport_request"))
		{
			int i0, i1, i2, i3;
			i0 = Rnd.get(3);
			if(i0 == 0)
			{
				i1 = 108784 + Rnd.get(100);
				i2 = 16000 + Rnd.get(100);
				i3 = -4928;
			}
			else if(i0 == 1)
			{
				i1 = 113824 + Rnd.get(100);
				i2 = 10448 + Rnd.get(100);
				i3 = -5164;
			}
			else
			{
				i1 = 115488 + Rnd.get(100);
				i2 = 22096 + Rnd.get(100);
				i3 = -5168;
			}
			player.teleToLocation(i1, i2, i3);
		}
		else
			super.onBypassFeedback(player, command);
	}
}

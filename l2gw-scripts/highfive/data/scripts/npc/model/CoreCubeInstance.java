package npc.model;

import ru.l2gw.gameserver.controllers.ThreadPoolManager;
import ru.l2gw.gameserver.model.L2Player;
import ru.l2gw.gameserver.model.instances.L2NpcInstance;
import ru.l2gw.gameserver.templates.L2NpcTemplate;
import ru.l2gw.commons.math.Rnd;

/**
 * @author rage
 * @date 23.03.11 13:59
 */
public class CoreCubeInstance extends L2NpcInstance
{
	public CoreCubeInstance(int objectId, L2NpcTemplate template, long bossIndex, long p1, long p2, long p3)
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
			if(Rnd.chance(50))
				player.teleToLocation(17252, 114121, -3439);
			else
				player.teleToLocation(17253, 114232, -3439);
		}
		else
			super.onBypassFeedback(player, command);
	}
}

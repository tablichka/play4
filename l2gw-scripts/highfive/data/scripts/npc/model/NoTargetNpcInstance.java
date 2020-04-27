package npc.model;

import ru.l2gw.gameserver.model.instances.L2NpcInstance;
import ru.l2gw.gameserver.model.L2Player;
import ru.l2gw.gameserver.templates.L2NpcTemplate;

/**
 * @author: rage
 * @date: 19.01.2010 19:12:51
 */
public class NoTargetNpcInstance extends L2NpcInstance
{
	public NoTargetNpcInstance(int objectId, L2NpcTemplate template, long bossIndex, long p1, long p2, long p3)
	{
		super(objectId, template, bossIndex, p1, p2, p3);
	}

	@Override
	public void onAction(L2Player player, boolean dontMove)
	{
		player.sendActionFailed();
	}
}

package npc.model;

import ru.l2gw.gameserver.model.instances.L2NpcInstance;
import ru.l2gw.gameserver.model.L2Player;
import ru.l2gw.gameserver.model.L2Character;
import ru.l2gw.gameserver.templates.L2NpcTemplate;

/**
 * @author rage
 * @date 10.09.2009 10:47:14
 */
public class FrintDummyInstance extends L2NpcInstance
{
	public FrintDummyInstance(int objectId, L2NpcTemplate template, long bossIndex, long p1, long p2, long p3)
	{
		super(objectId, template, bossIndex, p1, p2, p3);
	}

	@Override
	public void onAction(L2Player player, boolean dontMove)
	{
		player.sendActionFailed();
	}

	@Override
	public boolean isAttackable(L2Character attacker, boolean forceUse, boolean sendMessage)
	{
		return false;
	}
}

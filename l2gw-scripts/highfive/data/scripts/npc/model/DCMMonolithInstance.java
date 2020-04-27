package npc.model;

import ru.l2gw.gameserver.model.instances.L2NpcInstance;
import ru.l2gw.gameserver.model.L2Player;
import ru.l2gw.gameserver.model.entity.instance.Instance;
import ru.l2gw.gameserver.templates.L2NpcTemplate;
import ru.l2gw.gameserver.instancemanager.InstanceManager;

/**
 * User: ic
 * Date: 21.10.2009
 */
public class DCMMonolithInstance extends L2NpcInstance
{
	public DCMMonolithInstance(int objectId, L2NpcTemplate template, long bossIndex, long p1, long p2, long p3)
	{
		super(objectId, template, bossIndex, p1, p2, p3);
	}

	@Override
	public void onSpawn()
	{
		super.onSpawn();
	}


	@Override
	public void showChatWindow(L2Player player, int val)
	{
		Instance inst = InstanceManager.getInstance().getInstanceByPlayer(player);
		if(inst != null)
			inst.notifyEvent("monolith", this, player);
	}
}

package npc.model;

import ru.l2gw.gameserver.model.L2Player;
import ru.l2gw.gameserver.model.instances.L2NpcInstance;
import ru.l2gw.gameserver.templates.L2NpcTemplate;

/**
 * Данный инстанс используется телепортерами из/в Pagan Temple
 * @author SYS
 */
public class L2TriolsMirrorInstance extends L2NpcInstance
{
	public L2TriolsMirrorInstance(int objectId, L2NpcTemplate template, long bossIndex, long p1, long p2, long p3)
	{
		super(objectId, template, bossIndex, p1, p2, p3);
	}

	@Override
	public void showChatWindow(L2Player player, int val)
	{
		if(getNpcId() == 32040)
			player.teleToLocation(-12766, -35840, -10856); //to pagan
		else if(getNpcId() == 32039)
			player.teleToLocation(35079, -49758, -760); //from pagan
	}
}
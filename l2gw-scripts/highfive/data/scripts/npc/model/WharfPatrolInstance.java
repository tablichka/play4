package npc.model;

import ru.l2gw.gameserver.ai.CtrlIntention;
import ru.l2gw.gameserver.model.L2Player;
import ru.l2gw.gameserver.model.instances.L2NpcInstance;
import ru.l2gw.gameserver.serverpackets.NpcHtmlMessage;
import ru.l2gw.gameserver.serverpackets.StopMove;
import ru.l2gw.gameserver.templates.L2NpcTemplate;

/**
 * @author rage
 * @date 03.09.2010 15:26:36
 */
public class WharfPatrolInstance extends L2NpcInstance
{
	public WharfPatrolInstance(int objectId, L2NpcTemplate template, long bossIndex, long p1, long p2, long p3)
	{
		super(objectId, template, bossIndex, p1, p2, p3);
	}

	@Override
	public void onAction(L2Player player, boolean dontMove)
	{
		super.onAction(player, dontMove);
		player.sendPacket(new StopMove(player));
	}

	@Override
	public void showChatWindow(L2Player player, int val)
	{
		player.sendPacket(new NpcHtmlMessage(player, this, "data/html/default/" + getNpcId() + (getAI().getIntention() == CtrlIntention.AI_INTENTION_ATTACK ? "-1.htm" : ".htm"), val));
	}
}

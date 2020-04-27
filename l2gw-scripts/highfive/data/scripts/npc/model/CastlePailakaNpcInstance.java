package npc.model;

import ru.l2gw.gameserver.ai.CtrlIntention;
import ru.l2gw.gameserver.model.L2Player;
import ru.l2gw.gameserver.model.instances.L2NpcInstance;
import ru.l2gw.gameserver.templates.L2NpcTemplate;

/**
 * @author rage
 * @date 02.12.2010 19:30:58
 */
public class CastlePailakaNpcInstance extends L2NpcInstance
{
	public CastlePailakaNpcInstance(int objectId, L2NpcTemplate template, long bossIndex, long p1, long p2, long p3)
	{
		super(objectId, template, bossIndex, p1, p2, p3);
	}

	@Override
	public void showChatWindow(L2Player player, int val)
	{
		if(val == 0)
		{
			if(i_ai0 == 0)
				showChatWindow(player, "data/html/default/pailaka_bold001.htm");
			else if(i_ai0 == 1)
				showChatWindow(player, "data/html/default/pailaka_bold002.htm");
			else
				showChatWindow(player, "data/html/default/pailaka_bold003.htm");
		}
		else
			super.showChatWindow(player, val);
	}

	@Override
	public void onBypassFeedback(L2Player player, String command)
	{
		if(!isInRange(player, getInteractDistance(player)))
		{
			player.getAI().setIntention(CtrlIntention.AI_INTENTION_ATTACK, this);
			player.sendActionFailed();
		}
		else
		{
			if(command.equalsIgnoreCase("exit"))
				player.teleToClosestTown();
			else
				super.onBypassFeedback(player, command);
		}
	}

	@Override
	public boolean isInvul()
	{
		return false;
	}
}

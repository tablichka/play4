package npc.model;

import ru.l2gw.extensions.scripts.Functions;
import ru.l2gw.gameserver.clientpackets.Say2C;
import ru.l2gw.gameserver.model.L2Player;
import ru.l2gw.gameserver.model.instances.L2NpcInstance;
import ru.l2gw.gameserver.templates.L2NpcTemplate;

/**
 * @author: rage
 * @date: 01.09.11 9:21
 */
public class EventJackpotBugInstance extends L2NpcInstance
{
	public EventJackpotBugInstance(int objectId, L2NpcTemplate template, long bossIndex, long p1, long p2, long p3)
	{
		super(objectId, template, bossIndex, p1, p2, p3);
	}

	public void showChatWindow(L2Player player, int val)
	{
		if(val == 0)
			showChatWindow(player, "data/html/default/luckpi_001.htm");
		else if(val == 1)
		{
			if(i_ai0 >= 3)
			{
				Functions.npcSay(this, Say2C.ALL, 1900145);
				onDecay();
				doDie(null);
			}
			else
				showChatWindow(player, "data/html/default/luckpi_003.htm");
		}
		else if(val == 2)
			showChatWindow(player, "data/html/default/luckpi_002.htm");
	}

}

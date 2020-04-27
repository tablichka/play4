package npc.model;

import ru.l2gw.gameserver.model.instances.L2DoormenInstance;
import ru.l2gw.gameserver.model.L2Player;
import ru.l2gw.gameserver.templates.L2NpcTemplate;
import ru.l2gw.gameserver.serverpackets.NpcHtmlMessage;

/**
 * User: ic
 * Date: 09.01.2010
 */
public class DCDoormenInstance extends L2DoormenInstance
{
	private String _path = "data/html/doormen/clanhall/";

	public DCDoormenInstance(int objectId, L2NpcTemplate template, long bossIndex, long p1, long p2, long p3)
	{
		super(objectId, template, bossIndex, p1, p2, p3);
	}


	@Override
	public void showChatWindow(L2Player player, int val)
	{
		String filename = _path + "doormen-dc-no.htm";
		int condition = validateCondition(player);
		if(condition == 2) // Clan owns CH
			filename = _path + "doormen-dc.htm";

		NpcHtmlMessage html;
		html = new NpcHtmlMessage(player, this, filename, val);
		player.sendPacket(html);
	}
}

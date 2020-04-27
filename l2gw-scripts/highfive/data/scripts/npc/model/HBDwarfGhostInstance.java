package npc.model;

import ru.l2gw.extensions.scripts.Functions;
import ru.l2gw.gameserver.controllers.ThreadPoolManager;
import ru.l2gw.gameserver.clientpackets.Say2C;
import ru.l2gw.gameserver.model.L2Player;
import ru.l2gw.gameserver.model.instances.L2NpcInstance;
import ru.l2gw.gameserver.serverpackets.NpcHtmlMessage;
import ru.l2gw.gameserver.templates.L2NpcTemplate;
import ru.l2gw.util.Location;

/**
 * @author rage
 * @date 29.10.2010 18:23:56
 */
public class HBDwarfGhostInstance extends L2NpcInstance
{
	private static final Location _teleLoc = new Location(-14643, 274588, -9032, 49282);
	private static final String _path = "data/html/default/";

	private boolean _tullyWorkshop = false;
	private boolean _firstSpawn = true;

	public HBDwarfGhostInstance(int objectId, L2NpcTemplate template, long bossIndex, long p1, long p2, long p3)
	{
		super(objectId, template, bossIndex, p1, p2, p3);
	}

	@Override
	public void onSpawn()
	{
		super.onSpawn();
		_tullyWorkshop = getSpawn().getInstance() != null;
		if(_tullyWorkshop && _firstSpawn)
		{
			Functions.npcSayCustom(this, Say2C.ALL, "scripts.npc.model.HBDwarfGhost", null);
			ThreadPoolManager.getInstance().scheduleGeneral(new Runnable()
			{
				public void run()
				{
					_firstSpawn = false;
					if(!isDecayed())
					{
						teleToLocation(_teleLoc);
						setHeading(_teleLoc.getHeading());
					}
				}
			}, 10000);
		}
	}

	@Override
	public void onBypassFeedback(L2Player player, String command)
	{
		if(command.startsWith("TeleToTully6"))
		{
			
		}
		else
			super.onBypassFeedback(player, command);
	}

	public void showChatWindow(L2Player player, int val)
	{
		if(_tullyWorkshop && _firstSpawn)
		{
			player.sendActionFailed();
			return;
		}

		String filename = _path;
		if(val == 0)
			filename += getNpcId() + (_tullyWorkshop ? "i.htm" : ".htm");
		else
			filename += getNpcId() + "-" + val + ".htm";

		player.sendPacket(new NpcHtmlMessage(player, this, filename, 0));
	}
}

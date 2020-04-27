package events.XmasGift;

import ru.l2gw.extensions.scripts.Functions;
import ru.l2gw.extensions.scripts.ScriptFile;
import ru.l2gw.gameserver.instancemanager.ServerVariables;
import ru.l2gw.gameserver.model.L2Player;
import ru.l2gw.gameserver.model.gmaccess.AdminTemplateManager;
import ru.l2gw.gameserver.tables.SpawnTable;
import ru.l2gw.util.Files;

/**
 * @author: rage
 * @date: 30.12.11 12:41
 */
public class XmasGift extends Functions implements ScriptFile
{
	public void onLoad()
	{
		if(isActive())
		{
			_log.info("Loaded Event: Christmas Gift [state: activated]");
			SpawnTable.getInstance().startEventSpawn("br_xmas_gift_event");
		}
		else
			_log.info("Loaded Event: Christmas Gift [state: deactivated]");
	}

	/**
	 * Читает статус эвента из базы.
	 *
	 * @return
	 */
	private static boolean isActive()
	{
		return ServerVariables.getString("xmas_gift", "off").equalsIgnoreCase("on");
	}

	/**
	 * Запускает эвент
	 */
	public void startEvent()
	{
		L2Player player = (L2Player) self;
		if(!AdminTemplateManager.checkBoolean("eventMaster", player))
			return;

		if(!isActive())
		{
			ServerVariables.set("xmas_gift", "on");
			SpawnTable.getInstance().startEventSpawn("br_xmas_gift_event");
			_log.info("Event 'Christmas Gift' started.");
		}
		else
			player.sendMessage("Event 'Christmas Gift' already started.");

		show(Files.read("data/html/admin/events.htm", player), player);
	}

	/**
	 * Останавливает эвент
	 */
	public void stopEvent()
	{
		L2Player player = (L2Player) self;
		if(!AdminTemplateManager.checkBoolean("eventMaster", player))
			return;
		if(isActive())
		{
			ServerVariables.unset("xmas_gift");
			SpawnTable.getInstance().stopEventSpawn("br_xmas_gift_event", true);
			_log.info("Event 'Christmas Gift' stopped.");
		}
		else
			player.sendMessage("Event 'Christmas Gift' not started.");

		show(Files.read("data/html/admin/events.htm", player), player);
	}

	public void onReload()
	{
	}

	public void onShutdown()
	{
	}
}
package events.TestServer;

import ru.l2gw.extensions.scripts.Functions;
import ru.l2gw.extensions.scripts.ScriptFile;
import ru.l2gw.gameserver.instancemanager.ServerVariables;
import ru.l2gw.gameserver.model.L2Player;
import ru.l2gw.gameserver.model.gmaccess.AdminTemplateManager;
import ru.l2gw.gameserver.tables.SpawnTable;
import ru.l2gw.util.Files;

/**
 * @author: rage
 * @date: 10.10.11 15:52
 */
public class TestServer extends Functions implements ScriptFile
{
	public void onLoad()
	{
		if(isActive())
		{
			_log.info("Loaded Event: Test Server [state: activated]");
			SpawnTable.getInstance().startEventSpawn("test_server");
		}
		else
			_log.info("Loaded Event: Test Server [state: deactivated]");
	}

	/**
	 * Читает статус эвента из базы.
	 *
	 * @return
	 */
	private static boolean isActive()
	{
		return ServerVariables.getString("test_server", "off").equalsIgnoreCase("on");
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
			ServerVariables.set("test_server", "on");
			SpawnTable.getInstance().startEventSpawn("test_server");
			_log.info("Event 'Test Server' started.");
		}
		else
			player.sendMessage("Event 'Test Server' already started.");

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
			ServerVariables.unset("test_server");
			SpawnTable.getInstance().stopEventSpawn("test_server", true);
			_log.info("Event 'Test Server' stopped.");
		}
		else
			player.sendMessage("Event 'Test Server' not started.");

		show(Files.read("data/html/admin/events.htm", player), player);
	}

	public void onReload()
	{
	}

	public void onShutdown()
	{
	}
}
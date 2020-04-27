package events.CofferofShadows;

import ru.l2gw.gameserver.Config;
import ru.l2gw.extensions.multilang.CustomMessage;
import ru.l2gw.extensions.scripts.Functions;
import ru.l2gw.extensions.scripts.ScriptFile;
import ru.l2gw.gameserver.Announcements;
import ru.l2gw.gameserver.instancemanager.ServerVariables;
import ru.l2gw.gameserver.model.L2Object;
import ru.l2gw.gameserver.model.L2Player;
import ru.l2gw.gameserver.model.L2Spawn;
import ru.l2gw.gameserver.model.gmaccess.AdminTemplateManager;
import ru.l2gw.gameserver.tables.NpcTable;
import ru.l2gw.gameserver.templates.L2NpcTemplate;
import ru.l2gw.util.Files;
import ru.l2gw.util.Util;

import java.util.ArrayList;

// Эвент Coffer of Shadows
public class CofferofShadows extends Functions implements ScriptFile
{
	public static L2Object self;
	public static L2Object npc;
	private static int COFFER_PRICE = 50000; // 50.000 adena at x1 servers
	private static int COFFER_ID = 8659;
	private static int EVENT_MANAGER_ID = 32091;
	private static ArrayList<L2Spawn> _spawns = new ArrayList<L2Spawn>();

	private static boolean _active = false;

	/**
	 * Спавнит эвент менеджеров
	 */
	private void spawnEventManagers()
	{
		final int EVENT_MANAGERS[][] = { { -14823, 123567, -3143, 8192 }, // Gludio
				{ -83159, 150914, -3155, 49152 }, // Gludin
				{ 18600, 145971, -3095, 7400 }, // Dion
				{ 82158, 148609, -3493, 60 }, // Giran
				{ 110992, 218753, -3568, 0 }, // Hiene
				{ 116339, 75424, -2738, 0 }, // Hunter Village
				{ 81140, 55218, -1551, 32768 }, // Oren
				{ 147148, 27401, -2231, 2300 }, // Aden
				{ 43532, -46807, -823, 57344 }, // Rune
				{ 87765, -141947, -1367, 6500 }, // Schuttgart
				{ 147154, -55527, -2807, 61300 } // Goddard
		};

		L2NpcTemplate template = NpcTable.getTemplate(EVENT_MANAGER_ID);
		for(int[] element : EVENT_MANAGERS)
			try
			{
				L2Spawn sp = new L2Spawn(template);
				sp.setLocx(element[0]);
				sp.setLocy(element[1]);
				sp.setLocz(element[2]);
				sp.setAmount(1);
				sp.setHeading(element[3]);
				sp.setRespawnDelay(0);
				sp.init();
				_spawns.add(sp);
			}
			catch(ClassNotFoundException e)
			{
				e.printStackTrace();
			}
	}

	/**
	 * Удаляет спавн эвент менеджеров
	 */
	private void unSpawnEventManagers()
	{
		for(L2Spawn sp : _spawns)
		{
			sp.stopRespawn();
			sp.getLastSpawn().deleteMe();
		}
		_spawns.clear();
	}

	/**
	 * Читает статус эвента из базы.
	 * @return
	 */
	private static boolean isActive()
	{
		return ServerVariables.getString("CofferofShadows", "off").equalsIgnoreCase("on");
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
			ServerVariables.set("CofferofShadows", "on");
			spawnEventManagers();
			_log.info("Event: Coffer of Shadows started.");
			Announcements.getInstance().announceByCustomMessage("scripts.events.CofferofShadows.AnnounceEventStarted", null);
		}
		else
			player.sendMessage("Event 'Coffer of Shadows' already started.");

		_active = true;
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
			ServerVariables.unset("CofferofShadows");
			unSpawnEventManagers();
			_log.info("Event: Coffer of Shadows stopped.");
			Announcements.getInstance().announceByCustomMessage("scripts.events.CofferofShadows.AnnounceEventStoped", null);
		}
		else
			player.sendMessage("Event 'Coffer of Shadows' not started.");

		_active = false;
		show(Files.read("data/html/admin/events.htm", player), player);
	}

	/**
	* Продает 1 сундук игроку
	*/
	public static void buycoffer()
	{
		L2Player player = (L2Player) self;

		if(!player.isQuestContinuationPossible())
			return;

		if(player.isActionsDisabled() || player.isSitting() || player.getLastNpc().getDistance(player) > 300)
			return;

		if(player.reduceAdena("Coffer", COFFER_PRICE * Config.EVENT_CofferOfShadowsPriceRate, null, true))
		{
			player.addItem("CofferofShadows", COFFER_ID, 1, player.getLastNpc(), true);
			player.getInventory().sendItemList(true);
		}
	}

	/**
	 * Добавляет в диалоги эвент менеджеров строчку с байпасом для покупки сундука
	 */
	public static String DialogAppend_32091(Integer val)
	{
		if(val != 0)
			return "";

		String price = Util.formatAdena(COFFER_PRICE * Config.EVENT_CofferOfShadowsPriceRate);

		String append = "<a action=\"bypass -h scripts_events.CofferofShadows.CofferofShadows:buycoffer\">";
		append += new CustomMessage("scripts.events.CofferofShadows.buycoffer", self).addString(price);
		append += "</a>";

		return append;
	}

	public void onLoad()
	{
		if(isActive())
		{
			_active = true;
			spawnEventManagers();
			_log.info("Loaded Event: Coffer of Shadows [state: activated]");
		}
		else
			_log.info("Loaded Event: Coffer of Shadows [state: deactivated]");
	}

	public void onReload()
	{
		unSpawnEventManagers();
	}

	public void onShutdown()
	{
		unSpawnEventManagers();
	}

	public static void OnPlayerEnter(L2Player player)
	{
		if(_active)
			Announcements.getInstance().announceToPlayerByCustomMessage(player, "scripts.events.CofferofShadows.AnnounceEventStarted", null);
	}
}
package events.TheFallHarvest;

import ru.l2gw.gameserver.Config;
import ru.l2gw.commons.math.Rnd;
import ru.l2gw.extensions.scripts.Functions;
import ru.l2gw.extensions.scripts.ScriptFile;
import ru.l2gw.gameserver.Announcements;
import ru.l2gw.gameserver.handler.IOnDieHandler;
import ru.l2gw.gameserver.instancemanager.ServerVariables;
import ru.l2gw.gameserver.model.L2Character;
import ru.l2gw.gameserver.model.L2Object;
import ru.l2gw.gameserver.model.L2Player;
import ru.l2gw.gameserver.model.L2Spawn;
import ru.l2gw.gameserver.model.gmaccess.AdminTemplateManager;
import ru.l2gw.gameserver.model.instances.L2ItemInstance;
import ru.l2gw.gameserver.model.instances.L2NpcInstance;
import ru.l2gw.gameserver.serverpackets.SystemMessage;
import ru.l2gw.gameserver.tables.ItemTable;
import ru.l2gw.gameserver.tables.NpcTable;
import ru.l2gw.gameserver.templates.L2NpcTemplate;
import ru.l2gw.util.Files;

import java.util.ArrayList;

public class TheFallHarvest extends Functions implements ScriptFile, IOnDieHandler
{
	public static L2Object self;
	public static L2NpcInstance npc;
	private static int EVENT_MANAGER_ID = 31255;
	private static ArrayList<L2Spawn> _spawns = new ArrayList<L2Spawn>();

	private static boolean _active = false;

	public void onLoad()
	{
		if(isActive())
		{
			_active = true;
			spawnEventManagers();
			_log.info("Loaded Event: The Fall Harvest [state: activated]");
		}
		else
			_log.info("Loaded Event: The Fall Harvest [state: deactivated]");
	}

	/**
	 * Читает статус эвента из базы.
	 * @return
	 */
	private static boolean isActive()
	{
		return ServerVariables.getString("TheFallHarvest", "off").equalsIgnoreCase("on");
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
			ServerVariables.set("TheFallHarvest", "on");
			spawnEventManagers();
			_log.info("Event 'The Fall Harvest' started.");
			Announcements.getInstance().announceByCustomMessage("scripts.events.TheFallHarvest.AnnounceEventStarted", null);
		}
		else
			player.sendMessage("Event 'The Fall Harvest' already started.");

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
			ServerVariables.unset("TheFallHarvest");
			unSpawnEventManagers();
			_log.info("Event 'The Fall Harvest' stopped.");
			Announcements.getInstance().announceByCustomMessage("scripts.events.TheFallHarvest.AnnounceEventStoped", null);
		}
		else
			player.sendMessage("Event 'The Fall Harvest' not started.");

		_active = false;

		show(Files.read("data/html/admin/events.htm", player), player);
	}

	/**
	 * Спавнит эвент менеджеров
	 */
	private void spawnEventManagers()
	{
		final int EVENT_MANAGERS[][] = {
				{ 81921, 148921, -3467, 16384 },
				{ 146405, 28360, -2269, 49648 },
				{ 19319, 144919, -3103, 31135 },
				{ -82805, 149890, -3129, 33202 },
				{ -12347, 122549, -3104, 32603 },
				{ 110642, 220165, -3655, 61898 },
				{ 116619, 75463, -2721, 20881 },
				{ 85513, 16014, -3668, 23681 },
				{ 81999, 53793, -1496, 61621 },
				{ 148159, -55484, -2734, 44315 },
				{ 44185, -48502, -797, 27479 },
				{ 86899, -143229, -1293, 22021 } };

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

	public void onReload()
	{
		unSpawnEventManagers();
	}

	public void onShutdown()
	{
		unSpawnEventManagers();
	}

	/**
	 * Обработчик смерти мобов, управляющий эвентовым дропом
	 */
	@Override
	public void onDie(L2Character cha, L2Character killer)
	{
		if(_active && cha.isMonster() && !cha.isRaid() && killer != null && killer.getPlayer() != null && Rnd.get(1000) <= Config.TFH_POLLEN_CHANCE * Config.RATE_DROP_ITEMS && Math.abs(cha.getLevel() - killer.getLevel()) < 10)
		{
			L2ItemInstance item = ItemTable.getInstance().createItem("TheFallHarvest", 6391, 1, killer.getPlayer(), cha);
			((L2NpcInstance) cha).dropItem(killer.getPlayer(), item);
		}
	}

	/**
	* Обмен эвентовых вещей, где var - номер группы обмена.<br>
	* <li>Группа 0: 1 Pollen на 1 Squash Seed
	* <li>Группа 1: 50 Pollen на 1 Large Squash Seed
	* <li>Группа 2: 10 Pollen на 1 Chrono Darbuka
	*/
	public static void exchange(String[] var)
	{
		final int price_id[] = { 6391, 6391, 6391 };
		final int price_count[] = { 1, 50, 10 };
		final int goodds_id[] = { 6389, 6390, 7058 };
		final int goodds_count[] = { 1, 1, 1 };

		int grp = Integer.parseInt(var[0]);
		if(grp > price_id.length || grp < 0)
			return;

		L2Player player = (L2Player) self;

		if(!player.isQuestContinuationPossible())
			return;

		if(player.isActionsDisabled() || player.isSitting() || player.getLastNpc().getDistance(player) > 300)
			return;

		if(getItemCount(player, price_id[grp]) < price_count[grp])
		{
			player.sendPacket(new SystemMessage(SystemMessage.YOU_DO_NOT_HAVE_ENOUGH_REQUIRED_ITEMS));
			return;
		}

		removeItem(player, price_id[grp], price_count[grp]);
		addItem(player, goodds_id[grp], goodds_count[grp]);
	}

	public static void OnPlayerEnter(L2Player player)
	{
		if(_active)
			Announcements.getInstance().announceToPlayerByCustomMessage(player, "scripts.events.TheFallHarvest.AnnounceEventStarted", null);
	}
}
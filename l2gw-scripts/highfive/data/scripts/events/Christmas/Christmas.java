package events.Christmas;

import ru.l2gw.gameserver.Config;
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
import ru.l2gw.commons.math.Rnd;

import java.util.ArrayList;

public class Christmas extends Functions implements ScriptFile, IOnDieHandler
{
	public static L2Object self;
	public static L2NpcInstance npc;
	private static int EVENT_MANAGER_ID = 31863;
	private static int CTREE_ID = 13006;

	private static int[][] _dropdata = {
	// Item, chance
			{ 5556, 20 }, //Star Ornament 2%
			{ 5557, 20 }, //Bead Ornament 2%
			{ 5558, 50 }, //Fir Tree Branch 5%
			{ 5559, 5 }, //Flower Pot 0.5%
			// Музыкальные кристаллы 0.2%
			{ 5562, 2 },
			{ 5563, 2 },
			{ 5564, 2 },
			{ 5565, 2 },
			{ 5566, 2 },
			{ 5583, 2 },
			{ 5584, 2 },
			{ 5585, 2 },
			{ 5586, 2 },
			{ 5587, 2 },
			{ 4411, 2 },
			{ 4412, 2 },
			{ 4413, 2 },
			{ 4414, 2 },
			{ 4415, 2 },
			{ 4416, 2 },
			{ 4417, 2 },
			{ 5010, 2 },
			{ 7061, 2 },
			{ 7062, 2 },
			{ 6903, 2 },
			{ 8555, 2 } };

	private static ArrayList<L2Spawn> _spawns = new ArrayList<L2Spawn>();

	private static boolean _active = false;

	public void onLoad()
	{
		if(isActive())
		{
			_active = true;
			spawnEventManagers();
			_log.info("Loaded Event: Christmas [state: activated]");
		}
		else
			_log.info("Loaded Event: Christmas [state: deactivated]");
	}

	/**
	 * Читает статус эвента из базы.
	 * @return
	 */
	private static boolean isActive()
	{
		return ServerVariables.getString("Christmas", "off").equalsIgnoreCase("on");
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
			ServerVariables.set("Christmas", "on");
			spawnEventManagers();
			_log.info("Event 'Christmas' started.");
			Announcements.getInstance().announceByCustomMessage("scripts.events.Christmas.AnnounceEventStarted", null);
		}
		else
			player.sendMessage("Event 'Christmas' already started.");

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
			ServerVariables.unset("Christmas");
			unSpawnEventManagers();
			_log.info("Event 'Christmas' stopped.");
			Announcements.getInstance().announceByCustomMessage("scripts.events.Christmas.AnnounceEventStoped", null);
		}
		else
			player.sendMessage("Event 'Christmas' not started.");

		_active = false;

		show(Files.read("data/html/admin/events.htm", player), player);
	}

	/**
	 * Спавнит эвент менеджеров и рядом ёлки
	 */
	private void spawnEventManagers()
	{
		final int EVENT_MANAGERS[][] = {
				{ 81921, 148921, -3467, 16384 },
				{ 146405, 28360, -2269, 49648 },
				{ 19319, 144919, -3103, 31135 },
				{ -82805, 149890, -3129, 16384 },
				{ -12347, 122549, -3104, 16384 },
				{ 110642, 220165, -3655, 61898 },
				{ 116619, 75463, -2721, 20881 },
				{ 85513, 16014, -3668, 23681 },
				{ 81999, 53793, -1496, 61621 },
				{ 148159, -55484, -2734, 44315 },
				{ 44185, -48502, -797, 27479 },
				{ 86899, -143229, -1293, 8192 } };

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

		final int CTREES[][] = {
				{ 81961, 148921, -3467, 0 },
				{ 146445, 28360, -2269, 0 },
				{ 19319, 144959, -3103, 0 },
				{ -82845, 149890, -3129, 0 },
				{ -12387, 122549, -3104, 0 },
				{ 110602, 220165, -3655, 0 },
				{ 116659, 75463, -2721, 0 },
				{ 85553, 16014, -3668, 0 },
				{ 81999, 53743, -1496, 0 },
				{ 148199, -55484, -2734, 0 },
				{ 44185, -48542, -797, 0 },
				{ 86859, -143229, -1293, 0 } };

		template = NpcTable.getTemplate(CTREE_ID);
		for(int[] element : CTREES)
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
		if(_active && cha.isMonster() && !cha.isRaid() && killer != null && killer.getPlayer() != null && Math.abs(cha.getLevel() - killer.getLevel()) < 10)
		{
			int dropCounter = 0;
			for(int[] drop : _dropdata)
				if(Rnd.get(1000) <= drop[1] * Config.RATE_DROP_ITEMS)
				{
					dropCounter++;

					L2ItemInstance item = ItemTable.getInstance().createItem("Christmas", drop[0], 1, killer.getPlayer(), cha);
					((L2NpcInstance) cha).dropItem(killer.getPlayer(), item);

					// Из одного моба выпадет не более 3-х эвентовых предметов
					if(dropCounter > 2)
						break;
				}
		}
	}

	public static void exchange(String[] var)
	{
		L2Player player = (L2Player) self;

		if(!player.isQuestContinuationPossible())
			return;

		if(player.isActionsDisabled() || player.isSitting() || player.getLastNpc().getDistance(player) > 300)
			return;

		if(var[0].equalsIgnoreCase("0"))
		{
			if(getItemCount(player, 5556) >= 4 && getItemCount(player, 5557) >= 4 && getItemCount(player, 5558) >= 10 && getItemCount(player, 5559) >= 1)
			{
				removeItem(player, 5556, 4);
				removeItem(player, 5557, 4);
				removeItem(player, 5558, 10);
				removeItem(player, 5559, 1);
				addItem(player, 5560, 1); // Christmas Tree
				return;
			}
			player.sendPacket(new SystemMessage(SystemMessage.YOU_DO_NOT_HAVE_ENOUGH_REQUIRED_ITEMS));
		}
		if(var[0].equalsIgnoreCase("1"))
		{
			if(getItemCount(player, 5560) >= 10)
			{
				removeItem(player, 5560, 10);
				addItem(player, 5561, 1); // Special Christmas Tree
				return;
			}
			player.sendPacket(new SystemMessage(SystemMessage.YOU_DO_NOT_HAVE_ENOUGH_REQUIRED_ITEMS));
		}
		if(var[0].equalsIgnoreCase("2"))
		{
			if(getItemCount(player, 5560) >= 10)
			{
				removeItem(player, 5560, 10);
				addItem(player, 7836, 1); // Santa's Hat
				return;
			}
			player.sendPacket(new SystemMessage(SystemMessage.YOU_DO_NOT_HAVE_ENOUGH_REQUIRED_ITEMS));
		}
		if(var[0].equalsIgnoreCase("3"))
		{
			if(getItemCount(player, 5560) >= 10)
			{
				removeItem(player, 5560, 10);
				addItem(player, 8936, 1); // Santa's Antlers
				return;
			}
			player.sendPacket(new SystemMessage(SystemMessage.YOU_DO_NOT_HAVE_ENOUGH_REQUIRED_ITEMS));
		}
	}

	public static void OnPlayerEnter(L2Player player)
	{
		if(_active)
			Announcements.getInstance().announceToPlayerByCustomMessage(player, "scripts.events.Christmas.AnnounceEventStarted", null);
	}
}
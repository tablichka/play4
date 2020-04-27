package events.glitmedal;

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

/**
 * User: darkevil
 * Date: 26.02.2008
 * Time: 1:17:42
 */
public class glitmedal extends Functions implements ScriptFile, IOnDieHandler
{
	public static L2Object self;
	public static L2NpcInstance npc;
	private static int EVENT_MANAGER_ID1 = 31228; // Roy
	private static int EVENT_MANAGER_ID2 = 31229; // Winnie

	// Шанс выбить медали
	private static int MEDAL_CHANCE = Config.GLIT_MEDAL_CHANCE;
	private static int GLITTMEDAL_CHANCE = Config.GLIT_GLITTMEDAL_CHANCE;
	private static boolean EnableRate = Config.GLIT_EnableRate;

	// Для временного статуса который выдается в игре рандомно либо 0 либо 1
	private int isTalker;

	// Медали
	private static int EVENT_MEDAL = 6392;
	private static int EVENT_GLITTMEDAL = 6393;

	private static int Badge_of_Rabbit = 6399;
	private static int Badge_of_Hyena = 6400;
	private static int Badge_of_Fox = 6401;
	private static int Badge_of_Wolf = 6402;

	private static ArrayList<L2Spawn> _spawns = new ArrayList<L2Spawn>();
	private static boolean _active = false;

	public void onLoad()
	{
		if(isActive())
		{
			_active = true;
			spawnEventManagers();
			_log.info("Loaded Event: L2 Medal Collection Event [state: activated]");
			if(MEDAL_CHANCE > 80 || GLITTMEDAL_CHANCE > 50)
				_log.info("Event L2 Medal Collection: << W A R N I N G >> RATES IS TO HIGH!!!");
		}
		else
			_log.info("Loaded Event: L2 Medal Collection Event [state: deactivated]");
	}

	/**
	 * Читает статус эвента из базы.
	 *
	 * @return
	 */
	private static boolean isActive()
	{
		return ServerVariables.getString("glitter", "off").equalsIgnoreCase("on");
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
			ServerVariables.set("glitter", "on");
			spawnEventManagers();
			_log.info("Event 'L2 Medal Collection Event' started.");
			Announcements.getInstance().announceByCustomMessage("scripts.events.glitmedal.AnnounceEventStarted", null);
		}
		else
			player.sendMessage("Event 'L2 Medal Collection Event' already started.");

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
			ServerVariables.unset("glitter");
			unSpawnEventManagers();
			_log.info("Event 'L2 Medal Collection Event' stopped.");
			Announcements.getInstance().announceByCustomMessage("scripts.events.glitmedal.AnnounceEventStoped", null);
		}
		else
			player.sendMessage("Event 'L2 Medal Collection Event' not started.");

		_active = false;

		show(Files.read("data/html/admin/events.htm", player), player);
	}

	public static void OnPlayerEnter(L2Player player)
	{
		if(_active)
			Announcements.getInstance().announceToPlayerByCustomMessage(player, "scripts.events.glitmedal.AnnounceEventStarted", null);
	}

	/**
	 * Спавнит эвент менеджеров
	 */
	private void spawnEventManagers()
	{
		// 1й эвент кот
		final int EVENT_MANAGERS1[][] = {
				{ 147893, -56622, -2776, 0 },
				{ -81070, 149960, -3040, 0 },
				{ 82882, 149332, -3464, 49000 },
				{ 44176, -48732, -800, 33000 },
				{ 147920, 25664, -2000, 16384 },
				{ 117498, 76630, -2695, 38000 },
				{ 111776, 221104, -3543, 16384 },
				{ -84516, 242971, -3730, 34000 },
				{ -13073, 122801, -3117, 0 },
				{ -44337, -113669, -224, 0 },
				{ 11281, 15652, -4584, 25000 },
				{ 44122, 50784, -3059, 57344 },
				{ 80986, 54504, -1525, 32768 },
				{ 114733, -178691, -821, 0 },
				{ 18178, 145149, -3054, 7400 }, };

		L2NpcTemplate template = NpcTable.getTemplate(EVENT_MANAGER_ID1);
		for(int[] element : EVENT_MANAGERS1)
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

		// 2й эвент кот
		final int EVENT_MANAGERS2[][] = {
				{ 147960, -56584, -2776, 0 },
				{ -81070, 149860, -3040, 0 },
				{ 82798, 149332, -3464, 49000 },
				{ 44176, -48688, -800, 33000 },
				{ 147985, 25664, -2000, 16384 },
				{ 117459, 76664, -2695, 38000 },
				{ 111724, 221111, -3543, 16384 },
				{ -84516, 243015, -3730, 34000 },
				{ -13073, 122841, -3117, 0 },
				{ -44342, -113726, -240, 0 },
				{ 11327, 15682, -4584, 25000 },
				{ 44157, 50827, -3059, 57344 },
				{ 80986, 54452, -1525, 32768 },
				{ 114719, -178742, -821, 0 },
				{ 18154, 145192, -3054, 7400 }, };

		template = NpcTable.getTemplate(EVENT_MANAGER_ID2);
		for(int[] element : EVENT_MANAGERS2)
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
		if(_active && cha.isMonster() && !cha.isRaid() && cha.getLevel() >= Config.GLIT_MIN_MOB_LEVEL && killer != null && killer.getPlayer() != null && Math.abs(cha.getLevel() - killer.getLevel()) <= Config.GLIT_MAX_LEVEL_DIFF && !killer.getPlayer().getVarB("NoExp"))
		{
			if(Rnd.chance(MEDAL_CHANCE * (EnableRate ? Config.RATE_DROP_ITEMS : 1)))
			{
				L2ItemInstance item = ItemTable.getInstance().createItem("L2 Medal Collection Event", EVENT_MEDAL, 1, killer.getPlayer(), cha);
				((L2NpcInstance) cha).dropItem(killer.getPlayer(), item);
			}
			if(killer.getPlayer().getInventory().getCountOf(Badge_of_Wolf) == 0 && Rnd.chance(GLITTMEDAL_CHANCE * (EnableRate ? Config.RATE_DROP_ITEMS : 1)))
			{
				L2ItemInstance item = ItemTable.getInstance().createItem("L2 Medal Collection Event", EVENT_GLITTMEDAL, 1, killer.getPlayer(), cha);
				((L2NpcInstance) cha).dropItem(killer.getPlayer(), item);
			}
		}
	}

	public void glitchang()
	{
		L2Player player = (L2Player) self;

		if(getItemCount(player, EVENT_MEDAL) >= 1000)
		{
			removeItem(player, EVENT_MEDAL, 1000);
			addItem(player, EVENT_GLITTMEDAL, 10);
			return;
		}
		player.sendPacket(new SystemMessage(SystemMessage.YOU_DO_NOT_HAVE_ENOUGH_REQUIRED_ITEMS));
	}

	public void medal()
	{
		L2Player player = (L2Player) self;

		if(getItemCount(player, Badge_of_Wolf) >= 1)
		{
			show(Files.read("data/scripts/events/glitmedal/event_col_agent1_q0996_05.htm", player), player);
			return;
		}
		else if(getItemCount(player, Badge_of_Fox) >= 1)
		{
			show(Files.read("data/scripts/events/glitmedal/event_col_agent1_q0996_04.htm", player), player);
			return;
		}
		else if(getItemCount(player, Badge_of_Hyena) >= 1)
		{
			show(Files.read("data/scripts/events/glitmedal/event_col_agent1_q0996_03.htm", player), player);
			return;
		}
		else if(getItemCount(player, Badge_of_Rabbit) >= 1)
		{
			show(Files.read("data/scripts/events/glitmedal/event_col_agent1_q0996_02.htm", player), player);
			return;
		}

		show(Files.read("data/scripts/events/glitmedal/event_col_agent1_q0996_01.htm", player), player);
		return;
	}

	public void medalb()
	{
		L2Player player = (L2Player) self;

		if(getItemCount(player, Badge_of_Wolf) >= 1)
		{
			show(Files.read("data/scripts/events/glitmedal/event_col_agent2_q0996_05.htm", player), player);
			return;
		}
		else if(getItemCount(player, Badge_of_Fox) >= 1)
		{
			show(Files.read("data/scripts/events/glitmedal/event_col_agent2_q0996_04.htm", player), player);
			return;
		}
		else if(getItemCount(player, Badge_of_Hyena) >= 1)
		{
			show(Files.read("data/scripts/events/glitmedal/event_col_agent2_q0996_03.htm", player), player);
			return;
		}
		else if(getItemCount(player, Badge_of_Rabbit) >= 1)
		{
			show(Files.read("data/scripts/events/glitmedal/event_col_agent2_q0996_02.htm", player), player);
			return;
		}

		show(Files.read("data/scripts/events/glitmedal/event_col_agent2_q0996_01.htm", player), player);
		return;
	}

	public void game()
	{
		L2Player player = (L2Player) self;

		if(getItemCount(player, Badge_of_Fox) >= 1)
		{
			if(getItemCount(player, EVENT_GLITTMEDAL) >= 40)
			{
				show(Files.read("data/scripts/events/glitmedal/event_col_agent2_q0996_11.htm", player), player);
				return;
			}
			show(Files.read("data/scripts/events/glitmedal/event_col_agent2_q0996_12.htm", player), player);
			return;
		}
		else if(getItemCount(player, Badge_of_Hyena) >= 1)
		{
			if(getItemCount(player, EVENT_GLITTMEDAL) >= 20)
			{
				show(Files.read("data/scripts/events/glitmedal/event_col_agent2_q0996_11.htm", player), player);
				return;
			}
			show(Files.read("data/scripts/events/glitmedal/event_col_agent2_q0996_12.htm", player), player);
			return;
		}
		else if(getItemCount(player, Badge_of_Rabbit) >= 1)
		{
			if(getItemCount(player, EVENT_GLITTMEDAL) >= 10)
			{
				show(Files.read("data/scripts/events/glitmedal/event_col_agent2_q0996_11.htm", player), player);
				return;
			}
			show(Files.read("data/scripts/events/glitmedal/event_col_agent2_q0996_12.htm", player), player);
			return;
		}

		else if(getItemCount(player, EVENT_GLITTMEDAL) >= 5)
		{
			show(Files.read("data/scripts/events/glitmedal/event_col_agent2_q0996_11.htm", player), player);
			return;
		}

		show(Files.read("data/scripts/events/glitmedal/event_col_agent2_q0996_12.htm", player), player);
		return;
	}

	public void gamea()
	{
		L2Player player = (L2Player) self;
		isTalker = Rnd.get(2);

		if(getItemCount(player, Badge_of_Fox) >= 1)
		{
			if(getItemCount(player, EVENT_GLITTMEDAL) >= 40)
				if(isTalker == 1)
				{
					removeItem(player, Badge_of_Fox, 1);
					removeItem(player, EVENT_GLITTMEDAL, 40);
					addItem(player, Badge_of_Wolf, 1);
					show(Files.read("data/scripts/events/glitmedal/event_col_agent2_q0996_24.htm", player), player);
					return;
				}
				else if(isTalker == 0)
				{
					removeItem(player, EVENT_GLITTMEDAL, 40);
					show(Files.read("data/scripts/events/glitmedal/event_col_agent2_q0996_25.htm", player), player);
					return;
				}
			show(Files.read("data/scripts/events/glitmedal/event_col_agent2_q0996_26.htm", player), player);
			return;
		}

		else if(getItemCount(player, Badge_of_Hyena) >= 1)
		{
			if(getItemCount(player, EVENT_GLITTMEDAL) >= 20)
				if(isTalker == 1)
				{
					removeItem(player, Badge_of_Hyena, 1);
					removeItem(player, EVENT_GLITTMEDAL, 20);
					addItem(player, Badge_of_Fox, 1);
					show(Files.read("data/scripts/events/glitmedal/event_col_agent2_q0996_23.htm", player), player);
					return;
				}
				else if(isTalker == 0)
				{
					removeItem(player, EVENT_GLITTMEDAL, 20);
					show(Files.read("data/scripts/events/glitmedal/event_col_agent2_q0996_25.htm", player), player);
					return;
				}
			show(Files.read("data/scripts/events/glitmedal/event_col_agent2_q0996_26.htm", player), player);
			return;
		}

		else if(getItemCount(player, Badge_of_Rabbit) >= 1)
		{
			if(getItemCount(player, EVENT_GLITTMEDAL) >= 10)
				if(isTalker == 1)
				{
					removeItem(player, Badge_of_Rabbit, 1);
					removeItem(player, EVENT_GLITTMEDAL, 10);
					addItem(player, Badge_of_Hyena, 1);
					show(Files.read("data/scripts/events/glitmedal/event_col_agent2_q0996_22.htm", player), player);
					return;
				}
				else if(isTalker == 0)
				{
					removeItem(player, EVENT_GLITTMEDAL, 10);
					show(Files.read("data/scripts/events/glitmedal/event_col_agent2_q0996_25.htm", player), player);
					return;
				}
			show(Files.read("data/scripts/events/glitmedal/event_col_agent2_q0996_26.htm", player), player);
			return;
		}

		if(getItemCount(player, EVENT_GLITTMEDAL) >= 5)
			if(isTalker == 1)
			{
				removeItem(player, EVENT_GLITTMEDAL, 5);
				addItem(player, Badge_of_Rabbit, 1);
				show(Files.read("data/scripts/events/glitmedal/event_col_agent2_q0996_21.htm", player), player);
				return;
			}
			else if(isTalker == 0)
			{
				removeItem(player, EVENT_GLITTMEDAL, 5);
				show(Files.read("data/scripts/events/glitmedal/event_col_agent2_q0996_25.htm", player), player);
				return;
			}
		show(Files.read("data/scripts/events/glitmedal/event_col_agent2_q0996_26.htm", player), player);
		return;
	}

	public void gameb()
	{
		L2Player player = (L2Player) self;
		isTalker = Rnd.get(2);

		if(getItemCount(player, Badge_of_Fox) >= 1)
		{
			if(getItemCount(player, EVENT_GLITTMEDAL) >= 40)
				if(isTalker == 1)
				{
					removeItem(player, Badge_of_Fox, 1);
					removeItem(player, EVENT_GLITTMEDAL, 40);
					addItem(player, Badge_of_Wolf, 1);
					show(Files.read("data/scripts/events/glitmedal/event_col_agent2_q0996_34.htm", player), player);
					return;
				}
				else if(isTalker == 0)
				{
					removeItem(player, EVENT_GLITTMEDAL, 40);
					show(Files.read("data/scripts/events/glitmedal/event_col_agent2_q0996_35.htm", player), player);
					return;
				}
			show(Files.read("data/scripts/events/glitmedal/event_col_agent2_q0996_26.htm", player), player);
			return;
		}

		else if(getItemCount(player, Badge_of_Hyena) >= 1)
		{
			if(getItemCount(player, EVENT_GLITTMEDAL) >= 20)
				if(isTalker == 1)
				{
					removeItem(player, Badge_of_Hyena, 1);
					removeItem(player, EVENT_GLITTMEDAL, 20);
					addItem(player, Badge_of_Fox, 1);
					show(Files.read("data/scripts/events/glitmedal/event_col_agent2_q0996_33.htm", player), player);
					return;
				}
				else if(isTalker == 0)
				{
					removeItem(player, EVENT_GLITTMEDAL, 20);
					show(Files.read("data/scripts/events/glitmedal/event_col_agent2_q0996_35.htm", player), player);
					return;
				}
			show(Files.read("data/scripts/events/glitmedal/event_col_agent2_q0996_26.htm", player), player);
			return;
		}

		else if(getItemCount(player, Badge_of_Rabbit) >= 1)
		{
			if(getItemCount(player, EVENT_GLITTMEDAL) >= 10)
				if(isTalker == 1)
				{
					removeItem(player, Badge_of_Rabbit, 1);
					removeItem(player, EVENT_GLITTMEDAL, 10);
					addItem(player, Badge_of_Hyena, 1);
					show(Files.read("data/scripts/events/glitmedal/event_col_agent2_q0996_32.htm", player), player);
					return;
				}
				else if(isTalker == 0)
				{
					removeItem(player, EVENT_GLITTMEDAL, 10);
					show(Files.read("data/scripts/events/glitmedal/event_col_agent2_q0996_35.htm", player), player);
					return;
				}
			show(Files.read("data/scripts/events/glitmedal/event_col_agent2_q0996_26.htm", player), player);
			return;
		}

		if(getItemCount(player, EVENT_GLITTMEDAL) >= 5)
			if(isTalker == 1)
			{
				removeItem(player, EVENT_GLITTMEDAL, 5);
				addItem(player, Badge_of_Rabbit, 1);
				show(Files.read("data/scripts/events/glitmedal/event_col_agent2_q0996_31.htm", player), player);
				return;
			}
			else if(isTalker == 0)
			{
				removeItem(player, EVENT_GLITTMEDAL, 5);
				show(Files.read("data/scripts/events/glitmedal/event_col_agent2_q0996_35.htm", player), player);
				return;
			}
		show(Files.read("data/scripts/events/glitmedal/event_col_agent2_q0996_26.htm", player), player);
		return;
	}
}
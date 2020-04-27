package events.l2day;

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
import java.util.HashMap;

@SuppressWarnings("unused")
public class l2day extends Functions implements ScriptFile, IOnDieHandler
{
	public static L2Object self;
	public static L2NpcInstance npc;
	private static int EVENT_MANAGER_ID = 31230;

	// Буквы
	private static int[] A = { 3875, 3 }; // 1
	private static int[] C = { 3876, 3 }; // 1
	private static int[] E = { 3877, 9 }; // 3
	private static int[] F = { 3878, 3 }; // 1
	private static int[] G = { 3879, 3 }; // 1
	private static int[] H = { 3880, 3 }; // 1
	private static int[] I = { 3881, 3 }; // 1
	private static int[] L = { 3882, 3 }; // 1
	private static int[] N = { 3883, 9 }; // 3
	private static int[] O = { 3884, 6 }; // 2
	private static int[] R = { 3885, 3 }; // 1
	private static int[] S = { 3886, 3 }; // 1
	private static int[] T = { 3887, 6 }; // 2
	private static int[] II = { 3888, 3 }; // 1

	private static int[][] letters = { A, C, E, F, G, H, I, L, N, O, R, S, T, II };

	// Награды
	private static int BSOE = 3958;
	private static int BSOR = 3959;
	private static int GUIDANCE = 3926;
	private static int WHISPER = 3927;
	private static int FOCUS = 3928;
	private static int ACUMEN = 3929;
	private static int HASTE = 3930;
	private static int AGILITY = 3931;
	private static int EMPOWER = 3932;
	private static int MIGHT = 3933;
	private static int WINDWALK = 3934;
	private static int SHIELD = 3935;

	private static int ENCH_WPN_D = 955;
	private static int ENCH_WPN_C = 951;
	private static int ENCH_WPN_B = 947;
	private static int ENCH_WPN_A = 729;

	private static int RABBIT_EARS = 8947;
	private static int FEATHERED_HAT = 8950;
	private static int FAIRY_ANTENNAE = 8949;
	private static int ARTISANS_GOOGLES = 8951;
	private static int LITTLE_ANGEL_WING = 8948;

	private static int RING_OF_ANT_QUIEEN = 6660;
	private static int RING_OF_CORE = 6662;

	private static HashMap<String, Integer[][]> _words = new HashMap<String, Integer[][]>();

	static
	{
		_words.put("LineageII", new Integer[][] { { L[0], 1 }, { I[0], 1 }, { N[0], 1 }, { E[0], 2 }, { A[0], 1 }, { G[0], 1 }, { II[0], 1 } });
		_words.put("THRONE", new Integer[][] { { T[0], 1 }, { H[0], 1 }, { R[0], 1 }, { O[0], 1 }, { N[0], 1 }, { E[0], 1 } });
		_words.put("NCSOFT", new Integer[][] { { N[0], 1 }, { C[0], 1 }, { S[0], 1 }, { O[0], 1 }, { F[0], 1 }, { T[0], 1 } });
	}

	private static ArrayList<L2Spawn> _spawns = new ArrayList<L2Spawn>();

	private static boolean _active = false;

	public void onLoad()
	{
		if(isActive())
		{
			_active = true;
			spawnEventManagers();
			_log.info("Loaded Event: l2day [state: activated]");
		}
		else
			_log.info("Loaded Event: l2day [state: deactivated]");
	}

	/**
	 * Читает статус эвента из базы.
	 * @return
	 */
	private static boolean isActive()
	{
		return ServerVariables.getString("l2day", "off").equalsIgnoreCase("on");
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
			ServerVariables.set("l2day", "on");
			spawnEventManagers();
			_log.info("Event 'l2day' started.");
			Announcements.getInstance().announceByCustomMessage("scripts.events.l2day.AnnounceEventStarted", null);
		}
		else
			player.sendMessage("Event 'l2day' already started.");

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
			ServerVariables.unset("l2day");
			unSpawnEventManagers();
			_log.info("Event 'l2day' stopped.");
			Announcements.getInstance().announceByCustomMessage("scripts.events.l2day.AnnounceEventStoped", null);
		}
		else
			player.sendMessage("Event 'l2day' not started.");

		_active = false;

		show(Files.read("data/html/admin/events.htm", player), player);
	}

	/**
	 * Спавнит эвент менеджеров
	 */
	private void spawnEventManagers()
	{
		final int EVENT_MANAGERS[][] = {
				{ 19541, 145419, -3103, 30419 },
				{ 147485, -59049, -2980, 9138 },
				{ 109947, 218176, -3543, 63079 },
				{ -81363, 151611, -3121, 42910 },
				{ 144741, 28846, -2453, 2059 },
				{ 44192, -48481, -796, 23331 },
				{ -13889, 122999, -3109, 40099 },
				{ 116278, 75498, -2713, 12022 },
				{ 82029, 55936, -1519, 58708 },
				{ 147142, 28555, -2261, 59402 },
				{ 82153, 148390, -3466, 57344 }, };

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
		if(_active && cha.isMonster() && !cha.isRaid() && killer != null && killer.getPlayer() != null && killer.getLevel() - cha.getLevel() < 10)
		{
			int[] letter = letters[Rnd.get(letters.length)];
			if(Rnd.chance(letter[1] * ((L2NpcTemplate) cha.getTemplate()).hp_mod))
			{
				L2ItemInstance item = ItemTable.getInstance().createItem("l2day", letter[0], 1, killer.getPlayer(), cha);
				((L2NpcInstance) cha).dropItem(killer.getPlayer(), item);
			}
		}
	}

	/**
	* Обмен эвентовых вещей, где var - слово.
	*/
	public static void exchange(String[] var)
	{
		L2Player player = (L2Player) self;

		if(!player.isQuestContinuationPossible())
			return;

		if(player.isActionsDisabled() || player.isSitting() || player.getLastNpc().getDistance(player) > 300)
			return;

		Integer[][] mss = _words.get(var[0]);

		for(Integer[] l : mss)
			if(getItemCount(player, l[0]) < l[1])
			{
				player.sendPacket(new SystemMessage(SystemMessage.YOU_DO_NOT_HAVE_ENOUGH_REQUIRED_ITEMS));
				return;
			}

		for(Integer[] l : mss)
			removeItem(player, l[0], l[1]);

		int chance = Rnd.get(10000);
		if(var[0].equalsIgnoreCase("LineageII"))
		{
			if(chance < 8500) // 85
				addItem(player, Rnd.get(3926, 3935), 3);
			else if(chance < 9020) // 5.02
				addItem(player, BSOE, 1);
			else if(chance < 9540) // 5.2
				addItem(player, BSOR, 1);
			else if(chance < 9680) // 1.4
				addItem(player, ENCH_WPN_C, 3);
			else if(chance < 9750) // 0.7
				addItem(player, ENCH_WPN_B, 2);
			else if(chance < 9820) // 0.7
				addItem(player, ENCH_WPN_A, 1);
			else if(chance < 9870) // 0.5
				addItem(player, RABBIT_EARS, 1);
			else if(chance < 9920) // 0.5
				addItem(player, FEATHERED_HAT, 1);
			else if(chance < 9998) // 0.5
				addItem(player, FAIRY_ANTENNAE, 1);
			else if(chance == 9998) //0.01
				addItem(player, RING_OF_ANT_QUIEEN, 1);
			else if(chance == 9999) //0.01
				addItem(player, RING_OF_CORE, 1);
		}
		else if(var[0].equalsIgnoreCase("Throne"))
		{
			if(chance < 8500) // 85
				addItem(player, Rnd.get(3926, 3935), 2);
			else if(chance < 9020) // 5.02
				addItem(player, BSOE, 1);
			else if(chance < 9540) //5.02
				addItem(player, BSOR, 1);
			else if(chance < 9700) // 1.6
				addItem(player, ENCH_WPN_D, 4);
			else if(chance < 9810) // 1.1
				addItem(player, ENCH_WPN_C, 3);
			else if(chance < 9870) // 0.6
				addItem(player, ENCH_WPN_B, 2);
			else if(chance < 9930) // 0.6
				addItem(player, ARTISANS_GOOGLES, 1);
			else if(chance < 9998) // 0.5
				addItem(player, LITTLE_ANGEL_WING, 1);
			else if(chance == 9998) // 0.01
				addItem(player, RING_OF_ANT_QUIEEN, 1);
			else if(chance == 9999) // 0.01
				addItem(player, RING_OF_CORE, 1);
		}
		else if(var[0].equalsIgnoreCase("NCSOFT"))
			if(chance < 8500) // 85
				addItem(player, Rnd.get(3926, 3935), 2);
			else if(chance < 9020) // 5.02
				addItem(player, BSOE, 1);
			else if(chance < 9540) //5.02
				addItem(player, BSOR, 1);
			else if(chance < 9700) // 1.6
				addItem(player, ENCH_WPN_D, 4);
			else if(chance < 9810) // 1.1
				addItem(player, ENCH_WPN_C, 3);
			else if(chance < 9870) // 0.6
				addItem(player, ENCH_WPN_B, 2);
			else if(chance < 9930) // 0.6
				addItem(player, ARTISANS_GOOGLES, 1);
			else if(chance < 9998) // 0.5
				addItem(player, LITTLE_ANGEL_WING, 1);
			else if(chance == 9998) // 0.01
				addItem(player, RING_OF_ANT_QUIEEN, 1);
			else if(chance == 9999) // 0.01
				addItem(player, RING_OF_CORE, 1);
	}

	public static void OnPlayerEnter(L2Player player)
	{
		if(_active)
			Announcements.getInstance().announceToPlayerByCustomMessage(player, "scripts.events.l2day.AnnounceEventStarted", null);
	}
}
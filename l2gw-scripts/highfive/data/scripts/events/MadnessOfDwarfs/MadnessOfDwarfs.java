package events.MadnessOfDwarfs;

import ru.l2gw.extensions.scripts.Functions;
import ru.l2gw.extensions.scripts.ScriptFile;
import ru.l2gw.gameserver.Announcements;
import ru.l2gw.gameserver.clientpackets.Say2C;
import ru.l2gw.gameserver.instancemanager.ServerVariables;
import ru.l2gw.gameserver.model.L2Character;
import ru.l2gw.gameserver.model.L2Object;
import ru.l2gw.gameserver.model.L2Player;
import ru.l2gw.gameserver.model.L2Spawn;
import ru.l2gw.gameserver.model.gmaccess.AdminTemplateManager;
import ru.l2gw.gameserver.model.instances.L2NpcInstance;
import ru.l2gw.gameserver.serverpackets.Say2;
import ru.l2gw.gameserver.tables.NpcTable;
import ru.l2gw.util.Files;

import java.util.ArrayList;

// Эвент Madness Of Dwarfs

public class MadnessOfDwarfs extends Functions implements ScriptFile
{
	public static L2Object self;
	public static L2Object npc;
	public static boolean _isInWar = false;

	public static enum EventState
	{
		WAITING,
		SIEGE,
		LOSS_SIEGE;
	}

	private static final int EVENT_MANAGERS[] = {83446, 148638, -3431, -3331};
	private static final int EVENT_GK[] = {-114638, -249394, -2984, -3331};
	private static int EVENT_MANAGER_ID = 18491;
	private static int EVENT_GK_ID = 29055; // TODO: get another NPC, 29055 is for benom
	//[scripts_Util:Gatekeeper 108805 16092 -4936 0 @811;to the ground|Return to the ground.]
	private static int EVENT_ITEM_ID = 57;
	private static ArrayList<L2Spawn> _spawns = new ArrayList<L2Spawn>();
	private static boolean active = false;

	public void onLoad()
	{
		if(isActive())
		{
			active = true;
			spawnEventGK();
			spawnEventManagers();
			_log.info("Loaded Event: MadnessOfDwarfs [state: activated]");
		}
		else
			_log.info("Loaded Event: MadnessOfDwarfs [state: deactivated]");
	}

	public void onReload()
	{
		unSpawnEventManagers();
	}

	public void onShutdown()
	{
		onReload();
	}

	private static boolean isActive()
	{
		return ServerVariables.getString("MadnessOfDwarfs", "off").equalsIgnoreCase("on");
	}

	public static EventState getState()
	{
		if(ServerVariables.getString("MadnessOfDwarfs_state", "wait").equalsIgnoreCase("siege"))
			return EventState.SIEGE;
		if(ServerVariables.getString("MadnessOfDwarfs_state", "wait").equalsIgnoreCase("loss"))
			return EventState.LOSS_SIEGE;
		return EventState.WAITING;
	}

	public static void setState(EventState st)
	{
		switch(st)
		{
			case SIEGE:
				ServerVariables.set("MadnessOfDwarfs_state", "siege");
				break;
			case LOSS_SIEGE:
				ServerVariables.set("MadnessOfDwarfs_state", "loss");
				break;
			default:
				ServerVariables.set("MadnessOfDwarfs_state", "wait");
		}
	}

	public static void lossSiege(L2Character attacker, ArrayList<L2Player> playerList)
	{
		setState(EventState.LOSS_SIEGE);
		doJailPlayers(attacker, playerList);
	}

	public static void waitSiege()
	{
		setState(EventState.WAITING);
	}

	private static void doJailPlayers(L2Character attacker, ArrayList<L2Player> playerList)
	{
		for(L2Player pl : playerList)
		{
			if(pl != null)
			{
				if(attacker != null && attacker instanceof L2NpcInstance)
					pl.sendPacket(new Say2(pl.getObjectId(), Say2C.TELL, attacker.getName(), "Теперь ты мой пленик"));

				// pl.setReflection(-3);
				pl.setVar("EventMOD", "jailed");
				pl.teleToLocation(EVENT_GK[0] + 100, EVENT_GK[1], EVENT_GK[2]);
			}
		}
	}

	private static void doUnJailPlayers()
	{

		//        for(L2Player pl : playerList)
		//            doUnJailPlayer(pl);
	}

	private static void doUnJailPlayer(L2Player player)
	{
		if(player != null && player.getVar("EventMOD") != null)
		{
			// player.setReflection(0);
			player.unsetVar("EventMOD");
			player.teleToLocation(EVENT_MANAGERS[0] + 100, EVENT_MANAGERS[1], EVENT_MANAGERS[2]);
		}
	}

	public void BuyLiberty()
	{
		L2Player player = (L2Player) self;
		if(!isActive())
			return;
		//TODO расчеты выкупа и генерациа хтмллк
		doUnJailPlayer(player);
	}

	/**
	 * Запускает эвент
	 */
	public void startEvent()
	{
		L2Player player = (L2Player) self;
		if(!AdminTemplateManager.checkBoolean("eventMaster", player))
			return;

		if(!active)
		{
			ServerVariables.set("MadnessOfDwarfs", "on");
			spawnEventGK();
			spawnEventManagers();
			_log.info("Event: Madness Of Dwarfs started.");
			Announcements.getInstance().announceByCustomMessage("scripts.events.MadnessOfDwarfs.AnnounceEventStarted", null);
			active = true;
		}
		else
			player.sendMessage("Event 'Madness Of Dwarfs' already started.");

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
		if(active)
		{
			ServerVariables.unset("MadnessOfDwarfs");
			active = false;
			doUnJailPlayers();
			unSpawnEventManagers();
			_log.info("Event: Madness Of Dwarfs stopped.");
			Announcements.getInstance().announceByCustomMessage("scripts.events.MadnessOfGnomes.AnnounceEventStoped", null);
		}
		else
			player.sendMessage("Event 'Madness Of Dwarfs' not started.");

		show(Files.read("data/html/admin/events.htm", player), player);
	}

	public void ReportOnWar()
	{
		if(isActive())
		{
			L2Player player = (L2Player) self;
			L2NpcInstance LastNpc = player.getLastNpc();
			if(player.isActionsDisabled() || player.isSitting() || LastNpc == null || LastNpc.getDistance(player) > 300 || LastNpc.getObjectId() != npc.getObjectId())
				return;

			long count = getItemCount(player, EVENT_ITEM_ID);
			if(count < 1)
			{
				show(Files.read("data/scripts/events/MadnessOfDwarfs/ReportOnWar-noItems.htm", player), player);
				return;
			}
			removeItem(player, EVENT_ITEM_ID, count);
			_isInWar = true;
			show(Files.read("data/scripts/events/MadnessOfDwarfs/ReportOnWar-Ok.htm", player), player);
		}
	}

	private void spawnEventManagers()
	{
		_isInWar = false;
		try
		{
			L2Spawn sp = new L2Spawn(NpcTable.getTemplate(EVENT_MANAGER_ID));
			sp.setLocx(EVENT_MANAGERS[0]);
			sp.setLocy(EVENT_MANAGERS[1]);
			sp.setLocz(EVENT_MANAGERS[2]);
			sp.setAmount(1);
			sp.setHeading(EVENT_MANAGERS[3]);
			sp.setRespawnDelay(0);
			sp.init();
			sp.getLastSpawn().setAI(new MadnessOfDwarfs_ai(sp.getLastSpawn()));
			_spawns.add(sp);
		}
		catch(ClassNotFoundException e)
		{
			e.printStackTrace();
		}
	}

	private void spawnEventGK()
	{
		try
		{
			L2Player player = (L2Player) self;
			player.sendMessage("spawnEventGK.");

			L2Spawn sp = new L2Spawn(NpcTable.getTemplate(EVENT_GK_ID));
			sp.setLocx(EVENT_GK[0]);
			sp.setLocy(EVENT_GK[1]);
			sp.setLocz(EVENT_GK[2]);
			sp.setAmount(1);
			sp.setHeading(EVENT_GK[3]);
			sp.setRespawnDelay(0);
			sp.init();
			//            sp.getLastSpawn().setAI(new MadnessOfDwarfs_ai(sp.getLastSpawn()));
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

}
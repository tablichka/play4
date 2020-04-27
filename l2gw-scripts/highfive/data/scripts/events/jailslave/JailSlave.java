package events.jailslave;

import ru.l2gw.commons.arrays.GArray;
import ru.l2gw.extensions.listeners.L2ZoneEnterLeaveListener;
import ru.l2gw.extensions.scripts.Functions;
import ru.l2gw.extensions.scripts.ScriptFile;
import ru.l2gw.gameserver.Announcements;
import ru.l2gw.gameserver.cache.Msg;
import ru.l2gw.gameserver.handler.IOnDieHandler;
import ru.l2gw.gameserver.handler.ScriptHandler;
import ru.l2gw.gameserver.instancemanager.ZoneManager;
import ru.l2gw.gameserver.model.*;
import ru.l2gw.gameserver.model.gmaccess.AdminTemplateManager;
import ru.l2gw.gameserver.model.instances.L2ItemInstance;
import ru.l2gw.gameserver.model.instances.L2NpcInstance;
import ru.l2gw.gameserver.model.zone.L2Zone;
import ru.l2gw.gameserver.model.zone.L2Zone.ZoneType;
import ru.l2gw.gameserver.tables.DoorTable;
import ru.l2gw.gameserver.tables.ItemTable;
import ru.l2gw.gameserver.tables.NpcTable;
import ru.l2gw.gameserver.tables.SkillTable;
import ru.l2gw.gameserver.templates.L2Item;
import ru.l2gw.gameserver.templates.L2NpcTemplate;
import ru.l2gw.util.Location;

import java.util.ArrayList;
import java.util.LinkedList;


public class JailSlave extends Functions implements ScriptFile, IOnDieHandler
{
	public static L2Object self;
	public static L2NpcInstance npc;

	private static Boolean running = false;
	private static int players_count = 20; //Count players teleport to the collisium
	private static int time_step = 5; //Time(min) range to start teleport players
	private static ArrayList<L2Player> pvp_list = new ArrayList<L2Player>();

	private static L2Zone _zone = ZoneManager.getInstance().getZoneById(ZoneType.battle, 112);
	ZoneListener _zoneListener = new ZoneListener();

	private static int _doorId1 = 24190001;
	private static int _doorId2 = 24190002;
	private static int _doorId3 = 24190003;
	private static int _doorId4 = 24190004;

	private static LinkedList<L2Spawn> jailManagers = new LinkedList<L2Spawn>();
	
	private static final int jailManagerID = 13091;
	
	//spawn point 1
	
	private static final int jailManagerX1 = -113634;
	private static final int jailManagerY1 = -248466;
	private static final int jailManagerZ1 = -3019;

	//spawn point 2
	private static final int jailManagerX2 = -115462;
	private static final int jailManagerY2 = -248436;
	private static final int jailManagerZ2 = -3019;


	//spawn point 3
	private static final int jailManagerX3 = -115248;
	private static final int jailManagerY3 = -250528;
	private static final int jailManagerZ3 = -3019;


	//spawn point 4
	private static final int jailManagerX4 = -114533;
	private static final int jailManagerY4 = -250431;
	private static final int jailManagerZ4 = -3013;


	//spawn point 5
	private static final int jailManagerX5 = -113845;
	private static final int jailManagerY5 = -250414;
	private static final int jailManagerZ5 = -3013;


	//id итемы - пропуска из тюрьмы
	private static final int lifeBonusItemID = 6623;
	//сколько итемов взываем за выход из тюрьмы
	private static final int lifeBonusItemsCount = 1;
	

	public void onLoad()
	{
		_zone.getListenerEngine().addMethodInvokedListener(_zoneListener);
		_log.info("Loaded Event: Jail Slaves");
	}

	public void onReload()
	{
		onShutdown();
	}

	public void onShutdown()
	{
		_zone.getListenerEngine().removeMethodInvokedListener(_zoneListener);
		unSpawnJailManagers();

	}


	private static void spawnJailManagers()
	{

		L2NpcTemplate template = NpcTable.getTemplate(jailManagerID);
		L2Spawn jailManager = null;

		try
		{
			jailManager = new L2Spawn(template);
			jailManager.setLocx(jailManagerX1);
			jailManager.setLocy(jailManagerY1);
			jailManager.setLocz(jailManagerZ1);
			jailManager.setAmount(1);
			jailManager.setRespawnDelay(0);
			jailManager.setReflection(0);
			jailManager.init();
			
			jailManagers.add (jailManager);
			
			jailManager = new L2Spawn(template);
			jailManager.setLocx(jailManagerX2);
			jailManager.setLocy(jailManagerY2);
			jailManager.setLocz(jailManagerZ2);
			jailManager.setAmount(1);
			jailManager.setRespawnDelay(0);
			jailManager.setReflection(0);
			jailManager.init();
			
			jailManagers.add (jailManager);
			
			jailManager = new L2Spawn(template);
			jailManager.setLocx(jailManagerX3);
			jailManager.setLocy(jailManagerY3);
			jailManager.setLocz(jailManagerZ3);
			jailManager.setAmount(1);
			jailManager.setRespawnDelay(0);
			jailManager.setReflection(0);
			jailManager.init();
			
			jailManagers.add (jailManager);
			
			jailManager = new L2Spawn(template);
			jailManager.setLocx(jailManagerX4);
			jailManager.setLocy(jailManagerY4);
			jailManager.setLocz(jailManagerZ4);
			jailManager.setAmount(1);
			jailManager.setRespawnDelay(0);
			jailManager.setReflection(0);
			jailManager.init();

			jailManagers.add (jailManager);
			
			jailManager = new L2Spawn(template);
			jailManager.setLocx(jailManagerX5);
			jailManager.setLocy(jailManagerY5);
			jailManager.setLocz(jailManagerZ5);
			jailManager.setAmount(1);
			jailManager.setRespawnDelay(0);
			jailManager.setReflection(0);
			jailManager.init();

			jailManagers.add (jailManager);
		}
		catch(Exception e)
		{
			_log.info("Can't spawn jail slave manager");
		}
	}

	private static void unSpawnJailManagers()
	{
		if(jailManagers.size()>0)
		for (L2Spawn jm : jailManagers)
		{
			jm.stopRespawn();
			jm.getLastSpawn().deleteMe();
		}
		
		jailManagers.clear();
	}

	public static void start()
	{

		if(running)
			return;

		if(self != null)
			if(!AdminTemplateManager.checkBoolean("eventMaster", (L2Player) self))
				return;

		running = true;
		spawnJailManagers();

		Announcements.getInstance().announceToAll("Запущен эвент Последний Герой - Выжить любой ценой!");
		executeTask("events.jailslave.JailSlave", "announcePvp", new Object[1], (time_step - 1) * 60000);

	}

	public static void stop()
	{

		if(!running)
			return;

		if(self == null)
			return;

		if(!AdminTemplateManager.checkBoolean("eventMaster", (L2Player) self))
			return;

		GArray<L2Player> _slaves = getSlaves();

		if(_slaves != null && _slaves.size() > 0)
			for(L2Player player : _slaves)
				unJail(player);

		running = false;

		Announcements.getInstance().announceToAll("Эвент Последний Герой - Выжить любой ценой! завершён. Все игроки освобождены");
		unSpawnJailManagers();

	}

	private static GArray<L2Player> getSlaves()
	{
		return ZoneManager.getInstance().getZoneById(ZoneType.peace, 117).getPlayers();
	}

	private static void unJail(L2Player player)
	{
		player.teleToLocation(146783, 25808, -2008);
		player.setReflection(0);
	}

	public static void leaveJail()
	{

		if(!running)
			return;
			
		L2Player player = ((L2Player) self);
		

		if(!isInJail(player))
			return;

		L2Item item = ItemTable.getInstance().getTemplate(lifeBonusItemID);
		L2ItemInstance pay = player.getInventory().getItemByItemId(item.getItemId());
		if(pay != null && pay.getCount() >= lifeBonusItemsCount)
		{
			player.destroyItem("JailSlaveEscape", pay.getObjectId(),lifeBonusItemsCount , null, true);

			Announcements.getInstance().announceToAll(player.getName() + " освободился из тюрьмы!");

			unJail(player);

			announceFreeAfterFree();

		}
		else if(lifeBonusItemID == 57)
			player.sendPacket(Msg.YOU_DO_NOT_HAVE_ENOUGH_ADENA);
		else
			player.sendPacket(Msg.INCORRECT_ITEM_COUNT);

	}

	private static boolean isInJail(L2Player player)
	{
		//return (player.getX() <= -113740 && player.getX() >= -115525 && player.getY() <= -248388 && player.getY() >= -250700);
		return ZoneManager.getInstance().getZoneById(ZoneType.peace, 117).getPlayers().contains(player);
	}

	private static boolean isInArena(L2Player player)
	{
		return ZoneManager.getInstance().getZoneById(ZoneType.battle, 112).getPlayers().contains(player);
	}

	@Override
	public void onDie(L2Character self, L2Character killer)
	{

		if(!running)
			return;

		if(!(killer instanceof L2Player))
			return;
		if(!(self instanceof L2Player))
			return;	

		L2Player me = ((L2Player) self);
		L2Player enemy = ((L2Player) killer);
		
		if(AdminTemplateManager.checkBoolean("eventMaster", me) || AdminTemplateManager.checkBoolean("eventMaster", enemy)) return;

		if(isInJail(me) || isInJail(enemy))
			return;

		Announcements.getInstance().announceToAll(enemy.getName() + " убил " + me.getName());

		moveToJail(me);

		announceFreeAfterKill();

	}

	private static void announceFreeAfterKill()
	{

		if(!running)
			return;

		int free_players = L2ObjectsStorage.getAllPlayersCount() - getSlaves().size() - 1;

		Announcements.getInstance().announceToAll(free_players + " осталось на свободе");

	}
	
	private static void announceFreeAfterFree()
	{

		if(!running)
			return;

		int free_players = L2ObjectsStorage.getAllPlayersCount() - getSlaves().size();

		Announcements.getInstance().announceToAll(free_players + " осталось на свободе");

	}
	
	
	

	public static void announcePvp()
	{

		if(!running)
			return;

		Announcements.getInstance().announceToAll("До массового PvP в колизее осталась 1 минута!");
		executeTask("events.jailslave.JailSlave", "massPvp", new Object[1], 60000);

	}

	public static void massPvp()
	{

		if(!running)
			return;

		GArray<L2Player> already_ported = ZoneManager.getInstance().getZoneById(ZoneType.battle, 112).getPlayers();
		GArray<L2Player> all_players = L2ObjectsStorage.getAllPlayers();
		GArray<L2Player> players = new GArray<>();

		int count = 0;

		for(L2Player player : all_players)
			if(player != null && !AdminTemplateManager.checkBoolean("eventMaster", player) && !isInJail(player) && !already_ported.contains(player) && count < players_count)
			{
				players.add(player);
				count++;
			}

		if(players != null && players.size() > 0)
		{

			closeDoors();

			for(L2Player player : players)
			{
				Location pos = Location.coordsRandomize(149505, 46719, -3417, 0, 0, 500);
				player.teleToLocation(pos);
				player.setReflection(0);
				paralyzePlayer(player);
				executeTask("events.jailslave.JailSlave", "unParalyzePlayer", new Object[]{ player }, 30000);
			}

		}

		executeTask("events.jailslave.JailSlave", "announcePvp", new Object[1], (time_step - 1) * 60000);

	}

	private static void closeDoors()
	{
		DoorTable.getInstance().getDoor(_doorId1).closeMe();
		DoorTable.getInstance().getDoor(_doorId2).closeMe();
		DoorTable.getInstance().getDoor(_doorId3).closeMe();
		DoorTable.getInstance().getDoor(_doorId4).closeMe();
	}

	private static void openDoors()
	{
		DoorTable.getInstance().getDoor(_doorId1).openMe();
		DoorTable.getInstance().getDoor(_doorId2).openMe();
		DoorTable.getInstance().getDoor(_doorId3).openMe();
		DoorTable.getInstance().getDoor(_doorId4).openMe();
	}

	public static void moveToJail(L2Player player)
	{

		if(!running)
			return;

		
		player.teleToLocation(-114573, -249358, -3010);
		player.setReflection(0);
		player.sendMessage("Вы повержены и попали в тюрьму");
	}

	public static void backToArena(L2Player player)
	{
		player.teleToLocation(149389, 46781, -3438);
	}

	public static void paralyzePlayer(L2Player player)
	{
		L2Skill revengeSkill = SkillTable.getInstance().getInfo(4515, 1);

		if(player != null)
		{
			revengeSkill.applyEffects(player, player, false);

			if(player.getPet() != null)
				revengeSkill.applyEffects(player, player.getPet(), false);
		}
	}

	public static void unParalyzePlayer(L2Player player)
	{

		if(player != null)
		{
			player.stopEffect(4515);

			if(player.isPetSummoned())
				player.getPet().stopEffect(4515);

			if(player.getParty() != null)
				player.getParty().removePartyMember(player);

		}

	}

	public static void OnPlayerEnter(L2Player player)
	{

		if(!running)
			return;
			
		if(AdminTemplateManager.checkBoolean("eventMaster", player))
			return;
			
		if (isInArena(player))
		{		
		paralyzePlayer(player);
		player.sendMessage("Вы вошли в эвентовый колизей. Вы не сможете двигаться 20 секунд");
		executeTask("events.jailslave.JailSlave", "unParalyzePlayer", new Object[] { player }, 20000);
		return;		
		}

		if(!isInJail(player))
			executeTask("events.jailslave.JailSlave", "moveToJail", new Object[] { player }, 2000);
	}

	private class ZoneListener extends L2ZoneEnterLeaveListener
	{

		@Override
		public void objectEntered(L2Zone zone, L2Character object)
		{
		}

		@Override
		public void objectLeaved(L2Zone zone, L2Character object)
		{
			L2Player player = object.getPlayer();

			if(running && player != null && !AdminTemplateManager.checkBoolean("eventMaster", player) && !player.isDead() && !isInJail(player))
			{
				L2Playable playable = (L2Playable) object;

				paralyzePlayer(player);
				executeTask("events.jailslave.JailSlave", "backToArena", new Object[] { player }, 2000);
				executeTask("events.jailslave.JailSlave", "unParalyzePlayer", new Object[] { player }, 4000);

			}
		}

		@Override
		public void sendZoneStatus(L2Zone zone, L2Player player)
		{
		}
	}
}

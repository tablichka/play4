package events.HeroTrophy;

import ru.l2gw.commons.arrays.ArrayUtils;
import ru.l2gw.commons.arrays.GArray;
import ru.l2gw.commons.arrays.GCSArray;
import ru.l2gw.commons.math.Rnd;
import ru.l2gw.commons.utils.DbUtils;
import ru.l2gw.database.DatabaseFactory;
import ru.l2gw.extensions.multilang.CustomMessage;
import ru.l2gw.extensions.scripts.Functions;
import ru.l2gw.extensions.scripts.ScriptFile;
import ru.l2gw.gameserver.Config;
import ru.l2gw.gameserver.handler.*;
import ru.l2gw.gameserver.instancemanager.ServerVariables;
import ru.l2gw.gameserver.instancemanager.ZoneManager;
import ru.l2gw.gameserver.model.L2Character;
import ru.l2gw.gameserver.model.L2Playable;
import ru.l2gw.gameserver.model.L2Player;
import ru.l2gw.gameserver.model.entity.Hero;
import ru.l2gw.gameserver.model.gmaccess.AdminTemplateManager;
import ru.l2gw.gameserver.model.instances.L2ItemInstance;
import ru.l2gw.gameserver.model.zone.L2Zone;
import ru.l2gw.gameserver.tables.ItemTable;
import ru.l2gw.gameserver.tables.SpawnTable;
import ru.l2gw.util.Files;
import ru.l2gw.util.Util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author: rage
 * @date: 16.01.13 15:13
 */
public class HeroTrophy extends Functions implements ScriptFile, IOnDieHandler, IItemHandler, IVoicedCommandHandler
{
	private static int MAX_DROP_EAR;
	public static int EAR_COUNT_FOR_HERO;
	private static int MAX_LEVEL_DIFF;
	private static long EAR_GROW_TIME;
	private static boolean CHECK_HWID;
	private static String[] CHECK_ZONES;
	private static final SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm");

	private static Map<Integer, EarItemInfo> earItemInfo;
	private static Map<Integer, GCSArray<EarItemInfo>> players;
	private static boolean active;
	public static final int[] earItems = {23263, 23262, 23260, 23265, 23261, 23264};

	@Override
	public void onLoad()
	{
		active = ServerVariables.getBool("hero_trophy", false);
		MAX_DROP_EAR = Config.eventsProperties.getIntProperty("HeroTrophyMaxDropEar", 5);
		EAR_COUNT_FOR_HERO = Config.eventsProperties.getIntProperty("HeroTrophyEarCountForHero", 20);
		MAX_LEVEL_DIFF = Config.eventsProperties.getIntProperty("HeroTrophyMaxLevelDiff", 3);
		EAR_GROW_TIME = Config.eventsProperties.getLongProperty("HeroTrophyEarGrowTime", 24 * 60 * 60) * 1000;
		CHECK_HWID = Config.eventsProperties.getBooleanProperty("HeroTrophyCheckHWID", false);
		CHECK_ZONES = ArrayUtils.toStringArray(Config.eventsProperties.getProperty("HeroTrophyCheckZones", ""));

		_log.info("Loaded Event: Hero Trophy [state: " + (active ? "activated]" : "deactivated]"));
		ItemHandler.getInstance().registerItemHandler(this);
		if(active)
		{
			VoicedCommandHandler.getInstance().registerVoicedCommandHandler(this);
			earItemInfo = new ConcurrentHashMap<>();
			players = new ConcurrentHashMap<>();
			SpawnTable.getInstance().startEventSpawn("br_hero_trophy_event");

			Connection con = null;
			PreparedStatement stmt = null;
			ResultSet rset = null;

			try
			{
				con = DatabaseFactory.getInstance().getConnection();
				stmt = con.prepareStatement("DELETE FROM event_ht_ears WHERE kill_time + ? < unix_timestamp();");
				stmt.setInt(1, (int) (EAR_GROW_TIME / 1000));
				stmt.execute();
				DbUtils.closeQuietly(stmt);

				stmt = con.prepareStatement("DELETE FROM event_ht_ears WHERE owner_id NOT IN (SELECT obj_Id FROM characters)");
				stmt.execute();
				DbUtils.closeQuietly(stmt);

				stmt = con.prepareStatement("SELECT * FROM event_ht_ears");
				stmt.execute();
				rset = stmt.executeQuery();

				while(rset.next())
				{
					EarItemInfo ear = new EarItemInfo(rset.getInt("object_id"), rset.getInt("owner_id"), rset.getString("char_name"), rset.getInt("kill_time") * 1000L, rset.getBoolean("left"));
					earItemInfo.put(ear.objectId, ear);
					GCSArray<EarItemInfo> ears = getPlayerEars(ear.ownerId);
					ears.add(ear);
				}
			}
			catch(Exception e)
			{
				_log.error("Loaded Event: Hero Trophy can't restore event data: " + e, e);
			}
			finally
			{
				DbUtils.closeQuietly(con, stmt, rset);
			}
		}
	}

	@Override
	public void onReload()
	{
	}

	@Override
	public void onShutdown()
	{
	}

	public void start()
	{
		L2Player player = (L2Player) self;
		if(!AdminTemplateManager.checkBoolean("eventMaster", player))
			return;

		if(!active)
		{
			ServerVariables.set("hero_trophy", "true");
			onLoad();
		}
		else
			player.sendMessage("Event 'Hero Trophy' already started.");

		show(Files.read("data/html/admin/events2.htm", player), player);
	}

	public void stop()
	{
		L2Player player = (L2Player) self;
		if(!AdminTemplateManager.checkBoolean("eventMaster", player))
			return;

		if(active)
		{
			ServerVariables.unset("hero_trophy");
			SpawnTable.getInstance().stopEventSpawn("br_hero_trophy_event", true);
			active = false;
			_log.info("Event 'Hero Trophy' stopped.");
		}
		else
			player.sendMessage("Event 'Hero Trophy' not started.");

		show(Files.read("data/html/admin/events2.htm", player), player);
	}

	@Override
	public void onDie(L2Character self, L2Character killerChar)
	{
		if(!active)
			return;

		if(self instanceof L2Player && killerChar instanceof L2Playable && !self.isInZoneBattle() && !killerChar.isInZoneBattle())
		{
			L2Player victim = (L2Player) self;
			L2Player killer = killerChar.getPlayer();

			if(killer == null || killer.getInventoryLimit() - killer.getInventoryItemsCount() < 1 || killer.getLevel() - victim.getLevel() >= MAX_LEVEL_DIFF || CHECK_HWID && victim.getLastHWID().equals(killer.getLastHWID()))
				return;

			if(CHECK_ZONES.length > 0)
			{
				boolean ret = true;
				for(String zoneName : CHECK_ZONES)
				{
					L2Zone zone = ZoneManager.getInstance().getZoneByName(zoneName);
					if(zone != null && zone.isInsideZone(victim) && zone.isInsideZone(killerChar))
					{
						ret = false;
						break;
					}
				}

				if(ret)
					return;
			}

			GCSArray<EarItemInfo> ears = getPlayerEars(victim.getObjectId());

			if(ears.size() > 1)
			{
				GArray<L2ItemInstance> dropItems = new GArray<>();
				for(L2ItemInstance ear : victim.getInventory().getItems())
					if(ArrayUtils.contains(earItems, ear.getItemId()))
						dropItems.add(ear);

				if(dropItems.size() > 0)
				{
					int dropCount = Math.min(MAX_DROP_EAR, Math.max(1, dropItems.size() / 3));
					while(dropCount > 0)
					{
						L2ItemInstance ear = dropItems.get(Rnd.get(dropItems.size()));
						dropCount--;
						ear = victim.getInventory().dropItem("HeroTrophyDrop", ear, victim, killer);
						if(ear != null)
							killer.addItem("HeroTrophyDrop", ear, victim, true);
					}
				}
				return;
			}

			L2ItemInstance earItem = ItemTable.getInstance().createItem("HeroTrophy", earItems[victim.getRace().ordinal()], 1, killer, victim);
			boolean left = true;
			if(ears.size() > 0)
				left = !ears.get(0).left;

			EarItemInfo earInfo = new EarItemInfo(earItem.getObjectId(), victim.getObjectId(), victim.getName(), System.currentTimeMillis(), left);

			ears.add(earInfo);
			earItemInfo.put(earInfo.objectId, earInfo);
			_log.debug("add ear info: " + earInfo.objectId);
			killer.addItem("HeroTrophy", earItem, victim, true);
			victim.sendMessage(new CustomMessage("events.HeroTrophy.ear.cut", victim).addString(killer.getName()));
			insertEar(earInfo);
		}
	}

	public static void onItemRemoved(L2Character owner, L2ItemInstance item)
	{
		_log.debug(owner + " removed item: " + item);
		if(!active || !ArrayUtils.contains(earItems, item.getItemId()) || !(owner instanceof L2Player) || !owner.isHero() || Hero.isActiveHero(owner.getObjectId()))
			return;

		L2Player player = (L2Player) owner;
		long count = 0;
		for(int itemId : earItems)
			count += getItemCount(player, itemId);

		_log.debug(owner + " ear count: " + count);
		if(count < EAR_COUNT_FOR_HERO)
		{
			_log.debug(owner + " remove hero");
			player.setVar("ht_no_hero", "true");
			player.setHero(false);
			player.getInventory().validateItems();
			player.broadcastUserInfo(true);
		}
	}

	public static void OnPlayerEnter(L2Player player)
	{
		if(!active || player.getVarB("ht_no_hero"))
			return;

		long count = 0;
		for(int itemId : earItems)
			count += getItemCount(player, itemId);

		if(count >= EAR_COUNT_FOR_HERO)
			player.setHero(true);
	}

	@Override
	public boolean useItem(L2Playable playable, L2ItemInstance item)
	{
		if(!active)
			return false;

		EarItemInfo earInfo = earItemInfo.get(item.getObjectId());
		_log.debug("get ear info: " + item.getObjectId() + " " + earInfo);
		if(earInfo != null)
			playable.getPlayer().sendMessage(new CustomMessage("events.HeroTrophy.ear.info", playable.getPlayer()).addString(earInfo.name).addString(dateFormat.format(new Date(earInfo.killTime))));

		return true;
	}

	@Override
	public int[] getItemIds()
	{
		return earItems;
	}

	private static GCSArray<EarItemInfo> getPlayerEars(int objectId)
	{
		GCSArray<EarItemInfo> ears = players.get(objectId);
		if(ears == null)
		{
			ears = new GCSArray<>(2);
			players.put(objectId, ears);
			return ears;
		}

		for(EarItemInfo ear : ears)
		{
			if(ear.killTime + EAR_GROW_TIME < System.currentTimeMillis())
			{
				ears.remove(ear);
				earItemInfo.remove(ear.objectId);
				deleteEar(ear);
			}
		}

		return ears;
	}

	private static void insertEar(EarItemInfo ear)
	{
		Connection con = null;
		PreparedStatement stmt = null;

		try
		{
			con = DatabaseFactory.getInstance().getConnection();
			stmt = con.prepareStatement("REPLACE INTO event_ht_ears VALUES(?,?,?,?,?)");
			stmt.setInt(1, ear.objectId);
			stmt.setInt(2, ear.ownerId);
			stmt.setString(3, ear.name);
			stmt.setInt(4, (int) (ear.killTime / 1000));
			stmt.setBoolean(5, ear.left);
			stmt.execute();
		}
		catch(Exception e)
		{
			_log.error("Hero Trophy: can't store event data: " + e, e);
		}
		finally
		{
			DbUtils.closeQuietly(con, stmt);
		}
	}

	private static void deleteEar(EarItemInfo ear)
	{
		Connection con = null;
		PreparedStatement stmt = null;

		try
		{
			con = DatabaseFactory.getInstance().getConnection();
			stmt = con.prepareStatement("DELETE FROM event_ht_ears WHERE object_id = ?");
			stmt.setInt(1, ear.objectId);
			stmt.execute();
		}
		catch(Exception e)
		{
			_log.error("Hero Trophy: can't delete event data: " + e, e);
		}
		finally
		{
			DbUtils.closeQuietly(con, stmt);
		}
	}

	@Override
	public boolean useVoicedCommand(String command, L2Player player, String target)
	{
		if(!active)
			return false;

		GCSArray<EarItemInfo> ears = null;
		if(players.containsKey(player.getObjectId()))
			ears = getPlayerEars(player.getObjectId());

		Map<Integer, String> tpls = Util.parseTemplate(Files.read("data/scripts/events/HeroTrophy/html/ears_info.htm", player, false));
		String html = tpls.get(0);
		if(ears == null || ears.isEmpty())
		{
			html = html.replace("<?ear_left?>", tpls.get(2));
			html = html.replace("<?ear_right?>", tpls.get(2));
		}
		else
		{
			boolean left = false;
			for(int i = 0; i < 2; i++)
			{
				if(i < ears.size())
				{
					EarItemInfo ear = ears.get(i);
					long timeLeft = (ear.killTime + EAR_GROW_TIME - System.currentTimeMillis()) / 1000;
					int hour = (int) (timeLeft / 60 / 60);
					int min = (int) (timeLeft / 60 % 60);

					if(ear.left)
					{
						html = html.replace("<?ear_left?>", tpls.get(1).replace("<?hour?>", String.valueOf(hour)).replace("<?min?>", String.valueOf(min)));
						left = true;
					}
					else
					{
						html = html.replace("<?ear_right?>", tpls.get(1).replace("<?hour?>", String.valueOf(hour)).replace("<?min?>", String.valueOf(min)));
						left = false;
					}
				}
				else
				{
					html = html.replace(left ? "<?ear_right?>" : "<?ear_left?>", tpls.get(2));
				}
			}
		}

		show(html, player);
		return true;
	}

	@Override
	public String[] getVoicedCommandList()
	{
		return new String[]{"ear", "ears"};
	}

	/**
	 * @author: rage
	 * @date: 16.01.13 15:29
	 */
	public static class EarItemInfo
	{
		public final int objectId;
		public final int ownerId;
		public final String name;
		public final long killTime;
		public final boolean left;

		public EarItemInfo(int objectId, int ownerId, String name, long killTime, boolean left)
		{
			this.objectId = objectId;
			this.ownerId = ownerId;
			this.name = name;
			this.killTime = killTime;
			this.left = left;
		}

		@Override
		public String toString()
		{
			return "Ear{objectId=" + objectId + ";ownerId=" + ownerId +";name=" + name +";killTime=" + killTime +";left=" + left +"}";
		}
	}
}
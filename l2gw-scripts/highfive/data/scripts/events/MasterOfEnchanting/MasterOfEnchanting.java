package events.MasterOfEnchanting;

import javolution.util.FastMap;
import ru.l2gw.gameserver.Config;
import ru.l2gw.commons.math.Rnd;
import ru.l2gw.extensions.scripts.Functions;
import ru.l2gw.extensions.scripts.ScriptFile;
import ru.l2gw.gameserver.handler.IOnDieHandler;
import ru.l2gw.gameserver.instancemanager.ServerVariables;
import ru.l2gw.gameserver.model.L2Character;
import ru.l2gw.gameserver.model.L2Object;
import ru.l2gw.gameserver.model.L2Player;
import ru.l2gw.gameserver.model.gmaccess.AdminTemplateManager;
import ru.l2gw.gameserver.model.instances.L2ItemInstance;
import ru.l2gw.gameserver.model.instances.L2MonsterInstance;
import ru.l2gw.gameserver.model.instances.L2NpcInstance;
import ru.l2gw.gameserver.serverpackets.SystemMessage;
import ru.l2gw.gameserver.tables.SpawnTable;
import ru.l2gw.util.Files;
import ru.l2gw.util.Util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.Map;
import java.util.Properties;

/**
 * @author rage
 * @date 30.11.2010 19:17:58
 * Эвент Master of Enchanting
 * http://www.lineage2.com/archive/2009/06/master_of_encha.html
 */
public class MasterOfEnchanting extends Functions implements ScriptFile, IOnDieHandler
{
	public static L2Object self;
	public static L2NpcInstance npc;
	private static boolean _active = false;
	private static final Map<String, Integer> _playersData = new FastMap<String, Integer>().shared();
	private static final int yogy_staff = 13539;
	private static final int yogy_scroll = 13540;
	private static final String dataFile = "data/scripts/events/MasterOfEnchanting/players.data";

	public void onLoad()
	{
		if(isActive())
		{
			_active = true;
			_log.info("Loaded Event: Master of Enchanting [state: activated]");
			SpawnTable.getInstance().startEventSpawn("br_moe_event");
			try
			{
				Properties properties = new Properties();
				File file = new File(Config.DATAPACK_ROOT, dataFile);
				if(file.exists())
				{
					InputStream is = new FileInputStream(file);
					properties.load(is);
					is.close();
					for(Map.Entry<Object, Object> entry : properties.entrySet())
					{
						int date = Integer.parseInt(entry.getValue().toString());
						if(date > System.currentTimeMillis() / 1000)
							_playersData.put((String) entry.getKey(), date);
					}
				}
			}
			catch(Exception e)
			{
				_log.warn("Load Event: Master of Enchanting can't read players.data file! " + e);
				e.printStackTrace();
			}

		}
		else if(ServerVariables.getBool("moe_yogi", false))
		{
			_log.info("Loaded Event: Master of Enchanting [state: spawn Master Yogi] ");
			SpawnTable.getInstance().startEventSpawn("br_moe_event");
		}
		else
			_log.info("Loaded Event: Master of Enchanting [state: deactivated]");
	}

	/**
	 * Читает статус эвента из базы.
	 *
	 * @return
	 */
	private static boolean isActive()
	{
		return ServerVariables.getString("moe_event", "off").equalsIgnoreCase("on");
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
			ServerVariables.set("moe_event", "on");
			SpawnTable.getInstance().startEventSpawn("br_moe_event");
			_log.info("Event 'Master of Enchanting' started.");
		}
		else
			player.sendMessage("Event 'Master of Enchanting' already started.");

		_active = true;

		show(Files.read("data/html/admin/events2.htm", player), player);
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
			ServerVariables.unset("moe_event");
			SpawnTable.getInstance().stopEventSpawn("br_moe_event", true);
			_log.info("Event 'Master of Enchanting' stopped.");
		}
		else
			player.sendMessage("Event 'Master of Enchanting' not started.");

		_active = false;

		show(Files.read("data/html/admin/events2.htm", player), player);
	}

	public void spawnYogi()
	{
		L2Player player = (L2Player) self;
		if(!AdminTemplateManager.checkBoolean("eventMaster", player))
			return;
		if(!ServerVariables.getBool("moe_yogi", false))
		{
			ServerVariables.set("moe_yogi", true);
			_log.info("Loaded Event: spawn Master Yogi");
			SpawnTable.getInstance().startEventSpawn("br_moe_event");
		}
		show(Files.read("data/html/admin/events2.htm", player), player);
	}

	public void despawnYogi()
	{
		L2Player player = (L2Player) self;
		if(!AdminTemplateManager.checkBoolean("eventMaster", player))
			return;
		if(ServerVariables.getBool("moe_yogi", false))
		{
			ServerVariables.unset("moe_yogi");
			_log.info("Loaded Event: despawn Master Yogi");
			SpawnTable.getInstance().stopEventSpawn("br_moe_event", true);
		}
		show(Files.read("data/html/admin/events2.htm", player), player);
	}

	public void onReload()
	{
	}

	public void onShutdown()
	{
	}

	public static void link(String[] args)
	{
		L2Player player = (L2Player) self;
		if(player == null)
			return;

		showPage(player, args[0]);
	}

	public static void menu_select(String[] args)
	{
		L2Player player = (L2Player) self;
		if(player == null || !player.isQuestContinuationPossible(true))
			return;

		if(args.length < 2)
			return;

		int ask = Integer.parseInt(args[0]);
		int reply = Integer.parseInt(args[1]);

		if(ask == 1000)
		{
			if(reply == 1)
			{
				if(player.getAdena() >= Config.EVENT_MOS_STAFF_PRICE && Functions.getItemCount(player, yogy_staff) == 0)
				{
					showPage(player, "event_master_yogi_q01_05.htm");
					player.addItem("MoSEvent", yogy_staff, 1, npc, true);
					player.reduceAdena("MoSEvent", Config.EVENT_MOS_STAFF_PRICE, npc, true);
				}
				else
					showPage(player, "event_master_yogi_q01_05f.htm");
			}
			else if(reply == 2)
			{
				if(_active)
				{
					if(player.getAdena() >= Config.EVENT_MOS_SCROLL24_PRICE)
					{
						showPage(player, "event_master_yogi_q01_06.htm");
						String hwid = player.getLastHWID();
						if(hwid == null || hwid.isEmpty())
							hwid = player.getAccountName();
						int nextTime = _playersData.get(hwid) == null ? 0 : _playersData.get(hwid);
						if(nextTime > (System.currentTimeMillis() / 1000))
						{
							int t = nextTime - (int) (System.currentTimeMillis() / 1000);
							int h = t / 3600;
							int m = t % 3600 / 60;
							if(h > 0)
								player.sendPacket(new SystemMessage(SystemMessage.THERE_ARE_S1_HOURSS_AND_S2_MINUTES_REMAINING_UNTIL_THE_TIME_WHEN_THE_ITEM_CAN_BE_PURCHASED).addNumber(h).addNumber(m));
							else
								player.sendPacket(new SystemMessage(SystemMessage.THERE_ARE_S1_MINUTES_REMAINING_UNTIL_THE_TIME_WHEN_THE_ITEM_CAN_BE_PURCHASED).addNumber(m));
						}
						else if(player.reduceAdena("MoSEvent", Config.EVENT_MOS_SCROLL24_PRICE, npc, true))
						{
							player.addItem("MoSEvent", yogy_scroll, 24, npc, true);
							_playersData.put(hwid, (int) (System.currentTimeMillis() / 1000 + 6 * 3600));
							savePlayersData();
						}
					}
					else
						showPage(player, "event_master_yogi_q01_06f.htm");
				}
				else
					showPage(player, "event_master_yogi027.htm");
			}
			else if(reply == 3)
			{
				if(_active)
				{
					if(player.getAdena() >= Config.EVENT_MOS_SCROLL_PRICE)
					{
						showPage(player, "event_master_yogi_q01_07.htm");
						player.addItem("MoSEvent", yogy_scroll, 1, npc, true);
						player.reduceAdena("MoSEvent", Config.EVENT_MOS_SCROLL_PRICE, npc, true);
					}
					else
						showPage(player, "event_master_yogi_q01_07f.htm");
				}
				else
					showPage(player, "event_master_yogi027.htm");
			}
			else if(reply == 4)
			{
				if(_active)
				{
					if(player.getAdena() >= Config.EVENT_MOS_SCROLL_PRICE * 10)
					{
						showPage(player, "event_master_yogi_q01_07.htm");
						player.addItem("MoSEvent", yogy_scroll, 10, npc, true);
						player.reduceAdena("MoSEvent", Config.EVENT_MOS_SCROLL_PRICE * 10, npc, true);
					}
					else
						showPage(player, "event_master_yogi_fail_q01_26.htm");
				}
				else
					showPage(player, "event_master_yogi027.htm");
			}
		}
		else if(ask == 2000)
		{
			L2ItemInstance weapon = player.getActiveWeaponInstance();
			if(weapon != null && weapon.getItemId() == yogy_staff)
			{
				if(reply == 1)
				{
					if(weapon.getEnchantLevel() <= 3)
						showPage(player, "event_master_yogi_fail_q01_08.htm");
					else if(weapon.getEnchantLevel() == 4)
					{
						showPage(player, "event_master_yogi_success_1_4_7_q01_09.htm");
						player.addItem("MoSEvent", 6406, 1, npc, true);
						player.destroyItem("MoSEvent", weapon.getObjectId(), 1, npc, true);
					}
					else if(weapon.getEnchantLevel() == 5)
					{
						showPage(player, "event_master_yogi_success_1_4_7_q01_09.htm");
						player.addItem("MoSEvent", 6406, 2, npc, true);
						player.addItem("MoSEvent", 6407, 1, npc, true);
						player.destroyItem("MoSEvent", weapon.getObjectId(), 1, npc, true);
					}
					else if(weapon.getEnchantLevel() == 6)
					{
						showPage(player, "event_master_yogi_success_1_4_7_q01_09.htm");
						player.addItem("MoSEvent", 6406, 3, npc, true);
						player.addItem("MoSEvent", 6407, 2, npc, true);
						player.destroyItem("MoSEvent", weapon.getObjectId(), 1, npc, true);
					}
					else if(weapon.getEnchantLevel() == 7)
					{
						showPage(player, "event_master_yogi_success_1_4_7_q01_09.htm");
						int i0 = Rnd.get(3);
						if(i0 < 1)
							player.addItem("MoSEvent", 13074, 1, npc, true);
						else if(i0 < 2)
							player.addItem("MoSEvent", 13075, 1, npc, true);
						else
							player.addItem("MoSEvent", 13076, 1, npc, true);
						player.destroyItem("MoSEvent", weapon.getObjectId(), 1, npc, true);
					}
					else if(weapon.getEnchantLevel() == 8)
					{
						showPage(player, "event_master_yogi_success_1_4_7_q01_09.htm");
						player.addItem("MoSEvent", 955, 1, npc, true);
						player.destroyItem("MoSEvent", weapon.getObjectId(), 1, npc, true);
					}
					else if(weapon.getEnchantLevel() == 9)
					{
						showPage(player, "event_master_yogi_success_2_8_15_q01_10.htm");
						player.addItem("MoSEvent", 955, 1, npc, true);
						player.addItem("MoSEvent", 956, 1, npc, true);
						player.destroyItem("MoSEvent", weapon.getObjectId(), 1, npc, true);
					}
					else if(weapon.getEnchantLevel() == 10)
					{
						showPage(player, "event_master_yogi_success_2_8_15_q01_10.htm");
						player.addItem("MoSEvent", 951, 1, npc, true);
						player.destroyItem("MoSEvent", weapon.getObjectId(), 1, npc, true);
					}
					else if(weapon.getEnchantLevel() == 11)
					{
						showPage(player, "event_master_yogi_success_2_8_15_q01_10.htm");
						player.addItem("MoSEvent", 951, 1, npc, true);
						player.addItem("MoSEvent", 952, 1, npc, true);
						player.destroyItem("MoSEvent", weapon.getObjectId(), 1, npc, true);
					}
					else if(weapon.getEnchantLevel() == 12)
					{
						showPage(player, "event_master_yogi_success_2_8_15_q01_10.htm");
						player.addItem("MoSEvent", 947, 1, npc, true);
						player.destroyItem("MoSEvent", weapon.getObjectId(), 1, npc, true);
					}
					else if(weapon.getEnchantLevel() == 13)
					{
						showPage(player, "event_master_yogi_success_2_8_15_q01_10.htm");
						player.addItem("MoSEvent", 729, 1, npc, true);
						player.destroyItem("MoSEvent", weapon.getObjectId(), 1, npc, true);
					}
					else if(weapon.getEnchantLevel() == 14)
					{
						int i0 = Rnd.get(3);
						if(i0 < 1)
							player.addItem("MoSEvent", 13518, 1, npc, true);
						else if(i0 < 2)
							player.addItem("MoSEvent", 13519, 1, npc, true);
						else
							player.addItem("MoSEvent", 13522, 1, npc, true);
						showPage(player, "event_master_yogi_success_3_16_24_q01_11.htm");
						player.destroyItem("MoSEvent", weapon.getObjectId(), 1, npc, true);
					}
					else if(weapon.getEnchantLevel() == 15)
					{
						player.addItem("MoSEvent", 13992, 1, npc, true);
						player.destroyItem("MoSEvent", weapon.getObjectId(), 1, npc, true);
						showPage(player, "event_master_yogi_success_4_25_q01_12.htm");
					}
					else if(weapon.getEnchantLevel() == 16)
					{
						showPage(player, "event_master_yogi_success_3_16_24_q01_11.htm");
						player.addItem("MoSEvent", 8762, 1, npc, true);
						player.destroyItem("MoSEvent", weapon.getObjectId(), 1, npc, true);
					}
					else if(weapon.getEnchantLevel() == 17)
					{
						showPage(player, "event_master_yogi_success_3_16_24_q01_11.htm");
						player.addItem("MoSEvent", 959, 1, npc, true);
						player.destroyItem("MoSEvent", weapon.getObjectId(), 1, npc, true);
					}
					else if(weapon.getEnchantLevel() == 18)
					{
						player.addItem("MoSEvent", 13991, 1, npc, true);
						player.destroyItem("MoSEvent", weapon.getObjectId(), 1, npc, true);
						showPage(player, "event_master_yogi_success_4_26_q01_13.htm");
					}
					else if(weapon.getEnchantLevel() == 19)
						showPage(player, "event_master_yogi_success_coax_25_q01_19.htm");
					else if(weapon.getEnchantLevel() == 20)
						showPage(player, "event_master_yogi_success_coax_26_q01_20.htm");
					else if(weapon.getEnchantLevel() == 21)
						showPage(player, "event_master_yogi_success_coax_27_q01_21.htm");
					else if(weapon.getEnchantLevel() == 22)
						showPage(player, "event_master_yogi_success_coax_28_q01_22.htm");
					else if(weapon.getEnchantLevel() == 23)
					{
						showPage(player, "event_master_yogi_success_5_29_q01_16.htm");
						player.addItem("MoSEvent", 13988, 1, npc, true);
						player.destroyItem("MoSEvent", weapon.getObjectId(), 1, npc, true);
					}
					else
					{
						showPage(player, "event_master_yogi_success_5_30_q01_17.htm");
						player.addItem("MoSEvent", 13988, 1, npc, true);
						player.destroyItem("MoSEvent", weapon.getObjectId(), 1, npc, true);
					}
				}
			}
			else
				showPage(player, "event_master_yogi_fail_q01_25.htm");
		}
		else if(ask == 3000)
		{
			if(reply == 1)
			{
				L2ItemInstance weapon = player.getActiveWeaponInstance();
				if(weapon != null && weapon.getItemId() == yogy_staff)
				{
					if(weapon.getEnchantLevel() == 19)
					{
						player.addItem("MoSEvent", 13990, 1, npc, true);
						player.destroyItem("MoSEvent", weapon.getObjectId(), 1, npc, true);
						showPage(player, "event_master_yogi_success_4_27_q01_14.htm");
					}
					else if(weapon.getEnchantLevel() == 20)
					{
						showPage(player, "event_master_yogi_success_3_16_24_q01_11.htm");
						int i0 = Rnd.get(3);
						if(i0 < 1)
							player.addItem("MoSEvent", 9570, 1, npc, true);
						else if(i0 < 2)
							player.addItem("MoSEvent", 9572, 1, npc, true);
						else
							player.addItem("MoSEvent", 9571, 1, npc, true);

						player.destroyItem("MoSEvent", weapon.getObjectId(), 1, npc, true);
					}
					else if(weapon.getEnchantLevel() == 21)
					{
						showPage(player, "event_master_yogi_success_3_16_24_q01_11.htm");
						player.addItem("MoSEvent", 8762, 1, npc, true);
						player.addItem("MoSEvent", 8752, 1, npc, true);
						int i0 = Rnd.get(3);
						if(i0 < 1)
							player.addItem("MoSEvent", 9570, 1, npc, true);
						else if(i0 < 2)
							player.addItem("MoSEvent", 9572, 1, npc, true);
						else
							player.addItem("MoSEvent", 9571, 1, npc, true);

						showPage(player, "event_master_yogi_success_3_16_24_q01_11.htm");
						player.destroyItem("MoSEvent", weapon.getObjectId(), 1, npc, true);
					}
					else if(weapon.getEnchantLevel() == 22)
					{
						player.addItem("MoSEvent", 13989, 1, npc, true);
						player.destroyItem("MoSEvent", weapon.getObjectId(), 1, npc, true);
						showPage(player, "event_master_yogi_success_4_28_q01_15.htm");
					}
				}
				else
					showPage(player, "event_master_yogi_fail_q01_25.htm");
			}
		}
	}

	private static void showPage(L2Player player, String file)
	{
		String html = Files.read("data/scripts/events/MasterOfEnchanting/html/" + file, player);
		html = html.replaceAll("<\\?staff_price\\?>", Util.formatAdena(Config.EVENT_MOS_STAFF_PRICE));
		html = html.replaceAll("<\\?scroll_price\\?>", Util.formatAdena(Config.EVENT_MOS_SCROLL_PRICE));
		html = html.replaceAll("<\\?scroll_price10\\?>", Util.formatAdena(Config.EVENT_MOS_SCROLL_PRICE * 10));
		html = html.replaceAll("<\\?scroll_price24\\?>", Util.formatAdena(Config.EVENT_MOS_SCROLL24_PRICE));
		show(html, player);
	}

	@Override
	public void onDie(L2Character cha, L2Character killer)
	{
		if(_active && cha.isMonster() && !cha.isRaid() && cha.getLevel() >= Config.EVENT_MOS_MOB_MIN && killer != null && killer.getPlayer() != null && Math.abs(cha.getLevel() - killer.getLevel()) < 10 && Rnd.chance(Config.EVENT_MOS_SCROLL_DROP_CHANCE))
			((L2MonsterInstance) cha).dropItem(killer.getPlayer(), yogy_scroll, 1);
	}

	private static void savePlayersData()
	{
		try
		{
			Properties playersData = new Properties();
			FileOutputStream fos = new FileOutputStream(new File(Config.DATAPACK_ROOT, dataFile));

			for(String hwid : _playersData.keySet())
			{
				int date = _playersData.get(hwid);
				if(date > System.currentTimeMillis() / 1000)
					playersData.setProperty(hwid, String.valueOf(date));
				else
					_playersData.remove(hwid);
			}

			playersData.store(fos, "Master of Enchanting players data");
		}
		catch(Exception e)
		{
			_log.warn("Event: Master of Enchanting: can't save players data! " + e);
			e.printStackTrace();
		}
	}
}

package events.SavingSanta;

import ru.l2gw.commons.math.Rnd;
import ru.l2gw.extensions.scripts.Functions;
import ru.l2gw.extensions.scripts.ScriptFile;
import ru.l2gw.gameserver.instancemanager.ServerVariables;
import ru.l2gw.gameserver.model.L2Object;
import ru.l2gw.gameserver.model.L2ObjectsStorage;
import ru.l2gw.gameserver.model.L2Player;
import ru.l2gw.gameserver.model.gmaccess.AdminTemplateManager;
import ru.l2gw.gameserver.model.instances.L2ItemInstance;
import ru.l2gw.gameserver.model.instances.L2NpcInstance;
import ru.l2gw.gameserver.tables.ItemTable;
import ru.l2gw.gameserver.tables.SkillTable;
import ru.l2gw.gameserver.tables.SpawnTable;
import ru.l2gw.util.Files;

/**
 * @author rage
 * @date 29.11.2010 21:05:02
 * Новогодний эвент Saving Santa
 * http://www.lineage2.com/archive/2008/12/saving_santa_ev.html
 */
public class SavingSanta extends Functions implements ScriptFile
{
	public static L2Object self;
	public static L2NpcInstance npc;
	private static boolean _active = false;
	public static final int JACKPOT_CHANCE = 100;

	public void onLoad()
	{
		if(isActive())
		{
			_active = true;
			_log.info("Loaded Event: Saving Santa [state: activated]");
			SpawnTable.getInstance().startEventSpawn("br_xmas_event");
		}
		else
			_log.info("Loaded Event: Saving Santa [state: deactivated]");
	}

	/**
	 * Читает статус эвента из базы.
	 *
	 * @return
	 */
	private static boolean isActive()
	{
		return ServerVariables.getString("saving_santa", "off").equalsIgnoreCase("on");
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
			ServerVariables.set("saving_santa", "on");
			SpawnTable.getInstance().startEventSpawn("br_xmas_event");
			_log.info("Event 'Saving Santa' started.");
		}
		else
			player.sendMessage("Event 'Saving Santa' already started.");

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
			ServerVariables.unset("saving_santa");
			ServerVariables.unset("br_xmas_event");
			ServerVariables.unset("br_xmas_event_pc");
			L2NpcInstance _npc = L2ObjectsStorage.getByNpcId(1);
			if(_npc != null)
				_npc.deleteMe();

			_npc = L2ObjectsStorage.getByNpcId(6);
			if(_npc != null)
				_npc.deleteMe();

			SpawnTable.getInstance().stopEventSpawn("br_xmas_event", true);
			_log.info("Event 'Saving Santa' stopped.");
		}
		else
			player.sendMessage("Event 'Saving Santa' not started.");

		_active = false;

		show(Files.read("data/html/admin/events.htm", player), player);
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
		if(player == null)
			return;

		if(args.length < 2)
			return;

		int ask = Integer.parseInt(args[0]);
		int reply = Integer.parseInt(args[1]);
		int i1 = 0;
		int i2 = 0;
		if(ask == 50000)
		{
			switch(reply)
			{
				case 1:
				{
					if(player.getItemCountByItemId(20100) > 0)
						showPage(player, "br_xmas_wannabe_santa2004.htm");
					else
					{
						player.addItem("XMasEvent", 20100, 1, npc, true);
						showPage(player, "br_xmas_wannabe_santa2003.htm");
					}
					break;
				}
				case 2:
				{
					if(ServerVariables.getInt("br_xmas_event") != 1)
					{
						if(player.getEffectByAbnormalType("br_event_buf1") != null)
							showPage(player, "br_xmas_wannabe_santa2029.htm");
						else
						{
							npc.altUseSkill(SkillTable.getInstance().getInfo(23017, 1), player);
							showPage(player, "br_xmas_wannabe_santa2005.htm");
						}
					}
					else
						showPage(player, "br_xmas_wannabe_santa2006.htm");
					break;
				}
				case 3:
				{
					if(player.getItemCountByItemId(20108) > 0 || player.getItemCountByItemId(20107) > 0)
						showPage(player, "br_xmas_wannabe_santa2008.htm");
					else
						showPage(player, "br_xmas_wannabe_santa2023.htm");
					break;
				}
				case 10:
				{
					i1 = 20109;
					i2 = 2500;
					break;
				}
				case 11:
				{
					i1 = 20123;
					i2 = 2500;
					break;
				}
				case 12:
				{
					i1 = 20137;
					i2 = 2500;
					break;
				}
				case 13:
				{
					i1 = 20151;
					i2 = 2500;
					break;
				}
				case 14:
				{
					i1 = 20165;
					i2 = 2500;
					break;
				}
				case 15:
				{
					i1 = 20110;
					i2 = 81;
					break;
				}
				case 16:
				{
					i1 = 20124;
					i2 = 81;
					break;
				}
				case 17:
				{
					i1 = 20138;
					i2 = 81;
					break;
				}
				case 18:
				{
					i1 = 20152;
					i2 = 81;
					break;
				}
				case 19:
				{
					i1 = 20166;
					i2 = 81;
					break;
				}
				case 20:
				{
					i1 = 20111;
					i2 = 236;
					break;
				}
				case 21:
				{
					i1 = 20125;
					i2 = 236;
					break;
				}
				case 22:
				{
					i1 = 20139;
					i2 = 236;
					break;
				}
				case 23:
				{
					i1 = 20153;
					i2 = 236;
					break;
				}
				case 24:
				{
					i1 = 20167;
					i2 = 236;
					break;
				}
				case 25:
				{
					i1 = 20112;
					i2 = 164;
					break;
				}
				case 26:
				{
					i1 = 20126;
					i2 = 164;
					break;
				}
				case 27:
				{
					i1 = 20140;
					i2 = 164;
					break;
				}
				case 28:
				{
					i1 = 20154;
					i2 = 164;
					break;
				}
				case 29:
				{
					i1 = 20168;
					i2 = 164;
					break;
				}
				case 30:
				{
					i1 = 20113;
					i2 = 7902;
					break;
				}
				case 31:
				{
					i1 = 20127;
					i2 = 7902;
					break;
				}
				case 32:
				{
					i1 = 20141;
					i2 = 7902;
					break;
				}
				case 33:
				{
					i1 = 20155;
					i2 = 7902;
					break;
				}
				case 34:
				{
					i1 = 20169;
					i2 = 7902;
					break;
				}
				case 35:
				{
					i1 = 20114;
					i2 = 7895;
					break;
				}
				case 36:
				{
					i1 = 20128;
					i2 = 7895;
					break;
				}
				case 37:
				{
					i1 = 20142;
					i2 = 7895;
					break;
				}
				case 38:
				{
					i1 = 20156;
					i2 = 7895;
					break;
				}
				case 39:
				{
					i1 = 20170;
					i2 = 7895;
					break;
				}
				case 40:
				{
					i1 = 20115;
					i2 = 213;
					break;
				}
				case 41:
				{
					i1 = 20129;
					i2 = 213;
					break;
				}
				case 42:
				{
					i1 = 20143;
					i2 = 213;
					break;
				}
				case 43:
				{
					i1 = 20157;
					i2 = 213;
					break;
				}
				case 44:
				{
					i1 = 20171;
					i2 = 213;
					break;
				}
				case 45:
				{
					i1 = 20116;
					i2 = 270;
					break;
				}
				case 46:
				{
					i1 = 20130;
					i2 = 270;
					break;
				}
				case 47:
				{
					i1 = 20144;
					i2 = 270;
					break;
				}
				case 48:
				{
					i1 = 20158;
					i2 = 270;
					break;
				}
				case 49:
				{
					i1 = 20172;
					i2 = 270;
					break;
				}
				case 50:
				{
					i1 = 20117;
					i2 = 289;
					break;
				}
				case 51:
				{
					i1 = 20131;
					i2 = 289;
					break;
				}
				case 52:
				{
					i1 = 20145;
					i2 = 289;
					break;
				}
				case 53:
				{
					i1 = 20159;
					i2 = 289;
					break;
				}
				case 54:
				{
					i1 = 20173;
					i2 = 289;
					break;
				}
				case 55:
				{
					i1 = 20118;
					i2 = 305;
					break;
				}
				case 56:
				{
					i1 = 20132;
					i2 = 305;
					break;
				}
				case 57:
				{
					i1 = 20146;
					i2 = 305;
					break;
				}
				case 58:
				{
					i1 = 20160;
					i2 = 305;
					break;
				}
				case 59:
				{
					i1 = 20174;
					i2 = 305;
					break;
				}
				case 60:
				{
					i1 = 20119;
					i2 = 5706;
					break;
				}
				case 61:
				{
					i1 = 20133;
					i2 = 5706;
					break;
				}
				case 62:
				{
					i1 = 20147;
					i2 = 5706;
					break;
				}
				case 63:
				{
					i1 = 20161;
					i2 = 5706;
					break;
				}
				case 64:
				{
					i1 = 20175;
					i2 = 5706;
					break;
				}
				case 65:
				{
					i1 = 20120;
					i2 = 9340;
					break;
				}
				case 66:
				{
					i1 = 20134;
					i2 = 9340;
					break;
				}
				case 67:
				{
					i1 = 20148;
					i2 = 9340;
					break;
				}
				case 68:
				{
					i1 = 20162;
					i2 = 9340;
					break;
				}
				case 69:
				{
					i1 = 20176;
					i2 = 9340;
					break;
				}
				case 70:
				{
					i1 = 20121;
					i2 = 9344;
					break;
				}
				case 71:
				{
					i1 = 20135;
					i2 = 9344;
					break;
				}
				case 72:
				{
					i1 = 20149;
					i2 = 9344;
					break;
				}
				case 73:
				{
					i1 = 20163;
					i2 = 9344;
					break;
				}
				case 74:
				{
					i1 = 20177;
					i2 = 9344;
					break;
				}
				case 75:
				{
					i1 = 20122;
					i2 = 9348;
					break;
				}
				case 76:
				{
					i1 = 20136;
					i2 = 9348;
					break;
				}
				case 77:
				{
					i1 = 20150;
					i2 = 9348;
					break;
				}
				case 78:
				{
					i1 = 20164;
					i2 = 9348;
					break;
				}
				case 79:
				{
					i1 = 20178;
					i2 = 9348;
					break;
				}
			}
			if(reply >= 10 && reply <= 79)
			{
				if(player.getItemCountByItemId(20108) > 0)
				{
					if(player.destroyItemByItemId("XMasEvent", 20108, 1, npc, true))
					{
						L2ItemInstance item = ItemTable.getInstance().createItem("XMasEvent", i2, 1, npc);
						item.setEnchantLevel(10);
						item.setOwnerId(player.getObjectId());
						player.addItem("XMasEvent", item, npc, true);
					}
					showPage(player, "br_xmas_wannabe_santa2025.htm");
				}
				else if(player.getItemCountByItemId(20107) > 0)
				{
					if(player.destroyItemByItemId("XMasEvent", 20107, 1, npc, true))
					{
						L2ItemInstance item = ItemTable.getInstance().createItem("XMasEvent", i1, 1, npc);
						item.setEnchantLevel(Rnd.get(13) + 4);
						item.setOwnerId(player.getObjectId());
						player.addItem("XMasEvent", item, npc, true);
					}
					showPage(player, "br_xmas_wannabe_santa2024.htm");
				}
				else
				{
					showPage(player, "br_xmas_wannabe_santa2023.htm");
				}
			}
		}
	}

	private static void showPage(L2Player player, String file)
	{
		show(Files.read("data/scripts/events/SavingSanta/html/" + file, player), player);
	}
}

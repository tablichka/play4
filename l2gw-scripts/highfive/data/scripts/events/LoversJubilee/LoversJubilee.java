package events.LoversJubilee;

import ru.l2gw.commons.math.Rnd;
import ru.l2gw.extensions.scripts.Functions;
import ru.l2gw.extensions.scripts.ScriptFile;
import ru.l2gw.gameserver.handler.IOnDieHandler;
import ru.l2gw.gameserver.instancemanager.ServerVariables;
import ru.l2gw.gameserver.model.L2Character;
import ru.l2gw.gameserver.model.L2Object;
import ru.l2gw.gameserver.model.L2Player;
import ru.l2gw.gameserver.model.gmaccess.AdminTemplateManager;
import ru.l2gw.gameserver.model.instances.L2MonsterInstance;
import ru.l2gw.gameserver.model.instances.L2NpcInstance;
import ru.l2gw.gameserver.serverpackets.SystemMessage;
import ru.l2gw.gameserver.tables.SpawnTable;
import ru.l2gw.util.Files;

/**
 * @author rage
 * @date 09.02.11 12:18
 * http://www.lineage2.com/archive/2010/02/lovers_jubilee.html
 */
public class LoversJubilee extends Functions implements ScriptFile, IOnDieHandler
{
	public static L2Object self;
	public static L2NpcInstance npc;
	private static boolean _active = false;

	public void onLoad()
	{
		if(isActive())
		{
			_active = true;
			_log.info("Loaded Event: Lovers' Jubilee [state: activated]");
			SpawnTable.getInstance().startEventSpawn("br_rosalia");
		}
		else
			_log.info("Loaded Event: Lovers' Jubilee [state: deactivated]");
	}

	/**
	 * Читает статус эвента из базы.
	 *
	 * @return
	 */
	private static boolean isActive()
	{
		return ServerVariables.getString("lj_event", "off").equalsIgnoreCase("on");
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
			ServerVariables.set("lj_event", "on");
			SpawnTable.getInstance().startEventSpawn("br_rosalia");
			_log.info("Event 'Lovers' Jubilee' started.");
		}
		else
			player.sendMessage("Event 'Lovers' Jubilee' already started.");

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
			ServerVariables.unset("lj_event");
			SpawnTable.getInstance().stopEventSpawn("br_rosalia", true);
			_log.info("Event 'Lovers' Jubilee' stopped.");
		}
		else
			player.sendMessage("Event 'Lovers' Jubilee' not started.");

		_active = false;

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

		if(ask == 50020)
		{
			switch(reply)
			{
				case 1:
					if(Functions.getItemCount(player, 20921) >= 1)
						showPage(player, "br_val_rosalia010.htm");
					else
						showPage(player, "br_val_rosalia002.htm");
					break;
				case 2:
				case 3:
				case 4:
					if(Functions.getItemCount(player, 57) < 500)
						showPage(player, "br_val_rosalia024.htm");
					else
					{
						player.reduceAdena("LJEvent", 500, npc, true);
						if(reply == 2)
							player.addItem("LJEvent", 20905, 1, npc, true);
						else if(reply == 3)
							player.addItem("LJEvent", 20906, 1, npc, true);
						else if(reply == 4)
							player.addItem("LJEvent", 20907, 1, npc, true);
						showPage(player, "br_val_rosalia023.htm");
					}
					break;
				case 5:
				case 6:
				case 7:
					if(Functions.getItemCount(player, 57) < 5000)
						showPage(player, "br_val_rosalia024.htm");
					else
					{
						player.reduceAdena("LJEvent", 5000, npc, true);
						if(reply == 5)
							player.addItem("LJEvent", 20905, 10, npc, true);
						else if(reply == 6)
							player.addItem("LJEvent", 20906, 10, npc, true);
						else if(reply == 7)
							player.addItem("LJEvent", 20907, 10, npc, true);
						showPage(player, "br_val_rosalia023.htm");
					}
					break;
				case 8:
					if(Functions.getItemCount(player, 20914) >= 1)
						showPage(player, "br_val_rosalia007.htm");
					else
						showPage(player, "br_val_rosalia008.htm");
					break;
				case 9:
				case 10:
				case 11:
				case 12:
				case 13:
				case 14:
					if(player.getInventoryLimit() - player.getInventory().getSize() >= 2)
					{
						player.addItem("LJEvent", 20921, 1, npc, true);
						if(reply == 9)
							player.addItem("LJEvent", 20908, 1, npc, true);
						else if(reply == 10)
							player.addItem("LJEvent", 20909, 1, npc, true);
						else if(reply == 11)
							player.addItem("LJEvent", 20910, 1, npc, true);
						else if(reply == 12)
							player.addItem("LJEvent", 20911, 1, npc, true);
						else if(reply == 13)
							player.addItem("LJEvent", 20912, 1, npc, true);
						else if(reply == 14)
							player.addItem("LJEvent", 20913, 1, npc, true);

						showPage(player, "br_val_rosalia025.htm");
					}
					else
						player.sendPacket(new SystemMessage(6006));
					break;
				case 15:
				case 16:
				case 17:
				case 18:
				case 19:
				case 20:
					if(player.getInventoryLimit() - player.getInventory().getSize() >= 1)
					{
						if(player.destroyItemByItemId("LJEvent", 20914, 1, npc, true))
						{
							if(reply == 15)
								player.addItem("LJEvent", 20915, 1, npc, true);
							else if(reply == 16)
								player.addItem("LJEvent", 20916, 1, npc, true);
							else if(reply == 17)
								player.addItem("LJEvent", 20917, 1, npc, true);
							else if(reply == 18)
								player.addItem("LJEvent", 20918, 1, npc, true);
							else if(reply == 19)
								player.addItem("LJEvent", 20919, 1, npc, true);
							else if(reply == 20)
								player.addItem("LJEvent", 20920, 1, npc, true);
						}
						showPage(player, "br_val_rosalia026.htm");
					}
					else
						player.sendPacket(new SystemMessage(6006));
					break;
			}
		}
	}

	private static void showPage(L2Player player, String file)
	{
		String html = Files.read("data/scripts/events/LoversJubilee/html/" + file, player);
		show(html, player);
	}

	@Override
	public void onDie(L2Character cha, L2Character killer)
	{
		if(_active && cha.isMonster() && !cha.isRaid() && killer != null)
		{
			L2Player player = killer.getPlayer();
			if(player != null && Math.abs(cha.getLevel() - player.getLevel()) < 10)
			{
				if(Rnd.chance(25))
					((L2MonsterInstance) cha).dropItem(player, 20903, 1);
				if(Rnd.chance(10))
					((L2MonsterInstance) cha).dropItem(player, 20904, 1);
				if(Rnd.chance(5))
					((L2MonsterInstance) cha).dropItem(player, 20914, 1);
			}
		}
	}
}

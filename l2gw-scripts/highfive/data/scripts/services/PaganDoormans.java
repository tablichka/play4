package services;

import ru.l2gw.extensions.scripts.Functions;
import ru.l2gw.extensions.scripts.ScriptFile;
import ru.l2gw.gameserver.model.L2Object;
import ru.l2gw.gameserver.model.L2Player;
import ru.l2gw.gameserver.model.instances.L2DoorInstance;
import ru.l2gw.gameserver.serverpackets.SystemMessage;
import ru.l2gw.gameserver.tables.DoorTable;
import ru.l2gw.util.Files;

public class PaganDoormans extends Functions implements ScriptFile
{
	public static L2Object self;
	public static L2Object npc;
	private static int MainDoorId = 19160001;
	private static int SecondDoor1Id = 19160010;
	private static int SecondDoor2Id = 19160011;

	public void onLoad()
	{
		_log.info("Loaded Service: Pagan Doormans");
	}

	public void onReload()
	{
	}

	public void onShutdown()
	{
	}

	public static void openMainDoor()
	{
		L2Player player = (L2Player) self;

		if(player == null || player.isActionsDisabled() || player.isSitting() || player.getLastNpc().getDistance(player) > 300)
			return;

		if(getItemCount(player, 8064) == 0 && getItemCount(player, 8067) == 0)
		{
			player.sendPacket(new SystemMessage(SystemMessage.YOU_DO_NOT_HAVE_ENOUGH_REQUIRED_ITEMS));
			return;
		}

		openDoor(MainDoorId);
		if(getItemCount(player, 8064) > 0)
		{
			player.destroyItemByItemId("PaganTeleporter", 8064, 1, npc, true);
			player.addItem("PaganTeleporter", 8065, 1, npc, true);
		}
		show(Files.read("data/html/default/32034-1.htm", player), player);
	}

	public static void openSecondDoor()
	{
		L2Player player = (L2Player) self;

		if(player == null || player.isActionsDisabled() || player.isSitting() || player.getLastNpc().getDistance(player) > 300)
			return;

		if(getItemCount(player, 8067) == 0)
		{
			show(Files.read("data/html/default/32036-2.htm", player), player);
			return;
		}

		openDoor(SecondDoor1Id);
		openDoor(SecondDoor2Id);
		show(Files.read("data/html/default/32036-1.htm", player), player);
	}

	public static void pressSkull()
	{
		L2Player player = (L2Player) self;

		if(player == null || player.isActionsDisabled() || player.isSitting() || player.getLastNpc().getDistance(player) > 300)
			return;

		openDoor(MainDoorId);
		show(Files.read("data/html/default/32035-1.htm", player), player);
	}

	public static void press2ndSkull()
	{
		L2Player player = (L2Player) self;

		if(player == null || player.isActionsDisabled() || player.isSitting() || player.getLastNpc().getDistance(player) > 300)
			return;

		openDoor(SecondDoor1Id);
		openDoor(SecondDoor2Id);
		show(Files.read("data/html/default/32037-1.htm", player), player);
	}

	private static void openDoor(int doorId)
	{
		final int CLOSE_TIME = 10000; // 10 секунд
		L2DoorInstance door = DoorTable.getInstance().getDoor(doorId);
		if(!door.isOpen())
		{
			door.openMe();
			door.scheduleCloseMe(CLOSE_TIME);
		}
	}
}
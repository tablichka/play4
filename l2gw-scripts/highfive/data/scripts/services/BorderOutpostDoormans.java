package services;

import ru.l2gw.extensions.scripts.Functions;
import ru.l2gw.extensions.scripts.ScriptFile;
import ru.l2gw.gameserver.model.L2Object;
import ru.l2gw.gameserver.model.L2Player;
import ru.l2gw.gameserver.model.instances.L2DoorInstance;
import ru.l2gw.gameserver.tables.DoorTable;

/**
 * Используется в локации Eastern Border Outpost
 * @Author: SYS
 */
public class BorderOutpostDoormans extends Functions implements ScriptFile
{
	public static L2Object self;
	public static L2Object npc;
	private static int DoorId = 24170001;

	public void onLoad()
	{
		_log.info("Loaded Service: Border Outpost Doormans");
	}

	public void onReload()
	{}

	public void onShutdown()
	{}

	public static void openDoor()
	{
		L2Player player = (L2Player) self;

		if(player == null || player.isActionsDisabled() || player.isSitting() || player.getLastNpc().getDistance(player) > 300)
			return;

		L2DoorInstance door = DoorTable.getInstance().getDoor(DoorId);
		if(!door.isOpen())
			door.openMe();
	}

	public static void closeDoor()
	{
		L2Player player = (L2Player) self;

		if(player == null || player.isActionsDisabled() || player.isSitting() || player.getLastNpc().getDistance(player) > 300)
			return;

		L2DoorInstance door = DoorTable.getInstance().getDoor(DoorId);
		if(door.isOpen())
			door.closeMe();
	}
}
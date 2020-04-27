package npc.model;

import ru.l2gw.gameserver.model.L2Player;
import ru.l2gw.gameserver.model.instances.L2NpcInstance;
import ru.l2gw.gameserver.serverpackets.NpcHtmlMessage;
import ru.l2gw.gameserver.templates.L2NpcTemplate;
import ru.l2gw.util.Location;

/**
 * User: ic
 * Date: 03.07.2010
 */
public class MithrilMinesTeleporterInstance extends L2NpcInstance
{
	private static String _path = "data/html/default/";

	private static Location westLoc = new Location(173147, -173762, 3480);
	private static Location eastLoc = new Location(179560, -182956, -256);
	private int loc = 0; // 1 - west, 2 - east, 0 - center

	public MithrilMinesTeleporterInstance(int objectId, L2NpcTemplate template, long bossIndex, long p1, long p2, long p3)
	{
		super(objectId, template, bossIndex, p1, p2, p3);
	}

	@Override
	public void onSpawn()
	{
		super.onSpawn();

		if(isInRange(westLoc, 100))
			loc = 1;
		else if(isInRange(eastLoc, 100))
			loc = 2;
	}

	@Override
	public void showChatWindow(L2Player player, int val)
	{
		if(player.isCursedWeaponEquipped())
			return;

		String filename;

		int TELEPORT_CRY = 32652;
		if(loc == 1)
			filename = _path + TELEPORT_CRY + "-west.htm"; // West Teleporter
		else if(loc == 2)
			filename = _path + TELEPORT_CRY + "-east.htm"; // East Teleporter
		else
			filename = _path + TELEPORT_CRY + "-center.htm"; // Center

		NpcHtmlMessage html;
		html = new NpcHtmlMessage(player, this, filename, val);
		player.sendPacket(html);
	}

	@Override
	public boolean isFearImmune()
	{
		return true;
	}

	@Override
	public boolean isLethalImmune()
	{
		return true;
	}

	@Override
	public boolean isInvul()
	{
		return true;
	}

	@Override
	public boolean isMovementDisabled()
	{
		return true;
	}

}

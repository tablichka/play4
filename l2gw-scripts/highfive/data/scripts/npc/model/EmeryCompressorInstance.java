package npc.model;

import ru.l2gw.gameserver.model.L2Player;
import ru.l2gw.gameserver.model.instances.L2NpcInstance;
import ru.l2gw.gameserver.templates.L2NpcTemplate;
import ru.l2gw.util.Location;

import java.util.StringTokenizer;

/**
 * User: ic
 * Date: 03.07.2010
 */
public class EmeryCompressorInstance extends L2NpcInstance
{
	private static String _path = "data/html/default/";
	private static Location AERIAL_CLEFT_LOC = new Location(-204288, 242026, 1744);

	public EmeryCompressorInstance(int objectId, L2NpcTemplate template, long bossIndex, long p1, long p2, long p3)
	{
		super(objectId, template, bossIndex, p1, p2, p3);
	}

	@Override
	public void onBypassFeedback(L2Player player, String command)
	{
		StringTokenizer st = new StringTokenizer(command, " ");
		String actualCommand = st.nextToken(); // Get actual command

		if(player.isCursedWeaponEquipped())
			return;

		if(actualCommand.equalsIgnoreCase("teleport"))
		{

			if(player.getSkillLevel(840) < 1 && player.getSkillLevel(841) < 1 && player.getSkillLevel(842) < 1)
			{
				showChatWindow(player, _path + getNpcId() + "-no.htm");
			}
			else
			{
				player.teleToLocation(AERIAL_CLEFT_LOC);
			}
		}
		else
			super.onBypassFeedback(player, command);
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

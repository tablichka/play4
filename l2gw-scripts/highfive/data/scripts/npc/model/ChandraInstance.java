package npc.model;

import ru.l2gw.gameserver.model.L2Party;
import ru.l2gw.gameserver.model.L2Player;
import ru.l2gw.gameserver.model.instances.L2NpcInstance;
import ru.l2gw.gameserver.templates.L2NpcTemplate;
import ru.l2gw.util.Location;

import java.util.StringTokenizer;

/**
 * @author rage
 * @date 13.01.11 18:26
 */
public class ChandraInstance extends L2NpcInstance
{
	private static final Location forge_lower = new Location(173492, -112272, -5200);

	public ChandraInstance(int objectId, L2NpcTemplate template, long bossIndex, long p1, long p2, long p3)
	{
		super(objectId, template, bossIndex, p1, p2, p3);
	}

	@Override
	public void onBypassFeedback(L2Player player, String command)
	{
		StringTokenizer st = new StringTokenizer(command, " ");
		String actualCommand = st.nextToken(); // Get actual command

		if(actualCommand.equalsIgnoreCase("tele"))
		{
			L2Party party = player.getParty();
			if(party != null)
			{
				if(party.isLeader(player) && isInRange(player, 1000))
				{
					for(L2Player member : player.getParty().getPartyMembers())
						if(isInRange(member, 3000))
							member.teleToLocation(forge_lower);
				}
				else
					showChatWindow(player, 2);
			}
			else
				player.teleToLocation(forge_lower);
		}
		else
			super.onBypassFeedback(player, command);
	}
}

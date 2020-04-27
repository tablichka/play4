package npc.model;

import ru.l2gw.gameserver.model.instances.L2NpcInstance;
import ru.l2gw.gameserver.model.L2Player;
import ru.l2gw.gameserver.templates.L2NpcTemplate;
import ru.l2gw.gameserver.instancemanager.ServerVariables;

/**
 * User: ic
 * Date: 30.10.2009
 */
public class RaceStampManagerInstance extends L2NpcInstance
{
	private static final int RIGNOS = 32349;
	private static final int RACE_STAMP = 10013;

	public RaceStampManagerInstance(int objectId, L2NpcTemplate template, long bossIndex, long p1, long p2, long p3)
	{
		super(objectId, template, bossIndex, p1, p2, p3);
	}

	@Override
	public void showChatWindow(L2Player player, int val)
	{
		if(getNpcId() == RIGNOS)
		{
			long lastRaceStarted = ServerVariables.getLong("RaceStampLastTime", 0);
			if(System.currentTimeMillis() > lastRaceStarted + 30 * 60000 && val == 0)
				super.showChatWindow(player, "data/html/default/" + RIGNOS + ".htm");
			else if(System.currentTimeMillis() >= lastRaceStarted && System.currentTimeMillis() <= lastRaceStarted + 30 * 60000
			&& player.getItemCountByItemId(RACE_STAMP) == 4 && val == 0)
				super.showChatWindow(player, "data/html/default/" + RIGNOS + "-3.htm");
			else if(System.currentTimeMillis() >= lastRaceStarted && System.currentTimeMillis() <= lastRaceStarted + 30 * 60000 && val == 0)
				super.showChatWindow(player, "data/html/default/" + RIGNOS + "-2.htm");
			else
				super.showChatWindow(player, val);
		}
		else
			super.showChatWindow(player, val);
	}


}

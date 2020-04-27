package quests._734_PierceThroughAShield;

import quests.TerritoryWar.TerritoryWarKillQuest;
import ru.l2gw.commons.math.Rnd;
import ru.l2gw.gameserver.model.L2Player;
import ru.l2gw.gameserver.model.quest.QuestState;
import ru.l2gw.gameserver.serverpackets.ExShowScreenMessage;

/**
 * @author: rage
 * @date: 12.07.2010 10:39:45
 */
public class _734_PierceThroughAShield extends TerritoryWarKillQuest
{
	private final String _startMessage = "Defeat the MAX enemy knights";
	private final String _middleMessage = "Out of MAX knights you have defeated KILLS.";
	private final String _endMessage = "You weakened the enemy's defense!";
	private final long _rewardExp = 559000;
	private final long _rewardSp = 57016;

	public _734_PierceThroughAShield()
	{
		super(734, "_734_PierceThroughAShield", "Kill the enemy's knights!");
	}

	@Override
	public String onEvent(String event, QuestState qs)
	{
		if("start".equals(event))
		{
			int max  = Rnd.get(5, 15);
			qs.set("max", String.valueOf(max));
			qs.getPlayer().sendPacket(new ExShowScreenMessage(_startMessage.replace("MAX", String.valueOf(max)), 5000, ExShowScreenMessage.ScreenMessageAlign.TOP_CENTER));
		}
		else if("end".equals(event))
		{
			L2Player player = qs.getPlayer();
			player.sendPacket(new ExShowScreenMessage(_endMessage, 5000, ExShowScreenMessage.ScreenMessageAlign.TOP_CENTER));
			qs.addExpAndSp(_rewardExp, _rewardSp);
			player.addBadges(Rnd.get(8, 12));
			qs.exitCurrentQuest(true);
		}
		else if("warEnd".equals(event))
			qs.exitCurrentQuest(true);
		return null;
	}

	public String getMiddleMessage(int max, int kills)
	{
	 	return _middleMessage.replace("MAX", String.valueOf(max)).replace("KILLS", String.valueOf(kills));
	}
}

package quests._735_MakeSpearsDull;

import quests.TerritoryWar.TerritoryWarKillQuest;
import ru.l2gw.commons.math.Rnd;
import ru.l2gw.gameserver.model.L2Player;
import ru.l2gw.gameserver.model.quest.QuestState;
import ru.l2gw.gameserver.serverpackets.ExShowScreenMessage;

/**
 * @author: rage
 * @date: 12.07.2010 11:27:21
 */
public class _735_MakeSpearsDull extends TerritoryWarKillQuest
{
	private final String _startMessage = "Defeat MAX enemy warriors and rogues";
	private final String _middleMessage = "Out of MAX warriors and rogues you have defeated KILLS.";
	private final String _endMessage = "You weakened the enemy's attack!";
	private final long _rewardExp = 559000;
	private final long _rewardSp = 57016;

	public _735_MakeSpearsDull()
	{
		super(735, "_735_MakeSpearsDull", "Stop the enemy's attack");
	}

	@Override
	public String onEvent(String event, QuestState qs)
	{
		if("start".equals(event))
		{
			int max  = Rnd.get(15, 25);
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

package quests._736_WeakenMagic;

import quests.TerritoryWar.TerritoryWarKillQuest;
import ru.l2gw.commons.math.Rnd;
import ru.l2gw.gameserver.model.L2Player;
import ru.l2gw.gameserver.model.quest.QuestState;
import ru.l2gw.gameserver.serverpackets.ExShowScreenMessage;

/**
 * @author: rage
 * @date: 12.07.2010 11:31:15
 */
public class _736_WeakenMagic extends TerritoryWarKillQuest
{
	private final String _startMessage = "Defeat MAX enemy wizards and summoners";
	private final String _middleMessage = "Out of MAX wizards and summoners you have defeated KILLS.";
	private final String _endMessage = "You weakened the enemy's magic!";
	private final long _rewardExp = 559000;
	private final long _rewardSp = 57016;

	public _736_WeakenMagic()
	{
		super(736, "_736_WeakenMagic", "Stop the enemy's use of magic");
	}

	@Override
	public String onEvent(String event, QuestState qs)
	{
		if("start".equals(event))
		{
			int max = Rnd.get(15, 25);
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


package quests._729_ProtectTheTerritoryCatapult;

import ru.l2gw.gameserver.instancemanager.TerritoryWarManager;
import ru.l2gw.gameserver.model.L2Player;
import ru.l2gw.gameserver.model.quest.Quest;
import ru.l2gw.gameserver.model.quest.QuestState;

/**
 * @author: rage
 * @date: 11.07.2010 13:51:49
 */
public class _729_ProtectTheTerritoryCatapult extends Quest
{
	private static final long _rewardExp = 270000;
	private static final long _rewardSp = 27000;

	public _729_ProtectTheTerritoryCatapult()
	{
		super(729, "_729_ProtectTheTerritoryCatapult", "Protect the territory catapult."); // Party true
	}

	@Override
	public String onEvent(String event, QuestState st)
	{
		if("warEnd".equals(event))
		{
			L2Player player = st.getPlayer();
			if(!TerritoryWarManager.getTerritoryById(player.getTerritoryId()).isCatapultKilled())
			{
				player.addBadges(5);
				st.addExpAndSp(_rewardExp, _rewardSp);
			}
			st.exitCurrentQuest(true);
		}

		return null;
	}
}

package quests._730_ProtectTheSuppliesSafe;

import ru.l2gw.gameserver.instancemanager.TerritoryWarManager;
import ru.l2gw.gameserver.model.L2Player;
import ru.l2gw.gameserver.model.quest.Quest;
import ru.l2gw.gameserver.model.quest.QuestState;

/**
 * @author: rage
 * @date: 11.07.2010 14:05:08
 */
public class _730_ProtectTheSuppliesSafe extends Quest
{
	private static final long _rewardExp = 270000;
	private static final long _rewardSp = 27000;

	public _730_ProtectTheSuppliesSafe()
	{
		super(729, "_730_ProtectTheSuppliesSafe", "Protect the Supplies Safe."); // Party true
	}

	@Override
	public String onEvent(String event, QuestState st)
	{
		if("warEnd".equals(event))
		{
			L2Player player = st.getPlayer();
			if(!TerritoryWarManager.getTerritoryById(player.getTerritoryId()).isSuppliesKilled())
			{
				player.addBadges(5);
				st.addExpAndSp(_rewardExp, _rewardSp);
			}
			st.exitCurrentQuest(true);
		}

		return null;
	}
}

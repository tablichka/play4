package quests._731_ProtectTheMilitaryAssociationLeader;

import ru.l2gw.gameserver.instancemanager.TerritoryWarManager;
import ru.l2gw.gameserver.model.L2Player;
import ru.l2gw.gameserver.model.quest.Quest;
import ru.l2gw.gameserver.model.quest.QuestState;

/**
 * @author: rage
 * @date: 11.07.2010 14:07:10
 */
public class _731_ProtectTheMilitaryAssociationLeader extends Quest
{
	private static final long _rewardExp = 90000;
	private static final long _rewardSp = 9000;

	public _731_ProtectTheMilitaryAssociationLeader()
	{
		super(731, "_731_ProtectTheMilitaryAssociationLeader", "Protect the Military Association Leader."); // Party true
	}

	@Override
	public String onEvent(String event, QuestState st)
	{
		if("warEnd".equals(event))
		{
			L2Player player = st.getPlayer();
			if(!TerritoryWarManager.getTerritoryById(player.getTerritoryId()).isLeadersKilled())
			{
				player.addBadges(5);
				st.addExpAndSp(_rewardExp, _rewardSp);
			}
			st.exitCurrentQuest(true);
		}

		return null;
	}
}


package quests._732_ProtectTheReligiousAssociationLeader;

import ru.l2gw.gameserver.instancemanager.TerritoryWarManager;
import ru.l2gw.gameserver.model.L2Player;
import ru.l2gw.gameserver.model.quest.Quest;
import ru.l2gw.gameserver.model.quest.QuestState;

/**
 * @author: rage
 * @date: 11.07.2010 14:09:12
 */
public class _732_ProtectTheReligiousAssociationLeader extends Quest
{
	private static final long _rewardExp = 90000;
	private static final long _rewardSp = 9000;

	public _732_ProtectTheReligiousAssociationLeader()
	{
		super(732, "_732_ProtectTheReligiousAssociationLeader", "Protect the Religious Association Leader."); // Party true
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

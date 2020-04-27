package quests._701_ProofOfExistence;

import ru.l2gw.gameserver.model.L2Player;
import ru.l2gw.gameserver.model.instances.L2NpcInstance;
import ru.l2gw.gameserver.model.quest.Quest;
import ru.l2gw.gameserver.model.quest.QuestState;

/**
 * @author: rage
 * @date: 27.08.2010 19:11:57
 */
public class _701_ProofOfExistence extends Quest
{
	// NPCs
	private static final int ARTIUS = 32559;
	// ITEMS
	private static final int DEADMANS_REMAINS = 13875;

	public _701_ProofOfExistence()
	{
		super(701, "_701_ProofOfExistence", "Proof Of Existence");

		addStartNpc(ARTIUS);
		addTalkId(ARTIUS);
		
		addKillId(22606,22607,22608,22609);
		addQuestItem(DEADMANS_REMAINS);
	}

	@Override
	public String onTalk(L2NpcInstance npc, QuestState st)
	{
		String htmltext = "noquest";
		int cond = st.getInt("cond");
		L2Player player = st.getPlayer();

		if(npc.getNpcId() == ARTIUS)
		{
			QuestState first = player.getQuestState("_10273_GoodDayToFly");
			if(first != null && first.isCompleted() && st.isCreated() && player.getLevel() >= 78)
				htmltext = "32559-01.htm";
			else if(cond == 1)
			{
				long items = st.getQuestItemsCount(DEADMANS_REMAINS);
				if(items > 0)
				{
					st.takeItems(DEADMANS_REMAINS, -1);
					st.rollAndGive(57, items * 2500, 100);
					st.playSound(SOUND_ITEMGET);
					htmltext = "32559-06.htm";
				}
				else
					htmltext = "32559-04.htm";
			}
			else if(cond == 0)
				htmltext = "32559-00.htm";
		}
		return htmltext;
	}

	@Override
	public String onEvent(String event, QuestState st)
	{
		if(event.equals("32559-03.htm"))
		{
			st.setState(STARTED);
			st.set("cond",1);
			st.playSound(SOUND_ACCEPT);
		}
		else if(event.equals("32559-quit.htm"))
		{
			st.unset("cond");
			st.exitCurrentQuest(true);
			st.playSound(SOUND_FINISH);
		}
		return event;
	}

	@Override
	public void onKill(L2NpcInstance npc, QuestState st)
	{
		if(st.rollAndGive(DEADMANS_REMAINS, 1, 80))
			st.playSound(SOUND_ITEMGET);
	}
}

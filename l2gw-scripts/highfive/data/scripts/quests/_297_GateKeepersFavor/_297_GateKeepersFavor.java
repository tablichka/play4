package quests._297_GateKeepersFavor;

import ru.l2gw.gameserver.model.instances.L2NpcInstance;
import ru.l2gw.gameserver.model.quest.Quest;
import ru.l2gw.gameserver.model.quest.QuestState;

public class _297_GateKeepersFavor extends Quest
{
	private static final int STARSTONE = 1573;
	private static final int GATEKEEPER_TOKEN = 1659;

	public _297_GateKeepersFavor()
	{
		super(297, "_297_GateKeepersFavor", "Gate Keepers Favor");
		addStartNpc(30540);
		addTalkId(30540);
		addKillId(20521);
		addQuestItem(STARSTONE);
	}

	@Override
	public String onEvent(String event, QuestState st)
	{
		String htmltext = event;
		if(event.equalsIgnoreCase("30540-03.htm"))
		{
			st.set("cond", "1");
			st.setState(STARTED);
			st.playSound(SOUND_ACCEPT);
		}
		return htmltext;
	}

	@Override
	public String onTalk(L2NpcInstance npc, QuestState st)
	{
		String htmltext = "noquest";
		int npcId = npc.getNpcId();
		int cond = st.getInt("cond");
		if(npcId == 30540)
			if(st.isCreated())
			{
				if(st.getPlayer().getLevel() >= 15)
					htmltext = "30540-02.htm";
				else
					htmltext = "30540-01.htm";
			}
			else if(cond == 1 && st.getQuestItemsCount(STARSTONE) < 20)
				htmltext = "30540-04.htm";
			else if(cond == 2 && st.getQuestItemsCount(STARSTONE) < 20)
				htmltext = "30540-04.htm";
			else if(cond == 2 && st.getQuestItemsCount(STARSTONE) >= 20)
			{
				htmltext = "30540-05.htm";
				st.takeItems(STARSTONE, -1);
				st.giveItems(GATEKEEPER_TOKEN, 2);
				st.exitCurrentQuest(true);
				st.playSound(SOUND_FINISH);
			}
		return htmltext;
	}

	@Override
	public void onKill(L2NpcInstance npc, QuestState st)
	{
		if(st.getCond() == 1 && st.rollAndGiveLimited(STARSTONE, 1, 33, 20))
		{
			if(st.getQuestItemsCount(STARSTONE) == 20)
			{
				st.set("cond", "2");
				st.playSound(SOUND_MIDDLE);
				st.setState(STARTED);
			}
			else
				st.playSound(SOUND_ITEMGET);
		}
	}
}
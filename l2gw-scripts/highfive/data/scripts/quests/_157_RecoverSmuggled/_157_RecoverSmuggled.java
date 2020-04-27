package quests._157_RecoverSmuggled;

import ru.l2gw.gameserver.model.instances.L2NpcInstance;
import ru.l2gw.gameserver.model.quest.Quest;
import ru.l2gw.gameserver.model.quest.QuestState;

public class _157_RecoverSmuggled extends Quest
{
	int ADAMANTITE_ORE_ID = 1024;
	int BUCKLER = 20;

	public _157_RecoverSmuggled()
	{
		super(157, "_157_RecoverSmuggled", "Recover Smuggled");

		addStartNpc(30005);

		addTalkId(30005);

		addKillId(20121);

		addQuestItem(ADAMANTITE_ORE_ID);
	}

	@Override
	public String onEvent(String event, QuestState st)
	{
		if(st.isCompleted())
			return "completed";
		String htmltext = event;
		if(event.equals("1"))
		{
			st.set("cond", "1");
			st.setState(STARTED);
			st.playSound(SOUND_ACCEPT);
			htmltext = "30005-05.htm";
		}
		else if(event.equals("157_1"))
		{
			htmltext = "30005-04.htm";
			return htmltext;
		}
		return htmltext;
	}

	@Override
	public String onTalk(L2NpcInstance npc, QuestState st)
	{
		if(st.isCompleted())
			return "completed";
		int npcId = npc.getNpcId();
		String htmltext = "noquest";
		if(st.isCreated())
		{
			if(st.getPlayer().getLevel() >= 5)
				htmltext = "30005-03.htm";
			else
			{
				htmltext = "30005-02.htm";
				st.exitCurrentQuest(true);
			}
		}
		else if(npcId == 30005 && st.getInt("cond") == 1)
			htmltext = "30005-06.htm";
		else if(npcId == 30005 && st.getInt("cond") == 2 && st.getQuestItemsCount(ADAMANTITE_ORE_ID) == 20)
		{
			st.takeItems(ADAMANTITE_ORE_ID, st.getQuestItemsCount(ADAMANTITE_ORE_ID));
			st.playSound(SOUND_FINISH);
			st.giveItems(BUCKLER, 1);
			htmltext = "30005-07.htm";
			st.exitCurrentQuest(false);
		}
		return htmltext;
	}

	@Override
	public void onKill(L2NpcInstance npc, QuestState st)
	{
		int npcId = npc.getNpcId();
		if(npcId == 20121)
		{
			if(st.getInt("cond") == 1 && st.rollAndGiveLimited(ADAMANTITE_ORE_ID, 1, 14, 20))
			{
				if(st.getQuestItemsCount(ADAMANTITE_ORE_ID) == 20)
				{
					st.playSound(SOUND_MIDDLE);
					st.setCond(2);
					st.setState(STARTED);
				}
				else
					st.playSound(SOUND_ITEMGET);
			}
		}
	}
}
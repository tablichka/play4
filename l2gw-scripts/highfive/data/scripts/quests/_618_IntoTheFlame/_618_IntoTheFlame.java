package quests._618_IntoTheFlame;

import ru.l2gw.gameserver.model.L2Player;
import ru.l2gw.gameserver.model.instances.L2NpcInstance;
import ru.l2gw.gameserver.model.quest.Quest;
import ru.l2gw.gameserver.model.quest.QuestState;

public class _618_IntoTheFlame extends Quest
{
	//NPCs
	int KLEIN = 31540;
	int HILDA = 31271;

	//QUEST ITEMS
	int VACUALITE_ORE = 7265;
	int VACUALITE = 7266;
	int FLOATING_STONE = 7267;

	//CHANCE
	int CHANCE_FOR_QUEST_ITEMS = 50;

	public _618_IntoTheFlame()
	{
		super(618, "_618_IntoTheFlame", "In to The Flame"); // Party true

		addStartNpc(KLEIN);
		addTalkId(KLEIN, HILDA);
		for(int i = 0; i <= 5; i++)
		{
			addKillId(21274 + i);
			addKillId(21282 + i);
			addKillId(21290 + i);
		}
		addQuestItem(VACUALITE_ORE, VACUALITE);
	}

	@Override
	public String onEvent(String event, QuestState st)
	{
		String htmltext = event;
		int cond = st.getInt("cond");
		if(event.equalsIgnoreCase("31540-03.htm") && st.isCreated())
		{
			st.setState(STARTED);
			st.set("cond", "1");
			st.playSound(SOUND_ACCEPT);
		}
		else if(event.equalsIgnoreCase("31540-05.htm"))
			if(st.getQuestItemsCount(VACUALITE) > 0 && cond == 4)
			{
				st.playSound(SOUND_FINISH);
				st.exitCurrentQuest(true);
				st.giveItems(FLOATING_STONE, 1);
			}
			else
				htmltext = "31540-03.htm";
		else if(event.equalsIgnoreCase("31271-02.htm") && cond == 1)
		{
			st.set("cond", "2");
			st.playSound(SOUND_MIDDLE);
		}
		else if(event.equalsIgnoreCase("31271-05.htm"))
			if(cond == 3 && st.getQuestItemsCount(VACUALITE_ORE) == 50)
			{
				st.takeItems(VACUALITE_ORE, -1);
				st.giveItems(VACUALITE, 1);
				st.set("cond", "4");
				st.playSound(SOUND_MIDDLE);
			}
			else
				htmltext = "31271-03.htm";
		return htmltext;
	}

	@Override
	public String onTalk(L2NpcInstance npc, QuestState st)
	{
		String htmltext = "noquest";
		if(st.getPlayer().getQuestState("_618_IntoTheFlame") == null)
			return htmltext;
		int npcId = npc.getNpcId();
		int cond = st.getInt("cond");
		if(npcId == KLEIN)
		{
			if(st.isCreated())
			{
				if(st.getPlayer().getLevel() < 60)
				{
					htmltext = "31540-01.htm";
					st.exitCurrentQuest(true);
				}
				else
					htmltext = "31540-02.htm";
			}
			else if(cond == 4 && st.getQuestItemsCount(VACUALITE) > 0)
				htmltext = "31540-04.htm";
			else
				htmltext = "31540-03.htm";
		}
		else if(npcId == HILDA)
			if(cond == 1)
				htmltext = "31271-01.htm";
			else if(cond == 3 && st.getQuestItemsCount(VACUALITE_ORE) >= 50)
				htmltext = "31271-04.htm";
			else if(cond == 4)
				htmltext = "31271-06.htm";
			else
				htmltext = "31271-03.htm";
		return htmltext;
	}

	@Override
	public void onKill(L2NpcInstance npc, L2Player killer)
	{
		QuestState st = getRandomPartyMemberWithQuest(killer, 2);
		if(st != null && st.rollAndGiveLimited(VACUALITE_ORE, 1, 50, 50))
		{
			if(st.getQuestItemsCount(VACUALITE_ORE) == 50)
			{
				st.set("cond", "3");
				st.setState(STARTED);
				st.playSound(SOUND_MIDDLE);
			}
			else
				st.playSound(SOUND_ITEMGET);
		}
	}
}
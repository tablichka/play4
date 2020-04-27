package quests._156_MillenniumLove;

import ru.l2gw.gameserver.model.instances.L2NpcInstance;
import ru.l2gw.gameserver.model.quest.Quest;
import ru.l2gw.gameserver.model.quest.QuestState;

public class _156_MillenniumLove extends Quest
{
	int LILITHS_LETTER = 1022;
	int THEONS_DIARY = 1023;
	int GR_COMP_PACKAGE_SS = 5250;
	int GR_COMP_PACKAGE_SPS = 5256;

	public _156_MillenniumLove()
	{
		super(156, "_156_MillenniumLove", "Millennium Love");

		addStartNpc(30368);

		addTalkId(30368);
		addTalkId(30368);
		addTalkId(30368);
		addTalkId(30369);

		addQuestItem(LILITHS_LETTER, THEONS_DIARY);
	}

	@Override
	public String onEvent(String event, QuestState st)
	{
		if(st.isCompleted())
			return "completed";
		String htmltext = event;
		if(event.equals("30368-06.htm"))
		{
			st.giveItems(LILITHS_LETTER, 1);
			st.set("cond", "1");
			st.setState(STARTED);
			st.playSound(SOUND_ACCEPT);
		}
		else if(event.equals("156_1"))
		{
			st.takeItems(LILITHS_LETTER, -1);
			if(st.getQuestItemsCount(THEONS_DIARY) == 0)
			{
				st.giveItems(THEONS_DIARY, 1);
				st.set("cond", "2");
			}
			htmltext = "30369-03.htm";
		}
		else if(event.equals("156_2"))
		{
			st.takeItems(LILITHS_LETTER, -1);
			st.addExpAndSp(3000, 0);
			st.playSound(SOUND_FINISH);
			htmltext = "30369-04.htm";
			st.exitCurrentQuest(false);
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
		int cond = st.getInt("cond");
		if(npcId == 30368)
		{
			if(st.isCreated())
			{
				if(st.getPlayer().getLevel() >= 15)
					htmltext = "30368-02.htm";
				else
				{
					htmltext = "30368-05.htm";
					st.exitCurrentQuest(true);
				}
			}
			else if(cond == 1 && st.getQuestItemsCount(LILITHS_LETTER) == 1)
				htmltext = "30368-07.htm";
			else if(cond == 2 && st.getQuestItemsCount(THEONS_DIARY) == 1)
			{
				st.takeItems(THEONS_DIARY, -1);
				if(st.getPlayer().getClassId().isMage())
					st.giveItems(GR_COMP_PACKAGE_SPS, 1);
				else
					st.giveItems(GR_COMP_PACKAGE_SS, 1);
				st.addExpAndSp(6000, 0);
				st.unset("cond");
				st.playSound(SOUND_FINISH);
				htmltext = "30368-08.htm";
				st.exitCurrentQuest(false);
			}
		}
		else if(npcId == 30369)
			if(cond == 1 && st.getQuestItemsCount(LILITHS_LETTER) == 1)
				htmltext = "30369-02.htm";
			else if(cond == 2 && st.getQuestItemsCount(THEONS_DIARY) == 1)
				htmltext = "30369-05.htm";
		return htmltext;
	}
}
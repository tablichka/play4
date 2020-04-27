package quests._163_LegacyOfPoet;

import ru.l2gw.gameserver.model.base.Race;
import ru.l2gw.gameserver.model.instances.L2NpcInstance;
import ru.l2gw.gameserver.model.quest.Quest;
import ru.l2gw.gameserver.model.quest.QuestState;

public class _163_LegacyOfPoet extends Quest
{
	private final static int RUMIELS_POEM_1_ID = 1038;
	private final static int RUMIELS_POEM_3_ID = 1039;
	private final static int RUMIELS_POEM_4_ID = 1040;
	private final static int RUMIELS_POEM_5_ID = 1041;
	private final static int ADENA = 57;
	private final static int STARDEN = 30220;

	public _163_LegacyOfPoet()
	{
		super(163, "_163_LegacyOfPoet", "Legacy Of Poet");

		addStartNpc(STARDEN);
		addKillId(20372);
		addKillId(20373);

		addQuestItem(RUMIELS_POEM_1_ID, RUMIELS_POEM_3_ID, RUMIELS_POEM_4_ID, RUMIELS_POEM_5_ID);
	}

	@Override
	public String onEvent(String event, QuestState st)
	{
		if(st.isCompleted())
			return "completed";
		String htmltext = event;
		if(event.equals("1"))
		{
			st.set("id", "0");
			htmltext = "30220-07.htm";
			st.set("cond", "1");
			st.setState(STARTED);
			st.playSound(SOUND_ACCEPT);
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
		if(npcId == STARDEN && st.isCreated())
		{
			if(st.getPlayer().getRace() == Race.darkelf)
				htmltext = "30220-00.htm";
			else if(st.getPlayer().getLevel() >= 11)
			{
				htmltext = "30220-03.htm";
				return htmltext;
			}
			else
			{
				htmltext = "30220-02.htm";
				st.exitCurrentQuest(true);
			}
		}
		else if(npcId == STARDEN && st.getInt("cond") > 0)
			if(st.getQuestItemsCount(RUMIELS_POEM_1_ID) == 1 && st.getQuestItemsCount(RUMIELS_POEM_3_ID) == 1 && st.getQuestItemsCount(RUMIELS_POEM_4_ID) == 1 && st.getQuestItemsCount(RUMIELS_POEM_5_ID) == 1)
			{
				htmltext = "30220-09.htm";
				st.takeItems(RUMIELS_POEM_1_ID, 1);
				st.takeItems(RUMIELS_POEM_3_ID, 1);
				st.takeItems(RUMIELS_POEM_4_ID, 1);
				st.takeItems(RUMIELS_POEM_5_ID, 1);
				st.rollAndGive(ADENA, 13890, 100);
				st.addExpAndSp(21643, 943);
				st.playSound(SOUND_FINISH);
				st.exitCurrentQuest(false);
			}
			else
				htmltext = "30220-08.htm";
		return htmltext;
	}

	@Override
	public void onKill(L2NpcInstance npc, QuestState st)
	{
		int npcId = npc.getNpcId();
		if(npcId == 20372 || npcId == 20373)
		{
			if(st.getInt("cond") == 1)
			{
				if(st.rollAndGiveLimited(RUMIELS_POEM_1_ID, 1, 10, 1))
				{
					if(st.getQuestItemsCount(RUMIELS_POEM_1_ID) + st.getQuestItemsCount(RUMIELS_POEM_3_ID) + st.getQuestItemsCount(RUMIELS_POEM_4_ID) + st.getQuestItemsCount(RUMIELS_POEM_5_ID) == 4)
						st.playSound(SOUND_MIDDLE);
					else
						st.playSound(SOUND_ITEMGET);
				}
				if(st.rollAndGiveLimited(RUMIELS_POEM_3_ID, 1, 70, 1))
				{
					if(st.getQuestItemsCount(RUMIELS_POEM_1_ID) + st.getQuestItemsCount(RUMIELS_POEM_3_ID) + st.getQuestItemsCount(RUMIELS_POEM_4_ID) + st.getQuestItemsCount(RUMIELS_POEM_5_ID) == 4)
						st.playSound(SOUND_MIDDLE);
					else
						st.playSound(SOUND_ITEMGET);
				}
				if(st.rollAndGiveLimited(RUMIELS_POEM_4_ID, 1, 70, 1))
				{
					if(st.getQuestItemsCount(RUMIELS_POEM_1_ID) + st.getQuestItemsCount(RUMIELS_POEM_3_ID) + st.getQuestItemsCount(RUMIELS_POEM_4_ID) + st.getQuestItemsCount(RUMIELS_POEM_5_ID) == 4)
						st.playSound(SOUND_MIDDLE);
					else
						st.playSound(SOUND_ITEMGET);
				}
				if(st.rollAndGiveLimited(RUMIELS_POEM_5_ID, 1, 50, 1))
				{
					if(st.getQuestItemsCount(RUMIELS_POEM_1_ID) + st.getQuestItemsCount(RUMIELS_POEM_3_ID) + st.getQuestItemsCount(RUMIELS_POEM_4_ID) + st.getQuestItemsCount(RUMIELS_POEM_5_ID) == 4)
						st.playSound(SOUND_MIDDLE);
					else
						st.playSound(SOUND_ITEMGET);
				}
			}
		}
	}
}
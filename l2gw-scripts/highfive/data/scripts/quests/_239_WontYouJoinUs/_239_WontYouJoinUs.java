package quests._239_WontYouJoinUs;

import ru.l2gw.gameserver.model.instances.L2NpcInstance;
import ru.l2gw.gameserver.model.quest.Quest;
import ru.l2gw.gameserver.model.quest.QuestState;

/**
 * @author: rage
 * @date: 15.08.2010 14:47:30
 */
public class _239_WontYouJoinUs extends Quest
{
	// NPCs
	public static final int ATHENIA = 32643;

	public static final int WASTE_LANDFILL_MACHINE = 18805;
	public static final int SUPPRESSOR = 22656;
	public static final int EXTERMINATOR = 22657;

	public static final int SUPPORT_CERIFICATE = 14866;
	public static final int DESTROYED_MACHINE_PIECE = 14869;
	public static final int ENCHANTED_GOLEM_FRAGMENT = 14870;

	public _239_WontYouJoinUs()
	{
		super(239, "_239_WontYouJoinUs", "Won't You Join Us");
		addStartNpc(ATHENIA);
		addTalkId(ATHENIA);

		addKillId(WASTE_LANDFILL_MACHINE);
		addKillId(SUPPRESSOR);
		addKillId(EXTERMINATOR);
		addQuestItem(DESTROYED_MACHINE_PIECE, ENCHANTED_GOLEM_FRAGMENT);
	}

	@Override
	public String onEvent(String event, QuestState st)
	{
		if(st.isCompleted())
			return "completed";
		if(event.equals("32643-03.htm"))
		{
			st.set("cond", 1);
			st.setState(STARTED);
			st.playSound(SOUND_ACCEPT);
		}
		else if(event.equals("32643-07.htm"))
		{
			st.set("cond", 3);
			st.playSound(SOUND_MIDDLE);
		}
		return event;
	}

	@Override
	public String onTalk(L2NpcInstance npc, QuestState st)
	{
		String htmltext = "noquest";
		int npcId = npc.getNpcId();
		int cond = st.getInt("cond");
		QuestState qs = st.getPlayer().getQuestState("_237_WindsOfChange");
		QuestState qs2 = st.getPlayer().getQuestState("_238_SuccesFailureOfBusiness");
		if(npcId == ATHENIA)
		{
			if(st.isCompleted())
				htmltext = "32643-11.htm";
			else if(st.isCreated())
			{
				if(qs2 != null && qs2.isCompleted())
					htmltext = "32643-12.htm";
				else if(qs != null && qs.isCompleted() && st.getPlayer().getLevel() >= 82)
					htmltext = "32643-01.htm";
				else
				{
					htmltext = "32643-00.htm";
					st.exitCurrentQuest(true);
				}
			}
			else if(cond == 1)
			{
				if(st.getQuestItemsCount(DESTROYED_MACHINE_PIECE) >= 1)
					htmltext = "32643-05.htm";
				else
					htmltext = "32643-04.htm";
			}
			else if(cond == 2)
			{
				htmltext = "32643-06.htm";
				st.takeItems(DESTROYED_MACHINE_PIECE, 10);
			}
			else if(cond == 3)
			{
				if(st.getQuestItemsCount(ENCHANTED_GOLEM_FRAGMENT) >= 1)
					htmltext = "32643-08.htm";
				else
					htmltext = "32643-09.htm";
			}
			else if(cond == 4 && st.getQuestItemsCount(ENCHANTED_GOLEM_FRAGMENT) == 20)
			{
				htmltext = "32643-10.htm";
				st.takeItems(SUPPORT_CERIFICATE, 1);
				st.takeItems(ENCHANTED_GOLEM_FRAGMENT, 20);
				st.giveItems(57, 283346);
				st.addExpAndSp(1319736, 103553);
				st.playSound(SOUND_FINISH);
				st.exitCurrentQuest(false);
			}
		}
		return htmltext;
	}

	@Override
	public void onKill(L2NpcInstance npc, QuestState st)
	{
		int npcId = npc.getNpcId();
		int cond = st.getInt("cond");
		if(cond == 1 && npcId == WASTE_LANDFILL_MACHINE && st.rollAndGiveLimited(DESTROYED_MACHINE_PIECE, 1, 100, 10))
		{
			if(st.getQuestItemsCount(DESTROYED_MACHINE_PIECE) == 10)
			{
				st.set("cond", 2);
				st.playSound(SOUND_MIDDLE);
				st.setState(STARTED);
			}
			else
				st.playSound(SOUND_ITEMGET);

		}
		else if(cond == 3 && (npcId == SUPPRESSOR || npcId == EXTERMINATOR))
		{
			if(st.rollAndGiveLimited(ENCHANTED_GOLEM_FRAGMENT, 1, 80, 20))
			{
				if(st.getQuestItemsCount(ENCHANTED_GOLEM_FRAGMENT) == 20)
				{
					st.set("cond", 4);
					st.playSound(SOUND_MIDDLE);
					st.setState(STARTED);
				}
				else
					st.playSound(SOUND_ITEMGET);

			}
		}
	}
}


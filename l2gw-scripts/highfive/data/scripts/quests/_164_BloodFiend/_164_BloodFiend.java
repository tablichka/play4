package quests._164_BloodFiend;

import ru.l2gw.gameserver.model.instances.L2NpcInstance;
import ru.l2gw.gameserver.model.quest.Quest;
import ru.l2gw.gameserver.model.quest.QuestState;

public class _164_BloodFiend extends Quest
{
	//NPC
	private static final int Creamees = 30149;
	//Quest Items
	private static final int KirunakSkull = 1044;
	//Items
	private static final int Adena = 57;
	//MOB
	private static final int Kirunak = 27021;

	public _164_BloodFiend()
	{
		super(164, "_164_BloodFiend", "Blood Fiend");

		addStartNpc(Creamees);
		addTalkId(Creamees);
		addKillId(Kirunak);
		addQuestItem(KirunakSkull);
	}

	@Override
	public String onEvent(String event, QuestState st)
	{
		if(st.isCompleted())
			return "completed";
		String htmltext = event;
		if(event.equalsIgnoreCase("30149-04.htm"))
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
		if(st.isCompleted())
			return "completed";
		int npcId = npc.getNpcId();
		String htmltext = "noquest";
		int cond = st.getInt("cond");
		if(npcId == Creamees)
			if(st.isCreated())
			{
				if(st.getPlayer().getRace().ordinal() == 2)
				{
					htmltext = "30149-00.htm";
					st.exitCurrentQuest(true);
				}
				else if(st.getPlayer().getLevel() < 21)
				{
					htmltext = "30149-02.htm";
					st.exitCurrentQuest(true);
				}
				else
					htmltext = "30149-03.htm";
			}
			else if(cond == 1)
				htmltext = "30149-05.htm";
			else if(cond == 2)
			{
				st.takeItems(KirunakSkull, -1);
				st.rollAndGive(Adena, 42130, 100);
				st.addExpAndSp(35637, 1854);
				htmltext = "30149-06.htm";
				st.playSound(SOUND_FINISH);
				st.exitCurrentQuest(false);
			}
		return htmltext;
	}

	@Override
	public void onKill(L2NpcInstance npc, QuestState st)
	{
		int npcId = npc.getNpcId();
		int cond = st.getInt("cond");
		if(cond == 1 && npcId == Kirunak)
		{
			if(st.getQuestItemsCount(KirunakSkull) == 0)
				st.giveItems(KirunakSkull, 1);
			st.playSound(SOUND_MIDDLE);
			st.set("cond", "2");
			st.setState(STARTED);
		}
	}
}
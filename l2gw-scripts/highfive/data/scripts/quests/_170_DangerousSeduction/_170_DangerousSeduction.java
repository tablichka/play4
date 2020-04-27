package quests._170_DangerousSeduction;

import ru.l2gw.gameserver.model.instances.L2NpcInstance;
import ru.l2gw.gameserver.model.quest.Quest;
import ru.l2gw.gameserver.model.quest.QuestState;

public class _170_DangerousSeduction extends Quest
{
	//NPC
	private static final int Vellior = 30305;
	//Quest Items
	private static final int NightmareCrystal = 1046;
	//Items
	private static final int Adena = 57;
	//MOB
	private static final int Merkenis = 27022;

	public _170_DangerousSeduction()
	{
		super(170, "_170_DangerousSeduction", "Dangerous Seduction");
		addStartNpc(Vellior);
		addTalkId(Vellior);
		addKillId(Merkenis);
		addQuestItem(NightmareCrystal);
	}

	@Override
	public String onEvent(String event, QuestState st)
	{
		if(st.isCompleted())
			return "completed";
		String htmltext = event;
		if(event.equalsIgnoreCase("30305-04.htm"))
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
		if(npcId == Vellior)
			if(st.isCreated())
			{
				if(st.getPlayer().getRace().ordinal() != 2)
				{
					htmltext = "30305-00.htm";
					st.exitCurrentQuest(true);
				}
				else if(st.getPlayer().getLevel() < 21)
				{
					htmltext = "30305-02.htm";
					st.exitCurrentQuest(true);
				}
				else
					htmltext = "30305-03.htm";
			}
			else if(cond == 1)
				htmltext = "30305-05.htm";
			else if(cond == 2)
			{
				st.takeItems(NightmareCrystal, -1);
				st.rollAndGive(Adena, 102680, 100);
				st.addExpAndSp(38607, 4018);
				htmltext = "30305-06.htm";
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
		if(cond == 1 && npcId == Merkenis)
		{
			if(st.getQuestItemsCount(NightmareCrystal) == 0)
				st.giveItems(NightmareCrystal, 1);
			st.playSound(SOUND_MIDDLE);
			st.set("cond", "2");
			st.setState(STARTED);
		}
	}
}
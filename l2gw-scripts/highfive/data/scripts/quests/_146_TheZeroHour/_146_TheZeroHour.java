package quests._146_TheZeroHour;

import ru.l2gw.gameserver.model.L2Player;
import ru.l2gw.gameserver.model.instances.L2NpcInstance;
import ru.l2gw.gameserver.model.quest.Quest;
import ru.l2gw.gameserver.model.quest.QuestState;


public class _146_TheZeroHour extends Quest
{
	//NPC
	private final static int KAHMAN = 31554;

	//ITEMS
	private final static int FANG = 14859;
	private final static int KAHMAN_BOX = 14849; // reward

	//MINLEVEL
	private final static int MINLEVEL = 81;

	//MONSTERS
	private static final int QUEEN_SHYEED = 25671;

	public _146_TheZeroHour()
	{
		super(146, "_146_TheZeroHour", "The Zero Hour"); // Party true

		addStartNpc(KAHMAN);
		addTalkId(KAHMAN);
		addQuestItem(FANG);
		addKillId(QUEEN_SHYEED);

	}

	@Override
	public String onEvent(String event, QuestState st)
	{
		if(st.isCompleted())
			return "completed";
		String htmltext = event;
		if(event.equalsIgnoreCase("accept"))
		{
			st.set("cond", 1);
			st.setState(STARTED);
			st.playSound(SOUND_ACCEPT);
			htmltext = "31554-02.htm";
		}
		else if(event.equalsIgnoreCase("finish"))
		{
			st.playSound(SOUND_FINISH);
			st.giveItems(KAHMAN_BOX, 1);
			st.addExpAndSp(154616, 125000);
			st.exitCurrentQuest(false);
			htmltext = "31554-05.htm";
		}
		return htmltext;
	}

	@Override
	public String onTalk(L2NpcInstance npc, QuestState st)
	{
		if(st.isCompleted())
			return "completed";
		String htmltext = "noquest";
		int npcId = npc.getNpcId();
		int cond = st.getInt("cond");
		if(npcId == KAHMAN)
		{
			if(st.isCreated())
			{
				QuestState q109 = st.getPlayer().getQuestState("_109_InSearchOfTheNest");
				if(st.getPlayer().getLevel() < MINLEVEL)
				{
					htmltext = "31554-00.htm";
				}
				else if(q109 == null || !q109.isCompleted())
				{
					htmltext = "31554-00a.htm";
				}
				else
				{
					htmltext = "31554-01.htm";
				}
			}
			else if(st.isStarted())
			{
				if(cond == 1)
				{
					htmltext = "31554-03.htm";
				}
				else if(cond == 2)
				{
					htmltext = "31554-04.htm";
				}
			}
		}
		return htmltext;
	}

	@Override
	public void onKill(L2NpcInstance npc, L2Player killer)
	{
		QuestState st = getRandomPartyMemberWithQuest(killer, 1);
		if(st != null)
		{
			st.giveItems(FANG, 1);
			st.playSound(SOUND_MIDDLE);
			st.set("cond", 2);
			st.setState(STARTED);
		}
	}
}
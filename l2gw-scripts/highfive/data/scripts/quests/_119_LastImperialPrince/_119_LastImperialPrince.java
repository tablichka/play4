package quests._119_LastImperialPrince;

import ru.l2gw.gameserver.model.instances.L2NpcInstance;
import ru.l2gw.gameserver.model.quest.Quest;
import ru.l2gw.gameserver.model.quest.QuestState;

public class _119_LastImperialPrince extends Quest
{
	private static final int SPIRIT = 31453;	//Nameless Spirit
	private static final int DEVORIN = 32009;	//Devorin

	//ITEM
	private static final int BROOCH = 7262;	 //Antique Brooch

	//REWARD
	private static final int ADENA = 57;	  //Adena
	private static final int AMOUNT = 68787;	//Amount

	public _119_LastImperialPrince()
	{
		super(119, "_119_LastImperialPrince", "Last Imperial Prince");
		addStartNpc(SPIRIT);
		addTalkId(DEVORIN);
	}

	@Override
	public String onEvent(String event, QuestState st)
	{
		if(st.isCompleted())
			return "completed";
		String htmltext = event;
		if(event.equalsIgnoreCase("31453-4.htm"))
		{
			st.set("cond", "1");
			st.setState(STARTED);
			st.playSound(SOUND_ACCEPT);
		}
		else if(event.equalsIgnoreCase("32009-2.htm"))
		{
			if(st.getQuestItemsCount(BROOCH) < 1)
			{
				htmltext = "noquest";
				st.exitCurrentQuest(true);
			}
		}
		else if(event.equalsIgnoreCase("32009-3.htm"))
		{
			st.set("cond", "2");
			st.playSound(SOUND_MIDDLE);
		}
		else if(event.equalsIgnoreCase("31453-7.htm"))
		{
			st.giveItems(ADENA, AMOUNT);
			st.setState(COMPLETED);
			st.playSound(SOUND_FINISH);
			st.exitCurrentQuest(false);
		}
		return htmltext;
	}

	@Override
	public String onTalk(L2NpcInstance npc, QuestState st)
	{
		if(st.isCompleted())
			return "completed";
		String htmltext = "noquest";
		if(st == null)
			return htmltext;

		int cond = st.getInt("cond");
		int npcId = npc.getNpcId();
		if(st.getQuestItemsCount(BROOCH) < 1)
		{
			htmltext = "noquest";
			st.exitCurrentQuest(true);
		}
		else if(st.isCreated())
			if(st.getPlayer().getLevel() < 74)
			{
				htmltext = "<html><body>Quest for characters level 74 and above.</body></html>";
				st.exitCurrentQuest(true);
			}
			else
				htmltext = "31453-1.htm";
		else if(npcId == SPIRIT)
		{
			if(cond == 1)
				htmltext = "31453-4.htm";
			else if(cond == 2)
				htmltext = "31453-5.htm";
		}
		else if(npcId == DEVORIN)
		{
			if(cond == 1)
				htmltext = "32009-1.htm";
			else if(cond == 2)
				htmltext = "32009-3.htm";
		}
		return htmltext;
	}
}
package quests._654_JourneytoaSettlement;

import ru.l2gw.gameserver.model.quest.Quest;
import ru.l2gw.gameserver.model.quest.QuestState;
import ru.l2gw.gameserver.model.instances.L2NpcInstance;

public class _654_JourneytoaSettlement extends Quest
{
	//NPC
	private static int Nameless_Spirit = 31453;

	//TARGET
	private static int Canyon_Antelope = 21294;
	private static int Canyon_Antelope_Slave = 21295;

	//ITEM
	private static int Antelope_Skin = 8072; //Antelope Skin

	//REWARD
	private static int SCROLL = 8073; //Frintezza's Magic Force Field Removal Scroll

	public _654_JourneytoaSettlement()
	{
		super(654, "_654_JourneytoaSettlement", "Journey to a Settlement");

		addStartNpc(Nameless_Spirit);
		addKillId(Canyon_Antelope, Canyon_Antelope_Slave);
		addQuestItem(Antelope_Skin);
	}

	@Override
	public String onEvent(String event, QuestState st)
	{
		if(event.equalsIgnoreCase("31453-2.htm"))
		{
			st.set("cond", "1");
			st.setState(STARTED);
			st.playSound(SOUND_ACCEPT);
		}
		if(event.equalsIgnoreCase("31453-3.htm"))
		{
			st.set("cond", "2");
			st.setState(STARTED);
		}
		if(event.equalsIgnoreCase("31453-5.htm"))
		{
			st.giveItems(SCROLL, 1);
			st.playSound(SOUND_FINISH);
			st.exitCurrentQuest(true);
		}
		return event;
	}

	@Override
	public String onTalk(L2NpcInstance npc, QuestState st)
	{
		String htmltext = "noquest";
		int cond = st.getInt("cond");
		int npcId = npc.getNpcId();
		if(npcId == Nameless_Spirit)
		{
			if(st.isCreated())
			{
				QuestState LastImperialPrince = st.getPlayer().getQuestState("_119_LastImperialPrince");
				if(LastImperialPrince != null && LastImperialPrince.isCompleted() && st.getPlayer().getLevel() > 73)
					htmltext = "31453-1.htm";
				else
				{
					st.exitCurrentQuest(true);
					htmltext = "31453-0.htm";
				}
			}
			else if(cond == 1)
				htmltext = "31453-2.htm";
			else if(cond == 2)
				htmltext = "31453-3.htm";
			else if(cond == 3)
				htmltext = "31453-4.htm";
		}
		return htmltext;
	}

	@Override
	public void onKill(L2NpcInstance npc, QuestState st)
	{
		if(st.getInt("cond") == 2 && st.rollAndGiveLimited(Antelope_Skin, 1, 5, 1))
		{
			st.set("cond", "3");
			st.setState(STARTED);
			st.playSound(SOUND_MIDDLE);
		}
	}
}

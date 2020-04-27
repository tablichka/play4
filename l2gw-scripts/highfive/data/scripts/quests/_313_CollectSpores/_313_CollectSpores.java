package quests._313_CollectSpores;

import ru.l2gw.gameserver.model.instances.L2NpcInstance;
import ru.l2gw.gameserver.model.quest.Quest;
import ru.l2gw.gameserver.model.quest.QuestState;

public class _313_CollectSpores extends Quest
{
	//NPC
	public final int Herbiel = 30150;
	//Mobs
	public final int SporeFungus = 20509;
	//Quest Items
	public final int SporeSac = 1118;

	public _313_CollectSpores()
	{
		super(313, "_313_CollectSpores", " Collect Spores ");

		addStartNpc(Herbiel);
		addTalkId(Herbiel);
		addKillId(SporeFungus);
		addQuestItem(SporeSac);
	}

	@Override
	public String onEvent(String event, QuestState st)
	{
		if(event.equalsIgnoreCase("30150-05.htm"))
		{
			st.set("cond", "1");
			st.setState(STARTED);
			st.playSound(SOUND_ACCEPT);
		}
		return event;
	}

	@Override
	public String onTalk(L2NpcInstance npc, QuestState st)
	{
		String htmltext = "noquest";
		int cond = st.getInt("cond");
		if(st.isCreated())
		{
			if(st.getPlayer().getLevel() >= 8)
				htmltext = "30150-03.htm";
			else
			{
				htmltext = "30150-02.htm";
				st.exitCurrentQuest(true);
			}
		}
		else if(cond == 1)
			htmltext = "30150-06.htm";
		else if(cond == 2)
			if(st.getQuestItemsCount(SporeSac) < 10)
			{
				st.set("cond", "1");
				htmltext = "30150-06.htm";
			}
			else
			{
				st.takeItems(SporeSac, -1);
				st.rollAndGive(57, 3500, 100);
				st.playSound(SOUND_FINISH);
				htmltext = "30150-07.htm";
				st.exitCurrentQuest(true);
			}
		return htmltext;
	}

	@Override
	public void onKill(L2NpcInstance npc, QuestState st)
	{
		int npcId = npc.getNpcId();
		int cond = st.getInt("cond");
		if(cond == 1 && npcId == SporeFungus && st.rollAndGiveLimited(SporeSac, 1, 70, 10))
		{
			if(st.getQuestItemsCount(SporeSac) < 10)
				st.playSound(SOUND_ITEMGET);
			else
			{
				st.playSound(SOUND_MIDDLE);
				st.set("cond", "2");
				st.setState(STARTED);
			}
		}
	}
}
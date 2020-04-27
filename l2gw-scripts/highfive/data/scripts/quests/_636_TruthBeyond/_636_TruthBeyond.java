package quests._636_TruthBeyond;

import ru.l2gw.gameserver.model.instances.L2NpcInstance;
import ru.l2gw.gameserver.model.quest.Quest;
import ru.l2gw.gameserver.model.quest.QuestState;

public class _636_TruthBeyond extends Quest
{
	//Npc
	public final int ELIYAH = 31329;
	public final int FLAURON = 32010;

	//Items
	public final int PagansMark = 8067;
	public final int VISITORSMARK = 8064;

	public _636_TruthBeyond()
	{
		super(636, "_636_TruthBeyond", "Truth Beyond");

		addStartNpc(ELIYAH);
		addTalkId(FLAURON);
	}

	@Override
	public String onEvent(String event, QuestState st)
	{
		if(event.equals("31329-04.htm"))
		{
			st.set("cond", "1");
			st.setState(STARTED);
			st.playSound(SOUND_ACCEPT);
		}
		else if(event.equals("32010-02.htm"))
		{
			st.playSound(SOUND_FINISH);
			st.giveItems(VISITORSMARK, 1);
			st.exitCurrentQuest(true);
		}
		return event;
	}

	@Override
	public String onTalk(L2NpcInstance npc, QuestState st)
	{
		int npcId = npc.getNpcId();
		String htmltext = "noquest";
		int cond = st.getInt("cond");
		if(npcId == ELIYAH)
		{
			if(st.isStarted())
			{
				htmltext = "31329-04.htm";
			}
			else
			{
				if(st.getQuestItemsCount(VISITORSMARK) == 0 && st.getQuestItemsCount(PagansMark) == 0)
				{
					if(st.getPlayer().getLevel() > 72)
						htmltext = "31329-02.htm";
					else
					{
						htmltext = "31329-01.htm";
						st.exitCurrentQuest(true);
					}
				}
				else
					htmltext = "31329-05.htm";
			}
		}
		else if(npcId == FLAURON)
			if(cond == 1)
			{
				htmltext = "32010-01.htm";
			}
			else
				htmltext = "32010-03.htm";
		return htmltext;
	}
}
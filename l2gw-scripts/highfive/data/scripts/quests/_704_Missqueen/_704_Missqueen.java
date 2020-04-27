package quests._704_Missqueen;

import ru.l2gw.gameserver.model.instances.L2NpcInstance;
import ru.l2gw.gameserver.model.quest.Quest;
import ru.l2gw.gameserver.model.quest.QuestState;

public class _704_Missqueen extends Quest
{
	//Npc
	public final int m_q = 31760;

	//items
	public final int item_1 = 7832;
	public final int item_2 = 7833;

	public _704_Missqueen()
	{
		super(704, "_704_Missqueen", "Miss Queen", true);

		addStartNpc(m_q);
		addTalkId(m_q);
	}

	@Override
	public String onEvent(String event, QuestState st)
	{
		String htmltext = "noquest";
		if(event.equals("31760-02.htm"))
		{
			if(st.getInt("cond") == 0 && st.getPlayer().getLevel() <= 20 && st.getPlayer().getLevel() >= 6 && st.getPlayer().getPkKills() == 0)
			{
				st.giveItems(item_1, 1);
				st.set("cond", "1");
				htmltext = "c_1.htm";
				st.playSound(SOUND_ACCEPT);
			}
			else
				htmltext = "fail-01.htm";
		}
		else if(event.equals("31760-03.htm"))
			if(st.getInt("m_scond") == 0 && st.getPlayer().getLevel() <= 25 && st.getPlayer().getLevel() >= 20 && st.getPlayer().getPkKills() == 0)
			{
				st.giveItems(item_2, 1);
				st.set("m_scond", "1");
				htmltext = "c_2.htm";
				st.playSound(SOUND_ACCEPT);
			}
			else
				htmltext = "fail-02.htm";
		return htmltext;
	}

	@Override
	public String onTalk(L2NpcInstance npc, QuestState st)
	{
		String htmltext = "noquest";
		int npcId = npc.getNpcId();
		if(npcId == m_q)
			htmltext = "31760-01.htm";
		return htmltext;
	}

}
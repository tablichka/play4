package quests._121_PavelTheGiants;

import ru.l2gw.gameserver.model.instances.L2NpcInstance;
import ru.l2gw.gameserver.model.quest.Quest;
import ru.l2gw.gameserver.model.quest.QuestState;

public class _121_PavelTheGiants extends Quest
{
	//NPCs
	private static int NEWYEAR = 31961;
	private static int YUMI = 32041;

	public _121_PavelTheGiants()
	{
		super(121, "_121_PavelTheGiants", "Pavel The Giants");

		addStartNpc(NEWYEAR);
		addTalkId(NEWYEAR);
		addTalkId(YUMI);
	}

	@Override
	public String onEvent(String event, QuestState st)
	{
		if(st.isCompleted())
			return "completed";
		if(event.equals("collecter_yumi_q0121_0201.htm"))
		{
			st.playSound(SOUND_FINISH);
			st.addExpAndSp(76960, 5793);
			st.exitCurrentQuest(false);
		}
		return event;
	}

	@Override
	public String onTalk(L2NpcInstance npc, QuestState st)
	{
		if(st.isCompleted())
			return "completed";
		String htmltext = "noquest";
		int npcId = npc.getNpcId();
		int cond = st.getInt("cond");

		if(st.isCreated() && npcId == NEWYEAR)
		{
			if(st.getPlayer().getLevel() >= 46)
			{
				htmltext = "head_blacksmith_newyear_q0121_0101.htm";
				st.set("cond", "1");
				st.setState(STARTED);
				st.playSound(SOUND_ACCEPT);
			}
			else
			{
				htmltext = "head_blacksmith_newyear_q0121_0103.htm";
				st.exitCurrentQuest(false);
			}
		}
		else if(st.isStarted())
			if(npcId == YUMI && cond == 1)
				htmltext = "collecter_yumi_q0121_0101.htm";
			else
				htmltext = "head_blacksmith_newyear_q0121_0105.htm";
		return htmltext;
	}
}

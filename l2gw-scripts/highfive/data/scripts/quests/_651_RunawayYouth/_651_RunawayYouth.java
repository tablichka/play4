package quests._651_RunawayYouth;

import ru.l2gw.gameserver.model.quest.Quest;
import ru.l2gw.gameserver.model.quest.QuestState;
import ru.l2gw.gameserver.model.instances.L2NpcInstance;

public class _651_RunawayYouth extends Quest
{
	//Npc
	private static int IVAN = 32014;
	private static int BATIDAE = 31989;
	protected L2NpcInstance _npc;

	//Items
	private static int SOE = 736;

	private static String qn = "_651_RunawayYouth";

	public _651_RunawayYouth()
	{
		super(651, "_651_RunawayYouth", "Runaway Youth");

		addStartNpc(IVAN);
		addTalkId(BATIDAE);
	}

	@Override
	public String onEvent(String event, QuestState st)
	{
		String htmltext = event;
		if(event.equalsIgnoreCase("32014-04.htm"))
		{
			if(st.getQuestItemsCount(SOE) > 0)
			{
				st.set("cond", "1");
				st.setState(STARTED);
				st.playSound(SOUND_ACCEPT);
				st.takeItems(SOE, 1);
				htmltext = "32014-03.htm";
				//npc.broadcastPacket(MagicSkillUser(npc,npc,2013,1,20000,0));
				//Каст СОЕ и изчезновение НПЦ
				st.startQuestTimer("ivan_timer", 20000);
			}
		}
		else if(event.equalsIgnoreCase("32014-04a.htm"))
		{
			st.exitCurrentQuest(true);
			st.playSound(SOUND_GIVEUP);
		}
		else if(event.equalsIgnoreCase("ivan_timer"))
		{
			_npc.deleteMe();
			htmltext = null;
		}
		return htmltext;
	}

	@Override
	public String onTalk(L2NpcInstance npc, QuestState st)
	{
		String htmltext = "noquest";
		if(st.getPlayer().getQuestState(qn) != null)
		{
			int npcId = npc.getNpcId();
			int cond = st.getInt("cond");
			if(npcId == IVAN && st.isCreated())
			{
				if(st.getPlayer().getLevel() >= 26)
					htmltext = "32014-02.htm";
				else
				{
					htmltext = "32014-01.htm";
					st.exitCurrentQuest(true);
				}
			}
			else if(npcId == BATIDAE && cond == 1)
			{
				htmltext = "31989-01.htm";
				st.rollAndGive(57, 2883, 100);
				st.playSound(SOUND_FINISH);
				st.exitCurrentQuest(true);
			}
		}
		return htmltext;
	}
}

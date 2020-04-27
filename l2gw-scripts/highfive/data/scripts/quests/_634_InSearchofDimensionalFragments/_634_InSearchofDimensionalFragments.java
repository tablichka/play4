package quests._634_InSearchofDimensionalFragments;

import ru.l2gw.gameserver.model.L2Player;
import ru.l2gw.gameserver.model.instances.L2NpcInstance;
import ru.l2gw.gameserver.model.quest.Quest;
import ru.l2gw.gameserver.model.quest.QuestState;

public class _634_InSearchofDimensionalFragments extends Quest
{
	int DIMENSION_FRAGMENT_ID = 7079;

	public _634_InSearchofDimensionalFragments()
	{
		super(634, "_634_InSearchofDimensionalFragments", "In Search of Dimensional Fragments"); // Party true

		for(int npcId = 31494; npcId < 31508; npcId++)
		{
			addTalkId(npcId);
			addStartNpc(npcId);
		}

		for(int mobs = 21208; mobs < 21256; mobs++)
			addKillId(mobs);
	}

	@Override
	public String onEvent(String event, QuestState st)
	{
		if(event.equals("2a.htm"))
		{
			st.setState(STARTED);
			st.playSound(SOUND_ACCEPT);
			st.set("cond", "1");
		}
		else if(event.equals("5.htm"))
		{
			st.playSound(SOUND_FINISH);
			st.exitCurrentQuest(true);
		}
		return event;
	}

	@Override
	public String onTalk(L2NpcInstance npc, QuestState st)
	{
		String htmltext = "noquest";
		if(st.isCreated())
		{
			if(st.getPlayer().getLevel() < 20)
			{
				htmltext = "1.htm";
				st.exitCurrentQuest(true);
			}
			else
				htmltext = "2.htm";
		}
		else if(st.isStarted())
			htmltext = "4.htm";
		return htmltext;
	}

	@Override
	public void onKill(L2NpcInstance npc, L2Player killer)
	{
		QuestState st = getRandomPartyMemberWithQuest(killer, 1);
		if(st != null)
			st.rollAndGive(DIMENSION_FRAGMENT_ID, 1, 60);
	}
}
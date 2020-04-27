package quests._182_NewRecruits;

import ru.l2gw.gameserver.model.L2Player;
import ru.l2gw.gameserver.model.instances.L2NpcInstance;
import ru.l2gw.gameserver.model.quest.Quest;
import ru.l2gw.gameserver.model.quest.QuestState;

/**
 * @author: rage
 * @date: 21.10.2010 21:20:31
 * TODO: Nornil's Garden Instance
 */
public class _182_NewRecruits extends Quest
{
	// NPC's
	private static final int _kekropus = 32138;
	private static final int _nornil = 32258;

	public _182_NewRecruits()
	{
		super(182, "_182_NewRecruits", "New Recruits");

		addStartNpc(_kekropus);
		addTalkId(_kekropus);
		addTalkId(_nornil);
	}

	@Override
	public String onEvent(String event, QuestState st)
	{
		String htmltext = event;

		if(event.equalsIgnoreCase("32138-03.htm"))
		{
			st.setState(STARTED);
			st.setCond(1);
			st.playSound(SOUND_ACCEPT);
		}
		if(event.equalsIgnoreCase("32258-04.htm"))
		{
			st.giveItems(847, 2);
			st.playSound(SOUND_FINISH);
			st.exitCurrentQuest(false);
		}
		else if(event.equalsIgnoreCase("32258-05.htm"))
		{
			st.giveItems(890, 2);
			st.playSound(SOUND_FINISH);
			st.exitCurrentQuest(false);
		}
		return "npchtm:" + htmltext;
	}

	@Override
	public String onTalk(L2NpcInstance npc, QuestState st)
	{
		String htmltext = "noquest";
		L2Player player = st.getPlayer();

		if(player.getRace().ordinal() == 5)
			htmltext = "npchtm:32138-00.htm";
		else if(npc.getNpcId() == _kekropus)
		{
			if(st.isCreated())
				htmltext = "32138-01.htm";
			else if(st.isStarted() && st.getCond() == 1)
				htmltext = "npchtm:32138-03.htm";
			else if(st.isCompleted())
				htmltext = "npchtm:completed";
		}
		else if(npc.getNpcId() == _nornil && st.isStarted())
			htmltext = "npchtm:32258-01.htm";

		return htmltext;
	}
}

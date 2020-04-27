package quests._013_ParcelDelivery;

import ru.l2gw.gameserver.model.instances.L2NpcInstance;
import ru.l2gw.gameserver.model.quest.Quest;
import ru.l2gw.gameserver.model.quest.QuestState;

/**
 * One-time
 * Solo
 */
public class _013_ParcelDelivery extends Quest
{
	private static final int Fundin = 31274;
	private static final int Vulcan = 31539;

	private static final short PACKAGE = 7263;

	public _013_ParcelDelivery()
	{
		super(13, "_013_ParcelDelivery", "Parcel Delivery");

		addStartNpc(Fundin);
		addTalkId(Vulcan);
		addQuestItem(PACKAGE);
	}

	@Override
	public String onEvent(String event, QuestState st)
	{
		if(st.isCompleted())
			return "completed";

		String htmltext = event;
		if(event.equalsIgnoreCase("31274-2.htm"))
		{
			st.set("cond", "1");
			st.giveItems(PACKAGE, 1);
			st.setState(STARTED);
			st.playSound(SOUND_ACCEPT);
		}
		else if(event.equalsIgnoreCase("31539-2.htm"))
		{
			st.rollAndGive(57, 157834, 100);
			st.addExpAndSp(589082, 58794);
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
		int npcId = npc.getNpcId();
		if(st.isCreated() && npcId == Fundin)
		{
			if(st.getPlayer().getLevel() < 74)
			{
				htmltext = "31274-00.htm";
				st.exitCurrentQuest(true);
			}
			else
				htmltext = "31274-1.htm";
		}
		else if(st.isStarted() && st.getInt("cond") == 1)
		{
			switch(npcId)
			{
				case Fundin:
				{
					htmltext = "31274-2r.htm";
					break;
				}
				case Vulcan:
				{
					if(st.getQuestItemsCount(PACKAGE) > 0)
						htmltext = "31539-1.htm";
					break;
				}
			}
		}
		return htmltext;
	}
}
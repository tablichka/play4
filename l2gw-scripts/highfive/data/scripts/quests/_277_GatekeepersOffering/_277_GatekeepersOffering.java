package quests._277_GatekeepersOffering;

import ru.l2gw.gameserver.model.instances.L2NpcInstance;
import ru.l2gw.gameserver.model.quest.Quest;
import ru.l2gw.gameserver.model.quest.QuestState;

public class _277_GatekeepersOffering extends Quest
{
	private static final int STARSTONE1_ID = 1572;
	private static final int GATEKEEPER_CHARM_ID = 1658;

	public _277_GatekeepersOffering()
	{
		super(277, "_277_GatekeepersOffering", "Gatekeepers Offering");
		addStartNpc(30576);
		addKillId(20333);
		addQuestItem(STARSTONE1_ID);
	}

	@Override
	public String onEvent(String event, QuestState st)
	{
		String htmltext = event;
		if(event.equals("1"))
			if(st.getPlayer().getLevel() >= 15)
			{
				htmltext = "30576-03.htm";
				st.set("cond", "1");
				st.setState(STARTED);
				st.playSound(SOUND_ACCEPT);
			}
			else
				htmltext = "30576-01.htm";
		return htmltext;
	}

	@Override
	public String onTalk(L2NpcInstance npc, QuestState st)
	{
		String htmltext = "noquest";
		int npcId = npc.getNpcId();
		int cond = st.getInt("cond");

		if(npcId == 30576 && st.isCreated())
			htmltext = "30576-02.htm";
		else if(npcId == 30576 && cond == 1 && st.getQuestItemsCount(STARSTONE1_ID) < 20)
			htmltext = "30576-04.htm";
		else if(npcId == 30576 && cond == 2 && st.getQuestItemsCount(STARSTONE1_ID) < 20)
			htmltext = "30576-04.htm";
		else if(npcId == 30576 && cond == 2 && st.getQuestItemsCount(STARSTONE1_ID) >= 20)
		{
			htmltext = "30576-05.htm";
			st.takeItems(STARSTONE1_ID, -1);
			st.giveItems(GATEKEEPER_CHARM_ID, 1);
			st.playSound(SOUND_FINISH);
			st.exitCurrentQuest(true);
		}

		return htmltext;
	}

	@Override
	public void onKill(L2NpcInstance npc, QuestState st)
	{
		if(st.getCond() == 1 && st.rollAndGiveLimited(STARSTONE1_ID, 1, 33, 20))
		{
			if(st.getQuestItemsCount(STARSTONE1_ID) == 20)
			{
				st.set("cond", "2");
				st.playSound(SOUND_MIDDLE);
				st.setState(STARTED);
			}
			else
				st.playSound(SOUND_ITEMGET);

		}
	}
}
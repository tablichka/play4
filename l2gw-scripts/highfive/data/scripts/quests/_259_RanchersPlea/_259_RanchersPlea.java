package quests._259_RanchersPlea;

import ru.l2gw.gameserver.model.instances.L2NpcInstance;
import ru.l2gw.gameserver.model.quest.Quest;
import ru.l2gw.gameserver.model.quest.QuestState;

public class _259_RanchersPlea extends Quest
{
	private static final int GIANT_SPIDER_SKIN_ID = 1495;
	private static final int ADENA_ID = 57;
	private static final int HEALING_POTION_ID = 1061;
	private static final int WOODEN_ARROW_ID = 17;

	public _259_RanchersPlea()
	{
		super(259, "_259_RanchersPlea", "Ranchers Plea");
		addStartNpc(30497);

		addKillId(new int[]{20103, 20106, 20108});

		addQuestItem(GIANT_SPIDER_SKIN_ID);
	}

	@Override
	public String onEvent(String event, QuestState st)
	{
		String htmltext = event;
		if(event.equals("1"))
		{
			st.set("id", "0");
			st.set("cond", "1");
			st.setState(STARTED);
			st.playSound(SOUND_ACCEPT);
			htmltext = "30497-03.htm";
		}
		else if(event.equals("30497_1"))
		{
			htmltext = "30497-06.htm";
			st.set("cond", "0");
			st.playSound(SOUND_FINISH);
			st.exitCurrentQuest(true);
		}
		else if(event.equals("30497_2"))
			htmltext = "30497-07.htm";
		else if(event.equals("30405_1"))
			htmltext = "30405-03.htm";
		else if(event.equals("30405_2"))
		{
			htmltext = "30405-04.htm";
			st.rollAndGive(HEALING_POTION_ID, 1, 100);
			st.takeItems(GIANT_SPIDER_SKIN_ID, 10);
		}
		else if(event.equals("30405_3"))
		{
			htmltext = "30405-05.htm";
			st.rollAndGive(WOODEN_ARROW_ID, 50, 100);
			st.takeItems(GIANT_SPIDER_SKIN_ID, 10);
		}
		else if(event.equals("30405_4"))
			if(st.getQuestItemsCount(GIANT_SPIDER_SKIN_ID) >= 10)
				htmltext = "30405-06.htm";
			else if(st.getQuestItemsCount(GIANT_SPIDER_SKIN_ID) < 10)
				htmltext = "30405-07.htm";
		return htmltext;
	}

	@Override
	public String onTalk(L2NpcInstance npc, QuestState st)
	{
		int npcId = npc.getNpcId();
		String htmltext = "noquest";
		if(npcId == 30497 && st.isCreated())
		{
			if(st.getPlayer().getLevel() >= 15)
			{
				htmltext = "30497-02.htm";
				return htmltext;
			}
			else
			{
				htmltext = "30497-01.htm";
				st.exitCurrentQuest(true);
			}
		}
		else if(npcId == 30497 && st.getInt("cond") == 1 && st.getQuestItemsCount(GIANT_SPIDER_SKIN_ID) < 1)
			htmltext = "30497-04.htm";
		else if(npcId == 30497 && st.getInt("cond") == 1 && st.getQuestItemsCount(GIANT_SPIDER_SKIN_ID) >= 1)
		{
			htmltext = "30497-05.htm";
			st.rollAndGive(ADENA_ID, st.getQuestItemsCount(GIANT_SPIDER_SKIN_ID) * 25, 100);
			st.takeItems(GIANT_SPIDER_SKIN_ID, st.getQuestItemsCount(GIANT_SPIDER_SKIN_ID));
		}
		else if(npcId == 30405 && st.getInt("cond") == 1 && st.getQuestItemsCount(GIANT_SPIDER_SKIN_ID) < 10)
			htmltext = "30405-01.htm";
		else if(npcId == 30405 && st.getInt("cond") == 1 && st.getQuestItemsCount(GIANT_SPIDER_SKIN_ID) >= 10)
			htmltext = "30405-02.htm";
		return htmltext;
	}

	@Override
	public void onKill(L2NpcInstance npc, QuestState st)
	{
		if(st.getInt("cond") > 0)
			st.rollAndGive(GIANT_SPIDER_SKIN_ID, 1, 100);
	}
}
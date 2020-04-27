package quests._158_SeedOfEvil;

import ru.l2gw.gameserver.model.instances.L2NpcInstance;
import ru.l2gw.gameserver.model.quest.Quest;
import ru.l2gw.gameserver.model.quest.QuestState;

public class _158_SeedOfEvil extends Quest
{
	int CLAY_TABLET_ID = 1025;
	int ENCHANT_ARMOR_D = 956;

	public _158_SeedOfEvil()
	{
		super(158, "_158_SeedOfEvil", "158 Seed Of Evil");

		addStartNpc(30031);

		addTalkId(30031);

		addKillId(27016);

		addQuestItem(CLAY_TABLET_ID);
	}

	@Override
	public String onEvent(String event, QuestState st)
	{
		if(st.isCompleted())
			return "completed";
		String htmltext = event;
		if(event.equals("1"))
		{
			st.set("cond", "1");
			st.setState(STARTED);
			st.playSound(SOUND_ACCEPT);
			htmltext = "30031-04.htm";
		}
		return htmltext;
	}

	@Override
	public String onTalk(L2NpcInstance npc, QuestState st)
	{
		if(st.isCompleted())
			return "completed";
		int npcId = npc.getNpcId();
		String htmltext = "noquest";
		if(st.isCreated())
		{
			if(st.getPlayer().getLevel() >= 21)
			{
				htmltext = "30031-03.htm";
				return htmltext;
			}
			htmltext = "30031-02.htm";
			st.exitCurrentQuest(true);
		}
		else if(npcId == 30031 && st.getInt("cond") == 1)
			htmltext = "30031-05.htm";
		else if(npcId == 30031 && st.getInt("cond") == 2 && st.getQuestItemsCount(CLAY_TABLET_ID) != 0)
		{
			st.takeItems(CLAY_TABLET_ID, -1);
			st.playSound(SOUND_FINISH);
			st.addExpAndSp(17818, 927);
			st.rollAndGive(57, 1495, 100);
			st.rollAndGive(ENCHANT_ARMOR_D, 1, 100);
			htmltext = "30031-06.htm";
			st.exitCurrentQuest(false);
		}
		return htmltext;
	}

	@Override
	public void onKill(L2NpcInstance npc, QuestState st)
	{
		if(st.getInt("cond") == 1 && st.rollAndGiveLimited(CLAY_TABLET_ID, 1, 100, 1))
		{
			st.playSound(SOUND_MIDDLE);
			st.setCond(2);
			st.setState(STARTED);
		}
	}
}
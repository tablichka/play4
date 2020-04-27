package quests._117_OceanOfDistantStar;

import ru.l2gw.gameserver.model.instances.L2NpcInstance;
import ru.l2gw.gameserver.model.quest.Quest;
import ru.l2gw.gameserver.model.quest.QuestState;

public class _117_OceanOfDistantStar extends Quest
{
	//NPC
	private static final int Abey = 32053;
	private static final int GhostEngineer = 32055;
	private static final int Obi = 32052;
	private static final int GhostEngineer2 = 32054;
	private static final int Box = 32076;
	//Quest Items
	private static final int BookOfGreyStar = 8495;
	private static final int EngravedHammer = 8488;
	//Mobs
	private static final int BanditWarrior = 22023;
	private static final int BanditInspector = 22024;

	public _117_OceanOfDistantStar()
	{
		super(117, "_117_OceanOfDistantStar", "Ocean Of DistantStar");

		addStartNpc(Abey);

		addTalkId(GhostEngineer);
		addTalkId(Obi);
		addTalkId(Box);
		addTalkId(GhostEngineer2);

		addKillId(BanditWarrior);
		addKillId(BanditInspector);

		addQuestItem(BookOfGreyStar, EngravedHammer);
	}

	@Override
	public String onEvent(String event, QuestState st)
	{
		if(st.isCompleted())
			return "completed";
		String htmltext = event;
		if(event.equalsIgnoreCase("railman_abu_q0117_0104.htm"))
		{
			st.set("cond", "1");
			st.setState(STARTED);
			st.playSound(SOUND_ACCEPT);
		}
		else if(event.equalsIgnoreCase("ghost_of_railroadman2_q0117_0201.htm"))
			st.set("cond", "2");
		else if(event.equalsIgnoreCase("railman_obi_q0117_0301.htm"))
			st.set("cond", "3");
		else if(event.equalsIgnoreCase("railman_abu_q0117_0401.htm"))
			st.set("cond", "4");
		else if(event.equalsIgnoreCase("q_box_of_railroad_q0117_0501.htm"))
		{
			st.set("cond", "5");
			st.giveItems(EngravedHammer, 1);
		}
		else if(event.equalsIgnoreCase("railman_abu_q0117_0601.htm"))
			st.set("cond", "6");
		else if(event.equalsIgnoreCase("railman_obi_q0117_0701.htm"))
			st.set("cond", "7");
		else if(event.equalsIgnoreCase("railman_obi_q0117_0801.htm"))
		{
			st.takeItems(BookOfGreyStar, -1);
			st.set("cond", "9");
		}
		else if(event.equalsIgnoreCase("ghost_of_railroadman2_q0117_0901.htm"))
		{
			st.takeItems(EngravedHammer, -1);
			st.set("cond", "10");
		}
		else if(event.equalsIgnoreCase("ghost_of_railroadman_q0117_1002.htm"))
		{
			st.rollAndGive(57, 17647, 100);
			st.addExpAndSp(107387, 7369);
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
		int cond = 0;
		if(!st.isCreated())
			cond = st.getInt("cond");
		if(npcId == Abey)
		{
			if(cond == 0)
			{
				if(st.getPlayer().getLevel() >= 39)
					htmltext = "railman_abu_q0117_0101.htm";
				else
				{
					htmltext = "railman_abu_q0117_0103.htm";
					st.exitCurrentQuest(true);
				}
			}
			else if(cond == 3)
				htmltext = "railman_abu_q0117_0301.htm";
			else if(cond == 5 && st.getQuestItemsCount(EngravedHammer) > 0)
				htmltext = "railman_abu_q0117_0501.htm";
			else if(cond == 6 && st.getQuestItemsCount(EngravedHammer) > 0)
				htmltext = "railman_abu_q0117_0601.htm";
		}
		else if(npcId == GhostEngineer)
		{
			if(cond == 1)
				htmltext = "ghost_of_railroadman2_q0117_0101.htm";
			else if(cond == 9 && st.getQuestItemsCount(EngravedHammer) > 0)
				htmltext = "ghost_of_railroadman2_q0117_0801.htm";
		}
		else if(npcId == Obi)
		{
			if(cond == 2)
				htmltext = "railman_obi_q0117_0201.htm";
			else if(cond == 6 && st.getQuestItemsCount(EngravedHammer) > 0)
				htmltext = "railman_obi_q0117_0601.htm";
			else if(cond == 7 && st.getQuestItemsCount(EngravedHammer) > 0)
				htmltext = "railman_obi_q0117_0701.htm";
			else if(cond == 8 && st.getQuestItemsCount(BookOfGreyStar) > 0)
				htmltext = "railman_obi_q0117_0704.htm";
		}
		else if(npcId == Box && cond == 4)
			htmltext = "q_box_of_railroad_q0117_0401.htm";
		else if(npcId == GhostEngineer2 && cond == 10)
			htmltext = "ghost_of_railroadman_q0117_0901.htm";
		return htmltext;
	}

	@Override
	public void onKill(L2NpcInstance npc, QuestState st)
	{
		if(st.getInt("cond") == 7 && st.rollAndGiveLimited(BookOfGreyStar, 1, 30, 1))
		{
			st.playSound(SOUND_MIDDLE);
			st.set("cond", "8");
			st.setState(STARTED);
		}
	}
}
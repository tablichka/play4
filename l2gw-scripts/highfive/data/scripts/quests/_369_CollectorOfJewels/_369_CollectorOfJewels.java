package quests._369_CollectorOfJewels;

import ru.l2gw.gameserver.model.L2Player;
import ru.l2gw.gameserver.model.instances.L2NpcInstance;
import ru.l2gw.gameserver.model.quest.Quest;
import ru.l2gw.gameserver.model.quest.QuestState;

public class _369_CollectorOfJewels extends Quest
{
	// NPCs
	private static int NELL = 30376;
	// Mobs
	private static int Roxide = 20747;
	private static int Rowin_Undine = 20619;
	private static int Lakin_Undine = 20616;
	private static int Salamander_Rowin = 20612;
	private static int Lakin_Salamander = 20609;
	private static int Death_Fire = 20749;
	// Items
	private static int ADENA = 57;
	// Quest Items
	private static int FLARE_SHARD = 5882;
	private static int FREEZING_SHARD = 5883;

	public _369_CollectorOfJewels()
	{
		super(369, "_369_CollectorOfJewels", "Collector of Jewels"); // Party true
		addStartNpc(NELL);
		addKillId(Roxide);
		addKillId(Rowin_Undine);
		addKillId(Lakin_Undine);
		addKillId(Salamander_Rowin);
		addKillId(Lakin_Salamander);
		addKillId(Death_Fire);
		addQuestItem(FLARE_SHARD);
		addQuestItem(FREEZING_SHARD);

	}

	@Override
	public String onEvent(String event, QuestState st)
	{
		if(event.equalsIgnoreCase("30376-03.htm") && st.isCreated())
		{
			st.setState(STARTED);
			st.set("cond", "1");
			st.playSound(SOUND_ACCEPT);
		}
		else if(event.equalsIgnoreCase("30376-08.htm") && st.isStarted())
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
		if(npc.getNpcId() != NELL)
			return htmltext;
		if(st.isCreated())
		{
			if(st.getPlayer().getLevel() < 25)
			{
				st.exitCurrentQuest(true);
				htmltext = "30376-01.htm";
			}
			else
				htmltext = "30376-02.htm";
		}
		int cond = st.getInt("cond");
		if(cond == 1)
			htmltext = "30376-04.htm";
		else if(cond == 3)
			htmltext = "30376-09.htm";
		else if(cond == 2 || cond == 4)
		{
			int max_count = cond == 2 ? 50 : 200;
			htmltext = cond == 2 ? "30376-04.htm" : "30376-09.htm";
			long FLARE_SHARD_COUNT = st.getQuestItemsCount(FLARE_SHARD);
			long FREEZING_SHARD_COUNT = st.getQuestItemsCount(FREEZING_SHARD);
			if(FLARE_SHARD_COUNT < max_count || FREEZING_SHARD_COUNT < max_count)
			{
				st.set("cond", cond == 2 ? "1" : "3");
				st.setState(STARTED);
			}
			else
			{
				st.takeItems(FLARE_SHARD, -1);
				st.takeItems(FREEZING_SHARD, -1);
				if(cond == 2)
				{
					htmltext = "30376-05.htm";
					st.rollAndGive(ADENA, 12500, 100);
					st.playSound(SOUND_MIDDLE);
					st.set("cond", "3");
					st.setState(STARTED);
				}
				else
				{
					htmltext = "30376-10.htm";
					st.rollAndGive(ADENA, 63500, 100);
					st.playSound(SOUND_FINISH);
					st.exitCurrentQuest(true);
				}
			}
		}
		return htmltext;
	}

	@Override
	public void onKill(L2NpcInstance npc, L2Player killer)
	{
		QuestState st = getRandomPartyMemberWithQuest(killer, -1);
		if(st != null && (st.getCond() == 1 || st.getCond() == 3))
		{
			int npcId = npc.getNpcId();
			int MAX = st.getCond() == 1 ? 50 : 200;

			if(npcId == Roxide && st.rollAndGiveLimited(FREEZING_SHARD, 1, 85, MAX))
				st.playSound(SOUND_ITEMGET);
			else if(npcId == Rowin_Undine && st.rollAndGiveLimited(FREEZING_SHARD, 1, 73, MAX))
				st.playSound(SOUND_ITEMGET);
			else if(npcId == Lakin_Undine && st.rollAndGiveLimited(FREEZING_SHARD, 1, 60, MAX))
				st.playSound(SOUND_ITEMGET);
			else if(npcId == Salamander_Rowin && st.rollAndGiveLimited(FLARE_SHARD, 1, 77, MAX))
				st.playSound(SOUND_ITEMGET);
			else if(npcId == Lakin_Salamander && st.rollAndGiveLimited(FLARE_SHARD, 1, 77, MAX))
				st.playSound(SOUND_ITEMGET);
			else if(npcId == Death_Fire && st.rollAndGiveLimited(FLARE_SHARD, 1, 85, MAX))
				st.playSound(SOUND_ITEMGET);

			if(st.getQuestItemsCount(FREEZING_SHARD) == MAX && st.getQuestItemsCount(FLARE_SHARD) == MAX)
			{
				st.setCond(st.getCond() == 1 ? 2 : 4);
				st.setState(STARTED);
				st.playSound(SOUND_MIDDLE);
			}
		}
	}
}
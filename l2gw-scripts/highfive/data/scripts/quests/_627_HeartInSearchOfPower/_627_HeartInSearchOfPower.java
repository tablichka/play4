package quests._627_HeartInSearchOfPower;

import ru.l2gw.gameserver.model.L2Player;
import ru.l2gw.gameserver.model.instances.L2NpcInstance;
import ru.l2gw.gameserver.model.quest.Quest;
import ru.l2gw.gameserver.model.quest.QuestState;
import ru.l2gw.commons.arrays.GArray;
import ru.l2gw.commons.math.Rnd;

public class _627_HeartInSearchOfPower extends Quest
{
	//NPC
	private static final int M_NECROMANCER = 31518;
	private static final int ENFEUX = 31519;

	//ITEMS
	private static final int SEAL_OF_LIGHT = 7170;
	private static final int GEM_OF_SUBMISSION = 7171;
	private static final int GEM_OF_SAINTS = 7172;

	//REWARDS
	private static final int ADENA = 57;
	private static final int MOLD_HARDENER = 4041;
	private static final int ENRIA = 4042;
	private static final int ASOFE = 4043;
	private static final int THONS = 4044;

	//REWARDS
	//# [ ID1, ID2, COUNT1, COUNT1]	
	private static final int[][] REWARDS = {
			{1, ADENA, 0, 100000, 0},
			{2, ADENA, ASOFE, 6400, 13},
			{3, ADENA, THONS, 6400, 13},
			{4, ADENA, ENRIA, 13600, 6},
			{5, ADENA, MOLD_HARDENER, 17200, 3}};

	public _627_HeartInSearchOfPower()
	{
		super(627, "_627_HeartInSearchOfPower", "Heart In Search Of Power"); // party true

		addStartNpc(31518);

		addTalkId(31518);
		addTalkId(31519);

		for(int mobs = 21520; mobs <= 21541; mobs++)
			addKillId(mobs);
		for(int items = SEAL_OF_LIGHT; items <= GEM_OF_SAINTS; items++)
			addQuestItem(items);
	}

	@Override
	public String onEvent(String event, QuestState st)
	{
		String htmltext = event;
		if(event.equals("31518-1.htm"))
		{
			st.set("cond", "1");
			st.setState(STARTED);
			st.playSound(SOUND_ACCEPT);
		}
		else if(event.equals("31518-3.htm"))
		{
			st.takeItems(GEM_OF_SUBMISSION, -1);
			st.giveItems(SEAL_OF_LIGHT, 1);
			st.set("cond", "3");
			st.setState(STARTED);
		}
		else if(event.equals("31519-1.htm"))
		{
			st.takeItems(SEAL_OF_LIGHT, 1);
			st.giveItems(GEM_OF_SAINTS, 1);
			st.set("cond", "4");
			st.setState(STARTED);
		}
		for(int[] element : REWARDS)
		{
			if(event.equalsIgnoreCase(String.valueOf(element[0])) && st.getInt("cond") == 4 && st.getQuestItemsCount(GEM_OF_SAINTS) > 0)
			{
				st.takeItems(GEM_OF_SAINTS, -1);
				st.rollAndGive(element[1], element[3], 100);
				st.rollAndGive(element[2], element[4], 100);
				htmltext = "31518-6.htm";
				st.playSound(SOUND_FINISH);
				st.exitCurrentQuest(true);
			}
		}
		return htmltext;
	}

	@Override
	public String onTalk(L2NpcInstance npc, QuestState st)
	{
		String htmltext = "noquest";
		int npcId = npc.getNpcId();
		int cond = st.getInt("cond");
		if(npcId == M_NECROMANCER)
		{
			if(st.isCreated())
				if(st.getPlayer().getLevel() >= 60)
					htmltext = "31518-0.htm";
				else
				{
					htmltext = "31518-0a.htm";
					st.exitCurrentQuest(true);
				}
			else if(cond == 1)
				htmltext = "31518-1a.htm";
			else if(cond == 2)
			{
				if(st.getQuestItemsCount(GEM_OF_SUBMISSION) < 300)
				{
					st.set("cond", "1");
					st.setState(STARTED);
					st.getPlayer().sendMessage("Incorrect item count.");
					htmltext = "31518-1a.htm";
				}
				else
					htmltext = "31518-2.htm";
			}
			else if(cond == 3)
			{
				if(st.getQuestItemsCount(SEAL_OF_LIGHT) < 1)
					st.giveItems(SEAL_OF_LIGHT, 1);
				htmltext = "31518-3.htm";
			}
			else if(cond == 4 && st.getQuestItemsCount(GEM_OF_SAINTS) > 0)
				htmltext = "31518-4.htm";
		}
		else if(npcId == ENFEUX)
		{
			if(st.getQuestItemsCount(SEAL_OF_LIGHT) > 0)
				htmltext = "31519-0.htm";
			else if(st.getQuestItemsCount(GEM_OF_SAINTS) > 0)
				htmltext = "31519-1.htm";
		}
		return htmltext;
	}

	@Override
	public void onKill(L2NpcInstance npc, L2Player killer)
	{
		GArray<QuestState> pm = new GArray<QuestState>();

		for(QuestState st : getPartyMembersWithQuest(killer, 1))
			if(st.getQuestItemsCount(GEM_OF_SUBMISSION) < 300)
				pm.add(st);

		if(pm.isEmpty())
			return;


		QuestState st = pm.get(Rnd.get(pm.size()));

		if(st.rollAndGiveLimited(GEM_OF_SUBMISSION, 1, 100, 300))
			st.playSound(st.getQuestItemsCount(GEM_OF_SUBMISSION) == 300 ? SOUND_MIDDLE : SOUND_ITEMGET);

		if(st.getQuestItemsCount(GEM_OF_SUBMISSION) == 300)
		{
			st.set("cond", "2");
			st.setState(STARTED);
			st.playSound(SOUND_MIDDLE);
		}
	}
}
package quests._236_SeedsOfChaos;

import ru.l2gw.gameserver.controllers.ThreadPoolManager;
import ru.l2gw.gameserver.model.base.Race;
import ru.l2gw.gameserver.model.instances.L2NpcInstance;
import ru.l2gw.gameserver.model.quest.Quest;
import ru.l2gw.gameserver.model.quest.QuestState;

public class _236_SeedsOfChaos extends Quest
{
	// NPCs
	private final static int KEKROPUS = 32138;
	private final static int WIZARD = 31522;
	private final static int KATENAR = 32240;
	private final static int ROCK = 32238;
	private final static int HARKILGAMED = 32236;
	private final static int MAO = 32190;
	private final static int RODENPICULA = 32237;
	private final static int NORNIL = 32239;
	// Mobs
	private final static int[] NEEDLE_STAKATO_DRONES = {21516, 21517};
	private final static int[] SPLENDOR_MOBS = {
			21520,
			21521,
			21522,
			21523,
			21524,
			21525,
			21526,
			21527,
			21528,
			21529,
			21530,
			21531,
			21532,
			21533,
			21534,
			21535,
			21536,
			21537,
			21538,
			21539,
			21540,
			21541};
	// Items
	private final static short STAR_OF_DESTINY = 5011;
	private final static short SCROLL_ENCHANT_WEAPON_A = 729;
	// Quest Items
	private final static short SHINING_MEDALLION = 9743;
	private final static short BLACK_ECHO_CRYSTAL = 9745;
	// Chances
	private final static int BLACK_ECHO_CRYSTAL_CHANCE = 15;
	private final static int SHINING_MEDALLION_CHANCE = 20;

	private static boolean KATENAR_SPAWNED = false;
	private static boolean HARKILGAMED_SPAWNED = false;

	public _236_SeedsOfChaos()
	{
		super(236, "_236_SeedsOfChaos", "Seeds of Chaos");
		addStartNpc(KEKROPUS);
		addTalkId(WIZARD);
		addTalkId(KATENAR);
		addTalkId(ROCK);
		addTalkId(HARKILGAMED);
		addTalkId(MAO);
		addTalkId(RODENPICULA);
		addTalkId(NORNIL);

		addKillId(NEEDLE_STAKATO_DRONES);
		addKillId(SPLENDOR_MOBS);

		addQuestItem(SHINING_MEDALLION, BLACK_ECHO_CRYSTAL);
	}

	@Override
	public String onEvent(String event, QuestState st)
	{
		if(st.isCompleted())
			return "completed";
		int cond = st.getInt("cond");
		if(event.equalsIgnoreCase("32138_02b.htm") && st.isCreated())
		{
			if(st.getPlayer().getRace() != Race.kamael)
			{
				st.exitCurrentQuest(true);
				return "32138_00.htm";
			}
			if(st.getPlayer().getLevel() < 75)
			{
				st.exitCurrentQuest(true);
				return "32138_01.htm";
			}
			if(st.getQuestItemsCount(STAR_OF_DESTINY) == 0)
			{
				st.exitCurrentQuest(true);
				return "32138_01a.htm";
			}
			st.setState(STARTED);
			st.set("cond", "1");
			st.playSound(SOUND_ACCEPT);
		}
		else if(event.equalsIgnoreCase("31522_02.htm") && st.isStarted() && cond == 1)
			st.set("cond", "2");
		else if(event.equalsIgnoreCase("32236_08.htm") && st.isStarted() && cond == 13)
			st.set("cond", "14");
		else if(event.equalsIgnoreCase("32138_09.htm") && st.isStarted() && cond == 14)
			st.set("cond", "15");
		else if(event.equalsIgnoreCase("32263_11.htm") && st.isStarted() && cond == 16)
			st.set("cond", "17");
		else if(event.equalsIgnoreCase("32239_12.htm") && st.isStarted() && cond == 17)
			st.set("cond", "18");
		else if(event.equalsIgnoreCase("32263_13.htm") && st.isStarted() && cond == 18)
			st.set("cond", "19");
		else if(event.equalsIgnoreCase("32239_14.htm") && st.isStarted() && cond == 19)
			st.set("cond", "20");
		else if(event.equalsIgnoreCase("31522_03b.htm") && st.isStarted() && st.getQuestItemsCount(BLACK_ECHO_CRYSTAL) > 0)
		{
			st.takeItems(BLACK_ECHO_CRYSTAL, -1);
			st.set("echo", "1");
		}
		else if(event.equalsIgnoreCase("31522-ready") && st.isStarted() && (cond == 3 || cond == 4) && st.getInt("echo") == 1)
		{
			if(cond == 3)
				st.set("cond", "4");
			if(!KATENAR_SPAWNED)
			{
				st.getPcSpawn().addSpawn(KATENAR, 120000);
				ThreadPoolManager.getInstance().scheduleGeneral(new OnDespawn(true), 120000);
				KATENAR_SPAWNED = true;
			}
			return null;
		}
		else if(event.equalsIgnoreCase("32238-harkil") && st.isStarted() && (cond == 5 || cond == 13))
		{
			if(!HARKILGAMED_SPAWNED)
			{
				st.getPcSpawn().addSpawn(HARKILGAMED, 120000);
				ThreadPoolManager.getInstance().scheduleGeneral(new OnDespawn(false), 120000);
				HARKILGAMED_SPAWNED = true;
			}
			return null;
		}
		else if(event.equalsIgnoreCase("32236-hunt") && st.isStarted() && cond == 5)
		{
			st.set("cond", "12");
			return null;
		}
		else if(event.equalsIgnoreCase("32240_02.htm") && st.isStarted() && cond == 4)
		{
			st.set("cond", "5");
			st.unset("echo");
		}
		else if(event.equalsIgnoreCase("32190_02.htm") && st.isStarted() && (cond == 15 || cond == 16))
		{
			if(cond == 15)
				st.set("cond", "16");
			st.getPlayer().teleToLocation(-119534, 87176, -12593);
		}
		else if(event.equalsIgnoreCase("32263_15.htm") && st.isStarted() && cond == 20)
		{
			st.rollAndGive(SCROLL_ENCHANT_WEAPON_A, 1, 100);
			st.playSound(SOUND_FINISH);
			st.unset("cond");
			st.exitCurrentQuest(false);
		}

		return event;
	}

	@Override
	public String onTalk(L2NpcInstance npc, QuestState st)
	{
		if(st.isCompleted())
			return "completed";
		int npcId = npc.getNpcId();

		if(st.isCreated())
		{
			if(npcId != KEKROPUS)
				return "noquest";
			if(st.getPlayer().getRace().ordinal() != 5)
			{
				st.exitCurrentQuest(true);
				return "32138_00.htm";
			}
			if(st.getPlayer().getLevel() < 75)
			{
				st.exitCurrentQuest(true);
				return "32138_01.htm";
			}
			if(st.getQuestItemsCount(STAR_OF_DESTINY) == 0)
			{
				st.exitCurrentQuest(true);
				return "32138_01a.htm";
			}
			st.set("cond", "0");
			return "32138_02.htm";
		}

		if(!st.isStarted())
			return "noquest";
		int cond = st.getInt("cond");

		if(npcId == KEKROPUS)
			return cond < 14 ? "32138_02c.htm" : cond == 14 ? "32138_08.htm" : "32138_10.htm";

		if(npcId == KATENAR)
			return cond < 4 ? "noquest" : cond == 4 ? "32240_01.htm" : "32240_02.htm";

		if(npcId == ROCK)
			return cond == 5 || cond == 13 ? "32238-01.htm" : "32238-00.htm";

		if(npcId == MAO)
			return cond == 15 || cond == 16 ? "32190_01.htm" : "noquest";

		if(npcId == WIZARD)
		{
			if(cond == 1)
				return st.getQuestItemsCount(STAR_OF_DESTINY) == 0 ? "31522_00.htm" : "31522_01.htm";
			if(cond == 2)
				return "31522_02a.htm";
			if(cond == 3)
			{
				if(st.getQuestItemsCount(BLACK_ECHO_CRYSTAL) == 0)
				{
					st.set("cond", "2");
					return "31522_02a.htm";
				}
				return "31522_03.htm";
			}
			if(cond == 4 && st.getInt("echo") == 1 && !KATENAR_SPAWNED)
				return "31522_03c.htm";
			return "31522_04.htm";
		}

		if(npcId == HARKILGAMED)
		{
			if(cond == 5)
				return "32236_05.htm";
			if(cond == 12)
				return "32236_06.htm";
			if(cond == 13)
			{
				if(st.getQuestItemsCount(SHINING_MEDALLION) < 62)
				{
					st.set("cond", "12");
					return "32236_06.htm";
				}
				st.takeItems(SHINING_MEDALLION, -1);
				return "32236_07.htm";
			}
			if(cond > 13)
				return "32236_09.htm";
			return "noquest";
		}

		if(npcId == RODENPICULA)
		{
			if(cond == 16)
				return "32263_10.htm";
			if(cond == 17)
				return "32263_11.htm";
			if(cond == 18)
				return "32263_12.htm";
			if(cond == 19)
				return "32263_13.htm";
			if(cond == 20)
				return "32263_14.htm";
		}

		if(npcId == NORNIL)
		{
			if(cond == 17)
				return "32239_11.htm";
			if(cond == 18)
				return "32239_12.htm";
			if(cond == 19)
				return "32239_13.htm";
			if(cond == 20)
				return "32239_14.htm";
		}

		return "noquest";
	}

	@Override
	public void onKill(L2NpcInstance npc, QuestState st)
	{
		if(!st.isStarted())
			return;
		int npcId = npc.getNpcId();
		int cond = st.getInt("cond");

		if(IsInIntArray(npcId, NEEDLE_STAKATO_DRONES))
		{
			if(cond == 2 && st.rollAndGiveLimited(BLACK_ECHO_CRYSTAL, 1, BLACK_ECHO_CRYSTAL_CHANCE, 1))
			{
				st.set("cond", "3");
				st.playSound(SOUND_MIDDLE);
				st.setState(STARTED);
			}
		}
		else if(IsInIntArray(npcId, SPLENDOR_MOBS))
		{
			if(cond == 12 && st.rollAndGiveLimited(SHINING_MEDALLION, 1, SHINING_MEDALLION_CHANCE, 62))
			{
				if(st.getQuestItemsCount(SHINING_MEDALLION) < 62)
					st.playSound(SOUND_ITEMGET);
				else
				{
					st.set("cond", "13");
					st.playSound(SOUND_MIDDLE);
					st.setState(STARTED);
				}
			}
		}
	}

	private static boolean IsInIntArray(int i, int[] a)
	{
		for(int _i : a)
			if(_i == i)
				return true;
		return false;
	}

	public static class OnDespawn implements Runnable
	{
		private final boolean _SUBJ_KATENAR;

		public OnDespawn(boolean SUBJ_KATENAR)
		{
			_SUBJ_KATENAR = SUBJ_KATENAR;
		}

		public void run()
		{
			if(_SUBJ_KATENAR)
				KATENAR_SPAWNED = false;
			else
				HARKILGAMED_SPAWNED = false;
		}
	}
}
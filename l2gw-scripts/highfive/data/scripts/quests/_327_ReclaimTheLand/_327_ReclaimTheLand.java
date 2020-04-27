package quests._327_ReclaimTheLand;

import javolution.util.FastMap;
import ru.l2gw.commons.math.Rnd;
import ru.l2gw.gameserver.model.instances.L2NpcInstance;
import ru.l2gw.gameserver.model.quest.Quest;
import ru.l2gw.gameserver.model.quest.QuestState;

public class _327_ReclaimTheLand extends Quest
{
	// NPCs
	private static int Piotur = 30597;
	private static int Iris = 30034;
	private static int Asha = 30313;
	// Items
	private static int ADENA = 57;
	// Quest Items
	private static int TUREK_DOGTAG = 1846;
	private static int TUREK_MEDALLION = 1847;
	private static int CLAY_URN_FRAGMENT = 1848;
	private static int BRASS_TRINKET_PIECE = 1849;
	private static int BRONZE_MIRROR_PIECE = 1850;
	private static int JADE_NECKLACE_BEAD = 1851;
	private static int ANCIENT_CLAY_URN = 1852;
	private static int ANCIENT_BRASS_TIARA = 1853;
	private static int ANCIENT_BRONZE_MIRROR = 1854;
	private static int ANCIENT_JADE_NECKLACE = 1855;
	// Chances
	private static int Exchange_Chance = 80;

	private static FastMap<Integer, Integer> EXP = new FastMap<Integer, Integer>();

	public _327_ReclaimTheLand()
	{
		super(327, "_327_ReclaimTheLand", "Reclaim The Land");
		addStartNpc(Piotur);
		addTalkId(Iris);
		addTalkId(Asha);

		EXP.put(ANCIENT_CLAY_URN, 913);
		EXP.put(ANCIENT_BRASS_TIARA, 1065);
		EXP.put(ANCIENT_BRONZE_MIRROR, 1065);
		EXP.put(ANCIENT_JADE_NECKLACE, 1294);

		addKillId(20495, 20496, 20497, 20498, 20499, 20500, 20501);

		addQuestItem(CLAY_URN_FRAGMENT,
				BRASS_TRINKET_PIECE,
				BRONZE_MIRROR_PIECE,
				JADE_NECKLACE_BEAD,
				TUREK_MEDALLION,
				TUREK_DOGTAG);
	}

	private static boolean ExpReward(QuestState st, int item_id)
	{
		Integer exp = EXP.get(item_id);
		if(exp == null)
			exp = 182;
		long exp_reward = st.getQuestItemsCount(item_id * exp);
		if(exp_reward == 0)
			return false;
		st.takeItems(item_id, -1);
		st.addExpAndSp(exp_reward, 0);
		st.playSound(SOUND_MIDDLE);
		return true;
	}

	@Override
	public String onEvent(String event, QuestState st)
	{
		if(event.equalsIgnoreCase("30597-03.htm") && st.isCreated())
		{
			st.setState(STARTED);
			st.set("cond", "1");
			st.playSound(SOUND_ACCEPT);
		}
		else if(event.equalsIgnoreCase("30597-06.htm") && st.isStarted())
		{
			st.playSound(SOUND_FINISH);
			st.exitCurrentQuest(true);
		}
		else if(event.equalsIgnoreCase("30313-02.htm") && st.isStarted() && st.getQuestItemsCount(CLAY_URN_FRAGMENT) >= 5)
		{
			st.takeItems(CLAY_URN_FRAGMENT, 5);
			if(!Rnd.chance(Exchange_Chance))
				return "30313-10.htm";
			st.giveItems(ANCIENT_CLAY_URN, 1);
			st.playSound(SOUND_MIDDLE);
			return "30313-03.htm";
		}
		else if(event.equalsIgnoreCase("30313-04.htm") && st.isStarted() && st.getQuestItemsCount(BRASS_TRINKET_PIECE) >= 5)
		{
			st.takeItems(BRASS_TRINKET_PIECE, 5);
			if(!Rnd.chance(Exchange_Chance))
				return "30313-10.htm";
			st.giveItems(ANCIENT_BRASS_TIARA, 1);
			st.playSound(SOUND_MIDDLE);
			return "30313-05.htm";
		}
		else if(event.equalsIgnoreCase("30313-06.htm") && st.isStarted() && st.getQuestItemsCount(BRONZE_MIRROR_PIECE) >= 5)
		{
			st.takeItems(BRONZE_MIRROR_PIECE, 5);
			if(!Rnd.chance(Exchange_Chance))
				return "30313-10.htm";
			st.giveItems(ANCIENT_BRONZE_MIRROR, 1);
			st.playSound(SOUND_MIDDLE);
			return "30313-07.htm";
		}
		else if(event.equalsIgnoreCase("30313-08.htm") && st.isStarted() && st.getQuestItemsCount(JADE_NECKLACE_BEAD) >= 5)
		{
			st.takeItems(JADE_NECKLACE_BEAD, 5);
			if(!Rnd.chance(Exchange_Chance))
				return "30313-09.htm";
			st.giveItems(ANCIENT_JADE_NECKLACE, 1);
			st.playSound(SOUND_MIDDLE);
			return "30313-07.htm";
		}
		else if(event.equalsIgnoreCase("30034-03.htm") && st.isStarted())
		{
			if(!ExpReward(st, CLAY_URN_FRAGMENT))
				return "30034-02.htm";
		}
		else if(event.equalsIgnoreCase("30034-04.htm") && st.isStarted())
		{
			if(!ExpReward(st, BRASS_TRINKET_PIECE))
				return "30034-02.htm";
		}
		else if(event.equalsIgnoreCase("30034-05.htm") && st.isStarted())
		{
			if(!ExpReward(st, BRONZE_MIRROR_PIECE))
				return "30034-02.htm";
		}
		else if(event.equalsIgnoreCase("30034-06.htm") && st.isStarted())
		{
			if(!ExpReward(st, JADE_NECKLACE_BEAD))
				return "30034-02.htm";
		}
		else if(event.equalsIgnoreCase("30034-07.htm") && st.isStarted())
			if(!(ExpReward(st, ANCIENT_CLAY_URN) || ExpReward(st, ANCIENT_BRASS_TIARA) || ExpReward(st, ANCIENT_BRONZE_MIRROR) || ExpReward(st, ANCIENT_JADE_NECKLACE)))
				return "30034-02.htm";

		return event;
	}

	@Override
	public String onTalk(L2NpcInstance npc, QuestState st)
	{
		int npcId = npc.getNpcId();
		if(st.isCreated())
		{
			if(npcId != Piotur)
				return "noquest";
			if(st.getPlayer().getLevel() < 25)
			{
				st.exitCurrentQuest(true);
				return "30597-01.htm";
			}
			st.set("cond", "0");
			return "30597-02.htm";
		}

		if(!st.isStarted())
			return "noquest";

		if(npcId == Piotur)
		{
			long reward = st.getQuestItemsCount(TUREK_DOGTAG) * 40 + st.getQuestItemsCount(TUREK_MEDALLION) * 50;
			if(reward == 0)
				return "30597-04.htm";
			st.takeItems(TUREK_DOGTAG, -1);
			st.takeItems(TUREK_MEDALLION, -1);
			st.giveItems(ADENA, reward);
			st.playSound(SOUND_MIDDLE);
			return "30597-05.htm";
		}
		if(npcId == Iris)
			return "30034-01.htm";
		if(npcId == Asha)
			return "30313-01.htm";

		return "noquest";
	}

	@Override
	public void onKill(L2NpcInstance npc, QuestState st)
	{
		if(!st.isStarted())
			return;
		int npcId = npc.getNpcId();
		if(npcId == 20495)
		{
			if(Rnd.chance(13))
				giveSomeItems(st);

			if(st.rollAndGive(TUREK_MEDALLION, 1, 100))
				st.playSound(SOUND_ITEMGET);
		}
		else if(npcId == 20496)
		{
			if(Rnd.chance(9))
				giveSomeItems(st);

			if(st.rollAndGive(TUREK_DOGTAG, 1, 100))
				st.playSound(SOUND_ITEMGET);
		}
		else if(npcId == 20497)
		{
			if(Rnd.chance(11))
				giveSomeItems(st);

			if(st.rollAndGive(TUREK_MEDALLION, 1, 100))
				st.playSound(SOUND_ITEMGET);
		}
		else if(npcId == 20498)
		{
			if(Rnd.chance(10))
				giveSomeItems(st);

			if(st.rollAndGive(TUREK_DOGTAG, 1, 100))
				st.playSound(SOUND_ITEMGET);
		}
		else if(npcId == 20499)
		{
			if(Rnd.chance(8))
				giveSomeItems(st);

			if(st.rollAndGive(TUREK_DOGTAG, 1, 100))
				st.playSound(SOUND_ITEMGET);
		}
		else if(npcId == 20500)
		{
			if(Rnd.chance(7))
				giveSomeItems(st);

			if(st.rollAndGive(TUREK_DOGTAG, 1, 100))
				st.playSound(SOUND_ITEMGET);
		}
		else if(npcId == 20501)
		{
			if(Rnd.chance(12))
				giveSomeItems(st);

			if(st.rollAndGive(TUREK_MEDALLION, 1, 100))
				st.playSound(SOUND_ITEMGET);
		}

	}

	public void giveSomeItems(QuestState st)
	{
		int n = Rnd.get(100);
		if(n < 25)
			st.rollAndGive(CLAY_URN_FRAGMENT, 1, 100);
		else if(n < 50)
			st.rollAndGive(BRASS_TRINKET_PIECE, 1, 100);
		else if(n < 75)
			st.rollAndGive(BRONZE_MIRROR_PIECE, 1, 100);
		else
			st.rollAndGive(JADE_NECKLACE_BEAD, 1, 100);
	}

}
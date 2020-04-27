package quests._371_ShriekOfGhosts;

import javolution.util.FastMap;
import ru.l2gw.commons.math.Rnd;
import ru.l2gw.gameserver.model.L2Player;
import ru.l2gw.gameserver.model.instances.L2NpcInstance;
import ru.l2gw.gameserver.model.quest.Quest;
import ru.l2gw.gameserver.model.quest.QuestState;

public class _371_ShriekOfGhosts extends Quest
{
	// NPCs
	private static int REVA = 30867;
	private static int PATRIN = 30929;
	// Mobs
	private static int Hallates_Warrior = 20818;
	private static int Hallates_Knight = 20820;
	private static int Hallates_Commander = 20824;
	// Items
	private static int ADENA = 57;
	private static int Ancient_Porcelain__Excellent = 6003;
	private static int Ancient_Porcelain__High_Quality = 6004;
	private static int Ancient_Porcelain__Low_Quality = 6005;
	private static int Ancient_Porcelain__Lowest_Quality = 6006;
	// Quest Items
	private static int Ancient_Ash_Urn = 5903;
	private static int Ancient_Porcelain = 6002;
	// Chances
	private static int Urn_Chance = 43;
	private static int Ancient_Porcelain__Excellent_Chance = 2;
	private static int Ancient_Porcelain__High_Quality_Chance = 27;
	private static int Ancient_Porcelain__Low_Quality_Chance = 65;
	private static int Ancient_Porcelain__Lowest_Quality_Chance = 76;
	private FastMap<Integer, Integer> common_chances = new FastMap<Integer, Integer>();

	public _371_ShriekOfGhosts()
	{
		super(371, "_371_ShriekOfGhosts", "Shriek Of Ghosts"); // party = true
		addStartNpc(REVA);
		addTalkId(PATRIN);
		addKillId(Hallates_Warrior);
		addKillId(Hallates_Knight);
		addKillId(Hallates_Commander);
		addQuestItem(Ancient_Ash_Urn, Ancient_Porcelain);
		common_chances.put(Hallates_Warrior, 71);
		common_chances.put(Hallates_Knight, 74);
		common_chances.put(Hallates_Commander, 82);
	}

	@Override
	public String onEvent(String event, QuestState st)
	{
		String htmltext = event;
		if(event.equalsIgnoreCase("30867-03.htm") && st.isCreated())
		{
			st.setState(STARTED);
			st.set("cond", "1");
			st.playSound(SOUND_ACCEPT);
		}
		else if(event.equalsIgnoreCase("30867-10.htm") && st.isStarted())
		{
			long Ancient_Ash_Urn_count = st.getQuestItemsCount(Ancient_Ash_Urn);
			if(Ancient_Ash_Urn_count > 0)
			{
				st.takeItems(Ancient_Ash_Urn, -1);
				st.rollAndGive(ADENA, Ancient_Ash_Urn_count * 1000, 100);
			}
			st.exitCurrentQuest(true);
		}
		else if(event.equalsIgnoreCase("30867-TRADE") && st.isStarted())
		{
			long Ancient_Ash_Urn_count = st.getQuestItemsCount(Ancient_Ash_Urn);
			if(Ancient_Ash_Urn_count > 0)
			{
				htmltext = Ancient_Ash_Urn_count > 100 ? "30867-08.htm" : "30867-07.htm";
				int bonus = Ancient_Ash_Urn_count > 100 ? 17000 : 3000;
				st.takeItems(Ancient_Ash_Urn, -1);
				st.rollAndGive(ADENA, bonus + Ancient_Ash_Urn_count * 1000, 100);
			}
			else
				htmltext = "30867-06.htm";
		}
		else if(event.equalsIgnoreCase("30929-TRADE") && st.isStarted())
		{
			long Ancient_Porcelain_count = st.getQuestItemsCount(Ancient_Porcelain);
			if(Ancient_Porcelain_count > 0)
			{
				st.takeItems(Ancient_Porcelain, 1);
				if(Rnd.chance(Ancient_Porcelain__Excellent_Chance))
				{
					st.giveItems(Ancient_Porcelain__Excellent, 1);
					htmltext = "30929-03.htm";
				}
				else if(Rnd.chance(Ancient_Porcelain__High_Quality_Chance))
				{
					st.giveItems(Ancient_Porcelain__High_Quality, 1);
					htmltext = "30929-04.htm";
				}
				else if(Rnd.chance(Ancient_Porcelain__Low_Quality_Chance))
				{
					st.giveItems(Ancient_Porcelain__Low_Quality, 1);
					htmltext = "30929-05.htm";
				}
				else if(Rnd.chance(Ancient_Porcelain__Lowest_Quality_Chance))
				{
					st.giveItems(Ancient_Porcelain__Lowest_Quality, 1);
					htmltext = "30929-06.htm";
				}
				else
					htmltext = "30929-07.htm";
			}
			else
				htmltext = "30929-02.htm";
		}
		return htmltext;
	}

	@Override
	public String onTalk(L2NpcInstance npc, QuestState st)
	{
		String htmltext = "noquest";
		int npcId = npc.getNpcId();

		if(st.isCreated())
		{
			if(npcId != REVA)
				return htmltext;
			if(st.getPlayer().getLevel() >= 59)
			{
				htmltext = "30867-02.htm";
				st.set("cond", "0");
			}
			else
			{
				htmltext = "30867-01.htm";
				st.exitCurrentQuest(true);
			}
		}
		else if(st.isStarted() && npcId == REVA)
			htmltext = st.getQuestItemsCount(Ancient_Porcelain) > 0 ? "30867-05.htm" : "30867-04.htm";
		else if(st.isStarted() && npcId == PATRIN)
			htmltext = "30929-01.htm";

		return htmltext;
	}

	@Override
	public void onKill(L2NpcInstance npc, L2Player killer)
	{
		QuestState st = getRandomPartyMemberWithQuest(killer, 1);
		if(st != null)
		{
			if(common_chances.get(npc.getNpcId()) == null)
				return;

			if(st.rollAndGive(Ancient_Porcelain, 1, common_chances.get(npc.getNpcId())))
			{
				if(st.rollAndGive(Ancient_Ash_Urn, 1, Urn_Chance))
					st.playSound(SOUND_MIDDLE);
				else
					st.playSound(SOUND_ITEMGET);

			}

		}

	}
}
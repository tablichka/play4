package quests._064_CertifiedBerserker;

import ru.l2gw.gameserver.model.L2ObjectsStorage;
import ru.l2gw.gameserver.model.instances.L2NpcInstance;
import ru.l2gw.gameserver.model.quest.Quest;
import ru.l2gw.gameserver.model.quest.QuestState;
import ru.l2gw.gameserver.model.quest.QuestTimer;
import ru.l2gw.commons.math.Rnd;


public class _064_CertifiedBerserker extends Quest
{
	// NPC
	private static final int ORKURUS = 32207;
	private static final int TENAIN = 32215;
	private static final int GORT = 32252;
	private static final int HARKILGAMED = 32236;
	private static final int ENTIEN = 32200;

	// Mobs
	private static final int BREKA_ORC = 20267;
	private static final int BREKA_ORC_ARCHER = 20268;
	private static final int BREKA_ORC_SHAMAN = 20269;
	private static final int BREKA_ORC_OVERLORD = 20270;
	private static final int BREKA_ORC_WARRIOR = 20271;
	private static final int ROAD_SCAVENGER = 20551;
	private static final int DEAD_SEEKER = 20202;
	private static final int STAKATO = 20234;
	private static final int DIVINE = 27323;

	// Quest Item
	private static final int Dimenional_Diamonds = 7562;
	private static final int BREKA_ORC_HEAD = 9754;
	private static final int MESSAGE_PLATE = 9755;
	private static final int REPORT1 = 9756;
	private static final int REPORT2 = 9757;
	private static final int H_LETTER = 9758;
	private static final int T_REC = 9759;
	private static final int OrkurusRecommendation = 9760;

	public _064_CertifiedBerserker()
	{
		super(64, "_064_CertifiedBerserker", "Certified Berserker");

		addStartNpc(ORKURUS);

		addTalkId(ORKURUS);
		addTalkId(TENAIN);
		addTalkId(GORT);
		addTalkId(ENTIEN);
		addTalkId(HARKILGAMED);

		addKillId(BREKA_ORC);
		addKillId(BREKA_ORC_ARCHER);
		addKillId(BREKA_ORC_SHAMAN);
		addKillId(BREKA_ORC_OVERLORD);
		addKillId(BREKA_ORC_WARRIOR);
		addKillId(ROAD_SCAVENGER);
		addKillId(DEAD_SEEKER);
		addKillId(STAKATO);
		addKillId(DIVINE);
	}

	@Override
	public String onEvent(String event, QuestState st)
	{
		String htmltext = event;
		if(event.equalsIgnoreCase("32207-01a.htm"))
		{
			st.set("cond", "1");
			st.setState(STARTED);
			if(!st.getPlayer().getVarB("dd"))
			{
				st.giveItems(Dimenional_Diamonds, 48);
				st.getPlayer().setVar("dd", "1");
			}
			st.playSound(SOUND_ACCEPT);
		}
		else if(event.equalsIgnoreCase("32215-01a.htm"))
		{
			st.set("cond", "2");
		}
		else if(event.equalsIgnoreCase("32252-01a.htm"))
		{
			st.set("cond", "5");
		}
		else if(event.equalsIgnoreCase("32215-03d.htm"))
		{
			st.takeItems(MESSAGE_PLATE, -1);
			st.set("cond", "8");
		}
		else if(event.equalsIgnoreCase("32236-01a.htm"))
		{
			st.set("cond", "13");
			st.giveItems(H_LETTER, 1);
			QuestTimer timer = st.getQuestTimer("HARKILGAMED_Fail");
			if(timer != null)
				timer.cancel();
			L2NpcInstance isQuest = L2ObjectsStorage.getByNpcId(HARKILGAMED);
			if(isQuest != null)
				isQuest.deleteMe();
		}
		else if(event.equalsIgnoreCase("32215-05a.htm"))
		{
			st.set("cond", "14");
			st.takeItems(H_LETTER, -1);
			st.giveItems(T_REC, 1);
		}
		else if(event.equalsIgnoreCase("32207-03a.htm"))
		{
			if(!st.getPlayer().getVarB("prof2.1"))
			{
				st.addExpAndSp(174503, 11973);
				st.rollAndGive(57, 31552, 100);
				st.getPlayer().setVar("prof2.1", "1");
			}
			st.giveItems(OrkurusRecommendation, 1);
			st.playSound(SOUND_FINISH);
			st.exitCurrentQuest(true);
		}
		if(event.equalsIgnoreCase("HARKILGAMED_Fail"))
		{
			L2NpcInstance isQuest = L2ObjectsStorage.getByNpcId(HARKILGAMED);
			if(isQuest != null)
				isQuest.deleteMe();
			return null;
		}
		return htmltext;
	}

	@Override
	public String onTalk(L2NpcInstance npc, QuestState st)
	{
		int npcId = npc.getNpcId();
		String htmltext = "noquest";
		int cond = st.getInt("cond");
		if(npcId == ORKURUS)
		{
			if(st.getQuestItemsCount(OrkurusRecommendation) != 0)
			{
				htmltext = "completed";
				st.exitCurrentQuest(true);
			}
			else if(st.isCreated())
			{
				if(st.getPlayer().getClassId().getId() == 0x7D)
				{
					if(st.getPlayer().getLevel() >= 39)
						htmltext = "32207-01.htm";
					else
					{
						htmltext = "32207-02.htm";
						st.exitCurrentQuest(true);
					}
				}
				else
				{
					htmltext = "32207-02a.htm";
					st.exitCurrentQuest(true);
				}
			}
			else if(cond == 14)
			{
				st.takeItems(T_REC, -1);
				htmltext = "32207-03.htm";
			}

		}
		else if(npcId == TENAIN)
		{
			if(cond == 1)
				htmltext = "32215-01.htm";
			else if(cond == 3)
			{
				htmltext = "32215-02.htm";
				st.takeItems(BREKA_ORC_HEAD, -1);
				st.set("cond", "4");
			}
			else if(cond > 1 && st.getQuestItemsCount(BREKA_ORC_HEAD) == 20)
			{
				htmltext = "32215-02.htm";
				st.takeItems(BREKA_ORC_HEAD, -1);
				st.set("cond", "4");
			}
			else if(cond == 7)
				htmltext = "32215-03.htm";
			else if(cond == 11)
			{
				st.set("cond", "12");
				htmltext = "32215-04.htm";
			}
			else if(cond == 13)
			{
				st.set("cond", "14");
				htmltext = "32215-05.htm";
			}

		}
		else if(npcId == GORT)
		{
			if(cond == 4)
				htmltext = "32252-01.htm";
			else if(cond == 6)
			{
				htmltext = "32252-02.htm";
				st.set("cond", "7");
			}
			else if(cond > 4 && st.getQuestItemsCount(MESSAGE_PLATE) == 1)
			{
				htmltext = "32252-02.htm";
				st.set("cond", "7");
			}
		}
		else if(npcId == ENTIEN)
		{
			if(cond == 8)
			{
				st.set("cond", "9");
				htmltext = "32200-01.htm";
			}
			else if(cond == 10)
			{
				st.set("cond", "11");
				st.takeItems(REPORT1, -1);
				st.takeItems(REPORT2, -1);
				htmltext = "32200-02.htm";
			}
		}
		else if(npcId == HARKILGAMED)
			if(cond == 12)
				htmltext = "32236-01.htm";
		return htmltext;

	}

	@Override
	public void onKill(L2NpcInstance npc, QuestState st)
	{
		int npcId = npc.getNpcId();
		int cond = st.getInt("cond");
		if(cond == 2)
			if(npcId == BREKA_ORC || npcId == BREKA_ORC_ARCHER || npcId == BREKA_ORC_SHAMAN || npcId == BREKA_ORC_OVERLORD || npcId == BREKA_ORC_WARRIOR)
				if(st.rollAndGiveLimited(BREKA_ORC_HEAD, 1, 100, 20))
				{
					if(st.getQuestItemsCount(BREKA_ORC_HEAD) == 20)
					{
						st.playSound(SOUND_MIDDLE);
						st.set("cond", "3");
						st.setState(STARTED);
					}
					else
						st.playSound(SOUND_ITEMGET);
				}
		if(cond == 5 && npcId == ROAD_SCAVENGER && Rnd.chance(3) && st.getQuestItemsCount(MESSAGE_PLATE) == 0)
		{
			st.giveItems(MESSAGE_PLATE, 1);
			st.set("cond", "6");
			st.playSound(SOUND_MIDDLE);
			st.setState(STARTED);
		}
		if(cond == 9)
		{
			if(npcId == DEAD_SEEKER && st.getQuestItemsCount(REPORT1) == 0)
				st.rollAndGiveLimited(REPORT1, 1, 10, 1);
			else if(npcId == STAKATO && st.getQuestItemsCount(REPORT2) == 0)
				st.rollAndGiveLimited(REPORT2, 1, 10, 1);
			if(st.getQuestItemsCount(REPORT1) == 1 && st.getQuestItemsCount(REPORT2) == 1)
			{
				st.playSound(SOUND_MIDDLE);
				st.set("cond", "10");
				st.setState(STARTED);
			}
			else
				st.playSound(SOUND_ITEMGET);
		}
		if(cond == 12 && npcId == DIVINE && Rnd.chance(15))
		{
			L2NpcInstance isQuest = L2ObjectsStorage.getByNpcId(HARKILGAMED);
			if(isQuest == null)
			{
				st.getPcSpawn().addSpawn(HARKILGAMED);
				st.playSound(SOUND_MIDDLE);
				st.startQuestTimer("HARKILGAMED_Fail", 120000);
			}
			else
			{
				if(st.getQuestTimer("HARKILGAMED_Fail") == null)
					st.startQuestTimer("HARKILGAMED_Fail", 120000);
			}
		}
	}
}
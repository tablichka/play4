package quests._409_PathToOracle;

import ru.l2gw.gameserver.model.instances.L2NpcInstance;
import ru.l2gw.gameserver.model.quest.Quest;
import ru.l2gw.gameserver.model.quest.QuestState;

public class _409_PathToOracle extends Quest
{
	//npc
	public final int MANUEL = 30293;
	public final int ALLANA = 30424;
	public final int PERRIN = 30428;
	//mobs
	public final int LIZARDMAN_WARRIOR = 27032;
	public final int LIZARDMAN_SCOUT = 27033;
	public final int LIZARDMAN = 27034;
	public final int TAMIL = 27035;
	//items
	public final int CRYSTAL_MEDALLION_ID = 1231;
	public final int MONEY_OF_SWINDLER_ID = 1232;
	public final int DAIRY_OF_ALLANA_ID = 1233;
	public final int LIZARD_CAPTAIN_ORDER_ID = 1234;
	public final int LEAF_OF_ORACLE_ID = 1235;
	public final int HALF_OF_DAIRY_ID = 1236;
	public final int TAMATOS_NECKLACE_ID = 1275;

	public _409_PathToOracle()
	{
		super(409, "_409_PathToOracle", "Path to Oracle");

		addStartNpc(MANUEL);

		addTalkId(MANUEL);
		addTalkId(ALLANA);
		addTalkId(PERRIN);

		addKillId(LIZARDMAN_WARRIOR);
		addKillId(LIZARDMAN_SCOUT);
		addKillId(LIZARDMAN);
		addKillId(TAMIL);

		addQuestItem(MONEY_OF_SWINDLER_ID,
				DAIRY_OF_ALLANA_ID,
				LIZARD_CAPTAIN_ORDER_ID,
				CRYSTAL_MEDALLION_ID,
				HALF_OF_DAIRY_ID,
				TAMATOS_NECKLACE_ID);
	}

	@Override
	public String onEvent(String event, QuestState st)
	{
		String htmltext = event;
		if(event.equalsIgnoreCase("1"))
		{
			if(st.getPlayer().getClassId().getId() != 0x19)
			{
				if(st.getPlayer().getClassId().getId() == 0x1d)
					htmltext = "30293-02a.htm";
				else
					htmltext = "30293-02.htm";
				st.exitCurrentQuest(true);
			}
			else if(st.getPlayer().getLevel() < 18)
			{
				htmltext = "30293-03.htm";
				st.exitCurrentQuest(true);
			}
			else if(st.getQuestItemsCount(LEAF_OF_ORACLE_ID) > 0)
			{
				htmltext = "30293-04.htm";
				st.exitCurrentQuest(true);
			}
			else
			{
				st.set("cond", "1");
				st.setState(STARTED);
				st.playSound(SOUND_ACCEPT);
				st.giveItems(CRYSTAL_MEDALLION_ID, 1);
				htmltext = "30293-05.htm";
			}
		}
		else if(event.equalsIgnoreCase("30424-08.htm"))
		{
			st.getPcSpawn().addSpawn(LIZARDMAN_WARRIOR);
			st.getPcSpawn().addSpawn(LIZARDMAN_SCOUT);
			st.getPcSpawn().addSpawn(LIZARDMAN);
			st.set("cond", "2");
		}
		else if(event.equalsIgnoreCase("30424_1"))
			htmltext = "";
		else if(event.equalsIgnoreCase("30428_1"))
			htmltext = "30428-02.htm";
		else if(event.equalsIgnoreCase("30428_2"))
			htmltext = "30428-03.htm";
		else if(event.equalsIgnoreCase("30428_3"))
			st.getPcSpawn().addSpawn(TAMIL);
		return htmltext;
	}

	@Override
	public String onTalk(L2NpcInstance npc, QuestState st)
	{
		String htmltext = "noquest";
		int npcId = npc.getNpcId();
		int cond = st.getInt("cond");
		if(npcId == MANUEL)
		{
			if(cond < 1)
				htmltext = "30293-01.htm";
			else if(st.getQuestItemsCount(CRYSTAL_MEDALLION_ID) > 0)
				if(st.getQuestItemsCount(MONEY_OF_SWINDLER_ID) < 1 && st.getQuestItemsCount(DAIRY_OF_ALLANA_ID) < 1 && st.getQuestItemsCount(LIZARD_CAPTAIN_ORDER_ID) < 1 && st.getQuestItemsCount(HALF_OF_DAIRY_ID) < 1)
					htmltext = "30293-09.htm";
				else if(st.getQuestItemsCount(MONEY_OF_SWINDLER_ID) > 0 && st.getQuestItemsCount(DAIRY_OF_ALLANA_ID) > 0 && st.getQuestItemsCount(LIZARD_CAPTAIN_ORDER_ID) > 0 && st.getQuestItemsCount(HALF_OF_DAIRY_ID) < 1)
				{
					htmltext = "30293-08.htm";
					st.takeItems(MONEY_OF_SWINDLER_ID, 1);
					st.takeItems(DAIRY_OF_ALLANA_ID, -1);
					st.takeItems(LIZARD_CAPTAIN_ORDER_ID, -1);
					st.takeItems(CRYSTAL_MEDALLION_ID, -1);
					if(st.getPlayer().getClassId().getLevel() == 1)
					{
						st.giveItems(LEAF_OF_ORACLE_ID, 1);
						if(!st.getPlayer().getVarB("prof1"))
						{
							st.getPlayer().setVar("prof1", "1");
							if(st.getPlayer().getLevel() >= 20)
								st.addExpAndSp(320534, 20392);
							else if(st.getPlayer().getLevel() == 19)
								st.addExpAndSp(456128, 27090);
							else
								st.addExpAndSp(591724, 33788);
							st.rollAndGive(57, 163800, 100);
						}
					}
					st.playSound(SOUND_FINISH);
					st.showSocial(3);
					st.exitCurrentQuest(true);
				}
				else
					htmltext = "30293-07.htm";
		}
		else if(npcId == ALLANA)
		{
			if(st.getQuestItemsCount(CRYSTAL_MEDALLION_ID) > 0)
				if(st.getQuestItemsCount(MONEY_OF_SWINDLER_ID) < 1 && st.getQuestItemsCount(DAIRY_OF_ALLANA_ID) < 1 && st.getQuestItemsCount(LIZARD_CAPTAIN_ORDER_ID) < 1 && st.getQuestItemsCount(HALF_OF_DAIRY_ID) < 1)
				{
					if(cond > 2)
						htmltext = "30424-05.htm";
					else
						htmltext = "30424-01.htm";
				}
				else if(st.getQuestItemsCount(MONEY_OF_SWINDLER_ID) < 1 && st.getQuestItemsCount(DAIRY_OF_ALLANA_ID) < 1 && st.getQuestItemsCount(LIZARD_CAPTAIN_ORDER_ID) > 0 && st.getQuestItemsCount(HALF_OF_DAIRY_ID) < 1)
				{
					htmltext = "30424-02.htm";
					st.giveItems(HALF_OF_DAIRY_ID, 1);
					st.set("cond", "4");
				}
				else if(st.getQuestItemsCount(MONEY_OF_SWINDLER_ID) < 1 && st.getQuestItemsCount(DAIRY_OF_ALLANA_ID) < 1 && st.getQuestItemsCount(LIZARD_CAPTAIN_ORDER_ID) > 0 && st.getQuestItemsCount(HALF_OF_DAIRY_ID) > 0)
				{
					if(st.getQuestItemsCount(TAMATOS_NECKLACE_ID) < 1)
						htmltext = "30424-06.htm";
					else
						htmltext = "30424-03.htm";
				}
				else if(st.getQuestItemsCount(MONEY_OF_SWINDLER_ID) > 0 && st.getQuestItemsCount(DAIRY_OF_ALLANA_ID) < 1 && st.getQuestItemsCount(LIZARD_CAPTAIN_ORDER_ID) > 0 && st.getQuestItemsCount(HALF_OF_DAIRY_ID) > 0)
				{
					htmltext = "30424-04.htm";
					st.takeItems(HALF_OF_DAIRY_ID, -1);
					st.giveItems(DAIRY_OF_ALLANA_ID, 1);
					st.set("cond", "7");
				}
				else if(st.getQuestItemsCount(MONEY_OF_SWINDLER_ID) > 0 && st.getQuestItemsCount(LIZARD_CAPTAIN_ORDER_ID) > 0 && st.getQuestItemsCount(HALF_OF_DAIRY_ID) < 1 && st.getQuestItemsCount(DAIRY_OF_ALLANA_ID) > 0)
					htmltext = "30424-05.htm";
		}
		else if(npcId == PERRIN)
			if(st.getQuestItemsCount(CRYSTAL_MEDALLION_ID) > 0 && st.getQuestItemsCount(LIZARD_CAPTAIN_ORDER_ID) > 0)
				if(st.getQuestItemsCount(TAMATOS_NECKLACE_ID) > 0)
				{
					htmltext = "30428-04.htm";
					st.takeItems(TAMATOS_NECKLACE_ID, -1);
					st.giveItems(MONEY_OF_SWINDLER_ID, 1);
					st.set("cond", "6");
				}
				else if(st.getQuestItemsCount(MONEY_OF_SWINDLER_ID) > 0)
					htmltext = "30428-05.htm";
				else if(cond > 4)
					htmltext = "30428-06.htm";
				else
					htmltext = "30428-01.htm";
		return htmltext;
	}

	@Override
	public void onKill(L2NpcInstance npc, QuestState st)
	{
		int npcId = npc.getNpcId();
		int cond = st.getInt("cond");
		if(npcId == LIZARDMAN_WARRIOR || npcId == LIZARDMAN_SCOUT || npcId == LIZARDMAN)
		{
			if(cond == 2 && st.getQuestItemsCount(LIZARD_CAPTAIN_ORDER_ID) < 1)
			{
				st.giveItems(LIZARD_CAPTAIN_ORDER_ID, 1);
				st.playSound(SOUND_MIDDLE);
				st.set("cond", "3");
				st.setState(STARTED);
			}
		}
		else if(npcId == TAMIL)
			if(cond == 4 && st.getQuestItemsCount(TAMATOS_NECKLACE_ID) < 1)
			{
				st.giveItems(TAMATOS_NECKLACE_ID, 1);
				st.playSound(SOUND_MIDDLE);
				st.set("cond", "5");
				st.setState(STARTED);
			}
	}
}
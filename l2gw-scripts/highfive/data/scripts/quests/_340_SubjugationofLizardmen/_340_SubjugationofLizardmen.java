package quests._340_SubjugationofLizardmen;

import ru.l2gw.gameserver.model.L2ObjectsStorage;
import ru.l2gw.gameserver.model.instances.L2NpcInstance;
import ru.l2gw.gameserver.model.quest.Quest;
import ru.l2gw.gameserver.model.quest.QuestState;

public class _340_SubjugationofLizardmen extends Quest
{
	// NPCs
	private static int WEITSZ = 30385;
	private static int LEVIAN = 30037;
	private static int ADONIUS = 30375;
	private static int CHEST_OF_BIFRONS = 30989;
	// Mobs
	private static int FELIM_LIZARDMAN = 20008;
	private static int FELIM_LIZARDMAN_SCOUT = 20010;
	private static int FELIM_LIZARDMAN_WARRIOR = 20014;
	private static int LANGK_LIZARDMAN_SHAMAN = 21101;
	private static int LANGK_LIZARDMAN_LEADER = 20356;
	private static int LANGK_LIZARDMAN_SENTINEL = 21100;
	private static int LANGK_LIZARDMAN_LIEUTENANT = 20357;
	private static int SERPENT_DEMON_BIFRONS = 25146;
	// Items
	private static int ADENA = 57;
	// Quest Items (Drop)
	private static short TRADE_CARGO = 4255;
	private static short HOLY_SYMBOL = 4256;
	private static short ROSARY = 4257;
	private static short SINISTER_TOTEM = 4258;

	public _340_SubjugationofLizardmen()
	{
		super(340, "_340_SubjugationofLizardmen", "Subjugation of Lizardmen");
		addStartNpc(WEITSZ);
		addTalkId(LEVIAN);
		addTalkId(ADONIUS);
		addTalkId(CHEST_OF_BIFRONS);

		addKillId(SERPENT_DEMON_BIFRONS);
		addKillId(FELIM_LIZARDMAN, FELIM_LIZARDMAN_SCOUT, FELIM_LIZARDMAN_WARRIOR, LANGK_LIZARDMAN_SHAMAN,
				LANGK_LIZARDMAN_LEADER, LANGK_LIZARDMAN_SENTINEL, LANGK_LIZARDMAN_LIEUTENANT);

		addQuestItem(TRADE_CARGO, HOLY_SYMBOL, ROSARY, SINISTER_TOTEM);
	}

	@Override
	public String onEvent(String event, QuestState st)
	{
		if(st.isCompleted())
			return "completed";
		int cond = st.getInt("cond");
		if(event.equalsIgnoreCase("30385-4.htm"))
		{
			st.set("cond", "1");
			st.setState(STARTED);
			st.playSound(SOUND_ACCEPT);
		}
		else if(event.equalsIgnoreCase("30385-6.htm") && st.getQuestItemsCount(TRADE_CARGO) > 29)
		{
			st.set("cond", "2");
			st.setState(STARTED);
			st.takeItems(TRADE_CARGO, -1);
		}
		else if(event.equalsIgnoreCase("30375-2.htm") && cond == 2)
		{
			st.set("cond", "3");
			st.setState(STARTED);
		}
		else if(event.equalsIgnoreCase("30989-2.htm") && cond == 5)
		{
			st.set("cond", "6");
			st.setState(STARTED);
			st.giveItems(SINISTER_TOTEM, 1);
			st.playSound(SOUND_MIDDLE);
		}
		else if(event.equalsIgnoreCase("30037-4.htm") && cond == 6 && st.getQuestItemsCount(SINISTER_TOTEM) > 0)
		{
			st.set("cond", "7");
			st.setState(STARTED);
			st.takeItems(SINISTER_TOTEM, -1);
		}
		else if(event.equalsIgnoreCase("30385-10.htm") && cond == 7)
		{
			st.getPcSpawn().removeAllSpawn();
			st.rollAndGive(ADENA, 14700, 100);
			st.playSound(SOUND_FINISH);
			st.exitCurrentQuest(false);
		}
		else if(event.equalsIgnoreCase("30385-7.htm") && cond == 1 && st.getQuestItemsCount(TRADE_CARGO) > 29)
		{
			st.rollAndGive(ADENA, 4090, 100);
			st.playSound(SOUND_FINISH);
			st.exitCurrentQuest(false);
		}
		else if(event.equalsIgnoreCase("CHEST_OF_BIFRONS_Fail"))
		{
			L2NpcInstance chest_is_spawned = L2ObjectsStorage.getByNpcId(CHEST_OF_BIFRONS);
			if(chest_is_spawned != null)
				chest_is_spawned.deleteMe();
			return null;
		}
		return event;
	}

	@Override
	public String onTalk(L2NpcInstance npc, QuestState st)
	{
		if(st.isCompleted())
			return "completed";
		String htmltext = "noquest";
		int cond = st.getInt("cond");
		int npcId = npc.getNpcId();
		if(npcId == WEITSZ)
		{
			if(st.isCreated())
			{
				if(st.getPlayer().getLevel() < 17)
				{
					htmltext = "30385-1.htm";
					st.exitCurrentQuest(true);
				}
				else
					htmltext = "30385-2.htm";
			}
			else if(st.isStarted())
			{
				if(cond == 1)
					htmltext = st.getQuestItemsCount(TRADE_CARGO) < 30 ? "30385-8.htm" : "30385-5.htm";
				if(cond == 2)
					htmltext = "30385-11.htm";
				if(cond == 7)
					htmltext = "30385-9.htm";
			}
		}
		else if(npcId == ADONIUS)
		{
			if(cond == 2)
				htmltext = "30375-1.htm";
			if(cond == 3)
			{
				if(st.getQuestItemsCount(ROSARY) < 1 || st.getQuestItemsCount(HOLY_SYMBOL) < 1)
					htmltext = "30375-4.htm";
				else
				{
					st.takeItems(ROSARY, -1);
					st.takeItems(HOLY_SYMBOL, -1);
					st.set("cond", "4");
					st.setState(STARTED);
					htmltext = "30375-3.htm";
				}
			}
			if(cond == 4)
				htmltext = "30375-5.htm";
		}
		else if(npcId == LEVIAN)
		{
			if(cond == 4)
			{
				st.set("cond", "5");
				st.setState(STARTED);
				htmltext = "30037-1.htm";
			}
			if(cond == 5)
				htmltext = "30037-2.htm";
			if(cond == 6 && st.getQuestItemsCount(SINISTER_TOTEM) > 0)
				htmltext = "30037-3.htm";
			if(cond == 7)
				htmltext = "30037-5.htm";
		}
		if(npcId == CHEST_OF_BIFRONS && cond == 5)
			htmltext = "30989-1.htm";

		return htmltext;
	}

	@Override
	public void onKill(L2NpcInstance npc, QuestState st)
	{
		int npcId = npc.getNpcId();
		int cond = st.getInt("cond");
		if(npcId == SERPENT_DEMON_BIFRONS && cond == 5)
		{
			L2NpcInstance chest_is_spawned = L2ObjectsStorage.getByNpcId(CHEST_OF_BIFRONS);
			if(chest_is_spawned == null)
			{
				st.getPcSpawn().addSpawn(CHEST_OF_BIFRONS);
				st.playSound(SOUND_MIDDLE);
				st.startQuestTimer("CHEST_OF_BIFRONS_Fail", 120000);
			}
		}

		if(st.getCond() == 1)
		{
			if(npcId == FELIM_LIZARDMAN && st.rollAndGiveLimited(TRADE_CARGO, 1, 30, 30))
				st.playSound(st.getQuestItemsCount(TRADE_CARGO) == 30 ? SOUND_MIDDLE : SOUND_ITEMGET);
			else if(npcId == FELIM_LIZARDMAN_SCOUT && st.rollAndGiveLimited(TRADE_CARGO, 1, 33, 30))
				st.playSound(st.getQuestItemsCount(TRADE_CARGO) == 30 ? SOUND_MIDDLE : SOUND_ITEMGET);
			else if(npcId == FELIM_LIZARDMAN_WARRIOR && st.rollAndGiveLimited(TRADE_CARGO, 1, 36, 30))
				st.playSound(st.getQuestItemsCount(TRADE_CARGO) == 30 ? SOUND_MIDDLE : SOUND_ITEMGET);
		}
		else if(st.getCond() == 3)
		{
			if((npcId == LANGK_LIZARDMAN_SHAMAN || npcId == LANGK_LIZARDMAN_LEADER || npcId == LANGK_LIZARDMAN_SENTINEL || npcId == LANGK_LIZARDMAN_LIEUTENANT)
					&& (st.rollAndGiveLimited(HOLY_SYMBOL, 1, 12, 1) || st.rollAndGiveLimited(ROSARY, 1, 12, 1)))
				st.playSound(st.getQuestItemsCount(HOLY_SYMBOL) + st.getQuestItemsCount(ROSARY) == 2 ? SOUND_MIDDLE : SOUND_ITEMGET);

		}
	}
}
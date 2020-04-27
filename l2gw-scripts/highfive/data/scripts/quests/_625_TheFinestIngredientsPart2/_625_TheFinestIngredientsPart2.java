package quests._625_TheFinestIngredientsPart2;

import ru.l2gw.commons.math.Rnd;
import ru.l2gw.extensions.scripts.Functions;
import ru.l2gw.gameserver.clientpackets.Say2C;
import ru.l2gw.gameserver.model.L2ObjectsStorage;
import ru.l2gw.gameserver.model.L2Player;
import ru.l2gw.gameserver.model.L2Spawn;
import ru.l2gw.gameserver.model.instances.L2NpcInstance;
import ru.l2gw.gameserver.model.quest.Quest;
import ru.l2gw.gameserver.model.quest.QuestState;
import ru.l2gw.gameserver.tables.NpcTable;
import ru.l2gw.gameserver.tables.SpawnTable;
import ru.l2gw.gameserver.templates.L2NpcTemplate;
import ru.l2gw.util.Location;

import java.util.Date;

public class _625_TheFinestIngredientsPart2 extends Quest
{
	// NPCs
	private static int Jeremy = 31521;
	private static int Yetis_Table = 31542;
	private static Location tableLoc = new Location(157136, -121456, -2363, 40000);
	private static Location yetiLoc = new Location(158240, -121536, -2253, 58000);
	private static L2Spawn tableSpawn;
	private static L2Spawn yetiSpawn;
	// Mobs
	private static int RB_Icicle_Emperor_Bumbalump = 25296;
	// Items
	private static short Soy_Sauce_Jar = 7205;
	private static short Food_for_Bumbalump = 7209;
	private static short Special_Yeti_Meat = 7210;
	private static short Reward_First = 4589;
	private static short Reward_Last = 4594;

	public void onLoad()
	{
		String st = loadGlobalQuestVar("625_SpawnTime");
		long spawnTime = 0;
		if(st != null && !st.isEmpty())
			try
			{
				spawnTime = Long.parseLong(st);
			}
			catch(NumberFormatException e)
			{
			}

		try
		{
			L2NpcTemplate template = NpcTable.getTemplate(Yetis_Table);
			tableSpawn = new L2Spawn(template);
			tableSpawn.setAmount(1);
			tableSpawn.setRespawnDelay(60);
			tableSpawn.setLoc(tableLoc);
			tableSpawn.stopRespawn();
			SpawnTable.getInstance().addNewSpawn(tableSpawn, false, null);

			template = NpcTable.getTemplate(RB_Icicle_Emperor_Bumbalump);
			yetiSpawn = new L2Spawn(template);
			yetiSpawn.setAmount(1);
			yetiSpawn.setLoc(yetiLoc);
			yetiSpawn.stopRespawn();
		}
		catch(Exception e)
		{
			_log.warn(this + " cannot spawn table!");
			e.printStackTrace();
		}

		if(spawnTime < System.currentTimeMillis())
		{
			_log.info(this + " (spawn table)");
			tableSpawn.startRespawn();
			tableSpawn.init();
		}
		else
		{
			_log.info(this + " (schedule spawn table at " + new Date(spawnTime) + ")");
			startQuestTimer("spawn_table", spawnTime - System.currentTimeMillis(), null, null, true);
		}
	}

	public _625_TheFinestIngredientsPart2()
	{
		super(625, "_625_TheFinestIngredientsPart2", "The Finest Ingredients - Part 2"); // Party true
		addStartNpc(Jeremy);
		addTalkId(Yetis_Table);
		addKillId(RB_Icicle_Emperor_Bumbalump);
	}

	@Override
	public String onEvent(String event, L2NpcInstance npc, L2Player player)
	{
		if(event.equalsIgnoreCase("spawn_table"))
		{
			_log.info("Quest: 625: The Finest Ingredients - Part 2: spawn_table event.");
			tableSpawn.startRespawn();
			tableSpawn.init();
		}
		else if(event.equalsIgnoreCase("YETI_Fail"))
		{
			_log.info("Quest: 625: The Finest Ingredients - Part 2: Quest Timer YETI_Fail occured");
			if(yetiSpawn.getLastSpawn() != null && !yetiSpawn.getLastSpawn().isDead())
			{
				_log.info("Quest: 625: The Finest Ingredients - Part 2: Quest Timer Despawning Raid Boss and spawning Table back.");
				yetiSpawn.stopRespawn();
				yetiSpawn.despawnAll();
				tableSpawn.startRespawn();
				tableSpawn.init();
			}
		}
		return null;
	}

	@Override
	public String onEvent(String event, QuestState st)
	{
		String htmltext = event;
		L2NpcInstance isQuest = L2ObjectsStorage.getByNpcId(RB_Icicle_Emperor_Bumbalump);

		if(event.equalsIgnoreCase("31521-02.htm") && st.isCreated())
		{
			if(st.getQuestItemsCount(Soy_Sauce_Jar) == 0)
			{
				st.exitCurrentQuest(true);
				return "31521-00a.htm";
			}
			st.setState(STARTED);
			st.set("cond", "1");
			st.takeItems(Soy_Sauce_Jar, 1);
			st.giveItems(Food_for_Bumbalump, 1);
			st.playSound(SOUND_ACCEPT);
		}
		else if(event.equalsIgnoreCase("31521-04.htm") && st.isStarted() && st.getInt("cond") == 3)
		{
			st.exitCurrentQuest(true);
			if(st.getQuestItemsCount(Special_Yeti_Meat) == 0)
				return "31521-05.htm";
			st.takeItems(Special_Yeti_Meat, 1);
			st.rollAndGive(Rnd.get(Reward_First, Reward_Last), 5, 100);
		}
		else if(event.equalsIgnoreCase("31542-02.htm"))
		{

			if(st.getQuestItemsCount(Food_for_Bumbalump) > 0)
			{
				if(isQuest != null)
				{
					htmltext = "31542-03.htm";
					if(isQuest.isDead())
					{
						isQuest.decayMe();
						isQuest.deleteMe();
						isQuest = null;
					}
				}
				else
				{
					st.takeItems(Food_for_Bumbalump, -1);
					_log.info("Quest: 625: The Finest Ingredients - Part 2: spawning Yeti Raid Boss and starting Quest Timer YETI_Fail");
					yetiSpawn.spawnOne();
					isQuest = yetiSpawn.getLastSpawn();
					Functions.npcSay(isQuest, Say2C.ALL, "May the gods forever condemn you! Your power weakens!");
					st.playSound("ItemSound.quest_middle");
					st.set("cond", "2");
					cancelQuestTimer("YETI_Fail", null, null);
					startQuestTimer("YETI_Fail", 1200000, null, null, true);
					tableSpawn.stopRespawn();
					tableSpawn.despawnAll();
					htmltext = "31542-02.htm";
				}
			}
			else
				htmltext = "31542-04.htm";
		}
		return htmltext;
	}

	@Override
	public String onTalk(L2NpcInstance npc, QuestState st)
	{
		L2NpcInstance isQuest = L2ObjectsStorage.getByNpcId(RB_Icicle_Emperor_Bumbalump);
		String htmltext = "noquest";

		int npcId = npc.getNpcId();
		if(st.isCreated())
		{
			if(npcId != Jeremy)
				return "noquest";
			if(st.getPlayer().getLevel() < 73)
			{
				st.exitCurrentQuest(true);
				return "31683-00b.htm";
			}
			if(st.getQuestItemsCount(Soy_Sauce_Jar) == 0)
			{
				st.exitCurrentQuest(true);
				return "31521-00a.htm";
			}
			st.set("cond", "0");
			return "31521-01.htm";
		}

		if(!st.isStarted())
			return "noquest";
		int cond = st.getInt("cond");

		if(npcId == Jeremy)
		{
			if(cond == 1)
				return "31521-02a.htm";
			if(cond == 2)
				return "31521-03a.htm";
			if(cond == 3 && st.getQuestItemsCount(Special_Yeti_Meat) > 0)
				return "31521-03.htm";
			else
				return "31521-03a.htm";
		}

		if(npcId == Yetis_Table)
		{
			if(cond == 1)
				return "31542-01.htm";
			if(cond == 2)
			{
				if(isQuest != null)
				{
					htmltext = "31542-03.htm";
					if(isQuest.isDead())
					{
						isQuest.decayMe();
						isQuest.deleteMe();
						isQuest = null;
					}
				}
				else
					htmltext = "31542-01.htm";
			}
		}
		return htmltext;
	}

	@Override
	public void onKill(L2NpcInstance npc, L2Player killer)
	{
		int npcId = npc.getNpcId();

		if(npcId == RB_Icicle_Emperor_Bumbalump)
		{
			cancelQuestTimer("YETI_Fail", null, null);
			long spawnTime = System.currentTimeMillis() + 43200000 + Rnd.get(86400000);
			saveGlobalQuestVar("625_SpawnTime", String.valueOf(spawnTime));
			startQuestTimer("spawn_table", spawnTime - System.currentTimeMillis(), null, null, true);
			_log.info("Quest: 625: The Finest Ingredients - Part 2: next table spawn at " + new Date(spawnTime));

			for(QuestState st : getPartyMembersWithQuest(killer, 2))
			{
				if(st.getQuestItemsCount(Special_Yeti_Meat) == 0)
					st.giveItems(Special_Yeti_Meat, 1);
				st.set("cond", "3");
				st.playSound(SOUND_MIDDLE);
				st.setState(STARTED);
			}

			for(QuestState st : getPartyMembersWithQuest(killer, 1))
			{
				if(st.getQuestItemsCount(Special_Yeti_Meat) == 0)
					st.giveItems(Special_Yeti_Meat, 1);
				st.set("cond", "3");
				st.playSound(SOUND_MIDDLE);
				st.setState(STARTED);
			}
		}
	}
}
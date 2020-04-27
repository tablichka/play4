package quests._311_ExpulsionOfEvilSpirits;

import ru.l2gw.commons.math.Rnd;
import ru.l2gw.gameserver.model.L2Player;
import ru.l2gw.gameserver.model.L2Spawn;
import ru.l2gw.gameserver.model.instances.L2NpcInstance;
import ru.l2gw.gameserver.model.quest.Quest;
import ru.l2gw.gameserver.model.quest.QuestState;
import ru.l2gw.gameserver.tables.NpcTable;
import ru.l2gw.gameserver.templates.L2NpcTemplate;
import ru.l2gw.util.Location;

import java.util.Date;

/**
 * @author: rage
 * @date: 15.08.2010 15:54:16
 */
public class _311_ExpulsionOfEvilSpirits extends Quest
{
	// NPCs
	private static final int CHAIREN = 32655;
	private static final int ALTAR_ID = 18811;

	private static final int SOUL_CORE = 14881;
	private static final int SOUL_PENDANT = 14848;
	private static final int RAGNA_ORCS_AMULET = 14882;

	private static final int DROP_CHANCE = 20;

	private static final int[] MOBS = {22691, 22692, 22693, 22694, 22695, 22696, 22697, 22698, 22699, 22700, 22701, 22702};
	private long _altarSpawnTime = 0;
	private L2Spawn _altarSpawn;

	public void onLoad()
	{
		String st = loadGlobalQuestVar("311_SpawnTime");
		_altarSpawnTime = 0;
		if(st != null && !st.isEmpty())
			try
			{
				_altarSpawnTime = Long.parseLong(st);
			}
			catch(NumberFormatException e)
			{
			}

		try
		{
			L2NpcTemplate template = NpcTable.getTemplate(ALTAR_ID);
			_altarSpawn = new L2Spawn(template);
			_altarSpawn.setAmount(1);
			_altarSpawn.setRespawnDelay(6000);
			_altarSpawn.setLoc(new Location(74120, -101920, -960, 32760));
			_altarSpawn.stopRespawn();
		}
		catch(Exception e)
		{
			_log.warn(this + " cannot spawn altar!");
			e.printStackTrace();
		}

		if(_altarSpawnTime < System.currentTimeMillis())
		{
			_log.warn(this + " spawn altar!");
			_altarSpawn.startRespawn();
			_altarSpawn.init();
		}
		else
		{
			_log.warn(this + " (schedule spawn altar at " + new Date(_altarSpawnTime) + ")");
			startQuestTimer("spawn_altar", _altarSpawnTime - System.currentTimeMillis(), null, null, true);
		}
	}

	public _311_ExpulsionOfEvilSpirits()
	{
		super(311, "_311_ExpulsionOfEvilSpirits", "Expulsion Of Evil Spirits"); // party = true
		addStartNpc(CHAIREN);
		addTalkId(CHAIREN);
		addKillId(MOBS);
		addKillId(ALTAR_ID);

		addQuestItem(SOUL_CORE);
		addQuestItem(RAGNA_ORCS_AMULET);
	}

	@Override
	public String onEvent(String event, L2NpcInstance npc, L2Player player)
	{
		if(event.equalsIgnoreCase("spawn_altar"))
		{
			System.out.println("Loaded Quest: " + getQuestIntId() + ": " + getDescr() + " spawn_alter event.");
			_altarSpawn.startRespawn();
			_altarSpawn.init();
		}
		return null;
	}

	@Override
	public String onEvent(String event, QuestState st)
	{
		String htmltext = event;
		if(event.equals("32655-yes.htm"))
		{
			st.set("cond", 1);
			st.setState(STARTED);
			st.playSound(SOUND_ACCEPT);
		}
		else if(event.matches("^\\d+$"))
		{
			long f = st.getQuestItemsCount(RAGNA_ORCS_AMULET);
			int evt = Integer.parseInt(event);
			if(evt == 9482 && f >= 488)						// Recipe: Sealed Dynasty Breast Plate (60%)
				htmltext = onExchangeRequest(evt, st, 488);
			else if(evt == 9483 && f >= 305)						// Recipe: Sealed Dynasty Gaiter (60%)
				htmltext = onExchangeRequest(evt, st, 305);
			else if(evt == 9484 && f >= 183)						// Recipe: Sealed Dynasty Helmet (60%)
				htmltext = onExchangeRequest(evt, st, 183);
			else if(evt == 9485 && f >= 122)						// Recipe: Sealed Dynasty Gauntlet (60%)
				htmltext = onExchangeRequest(evt, st, 122);
			else if(evt == 9486 && f >= 122)						// Recipe: Sealed Dynasty Boots (60%)
				htmltext = onExchangeRequest(evt, st, 122);
			else if(evt == 9487 && f >= 366)						// Recipe: Sealed Dynasty Leather Armor (60%)
				htmltext = onExchangeRequest(evt, st, 366);
			else if(evt == 9488 && f >= 229)						// Recipe: Sealed Dynasty Leather Leggings (60%)
				htmltext = onExchangeRequest(evt, st, 229);
			else if(evt == 9489 && f >= 183)						// Recipe: Sealed Dynasty Leather Helmet (60%)
				htmltext = onExchangeRequest(evt, st, 183);
			else if(evt == 9490 && f >= 122)						// Recipe: Sealed Dynasty Leather Gloves (60%)
				htmltext = onExchangeRequest(evt, st, 122);
			else if(evt == 9491 && f >= 122)						// Recipe: Sealed Dynasty Leather Boots (60%)
				htmltext = onExchangeRequest(evt, st, 122);
			else if(evt == 9497 && f >= 129)						// Recipe: Sealed Dynasty Shield (60%)
				htmltext = onExchangeRequest(evt, st, 129);
			else if(evt == 9625 && f >= 667)						// Giant's Codex - Oblivion
				htmltext = onExchangeRequest(evt, st, 667);
			else if(evt == 9626 && f >= 1000)						// Giant's Codex - Discipline
				htmltext = onExchangeRequest(evt, st, 1000);
			else if(evt == 9628 && f >= 24)						// Leonard
				htmltext = onExchangeRequest(evt, st, 24);
			else if(evt == 9629 && f >= 43)						// Leonard
				htmltext = onExchangeRequest(evt, st, 43);
			else if(evt == 9630 && f >= 36)						// Adamantine
				htmltext = onExchangeRequest(evt, st, 36);
			else
				htmltext = "32655-13no.htm";
		}
		else if(event.equals("32655-14.htm"))
		{
			if(st.getQuestItemsCount(SOUL_CORE) >= 10)
			{
				st.takeItems(SOUL_CORE, 10);
				st.giveItems(SOUL_PENDANT, 1);
			}
			else
				htmltext = "32655-14no.htm";
		}
		else if(event.equals("32655-quit.htm"))
		{
			st.unset("cond");
			st.exitCurrentQuest(true);
			st.playSound(SOUND_FINISH);
		}
		return htmltext;
	}

	@Override
	public String onTalk(L2NpcInstance npc, QuestState st)
	{
		String htmltext = "noquest";
		int npcId = npc.getNpcId();
		int cond = st.getInt("cond");
		if(npcId == CHAIREN)
		{
			if(st.isCreated())
			{
				if(st.getPlayer().getLevel() >= 80)
					htmltext = "32655-01.htm";
				else
				{
					htmltext = "32655-lvl.htm";
					st.exitCurrentQuest(true);
				}
			}
			else if(st.isStarted())
			{
				if(st.getQuestItemsCount(SOUL_CORE) > 0 || st.getQuestItemsCount(RAGNA_ORCS_AMULET) > 0)
					htmltext = "32655-12.htm";
				else
					htmltext = "32655-10.htm";
			}
		}
		return htmltext;
	}

	@Override
	public void onKill(L2NpcInstance npc, L2Player killer)
	{
		if(npc.getNpcId() == ALTAR_ID)
		{
			_altarSpawnTime = System.currentTimeMillis() + 15 * 60000 + Rnd.get(15 * 60000);
			saveGlobalQuestVar("311_SpawnTime", String.valueOf(_altarSpawnTime));
			startQuestTimer("spawn_altar", _altarSpawnTime - System.currentTimeMillis(), null, null, true);
			_altarSpawn.stopRespawn();

			System.out.println("Quest: " + getQuestIntId() + ": " + getDescr() + " next altar spawn at " + new Date(_altarSpawnTime));
		}
		else
		{
			QuestState st = getRandomPartyMemberWithQuest(killer, 1);
			if(st != null)
			{
				if(st.rollAndGive(SOUL_CORE, 1, 1))
					st.playSound(SOUND_MIDDLE);

				if(st.rollAndGive(RAGNA_ORCS_AMULET, 1, DROP_CHANCE))
					st.playSound(SOUND_ITEMGET);
			}
		}
	}

	private String onExchangeRequest(int event, QuestState st, int qty)
	{
		st.giveItems(event, 1);
		st.takeItems(RAGNA_ORCS_AMULET, qty);
		st.playSound(SOUND_FINISH);
		return "32655-13ok.htm";
	}
}

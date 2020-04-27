package quests.Individual;

import ru.l2gw.gameserver.instancemanager.ResidenceManager;
import ru.l2gw.gameserver.model.L2Player;
import ru.l2gw.gameserver.model.L2Spawn;
import ru.l2gw.gameserver.model.instances.L2NpcInstance;
import ru.l2gw.gameserver.model.quest.Quest;
import ru.l2gw.gameserver.model.quest.QuestState;
import ru.l2gw.gameserver.tables.NpcTable;
import ru.l2gw.gameserver.templates.L2NpcTemplate;
import ru.l2gw.util.Location;

import java.util.Date;

public class Benom extends Quest
{
	private static final int BENOM = 29054;
	private static final int RUNE = 8;
	private static final int DUNGEON_GK = 35506;
	private static final int TELEPORT = 29055;
	private static boolean isBenomSpawned = false;
	private static boolean isBenomInTrone = false;
	private static L2Spawn benomSpawn;
	private static final Location benomLoc = new Location(11882, -49216, -3008, 43200);
	private static final Location dungeonLoc = new Location(12589, -49044, -2950);
	private static String prefix = "Benom-";

	public void onLoad()
	{
		long siegeDate = ResidenceManager.getInstance().getBuildingById(RUNE).getSiege().getSiegeDate().getTimeInMillis();
		long spawnTime = siegeDate - 86100000L;
		_log.info("Benom: spawn time: " + new Date(spawnTime));

		try
		{
			L2NpcTemplate template = NpcTable.getTemplate(BENOM);
			benomSpawn = new L2Spawn(template);
			benomSpawn.setAmount(1);
			benomSpawn.setLoc(benomLoc);
			benomSpawn.stopRespawn();
		}
		catch(Exception e)
		{
			_log.info("Benom: can't create benom spawn: " + e);
			e.printStackTrace();
		}

		if(spawnTime > System.currentTimeMillis())
		{
			_log.info("Benom: start spawn timer");
			startQuestTimer("benom_spawn", spawnTime - System.currentTimeMillis(), null, null, true);
		}
		else
		{
			long siegeEndTime = siegeDate + 2 * 60 * 60000;
			_log.info("Benom: spawn Benom");
			_log.info("Benom: despawn time: " + new Date(siegeEndTime));
			startQuestTimer("benom_despawn", siegeEndTime - System.currentTimeMillis() + 15000, null, null, true);
			benomSpawn.spawnOne();
			long tmp = siegeDate - System.currentTimeMillis() + 5 * 60000;
			if(tmp > 0)
			{
				_log.info("Benom: start benom task: " + (tmp / 60000) + " min.");
				startQuestTimer("benom_task", tmp, null, null, true);
			}
			else
			{
				_log.info("Benom: start benom task: 5 min.");
				startQuestTimer("benom_task", 5 * 60000, null, null, true);
			}

			isBenomSpawned = true;
		}
	}

	public Benom()
	{
		super(21003, "Benom", "Benom Individual", true);
		addStartNpc(DUNGEON_GK);
		addTalkId(DUNGEON_GK);
		addKillId(BENOM);
	}

	@Override
	public String onEvent(String event, L2NpcInstance npc, L2Player player)
	{
		if(event.equals("benom_spawn"))
		{
			_log.info("Benom: spawning benom");
			benomSpawn.spawnOne();
			isBenomSpawned = true;
			isBenomInTrone = false;
			long siegeEndTime = System.currentTimeMillis() + 2 * 60 * 60000 - 5 * 60000;
			_log.info("Benom: start despawn timer: " + new Date(siegeEndTime));
			startQuestTimer("benom_despawn", siegeEndTime - System.currentTimeMillis(), null, null, true);
			_log.info("Benom: Start check timer: 24 hour");
			startQuestTimer("benom_task", 24 * 60 * 60000 + 5 * 60000, null, null, true);
		}
		else if(event.equals("benom_task"))
		{
			_log.info("Benom: benom_task started");
			if(ResidenceManager.getInstance().getBuildingById(RUNE).getSiege().isInProgress())
			{
				if(benomSpawn.getLastSpawn() != null && !benomSpawn.getLastSpawn().isDead() && ResidenceManager.getInstance().getBuildingById(RUNE).getSiege().getKilledCtCount() > 1 && !isBenomInTrone)
				{
					_log.info("Benom: teleport benom to throne");
					benomSpawn.getLastSpawn().teleToLocation(11979, -49154, -530);
					benomSpawn.getLastSpawn().setAggroRange(1000);
					isBenomInTrone = true;
				}
				_log.info("Benom: reschedule benim_task");
				cancelQuestTimer("benom_task", null, null);
				startQuestTimer("benom_task", 5 * 60000, null, null, true);
			}
			else
			{
				_log.info("Benom: Rune siege is over, stop benom task, despawn Benom");
				benomSpawn.despawnAll();
				updateSpawnTime();
				cancelQuestTimer("benom_task", null, null);
			}
		}
		else if(event.equals("benom_despawn"))
		{
			_log.info("Benom: despawn task started");
			benomSpawn.despawnAll();
			if(!ResidenceManager.getInstance().getBuildingById(RUNE).getSiege().isInProgress())
				updateSpawnTime();
			else
			{
				_log.info("Benom: siege in progress start update task");
				startQuestTimer("benom_update", 15 * 60000, null, null, true);
			}
		}
		else if(event.equals("benom_update"))
			updateSpawnTime();

		return null;
	}

	@Override
	public String onTalk(L2NpcInstance npc, QuestState st)
	{
		if(npc.getNpcId() == DUNGEON_GK)
			// Let's check that player is in the castle owner clan
			if(st.getPlayer().getClan() != null && st.getPlayer().getClan().getHasCastle() == RUNE && !ResidenceManager.getInstance().getBuildingById(RUNE).getSiege().isInProgress())
			{
				//st.getPlayer().setVar("InstanceRP", st.getPlayer().getX() + "," + st.getPlayer().getY() + "," + st.getPlayer().getZ());
				st.getPlayer().setStablePoint(st.getPlayer().getLoc());
				st.getPlayer().teleToLocation(dungeonLoc);
				return null;
			}
			else
				return prefix + "clan.htm";
		return null;
	}

	@Override
	public void onKill(L2NpcInstance npc, L2Player killer)
	{
		cancelQuestTimer("benom_task", null, null);
		if(!isBenomInTrone)
			addSpawn(TELEPORT, new Location(12200, -49220, -3000), false, 900000);
	}

	private void updateSpawnTime()
	{
		_log.info("Benom: calculate spawn time");
		long siegeTime = ResidenceManager.getInstance().getBuildingById(RUNE).getSiege().getSiegeDate().getTimeInMillis();
		long spawnTime = siegeTime - 86100000L;
		_log.info("Benom AI: spawn time: " + new Date(spawnTime));
		isBenomSpawned = false;
		isBenomInTrone = false;
		startQuestTimer("benom_spawn", spawnTime - System.currentTimeMillis(), null, null, true);
	}
}

package quests.global;

import ru.l2gw.gameserver.Config;
import ru.l2gw.extensions.listeners.DayNightChangeListener;
import ru.l2gw.extensions.listeners.L2ZoneEnterLeaveListener;
import ru.l2gw.extensions.scripts.Functions;
import ru.l2gw.gameserver.controllers.GameTimeController;
import ru.l2gw.gameserver.controllers.ThreadPoolManager;
import ru.l2gw.gameserver.clientpackets.Say2C;
import ru.l2gw.gameserver.instancemanager.ServerVariables;
import ru.l2gw.gameserver.instancemanager.ZoneManager;
import ru.l2gw.gameserver.model.L2Character;
import ru.l2gw.gameserver.model.L2GroupSpawn;
import ru.l2gw.gameserver.model.L2Player;
import ru.l2gw.gameserver.model.L2Spawn;
import ru.l2gw.gameserver.model.instances.L2NpcInstance;
import ru.l2gw.gameserver.model.quest.Quest;
import ru.l2gw.gameserver.model.zone.L2Zone;
import ru.l2gw.gameserver.tables.DoorTable;
import ru.l2gw.gameserver.tables.SpawnTable;
import ru.l2gw.commons.arrays.GArray;
import ru.l2gw.commons.math.Rnd;

import java.util.concurrent.ScheduledFuture;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Hellbound extends Quest
{
	private static long hbPoints;
	private static int hbStage;
	private static long lastUpdate;
	private static boolean hellinarkKilled;
	private static final GArray<L2Spawn> _shadai = SpawnTable.getInstance().getEventSpawn("hb_shadai", null);

	public static int[] LIMIT = {
			0,
			300000,   // switch to 2nd stage
			600000,   // switch to 3d stage
			1000000,  // switch to 4th stage
			1010000,  // switch to 5th stage
			1400000,  // switch to 6th stage
			1900000,  // switch to 7th stage
			2500000,  // switch to 8th stage
			2600000,  // switch to 9th stage
			2700000,  // switch to 10th stage
			3000000,  // switch to 11th stage
			4000000   // ...
	};
	//Hellbound mobs
	private static final int[][][] HB_MOBS_LIST =
			{
					{
							{0, 0}
					},
					{	   // Stage 1
							{22321, 1},
							{22320, 1},
							{22324, 1},
							{22325, 1},
							{22327, 3},
							{22328, 3},
							{22329, 3},
							{22322, -10},
							{22323, -10}
					},
					{	   // Stage 2
							{18463, 5},
							{18464, 5},
							{22322, -10},
							{22323, -10}
					},
					{	   // Stage 3
							{22322, -10},
							{22323, -10},
							{22341, 100},
							{22343, 3},
							{22342, 3}
					},
					{	   // Stage 4
							{22323, -10},
							{22322, -10},
							{22448, -100},
							{18465, 10000}
					},
					{	   // Stage 5
							{22344, 3},
							{22345, 3},
							{22346, 5},
							{22448, -100}
					},
					{	   // Stage 6
							{22448, -100},
							{22326, 10000},
							{22327, 3},
							{22329, 3},
							{22328, 3},
							{22422, 3}
					},
					{	   // Stage 7
							{22448, -100},
							{22349, 5},
							{22350, 5},
							{22351, 5},
							{22352, 5},
							{22353, 100}
					},
					{	   // Stage 8
							{22448, -100},
							{18466, 100000}
					},
					{	   // Stage 9
							{22448, -100}
					},
					{	   // Stage 10
							{22448, -100},
							{22450, -100},
							{22362, 100},
							{22363, 1},
							{22364, 1},
							{22365, 1},
							{22366, 1},
							{22367, 1},
							{22368, 1},
							{22369, 1},
							{22370, 1},
							{22371, 1},
							{22372, 1}
					},
					{	   // Stage 11
							{22448, -100},
							{22450, -100},
							{22363, 1},
							{22364, 1},
							{22365, 1},
							{22366, 1},
							{22367, 1},
							{22368, 1},
							{22369, 1},
							{22370, 1},
							{22371, 1},
							{22372, 1},
							{22373, 1},
							{22374, 1},
							{22375, 1},
							{22376, 1},
							{22377, 1},
							{22378, 1},
							{22379, 1},
							{22380, 1},
							{22381, 1},
							{22382, 1},
							{22383, 1},
							{22384, 1},
							{22385, 1},
							{22386, 1},
							{22387, 1},
							{22388, 1},
							{22389, 1},
							{22390, 1},
					}
			};


	public void onLoad()
	{
		hbPoints = ServerVariables.getLong("hb_points", 0);
		hbStage = ServerVariables.getInt("hb_stage", 0);
		_log.info(this + " Hellbound points are " + hbPoints + " and stage " + hbStage + ".");

		hellinarkKilled = Boolean.parseBoolean(loadGlobalQuestVar("hellinarkKilled"));
		GameTimeController.getInstance().getListenerEngine().addPropertyChangeListener(new DayNightListener());

		for(int[][] mobs1 : HB_MOBS_LIST)
			for(int[] mobs : mobs1)
				addKillId(mobs[0]);

		spawnStage(hbStage);
		lastUpdate = System.currentTimeMillis() + 300000;
		for(int zoneId = 4; zoneId < 12; zoneId++)
		{
			L2Zone zone = ZoneManager.getInstance().getZoneById(L2Zone.ZoneType.dummy, zoneId);
			if(zone != null && zone.getExtAttributes() != null)
			{
				HBTrapZoneListener hbtrap = new HBTrapZoneListener();
				hbtrap.parseSpawnString(zone.getExtAttributes().getString("hb_spawn"));
				hbtrap.setDespawnTime(zone.getExtAttributes().getInteger("hb_spawn_time", 600));
				hbtrap.setZoneReuse(zone.getExtAttributes().getInteger("hb_cooldown_time", 1800));
				zone.getListenerEngine().addMethodInvokedListener(hbtrap);
			}
			else
				_log.warn(this + " no hb_ params for zone: " + zone);
		}
	}

	public void onReload()
	{
		onLoad();
	}

	public void onShutdown()
	{
		ServerVariables.set("hb_points", hbPoints);
	}

	public Hellbound()
	{
		super(25000, "Hellbound", "Hellbound Global Quest", true);
	}

	@Override
	public void onKill(L2NpcInstance npc, L2Player player)
	{
		int npcId = npc.getNpcId();
		//System.out.println("Hellbound mob ("+ npcId + ") killed. ");
		for(int[] mobs : HB_MOBS_LIST[hbStage])
			if(npcId == mobs[0])
			{
				addPoints(Math.round(mobs[1] * Config.RATE_HB_POINTS));
				if(npcId == 22326)
				{
					hellinarkKilled = true;
					saveGlobalQuestVar("hellinarkKilled", "true");
				}
				break;
			}
	}

	@Override
	public boolean isGlobal()
	{
		return true;
	}

	public static void spawnStage(int stage)
	{
		switch(stage)
		{
			case 0:
				SpawnTable.getInstance().startEventSpawn("warpgate_0");
				break;
			case 1:
				SpawnTable.getInstance().stopEventSpawn("warpgate_0", true);

				SpawnTable.getInstance().startEventSpawn("warpgate_1");
				SpawnTable.getInstance().startEventSpawn("harbor_area");
				SpawnTable.getInstance().startEventSpawn("green_spot");
				SpawnTable.getInstance().startEventSpawn("remnants");
				SpawnTable.getInstance().startEventSpawn("quarry");
				SpawnTable.getInstance().startEventSpawn("desert");
				SpawnTable.getInstance().startEventSpawn("hb_npcs_1");
				SpawnTable.getInstance().startEventSpawn("hb_dead");
				SpawnTable.getInstance().startEventSpawn("remnants_d");
				SpawnTable.getInstance().startEventSpawn("hb_natives_out");
				SpawnTable.getInstance().startEventSpawn("quarry_guards");
				break;
			case 2:
				SpawnTable.getInstance().stopEventSpawn("warpgate_0", true);

				SpawnTable.getInstance().startEventSpawn("warpgate_1");
				SpawnTable.getInstance().startEventSpawn("harbor_area");
				SpawnTable.getInstance().startEventSpawn("green_spot");
				SpawnTable.getInstance().startEventSpawn("remnants");
				SpawnTable.getInstance().startEventSpawn("quarry");
				SpawnTable.getInstance().startEventSpawn("desert");
				SpawnTable.getInstance().startEventSpawn("hb_npcs_1");
				SpawnTable.getInstance().startEventSpawn("hb_dead");
				SpawnTable.getInstance().startEventSpawn("remnants_d");
				SpawnTable.getInstance().startEventSpawn("hb_natives_out");
				SpawnTable.getInstance().startEventSpawn("quarry_guards");
				break;
			case 3:
				SpawnTable.getInstance().stopEventSpawn("warpgate_0", true);
				SpawnTable.getInstance().stopEventSpawn("warpgate_1", true);

				SpawnTable.getInstance().startEventSpawn("warpgate_2");
				SpawnTable.getInstance().startEventSpawn("harbor_area");
				SpawnTable.getInstance().startEventSpawn("green_spot");
				SpawnTable.getInstance().startEventSpawn("remnants");
				SpawnTable.getInstance().startEventSpawn("quarry");
				SpawnTable.getInstance().startEventSpawn("desert");
				SpawnTable.getInstance().startEventSpawn("hb_npcs_1");
				SpawnTable.getInstance().startEventSpawn("hb_dead");
				SpawnTable.getInstance().startEventSpawn("remnants_d");
				SpawnTable.getInstance().startEventSpawn("keltas1");
				SpawnTable.getInstance().startEventSpawn("darion_forces");
				SpawnTable.getInstance().startEventSpawn("hb_natives_out");
				SpawnTable.getInstance().startEventSpawn("quarry_guards");
				break;
			case 4:
				SpawnTable.getInstance().stopEventSpawn("keltas1", true);
				SpawnTable.getInstance().stopEventSpawn("warpgate_0", true);
				SpawnTable.getInstance().stopEventSpawn("warpgate_1", true);

				SpawnTable.getInstance().startEventSpawn("warpgate_2");
				SpawnTable.getInstance().startEventSpawn("harbor_area");
				SpawnTable.getInstance().startEventSpawn("green_spot");
				SpawnTable.getInstance().startEventSpawn("remnants");
				SpawnTable.getInstance().startEventSpawn("quarry");
				SpawnTable.getInstance().startEventSpawn("desert");
				SpawnTable.getInstance().startEventSpawn("hb_npcs_1");
				SpawnTable.getInstance().startEventSpawn("hb_dead");
				SpawnTable.getInstance().startEventSpawn("remnants_d");
				SpawnTable.getInstance().startEventSpawn("keltas2");
				SpawnTable.getInstance().startEventSpawn("darion_forces");
				SpawnTable.getInstance().startEventSpawn("derek");
				SpawnTable.getInstance().startEventSpawn("hb_natives_out");
				SpawnTable.getInstance().startEventSpawn("quarry_guards");
				break;
			case 5:
				SpawnTable.getInstance().stopEventSpawn("warpgate_0", true);
				SpawnTable.getInstance().stopEventSpawn("warpgate_1", true);
				SpawnTable.getInstance().stopEventSpawn("warpgate_2", true);

				SpawnTable.getInstance().startEventSpawn("warpgate_3");
				SpawnTable.getInstance().startEventSpawn("harbor_area");
				SpawnTable.getInstance().startEventSpawn("green_spot");
				SpawnTable.getInstance().startEventSpawn("remnants");
				SpawnTable.getInstance().startEventSpawn("quarry");
				SpawnTable.getInstance().startEventSpawn("desert");
				SpawnTable.getInstance().startEventSpawn("hb_npcs_1");
				SpawnTable.getInstance().startEventSpawn("hb_natives_in");
				SpawnTable.getInstance().startEventSpawn("quarry_guards");

				DoorTable.getInstance().getDoor(19250001).openMe();
				DoorTable.getInstance().getDoor(19250002).openMe();
				break;
			case 6:
				SpawnTable.getInstance().stopEventSpawn("warpgate_0", true);
				SpawnTable.getInstance().stopEventSpawn("warpgate_1", true);
				SpawnTable.getInstance().stopEventSpawn("warpgate_2", true);

				SpawnTable.getInstance().startEventSpawn("warpgate_3");
				SpawnTable.getInstance().startEventSpawn("harbor_area");
				SpawnTable.getInstance().startEventSpawn("green_spot");
				SpawnTable.getInstance().startEventSpawn("remnants");
				SpawnTable.getInstance().startEventSpawn("desert");
				SpawnTable.getInstance().startEventSpawn("hb_npcs_1");
				SpawnTable.getInstance().startEventSpawn("hb_natives_in");
				SpawnTable.getInstance().startEventSpawn("hb_stage_6");

				if(!hellinarkKilled)
					SpawnTable.getInstance().startEventSpawn("hellinark");

				DoorTable.getInstance().getDoor(19250001).openMe();
				DoorTable.getInstance().getDoor(19250002).openMe();
				break;
			case 7:
				SpawnTable.getInstance().stopEventSpawn("warpgate_0", true);
				SpawnTable.getInstance().stopEventSpawn("warpgate_1", true);
				SpawnTable.getInstance().stopEventSpawn("warpgate_2", true);
				SpawnTable.getInstance().stopEventSpawn("warpgate_3", true);

				SpawnTable.getInstance().startEventSpawn("warpgate_4");
				SpawnTable.getInstance().startEventSpawn("harbor_area");
				SpawnTable.getInstance().startEventSpawn("green_spot");
				SpawnTable.getInstance().startEventSpawn("remnants");
				SpawnTable.getInstance().startEventSpawn("desert");
				SpawnTable.getInstance().startEventSpawn("hb_npcs_1");
				SpawnTable.getInstance().startEventSpawn("hb_natives_in");
				SpawnTable.getInstance().startEventSpawn("wounded_passage");
				SpawnTable.getInstance().startEventSpawn("battered_lands");

				DoorTable.getInstance().getDoor(19250001).openMe();
				DoorTable.getInstance().getDoor(19250002).openMe();
				DoorTable.getInstance().getDoor(20250002).openMe();
				break;
			case 8:
				SpawnTable.getInstance().stopEventSpawn("warpgate_0", true);
				SpawnTable.getInstance().stopEventSpawn("warpgate_1", true);
				SpawnTable.getInstance().stopEventSpawn("warpgate_2", true);
				SpawnTable.getInstance().stopEventSpawn("warpgate_3", true);
				SpawnTable.getInstance().stopEventSpawn("wounded_passage", true);

				SpawnTable.getInstance().startEventSpawn("warpgate_4");
				SpawnTable.getInstance().startEventSpawn("harbor_area");
				SpawnTable.getInstance().startEventSpawn("green_spot");
				SpawnTable.getInstance().startEventSpawn("remnants");
				SpawnTable.getInstance().startEventSpawn("desert");
				SpawnTable.getInstance().startEventSpawn("hb_npcs_1");
				SpawnTable.getInstance().startEventSpawn("hb_natives_in");
				SpawnTable.getInstance().startEventSpawn("battered_lands");
				SpawnTable.getInstance().startEventSpawn("hb_stage8");

				DoorTable.getInstance().getDoor(19250001).openMe();
				DoorTable.getInstance().getDoor(19250002).openMe();
				DoorTable.getInstance().getDoor(20250002).openMe();
				break;
			case 9:
				SpawnTable.getInstance().stopEventSpawn("warpgate_0", true);
				SpawnTable.getInstance().stopEventSpawn("warpgate_1", true);
				SpawnTable.getInstance().stopEventSpawn("warpgate_2", true);
				SpawnTable.getInstance().stopEventSpawn("warpgate_3", true);
				SpawnTable.getInstance().stopEventSpawn("warpgate_4", true);
				SpawnTable.getInstance().stopEventSpawn("hb_stage8", true);

				SpawnTable.getInstance().startEventSpawn("warpgate_5");
				SpawnTable.getInstance().startEventSpawn("harbor_area");
				SpawnTable.getInstance().startEventSpawn("green_spot");
				SpawnTable.getInstance().startEventSpawn("remnants");
				SpawnTable.getInstance().startEventSpawn("desert");
				SpawnTable.getInstance().startEventSpawn("hb_npcs_1");
				SpawnTable.getInstance().startEventSpawn("hb_natives_in");
				SpawnTable.getInstance().startEventSpawn("battered_lands");
				SpawnTable.getInstance().startEventSpawn("hb_stage9slaves");

				DoorTable.getInstance().getDoor(19250001).openMe();
				DoorTable.getInstance().getDoor(19250002).openMe();
				DoorTable.getInstance().getDoor(20250002).openMe();
				DoorTable.getInstance().getDoor(20250001).openMe();
				break;
			case 10:
				SpawnTable.getInstance().stopEventSpawn("warpgate_0", true);
				SpawnTable.getInstance().stopEventSpawn("warpgate_1", true);
				SpawnTable.getInstance().stopEventSpawn("warpgate_2", true);
				SpawnTable.getInstance().stopEventSpawn("warpgate_3", true);
				SpawnTable.getInstance().stopEventSpawn("warpgate_4", true);

				SpawnTable.getInstance().startEventSpawn("warpgate_5");
				SpawnTable.getInstance().startEventSpawn("harbor_area");
				SpawnTable.getInstance().startEventSpawn("green_spot");
				SpawnTable.getInstance().startEventSpawn("remnants");
				SpawnTable.getInstance().startEventSpawn("desert");
				SpawnTable.getInstance().startEventSpawn("hb_npcs_1");
				SpawnTable.getInstance().startEventSpawn("hb_natives_in");
				SpawnTable.getInstance().startEventSpawn("battered_lands");
				SpawnTable.getInstance().startEventSpawn("hb_stage9slaves");

				DoorTable.getInstance().getDoor(19250001).openMe();
				DoorTable.getInstance().getDoor(19250002).openMe();
				DoorTable.getInstance().getDoor(20250002).openMe();
				DoorTable.getInstance().getDoor(20250001).openMe();
				break;
			case 11:
				SpawnTable.getInstance().stopEventSpawn("warpgate_0", true);
				SpawnTable.getInstance().stopEventSpawn("warpgate_1", true);
				SpawnTable.getInstance().stopEventSpawn("warpgate_2", true);
				SpawnTable.getInstance().stopEventSpawn("warpgate_3", true);
				SpawnTable.getInstance().stopEventSpawn("warpgate_4", true);

				SpawnTable.getInstance().startEventSpawn("warpgate_5");
				SpawnTable.getInstance().startEventSpawn("harbor_area");
				SpawnTable.getInstance().startEventSpawn("green_spot");
				SpawnTable.getInstance().startEventSpawn("remnants");
				SpawnTable.getInstance().startEventSpawn("desert");
				SpawnTable.getInstance().startEventSpawn("hb_npcs_1");
				SpawnTable.getInstance().startEventSpawn("hb_natives_in");
				SpawnTable.getInstance().startEventSpawn("battered_lands");
				SpawnTable.getInstance().startEventSpawn("hb_stage9slaves");

				DoorTable.getInstance().getDoor(19250001).openMe();
				DoorTable.getInstance().getDoor(19250002).openMe();
				DoorTable.getInstance().getDoor(20250002).openMe();
				DoorTable.getInstance().getDoor(20250001).openMe();
				DoorTable.getInstance().getDoor(20260003).openMe();
				break;
		}
	}

	public static void setStage(int stage)
	{
		ServerVariables.set("hb_stage", stage);
		ServerVariables.set("hb_points", hbPoints);
		hbStage = stage;
	}

	public static void addPoints(long points)
	{
		if(hbPoints + points > LIMIT[hbStage])
		{
			points = LIMIT[hbStage] - hbPoints;
		}

		hbPoints += points;

		switch(hbStage)
		{
			case 1:
				if(hbPoints >= LIMIT[hbStage])
					setStage(2);
				break;
			case 2:
				if(hbPoints >= LIMIT[hbStage])
				{
					setStage(3);
					SpawnTable.getInstance().startEventSpawn("keltas1");
					SpawnTable.getInstance().startEventSpawn("darion_forces");
					SpawnTable.getInstance().stopEventSpawn("warpgate_1", true);
					SpawnTable.getInstance().startEventSpawn("warpgate_2");
				}
				break;
			case 3:
				if(hbPoints >= LIMIT[hbStage] && ServerVariables.getInt("hb_ber_nt", 0) >= 1 && ServerVariables.getInt("hb_jude_nt", 0) >= 40)
				{
					setStage(4);
					SpawnTable.getInstance().stopEventSpawn("keltas1", true);
					SpawnTable.getInstance().startEventSpawn("keltas2");
					SpawnTable.getInstance().startEventSpawn("derek");
				}
				break;
			case 4:
				if(hbPoints >= LIMIT[hbStage])
				{
					setStage(5);
					SpawnTable.getInstance().stopEventSpawn("keltas2", true);
					SpawnTable.getInstance().stopEventSpawn("derek", true);
					SpawnTable.getInstance().stopEventSpawn("remnants_d", true);
					SpawnTable.getInstance().stopEventSpawn("hb_dead", true);
					SpawnTable.getInstance().stopEventSpawn("darion_forces", true);
					SpawnTable.getInstance().stopEventSpawn("warpgate_2", true);
					SpawnTable.getInstance().stopEventSpawn("hb_natives_out", true);

					SpawnTable.getInstance().startEventSpawn("warpgate_3");
					SpawnTable.getInstance().startEventSpawn("hb_natives_in");

					DoorTable.getInstance().getDoor(19250001).openMe();
					DoorTable.getInstance().getDoor(19250002).openMe();
				}
				break;
			case 5:
				if(hbPoints >= LIMIT[hbStage])
				{
					setStage(6);
					SpawnTable.getInstance().stopEventSpawn("quarry", true);
					SpawnTable.getInstance().stopEventSpawn("quarry_guards", true);

					SpawnTable.getInstance().startEventSpawn("hb_stage_6");
					SpawnTable.getInstance().startEventSpawn("hellinark");

					DoorTable.getInstance().getDoor(19250001).openMe();
					DoorTable.getInstance().getDoor(19250002).openMe();
				}
				break;
			case 6:
				if(hbPoints >= LIMIT[hbStage])
				{
					setStage(7);
					SpawnTable.getInstance().stopEventSpawn("hb_stage_6", true);
					SpawnTable.getInstance().stopEventSpawn("hellinark", true);
					SpawnTable.getInstance().stopEventSpawn("warpgate_3", true);

					SpawnTable.getInstance().startEventSpawn("warpgate_4");
					SpawnTable.getInstance().startEventSpawn("wounded_passage");
					SpawnTable.getInstance().startEventSpawn("battered_lands");

					DoorTable.getInstance().getDoor(19250001).openMe();
					DoorTable.getInstance().getDoor(19250002).openMe();
					DoorTable.getInstance().getDoor(20250002).openMe();
				}
				break;
			case 7:
				if(hbPoints >= LIMIT[hbStage])
				{
					setStage(8);
					SpawnTable.getInstance().stopEventSpawn("wounded_passage", true);
					SpawnTable.getInstance().startEventSpawn("hb_stage8");

					DoorTable.getInstance().getDoor(19250001).openMe();
					DoorTable.getInstance().getDoor(19250002).openMe();
					DoorTable.getInstance().getDoor(20250002).openMe();
				}
				break;

			case 8:
				if(hbPoints >= LIMIT[hbStage])
				{
					setStage(9);
					SpawnTable.getInstance().stopEventSpawn("hb_stage8", true);

					SpawnTable.getInstance().startEventSpawn("hb_stage9slaves");

					DoorTable.getInstance().getDoor(19250001).openMe();
					DoorTable.getInstance().getDoor(19250002).openMe();
					DoorTable.getInstance().getDoor(20250002).openMe();
					DoorTable.getInstance().getDoor(20250001).openMe();
				}
				break;

			case 9:
				if(hbPoints >= LIMIT[hbStage])
				{
					setStage(10);
					SpawnTable.getInstance().stopEventSpawn("warpgate_4", true);

					SpawnTable.getInstance().startEventSpawn("warpgate_5");

					DoorTable.getInstance().getDoor(19250001).openMe();
					DoorTable.getInstance().getDoor(19250002).openMe();
					DoorTable.getInstance().getDoor(20250002).openMe();
					DoorTable.getInstance().getDoor(20250001).openMe();
				}
				break;

			case 10:
				if(hbPoints >= LIMIT[hbStage])
				{
					setStage(11);

					DoorTable.getInstance().getDoor(19250001).openMe();
					DoorTable.getInstance().getDoor(19250002).openMe();
					DoorTable.getInstance().getDoor(20250002).openMe();
					DoorTable.getInstance().getDoor(20250001).openMe();
					DoorTable.getInstance().getDoor(20260003).openMe();
				}
				break;

		}

		if(lastUpdate < System.currentTimeMillis())
		{
			lastUpdate = System.currentTimeMillis() + 300000;
			ServerVariables.set("hb_points", hbPoints);
		}
	}

	public static long getPoints()
	{
		return hbPoints;
	}

	private static Pattern _p1 = Pattern.compile("(\\d+)\\-(\\d+),(.+?);");
	private static Pattern _p2 = Pattern.compile("(.+?)\\:(\\d+),*");

	private class HBTrapZoneListener extends L2ZoneEnterLeaveListener
	{
		private int _zoneReuse;
		private int _despawnTime;
		private long _lastSpawnTime = 0;
		private L2GroupSpawn _lastSpawnedGroup;
		private GArray<HBTrapSpawn> _trapSpawn;
		private ScheduledFuture<?> _despawnTask;

		@Override
		public void objectEntered(L2Zone zone, L2Character object)
		{
			if(!object.isPlayer())
				return;

			if(_lastSpawnTime < System.currentTimeMillis())
			{
				_lastSpawnTime = System.currentTimeMillis() + _zoneReuse;
				GArray<HBTrapSpawn> list = new GArray<HBTrapSpawn>();
				for(HBTrapSpawn hbts : _trapSpawn)
					if(hbts.minStage <= hbStage && hbStage <= hbts.maxStage)
						list.add(hbts);


				if(list.size() > 0)
				{
					int chance = Rnd.get(100);
					for(HBTrapSpawn hbts : list)
						if(chance > hbts.chance)
							chance -= hbts.chance;
						else
						{
							if(_despawnTask != null)
							{
								_despawnTask.cancel(true);
								_despawnTask = null;
								if(_lastSpawnedGroup != null && !_lastSpawnedGroup.isAllDead())
									_lastSpawnedGroup.despawnAll();
							}

							_lastSpawnedGroup = SpawnTable.getInstance().getEventGroupSpawn(hbts.event, null);
							break;
						}

					_lastSpawnedGroup.setRespawnDelay(0);
					_lastSpawnedGroup.doSpawn();

					boolean speech = true;
					for(L2NpcInstance npc : _lastSpawnedGroup.getAllSpawned())
					{
						npc.getAI().setGlobalAggro(0);
						if(speech)
						{
							switch(npc.getNpcId())
							{
								case 22344:
									if(hbStage > 5)
										Functions.npcSay(npc, Say2C.ALL, "Look in every nook and cranny around here!");
									else
										Functions.npcSay(npc, Say2C.ALL, "Work harder, damned slave!");
									speech = false;
									break;
								case 22330:
									Functions.npcSay(npc, Say2C.ALL, "Why would you build a tower in our territory?");
									speech = false;
									break;
								case 22324:
									Functions.npcSay(npc, Say2C.ALL, "Invader!");
									speech = false;
									break;
							}
						}
					}

					_despawnTask = ThreadPoolManager.getInstance().scheduleGeneral(new Runnable()
					{
						public void run()
						{
							_lastSpawnedGroup.despawnAll();
							_despawnTask = null;
						}
					}, _despawnTime);
				}
			}
		}

		@Override
		public void objectLeaved(L2Zone zone, L2Character object)
		{
		}

		public void sendZoneStatus(L2Zone zone, L2Player object)
		{
		}

		public void parseSpawnString(String spawn)
		{
			Matcher m = _p1.matcher(spawn);

			while(m.find())
			{
				try
				{
					int min = Integer.parseInt(m.group(1));
					int max = Integer.parseInt(m.group(2));
					Matcher m2 = _p2.matcher(m.group(3));
					while(m2.find())
					{
						int chance = Integer.parseInt(m2.group(2));
						if(_trapSpawn == null)
							_trapSpawn = new GArray<HBTrapSpawn>();
						_trapSpawn.add(new HBTrapSpawn(min, max, chance, m2.group(1)));
					}
				}
				catch(Exception e)
				{
					System.out.println("Hellbound: can't parse hb_spawn for zone: '" + spawn + "' " + e);
				}
			}
		}

		public void setZoneReuse(int reuse)
		{
			_zoneReuse = reuse * 1000;
		}

		public void setDespawnTime(int despawn)
		{
			_despawnTime = despawn * 1000;
		}

		private class HBTrapSpawn
		{
			public final int minStage;
			public final int maxStage;
			public final int chance;
			public final String event;

			public HBTrapSpawn(int min, int max, int c, String s)
			{
				minStage = min;
				maxStage = max;
				chance = c;
				event = s;
			}
		}
	}

	private class DayNightListener extends DayNightChangeListener
	{
		@Override
		public void switchToNight()
		{
			if(hbStage >= 9 && Rnd.chance(100))
				Functions.npcSayCustom(_shadai.get(0).spawnOne(), Say2C.SHOUT, "shadai.Shout1", null);
		}

		@Override
		public void switchToDay()
		{
			_shadai.get(0).despawnAll();
		}
	}
}

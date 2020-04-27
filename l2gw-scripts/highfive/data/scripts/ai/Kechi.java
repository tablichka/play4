package ai;

import instances.CrystalCavernsInstance;
import javolution.util.FastMap;
import ru.l2gw.gameserver.Config;
import ru.l2gw.gameserver.controllers.ThreadPoolManager;
import ru.l2gw.gameserver.ai.Balanced;
import ru.l2gw.gameserver.model.*;
import ru.l2gw.gameserver.model.entity.instance.Instance;
import ru.l2gw.gameserver.model.instances.L2DoorInstance;
import ru.l2gw.gameserver.model.instances.L2NpcInstance;
import ru.l2gw.gameserver.tables.MapRegionTable;
import ru.l2gw.gameserver.tables.NpcTable;
import ru.l2gw.gameserver.tables.ReflectionTable;
import ru.l2gw.gameserver.tables.SkillTable;
import ru.l2gw.util.Location;

/**
 * @author: rage
 * @date: 03.12.2009 15:50:00
 */
public class Kechi extends Balanced
{
	private int spawnWave;
	private static final int minion = 25533;
	private static final int doorId = 24220023;
	private static final Location[] spawnPoints = {
			new Location(153200, 149577, -12163, 62495),
			new Location(153107, 149578, -12163, 62495),
			new Location(153010, 149582, -12163, 62495),
			new Location(152894, 149584, -12163, 62495),
			new Location(152789, 149583, -12163, 62495),

			new Location(153180, 149497, -12163, 62495),
			new Location(153089, 149492, -12163, 62495),
			new Location(152970, 149491, -12163, 62495),
			new Location(152841, 149488, -12163, 62495),
			new Location(152723, 149486, -12163, 62495)
	};
	private static FastMap<Integer, Location[]> pathPoints;
	private L2DoorInstance door;
	private static final int RED_CORAL = 9692;

	public Kechi(L2Character actor)
	{
		super(actor);
		spawnWave = 0;
		door = getDoorById(doorId);
		pathPoints = new FastMap<Integer, Location[]>();

		pathPoints.put(0, new Location[]{new Location(153519, 149554, -12162), new Location(153660, 149867, -12162), new Location(153705, 149840, -12162)});
		pathPoints.put(1, new Location[]{new Location(153519, 149554, -12162), new Location(153614, 149784, -12162), new Location(153660, 149767, -12162)});
		pathPoints.put(2, new Location[]{new Location(153519, 149554, -12162), new Location(153572, 149702, -12162), new Location(153620, 149687, -12162)});
		pathPoints.put(3, new Location[]{new Location(153519, 149554, -12162), new Location(153551, 149630, -12162), new Location(153599, 149617, -12162)});
		pathPoints.put(4, new Location[]{new Location(153596, 149554, -12162)});

		pathPoints.put(5, new Location[]{new Location(153537, 149496, -12162), new Location(153662, 149186, -12162), new Location(153689, 149217, -12162)});
		pathPoints.put(6, new Location[]{new Location(153537, 149496, -12162), new Location(153598, 149273, -12162), new Location(153642, 149289, -12162)});
		pathPoints.put(7, new Location[]{new Location(153537, 149496, -12162), new Location(153561, 149351, -12162), new Location(153604, 149365, -12162)});
		pathPoints.put(8, new Location[]{new Location(153537, 149496, -12162), new Location(153546, 149432, -12162), new Location(153589, 149443, -12162)});
		pathPoints.put(9, new Location[]{new Location(153596, 149496, -12162)});
	}

	@Override
	protected void onEvtAttacked(L2Character attacker, int damage, L2Skill skill)
	{
		if(spawnWave == 0 && _thisActor.getCurrentHp() < _thisActor.getMaxHp() * 0.85)
		{
			spawnWave = 1;
			spawnMinions();
			checkCorals(attacker);
		}
		else if(spawnWave == 1 && _thisActor.getCurrentHp() < _thisActor.getMaxHp() * 0.55)
		{
			spawnWave = 2;
			spawnMinions();
			checkCorals(attacker);
		}
		else if(spawnWave == 2 && _thisActor.getCurrentHp() < _thisActor.getMaxHp() * 0.35)
		{
			spawnWave = 3;
			spawnMinions();
			checkCorals(attacker);
		}

		if(!attacker.isDead() && attacker.getPlayer() != null && attacker.getPlayer().getEffectBySkillId(CrystalCavernsInstance.TIMER_ID) == null)
		{
			if(Config.DEBUG_INSTANCES)
				Instance._log.info(_thisActor.getSpawn().getInstance() + " " + _thisActor + " attacked by " + attacker + " with no timer.");
			if(attacker.getPlayer().getParty() != null)
				for(L2Player member : attacker.getPlayer().getParty().getPartyMembers())
					member.teleToLocation(MapRegionTable.getInstance().getTeleToLocation(member, MapRegionTable.TeleportWhereType.ClosestTown));
			else
				attacker.getPlayer().teleToLocation(MapRegionTable.getInstance().getTeleToLocation(attacker.getPlayer(), MapRegionTable.TeleportWhereType.ClosestTown));
			return;
		}

		super.onEvtAttacked(attacker, damage, skill);
	}

	private void spawnMinions()
	{
		if(door == null)
		{
			_log.warn(_thisActor + " doorId: " + doorId + " can't find!");
			return;
		}

		for(int i = 0; i < spawnPoints.length; i++)
		{
			try
			{
				L2Spawn spawn = new L2Spawn(NpcTable.getTemplate(minion));
				spawn.setReflection(_thisActor.getReflection());
				spawn.setInstance(_thisActor.getSpawn().getInstance());
				spawn.setLoc(spawnPoints[i]);
				spawn.setAmount(1);
				spawn.stopRespawn();
				L2NpcInstance minion = spawn.spawnOne();
				((KechiMinion) minion.getAI()).setPath(pathPoints.get(i));
				((KechiMinion) minion.getAI()).setActive(System.currentTimeMillis() + 1000);
			}
			catch(Exception e)
			{
				_log.warn(_thisActor + " can't spawn minions: " + e);
				e.printStackTrace();
			}
		}

		door.openMe();

		ThreadPoolManager.getInstance().scheduleAi(new Runnable()
		{
			public void run()
			{
				door.closeMe();
			}
		}, 10000, false);
	}

	private void checkCorals(L2Character attacker)
	{
		if(attacker.getPlayer() != null && attacker.getPlayer().getParty() != null)
		{
			int c = 0;
			int p = 0;
			for(L2Player member : attacker.getPlayer().getParty().getPartyMembers())
			{
				p++;
				if(member.getItemCountByItemId(RED_CORAL) > 0)
					c++;
			}

			if(c == p)
			{
				L2Skill timer = SkillTable.getInstance().getInfo(CrystalCavernsInstance.TIMER_ID, 1);

				for(L2Player member : attacker.getPlayer().getParty().getPartyMembers())
					if(member.destroyItemByItemId("CrystalCaverns", RED_CORAL, 1, _thisActor, true))
						timer.applyEffects(member, member, false);
			}
		}
	}

	private L2DoorInstance getDoorById(int doorId)
	{
		Reflection ref = ReflectionTable.getInstance().getById(_thisActor.getReflection());

		for(L2Object object : ref.getAllObjects())
			if(object instanceof L2DoorInstance && ((L2DoorInstance) object).getDoorId() == doorId)
				return (L2DoorInstance) object;

		return null;
	}
}

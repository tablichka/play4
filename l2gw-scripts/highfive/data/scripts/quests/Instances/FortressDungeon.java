package quests.Instances;

import javolution.util.FastList;
import ru.l2gw.gameserver.controllers.ThreadPoolManager;
import ru.l2gw.gameserver.instancemanager.InstanceManager;
import ru.l2gw.gameserver.model.L2Player;
import ru.l2gw.gameserver.model.L2Spawn;
import ru.l2gw.gameserver.model.entity.instance.Instance;
import ru.l2gw.gameserver.model.instances.L2NpcInstance;
import ru.l2gw.gameserver.model.quest.Quest;
import ru.l2gw.gameserver.tables.NpcTable;
import ru.l2gw.gameserver.templates.L2NpcTemplate;
import ru.l2gw.util.Location;
import ru.l2gw.commons.math.Rnd;

/**
 * @author ic
 * @date 30.09.2009 15:00:00
 */
public class FortressDungeon extends Quest
{
	private static final FastList<Integer> WAVE1 = new FastList<Integer>();
	private static final FastList<Integer> WAVE2 = new FastList<Integer>();
	private static final FastList<Integer> BOSSES = new FastList<Integer>();
	private static final Location ROOM_CENTER = new Location(53317, 246060, -6550);
	private static L2Spawn spawn2;
	private static L2Spawn spawn3;
	private static final boolean DEBUG = false;

	public FortressDungeon()
	{
		super(22019, "FortressDungeon", "Fortress Dungeon", true);

		WAVE1.add(25572); // Hager the Outlaw |    61
		WAVE1.add(25575); // All-Seeing Rango |    62
		WAVE1.add(25578); // Jakard           |    63
		WAVE1.add(25579); // Helsing          |    64

		WAVE2.add(25582); // Gillien          |    65
		WAVE2.add(25585); // Medici           |    66
		WAVE2.add(25588); // Immortal Muus    |    67

		BOSSES.add(25589); // Brand the Exile  |    68
		BOSSES.add(25592); // Commander Koenig |    69
		BOSSES.add(25593); // Gerg the Hunter  |    70

		for(int i : WAVE1)
			addKillId(i);
		for(int i : WAVE2)
			addKillId(i);
		for(int i : BOSSES)
			addKillId(i);

	}

	@Override
	public void onKill(L2NpcInstance npc, L2Player player)
	{
		if(DEBUG)
			System.out.println("Fortress Dungeon Quest: player " + player + " killed mob :" + npc);

		if(WAVE1.contains(npc.getNpcId()))
		{
			if(DEBUG)
				System.out.println("Fortress Dungeon Quest: 1st wave RB killed.");
			Instance inst = InstanceManager.getInstance().getInstanceByPlayer(player);
			try
			{
				if(inst != null && inst.getTimeLeft() > 90)
				{
					L2NpcTemplate template = NpcTable.getTemplate(WAVE2.get(Rnd.get(WAVE2.size())));
					spawn2 = new L2Spawn(template);
					spawn2.setAmount(1);
					spawn2.setLoc(ROOM_CENTER);
					spawn2.setReflection(player.getReflection());
					spawn2.setInstance(inst);
					spawn2.stopRespawn();
					ThreadPoolManager.getInstance().scheduleAi(new SecondWave(spawn2), 120000, false);
					if(DEBUG)
						System.out.println("Fortress Dungeon Quest: Scheduled spawn next task.");
				}
				else
				{
					if(DEBUG)
						System.out.println("Fortress Dungeon Quest: Instance is null or time left less than 90 seconds. (" + (inst == null ? "instance is null" : "time left: " + inst.getTimeLeft()) + ")");
				}
			}
			catch(Exception e)
			{
				System.out.println("Fortress Dungeon: can't create raidboss2 spawn: " + e);
				e.printStackTrace();
			}
		}
		else if(WAVE2.contains(npc.getNpcId()))
		{
			Instance inst = InstanceManager.getInstance().getInstanceByPlayer(player);
			try
			{
				if(inst != null && inst.getTimeLeft() > 90)
				{
					L2NpcTemplate template = NpcTable.getTemplate(BOSSES.get(Rnd.get(BOSSES.size())));
					spawn3 = new L2Spawn(template);
					spawn3.setAmount(1);
					spawn3.setLoc(ROOM_CENTER);
					spawn3.setInstance(inst);
					spawn3.setReflection(player.getReflection());
					spawn3.stopRespawn();
					ThreadPoolManager.getInstance().scheduleAi(new BossesWave(spawn3), 120000, false);
				}
				else
				{
					if(DEBUG)
						System.out.println("Fortress Dungeon Quest: Instance is null or time left less than 90 seconds. (" + (inst == null ? "instance is null" : "time left: " + inst.getTimeLeft()) + ")");
				}
			}
			catch(Exception e)
			{
				System.out.println("Fortress Dungeon: can't create raidboss3 spawn: " + e);
				e.printStackTrace();
			}
		}
		else if(BOSSES.contains(npc.getNpcId()))
		{
			Instance inst = InstanceManager.getInstance().getInstanceByPlayer(player);
			if(inst == null)
			{
				_log.warn(this + " onKill killer has no instance! " + player + " reflection: " + player.getReflection());
				return;
			}

			inst.rescheduleEndTask(300);
		}
	}

	public static class SecondWave implements Runnable
	{
		L2Spawn _spawn;

		public SecondWave(L2Spawn spwn)
		{
			_spawn = spwn;
		}

		public void run()
		{
			try
			{
				if(DEBUG)
					System.out.println("Fortress Dungeon Quest: run(): initiating spawnOne() function.");
				_spawn.spawnOne();
			}
			catch(Throwable e)
			{
				System.out.println("Fortress Dungeon: can't spawn 2nd wave: " + e);
				e.printStackTrace();
			}
		}
	}

	public static class BossesWave implements Runnable
	{
		L2Spawn _spawn;

		public BossesWave(L2Spawn spwn)
		{
			_spawn = spwn;
		}

		public void run()
		{
			try
			{
				_spawn.spawnOne();
			}
			catch(Throwable e)
			{
				System.out.println("Fortress Dungeon: can't spawn bosses wave: " + e);
				e.printStackTrace();
			}
		}
	}

}

package quests.Instances;

import javolution.util.FastMap;
import ru.l2gw.commons.math.Rnd;
import ru.l2gw.gameserver.model.L2Player;
import ru.l2gw.gameserver.model.L2Spawn;
import ru.l2gw.gameserver.model.instances.L2NpcInstance;
import ru.l2gw.gameserver.model.quest.Quest;
import ru.l2gw.gameserver.tables.NpcTable;
import ru.l2gw.gameserver.templates.L2NpcTemplate;

/**
 * User: ic
 * Date: 30.05.2010
 */
public class RimKamaloka_70 extends Quest
{
	private static final int MOB = 22479;
	private static FastMap<Integer, Integer> POINTS = new FastMap<Integer, Integer>().shared();

	public RimKamaloka_70()
	{
		super(22070, "RimKamaloka_70", "Rim Kamaloka level 70", true);
		addKillId(MOB);
		addKillId(MOB + 1);
		addKillId(MOB + 2);
	}

	@Override
	public void onKill(L2NpcInstance npc, L2Player player)
	{
		int npcId = npc.getNpcId();
		if(npcId == MOB)
		{
			if(Rnd.chance(20))
			{
				try
				{
					L2Spawn spawn;
					L2NpcTemplate template = NpcTable.getTemplate(MOB + 1 + Rnd.get(0, 1)); // Random spawn Doppler or Void type mob
					spawn = new L2Spawn(template);
					spawn.setAmount(1);
					spawn.setReflection(npc.getReflection());
					spawn.setInstance(npc.getSpawn().getInstance());
					spawn.setRespawnTime(0);
					spawn.setLoc(npc.getLoc());
					spawn.stopRespawn();
					spawn.spawnOne();
				}
				catch(Exception e)
				{
					_log.warn(this + ": Can't create spawn for mob (doppler of void)! NPC: " + npc + " Reflection: " + npc.getReflection());
					e.printStackTrace();
				}
			}
		}
		else if(npcId == MOB + 1) // Doppler, lets say 1 point
			addPoints(player, 1);
		else if(npcId == MOB + 2) // Void, lets say 2 point
			addPoints(player, 2);
	}

	public static void clearPoints(L2Player player)
	{
		POINTS.put(player.getObjectId(), 0);
	}

	public static void addPoints(L2Player player, int point)
	{
		POINTS.put(player.getObjectId(), getPoints(player) + point);
	}

	public static int getPoints(L2Player player)
	{
		return POINTS.get(player.getObjectId()) != null ? POINTS.get(player.getObjectId()) : 0;
	}
}

package events;

import ru.l2gw.gameserver.instancemanager.ServerVariables;
import ru.l2gw.gameserver.model.L2Character;
import ru.l2gw.gameserver.model.L2Spawn;
import ru.l2gw.gameserver.tables.NpcTable;
import ru.l2gw.gameserver.templates.L2NpcTemplate;

import java.util.AbstractList;

public class Helper
{
	public static void SpawnNPCs(int npcId, int[][] locations, AbstractList<L2Spawn> list)
	{
		L2NpcTemplate template = NpcTable.getTemplate(npcId);
		if(template == null)
		{
			System.out.println("WARNING! events.Helper.SpawnNPCs template is null for npc: " + npcId);
			Thread.dumpStack();
			return;
		}
		for(int[] location : locations)
			try
			{
				L2Spawn sp = new L2Spawn(template);
				sp.setLocx(location[0]);
				sp.setLocy(location[1]);
				sp.setLocz(location[2]);
				sp.setHeading(location[3]);
				sp.setAmount(1);
				sp.setRespawnDelay(0);
				sp.init();
				if(list != null)
					list.add(sp);
			}
			catch(ClassNotFoundException e)
			{
				e.printStackTrace();
			}
	}

	public static void deSpawnNPCs(AbstractList<L2Spawn> list)
	{
		for(L2Spawn sp : list)
		{
			sp.stopRespawn();
			sp.getLastSpawn().deleteMe();
		}
		list.clear();
	}

	public static boolean IsActive(String name)
	{
		return ServerVariables.getString(name, "off").equalsIgnoreCase("on");
	}

	public static boolean SetActive(String name, boolean active)
	{
		if(active == IsActive(name))
			return false;
		if(active)
			ServerVariables.set(name, "on");
		else
			ServerVariables.unset(name);
		return true;
	}

	public static boolean SimpleCheckDrop(L2Character mob, L2Character killer)
	{
		return mob != null && mob.isMonster() && !mob.isRaid() && killer != null && killer.getPlayer() != null && killer.getLevel() - mob.getLevel() < 10;
	}

}
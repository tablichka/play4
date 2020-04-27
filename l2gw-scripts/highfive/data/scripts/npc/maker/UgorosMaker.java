package npc.maker;

import ru.l2gw.gameserver.model.instances.L2NpcInstance;
import ru.l2gw.gameserver.model.npcmaker.DefaultMaker;
import ru.l2gw.gameserver.tables.SpawnTable;

/**
 * @author: rage
 * @date: 07.09.11 16:56
 */
public class UgorosMaker extends DefaultMaker
{
	public String maker_ugoros_herb = "oren21_mb2220_ugozg02m1";

	public UgorosMaker(int maximum_npc, String name)
	{
		super(maximum_npc, name);
	}

	@Override
	public void onNpcCreated(L2NpcInstance npc)
	{
		if(npc.getSpawnDefine() == spawn_defines.get(0))
		{
			DefaultMaker maker0 = SpawnTable.getInstance().getNpcMaker(maker_ugoros_herb);
			if(maker0 != null)
			{
				maker0.onScriptEvent(78010083, 0, 0);
			}
		}
	}

	@Override
	public void onNpcDeleted(L2NpcInstance npc)
	{
		super.onNpcDeleted(npc);
		if(npc.getSpawnDefine() == spawn_defines.get(0))
		{
			DefaultMaker maker0 = SpawnTable.getInstance().getNpcMaker(maker_ugoros_herb);
			if(maker0 != null)
			{
				maker0.onScriptEvent(78010083, 1, 0);
			}
		}
	}
}

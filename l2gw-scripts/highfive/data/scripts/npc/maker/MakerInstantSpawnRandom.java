package npc.maker;

import ru.l2gw.commons.math.Rnd;
import ru.l2gw.gameserver.model.instances.L2NpcInstance;
import ru.l2gw.gameserver.model.npcmaker.DefaultMaker;
import ru.l2gw.gameserver.model.npcmaker.SpawnDefine;
import ru.l2gw.gameserver.tables.SpawnTable;

/**
 * @author: rage
 * @date: 23.02.12 19:36
 */
public class MakerInstantSpawnRandom extends DefaultMaker
{
	public String maker_name1 = "spawn_random_default1";
	public String maker_name2 = "spawn_random_default2";
	public String maker_name3 = "spawn_random_default3";
	public int maker_cnt = 1;
	public int respawn_time;

	public MakerInstantSpawnRandom(int maximum_npc, String name)
	{
		super(maximum_npc, name);
	}

	@Override
	public void onNpcDeleted(L2NpcInstance npc)
	{
		SpawnDefine deleted_def = npc.getSpawnDefine();
		int i1 = Rnd.get(maker_cnt);
		if(i1 == 0)
		{
			DefaultMaker maker0 = SpawnTable.getInstance().getNpcMaker(maker_name1);
			if(maker0 != null)
			{
				maker0.onScriptEvent(1001, deleted_def.respawn, 0);
			}
		}
		else if(i1 == 1)
		{
			DefaultMaker maker0 = SpawnTable.getInstance().getNpcMaker(maker_name2);
			if(maker0 != null)
			{
				maker0.onScriptEvent(1001, deleted_def.respawn, 0);
			}
		}
		else if(i1 == 2)
		{
			DefaultMaker maker0 = SpawnTable.getInstance().getNpcMaker(maker_name3);
			if(maker0 != null)
			{
				maker0.onScriptEvent(1001, deleted_def.respawn, 0);
			}
		}
	}
}
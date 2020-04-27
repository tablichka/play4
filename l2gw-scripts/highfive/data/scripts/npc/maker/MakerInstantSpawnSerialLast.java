package npc.maker;

import ru.l2gw.gameserver.model.instances.L2NpcInstance;
import ru.l2gw.gameserver.model.npcmaker.DefaultMaker;
import ru.l2gw.gameserver.model.npcmaker.SpawnDefine;
import ru.l2gw.gameserver.tables.SpawnTable;

/**
 * @author: rage
 * @date: 23.02.12 20:17
 */
public class MakerInstantSpawnSerialLast extends DefaultMaker
{
	public int loop_cnt = 0;
	public String maker_name = "spawn_serial_default";

	public MakerInstantSpawnSerialLast(int maximum_npc, String name)
	{
		super(maximum_npc, name);
	}

	@Override
	public void onNpcDeleted(L2NpcInstance npc)
	{
		SpawnDefine deleted_def = npc.getSpawnDefine();
		if(npc.isDead())
		{
			DefaultMaker maker0 = SpawnTable.getInstance().getNpcMaker(maker_name);
			if(maker0 != null)
			{
				maker0.onScriptEvent(1001, deleted_def.respawn, 1);
			}
		}
	}
}
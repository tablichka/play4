package npc.maker;

import ru.l2gw.gameserver.model.instances.L2NpcInstance;
import ru.l2gw.gameserver.model.npcmaker.DefaultMaker;
import ru.l2gw.gameserver.model.npcmaker.SpawnDefine;
import ru.l2gw.gameserver.tables.SpawnTable;

/**
 * @author: rage
 * @date: 23.02.12 20:09
 */
public class MakerInstantSpawnSerial extends DefaultMaker
{
	public int loop_cnt = 0;
	public String maker_name = "spawn_serial_default";

	public MakerInstantSpawnSerial(int maximum_npc, String name)
	{
		super(maximum_npc, name);
	}

	@Override
	public void onStart()
	{
		i_ai0 = loop_cnt;
		super.onStart();
	}

	@Override
	public void onNpcDeleted(L2NpcInstance npc)
	{
		SpawnDefine deleted_def = npc.getSpawnDefine();
		if(i_ai0 > 0)
		{
			i_ai0--;
			DefaultMaker maker0 = SpawnTable.getInstance().getNpcMaker(name);
			if(maker0 != null)
			{
				maker0.onScriptEvent(1001, deleted_def.respawn, 0);
			}
		}
		else if(i_ai0 == 0)
		{
			DefaultMaker maker0 = SpawnTable.getInstance().getNpcMaker(maker_name);
			if(maker0 != null)
			{
				maker0.onScriptEvent(1001, deleted_def.respawn, 1);
			}
		}
	}

	@Override
	public void onScriptEvent(int eventId, Object arg1, Object arg2)
	{
		if(eventId == 1001)
		{
			if((Integer) arg2 == 1)
			{
				i_ai0 = loop_cnt;
			}
			super.onScriptEvent(eventId, arg1, arg2);
		}
	}
}
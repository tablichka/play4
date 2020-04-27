package npc.maker;

import ru.l2gw.gameserver.model.instances.L2NpcInstance;
import ru.l2gw.gameserver.model.npcmaker.DefaultMaker;
import ru.l2gw.gameserver.model.npcmaker.SpawnDefine;
import ru.l2gw.gameserver.tables.SpawnTable;

/**
 * @author: rage
 * @date: 23.02.12 20:58
 */
public class ExclusiveSpawnNormal extends DefaultMaker
{
	public int unique_npc = 0;
	public String maker_name = "exclusive_spawn_normal_default";
	public String maker_name1 = "";
	public String maker_name2 = "";

	public ExclusiveSpawnNormal(int maximum_npc, String name)
	{
		super(maximum_npc, name);
	}

	@Override
	public void onStart()
	{
		i_ai0 = 0;
		super.onStart();
	}

	@Override
	public void onNpcCreated(L2NpcInstance npc)
	{
		if( npc.getNpcId() == unique_npc )
		{
			i_ai0 = 1;
			DefaultMaker maker0 = SpawnTable.getInstance().getNpcMaker(maker_name);
			if( maker0 != null )
			{
				maker0.onScriptEvent(1000, 1, 0);
			}
			maker0 = SpawnTable.getInstance().getNpcMaker(maker_name1);
			if( maker0 != null )
			{
				maker0.onScriptEvent(1000, 1, 0);
			}
			maker0 = SpawnTable.getInstance().getNpcMaker(maker_name2);
			if( maker0 != null )
			{
				maker0.onScriptEvent(1000, 1, 0);
			}
		}
		else if( i_ai0 == 1 )
		{
			npc.onDecay();
		}
	}

	@Override
	public void onNpcDeleted(L2NpcInstance npc)
	{
		if( npc.getNpcId() == unique_npc )
		{
			i_ai0 = 0;
			DefaultMaker maker0 = SpawnTable.getInstance().getNpcMaker(maker_name);
			if( maker0 != null )
			{
				maker0.onScriptEvent(1001, 0, 0);
			}
			maker0 = SpawnTable.getInstance().getNpcMaker(maker_name1);
			if( maker0 != null )
			{
				maker0.onScriptEvent(1001, 0, 0);
			}
			maker0 = SpawnTable.getInstance().getNpcMaker(maker_name2);
			if( maker0 != null )
			{
				maker0.onScriptEvent(1001, 0, 0);
			}
			super.onNpcDeleted(npc);
		}
		else if( i_ai0 == 0 )
		{
			SpawnDefine deleted_def = npc.getSpawnDefine();
			if( atomicIncrease(deleted_def, 1) )
			{
				deleted_def.respawn(npc, deleted_def.respawn, deleted_def.respawn_rand);
			}
		}
	}

	@Override
	public void onScriptEvent(int eventId, Object arg1, Object arg2)
	{
		i_ai0 = (Integer) arg1;
		super.onScriptEvent(eventId, arg1, arg2);
	}
}
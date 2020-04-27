package npc.maker;

import ru.l2gw.gameserver.model.instances.L2NpcInstance;
import ru.l2gw.gameserver.model.npcmaker.DefaultMaker;
import ru.l2gw.gameserver.tables.SpawnTable;

/**
 * @author: rage
 * @date: 14.10.11 21:46
 */
public class SmLevelDefault extends SmLevel
{
	public String next_maker_name = "level_switch";

	public SmLevelDefault(int maximum_npc, String name)
	{
		super(maximum_npc, name);
		on_start_spawn = 1;
	}

	@Override
	public void onStart()
	{
		i_ai0 = 1;
		super.onStart();
	}

	@Override
	public void onScriptEvent(int eventId, Object arg1, Object arg2)
	{
		if(eventId == 21140013)
		{
			i_ai0 = 0;
		}
		if(eventId == 21140012)
		{
			i_ai0 = 1;
		}
		super.onScriptEvent(eventId, arg1, arg2);
	}

	@Override
	public void onNpcDeleted(L2NpcInstance npc)
	{
		if(i_ai0 == 1)
		{
			DefaultMaker maker0 = SpawnTable.getInstance().getNpcMaker(next_maker_name);
			if(maker0 != null)
			{
				maker0.onScriptEvent(1001, 0, 0);
			}
		}
		super.onNpcDeleted(npc);
	}
}
package npc.maker;

import ru.l2gw.gameserver.model.npcmaker.DefaultMaker;
import ru.l2gw.gameserver.tables.SpawnTable;

/**
 * @author: rage
 * @date: 14.10.11 21:48
 */
public class LevelSwitch extends DefaultMaker
{
	public int spawn_time = 1000;

	public LevelSwitch(int maximum_npc, String name)
	{
		super(maximum_npc, name);
		on_start_spawn = 0;
	}

	@Override
	public void onAllNpcDeleted()
	{
		if(i_ai0 == 0)
		{
			i_ai0 = 1;
			addTimer(spawn_time, 60 * 60000);
		}
	}

	@Override
	public void onEvtTimer(int timerId, Object arg1, Object arg2)
	{
		DefaultMaker maker0 = SpawnTable.getInstance().getNpcMaker(name);
		if(timerId == spawn_time && maker0 != null)
		{
			maker0.onScriptEvent(1001, 0, 0);
			i_ai0 = 0;
		}
	}
}
package npc.maker;

import ru.l2gw.gameserver.model.instances.L2NpcInstance;
import ru.l2gw.gameserver.model.npcmaker.DefaultMaker;
import ru.l2gw.gameserver.tables.SpawnTable;

/**
 * @author: rage
 * @date: 09.10.11 21:15
 */
public class UniqueNpcKillEvent extends DefaultMaker
{
	public int unique_npc = 20130;
	public int event = 0;
	public String maker_name = "unique_npc_kill_event_default";

	public UniqueNpcKillEvent(int maximum_npc, String name)
	{
		super(maximum_npc, name);
	}

	@Override
	public void onNpcDeleted(L2NpcInstance npc)
	{
		if(npc.getNpcId() == unique_npc)
		{
			if(event == 0)
			{
				DefaultMaker maker0 = null;
				if("unique_npc_kill_event_default".equals(maker_name))
				{
					maker0 = SpawnTable.getInstance().getNpcMaker(name);
				}
				else
				{
					maker0 = SpawnTable.getInstance().getNpcMaker(maker_name);
				}
				if(maker0 != null)
				{
					maker0.onScriptEvent(1000, 0, 0);
				}
			}
			else if(event == 1)
			{
				DefaultMaker maker0 = SpawnTable.getInstance().getNpcMaker(maker_name);
				if(maker0 != null)
				{
					maker0.onScriptEvent(1001, 0, 0);
				}
			}
		}
		super.onNpcDeleted(npc);
	}
}
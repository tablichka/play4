package npc.maker;

import ru.l2gw.extensions.listeners.DoorOpenCloseListener;
import ru.l2gw.gameserver.model.instances.L2DoorInstance;
import ru.l2gw.gameserver.model.instances.L2NpcInstance;
import ru.l2gw.gameserver.model.npcmaker.DefaultMaker;
import ru.l2gw.gameserver.model.npcmaker.SpawnDefine;
import ru.l2gw.gameserver.tables.DoorTable;

/**
 * @author: rage
 * @date: 18.09.11 0:48
 */
public class CloseDoorMaker extends DefaultMaker
{
	public String DoorName = "none";
	protected int enabled = 0;

	public CloseDoorMaker(int maximum_npc, String name)
	{
		super(maximum_npc, name);
	}

	@Override
	public void onStart()
	{
		enabled = 0;
		DoorTable.getInstance().addOpenCloseListener(DoorName, new OpenCloseListener());
	}

	protected void onDoorEvent(L2DoorInstance door, int open)
	{
		if(open == 0)
		{
			if(enabled == 1)
			{
				return;
			}
			enabled = 1;
			for(SpawnDefine sd : spawn_defines)
			{
				if(debug > 0)
					_log.info(this + " onDoorEvent 0: " + sd);
				if(maximum_npc >= npc_count + sd.total && atomicIncrease(sd, sd.total))
					sd.spawn(sd.total, 0, 0);
			}
		}
		else if(enabled == 0)
		{
			return;
		}

		enabled = 0;
	}

	@Override
	public void onNpcCreated(L2NpcInstance npc)
	{
		if(enabled == 0)
		{
			npc.onDecay();
		}
	}

	@Override
	public void onNpcDeleted(L2NpcInstance npc)
	{
	}

	protected class OpenCloseListener extends DoorOpenCloseListener
	{
		@Override
		public void onOpenClose(L2DoorInstance door, int open)
		{
			onDoorEvent(door, open);
		}
	}
}

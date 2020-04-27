package npc.maker;

import ru.l2gw.gameserver.model.instances.L2NpcInstance;
import ru.l2gw.gameserver.model.npcmaker.DefaultMaker;
import ru.l2gw.gameserver.tables.SpawnTable;

import java.util.Calendar;

/**
 * @author: rage
 * @date: 19.01.12 14:30
 */
public class RoyalReqNextMaker extends RoyalRushMaker
{
	public String next_maker_name = "royal_req_next_maker_default";
	public int req_count = 0;
	public int BossMaker = 0;

	public RoyalReqNextMaker(int maximum_npc, String name)
	{
		super(maximum_npc, name);
	}

	@Override
	public void onNpcDeleted(L2NpcInstance npc)
	{
		if(BossMaker == 1)
		{
			if(npc.getNpcId() == 25339 || npc.getNpcId() == 25342 || npc.getNpcId() == 25346 || npc.getNpcId() == 25349)
			{
				DefaultMaker maker0 = SpawnTable.getInstance().getNpcMaker(next_maker_name);
				if(maker0 != null)
				{
					maker0.onScriptEvent(1002, 0, 0);
				}
			}
		}
		else if(npc_count == req_count)
		{
			int i1 = Calendar.getInstance().get(Calendar.MINUTE);
			if(i1 >= 0 && i1 <= 50)
			{
				DefaultMaker maker0 = SpawnTable.getInstance().getNpcMaker(next_maker_name);
				if(maker0 != null)
				{
					maker0.onScriptEvent(1001, 0, 0);
				}
			}
		}
	}
}
package npc.maker;

import ru.l2gw.commons.math.Rnd;
import ru.l2gw.gameserver.model.instances.L2NpcInstance;
import ru.l2gw.gameserver.model.npcmaker.DefaultMaker;
import ru.l2gw.gameserver.tables.SpawnTable;

/**
 * @author: rage
 * @date: 14.10.11 21:51
 */
public class KnightRoom extends DefaultMaker
{
	public String maker_name = "rune13_2315_06m2";

	public KnightRoom(int maximum_npc, String name)
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
	public void onNpcDeleted(L2NpcInstance npc)
	{
		i_ai0++;
		if(Rnd.get(100) > 70 && 1000 <= i_ai0 && i_ai0 < 2000)
		{
			DefaultMaker maker0 = SpawnTable.getInstance().getNpcMaker(maker_name);
			maker0.onScriptEvent(1001, 0, 0);
			i_ai0 = 0;
		}
		else if(Rnd.get(100) < 80 && 2000 <= i_ai0 && i_ai0 < 3000)
		{
			DefaultMaker maker0 = SpawnTable.getInstance().getNpcMaker(maker_name);
			maker0.onScriptEvent(1001, 0, 0);
			i_ai0 = 0;
		}
		else if(Rnd.get(100) < 90 && 3000 <= i_ai0)
		{
			DefaultMaker maker0 = SpawnTable.getInstance().getNpcMaker(maker_name);
			maker0.onScriptEvent(1001, 0, 0);
			i_ai0 = 0;
		}
		super.onNpcDeleted(npc);
	}
}
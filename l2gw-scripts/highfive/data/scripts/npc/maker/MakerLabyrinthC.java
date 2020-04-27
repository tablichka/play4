package npc.maker;

import ru.l2gw.gameserver.instancemanager.InstanceManager;
import ru.l2gw.gameserver.model.entity.instance.Instance;
import ru.l2gw.gameserver.model.instances.L2NpcInstance;
import ru.l2gw.gameserver.model.npcmaker.DefaultMaker;

/**
 * @author: rage
 * @date: 13.10.11 20:14
 */
public class MakerLabyrinthC extends InzoneMaker
{
	public String d_maker_name = "kamaloka03_1812_001m5";

	public MakerLabyrinthC(int maximum_npc, String name)
	{
		super(maximum_npc, name);
	}

	@Override
	public void onNpcDeleted(L2NpcInstance npc)
	{
		Instance inst = InstanceManager.getInstance().getInstanceByReflection(reflectionId);
		DefaultMaker maker0 = inst.getMaker(d_maker_name);
		if(maker0 != null)
		{
			maker0.onScriptEvent(1624004, 0, 0);
		}
	}
}

package npc.maker;

import ru.l2gw.gameserver.instancemanager.InstanceManager;
import ru.l2gw.gameserver.model.entity.instance.Instance;
import ru.l2gw.gameserver.model.instances.L2NpcInstance;
import ru.l2gw.gameserver.model.npcmaker.DefaultMaker;

/**
 * @author: rage
 * @date: 13.10.11 20:09
 */
public class MakerLabyrinthB extends InzoneMaker
{
	public String d_maker_name = "kamaloka03_1812_001m5";

	public MakerLabyrinthB(int maximum_npc, String name)
	{
		super(maximum_npc, name);
	}

	@Override
	public void onNpcDeleted(L2NpcInstance npc)
	{
		if(npc.isDead())
		{
			Instance inst = InstanceManager.getInstance().getInstanceByReflection(reflectionId);
			DefaultMaker maker0 = inst.getMaker(d_maker_name);
			if(maker0 != null)
			{
				maker0.onScriptEvent(1624003, 0, 0);
			}
		}
	}
}

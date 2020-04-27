package npc.model;

import ru.l2gw.gameserver.model.L2Character;
import ru.l2gw.gameserver.model.instances.L2DoorInstance;
import ru.l2gw.gameserver.model.instances.L2MonsterInstance;
import ru.l2gw.gameserver.model.instances.L2NpcInstance;
import ru.l2gw.gameserver.templates.L2NpcTemplate;

/**
 * @author rage
 * @date 08.09.2009 16:46:44
 */
public class FrintDarkPlayerInstance extends L2MonsterInstance
{
	public FrintDarkPlayerInstance(int objectId, L2NpcTemplate template, long bossIndex, long p1, long p2, long p3)
	{
		super(objectId, template, bossIndex, p1, p2, p3);
	}

	@Override
	public void doDie(L2Character killer)
	{
		super.doDie(killer);

		for(L2NpcInstance cha : getKnownNpc(2500))
			if(cha != null && cha.getNpcId() == getNpcId() && !cha.isDead())
				return;

		for(L2DoorInstance door : getSpawn().getInstance().getDoors())
			if(door.getDoorId() >= 17130061 && door.getDoorId() <= 17130070 && !door.isOpen())
				door.openMe();
	}
}

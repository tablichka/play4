package npc.model;

import ru.l2gw.gameserver.model.instances.L2MonsterInstance;
import ru.l2gw.gameserver.templates.L2NpcTemplate;

/**
 * @author rage
 * @date 16.08.2010 17:25:02
 */
public class KashaEyeInstance extends L2MonsterInstance
{
	public KashaEyeInstance(int objectId, L2NpcTemplate template, long bossIndex, long p1, long p2, long p3)
	{
		super(objectId, template, bossIndex, p1, p2, p3);
	}

	@Override
	public boolean isMovementDisabled()
	{
		return true;
	}
}

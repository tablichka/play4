package npc.model;

import ru.l2gw.commons.math.Rnd;
import ru.l2gw.gameserver.model.instances.L2RaidBossInstance;
import ru.l2gw.gameserver.templates.L2NpcTemplate;

/**
 * @author rage
 * @date 01.09.2009 9:34:50
 */
public class Kama66BossInstance extends L2RaidBossInstance
{
	public Kama66BossInstance(int objectId, L2NpcTemplate template, long bossIndex, long p1, long p2, long p3)
	{
		super(objectId, template, bossIndex, p1, p2, p3);
	}

	@Override
	protected int getMaintenanceInterval()
	{
		return Rnd.get(60000, 180000);
	}
}

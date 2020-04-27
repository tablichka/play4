package npc.model;

import ru.l2gw.gameserver.model.instances.L2MonsterInstance;
import ru.l2gw.gameserver.templates.L2NpcTemplate;

/**
 * User: ic
 * Date: 02.11.2009
 */
public class GatekeeperZombieInstance extends L2MonsterInstance
{
	public GatekeeperZombieInstance(int objectId, L2NpcTemplate template, long bossIndex, long p1, long p2, long p3)
	{
		super(objectId, template, bossIndex, p1, p2, p3);
	}

	@Override
	public boolean isFearImmune()
	{
		return true;
	}

	@Override
	public boolean isLethalImmune()
	{
		return true;
	}

	@Override
	public boolean isInvul()
	{
		return true;
	}

	@Override
	public boolean isMovementDisabled()
	{
		return true;
	}
}

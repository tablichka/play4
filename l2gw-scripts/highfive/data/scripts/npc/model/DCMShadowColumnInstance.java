package npc.model;

import ru.l2gw.gameserver.model.instances.L2MonsterInstance;
import ru.l2gw.gameserver.templates.L2NpcTemplate;

/**
 * User: ic
 * Date: 21.10.2009
 */
public class DCMShadowColumnInstance extends L2MonsterInstance
{
	public DCMShadowColumnInstance(int objectId, L2NpcTemplate template, long bossIndex, long p1, long p2, long p3)
	{
		super(objectId, template, bossIndex, p1, p2, p3);
	}
	
	@Override
	public boolean isMovementDisabled()
	{
		return true;
	}

	@Override
	public boolean isLethalImmune()
	{
		return true;
	}

	@Override
	public boolean isFearImmune()
	{
		return true;
	}

	@Override
	public boolean isAttackingDisabled()
	{
		return true;
	}

}

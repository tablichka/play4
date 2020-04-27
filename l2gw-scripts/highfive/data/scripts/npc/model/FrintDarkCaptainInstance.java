package npc.model;

import ru.l2gw.gameserver.model.L2Character;
import ru.l2gw.gameserver.model.instances.L2MonsterInstance;
import ru.l2gw.gameserver.model.instances.L2NpcInstance;
import ru.l2gw.gameserver.templates.L2NpcTemplate;

/**
 * @author rage
 * @date 08.09.2009 16:54:16
 */
public class FrintDarkCaptainInstance extends L2MonsterInstance
{
	public FrintDarkCaptainInstance(int objectId, L2NpcTemplate template, long bossIndex, long p1, long p2, long p3)
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

		getSpawn().getInstance().stopEventSpawn("frint_room2", true);
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

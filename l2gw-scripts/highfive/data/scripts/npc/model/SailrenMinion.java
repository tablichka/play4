package npc.model;

import bosses.SailrenManager;
import ru.l2gw.gameserver.model.L2Character;
import ru.l2gw.gameserver.model.instances.L2MonsterInstance;
import ru.l2gw.gameserver.templates.L2NpcTemplate;

/**
 * @author rage
 * @date 13.10.2009 15:56:14
 */
public class SailrenMinion extends L2MonsterInstance
{
	public SailrenMinion(int objectId, L2NpcTemplate template, long bossIndex, long p1, long p2, long p3)
	{
		super(objectId, template, bossIndex, p1, p2, p3);
	}

	@Override
	public void doDie(L2Character killer)
	{
		super.doDie(killer);
		SailrenManager.getInstance().notifyDead();
	}

	@Override
	public void decreaseHp(double damage, L2Character attacker, boolean directHp, boolean reflect)
	{
		super.decreaseHp(damage, attacker, directHp, reflect);
		SailrenManager.getInstance().updateLastAttack();
	}
}

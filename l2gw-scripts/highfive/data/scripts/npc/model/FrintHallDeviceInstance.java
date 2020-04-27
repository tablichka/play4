package npc.model;

import instances.FrintezzaBattleInstance;
import ru.l2gw.gameserver.ai.CtrlEvent;
import ru.l2gw.gameserver.model.L2Character;
import ru.l2gw.gameserver.model.instances.L2DoorInstance;
import ru.l2gw.gameserver.model.instances.L2MonsterInstance;
import ru.l2gw.gameserver.model.instances.L2NpcInstance;
import ru.l2gw.gameserver.templates.L2NpcTemplate;

/**
 * @author rage
 * @date 08.09.2009 16:03:49
 */
public class FrintHallDeviceInstance extends L2MonsterInstance
{
	private boolean attacked = false;
	public FrintHallDeviceInstance(int objectId, L2NpcTemplate template, long bossIndex, long p1, long p2, long p3)
	{
		super(objectId, template, bossIndex, p1, p2, p3);
	}

	@Override
	public void decreaseHp(double damage, L2Character attacker, boolean directHp, boolean reflect)
	{
		super.decreaseHp(damage, attacker, directHp, reflect);
		for(L2NpcInstance npc : getKnownNpc(2500))
			if(npc.isMonster() && !npc.isDead())
				npc.getAI().notifyEvent(CtrlEvent.EVT_AGGRESSION, attacker, 100);

		if(!attacked)
		{
			attacked = true;
			for(L2DoorInstance door : getSpawn().getInstance().getDoors())
				if(door.getDoorId() >= 17130051 && door.getDoorId() <= 17130058 && !door.isOpen())
					door.openMe();
		}
	}

	@Override
	public void doDie(L2Character killer)
	{
		super.doDie(killer);
		((FrintezzaBattleInstance) getSpawn().getInstance()).hallDeviceDisabled();
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

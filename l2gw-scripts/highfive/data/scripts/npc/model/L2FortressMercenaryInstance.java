package npc.model;

import ru.l2gw.gameserver.instancemanager.SiegeManager;
import ru.l2gw.gameserver.model.L2Character;
import ru.l2gw.gameserver.model.L2Clan;
import ru.l2gw.gameserver.model.L2Playable;
import ru.l2gw.gameserver.model.L2Player;
import ru.l2gw.gameserver.model.entity.siege.SiegeUnit;
import ru.l2gw.gameserver.model.instances.L2MonsterInstance;
import ru.l2gw.gameserver.templates.L2NpcTemplate;

public class L2FortressMercenaryInstance extends L2MonsterInstance
{
	public L2FortressMercenaryInstance(int objectId, L2NpcTemplate template, long bossIndex, long p1, long p2, long p3)
	{
		super(objectId, template, bossIndex, p1, p2, p3);
	}

	@Override
	public int getAggroRange()
	{
		return 80;
	}

	@Override
	public void doDie(L2Character killer)
	{
		getBuilding(1).getSiege().endSiege();
		super.doDie(killer);
	}

	@Override
	public boolean isAttackable(L2Character attacker, boolean forceUse, boolean sendMessage)
	{
		L2Player player = attacker.getPlayer();
		if(player == null)
			return false;
		L2Clan clan = player.getClan();
		return !(clan != null && SiegeManager.getSiege(this) == clan.getSiege() && !clan.isDefender());
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
	public boolean canMoveToHome()
	{
		return true;
	}

	@Override
	public boolean isInvul()
	{
		return false;
	}

	@Override
	public void decreaseHp(double damage, L2Character attacker, boolean directHp, boolean reflect)
	{
		L2Player player = null;
		if(attacker instanceof L2Playable)
			player = ((L2Playable) attacker).getPlayer();

		SiegeUnit fort = getBuilding(1);
		if(fort != null && player != null && fort.getSiege().isInProgress() && fort.getSiege().checkIsDefender(player.getClanId()))
			super.decreaseHp(damage, attacker, directHp, reflect);

	}


}
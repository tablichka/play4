package ai.base;

import ru.l2gw.gameserver.model.L2Character;
import ru.l2gw.gameserver.model.entity.category.CategoryManager;
import ru.l2gw.gameserver.model.instances.L2NpcInstance;

/**
 * @author: rage
 * @date: 21.09.11 19:00
 */
public class PartyLeader extends PartyLeaderParamWarrior
{

	public PartyLeader(L2Character actor)
	{
		super(actor);
	}

	@Override
	protected void onEvtSpawn()
	{
		_thisActor.weight_point = 10;
		addTimer(1007, 120000);
		super.onEvtSpawn();
	}

	@Override
	protected void onEvtTimer(int timerId, Object arg1, Object arg2)
	{
		if( timerId == 1007 )
		{
			if( !_thisActor.inMyTerritory(_thisActor) && !_thisActor.isInCombat() )
			{
				_thisActor.teleToLocation( _thisActor.getSpawnedLoc().getX(), _thisActor.getSpawnedLoc().getY(), _thisActor.getSpawnedLoc().getZ());
				removeAllAttackDesire();
			}
			addTimer(1007, 120000);
		}
		super.onEvtTimer(timerId, arg1, arg2);
	}

	@Override
	protected void onEvtPartyAttacked(L2Character attacker, L2Character victim, int damage)
	{
		if( victim != _thisActor )
		{
			if( attacker.isPlayer() || CategoryManager.isInCategory(12, attacker.getNpcId()) )
			{
				float f0 = 0;
				if( SetHateGroup >= 0 )
				{
					if( CategoryManager.isInCategory(SetHateGroup, attacker.getActiveClass()) )
					{
						f0 += SetHateGroupRatio;
					}
				}
				if( attacker.getActiveClass() == SetHateOccupation )
				{
					f0 += SetHateOccupationRatio;
				}
				if( SetHateRace == attacker.getPlayer().getRace().ordinal() )
				{
					f0 += SetHateRaceRatio;
				}
				f0 = damage / (_thisActor.getLevel() + 7f) + f0 / 100 * damage / (_thisActor.getLevel() + 7f);
				addAttackDesire(attacker, 1, (long) (f0 * damage * ((L2NpcInstance) victim).weight_point * 10));
			}
		}
	}

	@Override
	protected void onEvtPartyDead(L2NpcInstance partyPrivate)
	{
		if( partyPrivate != _thisActor && partyPrivate.getMinionData().minionRespawn > 0 )
		{
			_thisActor.respawnPrivate(partyPrivate, partyPrivate.weight_point, partyPrivate.getMinionData().minionRespawn);
		}
	}
}

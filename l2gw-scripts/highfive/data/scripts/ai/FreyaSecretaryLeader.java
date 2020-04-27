package ai;

import ai.base.PartyLeaderAggressive;
import ru.l2gw.gameserver.ai.CtrlEvent;
import ru.l2gw.gameserver.model.L2Character;
import ru.l2gw.gameserver.model.entity.category.CategoryManager;
import ru.l2gw.gameserver.model.instances.L2NpcInstance;
import ru.l2gw.gameserver.model.npcmaker.DefaultMaker;
import ru.l2gw.gameserver.tables.SpawnTable;

/**
 * @author: rage
 * @date: 21.09.11 20:03
 */
public class FreyaSecretaryLeader extends PartyLeaderAggressive
{
	public FreyaSecretaryLeader(L2Character actor)
	{
		super(actor);
	}

	@Override
	protected void onEvtSpawn()
	{
		_thisActor.i_ai1 = 0;
		super.onEvtSpawn();
	}

	@Override
	protected void onEvtPartyAttacked(L2Character attacker, L2Character victim, int damage)
	{
		if( victim != _thisActor && victim.getCurrentHp() < victim.getMaxHp() / 3 && _thisActor.getCurrentHp() > 1 && _thisActor.i_ai1 < 15 )
		{
			_thisActor.createOnePrivate(18327, "FreyaSecretaryPrivate", 0, 0, victim.getX(), victim.getY(), victim.getZ(), 0, 1000, getStoredIdFromCreature(attacker), 0);
			_thisActor.createOnePrivate(18327, "FreyaSecretaryPrivate", 0, 0, victim.getX(), victim.getY(), victim.getZ(), 0, 1000, getStoredIdFromCreature(attacker), 0);
			victim.getAI().notifyEvent(CtrlEvent.EVT_SCRIPT_EVENT, 10023, 0);
			_thisActor.i_ai1++;
		}
		super.onEvtPartyAttacked(attacker, victim, damage);
	}

	@Override
	protected void onEvtSeeCreature(L2Character creature)
	{
		if( !creature.isPlayer() && !CategoryManager.isInCategory(12, creature.getNpcId()) )
		{
			return;
		}
		if( _thisActor.getLifeTime() > SeeCreatureAttackerTime && _thisActor.inMyTerritory(_thisActor) )
		{
			addAttackDesire(creature, 1, 200);
		}
		super.onEvtSeeCreature(creature);
	}

	@Override
	protected void onEvtPartyDead(L2NpcInstance partyPrivate)
	{
	}

	@Override
	protected void onEvtDead(L2Character killer)
	{
		DefaultMaker maker0 = SpawnTable.getInstance().getNpcMaker("schuttgart13_mb2314_05m1");
		if( maker0 != null )
		{
			maker0.onScriptEvent(10005, 0, 0);
		}
		broadcastScriptEvent(11036, 1, null, 8000);
		super.onEvtDead(killer);
	}
}
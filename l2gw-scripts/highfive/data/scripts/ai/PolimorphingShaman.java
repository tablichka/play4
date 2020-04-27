package ai;

import ru.l2gw.gameserver.ai.Fighter;
import ru.l2gw.gameserver.model.L2Character;
import ru.l2gw.gameserver.model.L2Skill;
import ru.l2gw.gameserver.model.L2Spawn;
import ru.l2gw.gameserver.model.instances.L2NpcInstance;
import ru.l2gw.gameserver.tables.NpcTable;

/**
 * AI полиморфного шамана ID: 21258, превращающегося в тигра ID: 21259 при ударе
 *
 * @author SYS
 */
public class PolimorphingShaman extends Fighter
{
	private static final int TIGER_ID = 21259;
	private boolean _polymorphed;

	public PolimorphingShaman(L2Character actor)
	{
		super(actor);
	}

	@Override
	protected void onEvtSpawn()
	{
		super.onEvtSpawn();
		_polymorphed = false;
	}

	@Override
	protected void onEvtAttacked(L2Character attacker, int damage, L2Skill skill)
	{
		if(_polymorphed)
			return;
		_polymorphed = true;
		try
		{
			L2Spawn spawn = new L2Spawn(NpcTable.getTemplate(TIGER_ID));
			spawn.setLoc(_thisActor.getLoc());
			L2NpcInstance npc = spawn.doSpawn(true);
			npc.getAI().setGlobalAggro(-2);
			npc.addDamage(attacker, damage);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}

		_thisActor.decayMe();
		_thisActor.doDie(_thisActor);
	}
}
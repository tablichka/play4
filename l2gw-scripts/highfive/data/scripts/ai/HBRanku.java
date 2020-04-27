package ai;

import ru.l2gw.gameserver.ai.Fighter;
import ru.l2gw.gameserver.model.L2Character;
import ru.l2gw.gameserver.model.L2Skill;
import ru.l2gw.gameserver.model.entity.instance.Instance;
import ru.l2gw.gameserver.model.instances.L2MonsterInstance;
import ru.l2gw.gameserver.model.instances.L2NpcInstance;
import ru.l2gw.util.Location;
import ru.l2gw.util.MinionList;

/**
 * @author rage
 * @date 27.10.2010 16:46:44
 */
public class HBRanku extends Fighter
{
	private static final int CUBIC_ID = 32375;
	private static final int SCAPEGOAT_ID = 32305;
	private static final Location CUBIC_POSITION = new Location(-19016, 278312, -15040, 0);
	private long _lastAttacked;

	public HBRanku(L2Character actor)
	{
		super(actor);
	}

	@Override
	protected void onEvtAttacked(L2Character attacker, int damage, L2Skill skill)
	{
		_lastAttacked = System.currentTimeMillis();
		super.onEvtAttacked(attacker, damage, skill);
	}

	@Override
	protected boolean thinkActive()
	{
		if(_thisActor != null && !_thisActor.isDead() && _lastAttacked != 0 && _lastAttacked + 5 * 60000 < System.currentTimeMillis())
		{
			Instance inst = _thisActor.getSpawn().getInstance();
			if(inst != null)
				inst.stopInstance();

			return true;
		}

		return super.thinkActive();
	}

	@Override
	protected void thinkAttack()
	{
		if(_thisActor == null || _thisActor.isDead())
			return;

		// Уменьшаем ХП у миньонов-носильщиков во время боя
		MinionList ml = ((L2MonsterInstance) _thisActor).getMinionList();
		if(ml != null && ml.hasMinions())
			for(L2NpcInstance m : ml.getSpawnedMinions())
				if(m.getNpcId() == SCAPEGOAT_ID && !m.isDead())
					m.reduceHp(m.getMaxHp() / 30, _thisActor, true, false);

		super.thinkAttack();
	}

	@Override
	protected void onEvtDead(L2Character killer)
	{
		super.onEvtDead(killer);

		Instance inst = _thisActor.getSpawn().getInstance();
		if(inst != null)
		{
			inst.addSpawn(CUBIC_ID, CUBIC_POSITION, 0);
			inst.successEnd();
		}
	}
}

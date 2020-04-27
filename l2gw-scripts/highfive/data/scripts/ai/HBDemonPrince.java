package ai;

import ru.l2gw.gameserver.ai.Fighter;
import ru.l2gw.gameserver.model.L2Character;
import ru.l2gw.gameserver.model.L2Skill;
import ru.l2gw.gameserver.model.entity.instance.Instance;
import ru.l2gw.gameserver.tables.SkillTable;
import ru.l2gw.util.Location;

/**
 * @author rage
 * @date 27.10.2010 16:27:05
 */
public class HBDemonPrince extends Fighter
{
	private static final L2Skill _ud = SkillTable.getInstance().getInfo(5044, 3);
	private static final int CUBIC_ID = 32374;
	private static final Location CUBIC_POSITION = new Location(-22200, 278328, -8256, 0);
	private long _lastAttacked;
	private boolean _udUsed = true;

	public HBDemonPrince(L2Character actor)
	{
		super(actor);
		_useUD = true;
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
	protected boolean createNewTask()
	{
		// Удаляем все задания
		clearTasks();

		if(_udUsed && _thisActor.getCurrentHp() < _thisActor.getMaxHp() * 0.10)
		{
			_udUsed = false;
			addUseSkillDesire(_thisActor, _ud, 1, 1, DEFAULT_DESIRE * 100);
			return true;
		}

		return super.createNewTask();
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

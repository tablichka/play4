package ai;

import ru.l2gw.commons.math.Rnd;
import ru.l2gw.gameserver.ai.Fighter;
import ru.l2gw.gameserver.model.L2Character;
import ru.l2gw.gameserver.model.L2Skill;
import ru.l2gw.gameserver.model.instances.L2NpcInstance;

/**
 * @author rage
 * @date 18.08.2010 18:39:58
 */
public class StakatoCannibal extends Fighter
{
	private static L2Skill _drain, _heal;
	private long _nextDrain;

	public StakatoCannibal(L2Character actor)
	{
		super(actor);
		_drain = _thisActor.getTemplate().getSkillsByType("SPECIAL1")[0];
		_heal = _thisActor.getTemplate().getSkillsByType("SPECIAL2")[0];
	}

	@Override
	protected boolean createNewTask()
	{
		if(_thisActor.hasMinions())
		{
			L2NpcInstance minion = _thisActor.getMinionList().getSpawnedMinions().peek();
			if(minion != null && !minion.isDead())
			{
				if(_thisActor.getCurrentHp() < _thisActor.getMaxHp() * 0.75 && _nextDrain < System.currentTimeMillis() && _thisActor.hasMinions() && !_thisActor.isSkillDisabled(_drain.getId()))
				{
					addUseSkillDesire(minion, _drain, 1, 1, DEFAULT_DESIRE * 100);
					return true;
				}

				if(minion.getCurrentHp() < minion.getMaxHp() * 0.5 && _thisActor.getCurrentHp() > _thisActor.getMaxHp() * 0.5 && !_thisActor.isSkillDisabled(_heal.getId()) && Rnd.chance(50))
				{
					addUseSkillDesire(minion, _drain, 1, 1, DEFAULT_DESIRE * 100);
					return true;
				}
			}
		}

		return super.createNewTask();
	}

	@Override
	protected void onEvtFinishCasting(L2Skill skill)
	{
		super.onEvtFinishCasting(skill);
		if(skill != null && skill.getId() == _drain.getId())
		{
			if(_thisActor.getCurrentHp() < _thisActor.getMaxHp() * 0.25)
				_nextDrain = System.currentTimeMillis() + Rnd.get(20, 40) * 1000;
			else if(_thisActor.getCurrentHp() < _thisActor.getMaxHp() * 0.5)
				_nextDrain = System.currentTimeMillis() + Rnd.get(30, 60) * 1000;
			else if(_thisActor.getCurrentCp() < _thisActor.getMaxHp() * 0.75)
				_nextDrain = System.currentTimeMillis() + Rnd.get(40, 80) * 1000;
			else
				_nextDrain = System.currentTimeMillis() + Rnd.get(60, 90) * 1000;
		}
	}
}

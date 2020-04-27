package ai;

import ai.base.DefaultNpc;
import ru.l2gw.gameserver.model.L2Character;
import ru.l2gw.gameserver.model.L2Skill;
import ru.l2gw.gameserver.tables.SkillTable;

/**
 * @author: rage
 * @date: 19.12.11 21:30
 */
public class WarriorMimic extends DefaultNpc
{
	public int CreviceOfDiminsion = 0;

	public WarriorMimic(L2Character actor)
	{
		super(actor);
	}

	@Override
	protected void onEvtSpawn()
	{
	}

	@Override
	protected void onEvtSeeCreature(L2Character creature)
	{
		if(creature.isPlayer())
		{
			int i0;
			if(creature.getLevel() >= 1 && creature.getLevel() < 36)
			{
				i0 = 271515652;
			}
			else if(creature.getLevel() >= 36 && creature.getLevel() < 45)
			{
				i0 = 271515653;
			}
			else if(creature.getLevel() >= 45 && creature.getLevel() < 57)
			{
				i0 = 271515654;
			}
			else if(creature.getLevel() >= 57 && creature.getLevel() < 72)
			{
				i0 = 271515655;
			}
			else
			{
				i0 = 271515656;
			}
			if(SkillTable.mpConsume(i0) < _thisActor.getCurrentMp() && SkillTable.hpConsume(i0) < _thisActor.getCurrentHp() && !_thisActor.isSkillDisabledEx(i0))
			{
				addUseSkillDesire(_thisActor, i0, 0, 1, 1000000);
			}
			addTimer(2001, 5000);
		}
	}

	@Override
	protected void onEvtTimer(int timerId, Object arg1, Object arg2)
	{
		if(timerId == 2001)
		{
			_thisActor.onDecay();
		}
	}

	@Override
	protected void onEvtAttacked(L2Character attacker, int damage, L2Skill skill)
	{
		if(attacker == null)
			return;

		if(CreviceOfDiminsion != 0)
		{
			if(!_thisActor.inMyTerritory(attacker))
			{
				removeAttackDesire(attacker);
				return;
			}
		}
	}
}
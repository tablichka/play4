package ai;

import ai.base.WarriorUseSkill;
import ru.l2gw.gameserver.model.L2Character;
import ru.l2gw.gameserver.model.L2ObjectsStorage;
import ru.l2gw.gameserver.model.L2Skill;
import ru.l2gw.gameserver.tables.SkillTable;

/**
 * @author: rage
 * @date: 03.09.11 16:15
 */
public class AiMesmerDrake extends WarriorUseSkill
{
	public L2Skill sleepSkill = SkillTable.getInstance().getInfo(449118209);
	public L2Skill holdSkill = SkillTable.getInstance().getInfo(449183745);
	public int	SkillTimer = 20100506;

	public AiMesmerDrake(L2Character actor)
	{
		super(actor);
	}

	@Override
	protected void onEvtSpawn()
	{
		_thisActor.i_ai2 = 0;
		_thisActor.c_ai0 = 0;
		super.onEvtSpawn();
	}

	@Override
	protected void onEvtSeeCreature(L2Character creature)
	{
		if(creature.isPlayer() && _thisActor.i_ai2 == 0)
		{
			addUseSkillDesire(creature, sleepSkill, 0, 1, 99999999900000000L);
			_thisActor.c_ai0 = creature.getStoredId();
			addTimer(SkillTimer, 10000);
			_thisActor.i_ai2 = 1;
		}
	}

	@Override
	protected void onEvtTimer(int timerId, Object arg1, Object arg2)
	{
		if(timerId == SkillTimer)
		{
			L2Character target = L2ObjectsStorage.getAsCharacter(_thisActor.c_ai0);
			if(target != null)
				addUseSkillDesire(target, holdSkill, 0, 1, 99999999900000000L);
		}
		else
			super.onEvtTimer(timerId, arg1, arg2);
	}
}

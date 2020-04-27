package ai;

import ru.l2gw.gameserver.ai.DefaultAI;
import ru.l2gw.gameserver.model.L2Character;
import ru.l2gw.gameserver.model.L2Skill;
import ru.l2gw.gameserver.tables.SkillTable;

/**
 * @author: rage
 * @date: 06.09.11 13:42
 */
public class AiGuardianHelper extends DefaultAI
{
	public L2Skill SimbolSkill1 = SkillTable.getInstance().getInfo(442040321);
	public L2Skill SimbolSkill2 = SkillTable.getInstance().getInfo(442105857);
	public int DESPAWN_TIMER = 1112;

	public AiGuardianHelper(L2Character actor)
	{
		super(actor);
	}

	@Override
	protected void onEvtSpawn()
	{
		if(_thisActor.param1 == 0)
		{
			addUseSkillDesire(_thisActor, SimbolSkill1, 1, 1, 100000);
		}
		else if(_thisActor.param1 == 1)
		{
			addUseSkillDesire(_thisActor, SimbolSkill2, 1, 1, 100000);
		}
		addTimer(DESPAWN_TIMER, 3 * 60 * 1000);
		super.onEvtSpawn();
	}

	@Override
	protected void onEvtTimer(int timerId, Object arg1, Object arg2)
	{
		if(timerId == DESPAWN_TIMER)
		{
			_thisActor.onDecay();
		}
	}
}

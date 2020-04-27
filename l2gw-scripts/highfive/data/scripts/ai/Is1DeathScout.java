package ai;

import ai.base.IsBasic;
import ru.l2gw.gameserver.model.L2Character;
import ru.l2gw.gameserver.model.L2Skill;
import ru.l2gw.gameserver.tables.SkillTable;

/**
 * @author: rage
 * @date: 13.12.11 20:39
 */
public class Is1DeathScout extends IsBasic
{
	public L2Skill cruel_expunge1 = SkillTable.getInstance().getInfo(385286145);

	public Is1DeathScout(L2Character actor)
	{
		super(actor);
		Skill01_ID = SkillTable.getInstance().getInfo(385220609);
		Skill01_Probability = 20;
		Skill01_Target_Type = 0;
		Skill02_ID = SkillTable.getInstance().getInfo(386269185);
		Skill02_Probability = 5;
		Skill02_Target_Type = 0;
	}

	@Override
	protected void onEvtTimer(int timerId, Object arg1, Object arg2)
	{
		if( timerId == 8001 )
		{
			if( _thisActor.getMostHated() != null )
			{
				if(SkillTable.getAbnormalLevel(_thisActor.getMostHated(), Skill01_ID) > 0 )
				{
					addUseSkillDesire(_thisActor.getMostHated(), cruel_expunge1, 1, 0, 100000000);
				}
			}
		}
		super.onEvtTimer(timerId, arg1, arg2);
	}
}
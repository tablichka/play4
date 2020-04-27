package ai;

import ai.base.DefaultNpc;
import ru.l2gw.gameserver.model.L2Character;
import ru.l2gw.gameserver.model.L2Skill;
import ru.l2gw.gameserver.tables.SkillTable;

/**
 * @author: rage
 * @date: 27.09.11 22:15
 */
public class FreyaSpelling extends DefaultNpc
{
	public L2Skill Skill_Display = SkillTable.getInstance().getInfo(422772737);

	public FreyaSpelling(L2Character actor)
	{
		super(actor);
	}

	@Override
	protected void onEvtScriptEvent(int eventId, Object arg1, Object arg2)
	{
		if( eventId == 23140020 )
		{
			_thisActor.onDecay();
		}
	}
}

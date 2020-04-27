package ai;

import ru.l2gw.gameserver.ai.CtrlEvent;
import ru.l2gw.gameserver.model.L2Character;
import ru.l2gw.gameserver.model.L2ObjectsStorage;
import ru.l2gw.gameserver.model.L2Skill;
import ru.l2gw.gameserver.tables.SkillTable;

/**
 * @author: rage
 * @date: 24.09.11 19:06
 */
public class ChannelObject extends Citizen
{
	public L2Skill channeling_skill = SkillTable.getInstance().getInfo(441843713);

	public ChannelObject(L2Character actor)
	{
		super(actor);
	}

	@Override
	protected void onEvtSpawn()
	{
		if(_thisActor.param1 == 1000)
		{
			L2Character c0 = L2ObjectsStorage.getAsCharacter(_thisActor.param2);
			if(c0 != null)
			{
				_thisActor.altUseSkill(channeling_skill, c0);
			}
			int i0 = (int) _thisActor.param3;
			switch(i0)
			{
				case 1:
					_thisActor.changeNpcState(1);
					break;
				case 2:
					_thisActor.changeNpcState(2);
					break;
				case 3:
					_thisActor.changeNpcState(3);
					break;
			}
		}
	}

	@Override
	protected void onEvtDead(L2Character killer)
	{
		L2Character c0 = L2ObjectsStorage.getAsCharacter(_thisActor.param2);
		if(c0 != null)
		{
			_thisActor.notifyAiEvent(c0, CtrlEvent.EVT_SCRIPT_EVENT, 15001, 0, null);
		}
		super.onEvtDead(killer);
	}

	@Override
	protected void onEvtScriptEvent(int eventId, Object arg1, Object arg2)
	{
		if(eventId == 15007)
		{
			_thisActor.onDecay();
		}
	}
}

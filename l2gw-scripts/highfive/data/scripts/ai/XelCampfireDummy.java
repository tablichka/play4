package ai;

import ru.l2gw.gameserver.ai.CtrlEvent;
import ru.l2gw.gameserver.ai.L2CharacterAI;
import ru.l2gw.gameserver.model.L2Character;
import ru.l2gw.gameserver.model.L2Skill;
import ru.l2gw.gameserver.model.instances.L2NpcInstance;
import ru.l2gw.gameserver.tables.SkillTable;

/**
 * @author: rage
 * @date: 24.09.11 5:25
 */
public class XelCampfireDummy extends L2CharacterAI
{
	private L2NpcInstance _thisActor;

	public XelCampfireDummy(L2Character actor)
	{
		super(actor);
		_thisActor = (L2NpcInstance) actor;
	}

	@Override
	protected void onEvtSpawn()
	{
		_thisActor.notifyAiEvent(_thisActor.getLeader(), CtrlEvent.EVT_SCRIPT_EVENT, 2219022, _thisActor.getStoredId(), null);
	}

	@Override
	protected void onEvtSpelled(L2Skill skill, L2Character caster)
	{
		if( skill.getId() == 9075 )
		{
			_thisActor.altUseSkill(SkillTable.getInstance().getInfo(438304769), _thisActor);
			broadcastScriptEvent(2219024, caster.getStoredId(), null, 600);
		}
	}

	@Override
	protected void onEvtScriptEvent(int eventId, Object arg1, Object arg2)
	{
		if( eventId == 2219022 )
		{
			_thisActor.doDie(null);
		}
	}
}
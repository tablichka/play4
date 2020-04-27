package ai;

import ai.base.DefaultNpc;
import ru.l2gw.gameserver.model.L2Character;
import ru.l2gw.gameserver.model.L2Skill;
import ru.l2gw.gameserver.tables.SkillTable;

/**
 * @author: rage
 * @date: 28.09.11 0:32
 */
public class IcequeenP4Buff extends DefaultNpc
{
	public L2Skill Skill01_ID = SkillTable.getInstance().getInfo(412024833);
	public int TIMER_heal = 2314016;
	public int debug_mode = 0;

	public IcequeenP4Buff(L2Character actor)
	{
		super(actor);
	}

	@Override
	protected void onEvtSpawn()
	{
		addTimer(TIMER_heal, 5000);
	}

	@Override
	protected void onEvtTimer(int timerId, Object arg1, Object arg2)
	{
		if(timerId == TIMER_heal)
		{
			if(Skill01_ID.getMpConsume() < _thisActor.getCurrentMp() && Skill01_ID.getHpConsume() < _thisActor.getCurrentHp() && !_thisActor.isSkillDisabled(Skill01_ID.getId()))
			{
				addUseSkillDesire(_thisActor, Skill01_ID, 1, 1, 1000000);
			}
			addTimer(TIMER_heal, 10000);
		}
	}

	@Override
	protected void onEvtScriptEvent(int eventId, Object arg1, Object arg2)
	{
		if(eventId == 23140020)
		{
			_thisActor.onDecay();
		}
	}
}

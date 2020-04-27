package ai;

import ai.base.DefaultNpc;
import ru.l2gw.gameserver.model.L2Character;
import ru.l2gw.gameserver.model.L2Skill;
import ru.l2gw.gameserver.tables.SkillTable;
import ru.l2gw.util.Location;
import ru.l2gw.commons.math.Rnd;

/**
 * @author: rage
 * @date: 04.10.11 17:38
 */
public class Ssq2JudithSummon extends DefaultNpc
{
	public int p_TIMER_SUMMON = 1000;
	public int p_TIMER_SUMMON_GAP = 1000;
	public int p_MAX_KILL_NUM = 4;

	public L2Skill skill1 = SkillTable.getInstance().getInfo(441122817);

	public Ssq2JudithSummon(L2Character actor)
	{
		super(actor);
	}

	@Override
	protected void onEvtSpawn()
	{
		super.onEvtSpawn();
		_thisActor.i_ai0 = 0;
		if(skill1.getMpConsume() < _thisActor.getCurrentMp() && skill1.getHpConsume() < _thisActor.getCurrentHp() && !_thisActor.isSkillDisabled(skill1.getId()))
		{
			addUseSkillDesire(_thisActor, skill1, 1, 1, 1000000);
		}
		//ServerVariables.set("GM_" + 80217, _thisActor.id);
		addTimer(p_TIMER_SUMMON, p_TIMER_SUMMON_GAP);
	}

	@Override
	protected void onEvtTimer(int timerId, Object arg1, Object arg2)
	{
		if(timerId == p_TIMER_SUMMON)
		{
			Location loc = Location.coordsRandomize(_thisActor, 40, 50);
			_thisActor.createOnePrivate(27415, "AiSolinaLeader", 0, 1, loc.getX(), loc.getY(), loc.getZ(), 0, 0, 0, 0);
			loc = Location.coordsRandomize(_thisActor, 40, 50);
			_thisActor.createOnePrivate(22125, "Ssq2SolinaLayBrother", 0, 1, loc.getX(), loc.getY(), loc.getZ(), 0, 0, 0, 0);
			loc = Location.coordsRandomize(_thisActor, 40, 50);
			_thisActor.createOnePrivate(27415, "AiSolinaLeader", 0, 1, loc.getX(), loc.getY(), loc.getZ(), 0, 0, 0, 0);
			loc = Location.coordsRandomize(_thisActor, 40, 50);
			_thisActor.createOnePrivate(22125, "Ssq2SolinaLayBrother", 0, 1, loc.getX(), loc.getY(), loc.getZ(), 0, 0, 0, 0);
		}
		super.onEvtTimer(timerId, arg1, arg2);
	}

	@Override
	protected void onEvtScriptEvent(int eventId, Object arg1, Object arg2)
	{
		if(eventId == 90111)
		{
			_thisActor.i_ai0++;
			if(_thisActor.i_ai0 == p_MAX_KILL_NUM)
			{
				_thisActor.onDecay();
			}
		}
		else
			super.onEvtScriptEvent(eventId, arg1, arg2);
	}
}
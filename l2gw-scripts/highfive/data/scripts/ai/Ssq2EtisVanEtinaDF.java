package ai;

import ai.base.WarriorUseSkill;
import ru.l2gw.gameserver.model.L2Character;
import ru.l2gw.gameserver.model.L2Skill;
import ru.l2gw.gameserver.tables.SkillTable;

/**
 * @author: rage
 * @date: 06.10.11 19:40
 */
public class Ssq2EtisVanEtinaDF extends WarriorUseSkill
{
	public int p_TIMER_THINK = 500;
	public int p_TIMER_START_UP = 1000;
	public int p_TIMER_START_UP_GAP = 1000;

	public Ssq2EtisVanEtinaDF(L2Character actor)
	{
		super(actor);
		IsAggressive = 1;
		Aggressive_Time = 1.000000f;
	}

	@Override
	protected void onEvtSpawn()
	{
		super.onEvtSpawn();
		//ServerVariables.set("GM_" + 80405, _thisActor.id);
		addTimer(p_TIMER_THINK, 500);
		addTimer(p_TIMER_START_UP, p_TIMER_START_UP_GAP);
	}

	@Override
	protected void onEvtFinishCasting(L2Skill skill)
	{
		if(skill.getId() == 6707)
		{
			_thisActor.doDie(null);
		}
		else if(skill.getId() == 6708)
		{
			_thisActor.doDie(null);
		}
		super.onEvtFinishCasting(skill);
	}

	@Override
	protected void onEvtTimer(int timerId, Object arg1, Object arg2)
	{
		if(timerId == p_TIMER_START_UP)
		{
			if(SkillTable.getInstance().getInfo(441122817).getMpConsume() < _thisActor.getCurrentMp() && SkillTable.getInstance().getInfo(441122817).getHpConsume() < _thisActor.getCurrentHp() && !_thisActor.isSkillDisabled(SkillTable.getInstance().getInfo(441122817).getId()))
			{
				addUseSkillDesire(_thisActor, 441122817, 1, 1, 1000000);
			}
			_thisActor.setWalking();
		}
		else if(timerId == p_TIMER_THINK)
		{
			L2Character c0 = getAttackTarget();
			if(c0 != null && c0.isPlayer())
			{
				if(_thisActor.getLoc().distance3D(c0.getLoc()) < 100)
				{
					if(SkillTable.getInstance().getInfo(439615489).getMpConsume() < _thisActor.getCurrentMp() && SkillTable.getInstance().getInfo(439615489).getHpConsume() < _thisActor.getCurrentHp() && !_thisActor.isSkillDisabled(SkillTable.getInstance().getInfo(439615489).getId()))
					{
						addUseSkillDesire(c0, 439615489, 0, 1, 1000000);
					}
				}
			}
			_thisActor.lookNeighbor(300);
			addTimer(p_TIMER_THINK, 500);
		}
		else
			super.onEvtTimer(timerId, arg1, arg2);
	}

	@Override
	protected void onEvtScriptEvent(int eventId, Object arg1, Object arg2)
	{
		if(eventId == 90210)
		{
			_thisActor.onDecay();
		}
		else if(eventId == 90209)
		{
			_thisActor.onDecay();
		}
		else
			super.onEvtScriptEvent(eventId, arg1, arg2);
	}
}
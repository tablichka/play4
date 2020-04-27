package ai;

import ru.l2gw.commons.math.Rnd;
import ru.l2gw.gameserver.model.L2Character;
import ru.l2gw.gameserver.model.L2Skill;
import ru.l2gw.gameserver.tables.SkillTable;

/**
 * @author: rage
 * @date: 09.09.11 2:01
 */
public class AiBlackdaggerWing extends DetectPartyWizard
{
	public int DAMAGE_TIMER = 2010505;
	public L2Skill RANGE_DD_SKILL = SkillTable.getInstance().getInfo(447873025);
	public L2Skill POWER_SKILL = SkillTable.getInstance().getInfo(447807489);
	public int isChasePC = 2500;

	public AiBlackdaggerWing(L2Character actor)
	{
		super(actor);
	}

	@Override
	protected void onEvtAttacked(L2Character attacker, int damage, L2Skill skill)
	{
		int i0 = _thisActor.getSpawnedLoc().getX();
		int i1 = _thisActor.getSpawnedLoc().getY();
		int i2 = _thisActor.getX();
		int i3 = _thisActor.getY();
		int i4 = i0 - i2;
		int i5 = i1 - i3;
		if(i4 * i4 + i5 * i5 > isChasePC * isChasePC)
		{
			_thisActor.teleToLocation(_thisActor.getSpawnedLoc().getX(), _thisActor.getSpawnedLoc().getY(), _thisActor.getSpawnedLoc().getZ());
		}
		if(_thisActor.getCurrentHp() < (_thisActor.getMaxHp() * 0.500000) && _thisActor.i_ai2 == 0)
		{
			_thisActor.i_ai2 = 1;
			addTimer(DAMAGE_TIMER, 10000);
		}

		super.onEvtAttacked(attacker, damage, skill);
	}

	@Override
	protected void onEvtSeeCreature(L2Character creature)
	{
		if(creature.isPlayer() && _thisActor.i_ai2 == 1)
		{
			L2Character top_desire_target = _thisActor.getMostHated();
			if(top_desire_target != null && top_desire_target.isPlayer())
			{
				if(top_desire_target != creature)
				{
					if(Rnd.get(5) < 1)
					{
						addUseSkillDesire(creature, RANGE_DD_SKILL, 0, 1, 9999900000000000L);
					}
				}
			}
		}
		super.onEvtSeeCreature(creature);
	}

	@Override
	protected void onEvtFinishCasting(L2Skill skill)
	{
		L2Character target = _thisActor.getCastingTarget();
		if(skill == POWER_SKILL && target != null)
		{
			_thisActor.i_ai3++;
			if(_thisActor.i_ai3 > 3)
			{
				addUseSkillDesire(target, RANGE_DD_SKILL, 0, 1, 9900000000000L);
				_thisActor.i_ai3 = 0;
			}
		}
		super.onEvtFinishCasting(skill);
	}

	@Override
	protected void onEvtTimer(int timerId, Object arg1, Object arg2)
	{
		if(timerId == DAMAGE_TIMER)
		{
			_thisActor.lookNeighbor(600);
			addTimer(DAMAGE_TIMER, 30000);
		}
		super.onEvtTimer(timerId, arg1, arg2);
	}
}

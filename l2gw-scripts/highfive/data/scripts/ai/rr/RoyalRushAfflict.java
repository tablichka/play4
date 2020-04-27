package ai.rr;

import ru.l2gw.commons.math.Rnd;
import ru.l2gw.gameserver.model.L2Character;
import ru.l2gw.gameserver.model.L2Skill;
import ru.l2gw.gameserver.tables.SkillTable;

/**
 * @author: rage
 * @date: 19.01.12 18:14
 */
public class RoyalRushAfflict extends RoyalRushDefaultNpc
{
	public L2Skill afflict_skill1 = SkillTable.getInstance().getInfo(264241153);
	public L2Skill afflict_skill2 = SkillTable.getInstance().getInfo(264241153);
	public L2Skill afflict_skill3 = SkillTable.getInstance().getInfo(264241153);

	public RoyalRushAfflict(L2Character actor)
	{
		super(actor);
	}

	@Override
	protected void onEvtSpawn()
	{
		int i0 = Rnd.get(3);
		switch(i0)
		{
			case 0:
				_thisActor.i_ai0 = 0;
				addTimer(3001, 5000);
				break;
			case 1:
				_thisActor.i_ai0 = 1;
				addTimer(3002, 5000);
				break;
			case 2:
				_thisActor.i_ai0 = 2;
				addTimer(3002, 5000);
				break;
		}
		_thisActor.lookNeighbor(300);
		super.onEvtSpawn();
	}

	@Override
	protected void onEvtNoDesire()
	{
		addMoveAroundDesire(5, 5);
	}

	@Override
	protected void onEvtSeeCreature(L2Character creature)
	{
		if(creature.isPlayer() && Rnd.get(100) < 50)
		{
			addFollowDesire(creature, 100);
		}
	}

	@Override
	protected void onEvtAttacked(L2Character attacker, int damage, L2Skill skill)
	{
		addFleeDesire(attacker, 100);
		super.onEvtAttacked(attacker, damage, skill);

	}

	@Override
	protected void onEvtTimer(int timerId, Object arg1, Object arg2)
	{
		if(timerId == 3001)
		{
			if(afflict_skill1.getMpConsume() < _thisActor.getCurrentMp() && afflict_skill1.getHpConsume() < _thisActor.getCurrentHp() && !_thisActor.isSkillDisabled(afflict_skill1.getId()))
			{
				addUseSkillDesire(_thisActor, afflict_skill1, 0, 1, 1000000);
			}
		}
		if(timerId == 3002)
		{
			if(_thisActor.i_ai0 == 1)
			{
				if(afflict_skill2.getMpConsume() < _thisActor.getCurrentMp() && afflict_skill2.getHpConsume() < _thisActor.getCurrentHp() && !_thisActor.isSkillDisabled(afflict_skill2.getId()))
				{
					addUseSkillDesire(_thisActor, afflict_skill2, 0, 1, 1000000);
				}
			}
			else if(_thisActor.i_ai0 == 2)
			{
				if(afflict_skill3.getMpConsume() < _thisActor.getCurrentMp() && afflict_skill3.getHpConsume() < _thisActor.getCurrentHp() && !_thisActor.isSkillDisabled(afflict_skill3.getId()))
				{
					addUseSkillDesire(_thisActor, afflict_skill3, 0, 1, 1000000);
				}
			}
			addTimer(3003, 5000);
		}
		if(timerId == 3003)
		{
			_thisActor.lookNeighbor(300);
		}
		super.onEvtTimer(timerId, arg1, arg2);
	}

	@Override
	protected void onEvtFinishCasting(L2Skill skill)
	{
		switch(_thisActor.i_ai0)
		{
			case 0:
				_thisActor.onDecay();
				break;
			case 1:
				if(afflict_skill2.getMpConsume() < _thisActor.getCurrentMp() && afflict_skill2.getHpConsume() < _thisActor.getCurrentHp() && !_thisActor.isSkillDisabled(afflict_skill2.getId()))
				{
					addUseSkillDesire(_thisActor, afflict_skill2, 0, 1, 1000000);
				}
				break;
			case 2:
				if(afflict_skill3.getMpConsume() < _thisActor.getCurrentMp() && afflict_skill3.getHpConsume() < _thisActor.getCurrentHp() && !_thisActor.isSkillDisabled(afflict_skill3.getId()))
				{
					addUseSkillDesire(_thisActor, afflict_skill3, 0, 1, 1000000);
				}
				break;
		}
	}
}
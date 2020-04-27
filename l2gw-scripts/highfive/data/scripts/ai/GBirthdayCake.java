package ai;

import ai.base.DefaultNpc;
import ru.l2gw.gameserver.model.*;
import ru.l2gw.gameserver.tables.SkillTable;
import ru.l2gw.util.Util;

/**
 * @author: rage
 * @date: 23.01.12 14:03
 */
public class GBirthdayCake extends DefaultNpc
{
	public L2Skill skill01 = SkillTable.getInstance().getInfo(1458176001);

	public GBirthdayCake(L2Character actor)
	{
		super(actor);
	}

	@Override
	protected void onEvtSpawn()
	{
		L2Character c0 = L2ObjectsStorage.getAsCharacter(_thisActor.param1);
		if(c0 != null)
		{
			_thisActor.i_ai0 = c0.getObjectId();
		}
		else
		{
			_thisActor.i_ai0 = 0;
		}
		addTimer(5001, 1200000);
		addTimer(5002, 1000);
	}

	@Override
	protected void onEvtSeeCreature(L2Character creature)
	{
		if(creature.getObjectId() == _thisActor.i_ai0)
		{
			L2Party party0 = Util.getParty(creature);
			if(party0 == null)
			{
				if(skill01.getMpConsume() < _thisActor.getCurrentMp() && skill01.getHpConsume() < _thisActor.getCurrentHp() && !_thisActor.isSkillDisabled(skill01.getId()))
				{
					addUseSkillDesire(creature, skill01, 1, 1, 1000000);
				}
				super.onEvtSeeCreature(creature);
			}
			else
			{
				for(L2Player member : party0.getPartyMembers())
				{
					if(member != null)
					{
						if(skill01.getMpConsume() < _thisActor.getCurrentMp() && skill01.getHpConsume() < _thisActor.getCurrentHp() && !_thisActor.isSkillDisabled(skill01.getId()))
						{
							addUseSkillDesire(member, skill01, 1, 1, member.getObjectId());
						}
					}
				}
			}
		}
		super.onEvtSeeCreature(creature);
	}

	@Override
	protected void onEvtTimer(int timerId, Object arg1, Object arg2)
	{
		if(timerId == 5001)
		{
			_thisActor.onDecay();
		}
		else if(timerId == 5002)
		{
			L2Character c0 = L2ObjectsStorage.getAsCharacter(_thisActor.param1);
			if(c0 != null)
			{
				L2Party party0 = Util.getParty(c0);
				if(party0 == null)
				{
					if(skill01.getMpConsume() < _thisActor.getCurrentMp() && skill01.getHpConsume() < _thisActor.getCurrentHp() && !_thisActor.isSkillDisabled(skill01.getId()))
					{
						addUseSkillDesire(c0, skill01, 1, 1, 1000000);
					}
				}
				else
				{
					for(L2Player member : party0.getPartyMembers())
					{
						if(member != null)
						{
							if(skill01.getMpConsume() < _thisActor.getCurrentMp() && skill01.getHpConsume() < _thisActor.getCurrentHp() && !_thisActor.isSkillDisabled(skill01.getId()))
							{
								addUseSkillDesire(member, skill01, 1, 1, member.getObjectId());
							}
						}
					}
				}
			}
			else
			{
				_thisActor.lookNeighbor(500);
			}
			addTimer(5002, 60000);
		}
		else
			super.onEvtTimer(timerId, arg1, arg2);
	}
}
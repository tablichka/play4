package ai;

import ru.l2gw.gameserver.model.L2Character;
import ru.l2gw.gameserver.model.L2ObjectsStorage;
import ru.l2gw.gameserver.model.L2Skill;

/**
 * @author: rage
 * @date: 03.09.11 15:27
 */
public class AiFeastFeeder extends DetectPartyWarrior
{
	int	suicideTimer = 2010508;
	int	lifeTimer = 2010509;

	public AiFeastFeeder(L2Character actor)
	{
		super(actor);
	}

	@Override
	protected void onEvtSpawn()
	{
		_thisActor.i_ai0 = 0;
		addTimer(lifeTimer, 1000);
		L2Character cha = L2ObjectsStorage.getAsCharacter(_thisActor.param1);
		if(cha != null)
		{
			_thisActor.addDamageHate(cha, 0, 10000);
			addAttackDesire(cha, 1, 10000);
		}
		super.onEvtSpawn();
	}

	@Override
	protected void onEvtAttacked(L2Character attacker, int damage, L2Skill skill)
	{
		if(attacker == null)
			return;

		if(attacker.isNpc() && attacker.getNpcId() == 25722 && _thisActor.i_ai0 == 0)
		{
			_thisActor.i_ai0 = 1;
			attacker.setCurrentHp(attacker.getCurrentHp() + 30000);
			addTimer(suicideTimer, 1000);
		}
	}

	@Override
	protected void onEvtTimer(int timerId, Object arg1, Object arg2)
	{
		if(timerId == lifeTimer)
		{
			if(!_thisActor.isMyBossAlive())
				_thisActor.deleteMe();
			else
				addTimer(lifeTimer, 5000);
		}
		else if(timerId == suicideTimer)
			_thisActor.doDie(null);
	}
}

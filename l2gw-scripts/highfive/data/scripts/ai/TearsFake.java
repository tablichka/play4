package ai;

import ru.l2gw.gameserver.ai.Balanced;
import ru.l2gw.gameserver.model.L2Character;
import ru.l2gw.gameserver.model.L2Skill;

/**
 * @author rage
 * @date 16.11.2009 16:23:30
 */
public class TearsFake extends Balanced
{
	private long despawnHp = 0;

	public TearsFake(L2Character actor)
	{
		super(actor);
	}

	@Override
	protected void onEvtAttacked(L2Character attacker, int damage, L2Skill skill)
	{
		if(despawnHp == 0)
		{
			despawnHp = (long) _thisActor.getCurrentHp() - 50000;
			if(despawnHp < 0)
				despawnHp = (long) _thisActor.getCurrentHp() / 2;
		}

		if(despawnHp > _thisActor.getCurrentHp())
			_thisActor.deleteMe();
		else
			super.onEvtAttacked(attacker, damage, skill);
	}
}

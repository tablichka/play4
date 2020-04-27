package ai;

import ru.l2gw.gameserver.ai.DefaultAI;
import ru.l2gw.gameserver.model.L2Character;
import ru.l2gw.gameserver.model.L2Playable;
import ru.l2gw.util.Location;
import ru.l2gw.util.Util;

/**
 * @author rage
 * @date 14.10.2010 11:33:19
 */
public class PailakaWatcher extends DefaultAI
{
	private Location _teleLoc;
	private int aMin, aMax;

	public PailakaWatcher(L2Character actor)
	{
		super(actor);
		_actor.setImobilised(true);
		_actor.setIsInvul(true);
	}

	@Override
	protected void onEvtSpawn()
	{
		super.onEvtSpawn();
		_teleLoc = Util.getPointInRadius(_thisActor.getLoc(), 900, (int) Util.convertHeadingToDegree(_thisActor.getHeading()));

		int angle = (int) Util.convertHeadingToDegree(_thisActor.getHeading());

		aMin = angle + 90;
		aMax = angle - 90;

		if(aMax < 0)
			aMax += 360;

		if(aMin >= 360)
			aMin -= 360;
	}

	@Override
	protected boolean thinkActive()
	{
		if(_thisActor.getKnownNpc(500).size() <= 0)
			return true;

		for(L2Character cha : _thisActor.getKnownCharacters(2000))
			if(cha instanceof L2Playable)
			{
				int xa = (int) Util.calculateAngleFrom(cha.getX(), cha.getY(), _thisActor.getX(), _thisActor.getY());
				if(xa == 360)
					xa = 0;

				if(aMin > aMax)
				{
					if((aMin < xa && xa < 360) || (xa >= 0 && xa < aMax))
						cha.teleToLocation(_teleLoc, cha.getReflection());
				}
				else if(aMin < xa && xa < aMax)
					cha.teleToLocation(_teleLoc, cha.getReflection());
			}

		return true;
	}
}

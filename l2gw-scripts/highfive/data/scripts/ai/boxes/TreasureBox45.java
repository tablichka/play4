package ai.boxes;

import ai.base.GTreasureBox;
import ru.l2gw.commons.math.Rnd;
import ru.l2gw.gameserver.model.L2Character;

/**
 * @author: rage
 * @date: 19.12.11 21:03
 */
public class TreasureBox45 extends GTreasureBox
{
	public TreasureBox45(L2Character actor)
	{
		super(actor);
	}

	@Override
	protected void onEvtDead(L2Character killer)
	{
		super.onEvtDead(killer);
		int i0 = Rnd.get(10000);
		if( i0 < 7662 )
		{
			_thisActor.dropItem(killer, 736, 5);
		}
		i0 = Rnd.get(10000);
		if( i0 < 4789 )
		{
			_thisActor.dropItem(killer, 1061, 4);
		}
		i0 = Rnd.get(10000);
		if( i0 < 2554 )
		{
			_thisActor.dropItem(killer, 737, 3);
		}
		i0 = Rnd.get(10000);
		if( i0 < 7662 )
		{
			_thisActor.dropItem(killer, 1539, 5);
		}
		i0 = Rnd.get(10000);
		if( i0 < 3831 )
		{
			_thisActor.dropItem(killer, 8625, 2);
		}
		i0 = Rnd.get(10000);
		if( i0 < 3303 )
		{
			_thisActor.dropItem(killer, 8631, 2);
		}
		i0 = Rnd.get(10000);
		if( i0 < 6385 )
		{
			_thisActor.dropItem(killer, 8637, 3);
		}
		i0 = Rnd.get(10000);
		if( i0 < 6385 )
		{
			_thisActor.dropItem(killer, 8636, 4);
		}
		i0 = Rnd.get(10000);
		if( i0 < 4404 )
		{
			_thisActor.dropItem(killer, 8630, 2);
		}
		i0 = Rnd.get(10000);
		if( i0 < 5321 )
		{
			_thisActor.dropItem(killer, 8624, 2);
		}
		i0 = Rnd.get(10000);
		if( i0 < 5894 )
		{
			_thisActor.dropItem(killer, 10260, 1);
		}
		i0 = Rnd.get(10000);
		if( i0 < 5894 )
		{
			_thisActor.dropItem(killer, 10261, 1);
		}
		i0 = Rnd.get(10000);
		if( i0 < 5894 )
		{
			_thisActor.dropItem(killer, 10262, 1);
		}
		i0 = Rnd.get(10000);
		if( i0 < 5894 )
		{
			_thisActor.dropItem(killer, 10263, 1);
		}
		i0 = Rnd.get(10000);
		if( i0 < 5894 )
		{
			_thisActor.dropItem(killer, 10264, 1);
		}
		i0 = Rnd.get(10000);
		if( i0 < 5894 )
		{
			_thisActor.dropItem(killer, 10265, 1);
		}
		i0 = Rnd.get(10000);
		if( i0 < 5894 )
		{
			_thisActor.dropItem(killer, 10266, 1);
		}
		i0 = Rnd.get(10000);
		if( i0 < 5894 )
		{
			_thisActor.dropItem(killer, 10267, 1);
		}
		i0 = Rnd.get(10000);
		if( i0 < 5894 )
		{
			_thisActor.dropItem(killer, 10268, 1);
		}
		i0 = Rnd.get(10000);
		if( i0 < 8186 )
		{
			_thisActor.dropItem(killer, 5593, 9);
		}
		i0 = Rnd.get(10000);
		if( i0 < 7367 )
		{
			_thisActor.dropItem(killer, 5594, 2);
		}
		i0 = Rnd.get(10000);
		if( i0 < 737 )
		{
			_thisActor.dropItem(killer, 5595, 1);
		}
		i0 = Rnd.get(10000);
		if( i0 < 5894 )
		{
			_thisActor.dropItem(killer, 10269, 1);
		}
		i0 = Rnd.get(10000);
		if( i0 < 6226 )
		{
			_thisActor.dropItem(killer, 10137, 1);
		}
		i0 = Rnd.get(10000);
		if( i0 < 6226 )
		{
			_thisActor.dropItem(killer, 10138, 1);
		}
		i0 = Rnd.get(10000);
		if( i0 < 8301 )
		{
			_thisActor.dropItem(killer, 1538, 1);
		}
		i0 = Rnd.get(10000);
		if( i0 < 3113 )
		{
			_thisActor.dropItem(killer, 3936, 1);
		}
		i0 = Rnd.get(10000);
		if( i0 < 2491 )
		{
			_thisActor.dropItem(killer, 5577, 1);
		}
		i0 = Rnd.get(10000);
		if( i0 < 2491 )
		{
			_thisActor.dropItem(killer, 5578, 1);
		}
		i0 = Rnd.get(10000);
		if( i0 < 2491 )
		{
			_thisActor.dropItem(killer, 5579, 1);
		}
		i0 = Rnd.get(10000);
		if( i0 < 1437 )
		{
			_thisActor.dropItem(killer, 70, 1);
		}
		i0 = Rnd.get(10000);
		if( i0 < 1080 )
		{
			_thisActor.dropItem(killer, 21747, 1);
		}
	}
}
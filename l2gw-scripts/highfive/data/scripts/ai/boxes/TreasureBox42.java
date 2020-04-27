package ai.boxes;

import ai.base.GTreasureBox;
import ru.l2gw.commons.math.Rnd;
import ru.l2gw.gameserver.model.L2Character;

/**
 * @author: rage
 * @date: 19.12.11 21:02
 */
public class TreasureBox42 extends GTreasureBox
{
	public TreasureBox42(L2Character actor)
	{
		super(actor);
	}

	@Override
	protected void onEvtDead(L2Character killer)
	{
		super.onEvtDead(killer);
		int i0 = Rnd.get(10000);
		if( i0 < 6668 )
		{
			_thisActor.dropItem(killer, 736, 5);
		}
		i0 = Rnd.get(10000);
		if( i0 < 4168 )
		{
			_thisActor.dropItem(killer, 1061, 4);
		}
		i0 = Rnd.get(10000);
		if( i0 < 2223 )
		{
			_thisActor.dropItem(killer, 737, 3);
		}
		i0 = Rnd.get(10000);
		if( i0 < 6668 )
		{
			_thisActor.dropItem(killer, 1539, 5);
		}
		i0 = Rnd.get(10000);
		if( i0 < 3334 )
		{
			_thisActor.dropItem(killer, 8625, 2);
		}
		i0 = Rnd.get(10000);
		if( i0 < 2874 )
		{
			_thisActor.dropItem(killer, 8631, 2);
		}
		i0 = Rnd.get(10000);
		if( i0 < 5557 )
		{
			_thisActor.dropItem(killer, 8637, 3);
		}
		i0 = Rnd.get(10000);
		if( i0 < 5557 )
		{
			_thisActor.dropItem(killer, 8636, 4);
		}
		i0 = Rnd.get(10000);
		if( i0 < 3832 )
		{
			_thisActor.dropItem(killer, 8630, 2);
		}
		i0 = Rnd.get(10000);
		if( i0 < 4631 )
		{
			_thisActor.dropItem(killer, 8624, 2);
		}
		i0 = Rnd.get(10000);
		if( i0 < 5129 )
		{
			_thisActor.dropItem(killer, 10260, 1);
		}
		i0 = Rnd.get(10000);
		if( i0 < 5129 )
		{
			_thisActor.dropItem(killer, 10261, 1);
		}
		i0 = Rnd.get(10000);
		if( i0 < 5129 )
		{
			_thisActor.dropItem(killer, 10262, 1);
		}
		i0 = Rnd.get(10000);
		if( i0 < 5129 )
		{
			_thisActor.dropItem(killer, 10263, 1);
		}
		i0 = Rnd.get(10000);
		if( i0 < 5129 )
		{
			_thisActor.dropItem(killer, 10264, 1);
		}
		i0 = Rnd.get(10000);
		if( i0 < 5129 )
		{
			_thisActor.dropItem(killer, 10265, 1);
		}
		i0 = Rnd.get(10000);
		if( i0 < 5129 )
		{
			_thisActor.dropItem(killer, 10266, 1);
		}
		i0 = Rnd.get(10000);
		if( i0 < 5129 )
		{
			_thisActor.dropItem(killer, 10267, 1);
		}
		i0 = Rnd.get(10000);
		if( i0 < 5129 )
		{
			_thisActor.dropItem(killer, 10268, 1);
		}
		i0 = Rnd.get(10000);
		if( i0 < 7124 )
		{
			_thisActor.dropItem(killer, 5593, 9);
		}
		i0 = Rnd.get(10000);
		if( i0 < 6411 )
		{
			_thisActor.dropItem(killer, 5594, 2);
		}
		i0 = Rnd.get(10000);
		if( i0 < 642 )
		{
			_thisActor.dropItem(killer, 5595, 1);
		}
		i0 = Rnd.get(10000);
		if( i0 < 5129 )
		{
			_thisActor.dropItem(killer, 10269, 1);
		}
		i0 = Rnd.get(10000);
		if( i0 < 5418 )
		{
			_thisActor.dropItem(killer, 10137, 1);
		}
		i0 = Rnd.get(10000);
		if( i0 < 5418 )
		{
			_thisActor.dropItem(killer, 10138, 1);
		}
		i0 = Rnd.get(10000);
		if( i0 < 7223 )
		{
			_thisActor.dropItem(killer, 1538, 1);
		}
		i0 = Rnd.get(10000);
		if( i0 < 2709 )
		{
			_thisActor.dropItem(killer, 3936, 1);
		}
		i0 = Rnd.get(10000);
		if( i0 < 2167 )
		{
			_thisActor.dropItem(killer, 5577, 1);
		}
		i0 = Rnd.get(10000);
		if( i0 < 2167 )
		{
			_thisActor.dropItem(killer, 5578, 1);
		}
		i0 = Rnd.get(10000);
		if( i0 < 2167 )
		{
			_thisActor.dropItem(killer, 5579, 1);
		}
		i0 = Rnd.get(10000);
		if( i0 < 1250 )
		{
			_thisActor.dropItem(killer, 70, 1);
		}
		i0 = Rnd.get(10000);
		if( i0 < 940 )
		{
			_thisActor.dropItem(killer, 21747, 1);
		}
	}
}
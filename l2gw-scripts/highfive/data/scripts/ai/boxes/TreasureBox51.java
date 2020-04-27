package ai.boxes;

import ai.base.GTreasureBox;
import ru.l2gw.commons.math.Rnd;
import ru.l2gw.gameserver.model.L2Character;

/**
 * @author: rage
 * @date: 19.12.11 21:03
 */
public class TreasureBox51 extends GTreasureBox
{
	public TreasureBox51(L2Character actor)
	{
		super(actor);
	}

	@Override
	protected void onEvtDead(L2Character killer)
	{
		super.onEvtDead(killer);
		int i0 = Rnd.get(10000);
		if( i0 < 9881 )
		{
			_thisActor.dropItem(killer, 736, 5);
		}
		i0 = Rnd.get(10000);
		if( i0 < 6176 )
		{
			_thisActor.dropItem(killer, 1061, 4);
		}
		i0 = Rnd.get(10000);
		if( i0 < 3294 )
		{
			_thisActor.dropItem(killer, 737, 3);
		}
		i0 = Rnd.get(10000);
		if( i0 < 9881 )
		{
			_thisActor.dropItem(killer, 1539, 5);
		}
		i0 = Rnd.get(10000);
		if( i0 < 4941 )
		{
			_thisActor.dropItem(killer, 8625, 2);
		}
		i0 = Rnd.get(10000);
		if( i0 < 4259 )
		{
			_thisActor.dropItem(killer, 8631, 2);
		}
		i0 = Rnd.get(10000);
		if( i0 < 8234 )
		{
			_thisActor.dropItem(killer, 8637, 3);
		}
		i0 = Rnd.get(10000);
		if( i0 < 8234 )
		{
			_thisActor.dropItem(killer, 8636, 4);
		}
		i0 = Rnd.get(10000);
		if( i0 < 5679 )
		{
			_thisActor.dropItem(killer, 8630, 2);
		}
		i0 = Rnd.get(10000);
		if( i0 < 6862 )
		{
			_thisActor.dropItem(killer, 8624, 2);
		}
		i0 = Rnd.get(10000);
		if( i0 < 7601 )
		{
			_thisActor.dropItem(killer, 10260, 1);
		}
		i0 = Rnd.get(10000);
		if( i0 < 7601 )
		{
			_thisActor.dropItem(killer, 10261, 1);
		}
		i0 = Rnd.get(10000);
		if( i0 < 7601 )
		{
			_thisActor.dropItem(killer, 10262, 1);
		}
		i0 = Rnd.get(10000);
		if( i0 < 7601 )
		{
			_thisActor.dropItem(killer, 10263, 1);
		}
		i0 = Rnd.get(10000);
		if( i0 < 7601 )
		{
			_thisActor.dropItem(killer, 10264, 1);
		}
		i0 = Rnd.get(10000);
		if( i0 < 7601 )
		{
			_thisActor.dropItem(killer, 10265, 1);
		}
		i0 = Rnd.get(10000);
		if( i0 < 7601 )
		{
			_thisActor.dropItem(killer, 10266, 1);
		}
		i0 = Rnd.get(10000);
		if( i0 < 7601 )
		{
			_thisActor.dropItem(killer, 10267, 1);
		}
		i0 = Rnd.get(10000);
		if( i0 < 7601 )
		{
			_thisActor.dropItem(killer, 10268, 1);
		}
		i0 = Rnd.get(10000);
		if( i0 < 10557 )
		{
			_thisActor.dropItem(killer, 5593, 9);
		}
		i0 = Rnd.get(10000);
		if( i0 < 9501 )
		{
			_thisActor.dropItem(killer, 5594, 2);
		}
		i0 = Rnd.get(10000);
		if( i0 < 951 )
		{
			_thisActor.dropItem(killer, 5595, 1);
		}
		i0 = Rnd.get(10000);
		if( i0 < 7601 )
		{
			_thisActor.dropItem(killer, 10269, 1);
		}
		i0 = Rnd.get(10000);
		if( i0 < 8028 )
		{
			_thisActor.dropItem(killer, 21180, 1);
		}
		i0 = Rnd.get(10000);
		if( i0 < 6423 )
		{
			_thisActor.dropItem(killer, 21181, 1);
		}
		i0 = Rnd.get(10000);
		if( i0 < 10704 )
		{
			_thisActor.dropItem(killer, 1538, 1);
		}
		i0 = Rnd.get(10000);
		if( i0 < 4014 )
		{
			_thisActor.dropItem(killer, 3936, 1);
		}
		i0 = Rnd.get(10000);
		if( i0 < 3212 )
		{
			_thisActor.dropItem(killer, 5577, 1);
		}
		i0 = Rnd.get(10000);
		if( i0 < 3212 )
		{
			_thisActor.dropItem(killer, 5578, 1);
		}
		i0 = Rnd.get(10000);
		if( i0 < 3212 )
		{
			_thisActor.dropItem(killer, 5579, 1);
		}
		i0 = Rnd.get(10000);
		if( i0 < 546 )
		{
			_thisActor.dropItem(killer, 135, 1);
		}
		i0 = Rnd.get(10000);
		if( i0 < 1393 )
		{
			_thisActor.dropItem(killer, 21747, 1);
		}
	}
}
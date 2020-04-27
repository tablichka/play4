package ai.boxes;

import ai.base.GTreasureBox;
import ru.l2gw.commons.math.Rnd;
import ru.l2gw.gameserver.model.L2Character;

/**
 * @author: rage
 * @date: 19.12.11 21:04
 */
public class TreasureBox57 extends GTreasureBox
{
	public TreasureBox57(L2Character actor)
	{
		super(actor);
	}

	@Override
	protected void onEvtDead(L2Character killer)
	{
		super.onEvtDead(killer);
		int i0 = Rnd.get(10000);
		if( i0 < 8657 )
		{
			_thisActor.dropItem(killer, 736, 8);
		}
		i0 = Rnd.get(10000);
		if( i0 < 8657 )
		{
			_thisActor.dropItem(killer, 1061, 4);
		}
		i0 = Rnd.get(10000);
		if( i0 < 4617 )
		{
			_thisActor.dropItem(killer, 737, 3);
		}
		i0 = Rnd.get(10000);
		if( i0 < 6926 )
		{
			_thisActor.dropItem(killer, 8625, 2);
		}
		i0 = Rnd.get(10000);
		if( i0 < 5971 )
		{
			_thisActor.dropItem(killer, 8631, 2);
		}
		i0 = Rnd.get(10000);
		if( i0 < 8657 )
		{
			_thisActor.dropItem(killer, 8637, 4);
		}
		i0 = Rnd.get(10000);
		if( i0 < 9234 )
		{
			_thisActor.dropItem(killer, 8638, 3);
		}
		i0 = Rnd.get(10000);
		if( i0 < 4810 )
		{
			_thisActor.dropItem(killer, 8632, 2);
		}
		i0 = Rnd.get(10000);
		if( i0 < 5541 )
		{
			_thisActor.dropItem(killer, 8626, 2);
		}
		i0 = Rnd.get(10000);
		if( i0 < 4987 )
		{
			_thisActor.dropItem(killer, 10260, 1);
		}
		i0 = Rnd.get(10000);
		if( i0 < 4987 )
		{
			_thisActor.dropItem(killer, 10261, 1);
		}
		i0 = Rnd.get(10000);
		if( i0 < 4987 )
		{
			_thisActor.dropItem(killer, 10262, 1);
		}
		i0 = Rnd.get(10000);
		if( i0 < 4987 )
		{
			_thisActor.dropItem(killer, 10263, 1);
		}
		i0 = Rnd.get(10000);
		if( i0 < 4987 )
		{
			_thisActor.dropItem(killer, 10264, 1);
		}
		i0 = Rnd.get(10000);
		if( i0 < 4987 )
		{
			_thisActor.dropItem(killer, 10265, 1);
		}
		i0 = Rnd.get(10000);
		if( i0 < 4987 )
		{
			_thisActor.dropItem(killer, 10266, 1);
		}
		i0 = Rnd.get(10000);
		if( i0 < 4987 )
		{
			_thisActor.dropItem(killer, 10267, 1);
		}
		i0 = Rnd.get(10000);
		if( i0 < 4987 )
		{
			_thisActor.dropItem(killer, 10268, 1);
		}
		i0 = Rnd.get(10000);
		if( i0 < 6233 )
		{
			_thisActor.dropItem(killer, 5594, 2);
		}
		i0 = Rnd.get(10000);
		if( i0 < 624 )
		{
			_thisActor.dropItem(killer, 5595, 1);
		}
		i0 = Rnd.get(10000);
		if( i0 < 4987 )
		{
			_thisActor.dropItem(killer, 10269, 1);
		}
		i0 = Rnd.get(10000);
		if( i0 < 7214 )
		{
			_thisActor.dropItem(killer, 8736, 1);
		}
		i0 = Rnd.get(10000);
		if( i0 < 6233 )
		{
			_thisActor.dropItem(killer, 8737, 1);
		}
		i0 = Rnd.get(10000);
		if( i0 < 5195 )
		{
			_thisActor.dropItem(killer, 8738, 1);
		}
		i0 = Rnd.get(10000);
		if( i0 < 5402 )
		{
			_thisActor.dropItem(killer, 21183, 1);
		}
		i0 = Rnd.get(10000);
		if( i0 < 5402 )
		{
			_thisActor.dropItem(killer, 21184, 1);
		}
		i0 = Rnd.get(10000);
		if( i0 < 5402 )
		{
			_thisActor.dropItem(killer, 1538, 2);
		}
		i0 = Rnd.get(10000);
		if( i0 < 4052 )
		{
			_thisActor.dropItem(killer, 3936, 1);
		}
		i0 = Rnd.get(10000);
		if( i0 < 751 )
		{
			_thisActor.dropItem(killer, 9648, 1);
		}
		i0 = Rnd.get(10000);
		if( i0 < 901 )
		{
			_thisActor.dropItem(killer, 9649, 1);
		}
		i0 = Rnd.get(10000);
		if( i0 < 163 )
		{
			_thisActor.dropItem(killer, 5580, 1);
		}
		i0 = Rnd.get(10000);
		if( i0 < 163 )
		{
			_thisActor.dropItem(killer, 5581, 1);
		}
		i0 = Rnd.get(10000);
		if( i0 < 163 )
		{
			_thisActor.dropItem(killer, 5582, 1);
		}
		i0 = Rnd.get(10000);
		if( i0 < 161 )
		{
			_thisActor.dropItem(killer, 79, 1);
		}
		i0 = Rnd.get(10000);
		if( i0 < 103 )
		{
			_thisActor.dropItem(killer, 21748, 1);
		}
	}
}
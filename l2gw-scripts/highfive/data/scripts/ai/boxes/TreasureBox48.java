package ai.boxes;

import ai.base.GTreasureBox;
import ru.l2gw.commons.math.Rnd;
import ru.l2gw.gameserver.model.L2Character;

/**
 * @author: rage
 * @date: 19.12.11 21:03
 */
public class TreasureBox48 extends GTreasureBox
{
	public TreasureBox48(L2Character actor)
	{
		super(actor);
	}

	@Override
	protected void onEvtDead(L2Character killer)
	{
		super.onEvtDead(killer);
		int i0 = Rnd.get(10000);
		if( i0 < 8719 )
		{
			_thisActor.dropItem(killer, 736, 5);
		}
		i0 = Rnd.get(10000);
		if( i0 < 5450 )
		{
			_thisActor.dropItem(killer, 1061, 4);
		}
		i0 = Rnd.get(10000);
		if( i0 < 2907 )
		{
			_thisActor.dropItem(killer, 737, 3);
		}
		i0 = Rnd.get(10000);
		if( i0 < 8719 )
		{
			_thisActor.dropItem(killer, 1539, 5);
		}
		i0 = Rnd.get(10000);
		if( i0 < 4360 )
		{
			_thisActor.dropItem(killer, 8625, 2);
		}
		i0 = Rnd.get(10000);
		if( i0 < 3759 )
		{
			_thisActor.dropItem(killer, 8631, 2);
		}
		i0 = Rnd.get(10000);
		if( i0 < 7266 )
		{
			_thisActor.dropItem(killer, 8637, 3);
		}
		i0 = Rnd.get(10000);
		if( i0 < 7266 )
		{
			_thisActor.dropItem(killer, 8636, 4);
		}
		i0 = Rnd.get(10000);
		if( i0 < 5011 )
		{
			_thisActor.dropItem(killer, 8630, 2);
		}
		i0 = Rnd.get(10000);
		if( i0 < 6055 )
		{
			_thisActor.dropItem(killer, 8624, 2);
		}
		i0 = Rnd.get(10000);
		if( i0 < 6707 )
		{
			_thisActor.dropItem(killer, 10260, 1);
		}
		i0 = Rnd.get(10000);
		if( i0 < 6707 )
		{
			_thisActor.dropItem(killer, 10261, 1);
		}
		i0 = Rnd.get(10000);
		if( i0 < 6707 )
		{
			_thisActor.dropItem(killer, 10262, 1);
		}
		i0 = Rnd.get(10000);
		if( i0 < 6707 )
		{
			_thisActor.dropItem(killer, 10263, 1);
		}
		i0 = Rnd.get(10000);
		if( i0 < 6707 )
		{
			_thisActor.dropItem(killer, 10264, 1);
		}
		i0 = Rnd.get(10000);
		if( i0 < 6707 )
		{
			_thisActor.dropItem(killer, 10265, 1);
		}
		i0 = Rnd.get(10000);
		if( i0 < 6707 )
		{
			_thisActor.dropItem(killer, 10266, 1);
		}
		i0 = Rnd.get(10000);
		if( i0 < 6707 )
		{
			_thisActor.dropItem(killer, 10267, 1);
		}
		i0 = Rnd.get(10000);
		if( i0 < 6707 )
		{
			_thisActor.dropItem(killer, 10268, 1);
		}
		i0 = Rnd.get(10000);
		if( i0 < 9315 )
		{
			_thisActor.dropItem(killer, 5593, 9);
		}
		i0 = Rnd.get(10000);
		if( i0 < 8384 )
		{
			_thisActor.dropItem(killer, 5594, 2);
		}
		i0 = Rnd.get(10000);
		if( i0 < 839 )
		{
			_thisActor.dropItem(killer, 5595, 1);
		}
		i0 = Rnd.get(10000);
		if( i0 < 6707 )
		{
			_thisActor.dropItem(killer, 10269, 1);
		}
		i0 = Rnd.get(10000);
		if( i0 < 7084 )
		{
			_thisActor.dropItem(killer, 21180, 1);
		}
		i0 = Rnd.get(10000);
		if( i0 < 5668 )
		{
			_thisActor.dropItem(killer, 21181, 1);
		}
		i0 = Rnd.get(10000);
		if( i0 < 9446 )
		{
			_thisActor.dropItem(killer, 1538, 1);
		}
		i0 = Rnd.get(10000);
		if( i0 < 3542 )
		{
			_thisActor.dropItem(killer, 3936, 1);
		}
		i0 = Rnd.get(10000);
		if( i0 < 2834 )
		{
			_thisActor.dropItem(killer, 5577, 1);
		}
		i0 = Rnd.get(10000);
		if( i0 < 2834 )
		{
			_thisActor.dropItem(killer, 5578, 1);
		}
		i0 = Rnd.get(10000);
		if( i0 < 2834 )
		{
			_thisActor.dropItem(killer, 5579, 1);
		}
		i0 = Rnd.get(10000);
		if( i0 < 481 )
		{
			_thisActor.dropItem(killer, 135, 1);
		}
		i0 = Rnd.get(10000);
		if( i0 < 1229 )
		{
			_thisActor.dropItem(killer, 21747, 1);
		}
	}
}